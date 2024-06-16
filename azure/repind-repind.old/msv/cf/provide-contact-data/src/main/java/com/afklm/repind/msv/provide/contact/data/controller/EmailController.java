package com.afklm.repind.msv.provide.contact.data.controller;


import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.contact.data.models.error.BusinessError;
import com.afklm.repind.common.repository.contact.EmailRepository;
import com.afklm.repind.msv.provide.contact.data.transform.EmailsTransform;
import com.afklm.repind.msv.provide.contact.data.utils.StatusToKeep;
import com.afklm.soa.stubs.r000347.v1.model.EmailResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.afklm.repind.msv.provide.contact.data.utils.GinChecker;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class EmailController {

    private final EmailRepository emailRepository;
    private final EmailsTransform emailsTransform;

    @ApiOperation(value = "Search emails by gin", notes = "Search emails by gin")
    @GetMapping("/{gin}/emails")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.001 : Emails data not found for this gin"
                    + "- business.error.005 : GIN format not correct - should have 12 digits or less")
    })
    public ResponseEntity<List<EmailResponse>> getEmailsByGin(@PathVariable String gin) throws BusinessException {

        gin = GinChecker.checkGin(gin);

        log.info("Searching emails for GIN : {}", gin);

        List<String> listOfStatusToKeep = Arrays.asList(StatusToKeep.I.getAcronyme(),StatusToKeep.V.getAcronyme());

        List<EmailEntity> emails = this.emailRepository.findByIndividuGinAndStatutMediumIn(gin, listOfStatusToKeep);
        if (emails.isEmpty()) {
            throw new BusinessException(BusinessError.EMAILS_NOT_FOUND);
        }

        return buildResponse(emails);

    }

    public ResponseEntity<List<EmailResponse>> buildResponse(List<EmailEntity> emails){
        HttpStatus status = HttpStatus.OK;

        List<EmailResponse> emailResponses = this.emailsTransform.boToResponse(emails);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(emailResponses, headers, status);
    }

}
