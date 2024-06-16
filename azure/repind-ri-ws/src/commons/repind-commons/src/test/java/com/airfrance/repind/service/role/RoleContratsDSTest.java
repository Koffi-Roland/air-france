package com.airfrance.repind.service.role;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.service.role.internal.RoleDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RoleContratsDSTest {
   
	/** logger */
    private static final Log log = LogFactory.getLog(RoleContratsDSTest.class);
	
    @Autowired
	private RoleDS roleDS;

	@Test(expected = Test.None.class /* no exception expected */)
	@Ignore
	public void testDeleteRoleContract() throws JrafDomainException {
		RoleContratsDTO roleContractsDTO = new RoleContratsDTO();
		roleContractsDTO.setNumeroContrat("5C1245155");
		roleDS.deleteRoleContract(roleContractsDTO );
	}
	
	@Test
	public void test_findRoleContractByNumContract_ExistingContractNumber() throws JrafDomainException {
		RoleContratsDTO rcDto = roleDS.findRoleContractByNumContract("001024800054");

		Assert.assertNotNull(rcDto);
		Assert.assertEquals("000000000216", rcDto.getGin());
	}

	@Test
	public void test_findRoleContractByNumContract_NotExistingContractNumber() throws JrafDomainException {
		RoleContratsDTO rcDto = roleDS.findRoleContractByNumContract("000001230012");

		Assert.assertNull(rcDto);
	}
	
	
	/*
	 * If the parameter is null, the method should return a InvalidParameterException
	 */
	@Test
	public void isContractActiveNullParameterTest() throws JrafDomainException {		
		RoleContratsDTO rcDto = null;
		try {
			roleDS.isContractActive(rcDto);
			Assert.fail();
		}catch(InvalidParameterException e ) {}
	}
	
	
	/*
	 * If the start date is in the future, the method should return false
	 */
	@Test
	public void isContractActiveBeginDateInFuture() throws JrafDomainException, ParseException {		
		RoleContratsDTO rcDto = new RoleContratsDTO();
		 SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = parser.parse("2200-01-01");
		rcDto.setDateDebutValidite(date);
		boolean result = roleDS.isContractActive(rcDto);
		Assert.assertFalse(result);
	}
	
	/*
	 * If the start date is in the past, and the end date is null: the method should return true
	 */
	@Test
	public void isContractActiveBeginDateInPast() throws JrafDomainException, ParseException {		
		RoleContratsDTO rcDto = new RoleContratsDTO();
		 SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = parser.parse("1924-01-01");
		rcDto.setDateDebutValidite(date);
		boolean result = roleDS.isContractActive(rcDto);
		Assert.assertTrue(result);
	}
	
	/*
	 * If the start date is in the past, and the end date is in the future: the method should return true
	 */
	@Test
	public void isContractActiveBeginDateInPastAndEndInFuture() throws JrafDomainException, ParseException {		
		RoleContratsDTO rcDto = new RoleContratsDTO();
		 SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
	    Date startDate = parser.parse("1924-01-01");
	    Date endDate = parser.parse("2200-01-01");
		rcDto.setDateDebutValidite(startDate);
		rcDto.setDateFinValidite(endDate);
		boolean result = roleDS.isContractActive(rcDto);
		Assert.assertTrue(result);
	}
	
	
	/*
	 * If the start date is in the past, and the end date is in the past: the method should return false
	 */
	@Test
	public void isContractActiveBeginDateInPastAndEndInPast() throws JrafDomainException, ParseException {		
		RoleContratsDTO rcDto = new RoleContratsDTO();
		 SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
	    Date startDate = parser.parse("1924-01-01");
		rcDto.setDateDebutValidite(startDate);
		rcDto.setDateFinValidite(startDate);
		boolean result = roleDS.isContractActive(rcDto);
		Assert.assertFalse(result);
	}
	
	/*
	 * If you pass a null parameter, the method should throw an invalidParameterException
	 */
	@Test
	public void isFlyingBlueNullParameter() throws InvalidParameterException {
		RoleContratsDTO roleContrat = null;
		try {
			boolean result = roleDS.isFlyingBlue(roleContrat);
			Assert.fail();
		}catch(InvalidParameterException e) {}
		
	}
	
	/*
	 * If the contract is of type "FP" it s a flying blue, method should return true
	 */
	@Test
	public void isFlyingBlueGood() throws InvalidParameterException {
		RoleContratsDTO roleContrat = new RoleContratsDTO();
		roleContrat.setTypeContrat("FP");
		boolean result = roleDS.isFlyingBlue(roleContrat);
		Assert.assertTrue(result);
	}
	
	/*
	 * If the contract is not of type "FP", it s not a flying blue, method should return false
	 */
	@Test
	public void isFlyingBlueBad() throws InvalidParameterException {
		RoleContratsDTO roleContrat = new RoleContratsDTO();
		roleContrat.setTypeContrat("");
		boolean result = roleDS.isFlyingBlue(roleContrat);
		Assert.assertFalse(result);
		roleContrat.setTypeContrat("FPs");
		result = roleDS.isFlyingBlue(roleContrat);
		Assert.assertFalse(result);
		roleContrat.setTypeContrat("sFP");
		result = roleDS.isFlyingBlue(roleContrat);
		Assert.assertFalse(result);
		roleContrat.setTypeContrat("TO");
		result = roleDS.isFlyingBlue(roleContrat);
		Assert.assertFalse(result);
	}
	
	/*
	 * Have a contract flying blue, should return true
	 */
	@Test
	public void haveActiveFlyingBlueContractGood() throws JrafDomainException {
		List<RoleContratsDTO> contracts = new ArrayList<>();
		RoleContratsDTO contract1 = new RoleContratsDTO();
		RoleContratsDTO contract2 = new RoleContratsDTO();
		RoleContratsDTO contract3 = new RoleContratsDTO();
		RoleContratsDTO contract4 = new RoleContratsDTO();
		RoleContratsDTO contract5 = new RoleContratsDTO();

		contract1.setTypeContrat("");
		contract1.setDateDebutValidite(new Date());
		contract2.setTypeContrat("     ");
		contract2.setDateDebutValidite(new Date());
		contract3.setTypeContrat(null);
		contract3.setDateDebutValidite(new Date());
		contract4.setTypeContrat("toto");
		contract4.setDateDebutValidite(new Date());
		contract5.setTypeContrat("FP");
		contract5.setDateDebutValidite(new Date());


		contracts.add(contract1);
		contracts.add(contract2);
		contracts.add(contract3);
		contracts.add(contract4);
		contracts.add(contract5);

		RoleDS roleDSGood = Mockito.mock(RoleDS.class);
		Mockito.when(roleDSGood.isFlyingBlue(Mockito.any(RoleContratsDTO.class))).thenCallRealMethod();
		Mockito.when(roleDSGood.isContractActive(Mockito.any(RoleContratsDTO.class))).thenCallRealMethod();
		Mockito.when(roleDSGood.haveActiveFlyingBlueContract(Mockito.anyString())).thenCallRealMethod();
		Mockito.when(roleDSGood.haveActiveFlyingBlueContract(null)).thenCallRealMethod();
		Mockito.when(roleDSGood.findRoleContrats(Mockito.anyString())).thenReturn(contracts);
		
		boolean result = roleDSGood.haveActiveFlyingBlueContract("9");
		Assert.assertTrue(result);
	
	}
	
	
	/*
	 * Don't have a contract flying blue, should return false
	 */
	@Test
	public void haveActiveFlyingBlueContractBad() throws JrafDomainException {
		List<RoleContratsDTO> contracts = new ArrayList<>();
		RoleContratsDTO contract1 = new RoleContratsDTO();
		RoleContratsDTO contract2 = new RoleContratsDTO();
		RoleContratsDTO contract3 = new RoleContratsDTO();
		RoleContratsDTO contract4 = new RoleContratsDTO();
		RoleContratsDTO contract5 = new RoleContratsDTO();

		contract1.setTypeContrat("");
		contract1.setDateDebutValidite(new Date());
		contract2.setTypeContrat("     ");
		contract2.setDateDebutValidite(new Date());
		contract3.setTypeContrat(null);
		contract3.setDateDebutValidite(new Date());
		contract4.setTypeContrat("toto");
		contract4.setDateDebutValidite(new Date());
		contract5.setTypeContrat("FPs");
		contract5.setDateDebutValidite(new Date());


		contracts.add(contract1);
		contracts.add(contract2);
		contracts.add(contract3);
		contracts.add(contract4);
		contracts.add(contract5);

		RoleDS roleDS = getRoleDSMock(contracts);
		boolean result = roleDS.haveActiveFlyingBlueContract("9");
		Assert.assertFalse(result);
	
	}
	
	
	/*
	 * Don't have any contract (empty list), should return false
	 */
	@Test
	public void haveActiveFlyingBlueContractNotContractEmpty() throws JrafDomainException {
		List<RoleContratsDTO> contracts = new ArrayList<>();
		RoleDS roleDS = getRoleDSMock(contracts);
		boolean result = roleDS.haveActiveFlyingBlueContract("9");
		Assert.assertFalse(result);
	}
	
	
	/*
	 * Don't have any contract (null list), should return false
	 */
	@Test
	public void haveActiveFlyingBlueContract() throws JrafDomainException {
		List<RoleContratsDTO> contracts = null;
		RoleDS roleDS = getRoleDSMock(contracts);
		boolean result = roleDS.haveActiveFlyingBlueContract("9");
		Assert.assertFalse(result);
	
	}
	
	/*
	 * Parameter is null, should throw a InvalidParameterException
	 */
	@Test
	public void haveActiveFlyingBlueContractNullParameter() throws JrafDomainException {
		List<RoleContratsDTO> contracts = new ArrayList<RoleContratsDTO>();
		String gin = null;
		RoleDS roleDS = getRoleDSMock(contracts);
		try {
			roleDS.haveActiveFlyingBlueContract(gin);
			Assert.fail();
		}catch(InvalidParameterException e) {}
	}
	
	/* 
	 * Parameter is empty, should throw a InvalidParameterException
	 */
	@Test
	public void haveActiveFlyingBlueContractEmptyParameter() throws JrafDomainException {
		List<RoleContratsDTO> contracts = null;
		RoleDS roleDS = getRoleDSMock(contracts);
		try {
			roleDS.haveActiveFlyingBlueContract("");
			Assert.fail();
		}catch(InvalidParameterException e) {}
		
		try {
			roleDS.haveActiveFlyingBlueContract("                 ");
			Assert.fail();
		}catch(InvalidParameterException e) {}
	}
	
	public RoleDS getRoleDSMock(List<RoleContratsDTO> contracts) throws JrafDomainException {
		RoleDS roleDS = Mockito.mock(RoleDS.class);
		Mockito.when(roleDS.isFlyingBlue(Mockito.any(RoleContratsDTO.class))).thenCallRealMethod();
		Mockito.when(roleDS.isContractActive(Mockito.any(RoleContratsDTO.class))).thenCallRealMethod();
		Mockito.when(roleDS.haveActiveFlyingBlueContract(Mockito.anyString())).thenCallRealMethod();
		Mockito.when(roleDS.haveActiveFlyingBlueContract(null)).thenCallRealMethod();
		Mockito.when(roleDS.findRoleContrats(Mockito.anyString())).thenReturn(contracts);
		return roleDS;
	}
	

}
