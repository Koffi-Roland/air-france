package com.afklm.repind.msv.provide.preference.data.controller;

import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.preference.data.models.error.BusinessError;
import com.afklm.repind.msv.provide.preference.data.utils.GinChecker;
import com.afklm.repind.msv.provide.preference.data.service.CommunicationPreferenceService;
import com.afklm.soa.stubs.r000380.v1.model.CommunicationPreferencesResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CommunicationPreferenceController {
    /**
     * controller for communication Preferences
     */
    private CommunicationPreferenceService communicationPreferenceService;

    @ApiOperation(value = "Search communication preferences by gin", notes = "Search communication preferences by gin")
    @GetMapping("/{gin}/communicationpreference")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.001 : Communication preferences not found for this gin"
                    + "- business.error.004 : GIN format not correct - should have 12 digits or less")
    })
    public ResponseEntity<CommunicationPreferencesResponse> getCommunicationPreferenceByGin(@PathVariable String gin) throws BusinessException{
        /**
         * get the communication preferences to then build the response
         * @params GIN of the individual
         * @return buildResponse
         * @throws businessException if no communication preferences found
         */

        gin = GinChecker.checkGin(gin);

        log.info("Searching communication preferences for GIN : {}", gin);

        List<CommunicationPreferencesEntity> communicationPreferences = this.communicationPreferenceService.getCommunicationPreferencesByGin(gin);
        if(communicationPreferences.isEmpty()) {
            throw new BusinessException(BusinessError.COMM_PREFS_NOT_FOUND);
        }

        return buildResponse(communicationPreferences);
    }

    public ResponseEntity<CommunicationPreferencesResponse> buildResponse(List<CommunicationPreferencesEntity> communicationPreferences){
        /**
         * build the response
         * @params list of the communication preferences
         * @return response entity
         */

        HttpStatus status = HttpStatus.OK;

        CommunicationPreferencesResponse communicationPreferencesResponse =
                this.communicationPreferenceService.setCommunicationPreferencesResponse(communicationPreferences);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(communicationPreferencesResponse, headers, status);
    }
}
