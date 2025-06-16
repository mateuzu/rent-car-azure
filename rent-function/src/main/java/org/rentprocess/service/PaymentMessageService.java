package org.rentprocess.service;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsoft.azure.functions.ExecutionContext;
import org.rentprocess.domain.model.RentModel;
import org.rentprocess.factories.ObjectMapperFactory;

public class PaymentMessageService {

    private final ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

    public void sendMessageToPaymentQueue(RentModel rentModel, ExecutionContext context) {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(rentModel);
            context.getLogger().info("RENT MODEL TRANSFORMADO EM STRING");
            String queueName = System.getenv("PAYMENT_QUEUE_NAME");
            String connectionString = System.getenv("SERVICE_BUS_CONNECTION");

            ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                    .connectionString(connectionString)
                    .sender()
                    .queueName(queueName)
                    .buildClient();

            var message = new ServiceBusMessage(json);
            message.setContentType("application/json");
            message.getApplicationProperties().put("Tipo", "Pagamento");
            message.getApplicationProperties().put("Nome", rentModel.getNome());
            message.getApplicationProperties().put("Email", rentModel.getEmail());
            message.getApplicationProperties().put("Modelo", rentModel.getModelo());
            message.getApplicationProperties().put("Ano", rentModel.getAno());
            message.getApplicationProperties().put("TempoAluguel", rentModel.getTempoAluguel());
            message.getApplicationProperties().put("Data", rentModel.getData().toString());

            senderClient.sendMessage(message);

            context.getLogger().info("MENSAGEM ENVIADA PARA A FILA DE PAGAMENTO");

            senderClient.close();

        }catch (Exception e){
            context.getLogger().info("ERRO AO ENVIAR MENSAGEM PARA FILA DE PAGAMENTO: " + e.getMessage());
        }
    }
}
