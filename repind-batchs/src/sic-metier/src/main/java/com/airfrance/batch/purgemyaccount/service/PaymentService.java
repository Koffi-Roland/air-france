package com.airfrance.batch.purgemyaccount.service;

import com.afklm.repindpp.paymentpreference.dao.PaymentDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Transactional
    public void physicalDeletePayment(String gin){

        if(!paymentDetailsRepository.findByGin(gin).isEmpty()) {
            try {
                paymentDetailsRepository.deleteByGin(gin);
            } catch (Exception e) {
                log.error("Unable to delete physically payment with GIN= {}: {}", gin, e.getMessage());
            }
        }
        else{
            log.info("No payment preference found for this GIN {}", gin);
        }
    }
}
