package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_FFONoKU0EeSXNpATSKyi0Q i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.adresse.EmailTransform;
import com.airfrance.repind.dto.adresse.PostalAddressTransform;
import com.airfrance.repind.dto.adresse.TelecomsTransform;
import com.airfrance.repind.entity.firme.Fonction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedHashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : FonctionTransform.java</p>
 * transformation bo <-> dto pour un(e) Fonction
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class FonctionTransform {

    /*PROTECTED REGION ID(_FFONoKU0EeSXNpATSKyi0Q u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
	private static Log LOGGER = LogFactory.getLog(FonctionTransform.class);
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private FonctionTransform() {
    }
    
    /**
     * dto -> bo for a Fonction
     * @param fonctionDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Fonction dto2BoLight(FonctionDTO fonctionDTO) throws JrafDomainException {
        // instanciation du BO
        Fonction fonction = new Fonction();
        dto2BoLight(fonctionDTO, fonction);

        // on retourne le BO
        return fonction;
    }

    /**
     * dto -> bo for a fonction
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param fonctionDTO dto
     * @param fonction bo
     */
    public static void dto2BoLight(FonctionDTO fonctionDTO, Fonction fonction) {
    
        /*PROTECTED REGION ID(dto2BoLight_FFONoKU0EeSXNpATSKyi0Q) ENABLED START*/
        
        dto2BoLightImpl(fonctionDTO,fonction);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a fonction
     * @param fonctionDTO dto
     * @param fonction bo
     */
    private static void dto2BoLightImpl(FonctionDTO fonctionDTO, Fonction fonction){
    
        // property of FonctionDTO
        fonction.setCle(fonctionDTO.getCle());
        fonction.setFonction(fonctionDTO.getFonction());
        fonction.setDateDebutValidite(fonctionDTO.getDateDebutValidite());
        fonction.setDateFinValidite(fonctionDTO.getDateFinValidite());
        fonction.setDateCreation(fonctionDTO.getDateCreation());
        fonction.setSignatureCreation(fonctionDTO.getSignatureCreation());
        fonction.setDateModification(fonctionDTO.getDateModification());
        fonction.setSignatureModification(fonctionDTO.getSignatureModification());
        fonction.setVersion(fonctionDTO.getVersion());
    
    }

    /**
     * bo -> dto for a fonction
     * @param pFonction bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static FonctionDTO bo2DtoLight(Fonction pFonction) throws JrafDomainException {
        // instanciation du DTO
        FonctionDTO fonctionDTO = new FonctionDTO();
        bo2DtoLight(pFonction, fonctionDTO);
        // on retourne le dto
        return fonctionDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param fonction bo
     * @param fonctionDTO dto
     */
    public static void bo2DtoLight(
        Fonction fonction,
        FonctionDTO fonctionDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_FFONoKU0EeSXNpATSKyi0Q) ENABLED START*/

        bo2DtoLightImpl(fonction, fonctionDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param fonction bo
     * @param fonctionDTO dto
     */
    private static void bo2DtoLightImpl(Fonction fonction,
        FonctionDTO fonctionDTO){
    

        // simple properties
        fonctionDTO.setCle(fonction.getCle());
        fonctionDTO.setFonction(fonction.getFonction());
        fonctionDTO.setDateDebutValidite(fonction.getDateDebutValidite());
        fonctionDTO.setDateFinValidite(fonction.getDateFinValidite());
        fonctionDTO.setDateCreation(fonction.getDateCreation());
        fonctionDTO.setSignatureCreation(fonction.getSignatureCreation());
        fonctionDTO.setDateModification(fonction.getDateModification());
        fonctionDTO.setSignatureModification(fonction.getSignatureModification());
        fonctionDTO.setVersion(fonction.getVersion());
    
    }
    
    /*PROTECTED REGION ID(_FFONoKU0EeSXNpATSKyi0Q u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    
    /**
     * bo -> dto for a fonction
     * @param pFonction bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static FonctionDTO bo2Dto(Fonction pFonction) throws JrafDomainException {
        // instanciation du DTO
        FonctionDTO fonctionDTO = new FonctionDTO();
        bo2DtoLight(pFonction, fonctionDTO);
        bo2DtoLinks(pFonction, fonctionDTO);
        // on retourne le dto
        return fonctionDTO;
    }


    /**
     * Converts a set of Fontion entitis to a set of FonctionDTO.
     * 
     * @param fonctionsSet
     * 
     * @return null if the given set is null
     * 
     * @throws JrafDomainException
     */
	public static Set<FonctionDTO> bo2DtoLight(Set<Fonction> fonctionsSet) throws JrafDomainException {

		// Check parameter
		if (fonctionsSet != null) {
			// Initialize return
			Set<FonctionDTO> listFonctionDTO = new LinkedHashSet<FonctionDTO>();
			
			// Convert each Fonction of the set and add itto the result
			for (Fonction fonction : fonctionsSet) {
				listFonctionDTO.add(bo2DtoLight(fonction));
			}

			return listFonctionDTO;
		} else {
			return null;
		}
	}


    /**
     * @param listFonction
     * @return listFonctionDTO
     * @throws JrafDomainException
     */
    public static Set<FonctionDTO> bo2Dto(Set<Fonction> listFonction) throws JrafDomainException {
    	if(listFonction != null) 
    	{
    		Set<FonctionDTO> listFonctionDTO = new LinkedHashSet<FonctionDTO>();
    		for(Fonction fonction : listFonction) 
    		{
    			listFonctionDTO.add(bo2Dto(fonction));
    		}
    		return listFonctionDTO;
    	} 
    	else 
    	{
    		return null;
    	}
    }
    
    public static void bo2DtoLinks(Fonction fonction, FonctionDTO fonctionDTO){
    	
    	// Lien EMAILS
    	if(fonction.getEmails() != null) {
			try {
				fonctionDTO.setEmails((EmailTransform.bo2Dto(fonction.getEmails())));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
    	
    	// Lien POSTAL_ADDRESSES
    	if(fonction.getPostalAddresses() != null) {
			try {
				fonctionDTO.setPostalAddresses((PostalAddressTransform.bo2DtoSet(fonction.getPostalAddresses())));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
    	
    	// Lien TELECOMS
    	if(fonction.getTelecoms() != null) {
			try {
				fonctionDTO.setTelecoms((TelecomsTransform.bo2Dto(fonction.getTelecoms())));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
    	
    	
    }
    
    /**
     * dto -> bo for a Fonction
     * @param fonctionDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Fonction dto2Bo(FonctionDTO fonctionDTO) throws JrafDomainException {
        // instanciation du BO
        Fonction fonction = new Fonction();
        dto2BoLight(fonctionDTO, fonction);
        dto2BoLinks(fonctionDTO, fonction);
        // on retourne le BO
        return fonction;
    }
    
    /**
     * @param listFonctionDTO
     * @return listFonction
     * @throws JrafDomainException
     */
    public static Set<Fonction> dto2Bo(Set<FonctionDTO> listFonctionDTO) throws JrafDomainException {
    	if(listFonctionDTO != null) 
    	{
    		Set<Fonction> listFonction= new LinkedHashSet<Fonction>();
    		for(FonctionDTO fonctionDTO : listFonctionDTO) 
    		{
    			listFonction.add(dto2Bo(fonctionDTO));
    		}
    		return listFonction;
    	} 
    	else 
    	{
    		return null;
    	}
    }
    
    public static void dto2BoLinks(FonctionDTO fonctionDTO, Fonction fonction){
    	
    	// Lien EMAILS
    	if(fonctionDTO.getEmails() != null) {
			try {
				fonction.setEmails((EmailTransform.dto2Bo(fonctionDTO.getEmails())));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
    	
    	// Lien POSTAL_ADDRESSES
    	if(fonctionDTO.getPostalAddresses() != null) {
			try {
				fonction.setPostalAddresses((PostalAddressTransform.dto2BoSet(fonctionDTO.getPostalAddresses())));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
    	
    	// Lien TELECOMS
    	if(fonctionDTO.getTelecoms() != null) {
			try {
				fonction.setTelecoms((TelecomsTransform.dto2Bo(fonctionDTO.getTelecoms())));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
    	
    	
    }
    
    /**
     * @param listFonctionDTO
     * @return listFonction
     * @throws JrafDomainException
     */
    public static Set<Fonction> dto2BoAllLinks(Set<FonctionDTO> listFonctionDTO) throws JrafDomainException {
    	if(listFonctionDTO != null) 
    	{
    		Set<Fonction> listFonction= new LinkedHashSet<Fonction>();
    		for(FonctionDTO fonctionDTO : listFonctionDTO) 
    		{
    			listFonction.add(dto2BoAllLinks(fonctionDTO));
    		}
    		return listFonction;
    	} 
    	else 
    	{
    		return null;
    	}
    }
    
    /**
     * dto -> bo for a Fonction with member links
     * @param fonctionDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Fonction dto2BoAllLinks(FonctionDTO fonctionDTO) throws JrafDomainException {
        // instanciation du BO
        Fonction fonction = new Fonction();
        dto2BoLight(fonctionDTO, fonction);
        dto2BoLinks(fonctionDTO, fonction);
        dto2BoAllLinks(fonctionDTO, fonction);
        // on retourne le BO
        return fonction;
    }

	private static void dto2BoAllLinks(FonctionDTO fonctionDTO, Fonction fonction) throws JrafDomainException {
		// Members
		if (fonctionDTO.getMembre() != null) {
			fonction.setMembre(MembreTransform.dto2boWithAllLinks(fonctionDTO.getMembre()));
		}
	}

	public static Set<FonctionDTO> bo2DtoAllLinks(Set<Fonction> fonctionsList) throws JrafDomainException {
		// TODO Auto-generated method stub
		if(fonctionsList != null) 
    	{
    		Set<FonctionDTO> listFonctionDTO= new LinkedHashSet<FonctionDTO>();
    		for(Fonction fonction : fonctionsList) 
    		{
    			listFonctionDTO.add(bo2DtoAllLinks(fonction));
    		}
    		return listFonctionDTO;
    	} 
    	else 
    	{
    		return null;
    	}
	}
	
    public static FonctionDTO bo2DtoAllLinks(Fonction fonction) throws JrafDomainException {
        // instanciation du DTO
        FonctionDTO fonctionDTO = new FonctionDTO();
        bo2DtoLight(fonction, fonctionDTO);
        bo2DtoLinks(fonction, fonctionDTO);
        bo2DtoAllLinks(fonction, fonctionDTO);
        // on retourne le DTO
        return fonctionDTO;
    }
    
	private static void bo2DtoAllLinks(Fonction fonction, FonctionDTO fonctionDTO) throws JrafDomainException {
		// Members
		if (fonction.getMembre() != null) {
			fonctionDTO.setMembre(MembreTransform.bo2DtoWithAllLinks(fonction.getMembre()));
		}
	}

	/*PROTECTED REGION END*/
}

