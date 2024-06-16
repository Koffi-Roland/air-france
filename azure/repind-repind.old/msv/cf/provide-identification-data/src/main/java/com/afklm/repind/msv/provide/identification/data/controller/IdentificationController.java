package com.afklm.repind.msv.provide.identification.data.controller;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.individual.UsageClientEntity;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.identification.data.helper.ProvideHelper;
import com.afklm.repind.msv.provide.identification.data.models.IdentificationTransformModel;
import com.afklm.repind.msv.provide.identification.data.models.error.BusinessError;
import com.afklm.repind.msv.provide.identification.data.service.IdentificationService;
import com.afklm.soa.stubs.r000378.v1.model.IdentificationDataResponse;
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

import java.util.List;

@RestController
@AllArgsConstructor
/*
 * A controller used to give the response to one of the path of the MS
 */
public class IdentificationController {

    private IdentificationService identificationService;

    /**
     * Method called to answer a request to /{gin}/identification
     * @param gin The gin related to the data we want
     * @return The identification related to our gin
     * @throws BusinessException throw when our gin isn't in the db or if the gin is incorrect
     */
    @ApiOperation(value = "Provide identification data for a given gin", notes = "Provide identification data for a given gin")
    @GetMapping("/{gin}/identification")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.003 : Identification data not found for this gin"),
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.005 : Gin parameter should have 12 digit or less")
    })
    public ResponseEntity<IdentificationDataResponse> getIdentificationDateByGin(@PathVariable String gin) throws BusinessException {
        gin = ProvideHelper.ginChecker(gin);

        IdentificationTransformModel tmp = buildCondition(gin);

        if (tmp.getIndividu() == null || tmp.getLanguageCode().isEmpty()) {
            throw new BusinessException(BusinessError.IDENTIFICATION_NOT_FOUND);
        }

        return buildResponse(tmp.getIndividu(),tmp.getUsagesClient(),tmp.getLanguageCode());
    }

    public IdentificationTransformModel buildCondition(String gin){
        Individu individu = this.identificationService.getIndividuByGin(gin);
        List<UsageClientEntity> usagesClient = this.identificationService.getAllUsageClientByGin(gin);
        String languageCode = "";

        if(this.identificationService.getProfilByGin(gin) != null){
            languageCode = this.identificationService.getProfilByGin(gin).getCodeLangue();
        }

        return new IdentificationTransformModel(individu,usagesClient,languageCode);
    }

    /**
     * Method called to build the response once all condition are validated
     * @param individu The base of our response
     * @param usagesClient The base of our response
     * @param languageCode The base of our response
     * @return Return the accountDataResponse when the data exist in the database
     */
    public ResponseEntity<IdentificationDataResponse> buildResponse(Individu individu, List<UsageClientEntity> usagesClient, String languageCode){
        IdentificationDataResponse identificationDataResponse = this.identificationService.setIdentificationDataResponse(individu, usagesClient, languageCode);

        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(identificationDataResponse, headers, status);
    }
}
