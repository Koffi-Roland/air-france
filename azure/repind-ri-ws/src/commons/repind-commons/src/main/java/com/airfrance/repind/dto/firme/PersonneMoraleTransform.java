package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_QjebMODkEd-TxbwIaEembQ i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dto.adresse.EmailTransform;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.PostalAddressTransform;
import com.airfrance.repind.dto.adresse.TelecomsTransform;
import com.airfrance.repind.dto.agence.AgenceDTO;
import com.airfrance.repind.dto.agence.AgenceTransform;
import com.airfrance.repind.dto.profil.ProfilFirmeTransform;
import com.airfrance.repind.dto.profil.Profil_mereTransform;
import com.airfrance.repind.dto.role.BusinessRoleTransform;
import com.airfrance.repind.dto.zone.PmZoneTransform;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.agence.AgenceLight;
import com.airfrance.repind.entity.firme.*;
import com.airfrance.repind.entity.firme.enums.PersonneMoraleTypeEnum;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.EntityManager;
import java.util.*;

/*PROTECTED REGION END*/

/**
 * <p>Title : PersonneMoraleTransform.java</p>
 * transformation bo <-> dto pour un(e) PersonneMorale
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class PersonneMoraleTransform {

    /*PROTECTED REGION ID(_QjebMODkEd-TxbwIaEembQ u var - Tr) ENABLED START*/
    private static Log LOGGER = LogFactory.getLog(PersonneMoraleTransform.class);

    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private PersonneMoraleTransform() {
    }
    /**
     * dto -> bo for a PersonneMorale
     * @param personneMoraleDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static PersonneMorale dto2BoLight(PersonneMoraleDTO personneMoraleDTO) throws JrafDomainException {
        // instanciation du BO
        PersonneMorale personneMorale = null;
        Class dtoClass = personneMoraleDTO.getClass();
    if(dtoClass.equals(EntrepriseDTO.class)) {
         personneMorale = new Entreprise();
         EntrepriseTransform.dto2BoLight((EntrepriseDTO)personneMoraleDTO, (Entreprise)personneMorale);
    }
    if(dtoClass.equals(EtablissementDTO.class)) {
         personneMorale = new Etablissement();
         EtablissementTransform.dto2BoLight((EtablissementDTO)personneMoraleDTO, (Etablissement)personneMorale);
    }
    if(dtoClass.equals(GroupeDTO.class)) {
         personneMorale = new Groupe();
         GroupeTransform.dto2BoLight((GroupeDTO)personneMoraleDTO, (Groupe)personneMorale);
    }
    if(dtoClass.equals(ServiceDTO.class)) {
         personneMorale = new Service();
         ServiceTransform.dto2BoLight((ServiceDTO)personneMoraleDTO, (Service)personneMorale);
    }
    if(dtoClass.equals(AgenceDTO.class)) {
         personneMorale = new Agence();
         AgenceTransform.dto2BoLight((AgenceDTO)personneMoraleDTO, (Agence)personneMorale);
    }
        if(dtoClass == null) {
            throw new JrafDomainRollbackException("Type dto inconnu : " + dtoClass);
        }


        // on retourne le BO
        return personneMorale;
    }

    /**
     * dto -> bo for a personneMorale
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param personneMoraleDTO dto
     * @param personneMorale bo
     */
    public static void dto2BoLight(PersonneMoraleDTO personneMoraleDTO, PersonneMorale personneMorale) {
    
        /*PROTECTED REGION ID(dto2BoLight_QjebMODkEd-TxbwIaEembQ) ENABLED START*/

        dto2BoLightImpl(personneMoraleDTO, personneMorale);
        
        personneMorale.setNom(SicStringUtils.toUpperCaseWithoutAccentsForFirms(personneMoraleDTO.getNom()));

        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a personneMorale
     * @param personneMoraleDTO dto
     * @param personneMorale bo
     */
    private static void dto2BoLightImpl(PersonneMoraleDTO personneMoraleDTO, PersonneMorale personneMorale){
    
        // property of PersonneMoraleDTO
        personneMorale.setGin(personneMoraleDTO.getGin());
        personneMorale.setVersion(personneMoraleDTO.getVersion());
        personneMorale.setNom(personneMoraleDTO.getNom());
        personneMorale.setStatut(personneMoraleDTO.getStatut());
        personneMorale.setDateModificationStatut(personneMoraleDTO.getDateModificationStatut());
        personneMorale.setActiviteLocal(personneMoraleDTO.getActiviteLocal());
        personneMorale.setGinFusion(personneMoraleDTO.getGinFusion());
        personneMorale.setDateFusion(personneMoraleDTO.getDateFusion());
        personneMorale.setSignatureFusion(personneMoraleDTO.getSignatureFusion());
        personneMorale.setDateCreation(personneMoraleDTO.getDateCreation());
        personneMorale.setSignatureCreation(personneMoraleDTO.getSignatureCreation());
        personneMorale.setDateModification(personneMoraleDTO.getDateModification());
        personneMorale.setSignatureModification(personneMoraleDTO.getSignatureModification());
        personneMorale.setCodeIndustrie(personneMoraleDTO.getCodeIndustrie());
        personneMorale.setTypeDemarchage(personneMoraleDTO.getTypeDemarchage());
        personneMorale.setSiteInternet(personneMoraleDTO.getSiteInternet());
        personneMorale.setIndictNom(personneMoraleDTO.getIndictNom());
        personneMorale.setCieGest(personneMoraleDTO.getCieGest());
        personneMorale.setCodeSource(personneMoraleDTO.getCodeSource());
        personneMorale.setCodeSupport(personneMoraleDTO.getCodeSupport());
        personneMorale.setStatutJuridique(personneMoraleDTO.getStatutJuridique());
        personneMorale.setDateModificationNom(personneMoraleDTO.getDateModificationNom());
        personneMorale.setSiteCreation(personneMoraleDTO.getSiteCreation());
        personneMorale.setSiteModification(personneMoraleDTO.getSiteModification());
    
    }

    /**
     * bo -> dto for a personneMorale
     * @param pPersonneMorale bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static PersonneMoraleDTO bo2DtoLight(PersonneMorale pPersonneMorale) throws JrafDomainException {
        // instanciation du DTO
        PersonneMoraleDTO personneMoraleDTO = null;
        PersonneMorale personneMorale = null;
        Class<?> boClass=null;
        if (pPersonneMorale instanceof HibernateProxy) {
            boClass=Hibernate.getClass(pPersonneMorale);
            personneMorale = (PersonneMorale) ((HibernateProxy) pPersonneMorale).getHibernateLazyInitializer()
                    .getImplementation();
        } else {
            boClass = pPersonneMorale.getClass();
            personneMorale = pPersonneMorale;
        }
        if(boClass.equals(Entreprise.class)) {
            personneMoraleDTO = new EntrepriseDTO();
            EntrepriseTransform.bo2DtoLight((Entreprise)personneMorale, (EntrepriseDTO)personneMoraleDTO);
        }
        if(boClass.equals(Etablissement.class)) {
            personneMoraleDTO = new EtablissementDTO();
            EtablissementTransform.bo2DtoLight((Etablissement)personneMorale, (EtablissementDTO)personneMoraleDTO);
        }
        if(boClass.equals(Groupe.class)) {
            personneMoraleDTO = new GroupeDTO();
            GroupeTransform.bo2DtoLight((Groupe)personneMorale, (GroupeDTO)personneMoraleDTO);
        }
        if(boClass.equals(Service.class)) {
            personneMoraleDTO = new ServiceDTO();
            ServiceTransform.bo2DtoLight((Service)personneMorale, (ServiceDTO)personneMoraleDTO);
        }
        if(boClass.equals(Agence.class)) {
            personneMoraleDTO = new AgenceDTO();
            AgenceTransform.bo2DtoLight((Agence)personneMorale, (AgenceDTO)personneMoraleDTO);
        }
        if(personneMoraleDTO == null) {
            throw new JrafDomainRollbackException("Type bo inconnu : " + personneMorale.getClass());
        }
        
        // on retourne le dto
        return personneMoraleDTO;
    }
    
    /**
     * This method add missing segmentation value in return
     * bo -> dto for a personneMorale
     * @param pPersonneMorale bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static PersonneMoraleDTO bo2DtoLightForBatch(PersonneMorale pPersonneMorale) throws JrafDomainException {
        // instanciation du DTO
        PersonneMoraleDTO personneMoraleDTO = null;
        PersonneMorale personneMorale = null;
        Class<?> boClass=null;
        if (pPersonneMorale instanceof HibernateProxy) {
            boClass=Hibernate.getClass(pPersonneMorale);
            personneMorale = (PersonneMorale) ((HibernateProxy) pPersonneMorale).getHibernateLazyInitializer()
                    .getImplementation();
        } else {
            boClass = pPersonneMorale.getClass();
            personneMorale = pPersonneMorale;
        }
        if(boClass.equals(Etablissement.class)) {
            personneMoraleDTO = new EtablissementDTO();
            EtablissementTransform.bo2DtoLightForBatch((Etablissement)personneMorale, (EtablissementDTO)personneMoraleDTO);
        }
        if(personneMoraleDTO == null) {
            throw new JrafDomainRollbackException("Type bo inconnu : " + personneMorale.getClass());
        }       
        // on retourne le dto
        return personneMoraleDTO;
    }

	/**
	 * bo -> dto for a PersonneMoraleLight
	 * 
	 * @param personneMoraleLight bo
	 * 
	 * @return dto
	 * 
	 * @throws JrafDomainException if the DTO type is not supported
	 */
	public static PersonneMoraleDTO bo2DtoLight(PersonneMoraleLight personneMoraleLight) throws JrafDomainException {

		// Declare needed variables
		PersonneMoraleDTO personneMoraleDTO = null;
		PersonneMoraleLight personneMoraleLightToConvert = null;

		// Decide which conversion methodd to call depending on the PersonneMoraleLight class
		Class boClass = null;
		if (personneMoraleLight instanceof HibernateProxy) {
			boClass = Hibernate.getClass(personneMoraleLight);
			personneMoraleLightToConvert = (PersonneMoraleLight) ((HibernateProxy) personneMoraleLight).getHibernateLazyInitializer()
					.getImplementation();
		} else {
			boClass = personneMoraleLight.getClass();
			personneMoraleLightToConvert = personneMoraleLight;
		}
		if (boClass.equals(EntrepriseLight.class)) {
			personneMoraleDTO = new EntrepriseDTO();
			EntrepriseTransform.bo2DtoLight((EntrepriseLight) personneMoraleLightToConvert, (EntrepriseDTO) personneMoraleDTO);
		}
		if (boClass.equals(EtablissementLight.class)) {
			personneMoraleDTO = new EtablissementDTO();
			EtablissementTransform.bo2DtoLight((EtablissementLight) personneMoraleLightToConvert, (EtablissementDTO) personneMoraleDTO);
		}
		if (boClass.equals(GroupeLight.class)) {
			personneMoraleDTO = new GroupeDTO();
			GroupeTransform.bo2DtoLight((GroupeLight) personneMoraleLightToConvert, (GroupeDTO) personneMoraleDTO);
		}
		if (boClass.equals(ServiceLight.class)) {
			personneMoraleDTO = new ServiceDTO();
			ServiceTransform.bo2DtoLight((ServiceLight) personneMoraleLightToConvert, (ServiceDTO) personneMoraleDTO);
		}
		if (boClass.equals(AgenceLight.class)) {
			personneMoraleDTO = new AgenceDTO();
			AgenceTransform.bo2DtoLight((AgenceLight) personneMoraleLightToConvert, (AgenceDTO) personneMoraleDTO);
		}
		if (personneMoraleDTO == null) {
			throw new JrafDomainRollbackException("Type bo inconnu : " + personneMoraleLightToConvert.getClass());
		}

		// on retourne le dto
		return personneMoraleDTO;
	}


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param personneMorale bo
     * @param personneMoraleDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLight(
        PersonneMorale personneMorale,
        PersonneMoraleDTO personneMoraleDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_QjebMODkEd-TxbwIaEembQ) ENABLED START*/

        bo2DtoLightImpl(personneMorale, personneMoraleDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * This method add missing segmentation value in return
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param personneMorale bo
     * @param personneMoraleDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLightForBatch(
        PersonneMorale personneMorale,
        PersonneMoraleDTO personneMoraleDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_QjebMODkEd-TxbwIaEembQ) ENABLED START*/

        bo2DtoLightImplForBatch(personneMorale, personneMoraleDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param personneMorale bo
     * @param personneMoraleDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImpl(PersonneMorale personneMorale,
        PersonneMoraleDTO personneMoraleDTO) throws JrafDomainException{
    

        // simple properties
        personneMoraleDTO.setGin(personneMorale.getGin());
        personneMoraleDTO.setVersion(personneMorale.getVersion());
        personneMoraleDTO.setNom(personneMorale.getNom());
        personneMoraleDTO.setStatut(personneMorale.getStatut());
        personneMoraleDTO.setDateModificationStatut(personneMorale.getDateModificationStatut());
        personneMoraleDTO.setActiviteLocal(personneMorale.getActiviteLocal());
        personneMoraleDTO.setGinFusion(personneMorale.getGinFusion());
        personneMoraleDTO.setDateFusion(personneMorale.getDateFusion());
        personneMoraleDTO.setSignatureFusion(personneMorale.getSignatureFusion());
        personneMoraleDTO.setDateCreation(personneMorale.getDateCreation());
        personneMoraleDTO.setSignatureCreation(personneMorale.getSignatureCreation());
        personneMoraleDTO.setDateModification(personneMorale.getDateModification());
        personneMoraleDTO.setSignatureModification(personneMorale.getSignatureModification());
        personneMoraleDTO.setCodeIndustrie(personneMorale.getCodeIndustrie());
        personneMoraleDTO.setTypeDemarchage(personneMorale.getTypeDemarchage());
        personneMoraleDTO.setSiteInternet(personneMorale.getSiteInternet());
        personneMoraleDTO.setIndictNom(personneMorale.getIndictNom());
        personneMoraleDTO.setCieGest(personneMorale.getCieGest());
        personneMoraleDTO.setCodeSource(personneMorale.getCodeSource());
        personneMoraleDTO.setCodeSupport(personneMorale.getCodeSupport());
        personneMoraleDTO.setStatutJuridique(personneMorale.getStatutJuridique());
        personneMoraleDTO.setDateModificationNom(personneMorale.getDateModificationNom());
        personneMoraleDTO.setNumerosIdent(NumeroIdentTransform.bo2Dto(personneMorale.getNumerosIdent()));
        personneMoraleDTO.setSiteCreation(personneMorale.getSiteCreation());
        personneMoraleDTO.setSiteModification(personneMorale.getSiteModification());
    
    }
    
    /**
     * This method add missing segmentation value in return
     * Transform a business object to DTO. Implementation method
     * @param personneMorale bo
     * @param personneMoraleDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImplForBatch(PersonneMorale personneMorale,
        PersonneMoraleDTO personneMoraleDTO) throws JrafDomainException{
    

        // simple properties
        personneMoraleDTO.setGin(personneMorale.getGin());
        personneMoraleDTO.setVersion(personneMorale.getVersion());
        personneMoraleDTO.setNom(personneMorale.getNom());
        personneMoraleDTO.setStatut(personneMorale.getStatut());
        personneMoraleDTO.setDateModificationStatut(personneMorale.getDateModificationStatut());
        personneMoraleDTO.setActiviteLocal(personneMorale.getActiviteLocal());
        personneMoraleDTO.setGinFusion(personneMorale.getGinFusion());
        personneMoraleDTO.setDateFusion(personneMorale.getDateFusion());
        personneMoraleDTO.setSignatureFusion(personneMorale.getSignatureFusion());
        personneMoraleDTO.setDateCreation(personneMorale.getDateCreation());
        personneMoraleDTO.setSignatureCreation(personneMorale.getSignatureCreation());
        personneMoraleDTO.setDateModification(personneMorale.getDateModification());
        personneMoraleDTO.setSignatureModification(personneMorale.getSignatureModification());
        personneMoraleDTO.setCodeIndustrie(personneMorale.getCodeIndustrie());
        personneMoraleDTO.setTypeDemarchage(personneMorale.getTypeDemarchage());
        personneMoraleDTO.setSiteInternet(personneMorale.getSiteInternet());
        personneMoraleDTO.setIndictNom(personneMorale.getIndictNom());
        personneMoraleDTO.setCieGest(personneMorale.getCieGest());
        personneMoraleDTO.setCodeSource(personneMorale.getCodeSource());
        personneMoraleDTO.setCodeSupport(personneMorale.getCodeSupport());
        personneMoraleDTO.setStatutJuridique(personneMorale.getStatutJuridique());
        personneMoraleDTO.setDateModificationNom(personneMorale.getDateModificationNom());
        personneMoraleDTO.setNumerosIdent(NumeroIdentTransform.bo2Dto(personneMorale.getNumerosIdent()));
        personneMoraleDTO.setSiteCreation(personneMorale.getSiteCreation());
        personneMoraleDTO.setSiteModification(personneMorale.getSiteModification());
        personneMoraleDTO.setSegmentations(SegmentationTransform.bo2Dto(personneMorale.getSegmentations()));
    
    }

    
    /**
     * Transforms a BO to a DTO.
     * 
     * @param personneMoraleLight bo
     * @param personneMoraleDTO dto
     */
     public static void bo2DtoLight(PersonneMoraleLight personneMoraleLight,
        PersonneMoraleDTO personneMoraleDTO){
    
        // Fill in with the right data
        personneMoraleDTO.setGin(personneMoraleLight.getGin());
        personneMoraleDTO.setVersion(personneMoraleLight.getVersion());
        personneMoraleDTO.setNom(personneMoraleLight.getNom());
        personneMoraleDTO.setStatut(personneMoraleLight.getStatut());
        personneMoraleDTO.setDateModificationStatut(personneMoraleLight.getDateModificationStatut());
        personneMoraleDTO.setActiviteLocal(personneMoraleLight.getActiviteLocal());
        personneMoraleDTO.setGinFusion(personneMoraleLight.getGinFusion());
        personneMoraleDTO.setDateFusion(personneMoraleLight.getDateFusion());
        personneMoraleDTO.setSignatureFusion(personneMoraleLight.getSignatureFusion());
        personneMoraleDTO.setDateCreation(personneMoraleLight.getDateCreation());
        personneMoraleDTO.setSignatureCreation(personneMoraleLight.getSignatureCreation());
        personneMoraleDTO.setDateModification(personneMoraleLight.getDateModification());
        personneMoraleDTO.setSignatureModification(personneMoraleLight.getSignatureModification());
        personneMoraleDTO.setCodeIndustrie(personneMoraleLight.getCodeIndustrie());
        personneMoraleDTO.setTypeDemarchage(personneMoraleLight.getTypeDemarchage());
        personneMoraleDTO.setSiteInternet(personneMoraleLight.getSiteInternet());
        personneMoraleDTO.setIndictNom(personneMoraleLight.getIndictNom());
        personneMoraleDTO.setCieGest(personneMoraleLight.getCieGest());
        personneMoraleDTO.setCodeSource(personneMoraleLight.getCodeSource());
        personneMoraleDTO.setCodeSupport(personneMoraleLight.getCodeSupport());
        personneMoraleDTO.setStatutJuridique(personneMoraleLight.getStatutJuridique());
        personneMoraleDTO.setDateModificationNom(personneMoraleLight.getDateModificationNom());
        personneMoraleDTO.setSiteCreation(personneMoraleLight.getSiteCreation());
        personneMoraleDTO.setSiteModification(personneMoraleLight.getSiteModification());
    
    }
    
    /*PROTECTED REGION ID(_QjebMODkEd-TxbwIaEembQ u m - Tr) ENABLED START*/

    public static void bo2Dto(PersonneMorale personneMorale, PersonneMoraleDTO personneMoraleDTO) throws JrafDomainException {

        bo2DtoLight(personneMorale, personneMoraleDTO);
        bo2DtoLink(personneMorale, personneMoraleDTO);
    }

    /**
     * Ask both method : bo2DtoLight & bo2DtoLink
     * 
     * @param PersonneMorale
     * @return
     * @throws JrafDomainException
     */
    public static PersonneMoraleDTO bo2Dto(PersonneMorale pPersonneMorale) throws JrafDomainException {
        
        // instanciation du DTO
        PersonneMoraleDTO personneMoraleDTO = bo2DtoLight(pPersonneMorale);
        bo2DtoLink(pPersonneMorale, personneMoraleDTO);

        // on retourne le dto
        return personneMoraleDTO;
    }
    
    /**
     * Ask both method : bo2DtoLight & bo2DtoLinkWithoutUnitializedProxiesCollections
     * Use case : retrieving only collections that have been initialized in the request
     * @param PersonneMorale
     * @return
     * @throws JrafDomainException
     */
    public static PersonneMoraleDTO bo2DtoWithoutUnitializedProxiesCollections(PersonneMorale pPersonneMorale) throws JrafDomainException {
        
        // instanciation du DTO
        PersonneMoraleDTO personneMoraleDTO = bo2DtoLight(pPersonneMorale);
        bo2DtoLinkWithoutUnitializedProxiesCollections(pPersonneMorale, personneMoraleDTO);
        addEnfants(pPersonneMorale, personneMoraleDTO);

        // on retourne le dto
        return personneMoraleDTO;
    }

    /**
     * Adds children to personne morale if it is a firm.
     * 
     * @param personneMorale entity having children
     * @param personneMoraleDTO DTO to fill in with children
     */
	private static void addEnfants(PersonneMorale personneMorale, PersonneMoraleDTO personneMoraleDTO) {

		// Only firms need children, others do not make sense
		if (personneMorale.getClass().equals(Etablissement.class)
				&& personneMoraleDTO.getClass().equals(EtablissementDTO.class)) {
			EtablissementTransform.bo2DtoEnfants((Etablissement) personneMorale, (EtablissementDTO) personneMoraleDTO);
		}
	}

	// Set to null every collections that are not initialized through the request in the DAO
    public static void bo2DtoLinkWithoutUnitializedProxiesCollections(PersonneMorale pPersonneMorale, PersonneMoraleDTO pPersonneMoraleDTO) {
    	// Lien avec NUMERO_IDENT
    	if (!Hibernate.isInitialized(pPersonneMorale.getNumerosIdent())){
    		pPersonneMoraleDTO.setNumerosIdent(null);
    	}
    	else if ( pPersonneMorale.getNumerosIdent() != null) {
            try {
                // LOGGER.warn("Lien avec NUMERO_IDENT");
                pPersonneMoraleDTO.setNumerosIdent(NumeroIdentTransform.bo2Dto(pPersonneMorale.getNumerosIdent()));
                // LOGGER.warn("pPersonneMorale.getNumerosIdent().size()="+pPersonneMorale.getNumerosIdent().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec PROFIL_FIRME
        if ( pPersonneMorale.getProfilFirme() != null) {
            try {
                // LOGGER.warn("Lien avec PROFIL_FIRME");
                pPersonneMoraleDTO.setProfilFirme(ProfilFirmeTransform.bo2DtoLight(pPersonneMorale.getProfilFirme()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec SYNONYME
        if (!Hibernate.isInitialized(pPersonneMorale.getSynonymes())){
    		pPersonneMoraleDTO.setSynonymes(null);
    	}
    	else if ( pPersonneMorale.getSynonymes() != null) {
            try {
                // LOGGER.warn("Lien avec SYNONYME");
                pPersonneMoraleDTO.setSynonymes(SynonymeTransform.bo2Dto(pPersonneMorale.getSynonymes()));
                // LOGGER.warn("pPersonneMoraleDTO.getSynonymes().size()="+pPersonneMoraleDTO.getSynonymes().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec CHIFFRE
        if (!Hibernate.isInitialized(pPersonneMorale.getChiffres())){
    		pPersonneMoraleDTO.setChiffres(null);
    	}
    	else if ( pPersonneMorale.getChiffres() != null) {
            try {
                // LOGGER.warn("Lien avec CHIFFRE");
                pPersonneMoraleDTO.setChiffres(ChiffreTransform.bo2Dto(pPersonneMorale.getChiffres()));
                // LOGGER.warn("pPersonneMoraleDTO.getChiffres().size()="+pPersonneMoraleDTO.getChiffres().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec SEGMENTATION
        if (!Hibernate.isInitialized(pPersonneMorale.getSegmentations())){
    		pPersonneMoraleDTO.setSegmentations(null);
    	}
    	else if ( pPersonneMorale.getSegmentations() != null) {
            try {
                // LOGGER.warn("Lien avec SEGMENTATION");
                pPersonneMoraleDTO.setSegmentations(SegmentationTransform.bo2Dto(pPersonneMorale.getSegmentations()));
                // LOGGER.warn("pPersonneMoraleDTO.getSegmentationDTO().size()="+pPersonneMoraleDTO.getSegmentations().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec ADR_POST
        if (!Hibernate.isInitialized(pPersonneMorale.getPostalAddresses())){
    		pPersonneMoraleDTO.setPostalAddresses(null);
    	}
    	else if ( pPersonneMorale.getPostalAddresses() != null) {
            try {
                // LOGGER.warn("Lien avec ADR_POST");
                pPersonneMoraleDTO.setPostalAddresses(PostalAddressTransform.bo2DtoLight(pPersonneMorale.getPostalAddresses()));
                // LOGGER.warn("pPersonneMoraleDTO.getPostalAddresses().size()="+pPersonneMoraleDTO.getPostalAddresses().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec TELECOMS
        if (!Hibernate.isInitialized(pPersonneMorale.getTelecoms())){
    		pPersonneMoraleDTO.setTelecoms(null);
    	}
    	else if (pPersonneMorale.getTelecoms() != null) {
            try {
                // LOGGER.warn("Lien avec TELECOMS");
                pPersonneMoraleDTO.setTelecoms(TelecomsTransform.bo2Dto(pPersonneMorale.getTelecoms()));
                // LOGGER.warn("PersonneMoraleDTO.getTelecomsDTO().size()="+PersonneMoraleDTO.getTelecomsDTO().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec BUSINESS_ROLE
        if (!Hibernate.isInitialized(pPersonneMorale.getBusinessRoles())){
    		pPersonneMoraleDTO.setBusinessRoles(null);
    	}
    	else if (pPersonneMorale.getBusinessRoles() != null) {
            try {
                // LOGGER.warn("Lien avec BUSINESS_ROLE");
                pPersonneMoraleDTO.setBusinessRoles(BusinessRoleTransform.bo2Dto(pPersonneMorale.getBusinessRoles()));
                // LOGGER.warn("PersonneMoraleDTO.getBusinessRoleDTO().size()="+PersonneMoraleDTO.getBusinessRoleDTO().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec PM_ZONE
        if (!Hibernate.isInitialized(pPersonneMorale.getPmZones())){
    		pPersonneMoraleDTO.setPmZones(null);
    	}
    	else if (pPersonneMorale.getPmZones() != null) {
            try {
                // LOGGER.warn("Lien avec PM_ZONE");
                pPersonneMoraleDTO.setPmZones(PmZoneTransform.bo2Dto(pPersonneMorale.getPmZones()));
                // LOGGER.warn("pPersonneMoraleDTO.getPmZones().size()="+pPersonneMoraleDTO.getPmZones().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec EMAILS
        if (!Hibernate.isInitialized(pPersonneMorale.getEmails())){
    		pPersonneMoraleDTO.setEmails(null);
    	}
    	else if (pPersonneMorale.getEmails() != null) {
            try {
                // LOGGER.warn("Lien avec EMAILS");
                pPersonneMoraleDTO.setEmails(EmailTransform.bo2Dto(pPersonneMorale.getEmails()));
                // LOGGER.warn("pPersonneMoraleDTO.getEmails().size()="+pPersonneMoraleDTO.getEmails().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec PROFIL_MERE
        if (pPersonneMorale.getProfils() != null) {
            try {
                // LOGGER.warn("Lien avec PROFIL_MERE");
                pPersonneMoraleDTO.setProfils(Profil_mereTransform.bo2Dto(pPersonneMorale.getProfils()));
                // LOGGER.warn("pPersonneMoraleDTO.getProfils().size()="+pPersonneMoraleDTO.getProfils().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec MEMBRE
        // NE JAMAIS CHARGER LES MEMBRES PAR LE BO CAR SVT TROP NOMBREUX (si nécessaire, les charger par MembreDAO)
        /*
        if ( pPersonneMorale.getMembres() != null) {
            try {
                // LOGGER.warn("Lien avec MEMBRE");
                pPersonneMoraleDTO.setMembres(MembreTransform.bo2Dto(pPersonneMorale.getMembres()));
                // LOGGER.warn("pPersonneMoraleDTO.getMembres().size()="+pPersonneMoraleDTO.getMembres().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        */
        
        // Lien avec Parent
        if (pPersonneMorale.getParent() != null) {
            try {
                // LOGGER.warn("Lien avec PARENT");
                pPersonneMoraleDTO.setParent(PersonneMoraleTransform.bo2DtoLight(pPersonneMorale.getParent()));
                // LOGGER.warn("pPersonneMoraleDTO.getParent().size()="+pPersonneMoraleDTO.getParent().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
            // Parent du parent (Grand-papa)
            if (pPersonneMorale.getParent().getParent() != null) {
                try {
                    pPersonneMoraleDTO.getParent().setParent(PersonneMoraleTransform.bo2DtoLight(pPersonneMorale.getParent().getParent()));
                } catch (JrafDomainException e) {
                    if (LOGGER.isErrorEnabled()) {
                        LOGGER.error(e);
                    }
                }
                // Parent du parent du parent (Arrière grand-papa)
                if (pPersonneMorale.getParent().getParent().getParent() != null) {
                    try {
                        pPersonneMoraleDTO.getParent().getParent().setParent(PersonneMoraleTransform.bo2DtoLight(pPersonneMorale.getParent().getParent().getParent()));
                    } catch (JrafDomainException e) {
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error(e);
                        }
                    }
                }
                
            }
            
        }
        
     // Lien avec PERSONNE_MORALE GERANTES
        if (!Hibernate.isInitialized(pPersonneMorale.getPersonnesMoralesGerantes())){
    		pPersonneMoraleDTO.setPersonnesMoralesGerantes(null);
    	}
    	else if (pPersonneMorale.getPersonnesMoralesGerantes() != null) {
            try {
            	// LOGGER.warn("Lien avec PERSONNE_MORALE GERANTE");
            	pPersonneMoraleDTO.setPersonnesMoralesGerantes(GestionPMTransform.bo2Dto(pPersonneMorale.getPersonnesMoralesGerantes()));
	
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        
     // Lien avec PERSONNE_MORALE GEREES
        if (!Hibernate.isInitialized(pPersonneMorale.getPersonnesMoralesGerees())){
    		pPersonneMoraleDTO.setPersonnesMoralesGerees(null);
    	}
    	else if (pPersonneMorale.getPersonnesMoralesGerees() != null) {
            try {
            	
                // LOGGER.warn("Lien avec PERSONNE_MORALE GEREES");
            	pPersonneMoraleDTO.setPersonnesMoralesGerees(GestionPMTransform.bo2Dto(pPersonneMorale.getPersonnesMoralesGerees()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        
     // Lien avec SBT
        if ( pPersonneMorale.getSelfBookingTool() != null) {
            try {
                pPersonneMoraleDTO.setSelfBookingTool(SelfBookingToolTransform.bo2DtoLight(pPersonneMorale.getSelfBookingTool()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("COUILLES " + e);
                }
            }
        }
    }

    public static void bo2DtoLink(PersonneMorale pPersonneMorale, PersonneMoraleDTO pPersonneMoraleDTO) {

        // Lien avec NUMERO_IDENT
    	if ( pPersonneMorale.getNumerosIdent() != null) {
            try {
                // LOGGER.warn("Lien avec NUMERO_IDENT");
                pPersonneMoraleDTO.setNumerosIdent(NumeroIdentTransform.bo2Dto(pPersonneMorale.getNumerosIdent()));
                // LOGGER.warn("pPersonneMorale.getNumerosIdent().size()="+pPersonneMorale.getNumerosIdent().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec PROFIL_FIRME
        if ( pPersonneMorale.getProfilFirme() != null) {
            try {
                // LOGGER.warn("Lien avec PROFIL_FIRME");
                pPersonneMoraleDTO.setProfilFirme(ProfilFirmeTransform.bo2DtoLight(pPersonneMorale.getProfilFirme()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec SYNONYME
        if ( pPersonneMorale.getSynonymes() != null) {
            try {
                // LOGGER.warn("Lien avec SYNONYME");
                pPersonneMoraleDTO.setSynonymes(SynonymeTransform.bo2Dto(pPersonneMorale.getSynonymes()));
                // LOGGER.warn("pPersonneMoraleDTO.getSynonymes().size()="+pPersonneMoraleDTO.getSynonymes().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec CHIFFRE
        if ( pPersonneMorale.getChiffres() != null) {
            try {
                // LOGGER.warn("Lien avec CHIFFRE");
                pPersonneMoraleDTO.setChiffres(ChiffreTransform.bo2Dto(pPersonneMorale.getChiffres()));
                // LOGGER.warn("pPersonneMoraleDTO.getChiffres().size()="+pPersonneMoraleDTO.getChiffres().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec SEGMENTATION
        if ( pPersonneMorale.getSegmentations() != null) {
            try {
                // LOGGER.warn("Lien avec SEGMENTATION");
                pPersonneMoraleDTO.setSegmentations(SegmentationTransform.bo2Dto(pPersonneMorale.getSegmentations()));
                // LOGGER.warn("pPersonneMoraleDTO.getSegmentationDTO().size()="+pPersonneMoraleDTO.getSegmentations().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec ADR_POST
        if ( pPersonneMorale.getPostalAddresses() != null) {
            try {
                // LOGGER.warn("Lien avec ADR_POST");
                pPersonneMoraleDTO.setPostalAddresses(PostalAddressTransform.bo2DtoLight(pPersonneMorale.getPostalAddresses()));
                // LOGGER.warn("pPersonneMoraleDTO.getPostalAddresses().size()="+pPersonneMoraleDTO.getPostalAddresses().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec TELECOMS
        if (pPersonneMorale.getTelecoms() != null) {
            try {
                // LOGGER.warn("Lien avec TELECOMS");
                pPersonneMoraleDTO.setTelecoms(TelecomsTransform.bo2Dto(pPersonneMorale.getTelecoms()));
                // LOGGER.warn("PersonneMoraleDTO.getTelecomsDTO().size()="+PersonneMoraleDTO.getTelecomsDTO().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec BUSINESS_ROLE
        if (pPersonneMorale.getBusinessRoles() != null) {
            try {
                // LOGGER.warn("Lien avec BUSINESS_ROLE");
                pPersonneMoraleDTO.setBusinessRoles(BusinessRoleTransform.bo2Dto(pPersonneMorale.getBusinessRoles()));
                // LOGGER.warn("PersonneMoraleDTO.getBusinessRoleDTO().size()="+PersonneMoraleDTO.getBusinessRoleDTO().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec PM_ZONE
        if (pPersonneMorale.getPmZones() != null) {
            try {
                // LOGGER.warn("Lien avec PM_ZONE");
                pPersonneMoraleDTO.setPmZones(PmZoneTransform.bo2Dto(pPersonneMorale.getPmZones()));
                // LOGGER.warn("pPersonneMoraleDTO.getPmZones().size()="+pPersonneMoraleDTO.getPmZones().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec EMAILS
        if (pPersonneMorale.getEmails() != null) {
            try {
                // LOGGER.warn("Lien avec EMAILS");
                pPersonneMoraleDTO.setEmails(EmailTransform.bo2Dto(pPersonneMorale.getEmails()));
                // LOGGER.warn("pPersonneMoraleDTO.getEmails().size()="+pPersonneMoraleDTO.getEmails().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec PROFIL_MERE
        if (pPersonneMorale.getProfils() != null) {
            try {
                // LOGGER.warn("Lien avec PROFIL_MERE");
                pPersonneMoraleDTO.setProfils(Profil_mereTransform.bo2Dto(pPersonneMorale.getProfils()));
                // LOGGER.warn("pPersonneMoraleDTO.getProfils().size()="+pPersonneMoraleDTO.getProfils().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec MEMBRE
        // NE JAMAIS CHARGER LES MEMBRES PAR LE BO CAR SVT TROP NOMBREUX (si nécessaire, les charger par MembreDAO)
        /*
        if ( pPersonneMorale.getMembres() != null) {
            try {
                // LOGGER.warn("Lien avec MEMBRE");
                pPersonneMoraleDTO.setMembres(MembreTransform.bo2Dto(pPersonneMorale.getMembres()));
                // LOGGER.warn("pPersonneMoraleDTO.getMembres().size()="+pPersonneMoraleDTO.getMembres().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        */
        
        // Lien avec Parent
        if (pPersonneMorale.getParent() != null) {
            try {
                // LOGGER.warn("Lien avec PARENT");
                pPersonneMoraleDTO.setParent(PersonneMoraleTransform.bo2DtoLight(pPersonneMorale.getParent()));
                // LOGGER.warn("pPersonneMoraleDTO.getParent().size()="+pPersonneMoraleDTO.getParent().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
            // Parent du parent (Grand-papa)
            if (pPersonneMorale.getParent().getParent() != null) {
                try {
                    pPersonneMoraleDTO.getParent().setParent(PersonneMoraleTransform.bo2DtoLight(pPersonneMorale.getParent().getParent()));
                } catch (JrafDomainException e) {
                    if (LOGGER.isErrorEnabled()) {
                        LOGGER.error(e);
                    }
                }
                // Parent du parent du parent (Arrière grand-papa)
                if (pPersonneMorale.getParent().getParent().getParent() != null) {
                    try {
                        pPersonneMoraleDTO.getParent().getParent().setParent(PersonneMoraleTransform.bo2DtoLight(pPersonneMorale.getParent().getParent().getParent()));
                    } catch (JrafDomainException e) {
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error(e);
                        }
                    }
                }
                
            }
            
        }
        
     // Lien avec PERSONNE_MORALE GERANTES
        if (pPersonneMorale.getPersonnesMoralesGerantes() != null) {
            try {
            	// LOGGER.warn("Lien avec PERSONNE_MORALE GERANTE");
            	pPersonneMoraleDTO.setPersonnesMoralesGerantes(GestionPMTransform.bo2Dto(pPersonneMorale.getPersonnesMoralesGerantes()));
	
                // LOGGER.warn("pPersonneMoraleDTO.getMembres().size()="+pPersonneMoraleDTO.getMembres().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        
     // Lien avec PERSONNE_MORALE GEREES
        if (pPersonneMorale.getPersonnesMoralesGerees() != null) {
            try {
            	
                // LOGGER.warn("Lien avec PERSONNE_MORALE GEREES");
            	pPersonneMoraleDTO.setPersonnesMoralesGerees(GestionPMTransform.bo2Dto(pPersonneMorale.getPersonnesMoralesGerees()));
                // LOGGER.warn("pPersonneMoraleDTO.getMembres().size()="+pPersonneMoraleDTO.getMembres().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        
    }
    
    public static void dto2BoLink(EntityManager entityManager, PersonneMoraleDTO pmDTO, PersonneMorale pm) throws JrafDomainException {
        
        if (pmDTO.getParent() != null && pmDTO.getParent().getGin() != null) {
            
            PersonneMorale proxyParent = entityManager.getReference(PersonneMorale.class, pmDTO.getParent().getGin());
            pm.setParent(proxyParent);
        }
    }
    
	public static PersonneMorale dto2BoLinkAdrPost(PersonneMoraleDTO pmDTO) throws JrafDomainException {
		
		PersonneMorale pm = dto2BoLight(pmDTO);
		if (pm != null && pm.getPostalAddresses() == null)
		{
			pm.setPostalAddresses(new HashSet<PostalAddress>());
		}
		if (pm != null && pmDTO.getPostalAddresses() != null && !pmDTO.getPostalAddresses().isEmpty())
		{
			Iterator<PostalAddressDTO> it = pmDTO.getPostalAddresses().iterator();
			while (it.hasNext())
			{
				PostalAddress add = PostalAddressTransform.dto2BoPM(it.next());
				pm.getPostalAddresses().add(add);
			}								
		}
		
		return pm;
	}

    public static List<PersonneMoraleLightDTO> stringsToPersonneMoraleDtoList(List<String[]> stringsList) throws JrafDomainException {
	    if (stringsList == null) {
            throw new JrafDomainRollbackException("Strings list should not be null.");
        }

        List<PersonneMoraleLightDTO> personneMoraleLightDTOS = new ArrayList<>();

        for (String[] strings : stringsList) {
            personneMoraleLightDTOS.add(stringsToPersonneMoraleDto(strings));
        }

        return personneMoraleLightDTOS;
    }

	public static PersonneMoraleLightDTO stringsToPersonneMoraleDto(String[] strings) throws JrafDomainException {
        PersonneMoraleLightDTO personneMoraleLightDTO;

        if (strings == null || strings.length != 2) {
            throw new JrafDomainRollbackException("Unable to convert strings to PersonneMoraleLightDTO. Missing strings.");
        }

        String sGin = null;
        PersonneMoraleTypeEnum type = null;

        for (String string : strings) {
            Optional<PersonneMoraleTypeEnum> personneMoraleTypeEnum = PersonneMoraleTypeEnum.fromCode(string);
            if (personneMoraleTypeEnum.isPresent()) {
                // is type
                type = personneMoraleTypeEnum.get();
            } else {
                // is gin
                sGin = string;
            }
        }

        return new PersonneMoraleLightDTO(sGin, type);
    }

	private static void dto2BoAddLinks(PersonneMorale pm,
			PersonneMoraleDTO pmDTO) 
	{
		

		
	}

}

