package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.IndividualMulticriteriaDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaRequestDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaResponseDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.fail;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class SearchIndividualDSTest {

	private static final Log logger = LogFactory.getLog(SearchIndividualDSTest.class);

	@Autowired
	private IdentifyCustomerCrossReferentialDS identifyCustomerCrossReferentialDSBean;
	
	@Autowired
	private SearchIndividualDS searchIndividualDSFull;
	
	private IndividuDS individuDSMock;
    
    @Test
    public void sqlInjectionTest() throws SystemException{
    	RequestDTO request = new RequestDTO();
    	RequestorDTO requestor = new RequestorDTO();
    	requestor.setApplicationCode("Test");
    	requestor.setChannel("B2C");
    	requestor.setSignature("Test");
    	requestor.setSite("QVI");
    	
    	request.setIndex(1);
    	request.setProcessType("A");

    	SearchIdentifierDTO search = new SearchIdentifierDTO();
    	EmailDTO email = new EmailDTO();
    	email.setEmail("secure'@YourInput.plz");	
    	search.setEmail(email);
    	request.setSearchIdentifier(search);
    	
    	ContextDTO context = new ContextDTO();
    	context.setRequestor(requestor);
    	context.setTypeOfSearch("A");
    	context.setResponseType("U");
    	request.setContext(context);
    	
		try {
			identifyCustomerCrossReferentialDSBean.search(request);
		} catch (BusinessExceptionDTO e) {
			if("HQL/SQL".equals(e.getFaultInfo().getErrorCode())){
				fail("SQL injection working");
			}
		}
    }

	@Test
	public void test_searchIndividual_ICCR() throws JrafDomainException, BusinessExceptionDTO, SystemException, ParseException {

		//Preparing the request
		RequestDTO request = new RequestDTO();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setApplicationCode("Test");
		requestor.setChannel("B2C");
		requestor.setSignature("Test");
		requestor.setSite("QVI");

		request.setIndex(1);
		request.setProcessType("A");

		SearchIdentifierDTO search = new SearchIdentifierDTO();
		TelecomDTO telecom = new TelecomDTO();
		telecom.setCountryCode("33");
		telecom.setPhoneNumber("0606060606");
		search.setTelecom(telecom);
		request.setSearchIdentifier(search);

		ContextDTO context = new ContextDTO();
		context.setRequestor(requestor);
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		request.setContext(context);

		IndividualMulticriteriaDTO individual1 = new IndividualMulticriteriaDTO();
		IndividualMulticriteriaDTO individual2 = new IndividualMulticriteriaDTO();
		IndividualMulticriteriaDTO individual3 = new IndividualMulticriteriaDTO();

		SearchIndividualByMulticriteriaResponseDTO searchIndividualByMulticriteriaResponseDTO = new SearchIndividualByMulticriteriaResponseDTO();
		searchIndividualByMulticriteriaResponseDTO.setIndividuals( new HashSet<>(Arrays.asList(individual1, individual2, individual3)));

		individuDSMock = Mockito.mock(IndividuDS.class);
		Mockito.doReturn(searchIndividualByMulticriteriaResponseDTO).when(individuDSMock).searchIndividual(Mockito.any(SearchIndividualByMulticriteriaRequestDTO.class));

		searchIndividualDSFull.setIndividuDS(individuDSMock);

		try {
			searchIndividualDSFull.searchIndividual(request);
			fail("EXCEPTION_WAS_SUPPOSED_TO_BE_THROWN");
		} catch (BusinessExceptionDTO ex) {
			BusinessErrorDTO faultInfo = ex.getFaultInfo();
			Assert.assertEquals("133", faultInfo.getErrorCode());
			Assert.assertEquals("RESPONSE TYPE SET TO \"UNIQUE\", YET MULTIPLE RESULTS FOUND", faultInfo.getFaultDescription());
		}
	}

	@Test(expected = Test.None.class /* no exception expected */)
    public void test_filteringIndividuByFBTierMATraveler() throws JrafDomainException, BusinessExceptionDTO, SystemException, ParseException {
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	RequestDTO request = new RequestDTO();
    	RequestorDTO requestor = new RequestorDTO();
    	requestor.setApplicationCode("Test");
    	requestor.setChannel("B2C");
    	requestor.setSignature("Test");
    	requestor.setSite("QVI");
    	
    	request.setIndex(1);
    	request.setProcessType("A");

    	SearchIdentifierDTO search = new SearchIdentifierDTO();
    	TelecomDTO telecom = new TelecomDTO();
    	telecom.setCountryCode("33");
    	telecom.setPhoneNumber("0676831233");
    	search.setTelecom(telecom);
    	request.setSearchIdentifier(search);
    	
    	ContextDTO context = new ContextDTO();
    	context.setRequestor(requestor);
    	context.setTypeOfSearch("I");
    	context.setResponseType("U");
    	request.setContext(context);
    	
    	SearchIndividualByMulticriteriaResponseDTO searchIndividualByMulticriteriaResponseDTO = new SearchIndividualByMulticriteriaResponseDTO();
    	searchIndividualByMulticriteriaResponseDTO.setIndividuals(new HashSet<IndividualMulticriteriaDTO>());
    	
    	/*------INDIVIDU 1 ------*/
    	IndividualMulticriteriaDTO individualMulticriteriaDTO1 = new IndividualMulticriteriaDTO();
    	individualMulticriteriaDTO1.setRelevance("70");
    	
    	IndividuDTO individu1 = new IndividuDTO();
    	individu1.setRolecontratsdto(new HashSet<RoleContratsDTO>());

    	RoleContratsDTO contract1Individu1 = new RoleContratsDTO();
    	contract1Individu1.setDateCreation(sdf.parse("2017-01-01"));
    	contract1Individu1.setTypeContrat("MA");
    	contract1Individu1.setEtat("C");
    	individu1.getRolecontratsdto().add(contract1Individu1);
    	
    	RoleContratsDTO contract2Individu1 = new RoleContratsDTO();
    	contract2Individu1.setDateCreation(sdf.parse("2017-01-01"));
    	contract2Individu1.setDateDebutValidite(sdf.parse("2017-01-01"));
    	contract2Individu1.setTypeContrat("FP");
    	contract2Individu1.setEtat("C");
    	contract2Individu1.setTier("A");
    	individu1.getRolecontratsdto().add(contract2Individu1);
    	
    	individu1.setType("I");
    	individu1.setStatutIndividu("V");
    	individu1.setDateCreation(sdf.parse("2000-01-01"));
    	individualMulticriteriaDTO1.setIndividu(individu1);
    	searchIndividualByMulticriteriaResponseDTO.getIndividuals().add(individualMulticriteriaDTO1);
    	
    	/*------INDIVIDU 2 ------*/
    	IndividualMulticriteriaDTO individualMulticriteriaDTO2 = new IndividualMulticriteriaDTO();
    	individualMulticriteriaDTO2.setRelevance("70");
    	
    	IndividuDTO individu2 = new IndividuDTO();
    	individu2.setRolecontratsdto(new HashSet<RoleContratsDTO>());

    	RoleContratsDTO contract1Individu2 = new RoleContratsDTO();
    	contract1Individu2.setDateCreation(sdf.parse("2017-01-01"));
    	contract1Individu2.setTypeContrat("MA");
    	contract1Individu2.setEtat("C");
    	individu2.getRolecontratsdto().add(contract1Individu2);
    	
    	RoleContratsDTO contract2Individu2 = new RoleContratsDTO();
    	contract2Individu2.setDateCreation(sdf.parse("2017-01-01"));
    	contract2Individu2.setDateDebutValidite(sdf.parse("2017-01-01"));
    	contract2Individu2.setTypeContrat("FP");
    	contract2Individu2.setEtat("C");
    	contract2Individu2.setTier("A");
    	individu2.getRolecontratsdto().add(contract2Individu2);
    	
    	individu2.setType("I");
    	individu2.setStatutIndividu("V");
    	individu2.setDateCreation(sdf.parse("2000-01-01"));
    	individualMulticriteriaDTO2.setIndividu(individu2);
    	searchIndividualByMulticriteriaResponseDTO.getIndividuals().add(individualMulticriteriaDTO2);
    	
    	/*------INDIVIDU 3 ------*/
    	IndividualMulticriteriaDTO individualMulticriteriaDTO3 = new IndividualMulticriteriaDTO();
    	individualMulticriteriaDTO3.setRelevance("70");
    	
    	IndividuDTO individu3 = new IndividuDTO();
    	individu3.setRolecontratsdto(new HashSet<RoleContratsDTO>());

    	RoleContratsDTO contract1Individu3 = new RoleContratsDTO();
    	contract1Individu3.setDateCreation(sdf.parse("2017-01-01"));
    	contract1Individu3.setTypeContrat("MA");
    	contract1Individu3.setEtat("C");
    	individu3.getRolecontratsdto().add(contract1Individu3);
    	
    	RoleContratsDTO contract2Individu3 = new RoleContratsDTO();
    	contract2Individu3.setDateCreation(sdf.parse("2017-01-01"));
    	contract2Individu3.setDateDebutValidite(sdf.parse("2017-01-01"));
    	contract2Individu3.setTypeContrat("FP");
    	contract2Individu3.setEtat("C");
    	contract2Individu3.setTier("A");
    	individu3.getRolecontratsdto().add(contract2Individu3);
    	
    	individu3.setType("T");
    	individu3.setStatutIndividu("V");
    	individu3.setDateCreation(sdf.parse("2000-01-01"));
    	individualMulticriteriaDTO3.setIndividu(individu3);
    	searchIndividualByMulticriteriaResponseDTO.getIndividuals().add(individualMulticriteriaDTO3);
    	
    	individuDSMock = Mockito.mock(IndividuDS.class);
		/*Mockito.doReturn(searchIndividualByMulticriteriaResponseDTO).when(individuDSMock).searchIndividual(Mockito.any(SearchIndividualByMulticriteriaRequestDTO.class));
		
		searchIndividualDSFull.setIndividuDS(individuDSMock);
		
		ResponseDTO responseDTO = searchIndividualDSFull.searchIndividual(request);
		Assert.assertEquals(1, responseDTO.getCustomers().size());*/
    }
    
    @Test
    public void test_filteringOnFBContract() throws ParseException {
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	Set<IndividualMulticriteriaDTO> individus = new HashSet<IndividualMulticriteriaDTO>();
    	
    	IndividualMulticriteriaDTO individual1 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu1 = new IndividuDTO();
    	individu1.setRolecontratsdto(new HashSet<RoleContratsDTO>());
    	RoleContratsDTO contractIndividu1 = new RoleContratsDTO();
    	contractIndividu1.setDateCreation(sdf.parse("2017-09-01"));
    	contractIndividu1.setTypeContrat("MA");
    	contractIndividu1.setEtat("C");
    	individu1.getRolecontratsdto().add(contractIndividu1);
    	individual1.setIndividu(individu1);
    	individus.add(individual1);
    	
    	IndividualMulticriteriaDTO individual2 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu2 = new IndividuDTO();
    	individu2.setRolecontratsdto(new HashSet<RoleContratsDTO>());
    	RoleContratsDTO contractIndividu2 = new RoleContratsDTO();
    	contractIndividu2.setDateCreation(sdf.parse("2017-09-01"));
    	contractIndividu2.setDateDebutValidite(sdf.parse("2017-09-01"));
    	contractIndividu2.setTypeContrat("FP");
    	contractIndividu2.setEtat("C");
    	contractIndividu2.setTier("A");
    	individu2.getRolecontratsdto().add(contractIndividu2);
    	individual2.setIndividu(individu2);
    	individus.add(individual2);
    	
    	IndividualMulticriteriaDTO individual3 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu3 = new IndividuDTO();
    	individu3.setRolecontratsdto(new HashSet<RoleContratsDTO>());
    	RoleContratsDTO contractIndividu3 = new RoleContratsDTO();
    	contractIndividu3.setDateCreation(sdf.parse("2017-09-01"));
    	contractIndividu3.setDateDebutValidite(sdf.parse("2017-09-01"));
    	contractIndividu3.setTypeContrat("FP");
    	contractIndividu3.setEtat("C");
    	contractIndividu3.setTier("R");
    	individu3.getRolecontratsdto().add(contractIndividu3);
    	individual3.setIndividu(individu3);
    	individus.add(individual3);
    	
    	IndividualMulticriteriaDTO individual4 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu4 = new IndividuDTO();
    	individu4.setRolecontratsdto(new HashSet<RoleContratsDTO>());
    	RoleContratsDTO contractIndividu4 = new RoleContratsDTO();
    	contractIndividu4.setDateCreation(sdf.parse("2017-01-01"));
    	contractIndividu4.setDateDebutValidite(sdf.parse("2017-01-01"));
    	contractIndividu4.setTypeContrat("FP");
    	contractIndividu4.setEtat("C");
    	contractIndividu4.setTier("R");
    	individu4.getRolecontratsdto().add(contractIndividu4);
    	individual4.setIndividu(individu4);
    	individus.add(individual4);
    	
    	try {
        	SearchIndividualDS searchIndividualDS = new SearchIndividualDS();
        	Method method = SearchIndividualDS.class.getDeclaredMethod("filteringOnFBContract", Set.class);
        	method.setAccessible(true);
        	method.invoke(searchIndividualDS, individus);
        	
        	Assert.assertEquals(1, individus.size());
    	} catch (InvocationTargetException e) {
    	    e.printStackTrace();
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    }
    
    @Test
    public void test_filteringOnMAContract() throws ParseException {
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	Set<IndividualMulticriteriaDTO> individus = new HashSet<IndividualMulticriteriaDTO>();
    	
    	IndividualMulticriteriaDTO individual1 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu1 = new IndividuDTO();
    	individual1.setIndividu(individu1);
    	individus.add(individual1);
    	
    	IndividualMulticriteriaDTO individual2 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu2 = new IndividuDTO();
    	individu2.setRolecontratsdto(new HashSet<RoleContratsDTO>());
    	RoleContratsDTO contractIndividu2 = new RoleContratsDTO();
    	contractIndividu2.setDateCreation(sdf.parse("2017-09-01"));
    	contractIndividu2.setTypeContrat("MA");
    	contractIndividu2.setEtat("C");
    	individu2.getRolecontratsdto().add(contractIndividu2);
    	individual2.setIndividu(individu2);
    	individus.add(individual2);
    	
    	IndividualMulticriteriaDTO individual3 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu3 = new IndividuDTO();
    	individu3.setRolecontratsdto(new HashSet<RoleContratsDTO>());
    	RoleContratsDTO contractIndividu3 = new RoleContratsDTO();
    	contractIndividu3.setDateCreation(sdf.parse("2017-01-01"));
    	contractIndividu3.setTypeContrat("MA");
    	contractIndividu3.setEtat("C");
    	individu3.getRolecontratsdto().add(contractIndividu3);
    	individual3.setIndividu(individu3);
    	individus.add(individual3);
    	
    	IndividualMulticriteriaDTO individual4 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu4 = new IndividuDTO();
    	individu4.setRolecontratsdto(new HashSet<RoleContratsDTO>());
    	RoleContratsDTO contractIndividu4 = new RoleContratsDTO();
    	contractIndividu4.setDateCreation(sdf.parse("2017-01-01"));
    	contractIndividu4.setTypeContrat("MA");
    	contractIndividu4.setEtat("C");
    	individu4.getRolecontratsdto().add(contractIndividu4);
    	individual4.setIndividu(individu4);
    	individus.add(individual4);
    	
    	try {
        	SearchIndividualDS searchIndividualDS = new SearchIndividualDS();
        	Method method = SearchIndividualDS.class.getDeclaredMethod("filteringOnMAContract", Set.class);
        	method.setAccessible(true);
        	method.invoke(searchIndividualDS, individus);
        	
        	Assert.assertEquals(1, individus.size());
    	} catch (InvocationTargetException e) {
    	    e.printStackTrace();
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    }
    
    @Test
    public void test_filteringOnNoTraveler() throws ParseException {
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	Set<IndividualMulticriteriaDTO> individus = new HashSet<IndividualMulticriteriaDTO>();
    	
    	IndividualMulticriteriaDTO individual1 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu1 = new IndividuDTO();
    	individu1.setType("T");
    	individual1.setIndividu(individu1);
    	individus.add(individual1);
    	
    	IndividualMulticriteriaDTO individual2 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu2 = new IndividuDTO();
    	individu2.setType("I");
    	individu2.setDateCreation(sdf.parse("2017-06-01"));
    	individual2.setIndividu(individu2);
    	individus.add(individual2);
    	
    	IndividualMulticriteriaDTO individual3 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu3 = new IndividuDTO();
    	individu3.setType("I");
    	individu3.setDateCreation(sdf.parse("2017-01-01"));
    	individual3.setIndividu(individu3);
    	individus.add(individual3);
    	    	
    	try {
        	SearchIndividualDS searchIndividualDS = new SearchIndividualDS();
        	Method method = SearchIndividualDS.class.getDeclaredMethod("filteringOnNoTraveler", Set.class);
        	method.setAccessible(true);
        	method.invoke(searchIndividualDS, individus);
        	
        	Assert.assertEquals(1, individus.size());
    	} catch (InvocationTargetException e) {
    	    e.printStackTrace();
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    }
    
    @Test
    public void test_filteringOnTraveler() throws ParseException {
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	Set<IndividualMulticriteriaDTO> individus = new HashSet<IndividualMulticriteriaDTO>();
    	
    	IndividualMulticriteriaDTO individual1 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu1 = new IndividuDTO();
    	individu1.setType("T");
    	individu1.setDateCreation(sdf.parse("2017-01-01"));
    	individual1.setIndividu(individu1);
    	individus.add(individual1);
    	
    	IndividualMulticriteriaDTO individual2 = new IndividualMulticriteriaDTO();
    	IndividuDTO individu2 = new IndividuDTO();
    	individu2.setType("T");
    	individu2.setDateCreation(sdf.parse("2017-06-01"));
    	individual2.setIndividu(individu2);
    	individus.add(individual2);
    	
    	    	
    	try {
        	SearchIndividualDS searchIndividualDS = new SearchIndividualDS();
        	Method method = SearchIndividualDS.class.getDeclaredMethod("filteringOnTraveler", Set.class);
        	method.setAccessible(true);
        	method.invoke(searchIndividualDS, individus);
        	
        	Assert.assertEquals(1, individus.size());
    	} catch (InvocationTargetException e) {
    	    e.printStackTrace();
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    }
}
