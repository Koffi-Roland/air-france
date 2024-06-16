package com.afklm.repind.msv.provide.preference.data.controller;

import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.preference.data.models.error.BusinessError;
import com.afklm.repind.msv.provide.preference.data.utils.GinChecker;
import com.afklm.repind.msv.provide.preference.data.service.PreferenceService;
import com.afklm.soa.stubs.r000380.v1.model.PreferenceResponse;
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
public class PreferenceController {
    /**
     * controller for Preferences
     */

    private PreferenceService preferenceService;

    @ApiOperation(value = "Search preferences by gin", notes = "Search preferences by gin")
    @GetMapping("/{gin}/preference")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Business errors :<br />"
                    + "- business.error.002 : Preferences not found for this gin"
                    + "- business.error.004 : GIN format not correct - should have 12 digits or less")
    })
    public ResponseEntity<PreferenceResponse> getPreferenceByGin(@PathVariable("gin") String gin) throws BusinessException{
        /**
         * get the preferences to then build the response
         * @params GIN of the individual
         * @return buildResponse
         * @throws businessException if no preferences found
         */

        gin = GinChecker.checkGin(gin);

        log.info("Searching preferences for GIN : {}", gin);

        List<PreferenceEntity> preferenceEntities = this.preferenceService.getPreferenceEntitiesByGin(gin);

        if(preferenceEntities.isEmpty()) {
            throw new BusinessException(BusinessError.PREFS_NOT_FOUND);
        }

        return buildResponse(preferenceEntities);
    }

    public ResponseEntity<PreferenceResponse> buildResponse(List<PreferenceEntity> preferenceEntities){
        /**
         * build the response
         * @params list of the preferences
         * @return response entity
         */

        HttpStatus status = HttpStatus.OK;

        PreferenceResponse preferenceResponse = this.preferenceService.setPreferenceResponse(preferenceEntities);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(preferenceResponse, headers, status);
    }

}
