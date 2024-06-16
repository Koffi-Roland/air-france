package com.afklm.repind.v5.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v5.provideindividualdata.ProvideIndividualDataV5Impl;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v5.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v5.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v5.siccommontype.Requestor;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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
public class ProvideIndividualDataV5ImplForBDMTest {

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T730890";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "T730890";
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
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	@Ignore // Data migrated into RI
	// GIN FOUND IN THE RI BUT NOT FOUND IN THE BDM
	public void provideIndividualDataByGIN_BDM_NotFound() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// Init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400415094784");
		request.setOption("GIN");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getMarketingDataResponse());
		Assert.assertNotNull(response.getMarketingDataResponse().getMarketingInformation());
		Assert.assertNotNull(response.getMarketingDataResponse().getMarketingInformation().getErrorCode());
		Assert.assertEquals("GIN_NOT_FOUND", response.getMarketingDataResponse().getMarketingInformation().getErrorCode());
	}
	
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	// GIN FOUND IN THE RI AND FOUND IN THE BDM
	public void provideIndividualDataByGIN_BDM_Found() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// Init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("000000049231");
		request.setOption("GIN");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getMarketingDataResponse());
		Assert.assertNotNull(response.getMarketingDataResponse().getMarketingInformation());
		Assert.assertNull(response.getMarketingDataResponse().getMarketingInformation().getErrorCode());
	}
	
}
