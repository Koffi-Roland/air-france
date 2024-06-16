package com.airfrance.repind.service.ws.internal.ut.helper;

import com.airfrance.ref.exception.ContractExistException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ContractConstantValues;
import com.airfrance.ref.type.ContractDataKeyEnum;
import com.airfrance.repind.dto.individu.ContractDataDTO;
import com.airfrance.repind.dto.individu.ContractV2DTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.ContractRequestDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractRequestDTO;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.ws.internal.helpers.CreateOrUpdateSubscribersHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ContextConfiguration(locations="classpath:/config/application-context-spring-test.xml")
@Transactional(transactionManager="transactionManagerRepind")
public class CreateOrUpdateSubscribersHelperTest extends CreateOrUpdateSubscribersHelper {

	private RoleDS roleDS;
	
	CreateOrUpdateSubscribersHelper coush;
	
	@Before
	public void setUp() throws Exception {
		coush = new CreateOrUpdateSubscribersHelper();
		roleDS = Mockito.mock(RoleDS.class);
		coush.setRoleDS(roleDS);
	}
	
	@Test
	public void test_transformRequestToRoleContrats_BasicCaseTest() throws ParseException {
		CreateUpdateRoleContractRequestDTO request = new CreateUpdateRoleContractRequestDTO();
		ContractRequestDTO cr = new ContractRequestDTO();
		ContractV2DTO cv2 = new ContractV2DTO();
		request.setContractRequest(cr);
		cr.setContract(cv2);

		request.setActionCode("C");
		request.setGin("412345678912");
		cv2.setCompanyCode("AF");
		cv2.setContractNumber("001234567891");
		cv2.setContractStatus("V");
		cv2.setProductType("RP");
		cv2.setProductSubType("DOM");
		cv2.setIataCode("01234567");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		Date beg = sdf.parse("01/01/2014");
		Date end = sdf.parse("01/01/2020");
		cv2.setValidityStartDate(beg);
		cv2.setValidityEndDate(end);
		for (String contractDataKey : ContractDataKeyEnum.getKeysByType(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT)) {
			ContractDataDTO cd = new ContractDataDTO();
			cd.setKey(contractDataKey);
			switch (ContractDataKeyEnum.getEnumByKey(contractDataKey)) {
			case PRODUCTFAMILY:
				cd.setValue("F");
				break;
			case QUALIFYINGHISTMILES:
				cd.setValue("1000");
				break;
			case QUALIFYINGHISTSEGMENTS:
				cd.setValue("1500");
				break;
			case BONUSPERMISSION:
				cd.setValue("PERM");
				break;
			case TREATMENTFAMILY:
				cd.setValue("BDC");
				break;
			default:
				break;
			}
			cr.getContractData().add(cd);
		}


		RoleContratsDTO rc = this.transformRequestToRoleContrats(request);

		Assert.assertEquals("GIN", "412345678912", rc.getGin());
		Assert.assertEquals("GIN", "412345678912", rc.getBusinessroledto().getGinInd());
		Assert.assertEquals("COMPANY CODE", "AF", rc.getCodeCompagnie());
		Assert.assertEquals("CONTRACT NUMBER", "001234567891", rc.getNumeroContrat());
		Assert.assertEquals("CONTRACT NUMBER", "001234567891", rc.getBusinessroledto().getNumeroContrat());
		Assert.assertEquals("CONTRACT STATUS", "V", rc.getEtat());
		Assert.assertEquals("CONTRACT TYPE", "C", rc.getBusinessroledto().getType());
		Assert.assertEquals("PRODUCT TYPE", "RP", rc.getTypeContrat());
		Assert.assertEquals("PRODUCT TYPE", "DOM", rc.getSousType());
		Assert.assertEquals("AGENCE IATA", "01234567", rc.getAgenceIATA());
		Assert.assertEquals("DATA START", beg, rc.getDateDebutValidite());
		Assert.assertEquals("DATA END", end, rc.getDateFinValidite());
		Assert.assertEquals("PRODUCT FAMILY", "F", rc.getFamilleProduit());
		Assert.assertEquals("PRODUCT TREATMENT", "BDC", rc.getFamilleTraitement());
		Assert.assertEquals("QUALIFYING HIST MILES", new Integer(1000), rc.getMilesQualifPrec());
		Assert.assertEquals("QUALIFYING HIST SEGMENTS", new Integer(1500), rc.getSegmentsQualifPrec());
		Assert.assertEquals("PERMISSION BONUS", "PERM", rc.getPermissionPrime());
	}

