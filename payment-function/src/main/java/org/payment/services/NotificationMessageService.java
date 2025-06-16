package org.payment.services;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import org.payment.domain.model.PaymentModel;

import java.time.LocalDateTime;

public class NotificationMessageService {

    private NotificationMessageService(){};

    private static class InstanceHolder{
        private static final NotificationMessageService INSTANCE = new NotificationMessageService();
    }

    public static NotificationMessageService getInstance (){
        return InstanceHolder.INSTANCE;
    }

    public void sendToNotificationQueue(PaymentModel paymentModel, ObjectMapper mapper, ExecutionContext context) {
        try {
            String json = mapper.writeValueAsString(paymentModel);
            context.getLogger().info("GERANDO JSON DO OBJETO PAYMENT");
            String queueName = System.getenv("NOTIFICATION_QUEUE_NAME");

            String connectionString = System.getenv("SERVICE_BUS_CONNECTION");
            ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                    .connectionString(connectionString)
                    .sender()
                    .queueName(queueName)
                    .buildClient();

            context.getLogger().info("SENDER CLIENT INSTANCIADO");

            var message = new ServiceBusMessage(json);
            message.setContentType("application/json");
            message.getApplicationProperties().put("type", "notification");
            message.getApplicationProperties().put("message", "Pagamento Aprovado com sucesso!");
            message.getApplicationProperties().put("timestamp", LocalDateTime.now().toString());

            senderClient.sendMessage(message);

            context.getLogger().info("MENSAGEM ENVIADA PARA FILA DE NOTIFICACAO");
            senderClient.close();
        } catch (Exception e) {
            context.getLogger().info("ERRO AO ENVIAR MENSAGEM:" + e.getMessage());
        }
    }
}
