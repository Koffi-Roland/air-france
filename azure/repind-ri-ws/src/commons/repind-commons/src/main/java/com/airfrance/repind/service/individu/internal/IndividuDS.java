package com.airfrance.repind.service.individu.internal;

/*PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGw DS i) ENABLED START*/


import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.*;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.profil.ProfilsRepository;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.delegation.DelegationDataTransform;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.TelecomDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.adh.connexiondata.ConnexionDataRequestDTO;
import com.airfrance.repind.dto.individu.adh.connexiondata.ConnexionDataResponseDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.IndividualMulticriteriaDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaRequestDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResquestDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.EmailDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.profil.ProfilsDTO;
import com.airfrance.repind.dto.profil.ProfilsTransform;
import com.airfrance.repind.dto.role.*;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.IndividuLight;
import com.airfrance.repind.entity.profil.Profils;
import com.airfrance.repind.entity.refTable.RefTableREF_CIVILITE;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.entity.role.RoleGP;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.internal.unitservice.adresse.AdresseUS;
import com.airfrance.repind.service.internal.unitservice.individu.IndividuUS;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.service.reference.internal.RefLanguageDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.util.*;
import com.airfrance.repindutf8.dto.individu.KidSoloIndividuDTO;
import com.airfrance.repindutf8.util.SicUtf8StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IndividuDS {

	/** logger */
	private static final Log log = LogFactory.getLog(IndividuDS.class);

	/** the Entity Manager */
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	/** unit service : AdresseUS **/
	@Autowired
	private AdresseUS adresseUS;
	/** unit service : IndividuUS **/
	@Autowired
	private IndividuUS individuUS;

	@Autowired
	private PostalAddressDS postalAddressDS;

	@Autowired
	private EmailDS emailDS;

	/** main dao */
	@Autowired
	private IndividuRepository individuRepository;

	@Autowired
	private ProfilsRepository profilsRepository;

	@Autowired
	private VariablesRepository variableRepository;
	// REPIND-555 : For add prospect preference
	@Autowired
	@Qualifier("preferenceDS")
	private PreferenceDS preferenceDS;

	@Autowired
	private RefLanguageDS languageDS;

	@Autowired
	private CallersUtils callersUtils;

	private static final String URL_RCT_MERGE_TALEND = "https://dqcrmtac-rct.airfrance.fr/tac/metaServlet?";

	/* PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGw u var) ENABLED START */

	/** references on associated DAOs */
	@Autowired
	private PostalAddressRepository postalAddressRepository;

	/** Reference sur le unit service RoleDS **/
	@Autowired
	@Qualifier("roleDS")
	private RoleDS roleDS;

	/* PROTECTED REGION END */

	public static final  String _REF_P = "P";

	public static final String _REF_209 = "209";

	public static final String _REF_902 = "902";

	public static final String _REF_377 = "377";

	public static final String _REF_198 = "198";

	/**
	 * {@inheritDoc}
	 */

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(IndividuDTO individuDTO) throws JrafDomainException {

		/* PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGw DS-CM create) ENABLED START */
		Individu individu = null;

		// transformation dto -> bo
		individu = IndividuTransform.dto2Bo(individuDTO);

		// creation en base
		individu.setMandatoryDBFields();

		// Appel create de l'Abstract
		individuRepository.saveAndFlush(individu);

		// Version update and Id update if needed
		IndividuTransform.bo2Dto(individu, individuDTO);

		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(IndividuDTO dto) throws JrafDomainException {

		/* PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGw DS-CM remove) ENABLED START */
		Individu individu = null;
		// chargement du bo
		individu = individuRepository.findBySgin(dto.getSgin());

		// Checking the optimistic strategy
		if (!(individu.getVersion().equals(dto.getVersion()))) {
			throw new SimultaneousUpdateException("Simultaneous update on following individu: " + individu.getSgin());
		}

		// transformation dto -> bo
		IndividuTransform.dto2Bo(dto, individu);

		// suppression en base
		individuRepository.delete(individu);
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(String id) {
		individuRepository.deleteById(id);
	}

	/**
	 * {@inheritDoc}
	 */

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(IndividuDTO individuDTO) throws JrafDomainException {

		/* PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGw DS-CM update) ENABLED START */
		Individu individu = null;

		// chargement du bo
		individu = individuRepository.findBySgin(individuDTO.getSgin());

		// Checking the optimistic strategy
		if (!(individu.getVersion().equals(individuDTO.getVersion()))) {
			throw new SimultaneousUpdateException("Simultaneous update on following individu: " + individu.getSgin());
		}
		// transformation dto -> bo
		IndividuTransform.dto2Bo(individuDTO, individu);

		/* PROTECTED REGION END */

	}

	/**
	 * 
	 * @param gin
	 * @return Prospect with a valid status
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly = true)
	public IndividuDTO getProspectByGin(String gin) throws JrafDomainException {
		/* PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGw DS-CM get) ENABLED START */
		Individu individu = null;
		IndividuDTO individuDTO = null;
		// get en base
		individu = individuRepository.findBySgin(gin);

		// transformation bo -> dto
		if (individu != null) {

			if (individu.getType().equals("W") && !"X".equalsIgnoreCase(individu.getStatutIndividu())) {

				individuDTO = IndividuTransform.bo2Dto(individu);

				if (individuDTO != null && individuDTO.getProfilsdto() != null
						&& individuDTO.getProfilsdto().getScode_langue() != null
						&& (individuDTO.getCodeLangue() == null || "".equals(individuDTO.getCodeLangue()))) {
					individuDTO.setCodeLangue(individuDTO.getProfilsdto().getScode_langue());
				}
			}
		}
		return individuDTO;
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public IndividuDTO getIndProspectByGin(String gin) throws JrafDomainException {
		/* PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGw DS-CM get) ENABLED START */
		Individu individu = null;
		IndividuDTO individuDTO = null;
		// get en base
		individu = individuRepository.findBySgin(gin);

		// transformation bo -> dto
		if (individu != null) {

			if (individu.getType().equals("W") || (!"X".equalsIgnoreCase(individu.getStatutIndividu())
					&& !"F".equalsIgnoreCase(individu.getStatutIndividu()))) {

				individuDTO = IndividuTransform.bo2Dto(individu);

				if (individuDTO != null && individuDTO.getProfilsdto() != null
						&& individuDTO.getProfilsdto().getScode_langue() != null
						&& (individuDTO.getCodeLangue() == null || "".equals(individuDTO.getCodeLangue()))) {
					individuDTO.setCodeLangue(individuDTO.getProfilsdto().getScode_langue());
				}
			}
		}
		return individuDTO;
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */

	@Transactional(readOnly = true)
	public IndividuDTO get(IndividuDTO dto) throws JrafDomainException {
		/* PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGw DS-CM get) ENABLED START */
		IndividuDTO individuDTO = null;

		// REPIND-1312 : Check if we will get data from OSIRIS or HACHIKO
		boolean isFBRecognitionActivate = false;

		// get en base
		Optional<Individu> individu = individuRepository.findById(dto.getSgin());

		// transformation bo -> dto
		if (individu.isPresent()) {

			// Do not return a deleted Individual
			// REPIND-946 : do not return forgotten individual (status 'F')
			if (!"X".equalsIgnoreCase(individu.get().getStatutIndividu())
					&& !"F".equalsIgnoreCase(individu.get().getStatutIndividu())) {
				// TODO : Replace this check by a WHERE close - Check the type (I for
				// Individual, T for Traveler)
				// Type in the request must be the same as type in the response
				if ("A".equals(dto.getType()) || (dto.getType() == null)
						|| (dto.getType() != null && dto.getType().equals(individu.get().getType()))) {
					individuDTO = IndividuTransform.bo2Dto(individu.get(), isFBRecognitionActivate);

				}

				if (individuDTO != null && individuDTO.getProfilsdto() != null
						&& individuDTO.getProfilsdto().getScode_langue() != null
						&& (individuDTO.getCodeLangue() == null || "".equals(individuDTO.getCodeLangue()))) {
					individuDTO.setCodeLangue(individuDTO.getProfilsdto().getScode_langue());
				}
			}
		}
		return individuDTO;
		/* PROTECTED REGION END */
	}

	@Transactional(readOnly = true)
	public IndividuDTO getIndividualOnlyByGin(String gin) throws JrafDomainException {

		if (StringUtils.isEmpty(gin)) {
			throw new IllegalArgumentException("Unable to get individual with empty gin");
		}

		IndividuDTO indDTO = new IndividuDTO();
		indDTO.setSgin(gin);

		return getIndividualOnly(indDTO);
	}

	@Transactional(readOnly = true)
	public IndividuDTO getIndividualOnly(IndividuDTO dto) throws JrafDomainException {
		IndividuDTO individuDTO = null;

		// get en base
		Optional<Individu> individu = individuRepository.findById(dto.getSgin());

		// transformation bo -> dto
		if (individu.isPresent()) {

			// Do not return a deleted Individual
			// REPIND-946 : do not return forgotten individual (status 'F')
			if (!"X".equalsIgnoreCase(individu.get().getStatutIndividu())
					&& !"F".equalsIgnoreCase(individu.get().getStatutIndividu())) {
				// TODO : Replace this check by a WHERE close - Check the type (I for
				// Individual, T for Traveler)
				// Type in the request must be the same as type in the response
				if ("A".equals(dto.getType()) || (dto.getType() == null)
						|| (dto.getType() != null && dto.getType().equals(individu.get().getType()))) {
					individuDTO = IndividuTransform.bo2DtoLightWithProfil(individu.get());

				}

				if (individuDTO != null && individuDTO.getProfilsdto() != null
						&& individuDTO.getProfilsdto().getScode_langue() != null
						&& (individuDTO.getCodeLangue() == null || "".equals(individuDTO.getCodeLangue()))) {
					individuDTO.setCodeLangue(individuDTO.getProfilsdto().getScode_langue());
				}
			}
		}
		return individuDTO;
	}

	/**
	 * {@inheritDoc}
	 */

	@Transactional(readOnly = true)
	public IndividuDTO getAll(IndividuDTO dto) throws JrafDomainException {
		/* PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGw DS-CM get) ENABLED START */
		IndividuDTO individuDTO = null;
		// get en base
		Optional<Individu> individu = individuRepository.findById(dto.getSgin());

		// transformation bo -> dto
		if (individu.isPresent()) {
			// TODO : Replace this check by a WHERE close - Check the type (I for
			// Individual, T for Traveler)
			// Type in the request must be the same as type in the response
			if ("A".equals(dto.getType()) || (dto.getType() == null)
					|| (dto.getType() != null && dto.getType().equals(individu.get().getType()))) {
				individuDTO = IndividuTransform.bo2Dto(individu.get());
			}

			if (individuDTO != null && individuDTO.getProfilsdto() != null
					&& individuDTO.getProfilsdto().getScode_langue() != null
					&& (individuDTO.getCodeLangue() == null || "".equals(individuDTO.getCodeLangue()))) {
				individuDTO.setCodeLangue(individuDTO.getProfilsdto().getScode_langue());
			}
		}
		return individuDTO;
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */

	@Transactional(readOnly = true)
	public IndividuDTO get(String id) throws JrafDomainException {
		/* PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGw DS-CM getOid) ENABLED START */
		Individu individu = null;
		IndividuDTO individuDTO = null;
		// get en base
		individu = individuRepository.findBySgin(id);

		// transformation bo -> dto
		if (individu != null) {
			// REPIND-946 : do not return forgotten individual (status 'F')
			if (!"X".equalsIgnoreCase(individu.getStatutIndividu())
					&& !"F".equalsIgnoreCase(individu.getStatutIndividu())) {
				individuDTO = IndividuTransform.bo2Dto(individu);
			}
		}
		return individuDTO;
		/* PROTECTED REGION END */
	}

	@Transactional(readOnly = true)
	public List<RoleContratsDTO> getIndividualContractsWithFBRecognitionActivated(String gin)
			throws JrafDomainException {

		if (StringUtils.isEmpty(gin)) {
			return null;
		}

		return roleDS.findRoleContrats(gin, false);
	}

	public IndividuRepository getIndividuRepository() {
		return individuRepository;
	}

	public void setIndividuRepository(IndividuRepository individuRepository) {
		this.individuRepository = individuRepository;
	}

	/**
	 * Getter
	 * 
	 * @return IAdresseUS
	 */
	public AdresseUS getAdresseUS() {
		return adresseUS;
	}

	/**
	 * Setter
	 * 
	 * @param adresseUS the IAdresseUS
	 */
	public void setAdresseUS(AdresseUS adresseUS) {
		this.adresseUS = adresseUS;
	}

	/**
	 * Getter
	 * 
	 * @return IIndividuUS
	 */
	public IndividuUS getIndividuUS() {
		return individuUS;
	}

	/**
	 * Setter
	 * 
	 * @param individuUS the IIndividuUS
	 */
	public void setIndividuUS(IndividuUS individuUS) {
		this.individuUS = individuUS;
	}

	/**
	 * @return EntityManager
	 */
	public EntityManager getEntityManager() {
		/* PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGwgem ) ENABLED START */
		return entityManager;
		/* PROTECTED REGION END */
	}

	/**
	 * @param entityManager EntityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public CreateModifyIndividualResponseDTO createOrUpdateIndividual(CreateUpdateIndividualRequestDTO requestDTO)
			throws JrafDomainException {
		CreateModifyIndividualResponseDTO response = null;

		if (requestDTO == null) {
			return null;
		}

		if (requestDTO.getIndividualRequestDTO() == null
				|| requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO() == null) {
			throw new MissingParameterException("No Individual data");
		}

		if (requestDTO.getRequestorDTO() == null) {
			throw new MissingParameterException("No signature information");
		}

		IndividuDTO individualFromDB = null;
		IndividuDTO individualFromWS = null;

		String identifierFromWs = requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier();

		// Search the individual into DB
		if (identifierFromWs != null) {
			Individu individu = individuRepository.findBySgin(identifierFromWs);
			if (individu == null) {
				throw new NotFoundException("Individual with identifier " + identifierFromWs + " not found");
			}
			individualFromDB = IndividuTransform.bo2DtoForUpdate(individu);
		}

		// Create objet with data from input
		individualFromWS = IndividualRequestTransform.transformIndividualRequestFromWsToIndividuDTO(individualFromWS,
				requestDTO.getIndividualRequestDTO());

		SignatureDTO signature = getSignature(requestDTO.getRequestorDTO());

		// Creation or update
		if (individualFromDB == null || StringUtils.isEmpty(individualFromDB.getSgin())) {
			// Create
			response = createNewIndividual(individualFromWS, requestDTO, signature);
		} else {
			// Update
			response = updateIndividual(individualFromDB, individualFromWS, signature);
		}

		return response;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	// REPIND-1546 : CRITICAL SONAR because Transactional could not be private so
	// change to Public or delete Transactional
	public CreateModifyIndividualResponseDTO createNewIndividual(IndividuDTO individual,
			CreateUpdateIndividualRequestDTO requestDTO, SignatureDTO signature) throws JrafDomainException {
		CreateModifyIndividualResponseDTO response = null;

		prepareSignature(individual, signature);

		// Clean and normalized fields from input
		prepareIndividualFields(individual);

		// Get GIN number from sequence in DB
		String sGin = null;

		if ("BTA".equalsIgnoreCase(individual.getPrenom())) {
			sGin = getIndividualSequence("BTA");
		}
		else {
			sGin = getIndividualSequence(requestDTO.getRequestorDTO().getApplicationCode());
		}

		individual.setSgin(sGin);
		// create and update individual profil
		ProfilsDTO profil = new ProfilsDTO();
		IndividualRequestTransform.transformProfilRequestToProfilDTO(sGin, profil,
				requestDTO.getIndividualRequestDTO().getIndividualProfilDTO());
		prepareIndividualProfil(individual, profil, requestDTO.getIndividualRequestDTO());

		setGenderAndCivility(individual);

		Individu bo = IndividuTransform.dto2BoLight(individual);
		bo.setNomSC(individual.getNomSC());
		bo.setPrenomSC(individual.getPrenomSC());
		if(StringUtils.isNotBlank(bo.getNomSC())&&StringUtils.isNotBlank(bo.getPrenomSC())){
			bo.phonetise();
		}

		individuRepository.saveAndFlush(bo);

		Profils profilEntity = ProfilsTransform.dto2BoLight(profil);
		if (profilEntity != null) {
			profilsRepository.saveAndFlush(profilEntity);
		}

		// Fill response data
		response = new CreateModifyIndividualResponseDTO();
		response.setSuccess(true);
		response.setGin(sGin);
		response.setIndividu(IndividualResponseTransform.transformIndividuDtoToInfosIndividuDTO(individual));
		response.setProfil(IndividualResponseTransform.transformProfilDtoToProfilAvecCodeFonctionValideDTO(profil));

		return response;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public CreateModifyIndividualResponseDTO updateIndividual(IndividuDTO individuFromDB, IndividuDTO individuFromWS,
			SignatureDTO signatureFromWS) throws JrafDomainException {
		CreateModifyIndividualResponseDTO response = null;

		// Set or update data of existing individual with data from input
		prepareSignature(individuFromDB, signatureFromWS);
		ProfilsDTO profil = null;
		boolean needNewProfil = false;

		if (individuFromDB != null) {
			profil = individuFromDB.getProfilsdto();
			if (profil == null) {
				needNewProfil = true;

				profil = new ProfilsDTO();
				profil.setIversion(1);
				profil.setSmailing_autorise(NATFieldsEnum.NONE.getValue());
				profil.setSrin("1");
				profil.setSsolvabilite(OuiNonFlagEnum.OUI.toString());
				profil.setSgin(individuFromWS.getSgin());
			}
		}

		prepareIndividualForUpdate(individuFromWS, individuFromDB, profil, false);

		setDefaultSexe(individuFromDB);

		Individu bo = IndividuTransform.dto2BoForUpdate(individuFromDB);
		bo.phonetise();

		// Remove block telecom
		// REPIND-1602
		if (bo.getTelecoms() != null) {
			bo.getTelecoms().clear();
		}

		individuRepository.saveAndFlush(bo);

		Profils profilEntity = ProfilsTransform.dto2BoLight(profil);
		if (profilEntity != null) {
			profilsRepository.saveAndFlush(profilEntity);
		}

		response = new CreateModifyIndividualResponseDTO();
		response.setSuccess(true);
		response.setGin(individuFromDB.getSgin());
		response.setIndividu(IndividualResponseTransform.transformIndividuDtoToInfosIndividuDTO(individuFromDB));
		response.setProfil(IndividualResponseTransform.transformProfilDtoToProfilAvecCodeFonctionValideDTO(profil));

		return response;
	}

	private SignatureDTO getSignature(RequestorDTO requestor) throws MissingParameterException {
		if (requestor == null) {
			throw new MissingParameterException("Requestor is null");
		}

		SignatureDTO response = new SignatureDTO();
		response.setDate(new Date());
		response.setSignature(requestor.getSignature());
		response.setSite(requestor.getSite());
		response.setApplicationCode(requestor.getApplicationCode());
		response.setIpAddress(requestor.getIpAddress());

		return response;
	}

	private void prepareIndividualForUpdate(IndividuDTO individuFromWS, IndividuDTO individuFromDB, ProfilsDTO profil,
			boolean erasePayment) throws InvalidParameterException {

		// use individual status from WS if provided
		if (StringUtils.isNotEmpty(individuFromWS.getStatutIndividu())) {
			individuFromDB.setStatutIndividu(StringUtils.upperCase(individuFromWS.getStatutIndividu()));
		}

		// Set sexe
		if (StringUtils.isNotEmpty(individuFromWS.getSexe())) {
			individuFromDB.setSexe(StringUtils.upperCase(individuFromWS.getSexe()));
		}
		if (StringUtils.isEmpty(individuFromDB.getSexe())) {
			individuFromDB.setSexe(GenderEnum.UNKNOWN.toString());
		}

		// Set not fusionnable flag
		if (StringUtils.isNotEmpty(individuFromWS.getNonFusionnable())) {
			individuFromDB.setNonFusionnable(StringUtils.upperCase(individuFromWS.getNonFusionnable()));
		}

		// Set flag third trap
		if (StringUtils.isNotEmpty(individuFromWS.getTierUtiliseCommePiege())) {
			individuFromDB.setTierUtiliseCommePiege(StringUtils.upperCase(individuFromWS.getTierUtiliseCommePiege()));
		}

		// REPIND-1671 : Check the Constraint : scivilite in
		// ('M.','MR','MISS','MRS','MS', 'MX')
		if (!StringUtils.isEmpty(individuFromWS.getCivilite())) { // Check if CIVILITY have been filled

			individuFromWS.setCivilite(individuFromWS.getCivilite().trim().toUpperCase());

			RefTableREF_CIVILITE iRefTableREF_CIVILITE = RefTableREF_CIVILITE.instance();
			if (!iRefTableREF_CIVILITE.estValide(individuFromWS.getCivilite(), "")) { // Check if this value is correct
																						// or not

				log.warn("The field civility not valid '" + individuFromWS.getCivilite() + "'");
				throw new InvalidParameterException("The field civility not valid"); // We must raised an error because
																						// of ORACLE violated constraint
			}
		}

		// If enrollement with "M_" MADAM OR MISTER change it to "M." for database
		if (individuFromWS.getCivilite() != null) {
			String civFromDB = individuFromDB.getCivilite();

			if ("M_".equals(StringUtils.upperCase(individuFromWS.getCivilite()))
					|| "M".equals(StringUtils.upperCase(individuFromWS.getCivilite()))) {
				individuFromWS.setCivilite("M.");
				individuFromDB.setCivilite(individuFromWS.getCivilite());
			} else {
				individuFromWS.setCivilite(StringUtils.upperCase(individuFromWS.getCivilite().trim()));
				individuFromDB.setCivilite(individuFromWS.getCivilite());
			}

			// *** Delete Payments : if civiliy change ***
			if (civFromDB == null || !civFromDB.equals(StringUtils.upperCase(individuFromWS.getCivilite()))) {
				erasePayment = true;
			}
		}

		// Bussiness Rule for LastName and FirstName, Only iso latin caracters
		if (!NormalizedStringUtils.isNormalizableString(individuFromWS.getNomSC())) {
			throw new InvalidParameterException("Invalid character in lastname");
		}
		if (!NormalizedStringUtils.isNormalizableString(individuFromWS.getPrenomSC())) {
			throw new InvalidParameterException("Invalid character in firstname");
		}

		if (individuFromWS.getNomSC() != null) {
			String lastNameSCFromDB = individuFromDB.getNomSC();
			String lastNameFromDB = individuFromDB.getNom();

			individuFromWS.setNomSC(individuFromWS.getNomSC().trim());
			individuFromDB.setNom(NormalizedStringUtilsV2.normalizeName(individuFromWS.getNomSC()));
			individuFromDB.setNomSC(individuFromWS.getNomSC());

			// *** Delete Payments : if last name change ***
			if (lastNameSCFromDB == null || !lastNameSCFromDB.equalsIgnoreCase(individuFromWS.getNomSC())) {
				erasePayment = true;
			} else if (lastNameFromDB == null || !lastNameFromDB.equalsIgnoreCase(individuFromWS.getNom())) {
				erasePayment = true;
			}

		}

		if (individuFromWS.getPrenomSC() != null) {
			String firstNameSCFromDB = individuFromDB.getPrenomSC();
			String firstNameFromDB = individuFromDB.getPrenom();

			individuFromWS.setPrenomSC(individuFromWS.getPrenomSC().trim());
			individuFromDB.setPrenom(NormalizedStringUtilsV2.normalizeName(individuFromWS.getPrenomSC()));
			individuFromDB.setPrenomSC(individuFromWS.getPrenomSC());

			// *** Delete Payments : if first name change ***
			if (firstNameSCFromDB == null || !firstNameSCFromDB.equalsIgnoreCase(individuFromWS.getPrenomSC())) {
				erasePayment = true;
			} else if (firstNameFromDB == null || !firstNameFromDB.equalsIgnoreCase(individuFromWS.getPrenom())) {
				erasePayment = true;
			}

		}

		// Special char from lastname and firstname already removed by Transform ->
		// UPCAST fields
		individuFromDB.setNom(StringUtils.upperCase(individuFromDB.getNom()));
		individuFromDB.setPrenom(StringUtils.upperCase(individuFromDB.getPrenom()));

		if (individuFromWS.getDateNaissance() != null) {

			Date dobFromDB = individuFromDB.getDateNaissance();

			individuFromDB.setDateNaissance(SicDateUtils.cleanIndividualBirthDate(individuFromWS.getDateNaissance()));
			// IM01011342 - Delete payment preferences only if day, month and year from date
			// of birth changes
			if (dobFromDB == null) {
				erasePayment = true;
			} else {
				if (SicDateUtils.computeFrenchDate(individuFromWS.getDateNaissance())
						.compareTo(SicDateUtils.computeFrenchDate(dobFromDB)) != 0) {
					log.debug("erasePayments due to a DoB change");
					log.debug("DateUtils.computeFrenchDate(individu.getDateNaissance()="
							+ SicDateUtils.computeFrenchDate(individuFromWS.getDateNaissance()));
					log.debug("DateUtils.computeFrenchDate(individuBo.getDateNaissance())="
							+ SicDateUtils.computeFrenchDate(individuFromDB.getDateNaissance()));
					erasePayment = true;
				}
			}
		}

		// Transfert des autres donnees individu pour eviter un blanchiment
		log.debug("individu.getSecondPrenom()=" + individuFromWS.getSecondPrenom());
		log.debug("individu.getAliasPrenom()=" + individuFromWS.getAliasPrenom());
		log.debug("individu.getAliasNom1()=" + individuFromWS.getAliasNom1());
		log.debug("individu.getNationalite()=" + individuFromWS.getNationalite());
		log.debug("individu.getCodeTitre()=" + individuFromWS.getCodeTitre());
		log.debug("individuBo.getCodeTitre()=" + individuFromDB.getCodeTitre());

		if (StringUtils.isNotEmpty(individuFromWS.getCodeLangue())) {
			individuFromDB.setCodeLangue(StringUtils.upperCase(individuFromWS.getCodeLangue()));
		}
		if (StringUtils.isNotEmpty(individuFromWS.getSecondPrenom())) {
			individuFromDB.setSecondPrenom(NormalizedStringUtilsV2.normalizeName(individuFromWS.getSecondPrenom()));
		}
		if (StringUtils.isNotEmpty(individuFromWS.getAliasPrenom())) {
			individuFromDB.setAliasPrenom(NormalizedStringUtilsV2.normalizeName(individuFromWS.getAliasPrenom()));
		}
		if (StringUtils.isNotEmpty(individuFromWS.getAliasNom1())) {
			individuFromDB.setAliasNom1(NormalizedStringUtilsV2.normalizeName(individuFromWS.getAliasNom1()));
		}
		if (StringUtils.isNotEmpty(individuFromWS.getNationalite())) {
			individuFromDB.setNationalite(StringUtils.upperCase(individuFromWS.getNationalite()));
		}
		if (StringUtils.isNotEmpty(individuFromWS.getAutreNationalite())) {
			individuFromDB.setAutreNationalite(StringUtils.upperCase(individuFromWS.getAutreNationalite()));
		}

		if (StringUtils.isNotEmpty(individuFromWS.getAlias())) {
			individuFromDB.setAlias(NormalizedStringUtilsV2.normalizeName(individuFromWS.getAlias()));
		}

		if (StringUtils.isNotEmpty(individuFromWS.getCodeTitre())) {
			individuFromDB.setCodeTitre(StringUtils.upperCase(individuFromWS.getCodeTitre()));
		}

		// ********* Profil ********

		if (profil == null) {
			profil = new ProfilsDTO();
		}

		if (individuFromWS.getCodeLangue() != null) {
			profil.setScode_langue(StringUtils.upperCase(individuFromWS.getCodeLangue()));
		}

		// Add language code from DB
		else if (individuFromDB.getProfilsdto() != null && individuFromDB.getProfilsdto().getScode_langue() != null) {
			profil.setScode_langue(StringUtils.upperCase(individuFromDB.getProfilsdto().getScode_langue()));
		}

		// This is an update -> set id
		if (individuFromWS.getProfilsdto() != null) {
			ProfilsDTO profilFromWS = individuFromWS.getProfilsdto();

			if (individuFromDB.getProfilsdto() != null) {
				profil.setSrin(individuFromDB.getProfilsdto().getSrin());
			}

			// CodePro
			if (StringUtils.isNotEmpty(profilFromWS.getScode_professionnel())) {
				profil.setScode_professionnel(profilFromWS.getScode_professionnel());
			}
			// Etudiant
			if (StringUtils.isNotEmpty(profilFromWS.getSetudiant())) {
				profil.setSetudiant(profilFromWS.getSetudiant());
			}
			// Fonction code
			if (StringUtils.isNotEmpty(profilFromWS.getScode_fonction())) {
				profil.setScode_fonction(profilFromWS.getScode_fonction());
			}
			// Langue
			if (StringUtils.isNotEmpty(profilFromWS.getScode_langue())) {
				profil.setScode_langue(profilFromWS.getScode_langue());
			}
			// Code marital
			if (StringUtils.isNotEmpty(profilFromWS.getScode_maritale())) {
				profil.setScode_maritale(profilFromWS.getScode_maritale());
			}
			// NAT
			if (StringUtils.isNotEmpty(profilFromWS.getSmailing_autorise())) {
				profil.setSmailing_autorise(profilFromWS.getSmailing_autorise());
			}
			// NB Enfant
			if (profilFromWS.getInb_enfants() != null) {
				profil.setInb_enfants(profilFromWS.getInb_enfants());
			}
			// Segement
			if (StringUtils.isNotEmpty(profilFromWS.getSsegment())) {
				profil.setSsegment(profilFromWS.getSsegment());
			}
		}

		if (profil != null) {
			try {
				// REPIND-1659 : Check in database to match if the LANGUES exist
				if (!languageDS.isValidLanguageCode(profil.getScode_langue())) {
					log.warn("LANGUAGE_CODE is not valid : '" + profil.getScode_langue() + "'");
					profil.setScode_langue("FR");
				}
			} catch (JrafDomainException e) {
				log.warn(e);
				profil.setScode_langue("FR");
			}
		}
	}

	private String getIndividualSequence(String app) {
		if (StringUtils.isEmpty(app)) {
			return EncryptionUtils.processCheckDigit(individuRepository.getIdentifierNextValue());
		} else if ("KLM".equalsIgnoreCase(app)) {
			return EncryptionUtils.processCheckDigit(individuRepository.getKLMIdentifierNextValue());
		} else if ("WP".equalsIgnoreCase(app)) {
			return EncryptionUtils.processCheckDigit(individuRepository.getWPIdentifierNextValue());
		} else if ("AE".equalsIgnoreCase(app)) {
			return EncryptionUtils.processCheckDigit(individuRepository.getAEIdentifierNextValue());
		} else if ("BTA".equalsIgnoreCase(app)) {
			return EncryptionUtils.processCheckDigit(individuRepository.getBTAIdentifierNextValue());
		} else {
			return EncryptionUtils.processCheckDigit(individuRepository.getIdentifierNextValue());
		}
	}

	private void prepareSignature(IndividuDTO individual, SignatureDTO signature) throws MissingParameterException {
		if (signature == null) {
			throw new MissingParameterException("Signature is null");
		}

		Date date = signature.getDate();
		String sign = signature.getSignature();
		String site = signature.getSite();

		if (StringUtils.isEmpty(individual.getSignatureCreation())) {
			individual.setSignatureCreation(sign);
		}
		if (StringUtils.isEmpty(individual.getSiteCreation())) {
			individual.setSiteCreation(site);
		}
		if (individual.getDateCreation() == null) {
			individual.setDateCreation(date);
		}

		individual.setSignatureModification(sign);
		individual.setSiteModification(site);
		individual.setDateModification(date);
	}

	public void prepareIndividualProfil(IndividuDTO individual, ProfilsDTO profil,
			IndividualRequestDTO individualRequestDTO) {
		if (individual == null || individualRequestDTO == null) {
			return;
		}

		IndividualProfilDTO req = individualRequestDTO.getIndividualProfilDTO();
		if (profil == null) {
			profil = new ProfilsDTO();
		}

		if (req == null || StringUtils.isEmpty(req.getEmailOptin())) {
			profil.setSmailing_autorise(NATFieldsEnum.NONE.getValue());
		}

		profil.setSsolvabilite(OuiNonFlagEnum.OUI.toString());

		if (req == null || StringUtils.isEmpty(req.getLanguageCode())) {
			profil.setScode_langue("FR");
		} else {
			try {
				// REPIND-1659 : Check in database to match if the LANGUES exist
				if (!languageDS.isValidLanguageCode(req.getLanguageCode())) {
					log.warn("LANGUAGE_CODE is not valid : '" + req.getLanguageCode() + "'");
					profil.setScode_langue("FR");
				}
			} catch (JrafDomainException e) {
				log.warn(e);
				profil.setScode_langue("FR");
			}
		}

		if (req == null || StringUtils.isEmpty(req.getStudentCode())) {
			profil.setSetudiant(OuiNonFlagEnum.NON.toString());
		}
	}

	public void updateGenderAndCivility(IndividuDTO individuDTOFromWS, IndividuDTO individuDTOFromDB) throws InvalidParameterException {
		if(StringUtils.isEmpty(individuDTOFromWS.getCivilite()) && StringUtils.isEmpty(individuDTOFromWS.getSexe())){
			individuDTOFromWS.setCivilite(individuDTOFromDB.getCivilite());
			individuDTOFromWS.setSexe(individuDTOFromDB.getSexe());
		}else {
			setGenderAndCivility(individuDTOFromWS);
		}
	}

	public void setGenderAndCivility(IndividuDTO individual) throws InvalidParameterException {
		if (StringUtils.isEmpty(individual.getSexe())) {
			individual.setSexe(GenderEnum.UNKNOWN.toString());
			try {
				CivilityEnum civility = CivilityEnum.getEnumMandatory(individual.getCivilite());
				switch (civility) {
					case MS:
					case MRS:
					case MISS:
						individual.setSexe(GenderEnum.FEMALE.toString());
						break;
					case MISTER:
						individual.setSexe(GenderEnum.MALE.toString());
						break;
					case M_:
						individual.setSexe(GenderEnum.UNKNOWN.toString());
						break;
					case MX:
						individual.setSexe(GenderEnum.NONBINARY.toString());
						break;
				}
			} catch (InvalidParameterException e) {
				throw new InvalidParameterException(e);
			} catch (MissingParameterException e) {
				//Nothing to do
			}

		}
		if (StringUtils.isEmpty(individual.getCivilite())) {
			GenderEnum gender = GenderEnum.getEnum(individual.getSexe());
			switch (gender) {
				case UNKNOWN:
					individual.setCivilite(CivilityEnum.M_.toString());
					break;
				case MALE:
					individual.setCivilite(CivilityEnum.MISTER.toString());
					break;
				case FEMALE:
					individual.setCivilite(CivilityEnum.MRS.toString());
					break;
				case NONBINARY:
					individual.setCivilite(CivilityEnum.MX.toString());
					break;
			}
		}
	}

	public void setGenderSharepoint(IndividuDTO individual) throws InvalidParameterException {
		if (StringUtils.isEmpty(individual.getSexe())) {
			try {
				CivilityEnum civility = CivilityEnum.getEnumMandatory(individual.getCivilite());
				switch (civility) {
					case MS:
					case MRS:
					case MISS:
						individual.setSexe(GenderEnum.FEMALE.toString());
						break;
					case MISTER:
						individual.setSexe(GenderEnum.MALE.toString());
						break;
					case M_:
						// We use this function for updating an individual, so for this case we have to keep RI DB value
						// So we don't want to set the Sexe to unknown, but let it to null.
						break;
					case MX:
						individual.setSexe(GenderEnum.NONBINARY.toString());
						break;
				}
			} catch (InvalidParameterException e) {
				throw new InvalidParameterException(e);
			} catch (MissingParameterException e) {
				//Nothing to do
			}

		}
	}

	public void prepareIndividualFields(IndividuDTO individual) throws MissingParameterException, InvalidParameterException {
		if (individual == null) {
			return;
		}

		individual.setDateNaissance(SicDateUtils.cleanIndividualBirthDate(individual.getDateNaissance()));
		setGenderAndCivility(individual);

		// REPIND-1671 : Check the Constraint : scivilite in
		// ('M.','MISS','MR','MRS','MS', 'MX')
		if (!StringUtils.isEmpty(individual.getCivilite())) { // Check if CIVILITY have been filled

			individual.setCivilite(individual.getCivilite().trim().toUpperCase());

			RefTableREF_CIVILITE iRefTableREF_CIVILITE = RefTableREF_CIVILITE.instance();
			if (!iRefTableREF_CIVILITE.estValide(individual.getCivilite(), "")) { // Check if this value is correct or
																					// not

				log.warn("The field civility not valid '" + individual.getCivilite() + "'");
				throw new MissingParameterException("The field civility not valid"); // We must raised an error because
																						// of ORACLE violated constraint
			}
		}

		if (StringUtils.isEmpty(individual.getNonFusionnable())) {
			individual.setNonFusionnable(YesNoFlagEnum.NO.toString());
		}
		if (StringUtils.isEmpty(individual.getTierUtiliseCommePiege())) {
			individual.setTierUtiliseCommePiege(YesNoFlagEnum.NO.toString());
		}
		if (StringUtils.isEmpty(individual.getFraudeurCarteBancaire())) {
			individual.setFraudeurCarteBancaire(YesNoFlagEnum.NO.toString());
		}

		// Special char from lastname and firstname already removed by Transform ->
		// UPCAST fields
		individual.setNom(StringUtils.upperCase(individual.getNom()));
		individual.setPrenom(StringUtils.upperCase(individual.getPrenom()));
	}

	/* PROTECTED REGION END */

	/**
	 * createConnexionData
	 * 
	 * @param request in ConnexionDataRequestDTO
	 * @return The createConnexionData as <code>ConnexionDataResponseDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ConnexionDataResponseDTO createConnexionData(ConnexionDataRequestDTO request) throws JrafDomainException {
		/* PROTECTED REGION ID(_8nXA0C3sEeC7hbdMKof7lA) ENABLED START */
		// TODO method createConnexionData() to implement
		throw new UnsupportedOperationException();
		/* PROTECTED REGION END */
	}

	/**
	 * updateConnexionData
	 * 
	 * @param request in ConnexionDataRequestDTO
	 * @return The updateConnexionData as <code>ConnexionDataResponseDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ConnexionDataResponseDTO updateConnexionData(ConnexionDataRequestDTO request) throws JrafDomainException {
		/* PROTECTED REGION ID(_LvWToC3tEeC7hbdMKof7lA) ENABLED START */
		// TODO method updateConnexionData(ConnexionDataResponseDTO response,
		// ConnexionDataRequestDTO request) to implement
		throw new UnsupportedOperationException();
		/* PROTECTED REGION END */
	}

	/**
	 * findBy
	 * 
	 * @param firstName  in String
	 * @param lastName   in String
	 * @param email      in String
	 * @param ignoreCase in Boolean
	 * @param likeMode   in Boolean
	 * @return The findBy as <code>List</code>
	 * @throws JrafDomainException en cas d'exception
	 */

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List findBy(String firstName, String lastName, String email, Boolean ignoreCase, Boolean likeMode)
			throws JrafDomainException {
		/* PROTECTED REGION ID(_CZCycE7lEeCFM4AYw8gmuw) ENABLED START */
		String whereClause = "";
		String test = "=";
		String add = "";
		String upper = "";
		if (likeMode) {
			test = "like";
			add = "||'%'";
		}
		if (ignoreCase) {
			upper = " upper";
		}
		StringBuffer buffer = new StringBuffer("select i from Individu i JOIN i.email e where ");

		List result = new ArrayList();

		if (firstName != null && lastName != null && email != null) {
			buffer.append(upper).append("(i.nom) ").append(test).append(upper).append(" (:nom)").append(add);
			buffer.append(" and ").append(upper).append("(i.prenom) ").append(test).append(upper).append(" (:prenom)")
					.append(add);
			buffer.append(" and ").append("( i.statutIndividu = 'V' or i.statutIndividu = 'P' ) ").append(add);
			buffer.append(" and ").append(upper).append("(e.email) ").append(test).append(upper).append(" (:email)")
					.append(add);
			whereClause = buffer.toString();
			Query query = getEntityManager().createQuery(whereClause);
			query.setParameter("nom", firstName.toUpperCase());
			query.setParameter("prenom", lastName.toUpperCase());
			query.setParameter("email", email.toLowerCase());
			result = query.getResultList();
			IndividuDS.log.info("individus trouvés :" + result.size());
			if (!result.isEmpty()) {
				IndividuDS.log.info(result.get(0));
			}
		} else {
			throw new JrafDomainException("Missing parameter in findBy firstName lastName email");
		}
		return result;
		/* PROTECTED REGION END */
	}

	/**
	 * updateIndividual
	 * 
	 * @param individuDTO in IndividuDTO
	 * @throws JrafDomainException en cas d'exception
	 */

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateIndividual(IndividuDTO individuDTO) throws JrafDomainException {
		/* PROTECTED REGION ID(_3P1WIIEoEeCtut40RvtPWA) ENABLED START */

		if (individuDTO.getSgin() == null && individuDTO.getSgin().length() == 0) {
			throw new NotFoundException("GIN number is empty");
		}
		Individu individuBo = individuRepository.findBySgin(individuDTO.getSgin());
		if (individuBo == null) {
			throw new NotFoundException("Individual GIN " + individuDTO.getSgin() + " is not found");
		}

		// Ajout DL pour solutionner les problemes lies a la dequalification de la
		// civilite de nos individus :
		// la mise a jour de la donnee CIVILITE a M. est ignoree lorsque cette donnee
		// est qualifiee
		// (MR, MRS, MISS, MS)
		if ((individuDTO.getCivilite().equals("M.")) && (!individuBo.getCivilite().equals(individuDTO.getCivilite()))) {
			individuDTO.setCivilite(individuBo.getCivilite());
			if (individuBo.getCivilite().equals("MR")) {
				individuDTO.setSexe("M");
			} else {
				individuDTO.setSexe("F");
			}
		}

		// Si modif nom, prenom ou civilite, on met a jour les alias
		if (!individuBo.getNom().equalsIgnoreCase(individuDTO.getNom())
				|| !individuBo.getPrenom().equalsIgnoreCase(individuDTO.getPrenom())
				|| !individuBo.getCivilite().equalsIgnoreCase(individuDTO.getCivilite())) {
			individuBo.setAliasCivilite2(individuBo.getAliasCivilite1());
			individuBo.setAliasNom2(individuBo.getAliasNom1());
			individuBo.setAliasPrenom2(individuBo.getAliasPrenom1());
			individuBo.setAliasCivilite1(individuDTO.getCivilite());
			individuBo.setAliasNom1(individuDTO.getNom());
			individuBo.setAliasPrenom1(individuDTO.getPrenom());
		}

		// Business rules pour les modifications nom, prénom et date de naissance
		IndividuDS.log
				.debug("Individu::Remplace : Check Business Rules for name, first name and birthdate modification");
		boolean flagModifNom = false;
		boolean flagModifPrenom = false;
		if (!individuBo.getNom().equals(individuDTO.getNom())) {
			flagModifNom = true;
		}
		if (individuBo.getPrenom().equals(individuDTO.getPrenom())) {
			flagModifPrenom = true;
		}

		if (!_REF_P.equals(individuBo.getStatutIndividu())) {
			int nbModif = 0;
			if (flagModifNom) {
				nbModif++;
			}
			if (flagModifPrenom) {
				nbModif++;
			}
			if (individuBo.getDateNaissance().compareTo(individuDTO.getDateNaissance()) != 0) {
				nbModif++;
			}
			// Si plusieurs modifs simultanés on applique les business rules
			if (nbModif > 1) {
				boolean bModifDataOK = false;
				// Pour les modifs nom et prénom
				if (flagModifNom && flagModifPrenom && nbModif == 2) {
					// Si permutation nom et prénom, c'est OK
					if (individuDTO.getNom().equalsIgnoreCase(individuBo.getPrenom())
							&& individuDTO.getPrenom().equalsIgnoreCase(individuBo.getNom())) {
						bModifDataOK = true;
					}

					// si changement phonetique, c'est OK
					if (bModifDataOK == false) {
						String s_NomPhonetise = NormalizedStringUtils.PhonetiseChaine(individuBo.getNom(), true);
						;
						String s_NomCparPhonetise = NormalizedStringUtils.PhonetiseChaine(individuDTO.getNom(), true);
						String s_PrenomPhonetise = NormalizedStringUtils.PhonetiseChaine(individuBo.getPrenom(), true);
						String s_PrenomCparPhonetise = NormalizedStringUtils.PhonetiseChaine(individuDTO.getPrenom(),
								true);
						if (s_NomPhonetise.equals(s_NomCparPhonetise)
								&& s_PrenomPhonetise.equals(s_PrenomCparPhonetise)) {
							bModifDataOK = true;
						}
					}
					// si la distance levenshtein est inférieur à 3
					if (bModifDataOK == false) {
						int distanceLevenshteimNom = SicStringUtils.getLevenshteinDistance(individuBo.getNom(),
								individuDTO.getNom());
						int distanceLevenshteimPrenom = SicStringUtils.getLevenshteinDistance(individuBo.getPrenom(),
								individuDTO.getPrenom());
						if ((distanceLevenshteimNom + distanceLevenshteimPrenom) < 3) {
							bModifDataOK = true;
						}
					}
				}

				if (!bModifDataOK) {
					throw new JrafDomainException(_REF_377);
				}
			}
		}

		if (flagModifNom || flagModifPrenom) {
			individuBo.setNomSC(individuDTO.getNomSC());
			individuBo.setPrenomSC(individuDTO.getPrenomSC());
		}
		individuBo.setSecondPrenom(individuDTO.getSecondPrenom());
		individuBo.setAliasNom1(individuDTO.getAliasNom1());
		individuBo.setAliasPrenom(individuDTO.getAliasPrenom());
		individuBo.setCivilite(individuDTO.getCivilite());
		individuBo.setSexe(individuDTO.getSexe());
		individuBo.setDateNaissance(individuDTO.getDateNaissance());
		individuBo.setCodeTitre(individuDTO.getCodeTitre());
		if (individuDTO.getStatutIndividu() != null && individuDTO.getStatutIndividu().length() > 0) {
			// Interdiction de changer le statut d'un individu transféré!
			if (individuBo.getStatutIndividu().equals("T") && !individuDTO.getStatutIndividu().equals("T")) {
				throw new JrafDomainException(_REF_209 + ":INDIVIDU TRANSFERE");
			}
			individuBo.setStatutIndividu(individuDTO.getStatutIndividu());
		}
		individuBo.setFraudeurCarteBancaire(individuDTO.getFraudeurCarteBancaire());
		individuBo.setTierUtiliseCommePiege(individuDTO.getTierUtiliseCommePiege());
		individuBo.setNonFusionnable(individuDTO.getNonFusionnable());
		individuBo.setNationalite(individuDTO.getNationalite());
		individuBo.setAutreNationalite(individuDTO.getAutreNationalite());

		// Si l'individu n'a jamais été modifié,
		// la signature associée n'existe pas
		if (individuBo.getSignatureModification() != null) {
			if (individuDTO.getSignatureModification() == null
					|| individuDTO.getSignatureModification().length() == 0) {
				throw new JrafDomainException(_REF_902);
			}
			individuBo.setSignatureCreation(individuDTO.getSignatureModification());
			individuBo.setSiteModification(individuDTO.getSiteModification());
			individuBo.setDateModification(individuDTO.getDateModification());
		} else {
			individuBo.setSignatureCreation(individuDTO.getSignatureModification());
			individuBo.setSiteModification(individuDTO.getSiteModification());
			individuBo.setDateModification(individuDTO.getDateModification());
		}

		individuBo.phonetise();

		individuRepository.saveAndFlush(individuBo);

		/* PROTECTED REGION END */
	}

	protected void removeTelex(IndividuDTO individu) {
		if (individu == null || individu.getTelecoms() == null) {
			return;
		}
		List<TelecomsDTO> telecoms = new ArrayList<>(individu.getTelecoms());
		for (TelecomsDTO telecom : telecoms) {
			if ("X".equals(telecom.getSterminal())) {
				individu.getTelecoms().remove(telecom);
			}
		}
	}

	/**
	 * searchHomonyms
	 * 
	 * @param request in SearchHomonymsRequestDTO
	 * @return The searchHomonyms as <code>SearchHomonymsResponseDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */

	/**
	 * <p>
	 * This method exists in the prospect IndividualDS. They are both the same.
	 * </p>
	 * 
	 * @param email in String
	 * @return The getAnIndividualIdentification as <code>List<String></code>
	 * @throws JrafDomainException en cas d'exception
	 */

	@Transactional(readOnly = true)
	public List<String> getAnIndividualIdentification(String email) throws JrafDomainException {
		/* PROTECTED REGION ID(_Pk6UIJx1EeKxW7K9J-Vzuw) ENABLED START */
		// Call the DAO method
		return individuRepository.getAnIndividualIdentification(email);
		/* PROTECTED REGION END */
	}

	@Transactional(readOnly = true)
	public Integer countIndividualIdentification(String email) {
		/* PROTECTED REGION ID(_Pk6UIJx1EeKxW7K9J-Vzuw) ENABLED START */
		// Call the DAO method
		return individuRepository.countIndividualIdentification(email);
		/* PROTECTED REGION END */
	}

	/**
	 * Getter
	 * 
	 * @return
	 */
	public RoleDS getRoleDS() {
		return roleDS;
	}

	/**
	 * Setter
	 * 
	 * @param roleDS
	 */
	public void setRoleDS(RoleDS roleDS) {
		this.roleDS = roleDS;
	}

	public PostalAddressRepository getPostalAddressRepository() {
		return postalAddressRepository;
	}

	public void setPostalAddressRepository(PostalAddressRepository postalAddressRepository) {
		this.postalAddressRepository = postalAddressRepository;
	}

	/**
	 * {@inheritDoc}
	 */

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value = "transactionManagerRepind")
	public Individu create2(IndividuDTO individuDTO) throws JrafDomainException {

		Individu individu = null;

		// transformation dto -> bo
		individu = IndividuTransform.dto2Bo(individuDTO);

		// creation en base
		individu.setMandatoryDBFields();

		// Appel create de l'Abstract

		individuRepository.saveAndFlush(individu);

		// Version update and Id update if needed
		IndividuTransform.bo2Dto(individu, individuDTO);

		return individu;

	}

	@Transactional(readOnly = true)
	public IndividuDTO getByGin(String gin) throws JrafDomainException {

		if (StringUtils.isEmpty(gin)) {
			throw new IllegalArgumentException("Unable to get individual with empty gin");
		}

		IndividuDTO individuDTO = new IndividuDTO();
		individuDTO.setSgin(gin);

		individuDTO = get(individuDTO);

		return individuDTO;
	}

	@Transactional(readOnly = true)
	public IndividuDTO getAllByGin(String gin) throws JrafDomainException {

		if (StringUtils.isEmpty(gin)) {
			throw new IllegalArgumentException("Unable to get individual with empty gin");
		}

		IndividuDTO individuDTO = new IndividuDTO();
		individuDTO.setSgin(gin);

		individuDTO = getAll(individuDTO);

		return individuDTO;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void clearDelegateData(String gin) throws JrafDomainException {

		Optional<Individu> individu = individuRepository.findById(gin);

		if (!individu.isPresent()) {
			throw new JrafDomainException("Unable to find following individual: " + gin);
		}

		if (individu.get().getDelegateList() == null || individu.get().getDelegateList().isEmpty()) {
			return;
		}

		individu.get().getDelegateList().clear();

		individuRepository.saveAndFlush(individu.get());
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void clearDelegatorData(String gin) throws JrafDomainException {

		Optional<Individu> individu = individuRepository.findById(gin);

		if (!individu.isPresent()) {
			throw new JrafDomainException("Unable to find following individual: " + gin);
		}

		if (individu.get().getDelegatorList() == null || individu.get().getDelegatorList().isEmpty()) {
			return;
		}

		individu.get().getDelegatorList().clear();

		individuRepository.saveAndFlush(individu.get());
	}

	@Transactional(value = "transactionManagerRepind")
	public IndividuDTO refresh(IndividuDTO individuToRefresh) throws JrafDomainException {

		Optional<Individu> individuFromDB = individuRepository.findById(individuToRefresh.getSgin());

		if (!individuFromDB.isPresent()) {
			throw new NotFoundException("Unable to find following individual: " + individuToRefresh.getSgin());
		}

		return IndividuTransform.bo2Dto(individuFromDB.get());
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateIndividualSC(String gin, String firstNameSC, String lastNameSC) throws JrafDomainException {

		Optional<Individu> individuFromDB = individuRepository.findById(gin);

		if (!individuFromDB.isPresent()) {
			throw new NotFoundException("Unable to find following individual: " + gin);
		}

		if (StringUtils.isNotEmpty(firstNameSC)) {
			individuFromDB.get().setPrenomSC(firstNameSC);
		}

		if (StringUtils.isNotEmpty(lastNameSC)) {
			individuFromDB.get().setNomSC(lastNameSC);
		}

		individuRepository.saveAndFlush(individuFromDB.get());
	}

	public Boolean isFlyingBlue(String gin) {
		return individuRepository.isFlyingBlue(gin) > 0;
	}

	public Boolean isMyAccount(String gin) {
		return individuRepository.isMyAccount(gin) > 0;
	}

	public Boolean isPureMyAccount(String gin) {
		return individuRepository.isPureMyAccount(gin) > 0;
	}

	public String getLastValidEmail(String gin) {
		return individuRepository.getLastValidEmail(gin);
	}

	/**
	 * searchIndividual
	 * 
	 * @param request in SearchIndividualByMulticriteriaRequestDTO
	 * @return The searchHomonyms as
	 *         <code>SearchIndividualByMulticriteriaResponseDTO</code>
	 * @throws JrafDomainException
	 */

	@Transactional(readOnly = true)
	public SearchIndividualByMulticriteriaResponseDTO searchIndividual(
			SearchIndividualByMulticriteriaRequestDTO request) throws JrafDomainException {
		final int MAX_ELEMENTS = 20;
		final int MAX_INDIVIDUAL = 100;
		int highestRelevance = 0;
		final String NO_INDIVIDUAL_FOUND = "No individual found";

		SearchIndividualByMulticriteriaResponseDTO response = null;
		IndividuDS.log.debug("searchIndividual() BEGIN");
		List<Individu> listIndividu = getIndividuUS().creerIndividusByNameSearch(request);

		/** Not provide individual with type F or H **/
		Iterator<Individu> iterator = listIndividu.iterator();
		while (iterator.hasNext()) {
			Individu i = iterator.next();
			String type = i.getType();

			if ("F".equalsIgnoreCase(type) || "H".equalsIgnoreCase(type)) {
				iterator.remove();
			}
		}

		// RPEIND-1808: Retrieve Callers only for specific Consumer and Context
		if (!callersUtils.isAuthorized(request.getRequestor().getConsumerId(), request.getRequestor().getContext())) {
			iterator = listIndividu.iterator();
			while (iterator.hasNext()) {
				Individu i = iterator.next();
				String type = i.getType();

				if ("C".equalsIgnoreCase(type)) {
					iterator.remove();
				}
			}
		}

		if (listIndividu != null && !listIndividu.isEmpty()) {
			response = new SearchIndividualByMulticriteriaResponseDTO();
			Set<IndividualMulticriteriaDTO> listIndividuDTO = new HashSet<>();
			for (Individu individu : listIndividu) {
				IndividuDTO individuDTO = IndividuTransform.bo2DtoSearch(individu);

				/*
				 * TODO To adapt for prospect migration
				 * 
				 */
				// REPIND-854 : Prepare prospect migration
				// SearchIndividualByMultiCriteria must return PROSPECT
				// IdentifyCustomerCrossRef must not return PROSPECT ? Really ?
				if (individuDTO != null) {
					IndividualMulticriteriaDTO individuHomonym = new IndividualMulticriteriaDTO();
					individuHomonym.setIndividu(individuDTO);
					Integer relevance = RankComputer.computeRankIndividu(request, individuHomonym);
					if (relevance > highestRelevance) {
						highestRelevance = relevance;
					}
					individuHomonym.setRelevance(relevance.toString());
					// REPIND-227 : Changement la relevance autorisée est egale ou superieure a 40%
					// REPIND-671 : Relevance is 30% in the SSD.
					if (request != null && request.getProcessType() != null && ( // En mode AUTO, on n accepte pas en
																					// dessous de 30%
					("A".equals(request.getProcessType()) && Integer.parseInt(individuHomonym.getRelevance()) >= 30) ||
					// En mode MANUEL, on prend ce qui vient !
							("M".equals(request.getProcessType()))

					)) {
						listIndividuDTO.add(individuHomonym);
						// REPIND-1247 : On check si on est dans le cas ou le Max sera verifier plus
						// loin ou pas...
						if (listIndividuDTO.size() >= MAX_INDIVIDUAL) {
							if (!request.getProcessType().equalsIgnoreCase("A")) {
								// REPIND-1247 : On break uniquement si on est en process Manuel (car en Auto on
								// break plus loin)
								break;
							}
						}
					}
				}
			}

			// Si on est en mode automatique, on filtre la liste
			// on garde la plus haute relevance seulement, et 20 individus maximum
			Set<IndividualMulticriteriaDTO> listIndividuDTOauto = new HashSet<>();
			if (request.getProcessType().equalsIgnoreCase("A")) {
				for (IndividualMulticriteriaDTO individuRemonte : listIndividuDTO) {
					if (Integer.parseInt(individuRemonte.getRelevance()) >= highestRelevance) {
						listIndividuDTOauto.add(individuRemonte);
						// REPIND-1247 : On break si on est en process Automatique et qu'on depasse le
						// MAX
						if (listIndividuDTOauto.size() >= MAX_INDIVIDUAL) {
							break;
						}
					}
				}
				response.setIndividuals(listIndividuDTOauto);
			} else {
				response.setIndividuals(listIndividuDTO);
			}
			response.setVisaKey("remove");
			/*
			 * TODO To remove for prospect migration
			 * 
			 */
			// REPIND-854 : Prepare prospect migration
			if (listIndividuDTO.isEmpty()) {
				IndividuDS.log.info(NO_INDIVIDUAL_FOUND);
				throw new NotFoundException(NO_INDIVIDUAL_FOUND);
			}
		} else {
			IndividuDS.log.info(NO_INDIVIDUAL_FOUND);
			throw new NotFoundException(NO_INDIVIDUAL_FOUND);
		}
		IndividuDS.log.debug("searchIndividualByMulticriteria() END");

		return response;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value = "transactionManagerRepind")
	public String createAnIndividualTraveler(IndividuDTO individuDTO) throws JrafDomainException {

		// CREATE
		// Use SpringData to insert an Individual
		setGenderAndCivility(individuDTO);

		Individu individu = new Individu();
		IndividuTransform.dto2Bo(individuDTO, individu);
		individu.setSgin(EncryptionUtils.processCheckDigit(individuRepository.getTravelerNextValue()));

		// REPIND-1256 : Add value in case of Traveler Creation
		IndividuTransform.addDefaultPhonetic(individu);

		IndividuTransform.addGinToLink(individu);
		individuRepository.saveAndFlush(individu);

		return individu.getSgin();
	}

	// REPIND-1808 : Create Caller for CCP
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value = "transactionManagerRepind")
	public String createAnIndividualCaller(IndividuDTO individuDTO) throws JrafDomainException {
		setGenderAndCivility(individuDTO);

		Individu individu = new Individu();
		IndividuTransform.dto2Bo(individuDTO, individu);
		individu.setSgin(EncryptionUtils.processCheckDigit(individuRepository.getCallerNextValue()));

		// REPIND-1256 : Add value in case of Traveler Creation
		IndividuTransform.addDefaultPhonetic(individu);

		IndividuTransform.addGinToLink(individu);
		individuRepository.saveAndFlush(individu);

		return individu.getSgin();
	}

	// REPIND-555 : Migration PROSPECT depuis SICUTF8 vers SIC

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value = "transactionManagerRepind")
	public String createAnIndividualProspect(IndividuDTO individuDTO) throws JrafDomainException {
		Individu individu = new Individu();

		setDefaultSexe(individuDTO);

		IndividuTransform.dto2Bo(individuDTO, individu);
		individu.setSgin(EncryptionUtils.processCheckDigit(individuRepository.getProspectNextValue()));
		IndividuTransform.addGinToLink(individu);

		individuRepository.saveAndFlush(individu);

		if (individuDTO.getEmaildto() != null && !individuDTO.getEmaildto().isEmpty()) {
			com.airfrance.repind.dto.adresse.EmailDTO emailDTO = individuDTO.getEmaildto().iterator().next();
			emailDTO.setSgin(individu.getSgin());
			emailDTO.setVersion(0);

			// REPIND-1767 : Detect UTF8 in Email
			if (SicUtf8StringUtils.isNonASCII(emailDTO.getEmail())) {

				// EMAIL is full ASCII => Go on SIC
			} else {
				emailDS.create(emailDTO);
			}
		}

		if (individuDTO.getPostaladdressdto() != null && !individuDTO.getPostaladdressdto().isEmpty()) {
			individuDTO.getPostaladdressdto().get(0).setSgin(individu.getSgin());
			individuDTO.getPostaladdressdto().get(0).setVersion(0);
			postalAddressDS.create(individuDTO.getPostaladdressdto().get(0));
		}

		// For create preferences
		if (individuDTO.getPreferenceDTO() != null && !individuDTO.getPreferenceDTO().isEmpty()) {

			PreferenceDTO prefToCreateDTO = individuDTO.getPreferenceDTO().get(0);
			prefToCreateDTO.setGin(individu.getSgin());
			preferenceDS.create(prefToCreateDTO);

		}

		return individu.getSgin();
	}

	// REPIND-555 : Migration PROSPECT depuis SICUTF8 vers SIC

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String updateAnIndividualProspect(IndividuDTO updateProspectDTO, IndividuDTO findProspectDTO)
			throws JrafDomainException {

		Individu individu = new Individu();

		if (findProspectDTO != null) {
			log.debug("Update prospect with gin : " + findProspectDTO.getSgin().toString());

			// Etape 0: map gender-civility
			updateGenderAndCivility(updateProspectDTO, findProspectDTO);

			// Etape 1: maj des données prospect
			updateProspectDataV7(updateProspectDTO, findProspectDTO);

			// Etape 2: maj donnees telecoms
			if (updateProspectDTO.getTelecoms() != null) {
				if (findProspectDTO.getTelecoms() == null) {
					findProspectDTO.setTelecoms(updateProspectDTO.getTelecoms());
				} else {
					updateTelecomData(updateProspectDTO.getTelecoms().iterator().next(),
							findProspectDTO.getTelecoms().iterator().next());
				}
			} else {
				findProspectDTO.setTelecoms(null);
			}

			if(updateProspectDTO.getCommunicationpreferencesdto() != null){
				updateComPrefDataOptin(updateProspectDTO, findProspectDTO);

			}
			IndividuTransform.dto2Bo(findProspectDTO, individu);
			IndividuTransform.addGinToLink(individu);
			
			individuRepository.saveAndFlush(individu);

			// Etape 3: maj donnees localisation et preferences
			if (updateProspectDTO.getPostaladdressdto() != null && !updateProspectDTO.getPostaladdressdto().isEmpty()) {
				if (findProspectDTO.getPostaladdressdto().isEmpty()) {
					findProspectDTO.setPostaladdressdto(updateProspectDTO.getPostaladdressdto());
					findProspectDTO.getPostaladdressdto().get(0).setSgin(findProspectDTO.getSgin());
					findProspectDTO.getPostaladdressdto().get(0).setVersion(0);
					postalAddressDS.create(findProspectDTO.getPostaladdressdto().get(0));
				} else {
					updateLocalizationData(updateProspectDTO.getPostaladdressdto().get(0),
							findProspectDTO.getPostaladdressdto().get(0));
					postalAddressDS.update(findProspectDTO.getPostaladdressdto().get(0));
				}
			}

			if (updateProspectDTO.getEmaildto() != null && !updateProspectDTO.getEmaildto().isEmpty()) {

				com.airfrance.repind.dto.adresse.EmailDTO email = updateProspectDTO.getEmaildto().iterator().next();

				// REPIND-1767 : Update of an UTF8 data when no Creation have been done
				// Update Mode do not exist for Prospect
				if (findProspectDTO.getEmaildto().isEmpty()) {

					findProspectDTO.setEmaildto(updateProspectDTO.getEmaildto());

					com.airfrance.repind.dto.adresse.EmailDTO emailToUpdate = findProspectDTO.getEmaildto().iterator()
							.next();

					if (emailToUpdate != null && emailToUpdate.getEmail() != null) {

						String normalizedEmail = SicStringUtils.normalizeEmailOnlyASCII(emailToUpdate.getEmail());
						if (normalizedEmail != null && !"".equals(normalizedEmail)) {

							emailToUpdate.setEmail(normalizedEmail);
							emailToUpdate.setSgin(findProspectDTO.getSgin());
							emailToUpdate.setVersion(0);
							emailDS.create(emailToUpdate);
						}
					}

				} else {
					com.airfrance.repind.dto.adresse.EmailDTO emailToUpdate = findProspectDTO.getEmaildto().iterator()
							.next();

					if (!emailToUpdate.getEmail().equalsIgnoreCase(email.getEmail())) {
						emailToUpdate.setDateModification(new Date());
						emailToUpdate.setSiteModification("VLB");
						emailToUpdate.setSignatureModification("RI");
						emailToUpdate.setEmail(email.getEmail());
						emailDS.update(emailToUpdate);
					}
				}
			}

			if (updateProspectDTO.getPreferenceDTO() != null && !updateProspectDTO.getPreferenceDTO().isEmpty()) {

				updateProspectDTO.setSgin(findProspectDTO.getSgin());
				SignatureDTO signatureFromWS = new SignatureDTO();
				signatureFromWS.setSignature(updateProspectDTO.getSignatureModification());
				signatureFromWS.setSite(updateProspectDTO.getSiteModification());
				signatureFromWS.setDate(new Date());

				preferenceDS.updateIndividualPreferences(updateProspectDTO, findProspectDTO, signatureFromWS);

			}
		}

		return individu.getSgin();
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String updateAnIndividualProspectSharepoint(IndividuDTO updateProspectDTO, IndividuDTO findProspectDTO)
			throws JrafDomainException {

		Individu individu = new Individu();

		if (findProspectDTO != null) {
			log.debug("Update prospect with gin : " + findProspectDTO.getSgin().toString());

			// Etape 0: map gender-civility
			setGenderSharepoint(updateProspectDTO);

			// Etape 1: maj des données prospect
			updateProspectDataSharepoint(updateProspectDTO, findProspectDTO);

			// Etape 2: maj donnees telecoms
			if (updateProspectDTO.getTelecoms() != null) {
				if (findProspectDTO.getTelecoms() == null) {
					findProspectDTO.setTelecoms(updateProspectDTO.getTelecoms());
				} else {
					updateTelecomData(updateProspectDTO.getTelecoms().iterator().next(),
							findProspectDTO.getTelecoms().iterator().next());
				}
			} else {
				findProspectDTO.setTelecoms(null);
			}

			if(updateProspectDTO.getCommunicationpreferencesdto() != null){
				updateComPrefDataOptin(updateProspectDTO, findProspectDTO);

			}
			IndividuTransform.dto2Bo(findProspectDTO, individu);
			IndividuTransform.addGinToLink(individu);

			individuRepository.saveAndFlush(individu);

			// Etape 3: maj donnees localisation et preferences
			if (updateProspectDTO.getPostaladdressdto() != null && !updateProspectDTO.getPostaladdressdto().isEmpty()) {
				if (findProspectDTO.getPostaladdressdto().isEmpty()) {
					findProspectDTO.setPostaladdressdto(updateProspectDTO.getPostaladdressdto());
					findProspectDTO.getPostaladdressdto().get(0).setSgin(findProspectDTO.getSgin());
					findProspectDTO.getPostaladdressdto().get(0).setVersion(0);
					postalAddressDS.create(findProspectDTO.getPostaladdressdto().get(0));
				} else {
					updateLocalizationData(updateProspectDTO.getPostaladdressdto().get(0),
							findProspectDTO.getPostaladdressdto().get(0));
					postalAddressDS.update(findProspectDTO.getPostaladdressdto().get(0));
				}
			}

			if (updateProspectDTO.getEmaildto() != null && !updateProspectDTO.getEmaildto().isEmpty()) {

				com.airfrance.repind.dto.adresse.EmailDTO email = updateProspectDTO.getEmaildto().iterator().next();

				// REPIND-1767 : Update of an UTF8 data when no Creation have been done
				// Update Mode do not exist for Prospect
				if (findProspectDTO.getEmaildto().isEmpty()) {

					findProspectDTO.setEmaildto(updateProspectDTO.getEmaildto());

					com.airfrance.repind.dto.adresse.EmailDTO emailToUpdate = findProspectDTO.getEmaildto().iterator()
							.next();

					if (emailToUpdate != null && emailToUpdate.getEmail() != null) {

						String normalizedEmail = SicStringUtils.normalizeEmailOnlyASCII(emailToUpdate.getEmail());
						if (normalizedEmail != null && !"".equals(normalizedEmail)) {

							emailToUpdate.setEmail(normalizedEmail);
							emailToUpdate.setSgin(findProspectDTO.getSgin());
							emailToUpdate.setVersion(0);
							emailDS.create(emailToUpdate);
						}
					}

				} else {
					com.airfrance.repind.dto.adresse.EmailDTO emailToUpdate = findProspectDTO.getEmaildto().iterator()
							.next();

					if (!emailToUpdate.getEmail().equalsIgnoreCase(email.getEmail())) {
						emailToUpdate.setDateModification(new Date());
						emailToUpdate.setSiteModification("VLB");
						emailToUpdate.setSignatureModification("RI");
						emailToUpdate.setEmail(email.getEmail());
						emailDS.update(emailToUpdate);
					}
				}
			}

			if (updateProspectDTO.getPreferenceDTO() != null && !updateProspectDTO.getPreferenceDTO().isEmpty()) {

				updateProspectDTO.setSgin(findProspectDTO.getSgin());
				SignatureDTO signatureFromWS = new SignatureDTO();
				signatureFromWS.setSignature(updateProspectDTO.getSignatureModification());
				signatureFromWS.setSite(updateProspectDTO.getSiteModification());
				signatureFromWS.setDate(new Date());

				preferenceDS.updateIndividualPreferences(updateProspectDTO, findProspectDTO, signatureFromWS);

			}
		}

		return individu.getSgin();
	}

	// REPIND-555 : Prospect to Individual

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateAProspectToIndividual(IndividuDTO findProspectDTO) throws JrafDomainException {

		Individu individu = new Individu();
		findProspectDTO.setType("I");
		IndividuTransform.dto2Bo(findProspectDTO, individu);
		individu.setMandatoryDBFields();
		individu.setVersion(individuRepository.getVersionOfIndividual(findProspectDTO.getSgin()));

		individuRepository.saveAndFlush(individu);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value = "transactionManagerRepind")
	public String createAnIndividualExternal(IndividuDTO individuDTO) throws JrafDomainException {

		setGenderAndCivility(individuDTO);

		// Use SpringData to insert an Individual
		Individu individu = new Individu();
		IndividuTransform.dto2Bo(individuDTO, individu);
		individu.setSgin(EncryptionUtils.processCheckDigit(individuRepository.getExternalIdentifierNextValue()));

		// REPIND-1256 : Add value in case of External Creation
		IndividuTransform.addDefaultPhonetic(individu);

		IndividuTransform.addGinToLink(individu);
		individuRepository.saveAndFlush(individu);
		return individu.getSgin();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value = "transactionManagerRepind")
	public String updateAnIndividualExternal(IndividuDTO individuDTO) throws JrafDomainException {

		Individu individu  = new Individu();

		if (individuDTO == null || individuDTO.getSgin() == null || "".equals(individuDTO.getSgin())) {
			return null;
		}

		// Use SpringData to update an Individual
		Individu individuFromDB = individuRepository.findBySgin(individuDTO.getSgin());
		IndividuDTO individuDTOFromDB = IndividuTransform.bo2DtoForUpdate(individuFromDB);

		// On process External Identifier ne peut mettre a jour que des données d'un
		// individu du type External !
		if (ProcessEnum.E.getCode().equals(individuFromDB.getType())) {

			updateGenderAndCivility(individuDTO, individuDTOFromDB);

			updateProspectDataV7(individuDTO, individuDTOFromDB);

			IndividuTransform.dto2BoForUpdate(individuDTOFromDB, individu);

			individuRepository.saveAndFlush(individu);
		}

		return individuFromDB.getSgin();

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value = "transactionManagerRepind")
	public String updateAnKidSoloIndividual(IndividuDTO individuDTO) throws JrafDomainException {

		if (individuDTO == null || individuDTO.getSgin() == null || "".equals(individuDTO.getSgin())) {
			return null;
		}

		// Use SpringData to update an Individual
		Individu individu = individuRepository.getOne(individuDTO.getSgin());
		Optional<Profils> profils = profilsRepository.findById(individuDTO.getSgin());

		if (ProcessEnum.K.getCode().equals(individu.getType())) {
			setDefaultSexe(individuDTO);
			IndividuTransform.dto2BoForUpdate(individuDTO, individu);

			individu.setProfils(null);

			individuRepository.saveAndFlush(individu);

			if (individuDTO.getProfilsdto() != null) {
				if (profils.isPresent()) {
					profils.get().setScode_langue(individuDTO.getProfilsdto().getScode_langue());
					profilsRepository.saveAndFlush(profils.get());
				}
			}

		}
		return individu.getSgin();

	}

	/* PROTECTED REGION END */

	// Test LastName/FirstName and Email
	public void createModifyIndividualBusinessRule(CreateModifyIndividualResquestDTO resquest)
			throws JrafDomainException {

		// Bussiness Rule for LastName and FirstName, Only iso latin caracters
		if (!NormalizedStringUtils.isNormalizableString(resquest.getIndividu().getNom())) {
			throw new InvalidParameterException("Invalid character in lastname");
		}
		if (!NormalizedStringUtils.isNormalizableString(resquest.getIndividu().getPrenom())) {
			throw new InvalidParameterException("Invalid character in firstname");
		}

		// Email address quality
		if (resquest.getEmail() != null) {
			for (EmailDTO emailDTO : resquest.getEmail()) {
				if (!emailDTO.getStatutMedium().equals("V") && !emailDTO.getStatutMedium().equals("X")
						&& !emailDTO.getStatutMedium().equals("I")) {
					throw new InvalidParameterException("Invalid email status");
				}
			}
		}
	}

	private void updateTelecomData(TelecomsDTO updatedTelecomsDTO, TelecomsDTO foundTelecomsDTO) {
		if (foundTelecomsDTO.getSgin() == null && updatedTelecomsDTO.getSgin() != null) {
			foundTelecomsDTO.setSgin(updatedTelecomsDTO.getSgin());
		}
		if (updatedTelecomsDTO.getScode_medium() != null) {
			foundTelecomsDTO.setScode_medium(updatedTelecomsDTO.getScode_medium());
		}
		if (updatedTelecomsDTO.getSstatut_medium() != null) {
			foundTelecomsDTO.setSstatut_medium(updatedTelecomsDTO.getSstatut_medium());
		}
		if (updatedTelecomsDTO.getDdate_modification() != null) {
			foundTelecomsDTO.setDdate_modification(updatedTelecomsDTO.getDdate_modification());
		} else {
			foundTelecomsDTO.setDdate_modification(null);
		}
		if (updatedTelecomsDTO.getSsignature_modification() != null) {
			foundTelecomsDTO.setSsignature_modification(updatedTelecomsDTO.getSsignature_modification());
		} else {
			foundTelecomsDTO.setSsignature_modification(null);
		}
		if (updatedTelecomsDTO.getSsite_modification() != null) {
			foundTelecomsDTO.setSsite_modification(updatedTelecomsDTO.getSsite_modification());
		} else {
			foundTelecomsDTO.setSsite_modification(null);
		}
		if (updatedTelecomsDTO.getSterminal() != null) {
			foundTelecomsDTO.setSterminal(updatedTelecomsDTO.getSterminal());
		}
		if (updatedTelecomsDTO.getScode_region() != null) {
			foundTelecomsDTO.setScode_region(updatedTelecomsDTO.getScode_region());
		} else {
			foundTelecomsDTO.setScode_region(null);
		}
		if (updatedTelecomsDTO.getSnumero() != null) {
			foundTelecomsDTO.setSnumero(updatedTelecomsDTO.getSnumero());
		} else {
			foundTelecomsDTO.setSnumero(null);
		}
		if (updatedTelecomsDTO.getSnormalized_numero() != null) {
			foundTelecomsDTO.setSnormalized_numero(updatedTelecomsDTO.getSnormalized_numero());
		} else {
			foundTelecomsDTO.setSnormalized_numero(null);
		}
		if (updatedTelecomsDTO.getSnormalized_country() != null) {
			foundTelecomsDTO.setSnormalized_country(updatedTelecomsDTO.getSnormalized_country());
		} else {
			foundTelecomsDTO.setSnormalized_country(null);
		}
		if (updatedTelecomsDTO.getCountryCode() != null) {
			foundTelecomsDTO.setCountryCode(updatedTelecomsDTO.getCountryCode());
		} else {
			foundTelecomsDTO.setCountryCode(null);
		}
	}

	private void updateLocalizationData(PostalAddressDTO updatedLocalizationDTO,
			PostalAddressDTO foundLocalizationDTO) {
		if (foundLocalizationDTO.getSgin() == null && updatedLocalizationDTO.getSgin() != null) {
			foundLocalizationDTO.setSgin(updatedLocalizationDTO.getSgin());
		}
		if (updatedLocalizationDTO.getSville() != null) {
			foundLocalizationDTO.setSville(updatedLocalizationDTO.getSville());
		} else {
			foundLocalizationDTO.setSville(null);
		}
		if (updatedLocalizationDTO.getScode_pays() != null) {
			foundLocalizationDTO.setScode_pays(updatedLocalizationDTO.getScode_pays());
		} else {
			foundLocalizationDTO.setScode_pays(null);
		}
		if (updatedLocalizationDTO.getScode_medium() != null) {
			foundLocalizationDTO.setScode_medium(updatedLocalizationDTO.getScode_medium());
		}
		if (updatedLocalizationDTO.getSstatut_medium() != null) {
			foundLocalizationDTO.setSstatut_medium(updatedLocalizationDTO.getSstatut_medium());
		}
		if (updatedLocalizationDTO.getDdate_modification() != null) {
			foundLocalizationDTO.setDdate_modification(updatedLocalizationDTO.getDdate_modification());
		} else {
			foundLocalizationDTO.setDdate_modification(null);
		}
		if (updatedLocalizationDTO.getSsignature_modification() != null) {
			foundLocalizationDTO.setSsignature_modification(updatedLocalizationDTO.getSsignature_modification());
		} else {
			foundLocalizationDTO.setSsignature_modification(null);
		}
		if (updatedLocalizationDTO.getSsite_modification() != null) {
			foundLocalizationDTO.setSsite_modification(updatedLocalizationDTO.getSsite_modification());
		} else {
			foundLocalizationDTO.setSsite_modification(null);
		}
		if (updatedLocalizationDTO.getSno_et_rue() != null) {
			foundLocalizationDTO.setSno_et_rue(updatedLocalizationDTO.getSno_et_rue());
		} else {
			foundLocalizationDTO.setSno_et_rue(null);
		}
		if (updatedLocalizationDTO.getScode_province() != null) {
			foundLocalizationDTO.setScode_province(updatedLocalizationDTO.getScode_province());
		} else {
			foundLocalizationDTO.setScode_province(null);
		}
		if (updatedLocalizationDTO.getScode_postal() != null) {
			foundLocalizationDTO.setScode_postal(updatedLocalizationDTO.getScode_postal());
		} else {
			foundLocalizationDTO.setScode_postal(null);
		}
	}

	private void updateProspectDataV7(IndividuDTO updateProspectDTO, IndividuDTO findProspectDTO) {
		if (updateProspectDTO.getCivilite() != null) {
			findProspectDTO.setCivilite(updateProspectDTO.getCivilite());
		}
		if (updateProspectDTO.getSexe() != null) {
			findProspectDTO.setSexe(updateProspectDTO.getSexe());
		}
		if (updateProspectDTO.getPrenom() != null) {
			findProspectDTO.setPrenom(updateProspectDTO.getPrenom());
		}
		if (updateProspectDTO.getCodeLangue() != null) {
			findProspectDTO.setCodeLangue(updateProspectDTO.getCodeLangue());
		}
		if (updateProspectDTO.getNom() != null) {
			findProspectDTO.setNom(updateProspectDTO.getNom());
		}
		if (updateProspectDTO.getSecondPrenom() != null) {
			findProspectDTO.setSecondPrenom(updateProspectDTO.getSecondPrenom());
		}
		if (updateProspectDTO.getDateNaissance() != null) {
			findProspectDTO.setDateNaissance(updateProspectDTO.getDateNaissance());
		}
		if (updateProspectDTO.getNationalite() != null) {
			findProspectDTO.setNationalite(updateProspectDTO.getNationalite());
		}
		if (updateProspectDTO.getAutreNationalite() != null) {
			findProspectDTO.setAutreNationalite(updateProspectDTO.getAutreNationalite());
		}
		if (updateProspectDTO.getStatutIndividu() != null) {
			findProspectDTO.setStatutIndividu(updateProspectDTO.getStatutIndividu());
		} else {
			if ("X".equals(findProspectDTO.getStatutIndividu())) {
				findProspectDTO.setStatutIndividu("V");
			}
		}

		if (updateProspectDTO.getDateModification() != null) {
			findProspectDTO.setDateModification(updateProspectDTO.getDateModification());
		}
		if (updateProspectDTO.getSignatureModification() != null) {
			findProspectDTO.setSignatureModification(updateProspectDTO.getSignatureModification());
		}
		if (updateProspectDTO.getSiteModification() != null) {
			findProspectDTO.setSiteModification(updateProspectDTO.getSiteModification());
		}

		findProspectDTO = setAllName(findProspectDTO);

	}

	private void updateProspectDataSharepoint(IndividuDTO updateProspectDTO, IndividuDTO findProspectDTO) {
		// Count if any input individual information is different from the original individual one
		int counter = 0;

		if (updateProspectDTO.getSexe() != null){
			if(!findProspectDTO.getCivilite().equals(updateProspectDTO.getCivilite())
					&& !findProspectDTO.getSexe().equals(updateProspectDTO.getSexe())){
				findProspectDTO.setCivilite(updateProspectDTO.getCivilite());
				findProspectDTO.setSexe(updateProspectDTO.getSexe());
				counter++;
			}
		}

		if (updateProspectDTO.getPrenom() != null) {
			if ((findProspectDTO.getPrenom() != null
					&& !updateProspectDTO.getPrenom().equals(findProspectDTO.getPrenom()))
					|| (findProspectDTO.getPrenom() == null)){
				findProspectDTO.setPrenom(updateProspectDTO.getPrenom());
				counter++;
			}
		}
		if (updateProspectDTO.getNom() != null) {
			if ((findProspectDTO.getNom() != null
					&& !updateProspectDTO.getNom().equals(findProspectDTO.getNom()))
					|| (findProspectDTO.getNom() == null)){
				findProspectDTO.setNom(updateProspectDTO.getNom());
				counter++;
			}
		}
		if (updateProspectDTO.getDateNaissance() != null) {
			if ((findProspectDTO.getDateNaissance() != null
					&& updateProspectDTO.getDateNaissance().compareTo(findProspectDTO.getDateNaissance()) != 0)
					|| (findProspectDTO.getDateNaissance() == null)){
				findProspectDTO.setDateNaissance(updateProspectDTO.getDateNaissance());
				counter++;
			}
		}
		if (updateProspectDTO.getDateModification() != null && counter != 0) {
			findProspectDTO.setDateModification(updateProspectDTO.getDateModification());
		}
		if (updateProspectDTO.getSignatureModification() != null && counter != 0) {
			findProspectDTO.setSignatureModification(updateProspectDTO.getSignatureModification());
		}
		if (updateProspectDTO.getSiteModification() != null && counter != 0) {
			findProspectDTO.setSiteModification(updateProspectDTO.getSiteModification());
		}

		findProspectDTO = setAllName(findProspectDTO);

	}

	private void updateComPrefDataOptin(IndividuDTO updateProspectDTO, IndividuDTO findProspectDTO){

		List<CommunicationPreferencesDTO> communicationPreferencesDTOListProspect = updateProspectDTO.getCommunicationpreferencesdto();
		List<CommunicationPreferencesDTO> communicationPreferencesDTOListFindProspect = findProspectDTO.getCommunicationpreferencesdto();

		for(CommunicationPreferencesDTO communicationPreferencesDTO : communicationPreferencesDTOListProspect) {

			for(CommunicationPreferencesDTO communicationPreferencesDTO1 : communicationPreferencesDTOListFindProspect) {
				if(communicationPreferencesDTO.getDomain().equals(communicationPreferencesDTO1.getDomain()) &&
						communicationPreferencesDTO.getComGroupType().equals(communicationPreferencesDTO1.getComGroupType()) &&
				communicationPreferencesDTO.getComType().equals(communicationPreferencesDTO1.getComType())){
					String subscribe = communicationPreferencesDTO.getSubscribe();
					if(subscribe != null) {
						communicationPreferencesDTO1.setSubscribe(subscribe);
					}

				}
			}
		}


	}

	// Set new compref optin date and channel with the existing compref if they are
	// equals
	public void reuseProspectInitialComPrefData(List<CommunicationPreferencesDTO> commPrefsFromDB,
			List<CommunicationPreferencesDTO> commPrefsFromWS) {
		if (commPrefsFromDB != null && !commPrefsFromDB.isEmpty() && commPrefsFromWS != null
				&& !commPrefsFromWS.isEmpty()) {
			for (CommunicationPreferencesDTO dtoFromWS : commPrefsFromWS) {
				for (CommunicationPreferencesDTO boFromDB : commPrefsFromDB) {
					compareAndUpdateNewComPref(boFromDB, dtoFromWS);
				}
			}
		}
	}

	private void compareAndUpdateNewComPref(CommunicationPreferencesDTO boFromDB,
			CommunicationPreferencesDTO dtoFromWS) {
		if (boFromDB != null && dtoFromWS != null) {
			if (sameComPref(boFromDB, dtoFromWS)) {
				// Initial optin = Y --> request optin = Y ===> we keep the initial date of
				// optin
				if (boFromDB.getSubscribe().equalsIgnoreCase("Y") && dtoFromWS.getSubscribe().equalsIgnoreCase("Y")) {
					if (boFromDB.getDateOptin() != null) {
						dtoFromWS.setDateOptin(boFromDB.getDateOptin());
					}
					if (boFromDB.getChannel() != null) {
						dtoFromWS.setChannel(boFromDB.getChannel());
					}
					if (boFromDB.getDateOptinPartners() != null) {
						dtoFromWS.setDateOptinPartners(boFromDB.getDateOptinPartners());
					}

					// Also check market/language
					if (boFromDB.getMarketLanguageDTO() != null && dtoFromWS.getMarketLanguageDTO() != null) {
						compareAndUpdateMarketLanguage(boFromDB.getMarketLanguageDTO(),
								dtoFromWS.getMarketLanguageDTO());
					}
				}
			}
		}
	}

	private void compareAndUpdateMarketLanguage(Set<MarketLanguageDTO> mlFromDB, Set<MarketLanguageDTO> mlFromWS) {
		for (MarketLanguageDTO mlDtoFromWS : mlFromWS) {
			for (MarketLanguageDTO mlDtoFromDB : mlFromDB) {
				if (sameMarketLanguage(mlDtoFromDB, mlDtoFromWS)) {
					// Initial optin = Y --> request optin = Y ===> we keep the initial date of
					// optin
					if (mlDtoFromDB.getOptIn().equalsIgnoreCase("Y") && mlDtoFromWS.getOptIn().equalsIgnoreCase("Y")) {
						if (mlDtoFromDB.getDateOfConsent() != null) {
							// Reuse of initial date of consent from DB
							mlDtoFromWS.setDateOfConsent(mlDtoFromDB.getDateOfConsent());
						}
					}
				}
			}
		}
	}

	private boolean sameMarketLanguage(MarketLanguageDTO mlDtoFromDB, MarketLanguageDTO mlDtoFromWS) {
		boolean sameMarket = false;

		if (mlDtoFromDB.getMarket() != null && mlDtoFromWS.getMarket() != null) {
			sameMarket = mlDtoFromDB.getLanguage().equalsIgnoreCase(mlDtoFromWS.getLanguage());
		}

		if (sameMarket && mlDtoFromDB.getLanguage() != null && mlDtoFromWS.getLanguage() != null) {
			return mlDtoFromDB.getLanguage().equalsIgnoreCase(mlDtoFromWS.getLanguage());
		} else {
			return false;
		}
	}

	private boolean sameComPref(CommunicationPreferencesDTO boFromDB, CommunicationPreferencesDTO dtoFromWS) {
		boolean areEquals = false;
		if (boFromDB != null && dtoFromWS != null) {
			if (boFromDB.getComType() != null && dtoFromWS.getComType() != null) {
				areEquals = boFromDB.getComType().equalsIgnoreCase(dtoFromWS.getComType());
				if (!areEquals) {
					return areEquals;
				}
			}
			if (boFromDB.getComGroupType() != null && dtoFromWS.getComGroupType() != null) {
				areEquals = boFromDB.getComGroupType().equalsIgnoreCase(dtoFromWS.getComGroupType());
				if (!areEquals) {
					return areEquals;
				}
			}
			if (boFromDB.getDomain() != null && dtoFromWS.getDomain() != null) {
				areEquals = boFromDB.getDomain().equalsIgnoreCase(dtoFromWS.getDomain());
				if (!areEquals) {
					return areEquals;
				}
			}
		}
		return areEquals;
	}

	private IndividuDTO setAllName(IndividuDTO prospect) {
		if (prospect.getNom() != null) {
			if (!"".equals(prospect.getNom())) {
				prospect.setNom(NormalizedStringUtils.normalizeString(prospect.getNom()).toUpperCase());
			} else {
				prospect.setNom(null);
			}
		}
		if (prospect.getPrenom() != null) {
			if (!"".equals(prospect.getPrenom())) {
				prospect.setPrenom(NormalizedStringUtils.normalizeString(prospect.getPrenom()).toUpperCase());
			} else {
				prospect.setPrenom(null);
			}
		}
		if (prospect.getSecondPrenom() != null) {
			if (!"".equals(prospect.getSecondPrenom())) {
				prospect.setSecondPrenom(
						NormalizedStringUtils.normalizeString(prospect.getSecondPrenom()).toUpperCase());
			} else {
				prospect.setSecondPrenom(null);
			}
		}
		return prospect;
	}

	/* PROTECTED REGION ID(_LVcMwJfEZEfefvZ541Zdf) ENABLED START */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public Individu findByEmail(String email) throws JrafDomainException {
		/* PROTECTED REGION ID(_LVcMwJfEZEfefvZ541ZdfFinDByEmail) ENABLED START */
		String whereClause = "";
		String test = "=";
		String add = "";
		String upper = "";
		StringBuffer buffer = new StringBuffer("select i from Individu i JOIN i.email e where ");
		if (email != null) {
			buffer.append("( i.statutIndividu = 'V' or i.statutIndividu = 'P' ) ").append(add);
			buffer.append(" and ").append("( i.type = 'I' ) ").append(add);
			buffer.append(" and ").append(upper).append("(e.email) ").append(test).append(upper).append(" (:email)")
					.append(add);
			whereClause = buffer.toString();
			Query query = getEntityManager().createQuery(whereClause);
			query.setParameter("email", email.toLowerCase());
			List res = query.getResultList();
			if (res.isEmpty()) {
				return null;
			}
			return (Individu) res.get(0);
		}
		throw new JrafDomainException("Missing parameter email");
		/* PROTECTED REGION END */
	}

	@Transactional(value = "transactionManagerRepind", rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public IndividuDTO getIndividualOrProspectByGin(String gin) throws JrafDomainException {
		String whereClause = "";
		String test = "=";
		String add = "";
		String upper = "";
		StringBuffer buffer = new StringBuffer("select i from Individu i where ");
		if (gin != null) {
			buffer.append("(i.sgin) ").append(test).append(upper).append(" (:sgin)").append(add);
			whereClause = buffer.toString();
			Query query = getEntityManager().createQuery(whereClause);
			query.setParameter("sgin", gin);
			List res = query.getResultList();
			if (res.isEmpty()) {
				return null;
			}
			return IndividuTransform.bo2Dto((Individu) res.get(0));
		}
		throw new JrafDomainException("Missing parameter gin");
	}

	public IndividuDTO getIndividualOrProspectByGinExceptForgotten(String gin) throws JrafDomainException {
		return IndividuTransform.bo2DtoLight(individuRepository.getIndividualOrProspectByGinExceptForgotten(gin));
	}

	@Transactional(value = "transactionManagerRepind", rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<IndividuDTO> findProspectByEmail(String email) throws JrafDomainException {
		/* PROTECTED REGION ID(_LVcMwJfEZEfefvZ541ZdfFinDByEmail) ENABLED START */
		List<IndividuDTO> listInd = null;
		String whereClause = "";
		String test = "=";
		String add = "";
		String upper = "";
		StringBuffer buffer = new StringBuffer("select i from Individu i JOIN i.email e where ");
		if (email != null) {
			buffer.append("( i.type = 'W' ) ").append(add);
			buffer.append(" and ").append(upper).append("(e.email) ").append(test).append(upper).append(" (:email)")
					.append(add);
			whereClause = buffer.toString();
			Query query = getEntityManager().createQuery(whereClause);
			query.setParameter("email", email.toLowerCase());
			List res = query.getResultList();
			if (res.isEmpty()) {
				return null;
			} else {
				listInd = new ArrayList<IndividuDTO>();
				for (Object ind : res) {
					listInd.add(IndividuTransform.bo2Dto((Individu) ind));
				}
			}
			return listInd;
		}
		throw new JrafDomainException("Missing parameter email");
		/* PROTECTED REGION END */
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateIndividualComPref(Set<CommunicationPreferences> listeCommunicationPreferences, String gin)
			throws JrafDomainException {
		Optional<Individu> individu = individuRepository.findById(gin);

		// TODO to resolve ORPHAN delete problem must use addAll to comPref list
		if (individu.isPresent()) {
			if (individu.get().getCommunicationpreferences()!=null){
				individu.get().getCommunicationpreferences().addAll(listeCommunicationPreferences);
			}
			else{
				individu.get().setCommunicationpreferences(listeCommunicationPreferences);
			}
			Individu tmp = individuRepository.saveAndFlush(individu.get());
		} else {
			throw new NotFoundException("Gin = " + gin);

		}
	}

	/**
	 * deleteAProspect
	 * 
	 * @param prospect in ProspectDTO
	 * @return The deleteAProspect as <code>Boolean</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void deleteAProspect(IndividuDTO prospect) throws JrafDomainException {
		/* PROTECTED REGION ID(_n4RtIJ1AEeKEJ_XC1Z7jtg) ENABLED START */
		prospect.setStatutIndividu("X");
		Date date = new Date();
		prospect.setDateModification(date);

		individuRepository.saveAndFlush(IndividuTransform.dto2Bo(prospect));

		/* PROTECTED REGION END */
	}

	/**
	 * deleteAProspect
	 * 
	 * @param prospect in ProspectDTO
	 * @return The deleteAProspect as <code>Boolean</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(value = "transactionManagerRepind", rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public boolean deleteAProspectBoolean(IndividuDTO prospect) {

		/* PROTECTED REGION ID(_n4RtIJ1AEeKEJ_XC1Z7jtg) ENABLED START */
		try {

			Individu individu = individuRepository.getOne(prospect.getSgin());
			individu.setStatutIndividu("X");
			Date date = new Date();
			individu.setDateModification(date);
			individu.setSignatureModification("BATCH-INVAL-PP");
			individu.setSiteModification("BATCH_QVI");

			individuRepository.saveAndFlush(individu);

			return true;
		} catch (Exception ex) {
			log.fatal(ex);
			return false;
		}
		/* PROTECTED REGION END */
	}

	@Transactional(value = "transactionManagerRepind", rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public IndividuDTO getIndividuLightByGin(String gin) throws JrafDomainException {

		// Check input
		if (gin == null) {
			throw new JrafDaoException("Individual GIN cannot be empty!");
		}

		// Read the individual directly from the database
		IndividuLight individuLight = individuRepository.findIndividualLightByGin(gin);

		// Transform entity to DTO

		return IndividuTransform.bo2DtoLight(individuLight);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value = "transactionManagerRepind")
	public String createAnIndividualKidSolo(IndividuDTO individuDTO) throws JrafDomainException {

		setGenderAndCivility(individuDTO);

		Individu individu = new Individu();

		IndividuTransform.dto2Bo(individuDTO, individu);
		individu.setSgin(EncryptionUtils.processCheckDigit(individuRepository.getKidSoloNextValue()));
		IndividuTransform.addGinToLink(individu);

		ProfilsDTO profilDTO = new ProfilsDTO();
		profilDTO.setIversion(0);
		profilDTO.setSmailing_autorise("N");
		profilDTO.setSrin("0");
		profilDTO.setSsolvabilite("O");
		profilDTO.setSgin(individu.getSgin());
		if (individu.getProfils() != null) {
			profilDTO.setScode_langue(individu.getProfils().getScode_langue());
		}

		individu.setProfils(null);
		individuRepository.saveAndFlush(individu);

		profilsRepository.saveAndFlush(ProfilsTransform.dto2BoLight(profilDTO));

		return individu.getSgin();
	}

	public List<KidSoloIndividuDTO> findAnKidSoloIndividual(String lastname, String firstname,
			List<TelecomDTO> telecoms) throws JrafDomainException {
		return null;
	}

	public boolean isIndividualNotProvide(IndividuDTO individuDTO) {
		if (individuDTO != null) {
			String status = individuDTO.getStatutIndividu();
			String type = individuDTO.getType();
			if ("F".equalsIgnoreCase(status) || "K".equalsIgnoreCase(type) || "H".equalsIgnoreCase(type))
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

	public boolean isStatutNotReturned(IndividuDTO individuDTO) {
		if (individuDTO != null) {
			String statut = individuDTO.getStatutIndividu();
			String type = individuDTO.getType();
			if ("F".equals(statut) || "H".equals(type))
				return true;
			else {
				return false;
			}
		} else {
			return false;
		}
	}

	public IndividuDTO getAnyIndividualByGin(String gin) throws JrafDomainException {
		Individu individu = null;
		IndividuDTO individuDTO = null;

		// get en base
		individu = individuRepository.findBySgin(gin);

		// transformation bo -> dto
		if (individu != null) {
			individuDTO = IndividuTransform.bo2Dto(individu);
			// Account data
			if (individu.getAccountData() != null) {
				individuDTO.setAccountdatadto(AccountDataTransform.bo2Dto(individu.getAccountData()));
			}
			// Delegation data
			if (individu.getDelegateList() != null) {
				individuDTO.setDelegateListDTO(DelegationDataTransform.bo2Dto(individu.getDelegateList()));
			}
			if (individu.getDelegatorList() != null) {
				individuDTO.setDelegatorListDTO(DelegationDataTransform.bo2Dto(individu.getDelegatorList()));
			}
			// PrefilledNumbers
			if (individu.getPrefilledNumbers() != null) {
				individuDTO.setPrefilledNumbers(PrefilledNumbersTransform.bo2Dto(individu.getPrefilledNumbers()));
			}
		}
		return individuDTO;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateIndividualData(IndividuDTO individuDto) throws JrafDomainException {
		Individu individu = IndividuTransform.dto2Bo(individuDto);
		// Account data
		if (individuDto.getAccountdatadto() != null) {
			individu.setAccountData(AccountDataTransform.dto2Bo(individuDto.getAccountdatadto()));
		}
		// Delegation data : delegate
		if (individuDto.getDelegateListDTO() != null) {
			individu.setDelegateList(DelegationDataTransform.dto2Bo(individuDto.getDelegateListDTO()));
		}
		// Delegation data : delegator
		if (individuDto.getDelegatorListDTO() != null) {
			individu.setDelegatorList(DelegationDataTransform.dto2Bo(individuDto.getDelegatorListDTO()));
		}
		// Prefilled numbers
		if (individuDto.getPrefilledNumbers() != null) {
			individu.setPrefilledNumbers(PrefilledNumbersTransform.dto2Bo(individuDto.getPrefilledNumbers()));
		}

		individuRepository.saveAndFlush(individu);
	}

	public boolean isExisting(String gin) throws JrafDomainException {
		if (gin == null || gin.equals("")) {
			throw new JrafDomainException("");
		}
		Optional<Individu> individu = individuRepository.findById(gin);

		if (!individu.isPresent()) {
			return false;
		}
		return true;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateComPrefOfIndividual(IndividuDTO individuDTO) throws JrafDomainException {
		Individu individu = individuRepository.findBySgin(individuDTO.getSgin());

		if (individu != null) {
			if (individuDTO.getCommunicationpreferencesdto() != null) {
				if (individu.getCommunicationpreferences() == null) {
					individu.setCommunicationpreferences(new HashSet<CommunicationPreferences>());
				}
				individu.getCommunicationpreferences().clear();
				individu.getCommunicationpreferences()
						.addAll(CommunicationPreferencesTransform.dto2Bo(individuDTO.getCommunicationpreferencesdto()));
				individuRepository.saveAndFlush(individu);
			}
		} else {
			throw new NotFoundException("Gin = " + individuDTO.getSgin());
		}
	}

	public int isContractAndIndividualHaveSameNumber(String sgin) {
		return individuRepository.isContractAndIndividualHaveSameNumber(sgin).intValue();
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<String> getIndividuListByMatricule(String matricule) {
		// Call the DAO method
		return individuRepository.getIndividuListByMatricule(matricule);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<IndividuDTO> getIndividualsByInfos(String lastName, String firstName, String birthDate)
			throws JrafDomainException {
		List<IndividuDTO> individuDTOs = null;

		List<Individu> individusList = null;
		if (StringUtils.isNotEmpty(birthDate)) {
			individusList = individuRepository.findByNomAndPrenomAndDateNaissance(lastName, firstName, birthDate);
		} else { // No Birthdate on input
			individusList = individuRepository.findByNomAndPrenom(lastName, firstName);
		}

		if (individusList != null && !individusList.isEmpty()) {
			individuDTOs = new ArrayList<>();
			for (Individu gin : individusList) {
				IndividuDTO dto = IndividuTransform.bo2Dto(gin);
				individuDTOs.add(dto);
			}
		}

		return individuDTOs;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<IndividuDTO> getFamilyByMatricule(String matricule) throws JrafDomainException {
		List<IndividuDTO> individuDTOs = null;
		// Call the DAO method
		List<String> ginList = individuRepository.getFamilyByMatricule(matricule);
		if (ginList != null && !ginList.isEmpty()) {
			individuDTOs = new ArrayList<>();
			for (String gin : ginList) {
				IndividuDTO dto = IndividuTransform.bo2Dto(individuRepository.getOne(gin));
				individuDTOs.add(dto);
			}
		}
		return individuDTOs;
	}

	/**
	 * 
	 * @param matricule
	 * @param identifierOrder
	 * @return Family members got by matricule and order
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<IndividuDTO> getFamilyByMatriculeAndIdentifierOrder(String matricule, String identifierOrder)
			throws JrafDomainException {
		List<IndividuDTO> individuDTOs = null;

		// Call the DAO method
		List<String> ginList = individuRepository.getIndividuListByMatriculeAndIdentifierOrder(matricule,
				identifierOrder);

		if (ginList != null && !ginList.isEmpty()) {
			individuDTOs = new ArrayList<IndividuDTO>();

			for (String gin : ginList) {
				IndividuDTO dto = IndividuTransform.bo2Dto(individuRepository.getOne(gin));
				individuDTOs.add(dto);
			}
		}

		return individuDTOs;
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<BusinessRoleDTO> getBusinessRoleDTOByGIN(String sGIN) throws JrafDomainException {
		List<BusinessRoleDTO> businessRoleDTOs = new ArrayList<>();
		List<BusinessRole> businessRoles = null;
		// Call the DAO method
		if (sGIN != null && !sGIN.isEmpty()) {
			Optional<Individu> individu = individuRepository.findById(sGIN);

			if (individu.isPresent()) {
				businessRoles = individuRepository.getBusinessRoleByGIN(sGIN);
				if (businessRoles != null && !businessRoles.isEmpty()) {
					for (BusinessRole br : businessRoles) {
						if (br != null) {
							// Transform
							BusinessRoleDTO dto = BusinessRoleTransform.bo2Dto(br);
							if (dto != null) {
								businessRoleDTOs.add(dto);
							}
						}
					}
				}
			}
		}
		return businessRoleDTOs;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<RoleGPDTO> getGPRoleDTOByGIN(String sGIN) throws JrafDomainException {
		List<RoleGPDTO> roleGPDTOs = new ArrayList<>();
		List<RoleGP> roleGPs = null;

		// Call the DAO method
		if (sGIN != null && !sGIN.isEmpty()) {
			Optional<Individu> individu = individuRepository.findById(sGIN);

			if (individu.isPresent()) {
				roleGPs = individuRepository.getGPRoleByGIN(sGIN);
				if (roleGPs != null && !roleGPs.isEmpty()) {
					for (RoleGP gpr : roleGPs) {
						if (gpr != null) {
							// Transform
							RoleGPDTO dto = RoleGPTransform.bo2DtoLight(gpr);
							if (dto != null) {
								roleGPDTOs.add(dto);
							}
						}
					}
				}
			}
		}
		return roleGPDTOs;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<IndividuDTO> getIndividuByMatIdentifierOrder(String matricule, String identifierOrder)
			throws JrafDomainException {
		List<IndividuDTO> individuDTOs = null;
		// Call the DAO method
		List<String> ginList;
		if (!StringUtils.isEmpty(identifierOrder)) {
			ginList = individuRepository.getIndividuListByMatriculeAndIdentifierOrder(matricule, identifierOrder);
		} else {
			ginList = individuRepository.getIndividuListByMatriculeWithoutIdentifierOrder(matricule);
		}
		if (ginList != null && !ginList.isEmpty()) {
			individuDTOs = new ArrayList<>();
			for (String gin : ginList) {
				IndividuDTO dto = IndividuTransform.bo2Dto(individuRepository.getOne(gin));
				individuDTOs.add(dto);
			}
		}
		return individuDTOs;
	}

	public String individualMergeWithTalend(String gin0, String gin1) throws IOException {

		String context = "{\"ginSource\":\"" + gin0 + "\",\"ginTarget\":\"" + gin1 + "\"";

		String jsonObject = "{\"actionName\":\"runTask\","
				+ "\"authPass\":\"dqcrm\",\"authUser\":\"brmarcy-ext@airfrance.fr\","
				+ "\"mode\":\"synchronous\",\"taskId\":39," + "\"context\":" + context + "}}";

		String jsonObject64 = EncryptionUtils.encodeBase64(jsonObject);

		return callTalend(jsonObject64);
	}

	protected String callTalend(String jsonObject64) throws IOException {
		StringBuilder result = new StringBuilder();

		URL url = new URL(URL_RCT_MERGE_TALEND + jsonObject64);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();

		return result.toString();
	}

	/**
	 * Switch an individu to Hidden status. (For crise event)
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void switchToHidden(String gin, String site, String signature) throws JrafDomainException {
		Individu individuFromDB = individuRepository.findBySgin(gin);

		if (individuFromDB == null) {
			throw new NotFoundException("Unable to find following individual: " + gin);
		}

		individuRepository.updateTypeByGin(gin, "H", site, signature);
	}

	/**
	 * Switch a Prospect W, Traveler T to Individual I. We change TYPE of the
	 * individual to I value
	 */

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void switchToIndividual(String gin, String nom, String prenom, Date dateNaissance)
			throws JrafDomainException {
		switchToIndividual(gin, nom, prenom, dateNaissance, null, null);
	}

	/**
	 * Switch a Prospect W, Traveler T to Individual I. We change TYPE of the
	 * individual to I value (With control of an existing PROFIL)
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void switchToIndividual(String gin, String nom, String prenom, Date dateNaissance, String lang, String optin)
			throws JrafDomainException {

		Individu individuFromDB = individuRepository.findBySgin(gin);

		if (individuFromDB == null) {
			throw new NotFoundException("Unable to find following individual: " + gin);
		}

		if (StringUtils.isNotEmpty(individuFromDB.getType()) && !"I".equals(individuFromDB.getType())) {

			// On switch le type de l'Individu
			individuFromDB.setType("I");

			// On en profite pour mettre à jour le nom si il est null en base de données
			if (nom != null && !"".equals(nom)) {
				individuFromDB.setNom(NormalizedStringUtils.normalizeString(nom.trim()));
			}
			if (prenom != null && !"".equals(prenom)) {
				individuFromDB.setPrenom(NormalizedStringUtils.normalizeString(prenom.trim()));
			}

			Date DeuxJanvier1901 = new GregorianCalendar(1901, 01, 01).getTime();
			// On cherche une date de Naissance en base de données et Si il y a une date de
			// naisssance inferieur au 02/01/1901
			if (individuFromDB.getDateNaissance() != null && DeuxJanvier1901.after(individuFromDB.getDateNaissance())) {

				// On check si la date de naissance envoyé par le WS n'est NULL
				if (dateNaissance != null) {
					// Si la date de naissance passée est également incorrecte
					if (DeuxJanvier1901.after(individuFromDB.getDateNaissance())) {
						individuFromDB.setDateNaissance(null);
						// Si elle est bonne on la met a jour en même temps
					} else {
						individuFromDB.setDateNaissance(dateNaissance);
					}
					// Si le WS envoi une date de naissance NULL
				} else {
					// On vide la date de naissance qui etait incorrect avec les services adhesions
					// "119 - INVALID DATE LIMITE DEPASSEE : MAX 02JAN1901"
					individuFromDB.setDateNaissance(null);
				}
			}

			// On rajoute les informations de Phonetic SINDICT_NOM etc...
			individuFromDB.phonetise();

			// CREATE MISSING PROFIL
			// REPIND-1598 : We will create the PROFILE with data transmit by FB
			if (individuFromDB != null && individuFromDB.getProfils() == null) {

				Profils profil = new Profils();

				profil.setSrin(profilsRepository.getProfilsNextValue().toString());
				profil.setSgin(gin);
				profil.setIversion(1); // Default value
				profil.setSsolvabilite("O"); // Default value
				profil.setSetudiant("N"); // Default value
				profil.setInb_enfants(0); // Default value
				profil.setScode_langue(lang == null ? "FR" : lang); // FB provided or by Default
				profil.setSmailing_autorise(optin == null ? "N" : optin); // FB provided or by Default

				profilsRepository.saveAndFlush(profil);

				individuFromDB.setProfils(profil);
			}
		}

		individuRepository.saveAndFlush(individuFromDB);
	}

	@Transactional(readOnly = true)
	public IndividuDTO getCompleteDataByGin(String gin) throws JrafDomainException {
		Individu individu = null;
		IndividuDTO individuDTO = null;
		// get en base
		individu = individuRepository.findBySgin(gin);

		// transformation bo -> dto
		if (individu != null) {
			// REPIND-946 : do not return forgotten individual (status 'F')
			if (!"X".equalsIgnoreCase(individu.getStatutIndividu())
					&& !"F".equalsIgnoreCase(individu.getStatutIndividu())) {
				individuDTO = IndividuTransform.bo2DtoForUpdate(individu);
			}
		}
		return individuDTO;
	}

	private void setDefaultSexe(IndividuDTO individual) {
		if (individual == null) {
			return;
		}

		if (StringUtils.isEmpty(individual.getSexe())) {
			individual.setSexe(GenderEnum.UNKNOWN.toString());
		}
	}

	public List<IndividuDTO> findByFirstnameAndLastnameAndBirthdateAndMatricule(String nom, String prenom,
			String dateNaissance, String matricule) throws JrafDomainException {

		List<IndividuDTO> resultList = null;

		List<Individu> individuList = individuRepository.findByNomAndPrenomAndDateNaissanceAndMatricule(nom, prenom,
				dateNaissance, matricule);

		// transformation bo -> dto
		if (individuList != null && !individuList.isEmpty()) {
			for (Individu individu : individuList) {

				if (individu != null) {
					if (!"X".equalsIgnoreCase(individu.getStatutIndividu())
							&& !"F".equalsIgnoreCase(individu.getStatutIndividu())) {
						IndividuDTO individuDTO = IndividuTransform.bo2DtoForUpdate(individu);

						if (resultList == null) {
							resultList = new ArrayList<>();
						}

						resultList.add(individuDTO);
					}
				}
			}
		}

		return resultList;
	}

	/* PROTECTED REGION END */
	@Transactional(readOnly = true)
	public IndividuDTO createIndividuByGin(String gin) throws JrafDomainException {
		List<String> ginList = new ArrayList<>();
		IndividuDTO dto = null;
		String newGin = gin;

		do {
			Optional<Individu> individu = individuRepository.findById(newGin);
			newGin = null;
			if (individu.isPresent()) {
				dto = IndividuTransform.bo2Dto(individu.get());
				if (dto.getSgin() != dto.getGinFusion()) {
					newGin = dto.getGinFusion();
				}
				if (newGin != null) {
					dto = null;
					if (ginList.contains(newGin)) {
						throw new SicDomainException(_REF_198 + String.join(",", ginList));
					}
					ginList.add(newGin);
				}
			}
		} while (newGin != null);

		return dto;
	}

	/**
	 * Find Active individual by Email
	 * @param email String
	 * @return List of individual
	 * @throws JrafDomainException
	 */
	@Transactional
	public List<IndividuDTO> findIndividuByEmail(String email) throws JrafDomainException {
		List<Individu> boList = individuRepository.findActiveIndividualByEmail(email);
		if (CollectionUtils.isNotEmpty(boList)) {
			return boList
					.stream()
					.map(this::mapBoToDtoLight)
					.collect(Collectors.toList());
		}
		return null;
	}

	public boolean isAnIndividualType(IndividuDTO gin) {
		return ContextEnum.I.getCode().equalsIgnoreCase(gin.getType());
	}

	private IndividuDTO mapBoToDtoLight(Individu bo) {
		try {
			return IndividuTransform.bo2DtoLight(bo);
		} catch (JrafDomainException e) {
			log.error("unable to map individu BO");
			return null;
		}
	}
}
