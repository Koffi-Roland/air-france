package com.afklm.repind.msv.search.gin.by.contract.controller;

import com.afklm.repind.common.exception.BusinessException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.afklm.repind.msv.search.gin.by.contract.service.SearchGinByContractService;
import com.afklm.repind.msv.search.gin.by.contract.wrapper.WrapperSearchGinByContractResponse;


@RestController
@RequestMapping(value = "/")
@Slf4j
public class SearchGinByContractController {

    @Autowired
    private SearchGinByContractService searchGinByContractService;

    @ApiOperation(value = "Search Gin By Contract", notes = "Search Gin By Contract", response = WrapperSearchGinByContractResponse.class)
    @GetMapping(value = "/{num}", headers = "Accept=application/json", produces = "application/json; charset=utf-8")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request :<br />"
                    + "- business.400.001 : contract number is mandatory")
    })
    public ResponseEntity<WrapperSearchGinByContractResponse> searchGinByContract(
            @ApiParam(required = true, name = "contract", value = "Contract") @PathVariable("num") String num,
            @RequestParam(required = false, value="merge", defaultValue = "false") boolean merge) throws BusinessException {
        log.info("Searching GIN for contract number : {}, and merge : {}", num, merge);
        return searchGinByContractService.search(num, merge);
    }

}
