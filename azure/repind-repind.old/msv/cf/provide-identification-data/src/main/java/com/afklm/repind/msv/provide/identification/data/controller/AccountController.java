package com.afklm.repind.msv.provide.identification.data.controller;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.identification.data.helper.ProvideHelper;
import com.afklm.repind.msv.provide.identification.data.models.error.BusinessError;
import com.afklm.repind.msv.provide.identification.data.service.AccountService;
import com.afklm.soa.stubs.r000378.v1.model.AccountDataResponse;
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

@RestController
@AllArgsConstructor
/*
 * A controller used to give the response to one of the path of the MS
 */
public class AccountController {

    private AccountService accountService;

    /**
     * Method called to answer a request to /{gin}/account
     * @param gin The gin related to the data we want
     * @return The accountData related to our gin
     * @throws BusinessException throw when our gin isn't in the db or if the gin is incorrect
     */
    @ApiOperation(value = "Provide account data for a given gin", notes = "Provide account data for a given gin")
    @GetMapping("/{gin}/account")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.001 : Account data not found for this gin"),
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.005 : Gin parameter should have 12 digit or less")
    })
    public ResponseEntity<AccountDataResponse> getAccountDataByGin(@PathVariable String gin) throws BusinessException {
        gin = ProvideHelper.ginChecker(gin);

        AccountIdentifier accountIdentifier = this.accountService.getAccountIdentifierbyGin(gin);

        if (accountIdentifier == null) {
            throw new BusinessException(BusinessError.ACCOUNT_NOT_FOUND);
        }

         return buildResponse(accountIdentifier);

    }

    /**
     * Method called to build the response once all condition are validated
     * @param accountIdentifier The base of our response
     * @return Return the accountDataResponse when the data exist in the database
     */
    public ResponseEntity<AccountDataResponse> buildResponse(AccountIdentifier accountIdentifier){
        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        AccountDataResponse accountDataResponse = this.accountService.setAccountDataResponse(accountIdentifier);

        return new ResponseEntity<>(accountDataResponse, headers, status);
    }
}
