package com.afklm.batch.cleanupcreditcards.writer;

import com.afklm.batch.cleanupcreditcards.service.CleanupCreditCardsSummaryService;
import com.afklm.repindpp.paymentpreference.entity.PaymentDetails;
import com.afklm.repindpp.paymentpreference.service.internal.PaymentPreferencesDS;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class CleanupCreditCardsWriter implements ItemWriter<PaymentDetails> {

    @Autowired
    private PaymentPreferencesDS paymentPreferencesDS;

    @Autowired
    private CleanupCreditCardsSummaryService summaryService;

    @Override
    @Transactional
    public void write(List<? extends PaymentDetails> list) throws Exception {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(this::deletePaymentPreference);
        }
    }

    private void deletePaymentPreference(@NotNull PaymentDetails paymentDetails){
        try {
            paymentPreferencesDS.deletePaymentPreferencesByPaymentId(String.valueOf(paymentDetails.getPaymentId()));
            summaryService.incrementDeleteSuccessCounter();
            log.info("Deleted paymentID={}", paymentDetails.getPaymentId());
        } catch (Exception e) {
            summaryService.incrementDeleteFailedCounter();
            log.error("Error while deleting paymentID={}", paymentDetails.getPaymentId());
        }
    }

}
