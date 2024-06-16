package com.afklm.repind.v6.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v6.provideindividualdata.ProvideIndividualDataV6Impl;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v6.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v6.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v6.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v6.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000418.v6.siccommontype.RequestorV2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProvideIndividualDataV6ImplForProspectTest {

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "T412211";
	private static final String APP_CODE = "B2C";
	
	@Autowired
	@Qualifier("passenger_ProvideIndividualData-v06Bean")
	private ProvideIndividualDataV6Impl provideIndividualDataV6Impl;
	
	private RequestorV2 initRequestor() {
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setMatricule(MATRICULE);
		requestor.setApplicationCode(APP_CODE);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		
		return requestor;
	}
	
	@Test
	public void provideIndividualDataByGIN_ProspectOnly() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000002415");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900000002415", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("13418202@qq.com", response.getEmailResponse().get(0).getEmail().getEmail());
	}

	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecom() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900007330530");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900007330530", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals("0621411194", response.getTelecomResponse().get(0).getTelecom().getPhoneNumber());
	}

	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecomAndEmail() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900025436742");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900025436742", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals("0497283644", response.getTelecomResponse().get(0).getTelecom().getPhoneNumber());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("loha2@testaf.fr", response.getEmailResponse().get(0).getEmail().getEmail());		
	}

	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecomAndEmailAndAddress() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900025436742");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900025436742", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals("0497283644", response.getTelecomResponse().get(0).getTelecom().getPhoneNumber());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("loha2@testaf.fr", response.getEmailResponse().get(0).getEmail().getEmail());
		
		Assert.assertNotNull(response.getLocalizationResponse());
		Assert.assertEquals("FR", response.getLocalizationResponse().get(0).getLocalization().getCountryCode());
	}
	
	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecomAndEmailAndAddress_Bis() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900023138655");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900023138655", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals("0622334455", response.getTelecomResponse().get(0).getTelecom().getPhoneNumber());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("dcanasa@gmail.com", response.getEmailResponse().get(0).getEmail().getEmail());
		
		Assert.assertNotNull(response.getLocalizationResponse());
		Assert.assertEquals("28 RUE DE BERRY", response.getLocalizationResponse().get(0).getLocalization().getPostalAddress());
		Assert.assertEquals("75008", response.getLocalizationResponse().get(0).getLocalization().getZipCode());
		Assert.assertEquals("PARIS", response.getLocalizationResponse().get(0).getLocalization().getCity());
		Assert.assertEquals("FR", response.getLocalizationResponse().get(0).getLocalization().getCountryCode());
	}

	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecomAndEmailAndAddressAndCommPref() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900023138655");
		request.setOption("GIN");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900023138655", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals("0622334455", response.getTelecomResponse().get(0).getTelecom().getPhoneNumber());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("dcanasa@gmail.com", response.getEmailResponse().get(0).getEmail().getEmail());
		
		Assert.assertNotNull(response.getLocalizationResponse());
		Assert.assertEquals("28 RUE DE BERRY", response.getLocalizationResponse().get(0).getLocalization().getPostalAddress());
		Assert.assertEquals("75008", response.getLocalizationResponse().get(0).getLocalization().getZipCode());
		Assert.assertEquals("PARIS", response.getLocalizationResponse().get(0).getLocalization().getCity());
		Assert.assertEquals("FR", response.getLocalizationResponse().get(0).getLocalization().getCountryCode());
		
		Assert.assertNotNull(response.getCommunicationPreferencesResponse());
		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0));
		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences());
		Assert.assertEquals("N", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getCommunicationGroupeType());
		Assert.assertEquals("AF", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getCommunicationType());		
		Assert.assertEquals("S", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getDomain());
		Assert.assertEquals("N", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getOptIn());

		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage());
		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0));
		Assert.assertEquals("FR", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMarket());
		Assert.assertEquals("N", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getOptIn());
	}
	
	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecomAndEmailAndAddressAndCommPrefAndMarket() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900023138655");
		request.setOption("GIN");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900023138655", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals("0622334455", response.getTelecomResponse().get(0).getTelecom().getPhoneNumber());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("dcanasa@gmail.com", response.getEmailResponse().get(0).getEmail().getEmail());
		
		Assert.assertNotNull(response.getLocalizationResponse());
		Assert.assertEquals("28 RUE DE BERRY", response.getLocalizationResponse().get(0).getLocalization().getPostalAddress());
		Assert.assertEquals("75008", response.getLocalizationResponse().get(0).getLocalization().getZipCode());
		Assert.assertEquals("PARIS", response.getLocalizationResponse().get(0).getLocalization().getCity());
		Assert.assertEquals("FR", response.getLocalizationResponse().get(0).getLocalization().getCountryCode());
		
		Assert.assertNotNull(response.getCommunicationPreferencesResponse());
		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0));
		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences());
		Assert.assertEquals("N", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getCommunicationGroupeType());
		Assert.assertEquals("AF", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getCommunicationType());		
		Assert.assertEquals("S", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getDomain());
		Assert.assertEquals("N", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getOptIn());
		
		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage());
		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0));
		Assert.assertEquals("FR", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMarket());
		Assert.assertEquals("N", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getOptIn());
//		Assert.assertEquals(LanguageCodesEnum.FR, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getLanguage());

		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia());
		Assert.assertEquals(null, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia().getMedia1());
		Assert.assertEquals(null, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia().getMedia2());
		Assert.assertEquals(null, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia().getMedia3());
		Assert.assertEquals(null, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia().getMedia4());
		Assert.assertEquals(null, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia().getMedia5());
	}
	
	
	// Test Prefered Airport
	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithPreferedAirport() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000005616");
		request.setOption("GIN");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900000005616", response.getIndividualResponse().getIndividualInformations().getIdentifier());
