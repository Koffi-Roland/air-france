package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_EfLtoNWHEeGWVMiDtSYzVA i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.entity.firme.Membre;
import com.airfrance.repind.entity.firme.MembreLight;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : MembreTransform.java</p>
 * transformation bo <-> dto pour un(e) Membre
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class MembreTransform {

    /*PROTECTED REGION ID(_EfLtoNWHEeGWVMiDtSYzVA u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
	private static Log LOGGER = LogFactory.getLog(MembreTransform.class);
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private MembreTransform() {
    }
    /**
     * dto -> bo for a Membre
     * @param membreDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Membre dto2BoLight(MembreDTO membreDTO) throws JrafDomainException {
        // instanciation du BO
        Membre membre = new Membre();
        dto2BoLight(membreDTO, membre);

        // on retourne le BO
        return membre;
    }

    /**
     * dto -> bo for a membre
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param membreDTO dto
     * @param membre bo
     */
    public static void dto2BoLight(MembreDTO membreDTO, Membre membre) {
    
        /*PROTECTED REGION ID(dto2BoLight_EfLtoNWHEeGWVMiDtSYzVA) ENABLED START*/
        
        dto2BoLightImpl(membreDTO,membre);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a membre
     * @param membreDTO dto
     * @param membre bo
     */
    private static void dto2BoLightImpl(MembreDTO membreDTO, Membre membre){
    
        // property of MembreDTO
        membre.setKey(membreDTO.getKey());
        membre.setVersion(membreDTO.getVersion());
        membre.setFonction(membreDTO.getFonction());
        membre.setDateCreation(membreDTO.getDateCreation());
        membre.setSignatureCreation(membreDTO.getSignatureCreation());
        membre.setDateModification(membreDTO.getDateModification());
        membre.setSignatureModification(membreDTO.getSignatureModification());
        membre.setDateDebutValidite(membreDTO.getDateDebutValidite());
        membre.setDateFinValidite(membreDTO.getDateFinValidite());
        membre.setClient(membreDTO.getClient());
        membre.setContact(membreDTO.getContact());
        membre.setContactAf(membreDTO.getContactAf());
        membre.setEmissionHs(membreDTO.getEmissionHs());
        membre.setServiceAf(membreDTO.getServiceAf());
    
    }

    /**
     * bo -> dto for a membre
     * @param pMembre bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static MembreDTO bo2DtoLight(Membre pMembre) throws JrafDomainException {
        // instanciation du DTO
        MembreDTO membreDTO = new MembreDTO();
        bo2DtoLight(pMembre, membreDTO);
        // on retourne le dto
        return membreDTO;
    }


    /**
     * bo -> dto for a MembreLight
     * 
     * @param pMembre bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static MembreDTO bo2DtoLight(MembreLight membreLight) throws JrafDomainException {
        
    	// Declare return
        MembreDTO membreDTO = null;
        
        if (membreLight != null) {
        	// Fill in with the right information
        	membreDTO = new MembreDTO();
            membreDTO.setKey(membreLight.getKey());
            membreDTO.setVersion(membreLight.getVersion());
            membreDTO.setFonction(membreLight.getFonction());
            membreDTO.setDateCreation(membreLight.getDateCreation());
            membreDTO.setSignatureCreation(membreLight.getSignatureCreation());
            membreDTO.setDateModification(membreLight.getDateModification());
            membreDTO.setSignatureModification(membreLight.getSignatureModification());
            membreDTO.setDateDebutValidite(membreLight.getDateDebutValidite());
            membreDTO.setDateFinValidite(membreLight.getDateFinValidite());
            membreDTO.setClient(membreLight.getClient());
            membreDTO.setContact(membreLight.getContact());
            membreDTO.setContactAf(membreLight.getContactAf());
            membreDTO.setEmissionHs(membreLight.getEmissionHs());
            membreDTO.setServiceAf(membreLight.getServiceAf());
        }

        return membreDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param membre bo
     * @param membreDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLight(
        Membre membre,
        MembreDTO membreDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_EfLtoNWHEeGWVMiDtSYzVA) ENABLED START*/

        bo2DtoLightImpl(membre, membreDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param membre bo
     * @param membreDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImpl(Membre membre,
        MembreDTO membreDTO) throws JrafDomainException{
    

        // simple properties
        membreDTO.setKey(membre.getKey());
        membreDTO.setVersion(membre.getVersion());
        membreDTO.setFonction(membre.getFonction());
        membreDTO.setDateCreation(membre.getDateCreation());
        membreDTO.setSignatureCreation(membre.getSignatureCreation());
        membreDTO.setDateModification(membre.getDateModification());
        membreDTO.setSignatureModification(membre.getSignatureModification());
        membreDTO.setDateDebutValidite(membre.getDateDebutValidite());
        membreDTO.setDateFinValidite(membre.getDateFinValidite());
        membreDTO.setClient(membre.getClient());
        membreDTO.setContact(membre.getContact());
        membreDTO.setContactAf(membre.getContactAf());
        membreDTO.setEmissionHs(membre.getEmissionHs());
        membreDTO.setServiceAf(membre.getServiceAf());
        membreDTO.setIndividu(IndividuTransform.bo2Dto(membre.getIndividu()));
        membreDTO.setFonctions(FonctionTransform.bo2Dto(membre.getFonctions()));  
    }
    
    /*PROTECTED REGION ID(_EfLtoNWHEeGWVMiDtSYzVA u m - Tr) ENABLED START*/
    public static List<MembreDTO> bo2DtoLight(List<Membre> membres) throws JrafDomainException {
        if (membres != null) {
            List<MembreDTO> listMembreDTO = new ArrayList<MembreDTO>();
            for (Membre me : membres) {
                listMembreDTO.add(bo2DtoLight(me));
            }
            return listMembreDTO;
        } else {
            return null;
        }
    }


	/**
	 * Transforms a list of Membre entities to a list of DTOs.
	 * DTOs contain membre information (light), but also legal
	 * person information.
	 * 
	 * @param membershipsList
	 * 
	 * @return
	 * 
	 * @throws JrafDomainException 
	 */
	public static List<MembreDTO> bo2DtoLightWithPersonneMorale(List<MembreLight> membreLightList) throws JrafDomainException {
		
		// Initialize result
		List<MembreDTO> result = null;
		
		if (membreLightList != null) {
			result = new ArrayList<MembreDTO>();
			
			// Loop over the entities and transform them into DTOs
            for (MembreLight membreLight : membreLightList) {
            	// Add created DTOs to the result list
            	result.add(bo2DtoLightWithPersonneMorale(membreLight));
            }
        }

		return result;
	}
	

	/**
	 * Converts a Membre entity to DTO. Result contains
	 * light information about Membre and legal person innformation.
	 * 
	 * @param membreLight
	 * 
	 * @return
	 * 
	 * @throws JrafDomainException
	 */
    public static MembreDTO bo2DtoLightWithPersonneMorale(MembreLight membreLight) throws JrafDomainException {

    	// Initialize return
    	MembreDTO result = null;
    	
    	if (membreLight != null) {
    		// Add membership information to DTO
    		result = bo2DtoLight(membreLight);
    		
    		// Add Legal person information to DTO
    		result.setPersonneMorale(PersonneMoraleTransform.bo2DtoLight(membreLight.getPersonneMoraleLight()));
    	}

		return result;
	}
    
    
	public static List<MembreDTO> bo2Dto(Set<Membre> membres) throws JrafDomainException {
		if(membres != null)
    	{
    		List<MembreDTO> listMembreDTO = new ArrayList<MembreDTO>();
    		for(Membre me : membres){
    			listMembreDTO.add(bo2DtoLight(me));
    		}
    		return listMembreDTO;
    	}
    	else
    	{
    		return null;
    	}
	}
    
    public static Set<Membre> dto2BoLight(List<MembreDTO> membreDTO) throws JrafDomainException {
    	if(membreDTO != null)
    	{
    		Set<Membre> listMembre = new HashSet<Membre>();
    		for(MembreDTO me : membreDTO){
    			listMembre.add(dto2BoLight(me));
    		}
    		return listMembre;
    	}
    	else
    	{
    		return null;
    	}
	}
    
    /**
     * bo -> dto for a membre with its collections
     * @param pMembre bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static MembreDTO bo2DtoWithLinks(Membre pMembre) throws JrafDomainException {
        // instanciation du DTO
        MembreDTO membreDTO = new MembreDTO();
        bo2DtoLight(pMembre, membreDTO);
        bo2DtoLinks(pMembre, membreDTO);
        // on retourne le dto
        return membreDTO;
    }
    
    /**
     * bo -> dto for a membre with its collections
     * @param pMembre bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static MembreDTO bo2DtoWithAllLinks(Membre pMembre) throws JrafDomainException {
        // instanciation du DTO
        MembreDTO membreDTO = new MembreDTO();
        bo2DtoLight(pMembre, membreDTO);
        bo2DtoLinksWithFontionsIndividuPersonneMorale(pMembre, membreDTO);
        // on retourne le dto
        return membreDTO;
    }
    
    /**
     * bo -> dto for a membre with its collections
     * @param pMembre bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static MembreDTO bo2DtoWithAllLinksAndFonctionMember(Membre pMembre) throws JrafDomainException {
        // instanciation du DTO
        MembreDTO membreDTO = new MembreDTO();
        bo2DtoLight(pMembre, membreDTO);
        bo2DtoLinksWithFontionsIndividuPersonneMorale(pMembre, membreDTO);
        bo2DtoLinksWithFonctionMember(pMembre, membreDTO);
        // on retourne le dto
        return membreDTO;
    }
    
    private static void bo2DtoLinksWithFonctionMember(Membre membre, MembreDTO membreDTO) {
		// TODO Auto-generated method stub
    	// FONCTIONS
    	if ( membre.getFonctions() != null) {
            try {
            	membreDTO.setFonctions(FonctionTransform.bo2DtoAllLinks(membre.getFonctions()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    	
    	// Individu
    	if ( membre.getIndividu() != null) {
            try {
            	membreDTO.setIndividu(IndividuTransform.bo2DtoMember(membre.getIndividu()));
            	//membreDTO.setIndividu(IndividuTransform.bo2DtoLight(membre.getIndividu()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    	
    	// Personne morale
    	if ( membre.getPersonneMorale() != null) {
            try {
            	membreDTO.setPersonneMorale((PersonneMoraleTransform.bo2DtoLight(membre.getPersonneMorale())));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
	}
    
	/**
     * Transform a business object to DTO.
     * "method with links" to get collections of the member
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param membre bo
     * @param membreDTO dto
     */
    public static void bo2DtoLinks( Membre membre, MembreDTO membreDTO) {

    	// FONCTIONS
    	if ( membre.getFonctions() != null) {
            try {
            	membreDTO.setFonctions(FonctionTransform.bo2Dto(membre.getFonctions()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    	
    	// Individu
    	if ( membre.getIndividu() != null) {
            try {
            	membreDTO.setIndividu(IndividuTransform.bo2DtoMember(membre.getIndividu()));
            	//membreDTO.setIndividu(IndividuTransform.bo2DtoLight(membre.getIndividu()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

    }
    
    /**
     * Transform a business object to DTO.
     * "method with links Fontions Individu PersonneMorale" to get collections of the member
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param membre bo
     * @param membreDTO dto
     */
    public static void bo2DtoLinksWithFontionsIndividuPersonneMorale( Membre membre, MembreDTO membreDTO) {

    	// FONCTIONS
    	if ( membre.getFonctions() != null) {
            try {
            	membreDTO.setFonctions(FonctionTransform.bo2Dto(membre.getFonctions()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    	
    	// Individu
    	if ( membre.getIndividu() != null) {
            try {
            	membreDTO.setIndividu(IndividuTransform.bo2DtoMember(membre.getIndividu()));
            	//membreDTO.setIndividu(IndividuTransform.bo2DtoLight(membre.getIndividu()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    	
    	// Personne morale
    	if ( membre.getPersonneMorale() != null) {
            try {
            	membreDTO.setPersonneMorale((PersonneMoraleTransform.bo2DtoLight(membre.getPersonneMorale())));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

    }
    
    public static List<MembreDTO> bo2DtoWithLinks(Set<Membre> membres) throws JrafDomainException {
		if(membres != null)
    	{
    		List<MembreDTO> listMembreDTO = new ArrayList<MembreDTO>();
    		for(Membre me : membres){
    			listMembreDTO.add(bo2DtoWithLinks(me));
    		}
    		return listMembreDTO;
    	}
    	else
    	{
    		return null;
    	}
	}
    
    /**
     * Transform a DTO to a business object.
     * "method with links" to get collections of the memberDTO
     * @param membreDTO dto
     * @param membre bo
     */
    public static void dto2BoLinks(MembreDTO membreDTO, Membre membre) {

    	// FONCTIONS
    	if ( membreDTO.getFonctions() != null) {
            try {
            	membre.setFonctions(FonctionTransform.dto2Bo(membreDTO.getFonctions()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }

    }
    
    /**
     * dto -> bo for a Membre with its links
     * @param membreDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Membre dto2BoLinks(MembreDTO membreDTO) throws JrafDomainException {
        // instanciation du BO
        Membre membre = new Membre();
        dto2BoLight(membreDTO, membre);
        dto2BoLinks(membreDTO, membre);
        // on retourne le BO
        return membre;
    }
    
    /**
	 * Transform a Member with all its links
	 */
	public static void dto2Bo(MembreDTO membreDTO, Membre membre) throws InvalidParameterException {
		dto2BoLight(membreDTO, membre);
        dto2BoLinks(membreDTO, membre);
	}

    /**
     * dto -> bo for a membre with its collections
     * @param pMembre bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static Membre dto2boWithAllLinks(MembreDTO pMembreDto) throws JrafDomainException {
        // instanciation du BO
        Membre membre = new Membre();
        dto2BoLight(pMembreDto, membre);
        dto2BoLinksWithFontionsIndividuPersonneMorale(pMembreDto, membre);
        // on retourne le bo
        return membre;
    }
    
    /**
     * Transform a business object to DTO.
     * "method with links Fontions Individu PersonneMorale" to get collections of the member
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param membre bo
     * @param membreDTO dto
     */
    public static void dto2BoLinksWithFontionsIndividuPersonneMorale( MembreDTO membreDto, Membre membre) {

    	// FONCTIONS
    	if ( membreDto.getFonctions() != null) {
            try {
            	membre.setFonctions(FonctionTransform.dto2BoAllLinks(membreDto.getFonctions()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    	
    	// Individu
    	if ( membreDto.getIndividu() != null) {
            try {
            	membre.setIndividu(IndividuTransform.dto2BoMember(membreDto.getIndividu()));
            	//membreDTO.setIndividu(IndividuTransform.bo2DtoLight(membre.getIndividu()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    	
    	// Personne morale
    	if ( membreDto.getPersonneMorale() != null) {
            try {
            	membre.setPersonneMorale((PersonneMoraleTransform.dto2BoLight(membreDto.getPersonneMorale())));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }
    }
    
    /*PROTECTED REGION END*/
}

