package com.afklm.repind.msv.delete.myAccount.service;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.commonpp.entity.paymentDetails.PaymentDetailsEntity;
import com.afklm.repind.commonpp.repository.paymentDetails.FieldsRepository;
import com.afklm.repind.commonpp.repository.paymentDetails.PaymentDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(transactionManager = "transactionManagerRepind")
public class DeleteMyAccountPaymentDetailsService {

    private final PaymentDetailsRepository paymentDetailsRepository;
    private final FieldsRepository fieldsRepository;

    public DeleteMyAccountPaymentDetailsService(
            PaymentDetailsRepository paymentDetailsRepository,
            FieldsRepository fieldsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.fieldsRepository = fieldsRepository;
    }

    public void deletePaymentPreferences(AccountIdentifier accountData) {
        String gin = accountData.getSgin();
        List<PaymentDetailsEntity> paymentDetailsEntities = paymentDetailsRepository.findByGin(gin);
        log.info("Found {} payment details to delete", paymentDetailsEntities.size(), gin);
        fieldsRepository.deleteByPaymentIdIn(paymentDetailsEntities.stream().map(PaymentDetailsEntity::getPaymentId).toList());
        paymentDetailsRepository.deleteByGin(gin);
    }

}
