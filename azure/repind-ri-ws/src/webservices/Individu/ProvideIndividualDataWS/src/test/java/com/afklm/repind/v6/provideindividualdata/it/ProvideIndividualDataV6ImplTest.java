package com.afklm.repind.v6.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v6.provideindividualdata.ProvideIndividualDataV6Impl;
import com.afklm.repind.v6.provideindividualdata.helpers.PaymentPreferencesDataHelper;
import com.afklm.repind.v6.provideindividualdata.type.ScopesToProvideEnum;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v6.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v6.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v6.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v6.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000418.v6.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000418.v6.sicindividutype.ContractData;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ContractDataKeyEnum;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProvideIndividualDataV6ImplTest {

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T730890";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "T730890";
	private static final String APP_CODE = "B2C";
	
	@Autowired
	private DelegationDataDS delegationDataDS;
	
	@Autowired
	private IndividuDS individuDS;
	
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
	
	/**
	 * Create a provide's request with a individual of type F in our database
	 * @result this method will be return a BusinessErrorCode (the individual must not appear in the response).
	 *         BusinessErrorCode <code>ERROR_001</code>
	 */
	@Test
	@Transactional
	public void provideIndividualData_provideIndividualDataByIdentifierStatutF(){
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		//TODO : create a individu and update with statut F
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("110000028901");
		request.setOption("GIN");
		
		try{
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			System.out.println(e.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode().toString(), BusinessErrorCodeEnum.ERROR_001.toString());
		} catch (SystemException e) {
			Assert.fail();
		}
		
	}
	
	@Test
	public void provideIndividualDataByGIN_IndividuOnlyTargetA() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// Init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400401504483");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		// Telecoms
		Assert.assertEquals("Telecom number is not the expected", 3, response.getTelecomResponse().size());
		// Emails
		Assert.assertEquals("Emails number is not the expected", 2, response.getEmailResponse().size());
		// Postal address
		Assert.assertEquals("Postal addresses number is not the expected", 2, response.getPostalAddressResponse().size());
		// Individual infos
		Assert.assertEquals("400401504483", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("I"));
	}

	@Ignore
	@Test
	public void provideIndividualDataByGIN_IndividuOnlyTargetEWithoutE() throws SystemException {
		
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		request.setRequestor(initRequestor());
		request.setIdentificationNumber("000000004136");
		request.setOption("GIN");
//		request.setPopulationTargeted("E");
		request.getScopeToProvide().add("ALL");
		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			Assert.fail("Should not be found when population targeted is E");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
	}
	
	@Test
	public void provideIndividualDataByGIN_IndividuOnlyTargetSWithS() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400446583654");
		request.setOption("GIN");
//		request.setPopulationTargeted("S");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("400446583654", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getSocialNetworkDataResponse());
		Assert.assertEquals("AFKL_201412011616__guid_NClseOECdVrfyCPUhTW7-bRN9F1-FRxU_ne3", response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkId());
	}
	
	@Test
	public void provideIndividualDataByGIN_IndividuOnlyTargetSWithTwoS() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400446664250");
		request.setOption("GIN");
//		request.setPopulationTargeted("S");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("400446664250", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals("AFKL_201412020951__guid_N47GAhgXSgljExBtnHsGJz1bUZOgnmlZdJfx", response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkId());
		Assert.assertEquals(1, response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkData().size());
	}
	
	//@Ignore
	@Test
	public void provideIndividualDataByGIN_IndividuOnlyTargetI() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400401504483");
		request.setOption("GIN");
//		request.setPopulationTargeted("I");
		request.getScopeToProvide().add("IDENTIFICATION");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull("400401504483", response.getIndividualResponse().getIndividualInformations().getIdentifier());
	}
	
	@Test
	public void provideIndividualDataByGigya_IndividuOnlyTargetI() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("AFKL_201412181532__guid_IRRBKDf_jeH-qXuQ-APC0yOdpIIhHWq1ZKnJ");
		request.setOption("GID");
//		request.setPopulationTargeted("I");
		request.getScopeToProvide().add("IDENTIFICATION");
		request.getScopeToProvide().add("SOCIAL NETWORK");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("400347650311", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals("AFKL_201412181532__guid_IRRBKDf_jeH-qXuQ-APC0yOdpIIhHWq1ZKnJ", response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkId());
		Assert.assertEquals(1, response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkData().size());
	}

	//@Ignore
	//@Test
	public void provideIndividualDataByGIN_IndividuOnlyTargetT() throws SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400401504483");
		request.setOption("GIN");
