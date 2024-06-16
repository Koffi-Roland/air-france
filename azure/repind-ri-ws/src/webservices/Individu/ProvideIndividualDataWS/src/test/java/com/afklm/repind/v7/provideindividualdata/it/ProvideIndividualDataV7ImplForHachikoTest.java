package com.afklm.repind.v7.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v6.provideindividualdata.type.ScopesToProvideEnum;
import com.afklm.repind.v7.provideindividualdata.ProvideIndividualDataV7Impl;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v7.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v7.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v7.siccommontype.RequestorV2;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class ProvideIndividualDataV7ImplForHachikoTest {

	private static final Log logger = LogFactory.getLog(ProvideIndividualDataV7ImplForHachikoTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";

	@Autowired
	@Qualifier("passenger_ProvideIndividualData-v07Bean")
	private ProvideIndividualDataV7Impl provideIndividualDataV7Impl;

	@Test
	public void ProvideIndividualDataByIdentifier_GIN_Test() throws BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request = new ProvideIndividualInformationRequest();
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		request.setRequestor(requestor);

		request.setIdentificationNumber("110000005425");
		request.setOption("GIN");			

		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("110000005425", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertNull((response.getContractResponse().get(0).getContract().getIataCode()));
	}

	@Test
	public void ProvideIndividualDataByIdentifier_FP_Test() throws BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request = new ProvideIndividualInformationRequest();
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		request.setRequestor(requestor);

		request.setIdentificationNumber("001020005426");
		request.setOption("FP");			

		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("110000005425", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertNull((response.getContractResponse().get(0).getContract().getIataCode()));
	}

}
