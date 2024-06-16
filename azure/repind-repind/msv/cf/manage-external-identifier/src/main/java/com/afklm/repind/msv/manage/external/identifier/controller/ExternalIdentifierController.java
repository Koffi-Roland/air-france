package com.afklm.repind.msv.manage.external.identifier.controller;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.enums.ExternalIdentifierTypeEnum;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.manage.external.identifier.models.error.BusinessError;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifierResponse;
import com.afklm.repind.msv.manage.external.identifier.service.ExternalIdentifierService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
/*
 * A controller used to give the response to the path of the MS
 */
public class ExternalIdentifierController {
    private ExternalIdentifierService externalIdentifierService;

    /**
     * Method called to answer a request to /{gin}
     *
     * @param gin The gin related to the data we want, it's required
     * @return The ExternalIdentifierData related to our gin
     * @throws BusinessException throw when our gin isn't in the db or if the gin is incorrect
     */
    @ApiOperation(value = "Provide external contact data for a given gin", notes = "Provide external contact data for a given gin")
    @GetMapping("/{gin}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.001 : External Identifier not found"),
            @ApiResponse(code = 400, message = "Business errors :<br />"
                    + "- business.error.002 : Gin parameter should have 12 digit or less")
    })
    public ResponseEntity<ExternalIdentifierResponse> getExternalContactDataByGin(@PathVariable String gin) throws BusinessException {
        gin = this.checkGin(gin);

        log.info("Providing external contact data for GIN : {}", gin);

        List<ExternalIdentifier> externalIdentifierEntityList = externalIdentifierService.getExternalIdentifierListByGin(gin);

        if (externalIdentifierEntityList.isEmpty()) {
            throw new BusinessException(BusinessError.ALL_DATA_NOT_FOUND);
        }

        return this.buildResponseForGET(externalIdentifierEntityList);
    }

    /**
     * Method called to answer a request to /
     *
     * @param gin        The gin related to the data we want to delete, it's optional
     * @param identifier The identifier of the data we want to delete, it's required
     * @param type       The type of the data we want to delete, it's required
     * @throws BusinessException throw when our gin isn't in the db or if the input parameter aren't correct
     */
    @ApiOperation(value = "Delete external identifier data for given identifierId, type and potentially gin", notes = "Delete external identifier data for given identifierId, type and potentially gin")
    @DeleteMapping("/")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors : <br />"
                    + "- business.error.001 : External Identifier not found"),
            @ApiResponse(code = 400, message = "Business errors : <br />"
                    + "- business.error.002 : Gin parameter should have 12 digit or less <br />"
                    + "- business.error.400 : Required request parameter 'identifier' for method parameter type String is not present <br />"
                    + "- business.error.400 : Required request parameter 'type' for method parameter type String is not present <br />"
                    + "- business.error.005 : Wrong type")
    })
    public ResponseEntity<String> deleteExternalContactDataByIdentifierIdAndTypeAndGin(
            @RequestParam("identifier") String identifier,
            @RequestParam("type") String type,
            @RequestParam(value = "gin", required = false) String gin) throws BusinessException {

        if (identifier == null) {
            throw new BusinessException(BusinessError.MISSING_PARAMETER_IDENTIFIER);
        }

        if (type == null) {
            throw new BusinessException(BusinessError.MISSING_PARAMETER_TYPE);
        }

        log.info("Deleting external identifier data for identifierId : {}, type : {}, and GIN : {}",
                identifier,type,gin);

        try {
            ExternalIdentifierTypeEnum.valueOf(type);
        } catch (IllegalArgumentException ignored) {
            throw new BusinessException(BusinessError.WRONG_PARAMETER_TYPE);
        }

        if (gin != null) {
            gin = this.checkGin(gin);
        }

        externalIdentifierService.deleteExternalIdentifier(identifier, type, gin);

        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>("Deletion completed", headers, status);
    }

    /**
     * Method called to build the response once all condition are validated
     *
     * @param externalIdentifierEntityList The base of our response
     * @return Return the ExternalIdentifierResponse when the data exist in the database
     */
    public ResponseEntity<ExternalIdentifierResponse> buildResponseForGET(List<ExternalIdentifier> externalIdentifierEntityList) {
        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ExternalIdentifierResponse externalIdentifierDataResponse = this.externalIdentifierService.setExternalIdentifierDataResponse(externalIdentifierEntityList);

        return new ResponseEntity<>(externalIdentifierDataResponse, headers, status);
    }

    /**
     * A method who check if the gin is formatted the way we want or not and throw an error if it's too long.
     *
     * @param ginToCheck The gin we need to check
     * @return The gin checked and with 12 character (fill with 0 on the left)
     * @throws BusinessException if the gin is longer than 12 character
     */
    public String checkGin(String ginToCheck) throws BusinessException {
        if (ginToCheck.length() > 12) {
            throw new BusinessException(BusinessError.PARAMETER_GIN_INVALID);
        }
        return StringUtils.leftPad(ginToCheck, 12, "0");
    }
}
