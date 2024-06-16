package com.afklm.repind.msv.search.gin.by.social.media.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.social.media.controller.checker.SearchGinByExternalIdentifierChecker;
import com.afklm.repind.msv.search.gin.by.social.media.service.SearchGinByExternalIdentifierService;
import com.afklm.repind.msv.search.gin.by.social.media.wrapper.WrapperSearchGinByExternalIdentifierResponse;
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

import static com.afklm.repind.msv.search.gin.by.social.media.constant.ApiParameter.EXTERNAL_IDENTIFIER_ID;
import static com.afklm.repind.msv.search.gin.by.social.media.constant.ApiParameter.EXTERNAL_IDENTIFIER_TYPE;


@RestController
@Slf4j
@RequestMapping(value = "/")
@AllArgsConstructor
public class SearchGinByExternalIdentifierController {
    /**
     * controller for SearchByExternalIdentifier
     */

    private SearchGinByExternalIdentifierService searchGinByExternalIdentifierService;
    private SearchGinByExternalIdentifierChecker searchGinByExternalIdentifierChecker;

    /** * Search GIN endpoint
     * @param externalIdentifierId the externalIdentifierId
     * @param externalIdentifierType the externalIdentifierType
     * @return the list of gins
     * **/
    @ApiOperation(value = "Search Gin By External Identifier", notes = "Search Gin By External Identifier", response = WrapperSearchGinByExternalIdentifierResponse.class)
    @GetMapping(value = "/", headers = "Accept=application/json", produces = "application/json; charset=utf-8")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request :<br />"
                    + "- business.400 : Missing Parameter"),
            @ApiResponse(code = 404, message = "Not Found :<br />"
                    + "- business.404.001 : External Identifier Type not found"),
            @ApiResponse(code = 412, message = "Invalid parameter :<br />"
                    + "- business.412 : Mismatch type parameter"),
            @ApiResponse(code = 412, message = "Invalid parameter :<br />"
                    + "- business.412.001 : Invalid value for the 'External Identifier' parameter, external identifier must be valid"),
            @ApiResponse(code = 412, message = "Invalid parameter :<br />"
                    + "- business.412.002 : Invalid value for the 'External Type' parameter, external type must be valid")
    })
    public ResponseEntity<WrapperSearchGinByExternalIdentifierResponse> searchGinByExternalIdentifier(
            @ApiParam(required = true, name = "externalIdentifierId", value = "externalIdentifierId") @RequestParam(value = EXTERNAL_IDENTIFIER_ID, required = false) String externalIdentifierId,
            @ApiParam(required = true, name = "externalIdentifierType", value = "externalIdentifierType") @RequestParam(value = EXTERNAL_IDENTIFIER_TYPE, required = false) String externalIdentifierType,
            @RequestParam(required = false, value="merge", defaultValue = "false") boolean merge)
            throws BusinessException {
        /**
         * search the GINs corresponding to the parameters
         * @params externalIdentifier and externalType
         * @return response
         * @throws businessException if lack of parameters or externalIdentifierId have a wrong format
         */

        String  id = searchGinByExternalIdentifierChecker.checkSearchGinByExternalIdentifierId(externalIdentifierId);
        String  type = searchGinByExternalIdentifierChecker.checkSearchGinByExternalIdentifierType(externalIdentifierType);
        log.info("Searching GIN for external identifier : {}, external type : {}, and merge : {}", id,type,merge);
        return searchGinByExternalIdentifierService.search(id, type, merge);
    }
}
