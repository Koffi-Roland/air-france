package com.airfrance.repind.dto.agence;

/*PROTECTED REGION ID(_0VRt4Gk1EeGhB9497mGnHw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.bean.AgenceBean;
import com.airfrance.repind.dto.adresse.EmailTransform;
import com.airfrance.repind.dto.adresse.PostalAddressTransform;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.TelecomsTransform;
import com.airfrance.repind.dto.firme.*;
import com.airfrance.repind.dto.profil.ProfilFirmeTransform;
import com.airfrance.repind.dto.profil.Profil_mereDTO;
import com.airfrance.repind.dto.profil.Profil_mereTransform;
import com.airfrance.repind.dto.role.BusinessRoleTransform;
import com.airfrance.repind.dto.zone.PmZoneTransform;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.agence.AgenceLight;
import com.airfrance.repind.entity.firme.GestionPM;
import com.airfrance.repind.entity.profil.Profil_mere;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : AgenceTransform.java</p>
 * transformation bo <-> dto pour un(e) Agence
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class AgenceTransform {

    /*PROTECTED REGION ID(_0VRt4Gk1EeGhB9497mGnHw u var - Tr) ENABLED START*/
    private static Log LOGGER = LogFactory.getLog(AgenceTransform.class);

    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private AgenceTransform() {
    }
    /**
     * dto -> bo for a Agence
     * @param agenceDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Agence dto2BoLight(AgenceDTO agenceDTO) throws JrafDomainException {
        return (Agence)PersonneMoraleTransform.dto2BoLight(agenceDTO);
    }

    /**
     * dto -> bo for a agence
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param agenceDTO dto
     * @param agence bo
     */
    public static void dto2BoLight(AgenceDTO agenceDTO, Agence agence) {
    
        /*PROTECTED REGION ID(dto2BoLight_0VRt4Gk1EeGhB9497mGnHw) ENABLED START*/

        dto2BoLightImpl(agenceDTO, agence);

        /*PROTECTED REGION END*/
    }
    
    public static Agence dto2BoLinkAdrPost(AgenceDTO agenceDTO) throws JrafDomainException {
        return (Agence)PersonneMoraleTransform.dto2BoLinkAdrPost(agenceDTO);
    }
    
    /**
     * dto -> bo implementation for a agence
     * @param agenceDTO dto
     * @param agence bo
     */
    private static void dto2BoLightImpl(AgenceDTO agenceDTO, Agence agence){
    
        // superclass property
        PersonneMoraleTransform.dto2BoLight(agenceDTO, agence);
        // property of AgenceDTO
        agence.setDateModifiSTAIATA(agenceDTO.getDateModifiSTAIATA());
        agence.setDateAgrement(agenceDTO.getDateAgrement());
        agence.setDateDebut(agenceDTO.getDateDebut());
        agence.setDateFin(agenceDTO.getDateFin());
        agence.setDateRadiation(agenceDTO.getDateRadiation());
        agence.setVersion(agenceDTO.getVersion());
        agence.setAgenceRA2(agenceDTO.getAgenceRA2());
        agence.setBsp(agenceDTO.getBsp());
        agence.setCible(agenceDTO.getCible());
        agence.setCodeVilleIso(agenceDTO.getCodeVilleIso());
        agence.setCodeService(agenceDTO.getCodeService());
        agence.setDomaine(agenceDTO.getDomaine());
        agence.setEnvoieSI(agenceDTO.getEnvoieSI());
        agence.setExclusifGrdCpt(agenceDTO.getExclusifGrdCpt());
        agence.setGsa(agenceDTO.getGsa());
        agence.setInfra(agenceDTO.getInfra());
        agence.setLocalisation(agenceDTO.getLocalisation());
        agence.setNumeroIATAMere(agenceDTO.getNumeroIATAMere());
        agence.setObservation(agenceDTO.getObservation());
        agence.setSousDomaine(agenceDTO.getSousDomaine());
        agence.setStatutIATA(agenceDTO.getStatutIATA());
        agence.setType(agenceDTO.getType());
        agence.setTypeAgrement(agenceDTO.getTypeAgrement());
        agence.setTypeVente(agenceDTO.getTypeVente());
        agence.setZoneChalandise(agenceDTO.getZoneChalandise());
        agence.setIataStationAirportCode(agenceDTO.getIataStationAirportCode());
        agence.setForcingUpdate(agenceDTO.getForcingUpdate());
    }

    /**
     * bo -> dto for a agence
     * @param agence bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static AgenceDTO bo2DtoLight(Agence agence) throws JrafDomainException {
        return (AgenceDTO)PersonneMoraleTransform.bo2DtoLight(agence);
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param agence bo
     * @param agenceDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLight(
        Agence agence,
        AgenceDTO agenceDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_0VRt4Gk1EeGhB9497mGnHw) ENABLED START*/

        bo2DtoLightImpl(agence, agenceDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param agence bo
     * @param agenceDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImpl(Agence agence,
        AgenceDTO agenceDTO) throws JrafDomainException{
    
        // superclass property
        PersonneMoraleTransform.bo2DtoLight(agence, agenceDTO);

        // simple properties
        agenceDTO.setDateModifiSTAIATA(agence.getDateModifiSTAIATA());
        agenceDTO.setDateAgrement(agence.getDateAgrement());
        agenceDTO.setDateDebut(agence.getDateDebut());
        agenceDTO.setDateFin(agence.getDateFin());
        agenceDTO.setDateRadiation(agence.getDateRadiation());
        agenceDTO.setVersion(agence.getVersion());
        agenceDTO.setAgenceRA2(agence.getAgenceRA2());
        agenceDTO.setBsp(agence.getBsp());
        agenceDTO.setCible(agence.getCible());
        agenceDTO.setCodeVilleIso(agence.getCodeVilleIso());
        agenceDTO.setCodeService(agence.getCodeService());
        agenceDTO.setDomaine(agence.getDomaine());
        agenceDTO.setEnvoieSI(agence.getEnvoieSI());
        agenceDTO.setExclusifGrdCpt(agence.getExclusifGrdCpt());
        agenceDTO.setGsa(agence.getGsa());
        agenceDTO.setInfra(agence.getInfra());
        agenceDTO.setLocalisation(agence.getLocalisation());
        agenceDTO.setNumeroIATAMere(agence.getNumeroIATAMere());
        agenceDTO.setObservation(agence.getObservation());
        agenceDTO.setSousDomaine(agence.getSousDomaine());
        agenceDTO.setStatutIATA(agence.getStatutIATA());
        agenceDTO.setType(agence.getType());
        agenceDTO.setTypeAgrement(agence.getTypeAgrement());
        agenceDTO.setTypeVente(agence.getTypeVente());
        agenceDTO.setZoneChalandise(agence.getZoneChalandise());
        agenceDTO.setIataStationAirportCode(agence.getIataStationAirportCode());
        agenceDTO.setForcingUpdate(agence.getForcingUpdate());                
    
    }
    
    /*PROTECTED REGION ID(_0VRt4Gk1EeGhB9497mGnHw u m - Tr) ENABLED START*/

    public static void bo2Dto(Agence agence, AgenceDTO agenceDTO) throws JrafDomainException {
        bo2DtoLight(agence, agenceDTO);
        bo2DtoLink(agence, agenceDTO);
    }
    
    public static void bo2DtoForProvidePublicAgencyData(Agence agence, AgenceDTO agenceDTO) throws JrafDomainException {
        bo2DtoLight(agence, agenceDTO);

        // Lien avec SEGMENTATION
        if (agence.getSegmentations() != null) {
            try {
                LOGGER.debug("Lien avec SEGMENTATION");
                agenceDTO.setSegmentations(SegmentationTransform.bo2Dto(agence.getSegmentations()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    }

    public static void dto2Bo(AgenceDTO agenceDTO, Agence agence) {
        dto2BoLight(agenceDTO, agence);
        dto2BoLink(agenceDTO, agence);
    }

    public static void dto2BoLink(AgenceDTO agenceDTO, Agence agence) {

    	// Lien avec OFFICE ID
        if (agenceDTO.getOffices() != null) {
            try {
                LOGGER.debug("Lien avec OFFICE_ID");
                agence.setOffices(OfficeIDTransform.dto2Bo(agenceDTO.getOffices()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    	
        // Lien avec LETTRE COMPTE
        if (agenceDTO.getLettreCompte() != null) {
            try {
                LOGGER.debug("Lien avec LETTRE COMPTEUR");
                agence.setLettreCompte(LettreCompteTransform.dto2Bo(agenceDTO.getLettreCompte()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        // Lien avec MEMBRE RESEAU
        if (agenceDTO.getReseaux() != null) {
            try {
                LOGGER.debug("Lien avec MEMBRE RESEAU");
                agence.setReseaux(MembreReseauTransform.dto2Bo(agenceDTO.getReseaux()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        
        //PM ZONES
        if (agenceDTO.getPmZones() != null) {
            try {
                // LOGGER.warn("Lien avec PM_ZONE");
                agence.setPmZones(PmZoneTransform.dto2Bo(agenceDTO.getPmZones()));
                // LOGGER.warn("agenceDTO.getPmZones().size()="+agenceDTO.getPmZones().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        
        if(!CollectionUtils.isEmpty(agenceDTO.getNumerosIdent())) {
        	try {
        		LOGGER.debug("Lien avec NUMERO_IDENT");
        		agence.setNumerosIdent(NumeroIdentTransform.dto2Bo(agenceDTO.getNumerosIdent()));
        	} catch(Exception e) {
        		if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
        	}
        }
        
        if(!CollectionUtils.isEmpty(agenceDTO.getSegmentations())) {
        	try {
        		LOGGER.debug("LIEN avec SEGMENTATIONS");
        		agence.setSegmentations(SegmentationTransform.dto2Bo(agenceDTO.getSegmentations()));
        	} catch(Exception e) {
        		if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
        	}
        }
        
        if(!CollectionUtils.isEmpty(agenceDTO.getPersonnesMoralesGerantes())) {
        	try {
        		LOGGER.debug("LIEN avec GESTION_PM (gérants)");
        		Set<GestionPM> gestions = new HashSet<>();
        		for(GestionPMDTO pm : agenceDTO.getPersonnesMoralesGerantes()) {
        			gestions.add(GestionPMTransform.dto2Bo(pm));
        		}
        		agence.setPersonnesMoralesGerantes(gestions);
        	} catch(Exception e) {
        	    LOGGER.error(e);
        	}
        }  
        
        if(!CollectionUtils.isEmpty(agenceDTO.getTelecoms())) {
        	try {
        		LOGGER.debug("LIEN avec TELECOMS");
        		Set<Telecoms> telecoms = new HashSet<Telecoms>();
        		for(TelecomsDTO telecom : agenceDTO.getTelecoms()) {
        			telecoms.add(TelecomsTransform.dto2Bo(telecom));
        		}
        		agence.setTelecoms(telecoms);
        	} catch(Exception e) {
        	    LOGGER.error(e);
        	}
        }
    }

    public static void dto2BoLinkMinimal(AgenceDTO agenceDTO, Agence agence) {

        // Lien avec OFFICE ID
        if (agenceDTO.getOffices() != null) {
            try {
                LOGGER.debug("Lien avec OFFICE_ID");
                agence.setOffices(OfficeIDTransform.dto2Bo(agenceDTO.getOffices()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

    }

    public static void bo2DtoLink(Agence agence, AgenceDTO agenceDTO) {
    	// Lien avec OFFICE ID
        if (agence.getOffices() != null) {
            try {
                LOGGER.debug("Lien avec OFFICE_ID");
                agenceDTO.setOffices(OfficeIDTransform.bo2Dto(agence.getOffices()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        // Lien avec LETTRE COMPTE
        if (agence.getLettreCompte() != null) {
            try {
                LOGGER.debug("Lien avec LETTRE COMPTEUR");
                agenceDTO.setLettreCompte(LettreCompteTransform.bo2Dto(agence.getLettreCompte()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        // Lien avec MEMBRE RESEAU
        if (agence.getReseaux() != null) {
            try {
                LOGGER.debug("Lien avec MEMBRE RESEAU");
                agenceDTO.setReseaux(MembreReseauTransform.bo2Dto(agence.getReseaux()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        // Lien avec MEMBRE
        if (agence.getMembres() != null) {
            try {
                LOGGER.debug("Lien avec MEMBRE");
                agenceDTO.setMembres(MembreTransform.bo2Dto(agence.getMembres()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }        
        // Lien avec SEGMENTATION
        if (agence.getSegmentations() != null) {
            try {
                LOGGER.debug("Lien avec SEGMENTATION");
                agenceDTO.setSegmentations(SegmentationTransform.bo2Dto(agence.getSegmentations()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        // Lien avec TELECOMS
        if (agence.getTelecoms() != null) {
            try {
                LOGGER.debug("Lien avec TELECOMS");
                agenceDTO.setTelecoms(TelecomsTransform.bo2Dto(agence.getTelecoms()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        // Lien avec EMAILS
        if (agence.getEmails() != null) {
            try {
                LOGGER.debug("Lien avec EMAILS");
                agenceDTO.setEmails(EmailTransform.bo2Dto(agence.getEmails()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        // Lien avec NUMEROIDENT
        if (agence.getNumerosIdent() != null) {
            try {
                LOGGER.debug("Lien avec NUMEROIDENT");
                agenceDTO.setNumerosIdent(NumeroIdentTransform.bo2Dto(agence.getNumerosIdent()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        //Lien avec PM_ZONE
        if (agence.getPmZones() != null) {
        	try {
        		LOGGER.debug("Lien avec PM_ZONE");
        		agenceDTO.setPmZones(PmZoneTransform.bo2Dto(agence.getPmZones()));
        	} catch (JrafDomainException e) {
        		if(LOGGER.isErrorEnabled()) {
        			LOGGER.error(e);
        		}
        	}
        }
        
        if(!CollectionUtils.isEmpty(agence.getPersonnesMoralesGerees())) {
        	try {
        		LOGGER.debug("Lien avec GESTION_PM (gérés)");
        		agenceDTO.setPersonnesMoralesGerantes(GestionPMTransform.bo2Dto(agence.getPersonnesMoralesGerees())); //We need to invert because of the implementation of the DS.
        	} catch (JrafDomainException e) {
        		if(LOGGER.isErrorEnabled()) {
        			LOGGER.error(e);
        		}
        	}
        }
        // Lien avec SYNONYME
        if (agence.getSynonymes() != null) {
            try {
                LOGGER.debug("Lien avec SYNONYME");
                agenceDTO.setSynonymes(SynonymeTransform.bo2Dto(agence.getSynonymes()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        // Lien avec ADR_POST
        if (!CollectionUtils.isEmpty(agence.getPostalAddresses())) {
            try {
                LOGGER.debug("Lien avec ADR_POST");
                agenceDTO.setPostalAddresses(PostalAddressTransform.bo2Dto(agence.getPostalAddresses()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        
    }

    public static void bo2DtoLinkMinimal(Agence agence, AgenceDTO agenceDTO) {
        // Lien avec OFFICE ID
        if (agence.getOffices() != null) {
            try {
                LOGGER.debug("Lien avec OFFICE_ID");
                agenceDTO.setOffices(OfficeIDTransform.bo2Dto(agence.getOffices()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    }
    
    /**
     * @param bos
     * @return dtos
     * @throws JrafDomainException
     */
    public static List<AgenceDTO> bo2DtoLight(List<Agence> bos) throws JrafDomainException {
        
        if (bos != null) {
            
            List<AgenceDTO> dtos = new ArrayList<AgenceDTO>();
            for (Agence bo : bos) {
                
                dtos.add(bo2DtoLight(bo));
            }
            return dtos;
            
        } else {
            
            return null;
        }
    }
    
    /**
     * Ask both method : bo2DtoLight & bo2DtoLinkWithoutUnitializedProxiesCollections
     * Use case : retrieving only collections that have been initialized in the request
     * @param Agence
     * @return
     * @throws JrafDomainException
     */
    public static AgenceDTO bo2DtoWithoutUnitializedProxiesCollections(Agence agence, List<String> scope) throws JrafDomainException {
        
        // instanciation du DTO
    	AgenceDTO agenceDTO = bo2DtoLight(agence);
        bo2DtoLinkWithoutUnitializedProxiesCollections(agence, agenceDTO, scope);

        // on retourne le dto
        return agenceDTO;
    }
    
 // Set to null every collections that are not initialized through the request in the DAO
    public static void bo2DtoLinkWithoutUnitializedProxiesCollections(Agence agence, AgenceDTO agenceDTO, List<String> scope) {
    	
    	// Lien avec NUMERO_IDENT
    	if (!Hibernate.isInitialized(agence.getNumerosIdent())){
    		agenceDTO.setNumerosIdent(null);
    	}
    	else if ( agence.getNumerosIdent() != null) {
            try {
                // LOGGER.warn("Lien avec NUMERO_IDENT");
                agenceDTO.setNumerosIdent(NumeroIdentTransform.bo2Dto(agence.getNumerosIdent()));
                // LOGGER.warn("agence.getNumerosIdent().size()="+agence.getNumerosIdent().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec PROFIL_FIRE
    	if ( agence.getProfilFirme() != null) {
            try {
                // LOGGER.warn("Lien avec PROFIL_FIRME");
                agenceDTO.setProfilFirme(ProfilFirmeTransform.bo2DtoLight(agence.getProfilFirme()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    	
    	// Lien avec PROFIL_MERE
    	if ( agence.getProfils() != null) {
            try {
                // LOGGER.warn("Lien avec PROFIL_MERE");
                agenceDTO.setProfils(Profil_mereTransform.bo2DtoLight(agence.getProfils()));
                
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    	
    	// Lien avec PROFIL CONTENTIEUX
    	if ( agence != null) {
            if (agence.getProfils() != null) 
			 {
				if (agence.getProfils().size() > 0)
				{        				        			
					for (Profil_mere pm : agence.getProfils()) 
					{						  						
						for (Profil_mereDTO pmDTO : agenceDTO.getProfils())
						{
							Profil_mereTransform.bo2DtoLink(pm, pmDTO);
						}
							
					}												
				}
			}
		 }
        

        // Lien avec SYNONYME
        if (!Hibernate.isInitialized(agence.getSynonymes())){
    		agenceDTO.setSynonymes(null);
    	}
    	else if ( agence.getSynonymes() != null) {
            try {
                // LOGGER.warn("Lien avec SYNONYME");
                agenceDTO.setSynonymes(SynonymeTransform.bo2Dto(agence.getSynonymes()));
                // LOGGER.warn("agenceDTO.getSynonymes().size()="+agenceDTO.getSynonymes().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        

        // Lien avec SEGMENTATION
        if (!Hibernate.isInitialized(agence.getSegmentations())){
    		agenceDTO.setSegmentations(null);
    	}
    	else if ( agence.getSegmentations() != null) {
            try {
                // LOGGER.warn("Lien avec SEGMENTATION");
                agenceDTO.setSegmentations(SegmentationTransform.bo2Dto(agence.getSegmentations()));
                // LOGGER.warn("agenceDTO.getSegmentationDTO().size()="+agenceDTO.getSegmentations().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec ADR_POST
        if (!Hibernate.isInitialized(agence.getPostalAddresses())){
    		agenceDTO.setPostalAddresses(null);
    	}
    	else if (agence.getPostalAddresses() != null) {
	            try {
	                // LOGGER.warn("Lien avec ADR_POST");
	                agenceDTO.setPostalAddresses(PostalAddressTransform.bo2Dto(agence.getPostalAddresses()));
	                // LOGGER.warn("agenceDTO.getPostalAddresses().size()="+agenceDTO.getPostalAddresses().size());
	            } catch (JrafDomainException e) {
	                if (LOGGER.isErrorEnabled()) {
	                    LOGGER.error(e);
	                }
	            }
	        }
        

        // Lien avec TELECOMS
        if (!Hibernate.isInitialized(agence.getTelecoms())){
    		agenceDTO.setTelecoms(null);
    	}
    	else if (agence.getTelecoms() != null) {
            try {
                // LOGGER.warn("Lien avec TELECOMS");
                agenceDTO.setTelecoms(TelecomsTransform.bo2Dto(agence.getTelecoms()));
                // LOGGER.warn("PersonneMoraleDTO.getTelecomsDTO().size()="+PersonneMoraleDTO.getTelecomsDTO().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec BUSINESS_ROLE
        if (!Hibernate.isInitialized(agence.getBusinessRoles())){
    		agenceDTO.setBusinessRoles(null);
    	}
    	else if (agence.getBusinessRoles() != null) {
            try {
                // LOGGER.warn("Lien avec BUSINESS_ROLE");
                agenceDTO.setBusinessRoles(BusinessRoleTransform.bo2Dto(agence.getBusinessRoles()));
                // LOGGER.warn("PersonneMoraleDTO.getBusinessRoleDTO().size()="+PersonneMoraleDTO.getBusinessRoleDTO().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec PM_ZONE
        if (!Hibernate.isInitialized(agence.getPmZones())){
    		agenceDTO.setPmZones(null);
    	}
    	else if (agence.getPmZones() != null) {
            try {
                // LOGGER.warn("Lien avec PM_ZONE");
                agenceDTO.setPmZones(PmZoneTransform.bo2Dto(agence.getPmZones()));
                // LOGGER.warn("agenceDTO.getPmZones().size()="+agenceDTO.getPmZones().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

        // Lien avec EMAILS
        if (!Hibernate.isInitialized(agence.getEmails())){
    		agenceDTO.setEmails(null);
    	}
    	else if (agence.getEmails() != null) {
            try {
                // LOGGER.warn("Lien avec EMAILS");
                agenceDTO.setEmails(EmailTransform.bo2Dto(agence.getEmails()));
                // LOGGER.warn("agenceDTO.getEmails().size()="+agenceDTO.getEmails().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        
        // Lien avec MEMBRE_RESEAU
        if (!Hibernate.isInitialized(agence.getReseaux())){
    		agenceDTO.setReseaux(null);
    	}
    	else if ( agence.getReseaux() != null) {
            try {
                // LOGGER.warn("Lien avec MEMBRE");
                agenceDTO.setReseaux((MembreReseauTransform.bo2Dto(agence.getReseaux())));
                // LOGGER.warn("agenceDTO.getMembres().size()="+agenceDTO.getMembres().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        
        // Lien avec PERSONNE_MORALE GEREE
        if (!Hibernate.isInitialized(agence.getPersonnesMoralesGerees())){
    		agenceDTO.setPersonnesMoralesGerees(null);
    	}
    	else if (agence.getPersonnesMoralesGerees() != null) {
            try {
            	// LOGGER.warn("Lien avec PERSONNE_MORALE GERANTE");
            	agenceDTO.setPersonnesMoralesGerees(GestionPMTransform.bo2Dto(agence.getPersonnesMoralesGerees()));
	
                // LOGGER.warn("agenceDTO.getMembres().size()="+agenceDTO.getMembres().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        
        // Lien avec CIE_ALLIEE
        if (!Hibernate.isInitialized(agence.getCompagniesAlliees())){
    		agenceDTO.setCompagniesAlliees(null);
    	}
    	else if (agence.getCompagniesAlliees() != null) {
            try {
            	// LOGGER.warn("Lien avec PERSONNE_MORALE GERANTE");
            	agenceDTO.setCompagniesAlliees(CompagnieAllieeTransform.bo2DtoLight(agence.getCompagniesAlliees()));
	
                // LOGGER.warn("agenceDTO.getMembres().size()="+agenceDTO.getMembres().size());
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
        
        // Lien avec OFFICE ID
        if (!Hibernate.isInitialized(agence.getOffices())){
    		agenceDTO.setOffices(null);
    	}else if (agence.getOffices() != null) {
            try {
                // LOGGER.warn("Lien avec OFFICE_ID");
                agenceDTO.setOffices(OfficeIDTransform.bo2Dto(agence.getOffices()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    }
    
    
    /**
     * Transforms a BO to a DTO.
     * 
     * @param agenceLight
     * @param agenceDTO
     */
	public static void bo2DtoLight(AgenceLight agenceLight, AgenceDTO agenceDTO) {
		
		if (agenceLight != null) {
			// Make the conversion
	        PersonneMoraleTransform.bo2DtoLight(agenceLight, agenceDTO);
	        agenceDTO.setDateModifiSTAIATA(agenceLight.getDateModifiSTAIATA());
	        agenceDTO.setDateAgrement(agenceLight.getDateAgrement());
	        agenceDTO.setDateDebut(agenceLight.getDateDebut());
	        agenceDTO.setDateFin(agenceLight.getDateFin());
	        agenceDTO.setDateRadiation(agenceLight.getDateRadiation());
	        agenceDTO.setVersion(agenceLight.getVersion());
	        agenceDTO.setAgenceRA2(agenceLight.getAgenceRA2());
	        agenceDTO.setBsp(agenceLight.getBsp());
	        agenceDTO.setCible(agenceLight.getCible());
	        agenceDTO.setCodeVilleIso(agenceLight.getCodeVilleIso());
	        agenceDTO.setCodeService(agenceLight.getCodeService());
	        agenceDTO.setDomaine(agenceLight.getDomaine());
	        agenceDTO.setEnvoieSI(agenceLight.getEnvoieSI());
	        agenceDTO.setExclusifGrdCpt(agenceLight.getExclusifGrdCpt());
	        agenceDTO.setGsa(agenceLight.getGsa());
	        agenceDTO.setInfra(agenceLight.getInfra());
	        agenceDTO.setLocalisation(agenceLight.getLocalisation());
	        agenceDTO.setNumeroIATAMere(agenceLight.getNumeroIATAMere());
	        agenceDTO.setObservation(agenceLight.getObservation());
	        agenceDTO.setSousDomaine(agenceLight.getSousDomaine());
	        agenceDTO.setStatutIATA(agenceLight.getStatutIATA());
	        agenceDTO.setType(agenceLight.getType());
	        agenceDTO.setTypeAgrement(agenceLight.getTypeAgrement());
	        agenceDTO.setTypeVente(agenceLight.getTypeVente());
	        agenceDTO.setZoneChalandise(agenceLight.getZoneChalandise());
	        agenceDTO.setIataStationAirportCode(agenceLight.getIataStationAirportCode());
	        agenceDTO.setForcingUpdate(agenceLight.getForcingUpdate());
		}
	}

    /**
     * Convert a bean to DTO
     *
     * @param agenceBean
     * @return
     */
    public static AgenceDTO beanToDTO(AgenceBean agenceBean) {
        AgenceDTO agenceDTO = null;

        if (agenceBean != null) {
            agenceDTO = new AgenceDTO();
            agenceDTO.setGin(agenceBean.getGin());
            agenceDTO.setAgenceRA2(agenceBean.getAgenceRA2());
            agenceDTO.setNom(agenceBean.getNom());
            agenceDTO.setBsp(agenceBean.getBsp());
            agenceDTO.setLocalisation(agenceBean.getLocalisation());
            agenceDTO.setCodeVilleIso(agenceBean.getCodeVilleIso());
        }

        return agenceDTO;
    }

    public static void bo2DtoFullLink(Agence agence, AgenceDTO agenceDTO) throws JrafDomainException {
        bo2DtoFull(agence, agenceDTO);
        bo2DtoLink(agence, agenceDTO);
    }

    /**
     * Transform a business object to DTO. calls bo2DtoLightImpl in a protected
     * region so the user can override this without losing benefit of generation if
     * attributes vary in future
     *
     * @param agence    bo
     * @param agenceDTO dto
     */
    public static void bo2DtoFull(Agence agence, AgenceDTO agenceDTO) throws JrafDomainException {

        bo2DtoFullImpl(agence, agenceDTO);

    }

    /**
     * Transform a business object to DTO. Implementation method
     *
     * @param agence    bo
     * @param agenceDTO dto
     */
    private static void bo2DtoFullImpl(Agence agence, AgenceDTO agenceDTO) throws JrafDomainException {

        // superclass property
        PersonneMoraleTransform.bo2Dto(agence, agenceDTO);

        // simple properties
        agenceDTO.setDateModifiSTAIATA(agence.getDateModifiSTAIATA());
        agenceDTO.setDateAgrement(agence.getDateAgrement());
        agenceDTO.setDateDebut(agence.getDateDebut());
        agenceDTO.setDateFin(agence.getDateFin());
        agenceDTO.setDateRadiation(agence.getDateRadiation());
        agenceDTO.setVersion(agence.getVersion());
        agenceDTO.setAgenceRA2(agence.getAgenceRA2());
        agenceDTO.setBsp(agence.getBsp());
        agenceDTO.setCible(agence.getCible());
        agenceDTO.setCodeVilleIso(agence.getCodeVilleIso());
        agenceDTO.setCodeService(agence.getCodeService());
        agenceDTO.setDomaine(agence.getDomaine());
        agenceDTO.setEnvoieSI(agence.getEnvoieSI());
        agenceDTO.setExclusifGrdCpt(agence.getExclusifGrdCpt());
        agenceDTO.setGsa(agence.getGsa());
        agenceDTO.setInfra(agence.getInfra());
        agenceDTO.setLocalisation(agence.getLocalisation());
        agenceDTO.setNumeroIATAMere(agence.getNumeroIATAMere());
        agenceDTO.setObservation(agence.getObservation());
        agenceDTO.setSousDomaine(agence.getSousDomaine());
        agenceDTO.setStatutIATA(agence.getStatutIATA());
        agenceDTO.setType(agence.getType());
        agenceDTO.setTypeAgrement(agence.getTypeAgrement());
        agenceDTO.setTypeVente(agence.getTypeVente());
        agenceDTO.setZoneChalandise(agence.getZoneChalandise());
        agenceDTO.setIataStationAirportCode(agence.getIataStationAirportCode());
        agenceDTO.setForcingUpdate(agence.getForcingUpdate());

    }
}

