package com.airfrance.repind.service.external.it;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.ref.type.external.ExternalIdentifierTypeEnum;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.ws.ExternalIdentifierRequestDTO;
import com.airfrance.repind.dto.ws.IndividualInformationsDTO;
import com.airfrance.repind.dto.ws.IndividualRequestDTO;
import com.airfrance.repind.dto.ws.RequestorDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.internal.unitservice.external.ExternalIdentifierUS;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import com.airfrance.repind.service.ws.internal.helpers.UltimateException;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ActiveProfiles("test")
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ExternalIdentifierDSTest extends ExternalIdentifierDS {
	
    private static final Log log = LogFactory.getLog(ExternalIdentifierDSTest.class);
    
    private ExternalIdentifierUS externalIdentifierUSMock;
    
    private IndividuDTO individuDTO;
    
    @Autowired
    private IndividuDS individuDS;
    
    @Autowired
    private IndividuRepository individuRepository;
    
    @Autowired
    private ExternalIdentifierDS externalIdentifierDS;
    
	@Autowired
	@Qualifier("entityManagerFactoryRepind")
	private EntityManager entityManager;


	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";

	@Autowired
	private CreateOrUpdateIndividualDS createOrUpdateDS;

	@Test
	public void testCreateOrUpdateIndividual_insert_PNM_less_10() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		String sgin = "920000007462";
		String identifier = "TEST";
		String type = ExternalIdentifierTypeEnum.PNM_ID.getType();

		boolean created = createOrUpdateAnIndividualForPNM(ProcessEnum.E.getCode(), sgin, identifier, type);
		List<ExternalIdentifierDTO> eid_after= findExternalIdentifierByGinAndType(sgin, type );

		Integer nb = eid_after.size();

		Assertions.assertTrue(created);
		Assertions.assertEquals("2", nb.toString());
	}

	@Test
	public void testCreateOrUpdateIndividual_insert_PNM_whenSizeEqual_10() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		String sgin = "400754353413";
		String identifier = "ABBBAAAA-F77D-4731-9C03-E25634E97144";
		String type = ExternalIdentifierTypeEnum.PNM_ID.getType();

		boolean updated = createOrUpdateAnIndividualForPNM(ProcessEnum.E.getCode(), sgin, identifier, type);

		List<ExternalIdentifierDTO> eid_after = findExternalIdentifierByGinAndType(sgin, type );

		Integer nb = eid_after.size();

		Assertions.assertTrue(updated);
		Assertions.assertEquals("10", nb.toString());
	}

	@Test
	public void testCreateOrUpdateIndividual_update_PNM_whenSizeEqual_10() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		String sgin = "400754353413";
		String identifier = "AAAAAAAA-F77D-4731-9C03-E25634E97144";
		String type = ExternalIdentifierTypeEnum.PNM_ID.getType();

		boolean updated = createOrUpdateAnIndividualForPNM(ProcessEnum.E.getCode(), sgin, identifier, type);

		List<ExternalIdentifierDTO> eid_after = findExternalIdentifierByGinAndType(sgin, type );

		Integer nb = eid_after.size();

		Assertions.assertTrue(updated);
		Assertions.assertEquals("10", nb.toString());
	}

	@Test
    public void testResetAllAlias() throws JrafDomainException {
    	log.info("Test Method ResetAllAlias");
    	
    	Individu individuToFound = new Individu();
    	individuToFound.setSgin("110000085041");
    	individuToFound.setType("I");
    	individuToFound.setStatutIndividu("V");
    	individuDTO = IndividuTransform.bo2Dto(individuRepository.findOne(Example.of(individuToFound)).get());
    	
    	log.info("Gin for test: " + individuDTO.getSgin());
    	
    	int nbExternalIdentifier = individuDTO.getExternalIdentifierList().size();
    	
    	log.info("Already External Identifier: " + nbExternalIdentifier);
    	
    	ExternalIdentifierDTO externalIdentifierDTO = new ExternalIdentifierDTO();
    	externalIdentifierDTO.setIdentifier("TEST");
    	Date date = new Date();
		externalIdentifierDTO.setLastSeenDate(date);
    	externalIdentifierDTO.setCreationDate(date);
    	externalIdentifierDTO.setCreationSignature("TEST");
    	externalIdentifierDTO.setCreationSite("TEST");
    	externalIdentifierDTO.setIndividu(individuDTO);
    	externalIdentifierDTO.setType("LA");
    	
		externalIdentifierUSMock = Mockito.mock(ExternalIdentifierUS.class);
		externalIdentifierDS.setExternalIdentifierUS(externalIdentifierUSMock);
    	
		Mockito.doReturn("alias_for_test").when(externalIdentifierUSMock).generateAlias();
		Mockito.doReturn("1234").when(externalIdentifierUSMock).sendAlias(Mockito.anyString());
    	
    	externalIdentifierDS.createWithLinkedData(externalIdentifierDTO);
    	
		//Clear the entityManager to close transaction, to get the new data into the individual before refreshing data 
		entityManager.clear();

		individuDTO = individuDS.refresh(individuDTO);


		int size = externalIdentifierDS.findExternalIdentifier("110000085041").size();
		ExternalIdentifierDTO externalIdentifierDTOResult = externalIdentifierDS.findByExample(externalIdentifierDTO)
				.get(0);
    	
    	log.info("New External Identifier: " + individuDTO.getExternalIdentifierList().size());
    	
		Assert.assertNotEquals(nbExternalIdentifier, individuDTO.getExternalIdentifierList().size());
    	
    	externalIdentifierDS.resetAllAlias(individuDTO.getSgin());
    	
    	individuDTO = individuDS.refresh(individuDTO);
    	
    	Assert.assertEquals(nbExternalIdentifier, individuDTO.getExternalIdentifierList().size());
    }

	private boolean createOrUpdateAnIndividualForPNM(String process, String numeroGin, String identifier, String type) throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		boolean update = false;

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(process);

		// Preparing the request
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);


		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();

		if (numeroGin != null && !"".equals(numeroGin)) {
			indInfo.setIdentifier(numeroGin);
			indInfo.setStatus("V");
			indInfo.setCivility("MR");
		}

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// External Identifier
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		com.airfrance.repind.dto.ws.ExternalIdentifierDTO ei = new com.airfrance.repind.dto.ws.ExternalIdentifierDTO();
		ei.setIdentifier(identifier);
		ei.setType(type);
		eir.setExternalIdentifierDTO(ei);


		request.setExternalIdentifierRequestDTO(new ArrayList<ExternalIdentifierRequestDTO>());
		request.getExternalIdentifierRequestDTO().add(eir);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		update = response.getSuccess();

		return update;
	}
    
}
