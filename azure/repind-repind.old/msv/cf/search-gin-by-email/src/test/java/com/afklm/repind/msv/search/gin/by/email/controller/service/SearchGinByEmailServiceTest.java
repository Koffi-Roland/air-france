package com.afklm.repind.msv.search.gin.by.email.controller.service;

import com.afklm.repind.msv.search.gin.by.email.controller.utils.TestUtils;
import com.afklm.repind.msv.search.gin.by.email.repository.IEmailRepository;
import com.afklm.repind.msv.search.gin.by.email.service.SearchGinByEmailService;
import com.afklm.repind.msv.search.gin.by.email.service.encoder.SearchGinByEmailEncoder;
import com.afklm.repind.msv.search.gin.by.email.wrapper.WrapperSearchGinByEmailResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SearchGinByEmailServiceTest {

    @InjectMocks
    private SearchGinByEmailService searchGinByEmailService;

    @Mock
    private IEmailRepository emailRepository;

    @InjectMocks
    private SearchGinByEmailEncoder searchGinByEmailEncoder;

    @Mock
    private SearchGinByEmailEncoder searchGinByEmailEncoderInject;

    @Test
    public void searchByEmail() {

        when(emailRepository.findByEmailAndStatutMediumIn("jane.marple@repind.com", Arrays.asList("V" , "I"))).thenReturn(TestUtils.createEmailCollection(2));

        ResponseEntity<WrapperSearchGinByEmailResponse> response = searchGinByEmailService.search("jane.marple@repind.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

   @Test
    public void searchByEmailDecodeTest() {


        WrapperSearchGinByEmailResponse response = searchGinByEmailEncoder.decode(TestUtils.createEmailCollection(2));

       assertEquals(1,response.getGins().size());
    }
}
