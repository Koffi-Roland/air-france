package com.afklm.repind.msv.search.gin.by.phone.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.phone.controller.checker.SearchGinByPhoneChecker;
import com.afklm.repind.msv.search.gin.by.phone.service.SearchGinByPhoneService;
import com.afklm.repind.msv.search.gin.by.phone.wrapper.WrapperSearchGinByPhoneResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping(value = "/")
@AllArgsConstructor
public class SearchGinByPhoneController {

    private SearchGinByPhoneService searchGinByPhoneService;
    private SearchGinByPhoneChecker searchGinByPhoneChecker;

    @ApiOperation(value = "Search Gin By Phone", notes = "Search Gin By Phone", response = WrapperSearchGinByPhoneResponse.class)
    @GetMapping(value = "/{phone}", headers = "Accept=application/json", produces = "application/json; charset=utf-8")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request :<br />"
                    + "- business.400.001 : phone is mandatory"),
            @ApiResponse(code = 412, message = "Invalid parameter :<br />"
                    + "- business.412.001 : Invalid value for the 'phone' parameter, phone must follow the normalization ")
    })
    public ResponseEntity<WrapperSearchGinByPhoneResponse> searchGinByPhone(
            @ApiParam(required = true, name = "phone", value = "Phone") @PathVariable("phone") String phone,
            @RequestParam(required = false, value="merge", defaultValue = "false") boolean merge) throws BusinessException {
        log.info("Searching GIN for phone number : {}, and merge : {}", phone, merge);
        return searchGinByPhoneService.search(searchGinByPhoneChecker.checkSearchGinByPhone(phone), merge);
    }

}
