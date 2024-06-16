package com.afklm.repind.msv.search.individual.v2.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.individual.v2.controller.checker.SearchIndividualCheckerV2;
import com.afklm.repind.msv.search.individual.v2.service.SearchServiceV2;
import com.afklm.repind.msv.search.individual.v2.wrapper.WrapperSearchIndividualV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.afklm.repind.msv.search.individual.constant.ApiParameter.*;


/**
 * Search Rest Controller
 */
@RestController
@RequestMapping("/v2/search-individual")
@Slf4j
public class SearchRestControllerV2 {

    @Autowired
    private SearchServiceV2 searchServiceV2;

    @Autowired
    private SearchIndividualCheckerV2 searchChecker;

    /**
     * Search Individual endpoint
     * @param cin the cin
     * @param email the email
     * @param internationalPhoneNumber the phone
     * @param externalIdentifierId the externalIdentifierId
     * @param externalIdentifierType the externalIdentifierType
     * @param firstname the firstname
     * @param lastname the lastname
     * @param merge if we return merged individuals or not
     * @return the list of individual
     * @throws BusinessException
     */
    @Operation(summary = "Search Individual", description = "Search Individual"/*, response = List.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request :<br />"
                    + "- business.400 : At least one parameter must be provided : cin or email or internationalPhoneNumber or socialIdentifer with socialType"),
            @ApiResponse(responseCode = "412", description = "Invalid parameter :<br />"
                    + "- business.412.002 : Invalid value for the 'cin' parameter, cin must be valid"),
            @ApiResponse(responseCode = "412", description = "Invalid parameter :<br />"
                    + "- business.412.003 : Invalid value for the 'email' parameter, email must be valid"),
            @ApiResponse(responseCode = "412", description = "Invalid parameter :<br />"
                    + "- business.412.004 : Invalid value for the 'internationalPhoneNumber' parameter, internationalPhoneNumber must be valid"),
            @ApiResponse(responseCode = "412", description = "Invalid parameter :<br />"
                    + "- business.412.005 : Invalid value for the 'External Identifier' parameter, external identifier must be valid"),
            @ApiResponse(responseCode = "412", description = "Invalid parameter :<br />"
                    + "- business.412.006 : Invalid value for the 'External Type' parameter, external type must be valid"),
            @ApiResponse(responseCode = "412", description = "Invalid parameter :<br />"
                    + "- business.412.007 : Invalid value for the 'Lastname' parameter, Lastname must be valid")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<WrapperSearchIndividualV2>> search(
            @RequestParam(value = CIN, required = false) String cin,
            @RequestParam(value = EMAIL, required = false) String email,
            @RequestParam(value = INTERNATIONAL_PHONE_NUMBER, required = false) String internationalPhoneNumber,
            @RequestParam(value = EXTERNAL_IDENTIFIER_ID, required = false) String externalIdentifierId,
            @RequestParam(value = EXTERNAL_IDENTIFIER_TYPE, required = false) String externalIdentifierType,
            @RequestParam(value = FIRST_NAME, required = false) String firstname,
            @RequestParam(value = LAST_NAME, required = false) String lastname,
            @RequestParam(value = MERGE, required = false, defaultValue = "false") boolean merge)
            throws BusinessException {

        log.info("Search input. cin {}, email {}, internationalPhoneNumber {}, externalIdentifierId{}, externalIdentifierType{}, firstname {}, lastname {}, merge {}",
                cin, email, internationalPhoneNumber, externalIdentifierId, externalIdentifierType, firstname, lastname, merge);

        // validate gin/cin/email/phone/externalIdentifierId/externalIdentifierType/firstname/lastname
        searchChecker.checkSearchIndividual(cin, email, internationalPhoneNumber, externalIdentifierId ,externalIdentifierType, firstname, lastname);

        List<String> gins = searchServiceV2.searchBy(cin, email, internationalPhoneNumber, externalIdentifierId, externalIdentifierType, firstname, lastname, merge);

        return new ResponseEntity<>(gins.stream().map(WrapperSearchIndividualV2::new).collect(Collectors.toList()), HttpStatus.OK);
    }
}
