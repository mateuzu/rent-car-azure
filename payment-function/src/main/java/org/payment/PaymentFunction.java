package org.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.payment.domain.model.PaymentModel;
import org.payment.factory.ObjectMapperFactory;
import org.payment.services.NotificationMessageService;
import org.payment.services.PaymentService;

/**
 * Azure Functions with Service Bus Trigger.
 */
public class PaymentFunction {

    private final PaymentService paymentService = PaymentService.getInstance();
    private final NotificationMessageService notificationMessageService = NotificationMessageService.getInstance();
    private final ObjectMapper mapper = ObjectMapperFactory.createObjectMapper();

    @FunctionName("payment")
    public void run(
            @ServiceBusQueueTrigger(
                    name = "message",
                    queueName = "%QUEUE_NAME%",
                    connection = "SERVICE_BUS_CONNECTION") String message,
            @CosmosDBOutput(
                    connection = "CosmosDBConnection",
                    name = "outputDocument",
                    containerName = "%COSMOS_CONTAINER%",
                    databaseName = "%COSMOS_DB%",
                    createIfNotExists = true,
                    partitionKey = "/idPayment") OutputBinding<PaymentModel> cosmosOutput,
            final ExecutionContext context
    ) {
        context.getLogger().info("Mensagem recebida: " + message);
        PaymentModel paymentModel;

        try {
            paymentModel = mapper.readValue(message, PaymentModel.class);

            if (paymentModel == null) {
                context.getLogger().info("NÃO FOI POSSÍVEL CONVERTER A MENSAGEM");
                throw new RuntimeException("NÃO FOI POSSÍVEL CONVERTER A MENSAGEM!");
            }
            context.getLogger().info("MENSAGEM CONVERTIDA PARA OBJETO " + paymentModel);

            paymentService.processPayment(paymentModel, context);

            if (paymentModel.getStatus().equals("Aprovado")) {
                if (!paymentModel.isNotificationEnviada()) {
                    notificationMessageService.sendToNotificationQueue(paymentModel, mapper, context);
                    paymentModel.setNotificationEnviada(true);
                }
            }

            cosmosOutput.setValue(paymentModel);
            context.getLogger().info("GERANDO SAIDA PARA COSMOS DB");

        } catch (JsonProcessingException jsonEx) {
            context.getLogger().info("ERRO AO PROCESSAR MENSAGEM: " + jsonEx.getMessage());
        } catch (Exception e) {
            context.getLogger().info("ERRO INESPERADO: " + e.getMessage());
        }
    }
}
