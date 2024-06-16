package com.afklm.repind.msv.provide.identification.data.controller;


import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.individual.DelegationData;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.identification.data.helper.ProvideHelper;
import com.afklm.repind.msv.provide.identification.data.models.IdentificationTransformModel;
import com.afklm.repind.msv.provide.identification.data.models.error.BusinessError;
import com.afklm.repind.msv.provide.identification.data.service.AccountService;
import com.afklm.repind.msv.provide.identification.data.service.DelegationService;
import com.afklm.repind.msv.provide.identification.data.service.IdentificationService;
import com.afklm.soa.stubs.r000378.v1.model.AccountDataResponse;
import com.afklm.soa.stubs.r000378.v1.model.DelegationDataResponse;
import com.afklm.soa.stubs.r000378.v1.model.IdentificationDataResponse;
import com.afklm.soa.stubs.r000378.v1.model.ProvideIdentificationData;
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
public class GeneralController {
    private AccountController accountController;
    private IdentificationController identificationController;
    private DelegationController delegationController;
    private AccountService accountService;
    private IdentificationService identificationService;
    private DelegationService delegationService;

    /**
     * Method called to answer a request to /{gin}
     * @param gin The gin related to the data we want
     * @return The provideIdentificationData related to our gin
     * @throws BusinessException throw when our gin isn't in the db or if the gin is incorrect
     */
    @ApiOperation(value = "Provide ProvideIdentificationData data for a given gin", notes = "Provide ProvideIdentificationData data for a given gin")
    @GetMapping("/{gin}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.004 : ProvideIdentificationData not found for this gin"),
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.005 : Gin parameter should have 12 digit or less")
    })
    public ResponseEntity<ProvideIdentificationData> getProvideIdentificationDataByGin(@PathVariable String gin) throws BusinessException {
        gin = ProvideHelper.ginChecker(gin);

        ProvideIdentificationData provideIdentificationData = new ProvideIdentificationData();

        //AccountData Check
        AccountIdentifier accountIdentifier = this.accountService.getAccountIdentifierbyGin(gin);

        //DelegationData Check
        List<DelegationData> delegatorDataEntities = this.delegationService.getDelegationDataByDelegatorGin(gin);
        List<DelegationData> delegateDataEntities = this.delegationService.getDelegationDataByDelegateGin(gin);

        //IdentificationData Check
        IdentificationTransformModel tmp = this.identificationController.buildCondition(gin);

        //IdentificationData Mapping
        boolean identificationCheck = tmp.getIndividu() != null && !tmp.getLanguageCode().isEmpty();
        provideIdentificationData.setIdentificationDataReponse(new IdentificationDataResponse());
        if(identificationCheck){
            provideIdentificationData.setIdentificationDataReponse(identificationController.buildResponse(tmp.getIndividu(),tmp.getUsagesClient(),tmp.getLanguageCode()).getBody());
        }

        //AccountData Mapping
        boolean accountCheck =accountIdentifier != null;
        provideIdentificationData.setAccountDataReponse(new AccountDataResponse());
        if(accountCheck) {
            provideIdentificationData.setAccountDataReponse(accountController.buildResponse(accountIdentifier).getBody());
        }

        //DelegationData Mapping
        boolean delegationCheck = !delegatorDataEntities.isEmpty() || !delegateDataEntities.isEmpty();
        provideIdentificationData.setDelegationDataResponse(new DelegationDataResponse());
        if(delegationCheck){
            provideIdentificationData.setDelegationDataResponse(delegationController.buildResponse(delegatorDataEntities,delegateDataEntities).getBody());
        }

        if(!delegationCheck && !accountCheck && !identificationCheck){
            throw new BusinessException(BusinessError.GENERAL_NOT_FOUND);
        }

        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(provideIdentificationData, headers, status);
    }
}
