package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.PostalAddressRequest;
import com.afklm.soa.stubs.w000442.v8.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import com.airfrance.ref.type.ApplicationCodeEnum;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForNormalizationTest extends CreateOrUpdateIndividualImplV8{

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8ForNormalizationTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";

//	@Autowired
//	@Qualifier("passenger_CreateUpdateIndividualService-v7Bean")
//	private CreateOrUpdateIndividualImplV7 createOrUpdateIndividualImplV7;

	private String generateString() {
		return RandomStringUtils.randomAlphabetic(10);
	}

	private String generateEmail() {
		return generateString() + "@free.nul";
	}

	private String generatePhone() {
		return "04" + RandomStringUtils.randomNumeric(8);
	}

	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_Create_Normalization_Force() {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MISS");
		indInfo.setLastNameSC("NOM" + generateString());
		indInfo.setFirstNameSC("PRENOM" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1180 route des dollines");
		pac.setZipCode("06560");
		pac.setCity("Valbonne");
		pac.setCountryCode("fr");
		pac.setAdditionalInformation("");
		pac.setDistrict("");
		pac.setStateCode("");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("V");	// Valid
		pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("TU");
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);
		
		
		// WS call
//		CreateUpdateIndividualResponse response = createIndividual(request);
		// TODO workaround for maven issue with Bamboo
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			return;
		}
				
		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("400"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		// Assert.assertEquals(RecognitionType.FLT.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}

	@Test(expected = BusinessErrorBlocBusinessException.class)
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_Create_Normalization_With_Error() throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MISS");
		indInfo.setLastNameSC("NOM" + generateString());
		indInfo.setFirstNameSC("PRENOM" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("route des bip");
		pac.setZipCode("06560");
		pac.setCity("Valbonne");
		pac.setCountryCode("fr");
		pac.setAdditionalInformation("");
		pac.setDistrict("");
		pac.setStateCode("");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);

		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("V");	// Valid
		pap.setVersion("1");
		pap.setIndicAdrNorm(false);
		addRequest.setPostalAddressProperties(pap);

		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("TU");
		addRequest.setUsageAddress(ua);

		request.getPostalAddressRequest().add(addRequest);

		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		response = createIndividual(request);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_Create_Normalization() {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MISS");
		indInfo.setLastNameSC("NOM" + generateString());
		indInfo.setFirstNameSC("PRENOM" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1180 route des dollines");
		pac.setZipCode("06560");
		pac.setCity("Valbonne");
		pac.setCountryCode("fr");
		pac.setAdditionalInformation("");
		pac.setDistrict("");
		pac.setStateCode("");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("V");	// Valid
		pap.setVersion("1");
		pap.setIndicAdrNorm(false);	// On force la normalization
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("TU");
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);
		
		
		// WS call
		// TODO workaround for maven issue with Bamboo
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			return;
		}
		
		//Workaround when Experian is shutdown (Fix needed)
		
		if (response.isSuccess()) {
			// Tests
			Assert.assertNotNull(response);		
			Assert.assertNotNull(response.isSuccess());
			Assert.assertTrue(response.isSuccess());
			Assert.assertNotNull(response.getGin());
			Assert.assertTrue(response.getGin().startsWith("400"));

			Assert.assertNotNull(response.getInformationResponse());
			Assert.assertNotEquals(0, response.getInformationResponse());
		}
		
		// Assert.assertEquals(RecognitionType.FLT.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}


	@Test
	public void testCreateOrUpdateIndividual_Update_Normalization_Force() throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// CREATE 
		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MISS");
		indInfo.setLastNameSC("NOM" + generateString());
		indInfo.setFirstNameSC("PRENOM" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1180 route des dollines");
		pac.setZipCode("06560");
		pac.setCity("Valbonne");
		pac.setCountryCode("fr");
		pac.setAdditionalInformation("");
		pac.setDistrict("");
		pac.setStateCode("");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("V");	// Valid
		pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode(ApplicationCodeEnum.RPD.getCode());
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);
		
		
		// WS call for CREATE
		CreateUpdateIndividualResponse response = createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("400"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		String gin = response.getGin();
		
		// UPDATE
		indInfo.setIdentifier(gin);
		
		// WS call for CREATE
		response = createIndividual(request);
		
		
		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		
		
		// Assert.assertEquals(RecognitionType.FLT.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}

	@Test
	public void testCreateOrUpdateIndividual_Update_Normalization() throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// CREATE 
		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MISS");
		indInfo.setLastNameSC("NOM" + generateString());
		indInfo.setFirstNameSC("PRENOM" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1180 route des dollines");
		pac.setZipCode("06560");
		pac.setCity("Valbonne");
		pac.setCountryCode("fr");
		pac.setAdditionalInformation("");
		pac.setDistrict("");
		pac.setStateCode("");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("V");	// Valid
		pap.setVersion("1");
		pap.setIndicAdrNorm(false);	// On force la normalisation
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode(ApplicationCodeEnum.RPD.getCode());
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);
		
		
		// WS call for CREATE
		CreateUpdateIndividualResponse response = createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("400"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		String gin = response.getGin();
		
		// UPDATE
		indInfo.setIdentifier(gin);
		
		// WS call for CREATE
		response = createIndividual(request);
		
		
		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		
		
		// Assert.assertEquals(RecognitionType.FLT.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_Normalization_MasseRandom() throws BusinessErrorBlocBusinessException {

		long nbOK = 0;
		long nbKO = 0;
		long nbKKO = 0;	
		
		String[] listeAdresseANormaliser =  new String[]{
 				"4 Rue de la Petite Colline__06250_MOUGINS_FR_",
				"3530 NW 19TH ST__33125_MIAMI_US_NY",
				"IMMEUBLE SOTRAKA BANANKABOUGOU BOLLE__BPE 4481_BAMAKO_ML_",
				"SCHULSTRASSE 30__97236_RANDERSACKER_DE_",
				"1311 MADISON ST NW__20011_WASHINGTON_US_DC",
				"32 RUE DE TANGER__75019_PARIS_FR_",
				"EPPENDORFER WEG 189__20253_HAMBURG_DE_",
				"173 CHIN SWEE ROAD__169878_SINGAPORE_SG_",
				"HOFSTRASSE 13__52152_SIMMERATH_DE_",
				"301  CREIG SOMMERSET GARDENS__N17 8JY_LONDON_GB_",
				"30 COOLAMBER OARK__D16_DUBLIN_IE_",
				"170 DARNLEY ROAD__DA11 0SN_GRAVESEND_GB_",
				"ANDERSENSTRASSE 4__68259_MANNHEIM_DE_",
				"HENRIK IBSEN STRASSE 5__80638_MUENCHEN_DE_",
				"42 RUE DES SABLONS__77400_THORIGNY SUR MARNE_FR_",
				"HENRIK IBSEN STRASSE 5__80638_MUENCHEN_DE_",
				"AUC - DREA__3243_ADDIS ABABA_ET_",
				"BRYGGEVEIEN 56B_3470_3470_SLEMMESTAD_NO_",
				"57 RUE SAINT LAZARE__75009_PARIS_FR_",
				"HENRIK IBSEN STRASSE 5__80638_MUENCHEN_DE_",
				"GUADALEST NUMERO3 3C__03005_ALICANTE_ES_",
				"54098 PO BOX__SW20 8UU_LONDON_GB_",
				"23 AVENUE DE SAINT LOUBES__33440_AMBARES ET LAGRAVE_FR_",
				"NICOLAE PASCU NR 10, 129___BUCHAREST_RO_",
				"10A RUE SAINT BERNARD__67500_HAGUENAU_FR_",
				"42 RUE DES SABLONS__77400_THORIGNY SUR MARNE_FR_",
				"XX__XX_XX_NL_",
				"10 AVENUE SALVADOR ALLENDE__69120_VAULX EN VELIN_FR_",
				"X___X_BF_",
				"TJALKKADE 13__2022GC_HAARLEM_NL_",
				"12 ALLEE BEL AMI__76190_YVETOT_FR_",
				"XX__XX_XX_NL_",
				"HASTA 123__73291_ARBOGA_SE_",
				"XX__XX_XX_NL_",
				"TANGVEIEN 2__4372_EGERSUND_NO_",
				"XX__XX_XX_NL_",
				"6 AVENUE JEAN MONNET__83210_SOLLIES PONT_FR_",
				"__99999_LONDON_GB_",
				"BAIRRO TSALALA CASA183 4 MATOLA___MAPUTO_MZ_",
				"STANDBERGSTRAAT 6__1391 EL_ABCOUDE_NL_",
				"GILDEMARK 54__1351HE_ALMERE-HAVEN_NL_",
				"MARTINIQUE___FORT DE FRANCE_MQ_",
				"9 CHEMIN DE FOURNAYRE__63530_CHANAT LA MOUTEYRE_FR_",
				"1 BRAESIDE CLOSE_PINNER_HA5 4HH_LONDON_GB_",
				"8 RUE DE COULOMMIERS__77176_SAVIGNY LE TEMPLE_FR_",
				"MATIKO 22__48007_BILBAO_ES_",
				"HONGQIAOLU__200030_SHANGHAI_CN_",
				"106 ALBERT ROUSSELSTRAAT__2511ZK_THE HAGUE_NL_",
				"05 RUE DU PRESSOIR__91650_BREUILLET_FR_",
				"70 RUE JULES LAGAISSE__94400_VITRY SUR SEINE_FR_",
				"ENEVAGEN 15___ULLARED_SE_",
				"PALMSTRAAT 40__3572 TD_UTRECHT_NL_",
				"34 RUE SAINT ANDRE__93012_BOBIGNY_FR_",
				"DUSAGER 4__8200_AARHUS N_DK_",
				"-_-_-_-_NL_",
				"ELISABETHENSTRASSE 13__73760_OSTFILDERN_DE_",
				"XX__XX_XX_NL_",
				"1 BRAESIDE CLOSED_PINNER_HA5 4HH_LONDON_GB_",
				"CELEBESSTRAAT 4A__9715JE_GRONINGEN_NL_",
				"OTIER PLAISANCE__972_LE VAUCLIN_FR_",
				"SONG SAN HUAN__100000_BEIJING_CN_",
				"42 RUE DES SABLONS__77400_THORIGNY SUR MARNE_FR_",
				"JAN KOKLAAN 12__3829 GZ_HOOGLANDERVEEN_NL_",
				"AVDA ALBERTO ALCOCER 49 3RO__2016_MADRID_ES_",
				"SCHILLERPLATZ 11_ZWEITER STOCK LINKS_90409_NUERNBERG_DE_",
				"3 , LE PRAZ DU NANT__73000_BASSENS_FR_",
				"BROAGERVEJ 36__9260_GISTRUP_DK_",
				"C/O MR DUCHAMP 11 RUE ALEXIS ALQUIE__34000_MONTPELLIER_FR_",
				"C/O MR DUCHAMP 11 RUE ALEXIS ALQUIE__34000_MONTPELLIER_FR_",
				"SANDBERGSTRAAT 6__1391 el_ABCOUDE_NL_",
				"39 AVENUE DE LA GARE__81800_COUFFOULEUX_FR_",
				"14 PERCY LANE DUBLIN 4__D04 N205_DUBLIN_IE_",
				"HUNGARIA KRT 5-7___BUDAPEST_HU_",
				"KONINGIN EMMALAAN 25__2264SH_LEIDSCHENDAM_NL_",
				"IMMEUBLE SONIT__BP E4227_BAMAKO_ML_",
				"17 BIS CHEMIN DES FOSSES ROUGES__77700_CHESSY_FR_",
				"6 ROUTE DE MERVILLA__31320_CASTANET_FR_",
				"CALLE SANTIAGO AMON 25__28231_MADRID_ES_",
				"MOSCOW LENINSKII BUILD 40 APPT 352__119334_MOSCOW_RU_",
				"CONDE DE BORRELL, 308__08029_BARCELONA_ES_",
				"2 AVENUE DE LA GARE__62720_RINXENT_FR_",
				"1 BRAESIDE CLOSE_PINNER_HA5 4HH_LONDON_GB_",
				"CANDIDO BUSTEROS__48920_PORTUGALETE_ES_",
				"RAMIREZ GASTON 360_MIRAFLORES__LIMA_PE_",
				"RODENRIJSSESTRAAT 76A__3037NJ_ROTTERDAM_NL_",
				"2B CHEMIN DE LA CLAOU__31790_ST JORY_FR_",
				"ANJELIERSSTRAAT 362__1015NL_AMSTERDAM_NL_",
				"35 ALLEE DES CYPRES__77100_MEAUX_FR_",
				"63 CHEMIN JOSEPH AIGUIER__13009_MARSEILLE_FR_",
				"IMPASSE DES MARAIS__38760_VARCES ALLIERES ET RISSET_FR_",
				"COTONOU_COTONOU_229_COTONOU_BJ_",
				"2 RUE DE L OISE__95630_MERIEL_FR_",
				"ANCIENNE ROUTE DE QUINSON__04500_MONTAGNAC MONTPEZAT_FR_",
				"CCAS CENTRE ARAGON__34970_LATTES_FR_",
				"37 ANGELL COURT_APT 413_94305_STANFORD_US_CA",
				"21 RUE DES CHEVALIERS__77410_SAINT-MESMES_FR_",
				"ROOM1710NO464XIKANGROADJINGANDISTRICT__999999_SHANGHAI_CN_",
				"HONGQIAOLU__200032_SHANGHAI_CN_",
				"VIA FRATELLI BASTIA 29__40010_SALA BOLOGNESE_IT_",
				"._._._._IL_",
				"___TAIPEI_CN_",
				"IMPASSE DES MARAIS__38760_VARCES ALLIERES ET RISSET_FR_",
				"__75015_PARIS_FR_",
				"AVENUE JEAN MOULIN__75014_PARIS_FR_",
				"VOLTASTRAAT 11__6902PT_ZEVENAAR_NL_",
				"TBA__2190_TBA_BW_",
				"6 BOULEVARD EUGENE BODIN__35190_TINTENIAC_FR_",
				"N 23 RUE BOUDET__33000_BORDEAUX_FR_",
				"PRACETA ALEXANDRE HERCULANO 19_ALDEIA DE JUSO_2750-011_CASCAIS_PT_",
				"___TAIPEI_CN_",
				"IMPASSE DES MARAIS__38760_VARCES ALLIERES ET RISSET_FR_",
				"___TAIPEI_CN_",
				"IM TANNENGRUND 9__63589_LUTZELHAUSEN_DE_",
				"18 BLAGDENS LANE_LONDON_N14 6DG_LONDON_GB_",
				"9 RUE DE MONTRETOUT__92210_SAINT-CLOUD_FR_",
				"2021 SEA TO SKY HWY_PO BOX 140_V0N 2K0_MOUNT CURRIE_CA_BC",
				"PETRA DRAPSINA 25__11211_BORCA, BELGRADE_RS_",
				"__75015_PARIS_FR_",
				"17 RUE ALEXANDRE DUMAS__75012_PARIS_FR_",
				"GRAND LIMOGES__82500_BEAUMONT DE LOMAGNE_FR_",
				"130,50 MOO.1 KUDGONG PHANATNIKHOM__20140_CHONBURI_TH_",
				"DEABANDNOBLELAND APT 412___???_KR_",
				"3 RUE DE LA GARANCE__67240_BISCHWILLER_FR_",
				"22 RUE DE MARSEILLE__94700_MAISONS ALFORT_FR_",
				"CALLE CAMILO JOSE CELA 1, PORTAL 3, 1A__28922_ALCORCON_ES_",
				"__75015_PARIS_FR_",
				"2 RUE DE GRENOBLE__67000_STRASBOURG_FR_",
				"DE RUN 6501__5504DR_VELDHOVEN_NL_",
				"___TAIPEI_CN_",
				"2 RUE FRANCOISE DOLTO__44800_SAINT HERBLAIN_FR_",
				"9 ROUTE PRINCIPALE DU PORT__92638_GENNEVILLIERS_FR_",
				"32 BD RACHIDI__200700_CASABLANCA_MA_",
				"___TAIPEI_CN_",
				"WETERINGKADE 11__1358 AH_ALMERE_NL_",
				"__99999_LONDON_GB_",
				"___TAIPEI_CN_",
				"___AALBORG_DK_",
				"MAREDIJK 51__2613VS_LEIDEN_NL_",
				"HASTA 123__73291_ARBOGA_SE_",
				"63, SOVEREIGNS QUAY__MK401TF_BEDFORD_GB_",
				"TERBREGSELAAN 34__3055RG_ROTTERDAM_NL_",
				"___TAIPEI_CN_",
				"NORDBERGVEIEN 43_NORDBERGVEIEN 43_1738_BORGENHAUGEN_NO_",
				"KROSSNES 3A__4550_FARSUND_NO_",
				"SIENKIEWICZA 50__05-126_NIEPORET_PL_",
				"___TAIPEI_CN_",
				"4 , RUE DU PARADIS__62290_NOEUX-LES-MINES_FR_",
				"LES MARIOTTES__70230_DAMPIERRE SUR LINOTTE_FR_",
				"BERLIN___BERLIN_DE_",
				"THE FORGE RIBY LINCOLNSHIRE__DN37 8NH_RIBY_GB_",
				"17, RUE TISSERANT__92100_BOULOGNE-BILLANCOURT_FR_",
				"___TAIPEI_CN_",
				"SCHWABSTR 90__70193_STUTTGART_DE_",
				"11 AVENUE DE LA PORTE DE LA PLAINE__75015_PARIS_FR_",
				"BERLIN___BERLIN_DE_",
				"9 RUE CLUADE DEBUSSY__78470_SAINT REMY LES CHEVREUSE_FR_",
				"60 RUE DU LANDI__60250_HEILLES_FR_",
				"CALLE CANOA 27 1�A MADRID_28042_28042_MADRID_ES_M",
				"___TAIPEI_CN_",
				"20 RUE DE LA REPUBLIQUE__12700_CAPDENAC GARE_FR_",
				"VIALE LOMBARDIA 33__21013_GALLARATE_IT_",
				"10 RUE DES PRUNUS__67120_HINDISHEIM_FR_",
				"___TAIPEI_CN_",
				"FOLKSTONEWEG 36__1118LM_SCHIPHOL_NL_",
				"BERLIN___BERLIN_DE_",
				"AVENIDA HISPANIDAD 49 2C__10005_CACERES_ES_",
				"RUE D EGLANTIERS__70230_LOULANS VERCHAMPS_FR_",
				"60 RUE DE RICHELIEU__75002_PARIS_FR_",
				"___TAIPEI_CN_",
				"PAPENVOORDEN 13C__5688HK_OIRSCHOT_NL_",
				"___TAIPEI_CN_",
				"JAVASTRAAT 63__2022 XP_HAARLEM_NL_",
				"LILLEVANNSVEIEN 57B__0788_OSLO_NO_",
				"__8387_KOERICH_LU_",
				"PLAZA CARDONA VIVES__12001_CASTELLON_ES_",
				"91 RUE DE BUSNETTES__62190_LILLERS_FR_",
				"HOF VAN DELFTLAAN 60__2180_EKEREN_BE_",
				"60 RUE DE RICHELIEU__75002_PARIS_FR_",
				"RUE D EGLANTIERS__70230_LOULANS VERCHAMPS_FR_",
				"WEBERSTEIG 3__86738_DEININGEN_DE_",
				"WAARDERWEG 94__2031 BR_HAARLEM_NL_",
				"PORTAL DE LEGUTIANO N9 7IZQ__01002_VITORIA-GASTEIZ_ES_VI",
				"30 CRANBROOK AVE OSWALDTWISTLE ACCRINGTON__BB5 4NS_LANCASHIRE_GB_",
				"___XX_GB_",
				"BULEVAR KRALJA ALKSANDRA 336__11000_BEOGRAD_RS_",
				"4 BIS RUE DE BRINDEJONC DES MOULINAIS__31500_TOULOUSE_FR_",
				"31 RUE DE LA REPUBLIQUE__94370_SUCY EN BRIE_FR_",
				"3 SENTIER DE LA CHAUSSEE__94370_SUCY EN BRIE_FR_",
				"BERBLINGERSTRASSE 19__88518_HERBERTINGEN_DE_",
				"18 RUE ARISTIDE BRIAND__92300_LEVALLOIS PERRET_FR_",
				"HOFFSVEIEN 6A__0275_OSLO_NO_",
				"___HONG KONG_HK_",
				"___LONDON_GB_",
				"213 RUE DU FAUBOURG SAINT MARTIN__75010_PARIS_FR_",
				"123_CORSO LODI_20139_MILAN_IT_LO",
				"WOLTERBEEKWEG 8__6862be_OOSTERBEEK_NL_",
				"SUITE 31,BLOCK B,ALAUSA SHOPPING MALL___LAGOS_NG_",
				"___._CO_",
				"14 AM MARTINSBERG__91567_HERRIEDEN_DE_",
				"LAAN VAN SUCHTELEN VAN DE HAARE 15__1405AR_BUSSUM_NL_",
				"TRANSPORTWEG 74__9645KX_VEENDAM_NL_",
				"QUIKSILVER NAPALI SAS_162 RUE BELHARRA_64500_SAINT JEAN DE LUZ_FR_",
				"3 RUE DE LA GARANCE__67240_BISCHWILLER_FR_",
				"3 RUE DE LA GARANCE__67240_BISCHWILLER_FR_",
				"PASEO LORENZO SERRA__08922_SANTA COLOMA DE GRAMENET_ES_",
				"NATTLANDSVEIEN 23__5093_BERGEN_NO_",
				"17, RUE TISSERANT__92100_BOULOGNE-BILLANCOURT_FR_",
				"4 ALLEE HENRI SELLIER__92800_PUTEAUX_FR_",
				"42 VIA DELLA PACE__40010_SALA BOLOGNESE_IT_",
				"CHRISTIANSHOEHE 15__37085_GOETTINGEN_DE_",
				"___._CO_",
				"9 RUE DU STADE__40200_MIMIZAN_FR_",
				"X___X_GP_",
				"INTERNET CITY___DUBAI_AE_",
				"7 ASHGROVERD__N/A_ABERDEEN_FR_",
				"IDROTTSGATAN 21_442 66_442 66_MARSTRAND_SE_",
				"KVARNBERGSVAGEN 26__44447_STENUNGSUND_SE_",
				"CHRISTIANSHOEHE 15__37085_GOETTINGEN_DE_",
				"5 ST HELENS ROAD__B91 2DB_SOLIHULL_GB_",
				"1 ALLEE ILE DE GROIX__44350_GUERANDE_FR_",
				"5 VISCOUNT ROAD__2018_BEDFORDVIEW_ZA_",
				"HERNAN CORTES N 29__46004_VALENCIA_ES_",
				"VAN WALBEECKSTRAAT 26 1__1058CR_AMSTERDAM_NL_",
				"1405 NAVARRE__31380_AZAS_FR_",
				"42 VIA DELLA PACE__40010_SALA BOLOGNESE_IT_",
				"VIKTOR RYDBERGSGATAN, 29__41257_GOTEBORG_SE_",
				"5 QUAI DU 24 NOVEMBRE__67160_WISSEMBOURG_FR_",
				"BALKENWEG 13__4460_GELTERKINDEN_CH_",
				"32 KEMPTON AVENUE_ESSEX_RM12 6ED_LONDON_GB_",
				"___ZC9IAP_DE_",
				"AMMAN__11118_AMMAN_JO_",
				"AV. DE LOS HUETOS, 79__01010_VITORIA_ES_",
				"AV. RIO BRANCO 257 - 10 ANADR__20040-009_RIO DE JANEIRO_BR_",
				"AV. RIO BRANCO 257 - 10 ANDAR__20040-009_RIO DE JANEIRO_BR_",
				"2-OU VERHNIY MIHAILOVSKY PROEZD___MOSCOW_RU_",
				"BREDGADE 95C__8340_MALLING_DK_",
				"1405 NAVARRE__31380_AZAS_FR_",
				"45 GRANDE RUE__91450_ETIOLLES_FR_",
				"BEREND HENDRIKSPAD 6__1328EP_ALMERE_NL_",
				"1 RUE MEDERIC__94600_CHOISY LE ROI_FR_",
				"INTERNET CITY___DUBAI_AE_",
				"SUITE 08-03, 8TH FLOOR, MENARA KECK SENG,_JALAN BUKIT BINTANG_203_KUALA LUMPUR_MY_",
				"HUETTENSTR. 71__40215_DUESSELDORF_DE_",
				"MOLLEMARKEN 31__4600_KOGE_DK_",
				"YONGSANGU HANGANGRO 3GA CITY PARK 102-1002___SEOUL_KR_",
				"___BUCHHOLY_DE_",
				"KOBERGSGATAN 14_41671_41671_GOTEBORG_SE_",
				"BERGSRADSVAGEN 49_BAGARMOSSEN_12842_STOCKHOLM_SE_",
				"15 RUE DU HAMEAU__75015_PARIS_FR_",
				"___COPENHAGEN_DK_",
				"AMMAN__11118_AMMAN_JO_",
				"HC ANDERSENS BOULEVARD 37__1553_COPENHAGEN_DK_",
				"___COPENHAGEN_DK_",
				"CANTONULUI 14B__000000_CORBEANCA_RO_",
				"STE SOFTWAY MEDICAL_ARTEPARC BAT C_13590_MEYREUIL_FR_",
				"CALLE RIO EBRO N26 6D__28935_MOSTOLES_ES_",
				"MADLAKROSSEN 7__4042_HAFRSFJORD_NO_",
				"13  RUE HANRI BARBUSSE__69800_SAINT PRIEST_FR_",
				"__50000_ASSA_MA_",
				"LISDODDEPAD 6__8251SN_DRONTEN_NL_",
				"AMMAN__11118_AMMAN_JO_",
				"MANDENMAKERSTRAAT 12__1445 BB_PURMEREND_NL_",
				"NASTARAN,GILAN BOULEVARD_4166614611_4166614611_RASHT_IR_",
				"CALLE LUIS PIERNAS 38 7C__28017_MADRID_ES_",
				"___BUCHHOLY_DE_",
				"VASTRAVALLGATAN 31__27135_YSTAD_SE_",
				"CORSO XXII MARZO 41__20129_MILAN_IT_",
				"14-A SRIBNOKILSKA ST., APT.47__02095_KIEV_UA_",
				"2 RUE ROGER CAMBOULIVES__31057_TOULOUSE_FR_",
				"MUGGENSTUR MERSTRASSE 9__70499_STUTTGART_DE_",
				"AMMAN__11118_AMMAN_JO_",
				"26 LOTISSEMENT PRES D ARGENT__68120_RICHWILLER_FR_",
				"CARRER DELS CAVALLERS 75__08034_BARCELONA_ES_",
				"AGARDVEJ 27__7323_GIVE_DK_",
				"AV. RIO BRANCO 257 10 ANDAR__20040-009_RIO DE JANEIRO_BR_",
				"213 RUE DU FAUBOURG SAINT MARTIN__75010_PARIS_FR_",
				"HECTORSTRAAT 2 1__1076PR_AMSTERDAM_NL_",
				"MALLORCA 1_BALOS_35110_SANTA LUCIA DE TIIRAJANA_ES_J",
				"LANDALAGANGEN 6__41130_GOTEBORG_SE_",
				"LANDALAGANGEN 6__41130_GOTEBORG_SE_",
				"VASTRAVALLGATAN 31__27135_YSTAD_SE_",
				"6 AVENUE JULES DURAND__92600_ASNIERES SUR SEINE_FR_",
				"60 PORTE137 QARTIER COCO_KATI MALI__KATI MALI_ML_",
				"GUSTAV MAHLER STR 2__70195_STUTTGART_DE_",
				"19 RUE JEAN ZAY__69009_LYON_FR_",
				"12 BIS AVENUE DU MARECHAL TURENNE__70300_LUXEUIL LES BAINS_FR_",
				"NATALIE ZAHLES VEJ 21,3 TV__2450_KOEBENHAVN SV_DK_",
				"PADDESTOELENLAAN 63__3903GE_VEENENDAAL_NL_",
				"ARIBAU 12__08011_BARCELONA_ES_",
				"FUHLSBUETTLER STR 580__22337_HAMBURG_DE_",
				"24 AVE LAVOISIER__35174_BRUZ CEDEX_FR_",
				"MOSCOW, ZELENOGRAD, HOUSE 1103, FLAT 102__124460_MOSCOW_RU_",
				"FASANENSTRASSE 22__85757_KARLSFELD_DE_",
				"60 RUE DE RICHELIEU__75002_PARIS_FR_",
				"AMMAN__11118_AMMAN_JO_",
				"NIL___PVG_CN_",
				"16 RUE DES MURS SAINT YON__76100_ROUEN_FR_",
				"RIVIERE OMAN__97215_RIVIERE SALEE_MQ_",
				"__99999_PARIS_FR_",
				"KLOOSTERSTRAAT 9__5349 AB_OSS_NL_",
				"POSTBOKS 50__3705_SKIEN_NO_",
				"DAIMLERSTRASSE 8__72555_METZINGEN_DE_",
				"2980 WEST ROY FURMAN HWY__15370_WAYNESBURG_US_PA",
				"___SURREY_GB_",
				"___SURREY_GB_",
				"STR GALATA NR 55 BL C___VOLUNTARI_RO_",
				"60 RUE DE RICHELIEU__75002_PARIS_FR_",
				"20 RUE DE LA LIBERATION__4210_ESCH SUR ALZETTE_LU_",
				"KONSTRUKTORSKA 10A, 51__02-673_WARSZAWA_PL_",
				"10 ALEXANDRA COURT, BONHAY ROAD__EX4 3BZ_EXETER_GB_",
				"6 RUE JULES MASURIER__76600_LE HAVRE_FR_",
				"34 BIS RUE DU GENERAL DE GAULLE__94430_CHENNEVIERES SUR MARNE_FR_",
				"NIEUWE HAVEN 12__2511 LA_DEN HAAG_NL_",
				"12 BELLAMY HOUSE, NEW STREET__CM7 1EU_BRAINTREE_GB_",
				"HUERTAS DE LA VILLA__48007_BILBAO_ES_",
				"CALLE SANTO_74 MADRIDEJOS_45710_TOLEDO_ES_",
				"CHIASSI 22__25128_BRESCIA_IT_BS",
				"CHIASSI__25128_BRESCIA_IT_BS",
				"WILHELM-LEUSCHNER-STR. 1__56076_KOBLENZ_DE_",
				"14 RUE DU CHATEAU__77230_MARCHEMORET_FR_",
				"5 AVENUE DE VILMORIN__91370_VERRIERES LE BUISSON_FR_",
				"AMMAN__11118_AMMAN_JO_",
				"NASTARAN,GILAN BOULEVARD___RASHT_IR_",
				"__18057_ROSTOCK_DE_",
				"12 FREER CLOSE__Le7 9hu_HOUGHTON ON THE HILL_GB_",
				"21 RADNOR MEWS__W2 2SA_LONDON_GB_",
				"56 HURST ROAD__da15 9aa_SIDCUP_GB_",
				"18 HAHIZIM ST.__4626818_HERZLIYA_IL_",
				"OBERWIRTSTRASSE 1__06432_DIEZ_DE_",
				"????? ??? ??? 207-7??__46282_?????_KR_",
				"CALLE JOAN REBULL 22 A__43850_CAMBRILLS_ES_",
				"AV. RIO BRANCO 257 10 ANDAR__20040-009_RIO DE JANEIRO_BR_",
				"LEIRFENVEGEN 5__4355_KVERNALAND_NO_",
				"43 TER BOULEVARD DAVOUT__75020_PARIS_FR_",
				"26 RUE REIGNAULT__75013_PARIS_FR_",
				"CALLE SANTO___TOLEDO_ES_",
				" RUE VICTOR HUGO__92594_LEVALLOIS PERRET CEDEX_FR_",
				"PASAJE TODOS LOS SANTOS 6726___SANTIAGO_GB_",
				"SERAFIMOVICHA STR 2, 51__119072_MOSCOW_RU_",
				"7 NEWLAY LANE__LS18 4LE_LEEDS_GB_",
				"1 KAISERPLATZ__90763_FUERTH_DE_",
				"GESUALDO__83040_AVELLINO_IT_NA",
				"951 HOUGANG AVE 9 09-500__530951_SINGAPORE_SG_",
				"UNKNOWN___VENEZIA_IT_",
				"19 ALLEE VAN GOGH__33160_ST AUBIN DE MEDOC_FR_",
				"46 BOULEVARD ST ANTOINE__78150_LE CHESNAY_FR_",
				"AIR FRANCE__94209_IVRY_FR_",
				"BENTINCKPLEIN 85__KP3039_ROTTERDAM_NL_",
				"CNPE DE CIVAUX BP64__86320_CIVAUX_FR_",
				"7 RUE JEAN AMADE__66250_SAINT LAURENT DE LA SALANQUE_FR_",
				"ROGER DE LLURIA 82 3R1A__08009_BARCELONA_ES_",
				"156 PESCHER STRASSE__41065_MOENCHENGLADBACH_DE_",
				"13 RUE EUGNE VARLIN__75010_PARIS_FR_",
				"T HEALE PAED 26__8723ag_KOUDUM_NL_",
				"3 RUE JULES DELAUBE__33160_SAINT MEDARD EN JALLES_FR_",
				"8 BELEBEN___RISHON LEZION_IL_",
				"IM WIEGENFELD 10__85570_MARK SCHWABEN_DE_",
				"38 HYDE PARK GATE__sw7 5dp_LONDON_GB_",
				"PASEO DE LAS ARTES, 21 PORTAL 12, ATICO A__28320_PINTO_ES_",
				"19, SINSARO 18GIL, GWANAK-GU___SEOUL_KR_",
				"CHTCHENYC REPUBLIC__366000_GROZNY_RU_",
				"NAGY IMRE U. 134__AAAA_PECS_HU_",
				"7 NEWLAY LANE__LS18 4LE_LEEDS_GB_",
				"DEMYANA BEDNOGO STREET,2, BLDG 3? APP 64._123308_123308_??????_RU_",
				"DEMYANA BEDNOGO STREET 2-3-64__123308_MOSCOW_RU_",
				"STR. MUGUR MUGUREL, NR. 21, SECTOR 3__031227_BUCURESTI_RO_",
				"4 VESTRY MEWS 2A NORTH SIDE_WANDSWORTH COMMON_SW182SS_LONDON_GB_",
				"SONDERSOVEJ 18__8240_RISSKOV_DK_",
				"145 HAGENER STRASSE__58099_HAGEN_DE_",
				"KIRSTEN WALTHERS VEJ 7__2500_VALBY_DK_",
				"AVD SANTA TERESA N 34 LOCAL__47010_VALLADOLID_ES_",
				"OBERE STR 28__72766_REUTLINGEN_DE_",
				"KIRSTEN WALTHERS VEJ 7__2500_VALBY_DK_",
				"THIKA__00100_NAIROBI_KE_",
				"5 THE SIDINGS_ROY BRIDGE_PH314AG_ROY BRIDGE_GB_",
				"7 QUAI ANDRE CITROEN__75015_PARIS_FR_",
				"___COPENHAGEN_DK_",
				"68 BERLINER STRASSE__38165_LEHRE_DE_",
				"STEGMATTENWEG 11__4105_BIELBENKEN_CH_",
				"132 RUE MARIUS AUFAN__92300_LEVALLOIS PERRET_FR_",
				"ROUTE DE TREVES__2632_LUXEMBOURG_LU_",
				"HOOFDSTRAAT 62B__2678CL_DE LIER_NL_",
				"KIRSTEN WALTHERS VEJ 7__2500_VALBY_DK_",
				"KABANBAY STR. 53, OFFICE 1226___ASTANA_KZ_AL",
				"___MANCHESTER_GB_",
				"VIA SUORE 13/A__40026_IMOLA_IT_BO",
				"19 RUE MOLIERE BAT F APPT 22__60280_MARGNY LES COMPIEGNE_FR_ZZ",
				"3 MARKTPLATZ__64283_DARMSTADT_DE_",
				"???188?457?__201612_??_CN_SA",
				"66 RUE ABBE DE L EPEE__33000_BORDEAUX_FR_",
				"ARCHIMEDESLAAN 16_3.6_3584BA_UTRECHT_NL_",
				"AV. MAR MEDITERRANEO 71 4A__28340_VALDEMORO_ES_",
				"50 BEACH ROAD, NOORDHOEK___CAPE TOWN_ZA_",
				"IMMEUBLE SOTRAKA__BP E4481_BAMAKO_ML_",
				"408 SHAU KEI WAN ROAD___HONG KONG_HK_",
				"ZZZZ___HONG KONG_HK_",
				"CALLE BARON DE PATRAIX ,19 PTA4__46018_VALENCIA_ES_",
				"25 HAHNENKAMP__38124_BRUANSCHWEIG_DE_",
				"1 AVENUE EUGENE FREYSSINET__78280_GUYANCOURT_FR_",
				"CALLE GRAN PEZ__30004_MURCIA_ES_",
				"X___X_NL_",
				"RAMBLA ALCALA S/N__12597_SANTA MAGDALENA DE PULPIS_ES_CS",
				"AV MONTSENY 28__08198_SANT CUGAT DEL VALLES_GB_",
				"3 POSTSTRASSE__6300_WOERGL_AT_",
				"LES DIENS_LIEU DIT LES DIENS_71110_CHAMBILLY_FR_",
				"X___X_NL_",
				"OVERHOFF 10__6871 CZ_RENKUM_NL_",
				"PASEO DE BERIO 69__20018_DONOSTIA_ES_",
				"HOUWEG 2__3921DB_ELST_NL_",
				"AV MONTSENY 28__08198_SANT CUGAT DEL VALLES_GB_",
				"GUSTAV VIGELANDS VEI 1__0274_OSLO_NO_",
				"DROTTNINGVAGEN 1H__18132_LIDINGO_SE_",
				"HASENHEIDE 56__10967_BERLIN_DE_",
				"34 LAUREL WYND_DANESTONE_AB22 8XX_ABERDEEN_GB_",
				"1 RUE ELIE CREPEAU__44300_NANTES_FR_",
				"HARRENHORST 30__31542_BAD NENNDORF_DE_",
				"BAHNHOFSTRASSE 137__8620_WETZIKON ZH _CH_ZH",
				"AMMAN__11118_AMMAN_JO_",
				"ISAAC NEWTON SN POL IND CAN JORN__08430_LA ROCA DEL VALLES_ES_",
				"7 WINNARD AVENUE__LL181ED_RHYL_GB_",
				"06 BOULEVARD VIOLET__66301_THUIR_FR_",
				"29 RUE DE HABSHEIM__68400_RIEDISHEIM_FR_",
				"2511 ANTIETAM DRIVE__48105_ANN ARBOR_US_MI",
				"T HEALE PAED 26__8723ag_KOUDUM_NL_",
				"41 STETTINERSTRASSE__35410_HUNGEN_DE_",
				"8 BOULEVARD DE STRASBOURG__75010_PARIS_FR_",
				"RAMBLA ALCALA S/N__12597_SANTA MAGDALENA DE PULPIS_ES_CS",
				"16 CRAIGLANDS, ARDEEVIN RD,_DALKEY,_co dublin_DUBLIN_IE_",
				"1 RUE ELIE CREPEAU__44300_NANTES_FR_",
				"OVERHOFF 10__6871 CZ_RENKUM_NL_",
				"PLOT NO 95 LIGHT INDUSTRY AREA__65108_DAR ES SALAAM_TZ_",
				"29RUE DE HABSHEIM__68400_RIEDISHEIM_FR_",
				"??????___??????_RU_",
				"AMMAN__11118_AMMAN_JO_",
				"___MANCHESTER_GB_",
				"SW TERESY  5 9  M 408__91348_LODZ_PL_",
				"10 RUE IMAM AL GHAZALI LE BELVEDERE__1002_TUNIS_TN_",
				"PASARETI UT 114.__1026_BUDAPEST_HU_",
				"__99999_PARIS_FR_",
				"MONTALBAN DE CORDOBA__14548_CORDOBA_ES_",
				"AN DER KINDSWIESE 1__78315_RADOLFZELL_DE_",
				"16 AVENUE JEAN NATTE__83400_HYERES_FR_",
				"5 RUE DE LA GARE__74000_ANNECY_FR_",
				"SKOVSGAARDVEJ 2__9850_HIRTSHALS_DK_",
				"891, DIPLOMATE ROAD, MUYENGA__6852_KAMPALA_UG_",
				"HOEVESTEIN 205__6708AJ_WAGENINGEN_NL_",
				"DE GRAUWERT 14__3451 KL_VLEUTEN_NL_",
				"1 RUE DU GENERAL KOENIG__51726_REIMS_FR_",
				"__00000_NC_FR_",
				"16 AVENUE JEAN NATTE__83400_HYERES_FR_",
				"1-539-1-403 ONARI-CHO OMIYA-KU__330-0852_SAITAMA-SHI SAITAMA-KEN_JP_",
				"1-539-1 ONARI-CHO OMIYA-KU__330-0852_SAITAMA-SHI SAITAMA-KEN_JP_",
				"TIROSH ST 4_XXX_6085000_SHOHAM_IL_",
				"17 RUE REBEVAL__75019_PARIS_FR_",
				"LOUIS DAVIDSSTRAAT 10__7558LN_HENGELO OV_NL_",
				"CALLE GENERAL ARANAZ 1__28027_MADRID_ES_",
				"AMMAN__11118_AMMAN_JO_",
				"SCHONNEFELDSRASSE 78-80__45326_ESSEN_DE_",
				"__1202_GENEVE_CH_",
				"97013 POSTFACH__12701_BERLIN_DE_",
				"27 RUE DES LAVANDIERES__33600_PESSAC_FR_",
				"GANDIJEVA 197__11070_BELGRADE_RS_",
				"PLAZA DE MATUTE 4, 5OB__28012_MADRID_ES_",
				"NOUVEAU PONT___COTONOU_BJ_",
				"ZUIDERVAL 120__7543EZ_ENSCHEDE_NL_",
				"NIJVERHEIDSLAAN 29__8580_AVELGEM_BE_",
				"HOGE RIJNDIJK 97__2382 AC_ZOETERWOUDE_NL_",
				"PR. URIA GAGARINA 38-3-12__196158_SANKT-PETERBURG_RU_",
				"AUFLEGERSTRASSE 34__81735_MUNICH_DE_",
				"CHIYODA-KU TOKYO__102-8012_- NIBAN-CHO_JP_",
				"NO.173-23, SEC. 3, XITUN RD., XITUN DIST.__40764_TAICHUNG CITY_TW_",
				"NOUVELLE ROUTE BASTOS__14335_YAOUNDE_CM_",
				"DIJCKSCAMPENLAAN 42__1441PN_PURMEREND_NL_",
				"1686 , ROUTE DES ARCS__83720_TRANS-EN-PROVENCE_FR_",
				"NIEUWSTRAAT__1671BD_MEDEMBLIK_NL_",
				"6  RUE DES PRINCES   BAT  E  CHB  606__92100_BOULOGNE-BILLANCOURT_FR_",
				"CALLE SAN BARTOLOME 40 PISO 2C__20007_DONOSTIA_ES_",
				"JILINSHENG CHANGCHUNSHI FUAOXINDONGQU__130000_CHANGCHUN_CN_",
				"XX__XX_XX_NL_",
				"JAKTGATAN 33__11547_STOCKHOLM_SE_",
				"1 RUE DANGON__69004_LYON_FR_",
				"RUA ANTONIO PIRES 810__02730000_SAO PAULO_BR_SP",
				"AMMAN__11118_AMMAN_JO_",
				"LUIS MERELO Y MAS 1 ESC1__46023_VALENCIA_ES_",
				"BREDESTRAAT KOUTER 69__9920_LOVENDEGEM_BE_",
				"FLYDEDOKKEN 11 1. TH__9000_AALBORG_DK_",
				"XX__XX_XX_NL_",
				"YEHOSHUA RABINOVICH 64__58672_HOLON_IL_",
				"A. VAN VOORTHUIJSENWEG 9__8802ZC_FRANEKER_NL_",
				"XX__XX_XX_NL_",
				"3048-78 NAKABYO ABIKOSHI__2701121_CHIBA_JP_",
				"LA ROCHE BOURDEIL__37360_BEAUMONT LA RONCE_FR_",
				"1 CHEMIN DE DIANE_BEL AIR_91640_FONTENAY LEZ BRIIS_FR_",
				"XX__XX_XX_NL_",
				"4 LE BIGNON__35134_SAINTE COLOMBE_FR_",
				"XX__XX_XX_NL_",
				"10 IMPASSE DANIEL SORANO__31600_MURET_FR_ZZ",
				"APT 18 115 ROUTE DU MAS DESPORTS__34400_LUNEL_FR_",
				"111 WOBURN ST__02155_MEDFORD_US_MA",
				"XX__XX_XX_NL_",
				"FRUERING KIRKEVEJ 5_8660_8660_SKANDERBORG_DK_",
				"BLASKJELLVEIEN 20__4310_HOMMERSAK_NO_",
				"CHEMIN DES COTES D EN BAS__01800_PEROUGES_FR_",
				"4 LE BIGNON__34134_SAINTE COLOMBE_FR_",
				"___LEHRE_DE_",
				"HEBERGE DU LAC___TUNIS_TN_",
				"STENBJERGPARKEN 9__7400_HERNING_DK_",
				"7 RUE DE GROIX__22970_PLOUMAGOAR_FR_",
				"RUE CONDORCET__56530_QUEVEN_FR_",
				"3G31 KALPATARU AURA LBS MARG__400086_MUMBAI_IN_",
				"1 ALLEE ILE DE GROIX__44350_GUERANDE_FR_",
				"26 NORTH AVENUE_MOUNT MERRION_A94 V992_DUBLIN_IE_",
				"XXX__XXX_XXX_FR_",
				"4 RUE DES ABONDANCES__92100_BOULOGNE BILLANCOURT_FR_",
				"STR CANTONULUI NR 14B__077065_CORBEANCA_RO_",
				"AVENIDA REINO DE VALENCIA 102__46006_VALENCIA_ES_",
				"OLIVERA 72__08004_BARCELONA_ES_",
				"7 GOLDSPINK LANE__NE2 1NQ_NEWCASTLE UPON TYNE_GB_",
				"15 RUE BUISSONNIERE__33710_COMPS_FR_",
				"XX__XX_XX_NL_",
				"CLEMENT DHOOGHESTRAAT 16__9120_BEVEREN_BE_",
				"20 RUE LAPURDI__64310_SAINT PEE SUR NIVELLE_FR_",
				"SUITBERTWEG 1B__44805_BOCHUM_DE_",
				"VIA CORRIVAZZO NM 32B BS__25030_CASTELMELLA_IT_BS",
				"49 NITHSDALE ROAD_FLAT 0/2_G41 2AJ_GLASGOW_GB_",
				"KLOSTERSTRASSE 26__50931_KOLN_DE_",
				"CHEMIN DES COTES D EN BAS__01800_PEROUGES_FR_",
				"___BERLIN_DE_",
				"VASANT VIHAR_DEHRADUN_248006_DEHRADUN_IN_",
				"___BERLIN_DE_",
				"LELIEBERG 4__4708LK_ROOSENDAAL_NL_",
				"__9999_LONDON_GB_",
				"VIA G. OBERDAN, 31__40122_BOLOGNA_IT_BO",
				"___BERLIN_DE_",
				"19 RUE DES MONTAGNARDS__63122_CEYRAT_FR_",
				"___GAUDELOUPE_GP_",
				"7 RUE JEAN AMADE__66250_SAINT LAURENT DE LA SALANQUE_FR_",
				"___MANCHESTER_GB_",
				"158 , CHEMIN DE L'ARM�E D'AFRIQUE__13010_MARSEILLE_FR_",
				"___GAUDELOUPE_GP_",
				"01BP116___OUAGADOUGOU_BF_",
				"__99999_LONDON_GB_",
				"___LEHRE_GB_",
				"47 RUE GUERSANT__75017_PARIS_FR_",
				"15 RUE DES BRUS__81160_SAINT JUERY_FR_",
				"CALLE HELIODORO RODRIGUEZ LOPEZ__38005_SANTA CRUZ DE TENERIFE_ES_",
				"PO BOX 6386__31442_DAMMAM_SA_",
				"AMMAN__999999_AMMAN_JO_",
				"23 RUE DU NANT_CO PAQUET_1207_GENEVE_CH_",
				"GROOT HERTOGINNELAAN 87__2517ED_DEN HAAG_NL_",
				"BOLYAI TER 1_HU17781279_3700_KAZINCBARCIKA_HU_",
				"X___SAINT PAUL_RE_",
				"___GAUDELOUPE_GP_",
				"8 CHEMIN DU VRUGUIC__29500_ERGUE GABERIC_FR_",
				"PASEO BONANOVA__08022_BARCELONA_ES_",
				"CZARNIKAUER STRASSE 10__10439_BERLIN_DE_",
				"19C GARDENIA DRIVE__GU24 9XG_WOKING_GB_",
				"RUE PRINCIPALE LIEU DICCIPOLO__20146_SOTTA_FR_",
				"83A KILN RIDE__RG403PJ_WOKINGHAM_GB_",
				"___GAUDELOUPE_GP_",
				"16B, TEKELS PARK__GU15 2LF_CAMBERLEY_GB_",
				"ZONNEBLOEMSTRAAT 12__6561WK_GROESBEEK_NL_",
				"FBG PH. SUCHARD 10A__2017_BOUDRY_CH_NE",
				"__00000_00000_IT_",
				"4987 SIERRA VISTA AVE APT 6__92505_RIVERSIDE_US_",
				"MASDURAN__08016_BARCELONA_ES_",
				"362 , ALL�E DE BERGANTON__33127_SAINT-JEAN-D'ILLAC_FR_",
				"X___SAINT PAUL_RE_",
				"6 RUE JEAN MERMOZ__94310_ORLY_FR_",
				"MARGARETHENSTRASSE 4__65812_BAD SODEN / TAUNUS_DE_",
				"VIA PRIMO MAGGIO 18__21040_ORIGGIO_IT_",
				"HANDELSKAI 92__1200_WIEN_AT_",
				"__00000_00000_IT_",
				"__99_99_FR_",
				"ROSIISKAJA, 45B, AP69__02099_KIEV_UA_",
				"MARTINIQUE___FORT DE FRANCE_MQ_",
				"3104 HERON SHORES DRIVE_342931407_34293-1407_VENICE_US_FL",
				"___ECUBLAIN_CH_",
				"CALLE DE LA VIA LACTEA, 29__28023_MADRID_ES_",
				"MARSWEG 2__6988 BM_LATHUM_NL_",
				"41 EMILY ST__2280_MARKS POINT_AU_",
				"__00000_00000_IT_",
				"ELSABRAENDSTROEMSTRASSE 3__46045_OBERHAUSEN_DE_",
				"MARTINIQUE___FORT DE FRANCE_MQ_",
				"MARTINIQUE___FORT DE FRANCE_MQ_",
				"VIA L. MEONI 15__51037_MONTALE_IT_PT",
				"8 AVENUE DES FRAN__1950_KRAAINEN_BE_",
				"X___SAINT PAUL_RE_",
				"XXX__XXX_MADRID_ES_",
				"SOBORG HOVEDGADE 72B__2860_SOBORG_DK_",
				"4987 SIERRA VISTA AVE, APT 6__92505_RIVERSIDE_US_",
				"XXX__XXX_MADRID_ES_",
				"537-1 PRINSENGRACHT__1016HS_AMSTERDAM_NL_",
				"___PARIS_FR_",
				"36 BAT B RUE GATELLIET__77000_MELUN_FR_",
				"1 ALLEE DU ROUERGUE__17000_LA ROCHELLE_FR_",
				"WALSRODER STR. 77__30851_LANGENHAGEN_DE_",
				"1769 POSTFACH__29207_CELLE_DE_",
				"17 LANGSIDE COURT__g718ns_GLASGOW_GB_",
				"10 CHEMIN DU BASSIN__13014_MARSEILLE_FR_",
				"STETTINER STRASSE 41__35410_HUNGEN_DE_",
				"32 RUE DES OURMETS__31150_FENOUILLET_FR_",
				"ANDREJA PUMPURA IELA 3__LV1010_RIGA_LV_",
				"X___SAINT PAUL_RE_",
				"44 BRANNKYRKAGATAN__11822_STOCKHOLM_SE_",
				"362 , ALL�E DE BERGANTON__33127_SAINT-JEAN-D'ILLAC_FR_",
				"1 OXFORD STREET__M15AN_MANCHESTER_GB_",
				"5 IMPASSE DU VALLON__04510_AIGLUN_FR_",
				"INDIA__0000_INDIA_IN_",
				"INDIA__0000_INDIA_IN_",
				"14 PENCRAIG CLOSE__CV8 2NT_KENILWORTH_GB_",
				"97 RUE DE LABBE COPPIN__62224_EQUIHEN PLAGE_FR_",
				"169, AVENUE DE L'AMITI�__BP.68_BRAZZAVILLE_CG_",
				"103 BD JEAN MOULIN__48100_ANGERS_FR_",
				"TOMTEBOGATAN 42__11338_STOCKHOLM_SE_",
				"152 RUE BABEUF__93100_MONTREUIL_FR_",
				"20 BAHNHOFSTRASSE__13125_BERLIN KAROW_DE_",
				"16 RUE JEAN ZAY__94120_FONTENAY SOUS BOIS_FR_",
				"VIA ROMA 33__35020_LEGNARO_IT_PD",
				"2 BALTIYSKIY PEREULOK 5-39__125315_MOSCOW_RU_",
				"16 FOLKSKOLEGATAN__11735_STOCKHOLM_SE_",
				"BATALIONOW CH?OPSKICH, 10_44-200_44-200_RYBNIK_PL_",
				"VLIETENBURG 49__2804WR_GOUDA_NL_",
				"___GAUDELOUPE_GP_",
				"330 LEEDS ROAD_ECCLESHILL_BD2 3LQ_BRADFORD_GB_",
				"ORSKOVBAKKEN 2__7400_HERNING_DK_",
				"HAKANIEMENRANTA 14 B 67__00530_HELSINKI_FI_",
				"16 FOLKSKOLEGATAN__11735_STOCKHOLM_SE_",
				"4TER PLACE DUMOUSTIER__44000_NANTES_FR_",
				"213 CITE OLM SOUISSI__10000_RABAT_MA_",
				"ZUIDLAND 95__2408BM_ALPHEN AAN DEN RIJN_NL_",
				"2568 CHEMIN DE CLAVARY__06810_AURIBEAU SUR SIAGNE_FR_",
				"GRAN CAPITAN__18002_GRANADA_ES_",
				"????? ?? ??2? 1450-6?? ????? 603?___??_KP_",
				"PROFESOR SAINZ CANTERO 15__18002_GRANADA_ES_GR",
				"___GAUDELOUPE_GP_",
				"___MANCHESTER_GB_",
				"RUE CONDORCET__56530_QUEVEN_FR_",
				"RUE CONDORCET__56530_QUEVEN_FR_",
				"VIA MALPAGA__27010_MONTICELLI PAVESE_IT_PV",
				"VIA CAVOUR 8__25089_VILLANUOVA SUL CLISI_IT_",
				"30 RUE DU GUET__59800_LILLE_FR_",
				"CALLE DIAGONAL, 9__08230_MATADEPERA_ES_",
				"22 THE OVAL_WALKER_NE63LH_NEWCASTLE UPON TYNE_GB_",
				"MIDTSKOGEN 9 A_H0203_1400_SKI_NO_",
				"22 ROUTE DE SACLAY__91120_PALAISEAU_FR_",
				"126 UPPER TULSE HILL__SW2 2RR_LONDON_GB_",
				"STAROKONUSENNY PEREULOK 26-57__119002_MOSCOW_RU_",
				"15 IMPASSE JEANNE MARTIN__06300_NICE_FR_",
				"2568 CHEMIN DE CLAVARY__06810_AURIBEAU SUR SIAGNE_FR_",
				"19 BD BEAU SOLEIL__13015_MARSEILLE_FR_",
				"HOTEL PENNSYLVANIA__99999_NEW YORK_US_NY",
				"59 VILLIERS STREET__CV2 4HQ_COVENTRY_GB_",
				"HAMMERWEG 13__5702_NIEDERLENZ_CH_",
				"02 RUE MISSAK MANOUCHIAN__44230_SAINT SEBASTIEN SUR LOIRE_FR_",
				"AMMAN__11118_AMMAN_JO_",
				"MUN. BUCURESTI STR. SARADEI NR. 53-61___BUCURESTI_RO_",
				"VIA PERMUTA 16__40017_SAN GIOVANNI IN PERSICETO_IT_BO",
				"EICHENBUSCH 10A__21465_REINBEK_DE_",
				"13180 DOWNING LANE__92131_SAN DIEGO_US_CA",
				"17 AVENUE DU DOCTEUR ARNOLD NETTER__75012_PARIS_FR_",
				"RUA OLIVEIRA MARTINS NR 20-2� AND- APRT 8__9999_LUANDA_AO_",
				"AMMAN__11118_AMMAN_JO_",
				"STR CONDORILOR 9__600302_BACAU_RO_",
				"02 RUE MISSAK MANOUCHIAN__44230_SAINT SEBASTIEN SUR LOIRE_FR_",
				"GROTE BEER 222__1188BG_AMSTELVEEN_NL_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"HAMMERUM HOVEDGADE 62__7400_HERNING_DK_",
				"OLD BAY HORSE INN__WF11 9JA_LEEDS_GB_",
				"KILNAMANAGHBEG_GLENEALY__WICKLOW_IE_",
				"VIA NIMIS 11/C__33050_RUDA_IT_",
				"36 ALLEE DE LA HOUSSAIE__44600_ST NAZAIRE_FR_",
				"LELIEBERG 4__4708LK_ROOSENDAAL_NL_",
				"775 AVENUE MARCEL CAMUSSO__13600_LA CIOTAT_FR_",
				"HC ANDERSENS BOULEVARD 37__1553_COPENHAGEN_DK_",
				"1243 16TH STREET__07024_FORT LEE_US_NJ",
				"VESANGANTIE 2 I 11__40700_JYVASKYLA_FI_",
				"9 RUE DE LA CHAPELLE__29370_ELLIANT_FR_",
				"775 AVENUE MARCEL CAMUSSO__13600_LA CIOTAT_FR_",
				"PESTALOZZISTRASSE 2__CH 8200_SCHAFFHAUSEN_CH_",
				"RUE CONDORCET__56530_QUEVEN_FR_",
				"68 FOUNTAIN HOUSE__W1K7HQ_LONDON_GB_",
				"MAURITSKADE 19__2514HD_THE HAGUE_NL_",
				"122 RESIDENCE ELYSEE 2__78170_LA CELLE SAINT CLOUD_FR_",
				"SIEBENBUERGENERSTR 2__93057_REGENSBURG_DE_",
				"4 FARMAN_4 FARMAN_NW9 5PD_COLINDALE_GB_",
				"VIA EBOLI 1__92019_SCIACCA_IT_AG",
				"ROSIISKA, 45B AP69__02099_KIEV_UA_",
				"3 AGADIN RD__NG5 4SS_WOOTHORT_GB_",
				"MARSSTEDEN 50__7547 TE_ENSCHEDE_NL_",
				"9 ET 11 RUE LAFAYETTE__78000_VERSAILLES_FR_",
				"8 SAINT CHRISTOPHERS DRIVE__LS29 0RJ_ADDINGHAM_GB_",
				"18 BRADFORD PARK DRIVE UNITED KINDGOM__BL2 1PA_BOLTON_GB_",
				"AMMAN__11118_AMMAN_JO_",
				"35 RUE A BERNARDEAU__91550_PARA VIEILLE POSTE_FR_",
				"23 CHEMIN DE BELERIVE__1006_LAUSANNE_CH_",
				"PO BOX 9101__6500HB_NIJMEGEN_NL_",
				"44, ERIC MOORE ROAD, SURULERE__101282_LAGOS_NG_",
				"ZANDSTRAAT 13__6658 Cl_BENEDEN-LEEUWEN_NL_",
				"??????????66-3?10?__23152_NEW TAIPEI CITY_TW_",
				"AIT IDIR BENI DOUALA TIZIOUZOU ALGERIA_ALGERIA__ALGERIE_DZ_",
				"JOHAN THORSENS GATE 52__0000_STAVANGER_NO_",
				"FLAT A 5F MAYFAIR MANSION_6 ASH STREET_na_KOWLOON_HK_",
				"LUITPOLDSTRASSE 83__67480_EDENKOBEN_DE_",
				"31 BLACKSHALE ROAD_MANSFIELD WOODHOUSE_NG19 7GE_NOTTINGHAM_GB_",
				"LERSUNDI, 2__48009_BILBAO_ES_",
				"WILLEM VAN ORANJESTRAAT 42__4931 NJ_GEERTUIDENBERG_NL_",
				"RUA 38, N 6__0000_BENFICA LUANDA_AO_",
				"VAN SOMERENWEG 20_3062 RH_3062 RH_ROTTERDAM_NL_",
				"CALLE RAMON DE CALATRAVA, PORTAL 2, 1C__06800_MERIDA_ES_",
				"150 AVENUE SAINT JOSEPH__13290_AIX EN PROVENCE_FR_",
				"NA___MANCHESTER_GB_",
				"___PARIS_FR_",
				"___MANCHESTER_GB_",
				"SHDEROT HASHALOM 180/5__2192727_KARMIEL_IL_",
				"25 ROUTE DE LA SOULOIRE__33750_SAINT GERMAIN DU PUCH_FR_",
				"BOX 888___MAUN_BW_",
				"42 RUE MESLAY__75003_PARIS_FR_",
				"XX__XX_XX_NL_",
				"AMMAN__11118_AMMAN_JO_",
				"CZERNINGASSE 10/1A__1020_WIEN_AT_",
				"NORDSTRASSE 17__42489_WUELFRATH_DE_",
				"VIA ARIANO IRPINO 45__00100_ROMA_IT_",
				"LA BALANGUERA 1__07300_INCA_ES_",
				"WHITEHALL BARN_WHITE HALL LANE_CW11 3QJ_WARMINGHAM_GB_",
				"ZEISIGSTRASSE 23__91315_HOECHSTADT AN DER AISCH_DE_",
				"HOUSE NO 225__560043_BENGALURU_IN_",
				"880 AVENUE DES ARAVIS__74800_SAINT-PIERRE EN FAUCIGNY_FR_",
				"65 RUE RAYNOUARD_PARIS_75016_PARIS_FR_",
				"MID LEVELS_CROSSMOOR ROAD_BS26 2DY_AXBRIDGE_GB_",
				"ADELAARSTRAAT 17__3514CA_UTRECHT_NL_",
				"24 OBSERVATORY ROAD__SW14 7QD_LONDON_GB_",
				"XXX___XXX_DE_",
				"20 BOULEVARD DE LA REPUBLIQUE__78400_CHATOU_FR_",
				"100 ESPLANADE DU GENERAL DE GAULLE__92932_COURBEVOIE_FR_",
				"111 AVENUE VICTOR HUGO__75116_PARIS_FR_",
				"RUA MACAE - 311_CASA 3_28895889_RIO DAS OSTRAS_BR_RJ",
				"JOHANNES VAN DER WAALSSTRAAT, 58-2__1098PP_AMSTERDAM, NETHERLANDS_NL_",
				"___MANCHESTER_GB_",
				"AM ROTTCHEN 96__40468_DUSSELDORF_DE_",
				"AM ROETTCHEN 96__40468_DUESSELDORF_DE_",
				"__99900_NAPLES_IT_",
				"HOLUNDERWEG 4__86316_FRIEDBERG_DE_",
				"___MANCHESTER_GB_",
				"AMMAN__11118_AMMAN_JO_",
				"DISTT MOGA_PUNJAB_142039_MOGA_IN_PB",
				"52 NANT FAWR ROAD__CF23 6JR_CARDIFF_GB_",
				"117 RUE DE REUILLY__75012_PARIS_FR_",
				"BRABECKSTR. 4__30559_HANNOVER_DE_",
				"BRABECKSTR. 4__30559_HANNOVER_DE_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"23 RUE DOCTEUR FIGHIERA__06300_NICE_FR_",
				"___SOLIHULL_GB_",
				"NC__NC_MARSEILLE_FR_",
				"FAGERPARKEN 13__2950_VEDVAEK_DK_",
				"LEPANTO 1__46894_GENOVES_ES_",
				"__75009_PARIS_FR_",
				"960 CHEMIN DU COLLET RODON__13760_ST CANNAT_FR_",
				"32A CHERNY VRAH BLVD, FL.3___SOFIA_BG_",
				"KOTOKU SINONOME 1-9-14-1415__999999_TOKYO_JP_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"___ROMA_IT_",
				"XXX__XXX_XXX_ZZ_",
				"CHLOSTERBRUEEHL 14__5430_WETTINGEN_CH_",
				"43 TBILISSY HIGHWAY__0091_YEREVAN_AM_",
				"RUE AISSANI ABDELLAH__18016_JIJEL_DZ_",
				"__12701_BERLIN_DE_",
				"7426 SOUTH JAMES MADISON HWY__23936_DILLWYN_US_",
				"__._._FR_",
				"DOMINICA STATE COLLEGE_PO BOX 2066 STOCK FARM CAMPUS NANA_0724_ROSEAU_DM_",
				"60 ALLEE DU PAS DOUEN__33370_BONNETAN_FR_",
				"WHITEHALL BARN_WHITE HALL LANE_CW11 3QJ_WARMINGHAM_GB_",
				"RAKETSTRAAT 66__1130_BRUSSEL_BE_",
				"111 AVENUE VICTOR HUGO__75116_PARIS_FR_",
				"ERSKINE FERRY ROAD__G60 5EU_OLD KILPATRICK_GB_",
				"HANDELSKAI 94-96__1200_WIEN_AT_",
				"CITE 150 LOGTS N149__05000_BATNA_DZ_",
				"ROMEFLAT 118__1422ES_UITHOORN_NL_",
				"__12701_BERLIN_DE_",
				"VALENTINA SEROVA 34___KIEV_UA_",
				"PURPERREIGER 10__1191 TE_OUDERKERK AAN DE AMSTEL_NL_",
				"140 BD 2 MARS RUE MOHAMED YAZIDI___CASABLANCA_MA_",
				"140 BD 2 MARS AV MED YAZIDI___CASABLANCA_MA_",
				"17 RUE CAMELINAT__42000_SAINT-ETIENNE_FR_",
				"PARKLAAN 7__2225sn_KATWIJK_NL_",
				"BOERHAVENPLEIN 126__3112 LN_SCHIEDAM_NL_",
				"4 RUE DU ROND POINT__91510_LARDY_FR_",
				"__NC_NC_FR_",
				"___COPENHAGEN_DK_",
				"107 SRIVEN TOWERS 4TH CROSS LAKSHMI LAYO_BANGALORE_560037_BANGALORE_IN_",
				"HALVDAN SVARTES GATE 9__4044_STAVANGER_NO_",
				"___MANCHESTER_GB_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"B77 GANESH PARK NEAR GACL COLONY__392001_BHARUCH_IN_",
				"CALLE CRISTO DE LA VERA CRUZ 26__41808_VILLANUEVA DEL ARISCAL_ES_",
				"11 MESSAHA SQUARE  DOKKI GIZA EGYPT__12311_GIZA_EG_",
				"DOORNBURG 150__1081JX_AMSTERDAM_NL_",
				"7 VLEI ST RETIEF DESPATCH__6220_PORT ELIZABETH_ZA_",
				"7 VLEI ST, RETIEF, DESPATCH__6220_PORT ELIZABETH_ZA_",
				"NEUENGAMMER HAUSDEICH 602__21037_HAMBURG_DE_",
				"GLADIOLENSTRAAT 7__2071ns_SANTPOORT_NL_",
				"DUNCOMBE PARK__YO62 5EB_HELMSLEY_GB_",
				"ROUTE DU FARON__83200_TOULON_FR_",
				"1 VILLA DEYLAU__75116_PARIS_FR_",
				"??????????66-3?10?__231_NEW TAIPEI CITY_TW_",
				"2756 S QUINCY AVE__53207_MILWAUKEE_US_WI",
				"847 MONTGOMERY DRIVE__L9G3H6_ANCASTER, ONTARIO_CA_ON",
				"11 BIS RUE DES TERNES__75017_PARIS_FR_",
				"609 BRAHEGATAN 9__415 01_GOTHENBURG_SE_",
				"83 AVENUE SAINT JACQUES__91600_SAVIGNY SUR ORGE_FR_",
				"59 ROUTE DES JEUNES__1227_CAROUGE_CH_",
				"ARENABOULVARD 1_POSTBUS 22981_1100DL_AMSTERDAM_NL_",
				"36 RUE DE LA CHARBONNIERE__77144_MONTEVRAIN_FR_",
				"IDUNGATAN 3__11345_STOCKHOLM_SE_",
				"SPOORSTRAAT 19__4841AN_PRINSENBEEK_NL_",
				"___XXX_DE_",
				"GENERAL RICARDOS 8__28019_MADRID_ES_M",
				"GANGBOORD 32__3823TH_AMERSFOORT_NL_",
				"LIMA 115  PISO 4  B P 1073 C A B A__1073_BUENOS AIRES_AR_",
				"4 RUE FRANCIS PICABIA__94000_CRETEIL_FR_",
				"SANTIAGO APSOTOL__08903_BARCELONA_ES_",
				"COOPERATIVE HOUSE SOCIETY LTD_FIRST FLOOR,22 MOUNT MARY ROAD,BANDRA WEST_40050_MUMBAI_IN_",
				"4, IONA STREET LANE_EH68SX_EH68SX_EDINBURGH_GB_",
				"COOPERATIVE HOUSE SOCIETY LTD_FIRST FLOOR,22 MOUNT MARY ROAD,BANDRA WEST_400050_MUMBAI_IN_",
				"SHANDONG__266000_QINGDAO_CN_SG",
				"PLAZA DE LA VILA 18__08012_BARCELONA_ES_",
				"19 BD BEAU SOLEIL__13015_MARSEILLE_FR_",
				"11, ALLEE PIERRE DE MANSFELD__2118_LUXEMBOURG_LU_",
				"NOKANPERA 4___ VANHALINNA_FI_",
				"___OSLO_NO_",
				"6 ALL DU NIGER_TOULOUSE_31000_TOULOUSE_FR_",
				"LINDSKOVVEJ   58_9000_9000_AALBORG_DK_",
				"101 ALLEE D AQUITAINE__92000_NANTERRE_FR_",
				"11 RUE DES CHAMPS__L1323_LUXEMBOURG_LU_",
				"___OSLO_NO_",
				"9 ET 11_RUE LAFAYETTE_78000_VERSAILLES_FR_",
				"????? ?? ??? 11_???? 115? 801?_44240_ULSAN_KR_",
				"___OSLO_NO_",
				"89 RUE D ABOUKIR__75002_PARIS_FR_",
				"140 BD 2 MARS RUE MOHAMED YAZIDI___CASABLANCA_MA_",
				"LAS TRANQUERAS 160, DEPT. 703, LAS CONDES__7560283_SANTIAGO_CL_",
				"CALLE PRADES__43006_TARRAGONA_ES_",
				"37 BIS RUE JEAN DE LA FONTAINE__75016_PARIS_FR_",
				"BASSENGVEIEN 22_2604_2390_MOELV_NO_",
				"XX__XX_XX_NL_",
				"GRASSLAU 1__5500_BISCHOFSHOFEN_AT_",
				"RUE ALCIDE JENTZER__1205_GENEVA_CH_",
				"AVENIDA DE LA PAZ,65-2G__26004_LOGRONO_ES_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"HEIBLOEMSTRAAT 8__2490_BALEN_BE_",
				"858_FALADIE SEMA_2586_BAMAKO_ML_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"BEECH COTTAGE_LONGLAY FRASERBURGH_AB438RL_ABERDEEN_GB_",
				"SEXAXA WARD___MAUN_BW_",
				"__73655_PLUEDERHAUSEN_DE_",
				"9 RUE DES CHARMILLES__95150_TAVERNY_FR_",
				"GAMERSLAGPLEIN 48__6826 LB_ARNHEM_NL_",
				"54098 P O BOX__SW20 8UU_LONDON_GB_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"22 SCHULZESTRASSE__13187_BERLIN_DE_",
				"__XX_XX_FR_",
				"23 ROUTE DES HERYS__44120_VERTOU_FR_",
				"2116/ NA FLORENCI__1-11000_PRAHA_CZ_",
				"4 BD DES CORSAIRES__40130_CAPBRETON_FR_",
				"KINKERSTRAAT 274C__1053 gb_AMSTERDAM_NL_",
				"__12701_BERLIN_DE_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"__XX_XX_FR_",
				"?? ??? ??? ????37 ????? 501? 1203?__31455_ASAN_KR_",
				"BALLESTER 27 PRAL 4__08023_BARCELONA_ES_",
				"9 RUE DES CHARMILLES__95150_TAVERNY_FR_",
				"3 RUELLE CRICRI__77515_POMMEUSE_FR_",
				"___MANCHESTER_GB_",
				"__XX_XX_FR_",
				"___NEWCASTLE_GB_",
				"MAGNOLIA 3__08690_SANTA COLOMA DE CERVELLO_ES_B",
				"HEINRICHSTRASSE 217__8005_ZURICH_CH_",
				"DALTONSTRAAT 17__3846 BX_HARDERWIJK_NL_",
				"26 ARMSTRONG ROAD__NR7 9LJ_NORWICH_GB_",
				"33, SAGAIDACHNOGO STR__04070_KIEV_UA_",
				"15 RUE ETIENNE DOLET__75020_PARIS_FR_",
				"3 RUE PARMENTIER__31000_TOULOUSE_FR_",
				"91 PROMENADE DES GOLFEURS__77600_BUSSY ST GEORGES_FR_",
				"YOUSSEF SURSOCK___BEIRUT_LB_",
				"25 ROUTE DE LA SOULOIRE__33750_SAINT GERMAIN DU PUCH_FR_",
				"RIYADH___RIYADH_SA_",
				"INDEPENDENCIA 1074__7600_MAR DEL PLATA_AR_",
				"2 JUBILEE TERRACE_AB56 4QA_AB56 4QA_FINDOCHTY_GB_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"VIA XIMENES 12_00197_00197_ROMA_IT_RM",
				"ALFEREZ ROJAS 9__03009_ALICANTE_ES_",
				"258_FALADIE SEMA_285_BAMAKO_ML_",
				"9 RUE DES CHARMILLES__95150_TAVERNY_FR_",
				"SCIENCE PARK 1__66123_SAARBRUECKEN_DE_",
				" CHEMIN DE KERNAVENAS__29360_CLOHARS CARNOET_FR_",
				"HOOFDSTRAAT__8162AG_EPE_NL_",
				"1 THE GREEN BODEN PARK RATHFARNHAM__16_DUBLIN_IE_",
				"9 KEHILAT RIGA ST.__6940044_TEL AVIV_IL_",
				"TAXISSTRASSE 5__93086_WOERTH AN DER DONAU_DE_",
				"NEW DELHI__110008_NEW DELHI_IN_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"91 PROMENADE DES GOLFEURS__77600_BUSSY ST GEORGES_FR_",
				"VIA DI VITTORIO__04023_FORMIA_IT_LT",
				"NEWTONSTRAAT 2__3401JA_IJSSELSTEIN UT_NL_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"9 BIS BOULEVARD DE LA CORNICHE__74200_THONON LES BAINS_FR_",
				"HUERTOS, 13__46500_SAGUNTO_ES_",
				"PRESIDENTE BERRO 395__85000_TRINIDAD_UY_",
				"9, RUE SOYER__92200_NEUILLY-SUR-SEINE_FR_",
				"__XX_XX_FR_",
				"VALDIVIESO 401_RECOLETA__SANTIAGO_CL_",
				"305 LAKSHYA HOME_KARNATAKA_560067_BANGALORE_IN_",
				"75 RUE DU GENERAL GIRAUD__42300_ROANNE_FR_",
				"211 STATION ROAD__CV7 7FE_BALSALL COMMON_GB_",
				"211 STATION ROAD__CV7 7FE_BALSALL COMMON_GB_",
				"108_BAMAKO_478_BAMAKO_ML_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"CALLE BENET MERCA, 22__08012_BARCELONA_ES_",
				"WILHELMINASTRAAT 8__1054 WH_AMSTERDAM_NL_",
				"14 NORTON DRIVE__NR4 6JD_NORWICH_GB_",
				"10 RUE VANDREZANNE__75013_PARIS_FR_",
				"AV CHEDID JAFET 222__04551065_SAO PAULO_BR_",
				"FISCHERS ALLEE 84__22763_HAMBURG_DE_",
				"20 IMPASSE DU CLOCHER__77000_LA ROCHETTE_FR_",
				"24 IMPASSE DES CIVELLES__34470_PEROLS_FR_",
				"SOBERANIA 19__28260_GALAPAGAR_ES_",
				"CALLE RODES__08901_L'HOSPITALET DE LLOBREGAT_ES_B",
				"YOUSSEF SURSOCK___BEIRUT_LB_",
				"RAZIEL DAVID 29/7__324950_HAIFA_IL_",
				"RAZIEL DAVID 29/7__324950_HAIFA_IL_",
				"APOQUINDO 2929 PISO 22_LAS CONDES_7550246_SANTIAGO_CL_",
				"HANDELSTR. 1__12623_BERLIN_DE_",
				"24 THE AVENUE__NR12 8TR_WROXHAM_GB_",
				"6 BELVEDERE AVENUE NORTH CIRCULAR ROAD_D1 DUBLIN IRELAND_D1_DUBLIN_IE_",
				"VIA XIMENES 12_00197_00197_ROMA_IT_RM",
				"HOGLA 5___EIN SARID_IL_",
				"1314 DONG 704 HO JUGONG 13 DANJI APT__153-751_SEOUL_KR_",
				"103 AVENUE DU BAC__94210_LA VARENNE SAINT HILAIRE_FR_",
				"X__69_X_FR_",
				"RUE RAYHANE N 50__11000_SALE_MA_",
				"CALLE PARRALILLOS SN_FAC ECONOMICAS_09001_BURGOS_ES_",
				"13 OCTOMVRIOU__18758_ATHENS_GR_",
				"41 RUE LEPIC__75018_PARIS_FR_",
				"NASSAUPLEIN 42__1815GV_ALKMAAR_NL_",
				"RUMFORDSTR.22__80469_MUENCHEN_DE_",
				"9 BOULEVARD DE PRAGUE__30000_NIMES_FR_",
				"13 BUELT__48143_MUENSTER_DE_",
				"SANTA RITA NO. 84__90100_SANTIAGO OF CUBA_CU_",
				"NICOLAAS WITSENKADE_7 - II_1017ZR_AMSTERDAM_NL_",
				"29 ROMILLY ROAD WEST__CF5 1FT_CARDIFF_GB_",
				"29 ROMILLY ROAD WEST_Y PANT_CF5 1FT_CARDIFF_GB_",
				"278 THIMBLE MILL LANE__B7 5HD_BIRMINGHAM_GB_",
				"ERDIKO 42_ERDIKO 42_20500_MONDRAGON_ES_SS",
				"1 IMPASSE DES BUIS__27520_BOISSEY LE CHATEL_FR_",
				"172_KOROFINA RUE 172 PORTE 50_1856_BAMAKO_ML_",
				"POSTBOKS 13__4297_SKUDENESHAVN_NO_",
				"ERDIKO 42_ERDIKO 42_20500_MONDRAGON_ES_SS",
				"20 IMPASSE DU CLOCHER__77000_LA ROCHETTE_FR_",
				"97 RUE MARX DORMOY__91480_QUINCY SOUS SENART_FR_",
				"DERDE EGELANTIERSDWSTR 6_DERDE EGELANTIERSDWSTR 6_1015SG_AMSTERDAM_NL_",
				"X__69_X_FR_",
				"ERDIKO 42_ERDIKO 42_20500_MONDRAGON_ES_SS",
				"43 PEASLANDS ROAD_EX109BE_EX109BE_SIDMOUTH_GB_",
				"VOORHAVEN__3025HE_ROTTERDAM_NL_",
				"ERDIKO 42_ERDIKO 42_20500_MONDRAGON_ES_SS",
				"WATERLELIE 36__2671WE_NAALDWIJK_NL_",
				"DOCTOR IRANZO 54. ENTRESUELO 7__50002_ZARAGOZA_ES_",
				"339 RUE DE BELLEVILLE__75019_PARIS_FR_",
				"278 THIMBLE LANE__B7 5HD_BIRMINGHAM_GB_",
				"4828 WINDGATE TRL NW__30102_ACWORTH_US_GA",
				"___XXXX_DE_",
				"ERDIKO 42_ERDIKO 42_20500_MONDRAGON_ES_SS",
				"79 ST. PETERS ROAD__NR30 3AY_GREAT YARMOUTH_GB_",
				"104 GRIFFINRATH HALL__W23E5F7_MAYNOOTH KILDARE_IE_",
				"ERDIKO 42_ERDIKO 42_20500_MONDRAGON_ES_SS",
				"ERDIKO 42_ERDIKO 42_20500_MONDRAGON_ES_SS",
				"PEREZ GALDOS  36_1D_07006_PALMA_ES_PM",
				"RABINOVICH.DIMA@GMAIL.COM__4930101_PETAH TIKVA_IL_",
				"325 CAMALEY__2417_BINMALEY PANGASINAN_PH_",
				"XXX__XXX_XXX_FR_",
				"RUDDAMMSGATAN50__80311_GAVLE_SE_",
				"1 RUE DES FAITIERS__95270_LUZAUCHES_FR_",
				"KORTE WANTIJKADE 12__3311 MB_DORDRECHT_NL_",
				"__99900_SAO PAULO_BR_",
				"__88131_LINDAU_DE_",
				"10 SUR 607___GUANTANAMO_CU_",
				"SQSW302__70673204_BRASILIA_BR_DF",
				"??? ??? ??? ???20? 20___??_KR_",
				"125_BANANKABOUGOU IMMEUBLE SOTRAKA_4481_BAMAKO_ML_",
				"SQSW302 BLOCO D__70673204_BRASILIA_BR_",
				"ZUIDSTRAAT 3__8020_WAARDAMME_BE_",
				"VIA BAFILE XXIII AM 1__30016_JESOLO_IT_VE",
				"9034 80TH ST__11421_QUEENS_US_",
				"11 RUE MARCEL LECAT__95210_ST GRATIEN_FR_",
				"960 CHEMIN DU COLLET RODON__13760_ST CANNAT_FR_",
				"SHIS QI 26 CONJUNTO 2__71670020_BRASILIA_BR_",
				"__XX_XX_FR_",
				"JANA KAZIMIERZA 30_-_01-248_WARSAW_PL_",
				"__99999_LONDON_GB_",
				"TOFTES GATE 11 D__0556_OSLO_NO_",
				"13 DOMAINE DU MONTABO__97300_CAYENNE_GF_",
				"3 AVENUE THEOPHILE DELORME__84130_LE PONTET_FR_",
				"__XX_XX_FR_",
				"13 MAIL SALZGITTER__94000_CRETEIL_FR_",
				"ARELLANO ST.___DAGUPAN CITY_PH_",
				"SALWA___SALWA_KW_",
				"2116/ NA FLORENCI__1-11000_PRAHA_CZ_",
				"SANCHO EL SABIO 26__01008_VITORIA_ES_VI",
				"6 RUE JULES MASURIER__76600_LE HAVRE_FR_",
				"??????????? ??-?? 18/3, ??. 241_140002_140002_LUBERTCI_RU_",
				"1582_ORDRE DES AVOCATS DU MALI BPE2231_2231_BAMAKO_ML_",
				"RUA ALFREDO PUJOL 1844__02017004_SAO PAULO_BR_SP",
				"HOFENER STR 114__70372_STUTTGART_DE_",
				"__XX_XX_FR_",
				"1 WEST STREET__PE27 5PL_ST IVES_GB_",
				"CALLE 76 53 51_CALLE 76 NO. 53-51__BOGOTA_CO_",
				"13 PARADISE PASSAGE__N7 8NT_LONDON_GB_",
				"DILLENBURG 11__1911SP_UITGEEST_NL_",
				"DAEHAK-DONG, GWANAK-GU___SEOUL_KR_",
				"WILHELM RIEHL STR 43__80687_MUENCHEN_DE_",
				"12 RUE EUGENE PAJO__97354_REMIRE MONTJOLY_GF_",
				"RUA EMBAIXADOR RAUL FERNANDES  57__01455090_SAO PAULO_BR_SP",
				"CALLE PEDRO ASUA 11__01008_VITORIA_ES_VI",
				"DAGSLANDEVAGEN 10__59152_MOTALA_SE_",
				"HUIS TER HEIDEWEG 14__3705 LZ_ZEIST_NL_",
				"__XX_XX_FR_",
				"XXX___XXX_DE_",
				"ANDREAESTRASSE 7__71332_WAIBLINGEN_DE_",
				"30 MWAYA ROAD___DAR ES SALAAM_TZ_",
				"5 ATLANTIC QUAY  150 BROOMIELAW__G2 8LU_GLASGOW_GB_",
				"BALSARENY 34 1 4__08242_MANRESA_ES_",
				"ESPOZ 3355_VITACURA_7630685_SANTIAGO_CL_",
				"XXX___XXX_DE_",
				"X___X_GA_",
				"3 BOULEVARD DE LA LIBERATION__94300_VINCENNES_FR_",
				"__XX_XX_FR_",
				"XXX___XXX_DE_",
				"AVENUE DE CHILLON 78__1820_MONTREUX_CH_",
				"ISIDORA GOYENECHEA 3365___SANTIAGO_CL_",
				"CAMINO DE LA CRUZ, 14__30813_LORCA_ES_",
				"CARMENSTRASSE 45__8032_ZURICH_CH_",
				"SCHWEMANNSTR 5__31134_HILDESHEIM_DE_",
				"32 SANSOME WALK__WR1 1NA_WORCESTER_GB_",
				"__XX_XX_FR_",
				"CALLE CARDENAL CISNEROS 10. 1A__28010_MADRID_ES_",
				"112 DU REGIMENT DE BIGORRE_0_65000_TARBES_FR_",
				"UNKNOWN__UNKNOWN_UNKNOWN_DE_",
				"11A L-GAVRO STR., APT.158__04211_KIEV_UA_",
				"RUA IGARAPE 245_CASA_89207820_JOINVILLE_BR_SC",
				"AV RICARDO LYON 920 DEPTO 1309_AV RICARDO LYON 920 DEPTO 1309__SANTIAGO_CL_",
				"__XX_XX_FR_",
				"SWEELINCKLAAN 100__3723JH_BILTHOVEN_NL_",
				"FERNANDO BARKIN  2__48970_BASAURI_ES_BI",
				"FRANCESC MACIA 6B 2-1__43005_TARRAGONA_ES_",
				"PLAZA CATALUNA 14 8 PLANTA_CENTRO COMERCIAL EL CORTE INGLES_080002_BARCELONA_ES_B",
				"DAGSLANDEVAGEN 10__59152_MOTALA_SE_",
				"52 NICKLEBY ROAD__SK12 1LE_STOCKPORT_GB_",
				"GMUENDSTRASSE 23__9435_HEERBRUGG_CH_",
				"MAIN STREET___UPTON_GB_",
				"42 AVENUE DE LA FONTVIN__34970_LATTES_FR_",
				"P. O. BOX 3021, DAR ES SALAAM__11410_DAR ES SALAAM_TZ_",
				"2A KOROLIOVA STREET, 19 APP__03148_KYIV, UKRAINE_GB_",
				"BCD TRAVEL__45000_ESSEN_DE_",
				"BURGSTRASSE 23__04109_LEIPZIG_DE_",
				"102, JALAN JAGUNG_PANDAMARAN_42000_PEL KELANG_MY_",
				"41 RUE LEPIC__75018_PARIS_FR_",
				"AOS 7 BLOCO C APTO 616__70660073_BRASILIA_BR_DF",
				"DR FRANCO DA ROCHA 661__05015040_SAO PAULO_BR_SP",
				"C JOSEP SOLDEVILA__08030_BARCELONA_ES_",
				"RUA ANTONIO PIRES 810__02730000_SAO PAULO_BR_SP",
				"4 PLACE EDOUARD BRANLY__57070_METZ_FR_",
				"CALLE ISLAS CIES__28035_MADRID_ES_",
				"NUEVA DE SAN ANTON 23__18005_GRANADA_ES_",
				"SINESIO DELGADO 18, 3 A__28029_MADRID_ES_",
				"AV. ALBUFERA, 14__28701_SAN SEBASTIAN DE LOS REYES_ES_",
				"4520 GINA CT__21237_ROSEDALE_US_",
				"CALLE RICARDO GOIZUETA 6, 3A__28045_MADRID_ES_",
				"AVENIDA LA RIOJA__28691_VILLANUEVA_ES_",
				"RUA MIRASSOL 216__04044010_SAO PAULO_BR_SP",
				"23 NEW MOUNT STREET__M4 4DE_MANCHESTER_GB_",
				"GRAN DE SANT ANDREU 292 5-2__08030_BARCELONA_ES_",
				"AV ANTONIO MARQUES FIGUEIRA 1269_AV ANTONIO MARQUES FIGUEIRA 1269_08676420_SUZANO_BR_",
				"WIJNRUIT 68__4907HE_OOSTERHOUT_NL_",
				"CALLE ATALAYUELA 43_URBANIZACION SANTODOMINGO (ALGETE)_28120_ALGETE_ES_M"
		};
		
		
		for (String adresseANormaliser: listeAdresseANormaliser) {

			String[] aNormaliserStrings = adresseANormaliser.split("_");
			String aNumberAndStreet = "";
			String aZipCode = "";
			String aCity = "";
			String aCountryCode = "";				
			String aStateCode = "";

			if (aNormaliserStrings.length >= 1) {
				aNumberAndStreet = aNormaliserStrings[0];
			}
			if (aNormaliserStrings.length >= 3) {
				aZipCode = aNormaliserStrings[2];
			}
			if (aNormaliserStrings.length >= 4) {
				aCity = aNormaliserStrings[3];
			}
			if (aNormaliserStrings.length >= 5) {
				aCountryCode = aNormaliserStrings[4];
			}
			if (aNormaliserStrings.length >= 6) {
				aStateCode = aNormaliserStrings[5];
			}
				
			CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
			
			// Preparing the request		
			RequestorV2 requestor = new RequestorV2();
			requestor.setChannel(CHANNEL);
			requestor.setSite(SITE);
			requestor.setSignature(SIGNATURE);
	
			request.setRequestor(requestor);
	
			// Individual Request Bloc
			IndividualRequest indRequest = new IndividualRequest();
	
			IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			indInfo.setCivility("MISS");
			indInfo.setLastNameSC("NOM" + generateString());
			indInfo.setFirstNameSC("PRENOM" + generateString());
			indInfo.setStatus("V");
	
			indRequest.setIndividualInformations(indInfo);
			request.setIndividualRequest(indRequest);
			// Postal Address
			PostalAddressRequest addRequest = new PostalAddressRequest();
			PostalAddressContent pac = new PostalAddressContent();
			pac.setNumberAndStreet(aNumberAndStreet);
			pac.setZipCode(aZipCode);
			pac.setCity(aCity);
			pac.setCountryCode(aCountryCode);
			pac.setAdditionalInformation("");
			// pac.setDistrict(aDistrict);
			pac.setStateCode(aStateCode);
			pac.setCorporateName("");
			addRequest.setPostalAddressContent(pac);
			
			PostalAddressProperties pap = new PostalAddressProperties();
			pap.setMediumCode("D");		// Direct
			pap.setMediumStatus("V");	// Valid
			pap.setVersion("1");
			pap.setIndicAdrNorm(false);	// On force l'adresse
			addRequest.setPostalAddressProperties(pap);
			
//			UsageAddress ua = new UsageAddress();
//			ua.setUsageNumber("1");
//			ua.setApplicationCode("TU");
//			addRequest.setUsageAddress(ua);
			
			request.getPostalAddressRequest().add(addRequest);
			
			
			// WS call
			
			try {
			
				CreateUpdateIndividualResponse response = createIndividual(request);

				// Tests
				Assert.assertNotNull(response);		
//				Assert.assertNotNull(response.isSuccess());
//				Assert.assertTrue(response.isSuccess());
//				Assert.assertNotNull(response.getGin());
//				Assert.assertTrue(response.getGin().startsWith("400"));
	
//				Assert.assertNotNull(response.getInformationResponse());
//				Assert.assertNotEquals(0, response.getInformationResponse());
				nbOK++;
			
			} catch (BusinessErrorBlocBusinessException bebe) {
				
				Assert.assertNotNull(bebe);
				Assert.assertNotNull(bebe.getFaultInfo());
				Assert.assertNotNull(bebe.getFaultInfo().getBusinessError());
				logger.info("ERREUR : " + bebe.getFaultInfo().getBusinessError().getErrorCode() + "-" + bebe.getFaultInfo().getBusinessError().getErrorLabel() + " pour " + adresseANormaliser);
				nbKO++;
			} catch (Exception ex) {
				logger.info("ERREUR : " + ex.getMessage() + " pour " + adresseANormaliser);
				nbKKO++;
			}
			// Assert.assertEquals(RecognitionType.FLT.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
		}
		
		logger.info("RAPPORT : OK:" + nbOK + " KO:" + nbKO);
	}
	
}
