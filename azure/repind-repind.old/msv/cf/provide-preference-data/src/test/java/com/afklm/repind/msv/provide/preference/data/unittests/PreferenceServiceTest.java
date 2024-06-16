package com.afklm.repind.msv.provide.preference.data.unittests;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.preferences.PreferenceDataEntity;
import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import com.afklm.repind.common.repository.preferences.PreferenceDataRepository;
import com.afklm.repind.common.repository.preferences.PreferenceRepository;
import com.afklm.repind.msv.provide.preference.data.service.PreferenceService;
import com.afklm.repind.msv.provide.preference.data.transform.PreferenceTransform;
import com.afklm.soa.stubs.r000380.v1.model.Preference;
import com.afklm.soa.stubs.r000380.v1.model.PreferenceData;
import com.afklm.soa.stubs.r000380.v1.model.PreferenceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PreferenceServiceTest {
    @Mock
    private PreferenceDataRepository preferenceDataRepository;
    @Mock
    private PreferenceRepository preferenceRepository;
    @Mock
    private PreferenceTransform preferenceTransform;
    @InjectMocks
    private PreferenceService preferenceService;

    private final String GIN = "110001017463";

    private Individu individu = new Individu();

    /* --------- UCO --------- */
    private final Date dateCreationUCO = new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-30");
    private final Date dateModificationUCO = null;
    private final Long preferenceIdUCO = Long.valueOf(34736787);
    private final Long linkUCO = null;
    private final String signatureCreationUCO = "PRGDATAGOV-139";
    private final String signatureModificationUCO = null;
    private final String siteCreationUCO = "BATCH_QVI";
    private final String siteModificationUCO = null;
    private final String typeUCO = "UCO";

    /* --------- UTS --------- */
    private final Date dateCreationUTS = new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22");
    private final Date dateModificationUTS = null;
    private final Long preferenceIdUTS = Long.valueOf(34727556);
    private final Long linkUTS = null;
    private final String signatureCreationUTS = "PRGDATAGOV-139";
    private final String signatureModificationUTS = null;
    private final String siteCreationUTS = "BATCH_QVI";
    private final String siteModificationUTS = null;
    private final String typeUTS = "UTS";

    /* --------- ULO --------- */
    private final Date dateCreationULO = new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22");
    private final Date dateModificationULO = null;
    private final Long preferenceIdULO = Long.valueOf(34727557);
    private final Long linkULO = null;
    private final String signatureCreationULO = "PRGDATAGOV-139";
    private final String signatureModificationULO = null;
    private final String siteCreationULO = "BATCH_QVI";
    private final String siteModificationULO = null;
    private final String typeULO = "ULO";

    /* --------- UFB --------- */
    private final Date dateCreationUFB = new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22");
    private final Date dateModificationUFB = null;
    private final Long preferenceIdUFB = Long.valueOf(34727558);
    private final Long linkUFB = null;
    private final String signatureCreationUFB = "PRGDATAGOV-139";
    private final String signatureModificationUFB = null;
    private final String siteCreationUFB = "BATCH_QVI";
    private final String siteModificationUFB = null;
    private final String typeUFB = "UFB";

    public PreferenceServiceTest() throws ParseException {
    }

    @Test
    void getPreferenceDataTest() throws ParseException{
        List<PreferenceData> preferenceDatas = preferenceService.getPreferenceData(buildMockedPreferenceDataEntities());
        assertEquals(4,preferenceDatas.size());

        PreferenceData preferenceData1 = preferenceDatas.get(0);
        PreferenceData preferenceData2 = preferenceDatas.get(1);
        PreferenceData preferenceData3 = preferenceDatas.get(2);
        PreferenceData preferenceData4 = preferenceDatas.get(3);

        /* UCO Preference Data */
        assertEquals("preferredCommunicationChannel",preferenceData1.getKey());
        assertEquals("Pas un iphone",preferenceData1.getValue());
        assertEquals("BATCH_QVI",preferenceData1.getSiteCreation());
        assertEquals("PRGDATAGOV-139",preferenceData1.getSignatureCreation());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-30").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preferenceData1.getDateCreation());

        /* UTS Preference Data */
        assertEquals("customerDetails",preferenceData2.getKey());
        assertEquals("DOCS: P/FR/15FV14448/FR/30JUL51/M/01FEB22",preferenceData2.getValue());
        assertEquals("BATCH_QVI",preferenceData2.getSiteCreation());
        assertEquals("PRGDATAGOV-139",preferenceData2.getSignatureCreation());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preferenceData2.getDateCreation());

        /* ULO Preference Data */
        assertEquals("specificAttention",preferenceData3.getKey());
        assertEquals("Newspapers of the day + Le Point + Challenges. \n" +
                "Enjoy champagne.",preferenceData3.getValue());
        assertEquals("BATCH_QVI",preferenceData3.getSiteCreation());
        assertEquals("PRGDATAGOV-139",preferenceData3.getSignatureCreation());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preferenceData3.getDateCreation());

        /* UFB Preference Data */
        assertEquals("mealPreferences",preferenceData4.getKey());
        assertEquals("Cold breakfast + black coffee or expresso/ like red wine PESSAC",preferenceData4.getValue());
        assertEquals("BATCH_QVI",preferenceData4.getSiteCreation());
        assertEquals("PRGDATAGOV-139",preferenceData4.getSignatureCreation());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preferenceData4.getDateCreation());
    }

    @Test
    void setPreferenceResponseTest() throws ParseException {
        List<PreferenceEntity> preferenceEntities = buildMockedPreferenceEntities();
        List<PreferenceDataEntity> preferenceDataEntities = buildMockedPreferenceDataEntities();
        List<Preference> preferences = buildMockedPreferences();
        PreferenceResponse preferenceResponse = buildMockedPreferenceResponse();

        PreferenceEntity preferenceEntityUCO = preferenceEntities.get(0);
        PreferenceEntity preferenceEntityUTS = preferenceEntities.get(1);
        PreferenceEntity preferenceEntityULO = preferenceEntities.get(2);
        PreferenceEntity preferenceEntityUFB = preferenceEntities.get(3);

        PreferenceDataEntity preferenceDataEntityUCO = preferenceDataEntities.get(0);
        PreferenceDataEntity preferenceDataEntityUTS = preferenceDataEntities.get(1);
        PreferenceDataEntity preferenceDataEntityULO = preferenceDataEntities.get(2);
        PreferenceDataEntity preferenceDataEntityUFB = preferenceDataEntities.get(3);

        Preference preferenceUCO = preferences.get(0);
        Preference preferenceUTS = preferences.get(1);
        Preference preferenceULO = preferences.get(2);
        Preference preferenceUFB = preferences.get(3);

        List<PreferenceData> preferenceDataUCO =
                preferenceService.getPreferenceData(Collections.singletonList(preferenceDataEntityUCO));
        List<PreferenceData> preferenceDataUTS =
                preferenceService.getPreferenceData(Collections.singletonList(preferenceDataEntityUTS));
        List<PreferenceData> preferenceDataULO =
                preferenceService.getPreferenceData(Collections.singletonList(preferenceDataEntityULO));
        List<PreferenceData> preferenceDataUFB =
                preferenceService.getPreferenceData(Collections.singletonList(preferenceDataEntityUFB));

        when(preferenceDataRepository.getPreferenceDataEntitiesByPreferencePreferenceId(preferenceIdUCO))
                .thenReturn(Collections.singletonList(preferenceDataEntityUCO));
        when(preferenceDataRepository.getPreferenceDataEntitiesByPreferencePreferenceId(preferenceIdUTS))
                .thenReturn(Collections.singletonList(preferenceDataEntityUTS));
        when(preferenceDataRepository.getPreferenceDataEntitiesByPreferencePreferenceId(preferenceIdULO))
                .thenReturn(Collections.singletonList(preferenceDataEntityULO));
        when(preferenceDataRepository.getPreferenceDataEntitiesByPreferencePreferenceId(preferenceIdUFB))
                .thenReturn(Collections.singletonList(preferenceDataEntityUFB));

        when(preferenceTransform.setPreference(preferenceEntityUCO,preferenceDataUCO))
                .thenReturn(preferenceUCO);
        when(preferenceTransform.setPreference(preferenceEntityUTS,preferenceDataUTS))
                .thenReturn(preferenceUTS);
        when(preferenceTransform.setPreference(preferenceEntityULO,preferenceDataULO))
                .thenReturn(preferenceULO);
        when(preferenceTransform.setPreference(preferenceEntityUFB,preferenceDataUFB))
                .thenReturn(preferenceUFB);

        when(preferenceTransform.setPreferenceResponse(preferences))
                .thenReturn(preferenceResponse);

        PreferenceResponse response = preferenceService.setPreferenceResponse(preferenceEntities);
        List<Preference> responsePreferences = response.getPreferences();
        assertEquals(4,preferences.size());

        Preference preference1 = responsePreferences.get(0);
        Preference preference2 = responsePreferences.get(1);
        Preference preference3 = responsePreferences.get(2);
        Preference preference4 = responsePreferences.get(3);

        PreferenceData preferenceData1 = preference1.getPreferenceData().get(0);
        PreferenceData preferenceData2 = preference2.getPreferenceData().get(0);
        PreferenceData preferenceData3 = preference3.getPreferenceData().get(0);
        PreferenceData preferenceData4 = preference4.getPreferenceData().get(0);

        /* ----------------- PREFERENCES ----------------- */
        /* UCO Preference */
        assertEquals("UCO",preference1.getType());
        assertEquals("BATCH_QVI",preference1.getSiteCreation());
        assertEquals("PRGDATAGOV-139",preference1.getSignatureCreation());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-30").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preference1.getDateCreation());

        /* UTS Preference */
        assertEquals("UTS",preference2.getType());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preference2.getDateCreation());

        /* ULO Preference */
        assertEquals("ULO",preference3.getType());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preference3.getDateCreation());

        /* UFB Preference */
        assertEquals("UFB",preference4.getType());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preference4.getDateCreation());

        /* ----------------- PREFERENCE DATAS ----------------- */
        /* UCO Preference Data */
        assertEquals("preferredCommunicationChannel",preferenceData1.getKey());
        assertEquals("Pas un iphone",preferenceData1.getValue());
        assertEquals("BATCH_QVI",preferenceData1.getSiteCreation());
        assertEquals("PRGDATAGOV-139",preferenceData1.getSignatureCreation());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-30").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preferenceData1.getDateCreation());

        /* UTS Preference Data */
        assertEquals("customerDetails",preferenceData2.getKey());
        assertEquals("DOCS: P/FR/15FV14448/FR/30JUL51/M/01FEB22",preferenceData2.getValue());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preferenceData2.getDateCreation());

        /* ULO Preference Data */
        assertEquals("specificAttention",preferenceData3.getKey());
        assertEquals("Newspapers of the day + Le Point + Challenges. \n" +
                "Enjoy champagne.",preferenceData3.getValue());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preferenceData3.getDateCreation());

        /* UFB Preference Data */
        assertEquals("mealPreferences",preferenceData4.getKey());
        assertEquals("Cold breakfast + black coffee or expresso/ like red wine PESSAC",preferenceData4.getValue());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),preferenceData4.getDateCreation());
    }

    private List<PreferenceEntity> buildMockedPreferenceEntities() throws ParseException {
        List<PreferenceEntity> preferenceEntities = new ArrayList<>();
        individu.setGin(GIN);

        /* UCO Preference */
        PreferenceEntity preferenceEntityUCO = new PreferenceEntity();
        preferenceEntityUCO.setPreferenceId(preferenceIdUCO);
        preferenceEntityUCO.setIndividu(individu);
        preferenceEntityUCO.setType(typeUCO);
        preferenceEntityUCO.setSiteCreation(siteCreationUCO);
        preferenceEntityUCO.setSignatureCreation(signatureCreationUCO);
        preferenceEntityUCO.setDateCreation(dateCreationUCO);
        preferenceEntityUCO.setLink(linkUCO);
        preferenceEntityUCO.setSignatureModification(signatureModificationUCO);
        preferenceEntityUCO.setSiteModification(siteModificationUCO);
        preferenceEntityUCO.setDateModification(dateModificationUCO);
        preferenceEntities.add(preferenceEntityUCO);

        /* UTS Preference */
        PreferenceEntity preferenceEntityUTS = new PreferenceEntity();
        preferenceEntityUTS.setPreferenceId(preferenceIdUTS);
        preferenceEntityUTS.setIndividu(individu);
        preferenceEntityUTS.setType(typeUTS);
        preferenceEntityUTS.setSiteCreation(siteCreationUTS);
        preferenceEntityUTS.setSignatureCreation(signatureCreationUTS);
        preferenceEntityUTS.setDateCreation(dateCreationUTS);
        preferenceEntityUTS.setLink(linkUTS);
        preferenceEntityUTS.setSignatureModification(signatureModificationUTS);
        preferenceEntityUTS.setSiteModification(siteModificationUTS);
        preferenceEntityUTS.setDateModification(dateModificationUTS);
        preferenceEntities.add(preferenceEntityUTS);

        /* ULO Preference */
        PreferenceEntity preferenceEntityULO = new PreferenceEntity();
        preferenceEntityULO.setPreferenceId(preferenceIdULO);
        preferenceEntityULO.setIndividu(individu);
        preferenceEntityULO.setType(typeULO);
        preferenceEntityULO.setSiteCreation(siteCreationULO);
        preferenceEntityULO.setSignatureCreation(signatureCreationULO);
        preferenceEntityULO.setDateCreation(dateCreationULO);
        preferenceEntityULO.setLink(linkULO);
        preferenceEntityULO.setSignatureModification(signatureModificationULO);
        preferenceEntityULO.setSiteModification(siteModificationULO);
        preferenceEntityULO.setDateModification(dateModificationULO);
        preferenceEntities.add(preferenceEntityULO);

        /* UFB Preference */
        PreferenceEntity preferenceEntityUFB = new PreferenceEntity();
        preferenceEntityUFB.setPreferenceId(preferenceIdUFB);
        preferenceEntityUFB.setIndividu(individu);
        preferenceEntityUFB.setType(typeUFB);
        preferenceEntityUFB.setSiteCreation(siteCreationUFB);
        preferenceEntityUFB.setSignatureCreation(signatureCreationUFB);
        preferenceEntityUFB.setDateCreation(dateCreationUFB);
        preferenceEntityUFB.setLink(linkUFB);
        preferenceEntityUFB.setSignatureModification(signatureModificationUFB);
        preferenceEntityUFB.setSiteModification(siteModificationUFB);
        preferenceEntityUFB.setDateModification(dateModificationUFB);
        preferenceEntities.add(preferenceEntityUFB);

        return preferenceEntities;
    }

    private List<Preference> buildMockedPreferences() throws ParseException {

        List<PreferenceData> preferenceDataUCO =
                preferenceService.getPreferenceData(Collections.singletonList(buildMockedPreferenceDataEntities().get(0)));
        List<PreferenceData> preferenceDataUTS =
                preferenceService.getPreferenceData(Collections.singletonList(buildMockedPreferenceDataEntities().get(1)));
        List<PreferenceData> preferenceDataULO =
                preferenceService.getPreferenceData(Collections.singletonList(buildMockedPreferenceDataEntities().get(2)));
        List<PreferenceData> preferenceDataUFB =
                preferenceService.getPreferenceData(Collections.singletonList(buildMockedPreferenceDataEntities().get(3)));

        List<Preference> preferences = new ArrayList<>();

        /* UCO Preference */
        PreferenceEntity preferenceEntityUCO = buildMockedPreferenceEntities().get(0);
        Preference preferenceUCO = new Preference();
        preferenceUCO.setDateCreation(preferenceEntityUCO.getDateCreation() == null ? null : preferenceEntityUCO.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        preferenceUCO.setDateModification(preferenceEntityUCO.getDateModification() == null ? null : preferenceEntityUCO.getDateModification().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        preferenceUCO.setId(preferenceEntityUCO.getPreferenceId());
        preferenceUCO.setLink(preferenceEntityUCO.getLink());
        preferenceUCO.setPreferenceData(preferenceDataUCO);
        preferenceUCO.setSignatureCreation(preferenceEntityUCO.getSignatureCreation());
        preferenceUCO.setSignatureModification(preferenceEntityUCO.getSignatureModification());
        preferenceUCO.setSiteCreation(preferenceEntityUCO.getSiteCreation());
        preferenceUCO.setSiteModification(preferenceEntityUCO.getSiteModification());
        preferenceUCO.setType(preferenceEntityUCO.getType());
        preferences.add(preferenceUCO);

        /* UTS Preference */
        PreferenceEntity preferenceEntityUTS = buildMockedPreferenceEntities().get(1);
        Preference preferenceUTS = new Preference();
        preferenceUTS.setDateCreation(preferenceEntityUTS.getDateCreation() == null ? null : preferenceEntityUTS.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        preferenceUTS.setDateModification(preferenceEntityUTS.getDateModification() == null ? null : preferenceEntityUTS.getDateModification().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        preferenceUTS.setId(preferenceEntityUTS.getPreferenceId());
        preferenceUTS.setLink(preferenceEntityUTS.getLink());
        preferenceUTS.setPreferenceData(preferenceDataUTS);
        preferenceUTS.setSignatureCreation(preferenceEntityUTS.getSignatureCreation());
        preferenceUTS.setSignatureModification(preferenceEntityUTS.getSignatureModification());
        preferenceUTS.setSiteCreation(preferenceEntityUTS.getSiteCreation());
        preferenceUTS.setSiteModification(preferenceEntityUTS.getSiteModification());
        preferenceUTS.setType(preferenceEntityUTS.getType());
        preferences.add(preferenceUTS);

        /* ULO Preference */
        PreferenceEntity preferenceEntityULO = buildMockedPreferenceEntities().get(2);
        Preference preferenceULO = new Preference();
        preferenceULO.setDateCreation(preferenceEntityULO.getDateCreation() == null ? null : preferenceEntityULO.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        preferenceULO.setDateModification(preferenceEntityULO.getDateModification() == null ? null : preferenceEntityULO.getDateModification().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        preferenceULO.setId(preferenceEntityULO.getPreferenceId());
        preferenceULO.setLink(preferenceEntityULO.getLink());
        preferenceULO.setPreferenceData(preferenceDataULO);
        preferenceULO.setSignatureCreation(preferenceEntityULO.getSignatureCreation());
        preferenceULO.setSignatureModification(preferenceEntityULO.getSignatureModification());
        preferenceULO.setSiteCreation(preferenceEntityULO.getSiteCreation());
        preferenceULO.setSiteModification(preferenceEntityULO.getSiteModification());
        preferenceULO.setType(preferenceEntityULO.getType());
        preferences.add(preferenceULO);

        /* UFB Preference */
        PreferenceEntity preferenceEntityUFB = buildMockedPreferenceEntities().get(3);
        Preference preferenceUFB = new Preference();
        preferenceUFB.setDateCreation(preferenceEntityUFB.getDateCreation() == null ? null : preferenceEntityUFB.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        preferenceUFB.setDateModification(preferenceEntityUFB.getDateModification() == null ? null : preferenceEntityUFB.getDateModification().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        preferenceUFB.setId(preferenceEntityUFB.getPreferenceId());
        preferenceUFB.setLink(preferenceEntityUFB.getLink());
        preferenceUFB.setPreferenceData(preferenceDataUFB);
        preferenceUFB.setSignatureCreation(preferenceEntityUFB.getSignatureCreation());
        preferenceUFB.setSignatureModification(preferenceEntityUFB.getSignatureModification());
        preferenceUFB.setSiteCreation(preferenceEntityUFB.getSiteCreation());
        preferenceUFB.setSiteModification(preferenceEntityUFB.getSiteModification());
        preferenceUFB.setType(preferenceEntityUFB.getType());
        preferences.add(preferenceUFB);

        return preferences;
    }

    private PreferenceResponse buildMockedPreferenceResponse() throws ParseException {
        PreferenceResponse preferenceResponse = new PreferenceResponse();
        preferenceResponse.setPreferences(buildMockedPreferences());
        return preferenceResponse;
    }

    private List<PreferenceDataEntity> buildMockedPreferenceDataEntities() throws ParseException {
        List<PreferenceDataEntity> preferenceDataEntities = new ArrayList<>();

        /* UCO Preference Data */
        PreferenceDataEntity preferenceDataEntityUCO = new PreferenceDataEntity();
        preferenceDataEntityUCO.setKey("preferredCommunicationChannel");
        preferenceDataEntityUCO.setValue("Pas un iphone");
        preferenceDataEntityUCO.setSiteCreation("BATCH_QVI");
        preferenceDataEntityUCO.setSignatureCreation("PRGDATAGOV-139");
        preferenceDataEntityUCO.setDateCreation(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-30"));
        preferenceDataEntities.add(preferenceDataEntityUCO);

        /* UTS Preference Data */
        PreferenceDataEntity preferenceDataEntityUTS = new PreferenceDataEntity();
        preferenceDataEntityUTS.setKey("customerDetails");
        preferenceDataEntityUTS.setValue("DOCS: P/FR/15FV14448/FR/30JUL51/M/01FEB22");
        preferenceDataEntityUTS.setSiteCreation("BATCH_QVI");
        preferenceDataEntityUTS.setSignatureCreation("PRGDATAGOV-139");
        preferenceDataEntityUTS.setDateCreation(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22"));
        preferenceDataEntities.add(preferenceDataEntityUTS);

        /* ULO Preference Data */
        PreferenceDataEntity preferenceDataEntityULO = new PreferenceDataEntity();
        preferenceDataEntityULO.setKey("specificAttention");
        preferenceDataEntityULO.setValue("Newspapers of the day + Le Point + Challenges. \n" +
                "Enjoy champagne.");
        preferenceDataEntityULO.setSiteCreation("BATCH_QVI");
        preferenceDataEntityULO.setSignatureCreation("PRGDATAGOV-139");
        preferenceDataEntityULO.setDateCreation(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22"));
        preferenceDataEntities.add(preferenceDataEntityULO);

        /* UFB Preference Data */
        PreferenceDataEntity preferenceDataEntityUFB = new PreferenceDataEntity();
        preferenceDataEntityUFB.setKey("mealPreferences");
        preferenceDataEntityUFB.setValue("Cold breakfast + black coffee or expresso/ like red wine PESSAC");
        preferenceDataEntityUFB.setSiteCreation("BATCH_QVI");
        preferenceDataEntityUFB.setSignatureCreation("PRGDATAGOV-139");
        preferenceDataEntityUFB.setDateCreation(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-22"));
        preferenceDataEntities.add(preferenceDataEntityUFB);

        return preferenceDataEntities;
    }
}
