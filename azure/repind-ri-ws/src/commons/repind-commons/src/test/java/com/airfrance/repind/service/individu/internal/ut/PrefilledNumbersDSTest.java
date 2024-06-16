package com.airfrance.repind.service.individu.internal.ut;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.PrefilledNumbersDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.PrefilledNumbers;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.PrefilledNumbersDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
public class PrefilledNumbersDSTest extends PrefilledNumbersDS {

	private final String SAMPLE_GIN1 = "12345";
	private final String SAMPLE_GIN2 = "54321";
	private final String SAMPLE_NAME1 = "Brun";
	private final String SAMPLE_FIRSTNAME1 = "Mathias";
	private final String SAMPLE_FIRSTNAME2 = "Matthias";
	private final String SAMPLE_ERROR_MSG = "error";
	private final String SAMPLE_SAPHIR1 = "001220957095";
	private final String SAMPLE_SAPHIR2 = "001220957096";
	
	private final String SAPHIR_TYPE = "S";
	
	private Date startDateOK;
	private Date endDateOK;
	
	private Date startDateKO;
	private Date endDateKO;
	
	public PrefilledNumbersDSTest() {
		roleDS = EasyMock.createMock(RoleDS.class);
		individuDS = EasyMock.createMock(IndividuDS.class);
		individuRepository = EasyMock.createMock(IndividuRepository.class);
	}
	
	@Before
	public void setUp() {
	
		startDateOK = getDateFromToday(-1);
		endDateOK = getDateFromToday(1);
		
		startDateKO = getDateFromToday(1);
		endDateKO = getDateFromToday(2);
	}
	
	@After
	public void tearDown() {
		EasyMock.reset(individuRepository);
		EasyMock.reset(individuDS);
		EasyMock.reset(roleDS);
	}
	
	@Test
	public void testUpdatePrefilledNumbers() throws JrafDomainException {
	
		// From web service : SAPHIR 1
		List<PrefilledNumbersDTO> prefilledNumbersListFromWS = getPrefilledNumbersDTO(SAMPLE_SAPHIR1);
		
		// From database : SAPHIR 2
		Set<PrefilledNumbers> prefilledNumbersListFromDB = getPrefilledNumbers(SAMPLE_SAPHIR2);
		Individu individuFromDB = new Individu();
		individuFromDB.setPrefilledNumbers(prefilledNumbersListFromDB);

		EasyMock.expect(individuRepository.findById(SAMPLE_GIN1)).andReturn(Optional.of(individuFromDB));
		EasyMock.expect(individuRepository.saveAndFlush(individuFromDB)).andReturn(individuFromDB);
		EasyMock.replay(individuRepository);
		
		updatePrefilledNumbers(SAMPLE_GIN1, prefilledNumbersListFromWS);

		// Expected in database after update : SAPHIR 1
		Assert.assertEquals(SAMPLE_SAPHIR1,individuFromDB.getPrefilledNumbers().iterator().next().getContractNumber());
	}
	
	@Test
	public void testIsConsistentSaphirNumber() throws JrafDomainException {
		
		IndividuDTO individu = new IndividuDTO();
		individu.setSgin(SAMPLE_GIN1);
		
		RoleContratsDTO roleContract = new RoleContratsDTO();
		roleContract.setIndividudto(individu);
		
		individuDS = EasyMock.createMock(IndividuDS.class);
		
		IndividuDTO mockIndividu = EasyMock.anyObject(IndividuDTO.class);
		
		EasyMock.expect(individuDS.get(mockIndividu)).andStubReturn(individu);
		EasyMock.replay(individuDS);
		
		boolean result = isConsistentSaphirNumber(roleContract, SAMPLE_GIN1);
		
		Assert.assertTrue(result);
	}
	
