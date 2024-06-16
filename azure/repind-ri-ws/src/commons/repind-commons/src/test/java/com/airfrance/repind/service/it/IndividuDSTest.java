package com.airfrance.repind.service.it;


import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.ContactDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.RequestorDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaRequestDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.ws.IndividualInformationsDTO;
import com.airfrance.repind.dto.ws.IndividualProfilDTO;
import com.airfrance.repind.dto.ws.IndividualRequestDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.IndividuLight;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.internal.unitservice.individu.IndividuUS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class IndividuDSTest extends IndividuDS{

	/** logger */
	private static final Log LOG = LogFactory.getLog(IndividuDSTest.class);
	private static final String APPLICATION_CODE = "GP";
	private static final String EMAIL = "test@airfrance.fr";
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T490195";
	private static final String SITE = "QVI";
	private static final String PROCESS_TYPE = "A";
	private static final String INEXISTING_INDIVIDUAL_GIN = "12346";
	private static final String INDIVIDUAL_LIGHT_GIN = "456789123";

	@Autowired
	private IndividuDS individuDS;
	
	@Test
	@Rollback(true)
	public void testFindBy() {
		try {
			this.findBy("BOURDON", "YVES", "yves-bourdon@compuserve.com", false, false);
		} catch (JrafDomainException e) {
			Assert.fail(String.format("Erreur non prevue : %s", e.getMessage()));
		}
	}

	/*@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testFindByIdentifierFetchingRelationships1() {

		String pIdentifierType = "GIN";
		String pIdentifierValue = "400380208372";
		try {
			IndividuDTO dto = individuDS.findByIdentierFetchingRelationships(pIdentifierType, pIdentifierValue);
			IndividuDSTest.LOG.info(String.format("dto.getCommunicationpreferences().size() = %d", dto.getCommunicationpreferencesdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getDelegateList().size() = %d", dto.getDelegateListDTO().size()));
			IndividuDSTest.LOG.info(String.format("dto.getDelegatorList().size() = %d", dto.getDelegatorListDTO().size()));
			IndividuDSTest.LOG.info(String.format("dto.getEmail().size() = %d", dto.getEmaildto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getExternalIdentifierList().size() = %d", dto.getExternalIdentifierList().size()));
			IndividuDSTest.LOG.info(String.format("dto.getPostaladdress().size() = %d", dto.getPostaladdressdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getPrefilledNumbers().size() = %d", dto.getPrefilledNumbers().size()));
			IndividuDSTest.LOG.info(String.format("dto.getProfil_mere().size() = %d", dto.getProfil_meredto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getRolecontrats().size() = %d", dto.getRolecontratsdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getTelecoms().size() = %d", dto.getTelecoms().size()));

			for (ExternalIdentifierDTO eiDto : dto.getExternalIdentifierList()) {
				IndividuDSTest.LOG.info(String.format("eiDto.getExternalIdentifierDataList().size() = %d", eiDto.getExternalIdentifierDataList().size()));
			}
		} catch (JrafDomainException e) {
			Assert.fail(String.format("Erreur non prevue : %s", e.getMessage()));
		}
	}*/

	/*@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testFindByIdentifierFetchingRelationships2() {

		String pIdentifierType = "GIN";
		String pIdentifierValue = "400007222796";
		try {
			IndividuDTO dto = individuDS.findByIdentierFetchingRelationships(pIdentifierType, pIdentifierValue);
			IndividuDSTest.LOG.info(String.format("dto.getCommunicationpreferences().size() = %d", dto.getCommunicationpreferencesdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getDelegateList().size() = %d", dto.getDelegateListDTO().size()));
			IndividuDSTest.LOG.info(String.format("dto.getDelegatorList().size() = %d", dto.getDelegatorListDTO().size()));
			IndividuDSTest.LOG.info(String.format("dto.getEmail().size() = %d", dto.getEmaildto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getExternalIdentifierList().size() = %d", dto.getExternalIdentifierList().size()));
			IndividuDSTest.LOG.info(String.format("dto.getPostaladdress().size() = %d", dto.getPostaladdressdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getPrefilledNumbers().size() = %d", dto.getPrefilledNumbers().size()));
			IndividuDSTest.LOG.info(String.format("dto.getProfil_mere().size() = %d", dto.getProfil_meredto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getRolecontrats().size() = %d", dto.getRolecontratsdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getTelecoms().size() = %d", dto.getTelecoms().size()));

			for (ExternalIdentifierDTO eiDto : dto.getExternalIdentifierList()) {
				IndividuDSTest.LOG.info(String.format("eiDto.getExternalIdentifierDataList().size() = %d", eiDto.getExternalIdentifierDataList().size()));
			}
		} catch (JrafDomainException e) {
			Assert.fail(String.format("Erreur non prevue : %s", e.getMessage()));
		}
	}*/

	/*@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testFindByIdentifierFetchingRelationships3() {

		String pIdentifierType = "GIN";
		String pIdentifierValue = "400350914750";
		try {
			IndividuDTO dto = individuDS.findByIdentierFetchingRelationships(pIdentifierType, pIdentifierValue);
			IndividuDSTest.LOG.info(String.format("dto.getCommunicationpreferences().size() = %d", dto.getCommunicationpreferencesdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getDelegateList().size() = %d", dto.getDelegateListDTO().size()));
			IndividuDSTest.LOG.info(String.format("dto.getDelegatorList().size() = %d", dto.getDelegatorListDTO().size()));
			IndividuDSTest.LOG.info(String.format("dto.getEmail().size() = %d", dto.getEmaildto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getExternalIdentifierList().size() = %d", dto.getExternalIdentifierList().size()));
			IndividuDSTest.LOG.info(String.format("dto.getPostaladdress().size() = %d", dto.getPostaladdressdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getPrefilledNumbers().size() = %d", dto.getPrefilledNumbers().size()));
			IndividuDSTest.LOG.info(String.format("dto.getProfil_mere().size() = %d", dto.getProfil_meredto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getRolecontrats().size() = %d", dto.getRolecontratsdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getTelecoms().size() = %d", dto.getTelecoms().size()));

			for (ExternalIdentifierDTO eiDto : dto.getExternalIdentifierList()) {
				IndividuDSTest.LOG.info(String.format("eiDto.getExternalIdentifierDataList().size() = %d", eiDto.getExternalIdentifierDataList().size()));
			}
		} catch (JrafDomainException e) {
			Assert.fail(String.format("Erreur non prevue : %s", e.getMessage()));
		}
	}*/

	/*@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testFindByIdentifierFetchingRelationships4() {

		String pIdentifierType = "FP";
		String pIdentifierValue = "001619306845";
		try {
			IndividuDTO dto = individuDS.findByIdentierFetchingRelationships(pIdentifierType, pIdentifierValue);
			IndividuDSTest.LOG.info(String.format("dto.getCommunicationpreferences().size() = %d", dto.getCommunicationpreferencesdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getDelegateList().size() = %d", dto.getDelegateListDTO().size()));
			IndividuDSTest.LOG.info(String.format("dto.getDelegatorList().size() = %d", dto.getDelegatorListDTO().size()));
			IndividuDSTest.LOG.info(String.format("dto.getEmail().size() = %d", dto.getEmaildto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getExternalIdentifierList().size() = %d", dto.getExternalIdentifierList().size()));
			IndividuDSTest.LOG.info(String.format("dto.getPostaladdress().size() = %d", dto.getPostaladdressdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getPrefilledNumbers().size() = %d", dto.getPrefilledNumbers().size()));
			IndividuDSTest.LOG.info(String.format("dto.getProfil_mere().size() = %d", dto.getProfil_meredto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getRolecontrats().size() = %d", dto.getRolecontratsdto().size()));
			IndividuDSTest.LOG.info(String.format("dto.getTelecoms().size() = %d", dto.getTelecoms().size()));

			for (ExternalIdentifierDTO eiDto : dto.getExternalIdentifierList()) {
				IndividuDSTest.LOG.info(String.format("eiDto.getExternalIdentifierDataList().size() = %d", eiDto.getExternalIdentifierDataList().size()));
			}
		} catch (JrafDomainException e) {
			Assert.fail(String.format("Erreur non prevue : %s", e.getMessage()));
		}
	}*/

	@Test
	public void testRemoveTelex() {
		IndividuDTO individu = new IndividuDTO();
		Set<TelecomsDTO> telecoms = new HashSet<>();
		TelecomsDTO telecom1 = new TelecomsDTO();
		telecom1.setSterminal("X");
		telecoms.add(telecom1);

		telecom1 = new TelecomsDTO();
		telecom1.setSterminal("T");
		telecoms.add(telecom1);

		telecom1 = new TelecomsDTO();
		telecom1.setSterminal("X");
		telecoms.add(telecom1);

		telecom1 = new TelecomsDTO();
		telecom1.setSterminal("A");
		telecoms.add(telecom1);

		telecom1 = new TelecomsDTO();
		telecom1.setSterminal("X");
		telecoms.add(telecom1);

		individu.setTelecoms(telecoms);
		removeTelex(individu);

		List<TelecomsDTO> result = new ArrayList<>(individu.getTelecoms());
		Assert.assertEquals(2, result.size());
		for (TelecomsDTO element : result) {
			Assert.assertNotEquals("X", element.getSterminal());
		}
	}

	@Test
	public void testSearchIndividual() {
		SearchIndividualByMulticriteriaRequestDTO request = createSearchIndividualByMulticriteriaRequestDTO();
		try {
			this.setIndividuUS(EasyMock.createMock(IndividuUS.class));
			SearchIndividualByMulticriteriaRequestDTO requeteHomonymesDTO = EasyMock.anyObject(SearchIndividualByMulticriteriaRequestDTO.class);
			List<Individu> listIndividu = new ArrayList<>();
			listIndividu.add(createIndividu());
			EasyMock.expect(this.getIndividuUS().creerIndividusByNameSearch(requeteHomonymesDTO)).andReturn(listIndividu);
			EasyMock.replay(this.getIndividuUS());
			SearchIndividualByMulticriteriaResponseDTO response = this.searchIndividual(request);
			Assert.assertNotNull(response.getVisaKey());
			Assert.assertNotNull(response.getIndividuals());
		} catch (JrafDomainException e) {
			Assert.assertEquals(e.getMessage(), "Individual not found: No individual found");
		}
	}

	@Test
	public void testSearchIndividualNotFound() {
		SearchIndividualByMulticriteriaRequestDTO request = createSearchIndividualByMulticriteriaRequestDTO();
		try {
			this.setIndividuUS(EasyMock.createMock(IndividuUS.class));
			SearchIndividualByMulticriteriaRequestDTO requeteHomonymesDTO = EasyMock.anyObject(SearchIndividualByMulticriteriaRequestDTO.class);
			List<Individu> listIndividu = new ArrayList<>();
			EasyMock.expect(this.getIndividuUS().creerIndividusByNameSearch(requeteHomonymesDTO)).andReturn(listIndividu);
			EasyMock.replay(this.getIndividuUS());
			this.searchIndividual(request);
			Assert.fail();
		} catch (JrafDomainException e) {
			if(e instanceof NotFoundException) {
			}else{
				Assert.fail();
			}
		}
	}
	
	@Test
	public void testIsExisting(){
		try {
			Assert.assertFalse(individuDS.isExisting("000000000000"));
		} catch (JrafDomainException e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetIndividuLightByGin() {

		// Fail message
		final String failMessage = "No exception is supposed to be thrown!";

		// Prepare mocks
		IndividuLight individuLight = createIndividuLight();
		this.setIndividuRepository(EasyMock.createMock(IndividuRepository.class));
		EasyMock.expect(this.getIndividuRepository().findIndividualLightByGin(IndividuDSTest.INDIVIDUAL_LIGHT_GIN)).andReturn(individuLight);
		EasyMock.expect(this.getIndividuRepository().findIndividualLightByGin(IndividuDSTest.INEXISTING_INDIVIDUAL_GIN)).andReturn(null);
		EasyMock.replay(this.getIndividuRepository());
		
		// Test null gin
		try {
			individuDS.getIndividuLightByGin(null);
		} catch (JrafDomainException e) {
			System.out.println("Test OK");
		}

		try {
			// Test inexisting individual
			IndividuDTO result = this.getIndividuLightByGin(IndividuDSTest.INEXISTING_INDIVIDUAL_GIN);
			Assert.assertNull(result);

			// Test existing individual
			result = this.getIndividuLightByGin(IndividuDSTest.INDIVIDUAL_LIGHT_GIN);
			Assert.assertNotNull(result);
			Assert.assertEquals(individuLight.getNom(), result.getNom());
			Assert.assertEquals(individuLight.getPrenom(), result.getPrenom());
			Assert.assertEquals(individuLight.getSgin(), result.getSgin());
			Assert.assertEquals(individuLight.getCivilite(), result.getCivilite());
			Assert.assertEquals(individuLight.getSexe(), result.getSexe());
			Assert.assertEquals(individuLight.getType(), result.getType());
			Assert.assertEquals(individuLight.getSiteModification(), result.getSiteModification());
			Assert.assertEquals(individuLight.getSiteCreation(), result.getSiteCreation());
			Assert.assertEquals(individuLight.getSignatureCreation(), result.getSignatureCreation());
			Assert.assertEquals(individuLight.getSignatureModification(), result.getSignatureModification());
			Assert.assertEquals(individuLight.getDateModification().getTime() / 1000,
					result.getDateModification().getTime() / 1000);
			Assert.assertEquals(individuLight.getDateCreation().getTime() / 1000, result.getDateCreation().getTime() / 1000);

		} catch (JrafDomainException e) {
			Assert.fail(failMessage);
		}
	}
	

	private SearchIndividualByMulticriteriaRequestDTO createSearchIndividualByMulticriteriaRequestDTO() {
		SearchIndividualByMulticriteriaRequestDTO request = new SearchIndividualByMulticriteriaRequestDTO();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(IndividuDSTest.CHANNEL);
		requestor.setSignature(IndividuDSTest.SIGNATURE);
		requestor.setSite(IndividuDSTest.SITE);
		requestor.setApplicationCode(IndividuDSTest.APPLICATION_CODE);
		request.setRequestor(requestor);
		ContactDTO contact = new ContactDTO();
		contact.setEmail(IndividuDSTest.EMAIL);
		request.setContact(contact);
		request.setProcessType(IndividuDSTest.PROCESS_TYPE);
		return request;
	}

	private Individu createIndividu() {
		Individu individu = new Individu();
		individu.setSgin("");
		individu.setVersion(0);
		individu.setCivilite("");
		individu.setMotDePasse("");
		individu.setNom("");
		individu.setAlias("");
		individu.setPrenom("");
		individu.setSecondPrenom("");
		individu.setAliasPrenom("");
		individu.setSexe("");
		individu.setIdentifiantPersonnel("");
		individu.setDateNaissance(Calendar.getInstance().getTime());
		individu.setStatutIndividu("");
		individu.setCodeTitre("");
		individu.setNationalite("");
		individu.setAutreNationalite("");
		individu.setNonFusionnable("");
		individu.setSiteCreation("");
		individu.setSignatureCreation("");
		individu.setDateCreation(Calendar.getInstance().getTime());
		individu.setSiteModification("");
		individu.setSignatureModification("");
		individu.setDateModification(Calendar.getInstance().getTime());
		individu.setSiteFraudeur("");
		individu.setSignatureFraudeur("");
		individu.setDateModifFraudeur(Calendar.getInstance().getTime());
		individu.setSiteMotDePasse("");
		individu.setSignatureMotDePasse("");
		individu.setDateModifMotDePasse(Calendar.getInstance().getTime());
		individu.setFraudeurCarteBancaire("");
		individu.setTierUtiliseCommePiege("");
		individu.setAliasNom1("");
		individu.setAliasNom2("");
		individu.setAliasPrenom1("");
		individu.setAliasPrenom2("");
		individu.setAliasCivilite1("");
		individu.setAliasCivilite2("");
		individu.setIndicNomPrenom("");
		individu.setIndicNom("");
		individu.setIndcons("");
		individu.setGinFusion("");
		individu.setDateFusion(null);
		individu.setProvAmex("");
		individu.setCieGest("");
		individu.setType("");
		return individu;
	}


	private IndividuLight createIndividuLight() {

		IndividuLight individu = new IndividuLight();
		individu.setSgin("123");
		individu.setVersion(0);
		individu.setCivilite("MR");
		individu.setMotDePasse("mdp");
		individu.setNom("nom");
		individu.setAlias("alias");
		individu.setPrenom("prenom");
		individu.setSecondPrenom("sec pre");
		individu.setAliasPrenom("al pre");
		individu.setSexe("M");
		individu.setIdentifiantPersonnel("id perso");
		individu.setDateNaissance(Calendar.getInstance().getTime());
		individu.setStatutIndividu("v");
		individu.setCodeTitre("co");
		individu.setNationalite("TN");
		individu.setAutreNationalite("FR");
		individu.setNonFusionnable("Y");
		individu.setSiteCreation("site crea");
		individu.setSignatureCreation("sign crea");
		individu.setDateCreation(Calendar.getInstance().getTime());
		individu.setSiteModification("site modif");
		individu.setSignatureModification("sign modif");
		individu.setDateModification(Calendar.getInstance().getTime());
		individu.setSiteFraudeur("site fraude");
		individu.setSignatureFraudeur("signa fraude");
		individu.setDateModifFraudeur(Calendar.getInstance().getTime());
		individu.setSiteMotDePasse("site mdp");
		individu.setSignatureMotDePasse("sign mdp");
		individu.setDateModifMotDePasse(Calendar.getInstance().getTime());
		individu.setFraudeurCarteBancaire("fraude cb");
		individu.setTierUtiliseCommePiege("tiers piege");
		individu.setAliasNom1("alis nom 1");
		individu.setAliasNom2("alias nom 2");
		individu.setAliasPrenom1("alias prenom 1");
		individu.setAliasPrenom2("alias prenom 2");
		individu.setAliasCivilite1("MR");
		individu.setAliasCivilite2("MRS");
		individu.setIndicNomPrenom("indic prenom nom");
		individu.setIndicNom("indic  nom");
		individu.setIndcons("ind cons");
		individu.setGinFusion("123456");
		individu.setDateFusion(new Date(12489));
		individu.setProvAmex("prov amex");
		individu.setCieGest("cie gest");
		individu.setType("I");

		return individu;
	}
	
	@Test
	@Rollback(true)
	public void testCreateNewIndividual() {
		// Init input 
		com.airfrance.repind.dto.ws.RequestorDTO sign = getRequestorDTO();
		
		CreateUpdateIndividualRequestDTO req = new CreateUpdateIndividualRequestDTO();
		req.setIndividualRequestDTO(new IndividualRequestDTO());
		req.setRequestorDTO(sign);
		
		IndividualInformationsDTO info = getNewTestIndividual();
		req.getIndividualRequestDTO().setIndividualInformationsDTO(info);
		
		IndividualProfilDTO profil = getNewTestProfil();
		req.getIndividualRequestDTO().setIndividualProfilDTO(profil);
		
		// Start test
		try {
			CreateModifyIndividualResponseDTO resp = individuDS.createOrUpdateIndividual(req);
			Assert.assertNotNull(resp);
			Assert.assertNotNull(resp.getGin());
			
		} catch (JrafDomainException e) {
			Assert.fail();
		}
	}
	
	@Test
	@Rollback(true)
	public void testUpdateIndividual() {
		// Init input 
		com.airfrance.repind.dto.ws.RequestorDTO sign = getRequestorDTO();
		
		CreateUpdateIndividualRequestDTO req = new CreateUpdateIndividualRequestDTO();
		req.setIndividualRequestDTO(new IndividualRequestDTO());
		req.setRequestorDTO(sign);
		
		IndividualInformationsDTO info = getExistingTestIndividual();
		req.getIndividualRequestDTO().setIndividualInformationsDTO(info);
		
		IndividualProfilDTO profil = getNewTestProfil();
		req.getIndividualRequestDTO().setIndividualProfilDTO(profil);
		
		try {
			CreateModifyIndividualResponseDTO resp = individuDS.createOrUpdateIndividual(req);
			Assert.assertNotNull(resp);
			Assert.assertTrue(resp.getSuccess());
			
		} catch (JrafDomainException e) {
			Assert.fail();
		}
	}

	private IndividualInformationsDTO getExistingTestIndividual() {
		IndividualInformationsDTO resp = new IndividualInformationsDTO();
		
		resp.setIdentifier("400424668522");
		resp.setNationality("FR");
		resp.setStatus("V");
		resp.setCivility("MR");
		resp.setFirstNameSC("PrénomTest1");
		resp.setLastNameSC("NomTest2");
		resp.setLanguageCode("EN");
		
		return resp;
	}

	private com.airfrance.repind.dto.ws.RequestorDTO getRequestorDTO() {
		com.airfrance.repind.dto.ws.RequestorDTO resp = new com.airfrance.repind.dto.ws.RequestorDTO();
		
		resp.setApplicationCode("RPD");
		resp.setSignature("test");
		resp.setSite("QVI");
		
		return resp;
	}

	private IndividualProfilDTO getNewTestProfil() {
		IndividualProfilDTO resp = new IndividualProfilDTO();
		
		resp.setLanguageCode("FR");
		
		return resp;
	}

	private IndividualInformationsDTO getNewTestIndividual() {
		IndividualInformationsDTO resp = new IndividualInformationsDTO();
		
		resp.setGender("M");
		resp.setBirthDate(new Date());
		resp.setNationality("FR");
		resp.setStatus("V");
		resp.setCivility("MR");
		resp.setFirstNameSC("PrénomTest1");
		resp.setLastNameSC("NomTest2");
		resp.setLanguageCode("EN");
		
		return resp;
	}
}
