package com.afklm.repind.msv.provide.preference.data.controller;

import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.preference.data.models.error.BusinessError;
import com.afklm.repind.msv.provide.preference.data.utils.GinChecker;
import com.afklm.soa.stubs.r000380.v1.model.ProvidePreferencesData;
import com.afklm.repind.common.repository.preferences.PreferenceRepository;
import com.afklm.repind.common.repository.preferences.CommunicationPreferencesRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
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
public class GeneralController {
    /**
     * controller for communication preferences and preferences
     */

    private PreferenceController preferenceController;
    private CommunicationPreferenceController communicationPreferenceController;

    private PreferenceRepository preferenceRepository;
    private CommunicationPreferencesRepository communicationPreferencesRepository;


    @ApiOperation(value = "Search preferences and communication preferences by gin",
            notes = "Search preferences and communication preferences by gin")
    @GetMapping("/{gin}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Business errors :<br />"
                    + "- business.error.003 : No Communication preferences or Preferences found for this gin"
                    + "- business.error.004 : GIN format not correct - should have 12 digits or less")
    })
    public ResponseEntity<ProvidePreferencesData> getProvidePreferencesDataByGin(@PathVariable("gin") String gin) throws BusinessException {
        /**
         * get the preferences and communication preferences to then build the response
         * @params GIN of the individual
         * @return general response
         * @throws businessException if no preferences or communication preferences found
         */

        gin = GinChecker.checkGin(gin);

        List<CommunicationPreferencesEntity> communicationPreferencesEntities =
                communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(gin);
        List<PreferenceEntity> preferenceEntities = preferenceRepository.getPreferenceEntitiesByIndividuGin(gin);

        ProvidePreferencesData providePreferencesData = new ProvidePreferencesData();
        HttpStatus status = HttpStatus.OK;

        if(communicationPreferencesEntities.isEmpty() && preferenceEntities.isEmpty()){
            throw new BusinessException(BusinessError.GENERAL_NOT_FOUND);
        }

        if(!communicationPreferencesEntities.isEmpty()){
            providePreferencesData.setCommunicationPreferencesResponse(
                    communicationPreferenceController.buildResponse(communicationPreferencesEntities).getBody());
        }

        if(!preferenceEntities.isEmpty()){
            providePreferencesData.setPreferenceResponse(
                    preferenceController.buildResponse(preferenceEntities).getBody());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(providePreferencesData, headers, status);
    }
}
