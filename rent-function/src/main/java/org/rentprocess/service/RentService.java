package org.rentprocess.service;

import com.microsoft.azure.functions.ExecutionContext;
import org.rentprocess.domain.RentRepository;
import org.rentprocess.domain.model.RentModel;

public class RentService {

    private final RentRepository rentRepository = new RentRepository();
    private final PaymentMessageService paymentMessageService = new PaymentMessageService();

    public void processRent(RentModel rentModel, ExecutionContext context) {
        try {
            context.getLogger().info("TENTANDO SALVAR LOCACAO...");
            var optionalRental = rentRepository.saveRent(rentModel, context);
            if (optionalRental.isPresent()) {
                context.getLogger().info("LOCACAO SALVA NO BANCO.");
                paymentMessageService.sendMessageToPaymentQueue(rentModel, context);
            } else {
                context.getLogger().severe("ERRO AO SALVAR LOCACAO.");
            }
        } catch (Exception ex) {
            context.getLogger().severe("ERRO AO PROCESSAR LOCACAO: " + ex.getMessage());
        }
    }
}