	@Test
	public void test_transformRequestToRoleContrats_GinTooLongTest() throws ParseException {
		CreateUpdateRoleContractRequestDTO request = new CreateUpdateRoleContractRequestDTO();
		ContractRequestDTO cr = new ContractRequestDTO();
		ContractV2DTO cv2 = new ContractV2DTO();
		request.setContractRequest(cr);
		cr.setContract(cv2);

		request.setActionCode("C");
		request.setGin("4123456789120");
		cv2.setCompanyCode("AF");
		cv2.setContractNumber("001234567891");
		cv2.setContractStatus("V");
		cv2.setProductType("RP");
		cv2.setProductSubType("DOM");
		cv2.setIataCode("01234567");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		Date beg = sdf.parse("01/01/2014");
		Date end = sdf.parse("01/01/2020");
		cv2.setValidityStartDate(beg);
		cv2.setValidityEndDate(end);
		for (String contractDataKey : ContractDataKeyEnum.getKeysByType(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT)) {
			ContractDataDTO cd = new ContractDataDTO();
			cd.setKey(contractDataKey);
			switch (ContractDataKeyEnum.getEnumByKey(contractDataKey)) {
			case PRODUCTFAMILY:
				cd.setValue("F");
				break;
			case QUALIFYINGHISTMILES:
				cd.setValue("1000");
				break;
			case QUALIFYINGHISTSEGMENTS:
				cd.setValue("1500");
				break;
			case BONUSPERMISSION:
				cd.setValue("PERM");
				break;
			case TREATMENTFAMILY:
				cd.setValue("BDC");
				break;
			default:
				break;
			}
			cr.getContractData().add(cd);
		}


		RoleContratsDTO rc = this.transformRequestToRoleContrats(request);

		Assert.assertEquals("GIN", "4123456789120", rc.getGin());
		Assert.assertEquals("GIN", "4123456789120", rc.getBusinessroledto().getGinInd());
		Assert.assertEquals("COMPANY CODE", "AF", rc.getCodeCompagnie());
		Assert.assertEquals("CONTRACT NUMBER", "001234567891", rc.getNumeroContrat());
		Assert.assertEquals("CONTRACT NUMBER", "001234567891", rc.getBusinessroledto().getNumeroContrat());
		Assert.assertEquals("CONTRACT STATUS", "V", rc.getEtat());
		Assert.assertEquals("CONTRACT TYPE", "C", rc.getBusinessroledto().getType());
		Assert.assertEquals("PRODUCT TYPE", "RP", rc.getTypeContrat());
		Assert.assertEquals("PRODUCT TYPE", "DOM", rc.getSousType());
		Assert.assertEquals("AGENCE IATA", "01234567", rc.getAgenceIATA());
		Assert.assertEquals("DATA START", beg, rc.getDateDebutValidite());
		Assert.assertEquals("DATA END", end, rc.getDateFinValidite());
		Assert.assertEquals("PRODUCT FAMILY", "F", rc.getFamilleProduit());
		Assert.assertEquals("PRODUCT TREATMENT", "BDC", rc.getFamilleTraitement());
		Assert.assertEquals("QUALIFYING HIST MILES", new Integer(1000), rc.getMilesQualifPrec());
		Assert.assertEquals("QUALIFYING HIST SEGMENTS", new Integer(1500), rc.getSegmentsQualifPrec());
		Assert.assertEquals("PERMISSION BONUS", "PERM", rc.getPermissionPrime());
	}


	@Test
	public void test_transformRequestToRoleContrats_AlphaToNumberForContractData() throws ParseException {
		CreateUpdateRoleContractRequestDTO request = new CreateUpdateRoleContractRequestDTO();
		ContractRequestDTO cr = new ContractRequestDTO();
		ContractV2DTO cv2 = new ContractV2DTO();
		request.setContractRequest(cr);
		cr.setContract(cv2);

		request.setActionCode("C");
		request.setGin("4123456789120");
		cv2.setCompanyCode("AF");
		cv2.setContractNumber("001234567891");
		cv2.setContractStatus("V");
		cv2.setProductType("RP");
		cv2.setProductSubType("DOM");
		cv2.setIataCode("01234567");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		Date beg = sdf.parse("01/01/2014");
		Date end = sdf.parse("01/01/2020");
		cv2.setValidityStartDate(beg);
		cv2.setValidityEndDate(end);
		for (String contractDataKey : ContractDataKeyEnum.getKeysByType(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT)) {
			ContractDataDTO cd = new ContractDataDTO();
			cd.setKey(contractDataKey);
			switch (ContractDataKeyEnum.getEnumByKey(contractDataKey)) {
			case PRODUCTFAMILY:
				cd.setValue("F");
				break;
			case QUALIFYINGHISTMILES:
				cd.setValue("1000");
				break;
			case QUALIFYINGHISTSEGMENTS:
				cd.setValue("15AZER");
				break;
			case BONUSPERMISSION:
				cd.setValue("PERM");
				break;
			case TREATMENTFAMILY:
				cd.setValue("BDC");
				break;
			default:
				break;
			}
			cr.getContractData().add(cd);
		}


		try {
			this.transformRequestToRoleContrats(request);
			Assert.fail();
		} catch (RuntimeException re) {
			Assert.assertTrue(re instanceof NumberFormatException);
		}
	}

