package com.afklm.repind.msv.provide.preference.data.unittests;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import com.afklm.repind.common.repository.preferences.CommunicationPreferencesRepository;
import com.afklm.repind.common.repository.preferences.PreferenceRepository;
import com.afklm.repind.msv.provide.preference.data.controller.CommunicationPreferenceController;
import com.afklm.repind.msv.provide.preference.data.controller.PreferenceController;
import com.afklm.repind.msv.provide.preference.data.controller.GeneralController;
import com.afklm.soa.stubs.r000380.v1.model.CommunicationPreferencesResponse;
import com.afklm.soa.stubs.r000380.v1.model.PreferenceResponse;
import com.afklm.soa.stubs.r000380.v1.model.ProvidePreferencesData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.repind.common.exception.BusinessException;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GeneralControllerTest {
    @Mock
    private PreferenceController preferenceController;

    @Mock
    private CommunicationPreferenceController communicationPreferenceController;

    @Mock
    private PreferenceRepository preferenceRepository;

    @Mock
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    @InjectMocks
    private GeneralController generalController;

    private final String GIN = "110001017463";

    private final List<CommunicationPreferencesEntity> communicationPreferencesEntities = buildMockedComPrefEntities();

    private final List<PreferenceEntity> preferenceEntities = buildMockedPrefEntities();

    private final PreferenceResponse preferenceResponse = new PreferenceResponse();
    private final CommunicationPreferencesResponse communicationPreferencesResponse = new CommunicationPreferencesResponse();

    private final HttpHeaders headers = new HttpHeaders();
    private final HttpStatus status = HttpStatus.OK;

    private Individu individu = new Individu();

    @Test
    void getProvidePreferencesDataByGinTest() throws BusinessException {
        when(communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(GIN))
                .thenReturn(communicationPreferencesEntities);
        when(preferenceRepository.getPreferenceEntitiesByIndividuGin(GIN))
                .thenReturn(preferenceEntities);
        when(communicationPreferenceController.buildResponse(communicationPreferencesEntities))
                .thenReturn(new ResponseEntity<>(communicationPreferencesResponse, headers, status));
        when(preferenceController.buildResponse(preferenceEntities))
                .thenReturn(new ResponseEntity<>(preferenceResponse, headers, status));

        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<ProvidePreferencesData> responseEntity = generalController.getProvidePreferencesDataByGin(GIN);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(headers, responseEntity.getHeaders());
    }

    private List<CommunicationPreferencesEntity> buildMockedComPrefEntities(){
        List<CommunicationPreferencesEntity> comPrefEntities = new ArrayList<>();
        Individu individu = new Individu();
        individu.setGin(GIN);

        CommunicationPreferencesEntity comPrefEntity = new CommunicationPreferencesEntity();
        comPrefEntity.setIndividu(individu);

        comPrefEntities.add(comPrefEntity);

        return comPrefEntities;
    }

    private List<PreferenceEntity> buildMockedPrefEntities(){
        List<PreferenceEntity> prefEntities = new ArrayList<>();
        individu.setGin(GIN);

        PreferenceEntity prefEntity = new PreferenceEntity();
        prefEntity.setIndividu(individu);

        prefEntities.add(prefEntity);

        return prefEntities;
    }
}