//		request.setPopulationTargeted("T");
		request.getScopeToProvide().add("ALL");
		
		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			Assert.fail("Should not be found when targeted population is T");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
	}
	
	@Test
	public void provideIndividualDataByGIN_ProspectOnlyTargetA() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000001509");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900000001509", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("0214kathy@163.com", response.getEmailResponse().get(0).getEmail().getEmail());
	}
	
	@Test
	public void provideIndividualDataByGIN_ProspectOnlyTargetAStatusX() throws SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000000186");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");
		
		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			Assert.fail("Should not be found when targeted population is T");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
	}
	
	@Test
	public void provideIndividualDataByGIN_ProspectOnlyTargetT() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000001509");
		request.setOption("GIN");
//		request.setPopulationTargeted("T");
		request.getScopeToProvide().add("ALL");
	
//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			Assert.assertEquals("900000001509", response.getIndividualResponse().getIndividualInformations().getIdentifier());
			Assert.assertTrue(!response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("T"));
			
			
/*			Assert.fail("Should not be found when targeted population is T");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
*/		
	}
	
	@Test
	public void provideIndividualDataByGIN_ProspectOnlyTargetW() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000002649");
		request.setOption("GIN");
//		request.setPopulationTargeted("W");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900000002649", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("13254181@qq.com", response.getEmailResponse().get(0).getEmail().getEmail());
	}
	
	@Test
	public void provideIndividualDataByGIN_ProspectTravelerTargetA() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("910000000000");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("EMAIL");
		request.getScopeToProvide().add("TRAVELER");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("910000000000", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("testtraveler@test.fr", response.getEmailResponse().get(0).getEmail().getEmail());
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertTrue(response.getContractResponse().size() > 0);
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContractData());
		// REPIND-663 : On a ajoute le contrat de type T
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertEquals("TR", response.getContractResponse().get(0).getContract().getProductType());
	}
	
	@Test
	public void provideIndividualDataByGIN_ProspectTravelerTargetP() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000000831");
		request.setOption("GIN");
//		request.setPopulationTargeted("P");
		request.getScopeToProvide().add("TRAVELER");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900000000831", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertTrue(response.getContractResponse().size() > 0);
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContractData());
		for(ContractData contractDataLoop : response.getContractResponse().get(0).getContractData()) {
			if(ContractDataKeyEnum.MATCHINGRECOGNITION.getKey().equalsIgnoreCase(contractDataLoop.getKey())) {
				Assert.assertEquals("FLE", contractDataLoop.getValue());
			}
		}
		// REPIND-663 : On a ajoute le contrat de type T
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertEquals("TR", response.getContractResponse().get(0).getContract().getProductType());		
	}
	
	@Test
	public void provideIndividualDataByGIN_ProspectTravelerTargetW() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000000831");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("EMAIL");
		request.getScopeToProvide().add("TRAVELER");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900000000831", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("1021009613@qq.com", response.getEmailResponse().get(0).getEmail().getEmail());
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertTrue(response.getContractResponse().size() > 0);
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContractData());
		for(ContractData contractDataLoop : response.getContractResponse().get(0).getContractData()) {
			if(ContractDataKeyEnum.MATCHINGRECOGNITION.getKey().equalsIgnoreCase(contractDataLoop.getKey())) {
				Assert.assertEquals("FLE", contractDataLoop.getValue());
			}
		}
		// REPIND-663 : On a ajoute le contrat de type T
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertEquals("TR", response.getContractResponse().get(0).getContract().getProductType());
	}
	
	@Test
	public void provideIndividualDataByGigya_SocialOnlyTargetS() throws BusinessErrorBlocBusinessException, SystemException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("AFKL_201412011654__guid_S2NL6YOQyXqHKaMSoCm3KAeAwS7ZzrltxQNX");
		request.setOption("GID");
//		request.setPopulationTargeted("S");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("400446060426", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals("AFKL_201412011654__guid_S2NL6YOQyXqHKaMSoCm3KAeAwS7ZzrltxQNX", response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkId());
//		Assert.assertEquals(1, response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkData().size());

	}
	
	@Test
	public void provideIndividualDataByGIN_SocialOnlyTargetE() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400345452716");
		request.setOption("GIN");