	@Test
	public void test_transformRequestToRoleContrats_NoRequest() {
		RoleContratsDTO rc = this.transformRequestToRoleContrats(null);

		Assert.assertNull(rc);

		rc = this.transformRequestToRoleContrats(new CreateUpdateRoleContractRequestDTO());

		Assert.assertNull(rc);
	}

	@Test
	public void test_createRoleContract_KnownContractNumber() throws JrafDomainException  {
		RoleContratsDTO rcDto = new RoleContratsDTO();
		rcDto.setNumeroContrat("002051501254");
		rcDto.setGin("400476875690");
		Mockito.when(roleDS.findRoleContractByNumContract("002051501254"))
		.thenReturn(new RoleContratsDTO());
		try {
			coush.createRoleContract(rcDto, new SignatureDTO());
			Assert.fail();
		} catch (JrafDomainException e) {
			Assert.assertTrue(e instanceof ContractExistException);
		}
	}

	@Test
	public void test_createRoleContract_NullOrEmptyContractNumber()  {
		RoleContratsDTO rcDto = new RoleContratsDTO();
		rcDto.setNumeroContrat("");
		try {
			coush.createRoleContract(rcDto, new SignatureDTO());
			Assert.fail();
		} catch (JrafDomainException e) {
			Assert.assertTrue(e instanceof MissingParameterException);
		}
	}

	@Test
	@Transactional
	public void test_createRoleContract_NewContractNumber() throws ParseException, JrafDomainException {
		
		RoleContratsDTO rcDto = new RoleContratsDTO();
		rcDto.setNumeroContrat("002051501254");
		rcDto.setGin("400476875690");

		BusinessRoleDTO brDto = new BusinessRoleDTO();
		brDto.setGinInd("400476875690");
		brDto.setNumeroContrat("012345678901");
		brDto.setType("C");
		rcDto.setBusinessroledto(brDto);
		
		SignatureDTO sigDto = new SignatureDTO();
		Date dateCreate = new Date();
		sigDto.setDate(dateCreate);
		sigDto.setSignature("CREATE_TEST");
		sigDto.setSite("QVI");
		
		Mockito.when(roleDS.findRoleContractByNumContract("002051501254"))
		.thenReturn(null);
		
		Mockito.when(roleDS.createRoleContractForSubscribers(rcDto))
		.thenReturn("002051501254");
		
		String result = coush.createRoleContract(rcDto, sigDto);
		
		Assert.assertEquals("002051501254",result);
		
	}

	@Test
	@Transactional
	@Rollback(true)
	public void test_createRoleContract_NotExistingGIN() throws ParseException, JrafDomainException {

		// Init datas
		BusinessRoleDTO brDto = new BusinessRoleDTO();
		brDto.setGinInd("000000000000");
		brDto.setNumeroContrat("012345678901");
		brDto.setType("C");

		IndividuDTO indDto = new IndividuDTO();
		indDto.setSgin("000000000000");

		RoleContratsDTO rcDto = new RoleContratsDTO();
		rcDto.setBusinessroledto(brDto);
		rcDto.setIndividudto(indDto);
		rcDto.setGin("000000000000");
		rcDto.setNumeroContrat("012345678901");
		rcDto.setCodeCompagnie("AF");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		Date beg = sdf.parse("01/01/2014");
		Date end = sdf.parse("01/01/2020");
		rcDto.setDateDebutValidite(beg);
		rcDto.setDateFinValidite(end);
		rcDto.setEtat("C");
		rcDto.setSousType("DOM");
		rcDto.setTypeContrat("RP");

		SignatureDTO signDto = new SignatureDTO();
		Date dateCreate = new Date();
		signDto.setDate(dateCreate);
		signDto.setSignature("CREATE_TEST");
		signDto.setSite("QVI");

		// Execute Test
		try {
			coush.createRoleContract(rcDto, signDto);
		} catch (JrafDomainException e) {
			Assert.assertTrue(e instanceof NotFoundException);
		}
	}