//		Assert.assertNotNull(response.getTelecomResponse());
//		Assert.assertEquals("0622334455", response.getTelecomResponse().get(0).getTelecom().getPhoneNumber());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("178430759@qq.com", response.getEmailResponse().get(0).getEmail().getEmail());
		
		Assert.assertNotNull(response.getLocalizationResponse());
		Assert.assertNull(response.getLocalizationResponse().get(0).getLocalization().getPostalAddress());
		Assert.assertNull(response.getLocalizationResponse().get(0).getLocalization().getZipCode());
		Assert.assertNull(response.getLocalizationResponse().get(0).getLocalization().getCity());
		Assert.assertEquals("CN", response.getLocalizationResponse().get(0).getLocalization().getCountryCode());
		
		Assert.assertNotNull(response.getCommunicationPreferencesResponse());
		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0));
		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences());
		Assert.assertEquals("N", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getCommunicationGroupeType());
		Assert.assertEquals("AF", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getCommunicationType());		
		Assert.assertEquals("S", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getDomain());
		Assert.assertEquals("Y", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getOptIn());
		
		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage());
		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0));
		Assert.assertEquals("CN", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMarket());
		Assert.assertEquals("Y", response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getOptIn());
		Assert.assertEquals(LanguageCodesEnum.ZH, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getLanguage());

		Assert.assertNotNull(response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia());
		Assert.assertEquals(null, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia().getMedia1());
		Assert.assertEquals(null, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia().getMedia2());
		Assert.assertEquals(null, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia().getMedia3());
		Assert.assertEquals(null, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia().getMedia4());
		Assert.assertEquals(null, response.getCommunicationPreferencesResponse().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMedia().getMedia5());
		
		Assert.assertNotNull(response.getPreferencesResponse());
		Assert.assertNotNull(response.getPreferencesResponse().getPreference());
		Assert.assertNotNull(response.getPreferencesResponse().getPreference().get(0));
//		Assert.assertNotNull(response.getPreferencesResponse().getPreference().get(0).getTypePreference());
//		Assert.assertEquals("TPC", response.getPreferencesResponse().getPreference().get(0).getTypePreference());
		
		Assert.assertNotNull(response.getPreferencesResponse().getPreference().get(0).getPreferenceData());
		Assert.assertNotNull(response.getPreferencesResponse().getPreference().get(0).getPreferenceData().get(0));		
		Assert.assertEquals("preferredAirport", response.getPreferencesResponse().getPreference().get(0).getPreferenceData().get(0).getKey());
		Assert.assertEquals("PEK", response.getPreferencesResponse().getPreference().get(0).getPreferenceData().get(0).getValue());
		
	}

	
	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecomAndEmail_Localization() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000001695");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");

		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900000001695", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("123@uclan.ac.uk", response.getEmailResponse().get(0).getEmail().getEmail());

		Assert.assertNotNull(response.getLocalizationResponse());
		Assert.assertEquals("CN", response.getLocalizationResponse().get(0).getLocalization().getCountryCode());
		Assert.assertEquals("V", response.getLocalizationResponse().get(0).getLocalization().getMediumStatus());
		Assert.assertEquals("D", response.getLocalizationResponse().get(0).getLocalization().getMediumCode());

		Assert.assertNotNull(response.getPreferencesResponse());
//		Assert.assertEquals("TPC", response.getPreferencesResponse().getPreference().get(0).getTypePreference());

//		if ("TYPE".equalsIgnoreCase(response.getPreferencesResponse().getPreference().get(0).getPreferenceData().get(0).getKey())) {
//			Assert.assertEquals("WWP", response.getPreferencesResponse().getPreference().get(0).getPreferenceData().get(0).getValue());	
//		} else {
			Assert.assertEquals("preferredAirport", response.getPreferencesResponse().getPreference().get(0).getPreferenceData().get(0).getKey());
			Assert.assertEquals("MAN", response.getPreferencesResponse().getPreference().get(0).getPreferenceData().get(0).getValue());
//		}		
	}

	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecomAndEmail_LocalizationPreferences() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000286401");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");

		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900000286401", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("antonia_novel@hotmail.com", response.getEmailResponse().get(0).getEmail().getEmail());

		Assert.assertNotNull(response.getLocalizationResponse());
		Assert.assertEquals("ES", response.getLocalizationResponse().get(0).getLocalization().getCountryCode());
		Assert.assertEquals("V", response.getLocalizationResponse().get(0).getLocalization().getMediumStatus());
		Assert.assertEquals("D", response.getLocalizationResponse().get(0).getLocalization().getMediumCode());

		Assert.assertNotNull(response.getPreferencesResponse());
//		Assert.assertEquals("TPC", response.getPreferencesResponse().getPreference().get(0).getTypePreference());
		
//		if ("TYPE".equalsIgnoreCase(response.getPreferencesResponse().getPreference().get(0).getPreferenceData().get(0).getKey())) {
//			Assert.assertEquals("WWP", response.getPreferencesResponse().getPreference().get(0).getPreferenceData().get(0).getValue());	
//		} else {
			Assert.assertEquals("preferredAirport", response.getPreferencesResponse().getPreference().get(0).getPreferenceData().get(0).getKey());
			Assert.assertEquals("BCN", response.getPreferencesResponse().getPreference().get(0).getPreferenceData().get(0).getValue());
//		}		
	}

}
