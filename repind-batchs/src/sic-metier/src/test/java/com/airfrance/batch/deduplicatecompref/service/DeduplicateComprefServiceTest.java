package com.airfrance.batch.deduplicatecompref.service;

import com.airfrance.batch.deduplicatecompref.model.CommunicationPreferencesModel;
import com.airfrance.repind.bean.CommunicationPreferencesBean;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Unit testing service layer - Deduplication communication preferences service
 */
@ExtendWith(MockitoExtension.class)
public class DeduplicateComprefServiceTest {

    /**
     * Communication preferences repository mock
     */
    @Mock
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    /**
     * Deduplicatae communication preferences mock
     */
    @InjectMocks
    private DeduplicateComprefService deduplicateComprefService;

    /**
     * Communication preference model
     */
    private CommunicationPreferencesModel communicationPreferencesModel;

    /**
     * Communication preferences bean
     */
    private CommunicationPreferencesBean communicationPreferences;

    @BeforeEach
    public void setup()
    {
        //given - precondition or setup
        this.communicationPreferencesModel = CommunicationPreferencesModel.builder()
                .comPrefId(18200112)
                .gin("110000038701")
                .comType("AF")
                .build();

        this.communicationPreferences = CommunicationPreferencesBean.builder()
                .comPrefId(18200112)
                .gin("110000038701")
                .build();
    }

    @Test
    @DisplayName("JUnit test for testing batch processor SNKL")
    public void testFindCommunicationPreferencesSNAForSNKL()
    {
        //Given
        List<CommunicationPreferencesBean> communicationPreferencesBeans = new ArrayList<>();
        communicationPreferencesBeans.add(this.communicationPreferences);
        //When
        Mockito.when(communicationPreferencesRepository.getCommunicationPreferencesSNAForSNKL(this.communicationPreferencesModel.getComPrefId(),this.communicationPreferencesModel.getGin(),this.communicationPreferencesModel.getComType())).thenReturn(communicationPreferencesBeans);
        List<CommunicationPreferencesBean> communicationPreferencesBeansTest = this.deduplicateComprefService.findCommunicationPreferences(this.communicationPreferencesModel);
        //Then -verify the output
        assertThat(communicationPreferencesBeansTest).isNotNull();
        assertThat(communicationPreferencesBeansTest.size()).isEqualTo(1);
    }

}
