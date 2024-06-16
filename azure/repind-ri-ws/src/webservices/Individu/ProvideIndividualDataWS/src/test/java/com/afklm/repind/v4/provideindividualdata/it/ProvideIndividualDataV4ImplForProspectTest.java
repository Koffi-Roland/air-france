package com.afklm.repind.v4.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v4.provideindividualdata.ProvideIndividualDataV4Impl;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v4.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v4.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v4.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v4.response.BusinessErrorCodeEnum;
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
public class ProvideIndividualDataV4ImplForProspectTest {

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
	public void provideIndividualDataByGIN_ProspectOnly() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000002415");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
//		request.getScopeToProvide().add("ALL");
		
		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV4Impl.provideIndividualDataByIdentifier(request);
			Assert.fail("Should not be found because this is a Prospect");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecom() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900007330530");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
//		request.getScopeToProvide().add("ALL");
		
		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV4Impl.provideIndividualDataByIdentifier(request);
			Assert.fail("Should not be found because this is a Prospect");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	@Test
	public void provideIndividualDataByGIN_ProspectOnlyWithTelecomAndEmail() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900025436742");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
//		request.getScopeToProvide().add("ALL");
		
		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV4Impl.provideIndividualDataByIdentifier(request);
			Assert.fail("Should not be found because this is a Prospect");
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
	}
}
