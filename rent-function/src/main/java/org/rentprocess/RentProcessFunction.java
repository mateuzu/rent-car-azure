package org.rentprocess;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.rentprocess.domain.model.RentModel;
import org.rentprocess.factories.ObjectMapperFactory;
import org.rentprocess.service.PaymentMessageService;
import org.rentprocess.service.RentService;

import java.sql.*;

/**
 * Azure Functions with Service Bus Trigger.
 */
public class RentProcessFunction {

    private final RentService rentService = new RentService();
    private final ObjectMapper mapper = ObjectMapperFactory.createObjectMapper();

    @FunctionName("RentProcess")
    public void run(
            @ServiceBusQueueTrigger(
                    name = "message",
                    queueName = "%QUEUE_NAME%",
                    connection = "SERVICE_BUS_CONNECTION")
            String message,
            final ExecutionContext context
    ) {
        context.getLogger().info("Mensagem recebida: " + message);
        RentModel rentModel;

        try {
            rentModel = mapper.readValue(message, RentModel.class);

            if(rentModel == null){
                context.getLogger().info("MENSAGEM MAL FORMATADA");
                throw new RuntimeException("MENSAGEM MAL FORMATADA");
            }

            context.getLogger().info("MENSAGEM FORMATADA EM RENT MODEL");

            rentService.processRent(rentModel, context);

            context.getLogger().info("FUNÇÃO EXECUTADA!");

        } catch (JsonParseException jsonEx){
            context.getLogger().info("ERRO AO CONVERTER JSON: " + jsonEx.getMessage());
        }
        catch (Exception ex){
            context.getLogger().info("ERRO INTERNO: " + ex.getMessage());
        }
    }
}

