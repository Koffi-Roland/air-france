package com.afklm.repind.msv.provide.identification.data.controller;

import com.afklm.repind.common.entity.individual.DelegationData;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.identification.data.helper.ProvideHelper;
import com.afklm.repind.msv.provide.identification.data.models.error.BusinessError;
import com.afklm.repind.msv.provide.identification.data.service.DelegationService;
import com.afklm.soa.stubs.r000378.v1.model.DelegationDataResponse;
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
public class DelegationController {
    private DelegationService delegationService;

    /**
     * Method called to answer a request to /{gin}/delegation
     * @param gin The gin related to the data we want
     * @return The delegationData related to our gin
     * @throws BusinessException throw when our gin isn't in the db or if the gin is incorrect
     */
    @ApiOperation(value = "Provide delegation data for a given gin", notes = "Provide delegation data for a given gin")
    @GetMapping("/{gin}/delegation")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.002 : Delegation data not found for this gin"),
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.005 : Gin parameter should have 12 digit or less")
    })
    public ResponseEntity<DelegationDataResponse> getDelegationDataByGin(@PathVariable String gin) throws BusinessException {
        gin = ProvideHelper.ginChecker(gin);

        List<DelegationData> delegatorDataEntities = this.delegationService.getDelegationDataByDelegatorGin(gin);
        List<DelegationData> delegateDataEntities = this.delegationService.getDelegationDataByDelegateGin(gin);

        if (delegatorDataEntities.isEmpty() && delegateDataEntities.isEmpty()) {
            throw new BusinessException(BusinessError.DELEGATION_NOT_FOUND);
        }

        return buildResponse(delegatorDataEntities,delegateDataEntities);
    }

    /**
     * Method called to build the response once all condition are validated
     * @param delegatorDataEntities The base of our response
     * @param delegateDataEntities The base of our response
     * @return Return the delegationData when the data exist in the database
     */
    public ResponseEntity<DelegationDataResponse> buildResponse(List<DelegationData> delegatorDataEntities, List<DelegationData> delegateDataEntities){
        DelegationDataResponse delegationDataResponse = new DelegationDataResponse();

        //Process for delegate
        delegationService.setDelegateResponse(delegationDataResponse,delegatorDataEntities);
        //Process for delegator
        delegationService.setDelegatorResponse(delegationDataResponse,delegateDataEntities);

        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(delegationDataResponse, headers, status);
    }
}
