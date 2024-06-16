package com.airfrance.batch.purgemyaccount.writer;

import com.airfrance.batch.purgemyaccount.model.PayPhysicalDelete;
import com.airfrance.batch.purgemyaccount.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class PayPhysicalDeleteWriter implements ItemWriter<PayPhysicalDelete> {

    @Autowired
    private PaymentService paymentService;

    /**
     * Write process for delete physically payment details
     *
     * @param payPhysicalDeleteItems payment model
     * @throws Exception exception
     */
    @Override
    public void write(@NotNull List<? extends PayPhysicalDelete> payPhysicalDeleteItems) throws Exception {
        if (!CollectionUtils.isEmpty(payPhysicalDeleteItems)) {
            payPhysicalDeleteItems.forEach(item -> {
                this.paymentService.physicalDeletePayment(item.getGin());
            });
        }
        else
        {
            log.error("The list of data is empty.");
            throw new IllegalArgumentException("The list of data is empty...");
        }
    }
}
