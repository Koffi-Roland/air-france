package com.afklm.repind.msv.provide.contract.data.controller;

import com.afklm.repind.common.enums.IdentifierTypeEnum;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.contract.data.models.error.BusinessError;
import com.afklm.repind.msv.provide.contract.data.models.stubs.Contract;
import com.afklm.repind.msv.provide.contract.data.service.ContractService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
/*
 * This class is the controller of the MS, it's responsible for the request handling
 */
public class Controller {
    private ContractService contractService;

    /**
     * This method is the controller of the request /{type}/{identifier}
     *
     * @param type       can be GIN or CIN, it's not case-sensitive for the type
     * @param identifier the identifier of the specified type
     * @return The ResponseEntity containing a list of the contract of the individual linked to the given parameter
     * @throws BusinessException It's returned if we found nothing or if we have a problem with the parameter.
     */
    @ApiOperation(value = "Provide ProvideIdentificationData data for a given gin", notes = "Provide ProvideIdentificationData data for a given gin")
    @GetMapping("/{type}/{identifier}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Business errors :<br />"
                    + "- business.error.001 : Contract data not found for this gin"),
            @ApiResponse(responseCode = "403", description = "Business errors :<br />"
                    + "- business.error.002 : Type or Identifier of individual is missing"),
            @ApiResponse(responseCode = "403", description = "Business errors :<br />"
                    + "- business.error.003 : Type must be CIN or GIN only"),
            @ApiResponse(responseCode = "403", description = "Business errors :<br />"
                    + "- business.error.004 : Cin parameter is incorrect"),
            @ApiResponse(responseCode = "403", description = "Business errors :<br />"
                    + "- business.error.005 : Gin parameter should have 12 digit or less")
    })
    public ResponseEntity<List<Contract>> getAllContract(@PathVariable("type") String type, @PathVariable("identifier") String identifier) throws BusinessException {
        identifier = checkParameter(type, identifier);

        List<Contract> contractList = contractService.getContractList(type, identifier);
        if (contractList == null || contractList.isEmpty()) {
            log.error("No contract found for this request type " + type + " and identifier " + identifier);
            throw new BusinessException(BusinessError.CONTRACT_NOT_FOUND);
        }
        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(contractList, headers, status);
    }

    /**
     * This method check the parameter and return the formatted identifier
     *
     * @param type       The type of the identifier
     * @param identifier The identifier
     * @return The formatted identifier
     * @throws BusinessException Can be thrown if the type/identifier is null or empty or if the identifier is incorrect
     */
    public String checkParameter(String type, String identifier) throws BusinessException {
        checkType(type);
        if (identifier == null || identifier.isEmpty()) {
            log.error("Missing identifier for this request");
            throw new BusinessException(BusinessError.MISSING_PARAMETER);
        }
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (IdentifierTypeEnum.valueOf(type.toUpperCase()).equals(IdentifierTypeEnum.CIN)) {
            if ((identifier.length() == 8 && alphabet.contains(String.valueOf(identifier.charAt(6)).toUpperCase())
                    && alphabet.contains(String.valueOf(identifier.charAt(7)).toUpperCase())) ||  List.of(15,20).contains(identifier.length())) {
                return identifier;
            }
            if (List.of(12,11,10,9,8).contains(identifier.length())) {
                return StringUtils.leftPad(identifier, 12, '0');
            }
            else {
                log.error("CIN parameter incorrect : " + identifier);
                throw new BusinessException(BusinessError.PARAMETER_CIN_INVALID);
            }
        } else {
            if (identifier.length() <= 12) {
                return StringUtils.leftPad(identifier, 12, "0");
            } else {
                log.error("GIN parameter incorrect : " + identifier);
                throw new BusinessException(BusinessError.PARAMETER_GIN_INVALID);
            }
        }
    }

    /**
     * This method is specifically designed to throw an error if we have a bad type (CIN or GIN) for the request
     * @param type The type just clarify which type of identifier we have
     * @throws BusinessException Can be thrown if we have a missing parameter or if the parameter is invalid
     */
    public void checkType(String type) throws BusinessException {
        if (type == null || type.isEmpty()) {
            log.error("Missing Parameter for this request.");
            throw new BusinessException(BusinessError.MISSING_PARAMETER);
        }
        try {
            IdentifierTypeEnum.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Incorrect type for this request");
            throw new BusinessException(BusinessError.PARAMETER_TYPE_INVALID);
        }
    }
}