//		request.setPopulationTargeted("E");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("400345452716", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals("AFKL_201412161004__guid_j4Qh7Otv2mD912B8a1Thzg==", response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkId());
		Assert.assertEquals(1, response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkData().size());
	}

	@Test
	@Ignore
	public void provideIndividualDataByGIN_SocialOnlyTargetS() throws BusinessErrorBlocBusinessException, SystemException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("920000001276");
		request.setOption("GIN");
//		request.setPopulationTargeted("S");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("920000001276", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals("NumeroIDGIGYA_AFKL_0441675536_gwnaMHdjoC", response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkId());
		Assert.assertEquals(1, response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkData().size());

	}

	@Test
	public void provideIndividualDataByGIN_SocialOnlyTargetT() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("910000003355");
		request.setOption("GIN");
//		request.setPopulationTargeted("T");
		request.getScopeToProvide().add("ALL");
		
//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			Assert.assertEquals("910000003355", response.getIndividualResponse().getIndividualInformations().getIdentifier());
//			Assert.assertTrue(!response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("T"));
			
/*			Assert.fail("Should not be found when targeted population is T");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
*/	}
	
	@Test
	public void provideIndividualDataByGIN_TravelerOnlyTargetA() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("910000000485");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("TRAVELER");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("910000000485", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getContractResponse());
 		Assert.assertTrue(response.getContractResponse().size() > 0);
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContractData());
		// REPIND-663 : On a ajoute le contrat de type T
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertEquals("TR", response.getContractResponse().get(0).getContract().getProductType());		
	}
	
	@Test
	public void provideIndividualDataByGIN_TravelerOnlyTargetAScopeI() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("910000003300");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("IDENTIFICATION");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("910000003300", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertTrue(response.getContractResponse().size() == 0);
	}
	
	@Test
	public void provideIndividualDataByGIN_TravelerOnlyTargetI() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("910000000065");
		request.setOption("GIN");
//		request.setPopulationTargeted("I");
		request.getScopeToProvide().add("TRAVELER");
		
//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			Assert.assertEquals("910000000065", response.getIndividualResponse().getIndividualInformations().getIdentifier());
//			Assert.assertTrue(!response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("I"));
			
/*			Assert.fail("Should not be found when targeted population is T");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
*/
		// REPIND-663 : On a ajoute le contrat de type T
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertEquals("TR", response.getContractResponse().get(0).getContract().getProductType());		
	}
	
	@Test
	public void provideIndividualDataByGIN_TravelerOnlyTargetT() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("920000009142");
		request.setOption("GIN");
//		request.setPopulationTargeted("T");
		request.getScopeToProvide().add("TRAVELER");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("920000009142", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertTrue(response.getContractResponse().size() > 0);
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContractData());
		
		// REPIND-663 : On a ajoute le contrat de type T
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertEquals("TR", response.getContractResponse().get(0).getContract().getProductType());				
	}
	
	@Test
	public void provideIndividualDataByGIN_TravelerOnlyTargetTScopeI() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("910000000566");
		request.setOption("GIN");
//		request.setPopulationTargeted("T");
		request.getScopeToProvide().add("IDENTIFICATION");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("910000000566", response.getIndividualResponse().getIndividualInformations().getIdentifier());		Assert.assertNotNull(response.getContractResponse());
		Assert.assertTrue(response.getContractResponse().size() == 0);
	}
	
	@Test
	public void provideIndividualDataByGIN_TravelerOnlyTargetTScopeT() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("920000003483");
		request.setOption("GIN");
//		request.setPopulationTargeted("T");
		request.getScopeToProvide().add("TRAVELER");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertNull(response.getIndividualResponse().getIndividualProfil());
		Assert.assertNull(response.getIndividualResponse().getNormalizedName());
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertTrue(response.getContractResponse().size() > 0);
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContractData());
		
		// REPIND-663 : On a ajoute le contrat de type T
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertEquals("TR", response.getContractResponse().get(0).getContract().getProductType());		
	}
	
	@Test
	//@Ignore
	public void provideIndividuaDataByGIN_TravelerOnlyTargetTWithoutTravelerRole() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("910000004416");
		request.setOption("GIN");
//		request.setPopulationTargeted("T");
		request.getScopeToProvide().add("ALL");
		
//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			Assert.assertEquals("910000004416", response.getIndividualResponse().getIndividualInformations().getIdentifier());
			Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("T"));
			
			
