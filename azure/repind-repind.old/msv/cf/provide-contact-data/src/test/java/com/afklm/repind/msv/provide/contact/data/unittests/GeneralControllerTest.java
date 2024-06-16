package com.afklm.repind.msv.provide.contact.data.unittests;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.repository.contact.EmailRepository;
import com.afklm.repind.common.repository.contact.PostalAddressRepository;
import com.afklm.repind.common.repository.contact.TelecomsRepository;
import com.afklm.repind.msv.provide.contact.data.controller.EmailController;
import com.afklm.repind.msv.provide.contact.data.controller.GeneralController;
import com.afklm.repind.msv.provide.contact.data.controller.PostalAddressController;
import com.afklm.repind.msv.provide.contact.data.controller.TelecomsController;
import com.afklm.repind.msv.provide.contact.data.utils.StatusToKeep;
import com.afklm.soa.stubs.r000347.v1.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.repind.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GeneralControllerTest {
    @Mock
    private EmailController emailController;

    @Mock
    private PostalAddressController postalAddressController;

    @Mock
    private TelecomsController telecomsController;

    @Mock
    private EmailRepository emailRepository;
    @Mock
    private PostalAddressRepository postalAddressRepository;
    @Mock
    private TelecomsRepository telecomsRepository;

    @InjectMocks
    private GeneralController generalController;

    private final String GIN = "110001017463";

    private final List<String> listOfStatusToKeep = Arrays.asList(StatusToKeep.I.getAcronyme(),StatusToKeep.V.getAcronyme());

    private final List<EmailEntity> emails = buildMockedEmailEntities();

    private final List<PostalAddress> postalAddresses = buildMockedPostalAddresses();

    private final List<Telecoms> telecoms = buildMockedTelecoms();

    private final EmailResponse emailResponse = new EmailResponse();
    private final PostalAddressResponse postalAddressResponse = new PostalAddressResponse();
    private final TelecomResponse telecomResponse = new TelecomResponse();

    private final HttpHeaders headers = new HttpHeaders();
    private final HttpStatus status = HttpStatus.OK;

    @Test
    void getProvideContactDataByGinTest() throws BusinessException {
        List<EmailResponse> emailResponses = Collections.singletonList(emailResponse);
        List<PostalAddressResponse> postalAddressResponses = Collections.singletonList(postalAddressResponse);
        List<TelecomResponse> telecomResponses = Collections.singletonList(telecomResponse);

        when(emailRepository.findByIndividuGinAndStatutMediumIn(GIN, listOfStatusToKeep)).thenReturn(emails);
        when(postalAddressRepository.findByIndividuGinAndStatutMediumIn(GIN, listOfStatusToKeep)).thenReturn(postalAddresses);
        when(telecomsRepository.findByIndividuGinAndStatutMediumIn(GIN, listOfStatusToKeep)).thenReturn(telecoms);

        when(emailController.buildResponse(emails))
                .thenReturn(new ResponseEntity<>(emailResponses, headers, status));
        when(postalAddressController.buildResponse(postalAddresses))
                .thenReturn(new ResponseEntity<>(postalAddressResponses, headers, status));
        when(telecomsController.buildResponse(telecoms))
                .thenReturn(new ResponseEntity<>(telecomResponses, headers, status));

        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<ProvideContactData> responseEntity = generalController.getProvideContactDataByGin(GIN);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(headers, responseEntity.getHeaders());
    }

    private List<EmailEntity> buildMockedEmailEntities(){
        List<EmailEntity> emailEntities = new ArrayList<>();

        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setAin("25521403");

        emailEntities.add(emailEntity);

        return emailEntities;
    }

    private List<PostalAddress> buildMockedPostalAddresses(){
        List<PostalAddress> postalAddressEntities = new ArrayList<>();

        PostalAddress postalAddressEntity = new PostalAddress();
        postalAddressEntity.setAin("25521403");

        postalAddressEntities.add(postalAddressEntity);

        return postalAddressEntities;
    }

    private List<Telecoms> buildMockedTelecoms(){
        List<Telecoms> telecomsEntities = new ArrayList<>();

        Telecoms telecomsEntity = new Telecoms();
        telecomsEntity.setAin("25521403");

        telecomsEntities.add(telecomsEntity);

        return telecomsEntities;
    }
}