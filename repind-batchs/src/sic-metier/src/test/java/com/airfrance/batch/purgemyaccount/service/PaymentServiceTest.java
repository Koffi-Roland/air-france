package com.airfrance.batch.purgemyaccount.service;

import com.afklm.repindpp.paymentpreference.dao.PaymentDetailsRepository;
import com.afklm.repindpp.paymentpreference.entity.PaymentDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentDetailsRepository paymentDetailsRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void testPhysicalDeletePayment() {
        // Given
        String gin = "400338909046";
        when(paymentDetailsRepository.findByGin(gin)).thenReturn(Collections.singletonList(new PaymentDetails()));

        // When
        paymentService.physicalDeletePayment(gin);

        // Then
        verify(paymentDetailsRepository).deleteByGin(gin);
    }
}
