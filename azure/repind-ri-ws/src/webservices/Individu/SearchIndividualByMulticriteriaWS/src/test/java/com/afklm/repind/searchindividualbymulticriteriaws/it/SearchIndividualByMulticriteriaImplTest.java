package com.afklm.repind.searchindividualbymulticriteriaws.it;

import com.afklm.repind.searchindividualbymulticriteriaws.SearchIndividualByMulticriteriaImpl;
import com.afklm.soa.stubs.w001271.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaRequest;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaResponse;
import com.afklm.soa.stubs.w001271.v2.request.Contact;
import com.afklm.soa.stubs.w001271.v2.request.Identity;
import com.afklm.soa.stubs.w001271.v2.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w001271.v2.siccommontype.Requestor;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.IndividualMulticriteriaDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaRequestDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaResponseDTO;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import org.apache.commons.lang.RandomStringUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class SearchIndividualByMulticriteriaImplTest extends SearchIndividualByMulticriteriaImpl{

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T490195";
	private static final String SITE = "QVI";
	private static final String APPLICATION_CODE = "GP";
	private static final String FB_NUMBER_SAMPLE = "002053750936";
	private static final String GIN = "400155458745";
	private static final String EMAIL = "test@airfrance.fr";
	private static final String PHONE = "0991121804";

	private SearchIndividualByMulticriteriaRequest request;

	@Before
	public void setUp() throws JrafDomainException {
        //Preparing the request
        Requestor requestor = new Requestor();
        requestor.setChannel(CHANNEL);
        requestor.setSignature(SIGNATURE);
        requestor.setSite(SITE);
        requestor.setApplicationCode(APPLICATION_CODE);
        request = new SearchIndividualByMulticriteriaRequest();
        request.setRequestor(requestor);
	}

	@Test
	public void testSearchIndividualByMulticriteriaMissingApplicationCode() throws BusinessErrorBlocBusinessException {
		Requestor requestor = request.getRequestor();
		requestor.setApplicationCode(null);
		try {
			this.searchIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERR_133);
		}
	}

	@Test
	public void testSearchIndividualByMulticriteriaMissingFirstName() {
		Identity identity = new Identity();
		identity.setLastName("test");
		request.setIdentity(identity);
		try {
			this.searchIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERR_104);
		}
	}

	@Test
	public void testSearchIndividualByMulticriteriaMissingLastName() {
		Identity identity = new Identity();
		identity.setFirstName("test");
		request.setIdentity(identity);
		try {
			this.searchIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERR_103);
		}
	}

	@Test
	public void testSearchIndividualByMulticriteriaLastNameIsTooShort() {
		Identity identity = new Identity();
		identity.setFirstName("test");
		identity.setLastName("t");
		request.setIdentity(identity);
		try {
			this.searchIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERR_133);
		}
	}

	@Test
	public void testSearchIndividualByMulticriteriaFirstNameAndLastNameIsTooShort() {
		Identity identity = new Identity();
		identity.setFirstName("t");
		identity.setLastName("t");
		request.setIdentity(identity);
		try {
			this.searchIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERR_133);
		}
	}

	@Test
	public void testSearchIndividualByMulticriteriaMissingLastNameAndFirstNameAndEmailAndPhone() {
		Identity identity = new Identity();
		request.setIdentity(identity);
		try {
			this.searchIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERR_133);
		}
	}

	@Test
	public void testSearchIndividualByMulticriteriaWithValidEmail() throws BusinessErrorBlocBusinessException, JrafDomainException {
		Contact contact = new Contact();
	    contact.setEmail(EMAIL);
	    request.setContact(contact);
	    createMockIndividuDS();
		SearchIndividualByMulticriteriaResponse response = this.searchIndividual(request);
		Assert.assertNotNull(response.getIndividual());
	}

	@Test
	public void testSearchIndividualByMulticriteriaWithValidFirstNameAndLastName() throws BusinessErrorBlocBusinessException, JrafDomainException {
		Identity identity = new Identity();
		identity.setFirstName("test");
		identity.setLastName("test");
		request.setIdentity(identity);
	    createMockIndividuDS();
		SearchIndividualByMulticriteriaResponse response = this.searchIndividual(request);
		Assert.assertNotNull(response.getIndividual());
	}

	@Test
	public void testSearchIndividualByMulticriteriaWithValidPhone() throws BusinessErrorBlocBusinessException, JrafDomainException {
		Contact contact = new Contact();
	    contact.setPhoneNumber(PHONE);
	    request.setContact(contact);
	    createMockIndividuDS();
		SearchIndividualByMulticriteriaResponse response = this.searchIndividual(request);
		Assert.assertNotNull(response.getIndividual());
	}

	@Test
	public void testSearchIndividualByMulticriteriaNotFoundException() throws JrafDomainException {
		Contact contact = new Contact();
	    contact.setPhoneNumber(PHONE);
	    request.setContact(contact);
	    request.setPopulationTargeted("I");
	    createMockIndividuDS(false);
		try {
			this.searchIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(),  BusinessErrorCodeEnum.ERR_001);
		}
	}

	@Test
	public void testSearchIndividualByMulticriteriaToManyResultFound() throws JrafDomainException {
		Contact contact = new Contact();
	    contact.setPhoneNumber(PHONE);
	    request.setContact(contact);
	    createMockIndividuDS(true, 255, false);
		try {
			this.searchIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(),  BusinessErrorCodeEnum.ERR_246);
		}
	}

	@Test
	public void testSearchIndividualByMulticriteriaSearchForProspectWithPopulationTargetA() throws JrafDomainException {
		Contact contact = new Contact();
	    contact.setPhoneNumber(PHONE);
	    contact.setCountryCode("33");
	    request.setContact(contact);
	    request.setPopulationTargeted("A");	    
	    createMockIndividuDS(true, 0, true);
	    createMockTelecomDS();
		try {
			SearchIndividualByMulticriteriaResponse response = this.searchIndividual(request);
			Assert.assertNotNull(response.getIndividual());
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.fail();
		}
	}

	@Test
	public void testSearchIndividualByMulticriteriaSearchForProspectWithPopulationTargetANotFound() throws JrafDomainException {
		Contact contact = new Contact();
	    contact.setPhoneNumber(PHONE);
	    request.setContact(contact);
	    request.setPopulationTargeted("A");
	    createMockIndividuDS(false, 0, true, true);
		try {
			this.searchIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(),  BusinessErrorCodeEnum.ERR_001);
		}
	}

	@Test
	public void testSearchIndividualByMulticriteriaWithPopulationTargetW() throws JrafDomainException {
		Contact contact = new Contact();
	    contact.setPhoneNumber(PHONE);
	    request.setContact(contact);
	    request.setPopulationTargeted("W");
	    try {
			SearchIndividualByMulticriteriaResponse response = this.searchIndividual(request);
			Assert.assertNull(response);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.fail();
		}
	}

	private void createMockIndividuDS() throws JrafDomainException {
		createMockIndividuDS(true, 0, false, false);
	}

	private void createMockIndividuDS(Boolean mode) throws JrafDomainException {
		createMockIndividuDS(mode, 0, false, false);
	}

	private void createMockIndividuDS(Boolean mode, int nbIndividus, Boolean isEmptyIndividuals) throws JrafDomainException {
		createMockIndividuDS(mode, nbIndividus, isEmptyIndividuals, false);
	}

	private void createMockTelecomDS() throws JrafDomainException {

		TelecomDS telecomDS = EasyMock.createMock(TelecomDS.class);
		this.setTelecomDS(telecomDS);
				
		TelecomsDTO telecomDTO = EasyMock.anyObject(TelecomsDTO.class);
		
		TelecomsDTO telecomNDTO = new TelecomsDTO();
		telecomNDTO.setIsnormalized("Y");
		telecomNDTO.setSnormalized_country("33");
		telecomNDTO.setSnormalized_numero("0102030405");
		telecomNDTO.setSnorm_inter_country_code("FR");
		telecomNDTO.setSnorm_nat_phone_number_clean("+33102030405");
		
		EasyMock.expect(getTelecomDS().normalizePhoneNumber(telecomDTO)).andReturn(telecomNDTO);

		EasyMock.replay(getTelecomDS());
	}

	
	private void createMockIndividuDS(Boolean mode, int nbIndividus, Boolean isEmptyIndividuals, boolean isEmptyPospect) throws JrafDomainException {
		
		IndividuDS individuDS = EasyMock.createMock(IndividuDS.class);
		this.setIndividuDS(individuDS);
		
		SearchIndividualByMulticriteriaRequestDTO requeteDTO = EasyMock.anyObject(SearchIndividualByMulticriteriaRequestDTO.class);
		SearchIndividualByMulticriteriaResponseDTO result = new SearchIndividualByMulticriteriaResponseDTO();
		Set<IndividualMulticriteriaDTO> pIndividuals = new HashSet<IndividualMulticriteriaDTO>();
		if(!isEmptyIndividuals) {
			int i = 0;
			List<String> listOfGin = new ArrayList<String>();
			do {
				IndividualMulticriteriaDTO individualMultDTO = new IndividualMulticriteriaDTO();
				IndividuDTO pIndividu = new IndividuDTO();
				String gin = RandomStringUtils.randomNumeric(12);
				while (listOfGin.contains(gin)) {
					gin = RandomStringUtils.randomNumeric(12);
				}
				listOfGin.add(gin);
				pIndividu.setSgin(gin);
				pIndividu.setVersion(0);
				pIndividu.setNom("TEST");
				pIndividu.setSexe("M");
				pIndividu.setIdentifiantPersonnel(FB_NUMBER_SAMPLE);
				pIndividu.setDateNaissance(new GregorianCalendar(1980, 03, 25).getTime());
				pIndividu.setNationalite("FR");
				pIndividu.setSecondPrenom("");
				pIndividu.setPrenom("TEST");
				pIndividu.setNonFusionnable("N");
				pIndividu.setStatutIndividu("V");
				pIndividu.setTierUtiliseCommePiege("N");
				pIndividu.setCivilite("MR");
				pIndividu.setAliasNom1("");
				pIndividu.setAliasPrenom("");
				pIndividu.setNomSC("");
				pIndividu.setPrenomSC("");
				pIndividu.setCodeLangue("FR");
				individualMultDTO.setIndividu(pIndividu);
				pIndividuals.add(individualMultDTO );
				i++;
			} while (i <= nbIndividus);
		}
		result.setIndividuals(pIndividuals);
		if(mode) {
			EasyMock.expect(getIndividuDS().searchIndividual(requeteDTO)).andReturn(result);
		} else {
			EasyMock.expect(getIndividuDS().searchIndividual(requeteDTO)).andThrow(new NotFoundException("NOT FOUND"));
		}
		EasyMock.replay(getIndividuDS());
	}
}
