package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_XN3RMLdcEeCrCZp8iGNNVw i - Tr) ENABLED START*/

// add not generated imports here
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.Entreprise;
import com.airfrance.repind.entity.firme.EntrepriseLight;

/*PROTECTED REGION END*/

/**
 * <p>Title : EntrepriseTransform.java</p>
 * transformation bo <-> dto pour un(e) Entreprise
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class EntrepriseTransform {

    /*PROTECTED REGION ID(_XN3RMLdcEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private EntrepriseTransform() {
    }
    /**
     * dto -> bo for a Entreprise
     * @param entrepriseDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Entreprise dto2BoLight(EntrepriseDTO entrepriseDTO) throws JrafDomainException {
        return (Entreprise)PersonneMoraleTransform.dto2BoLight(entrepriseDTO);
    }

    /**
     * dto -> bo for a entreprise
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param entrepriseDTO dto
     * @param entreprise bo
     */
    public static void dto2BoLight(EntrepriseDTO entrepriseDTO, Entreprise entreprise) {
    
        /*PROTECTED REGION ID(dto2BoLight_XN3RMLdcEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoLightImpl(entrepriseDTO,entreprise);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a entreprise
     * @param entrepriseDTO dto
     * @param entreprise bo
     */
    private static void dto2BoLightImpl(EntrepriseDTO entrepriseDTO, Entreprise entreprise){
    
        // superclass property
        PersonneMoraleTransform.dto2BoLight(entrepriseDTO, entreprise);
        // property of EntrepriseDTO
        entreprise.setSiren(entrepriseDTO.getSiren());
    
    }

    /**
     * bo -> dto for a entreprise
     * @param entreprise bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static EntrepriseDTO bo2DtoLight(Entreprise entreprise) throws JrafDomainException {
        return (EntrepriseDTO)PersonneMoraleTransform.bo2DtoLight(entreprise);
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param entreprise bo
     * @param entrepriseDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLight(
        Entreprise entreprise,
        EntrepriseDTO entrepriseDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_XN3RMLdcEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(entreprise, entrepriseDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param entreprise bo
     * @param entrepriseDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImpl(Entreprise entreprise,
        EntrepriseDTO entrepriseDTO) throws JrafDomainException{
    
        // superclass property
        PersonneMoraleTransform.bo2DtoLight(entreprise, entrepriseDTO);

        // simple properties
        entrepriseDTO.setSiren(entreprise.getSiren());
    
    }
    
    
    /**
     * Transforms a BO to a DTO.
     * 
     * @param entrepriseLight
     * @param entrepriseDTO
     */
	public static void bo2DtoLight(EntrepriseLight entrepriseLight, EntrepriseDTO entrepriseDTO) {

		if (entrepriseLight != null) {
			// Make the conversion
	        PersonneMoraleTransform.bo2DtoLight(entrepriseLight, entrepriseDTO);
	        entrepriseDTO.setSiren(entrepriseLight.getSiren());
		}
	}
    
    /*PROTECTED REGION ID(_XN3RMLdcEeCrCZp8iGNNVw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