/*			Assert.fail("Should not be found when targeted population is T and has no role traveler");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
*/		
	}
	
	@Test
	public void provideIndividualDataByGIN_TravelerOnlyTargetTWithEmail() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("910000000065");
		request.setOption("GIN");
//		request.setPopulationTargeted("T");
		request.getScopeToProvide().add("EMAIL");
		request.getScopeToProvide().add("TRAVELER");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("910000000065", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals("test010101@gmel.fr", response.getEmailResponse().get(0).getEmail().getEmail());
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertTrue(response.getContractResponse().size() > 0);
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContractData());
		
		// REPIND-663 : On a ajoute le contrat de type T
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertEquals("TR", response.getContractResponse().get(0).getContract().getProductType());				
	}
	
	@Test
	public void provideIndividualDataByGIN_TravelerOnlyTargetTWithTelecom() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("910000000065");
		request.setOption("GIN");
//		request.setPopulationTargeted("T");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("910000000065", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals("0405060708", response.getTelecomResponse().get(0).getTelecom().getPhoneNumber());
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertTrue(response.getContractResponse().size() > 0);
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContractData());

		// REPIND-663 : On a ajoute le contrat de type T
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertEquals("TR", response.getContractResponse().get(0).getContract().getProductType());				
	}

	@Test
	public void provideIndividualDataByGIN_IndividuITargetI() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400272036182");
		request.setOption("GIN");
//		request.setPopulationTargeted("I");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("400272036182", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals("IW", response.getIndividualResponse().getIndividualInformations().getPopulationType());
	}

	@Test
	public void provideIndividualDataByGIN_IndividuITargetT() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400272036182");
		request.setOption("GIN");
//		request.setPopulationTargeted("T");
		request.getScopeToProvide().add("ALL");
		
//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			Assert.assertEquals("400272036182", response.getIndividualResponse().getIndividualInformations().getIdentifier());
			Assert.assertTrue(!response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("T"));
			
/*			Assert.fail("Should not be found when targeted population is T and has no role traveler");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
*/	}
	
	@Test
	public void provideIndividualDataByGIN_IndividuITargetS() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400272036182");
		request.setOption("GIN");
//		request.setPopulationTargeted("S");
		request.getScopeToProvide().add("ALL");
		
//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			Assert.assertEquals("400272036182", response.getIndividualResponse().getIndividualInformations().getIdentifier());
			Assert.assertTrue(!response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("T"));
			
/*			Assert.fail("Should not be found when targeted population is S and has no social network");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
*/
	}
	
	@Test
	@Ignore
	public void provideIndividualDataByGIN_IndividuITargetW() throws SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400272036182");
		request.setOption("GIN");
//		request.setPopulationTargeted("W");
		request.getScopeToProvide().add("ALL");
		
		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			Assert.fail("Should not be found when targeted population is W and has no comm pref");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}

	}
	
	@Test
	public void provideIndividualDataByGIN_IndividuITargetE() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400272036182");
		request.setOption("GIN");
//		request.setPopulationTargeted("E");
		request.getScopeToProvide().add("ALL");
		
//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			Assert.assertEquals("400272036182", response.getIndividualResponse().getIndividualInformations().getIdentifier());
			Assert.assertTrue(!response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("E"));
			
/*			Assert.fail("Should not be found when targeted population is E and has no external identifier");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
*/	}
	
	@Test
	public void provideIndividualDataByGIN_IndividuITTargetI() throws SystemException, BusinessErrorBlocBusinessException {
		
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400474550021");
		request.setOption("GIN");
//		request.setPopulationTargeted("I");
		request.getScopeToProvide().add("IDENTIFICATION");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("400474550021", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("I"));
	}
	
	@Test
	public void provideIndividualDataByGIN_IndividualITTargetT() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400474550021");
		request.setOption("GIN");
//		request.setPopulationTargeted("T");
		request.getScopeToProvide().add("IDENTIFICATION");
		
//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			Assert.assertEquals("400474550021", response.getIndividualResponse().getIndividualInformations().getIdentifier());
			Assert.assertTrue(!response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("T"));
						
			
/*			Assert.fail("Should not be found when targeted population is T and has no travel data");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
*/
	}
	
	@Test
	public void provideIndividualDataByGIN_IndiviuITTargetS() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400474550021");
		request.setOption("GIN");
//		request.setPopulationTargeted("S");
		request.getScopeToProvide().add("ALL");
		
