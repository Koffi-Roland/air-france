package com.afklm.repind.msv.manage.individual.identifier.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.manage.individual.identifier.response.WrapperFindGinByIdentifierResponse;
import com.afklm.repind.msv.manage.individual.identifier.service.IdentifierService;
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
public class ProvideGinByIdentifierController {

    private IdentifierService identifierService;

    @ApiOperation(value = "Get GIN By email ID", notes = "Retrieve GIN By email ID", response = WrapperFindGinByIdentifierResponse.class)
    @GetMapping(value = "/email/{email:.+}", headers = "Accept=application/json", produces = "application/json; charset=utf-8")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Business errors :<br />"
                    + "- 382 : Email already used by flying blue members"),
            @ApiResponse(code = 404, message = "GIN not found :<br />"
                    + "- 001 : GIN not found"),
            @ApiResponse(code = 412, message = "Invalid parameter :<br />"
                    + "- business.412.001 : Invalid value for the 'email' parameter, email must be valid")
    })
    public ResponseEntity<WrapperFindGinByIdentifierResponse> getGinByEmail(
            @ApiParam(required = true, name = "email", value = "email") @PathVariable final String email) throws BusinessException {
        return identifierService.findGinByEmail(email);
    }

    @ApiOperation(value = "Get GIN by CIN ID", notes = "Search Gin By CIN ID (Support only Flying Blue contract ID)", response = WrapperFindGinByIdentifierResponse.class)
    @GetMapping(value = "/contract/{cin}", headers = "Accept=application/json", produces = "application/json; charset=utf-8")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "GIN not found :<br />"
                    + "- 001 : GIN not found"),
            @ApiResponse(code = 412, message = "Invalid parameter :<br />"
                    + "- business.412.002 : Invalid value for the 'cin' parameter, CIN should be between 10 and 12 characters and only contain numbers.")
    })
    public ResponseEntity<WrapperFindGinByIdentifierResponse> getGinByCin(
            @ApiParam(required = true, name = "cin", value = "cin") @PathVariable final String cin) throws BusinessException {
        return identifierService.findGinByContract(cin);
    }
}
