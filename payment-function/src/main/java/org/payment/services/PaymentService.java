package org.payment.services;

import com.microsoft.azure.functions.ExecutionContext;
import org.payment.domain.model.PaymentModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PaymentService {
    private final String[] statusList = {"Aprovado", "Reprovado", "Em análise"};
    private final Random random = new Random();

    private PaymentService(){}

    private static class InstanceHoler {
        private static final PaymentService INSTANCE = new PaymentService();
    }

    public static PaymentService getInstance(){
        return InstanceHoler.INSTANCE;
    }

    public void processPayment(PaymentModel paymentModel, ExecutionContext context){
        int index = random.nextInt(statusList.length);
        String status = statusList[index];
        paymentModel.setStatus("Aprovado");

        context.getLogger().info("STATUS DO PAGAMENTO: " + paymentModel.getStatus());

        if("Aprovado".equalsIgnoreCase(paymentModel.getStatus())){
            paymentModel.setDataAprovacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            context.getLogger().info("PAGAMENTO APROVADO EM: " + paymentModel.getDataAprovacao());
        }
    }
}