//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			Assert.assertEquals("400474550021", response.getIndividualResponse().getIndividualInformations().getIdentifier());
			Assert.assertTrue(!response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("S"));
			
/*			Assert.fail("Should not be found when targeted population is S and has no social network");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
*/
	}

	@Test
	@Ignore
	public void provideIndividualDataByGIN_IndiviuITTargetW() throws SystemException {
			ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
			
			request.setRequestor(initRequestor());
			request.setIdentificationNumber("400474550021");
			request.setOption("GIN");
//			request.setPopulationTargeted("W");
			request.getScopeToProvide().add("ALL");
			
			try {
				ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
				Assert.fail("Should not be found when targeted population is W and has no comm pref");
			} catch (BusinessErrorBlocBusinessException bException) {
				Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
			}

	}

	@Test
	public void provideIndividualDataByGIN_IndiviuITTargetE() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400474550021");
		request.setOption("GIN");
//		request.setPopulationTargeted("E");
		request.getScopeToProvide().add("ALL");
		
//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			Assert.assertEquals("400474550021", response.getIndividualResponse().getIndividualInformations().getIdentifier());
			Assert.assertTrue(!response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("E"));
			
			
/*			Assert.fail("Should not be found when targeted population is E and has no external id");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
*/	}

	@Test
	public void provideIndividualDataByGIGYA_SocialNetworkData() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("AFKL_201412130137__guid_zWgbVBfQcPcXuXXIMWvpIlfx_Q0AlnbdpIqv");
		request.setOption("GID");
//		request.setPopulationTargeted("S");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("400447556783", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("E"));
	}

	@Test
	public void provideIndividualDataByGIGYA_ExternalIdentifier() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("AFKL_201607072142__guid_oz6rAt23WEzdwPIG5JdI65WAJkw4Sjgkk9zD");
		request.setOption("GID");
//		request.setPopulationTargeted("E");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("700000000003", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("E"));
	}

	@Test
	public void provideIndividualDataByGIN_ExternalIdentifierAndSocialNetworkData() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400400030644");
		request.setOption("GIN");
//		request.setPopulationTargeted("E");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("400400030644", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getExternalIdentifierResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0));
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifier());
		Assert.assertEquals("AFKL_201607072144__guid_oz7rAt24WEzdwPIG6JdI67WAJkw5Sjgkk1zD", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getIdentifier());
		
		Assert.assertNotNull(response.getSocialNetworkDataResponse());
		Assert.assertNotNull(response.getSocialNetworkDataResponse().getSocialNetworkTypeLight());
		Assert.assertNotNull(response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkId());
		Assert.assertEquals("AFKL_201504250118__guid_bKZUHEhlvJNJG4mzElEnb1OSdYs1i-ES3bgZ", response.getSocialNetworkDataResponse().getSocialNetworkTypeLight().getSocialNetworkId());

		Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("E"));
	}
	
	@Test
	public void provideIndividualDataByGigya_NULL() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("AFKL_201607072144__guid_oz7rAt24WEzdwPIG6JdI67WAJkw5Sjgkk1zD");
		request.setOption("GID");
//		request.setPopulationTargeted("I");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("400400030644", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		
		Assert.assertNotNull(response.getExternalIdentifierResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0));	// One GIGYA
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifier());
		
		Assert.assertEquals("GIGYA_ID", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getType());
		Assert.assertEquals("AFKL_201607072144__guid_oz7rAt24WEzdwPIG6JdI67WAJkw5Sjgkk1zD", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getIdentifier());
		
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse());
		Assert.assertEquals(0, response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().size());
	}
	
	@Test
	public void provideIndividualDataByGIN_ExternalIdentifier_FACEBOOKID() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("700000000004");
		request.setOption("GIN");
//		request.setPopulationTargeted("E");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("700000000004", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getExternalIdentifierResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0));
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifier());
		Assert.assertEquals("FACEBOOK_ID", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getType());
		Assert.assertEquals("FACEBOOK_ID_Exempledqsqdhqhslkdhsqui", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getIdentifier());

		Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("E"));
	}

	@Test
	public void provideIndividualDataByGIN_ExternalIdentifier_TWITTERID() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("700000000005");
		request.setOption("GIN");
