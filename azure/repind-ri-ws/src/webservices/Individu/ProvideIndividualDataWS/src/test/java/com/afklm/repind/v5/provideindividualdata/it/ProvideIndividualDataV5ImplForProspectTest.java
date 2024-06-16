package com.afklm.repind.v5.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v5.provideindividualdata.ProvideIndividualDataV5Impl;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v5.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v5.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v5.siccommontype.Requestor;
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
public class ProvideIndividualDataV5ImplForProspectTest {

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "T412211";
	private static final String APP_CODE = "B2C";
	
	@Autowired
	@Qualifier("passenger_ProvideIndividualData-v05Bean")
	private ProvideIndividualDataV5Impl provideIndividualDataV5Impl;
	
	private Requestor initRequestor() {
		Requestor requestor = new Requestor();
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
		
		ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);

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
		
		ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);

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
		
		ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900025436742", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals("0497283644", response.getTelecomResponse().get(0).getTelecom().getPhoneNumber());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("loha2@testaf.fr", response.getEmailResponse().get(0).getEmail().getEmail());		
		
		
	}
	
	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecomAndEmail_Localization() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000001695");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");

		ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);

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
		Assert.assertEquals("MAN", response.getPreferencesResponse().getPreference().getPrefferedAirport());
	}

	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecomAndEmail_LocalizationPreferences() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000286401");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");

		ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);

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
		Assert.assertEquals("BCN", response.getPreferencesResponse().getPreference().getPrefferedAirport());
	}
}
