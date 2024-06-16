package com.afklm.repind.msv.provide.preference.data.unittests;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.entity.preferences.MarketLanguageEntity;
import com.afklm.repind.common.repository.preferences.CommunicationPreferencesRepository;
import com.afklm.repind.common.repository.preferences.MarketLanguageRepository;
import com.afklm.repind.msv.provide.preference.data.service.CommunicationPreferenceService;
import com.afklm.repind.msv.provide.preference.data.transform.CommunicationPreferenceTransform;
import com.afklm.soa.stubs.r000380.v1.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CommunicationPreferenceServiceTest {

    @Mock
    private CommunicationPreferencesRepository communicationPreferencesRepository;
    @Mock
    private MarketLanguageRepository marketLanguageRepository;
    @Mock
    private CommunicationPreferenceTransform communicationPreferenceTransform;
    @InjectMocks
    private CommunicationPreferenceService communicationPreferenceService;

    private final String GIN = "110001017463";

    /* --------- Ultimate --------- */
    private final Long comPrefIdUltimate = Long.valueOf(36654314);
    private final String channelUltimate = null;
    private final String comGroupTypeUltimate = "S";
    private final String comTypeUltimate = "UL_PS";
    private final Timestamp dateCreationUltimate = Timestamp.valueOf("2017-01-18 13:34:51.331");
    private final Timestamp dateEntryUltimate = null;
    private final Timestamp dateModificationUltimate = Timestamp.valueOf("2017-05-30 15:51:58.236");
    private final Timestamp dateOptinUltimate = Timestamp.valueOf("2017-05-30 15:51:58.236");
    private final Timestamp dateOptinPartnersUltimate = null;
    private final String domainUltimate = "U";
    private final String media1Ultimate = null;
    private final String media2Ultimate = null;
    private final String media3Ultimate = null;
    private final String media4Ultimate = null;
    private final String media5Ultimate = null;
    private final String optinPartnersUltimate = null;
    private final String signatureCreationUltimate = "CBS";
    private final String signatureModificationUltimate = "REPIND";
    private final String siteCreationUltimate = "QVI";
    private final String siteModificationUltimate = "QVI";
    private final String subscribeUltimate = "N";

    /* --------- Sales --------- */
    private final Long comPrefIdSales = Long.valueOf(27598590);
    private final String channelSales = "FB_Migration";
    private final String comGroupTypeSales = "N";
    private final String comTypeSales = "AF";
    private final Timestamp dateCreationSales = Timestamp.valueOf("2014-09-18 16:05:58.198");
    private final Timestamp dateEntrySales = null;
    private final Timestamp dateModificationSales = Timestamp.valueOf("2021-09-14 08:17:28.789");
    private final Timestamp dateOptinSales = Timestamp.valueOf("1992-06-25 00:00:00.000");
    private final Timestamp dateOptinPartnersSales = null;
    private final String domainSales = "S";
    private final String media1Sales = null;
    private final String media2Sales = null;
    private final String media3Sales = null;
    private final String media4Sales = null;
    private final String media5Sales = null;
    private final String optinPartnersSales = null;
    private final String signatureCreationSales = "ALIM-WW-BATCH";
    private final String signatureModificationSales = "REPIND/IHM";
    private final String siteCreationSales = "VLB";
    private final String siteModificationSales = "QVI";
    private final String subscribeSales = "Y";

    @Test
    void getMarketLanguagesTest() throws ParseException{
        List<MarketLanguage> marketLanguages = communicationPreferenceService.getMarketLanguages(buildMockedMarketLanguageEntities());
        assertEquals(2,marketLanguages.size());

        MarketLanguage marketLanguageUltimate = marketLanguages.get(0);
        MarketLanguage marketLanguageSales = marketLanguages.get(1);

        /* Ultimate */
        assertEquals("FR",marketLanguageUltimate.getMarket());
        assertEquals("FR",marketLanguageUltimate.getLanguageCode());
        assertEquals("N",marketLanguageUltimate.getOptin());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2017-01-18T13:37:48.000Z").toInstant().atOffset(ZoneOffset.UTC),marketLanguageUltimate.getDateOptin());
        assertEquals("CBS",marketLanguageUltimate.getSignatureCreation());
        assertEquals("QVI",marketLanguageUltimate.getSiteCreation());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2017-01-18T13:34:51.331Z").toInstant().atOffset(ZoneOffset.UTC),marketLanguageUltimate.getDateCreation());
        assertEquals("CBS",marketLanguageUltimate.getSignatureModification());
        assertEquals("QVI",marketLanguageUltimate.getSiteModification());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2017-01-18T13:39:28.641Z").toInstant().atOffset(ZoneOffset.UTC),marketLanguageUltimate.getDateModification());

        /* Sales */
        assertEquals("FR",marketLanguageSales.getMarket());
        assertEquals("FR",marketLanguageSales.getLanguageCode());
        assertEquals("Y",marketLanguageSales.getOptin());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("1992-06-25T00:00:00.000Z").toInstant().atOffset(ZoneOffset.UTC),marketLanguageSales.getDateOptin());
        assertEquals("ALIM-WW-BATCH",marketLanguageSales.getSignatureCreation());
        assertEquals("VLB",marketLanguageSales.getSiteCreation());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2014-09-18T16:05:58.198Z").toInstant().atOffset(ZoneOffset.UTC),marketLanguageSales.getDateCreation());
        assertEquals("REPIND/IHM",marketLanguageSales.getSignatureModification());
        assertEquals("QVI",marketLanguageSales.getSiteModification());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2014-09-21T08:17:28.789Z").toInstant().atOffset(ZoneOffset.UTC),marketLanguageSales.getDateModification());

    }

    @Test
    void setCommunicationPreferencesResponseTest() throws ParseException {
        List<CommunicationPreferencesEntity> communicationPreferencesEntities = buildMockedCommunicationPreferencesEntities();
        List<MarketLanguageEntity> marketLanguageEntities = buildMockedMarketLanguageEntities();
        List<CommunicationPreferences> communicationPreferences = buildMockedCommunicationPreferences();
        CommunicationPreferencesResponse communicationPreferencesResponse = buildMockedCommunicationPreferenceResponse();

        CommunicationPreferencesEntity communicationPreferencesEntityUltimate = communicationPreferencesEntities.get(0);
        CommunicationPreferencesEntity communicationPreferencesEntitySales = communicationPreferencesEntities.get(1);

        MarketLanguageEntity marketLanguageEntityUltimate = marketLanguageEntities.get(0);
        MarketLanguageEntity marketLanguageEntitySales = marketLanguageEntities.get(1);

        CommunicationPreferences communicationPreferencesUltimate = communicationPreferences.get(0);
        CommunicationPreferences communicationPreferencesSales = communicationPreferences.get(1);

        List<MarketLanguage> marketLanguagesUltimate =
                communicationPreferenceService.getMarketLanguages(Collections.singletonList(marketLanguageEntityUltimate));
        List<MarketLanguage> marketLanguagesSales =
                communicationPreferenceService.getMarketLanguages(Collections.singletonList(marketLanguageEntitySales));

        when(marketLanguageRepository.getMarketLanguageEntitiesByComPrefId(comPrefIdUltimate))
                .thenReturn(Collections.singletonList(marketLanguageEntityUltimate));
        when(marketLanguageRepository.getMarketLanguageEntitiesByComPrefId(comPrefIdSales))
                .thenReturn(Collections.singletonList(marketLanguageEntitySales));

        when(communicationPreferenceTransform.setCommunicationPreference(communicationPreferencesEntityUltimate,marketLanguagesUltimate))
                .thenReturn(communicationPreferencesUltimate);
        when(communicationPreferenceTransform.setCommunicationPreference(communicationPreferencesEntitySales,marketLanguagesSales))
                .thenReturn(communicationPreferencesSales);

        when(communicationPreferenceTransform.setCommunicationPreferenceResponse(communicationPreferences))
                .thenReturn(communicationPreferencesResponse);

        CommunicationPreferencesResponse response = communicationPreferenceService.setCommunicationPreferencesResponse(communicationPreferencesEntities);
        List<CommunicationPreferences> responseCommunicationPreferences = response.getCommunicationPreferences();
        assertEquals(2,communicationPreferences.size());

        CommunicationPreferences communicationPreference1 = responseCommunicationPreferences.get(0);
        CommunicationPreferences communicationPreference2 = responseCommunicationPreferences.get(1);

        MarketLanguage marketLanguage1 = communicationPreference1.getMarketLanguages().get(0);
        MarketLanguage marketLanguage2 = communicationPreference2.getMarketLanguages().get(0);

        /* CommunicationPreferenceUltimate */
        assertEquals("U",communicationPreference1.getDomain());
        assertEquals("N",communicationPreference1.getSubscribe());
        assertEquals("S",communicationPreference1.getComGroupType());
        assertEquals("UL_PS",communicationPreference1.getComType());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2017-05-30T15:51:58.236Z").toInstant().atOffset(ZoneOffset.UTC),communicationPreference1.getDateOptin());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2017-01-18T13:34:51.331Z").toInstant().atOffset(ZoneOffset.UTC),communicationPreference1.getDateCreation());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2017-05-30T15:51:58.236Z").toInstant().atOffset(ZoneOffset.UTC),communicationPreference1.getDateModification());

        /* MarketLanguageUltimate */
        assertEquals("FR",marketLanguage1.getMarket());
        assertEquals("N",marketLanguage1.getOptin());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2017-01-18T13:37:48.000Z").toInstant().atOffset(ZoneOffset.UTC),marketLanguage1.getDateOptin());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2017-01-18T13:34:51.331Z").toInstant().atOffset(ZoneOffset.UTC),marketLanguage1.getDateCreation());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2017-01-18T13:39:28.641Z").toInstant().atOffset(ZoneOffset.UTC),marketLanguage1.getDateModification());

        /* CommunicationPreferenceSales */
        assertEquals("S",communicationPreference2.getDomain());
        assertEquals("Y",communicationPreference2.getSubscribe());
        assertEquals("N",communicationPreference2.getComGroupType());
        assertEquals("AF",communicationPreference2.getComType());
        assertEquals("FB_Migration",communicationPreference2.getChannel());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("1992-06-25T00:00:00.000Z").toInstant().atOffset(ZoneOffset.UTC),communicationPreference2.getDateOptin());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2014-09-18T16:05:58.198Z").toInstant().atOffset(ZoneOffset.UTC),communicationPreference2.getDateCreation());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2021-09-14T08:17:28.789Z").toInstant().atOffset(ZoneOffset.UTC),communicationPreference2.getDateModification());

        /* MarketLanguageSales */
        assertEquals("FR",marketLanguage2.getMarket());
        assertEquals("Y",marketLanguage2.getOptin());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("1992-06-25T00:00:00.000Z").toInstant().atOffset(ZoneOffset.UTC),marketLanguage2.getDateOptin());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2014-09-18T16:05:58.198Z").toInstant().atOffset(ZoneOffset.UTC),marketLanguage2.getDateCreation());
    }

    private List<CommunicationPreferencesEntity> buildMockedCommunicationPreferencesEntities(){
        List<CommunicationPreferencesEntity> communicationPreferencesEntityList = new ArrayList<>();
        Individu individu = new Individu();
        individu.setGin(GIN);

        /* Ultimate */
        CommunicationPreferencesEntity communicationPreferencesEntityUltimate = new CommunicationPreferencesEntity();
        communicationPreferencesEntityUltimate.setIndividu(individu);
        communicationPreferencesEntityUltimate.setComPrefId(Long.valueOf(36654314));
        communicationPreferencesEntityUltimate.setComGroupType("S");
        communicationPreferencesEntityUltimate.setComType("UL_PS");
        communicationPreferencesEntityUltimate.setSubscribe("N");
        communicationPreferencesEntityUltimate.setDomain("U");
        communicationPreferencesEntityUltimate.setSignatureCreation("CBS");
        communicationPreferencesEntityUltimate.setSiteCreation("QVI");
        communicationPreferencesEntityUltimate.setSignatureModification("REPIND");
        communicationPreferencesEntityUltimate.setSiteModification("QVI");
        communicationPreferencesEntityUltimate.setDateCreation(Timestamp.valueOf("2017-01-18 13:34:51.331"));
        communicationPreferencesEntityUltimate.setDateOptin(Timestamp.valueOf("2017-05-30 15:51:58.236"));
        communicationPreferencesEntityUltimate.setDateModification(Timestamp.valueOf("2017-05-30 15:51:58.236"));
        communicationPreferencesEntityList.add(communicationPreferencesEntityUltimate);

        /* Sales */
        CommunicationPreferencesEntity communicationPreferencesEntitySales = new CommunicationPreferencesEntity();
        communicationPreferencesEntitySales.setIndividu(individu);
        communicationPreferencesEntitySales.setComPrefId(Long.valueOf(27598590));
        communicationPreferencesEntitySales.setComGroupType("N");
        communicationPreferencesEntitySales.setComType("AF");
        communicationPreferencesEntitySales.setSubscribe("Y");
        communicationPreferencesEntitySales.setDomain("S");
        communicationPreferencesEntitySales.setSignatureCreation("ALIM-WW-BATCH");
        communicationPreferencesEntitySales.setSiteCreation("VLB");
        communicationPreferencesEntitySales.setSignatureModification("REPIND/IHM");
        communicationPreferencesEntitySales.setSiteModification("QVI");
        communicationPreferencesEntitySales.setChannel("FB_Migration");
        communicationPreferencesEntitySales.setDateCreation(Timestamp.valueOf("2014-09-18 16:05:58.198"));
        communicationPreferencesEntitySales.setDateOptin(Timestamp.valueOf("1992-06-25 00:00:00.000"));
        communicationPreferencesEntitySales.setDateModification(Timestamp.valueOf("2021-09-14 08:17:28.789"));
        communicationPreferencesEntityList.add(communicationPreferencesEntitySales);

        return communicationPreferencesEntityList;
    }

    private List<CommunicationPreferences> buildMockedCommunicationPreferences() throws ParseException{

        List<MarketLanguage> marketLanguagesUltimate =
                communicationPreferenceService.getMarketLanguages(Collections.singletonList(buildMockedMarketLanguageEntities().get(0)));
        List<MarketLanguage> marketLanguagesSales =
                communicationPreferenceService.getMarketLanguages(Collections.singletonList(buildMockedMarketLanguageEntities().get(1)));

        List<CommunicationPreferences> communicationPreferences = new ArrayList<>();

        /* Ultimate Communication Preferences */
        CommunicationPreferences communicationPreferencesUltimate = new CommunicationPreferences();
        communicationPreferencesUltimate.setChannel(channelUltimate);
        communicationPreferencesUltimate.setComGroupType(comGroupTypeUltimate);
        communicationPreferencesUltimate.setComType(comTypeUltimate);
        communicationPreferencesUltimate.setDateCreation(dateCreationUltimate.toInstant().atOffset(ZoneOffset.UTC));
        communicationPreferencesUltimate.setDateEntry(dateEntryUltimate == null ? null : dateEntryUltimate.toInstant().atOffset(ZoneOffset.UTC));
        communicationPreferencesUltimate.setDateModification(dateModificationUltimate == null ? null : dateModificationUltimate.toInstant().atOffset(ZoneOffset.UTC));
        communicationPreferencesUltimate.setDateOptin(dateOptinUltimate == null ? null : dateOptinUltimate.toInstant().atOffset(ZoneOffset.UTC));
        communicationPreferencesUltimate.setDateOptinPartners(dateOptinPartnersUltimate == null ? null : dateOptinPartnersUltimate.toInstant().atOffset(ZoneOffset.UTC));
        communicationPreferencesUltimate.setDomain(domainUltimate);
        communicationPreferencesUltimate.setMarketLanguages(marketLanguagesUltimate);
        communicationPreferencesUltimate.setMedia1(media1Ultimate);
        communicationPreferencesUltimate.setMedia2(media2Ultimate);
        communicationPreferencesUltimate.setMedia3(media3Ultimate);
        communicationPreferencesUltimate.setMedia4(media4Ultimate);
        communicationPreferencesUltimate.setMedia5(media5Ultimate);
        communicationPreferencesUltimate.setOptinPartners(optinPartnersUltimate);
        communicationPreferencesUltimate.setSignatureCreation(signatureCreationUltimate);
        communicationPreferencesUltimate.setSignatureModification(signatureModificationUltimate);
        communicationPreferencesUltimate.setSiteCreation(siteCreationUltimate);
        communicationPreferencesUltimate.setSiteModification(siteModificationUltimate);
        communicationPreferencesUltimate.setSubscribe(subscribeUltimate);
        communicationPreferences.add(communicationPreferencesUltimate);

        /* Sales Communication Preferences */
        CommunicationPreferences communicationPreferencesSales = new CommunicationPreferences();
        communicationPreferencesSales.setChannel(channelSales);
        communicationPreferencesSales.setComGroupType(comGroupTypeSales);
        communicationPreferencesSales.setComType(comTypeSales);
        communicationPreferencesSales.setDateCreation(dateCreationSales.toInstant().atOffset(ZoneOffset.UTC));
        communicationPreferencesSales.setDateEntry(dateEntrySales == null ? null : dateEntrySales.toInstant().atOffset(ZoneOffset.UTC));
        communicationPreferencesSales.setDateModification(dateModificationSales == null ? null : dateModificationSales.toInstant().atOffset(ZoneOffset.UTC));
        communicationPreferencesSales.setDateOptin(dateOptinSales == null ? null : dateOptinSales.toInstant().atOffset(ZoneOffset.UTC));
        communicationPreferencesSales.setDateOptinPartners(dateOptinPartnersSales == null ? null : dateOptinPartnersSales.toInstant().atOffset(ZoneOffset.UTC));
        communicationPreferencesSales.setDomain(domainSales);
        communicationPreferencesSales.setMarketLanguages(marketLanguagesSales);
        communicationPreferencesSales.setMedia1(media1Sales);
        communicationPreferencesSales.setMedia2(media2Sales);
        communicationPreferencesSales.setMedia3(media3Sales);
        communicationPreferencesSales.setMedia4(media4Sales);
        communicationPreferencesSales.setMedia5(media5Sales);
        communicationPreferencesSales.setOptinPartners(optinPartnersSales);
        communicationPreferencesSales.setSignatureCreation(signatureCreationSales);
        communicationPreferencesSales.setSignatureModification(signatureModificationSales);
        communicationPreferencesSales.setSiteCreation(siteCreationSales);
        communicationPreferencesSales.setSiteModification(siteModificationSales);
        communicationPreferencesSales.setSubscribe(subscribeSales);
        communicationPreferences.add(communicationPreferencesSales);

        return communicationPreferences;
    }

    private CommunicationPreferencesResponse buildMockedCommunicationPreferenceResponse() throws ParseException {
        CommunicationPreferencesResponse communicationPreferencesResponse = new CommunicationPreferencesResponse();
        communicationPreferencesResponse.setCommunicationPreferences(buildMockedCommunicationPreferences());
        return communicationPreferencesResponse;
    }

    private List<MarketLanguageEntity> buildMockedMarketLanguageEntities(){
        List<MarketLanguageEntity> marketLanguageEntityList = new ArrayList<>();

        /* Ultimate */
        MarketLanguageEntity marketLanguageEntityUltimate = new MarketLanguageEntity();
        marketLanguageEntityUltimate.setComPrefId(Long.valueOf(36654314));
        marketLanguageEntityUltimate.setMarketLanguageId(22348883);
        marketLanguageEntityUltimate.setSignatureCreation("CBS");
        marketLanguageEntityUltimate.setSiteCreation("QVI");
        marketLanguageEntityUltimate.setLanguageCode("FR");
        marketLanguageEntityUltimate.setMarket("FR");
        marketLanguageEntityUltimate.setSignatureModification("CBS");
        marketLanguageEntityUltimate.setSiteModification("QVI");
        marketLanguageEntityUltimate.setOptin("N");
        marketLanguageEntityUltimate.setDateCreation(Timestamp.valueOf("2017-01-18 13:34:51.331"));
        marketLanguageEntityUltimate.setDateOptin(Timestamp.valueOf("2017-01-18 13:37:48.000"));
        marketLanguageEntityUltimate.setDateModification(Timestamp.valueOf("2017-01-18 13:39:28.641"));
        marketLanguageEntityList.add(marketLanguageEntityUltimate);

        /* Sales */
        MarketLanguageEntity marketLanguageEntitySales = new MarketLanguageEntity();
        marketLanguageEntitySales.setComPrefId(Long.valueOf(27598590));
        marketLanguageEntitySales.setMarketLanguageId(13553966);
        marketLanguageEntitySales.setSignatureCreation("ALIM-WW-BATCH");
        marketLanguageEntitySales.setSiteCreation("VLB");
        marketLanguageEntitySales.setLanguageCode("FR");
        marketLanguageEntitySales.setMarket("FR");
        marketLanguageEntitySales.setSignatureModification("REPIND/IHM");
        marketLanguageEntitySales.setSiteModification("QVI");
        marketLanguageEntitySales.setOptin("Y");
        marketLanguageEntitySales.setDateCreation(Timestamp.valueOf("2014-09-18 16:05:58.198"));
        marketLanguageEntitySales.setDateOptin(Timestamp.valueOf("1992-06-25 00:00:00.000"));
        marketLanguageEntitySales.setDateModification(Timestamp.valueOf("2014-09-21 08:17:28.789"));
        marketLanguageEntityList.add(marketLanguageEntitySales);

        return marketLanguageEntityList;
    }
}
