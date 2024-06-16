package com.airfrance.repind.service.agence.internal;

import com.airfrance.repind.entity.refTable.RefTableREF_STATUTPM;
import com.airfrance.repind.entity.refTable.RefTableREF_STATUT_IATA;
import com.airfrance.repind.entity.refTable.RefTableREF_STA_MED;
import com.airfrance.repind.entity.refTable.RefTableREF_TYP_AGEN;
import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.*;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.bean.AgenceBean;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.adresse.TelecomsRepository;
import com.airfrance.repind.dao.agence.AgenceRepository;
import com.airfrance.repind.dao.agence.BspDataRepository;
import com.airfrance.repind.dao.agence.MembreReseauRepository;
import com.airfrance.repind.dao.agence.OfficeIDRepository;
import com.airfrance.repind.dao.firme.*;
import com.airfrance.repind.dao.profil.ProfilDemarchageRepository;
import com.airfrance.repind.dao.profil.Profil_mereRepository;
import com.airfrance.repind.dao.zone.PmZoneRepository;
import com.airfrance.repind.dto.adresse.*;
import com.airfrance.repind.dto.agence.*;
import com.airfrance.repind.dto.firme.*;
import com.airfrance.repind.dto.profil.ProfilDemarchageTransform;
import com.airfrance.repind.dto.profil.ProfilFirmeDTO;
import com.airfrance.repind.dto.profil.Profil_mereDTO;
import com.airfrance.repind.dto.profil.Profil_mereTransform;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.zone.*;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.agence.BspData;
import com.airfrance.repind.entity.agence.MembreReseau;
import com.airfrance.repind.entity.agence.OfficeID;
import com.airfrance.repind.entity.firme.*;
import com.airfrance.repind.entity.firme.enums.LegalPersonStatusEnum;
import com.airfrance.repind.entity.firme.enums.SynonymTypeEnum;
import com.airfrance.repind.entity.profil.ProfilDemarchage;
import com.airfrance.repind.entity.profil.Profil_mere;
import com.airfrance.repind.entity.zone.PmZone;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.entity.zone.enums.PrivilegedLinkEnum;
import com.airfrance.repind.exception.agence.RA2AgencyNotAllowedException;
import com.airfrance.repind.exception.TooManyResultsDaoException;
import com.airfrance.repind.exception.TooManyResultsDomainException;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.agence.internal.helper.AgencyValidationHelper;
import com.airfrance.repind.service.firm.internal.EtablissementDS;
import com.airfrance.repind.service.firm.internal.NumeroIdentDS;
import com.airfrance.repind.service.internal.unitservice.adresse.EmailUS;
import com.airfrance.repind.service.internal.unitservice.adresse.TelecomUS;
import com.airfrance.repind.service.internal.unitservice.agence.MembreReseauUS;
import com.airfrance.repind.service.internal.unitservice.firm.GestionPMUS;
import com.airfrance.repind.service.internal.unitservice.firm.NumeroIdentUS;
import com.airfrance.repind.service.internal.unitservice.firm.SegmentationUS;
import com.airfrance.repind.service.internal.unitservice.firm.SynonymeUS;
import com.airfrance.repind.service.internal.unitservice.profil.ProfilFirmeUS;
import com.airfrance.repind.service.internal.unitservice.zone.LienIntCpZdUs;
import com.airfrance.repind.service.internal.unitservice.zone.PmZoneUS;
import com.airfrance.repind.service.internal.unitservice.zone.ZoneCommUS;
import com.airfrance.repind.service.reference.internal.RefCityDS;
import com.airfrance.repind.service.reference.internal.RefCountryDS;
import com.airfrance.repind.service.zone.internal.ZoneDS;
import com.airfrance.repind.util.AgencyConstantValues;
import com.airfrance.repind.util.SicDateUtils;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgenceDS {

	/** logger */
	private static final Log log = LogFactory.getLog(AgenceDS.class);

	/** the Entity Manager */
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	/** unit service : EmailUS **/
	@Autowired
	private EmailUS emailUS;
	/** unit service : GestionPMUS **/
	@Autowired
	private GestionPMUS gestionPMUS;
	/** unit service : NumeroIdentUS **/
	@Autowired
	private NumeroIdentUS numeroIdentUS;
	/** unit service : PmZoneUS **/
	@Autowired
	private PmZoneUS pmZoneUS;
	/** unit service : SynonymeUS **/
	@Autowired
	private SynonymeUS synonymeUS;
	/** unit service : TelecomUS **/
	@Autowired
	private TelecomUS telecomUS;
	/** unit service : ZoneCommUS **/
	@Autowired
	private ZoneCommUS zoneCommUS;
	/** unit service : SegmentationUS **/
	@Autowired
	private SegmentationUS segmentationUs;

	@Autowired
	RefCityDS refCityDS;

	/** main dao */
	@Autowired
	private AgenceRepository agenceRepository;

	@Autowired
	private PostalAddressRepository postalAddressRepository;

	@Autowired
	private TelecomsRepository telecomsRepository;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private PmZoneRepository pmZoneRepository;

	@Autowired
	private BspDataRepository bspDataRepository;
	
	@Autowired
	private OfficeIDRepository officeIDRepository;
	
	@Autowired
    private NumeroIdentRepository numeroIdentRepository;
	
	@Autowired
    private SynonymeRepository synonymeRepository;
	
	@Autowired
	private MembreReseauRepository membreReseauRepository;
	
	@Autowired
    private GestionPMRepository gestionPMRepository;
	
	@Autowired
    private MembreRepository membreRepository;

	/* PROTECTED REGION ID(_vpGyUGk4EeGhB9497mGnHw u var) ENABLED START */

	@Autowired
	@Qualifier("numeroIdentDS")
	private NumeroIdentDS numberIdDS;

	@Autowired
	@Qualifier("officeIdDS")
	private OfficeIdDS officeIdDS;

	@Autowired
	@Qualifier("zoneDS")
	private ZoneDS zoneDS;

	/** domain service : TelecomDS **/
	@Autowired
	@Qualifier("telecomDS")
	private TelecomDS telecomDS;

	/** domain service : TelecomDS **/
	@Autowired
	@Qualifier("membreReseauDS")
	private MembreReseauDS membreReseauDS;

	@Autowired
	@Qualifier("etablissementDS")
	private EtablissementDS etablissementDS;

	@Autowired
	private EmailDS emailDs;

	/** unit service : membreReseauUS **/
	@Autowired
	private MembreReseauUS membreReseauUS;

	@Autowired
	private LienIntCpZdUs lienIntCpZdUs;

	@Autowired
	private CatchmentAreaDS catchmentAreaDS;

	@Autowired
	private Profil_mereRepository profilMereRepository;

	@Autowired
	private ProfilDemarchageRepository profilDemarchageRepository;

	@Autowired
	private SegmentationRepository segmentationRepository;
	
	@Autowired
	private PersonneMoraleRepository personneMoraleRepository;

	@Autowired
	private ProfilFirmeUS profilFirmeUs;
    
    @Autowired
    private RefCountryDS refCountryDs;

    @Autowired
	private PostalAddressDS postalAddressDS;
   
    @Transactional(readOnly=true)
	public Integer countWhere(AgenceDTO dto) throws JrafDomainException {
		Agence agence = AgenceTransform.dto2BoLight(dto);
		return (int) agenceRepository.count(Example.of(agence));
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public AgenceDTO create(AgenceDTO agenceDTO) throws JrafDomainException {

		/* PROTECTED REGION ID(_vpGyUGk4EeGhB9497mGnHw DS-CM create) ENABLED START */
		Agence agence = new Agence();

		// transformation light dto -> bo
		AgenceTransform.dto2Bo(agenceDTO, agence);

		// creation en base
		// Appel create de l'Abstract
		agenceRepository.saveAndFlush(agence);

		// Version update and Id update if needed
		AgenceTransform.bo2Dto(agence, agenceDTO);
		/* PROTECTED REGION END */

		return agenceDTO;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(AgenceDTO dto) throws JrafDomainException {
		Agence agence = AgenceTransform.dto2BoLight(dto);
		agenceRepository.delete(agence);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(String oid) throws JrafDomainException {
		agenceRepository.deleteById(oid);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public AgenceDTO update(AgenceDTO agenceDTO) throws JrafDomainException {
		Optional<Agence> result = agenceRepository.findById(agenceDTO.getGin());
		if (result.isPresent()) {
			Agence agence = result.get();
			// transformation dto -> bo ( with links)
			AgenceTransform.dto2Bo(agenceDTO, agence);
			agenceRepository.saveAndFlush(agence);
			processUpdateForAssociatedData(agenceDTO, agence);

			AgenceTransform.bo2Dto(agence, agenceDTO);
			return agenceDTO;
		}
		return null;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void processUpdateForAssociatedData(AgenceDTO agenceDTO, Agence agenceFromDB) throws JrafDomainException {

		if (agenceFromDB != null && agenceDTO != null) {

			// OFFICE_ID
			if (!CollectionUtils.isEmpty(agenceDTO.getOffices())) {
				for (OfficeIDDTO officeId : agenceDTO.getOffices()) {
					if (officeId.getCle() == null)
						officeIdDS.create(officeId, agenceFromDB);
					else
						officeIdDS.update(officeId, agenceFromDB);
				}
			}

			// NUMERO IDENT
			if (!CollectionUtils.isEmpty(agenceDTO.getNumerosIdent())) {
				List<NumeroIdent> nums = new ArrayList<>();
				for (NumeroIdentDTO num : agenceDTO.getNumerosIdent()) {
					nums.add(NumeroIdentTransform.dto2BoLight(num));
				}
				numeroIdentUS.createOrUpdateOrDelete(nums, agenceFromDB);
			}

			// SEGMENTATION
			if (!CollectionUtils.isEmpty(agenceDTO.getSegmentations())) {
				List<Segmentation> segmentations = new ArrayList<>();
				for (SegmentationDTO segmentation : agenceDTO.getSegmentations()) {
					segmentations.add(SegmentationTransform.dto2BoLight(segmentation));
				}
				segmentationUs.createUpdateOrDeleteForAgency(segmentations, agenceFromDB);
			}

			// TELECOM
			if (!CollectionUtils.isEmpty(agenceDTO.getTelecoms())) {
				for (TelecomsDTO telecomsDTO : agenceDTO.getTelecoms()) {
					telecomDS.createUpdateAgencyTelecom(telecomsDTO, agenceFromDB);
				}
			}

			// EMAIL
			if (!CollectionUtils.isEmpty(agenceDTO.getEmails())) {
				for (EmailDTO email : agenceDTO.getEmails()) {
					emailDs.createOrUpdateAgencyEmail(email, agenceFromDB);
				}
			}

			// POSTAL ADDRESS
			if (!CollectionUtils.isEmpty(agenceDTO.getPostalAddresses())) {
				for (PostalAddressDTO adr : agenceDTO.getPostalAddresses()) {
					postalAddressDS.createUpdateAgencyPostalAddress(adr, agenceFromDB);
				}
			}

			// PM ZONE
			if (!CollectionUtils.isEmpty(agenceDTO.getPmZones())) {
				List<PmZone> zcs = preparePmZone(agenceDTO.getPmZones(), ZoneCommDTO.class);
				List<PmZone> zvs = preparePmZone(agenceDTO.getPmZones(), ZoneVenteDTO.class);

				/* Needed to keep only existing pmZones in database for this agency.
				 * Without that, we have a NullPointerException when checking the pmZone
				 * because the zoneDecoup of the new link doesn't have a gin.
				 */
				agenceRepository.refresh(agenceFromDB);

				if (!CollectionUtils.isEmpty(zcs))
					pmZoneUS.createOrUpdateOrDeleteZcLinks(zcs, agenceFromDB);
				if (!CollectionUtils.isEmpty(zvs))
					pmZoneUS.createOrUpdateOrDeleteZvLinks(zvs, agenceFromDB);
			}

			if (!CollectionUtils.isEmpty(agenceDTO.getPersonnesMoralesGerantes())) {
				List<GestionPM> linkedCompanies = new ArrayList<>();
				for (GestionPMDTO company : agenceDTO.getPersonnesMoralesGerees()) {
					company.setPersonneMoraleGeree(etablissementDS.get((EtablissementDTO) company.getPersonneMoraleGeree()));
					linkedCompanies.add(GestionPMTransform.dto2Bo(company));
				}
				gestionPMUS.createOrUpdateOrDeleteForAgency(linkedCompanies, agenceFromDB);
			}

			// SYNONYME
			Set<SynonymeDTO> synonymes = agenceDTO.getSynonymes();
			if (!CollectionUtils.isEmpty(synonymes)) {
				for (SynonymeDTO synonymeDto : synonymes) {
					synonymeDto.setPersonneMorale(AgenceTransform.bo2DtoLight(agenceFromDB));
					synonymeUS.createOrUpdate(synonymeDto);
				}
			}

			// Profile firm
			ProfilFirmeDTO profilFirmeDto = agenceDTO.getProfilFirme();
			if (profilFirmeDto != null) {
				profilFirmeDto.setGin(agenceDTO.getGin());

				if (agenceFromDB.getProfilFirme() != null) {
					profilFirmeUs.update(profilFirmeDto);
				} else {
					profilFirmeUs.create(profilFirmeDto);
				}
			}

			// Profile
			if (CollectionUtils.isEmpty(agenceFromDB.getProfils())) {
				Set<Profil_mereDTO> profils = agenceDTO.getProfils();
				if (!CollectionUtils.isEmpty(profils)) {
					for (Profil_mereDTO profilMereDto : profils) {
						profilMereDto.setSgin_pm(agenceFromDB.getGin());
						Profil_mere profilMere = new Profil_mere();
						Profil_mereTransform.dto2BoLight(profilMereDto, profilMere);
						Profil_mere savedProfilMere = profilMereRepository.saveAndFlush(profilMere);

						ProfilDemarchage profilDemarcharge = ProfilDemarchageTransform
								.dto2BoLight(profilMereDto.getProfilDemarcharge());
						profilDemarcharge.setProfil(savedProfilMere);
						profilDemarchageRepository.saveAndFlush(profilDemarcharge);
					}
				}
			}
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public AgenceDTO removeOfficeId(String identifiant, AgenceDTO agenceDTO) throws JrafDomainException {
		if(agenceDTO!=null && !CollectionUtils.isEmpty(agenceDTO.getOffices())) {
			Set<OfficeIDDTO> offices = new HashSet<OfficeIDDTO>(agenceDTO.getOffices());
			for(OfficeIDDTO office : agenceDTO.getOffices()) {
				if(office.getCle().toString().equals(identifiant)) {
					officeIdDS.remove(office);
					offices.remove(office);
				}
			}
			agenceDTO.setOffices(offices);
		}
		return agenceDTO;
	}

	@Transactional(readOnly=true)
	public List<AgenceDTO> findAll() throws JrafDomainException {
		List<AgenceDTO> results = new ArrayList<>();
		for (Agence found : agenceRepository.findAll()) {
			results.add(AgenceTransform.bo2DtoLight(found));
		}
		return results;
	}

	@Transactional(readOnly = true)
	public Integer count() throws JrafDomainException {
		return (int) agenceRepository.count();
	}

	@Transactional(readOnly = true)
	public List<AgenceDTO> findByExample(AgenceDTO dto) throws JrafDomainException {
		Agence agence = AgenceTransform.dto2BoLight(dto);
		List<AgenceDTO> result = new ArrayList<>();
		for (Agence found : agenceRepository.findAll(Example.of(agence))) {
			result.add(AgenceTransform.bo2DtoLight(found));
		}
		return result;
	}

	@Transactional(readOnly = true)
	public List<AgenceDTO> findDaughtersByMomIATANumber(String momIATANumber) throws JrafDomainException {
		List<AgenceDTO> result = new ArrayList<>();
		for (Agence found : agenceRepository.findAgencyDaughtersByMomIataNumber(momIATANumber)) {
			AgenceDTO agenceDTOReply = new AgenceDTO();
			AgenceTransform.bo2Dto(found, agenceDTOReply);
			result.add(agenceDTOReply);
		}
		return result;
	}

	@Transactional(readOnly = true)
	public AgenceDTO get(AgenceDTO dto) throws JrafDomainException {
		Optional<Agence> agence = agenceRepository.findById(dto.getGin());
		if (!agence.isPresent()) {
			return null;
		}
		return AgenceTransform.bo2DtoLight(agence.get());
	}

	@Transactional(readOnly = true)
	public AgenceDTO get(String oid) throws JrafDomainException {
		Optional<Agence> agence = agenceRepository.findById(oid);
		if (!agence.isPresent()) {
			return null;
		}
		return AgenceTransform.bo2DtoLight(agence.get());
	}

	/**
	 * Gets the waiting list.
	 *
	 * @return the waiting list
	 * @throws JrafDomainException the jraf domain exception
	 */
	@Transactional(readOnly = true)
	public List<AgenceDTO> getWaitingList() throws JrafDomainException {
		List<AgenceDTO> result = new ArrayList<>();
		for (Agence found : agenceRepository.findWaitingList()) {
			AgenceDTO agenceDTOReply = new AgenceDTO();
			AgenceTransform.bo2Dto(found, agenceDTOReply);
			result.add(agenceDTOReply);
		}
		return result;
	}

	public AgenceRepository getAgenceRepository() {
		return agenceRepository;
	}

	public void setAgenceRepository(AgenceRepository agenceRepository) {
		this.agenceRepository = agenceRepository;
	}

	/**
	 * Getter
	 *
	 * @return IEmailUS
	 */
	public EmailUS getEmailUS() {
		return emailUS;
	}

	/**
	 * Setter
	 *
	 * @param emailUS the IEmailUS
	 */
	public void setEmailUS(EmailUS emailUS) {
		this.emailUS = emailUS;
	}

	/**
	 * Getter
	 *
	 * @return IGestionPMUS
	 */
	public GestionPMUS getGestionPMUS() {
		return gestionPMUS;
	}

	/**
	 * Setter
	 *
	 * @param gestionPMUS the IGestionPMUS
	 */
	public void setGestionPMUS(GestionPMUS gestionPMUS) {
		this.gestionPMUS = gestionPMUS;
	}

	/**
	 * Getter
	 *
	 * @return INumeroIdentUS
	 */
	public NumeroIdentUS getNumeroIdentUS() {
		return numeroIdentUS;
	}

	/**
	 * Setter
	 *
	 * @param numeroIdentUS the INumeroIdentUS
	 */
	public void setNumeroIdentUS(NumeroIdentUS numeroIdentUS) {
		this.numeroIdentUS = numeroIdentUS;
	}

	/**
	 * Getter
	 *
	 * @return IPmZoneUS
	 */
	public PmZoneUS getPmZoneUS() {
		return pmZoneUS;
	}

	/**
	 * Setter
	 *
	 * @param pmZoneUS the IPmZoneUS
	 */
	public void setPmZoneUS(PmZoneUS pmZoneUS) {
		this.pmZoneUS = pmZoneUS;
	}

	/**
	 * Getter
	 *
	 * @return ISynonymeUS
	 */
	public SynonymeUS getSynonymeUS() {
		return synonymeUS;
	}

	/**
	 * Setter
	 *
	 * @param synonymeUS the ISynonymeUS
	 */
	public void setSynonymeUS(SynonymeUS synonymeUS) {
		this.synonymeUS = synonymeUS;
	}

	/**
	 * Getter
	 *
	 * @return ITelecomUS
	 */
	public TelecomUS getTelecomUS() {
		return telecomUS;
	}

	/**
	 * Setter
	 *
	 * @param telecomUS the ITelecomUS
	 */
	public void setTelecomUS(TelecomUS telecomUS) {
		this.telecomUS = telecomUS;
	}

	/**
	 * Getter
	 *
	 * @return IZoneCommUS
	 */
	public ZoneCommUS getZoneCommUS() {
		return zoneCommUS;
	}

	/**
	 * Setter
	 *
	 * @param zoneCommUS the IZoneCommUS
	 */
	public void setZoneCommUS(ZoneCommUS zoneCommUS) {
		this.zoneCommUS = zoneCommUS;
	}

	/**
	 * Getter
	 *
	 * @return ITelecomDS
	 */
	public TelecomDS getTelecomDS() {
		return telecomDS;
	}

	/**
	 * Setter
	 *
	 * @param telecomDS the ITelecomDS
	 */
	public void setTelecomDS(TelecomDS telecomDS) {
		this.telecomDS = telecomDS;
	}

	public EmailDS getEmailDs() {
		return emailDs;
	}

	public void setEmailDs(EmailDS emailDs) {
		this.emailDs = emailDs;
	}

	public MembreReseauDS getMembreReseauDS() {
		return membreReseauDS;
	}

	public void setMembreReseauDS(MembreReseauDS membreReseauDS) {
		this.membreReseauDS = membreReseauDS;
	}

	/**
	 * @return EntityManager
	 */
	public EntityManager getEntityManager() {
		/* PROTECTED REGION ID(_vpGyUGk4EeGhB9497mGnHwgem ) ENABLED START */
		return entityManager;
		/* PROTECTED REGION END */
	}

	/**
	 * @param entityManager EntityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * createNewAgency
	 *
	 * @param newAgency in AgenceDTO
	 * @param commit    in boolean
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createNewAgency(AgenceDTO newAgency, boolean commit) throws JrafDomainException {
		/* PROTECTED REGION ID(_uE9PYReJEeKJFbgRY_ODIg) ENABLED START */

		if (commit) {

			Agence agence = AgenceTransform.dto2BoLight(newAgency);

			agence.setOffices(OfficeIDTransform.dto2Bo(newAgency.getOffices()));
			(new ArrayList<OfficeID>(agence.getOffices())).get(0).setAgence(agence);

			// IM02213359
			agence.setPmZones(PmZoneTransform.dto2Bo(newAgency.getPmZones()));
			agence.setPostalAddresses(PostalAddressTransform.dto2Bo(newAgency.getPostalAddresses()));
			(new ArrayList<PostalAddress>(agence.getPostalAddresses())).get(0)
					.setPersonneMorale((PersonneMorale) agence);

			Set<PmZone> pmZ = new LinkedHashSet<>();
			Set<PmZone> pmZones = agence.getPmZones();
			pmZ.addAll(pmZones);
			(new ArrayList<PmZone>(pmZones)).get(0).setPersonneMorale((PersonneMorale) agence);

			// Remplacement List->Set
			for (PmZone pmZone : pmZones) {
				pmZone.getZoneDecoup().setPmZones(pmZ);
				break;
			}

			agenceRepository.saveAndFlush(agence);
			// Try update postalAddr
			postalAddressRepository.saveAndFlush((new ArrayList<PostalAddress>(agence.getPostalAddresses())).get(0));
			pmZoneRepository.saveAndFlush(((new ArrayList<PmZone>(pmZones)).get(0)));

			// Reset oid lost in the conversion

			log.info("Agency created");
		}
		/* PROTECTED REGION END */
	}

	/**
	 * getAgencyById
	 *
	 * @param id in NumeroIdentDTO
	 * @return The getAgencyById as <code>List<AgenceDTO></code>
	 * @throws JrafDomainException en cas d'exception
	 */
    @Transactional(readOnly=true)
	public List<AgenceDTO> getAgencyById(NumeroIdentDTO id) throws JrafDomainException {
		/* PROTECTED REGION ID(_uE9PbxeJEeKJFbgRY_ODIg) ENABLED START */

		return AgenceTransform.bo2DtoLight(agenceRepository.findByNumero(id.getNumero()));

		/* PROTECTED REGION END */
	}

	/**
	 * getAgencyByName
	 *
	 * @param agencyName in String
	 * @return The getAgencyByName as <code>List<AgenceDTO></code>
	 * @throws JrafDomainException en cas d'exception
	 */

	@Transactional(readOnly = true)
	public List<AgenceDTO> getLightAgencyByName(String agencyName) throws JrafDomainException {
		/* PROTECTED REGION ID(_uE9PZReJEeKJFbgRY_ODIg) ENABLED START */
		LocalDateTime startDate = LocalDateTime.now();
		List<AgenceDTO> dtos = new ArrayList<>();

		List<AgenceBean> agence = agenceRepository.findLightAgenceByName(agencyName);

		log.info("****** " + agence.size() + " agencies found for " + agencyName + " in "
				+ ChronoUnit.MILLIS.between(startDate, LocalDateTime.now()) + " ms");

		for (AgenceBean found : agence) {
			dtos.add(AgenceTransform.beanToDTO(found));
		}

		return dtos;

		/* PROTECTED REGION END */
	}

	/**
	 * getAgencyByOfficeId
	 *
	 * @param oid in OfficeIDDTO
	 * @return The getAgencyByOfficeId as <code>List<AgenceDTO></code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly = true)
	public List<AgenceDTO> getAgencyByOfficeId(OfficeIDDTO oid) throws JrafDomainException {
		/* PROTECTED REGION ID(_uE9PbBeJEeKJFbgRY_ODIg) ENABLED START */

		AgenceDTO agencyDto = new AgenceDTO();
		Set<OfficeIDDTO> dtos = new HashSet<>();
		dtos.add(oid);
		agencyDto.setOffices(dtos);

		return findByExample(agencyDto);

		/* PROTECTED REGION END */
	}

	/**
	 * getAgencyDaughters
	 *
	 * @param momIATANumber
	 * @return The getAgencyDaughters as <code>List<AgenceDTO></code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly = true)
	public List<AgenceDTO> getAgencyDaughters(String momIATANumber) throws JrafDomainException {
		/* PROTECTED REGION ID(_uE9PbBeJEeKJFbgRY_ODIg) ENABLED START */

		return findDaughtersByMomIATANumber(momIATANumber);

		/* PROTECTED REGION END */
	}

	/**
	 * searchAgence
	 *
	 * @param agenceRequest in AgenceDTO
	 * @return The searchAgence as <code>AgenceDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly = true)
	public AgenceDTO searchAgence(AgenceDTO agenceRequest) throws JrafDomainException {
		Optional<Agence> agence = agenceRepository.findById(agenceRequest.getGin());
		AgenceDTO agenceDTOReply = new AgenceDTO();

		if (!agence.isPresent()) {
			return null;
		}

		AgenceTransform.bo2Dto(agence.get(), agenceDTOReply);

		return agenceDTOReply;
	}

	/**
	 * searchAgence
	 *
	 * @param agenceRequest in AgenceDTO
	 * @return The searchAgence as <code>AgenceDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly = true)
	public AgenceDTO searchAgenceForProvidePublicAgencyData(AgenceDTO agenceRequest) throws JrafDomainException {
		Optional<Agence> agence = agenceRepository.findById(agenceRequest.getGin());
		AgenceDTO agenceDTOReply = new AgenceDTO();

		if (!agence.isPresent()) {
			return null;
		}

		AgenceTransform.bo2DtoForProvidePublicAgencyData(agence.get(), agenceDTOReply);

		return agenceDTOReply;
	}

	/**
	 * updateOfficeId
	 *
	 * @param agency in AgenceDTO
	 * @param commit in boolean
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateOfficeId(AgenceDTO agency, boolean commit) throws JrafDomainException {
		/* PROTECTED REGION ID(_uE9PaBeJEeKJFbgRY_ODIg) ENABLED START */

		if (commit) {

			String signOid = null;
			Agence currentAgence = null;

			for (OfficeIDDTO oid : agency.getOffices()) {

				if (oid.getCle() == null) {
					// find agence by sgin if never searched
					if (currentAgence == null) {
						currentAgence = findAgenceBySgin(agency.getGin());
					}

					// find the offices or set a new hashset
					if (currentAgence.getOffices() == null) {
						currentAgence.setOffices(new HashSet<>());
					}
					OfficeID officeIdToAdd = OfficeIDTransform.dto2Bo(oid);
					officeIdToAdd.setAgence(currentAgence);
					currentAgence.getOffices().add(officeIdToAdd);
					agenceRepository.saveAndFlush(currentAgence);
					signOid = oid.getSignatureMaj();
				}

			}
			PersonneMorale pm = this.getEntityManager().getReference(PersonneMorale.class, agency.getGin());
			pm.setSignatureModification(signOid);
			pm.setDateModification(new Date());
			this.getEntityManager().persist(pm);
			this.getEntityManager().flush();
		}

		String oid = (new ArrayList<>(agency.getOffices())).get(0).getOfficeID();
		String gin = agency.getGin();

		log.info("Office id " + oid + " for agency : " + gin + " added");

		/* PROTECTED REGION END */
	}

	/* PROTECTED REGION ID(_vpGyUGk4EeGhB9497mGnHw u m) ENABLED START */
	public void disableCommit() {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(AgenceDTO agenceDTO, boolean commit) throws JrafDomainException {

		if (commit) {
			this.update(agenceDTO);
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createAgency(AgenceDTO agenceDTO, boolean commit, boolean flush) throws JrafDomainException {
		this.create(agenceDTO, commit);

		if (flush)
			entityManager.flush();
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateLight(AgenceDTO agenceDTO) throws JrafDomainException {

		Optional<Agence> result = agenceRepository.findById(agenceDTO.getGin());

		if (result.isPresent()) {
			Agence agence = result.get();

			// transformation dto -> bo ( with links)
			AgenceTransform.dto2BoLight(agenceDTO, agence);

			agenceRepository.saveAndFlush(agence);

			AgenceTransform.bo2Dto(agence, agenceDTO);
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(AgenceDTO agenceDTO, boolean commit) throws JrafDomainException {

		if (commit) {
			this.create(agenceDTO);
			String gin = agenceDTO.getGin();
			log.info("Agency " + gin + " succesfully created");

		}

	}

	public NumeroIdentDS getNumberIdDS() {
		return numberIdDS;
	}

	public void setNumberIdDS(NumeroIdentDS numberIdDS) {
		this.numberIdDS = numberIdDS;
	}

	public OfficeIdDS getOfficeIdDS() {
		return officeIdDS;
	}

	public void setOfficeIdDS(OfficeIdDS officeIdDS) {
		this.officeIdDS = officeIdDS;
	}

	public ZoneDS getZoneDS() {
		return zoneDS;
	}

	public void setZoneDS(ZoneDS zoneDS) {
		this.zoneDS = zoneDS;
	}

	/**
	 * Find agency by enum search with all collections
	 *
	 * @param optionType
	 * @param optionValue
	 * @param azFilter
	 * @param filterAddress
	 * @param filterTelecom
	 * @param filterEmail
	 * @param filterMarketChoice
	 * @param filterContract
	 * @param filterZones
	 * @param filterNetwork
	 * @param scope
	 * @return The agency as <code>AgenceDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public AgenceDTO findByEnumSearchWithAllCollections(String optionType, String optionValue, String azFilter,
														Date searchDate, Boolean filterAddress, Boolean filterTelecom, Boolean filterEmail,
														Boolean filterMarketChoice, Boolean filterContract, Boolean filterZones, Boolean filterNetwork,
														List<String> scope) throws JrafDomainException {

		log.info("START findByEnumSearchWithAllCollections in DS with " + optionType + " and " + optionValue + " at "
				+ System.currentTimeMillis());

		if ("".equals(optionType)) {
			optionType = "GIN";
		} else {
			optionType = optionType.toUpperCase();
		}
		if (azFilter != null) {
			azFilter = azFilter.toUpperCase();
		}
		// Par défaut on ne renvoie que les valeurs valides
		if (filterAddress == null) {
			filterAddress = true;
		}
		if (filterTelecom == null) {
			filterTelecom = true;
		}
		if (filterEmail == null) {
			filterEmail = true;
		}
		if (filterMarketChoice == null) {
			filterMarketChoice = true;
		}
		if (filterContract == null) {
			filterContract = true;
		}
		if (filterZones == null) {
			filterZones = true;
		}
		if (filterNetwork == null) {
			filterNetwork = true;
		}

		if (searchDate == null) {
			searchDate = new Date();
		}

		// Resultat
		AgenceDTO agenceDTO = null;
		Agence agence = null;
		try {
			agence = agenceRepository.findAgencyByOptions(optionType, optionValue, searchDate, scope);
		} catch (TooManyResultsDaoException e) {
			throw new TooManyResultsDomainException(e.getMessage());
		}

		// Test si l'agence est RA2 ou non
		if (agence != null) {
			if ("Y".equalsIgnoreCase(agence.getAgenceRA2())) {
				throw new RA2AgencyNotAllowedException("RA2 agency not allowed");
			}
			agenceDTO = AgenceTransform.bo2DtoWithoutUnitializedProxiesCollections(agence, scope);

			// Date actuelle, utilisée pour les filtres
			Date currentDate = new Date();

			// Filtre sur les requested airlines : si au moins une des zc4 de la firme est
			// de type AZ (se termine par AZ), on renvoie la firme sinon on la met à null
			if ("AZ".equals(azFilter)) {
				boolean azAgency = false;
				if (agenceDTO.getPmZones() != null) {
					Iterator<PmZoneDTO> iterator = agenceDTO.getPmZones().iterator();
					while (iterator.hasNext()) {
						PmZoneDTO pmZoneDTO = iterator.next();

						if (pmZoneDTO.getZoneDecoup() != null
								&& pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)) {
							ZoneCommDTO zc = (ZoneCommDTO) pmZoneDTO.getZoneDecoup();
							if (zc.getZc4() != null && "V".equals(zc.getStatut()) && zc.getZc4().endsWith("AZ")) {
								azAgency = true;
								break;
							}
						}
					}
				}
				// La firme de possède pas de ZC possédant un ZC 4 AZ, on ne renvoie rien
				if (!azAgency)
					return null;
			}

			// Filtre sur les adresses -> seulement les status 'S', 'I' ou 'V' sont gardés :
			if (filterAddress != null && filterAddress && agenceDTO.getPostalAddresses() != null) {

				Iterator<PostalAddressDTO> iterator = agenceDTO.getPostalAddresses().iterator();
				while (iterator.hasNext()) {
					PostalAddressDTO postalAddressDTO = iterator.next();
					if (!"S".equals(postalAddressDTO.getSstatut_medium())
							&& !"I".equals(postalAddressDTO.getSstatut_medium())
							&& !"V".equals(postalAddressDTO.getSstatut_medium())) {
						iterator.remove();

					}
				}
			}

			// Filtre sur les emails -> seulement les status 'S', 'I' ou 'V' sont gardés :
			if (filterEmail != null && filterEmail && agenceDTO.getEmails() != null) {

				Iterator<EmailDTO> iterator = agenceDTO.getEmails().iterator();
				while (iterator.hasNext()) {
					EmailDTO emailDTO = iterator.next();
					if (!"S".equals(emailDTO.getStatutMedium()) && !"I".equals(emailDTO.getStatutMedium())
							&& !"V".equals(emailDTO.getStatutMedium())) {
						iterator.remove();

					}
				}
			}

			// Filtre sur les telecoms -> seulement les status 'S', 'I' ou 'V' sont gardés :
			if (filterTelecom != null && filterTelecom && agenceDTO.getTelecoms() != null) {

				Iterator<TelecomsDTO> iterator = agenceDTO.getTelecoms().iterator();
				while (iterator.hasNext()) {
					TelecomsDTO telecomDTO = iterator.next();
					if (!"S".equals(telecomDTO.getSstatut_medium()) && !"I".equals(telecomDTO.getSstatut_medium())
							&& !"V".equals(telecomDTO.getSstatut_medium())) {
						iterator.remove();

					}
				}
			}

			// Filtre sur les market choices (segmentations) -> seulement ceux avec une date
			// de fin supérieure à la date actuelle sont gardés :
			if (filterMarketChoice != null && filterMarketChoice && agenceDTO.getSegmentations() != null) {

				Iterator<SegmentationDTO> iterator = agenceDTO.getSegmentations().iterator();
				while (iterator.hasNext()) {
					SegmentationDTO segmentationDTO = iterator.next();
					if (segmentationDTO.getDateSortie() != null
							&& segmentationDTO.getDateSortie().compareTo(currentDate) < 0) {
						iterator.remove();

					}
				}
			}

			// Filtre sur les contrats -> seulement ceux avec une date de fin supérieure à
			// la date actuelle sont gardés :
			if (filterContract != null && filterContract && agenceDTO.getBusinessRoles() != null) {

				Iterator<BusinessRoleDTO> iterator = agenceDTO.getBusinessRoles().iterator();
				while (iterator.hasNext()) {
					BusinessRoleDTO businessRoleDTO = iterator.next();
					if (businessRoleDTO.getRoleAgence() != null
							&& businessRoleDTO.getRoleAgence().getFinValidite() != null
							&& businessRoleDTO.getRoleAgence().getFinValidite().compareTo(currentDate) < 0) {
						iterator.remove();

					}
				}
			}

			// Filtre sur les zones, selon la valeur du scope
			if (agenceDTO.getPmZones() != null) {
				boolean zoneCommRequested = (scope.contains("COMMERCIAL_ZONES") || scope.contains("ALL")) ? true
						: false;
				boolean zoneFinRequested = (scope.contains("FINANCIAL_ZONES") || scope.contains("ALL")) ? true : false;
				boolean zoneVenteRequested = (scope.contains("SALES_ZONES") || scope.contains("ALL")) ? true : false;
				Iterator<PmZoneDTO> iterator = agenceDTO.getPmZones().iterator();
				while (iterator.hasNext()) {
					PmZoneDTO pmZoneDTO = iterator.next();
					if (!zoneCommRequested && pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)) {
						iterator.remove();
						continue;
					}
					if (!zoneFinRequested && pmZoneDTO.getZoneDecoup().getClass().equals(ZoneFinanciereDTO.class)) {
						iterator.remove();
						continue;
					}
					if (!zoneVenteRequested && pmZoneDTO.getZoneDecoup().getClass().equals(ZoneVenteDTO.class)) {
						iterator.remove();
						continue;
					}
				}

			}

			// Filtre sur les zones -> seulement celles avec une date de fin supérieure à la
			// date actuelle sont gardés :
			if (filterZones != null && filterZones && agenceDTO.getPmZones() != null) {

				Iterator<PmZoneDTO> iterator = agenceDTO.getPmZones().iterator();
				while (iterator.hasNext()) {
					PmZoneDTO pmZoneDTO = iterator.next();
					if (pmZoneDTO.getDateFermeture() != null
							&& pmZoneDTO.getDateFermeture().compareTo(currentDate) < 0) {
						iterator.remove();
					}

				}
			}

			// Filtre sur les zones -> seulement celles avec une date de fin supérieure à la
			// date actuelle sont gardés :
			if (filterNetwork != null && filterNetwork && agenceDTO.getReseaux() != null) {

				Iterator<MembreReseauDTO> iterator = agenceDTO.getReseaux().iterator();
				while (iterator.hasNext()) {
					MembreReseauDTO membreReseauDTO = iterator.next();
					if (membreReseauDTO.getDateFin() != null
							&& membreReseauDTO.getDateFin().compareTo(currentDate) < 0) {
						iterator.remove();
					}
				}
			}
		}

		log.info("END findByEnumSearchWithAllCollections at " + System.currentTimeMillis());

		return agenceDTO;
	}

	/**
	 * Find agency by enum search with all collections, not excluding RA2 agencies
	 *
	 * @param optionType
	 * @param optionValue
	 * @param azFilter
	 * @param filterAddress
	 * @param filterTelecom
	 * @param filterEmail
	 * @param filterMarketChoice
	 * @param filterContract
	 * @param filterZones
	 * @param filterNetwork
	 * @param scope
	 * @return The agency as <code>AgenceDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public AgenceDTO findByEnumSearchWithAllCollectionsWithRA2(String optionType, String optionValue, String azFilter,
															   Date searchDate, Boolean filterAddress, Boolean filterTelecom, Boolean filterEmail,
															   Boolean filterMarketChoice, Boolean filterContract, Boolean filterZones, Boolean filterNetwork,
															   List<String> scope) throws JrafDomainException {

		log.info("START findByEnumSearchWithAllCollections in DS with " + optionType + " and " + optionValue + " at "
				+ System.currentTimeMillis());

		if ("".equals(optionType)) {
			optionType = "GIN";
		} else {
			optionType = optionType.toUpperCase();
		}
		if (azFilter != null) {
			azFilter = azFilter.toUpperCase();
		}
		// Par défaut on ne renvoie que les valeurs valides
		if (filterAddress == null) {
			filterAddress = true;
		}
		if (filterTelecom == null) {
			filterTelecom = true;
		}
		if (filterEmail == null) {
			filterEmail = true;
		}
		if (filterMarketChoice == null) {
			filterMarketChoice = true;
		}
		if (filterContract == null) {
			filterContract = true;
		}
		if (filterZones == null) {
			filterZones = true;
		}
		if (filterNetwork == null) {
			filterNetwork = true;
		}

		if (searchDate == null) {
			searchDate = new Date();
		}

		// Resultat
		AgenceDTO agenceDTO = null;
		Agence agence = null;
		try {
			agence = agenceRepository.findAgencyByOptions(optionType, optionValue, searchDate, scope);
		} catch (TooManyResultsDaoException e) {
			throw new TooManyResultsDomainException(e.getMessage());
		}

		if (agence != null) {
			agenceDTO = AgenceTransform.bo2DtoWithoutUnitializedProxiesCollections(agence, scope);

			// RAP-271 : Add SIRET of parent to output keyNumbers
			if (agence.getParent() != null) {
				PersonneMoraleDTO personneMoraleDTO = PersonneMoraleTransform.bo2Dto(agence.getParent());
				if (personneMoraleDTO != null && personneMoraleDTO.getClass().equals(EtablissementDTO.class)) {
					agenceDTO.setParent(personneMoraleDTO);
				}
			}

			// Date actuelle, utilisée pour les filtres
			Date currentDate = new Date();

			// Filtre sur les requested airlines : si au moins une des zc4 de la firme est
			// de type AZ (se termine par AZ), on renvoie la firme sinon on la met à null
			if ("AZ".equals(azFilter)) {
				boolean azAgency = false;
				if (agenceDTO.getPmZones() != null) {
					Iterator<PmZoneDTO> iterator = agenceDTO.getPmZones().iterator();
					while (iterator.hasNext()) {
						PmZoneDTO pmZoneDTO = iterator.next();

						if (pmZoneDTO.getZoneDecoup() != null
								&& pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)) {
							ZoneCommDTO zc = (ZoneCommDTO) pmZoneDTO.getZoneDecoup();
							if (zc.getZc4() != null && "V".equals(zc.getStatut()) && zc.getZc4().endsWith("AZ")) {
								azAgency = true;
								break;
							}
						}
					}
				}
				// La firme de possède pas de ZC possédant un ZC 4 AZ, on ne renvoie rien
				if (!azAgency)
					return null;
			}

			// Filtre sur les adresses -> seulement les status 'S', 'I' ou 'V' sont gardés :
			if (filterAddress != null && filterAddress && agenceDTO.getPostalAddresses() != null) {

				Iterator<PostalAddressDTO> iterator = agenceDTO.getPostalAddresses().iterator();
				while (iterator.hasNext()) {
					PostalAddressDTO postalAddressDTO = iterator.next();
					if (!"S".equals(postalAddressDTO.getSstatut_medium())
							&& !"I".equals(postalAddressDTO.getSstatut_medium())
							&& !"V".equals(postalAddressDTO.getSstatut_medium())) {
						iterator.remove();
					}
				}
			}

			// Filtre sur les emails -> seulement les status 'S', 'I' ou 'V' sont gardés :
			if (filterEmail != null && filterEmail && agenceDTO.getEmails() != null) {

				Iterator<EmailDTO> iterator = agenceDTO.getEmails().iterator();
				while (iterator.hasNext()) {
					EmailDTO emailDTO = iterator.next();
					if (!"S".equals(emailDTO.getStatutMedium()) && !"I".equals(emailDTO.getStatutMedium())
							&& !"V".equals(emailDTO.getStatutMedium())) {
						iterator.remove();
					}
				}
			}

			// Filtre sur les telecoms -> seulement les status 'S', 'I' ou 'V' sont gardés :
			if (filterTelecom != null && filterTelecom && agenceDTO.getTelecoms() != null) {

				Iterator<TelecomsDTO> iterator = agenceDTO.getTelecoms().iterator();
				while (iterator.hasNext()) {
					TelecomsDTO telecomDTO = iterator.next();
					if (!"S".equals(telecomDTO.getSstatut_medium()) && !"I".equals(telecomDTO.getSstatut_medium())
							&& !"V".equals(telecomDTO.getSstatut_medium())) {
						iterator.remove();
					}
				}
			}

			// Filtre sur les market choices (segmentations) -> seulement ceux avec une date
			// de fin supérieure à la date actuelle sont gardés :
			if (filterMarketChoice != null && filterMarketChoice && agenceDTO.getSegmentations() != null) {

				Iterator<SegmentationDTO> iterator = agenceDTO.getSegmentations().iterator();
				while (iterator.hasNext()) {
					SegmentationDTO segmentationDTO = iterator.next();
					if (segmentationDTO.getDateSortie() != null
							&& segmentationDTO.getDateSortie().compareTo(currentDate) < 0) {
						iterator.remove();
					}
				}
			}

			// Filtre sur les contrats -> seulement ceux avec une date de fin supérieure à
			// la date actuelle sont gardés :
			if (filterContract != null && filterContract && agenceDTO.getBusinessRoles() != null) {

				Iterator<BusinessRoleDTO> iterator = agenceDTO.getBusinessRoles().iterator();
				while (iterator.hasNext()) {
					BusinessRoleDTO businessRoleDTO = iterator.next();
					if (businessRoleDTO.getRoleAgence() != null
							&& businessRoleDTO.getRoleAgence().getFinValidite() != null
							&& businessRoleDTO.getRoleAgence().getFinValidite().compareTo(currentDate) < 0) {
						iterator.remove();
					}
				}
			}

			// Filtre sur les zones, selon la valeur du scope
			if (agenceDTO.getPmZones() != null) {
				boolean zoneCommRequested = (scope.contains("COMMERCIAL_ZONES") || scope.contains("ALL")) ? true
						: false;
				boolean zoneFinRequested = (scope.contains("FINANCIAL_ZONES") || scope.contains("ALL")) ? true : false;
				boolean zoneVenteRequested = (scope.contains("SALES_ZONES") || scope.contains("ALL")) ? true : false;
				Iterator<PmZoneDTO> iterator = agenceDTO.getPmZones().iterator();
				while (iterator.hasNext()) {
					PmZoneDTO pmZoneDTO = iterator.next();
					if (!zoneCommRequested && pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)) {
						iterator.remove();
						continue;
					}
					if (!zoneFinRequested && pmZoneDTO.getZoneDecoup().getClass().equals(ZoneFinanciereDTO.class)) {
						iterator.remove();
						continue;
					}
					if (!zoneVenteRequested && pmZoneDTO.getZoneDecoup().getClass().equals(ZoneVenteDTO.class)) {
						iterator.remove();
						continue;
					}
				}

			}

			// Filtre sur les zones -> seulement celles avec une date de fin supérieure à la
			// date actuelle sont gardés :
			if (filterZones != null && filterZones && agenceDTO.getPmZones() != null) {
				Iterator<PmZoneDTO> iterator = agenceDTO.getPmZones().iterator();
				while (iterator.hasNext()) {
					PmZoneDTO pmZoneDTO = iterator.next();
					if ((pmZoneDTO.getDateFermeture() != null && pmZoneDTO.getDateFermeture().compareTo(currentDate) < 0
							&& !DateUtils.isSameDay(pmZoneDTO.getDateFermeture(), currentDate))
							|| pmZoneDTO.getDateOuverture().compareTo(currentDate) > 0) {
						iterator.remove();
					}
				}
			}

			// Filtre sur les zones -> seulement celles avec une date de fin supérieure à la
			// date actuelle sont gardés :
			if (filterNetwork != null && filterNetwork && agenceDTO.getReseaux() != null) {
				Iterator<MembreReseauDTO> iterator = agenceDTO.getReseaux().iterator();
				while (iterator.hasNext()) {
					MembreReseauDTO membreReseauDTO = iterator.next();
					if (membreReseauDTO.getDateFin() != null
							&& membreReseauDTO.getDateFin().compareTo(currentDate) < 0) {
						iterator.remove();
					}
				}
			}
		}

		log.info("END findByEnumSearchWithAllCollections at " + System.currentTimeMillis());

		return agenceDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.airfrance.repind.service.agence.IAgenceDS#updateLUA(com.airfrance.repind.dto.
	 * agence.AgenceDTO)
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateLUA(String pIdentifierType, String pIdentifierValue, AgenceDTO agenceDTO)
			throws JrafDomainException {

		log.info("BEGIN updateLUA");
		// SCOPE_TO_PROVIDE
		List<String> scopeToProvide = new ArrayList<String>();
		if (agenceDTO.getTelecoms() != null) {
			scopeToProvide.add("TELECOMS");
		}
		if (agenceDTO.getEmails() != null) {
			scopeToProvide.add("EMAILS");
		}
		if (agenceDTO.getSynonymes() != null) {
			scopeToProvide.add("SYNONYMS");
		}
		if (agenceDTO.getPmZones() != null) {
			scopeToProvide.add("COMMERCIAL_ZONES");
		}

		if (agenceDTO.getReseaux() != null) {
			scopeToProvide.add("NETWORKS");
		}

		// Recherche de l'Agence en fonction des options type, value et scopeToProvide
		Agence agence = agenceRepository.findAgencyByOptions(pIdentifierType, pIdentifierValue, new Date(),
				scopeToProvide);

		// On vérifie que l'id fourni retourne bien une agence
		if (agence == null) {

			throw new JrafDomainRollbackException("001"); // NOT FOUND
		}
		// On vérifie que la version en entrée coïncide avec la version stockée
		else if (!agence.getVersion().equals(agenceDTO.getVersion())) {

			throw new JrafDomainRollbackException("003"); // SIMULTANEOUS UPDATE
		}

		// Seule une agence active peut être modifiée
		LegalPersonStatusEnum status = LegalPersonStatusEnum.fromLiteral(agence.getStatut());
		if (!LegalPersonStatusEnum.ACTIVE.equals(status)) {
			throw new JrafDomainRollbackException("177"); // INVALID STATUS
		}

		// Une agence RA2 ne peut pas être modifiée
		if ("Y".equals(agence.getAgenceRA2())) {

			// Erreur 931 - RA2 AGENCY NOT ALLOWED
			throw new JrafDomainRollbackException("931");
		}

		// Synonymes
		if (agenceDTO.getSynonymes() != null) {

			List<Synonyme> synonymesToCreateOrUpdateOrDelete = new ArrayList<>();
			for (SynonymeDTO synonymeDTO : agenceDTO.getSynonymes()) {
				synonymesToCreateOrUpdateOrDelete.add(SynonymeTransform.dto2BoLight(synonymeDTO));
			}
			synonymeUS.createOrUpdateOrDelete(synonymesToCreateOrUpdateOrDelete, agence);
		}

		// Email
		if (agenceDTO.getEmails() != null) {

			List<Email> emailsToCreateOrUpdateOrDelete = new ArrayList<>();
			for (EmailDTO emailDTO : agenceDTO.getEmails()) {
				emailsToCreateOrUpdateOrDelete.add(EmailTransform.dto2BoLight(emailDTO));
			}
			emailUS.createOrUpdateOrDelete(emailsToCreateOrUpdateOrDelete, agence);
		}

		// Telecoms
		if (agenceDTO.getTelecoms() != null) {

			List<Telecoms> telecomsToCreateOrUpdateOrDelete = new ArrayList<>();
			for (TelecomsDTO telecomDTO : agenceDTO.getTelecoms()) {
				telecomsToCreateOrUpdateOrDelete.add(TelecomsTransform.dto2BoLight(telecomDTO));
			}
			telecomDS.createOrUpdateOrDelete(telecomsToCreateOrUpdateOrDelete, agence);
		}

		// PmZones
		List<PmZone> zcLinksToCreateOrUpdateOrDelete = new ArrayList<>();
		if (agenceDTO.getPmZones() != null) {

			for (PmZoneDTO zoneLinkDto : agenceDTO.getPmZones()) {

				if (ZoneCommDTO.class.equals(zoneLinkDto.getZoneDecoup().getClass())) {

					ZoneComm zc = ZoneCommTransform.dto2BoLight((ZoneCommDTO) zoneLinkDto.getZoneDecoup());
					PmZone zcLink = PmZoneTransform.dto2BoLight(zoneLinkDto);
					zcLink.setZoneDecoup(zc);
					zcLinksToCreateOrUpdateOrDelete.add(zcLink);
				}
			}
		}
		pmZoneUS.createOrUpdateOrDeleteZcLinks(zcLinksToCreateOrUpdateOrDelete, agence);

		// Network
		if (!UList.isNullOrEmpty(agenceDTO.getReseaux()) && !MembreReseauDTO.areEmpty(agenceDTO.getReseaux())) {
			membreReseauDS.checkNetworkLinkInput(agenceDTO, agence);
			List<MembreReseau> listMembreReseau = new ArrayList<>();
			listMembreReseau.addAll(MembreReseauTransform.dto2Bo(agenceDTO.getReseaux()));
			membreReseauUS.createOrUpdateOrDeleteNetworkLink(listMembreReseau, agence);

		}

		// date, site et signature de modification de l'agence
		agence.setDateModification(new Date());
		agence.setSignatureModification(agenceDTO.getSignatureModification());
		agence.setSiteModification(agenceDTO.getSiteModification());

		agenceRepository.saveAndFlush(agence);

		// Version update and Id update if needed
		// ----------------------------------------
		agenceRepository.refresh(agence);

		// Bo2Dto Agence
		AgenceTransform.bo2DtoLight(agence, agenceDTO);

		// Bo2Dto Synonymes
		agenceDTO.setSynonymes(new HashSet<SynonymeDTO>());
		if (!CollectionUtils.isEmpty(agence.getSynonymes())) {

			for (Synonyme synonyme : agence.getSynonymes()) {
				agenceDTO.getSynonymes().add(SynonymeTransform.bo2DtoLight(synonyme));
			}
		}

		// Bo2Dto Emails
		agenceDTO.setEmails(new HashSet<EmailDTO>());
		if (!CollectionUtils.isEmpty(agence.getEmails())) {

			for (Email email : agence.getEmails()) {
				agenceDTO.getEmails().add(EmailTransform.bo2DtoLight(email));
			}
		}

		// Bo2Dto Telecoms
		agenceDTO.setTelecoms(new HashSet<TelecomsDTO>());
		if (!CollectionUtils.isEmpty(agence.getTelecoms())) {

			for (Telecoms telecom : agence.getTelecoms()) {
				agenceDTO.getTelecoms().add(TelecomsTransform.bo2DtoLight(telecom));
			}
		}

		// Bo2Dto Liens ZC
		agenceDTO.setPmZones(new HashSet<PmZoneDTO>());
		List<PmZone> zcLinks = agence.fetchCommercialZoneLinks();
		if (!CollectionUtils.isEmpty(zcLinks)) {

			for (PmZone zcLink : zcLinks) {
				PmZoneDTO zcLinkDto = PmZoneTransform.bo2DtoLight(zcLink);
				zcLinkDto.setZoneDecoup(ZoneCommTransform.bo2DtoLight((ZoneComm) zcLink.getZoneDecoup()));
				agenceDTO.getPmZones().add(zcLinkDto);
			}
		}

		// Bo2 DTo networks
		agenceDTO.setReseaux(new HashSet<MembreReseauDTO>());
		Set<MembreReseau> membreReseaux = agence.getReseaux();
		if (!CollectionUtils.isEmpty(membreReseaux)) {
			agenceDTO.getReseaux().addAll(MembreReseauTransform.bo2Dto(membreReseaux));
		}

		// Fin
		log.info("END updateLUA");
	}

	public Integer getVersionByNumIdent(String pIdentifValue) throws JrafDomainException {
		return agenceRepository.getVersionByNumIdent(pIdentifValue);
	}

	public List<BspDataDTO> provideBspData(String gin) throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from SIC2.BSP_DATA WHERE SGIN=:gin ");

		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString(), BspData.class);
		myQuery.setParameter("gin", gin);

		List<BspData> result;
		List<BspDataDTO> listResultDTO = new ArrayList<>();

		try {
			result = (List<BspData>) myQuery.getResultList();
			for (BspData element : result) {
				BspDataDTO resultDTO = BspDataTransform.bo2Dto(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			listResultDTO = null;
		}

		return listResultDTO;
	}

	public List<String> findGinAgencyByIATA(String nbIata) {

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from SIC2.NUMERO_IDENT WHERE SNUMERO=:nbIata AND STYPE='IA' ");

		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString(), NumeroIdent.class);
		myQuery.setParameter("nbIata", nbIata);

		List<NumeroIdent> result;
		List<String> listGin = new ArrayList<>();

		try {
			result = (List<NumeroIdent>) myQuery.getResultList();

			for (NumeroIdent element : result) {
				listGin.add(element.getPersonneMorale().getGin());
			}

		} catch (NoResultException e) {
			return null;
		}

		return listGin;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public BspDataDTO createBspData(BspDataDTO bspDataDTO) throws JrafDomainException {
		// modifier le transform pour mettre toutes les données
		BspData bspData = null;

		bspData = BspDataTransform.dto2Bo(bspDataDTO);
		return BspDataTransform.bo2Dto(bspDataRepository.saveAndFlush(bspData));
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateBspData(BspDataDTO bspDataDTO) throws JrafDomainException {
		// modifier le transform pour mettre toutes les données
		BspData bspData = null;

		bspData = BspDataTransform.dto2Bo(bspDataDTO);

	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public BspDataDTO updateBspDataRa1(BspDataDTO bspDataDTO) throws JrafDomainException {
		// modifier le transform pour mettre toutes les données
		BspData bspData = null;

		bspData = BspDataTransform.dto2Bo(bspDataDTO);

		return BspDataTransform.bo2Dto(bspDataRepository.saveAndFlush(bspData));
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public boolean deleteBspDataById(@NotNull int id)  {
		try {
			boolean toBeDeleted = bspDataRepository.existsById((id));

			if (!toBeDeleted) {
				throw new NotFoundException(String.valueOf(false));
			}

			bspDataRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public int deleteBspData(String gin, String company) throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("delete from SIC2.BSP_DATA WHERE SGIN=:gin AND COMPANY=:company");

		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString(), BspData.class);
		myQuery.setParameter("gin", gin);
		myQuery.setParameter("company", company);

		int result;

		try {
			result = myQuery.executeUpdate();

		} catch (HibernateException e) {
			return -1;
		}
		return result;
	}

	public String findCatchmentAreaByPostalCode(String sPostalCode) throws JrafDaoException {

		return catchmentAreaDS.findCatchmentAreaByPostalCode(sPostalCode);
	}

	public List<AgenceDTO> findAllByAgenceRA2(String ra2, Pageable pageable) throws JrafDomainException {

		List<AgenceDTO> returnList = new ArrayList<>();

		if (StringUtils.isEmpty(ra2)) {
			return returnList;
		}

		List<Agence> result = agenceRepository.findAllByAgenceRA2(ra2, pageable);

		if (!UList.isNullOrEmpty(result)) {
			for (Agence agenceLoop : result) {
				AgenceDTO agenceDTOLoop = new AgenceDTO();
				AgenceTransform.bo2Dto(agenceLoop, agenceDTOLoop);
				returnList.add(agenceDTOLoop);
			}
		}

		return returnList;
	}

	public String findCatchmentAreaByDeptCode(String sDeptCode) throws JrafDaoException {
		return catchmentAreaDS.findCatchmentAreaByDeptCode(sDeptCode);
	}

	/**
	 * Retrieves the RA2 agency by the parameters specified
	 *
	 * @param name        name of the agency
	 * @param cityCode    city code of the agency
	 * @param countryCode country code of the agency
	 * @return the list of agencies found
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly = true)
	public List<AgenceDTO> findRa2AgencyByNameAddress(@NotNull final String name, @NotNull final String cityCode,
													  @NotNull final String countryCode) throws JrafDomainException {
		List<AgenceDTO> result = new ArrayList<>();
		for (Agence found : agenceRepository.findRa2AgencyByNameAddress(name, cityCode, countryCode)) {
			AgenceDTO agenceDto = AgenceTransform.bo2DtoLight(found);
			AgenceTransform.bo2DtoLinkMinimal(found, agenceDto);
			result.add(agenceDto);
		}
		return result;
	}

	/**
	 * Retrieves the agency for the specified GIN
	 *
	 * @param sgin GIN of the agency
	 * @return {@code AgenceDTO} for the specified GIN, else {@code null}
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly = true)
	public AgenceDTO findLightBySgin(@NotNull final String sgin) throws JrafDomainException {
		Optional<Agence> response = agenceRepository.findById(sgin);
		AgenceDTO agenceDto = null;
		if (response.isPresent()) {
			Agence found = response.get();
			agenceDto = AgenceTransform.bo2DtoLight(found);
			AgenceTransform.bo2DtoLinkMinimal(found, agenceDto);
		}
		return agenceDto;
	}

	/**
	 * <ul>
	 * Depending on the action, the following is executed :
	 * <li>CREATE : An Agency is created from scratch - {@code Agence},
	 * {@code OfficeID}, {@code PmZone}, {@code PostalAddress}, {@code Profil_mere},
	 * {@code ProfilFirme}, {@code Synonyme} and {@codeProfilDemarchage} are created
	 * </li>
	 * <li>UPDATE : A new association with {@code OfficeID} is done</li>
	 * <li>DELETE : Association with {@code OfficeID} is removed</li>
	 * </ul>
	 *
	 * @param agenceDTO
	 * @param action    the action to be executed
	 * @return
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String processForAlimentaionGds(AgenceDTO agenceDTO, String action) throws JrafDomainException {
		String gin;
		if (AgencyConstantValues.ACTION_CREATE.equals(action)) {
			// Agency
			Agence agence = AgenceTransform.dto2BoLight(agenceDTO);
			Agence newAgency = agenceRepository.saveAndFlush(agence);
			gin = newAgency.getGin();

			agenceDTO.setGin(gin);
			processAssociatedData(agenceDTO, newAgency);
		} else {
			gin = agenceDTO.getGin();
			Optional<Agence> result = agenceRepository.findById(gin);
			if (result.isPresent()) {
				Agence agency = result.get();
				AgenceTransform.dto2BoLight(agenceDTO, agency);
				agenceRepository.saveAndFlush(agency);
				updateOfficeId(agency, agenceDTO.getOffices(), action);
			}
		}

		return gin;
	}

	/**
	 * Retrieves the agency for the specified criteria.
	 *
	 * @param numeroId   the identification number
	 * @param types      type of identification number to be searched
	 * @param findActive retrieves only active if ({@code true}, else retrieves only
	 *                   inactive
	 * @return the agency for the specified criteria
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly = true)
	public List<AgenceDTO> findByIdType(@NotNull final String numeroId, @NotNull final List<String> types,
										boolean findActive) throws JrafDomainException {
		List<AgenceDTO> result = null;
		List<Agence> agencies = null;
		if (findActive) {
			agencies = agenceRepository.findActiveByIdType(numeroId, types);
		} else {
			agencies = agenceRepository.findInactiveByIdType(numeroId, types);
		}
		if (!CollectionUtils.isEmpty(agencies)) {
			result = new ArrayList<>();
			for (Agence found : agencies) {
				AgenceDTO agenceDto = AgenceTransform.bo2DtoLight(found);
				AgenceTransform.bo2DtoLinkMinimal(found, agenceDto);
				result.add(agenceDto);
			}
		}
		return result;
	}

	/**
	 * <ul>
	 * Depending on the action, the following is executed :
	 * <li>CREATE : An Agency is created from scratch - {@code Agence},
	 * {@code PmZone}, {@code PostalAddress}, {@code Profil_mere},
	 * {@code ProfilFirme}, {@code Synonyme} and
	 * {@codeProfilDemarchage} ,{@link TelecomsDTO}, {@link EmailDTO},
	 * {@link MembreReseauDTO}, {@link SegmentationDTO} are created</li>
	 * <li>UPDATE : Modify, Radiation, Restructure, Number change</li>
	 * </ul>
	 *
	 * @param agenceDTO
	 * @param action    the action to be executed
	 * @return
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String processForBandesArc(AgenceDTO agenceDTO, String action) throws JrafDomainException {
		String gin;
		if (AgencyConstantValues.ACTION_CREATE.equals(action)) {
			// Agency
			Agence agence = AgenceTransform.dto2BoLight(agenceDTO);
			Agence newAgency = agenceRepository.saveAndFlush(agence);
			gin = newAgency.getGin();
			agenceDTO.setGin(gin);
			processAssociatedData(agenceDTO, newAgency);
		} else { // MODIFICATION or RESTRUCTURE
			gin = agenceDTO.getGin();
			Optional<Agence> result = agenceRepository.findById(gin);
			if (result.isPresent()) {
				Agence agency = result.get();
				AgenceTransform.dto2BoLight(agenceDTO, agency);
				agenceRepository.saveAndFlush(agency);
				processAssociatedData(agenceDTO, agency);
			}
		}

		return gin;
	}

	@Transactional(rollbackFor = { JrafDomainRollbackException.class,
			JrafDaoException.class }, noRollbackFor = JrafDomainNoRollbackException.class)
	public AgenceDTO createRa1(@NotNull AgenceDTO agencyDto) throws JrafDomainException {
		if (RefTableREF_TYP_AGEN._REF_9.equals(agencyDto.getType())) {
			agencyDto.setAgenceRA2(YesNoFlagEnum.YES.toString());
			agencyDto.setActiviteLocal(AgencyValidationHelper.ACTIVITY_RA2);
		} else {
			agencyDto.setAgenceRA2(YesNoFlagEnum.NO.toString());
		}
		agencyDto.setVersion(1);
		if (StringUtils.isBlank(agencyDto.getSiteCreation())) {
			agencyDto.setSiteCreation(AgencyValidationHelper.SITE_QVI);
			agencyDto.setSiteModification(AgencyValidationHelper.SITE_QVI);
		}
		if (StringUtils.isBlank(agencyDto.getSignatureCreation())) {
			agencyDto.setSignatureCreation(AgencyValidationHelper.CREATION_SIGNATURE);
		}
		if (StringUtils.isBlank(agencyDto.getSignatureModification())) {
			agencyDto.setSignatureModification(AgencyValidationHelper.CREATION_SIGNATURE);
		}
		// Validation
		AgencyValidationHelper.validateInputData(agencyDto, agenceRepository, refCountryDs, numberIdDS);
		AgencyValidationHelper.validateCreationData(agencyDto, zoneCommUS, pmZoneUS.getZoneVenteUS());

		// Update signatures
		Date today = new Date();
		agencyDto.setDateCreation(today);
		agencyDto.setDateModification(today);

		// Creation
		Agence agence = AgenceTransform.dto2BoLight(agencyDto);
		Agence newAgency = agenceRepository.save(agence);
		agencyDto.setGin(newAgency.getGin());
		processAssociatedDataForCreateRa1(agencyDto, newAgency);
		agenceRepository.flush();
		return agencyDto;
	}

	@Transactional(rollbackFor = { JrafDomainRollbackException.class, JrafDaoException.class }, noRollbackFor = JrafDomainNoRollbackException.class)
	public AgenceDTO updateRa1(@NotNull AgenceDTO agencyDto) throws JrafDomainException {
		Optional<Agence> result = agenceRepository.findById(agencyDto.getGin());
		Date today = new Date();
		if (result.isPresent()) {
			Agence existingAgency = result.get();
			// Validate
			AgencyValidationHelper.validateUpdate(agencyDto, numberIdDS, existingAgency);

			existingAgency.setNom(agencyDto.getNom());
			existingAgency.setNumeroIATAMere(agencyDto.getNumeroIATAMere());
			existingAgency.setType(agencyDto.getType());
			existingAgency.setStatut(agencyDto.getStatut());
			existingAgency.setStatutIATA(agencyDto.getStatutIATA());
			existingAgency.setTypeAgrement(agencyDto.getTypeAgrement());
			existingAgency.setDateDebut(agencyDto.getDateDebut());
			existingAgency.setDateAgrement(agencyDto.getDateAgrement());
			existingAgency.setDateRadiation(agencyDto.getDateRadiation());
			existingAgency.setDateFin(agencyDto.getDateFin());
			existingAgency.setTypeVente(agencyDto.getTypeVente());
			existingAgency.setLocalisation(agencyDto.getLocalisation());
			existingAgency.setDomaine(agencyDto.getDomaine());
			existingAgency.setSousDomaine(agencyDto.getSousDomaine());
			existingAgency.setInfra(agencyDto.getInfra());
			existingAgency.setExclusifGrdCpt(agencyDto.getExclusifGrdCpt());
			existingAgency.setGsa(agencyDto.getGsa());
			existingAgency.setBsp(agencyDto.getBsp());
			existingAgency.setCodeVilleIso(agencyDto.getCodeVilleIso());
			existingAgency.setIataStationAirportCode(agencyDto.getIataStationAirportCode());
			existingAgency.setForcingUpdate(agencyDto.getForcingUpdate());
			existingAgency.setSiteInternet(agencyDto.getSiteInternet());
			existingAgency.setObservation(agencyDto.getObservation());

			if (StringUtils.isBlank(agencyDto.getSiteCreation())) {
				existingAgency.setSiteModification(AgencyValidationHelper.SITE_QVI);
			} else {
				existingAgency.setSiteModification(agencyDto.getSiteModification());
			}
			existingAgency.setSignatureModification(agencyDto.getSignatureModification());
			existingAgency.setDateModification(today);

			// Update
			if (!CollectionUtils.isEmpty(existingAgency.getOffices())) {
				for (OfficeID bo : existingAgency.getOffices()) {
					bo.setAgence(existingAgency);
				}
			}

			ProfilFirmeDTO profilFirmeDto = agencyDto.getProfilFirme();
			if (profilFirmeDto != null) {
				profilFirmeDto.setGin(agencyDto.getGin());

				if (existingAgency.getProfilFirme() != null) {
					profilFirmeUs.update(profilFirmeDto);
				} else {
					profilFirmeUs.create(profilFirmeDto);
				}
			}

			if (!CollectionUtils.isEmpty(agencyDto.getOffices())) {
				for (OfficeIDDTO officeId : agencyDto.getOffices()) {
					if (officeId.getCle() == null)
						officeIdDS.createInAgency(officeId, agencyDto);
					else
						officeIdDS.update(officeId, existingAgency);
				}
			}

			if (!CollectionUtils.isEmpty(agencyDto.getNumerosIdent())) {
				List<NumeroIdent> nums = new ArrayList<>();
				for (NumeroIdentDTO num : agencyDto.getNumerosIdent()) {
					nums.add(NumeroIdentTransform.dto2BoLight(num));
				}
				numeroIdentUS.createOrUpdateOrDelete(nums, existingAgency);
			}

			if (!CollectionUtils.isEmpty(agencyDto.getSegmentations())) {
				List<Segmentation> segmentations = new ArrayList<>();
				for (SegmentationDTO segmentation : agencyDto.getSegmentations()) {
					segmentations.add(SegmentationTransform.dto2BoLight(segmentation));
				}
				segmentationUs.createUpdateOrDeleteForAgency(segmentations, existingAgency, true, true);
			}

			if (!CollectionUtils.isEmpty(agencyDto.getPostalAddresses())) {
				final String ADRESS_TYPE_LOCATION = "L";
				List <PostalAddressDTO> postalAddressesToCreateOrUpdate = new ArrayList<>();
				List <PostalAddressDTO> existingPostalAdressDTO = PostalAddressTransform.bo2Dto(existingAgency.getPostalAddresses());
				for (PostalAddressDTO address : existingPostalAdressDTO) {
					for (PostalAddressDTO address2Check : agencyDto.getPostalAddresses()) {
							if (agencyDto.getPostalAddresses().stream().filter(obj -> obj.getScode_medium().equals(ADRESS_TYPE_LOCATION)).count() > 1) {
								throw new JrafDomainException("ONLY ONE LOCATION ALLOWED");
							}
						if (agencyDto.getPostalAddresses().stream().noneMatch(obj -> obj.getScode_medium().equals(ADRESS_TYPE_LOCATION))){
							throw new JrafDomainException("LOCATION MANDATORY");
						}

						if (existingPostalAdressDTO.stream().noneMatch(obj -> obj.equals(address2Check))){
							if (!(postalAddressesToCreateOrUpdate.contains(address2Check))) {
								postalAddressesToCreateOrUpdate.add(address2Check);
							}
						}

						if (!(postalAddressesToCreateOrUpdate.contains(address2Check))) {
							postalAddressesToCreateOrUpdate.add(address2Check);
						}

					}


				}
				for (PostalAddressDTO postalAddressDTO : postalAddressesToCreateOrUpdate) {
					postalAddressDS.createUpdateDeleteWithNormalization(postalAddressDTO, existingAgency);
				}

			}

			if (!CollectionUtils.isEmpty(agencyDto.getTelecoms())) {
				List<Telecoms> telecoms = new ArrayList<>();
				// WOPA-648: Force the update to avoid normalization errors
				boolean forceUpdate = true;
				for (TelecomsDTO telecom : agencyDto.getTelecoms()) {
					if (RefTableREF_STATUTPM._REF_X.equals(telecom.getSstatut_medium())) {
						Telecoms telecomToDelete = new Telecoms();
						telecomToDelete.setSain(telecom.getSain());
						telecoms.add(telecomToDelete);
					} else {
						telecoms.add(TelecomsTransform.dto2BoLight(telecom));
					}
				}
				telecomDS.createOrUpdateOrDelete(telecoms, existingAgency, forceUpdate);
			}

			if (!CollectionUtils.isEmpty(agencyDto.getEmails())) {
				List<Email> emails = new ArrayList<>();
				for (EmailDTO email : agencyDto.getEmails()) {
					if (RefTableREF_STATUTPM._REF_X.equals(email.getStatutMedium())) {
						Email emailToDelete = new Email();
						emailToDelete.setSain(email.getSain());
						emailToDelete.setVersion(email.getVersion());
						emails.add(emailToDelete);
					} else {
						emails.add(EmailTransform.dto2BoLight(email));
					}
				}
				emailUS.createOrUpdateOrDelete(emails, existingAgency);
				existingAgency.setEmails(new HashSet<>(emails));
			}

			if (!CollectionUtils.isEmpty(agencyDto.getSynonymes())) {
				List<Synonyme> synonymes = new ArrayList<>();
				for (SynonymeDTO synonymeDTO : agencyDto.getSynonymes()) {
					synonymes.add(SynonymeTransform.dto2BoLight(synonymeDTO));
				}
				synonymeUS.createOrUpdateOrDelete(synonymes, existingAgency);
			}

			if (!CollectionUtils.isEmpty(agencyDto.getPmZones())) {
				List<PmZone> zcs = preparePmZoneForAgencyUpdate(agencyDto.getPmZones(), ZoneCommDTO.class);
				List<PmZone> zvs = preparePmZoneForAgencyUpdate(agencyDto.getPmZones(), ZoneVenteDTO.class);

				/*
				 * Needed to keep only existing pmZones in database for this agency. Without
				 * that, we have a NullPointerException when checking the pmZone because the
				 * zoneDecoup of the new link doesn't have a gin.
				 */
				agenceRepository.refresh(existingAgency);

				if (!CollectionUtils.isEmpty(zcs))
					pmZoneUS.createOrUpdateOrDeleteZcLinks(zcs, existingAgency);
				if (!CollectionUtils.isEmpty(zvs))
					pmZoneUS.createOrUpdateOrDeleteZvLinksV2(zvs, existingAgency);
			}

			if (!UList.isNullOrEmpty(agencyDto.getReseaux()) && !MembreReseauDTO.areEmpty(agencyDto.getReseaux())) {
				membreReseauDS.checkNetworkLinkInput(agencyDto, existingAgency);
				List<MembreReseau> listMembreReseau = new ArrayList<>();
				listMembreReseau.addAll(MembreReseauTransform.dto2Bo(agencyDto.getReseaux()));
				membreReseauUS.createOrUpdateOrDeleteNetworkLink(listMembreReseau, existingAgency);
			}

			if (!CollectionUtils.isEmpty(agencyDto.getPersonnesMoralesGerees())) {
				List<GestionPM> linkedCompanies = new ArrayList<>();
				for (GestionPMDTO gestionPm : agencyDto.getPersonnesMoralesGerees()) {
					gestionPm.setPersonneMoraleGeree(
							etablissementDS.get((EtablissementDTO) gestionPm.getPersonneMoraleGeree()));
					linkedCompanies.add(GestionPMTransform.dto2Bo(gestionPm));
				}
				gestionPMUS.createOrUpdateOrDeleteForAgency(linkedCompanies, existingAgency);
			}

			agenceRepository.saveAndFlush(existingAgency);

			AgenceTransform.bo2Dto(existingAgency, agencyDto);
			return agencyDto;
		}
		return null;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void processAssociatedData(AgenceDTO agencyDto, Agence agencyBo) throws JrafDomainException {
		// Identification number
		Set<NumeroIdentDTO> identificationNumbers = agencyDto.getNumerosIdent();
		if (!CollectionUtils.isEmpty(identificationNumbers)) {
			for (NumeroIdentDTO identificationNumber : identificationNumbers) {
				identificationNumber.setPersonneMorale(agencyDto);
				numberIdDS.create(identificationNumber);
			}
		}

		// Office Id
		Set<OfficeIDDTO> offices = agencyDto.getOffices();
		if (!CollectionUtils.isEmpty(offices)) {
			for (OfficeIDDTO officeId : offices) {
				officeIdDS.create(officeId, agencyBo);
			}
		}

		// Zone
		Set<PmZone> zones = PmZoneTransform.dto2Bo(agencyDto.getPmZones());
		if (!CollectionUtils.isEmpty(zones)) {
			for (PmZone zone : zones) {
				zone.setPersonneMorale(agencyBo);
				pmZoneRepository.saveAndFlush(zone);
			}
		}

		// Postal Address
		Set<PostalAddress> addresses = PostalAddressTransform.dto2Bo(agencyDto.getPostalAddresses());
		if (!CollectionUtils.isEmpty(addresses)) {
			for (PostalAddress address : addresses) {
				address.setPersonneMorale(agencyBo);
				postalAddressRepository.saveAndFlush(address);
			}
		}

		// Profile
		Set<Profil_mereDTO> profils = agencyDto.getProfils();
		if (!CollectionUtils.isEmpty(profils)) {
			for (Profil_mereDTO profilMereDto : profils) {
				profilMereDto.setSgin_pm(agencyBo.getGin());
				Profil_mere profilMere = new Profil_mere();
				Profil_mereTransform.dto2BoLight(profilMereDto, profilMere);
				Profil_mere savedProfilMere = profilMereRepository.saveAndFlush(profilMere);

				ProfilDemarchage profilDemarcharge = ProfilDemarchageTransform
						.dto2BoLight(profilMereDto.getProfilDemarcharge());
				profilDemarcharge.setProfil(savedProfilMere);
				profilDemarchageRepository.saveAndFlush(profilDemarcharge);
			}
		}

		// Profile firm
		ProfilFirmeDTO profilFirmeDto = agencyDto.getProfilFirme();
		if (profilFirmeDto != null) {
			profilFirmeDto.setGin(agencyDto.getGin());
			profilFirmeUs.create(profilFirmeDto);
		}

		// Synonym
		Set<SynonymeDTO> synonymes = agencyDto.getSynonymes();
		if (!CollectionUtils.isEmpty(synonymes)) {
			for (SynonymeDTO synonymeDto : synonymes) {
				synonymeDto.setPersonneMorale(agencyDto);
				synonymeUS.createOrUpdate(synonymeDto);
			}
		}

		// Network
		Set<MembreReseauDTO> networks = agencyDto.getReseaux();
		if (!CollectionUtils.isEmpty(networks)) {
			for (MembreReseauDTO network : networks) {
				network.setAgence(agencyDto);
				membreReseauDS.create(network);
			}
		}

		// Phone
		Set<Telecoms> telecoms = TelecomsTransform.dto2Bo(agencyDto.getTelecoms());
		if (!CollectionUtils.isEmpty(telecoms)) {
			for (Telecoms telecom : telecoms) {
				telecom.setPersonneMorale(agencyBo);
				telecomsRepository.saveAndFlush(telecom);
			}
		}

		// Email
		Set<Email> emails = EmailTransform.dto2Bo(agencyDto.getEmails());
		if (!CollectionUtils.isEmpty(emails)) {
			for (Email email : emails) {
				email.setPersonneMorale(agencyBo);
				emailRepository.saveAndFlush(email);
			}
		}

		// Segmentation
		Set<SegmentationDTO> segmentations = agencyDto.getSegmentations();
		if (!CollectionUtils.isEmpty(segmentations)) {
			for (SegmentationDTO segmentationDto : segmentations) {
				Segmentation segmentation = SegmentationTransform.dto2BoLight(segmentationDto);
				segmentation.setPersonneMorale(agencyBo);
				segmentationRepository.saveAndFlush(segmentation);
			}
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void processAssociatedDataForCreateRa1(AgenceDTO agencyDto, Agence agencyBo) throws JrafDomainException {
		// Postal Address
		List<PostalAddressDTO> addresses = agencyDto.getPostalAddresses();
		if (!CollectionUtils.isEmpty(addresses)) {
			for (PostalAddressDTO address : addresses) {
				postalAddressDS.createUpdateDeleteWithNormalization(address, agencyBo);
			}
		}

		// Identification number
		Set<NumeroIdentDTO> identificationNumbers = agencyDto.getNumerosIdent();
		if (!CollectionUtils.isEmpty(identificationNumbers)) {
			for (NumeroIdentDTO identificationNumber : identificationNumbers) {
				identificationNumber.setPersonneMorale(agencyDto);
				numberIdDS.create(identificationNumber);
			}
		}

		// Office Id
		Set<OfficeIDDTO> offices = agencyDto.getOffices();
		if (!CollectionUtils.isEmpty(offices)) {
			for (OfficeIDDTO officeId : offices) {
				officeIdDS.create(officeId, agencyBo);
			}
		}

		// Zone
		Set<PmZone> zones = PmZoneTransform.dto2Bo(agencyDto.getPmZones());
		if (!CollectionUtils.isEmpty(zones)) {
			for (PmZone zone : zones) {
				zone.setPersonneMorale(agencyBo);
				pmZoneRepository.saveAndFlush(zone);
			}
		}

		// Profile
		Set<Profil_mereDTO> profils = agencyDto.getProfils();
		if (!CollectionUtils.isEmpty(profils)) {
			for (Profil_mereDTO profilMereDto : profils) {
				profilMereDto.setSgin_pm(agencyBo.getGin());
				Profil_mere profilMere = new Profil_mere();
				Profil_mereTransform.dto2BoLight(profilMereDto, profilMere);
				Profil_mere savedProfilMere = profilMereRepository.saveAndFlush(profilMere);

				ProfilDemarchage profilDemarcharge = ProfilDemarchageTransform
						.dto2BoLight(profilMereDto.getProfilDemarcharge());
				profilDemarcharge.setProfil(savedProfilMere);
				profilDemarchageRepository.saveAndFlush(profilDemarcharge);
			}
		}

		// Profile firm
		ProfilFirmeDTO profilFirmeDto = agencyDto.getProfilFirme();
		if (profilFirmeDto != null) {
			profilFirmeDto.setGin(agencyDto.getGin());
			profilFirmeUs.create(profilFirmeDto);
		}

		// Synonym
		Set<SynonymeDTO> synonymes = agencyDto.getSynonymes();
		if (!CollectionUtils.isEmpty(synonymes)) {
			for (SynonymeDTO synonymeDto : synonymes) {
				synonymeDto.setPersonneMorale(agencyDto);
				synonymeUS.createOrUpdate(synonymeDto);
			}
		}

		// Network
		Set<MembreReseauDTO> networks = agencyDto.getReseaux();
		if (!CollectionUtils.isEmpty(networks)) {
			for (MembreReseauDTO network : networks) {
				network.setAgence(agencyDto);
				membreReseauDS.create(network);
			}
		}

		// Phone
		Set<Telecoms> telecoms = TelecomsTransform.dto2Bo(agencyDto.getTelecoms());
		if (!CollectionUtils.isEmpty(telecoms)) {
			for (Telecoms telecom : telecoms) {
				telecom.setPersonneMorale(agencyBo);
				telecomsRepository.saveAndFlush(telecom);
			}
		}

		// Email
		Set<Email> emails = EmailTransform.dto2Bo(agencyDto.getEmails());
		if (!CollectionUtils.isEmpty(emails)) {
			for (Email email : emails) {
				email.setPersonneMorale(agencyBo);
				emailRepository.saveAndFlush(email);
			}
		}

		// Segmentation
		Set<SegmentationDTO> segmentations = agencyDto.getSegmentations();
		if (!CollectionUtils.isEmpty(segmentations)) {
			for (SegmentationDTO segmentationDto : segmentations) {
				Segmentation segmentation = SegmentationTransform.dto2BoLight(segmentationDto);
				segmentation.setPersonneMorale(agencyBo);
				segmentationRepository.saveAndFlush(segmentation);
			}
		}
	}

	private void updateOfficeId(Agence agence, Set<OfficeIDDTO> offices, String action) throws JrafDomainException {
		if (AgencyConstantValues.ACTION_UPDATE.equals(action)) {
			for (OfficeIDDTO officeId : offices) {
				if (officeId.getCle() == null) {
					officeIdDS.create(officeId, agence);
					break;
				}
			}
		} else if (AgencyConstantValues.ACTION_DELETE.equals(action)) {
			for (OfficeIDDTO officeId : offices) {
				officeIdDS.remove(officeId.getCle());
			}
		}
	}

	private Agence findAgenceBySgin(String sgin) throws JrafDomainException {
		// no search if sgin is null
		if (sgin == null) {
			throw new JrafDomainException("Unable to search Agence with null Sgin");
		}

		// search the agence
		Optional<Agence> found = agenceRepository.findById(sgin);

		// stop process is no agence found with sgin
		if (!found.isPresent()) {
			throw new JrafDomainException("Unable to find Agence with Sgin : " + sgin);
		}

		return found.get();
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void prepareMasterGsaAgency(String process, AgenceDTO agenceFromDB, AgenceDTO agenceDTO) throws JrafDomainException {
		if (RefTableREF_STATUTPM._REF_A.equals(agenceDTO.getStatut())
				|| RefTableREF_STATUTPM._REF_Q.equals(agenceDTO.getStatut())
				|| RefTableREF_STATUTPM._REF_R.equals(agenceFromDB.getStatut())) {

			if (RefTableREF_STATUTPM._REF_A.equals(agenceFromDB.getStatut())) {
				process = AgencyConstantValues.ACTION_UPDATE;
			} else if (RefTableREF_STATUTPM._REF_R.equals(agenceFromDB.getStatut())) {
				// On ferme celle en base
				agenceFromDB.setStatut(RefTableREF_STATUTPM._REF_X);
				agenceFromDB.setDateModificationStatut(agenceDTO.getDateModification());
				agenceFromDB.setDateFin(agenceFromDB.getDateRadiation());
				agenceFromDB.setSiteModification(agenceDTO.getSiteModification());
				agenceFromDB.setSignatureModification(agenceDTO.getSignatureModification());
				agenceFromDB.setDateModification(agenceDTO.getDateModification());

				agenceRepository.saveAndFlush(AgenceTransform.dto2BoLight(agenceFromDB));

				if (!CollectionUtils.isEmpty(agenceFromDB.getNumerosIdent())) {
					for (NumeroIdentDTO ni : agenceFromDB.getNumerosIdent()) {
						ni.setDateFermeture(agenceFromDB.getDateFin());
						ni.setDateModification(agenceDTO.getDateModification());
						numberIdDS.update(ni);
					}
				}
			}
		}
	}

	private List<PmZone> preparePmZone(Set<PmZoneDTO> zonesList, Class zoneType) throws JrafDomainException {
		List<PmZone> zones = new ArrayList<>();
		boolean hasPrivilegedLink = hasPrivilegedLink(zonesList, zoneType);

		for(PmZoneDTO zone : zonesList) {
			if(zone.getZoneDecoup().getClass().equals(zoneType)) {
				if(hasPrivilegedLink && !PrivilegedLinkEnum.YES.toLiteral().equals(zone.getLienPrivilegie())) { //If a privileged zone is already present
					PmZone temp = PmZoneTransform.dto2Bo(zone);
					temp.setLienPrivilegie(PrivilegedLinkEnum.NO.toLiteral());
					zones.add(temp);
				} else if (StringUtils.isBlank(zone.getLienPrivilegie()) ||
						PrivilegedLinkEnum.YES.toLiteral().equals(zone.getLienPrivilegie())) { //If it's the first zc creation for this agency
					PmZone temp = PmZoneTransform.dto2Bo(zone);
					temp.setLienPrivilegie(PrivilegedLinkEnum.YES.toLiteral());
					zones.add(temp);
				}
			}
		}
		return zones;
	}

	private List<PmZone> preparePmZoneForAgencyUpdate(Set<PmZoneDTO> zonesList, Class zoneType) throws JrafDomainException {
		List<PmZone> zones = new ArrayList<>();

		for (PmZoneDTO zone : zonesList) {
			if (zone.getZoneDecoup().getClass().equals(zoneType)) {
				// If creating a privileged ZC or ZV link, add to the list (the old link will be closed automatically)
				// If creating or updating a non-privileged link, add to the list
				if (PrivilegedLinkEnum.YES.toLiteral().equals(zone.getLienPrivilegie()) && zone.getCle() == null) {
					PmZone temp = PmZoneTransform.dto2Bo(zone);
					temp.setLienPrivilegie(PrivilegedLinkEnum.YES.toLiteral());
					zones.add(temp);
				} else if (!PrivilegedLinkEnum.YES.toLiteral().equals(zone.getLienPrivilegie())) {
					PmZone temp = PmZoneTransform.dto2Bo(zone);
					temp.setLienPrivilegie(PrivilegedLinkEnum.NO.toLiteral());
					zones.add(temp);
				}
			}
		}
		return zones;
	}

	private boolean hasPrivilegedLink(Set<PmZoneDTO> zones, Class zoneType) {
		for(PmZoneDTO zone : zones) {
			if(PrivilegedLinkEnum.YES.toLiteral().equals(zone.getLienPrivilegie()) && zone.getZoneDecoup().getClass().equals(zoneType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <ul>
	 * Depending on the action, the following is executed :
	 * <li>CREATE : An Agency is created from scratch</li>
	 * <li>UPDATE : Modify, Radiation, Restructure, Number change</li>
	 * </ul>
	 *
	 * @param agenceDTO
	 * @param process    the action to be executed
	 * @param type Iata or GSA (IATA by default)
	 * @param isMasterTapeIata
	 * @return
	 * @throws JrafDomainException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String CreateOrUpdateIataAgency(AgenceDTO agenceDTO, String process, String type, boolean isMasterTapeIata) throws JrafDomainException {

		String iataNumber = getIataNumber(agenceDTO.getNumerosIdent());
		type = StringUtils.isNotBlank(type) ? type : AgencyConstantValues.IATA;

		// Find agency
		AgenceDTO agenceFromDB = selectOneAgencyFromDB(iataNumber);

		// Prepare process for Master tape and GSA
		if (isMasterTapeIata && isGsa(type) && agenceFromDB != null) {
			prepareMasterGsaAgency(process, agenceFromDB, agenceDTO);
		}

		// Case Creation -->  RecId = A / Process = C
		if (AgencyConstantValues.ACTION_CREATE.equals(process)) {
			if (agenceFromDB != null) {
				// Agency already exist
				if (AgencyConstantValues.GSA.equals(type)) {
					agenceRepository.delete(AgenceTransform.dto2BoLight(agenceFromDB));
				} else {
					throw new AlreadyExistException("IATA Number : " + iataNumber + " - Agency already exists" );
				}
			}

			String cityLoc = null;

			// Set code City
			if (StringUtils.isBlank(agenceDTO.getCodeVilleIso())) {
				// Find city from localization address
				cityLoc = getCityFromLocalizationAddress(agenceDTO);
				try {
					agenceDTO.setCodeVilleIso(refCityDS.getCountryCapitol(cityLoc));
				} catch (JrafDomainException e) {
					log.error("IATA Number : " + iataNumber + " - Unable to get country capitol");
				}
			}

			// Attach default ZV
			if (StringUtils.isNotBlank(cityLoc)) {
				try {
					manageStandbyZV(agenceDTO, cityLoc);
				} catch (JrafDomainException e) {
					log.error("IATA Number : " + iataNumber + " - " + e.getMessage());
				}
			}

			prepareCreation(agenceDTO);
			createNewIataAgency(agenceDTO);

			if (StringUtils.isNotBlank(iataNumber)) {
				// Get Agency created in Database
				List<Agence> results = agenceRepository.findActiveByIdType(iataNumber, Arrays.asList(AgencyConstantValues.IATA_TYPE));
				if (results == null || results.isEmpty()) {
					throw new AgencyNotFoundException("Error while creating new Agency");
				} else if (results.size() > 1) { // More than 1 active agency with the same IATA Number
					throw new TooManyResultsDomainException("IATA Number : " + iataNumber + " - Multiple Active Agency with the same IATA number");
				} else {
					return results.get(0).getGin();
				}
			}
		}
		// Case Update --> RecId = B or C / Process = U
		else if (AgencyConstantValues.ACTION_UPDATE.equals(process)) {
			if (agenceFromDB == null) {
				throw new AgencyNotFoundException("Agency " + iataNumber + " not found");
			}

			String statutIata = agenceFromDB.getStatutIATA();
			String statutAgence = agenceFromDB.getStatut();

			if (StringUtils.isBlank(statutIata)) {
				throw new InvalidMediumStatusException("Agency " + iataNumber + " : No IATA status");
			}
			if (isArcStatus(statutIata)) {
				// No update of ARC agency from BANDE_IATA
				return null;
			}

			// Open if closed agency
			if (RefTableREF_STATUTPM._REF_R.equals(statutAgence)) {
				agenceDTO.setStatut(RefTableREF_STATUTPM._REF_A);
				agenceDTO.setDateRadiation(null);
				agenceDTO.setDateFin(null);
			}

			// *****************
			// * UPDATE AGENCY *
			// *****************
			agenceDTO.setGin(agenceFromDB.getGin());
			agenceDTO = updateIataAgency(agenceDTO, agenceFromDB);

			return agenceDTO.getGin();
		}
		// Case Radiation --> RecId = D / Process = D
		else if (AgencyConstantValues.ACTION_DELETE.equals(process)) {
			if (agenceFromDB == null) {
				throw new AgencyNotFoundException("Agency " + iataNumber + " not found");
			}

			String statutIata = agenceFromDB.getStatutIATA();
			String statutAgence = agenceFromDB.getStatut();

			if (StringUtils.isBlank(statutIata)) {
				throw new InvalidMediumStatusException("No IATA status");
			}
			if (isArcStatus(statutIata)) {
				// No update of ARC agency from BANDE_IATA
				log.warn("No update of ARC agency from BANDE_IATA");
				return agenceFromDB.getGin();
			}

			// Close agency
			agenceFromDB.setDateFin(agenceDTO.getDateRadiation());
			agenceFromDB.setDateRadiation(agenceDTO.getDateRadiation());
			agenceFromDB.setStatut(agenceDTO.getStatut());
			agenceFromDB.setDateModificationStatut(agenceDTO.getDateModification());

			// Signature Modif
			agenceFromDB.setSiteModification(agenceDTO.getSiteModification());
			agenceFromDB.setSignatureModification(agenceDTO.getSignatureModification());
			agenceFromDB.setDateModification(agenceDTO.getDateModification());

			Agence updatedAgency = agenceRepository.saveAndFlush(AgenceTransform.dto2BoLight(agenceFromDB));

			return updatedAgency.getGin();
		}

		return null;
	}

	private boolean isGsa(String type) {
		return AgencyConstantValues.GSA.equals(type);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class)
	public AgenceDTO selectOneAgencyFromDB(String iataNumber) throws JrafDomainException {
		// Find agency by Iata number
		List<String> allTypes = Arrays.asList(
				NumeroIdentTypeEnum.ARC.getCode(),
				NumeroIdentTypeEnum.ATAF.getCode(),
				NumeroIdentTypeEnum.IATA.getCode(),
				NumeroIdentTypeEnum.TID.getCode(),
				NumeroIdentTypeEnum.NON_AGREE.getCode());

		List<AgenceDTO> agencyListFromDB = findByIdType(iataNumber, allTypes, true);
		AgenceDTO agenceFromDB = null;
		// Check result from DB
		if (!CollectionUtils.isEmpty(agencyListFromDB)) {
			if ( agencyListFromDB.size() == 1) {
				agenceFromDB = agencyListFromDB.get(0);
			} else {
				boolean differentGin = false;
				boolean firstElement = true;
				String ginRef = "";
				for (AgenceDTO loop : agencyListFromDB) {
					if (firstElement) {
						ginRef = loop.getGin();
						firstElement = false;
					}
					differentGin = !ginRef.equals(loop.getGin());

					if (differentGin) {
						log.error("Multiple GIN with same number");
						throw new TooManyResultsDomainException("IATA Number : " + iataNumber + " - Multiple GIN with same number");
					}
				}
				if (!differentGin) {
					agenceFromDB = agencyListFromDB.get(0);
				}
			}
		}

		return  agenceFromDB;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public AgenceDTO updateIataAgency(AgenceDTO inAgency, AgenceDTO agencyToUpdate) throws JrafDomainException {
		if (inAgency == null || agencyToUpdate == null) {
			return null;
		}

		agencyToUpdate.setDateFin(inAgency.getDateFin());
		agencyToUpdate.setDateRadiation(inAgency.getDateRadiation());
		if (inAgency.getStatut() != null) {
			agencyToUpdate.setStatut(inAgency.getStatut());
		}
		if (inAgency.getDateModifiSTAIATA() != null) {
			agencyToUpdate.setDateModifiSTAIATA(inAgency.getDateModifiSTAIATA());
		}
		if (inAgency.getDateAgrement() != null) {
			agencyToUpdate.setDateAgrement(inAgency.getDateAgrement());
		}
		if (inAgency.getDateDebut() != null) {
			agencyToUpdate.setDateDebut(inAgency.getDateDebut());
		}
		if (StringUtils.isNotBlank(inAgency.getNom())) {
			agencyToUpdate.setNom(inAgency.getNom());
			agencyToUpdate.setDateModificationNom(inAgency.getDateModification());
		}
		if (StringUtils.isNotBlank(inAgency.getAgenceRA2())) {
			agencyToUpdate.setAgenceRA2(inAgency.getAgenceRA2());
		}
		if (StringUtils.isNotBlank(inAgency.getBsp())) {
			agencyToUpdate.setBsp(inAgency.getBsp());
		}
		if (StringUtils.isNotBlank(inAgency.getCible())) {
			agencyToUpdate.setCible(inAgency.getCible());
		}
		if (StringUtils.isNotBlank(inAgency.getCodeVilleIso())) {
			agencyToUpdate.setCodeVilleIso(inAgency.getCodeVilleIso());
		}
		if (StringUtils.isNotBlank(inAgency.getCodeService())) {
			agencyToUpdate.setCodeService(inAgency.getCodeService());
		}
		if (StringUtils.isNotBlank(inAgency.getDomaine())) {
			agencyToUpdate.setDomaine(inAgency.getDomaine());
		}
		if (StringUtils.isNotBlank(inAgency.getEnvoieSI())) {
			agencyToUpdate.setEnvoieSI(inAgency.getEnvoieSI());
		}
		if (StringUtils.isNotBlank(inAgency.getExclusifGrdCpt())) {
			agencyToUpdate.setExclusifGrdCpt(inAgency.getExclusifGrdCpt());
		}
		if (StringUtils.isNotBlank(inAgency.getGsa())) {
			agencyToUpdate.setGsa(inAgency.getGsa());
		}
		if (StringUtils.isNotBlank(inAgency.getInfra())) {
			agencyToUpdate.setInfra(inAgency.getInfra());
		}
		if (StringUtils.isNotBlank(inAgency.getLocalisation())) {
			agencyToUpdate.setLocalisation(inAgency.getLocalisation());
		}
		if (StringUtils.isNotBlank(inAgency.getNumeroIATAMere())) {
			agencyToUpdate.setNumeroIATAMere(inAgency.getNumeroIATAMere());
		}
		if (StringUtils.isNotBlank(inAgency.getObservation())) {
			agencyToUpdate.setObservation(inAgency.getObservation());
		}
		if (StringUtils.isNotBlank(inAgency.getSousDomaine())) {
			agencyToUpdate.setSousDomaine(inAgency.getSousDomaine());
		}
		if (StringUtils.isNotBlank(inAgency.getStatutIATA())) {
			agencyToUpdate.setStatutIATA(inAgency.getStatutIATA());
			agencyToUpdate.setDateModifiSTAIATA(inAgency.getDateModification());
		}
		if (StringUtils.isNotBlank(inAgency.getType())) {
			agencyToUpdate.setType(inAgency.getType());
		}
		if (StringUtils.isNotBlank(inAgency.getTypeAgrement())) {
			agencyToUpdate.setTypeAgrement(inAgency.getTypeAgrement());
		}
		if (StringUtils.isNotBlank(inAgency.getTypeVente())) {
			agencyToUpdate.setTypeVente(inAgency.getTypeVente());
		}
		if (StringUtils.isNotBlank(inAgency.getZoneChalandise())) {
			agencyToUpdate.setZoneChalandise(inAgency.getZoneChalandise());
		}
		if (StringUtils.isNotBlank(inAgency.getIataStationAirportCode())) {
			agencyToUpdate.setIataStationAirportCode(inAgency.getIataStationAirportCode());
		}

		// Signature Modif
		agencyToUpdate.setSiteModification(inAgency.getSiteModification());
		agencyToUpdate.setSignatureModification(inAgency.getSignatureModification());
		agencyToUpdate.setDateModification(inAgency.getDateModification());

		Agence updatedAgency = agenceRepository.saveAndFlush(AgenceTransform.dto2BoLight(agencyToUpdate));

		// Linked data
		processUpdateForAssociatedData(inAgency, updatedAgency);

		return AgenceTransform.bo2DtoLight(updatedAgency);
	}

	private boolean isArcStatus(String statutIata) {
		return statutIata.equals(RefTableREF_STATUT_IATA._REF_AA) ||
				statutIata.equals(RefTableREF_STATUT_IATA._REF_AR) ||
				statutIata.equals(RefTableREF_STATUT_IATA._REF_AT) ||
				statutIata.equals(RefTableREF_STATUT_IATA._REF_BB) ||
				statutIata.equals(RefTableREF_STATUT_IATA._REF_BI) ||
				statutIata.equals(RefTableREF_STATUT_IATA._REF_BT) ||
				statutIata.equals(RefTableREF_STATUT_IATA._REF_HH) ||
				statutIata.equals(RefTableREF_STATUT_IATA._REF_HI) ||
				statutIata.equals(RefTableREF_STATUT_IATA._REF_HR) ||
				statutIata.equals(RefTableREF_STATUT_IATA._REF_HT) ||
				statutIata.equals(RefTableREF_STATUT_IATA._REF_SS);
	}

	private void prepareCreation(AgenceDTO agenceDTO) {
		// Check synonyms
		String raisonSociale = agenceDTO.getNom();
		if (CollectionUtils.isEmpty(agenceDTO.getSynonymes())) {

			Set<SynonymeDTO> synonyms = new HashSet<>();

			SynonymeDTO synonymTrade = new SynonymeDTO();
			synonymTrade.setType(SynonymTypeEnum.TRADE_NAME.toLiteral());
			synonymTrade.setNom(raisonSociale);
			synonymTrade.setStatut(MediumStatusEnum.VALID.toString());
			synonymTrade.setDateModificationSnom(new Date());
			synonyms.add(synonymTrade);

			SynonymeDTO usualName = new SynonymeDTO();
			usualName.setType(SynonymTypeEnum.USUAL_NAME.toLiteral());
			usualName.setNom(raisonSociale);
			usualName.setStatut(MediumStatusEnum.VALID.toString());
			usualName.setDateModificationSnom(new Date());
			synonyms.add(usualName);

			agenceDTO.setSynonymes(synonyms);
		} else if (agenceDTO.getSynonymes().size() < 2) {
			// Only Marque Commercial is filled
			SynonymeDTO usualName = new SynonymeDTO();
			usualName.setType(SynonymTypeEnum.USUAL_NAME.toLiteral());
			usualName.setNom(raisonSociale);
			usualName.setStatut(MediumStatusEnum.VALID.toString());
			usualName.setDateModificationSnom(new Date());
			agenceDTO.getSynonymes().add(usualName);
		}

		// Check Modification dates
		String sign = agenceDTO.getSignatureCreation();
		String site = agenceDTO.getSiteCreation();
		Date dateSign = agenceDTO.getDateCreation();

		if (StringUtils.isBlank(agenceDTO.getSignatureModification())) {
			agenceDTO.setSignatureModification(sign);
		}
		if (StringUtils.isBlank(agenceDTO.getSiteModification())) {
			agenceDTO.setSiteModification(site);
		}
		if (agenceDTO.getDateModification() == null) {
			agenceDTO.setDateModification(dateSign);
		}

		// Date for contacts
		// Tel
		if (!CollectionUtils.isEmpty(agenceDTO.getTelecoms())) {
			for (TelecomsDTO tel : agenceDTO.getTelecoms()) {
				if (StringUtils.isBlank(tel.getSsignature_modification())) {
					tel.setSsignature_modification(sign);
				}
				if (StringUtils.isBlank(tel.getSsite_modification())) {
					tel.setSsite_modification(site);
				}
				if (tel.getDdate_modification() == null) {
					tel.setDdate_modification(dateSign);
				}
			}
		}

		// Email
		if (!CollectionUtils.isEmpty(agenceDTO.getEmails())) {
			for (EmailDTO mail : agenceDTO.getEmails()) {
				if (StringUtils.isBlank(mail.getSignatureModification())) {
					mail.setSignatureModification(sign);
				}
				if (StringUtils.isBlank(mail.getSiteModification())) {
					mail.setSiteModification(site);
				}
				if (mail.getDateModification() == null) {
					mail.setDateModification(dateSign);
				}
			}
		}

		// Address
		if (!CollectionUtils.isEmpty(agenceDTO.getPostalAddresses())) {
			for (PostalAddressDTO adr : agenceDTO.getPostalAddresses()) {
				if (StringUtils.isBlank(adr.getSsignature_modification())) {
					adr.setSsignature_modification(sign);
				}
				if (StringUtils.isBlank(adr.getSsite_modification())) {
					adr.setSsite_modification(site);
				}
				if (adr.getDdate_modification() == null) {
					adr.setDdate_modification(dateSign);
				}
			}
		}
	}

	private String getCityFromLocalizationAddress(AgenceDTO inAgency) {

		if (inAgency.getPostalAddresses() != null && !inAgency.getPostalAddresses().isEmpty()) {
			for (PostalAddressDTO pa : inAgency.getPostalAddresses()) {
				if (MediumCodeEnum.LOCALISATION.toString().equals(pa.getScode_medium())) {
					return pa.getScode_pays();
				}
			}
		}
		return null;
	}

	private void manageStandbyZV(AgenceDTO inAgency, String cityLoc) throws JrafDomainException {

		ZoneVenteDTO zoneVenteDTO = new ZoneVenteDTO();
		List<LienIntCpZdDTO> lienCpZdList = lienIntCpZdUs.findByCountrySalesZone(cityLoc, zoneVenteDTO);
		if (!CollectionUtils.isEmpty(lienCpZdList)) {
			// We return the first result
			LienIntCpZdDTO linIntCpZd = lienCpZdList.get(0);
			zoneVenteDTO = zoneDS.findSaleZoneByGin(linIntCpZd.getZoneDecoup().getGin());

			// Define creation of ZV Poubelle or ZV Attente
			boolean createZVPoubelle = false;
			boolean createZVAttente = true;

			// Check start date of Agency and Zones
			Date debutAgence = inAgency.getDateDebut();
			Date debutZVAttente = zoneVenteDTO.getDateOuverture();
			Date finZVAttente = zoneVenteDTO.getDateFermeture();
			Date debutZCAttente = SicDateUtils.convertStringToDateDDMMYYYY(AgencyConstantValues.DEFAULT_START_DATE);
			Date finZCAttente = SicDateUtils.convertStringToDateDDMMYYYY(AgencyConstantValues.DEFAULT_START_DATE);

			if (debutAgence.before(debutZVAttente) || (finZVAttente != null && finZVAttente.before(debutAgence))) {
				// Create a ZV poubell
				createZVPoubelle = true;
				createZVAttente = false;
			}

			// Step 1: Find ZC date linked to the ZV Standby
			if (createZVAttente) {
				PmZoneDTO pmZoneDTO = new PmZoneDTO();
				pmZoneDTO.setZoneDecoup(zoneVenteDTO);
				pmZoneDTO.setDateOuverture(debutAgence);

				// Find the ZC level 1
				ZoneCommDTO zoneCommDTO = zoneDS.getZcFromZv(zoneVenteDTO, 1);
				if (zoneCommDTO == null) {
					log.error("No ZC linked to ZV");
					throw new InvalidCommercialZoneException("Invalid ZC or ZV");
				}
				debutZCAttente = zoneCommDTO.getDateOuverture();
				finZCAttente = zoneCommDTO.getDateFermeture();

				if (finZCAttente != null && finZCAttente.before(debutAgence)) {
					throw new InvalidCommercialZoneException("ZC5 not valid");
				}

				// Insert ZV attente
				PmZoneDTO pmZoneVenteAtt = new PmZoneDTO();
				pmZoneVenteAtt.setZoneDecoup(zoneVenteDTO);
				// On met la date d'ouverture du lien a la date d'ouverture la plus recente
				// entre la date d'ouverture de l'agence et la date d'ouverture de la ZV
				if (debutZVAttente != null && debutZVAttente.after(debutAgence)) {
					pmZoneVenteAtt.setDateOuverture(debutZVAttente);
				} else {
					pmZoneVenteAtt.setDateOuverture(debutAgence);
				}
				// pmZoneVenteAtt.setDateOuverture(debutAgence);
				pmZoneVenteAtt.setLienPrivilegie(OuiNonFlagEnum.OUI.toString());
				pmZoneVenteAtt.setSignature(AgencyConstantValues.BANDE_IATA_SIGN);
				pmZoneVenteAtt.setDateModif(new Date());
				// Pas de fermeture de lien
				//pmZoneVenteAtt.setDateFermeture(SicDateUtils.convertStringToDateDDMMYYYY(AgencyConstantValues.DEFAULT_START_DATE));
				if (inAgency.getPmZones() != null) {
					inAgency.getPmZones().add(pmZoneVenteAtt);
				} else {
					inAgency.setPmZones(Sets.newHashSet(Collections.singletonList(pmZoneVenteAtt)));
				}

				// Add ZC to agency
				PmZoneDTO pmZoneCommAtt = new PmZoneDTO();
				// On met la date d'ouverture du lien a la date d'ouverture la plus recente
				// entre la date d'ouverture de l'agence et la date d'ouverture de la ZC5 liee a 90 90 90 90
				if (debutZCAttente != null && debutZCAttente.after(debutAgence)) {
					pmZoneCommAtt.setDateOuverture(debutZCAttente);
				} else {
					pmZoneCommAtt.setDateOuverture(debutAgence);
				}

				pmZoneCommAtt.setZoneDecoup(zoneCommDTO);
				pmZoneCommAtt.setLienPrivilegie(OuiNonFlagEnum.OUI.toString());
				pmZoneCommAtt.setSignature(AgencyConstantValues.BANDE_IATA_SIGN);
				pmZoneCommAtt.setDateModif(new Date());
				//pmZoneCommAtt.setDateFermeture(SicDateUtils.convertStringToDateDDMMYYYY(AgencyConstantValues.DEFAULT_START_DATE));
				inAgency.getPmZones().add(pmZoneCommAtt);
				inAgency.setStatut(RefTableREF_STATUTPM._REF_A);
			}

			// Step 2: Find ZV poubelle and associated ZC
			if (createZVPoubelle) {
				// La Zone de Vente Poubelle est la 90 90 90 90 (codage en dur valide par AMO)
				int zpCode = AgencyConstantValues.ZV_POUB_CODE;
				ZoneVenteDTO zvPoub = zoneDS.findZoneVenteByZv0Zv1Zv2Zv3(zpCode, zpCode, zpCode, zpCode);
				PmZoneDTO pmZoneVentePoub = new PmZoneDTO();
				pmZoneVentePoub.setZoneDecoup(zvPoub);
				pmZoneVentePoub.setDateOuverture(debutAgence);
				pmZoneVentePoub.setLienPrivilegie(OuiNonFlagEnum.OUI.toString());
				pmZoneVentePoub.setSignature(AgencyConstantValues.BANDE_IATA_SIGN);
				pmZoneVentePoub.setDateModif(new Date());
				//pmZoneVentePoub.setDateFermeture(SicDateUtils.convertStringToDateDDMMYYYY(AgencyConstantValues.DEFAULT_START_DATE));

				ZoneCommDTO zoneCommDTO = zoneDS.getZcFromZv(zvPoub, 1);
				if (zoneCommDTO == null) {
					log.error("No ZC link to ZV 90 90 90 90");
					throw new InvalidCommercialZoneException("No ZC link to ZV 90 90 90 90");
				}

				Date debutZCPoubelle = zoneCommDTO.getDateOuverture();
				Date finZCPoubelle = zoneCommDTO.getDateFermeture();

				if (finZCPoubelle != null && finZCPoubelle.before(debutAgence)) {
					throw new InvalidCommercialZoneException("ZC5 linked to 90 90 90 90 is invalid");
				}

				inAgency.setStatut(RefTableREF_STATUTPM._REF_A);

				// Add ZV to agency
				if (inAgency.getPmZones() != null) {
					inAgency.getPmZones().add(pmZoneVentePoub);
				} else {
					inAgency.setPmZones(Sets.newHashSet(Collections.singletonList(pmZoneVentePoub)));
				}

				// Add ZC to agency
				PmZoneDTO pmZoneCommPoub = new PmZoneDTO();
				// On met la date d'ouverture du lien a la date d'ouverture la plus recente
				// entre la date d'ouverture de l'agence et la date d'ouverture de la ZC5 liee a 90 90 90 90
				if (debutZCPoubelle != null && debutZCPoubelle.after(debutAgence)) {
					pmZoneCommPoub.setDateOuverture(debutZCPoubelle);
				} else {
					pmZoneCommPoub.setDateOuverture(debutAgence);
				}
				pmZoneCommPoub.setZoneDecoup(zoneCommDTO);
				pmZoneCommPoub.setLienPrivilegie(OuiNonFlagEnum.OUI.toString());
				pmZoneCommPoub.setSignature(AgencyConstantValues.BANDE_IATA_SIGN);
				pmZoneCommPoub.setDateModif(new Date());
				//pmZoneCommPoub.setDateFermeture(SicDateUtils.convertStringToDateDDMMYYYY(AgencyConstantValues.DEFAULT_START_DATE));

				inAgency.getPmZones().add(pmZoneCommPoub);
			}
		}
	}

	/**
	 * createNewIataAgency
	 *
	 * @param agenceDTO in AgenceDTO
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createNewIataAgency(AgenceDTO agenceDTO) throws JrafDomainException {

		Agence newAgency = agenceRepository.saveAndFlush(AgenceTransform.dto2BoLight(agenceDTO));
		agenceDTO.setGin(newAgency.getGin());
		processAssociatedData(agenceDTO,newAgency);

	}

	private String getIataNumber(Set<NumeroIdentDTO> numerosIdent) {
		if (numerosIdent != null && !numerosIdent.isEmpty()) {
			for (NumeroIdentDTO numeroIdentDTO : numerosIdent) {
				if (AgencyConstantValues.IATA_TYPE.equals(numeroIdentDTO.getType())) {
					return numeroIdentDTO.getNumero();
				}
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public List<AgenceDTO> findAllRa2ByCodeVilleIsoWithAnnexTable(String cityCode) throws JrafDomainException {
		List<AgenceDTO> results = new ArrayList<>();
		List<Agence> agencyList = agenceRepository.findAllRa2ByCodeVilleIso(cityCode);

		return agencyList
				.stream()
				.map(agence -> {
					AgenceDTO agenceDTO = new AgenceDTO();
					try {
						AgenceTransform.bo2DtoFullLink(agence, agenceDTO);
					} catch (JrafDomainException e) {
						log.error("unable to map agency");
					}
					return agenceDTO;
				})
				.collect(Collectors.toList());
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, propagation = Propagation.REQUIRES_NEW)
	public void updateAgencesAndLinkedTableForRa2(AgenceDTO agenceDTO, Agence agence,
												  Map<String, Map<String, List<PmZone>>> pmZoneMap) throws JrafDomainException {

		log.info("BEGIN updateAgencesAndLinkedTable");

		// Email
		/*
		 * if (agence.getEmails() != null) {
		 * emailUS.createOrUpdateRa2(agence.getEmails()); }
		 *
		 * // Telecoms if (agence.getTelecoms() != null) {
		 * telecomDS.createOrUpdateRa2(agence.getTelecoms()); }
		 */

		// PmZones
		if (agence.getPmZones() != null && pmZoneMap.get("zv") != null) {

			pmZoneUS.createOrUpdateOrDeleteRa2ZvLinks(pmZoneMap.get("zv"), agence);

		}

		if (agence.getPmZones() != null && pmZoneMap.get("zc") != null) {
			pmZoneUS.createOrUpdateOrDeleteRa2ZcLinks(pmZoneMap.get("zc"), agence);
		}

		// Network
		if (!UList.isNullOrEmpty(agenceDTO.getReseaux()) && !MembreReseauDTO.areEmpty(agenceDTO.getReseaux())) {
			membreReseauDS.checkNetworkLinkInput(agenceDTO, agence);
			List<MembreReseau> listMembreReseau = new ArrayList<>();
			listMembreReseau.addAll(MembreReseauTransform.dto2Bo(agenceDTO.getReseaux()));
			membreReseauUS.createOrUpdateOrDeleteNetworkLink(listMembreReseau, agence);

		}

		// date, site et signature de modification de l'agence
		agence.setDateModification(new Date());
		agence.setSignatureModification(agenceDTO.getSignatureModification());
		agence.setSiteModification(agenceDTO.getSiteModification());

		agenceRepository.saveAndFlush(agence);
		// Fin
		log.info("END updateAgencesAndLinkedTable");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public AgenceDTO updateRa2(AgenceDTO agenceDTO) throws JrafDomainException {
		Optional<Agence> result = agenceRepository.findById(agenceDTO.getGin());
		if (result.isPresent()) {
			log.info("update agency " + agenceDTO.getGin());
			Agence agencyFromDb = result.get();
			if (!CollectionUtils.isEmpty(agenceDTO.getPmZones())) {
				List<PmZone> zcs = preparePmZoneForAgencyUpdate(agenceDTO.getPmZones(), ZoneCommDTO.class);
				List<PmZone> zvs = preparePmZoneForAgencyUpdate(agenceDTO.getPmZones(), ZoneVenteDTO.class);

				if (!CollectionUtils.isEmpty(zcs)) {
					log.info("start update ZC link");
					pmZoneUS.createOrUpdateOrDeleteZcLinks(zcs, agencyFromDb);
					log.info("end update ZC link");
				}
				if (!CollectionUtils.isEmpty(zvs)) {
					log.info("start update ZV link");
					pmZoneUS.createOrUpdateOrDeleteZvLinksV2(zvs, agencyFromDb);
					log.info("end update ZV link");
				}
			}
			agenceRepository.refresh(agencyFromDb);
			AgenceTransform.bo2Dto(agencyFromDb, agenceDTO);
			return agenceDTO;
		}
		return null;
	}

	/**
	 * Soft deletion of an agency: closes all links and sets the agency to Deleted status
	 * @param agencyDto Agency to delete
	 * @return Deleted agency
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = { JrafDomainRollbackException.class, JrafDaoException.class }, noRollbackFor = JrafDomainNoRollbackException.class)
	public AgenceDTO deleteSoftRa1(@NotNull AgenceDTO agencyDto) throws JrafDomainException {
		Optional<Agence> result = agenceRepository.findById(agencyDto.getGin());
		if (result.isPresent()) {
			Agence existingAgency = result.get();

			Date updateDate = new Date();
			String updateSignature = AgencyConstantValues.AGENCY_DELETE_SIGNATURE;
			if (StringUtils.isNotBlank(agencyDto.getSignatureModification())) {
				updateSignature = agencyDto.getSignatureModification();
			}
			String updateSite = AgencyValidationHelper.SITE_QVI;
			if (StringUtils.isNotBlank(agencyDto.getSiteModification())) {
				updateSite = agencyDto.getSiteModification();
			}

			// PERS_MORALE
			existingAgency.setStatut(RefTableREF_STATUTPM._REF_D);
			existingAgency.setDateModificationStatut(updateDate);
			existingAgency.setDateModification(updateDate);
			existingAgency.setSignatureModification(updateSignature);
			existingAgency.setSiteModification(updateSite);
			
			// AGENCE
			if (existingAgency.getDateFin() == null) {				
				existingAgency.setDateFin(updateDate);
			}
			if (existingAgency.getDateAgrement() != null && existingAgency.getDateRadiation() == null) {				
				existingAgency.setDateRadiation(updateDate);
			}
			
			// OFFICE_ID
			if (!CollectionUtils.isEmpty(existingAgency.getOffices())) {
				for (OfficeID officeId : existingAgency.getOffices()) {
					officeId.setDateMaj(updateDate);
					officeId.setSignatureMaj(updateSignature);
					officeId.setSiteMaj(updateSite);
					officeIDRepository.saveAndFlush(officeId);
				}
			}
			
			// NUMERO_IDENT
			if (!CollectionUtils.isEmpty(existingAgency.getNumerosIdent())) {
				for (NumeroIdent numero : existingAgency.getNumerosIdent()) {
					if (numero.getDateFermeture() == null || numero.getDateFermeture().after(updateDate)) {						
						numero.setDateFermeture(updateDate);
						numero.setStatut(RefTableREF_STA_MED._REF_X);
						numero.setDateModification(updateDate);
						numeroIdentRepository.saveAndFlush(numero);
					}
				}
			}
			
			// SEGMENTATION
			if (!CollectionUtils.isEmpty(existingAgency.getSegmentations())) {
				for (Segmentation segmentation : existingAgency.getSegmentations()) {
					if (segmentation.getDateSortie() == null || segmentation.getDateSortie().after(updateDate)) {						
						segmentation.setDateSortie(updateDate);
						segmentationRepository.saveAndFlush(segmentation);
					}
				}
			}

			// TELECOMS
			if (!CollectionUtils.isEmpty(existingAgency.getTelecoms())) {
				for (Telecoms telecom : existingAgency.getTelecoms()) {
					if (RefTableREF_STA_MED._REF_V.equals(telecom.getSstatut_medium())) {						
						telecom.setSstatut_medium(RefTableREF_STA_MED._REF_X);
						telecom.setDdate_modification(updateDate);
						telecom.setSsignature_modification(updateSignature);
						telecom.setSsite_modification(updateSite);
						telecomsRepository.saveAndFlush(telecom);
					}
				}
			}

			// EMAILS
			if (!CollectionUtils.isEmpty(existingAgency.getEmails())) {
				for (Email email : existingAgency.getEmails()) {
					if (RefTableREF_STA_MED._REF_V.equals(email.getStatutMedium())) {						
						email.setStatutMedium(RefTableREF_STA_MED._REF_X);
						email.setDateModification(updateDate);
						email.setSignatureModification(updateSignature);
						email.setSiteModification(updateSite);
						emailRepository.saveAndFlush(email);
					}
				}
			}
			
			// ADR_POST
			if (!CollectionUtils.isEmpty(existingAgency.getPostalAddresses())) {
				for (PostalAddress address : existingAgency.getPostalAddresses()) {
					if (RefTableREF_STA_MED._REF_V.equals(address.getSstatut_medium())) {						
						address.setSstatut_medium(RefTableREF_STA_MED._REF_X);
						address.setDdate_modification(updateDate);
						address.setSsignature_modification(updateSignature);
						address.setSsite_modification(updateSite);
						postalAddressRepository.saveAndFlush(address);
					}
				}
			}
			
			// SYNONYME
			if (!CollectionUtils.isEmpty(existingAgency.getSynonymes())) {
				for (Synonyme synonyme : existingAgency.getSynonymes()) {
					if (RefTableREF_STA_MED._REF_V.equals(synonyme.getStatut())) {						
						synonyme.setStatut(RefTableREF_STA_MED._REF_X);
						synonymeRepository.saveAndFlush(synonyme);
					}
				}
			}

			// PM_ZONE
			if (!CollectionUtils.isEmpty(existingAgency.getPmZones())) {
				for (PmZone pmZone : existingAgency.getPmZones()) {
					if (pmZone.getDateFermeture() == null || pmZone.getDateFermeture().after(updateDate)) {						
						pmZone.setDateFermeture(updateDate);
						pmZone.setDateModif(updateDate);
						pmZone.setSignature(updateSignature);
						pmZoneRepository.saveAndFlush(pmZone);
					}
				}
			}
			
			// MEMBRE_RESEAU
			if (!CollectionUtils.isEmpty(existingAgency.getReseaux())) {
				for (MembreReseau membreReseau : existingAgency.getReseaux()) {
					if (membreReseau.getDateFin() == null || membreReseau.getDateFin().after(updateDate)) {						
						membreReseau.setDateFin(updateDate);
						membreReseauRepository.saveAndFlush(membreReseau);
					}
				}
			}
			
			// GESTION_PM
			if (!CollectionUtils.isEmpty(existingAgency.getPersonnesMoralesGerees())) {
				for (GestionPM gestionPm : existingAgency.getPersonnesMoralesGerees()) {
					if (gestionPm.getDateFinLien() == null || gestionPm.getDateFinLien().after(updateDate)) {						
						gestionPm.setDateFinLien(updateDate);
						gestionPm.setDateMaj(updateDate);
						gestionPm.setSignatureMaj(updateSignature);
						gestionPm.setSiteMaj(updateSite);
						gestionPMRepository.saveAndFlush(gestionPm);
					}
				}
			}
			
			// MEMBRE
			if (!CollectionUtils.isEmpty(existingAgency.getMembres())) {
				for (Membre membre : existingAgency.getMembres()) {
					if (membre.getDateFinValidite() == null || membre.getDateFinValidite().after(updateDate)) {						
						membre.setDateFinValidite(updateDate);
						membre.setDateModification(updateDate);
						membre.setSignatureModification(updateSignature);
						membreRepository.saveAndFlush(membre);
					}
				}
			}
			
			// BSP_DATA
			List<BspData> bsps = bspDataRepository.findByGin(existingAgency.getGin());
			if (!CollectionUtils.isEmpty(bsps)) {
				for (BspData bsp : bsps) {
					bsp.setDateModification(updateDate);
					bsp.setSignatureModification(updateSignature);
					bsp.setSiteModification(updateSite);
					bspDataRepository.saveAndFlush(bsp);
				}
			}

			Agence deleted = agenceRepository.saveAndFlush(existingAgency);
			AgenceTransform.bo2Dto(deleted, agencyDto);
			
			return agencyDto;
		}
		return null;
	}
	
	/**
	 * Hard deletion on an agency
	 * @param agencyToDelete Agency to delete
	 * @return true if deleted successfully, false otherwise
	 * @throws JrafDaoException
	 * @throws NotFoundException 
	 * @throws AgencyNotFoundException 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void deleteHardRa1(AgenceDTO agencyToDelete) throws JrafDaoException, AgencyNotFoundException {
		if (StringUtils.isBlank(agencyToDelete.getGin())) {
			throw new AgencyNotFoundException("Agency GIN not found");
		}
		
		personneMoraleRepository.deleteCascadeByGin(agencyToDelete.getGin());
	}


}
