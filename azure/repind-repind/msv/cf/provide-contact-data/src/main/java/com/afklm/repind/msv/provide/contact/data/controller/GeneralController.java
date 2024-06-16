package com.afklm.repind.msv.provide.contact.data.controller;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.contact.data.models.error.BusinessError;
import com.afklm.repind.msv.provide.contact.data.utils.GinChecker;
import com.afklm.repind.msv.provide.contact.data.utils.StatusToKeep;
import com.afklm.soa.stubs.r000347.v1.model.ProvideContactData;
import com.afklm.repind.common.repository.contact.EmailRepository;
import com.afklm.repind.common.repository.contact.PostalAddressRepository;
import com.afklm.repind.common.repository.contact.TelecomsRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
public class GeneralController {
    private EmailController emailController;
    private PostalAddressController postalAddressController;
    private TelecomsController telecomsController;

    private EmailRepository emailRepository;
    private PostalAddressRepository postalAddressRepository;
    private TelecomsRepository telecomsRepository;

    @ApiOperation(value = "Search emails, postal addresses and telecoms by gin",
            notes = "Search emails, postal addresses and telecoms by gin")
    @GetMapping("/{gin}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.004 : No contact data found for this gin"
                    + "- business.error.005 : GIN format not correct - should have 12 digits or less")
    })
    public ResponseEntity<ProvideContactData> getProvideContactDataByGin(@PathVariable("gin") String gin) throws BusinessException{

        gin = GinChecker.checkGin(gin);

        List<String> listOfStatusToKeep = Arrays.asList(StatusToKeep.I.getAcronyme(),StatusToKeep.V.getAcronyme());

        List<EmailEntity> emails = emailRepository.findByIndividuGinAndStatutMediumIn(gin, listOfStatusToKeep);
        List<PostalAddress> postalAddresses = postalAddressRepository.findByIndividuGinAndStatutMediumIn(gin, listOfStatusToKeep);
        List<Telecoms> telecoms = telecomsRepository.findByIndividuGinAndStatutMediumIn(gin, listOfStatusToKeep);

        ProvideContactData provideContactData = new ProvideContactData();
        HttpStatus status = HttpStatus.OK;

        if (emails.isEmpty() && postalAddresses.isEmpty() && telecoms.isEmpty()) {
            throw new BusinessException(BusinessError.GENERAL_NOT_FOUND);
        }

        if (!emails.isEmpty()) {
            provideContactData.setEmailResponse(emailController.buildResponse(emails).getBody());
        }

        if (!postalAddresses.isEmpty()) {
            provideContactData.setPostalAddressResponse(postalAddressController.buildResponse(postalAddresses).getBody());
        }

        if (!telecoms.isEmpty()) {
            provideContactData.setTelecomResponse(telecomsController.buildResponse(telecoms).getBody());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(provideContactData, headers, status);

    }
}
