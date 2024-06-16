package com.afklm.repind.msv.search.gin.by.email.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.email.controller.checker.SearchGinByEmailChecker;
import com.afklm.repind.msv.search.gin.by.email.service.SearchGinByEmailService;
import com.afklm.repind.msv.search.gin.by.email.wrapper.WrapperSearchGinByEmailResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping(value = "/")
@AllArgsConstructor
public class SearchGinByEmailController {

    private SearchGinByEmailService searchGinByEmailService;

    private SearchGinByEmailChecker searchGinByEmailChecker;

    @ApiOperation(value = "Search Gin By Email", notes = "Search Gin By Email", response = WrapperSearchGinByEmailResponse.class)
    @GetMapping(value = "/{email:.+}", headers = "Accept=application/json", produces = "application/json; charset=utf-8")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request :<br />"
                    + "- business.400.001 : email is mandatory"),
            @ApiResponse(code = 412, message = "Invalid parameter :<br />"
                    + "- business.412.001 : Invalid value for the 'email' parameter, email must be valid")
    })
    public ResponseEntity<WrapperSearchGinByEmailResponse> searchGinByEmails(
            @ApiParam(required = true, name = "email", value = "Email") @PathVariable final String email) throws BusinessException {
        return searchGinByEmailService.search(searchGinByEmailChecker.checkSearchGinByEmail(email));
    }

}
