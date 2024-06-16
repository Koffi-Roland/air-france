package com.afklm.repind.msv.search.gin.by.lastname.firstname.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.controller.checker.SearchGinByLastnameAndFirstnameChecker;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.service.SearchGinByLastnameAndFirstnameService;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.wrapper.WrapperSearchGinByLastnameAndFirstnameResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.afklm.repind.msv.search.gin.by.lastname.firstname.constant.ApiParameter.FIRSTNAME;
import static com.afklm.repind.msv.search.gin.by.lastname.firstname.constant.ApiParameter.LASTNAME;

@RestController
@Slf4j
@RequestMapping(value = "/")
@AllArgsConstructor
public class SearchGinByLastnameAndFirstnameController {

    private SearchGinByLastnameAndFirstnameService searchGinByLastnameAndFirstnameService;

    private SearchGinByLastnameAndFirstnameChecker searchGinByLastnameAndFirstnameChecker;

    /** * Search GIN endpoint
     * @param lastname the Lastname
     * @param firstname the Firstname
     * @return the list of gins
     * **/
    @ApiOperation(value = "Search Gin By Lastname And Firstname", notes = "Search Gin By Lastname And Firstname", response = WrapperSearchGinByLastnameAndFirstnameResponse.class)
    @GetMapping( value = "/", headers = "Accept=application/json", produces = "application/json; charset=utf-8")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request :<br />"
                    + "- business.400 : Missing Request Parameter"),
            @ApiResponse(code = 412, message = "Invalid parameter :<br />"
                    + "- business.412.001 : Invalid value for the 'lastname' parameter, lastname must be valid"),
            @ApiResponse(code = 412, message = "Invalid parameter :<br />"
                    + "- business.412.002 : Invalid value for the 'firstname' parameter, firstname must be valid")
    })
    public ResponseEntity<WrapperSearchGinByLastnameAndFirstnameResponse> searchGinByLastnameAndFirstname(
            @ApiParam(required = true, name = "lastname", value = "lastname") @RequestParam(value = LASTNAME, required = false) String lastname,
            @ApiParam(required = true, name = "firstname", value = "firstname") @RequestParam(value = FIRSTNAME, required = false) String firstname,
            @RequestParam(required = false, value="merge", defaultValue = "false") final boolean merge)
            throws BusinessException {
        String  lastN = searchGinByLastnameAndFirstnameChecker.checkSearchGinByLastname(lastname);
        String  firstN = searchGinByLastnameAndFirstnameChecker.checkSearchGinByFirstname(firstname);
        log.info("Searching GIN for lastName : {}, firstName : {}, and merge : {}", lastname,firstname,merge);

        return searchGinByLastnameAndFirstnameService.search(lastN, firstN, merge);    }
}
