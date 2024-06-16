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
public class ProvideIndividualDataV4ImplForHachikoTest {

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "T412211";
	private static final String APP_CODE = "B2C";
	
	@Autowired
	@Qualifier("passenger_ProvideIndividualData-v04Bean")
	private ProvideIndividualDataV4Impl provideIndividualDataV4Impl;
	
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
	public void provideIndividualDataByGIN_OSIRIS_HACHIKO_Found() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// Init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("110000005425");
		request.setOption("GIN");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV4Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("110000005425", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertNull((response.getContractResponse().get(0).getContract().getMilesBalance()));
	}

	@Test
	public void provideIndividualDataByCIN_OSIRIS_HACHIKO_Found() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// Init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("001020005426");
		request.setOption("FP");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV4Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("110000005425", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		
		Assert.assertNotNull(response.getContractResponse());
		Assert.assertNotNull(response.getContractResponse().get(0));
		Assert.assertNotNull(response.getContractResponse().get(0).getContract());
		Assert.assertNull((response.getContractResponse().get(0).getContract().getMilesBalance()));
	}
	
}
