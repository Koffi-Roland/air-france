package com.afklm.repind.v1.createorupdaterolecontractws.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v1.createorupdaterolecontractws.CreateOrUpdateRoleContractImplV1;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001567.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001567.v1.data.CreateUpdateRoleContractRequest;
import com.afklm.soa.stubs.w001567.v1.data.CreateUpdateRoleContractResponse;
import com.afklm.soa.stubs.w001567.v1.request.ContractRequest;
import com.afklm.soa.stubs.w001567.v1.response.BusinessError;
import com.afklm.soa.stubs.w001567.v1.response.BusinessErrorBloc;
import com.afklm.soa.stubs.w001567.v1.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w001567.v1.siccommontype.Requestor;
import com.afklm.soa.stubs.w001567.v1.sicindividutype.ContractData;
import com.afklm.soa.stubs.w001567.v1.sicindividutype.ContractV2;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.soap.SOAPException;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateRoleTravelerImplV1Test {

	@Autowired
	@Qualifier("passenger_CreateOrUpdateRoleContractService-v1Bean")
	@Spy
	CreateOrUpdateRoleContractImplV1 createOrUpdateRoleContractImplV1;
	
	@Before
	public void initMocks() throws BusinessErrorBlocBusinessException, SystemException, JrafDomainException, SOAPException {
		MockitoAnnotations.initMocks(this);
		
		Mockito.doNothing().when(createOrUpdateRoleContractImplV1).checkRightsOnContract(Mockito.any(String.class), Mockito.any(String.class));
//		Mockito.doCallRealMethod().when(createOrUpdateRoleContractImplV1).createRoleContract(Mockito.any(CreateUpdateRoleContractRequest.class));
//		Mockito.doCallRealMethod().when(createOrUpdateRoleContractImplV1).checkInput(Mockito.any(CreateUpdateRoleContractRequest.class));
//		Mockito.doCallRealMethod().when(createOrUpdateRoleContractImplV1).getActionManager();
//		Mockito.doCallRealMethod().when(createOrUpdateRoleContractImplV1).getBusinessExceptionHelperV1();
	}
	
	@Test
	public void test_createOrUpdateRoleTraveler_MissingActionCode() throws SystemException {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		
		
		try {
			createOrUpdateRoleContractImplV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_133, mpe.getFaultInfo().getBusinessError().getErrorCode());
			Assert.assertEquals("Missing parameter exception: actionCode is mandatory", mpe.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}

	@Test
	public void test_createOrUpdateRoleTraveler_MissingContractData() throws SystemException {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		
		request.setActionCode("U");
		
		try {
			createOrUpdateRoleContractImplV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_133, mpe.getFaultInfo().getBusinessError().getErrorCode());
			Assert.assertEquals("Missing parameter exception: contract informations are mandatory", mpe.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}

	@Test
	public void test_createOrUpdateRoleTraveler_MissingRequestor() throws SystemException {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		
		request.setActionCode("U");
		
		ContractRequest cr = new ContractRequest();
		ContractV2 c = new ContractV2();
		c.setContractType("T");
		c.setContractStatus("C");
		c.setProductType("TR");
		cr.setContract(c);
		request.setContractRequest(cr);
		
		try {
			createOrUpdateRoleContractImplV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertNotNull(mpe);
			Assert.assertNotNull(mpe.getFaultInfo());
			Assert.assertNotNull(mpe.getFaultInfo().getBusinessError());

			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_133, mpe.getFaultInfo().getBusinessError().getErrorCode());
			Assert.assertEquals("Missing parameter exception: requestor is Mandatory", mpe.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}
	
	@Test
	// @Ignore	// Pas cool car on devrait avoir l erreur du GIN obligatoire et ce n est pas le cas...
	public void test_createOrUpdateRoleTraveler_MissingGin() throws SystemException {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		
		Requestor r = new Requestor();
		r.setApplicationCode("B2C");
		r.setMatricule("T412211");
		r.setSite("QVI");
		r.setChannel("B2C");
		r.setSignature("T412211");
		request.setRequestor(r);
		
		request.setActionCode("U");
		
		ContractRequest cr = new ContractRequest();
		ContractV2 c = new ContractV2();
		c.setContractType("T");
		c.setContractStatus("C");
		c.setProductType("TR");
		cr.setContract(c);
		request.setContractRequest(cr);
		
		try {
			CreateUpdateRoleContractResponse response = createOrUpdateRoleContractImplV1.createRoleContract(request);
			Assert.fail("Il devrait manquer le GIN - Success " + response.isSuccess());
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertNotNull(mpe);
			Assert.assertNotNull(mpe.getFaultInfo());
			Assert.assertNotNull(mpe.getFaultInfo().getBusinessError());

			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_133, mpe.getFaultInfo().getBusinessError().getErrorCode());
			Assert.assertEquals("Missing parameter exception: Gin is mandatory", mpe.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}
	
	@Test
	public void test_createOrUpdateRoleTraveler_BadContractData() throws SystemException {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setGin("910000000000");
		request.setActionCode("C");
		
		Requestor r = new Requestor();
		r.setApplicationCode("B2C");
		r.setMatricule("T412211");
		r.setSite("QVI");
		r.setChannel("B2C");
		r.setSignature("T412211");		
		request.setRequestor(r);
		
		ContractRequest cr = new ContractRequest();
		ContractV2 c = new ContractV2();
		c.setContractType("T");
		c.setContractStatus("C");
		c.setProductType("TR");
		cr.setContract(c);
		
		ContractData cd1 = new ContractData();
		cd1.setKey("NIMPORTEQUOI");
		cd1.setValue("RIEN");
		cr.getContractData().add(cd1);
		
		request.setContractRequest(cr);

		try {
			createOrUpdateRoleContractImplV1.createRoleContract(request);
			Assert.fail("Pas normal, on devrait avoir une exception NIMPORTEQUOI non valide");
			
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertNotNull(mpe);
			Assert.assertNotNull(mpe.getFaultInfo());
		
			BusinessErrorBloc beb = (BusinessErrorBloc) mpe.getFaultInfo() ;
			
			Assert.assertNotNull(beb);
			Assert.assertNotNull(beb.getBusinessError());
			
			BusinessError be = (BusinessError) beb.getBusinessError();
			
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, be.getErrorCode());
			Assert.assertEquals("INVALID PARAMETER", be.getErrorLabel());
			Assert.assertEquals("Invalid parameter: NIMPORTEQUOI is not a valid key for traveler", be.getErrorDetail());
		}
	}

	@Test
// 	@Ignore
	public void test_createOrUpdateRoleTraveler_BadFormatLastRecognitionDate() throws SystemException {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setGin("910000000000");
		request.setActionCode("C");
		
		Requestor r = new Requestor();
		r.setApplicationCode("B2C");
		r.setMatricule("T412211");
		r.setSite("QVI");
		r.setChannel("B2C");
		r.setSignature("T412211");		
		request.setRequestor(r);
		
		ContractRequest cr = new ContractRequest();
		ContractV2 c = new ContractV2();
		// c.setContractType("TR");
		c.setContractStatus("C");
		c.setProductType("TR");		
		cr.setContract(c);
		
		ContractData cd1 = new ContractData();
		cd1.setKey("lastRecognitionDate");
		cd1.setValue("30-08-2016");
		cr.getContractData().add(cd1);
		ContractData cd2 = new ContractData();
		cd2.setKey("MatchingRecognition");
		cd2.setValue("FOR");
		cr.getContractData().add(cd2);
		request.setContractRequest(cr);
		
		try {
			createOrUpdateRoleContractImplV1.createRoleContract(request);
			
			Assert.fail("Pas normal, on devrait avoir une exception BAD BIRTHDATE FORMAT non valide");
			
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertNotNull(mpe);
			Assert.assertNotNull(mpe.getFaultInfo());
		
			BusinessErrorBloc beb = (BusinessErrorBloc) mpe.getFaultInfo() ;
			
			Assert.assertNotNull(beb);
			Assert.assertNotNull(beb.getBusinessError());
			
			BusinessError be = (BusinessError) beb.getBusinessError();
			
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, be.getErrorCode());
			Assert.assertEquals("INVALID PARAMETER", be.getErrorLabel());
			Assert.assertEquals("Invalid parameter: Date must use the pattern dd/MM/yyyy", be.getErrorDetail());
		}
	}

	@Test
	public void test_createOrUpdateRoleTraveler_SimpleUpdate() throws SystemException {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setGin("910000000000");
		request.setActionCode("U");
		
		Requestor r = new Requestor();
		r.setApplicationCode("B2C");
		r.setMatricule("T412211");
		r.setSite("QVI");
		r.setChannel("B2C");
		r.setSignature("T412211");		
		request.setRequestor(r);
		
		ContractRequest cr = new ContractRequest();
		ContractV2 c = new ContractV2();
		c.setContractType("T");
		c.setContractStatus("C");
		c.setProductType("TR");
		cr.setContract(c);
		
		ContractData cd1 = new ContractData();
		cd1.setKey("lastRecognitionDate");
		cd1.setValue("30/08/2016");
		cr.getContractData().add(cd1);
		ContractData cd2 = new ContractData();
		cd2.setKey("MatchingRecognition");
		cd2.setValue("FOR");
		cr.getContractData().add(cd2);
		request.setContractRequest(cr);
		
		try {
			CreateUpdateRoleContractResponse response = createOrUpdateRoleContractImplV1.createRoleContract(request);
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.isSuccess());
			Assert.assertTrue(response.isSuccess());
			Assert.assertNotNull(response.getContractNumber());
			Assert.assertTrue(!"".equals(response.getContractNumber()));
			
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.fail("Error : " + mpe.getMessage());
		}
	}

	@Test
	public void test_createOrUpdateRoleTraveler_SimpleCreate() throws SystemException {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setGin("900000000831");
		request.setActionCode("C");
		
		Requestor r = new Requestor();
		r.setApplicationCode("B2C");
		r.setMatricule("T412211");
		r.setSite("QVI");
		r.setChannel("B2C");
		r.setSignature("T412211");		
		request.setRequestor(r);
		
		ContractRequest cr = new ContractRequest();
		ContractV2 c = new ContractV2();
		 c.setContractType("T");
		// c.setProductSubType("T");
		c.setContractStatus("C");
		c.setProductType("TR");
		cr.setContract(c);
		
		ContractData cd1 = new ContractData();
		cd1.setKey("lastRecognitionDate");
		cd1.setValue("30/12/2015");
		cr.getContractData().add(cd1);
		ContractData cd2 = new ContractData();
		cd2.setKey("MatchingRecognition");
		cd2.setValue("FLE");
		cr.getContractData().add(cd2);
		request.setContractRequest(cr);
		
		try {
			CreateUpdateRoleContractResponse response = createOrUpdateRoleContractImplV1.createRoleContract(request);
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.isSuccess());
			Assert.assertTrue(response.isSuccess());
			Assert.assertNotNull(response.getContractNumber());
			Assert.assertTrue(!"".equals(response.getContractNumber()));
			
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.fail("Error : " + mpe.getMessage());
		}
	}

	@Test
	public void test_createOrUpdateRoleTraveler_BadProductContactType() throws SystemException {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setGin("910000000001");
		request.setActionCode("U");
		
		Requestor r = new Requestor();
		r.setApplicationCode("B2C");
		r.setMatricule("T412211");
		r.setSite("QVI");
		r.setChannel("B2C");
		r.setSignature("T412211");		
		request.setRequestor(r);
		
		ContractRequest cr = new ContractRequest();
		ContractV2 c = new ContractV2();
		c.setContractType("U");
		c.setContractStatus("C");
		c.setProductType("T");
		cr.setContract(c);
		
		ContractData cd1 = new ContractData();
		cd1.setKey("lastRecognitionDate");
		cd1.setValue("30/08/2016");
		cr.getContractData().add(cd1);
		ContractData cd2 = new ContractData();
		cd2.setKey("MatchingRecognition");
		cd2.setValue("FOR");
		cr.getContractData().add(cd2);
		request.setContractRequest(cr);
		
		try {
			createOrUpdateRoleContractImplV1.createRoleContract(request);
			
			Assert.fail("Pas normal, on devrait avoir une exception INVALID PARAMETER non valide");
			
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertNotNull(mpe);
			Assert.assertNotNull(mpe.getFaultInfo());
		
			BusinessErrorBloc beb = (BusinessErrorBloc) mpe.getFaultInfo() ;
			
			Assert.assertNotNull(beb);
			Assert.assertNotNull(beb.getBusinessError());
			
			BusinessError be = (BusinessError) beb.getBusinessError();
			
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_212, be.getErrorCode());
			Assert.assertEquals("CONTRACT NOT FOUND", be.getErrorLabel());
			Assert.assertEquals("Contract was not found with provided contract number: ProductType: T, SubProductType: null not found", be.getErrorDetail());
		}
	}
	
	
	@Test
	// REPIND-794 : Migration Prospect make possible a link between TRAVELER and PROSPECT  
	public void test_createOrUpdateRoleTraveler_Prospect() throws SystemException {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setGin("900003718017");
		request.setActionCode("U");
		// request.setActionCode("C");
		
		Requestor r = new Requestor();
		r.setChannel("B2C");
		r.setApplicationCode("APP");
		r.setSite("QVI");
		r.setSignature("CBS");		
		request.setRequestor(r);
		
		ContractRequest cr = new ContractRequest();
		ContractV2 c = new ContractV2();
		 c.setContractType("T");
		c.setContractStatus("C");
		c.setProductType("TR");
		c.setCompanyCode("AF");
		cr.setContract(c);
		
		ContractData cd1 = new ContractData();
		cd1.setKey("lastRecognitionDate");
		cd1.setValue("10/11/2016");
		cr.getContractData().add(cd1);
		ContractData cd2 = new ContractData();
		cd2.setKey("MatchingRecognition");
		cd2.setValue("FLE");
		cr.getContractData().add(cd2);
		request.setContractRequest(cr);
		
		try {
			CreateUpdateRoleContractResponse response = createOrUpdateRoleContractImplV1.createRoleContract(request);
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.isSuccess());
			Assert.assertTrue(response.isSuccess());
			Assert.assertNotNull(response.getContractNumber());
			Assert.assertTrue(!"".equals(response.getContractNumber()));
			
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.fail("Error : " + mpe.getMessage());
		}
	}
	

}