	@Test
	public void testIsConsistentSaphirNumberFail() throws JrafDomainException {
		
		IndividuDTO individu = new IndividuDTO();
		individu.setSgin(SAMPLE_GIN1);
		
		JrafDomainException exception = new JrafDomainException(SAMPLE_ERROR_MSG);
		
		RoleContratsDTO roleContract = new RoleContratsDTO();
		roleContract.setIndividudto(individu);
		
		IndividuDTO mockIndividu = EasyMock.anyObject(IndividuDTO.class);
		
		EasyMock.expect(individuDS.get(mockIndividu)).andThrow(exception);
		EasyMock.replay(individuDS);
		
		boolean result = isConsistentSaphirNumber(roleContract, SAMPLE_GIN1);
		
		Assert.assertFalse(result);
	}
	
	@Test
	public void testIsValidRoleContract() {
		
		RoleContratsDTO roleContract = new RoleContratsDTO();
		
		roleContract.setDateDebutValidite(startDateOK);
		roleContract.setDateFinValidite(endDateOK);
		
		boolean result = isValidRoleContract(roleContract);
		
		Assert.assertTrue(result);
	}
	
	@Test
	public void testIsValidRoleContractFail() {
		
		RoleContratsDTO roleContract = new RoleContratsDTO();
		
		roleContract.setDateDebutValidite(startDateKO);
		roleContract.setDateFinValidite(endDateKO);
		
		boolean result = isValidRoleContract(roleContract);
		
		Assert.assertFalse(result);
	}
	
	@Test
	public void testIsSameIndividu() {
		
		IndividuDTO left = new IndividuDTO();
		IndividuDTO right = new IndividuDTO();
		
		left.setSgin(SAMPLE_GIN1);
		right.setSgin(SAMPLE_GIN2);
		
		left.setNom(SAMPLE_NAME1);
		right.setNom(SAMPLE_NAME1);
		
		left.setPrenom(SAMPLE_FIRSTNAME1);
		right.setPrenom(SAMPLE_FIRSTNAME2);
		
		boolean result = isSameIndividu(left,right);
		
		Assert.assertTrue(result);
	}
	
	@Test
	public void testIsSameIndividuGin() {
		
		IndividuDTO left = new IndividuDTO();
		IndividuDTO right = new IndividuDTO();
		
		left.setSgin(SAMPLE_GIN1);
		right.setSgin(SAMPLE_GIN1);
		
		boolean result = isSameIndividu(left,right);
		
		Assert.assertTrue(result);
	}
	
	@Test
	public void testIsSameIndividuFail() {
		
		IndividuDTO left = new IndividuDTO();
		IndividuDTO right = new IndividuDTO();
		
		left.setSgin(SAMPLE_GIN1);
		right.setSgin(SAMPLE_GIN2);
		
		left.setNom(SAMPLE_NAME1);
		right.setNom(SAMPLE_NAME1);
		
		boolean result = isSameIndividu(left,right);
		
		Assert.assertFalse(result);
	}
	
	private Date getDateFromToday(int nbDays) {
		
		Date today = new Date();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DATE,nbDays);

		return calendar.getTime();
	}
	
	private List<PrefilledNumbersDTO> getPrefilledNumbersDTO(String saphirNumber) {
		
		PrefilledNumbersDTO prefilledNumbers = new PrefilledNumbersDTO();
		prefilledNumbers.setContractNumber(saphirNumber);
		prefilledNumbers.setContractType(SAPHIR_TYPE);
		prefilledNumbers.setSgin(SAMPLE_GIN1);
		
		List<PrefilledNumbersDTO> prefilledNumbersList = new ArrayList<PrefilledNumbersDTO>();
		prefilledNumbersList.add(prefilledNumbers);
		
		return prefilledNumbersList;
	}
	
	private Set<PrefilledNumbers> getPrefilledNumbers(String saphirNumber) {
		
		PrefilledNumbers prefilledNumber = new PrefilledNumbers();
		prefilledNumber.setContractNumber(saphirNumber);
		prefilledNumber.setContractType(SAPHIR_TYPE);
		prefilledNumber.setSgin(SAMPLE_GIN1);
		
		Set<PrefilledNumbers> prefilledNumbersList = new HashSet<PrefilledNumbers>();
		prefilledNumbersList.add(prefilledNumber);
		
		return prefilledNumbersList;
	}
	
}
