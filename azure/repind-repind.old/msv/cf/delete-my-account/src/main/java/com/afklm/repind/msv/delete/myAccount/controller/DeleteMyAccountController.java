package com.afklm.repind.msv.delete.myAccount.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.delete.myAccount.controller.checher.DeleteMyAccountChecker;
import com.afklm.repind.msv.delete.myAccount.service.DeleteMyAccountService;
import com.afklm.repind.msv.delete.myAccount.wrapper.WrapperDeleteMyAccountResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/delete-my-account")
@AllArgsConstructor
public class DeleteMyAccountController {

    private DeleteMyAccountService deleteMyAccountService;
    private DeleteMyAccountChecker deleteMyAccountChecker;

    @ApiOperation(value = "Delete a myAccount contract",
            notes = "Delete a contract and data of a myAccount.<br />"
                    + "The application must be allowed to myAccount Contract.<br />"
                    + "See list of allowed applications.", response = WrapperDeleteMyAccountResponse.class)
    @DeleteMapping(value = "/", produces = "application/json; charset=utf-8")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing request  :<br />"
                    + "- business.400.001 : Gin is mandatory"),
            @ApiResponse(code = 403, message = "Bad request :<br />"
                    + "- business.403.001 : Cannot delete as other contract exist"),
            @ApiResponse(code = 404, message = "Not found : <br />"
                    + "- business.404.001 : Contract not found"),
            @ApiResponse(code = 412, message = "Invalid parameter: <br />"
                    + "- business.412.001 : Invalid value for the 'gin' parameter, length must be equal to 12")
    })
    public ResponseEntity<WrapperDeleteMyAccountResponse> deleteMyAccountContractAndData(
            @ApiParam(required = true, name = "gin", value = "Gin of the individual to remove from accountData table")
            @RequestParam(value = "gin", required = true) final String gin) throws BusinessException {
        log.info("delete myAccount contract call");
        return deleteMyAccountService.deleteMyAccount(deleteMyAccountChecker.checkDeleteMyAccountData(gin));
    }
}
