package com.afklm.repind.v4.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v4.provideindividualdata.ProvideIndividualDataV4Impl;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v4.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v4.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v4.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v4.siccommontype.Requestor;
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
public class ProvideIndividualDataV4ImplForScopeTest {

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "TEST_RI";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "M406368";
	private static final String APP_CODE = "ISI";
	
	@Autowired
	@Qualifier("passenger_ProvideIndividualData-v04Bean")
	private ProvideIndividualDataV4Impl provideIndividualDataImpl;
		
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
		request.getRequestor().setScope("ALL_DATA");
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}
	
	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_INDIVIDUAL_DATA() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getRequestor().setScope("INDIVIDUAL_DATA");
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_INDIVIDUAL_AND_MARKETING_DATA() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getRequestor().setScope("INDIVIDUAL_AND_MARKETING_DATA");
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	public void provideIndividualData_provideIndividualDataByIdentifier_SCOPE_INDIVIDUAL_AND_FB_DATA() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.getRequestor().setScope("INDIVIDUAL_AND_FB_DATA");
		
		ProvideIndividualInformationResponse response = provideIndividualDataImpl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
	}

}
