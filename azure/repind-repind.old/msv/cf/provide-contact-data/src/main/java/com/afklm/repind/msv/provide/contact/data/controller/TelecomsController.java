package com.afklm.repind.msv.provide.contact.data.controller;

import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.contact.data.models.error.BusinessError;
import com.afklm.repind.common.repository.contact.TelecomsRepository;

import com.afklm.repind.msv.provide.contact.data.transform.TelecomsTransform;
import com.afklm.repind.msv.provide.contact.data.utils.GinChecker;
import com.afklm.repind.msv.provide.contact.data.utils.StatusToKeep;
import com.afklm.soa.stubs.r000347.v1.model.TelecomResponse;
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

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class TelecomsController {

    private final TelecomsRepository telecomsRepository;
    private final TelecomsTransform telecomsTransform;

    @ApiOperation(value = "Search telecoms by gin", notes = "Search telecoms by gin")
    @GetMapping("/{gin}/telecoms")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.003 : Telecoms data not found for this gin"
                    + "- business.error.005 : GIN format not correct - should have 12 digits or less")
    })
    public ResponseEntity<List<TelecomResponse>> getTelecomsByGin(@PathVariable String gin) throws BusinessException{

        gin = GinChecker.checkGin(gin);

        log.info("Searching telecoms for GIN : {}", gin);

        List<String> listOfStatusToKeep = Arrays.asList(StatusToKeep.I.getAcronyme(),StatusToKeep.V.getAcronyme());

        List<Telecoms> telecoms = this.telecomsRepository.findByIndividuGinAndStatutMediumIn(gin, listOfStatusToKeep);
        if (telecoms.isEmpty()) {
            throw new BusinessException(BusinessError.TELECOMS_NOT_FOUND);
        }

        return buildResponse(telecoms);

    }

    public ResponseEntity<List<TelecomResponse>> buildResponse(List<Telecoms> telecoms){
        HttpStatus status = HttpStatus.OK;

        List<TelecomResponse> telecomsResponse = this.telecomsTransform.boToResponse(telecoms);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(telecomsResponse, headers, status);
    }

}
