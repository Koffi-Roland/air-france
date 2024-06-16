package com.airfrance.repind.service.firm.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.firme.EntrepriseRepository;
import com.airfrance.repind.dao.firme.EtablissementRepository;
import com.airfrance.repind.dao.firme.SelfBookingToolRepository;
import com.airfrance.repind.dao.profil.ProfilFirmeRepository;
import com.airfrance.repind.dao.zone.ZoneCommRepository;
import com.airfrance.repind.dto.adresse.*;
import com.airfrance.repind.dto.firme.*;
import com.airfrance.repind.dto.profil.ProfilFirmeTransform;
import com.airfrance.repind.dto.zone.PmZoneDTO;
import com.airfrance.repind.dto.zone.PmZoneTransform;
import com.airfrance.repind.dto.zone.ZoneCommDTO;
import com.airfrance.repind.dto.zone.ZoneVenteDTO;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.firme.*;
import com.airfrance.repind.entity.profil.ProfilFirme;
import com.airfrance.repind.entity.zone.PmZone;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.internal.unitservice.adresse.EmailUS;
import com.airfrance.repind.service.internal.unitservice.adresse.PostalAddressUS;
import com.airfrance.repind.service.internal.unitservice.adresse.TelecomUS;
import com.airfrance.repind.service.internal.unitservice.firm.*;
import com.airfrance.repind.service.internal.unitservice.profil.ProfilFirmeUS;
import com.airfrance.repind.service.internal.unitservice.zone.PmZoneUS;
import com.airfrance.repind.service.internal.unitservice.zone.ZoneCommUS;
import com.airfrance.repind.service.internal.unitservice.zone.ZoneVenteUS;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class EtablissementDS {

    /** logger */
    private static final Log log = LogFactory.getLog(EtablissementDS.class);

    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

    /** unit service : ServiceDS **/
    @Autowired
    private ServiceDS serviceDS;
    /** unit service : ChiffreUS **/
    @Autowired
    private ChiffreUS chiffreUS;
    /** unit service : EmailUS **/
    @Autowired
    private EmailUS emailUS;
    /** unit service : EntrepriseUS **/
    @Autowired
    private EntrepriseUS entrepriseUS;
    /** unit service : EtablissementUS **/
    @Autowired
    private EtablissementUS etablissementUS;
    /** unit service : GestionPMUS **/
    @Autowired
    private GestionPMUS gestionPMUS;
    /** unit service : NumeroIdentUS **/
    @Autowired
    private NumeroIdentUS numeroIdentUS;
    /** unit service : PersonneMoraleUS **/
    @Autowired
    private PersonneMoraleUS personneMoraleUS;
    /** unit service : PmZoneUS **/
    @Autowired
    private PmZoneUS pmZoneUS;
    /** unit service : PostalAddressUS **/
    @Autowired
    private PostalAddressUS postalAddressUS;
    /** unit service : ProfilFirmeUS **/
    @Autowired
    private ProfilFirmeUS profilFirmeUS;
    /** unit service : SegmentationUS **/
    @Autowired
    private SegmentationUS segmentationUS;
    /** unit service : SelfBookingToolUS **/
    @Autowired
    private SelfBookingToolUS selfBookingToolUS;
    /** unit service : ServiceUS **/
    @Autowired
    private ServiceUS serviceUS;
    /** unit service : SynonymeUS **/
    @Autowired
    private SynonymeUS synonymeUS;
    /** unit service : TelecomUS **/
    @Autowired
    private TelecomUS telecomUS;
    /** unit service : ZoneCommUS **/
    @Autowired
    private ZoneCommUS zoneCommUS;
    /** unit service : ZoneVenteUS **/
    @Autowired
    private ZoneVenteUS zoneVenteUS;

    /** main dao */
    @Autowired
    private EtablissementRepository etablissementRepository;
    /** references on associated DAOs */
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    /** references on associated DAOs */
    @Autowired
    private ProfilFirmeRepository profilFirmeRepository;
    /** references on associated DAOs */
    @Autowired
    private SelfBookingToolRepository selfBookingToolRepository;
    /** references on associated DAOs */
    @Autowired
    private ZoneCommRepository zoneCommRepository;

    /*PROTECTED REGION ID(_zkmRoOj-EeCMIaqimW3ZtA u var) ENABLED START*/
    // add your custom variables here if necessary
    
    /** visa key **/
    private final String VISA_TOKEN = "VisaKeySiC2";
	
    /** domain service : TelecomDS **/
    @Autowired
    private TelecomDS telecomDS;

    @Transactional(readOnly=true)
    public Integer countWhere(EtablissementDTO dto) throws JrafDomainException {        
		Etablissement etablissement = EtablissementTransform.dto2BoLight(dto);
		return (int) etablissementRepository.count(Example.of(etablissement));
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(EtablissementDTO etablissementDTO) throws JrafDomainException {
        throw new UnsupportedOperationException();
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(EtablissementDTO dto) throws JrafDomainException {
        Etablissement etablissement = EtablissementTransform.dto2BoLight(dto);
        etablissementRepository.delete(etablissement);
    }
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(String oid) throws JrafDomainException {
    	etablissementRepository.deleteById(oid);
    }
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void update(EtablissementDTO etablissementDTO) throws JrafDomainException {
        update(etablissementDTO, true, true);
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, propagation = Propagation.REQUIRES_NEW)
    public void updateFromBatch(EtablissementDTO etablissementDTO) throws JrafDomainException {
    	updateFromBatch(etablissementDTO, true, true);
    }
    
    @Transactional(readOnly=true)
    public List<EtablissementDTO> findAll() throws JrafDomainException {
		List<EtablissementDTO> results = new ArrayList<>();
		for(Etablissement found : etablissementRepository.findAll()) {
			results.add(EtablissementTransform.bo2DtoLight(found));
		}
		return results;
    }

    @Transactional(readOnly=true)
    public Integer count() throws JrafDomainException {
    	return (int) etablissementRepository.count();
    }

    @Transactional(readOnly=true)
    public List<EtablissementDTO> findByExample(EtablissementDTO dto) throws JrafDomainException {
        Etablissement etablissement = EtablissementTransform.dto2BoLight(dto);
        List<EtablissementDTO> result = new ArrayList<>();
        for (Etablissement found : etablissementRepository.findAll(Example.of(etablissement))) {
			result.add(EtablissementTransform.bo2DtoLight(found));
		}
		return result;
    }

    @Transactional(readOnly=true)
    public EtablissementDTO get(EtablissementDTO dto) throws JrafDomainException {
        Optional<Etablissement> etablissement = etablissementRepository.findById(dto.getGin());
        
        if (!etablissement.isPresent()) {
            return null;
        }
        return EtablissementTransform.bo2DtoLight(etablissement.get());
    }

    @Transactional(readOnly=true)
    public EtablissementDTO get(String oid) throws JrafDomainException {
        Optional<Etablissement> etablissement = etablissementRepository.findById(oid);
        
        if (!etablissement.isPresent()) {
            return null;
        }
        return EtablissementTransform.bo2DtoLight(etablissement.get());
    }
    
    //This method add missing segmentation value in return
    @Transactional(readOnly=true)
    public EtablissementDTO getForBatch(String oid) throws JrafDomainException {
        Optional<Etablissement> etablissement = etablissementRepository.findById(oid);
        
        if (!etablissement.isPresent()) {
            return null;
        }
        return EtablissementTransform.bo2DtoLightForBatch(etablissement.get());
    }
    
    @Transactional(readOnly=true)
    public EtablissementDTO findBySiret(String siret) throws JrafDomainException {
        Optional<Etablissement> etablissement = Optional.ofNullable(etablissementRepository.findUniqueBySiret(siret));
        
        if (!etablissement.isPresent()) {
            return null;
        }
        return EtablissementTransform.bo2DtoLight(etablissement.get());
    }
    
    public EtablissementRepository getEtablissementRepository() {
		return etablissementRepository;
	}

	public void setEtablissementRepository(EtablissementRepository etablissementRepository) {
		this.etablissementRepository = etablissementRepository;
	}

	public ProfilFirmeRepository getProfilFirmeRepository() {
		return profilFirmeRepository;
	}

	public void setProfilFirmeRepository(ProfilFirmeRepository profilFirmeRepository) {
		this.profilFirmeRepository = profilFirmeRepository;
	}

    public SelfBookingToolRepository getSelfBookingToolRepository() {
		return selfBookingToolRepository;
	}

	public void setSelfBookingToolRepository(SelfBookingToolRepository selfBookingToolRepository) {
		this.selfBookingToolRepository = selfBookingToolRepository;
	}


    /**
     * Getter
     * @return IChiffreUS
     */
    public ChiffreUS getChiffreUS() {
        return chiffreUS;
    }

    /**
     * Setter
     * @param chiffreUS the IChiffreUS 
     */
    public void setChiffreUS(ChiffreUS chiffreUS) {
        this.chiffreUS = chiffreUS;
    }
    /**
     * Getter
     * @return IEmailUS
     */
    public EmailUS getEmailUS() {
        return emailUS;
    }

    /**
     * Setter
     * @param emailUS the IEmailUS 
     */
    public void setEmailUS(EmailUS emailUS) {
        this.emailUS = emailUS;
    }
    /**
     * Getter
     * @return IEntrepriseUS
     */
    public EntrepriseUS getEntrepriseUS() {
        return entrepriseUS;
    }

    /**
     * Setter
     * @param entrepriseUS the IEntrepriseUS 
     */
    public void setEntrepriseUS(EntrepriseUS entrepriseUS) {
        this.entrepriseUS = entrepriseUS;
    }
    /**
     * Getter
     * @return IEtablissementUS
     */
    public EtablissementUS getEtablissementUS() {
        return etablissementUS;
    }

    /**
     * Setter
     * @param etablissementUS the IEtablissementUS 
     */
    public void setEtablissementUS(EtablissementUS etablissementUS) {
        this.etablissementUS = etablissementUS;
    }
    /**
     * Getter
     * @return IGestionPMUS
     */
    public GestionPMUS getGestionPMUS() {
        return gestionPMUS;
    }

    /**
     * Setter
     * @param gestionPMUS the IGestionPMUS 
     */
    public void setGestionPMUS(GestionPMUS gestionPMUS) {
        this.gestionPMUS = gestionPMUS;
    }
    /**
     * Getter
     * @return INumeroIdentUS
     */
    public NumeroIdentUS getNumeroIdentUS() {
        return numeroIdentUS;
    }

    /**
     * Setter
     * @param numeroIdentUS the INumeroIdentUS 
     */
    public void setNumeroIdentUS(NumeroIdentUS numeroIdentUS) {
        this.numeroIdentUS = numeroIdentUS;
    }
    /**
     * Getter
     * @return IPersonneMoraleUS
     */
    public PersonneMoraleUS getPersonneMoraleUS() {
        return personneMoraleUS;
    }

    /**
     * Setter
     * @param personneMoraleUS the IPersonneMoraleUS 
     */
    public void setPersonneMoraleUS(PersonneMoraleUS personneMoraleUS) {
        this.personneMoraleUS = personneMoraleUS;
    }
    /**
     * Getter
     * @return IPmZoneUS
     */
    public PmZoneUS getPmZoneUS() {
        return pmZoneUS;
    }

    /**
     * Setter
     * @param pmZoneUS the IPmZoneUS 
     */
    public void setPmZoneUS(PmZoneUS pmZoneUS) {
        this.pmZoneUS = pmZoneUS;
    }
    /**
     * Getter
     * @return IPostalAddressUS
     */
    public PostalAddressUS getPostalAddressUS() {
        return postalAddressUS;
    }

    /**
     * Setter
     * @param postalAddressUS the IPostalAddressUS 
     */
    public void setPostalAddressUS(PostalAddressUS postalAddressUS) {
        this.postalAddressUS = postalAddressUS;
    }
    /**
     * Getter
     * @return IProfilFirmeUS
     */
    public ProfilFirmeUS getProfilFirmeUS() {
        return profilFirmeUS;
    }

    /**
     * Setter
     * @param profilFirmeUS the IProfilFirmeUS 
     */
    public void setProfilFirmeUS(ProfilFirmeUS profilFirmeUS) {
        this.profilFirmeUS = profilFirmeUS;
    }
    /**
     * Getter
     * @return ISegmentationUS
     */
    public SegmentationUS getSegmentationUS() {
        return segmentationUS;
    }

    /**
     * Setter
     * @param segmentationUS the ISegmentationUS 
     */
    public void setSegmentationUS(SegmentationUS segmentationUS) {
        this.segmentationUS = segmentationUS;
    }
    /**
     * Getter
     * @return ISelfBookingToolUS
     */
    public SelfBookingToolUS getSelfBookingToolUS() {
        return selfBookingToolUS;
    }

    /**
     * Setter
     * @param selfBookingToolUS the ISelfBookingToolUS 
     */
    public void setSelfBookingToolUS(SelfBookingToolUS selfBookingToolUS) {
        this.selfBookingToolUS = selfBookingToolUS;
    }
    /**
     * Getter
     * @return IServiceUS
     */
    public ServiceUS getServiceUS() {
        return serviceUS;
    }

    /**
     * Setter
     * @param serviceUS the IServiceUS 
     */
    public void setServiceUS(ServiceUS serviceUS) {
        this.serviceUS = serviceUS;
    }
    /**
     * Getter
     * @return ISynonymeUS
     */
    public SynonymeUS getSynonymeUS() {
        return synonymeUS;
    }

    /**
     * Setter
     * @param synonymeUS the ISynonymeUS 
     */
    public void setSynonymeUS(SynonymeUS synonymeUS) {
        this.synonymeUS = synonymeUS;
    }
    /**
     * Getter
     * @return ITelecomUS
     */
    public TelecomUS getTelecomUS() {
        return telecomUS;
    }

    /**
     * Setter
     * @param telecomUS the ITelecomUS 
     */
    public void setTelecomUS(TelecomUS telecomUS) {
        this.telecomUS = telecomUS;
    }
    /**
     * Getter
     * @return IZoneCommUS
     */
    public ZoneCommUS getZoneCommUS() {
        return zoneCommUS;
    }

    /**
     * Setter
     * @param zoneCommUS the IZoneCommUS 
     */
    public void setZoneCommUS(ZoneCommUS zoneCommUS) {
        this.zoneCommUS = zoneCommUS;
    }
    /**
     * Getter
     * @return IZoneVenteUS
     */
    public ZoneVenteUS getZoneVenteUS() {
        return zoneVenteUS;
    }

    /**
     * Setter
     * @param zoneVenteUS the IZoneVenteUS 
     */
    public void setZoneVenteUS(ZoneVenteUS zoneVenteUS) {
        this.zoneVenteUS = zoneVenteUS;
    }
    /**
     * Getter
     * @return IServiceDS
     */
    public ServiceDS getServiceDS() {
        return serviceDS;
    }

    /**
     * Setter
     * @param serviceDS the IServiceDS 
     */
    public void setServiceDS(ServiceDS serviceDS) {
        this.serviceDS = serviceDS;
    }

    /**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_zkmRoOj-EeCMIaqimW3ZtAgem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /*PROTECTED REGION ID(_zkmRoOj-EeCMIaqimW3ZtA u m) ENABLED START*/
    // add your custom methods here if necessary

    // TODO MBE pour utiliser JPA2.0 Criteria, il faut hibernate-jpa-2.0-api-1.0.0.Final.jar ce qui n�cessite de passer � une version Hibernate >= 3.6.0
    /*
     * private List<Etablissement> findAllJpa2Criteria() throws JrafDomainException {
     * 
     * List<Etablissement> boFounds = null; System.out.println("test JPA2.0 Criteria"); CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder(); CriteriaQuery<Etablissement>
     * cqEtablissement = criteriaBuilder.createQuery(Etablissement.class); Root<Etablissement> etablissementRoot = cqEtablissement(Etablissement.class); cqEtablissement.select(etablissementRoot);
     * TypedQuery<Escale> typeQuery = entityManager.createQuery(cqEtablissement); boFounds = typeQuery.getResultList(); return boFounds; }
     */

    /**
     * Getter
     * @return ITelecomDS
     */
    public TelecomDS getTelecomDS() {
        return telecomDS;
    }
    
    /**
     * Setter
     * @param telecomDS the ITelecomDS 
     */
    public void setTelecomDS(TelecomDS telecomDS) {
        this.telecomDS = telecomDS;
    }
	
    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(EtablissementDTO etablissementDTO, String pToken, String pCodeMetier) throws JrafDomainException {

        log.info("BEGIN create");
        
        Etablissement etablissement = null;

        // transformation light dto -> bo
        etablissement = EtablissementTransform.dto2BoLight(etablissementDTO);
                
        Date now = new Date();
        etablissement.setDateCreation(now);
        
        // transformation dto -> bo
        //--------------------------        
        
        // transformation parent dto -> bo
        if (etablissementDTO.getParent() != null) {
        	etablissement.setParent(EntrepriseTransform.dto2BoLight((EntrepriseDTO)etablissementDTO.getParent()));
        }       
        
        // Check status 
        etablissementUS.checkStatutWhenCreating(etablissement);
        
        // Check nom
        etablissementUS.checkNom(etablissement);

        // Check type etablissement
        etablissementUS.checkType(etablissement);
        
        // Check code source
        etablissementUS.checkCodeSource(etablissement);
        
        // Check code support
        etablissementUS.checkCodeSupport(etablissement);
        
        // Check siege social
        etablissementUS.checkSiegeSocial(etablissement);
        
        // Check activite locale
        etablissementUS.checkActivitetLocale(etablissement);
        
        // Check code industrie 
        etablissementUS.checkCodeIndus(etablissement);
        
        // Check Statut juridique
        etablissementUS.checkStatutJuridique(etablissement);
        
        // Check type demarchage
        etablissementUS.checkTypeDemarchage(etablissement);
        
        // RG - hi�rarchie
        //-----------------
        Entreprise entrepriseToCreate = null;
        if (etablissement.getParent() == null) {            
            
            Entreprise entrepriseParente = null;
            
            // le siret est fourni
            if(!StringUtils.isEmpty(etablissement.getSiret())) {
                
            	// Check minimal du SIRET
            	if (etablissement.getSiret().length() < 9)
            		throw new JrafDomainRollbackException("170");
            	
                // on deduit le siren
                String sirenDeduit = etablissement.getSiret().substring(0, 9);                
                
                // on recherche en base une entreprise ayant ce siren
                entrepriseParente = entrepriseRepository.findAnyBySiren(sirenDeduit);
            }
            
            if (entrepriseParente == null) {
                
                // on genere l'entreprise parente
                entrepriseParente = entrepriseUS.newParent(etablissement);
                entrepriseToCreate = entrepriseParente;
            }
            
            // liaison avec l'etablissement
            etablissement.setParent(entrepriseParente);
            
        } else {
            
            // un parent est fourni
            entrepriseUS.checkParent(etablissement);
        }
        
        // RG - traduction phonetique de la marque commerciale
        //-----------------------------------------------------
        etablissement.setIndictNom(null);       // pour le moment, on n'alimente plus ce champ car il faut remettre � plat
        
        // Fin
        // ----
        etablissement.setGin(null);
        etablissement.setVersion(null);
        
        // On renseigne les champs xxxModification a cause de la replication
        etablissement.setDateModification(now);
        etablissement.setSiteModification(etablissement.getSiteCreation());
        etablissement.setSignatureModification(etablissement.getSignatureCreation());
        
        // Creation en base
        // -----------------
        
        // creation de l'entreprise parente si n�cessaire
        if (entrepriseToCreate != null) {
            
            // creation de l'entreprise
            entrepriseToCreate = entrepriseRepository.saveAndFlush(entrepriseToCreate);
            
            // creation des synonymes
            synonymeUS.createOrUpdateOrDelete(new ArrayList<Synonyme>(), entrepriseToCreate);
        }

        // creation de l'etablissement
        etablissementRepository.saveAndFlush(etablissement);
        
        // transformation profilFirme dto -> bo
        if (etablissementDTO.getProfilFirme() != null) {
        	ProfilFirme profilFirme = ProfilFirmeTransform.dto2BoLight(etablissementDTO.getProfilFirme());
        	profilFirmeUS.check(profilFirme, etablissement);
        }
        
        // transformation selfBookingTool dto -> bo
        // SelfBookingTool
        if (etablissementDTO.getSelfBookingTool() != null) {
        	SelfBookingTool selfBookingTool = SelfBookingToolTransform.dto2BoLight(etablissementDTO.getSelfBookingTool());
        	selfBookingToolUS.check(selfBookingTool, etablissement);
        }   
        
        // creation des synonymes
        List<Synonyme> synonymesToCreate = new ArrayList<>();
        if (!CollectionUtils.isEmpty(etablissementDTO.getSynonymes())) {            
            for (SynonymeDTO dto : etablissementDTO.getSynonymes()) {                
                if (dto != null) {                    
                    synonymesToCreate.add(SynonymeTransform.dto2BoLight(dto));
                }
            }
        }       
        synonymeUS.createOrUpdateOrDelete(synonymesToCreate, etablissement);

        // creation des adresses postales
        List<PostalAddress> postalAddressesToCreate = new ArrayList<>();
        if (!CollectionUtils.isEmpty(etablissementDTO.getPostalAddresses())) {            
            for (PostalAddressDTO dto : etablissementDTO.getPostalAddresses()) {                
                if (dto != null) {                    
                    postalAddressesToCreate.add(PostalAddressTransform.dto2BoLight(dto));
                }
            }
        }       
        postalAddressUS.createOrUpdateOrDelete(postalAddressesToCreate, etablissement);

        // creation des emails
        List<Email> emailsToCreate = new ArrayList<>();
        if (!CollectionUtils.isEmpty(etablissementDTO.getEmails())) {            
            for (EmailDTO dto : etablissementDTO.getEmails()) {
				if (dto != null) {
					emailsToCreate.add(EmailTransform.dto2BoLight(dto));
                }
            }
        }
        emailUS.createOrUpdateOrDelete(emailsToCreate, etablissement);
        
	    // cr�ation des numeros d'identification
        List<NumeroIdent> numerosIdentToCreate = new ArrayList<>();
        if (!CollectionUtils.isEmpty(etablissementDTO.getNumerosIdent())) {            
            for (NumeroIdentDTO dto : etablissementDTO.getNumerosIdent()) {                
                if (dto != null) {                    
                    numerosIdentToCreate.add(NumeroIdentTransform.dto2BoLight(dto));
                }
            }
        }
        numeroIdentUS.createOrUpdateOrDelete(numerosIdentToCreate, etablissement);

        // creation des segmentations
        List<Segmentation> segmentationsToCreate = new ArrayList<>();
        if (!CollectionUtils.isEmpty(etablissementDTO.getSegmentations())) {            
            for (SegmentationDTO dto : etablissementDTO.getSegmentations()) {                
                if (dto != null) {                    
                    segmentationsToCreate.add(SegmentationTransform.dto2BoLight(dto));
                }
            }
        }          
        segmentationUS.createOrUpdateOrDelete(segmentationsToCreate, etablissement);

        // creation des telecoms
        List<Telecoms> telecomsToCreate = new ArrayList<>();
        if (!CollectionUtils.isEmpty(etablissementDTO.getTelecoms())) {            
            for (TelecomsDTO dto : etablissementDTO.getTelecoms()) {
				if (dto != null) {
					telecomsToCreate.add(TelecomsTransform.dto2BoLight(dto));
                }
            }
        }
        telecomDS.createOrUpdateOrDelete(telecomsToCreate, etablissement);        
        
        // creation des chiffres
        List<Chiffre> chiffresToCreate = new ArrayList<>();
        if (!CollectionUtils.isEmpty(etablissementDTO.getChiffres())) {            
            for (ChiffreDTO dto : etablissementDTO.getChiffres()) {                
                if (dto != null) {                    
                    chiffresToCreate.add(ChiffreTransform.dto2BoLight(dto));
            	}
            }
        }
        chiffreUS.createOrUpdateOrDelete(chiffresToCreate, etablissement);

        // creation des agences li�es
        List<GestionPM> gestionPMsToCreate = new ArrayList<>();
        if (!CollectionUtils.isEmpty(etablissementDTO.getPersonnesMoralesGerantes())) {            
            for (GestionPMDTO dto : etablissementDTO.getPersonnesMoralesGerantes()) {                
                if (dto != null) {                    
                    gestionPMsToCreate.add(GestionPMTransform.dto2Bo(dto));
            	}
            }
        }
        gestionPMUS.createOrUpdateOrDelete(gestionPMsToCreate, etablissement);

        // check Token Visa
        String sCodePays = getCodeCountyValidLocalizationPostalAddress(postalAddressesToCreate);

        if (!isValidToken(pToken, etablissement.getNom(), 
                		"T", sCodePays, pCodeMetier ))
        	// Erreur 277 - VISA DURATION EXPIRED
            throw new JrafDomainRollbackException("277");
        
        // Version update and Id update if needed
        //----------------------------------------
        
        etablissementRepository.refresh(etablissement);
        
        // Check after refresh to get BO up to date
        
        // PMzone => ZoneComm et ZoneVente
        List<PmZone> gestionZoneCommsToCreate = new ArrayList<>();
        List<PmZone> gestionZoneVentesToCreate = new ArrayList<>();
        if (!CollectionUtils.isEmpty(etablissementDTO.getPmZones())) {            
            for (PmZoneDTO dto : etablissementDTO.getPmZones()) {                
                if (dto != null && dto.getZoneDecoup() != null) {
                	if (ZoneCommDTO.class.equals(dto.getZoneDecoup().getClass()))
                		gestionZoneCommsToCreate.add(PmZoneTransform.dto2Bo(dto));
                	else if (ZoneVenteDTO.class.equals(dto.getZoneDecoup().getClass()))
                		gestionZoneVentesToCreate.add(PmZoneTransform.dto2Bo(dto));
                	else
                		// TODO MBE Peut-on avoir des zones financieres par exemple ?
                        throw new UnsupportedOperationException();
            	}
            }
        }
        // ZoneComm
        pmZoneUS.createOrUpdateOrDeleteZcLinks(gestionZoneCommsToCreate, etablissement);
        // ZoneVente
        pmZoneUS.createOrUpdateOrDeleteZvLinks(gestionZoneVentesToCreate, etablissement);
        
        // RG - siret
        //------------
        etablissementUS.checkSiret(etablissement);
        
        // TODO CEP/MBE :  Voir si on peut faire autrement que de relire une nouvelle fois le BO
        etablissementRepository.refresh(etablissement);
        
        // Bo2Dto Etablissement
        EtablissementTransform.bo2DtoLight(etablissement, etablissementDTO);
        
        // Bo2Dto Parent
        etablissementDTO.setParent(null);
        if (etablissement.getParent() != null) {
            etablissementDTO.setParent(PersonneMoraleTransform.bo2DtoLight(etablissement.getParent()));
        }
        
        // Bo2Dto ProfilFirme
        etablissementDTO.setProfilFirme(null);
        if (etablissement.getProfilFirme() != null) {

            etablissementDTO.setProfilFirme(ProfilFirmeTransform.bo2DtoLight(etablissement.getProfilFirme()));            
        }
        
        // Bo2Dto SelfBookingTool
        etablissementDTO.setSelfBookingTool(null);
        if (etablissement.getSelfBookingTool() != null) {

            etablissementDTO.setSelfBookingTool(SelfBookingToolTransform.bo2DtoLight(etablissement.getSelfBookingTool()));            
        }          
        
        // Bo2Dto Synonymes
        etablissementDTO.setSynonymes(new HashSet<SynonymeDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getSynonymes())) {
            
            for (Synonyme synonyme : etablissement.getSynonymes()) {                
                etablissementDTO.getSynonymes().add(SynonymeTransform.bo2DtoLight(synonyme));      
            }
        }

        // Bo2Dto PostalAddress
        etablissementDTO.setPostalAddresses(new ArrayList<PostalAddressDTO>()); 
        if (!CollectionUtils.isEmpty(etablissement.getPostalAddresses())) {
            
            for (PostalAddress postalAddress : etablissement.getPostalAddresses()) {
		etablissementDTO.getPostalAddresses().add(PostalAddressTransform.bo2DtoLight(postalAddress));
            }
        }
        
        // Bo2Dto Emails
        etablissementDTO.setEmails(new HashSet<EmailDTO>()); 
        if (!CollectionUtils.isEmpty(etablissement.getEmails())) {

            for (Email email : etablissement.getEmails()) {                
        	etablissementDTO.getEmails().add(EmailTransform.bo2DtoLight(email));      
            }
        }        
        
        // Bo2Dto Chiffres
        etablissementDTO.setChiffres(new HashSet<ChiffreDTO>());
        if (etablissementDTO.getChiffres() != null) {
            
            for (Chiffre chiffre : etablissement.getChiffres()) {
                etablissementDTO.getChiffres().add(ChiffreTransform.bo2DtoLight(chiffre));
            }
        }
        
        // Bo2Dto Segmentation
        etablissementDTO.setSegmentations(new HashSet<SegmentationDTO>()); 
        if (etablissementDTO.getSegmentations() != null) {
            
            for (Segmentation segmentation : etablissement.getSegmentations()) {
                etablissementDTO.getSegmentations().add(SegmentationTransform.bo2DtoLight(segmentation));
            }
        }    
		
        // Bo2Dto Telecoms
        etablissementDTO.setTelecoms(new HashSet<TelecomsDTO>()); 
        if (!CollectionUtils.isEmpty(etablissement.getTelecoms())) {

            for (Telecoms telecom : etablissement.getTelecoms()) {                
        	etablissementDTO.getTelecoms().add(TelecomsTransform.bo2DtoLight(telecom));      
            }
        } 
        
        // Bo2Dto NumIdent
        etablissementDTO.setNumerosIdent(new HashSet<NumeroIdentDTO>()); 
        if (etablissementDTO.getNumerosIdent() != null) {

            for (NumeroIdent numero : etablissement.getNumerosIdent()) {
                etablissementDTO.getNumerosIdent().add(NumeroIdentTransform.bo2DtoLight(numero));
            }
        }        
        
        // Bo2Dto GestionPMs
        etablissementDTO.setPersonnesMoralesGerantes(new HashSet<GestionPMDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getPersonnesMoralesGerantes())) {
            for (GestionPM gestionPM : etablissement.getPersonnesMoralesGerantes()) {
                etablissementDTO.getPersonnesMoralesGerantes().add(GestionPMTransform.bo2DtoLight(gestionPM));
            }
        }    
        
        // Bo2Dto PmZone
        etablissementDTO.setPmZones(new HashSet<PmZoneDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getPmZones())) {
            for (PmZone pmZone : etablissement.getPmZones()) {
                etablissementDTO.getPmZones().add(PmZoneTransform.bo2Dto(pmZone));
            }
        }          
        
        log.info("END create");
    }

    
    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void updateFromBatch(EtablissementDTO etablissementDTO, boolean pUpdateIdentification, boolean pUpdateFirm) throws JrafDomainException {
    	log.info("BEGIN update");
        
        Assert.notNull(etablissementDTO.getGin(), "gin is mandatory");
        Assert.notNull(etablissementDTO.getVersion(), "version is mandatory");
        
        log.info("gin = " + etablissementDTO.getGin());
        log.info("version = " + etablissementDTO.getVersion());
        
        Etablissement etablissement = null;
        
        Optional<Etablissement> result = etablissementRepository.findById(etablissementDTO.getGin());
        
        if (result.isPresent()) {
        	etablissement = result.get();
        }

        // On verifie que l'id fourni retourne bien un etablissement
        if (etablissement == null) {

            throw new JrafDomainRollbackException("001");       // NOT FOUND
        }
        
        // On v�rifie que la version en entree coencide avec la version stock�e
        else if (!etablissement.getVersion().equals(etablissementDTO.getVersion())) {

            throw new JrafDomainRollbackException("003");       // SIMULTANEOUS UPDATE
        }

        // Mise a jour des proprietes light de PersonneMorale
        boolean businessNameHasChanged = false;
        boolean statusHasChanged = false;
        // Historique des statuts de la PM utilise pour les PMZones
        String oldStatut = etablissement.getStatut();
        String newStatut = etablissementDTO.getStatut();
        Date now = new Date();
        if (pUpdateIdentification) {
        	
            // Check status 
            statusHasChanged = !oldStatut.equals(newStatut);
            etablissement.setStatut(etablissementDTO.getStatut());
            etablissementUS.checkStatutWhenUpdating(etablissement);
            
            // Check nom
            businessNameHasChanged = !etablissement.getNom().equals(etablissementDTO.getNom());
            etablissement.setNom(etablissementDTO.getNom());
            etablissementUS.checkNom(etablissement);

            // Check activite locale
            etablissement.setActiviteLocal(etablissementDTO.getActiviteLocal());
            etablissementUS.checkActivitetLocale(etablissement);
            
            // Check code industrie 
            etablissement.setCodeIndustrie(etablissementDTO.getCodeIndustrie());
            etablissementUS.checkCodeIndus(etablissement);
            
            // Check type demarchage
            etablissement.setTypeDemarchage(etablissementDTO.getTypeDemarchage());
            etablissementUS.checkTypeDemarchage(etablissement);
            
            // Check Statut juridique
            etablissement.setStatutJuridique(etablissementDTO.getStatutJuridique());
            etablissementUS.checkStatutJuridique(etablissement);

            PersonneMoraleTransform.dto2BoLight(etablissementDTO, etablissement);
        }
        if (businessNameHasChanged) {
            etablissement.setDateModificationNom(now);
        }
        if (statusHasChanged) {
            etablissement.setDateModificationStatut(now);
            
        }
        
        etablissement.setDateModification(now);
        etablissement.setSignatureModification(etablissementDTO.getSignatureModification());
        etablissement.setSiteModification(etablissementDTO.getSiteModification());
        
        // Mise � jour des proprietes de Etablissement
        if (pUpdateFirm) {

            // Check type etablissement
            etablissement.setType(etablissementDTO.getType());
            etablissementUS.checkType(etablissement);
            
            // Check siege social
            etablissement.setSiegeSocial(etablissementDTO.getSiegeSocial());
            etablissementUS.checkSiegeSocial(etablissement);

            etablissement.setSiret(etablissementDTO.getSiret());
            etablissementUS.checkSiret(etablissement);
            
            etablissement.setCe(etablissementDTO.getCe());
            etablissement.setRem(etablissementDTO.getRem());
        }
        
        // ProfilFirme
        if (etablissementDTO.getProfilFirme() != null) {
            ProfilFirme profilFirme = ProfilFirmeTransform.dto2BoLight(etablissementDTO.getProfilFirme());
        	profilFirmeUS.check(profilFirme, etablissement);
        }
        // SelfBookingTool
        if (etablissementDTO.getSelfBookingTool() != null) {
        	SelfBookingTool selfBookingTool = SelfBookingToolTransform.dto2BoLight(etablissementDTO.getSelfBookingTool());
        	selfBookingToolUS.check(selfBookingTool, etablissement);
        }
             
        // Chiffres
        if (etablissementDTO.getChiffres() != null) {
                        
            List<Chiffre> chiffresToCreateOrUpdateOrDelete = new ArrayList<>();
            for (ChiffreDTO chiffreDTO : etablissementDTO.getChiffres()) {
            	chiffresToCreateOrUpdateOrDelete.add(ChiffreTransform.dto2BoLight(chiffreDTO));
            }
            chiffreUS.createOrUpdateOrDelete(chiffresToCreateOrUpdateOrDelete, etablissement);
        }                

        // Key Number / Numero Ident
        //We didn't modify NumerosIdent
        /*if (etablissementDTO.getNumerosIdent() != null) {

            List<NumeroIdent> numerosIdentToCreateOrUpdateOrDelete = new ArrayList<>();
            for (NumeroIdentDTO numeroIdentDTO : etablissementDTO.getNumerosIdent()) {
            	numerosIdentToCreateOrUpdateOrDelete.add(NumeroIdentTransform.dto2BoLight(numeroIdentDTO));
            }
            numeroIdentUS.createOrUpdateOrDelete(numerosIdentToCreateOrUpdateOrDelete, etablissement);
        }*/
        
        // Segmentation
        if (etablissementDTO.getSegmentations() != null) {
            
            List<Segmentation> segmentationsToCreateOrUpdateOrDelete = new ArrayList<>();
            for (SegmentationDTO segmentationDTO : etablissementDTO.getSegmentations()) {
            	segmentationsToCreateOrUpdateOrDelete.add(SegmentationTransform.dto2BoLight(segmentationDTO));
            }
            segmentationUS.createOrUpdateOrDeleteFromBatch(segmentationsToCreateOrUpdateOrDelete, etablissement);
        }

     // Telecoms
        if (etablissementDTO.getTelecoms() != null) {

            List<Telecoms> telecomsToCreateOrUpdateOrDelete = new ArrayList<>();
            for (TelecomsDTO telecomDTO : etablissementDTO.getTelecoms()) {
            	telecomsToCreateOrUpdateOrDelete.add(TelecomsTransform.dto2BoLight(telecomDTO));
            }
            telecomDS.createOrUpdateOrDelete(telecomsToCreateOrUpdateOrDelete, etablissement);
        }

        // GestionPMs
        if (etablissementDTO.getPersonnesMoralesGerantes() != null) {
                        
            List<GestionPM> gestionPMsToCreateOrUpdateOrDelete = new ArrayList<>();
            for (GestionPMDTO gestionPMDTO : etablissementDTO.getPersonnesMoralesGerantes()) {
            	gestionPMsToCreateOrUpdateOrDelete.add(GestionPMTransform.dto2Bo(gestionPMDTO));
            }
            gestionPMUS.createOrUpdateOrDelete(gestionPMsToCreateOrUpdateOrDelete, etablissement);
        }        
        
        
        // Enregistrement en base
        etablissementRepository.saveAndFlush(etablissement);
        
        // Version update and Id update if needed
        //----------------------------------------

        etablissementRepository.refresh(etablissement);
        
        // Check after refresh to get BO up to date
        // PMzone => ZoneComm et ZoneVente
        if (!CollectionUtils.isEmpty(etablissementDTO.getPmZones()) || statusHasChanged) {
        	List<PmZone> gestionZoneCommsToCreateOrUpdate = new ArrayList<>();
            List<PmZone> gestionZoneVentesToCreateOrUpdate = new ArrayList<>();
        	if (!CollectionUtils.isEmpty(etablissementDTO.getPmZones())){ 
	        	
	            for (PmZoneDTO dto : etablissementDTO.getPmZones()) {                
	                if (dto != null && dto.getZoneDecoup() != null) {
	                	if (ZoneCommDTO.class.equals(dto.getZoneDecoup().getClass()))
	                		gestionZoneCommsToCreateOrUpdate.add(PmZoneTransform.dto2Bo(dto));
	                	else if (ZoneVenteDTO.class.equals(dto.getZoneDecoup().getClass()))
	                		gestionZoneVentesToCreateOrUpdate.add(PmZoneTransform.dto2Bo(dto));
	                	else
	                		// TODO MBE Peut-on avoir des zones financieres par exemple ?
	                        throw new UnsupportedOperationException();
	            	}
	            }
        	}
            // ZoneComm
            if (!gestionZoneCommsToCreateOrUpdate.isEmpty() || statusHasChanged){
        		// si l'ancien statut est A ou P et le nouveau est X
        		if ( ("A".equals(oldStatut) || "P".equals(oldStatut)) && "X".equals(newStatut)){
        			pmZoneUS.transferInCancellationZone(etablissement);
        			etablissementRepository.refresh(etablissement);
        			//gestionZoneCommsToCreateOrUpdate.add(zcLinkTocreate);
        		}
        		if ( ("X".equals(oldStatut)) && "A".equals(newStatut)){
        			// check if the firm needs a SIRET 
        			etablissementUS.checkSiret(etablissement);
        			// put the firm into an ATT ZC
        			pmZoneUS.transferInStandbyZone(etablissement);
        			etablissementRepository.refresh(etablissement);
        		}
        		if ( ("X".equals(oldStatut)) && "P".equals(newStatut)){
        			// put the firm into an ATT ZC
        			pmZoneUS.transferInStandbyZone(etablissement);
        			etablissementRepository.refresh(etablissement);
            	}
            	pmZoneUS.createOrUpdateOrDeleteZcLinks(gestionZoneCommsToCreateOrUpdate, etablissement);
            }
            // ZoneVente
            if (!gestionZoneVentesToCreateOrUpdate.isEmpty())
            	pmZoneUS.createOrUpdateOrDeleteZvLinks(gestionZoneVentesToCreateOrUpdate, etablissement);
        }        

        
        // Check par défaut des Zones Com
        pmZoneUS.checkAndUpdateZonesCom(etablissement);
        
        //Check Enfants
        if (statusHasChanged && newStatut.equals("X")) {
        	etablissementUS.checkServices(etablissement);       	
        }

        
        // TODO CEP/MBE :  Voir si on peut faire autrement que de relire une nouvelle fois le BO
        etablissementRepository.refresh(etablissement);
        
        // Bo2Dto Etablissement
        EtablissementTransform.bo2DtoLight(etablissement, etablissementDTO);

        // Bo2Dto Parent
        etablissementDTO.setParent(null);
        if (etablissement.getParent() != null) {
            etablissementDTO.setParent(PersonneMoraleTransform.bo2DtoLight(etablissement.getParent()));
        }
        
        // Bo2Dto ProfilFirme
        etablissementDTO.setProfilFirme(null);
        if (etablissement.getProfilFirme() != null) {

            etablissementDTO.setProfilFirme(ProfilFirmeTransform.bo2DtoLight(etablissement.getProfilFirme()));            
        }

        // Bo2Dto SelfBookingTool
        etablissementDTO.setSelfBookingTool(null);
        if (etablissement.getSelfBookingTool() != null) {

            etablissementDTO.setSelfBookingTool(SelfBookingToolTransform.bo2DtoLight(etablissement.getSelfBookingTool()));            
        }        

        // Bo2Dto Synonymes
        etablissementDTO.setSynonymes(new HashSet<SynonymeDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getSynonymes())) {

            for (Synonyme synonyme : etablissement.getSynonymes()) {                
                etablissementDTO.getSynonymes().add(SynonymeTransform.bo2DtoLight(synonyme));      
            }
        }

        // Bo2Dto NumeroIdent
        etablissementDTO.setNumerosIdent(new HashSet<NumeroIdentDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getNumerosIdent())) {

            for (NumeroIdent numeroIdent : etablissement.getNumerosIdent()) {                
                etablissementDTO.getNumerosIdent().add(NumeroIdentTransform.bo2DtoLight(numeroIdent));      
            }
        }

        // Bo2Dto PostalAddresses
        etablissementDTO.setPostalAddresses(new ArrayList<PostalAddressDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getPostalAddresses())) {

            for (PostalAddress postalAddress : etablissement.getPostalAddresses()) {                
                etablissementDTO.getPostalAddresses().add(PostalAddressTransform.bo2DtoLight(postalAddress));      
            }
          }

        // Bo2Dto Chiffre
        etablissementDTO.setChiffres(new HashSet<ChiffreDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getChiffres())) {

            for (Chiffre chiffre : etablissement.getChiffres()) {                
                etablissementDTO.getChiffres().add(ChiffreTransform.bo2DtoLight(chiffre));
            }
        }

        // Bo2Dto Email
        etablissementDTO.setEmails(new HashSet<EmailDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getEmails())) {

			for (Email email : etablissement.getEmails()) {                
                etablissementDTO.getEmails().add(EmailTransform.bo2DtoLight(email));      
            }
        }
	
        // Bo2Dto Segmentation
        etablissementDTO.setSegmentations(new HashSet<SegmentationDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getSegmentations())) {

            for (Segmentation segmentation : etablissement.getSegmentations()) {                
                etablissementDTO.getSegmentations().add(SegmentationTransform.bo2DtoLight(segmentation));      
            }
        } 
        
        // Bo2Dto Telecoms
        etablissementDTO.setTelecoms(new HashSet<TelecomsDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getTelecoms())) {

			for (Telecoms telecom : etablissement.getTelecoms()) {                
                etablissementDTO.getTelecoms().add(TelecomsTransform.bo2DtoLight(telecom));      
            }
        }
        
        // Bo2Dto GestionPM
        etablissementDTO.setPersonnesMoralesGerantes(new HashSet<GestionPMDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getPersonnesMoralesGerantes())) {

            for (GestionPM gestionPM : etablissement.getPersonnesMoralesGerantes()) {                
                etablissementDTO.getPersonnesMoralesGerantes().add(GestionPMTransform.bo2DtoLight(gestionPM));
            }
        }        

        // Bo2Dto PmZone
        etablissementDTO.setPmZones(new HashSet<PmZoneDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getPmZones())) {

            for (PmZone pmZone : etablissement.getPmZones()) {                
                etablissementDTO.getPmZones().add(PmZoneTransform.bo2Dto(pmZone));
            }
        }        
        
        // Fin
        log.info("END update");
    }
    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void update(EtablissementDTO etablissementDTO, boolean pUpdateIdentification, boolean pUpdateFirm) throws JrafDomainException {

        log.info("BEGIN update");
        
        Assert.notNull(etablissementDTO.getGin(), "gin is mandatory");
        Assert.notNull(etablissementDTO.getVersion(), "version is mandatory");
        
        log.info("gin = " + etablissementDTO.getGin());
        log.info("version = " + etablissementDTO.getVersion());
        
        Etablissement etablissement = null;
        
        Optional<Etablissement> result = etablissementRepository.findById(etablissementDTO.getGin());
        
        if (result.isPresent()) {
        	etablissement = result.get();
        }

        // On verifie que l'id fourni retourne bien un etablissement
        if (etablissement == null) {

            throw new JrafDomainRollbackException("001");       // NOT FOUND
        }
        
        // On v�rifie que la version en entree coencide avec la version stock�e
        else if (!etablissement.getVersion().equals(etablissementDTO.getVersion())) {

            throw new JrafDomainRollbackException("003");       // SIMULTANEOUS UPDATE
        }

        // Mise a jour des proprietes light de PersonneMorale
        boolean businessNameHasChanged = false;
        boolean statusHasChanged = false;
        // Historique des statuts de la PM utilise pour les PMZones
        String oldStatut = etablissement.getStatut();
        String newStatut = etablissementDTO.getStatut();
        Date now = new Date();
        if (pUpdateIdentification) {
        	
            // Check status 
            statusHasChanged = !oldStatut.equals(newStatut);
            etablissement.setStatut(etablissementDTO.getStatut());
            etablissementUS.checkStatutWhenUpdating(etablissement);
            
            // Check nom
            businessNameHasChanged = !etablissement.getNom().equals(etablissementDTO.getNom());
            etablissement.setNom(etablissementDTO.getNom());
            etablissementUS.checkNom(etablissement);

            // Check activite locale
            etablissement.setActiviteLocal(etablissementDTO.getActiviteLocal());
            etablissementUS.checkActivitetLocale(etablissement);
            
            // Check code industrie 
            etablissement.setCodeIndustrie(etablissementDTO.getCodeIndustrie());
            etablissementUS.checkCodeIndus(etablissement);
            
            // Check type demarchage
            etablissement.setTypeDemarchage(etablissementDTO.getTypeDemarchage());
            etablissementUS.checkTypeDemarchage(etablissement);
            
            // Check Statut juridique
            etablissement.setStatutJuridique(etablissementDTO.getStatutJuridique());
            etablissementUS.checkStatutJuridique(etablissement);

            PersonneMoraleTransform.dto2BoLight(etablissementDTO, etablissement);
        }
        if (businessNameHasChanged) {
            etablissement.setDateModificationNom(now);
        }
        if (statusHasChanged) {
            etablissement.setDateModificationStatut(now);
            
        }
        
        etablissement.setDateModification(now);
        etablissement.setSignatureModification(etablissementDTO.getSignatureModification());
        etablissement.setSiteModification(etablissementDTO.getSiteModification());
        
        // Mise � jour des proprietes de Etablissement
        if (pUpdateFirm) {

            // Check type etablissement
            etablissement.setType(etablissementDTO.getType());
            etablissementUS.checkType(etablissement);
            
            // Check siege social
            etablissement.setSiegeSocial(etablissementDTO.getSiegeSocial());
            etablissementUS.checkSiegeSocial(etablissement);

            etablissement.setSiret(etablissementDTO.getSiret());
            etablissementUS.checkSiret(etablissement);
            
            etablissement.setCe(etablissementDTO.getCe());
            etablissement.setRem(etablissementDTO.getRem());
        }
        
        // ProfilFirme
        if (etablissementDTO.getProfilFirme() != null) {
            ProfilFirme profilFirme = ProfilFirmeTransform.dto2BoLight(etablissementDTO.getProfilFirme());
        	profilFirmeUS.check(profilFirme, etablissement);
        }
        // SelfBookingTool
        if (etablissementDTO.getSelfBookingTool() != null) {
        	SelfBookingTool selfBookingTool = SelfBookingToolTransform.dto2BoLight(etablissementDTO.getSelfBookingTool());
        	selfBookingToolUS.check(selfBookingTool, etablissement);
        }
        
        // Enregistrement en base
        etablissementRepository.saveAndFlush(etablissement);
                            
        // Synonymes
        if (etablissementDTO.getSynonymes() != null) {
                                
            List<Synonyme> synonymesToCreateOrUpdateOrDelete = new ArrayList<>();
            for (SynonymeDTO synonymeDTO : etablissementDTO.getSynonymes()) {
                synonymesToCreateOrUpdateOrDelete.add(SynonymeTransform.dto2BoLight(synonymeDTO));
            }
            synonymeUS.createOrUpdateOrDelete(synonymesToCreateOrUpdateOrDelete, etablissement);
        }

        // Adresses postales
        if (etablissementDTO.getPostalAddresses() != null) {

            List<PostalAddress> postalAddressesToCreateOrUpdateOrDelete = new ArrayList<>();
            for (PostalAddressDTO postalAddressDTO : etablissementDTO.getPostalAddresses()) {
            	postalAddressesToCreateOrUpdateOrDelete.add(PostalAddressTransform.dto2BoLight(postalAddressDTO));
            }
            postalAddressUS.createOrUpdateOrDelete(postalAddressesToCreateOrUpdateOrDelete, etablissement);
        }
                    
        // Chiffres
        if (etablissementDTO.getChiffres() != null) {
                        
            List<Chiffre> chiffresToCreateOrUpdateOrDelete = new ArrayList<>();
            for (ChiffreDTO chiffreDTO : etablissementDTO.getChiffres()) {
            	chiffresToCreateOrUpdateOrDelete.add(ChiffreTransform.dto2BoLight(chiffreDTO));
            }
            chiffreUS.createOrUpdateOrDelete(chiffresToCreateOrUpdateOrDelete, etablissement);
        }                

       	// Key Number / Numero Ident
        if (etablissementDTO.getNumerosIdent() != null) {

            List<NumeroIdent> numerosIdentToCreateOrUpdateOrDelete = new ArrayList<>();
            for (NumeroIdentDTO numeroIdentDTO : etablissementDTO.getNumerosIdent()) {
            	numerosIdentToCreateOrUpdateOrDelete.add(NumeroIdentTransform.dto2BoLight(numeroIdentDTO));
            }
            numeroIdentUS.createOrUpdateOrDelete(numerosIdentToCreateOrUpdateOrDelete, etablissement);
        }

        // Email
        if (etablissementDTO.getEmails() != null) {

            List<Email> emailsToCreateOrUpdateOrDelete = new ArrayList<>();
            for (EmailDTO emailDTO : etablissementDTO.getEmails()) {
            	emailsToCreateOrUpdateOrDelete.add(EmailTransform.dto2BoLight(emailDTO));
            }
            emailUS.createOrUpdateOrDelete(emailsToCreateOrUpdateOrDelete, etablissement);
        }

       	// Segmentation
        if (etablissementDTO.getSegmentations() != null) {
            
            List<Segmentation> segmentationsToCreateOrUpdateOrDelete = new ArrayList<>();
            for (SegmentationDTO segmentationDTO : etablissementDTO.getSegmentations()) {
            	segmentationsToCreateOrUpdateOrDelete.add(SegmentationTransform.dto2BoLight(segmentationDTO));
            }
            segmentationUS.createOrUpdateOrDelete(segmentationsToCreateOrUpdateOrDelete, etablissement);
        }

        // Telecoms
        if (etablissementDTO.getTelecoms() != null) {

            List<Telecoms> telecomsToCreateOrUpdateOrDelete = new ArrayList<>();
            for (TelecomsDTO telecomDTO : etablissementDTO.getTelecoms()) {
            	telecomsToCreateOrUpdateOrDelete.add(TelecomsTransform.dto2BoLight(telecomDTO));
            }
            telecomDS.createOrUpdateOrDelete(telecomsToCreateOrUpdateOrDelete, etablissement);
        }

        // GestionPMs
        if (etablissementDTO.getPersonnesMoralesGerantes() != null) {
                        
            List<GestionPM> gestionPMsToCreateOrUpdateOrDelete = new ArrayList<>();
            for (GestionPMDTO gestionPMDTO : etablissementDTO.getPersonnesMoralesGerantes()) {
            	gestionPMsToCreateOrUpdateOrDelete.add(GestionPMTransform.dto2Bo(gestionPMDTO));
            }
            gestionPMUS.createOrUpdateOrDelete(gestionPMsToCreateOrUpdateOrDelete, etablissement);
        }        
        
        // Version update and Id update if needed
        //----------------------------------------

        etablissementRepository.refresh(etablissement);
        
        // Check after refresh to get BO up to date
        // PMzone => ZoneComm et ZoneVente
        if (!CollectionUtils.isEmpty(etablissementDTO.getPmZones()) || statusHasChanged) {
        	List<PmZone> gestionZoneCommsToCreateOrUpdate = new ArrayList<>();
            List<PmZone> gestionZoneVentesToCreateOrUpdate = new ArrayList<>();
        	if (!CollectionUtils.isEmpty(etablissementDTO.getPmZones())){ 
	        	
	            for (PmZoneDTO dto : etablissementDTO.getPmZones()) {                
	                if (dto != null && dto.getZoneDecoup() != null) {
	                	if (ZoneCommDTO.class.equals(dto.getZoneDecoup().getClass()))
	                		gestionZoneCommsToCreateOrUpdate.add(PmZoneTransform.dto2Bo(dto));
	                	else if (ZoneVenteDTO.class.equals(dto.getZoneDecoup().getClass()))
	                		gestionZoneVentesToCreateOrUpdate.add(PmZoneTransform.dto2Bo(dto));
	                	else
	                		// TODO MBE Peut-on avoir des zones financieres par exemple ?
	                        throw new UnsupportedOperationException();
	            	}
	            }
        	}
            // ZoneComm
            if (!gestionZoneCommsToCreateOrUpdate.isEmpty() || statusHasChanged){
        		// si l'ancien statut est A ou P et le nouveau est X
        		if ( ("A".equals(oldStatut) || "P".equals(oldStatut)) && "X".equals(newStatut)){
        			pmZoneUS.transferInCancellationZone(etablissement);
        			etablissementRepository.refresh(etablissement);
        			//gestionZoneCommsToCreateOrUpdate.add(zcLinkTocreate);
        		}
        		if ( ("X".equals(oldStatut)) && "A".equals(newStatut)){
        			// check if the firm needs a SIRET 
        			etablissementUS.checkSiret(etablissement);
        			// put the firm into an ATT ZC
        			pmZoneUS.transferInStandbyZone(etablissement);
        			etablissementRepository.refresh(etablissement);
        		}
        		if ( ("X".equals(oldStatut)) && "P".equals(newStatut)){
        			// put the firm into an ATT ZC
        			pmZoneUS.transferInStandbyZone(etablissement);
        			etablissementRepository.refresh(etablissement);
            	}
            	pmZoneUS.createOrUpdateOrDeleteZcLinks(gestionZoneCommsToCreateOrUpdate, etablissement);
            }
            // ZoneVente
            if (!gestionZoneVentesToCreateOrUpdate.isEmpty())
            	pmZoneUS.createOrUpdateOrDeleteZvLinks(gestionZoneVentesToCreateOrUpdate, etablissement);
        }        

        
        // Check par défaut des Zones Com
        pmZoneUS.checkAndUpdateZonesCom(etablissement);
        
        //Check Enfants
        if (statusHasChanged && newStatut.equals("X")) {
        	etablissementUS.checkServices(etablissement);       	
        }

        
        // TODO CEP/MBE :  Voir si on peut faire autrement que de relire une nouvelle fois le BO
        etablissementRepository.refresh(etablissement);
        
        // Bo2Dto Etablissement
        EtablissementTransform.bo2DtoLight(etablissement, etablissementDTO);

        // Bo2Dto Parent
        etablissementDTO.setParent(null);
        if (etablissement.getParent() != null) {
            etablissementDTO.setParent(PersonneMoraleTransform.bo2DtoLight(etablissement.getParent()));
        }
        
        // Bo2Dto ProfilFirme
        etablissementDTO.setProfilFirme(null);
        if (etablissement.getProfilFirme() != null) {

            etablissementDTO.setProfilFirme(ProfilFirmeTransform.bo2DtoLight(etablissement.getProfilFirme()));            
        }

        // Bo2Dto SelfBookingTool
        etablissementDTO.setSelfBookingTool(null);
        if (etablissement.getSelfBookingTool() != null) {

            etablissementDTO.setSelfBookingTool(SelfBookingToolTransform.bo2DtoLight(etablissement.getSelfBookingTool()));            
        }        

        // Bo2Dto Synonymes
        etablissementDTO.setSynonymes(new HashSet<SynonymeDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getSynonymes())) {

            for (Synonyme synonyme : etablissement.getSynonymes()) {                
                etablissementDTO.getSynonymes().add(SynonymeTransform.bo2DtoLight(synonyme));      
            }
        }

        // Bo2Dto NumeroIdent
        etablissementDTO.setNumerosIdent(new HashSet<NumeroIdentDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getNumerosIdent())) {

            for (NumeroIdent numeroIdent : etablissement.getNumerosIdent()) {                
                etablissementDTO.getNumerosIdent().add(NumeroIdentTransform.bo2DtoLight(numeroIdent));      
            }
        }

        // Bo2Dto PostalAddresses
        etablissementDTO.setPostalAddresses(new ArrayList<PostalAddressDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getPostalAddresses())) {

            for (PostalAddress postalAddress : etablissement.getPostalAddresses()) {                
                etablissementDTO.getPostalAddresses().add(PostalAddressTransform.bo2DtoLight(postalAddress));      
            }
            }

        // Bo2Dto Chiffre
        etablissementDTO.setChiffres(new HashSet<ChiffreDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getChiffres())) {

            for (Chiffre chiffre : etablissement.getChiffres()) {                
                etablissementDTO.getChiffres().add(ChiffreTransform.bo2DtoLight(chiffre));
            }
        }

        // Bo2Dto Email
        etablissementDTO.setEmails(new HashSet<EmailDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getEmails())) {

			for (Email email : etablissement.getEmails()) {                
                etablissementDTO.getEmails().add(EmailTransform.bo2DtoLight(email));      
            }
        }
	
        // Bo2Dto Segmentation
        etablissementDTO.setSegmentations(new HashSet<SegmentationDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getSegmentations())) {

            for (Segmentation segmentation : etablissement.getSegmentations()) {                
                etablissementDTO.getSegmentations().add(SegmentationTransform.bo2DtoLight(segmentation));      
            }
        } 
        
        // Bo2Dto Telecoms
        etablissementDTO.setTelecoms(new HashSet<TelecomsDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getTelecoms())) {

			for (Telecoms telecom : etablissement.getTelecoms()) {                
                etablissementDTO.getTelecoms().add(TelecomsTransform.bo2DtoLight(telecom));      
            }
        }
        
        // Bo2Dto GestionPM
        etablissementDTO.setPersonnesMoralesGerantes(new HashSet<GestionPMDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getPersonnesMoralesGerantes())) {

            for (GestionPM gestionPM : etablissement.getPersonnesMoralesGerantes()) {                
                etablissementDTO.getPersonnesMoralesGerantes().add(GestionPMTransform.bo2DtoLight(gestionPM));
            }
        }        

        // Bo2Dto PmZone
        etablissementDTO.setPmZones(new HashSet<PmZoneDTO>());
        if (!CollectionUtils.isEmpty(etablissement.getPmZones())) {

            for (PmZone pmZone : etablissement.getPmZones()) {                
                etablissementDTO.getPmZones().add(PmZoneTransform.bo2Dto(pmZone));
            }
        }        
        
        // Fin
        log.info("END update");
    }
    
    
    private boolean isValidToken(String pToken, String pNom, String pType, String pCodePays, String pCodeMetier) {
		boolean valid = false;

        if (VISA_TOKEN.equals(pToken))
        	return true;
        
        // compute Token Visa
        String sToken = personneMoraleUS.generateTokenKey(pNom, pType, pCodePays, pCodeMetier);

        if (VISA_TOKEN.equals(pToken) || (pToken != null && pToken.equals(sToken)))
        	valid = true;
        	
		return valid;
    }
    
    /**
     * Return the code country of the valid localization postal address
     * @param pAdrs
     * @return
     */
    private String getCodeCountyValidLocalizationPostalAddress(Collection<PostalAddress> pAdrs) {
    	String sCodePays = null;
    	
    	for (PostalAddress adresse : pAdrs) {
    		if ("V".equals(adresse.getSstatut_medium()) 
    				&& "L".equals(adresse.getScode_medium())) {
    			sCodePays = adresse.getScode_pays();
    			break;
    		}
		}
    	
    	return sCodePays;
    }

	
	public List<EtablissementDTO> findByExample(EtablissementDTO etablissementDTO, int i, int j) throws JrafDomainException {
		List<EtablissementDTO> results = new ArrayList<>();

		for(Etablissement found : etablissementRepository.findEtablissementByExample(EtablissementTransform.dto2BoLight(etablissementDTO), i, j)) {
			results.add(EtablissementTransform.bo2DtoLight(found));
		}
		
		return results;
	}
    
    /*PROTECTED REGION END*/
}
