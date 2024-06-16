package com.afklm.rigui.services.individu.internal;

/*PROTECTED REGION ID(_S2dsgPcUEd-Kx8TJdz7fGw DS i) ENABLED START*/


import com.afklm.rigui.dao.adresse.PostalAddressRepository;
import com.afklm.rigui.dao.environnement.VariablesRepository;
import com.afklm.rigui.dao.individu.IndividuRepository;
import com.afklm.rigui.dao.profil.ProfilsRepository;
import com.afklm.rigui.dto.adresse.PostalAddressDTO;
import com.afklm.rigui.dto.adresse.TelecomsDTO;
import com.afklm.rigui.dto.delegation.DelegationDataTransform;
import com.afklm.rigui.dto.individu.CommunicationPreferencesDTO;
import com.afklm.rigui.dto.individu.MarketLanguageDTO;
import com.afklm.rigui.dto.individu.SignatureDTO;
import com.afklm.rigui.dto.individu.TelecomDTO;
import com.afklm.rigui.dto.individu.*;
import com.afklm.rigui.dto.individu.adh.connexiondata.ConnexionDataRequestDTO;
import com.afklm.rigui.dto.individu.adh.connexiondata.ConnexionDataResponseDTO;
import com.afklm.rigui.dto.individu.adh.searchindividualbymulticriteria.IndividualMulticriteriaDTO;
import com.afklm.rigui.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaRequestDTO;
import com.afklm.rigui.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaResponseDTO;
import com.afklm.rigui.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.afklm.rigui.dto.individu.createmodifyindividual.CreateModifyIndividualResquestDTO;
import com.afklm.rigui.dto.individu.createmodifyindividual.EmailDTO;
import com.afklm.rigui.dto.preference.PreferenceDTO;
import com.afklm.rigui.dto.profil.ProfilsDTO;
import com.afklm.rigui.dto.profil.ProfilsTransform;
import com.afklm.rigui.dto.role.*;
import com.afklm.rigui.dto.ws.*;
import com.afklm.rigui.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.afklm.rigui.entity.individu.CommunicationPreferences;
import com.afklm.rigui.entity.individu.Individu;
import com.afklm.rigui.entity.individu.IndividuLight;
import com.afklm.rigui.entity.profil.Profils;
import com.afklm.rigui.entity.refTable.RefTableREF_CIVILITE;
import com.afklm.rigui.entity.role.BusinessRole;
import com.afklm.rigui.entity.role.RoleGP;
import com.afklm.rigui.repindutf8.dto.individu.KidSoloIndividuDTO;
import com.afklm.rigui.repindutf8.util.SicUtf8StringUtils;
import com.afklm.rigui.services.adresse.internal.EmailDS;
import com.afklm.rigui.services.adresse.internal.PostalAddressDS;
import com.afklm.rigui.services.internal.unitservice.adresse.AdresseUS;
import com.afklm.rigui.services.internal.unitservice.individu.IndividuUS;
import com.afklm.rigui.services.preference.internal.PreferenceDS;
import com.afklm.rigui.services.reference.internal.RefLanguageDS;
import com.afklm.rigui.services.role.internal.RoleDS;
import com.afklm.rigui.util.*;
import com.afklm.rigui.exception.*;
import com.afklm.rigui.exception.jraf.JrafDaoException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.enums.*;
import com.afklm.rigui.util.CallersUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import  com.afklm.rigui.enums.CivilityEnum;
import  com.afklm.rigui.enums.GenderEnum;

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
	public String getLastValidEmail(String gin) {
		return individuRepository.getLastValidEmail(gin);
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

	public IndividuDTO getIndividualOrProspectByGinExceptForgotten(String gin) throws JrafDomainException {
		return IndividuTransform.bo2DtoLight(individuRepository.getIndividualOrProspectByGinExceptForgotten(gin));
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
				IndividuDTO dto = IndividuTransform.bo2Dto(individuRepository.findById(gin).get());
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
				IndividuDTO dto = IndividuTransform.bo2Dto(individuRepository.findById(gin).get());
				individuDTOs.add(dto);
			}
		}

		return individuDTOs;
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
				IndividuDTO dto = IndividuTransform.bo2Dto(individuRepository.findById(gin).get());
				individuDTOs.add(dto);
			}
		}
		return individuDTOs;
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
