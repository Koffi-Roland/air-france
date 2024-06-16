package com.afklm.repind.v6.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v6.provideindividualdata.ProvideIndividualDataV6Impl;
import com.afklm.repind.v6.provideindividualdata.type.ScopesToProvideEnum;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v6.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v6.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v6.data.ProvideIndividualInformationResponse;
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
public class ProvideIndividualDataV6ImplForScopeTest {

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "TEST_RI";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "M406368";
	private static final String APP_CODE = "ISI";
	
	@Autowired
	@Qualifier("passenger_ProvideIndividualData-v06Bean")
	private ProvideIndividualDataV6Impl provideIndividualDataImpl;
		
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
	public void provideIndividualData_provideIndividualDataByIdentifier_NO_SCOPE() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_ALL() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_INDIVIDUAL() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.INDIVIDUAL.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_IDENTIFICATION() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.IDENTIFICATION.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_POSTAL_ADDRESS() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.POSTAL_ADDRESS.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_LOCALIZATION() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.LOCALIZATION.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_EMAIL() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.EMAIL.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_TELECOM() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.TELECOM.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_CONTRACT() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.CONTRACT.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_EXTERNAL_IDENTIFIER() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.EXTERNAL_IDENTIFIER.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_ACCOUNT() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.ACCOUNT.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_PREFILLED_NUMBER() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.PREFILLED_NUMBER.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_PREFERENCE() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.PREFERENCE.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_MARKETING() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.MARKETING.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_FLYING_BLUE_CONTRACT() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.FLYING_BLUE_CONTRACT.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_SOCIAL_NETWORK() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.SOCIAL_NETWORK.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_COMMUNICATION_PREFERENCE() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.COMMUNICATIION_PREFERENCE.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_DELEGATION() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.DELEGATION.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_TRAVELER() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.TRAVELER.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_UCCR_CONTRACT() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.UCCR_CONTRACT.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_ALERT() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.ALERT.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_DUAL() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.INDIVIDUAL.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.ACCOUNT.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_TRIPLET() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.INDIVIDUAL.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.EMAIL.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.ACCOUNT.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_QUADRO() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.INDIVIDUAL.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.EMAIL.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.TELECOM.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.ACCOUNT.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_CINQUA() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.INDIVIDUAL.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.EMAIL.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.TELECOM.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.ACCOUNT.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.CONTRACT.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_SIX() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.INDIVIDUAL.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.EMAIL.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.TELECOM.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.ALERT.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.FLYING_BLUE_CONTRACT.toString());
		request.getScopeToProvide().add(ScopesToProvideEnum.ACCOUNT.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}
}
