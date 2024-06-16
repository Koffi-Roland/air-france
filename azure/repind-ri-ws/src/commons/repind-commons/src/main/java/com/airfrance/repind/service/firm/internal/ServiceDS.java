package com.airfrance.repind.service.firm.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.firme.EtablissementRepository;
import com.airfrance.repind.dao.firme.ServiceRepository;
import com.airfrance.repind.dto.adresse.*;
import com.airfrance.repind.dto.firme.*;
import com.airfrance.repind.dto.profil.ProfilFirmeTransform;
import com.airfrance.repind.dto.zone.PmZoneDTO;
import com.airfrance.repind.dto.zone.ZoneCommDTO;
import com.airfrance.repind.dto.zone.ZoneVenteDTO;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.firme.*;
import com.airfrance.repind.entity.profil.ProfilFirme;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.internal.unitservice.adresse.EmailUS;
import com.airfrance.repind.service.internal.unitservice.adresse.PostalAddressUS;
import com.airfrance.repind.service.internal.unitservice.adresse.TelecomUS;
import com.airfrance.repind.service.internal.unitservice.firm.*;
import com.airfrance.repind.service.internal.unitservice.profil.ProfilFirmeUS;
import com.airfrance.repind.service.internal.unitservice.zone.PmZoneUS;
import com.airfrance.repind.service.internal.unitservice.zone.ZoneCommUS;
import com.airfrance.repind.service.internal.unitservice.zone.ZoneVenteUS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@org.springframework.stereotype.Service
public class ServiceDS {

    /** logger */
    private static final Log log = LogFactory.getLog(ServiceDS.class);
    
    /** visa key **/
    private final String VISA_TOKEN = "VisaKeySiC2";

    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

    /** main dao */
    @Autowired
    private ServiceRepository serviceRepository;
    
    /** references on associated DAOs */
    @Autowired
    private EtablissementRepository etablissementRepository;
    
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
    
    /** domain service : TelecomDS **/
    @Autowired
    private TelecomDS telecomDS;
    
    public EtablissementRepository getEtablissementRepository() {
		return etablissementRepository;
	}