//		request.setPopulationTargeted("E");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("700000000005", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getExternalIdentifierResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0));
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifier());
		Assert.assertEquals("FACEBOOK_ID", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getType());
		Assert.assertEquals("TWITTER_ID_ExempleDajbddpmqddyg", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getIdentifier());

		Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("E"));
	}

	@Test
	public void provideIndividualDataByGIN_ExternalIdentifier_GIGYAID() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("700000000006");
		request.setOption("GIN");
//		request.setPopulationTargeted("E");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("700000000006", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getExternalIdentifierResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0));
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifier());
		Assert.assertEquals("GIGYA_ID", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getType());
		Assert.assertEquals("AFKL_201607082143__guid_oz7rAt34WEzdwPIG6JdI76WAJkw5Sjgkk0zD", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getIdentifier());

		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0));
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData());
		Assert.assertEquals("USED_BY_KL", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData().getKey());
		Assert.assertEquals("Y", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData().getValue());
		
		Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("E"));
	}
	
	@Test
	public void provideIndividualDataByGigya_KL() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("AFKL_201607082143__guid_oz7rAt34WEzdwPIG6JdI76WAJkw5Sjgkk0zD");
		request.setOption("GID");
//		request.setPopulationTargeted("I");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("700000000006", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		
		Assert.assertNotNull(response.getExternalIdentifierResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0));	// One GIGYA
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifier());
		
		Assert.assertEquals("GIGYA_ID", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getType());
		Assert.assertEquals("AFKL_201607082143__guid_oz7rAt34WEzdwPIG6JdI76WAJkw5Sjgkk0zD", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getIdentifier());
		
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0));
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData());
		
		Assert.assertEquals("USED_BY_KL", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData().getKey());
		Assert.assertEquals("Y", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData().getValue());
	}
	
	@Test
	public void provideIndividualDataByGIN_ContractAccount_Bug_FirstLastName() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400311637013");
		request.setOption("GIN");
//		request.setPopulationTargeted("E");
		request.getScopeToProvide().add("IDENTIFICATION");
		request.getScopeToProvide().add("CONTRACT");
		request.getScopeToProvide().add("ACCOUNT");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());		
		Assert.assertEquals("400311637013", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals("COMPB", response.getIndividualResponse().getNormalizedName().getFirstName());
		Assert.assertEquals("LAMIPARTB", response.getIndividualResponse().getNormalizedName().getLastName());
	}
	
	@Test
	public void provideIndividualDataByFACEBOOKID() throws SystemException, BusinessErrorBlocBusinessException {
	ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("700000000004");
		request.setOption("GIN");
//		request.setPopulationTargeted("E");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("700000000004", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getExternalIdentifierResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0));
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifier());
		Assert.assertEquals("FACEBOOK_ID", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getType());
		Assert.assertEquals("FACEBOOK_ID_Exempledqsqdhqhslkdhsqui", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getIdentifier());

		Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("E"));
	}

	@Test
	public void provideIndividualDataByGigya_AFKL() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("NumeroIDGIGYA_AFKL_0421990258_nYPHCVtsGa");
		request.setOption("GID");
//		request.setPopulationTargeted("I");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("920000001560", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		
		Assert.assertNotNull(response.getExternalIdentifierResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0));	// One GIGYA
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifier());
		
		Assert.assertEquals("GIGYA_ID", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getType());
		Assert.assertEquals("NumeroIDGIGYA_AFKL_0421990258_nYPHCVtsGa", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getIdentifier());
		
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0));
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData());

