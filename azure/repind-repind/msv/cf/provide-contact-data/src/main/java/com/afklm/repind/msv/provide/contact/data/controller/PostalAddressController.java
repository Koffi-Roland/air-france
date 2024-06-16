package com.afklm.repind.msv.provide.contact.data.controller;

import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.contact.data.models.error.BusinessError;
import com.afklm.repind.common.repository.contact.PostalAddressRepository;
import com.afklm.repind.msv.provide.contact.data.transform.PostalAddressTransform;
import com.afklm.repind.msv.provide.contact.data.utils.GinChecker;
import com.afklm.repind.msv.provide.contact.data.utils.StatusToKeep;
import com.afklm.soa.stubs.r000347.v1.model.PostalAddressResponse;
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
public class PostalAddressController {

    private final PostalAddressRepository postalAddressRepository;
    private final PostalAddressTransform postalAddressTransform;

    @ApiOperation(value = "Search postal addresses by gin", notes = "Search postal addresses by gin")
    @GetMapping("/{gin}/addresses")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.002 : Postal addresses data not found for this gin"
                    + "- business.error.005 : GIN format not correct - should have 12 digits or less")
    })
    public ResponseEntity<List<PostalAddressResponse>> getPostalAddressesByGin(@PathVariable("gin") String gin) throws BusinessException{

        gin = GinChecker.checkGin(gin);

        log.info("Searching postal addresses for GIN : {}", gin);

        List<String> listOfStatusToKeep = Arrays.asList(StatusToKeep.I.getAcronyme(),StatusToKeep.V.getAcronyme());

        List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividuGinAndStatutMediumIn(gin, listOfStatusToKeep);
        if (postalAddresses.isEmpty()) {
            throw new BusinessException(BusinessError.POSTAL_ADDRESS_NOT_FOUND);
        }

        return buildResponse(postalAddresses);

    }

    public ResponseEntity<List<PostalAddressResponse>> buildResponse(List<PostalAddress> postalAddresses){
        HttpStatus status = HttpStatus.OK;

        List<PostalAddressResponse> postalAddressResponses = this.postalAddressTransform.boToResponse(postalAddresses);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(postalAddressResponses, headers, status);
    }

}