	public void setEtablissementRepository(EtablissementRepository etablissementRepository) {
		this.etablissementRepository = etablissementRepository;
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

    @Transactional(readOnly=true)
    public Integer countWhere(ServiceDTO dto) throws JrafDomainException {
		Service service = ServiceTransform.dto2BoLight(dto);
		return (int) serviceRepository.count(Example.of(service));
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(ServiceDTO serviceDTO) throws JrafDomainException {

       /*PROTECTED REGION ID(_qsbwkC6FEeSfSooroMx0yQ DS-CM create) ENABLED START*/
        Service service = null;

        // light transformation dto -> bo
        service = ServiceTransform.dto2BoLight(serviceDTO);

        // create in database (call the abstract class)
        service = serviceRepository.saveAndFlush(service);

        // Version update and Id update if needed
        ServiceTransform.bo2DtoLight(service, serviceDTO);
       /*PROTECTED REGION END*/
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(ServiceDTO dto) throws JrafDomainException {

       /*PROTECTED REGION ID(_qsbwkC6FEeSfSooroMx0yQ DS-CM remove) ENABLED START*/
        Service service = null;
        
        // light transform dto -> bo
        service = ServiceTransform.dto2BoLight(dto);
        
        // delete (database)
        serviceRepository.delete(service);
       /*PROTECTED REGION END*/
    }
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(String oid) throws JrafDomainException {
    	serviceRepository.deleteById(oid);
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void update(ServiceDTO serviceDTO) throws JrafDomainException {
       Optional<Service> service = serviceRepository.findById(serviceDTO.getGin());
         
       if (service.isPresent()) {
    	// transformation light dto -> bo
           ServiceTransform.dto2BoLight(serviceDTO, service.get()); 
       }
    }


    @Transactional(readOnly=true)
    public List<ServiceDTO> findAll() throws JrafDomainException {
		List<ServiceDTO> results = new ArrayList<>();
		for(Service found : serviceRepository.findAll()) {
			results.add(ServiceTransform.bo2DtoLight(found));
		}
		return results;
    }


    @Transactional(readOnly=true)
    public Integer count() throws JrafDomainException {
    	return (int) serviceRepository.count();
    }

    @Transactional(readOnly=true)
    public List<ServiceDTO> findByExample(ServiceDTO dto, Integer limit) throws JrafDomainException {
        Service service = ServiceTransform.dto2BoLight(dto);
        List<ServiceDTO> result = new ArrayList<>();
        for (Service found : serviceRepository.findAll(Example.of(service), PageRequest.of(0,limit))) {
			result.add(ServiceTransform.bo2DtoLight(found));
		}
		return result;
    }

    @Transactional(readOnly=true)
    public ServiceDTO get(ServiceDTO dto) throws JrafDomainException {
        Optional<Service> service = serviceRepository.findById(dto.getGin());
        
        if (!service.isPresent()) {
        	return null;
        }
       
        return ServiceTransform.bo2DtoLight(service.get());
    }

    @Transactional(readOnly=true)
    public ServiceDTO get(String oid) throws JrafDomainException {
        Optional<Service> service = serviceRepository.findById(oid);
        
        if (!service.isPresent()) {
        	return null;
        }
       
        return ServiceTransform.bo2DtoLight(service.get());
    }

    public ServiceRepository getServiceRepository() {
		return serviceRepository;
	}

	public void setServiceRepository(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}

	/**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_qsbwkC6FEeSfSooroMx0yQgem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /*PROTECTED REGION ID(_qsbwkC6FEeSfSooroMx0yQ u m) ENABLED START*/
    // add your custom methods here if necessary
    public List<ServiceDTO> findClosedNotModifiedSince(int numberOfDays) 
    	throws JrafDomainException {
        
        List<Service> boFounds = null;
        List<ServiceDTO> dtoFounds = null;
        ServiceDTO serviceDTO = null;
        Service service = null;

        boFounds = serviceRepository.findClosedNotModifiedSince(numberOfDays);
        
        // transformation bo -> DTO
        if (boFounds != null) {
            dtoFounds = new ArrayList<ServiceDTO>(boFounds.size());
            Iterator<Service> i = boFounds.iterator();
            while (i.hasNext()) {
                service = (Service) i.next();
                serviceDTO = ServiceTransform.bo2DtoLight(service);
                dtoFounds.add(serviceDTO);
            }
        }
        return dtoFounds;
    }    

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(ServiceDTO serviceDTO, String pToken, String pCodeMetier) throws JrafDomainException {
    	
    	log.info("BEGIN create");
    	
    	Service service = null;
    	
        // transformation light dto -> bo
    	service = ServiceTransform.dto2BoLight(serviceDTO);
    	
        Date now = new Date();
        service.setDateCreation(now);
        
        // transformation parent dto -> bo
        if (serviceDTO.getParent() != null) {
        	service.setParent(EtablissementTransform.dto2BoLight((EtablissementDTO)serviceDTO.getParent()));
        }
        
        // Check status 
        serviceUS.checkStatutWhenCreating(service);
        
        // Check nom
        serviceUS.checkNom(service);
        
        // Check code source
        serviceUS.checkCodeSource(service);
        
        // Check code support
        serviceUS.checkCodeSupport(service);
        
        // Check activite locale
        serviceUS.checkActivitetLocale(service);
        
        // Check code industrie 
        serviceUS.checkCodeIndus(service);
        
        // Check Statut juridique
        serviceUS.checkStatutJuridique(service);
        
        // Check type demarchage
        serviceUS.checkTypeDemarchage(service);
        
        //Check Gin Firm is provided
        if (service.getParent() != null) {
        	etablissementUS.checkParent(service);
        } else {
        	throw new JrafDomainRollbackException("182"); // Erreur 182 - GIN FIRM MANDATORY
        }
                
        // RG - traduction phonétique de la marque commerciale
        //-----------------------------------------------------
        service.setIndictNom(null);       // pour le moment, on n'alimente plus ce champ car il faut remettre à plat
        
        service.setGin(null);
        service.setVersion(null);
        
        // On renseigne les champs xxxModification a cause de la réplication
        service.setDateModification(now);
        service.setSiteModification(service.getSiteCreation());
        service.setSignatureModification(service.getSignatureCreation());
        
        // création de l'établissement
        serviceRepository.saveAndFlush(service);
    	
        // transformation profilFirme dto -> bo
        if (serviceDTO.getProfilFirme() != null) {
        	ProfilFirme profilFirme = ProfilFirmeTransform.dto2BoLight(serviceDTO.getProfilFirme());
        	profilFirmeUS.check(profilFirme, service);
        }
        
        // transformation selfBookingTool dto -> bo
        if (serviceDTO.getSelfBookingTool() != null) {
        	SelfBookingTool selfBookingTool = SelfBookingToolTransform.dto2BoLight(serviceDTO.getSelfBookingTool());
        	selfBookingToolUS.check(selfBookingTool, service);
        }
                
        // création des synonymes
        List<Synonyme> synonymesToCreate = new ArrayList<Synonyme>();
        if (!CollectionUtils.isEmpty(serviceDTO.getSynonymes())) {            
            for (SynonymeDTO dto : serviceDTO.getSynonymes()) {                
                if (dto != null) {                    
                    synonymesToCreate.add(SynonymeTransform.dto2BoLight(dto));
                }
            }
        }       
        synonymeUS.createOrUpdateOrDelete(synonymesToCreate, service);
        
        // création des adresses postales
        List<PostalAddress> postalAddressesToCreate = new ArrayList<PostalAddress>();
        if (!CollectionUtils.isEmpty(serviceDTO.getPostalAddresses())) {            
            for (PostalAddressDTO dto : serviceDTO.getPostalAddresses()) {                
                if (dto != null) {                    
                    postalAddressesToCreate.add(PostalAddressTransform.dto2BoLight(dto));
                }
            }
        }       
        postalAddressUS.createOrUpdateOrDelete(postalAddressesToCreate, service);
        
        // création des emails
        List<Email> emailsToCreate = new ArrayList<Email>();
        if (!CollectionUtils.isEmpty(serviceDTO.getEmails())) {            
            for (EmailDTO dto : serviceDTO.getEmails()) {
				if (dto != null) {
					emailsToCreate.add(EmailTransform.dto2BoLight(dto));
                }
            }
        }
        emailUS.createOrUpdateOrDelete(emailsToCreate, service);
        
	    // création des numéros d'identification
        List<NumeroIdent> numerosIdentToCreate = new ArrayList<NumeroIdent>();
        if (!CollectionUtils.isEmpty(serviceDTO.getNumerosIdent())) {            
            for (NumeroIdentDTO dto : serviceDTO.getNumerosIdent()) {                
                if (dto != null) {                    
                    numerosIdentToCreate.add(NumeroIdentTransform.dto2BoLight(dto));
                }
            }
        }
        numeroIdentUS.createOrUpdateOrDelete(numerosIdentToCreate, service);
        
        // création des segmentations
        List<Segmentation> segmentationsToCreate = new ArrayList<Segmentation>();
        if (!CollectionUtils.isEmpty(serviceDTO.getSegmentations())) {            
            for (SegmentationDTO dto : serviceDTO.getSegmentations()) {                
                if (dto != null) {                    
                    segmentationsToCreate.add(SegmentationTransform.dto2BoLight(dto));
                }
            }
        }          
        segmentationUS.createOrUpdateOrDelete(segmentationsToCreate, service);
        
        // création des telecoms
        List<Telecoms> telecomsToCreate = new ArrayList<Telecoms>();
        if (!CollectionUtils.isEmpty(serviceDTO.getTelecoms())) {            
            for (TelecomsDTO dto : serviceDTO.getTelecoms()) {
				if (dto != null) {
					telecomsToCreate.add(TelecomsTransform.dto2BoLight(dto));
                }
            }
        }
        telecomDS.createOrUpdateOrDelete(telecomsToCreate, service);
        
    	// création des chiffres
        List<Chiffre> chiffresToCreate = new ArrayList<Chiffre>();
        if (!CollectionUtils.isEmpty(serviceDTO.getChiffres())) {            
            for (ChiffreDTO dto : serviceDTO.getChiffres()) {                
                if (dto != null) {                    
                    chiffresToCreate.add(ChiffreTransform.dto2BoLight(dto));
            	}
            }
        }
        chiffreUS.createOrUpdateOrDelete(chiffresToCreate, service);
        
        // création des agences li�es
        List<GestionPM> gestionPMsToCreate = new ArrayList<GestionPM>();
        if (!CollectionUtils.isEmpty(serviceDTO.getPersonnesMoralesGerantes())) {            
            for (GestionPMDTO dto : serviceDTO.getPersonnesMoralesGerantes()) {                
                if (dto != null) {                    
                    gestionPMsToCreate.add(GestionPMTransform.dto2Bo(dto));
            	}
            }
        }
        gestionPMUS.createOrUpdateOrDelete(gestionPMsToCreate, service);
        
        // check Token Visa
        String sCodePays = personneMoraleUS.getCodeCountryValidLocalizationPostalAddress(postalAddressesToCreate);

        if (!isValidToken(pToken, service.getNom(), "S", sCodePays, pCodeMetier )) {
        	// Erreur 277 - VISA DURATION EXPIRED
            throw new JrafDomainRollbackException("277");
        }
        
        // Version update and Id update if needed        
        serviceRepository.refresh(service);
        
        /*--------------ZC AND ZV ARE NOT ALLOWED FOR SERVICES---------------------*/
        if (!CollectionUtils.isEmpty(serviceDTO.getPmZones())) {
        	for (PmZoneDTO dto : serviceDTO.getPmZones()) {    
        		if (dto != null && dto.getZoneDecoup() != null) {
        			if (ZoneCommDTO.class.equals(dto.getZoneDecoup().getClass())) {
        				throw new JrafDomainRollbackException("COMMERCIAL ZONE NOT ALLOWED FOR SERVICE");
        			} else if (ZoneVenteDTO.class.equals(dto.getZoneDecoup().getClass())) {
        				throw new JrafDomainRollbackException("SALES ZONE NOT ALLOWED FOR SERVICE");
        			}
        		}
        	}
        }
        
        /*--------------IF ZC AND ZV ARE ALLOWED UNCOMMENT FOLLOWING LINES---------------------*/
        /*// PMzone
        List<PmZone> gestionZoneCommsToCreate = new ArrayList<PmZone>();
        List<PmZone> gestionZoneVentesToCreate = new ArrayList<PmZone>();
        if (!CollectionUtils.isEmpty(serviceDTO.getPmZones())) {            
            for (PmZoneDTO dto : serviceDTO.getPmZones()) {                
                if (dto != null && dto.getZoneDecoup() != null) {
                	if (ZoneCommDTO.class.equals(dto.getZoneDecoup().getClass()))
                		gestionZoneCommsToCreate.add(PmZoneTransform.dto2Bo(dto));
                	else if (ZoneVenteDTO.class.equals(dto.getZoneDecoup().getClass()))
                		gestionZoneVentesToCreate.add(PmZoneTransform.dto2Bo(dto));
                	else
                        throw new UnsupportedOperationException();
            	}
            }
        }
        // ZC
        pmZoneUS.createOrUpdateOrDeleteZcLinks(gestionZoneCommsToCreate, service);
        // ZV
        pmZoneUS.createOrUpdateOrDeleteZvLinks(gestionZoneVentesToCreate, service);*/
        
        serviceRepository.refresh(service);
        
        // Bo2Dto Service
        ServiceTransform.bo2DtoLight(service, serviceDTO);
        
        // Bo2Dto Parent
        serviceDTO.setParent(null);
        if (service.getParent() != null) {
        	serviceDTO.setParent(PersonneMoraleTransform.bo2DtoLight(service.getParent()));
        }
        
        // Bo2Dto ProfilFirme
        serviceDTO.setProfilFirme(null);
        if (service.getProfilFirme() != null) {
        	serviceDTO.setProfilFirme(ProfilFirmeTransform.bo2DtoLight(service.getProfilFirme()));            
        }
        
        // Bo2Dto SelfBookingTool
        serviceDTO.setSelfBookingTool(null);
        if (service.getSelfBookingTool() != null) {
        	serviceDTO.setSelfBookingTool(SelfBookingToolTransform.bo2DtoLight(service.getSelfBookingTool()));            
        }          
        
        // Bo2Dto Synonymes
        serviceDTO.setSynonymes(new HashSet<SynonymeDTO>());
        if (!CollectionUtils.isEmpty(service.getSynonymes())) {
            for (Synonyme synonyme : service.getSynonymes()) {                
            	serviceDTO.getSynonymes().add(SynonymeTransform.bo2DtoLight(synonyme));      
            }
        }

        // Bo2Dto PostalAddress
        serviceDTO.setPostalAddresses(new ArrayList<PostalAddressDTO>()); 
        if (!CollectionUtils.isEmpty(service.getPostalAddresses())) {
            for (PostalAddress postalAddress : service.getPostalAddresses()) {
            	serviceDTO.getPostalAddresses().add(PostalAddressTransform.bo2DtoLight(postalAddress));
            }
        }
        
        // Bo2Dto Emails
        serviceDTO.setEmails(new HashSet<EmailDTO>()); 
        if (!CollectionUtils.isEmpty(service.getEmails())) {
            for (Email email : service.getEmails()) {                
            	serviceDTO.getEmails().add(EmailTransform.bo2DtoLight(email));      
            }
        }        
        
        // Bo2Dto Chiffres
        serviceDTO.setChiffres(new HashSet<ChiffreDTO>());
        if (serviceDTO.getChiffres() != null) {
            for (Chiffre chiffre : service.getChiffres()) {
            	serviceDTO.getChiffres().add(ChiffreTransform.bo2DtoLight(chiffre));
            }
        }
        
        // Bo2Dto Segmentation
        serviceDTO.setSegmentations(new HashSet<SegmentationDTO>()); 
        if (serviceDTO.getSegmentations() != null) {
            for (Segmentation segmentation : service.getSegmentations()) {
            	serviceDTO.getSegmentations().add(SegmentationTransform.bo2DtoLight(segmentation));
            }
        }    
		
        // Bo2Dto Telecoms
        serviceDTO.setTelecoms(new HashSet<TelecomsDTO>()); 
        if (!CollectionUtils.isEmpty(service.getTelecoms())) {
            for (Telecoms telecom : service.getTelecoms()) {                
            	serviceDTO.getTelecoms().add(TelecomsTransform.bo2DtoLight(telecom));      
            }
        } 
        
        // Bo2Dto NumIdent
        serviceDTO.setNumerosIdent(new HashSet<NumeroIdentDTO>()); 
        if (serviceDTO.getNumerosIdent() != null) {
            for (NumeroIdent numero : service.getNumerosIdent()) {
            	serviceDTO.getNumerosIdent().add(NumeroIdentTransform.bo2DtoLight(numero));
            }
        }        
        
        // Bo2Dto GestionPMs
        serviceDTO.setPersonnesMoralesGerantes(new HashSet<GestionPMDTO>());
        if (!CollectionUtils.isEmpty(service.getPersonnesMoralesGerantes())) {
            for (GestionPM gestionPM : service.getPersonnesMoralesGerantes()) {
            	serviceDTO.getPersonnesMoralesGerantes().add(GestionPMTransform.bo2DtoLight(gestionPM));
            }
        }
        
        /*--------------IF ZC AND ZV ARE ALLOWED UNCOMMENT FOLLOWING LINES---------------------*/
        /*// Bo2Dto PmZone
        serviceDTO.setPmZones(new HashSet<PmZoneDTO>());
        if (!CollectionUtils.isEmpty(service.getPmZones())) {
            for (PmZone pmZone : service.getPmZones()) {
            	serviceDTO.getPmZones().add(PmZoneTransform.bo2Dto(pmZone));
            }
        }*/

        log.info("END create");
    }
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void update(ServiceDTO serviceDTO, boolean pUpdateIdentification, boolean pUpdateService) throws JrafDomainException {
    	
    	log.info("BEGIN update");
    	
        Assert.notNull(serviceDTO.getGin(), "gin is mandatory");
        Assert.notNull(serviceDTO.getVersion(), "version is mandatory");
        
        log.info("gin = " + serviceDTO.getGin());
        log.info("version = " + serviceDTO.getVersion());
       
        Service service = null;
        
        Optional<Service> result = serviceRepository.findById(serviceDTO.getGin());
        
        if (result.isPresent()) {
        	service = result.get();
        }
        
        // On verifie que l'id fourni retourne bien un etablissement
        if (service == null) {
            throw new JrafDomainRollbackException("001");       // NOT FOUND
        }
        
        // On verifie que la version en entree coencide avec la version stockee
        else if (!service.getVersion().equals(serviceDTO.getVersion())) {
            throw new JrafDomainRollbackException("003");       // SIMULTANEOUS UPDATE
        }
        
        // Mise a jour des proprietes light de PersonneMorale
        boolean businessNameHasChanged = false;
        boolean statusHasChanged = false;
        
        // Historique des statuts de la PM utilise pour les PMZones
        String oldStatut = service.getStatut();
        String newStatut = serviceDTO.getStatut();
        
        Date now = new Date();
        
        if (pUpdateIdentification) {
        	
            // Check status 
            statusHasChanged = !oldStatut.equals(newStatut);
            service.setStatut(serviceDTO.getStatut());
            serviceUS.checkStatutWhenUpdating(service);
            
            // Check nom
            businessNameHasChanged = !service.getNom().equals(serviceDTO.getNom());
            service.setNom(serviceDTO.getNom());
            serviceUS.checkNom(service);
            
            // Check activite locale
            service.setActiviteLocal(serviceDTO.getActiviteLocal());
            serviceUS.checkActivitetLocale(service);
            
            // Check code industrie 
            service.setCodeIndustrie(serviceDTO.getCodeIndustrie());
            serviceUS.checkCodeIndus(service);
            
            // Check type demarchage
            service.setTypeDemarchage(serviceDTO.getTypeDemarchage());
            serviceUS.checkTypeDemarchage(service);
            
            // Check Statut juridique
            service.setStatutJuridique(serviceDTO.getStatutJuridique());
            serviceUS.checkStatutJuridique(service);
            
            PersonneMoraleTransform.dto2BoLight(serviceDTO, service);
        }
        
        if (businessNameHasChanged) {
        	service.setDateModificationNom(now);
        }
        if (statusHasChanged) {
        	service.setDateModificationStatut(now);
        }
        
        service.setDateModification(now);
        service.setSignatureModification(serviceDTO.getSignatureModification());
        service.setSiteModification(serviceDTO.getSiteModification());
        
        // Mise a jour des proprietes de Service
        if (pUpdateService) {
            // transformation parent dto -> bo
            if (serviceDTO.getParent() != null && serviceDTO.getParent().getGin() != service.getParent().getGin()) {
            	service.setParent(EtablissementTransform.dto2BoLight((EtablissementDTO)serviceDTO.getParent()));
            }
        	
        	//ParentGinFirm
        	if (service.getParent() != null) {
            	etablissementUS.checkParent(service);
            } else {
            	throw new JrafDomainRollbackException("182"); // Erreur 182 - GIN FIRM MANDATORY
            }
        }
        
        // ProfilFirme
        if (serviceDTO.getProfilFirme() != null) {
            ProfilFirme profilFirme = ProfilFirmeTransform.dto2BoLight(serviceDTO.getProfilFirme());
        	profilFirmeUS.check(profilFirme, service);
        }
        
        // SelfBookingTool
        if (serviceDTO.getSelfBookingTool() != null) {
        	SelfBookingTool selfBookingTool = SelfBookingToolTransform.dto2BoLight(serviceDTO.getSelfBookingTool());
        	selfBookingToolUS.check(selfBookingTool, service);
        }
        
        // Enregistrement en base
        serviceRepository.saveAndFlush(service);
        
        // Synonymes
        if (serviceDTO.getSynonymes() != null) {
                                
            List<Synonyme> synonymesToCreateOrUpdateOrDelete = new ArrayList<Synonyme>();
            for (SynonymeDTO synonymeDTO : serviceDTO.getSynonymes()) {
                synonymesToCreateOrUpdateOrDelete.add(SynonymeTransform.dto2BoLight(synonymeDTO));
            }
            synonymeUS.createOrUpdateOrDelete(synonymesToCreateOrUpdateOrDelete, service);
        }
        
        // Adresses postales
        if (serviceDTO.getPostalAddresses() != null) {

            List<PostalAddress> postalAddressesToCreateOrUpdateOrDelete = new ArrayList<PostalAddress>();
            for (PostalAddressDTO postalAddressDTO : serviceDTO.getPostalAddresses()) {
            	postalAddressesToCreateOrUpdateOrDelete.add(PostalAddressTransform.dto2BoLight(postalAddressDTO));
            }
            postalAddressUS.createOrUpdateOrDelete(postalAddressesToCreateOrUpdateOrDelete, service);
        }
        
        // Chiffres
        if (serviceDTO.getChiffres() != null) {
                        
            List<Chiffre> chiffresToCreateOrUpdateOrDelete = new ArrayList<Chiffre>();
            for (ChiffreDTO chiffreDTO : serviceDTO.getChiffres()) {
            	chiffresToCreateOrUpdateOrDelete.add(ChiffreTransform.dto2BoLight(chiffreDTO));
            }
            chiffreUS.createOrUpdateOrDelete(chiffresToCreateOrUpdateOrDelete, service);
        }                

       	// Key Number / Numero Ident
        if (serviceDTO.getNumerosIdent() != null) {

            List<NumeroIdent> numerosIdentToCreateOrUpdateOrDelete = new ArrayList<NumeroIdent>();
            for (NumeroIdentDTO numeroIdentDTO : serviceDTO.getNumerosIdent()) {
            	numerosIdentToCreateOrUpdateOrDelete.add(NumeroIdentTransform.dto2BoLight(numeroIdentDTO));
            }
            numeroIdentUS.createOrUpdateOrDelete(numerosIdentToCreateOrUpdateOrDelete, service);
        }

        // Email
        if (serviceDTO.getEmails() != null) {

            List<Email> emailsToCreateOrUpdateOrDelete = new ArrayList<Email>();
            for (EmailDTO emailDTO : serviceDTO.getEmails()) {
            	emailsToCreateOrUpdateOrDelete.add(EmailTransform.dto2BoLight(emailDTO));
            }
            emailUS.createOrUpdateOrDelete(emailsToCreateOrUpdateOrDelete, service);
        }

       	// Segmentation
        if (serviceDTO.getSegmentations() != null) {
            
            List<Segmentation> segmentationsToCreateOrUpdateOrDelete = new ArrayList<Segmentation>();
            for (SegmentationDTO segmentationDTO : serviceDTO.getSegmentations()) {
            	segmentationsToCreateOrUpdateOrDelete.add(SegmentationTransform.dto2BoLight(segmentationDTO));
            }
            segmentationUS.createOrUpdateOrDelete(segmentationsToCreateOrUpdateOrDelete, service);
        }

        // Telecoms
        if (serviceDTO.getTelecoms() != null) {

            List<Telecoms> telecomsToCreateOrUpdateOrDelete = new ArrayList<Telecoms>();
            for (TelecomsDTO telecomDTO : serviceDTO.getTelecoms()) {
            	telecomsToCreateOrUpdateOrDelete.add(TelecomsTransform.dto2BoLight(telecomDTO));
            }
            telecomDS.createOrUpdateOrDelete(telecomsToCreateOrUpdateOrDelete, service);
        }

        // GestionPMs
        if (serviceDTO.getPersonnesMoralesGerantes() != null) {
                        
            List<GestionPM> gestionPMsToCreateOrUpdateOrDelete = new ArrayList<GestionPM>();
            for (GestionPMDTO gestionPMDTO : serviceDTO.getPersonnesMoralesGerantes()) {
            	gestionPMsToCreateOrUpdateOrDelete.add(GestionPMTransform.dto2Bo(gestionPMDTO));
            }
            gestionPMUS.createOrUpdateOrDelete(gestionPMsToCreateOrUpdateOrDelete, service);
        } 
        
        // Version update and Id update if needed
        serviceRepository.refresh(service);
        
        /*--------------ZC AND ZV ARE NOT ALLOWED FOR SERVICES---------------------*/
        if (!CollectionUtils.isEmpty(serviceDTO.getPmZones())) {
        	for (PmZoneDTO dto : serviceDTO.getPmZones()) {    
        		if (dto != null && dto.getZoneDecoup() != null) {
        			if (ZoneCommDTO.class.equals(dto.getZoneDecoup().getClass())) {
        				throw new JrafDomainRollbackException("COMMERCIAL ZONE NOT ALLOWED");
        			} else if (ZoneVenteDTO.class.equals(dto.getZoneDecoup().getClass())) {
        				throw new JrafDomainRollbackException("SALES ZONE NOT ALLOWED");
        			}
        		}
        	}
        }
        
        /*--------------IF ZC AND ZV ARE ALLOWED UNCOMMENT FOLLOWING LINES---------------------*/
        /*// PMzone
        if (!CollectionUtils.isEmpty(serviceDTO.getPmZones()) || statusHasChanged) {
        	List<PmZone> gestionZoneCommsToCreateOrUpdate = new ArrayList<PmZone>();
            List<PmZone> gestionZoneVentesToCreateOrUpdate = new ArrayList<PmZone>();
        	if (!CollectionUtils.isEmpty(serviceDTO.getPmZones())) { 
	            for (PmZoneDTO dto : serviceDTO.getPmZones()) {                
	                if (dto != null && dto.getZoneDecoup() != null) {
	                	if (ZoneCommDTO.class.equals(dto.getZoneDecoup().getClass())) {
	                		gestionZoneCommsToCreateOrUpdate.add(PmZoneTransform.dto2Bo(dto));
	                	} else if (ZoneVenteDTO.class.equals(dto.getZoneDecoup().getClass())) {
	                		gestionZoneVentesToCreateOrUpdate.add(PmZoneTransform.dto2Bo(dto));
	                	} else {
	                        throw new UnsupportedOperationException();
	                	}
	            	}
	            }
        	}
            // ZC
            if (!gestionZoneCommsToCreateOrUpdate.isEmpty() || statusHasChanged) {
        		// si l'ancien statut est A ou P et le nouveau est X
        		if ( ("A".equals(oldStatut) || "P".equals(oldStatut)) && "X".equals(newStatut)) {
        			pmZoneUS.transferInCancellationZone(service);
        			mainDao.refresh(service);
        		}
        		if (("X".equals(oldStatut)) && "A".equals(newStatut)) {
         			// put the firm into an ATT ZC
        			pmZoneUS.transferInStandbyZone(service);
        			mainDao.refresh(service);
        		}
        		if (("X".equals(oldStatut)) && "P".equals(newStatut)) {
        			// put the firm into an ATT ZC
        			pmZoneUS.transferInStandbyZone(service);
        			mainDao.refresh(service);
            	}
            	pmZoneUS.createOrUpdateOrDeleteZcLinks(gestionZoneCommsToCreateOrUpdate, service);
            }
            // ZV
            if (!gestionZoneVentesToCreateOrUpdate.isEmpty()) {
            	pmZoneUS.createOrUpdateOrDeleteZvLinks(gestionZoneVentesToCreateOrUpdate, service);
            }
        }        

        // Check par défaut des Zones Com
        pmZoneUS.checkAndUpdateZonesCom(service);
        
        // Update the BO
        mainDao.refresh(service);*/
                
        // Bo2Dto Service
        ServiceTransform.bo2DtoLight(service, serviceDTO);

        // Bo2Dto Parent
        serviceDTO.setParent(null);
        if (service.getParent() != null) {
        	serviceDTO.setParent(PersonneMoraleTransform.bo2DtoLight(service.getParent()));
        }
        
        // Bo2Dto ProfilFirme
        serviceDTO.setProfilFirme(null);
        if (service.getProfilFirme() != null) {
        	serviceDTO.setProfilFirme(ProfilFirmeTransform.bo2DtoLight(service.getProfilFirme()));            
        }

        // Bo2Dto SelfBookingTool
        serviceDTO.setSelfBookingTool(null);
        if (service.getSelfBookingTool() != null) {
        	serviceDTO.setSelfBookingTool(SelfBookingToolTransform.bo2DtoLight(service.getSelfBookingTool()));            
        }        

        // Bo2Dto Synonymes
        serviceDTO.setSynonymes(new HashSet<SynonymeDTO>());
        if (!CollectionUtils.isEmpty(service.getSynonymes())) {
            for (Synonyme synonyme : service.getSynonymes()) {                
            	serviceDTO.getSynonymes().add(SynonymeTransform.bo2DtoLight(synonyme));      
            }
        }

        // Bo2Dto NumeroIdent
        serviceDTO.setNumerosIdent(new HashSet<NumeroIdentDTO>());
        if (!CollectionUtils.isEmpty(service.getNumerosIdent())) {
            for (NumeroIdent numeroIdent : service.getNumerosIdent()) {                
            	serviceDTO.getNumerosIdent().add(NumeroIdentTransform.bo2DtoLight(numeroIdent));      
            }
        }

        // Bo2Dto PostalAddresses
        serviceDTO.setPostalAddresses(new ArrayList<PostalAddressDTO>());
        if (!CollectionUtils.isEmpty(service.getPostalAddresses())) {
            for (PostalAddress postalAddress : service.getPostalAddresses()) {                
            	serviceDTO.getPostalAddresses().add(PostalAddressTransform.bo2DtoLight(postalAddress));      
            }
        }

        // Bo2Dto Chiffre
        serviceDTO.setChiffres(new HashSet<ChiffreDTO>());
        if (!CollectionUtils.isEmpty(service.getChiffres())) {
            for (Chiffre chiffre : service.getChiffres()) {                
            	serviceDTO.getChiffres().add(ChiffreTransform.bo2DtoLight(chiffre));
            }
        }

        // Bo2Dto Email
        serviceDTO.setEmails(new HashSet<EmailDTO>());
        if (!CollectionUtils.isEmpty(service.getEmails())) {
			for (Email email : service.getEmails()) {                
				serviceDTO.getEmails().add(EmailTransform.bo2DtoLight(email));      
            }
        }
	
        // Bo2Dto Segmentation
        serviceDTO.setSegmentations(new HashSet<SegmentationDTO>());
        if (!CollectionUtils.isEmpty(service.getSegmentations())) {
            for (Segmentation segmentation : service.getSegmentations()) {                
            	serviceDTO.getSegmentations().add(SegmentationTransform.bo2DtoLight(segmentation));      
            }
        } 
        
        // Bo2Dto Telecoms
        serviceDTO.setTelecoms(new HashSet<TelecomsDTO>());
        if (!CollectionUtils.isEmpty(service.getTelecoms())) {
			for (Telecoms telecom : service.getTelecoms()) {                
				serviceDTO.getTelecoms().add(TelecomsTransform.bo2DtoLight(telecom));      
            }
        }
        
        // Bo2Dto GestionPM
        serviceDTO.setPersonnesMoralesGerantes(new HashSet<GestionPMDTO>());
        if (!CollectionUtils.isEmpty(service.getPersonnesMoralesGerantes())) {
            for (GestionPM gestionPM : service.getPersonnesMoralesGerantes()) {                
            	serviceDTO.getPersonnesMoralesGerantes().add(GestionPMTransform.bo2DtoLight(gestionPM));
            }
        }
        
        /*--------------IF ZC AND ZV ARE ALLOWED UNCOMMENT FOLLOWING LINES---------------------*/
        /*// Bo2Dto PmZone
        serviceDTO.setPmZones(new HashSet<PmZoneDTO>());
        if (!CollectionUtils.isEmpty(service.getPmZones())) {
            for (PmZone pmZone : service.getPmZones()) {                
            	serviceDTO.getPmZones().add(PmZoneTransform.bo2Dto(pmZone));
            }
        }*/   

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
}