	@Test
	@Transactional
	@Rollback(true)
	public void test_updateRoleContract_NotExistingGIN() throws ParseException, JrafDomainException {
		// Init datas
		BusinessRoleDTO brDto = new BusinessRoleDTO();
		brDto.setGinInd("000000000000");
		brDto.setNumeroContrat("012345678901");
		brDto.setType("C");

		IndividuDTO indDto = new IndividuDTO();
		indDto.setSgin("000000000000");

		RoleContratsDTO rcDto = new RoleContratsDTO();
		rcDto.setBusinessroledto(brDto);
		rcDto.setIndividudto(indDto);
		rcDto.setGin("000000000000");
		rcDto.setNumeroContrat("012345678901");
		rcDto.setCodeCompagnie("AF");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		Date beg = sdf.parse("01/01/2014");
		Date end = sdf.parse("01/01/2020");
		rcDto.setDateDebutValidite(beg);
		rcDto.setDateFinValidite(end);
		rcDto.setEtat("C");
		rcDto.setSousType("DOM");
		rcDto.setTypeContrat("RP");

		SignatureDTO signDto = new SignatureDTO();
		Date dateCreate = new Date();
		signDto.setDate(dateCreate);
		signDto.setSignature("CREATE_TEST");
		signDto.setSite("QVI");

		// Execute Test
		try {
			coush.updateRoleContract(rcDto, signDto);
		} catch (JrafDomainException e) {
			Assert.assertTrue(e instanceof NotFoundException);
		}
	}

	@Test
	@Transactional
	@Rollback(true)
	public void test_updateRoleContract_simpleUpdate() throws ParseException, JrafDomainException {

		RoleContratsDTO rcDto = new RoleContratsDTO();
		
		BusinessRoleDTO brDto = new BusinessRoleDTO();
		brDto.setGinInd("400476875690");
		brDto.setNumeroContrat("001108854470");
		brDto.setType("C");
		rcDto.setBusinessroledto(brDto);
		
		rcDto.setNumeroContrat("001108854470");
		rcDto.setGin("400476875690");

		rcDto.setGin("400476875690");
		rcDto.setNumeroContrat("001108854470");
		rcDto.setCodeCompagnie("AF");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		Date beg = sdf.parse("01/01/2014");
		Date end = sdf.parse("01/01/2020");
		rcDto.setDateDebutValidite(beg);
		rcDto.setDateFinValidite(end);
		rcDto.setEtat("C");
		rcDto.setSousType("DOM");
		rcDto.setTypeContrat("RP");

		
		SignatureDTO signDto = new SignatureDTO();
		Date dateCreate = new Date();
		signDto.setDate(dateCreate);
		signDto.setSignature("UPDATE_TEST");
		signDto.setSite("QVI");

		Mockito.when(roleDS.findRoleContractByNumContract("001108854470"))
		.thenReturn(rcDto);
		
		Mockito.when(roleDS.updateRoleContractForSubscribers(rcDto))
		.thenReturn("001108854470");
		
		String result = coush.updateRoleContract(rcDto, signDto);
		
		Assert.assertEquals("001108854470", result);

	}

	@Test
	@Transactional
	@Rollback(true)
	public void test_updateRoleContract_unknownContractNumber() throws JrafDomainException, ParseException {
		// Init datas
		BusinessRoleDTO brDto = new BusinessRoleDTO();
		brDto.setGinInd("400476875690");
		brDto.setNumeroContrat("012345678901");
		brDto.setType("C");

		IndividuDTO indDto = new IndividuDTO();
		indDto.setSgin("400476875690");

		RoleContratsDTO rcDto = new RoleContratsDTO();
		rcDto.setBusinessroledto(brDto);
		rcDto.setIndividudto(indDto);
		rcDto.setGin("400476875690");
		rcDto.setNumeroContrat("012345678901");
		rcDto.setCodeCompagnie("AF");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		Date beg = sdf.parse("01/01/2014");
		Date end = sdf.parse("01/01/2020");
		rcDto.setDateDebutValidite(beg);
		rcDto.setDateFinValidite(end);
		rcDto.setEtat("C");
		rcDto.setSousType("DOM");
		rcDto.setTypeContrat("RP");

		SignatureDTO signDto = new SignatureDTO();
		Date dateCreate = new Date();
		signDto.setDate(dateCreate);
		signDto.setSignature("UPDATE_TEST");
		signDto.setSite("QVI");

		Mockito.when(roleDS.findRoleContractByNumContract("012345678901"))
		.thenReturn(null);
		
		Mockito.when(roleDS.createRoleContractForSubscribers(rcDto))
		.thenReturn("012345678901");
		
		String result = coush.updateRoleContract(rcDto, signDto);
		
		Assert.assertEquals("012345678901", result);
	}
	

	@Test
	@Transactional
	public void test_deleteRoleContract_knownContract() throws JrafDomainException, ParseException {
		
		Mockito.when(roleDS.findRoleContractByNumContract("001108854470"))
		.thenReturn(new RoleContratsDTO());
		
		Boolean ret = coush.deleteRoleContract("001108854470");
		
		Assert.assertTrue(ret);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_deleteRoleContract_unKnownContract() throws JrafDomainException, ParseException {
		// 999999999999 do not exist in database
		Boolean ret = coush.deleteRoleContract("999999999999");
		Assert.assertFalse(ret);
	}
}