//		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(1));
//		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(1).getExternalIdentifierData());		
		
		if ("USED_BY_AF".equals(response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData().getKey())) {
			Assert.assertEquals("USED_BY_AF", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData().getKey());
			Assert.assertEquals("Y", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData().getValue());
//			Assert.assertEquals("USED_BY_KL", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(1).getExternalIdentifierData().getKey());
//			Assert.assertEquals("Y", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(1).getExternalIdentifierData().getValue());
		} else {
			Assert.assertEquals("USED_BY_KL", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData().getKey());
			Assert.assertEquals("Y", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(0).getExternalIdentifierData().getValue());
//			Assert.assertEquals("USED_BY_AF", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(1).getExternalIdentifierData().getKey());
//			Assert.assertEquals("Y", response.getExternalIdentifierResponse().get(0).getExternalIdentifierDataResponse().get(1).getExternalIdentifierData().getValue());
		}
	}
	
	@Test
	public void provideIndividualDataByTwitter_1() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("NumeroIDTWITTER_123456789101111");
		request.setOption("TWT");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("920000002606", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		
		Assert.assertNotNull(response.getExternalIdentifierResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0));
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifier());
		
		Assert.assertEquals("TWITTER_ID", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getType());
		Assert.assertEquals("NumeroIDTWITTER_123456789101111", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getIdentifier());
	}
	
	@Test
	public void provideIndividualDataByFacebook_1() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("FACEBOOK_ID_Exempledqsqdhqhslkdhsqui");
		request.setOption("FB");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("700000000004", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		
		Assert.assertNotNull(response.getExternalIdentifierResponse());
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0));
		Assert.assertNotNull(response.getExternalIdentifierResponse().get(0).getExternalIdentifier());
		
		Assert.assertEquals("FACEBOOK_ID", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getType());
		Assert.assertEquals("FACEBOOK_ID_Exempledqsqdhqhslkdhsqui", response.getExternalIdentifierResponse().get(0).getExternalIdentifier().getIdentifier());
	}


	@Test
	public void provideIndividualDataByFB() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("2008934711");
		request.setOption("FP");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("400168400740", response.getIndividualResponse().getIndividualInformations().getIdentifier());
	}

	
	// Test pour CRMPUSH : Appel on shot sur MA et/ou FP 
	@Test
	public void provideIndividualDataByAC_SearchForGIN() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("071228AP");
		request.setOption("AC");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("400491001152", response.getIndividualResponse().getIndividualInformations().getIdentifier());
	}

	@Test
	public void provideIndividualDataByFP_SearchForGIN() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("001116082283");
		request.setOption("AC");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("400491001152", response.getIndividualResponse().getIndividualInformations().getIdentifier());
	}

	// REPIND-854 : compliance for old version of ProvideIndividualData 
	@Test
	public void provideIndividualDataByGIN_ProspectWithLocalization() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900002749125");
		request.setOption("GIN");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		// Test if it's a prospect
		Assert.assertNotNull(response.getLocalizationResponse());
		Assert.assertEquals(response.getLocalizationResponse().size(), 1);
		Assert.assertNull(response.getMarketingDataResponse());
		Assert.assertEquals(response.getPostalAddressResponse().size(), 0);
		Assert.assertEquals("900002749125", response.getIndividualResponse().getIndividualInformations().getIdentifier());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void provideIndividualDataByGIN_DelegationKidSolo() throws BusinessErrorBlocBusinessException, SystemException, JrafDomainException {
		
		IndividuDTO kidSolo = new IndividuDTO();
		kidSolo.setNom("KID SOLO TEST U");
		kidSolo.setPrenom("KID SOLO TEST U");
		kidSolo.setDateNaissance(new Date());
		kidSolo.setCivilite("MR");
		kidSolo.setNonFusionnable("N");
		kidSolo.setSiteCreation("QVI");
		kidSolo.setDateCreation(new Date());
		kidSolo.setSignatureCreation("TU");
		String ginKidSolo = individuDS.createAnIndividualKidSolo(kidSolo);
		
		IndividuDTO individuDTO = individuDS.getByGin("400311637013");
		IndividuDTO individuDTOKidSolo = individuDS.getByGin(ginKidSolo);
		
		List<DelegationDataDTO> listDelegationData = new ArrayList<DelegationDataDTO>();
		
		DelegationDataDTO delegationDataUM = new DelegationDataDTO();
		delegationDataUM.setType("UM");
		delegationDataUM.setStatus("A");
		delegationDataUM.setDelegatorDTO(individuDTO);
		delegationDataUM.setDelegateDTO(individuDTOKidSolo);
		delegationDataUM.setCreationDate(new Date());
		delegationDataUM.setCreationSignature("TU");
		delegationDataUM.setCreationSite("QVI");
		listDelegationData.add(delegationDataUM);
				
		DelegationDataDTO delegationDataUA = new DelegationDataDTO();
		delegationDataUA.setType("UA");
		delegationDataUA.setStatus("A");
		delegationDataUA.setDelegatorDTO(individuDTO);
		delegationDataUA.setDelegateDTO(individuDTOKidSolo);
		delegationDataUA.setCreationDate(new Date());
		delegationDataUA.setCreationSignature("TU");
		delegationDataUA.setCreationSite("QVI");
		listDelegationData.add(delegationDataUA);
				
		DelegationDataDTO delegationDataTU = new DelegationDataDTO();
		delegationDataTU.setType("TU");
		delegationDataTU.setStatus("A");
		delegationDataTU.setDelegatorDTO(individuDTO);
		delegationDataTU.setDelegateDTO(individuDTOKidSolo);
		delegationDataTU.setCreationDate(new Date());
		delegationDataTU.setCreationSignature("TU");
		delegationDataTU.setCreationSite("QVI");
		listDelegationData.add(delegationDataUA);
		
		DelegationDataDTO delegationDataUF = new DelegationDataDTO();
		delegationDataUF.setType("UF");
		delegationDataUF.setStatus("A");
		delegationDataUF.setDelegatorDTO(individuDTO);
		delegationDataUF.setDelegateDTO(individuDTOKidSolo);
		delegationDataUF.setCreationDate(new Date());
		delegationDataUF.setCreationSignature("TU");
		delegationDataUF.setCreationSite("QVI");
		listDelegationData.add(delegationDataUF);
		
		individuDTO.setDelegatorListDTO(listDelegationData);
		
		individuDS.updateIndividual(individuDTO);
		
		delegationDataDS.create(delegationDataUM);
		delegationDataDS.create(delegationDataUF);
		delegationDataDS.create(delegationDataTU);
		delegationDataDS.create(delegationDataUA);
		
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400311637013");
		request.setOption("GIN");
		request.getScopeToProvide().add("DELEGATION");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertEquals(2, response.getDelegationDataResponse().getDelegate().size());
	}
	
	/**
	 * Create a provide's request with a individual not exist our database
	 * @result this method will be return a BusinessErrorCode (the individual must not appear in the response).
	 *         BusinessErrorCode <code>ERROR_001</code>
	 */
	@Test
	@Transactional
	public void provideIndividualData_provideIndividualDataByIdentifierStatutFNotExist(){
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		//TODO : create a individu and update with statut F
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("000000000000");
		request.setOption("GIN");
		
		try{
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode().toString(), BusinessErrorCodeEnum.ERROR_001.toString());
		} catch (SystemException e) {
			Assert.fail();
		}
	}
	
	@Test
	@Transactional
	public void provideIndividualData_provideIndividualDataByIdentifierCCNR16() throws BusinessErrorBlocBusinessException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("1234567890123456");
		request.setOption("AC");
		
		try{
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			Assert.fail();			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode().toString(), BusinessErrorCodeEnum.ERROR_001.toString());
		} catch (SystemException e) {
			Assert.fail();
		}
	}

	@Test
	@Transactional
	public void provideIndividualDataByGIN_EmailInvalid() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// Init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400518660834");
		request.setOption("GIN");
		request.getScopeToProvide().add("EMAIL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals(1, response.getEmailResponse().size());
		
		Assert.assertEquals("400518660834", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals("raymonmercy1@gmail.com", response.getEmailResponse().get(0).getEmail().getEmail());
	}	

	@Test
	@Transactional
	public void provideIndividualData_provideIndividualDataByIdentifierCCNR15() throws BusinessErrorBlocBusinessException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("123456789012345");
		request.setOption("AC");
		
		try{
			ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
			Assert.fail();			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode().toString(), BusinessErrorCodeEnum.ERROR_001.toString());
		} catch (SystemException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void provideIndividualDataByGin_REPIND_PP_inError() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException, UtfException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		PaymentPreferencesDataHelper paymentPreferencesDataHelperMock = Mockito.mock(PaymentPreferencesDataHelper.class);
		provideIndividualDataV6Impl.setPaymentPreferencesDataHelperV5(paymentPreferencesDataHelperMock);
		Mockito.doThrow(new JrafDomainException("INTERNAL ERROR")).when(paymentPreferencesDataHelperMock).provideMaskedPaymentPreferences(Mockito.anyString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertTrue(response.getAccountDataResponse().getAccountData().getPercentageFullProfil() <= 75);
	}

	@Test
	@Transactional
	public void provideIndividualData_ForMergeWithWarningWithoutAccountData()
			throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("110000012801");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());

		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl
				.provideIndividualDataByIdentifier(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getWarningResponse());
		Assert.assertNotNull(response.getWarningResponse().get(0));
		Assert.assertNotNull(response.getWarningResponse().get(0).getWarning());
		Assert.assertEquals("INDIVIDUAL MERGED", response.getWarningResponse().get(0).getWarning().getWarningCode());
		Assert.assertEquals("400003729376", response.getWarningResponse().get(0).getWarning().getWarningDetails());
	}
}
