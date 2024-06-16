package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_Z8W3wLdcEeCrCZp8iGNNVw i - Tr) ENABLED START*/

// add not generated imports here
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.Groupe;
import com.airfrance.repind.entity.firme.GroupeLight;

/*PROTECTED REGION END*/

/**
 * <p>Title : GroupeTransform.java</p>
 * transformation bo <-> dto pour un(e) Groupe
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class GroupeTransform {

    /*PROTECTED REGION ID(_Z8W3wLdcEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private GroupeTransform() {
    }
    /**
     * dto -> bo for a Groupe
     * @param groupeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Groupe dto2BoLight(GroupeDTO groupeDTO) throws JrafDomainException {
        return (Groupe)PersonneMoraleTransform.dto2BoLight(groupeDTO);
    }

    /**
     * dto -> bo for a groupe
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param groupeDTO dto
     * @param groupe bo
     */
    public static void dto2BoLight(GroupeDTO groupeDTO, Groupe groupe) {
    
        /*PROTECTED REGION ID(dto2BoLight_Z8W3wLdcEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoLightImpl(groupeDTO,groupe);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a groupe
     * @param groupeDTO dto
     * @param groupe bo
     */
    private static void dto2BoLightImpl(GroupeDTO groupeDTO, Groupe groupe){
    
        // superclass property
        PersonneMoraleTransform.dto2BoLight(groupeDTO, groupe);
        // property of GroupeDTO
        groupe.setCode(groupeDTO.getCode());
    
    }

    /**
     * bo -> dto for a groupe
     * @param groupe bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static GroupeDTO bo2DtoLight(Groupe groupe) throws JrafDomainException {
        return (GroupeDTO)PersonneMoraleTransform.bo2DtoLight(groupe);
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param groupe bo
     * @param groupeDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLight(
        Groupe groupe,
        GroupeDTO groupeDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_Z8W3wLdcEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(groupe, groupeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param groupe bo
     * @param groupeDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImpl(Groupe groupe,
        GroupeDTO groupeDTO) throws JrafDomainException{
    
        // superclass property
        PersonneMoraleTransform.bo2DtoLight(groupe, groupeDTO);

        // simple properties
        groupeDTO.setCode(groupe.getCode());
    
    }
    
    
    /**
     * Transforms a BO to a DTO.
     * 
     * @param personneMoraleLightToConvert
     * @param personneMoraleDTO
     */
	public static void bo2DtoLight(GroupeLight groupeLight, GroupeDTO groupeDTO) {
		
		if (groupeLight != null) {
			// Make the conversion
	        PersonneMoraleTransform.bo2DtoLight(groupeLight, groupeDTO);
	        groupeDTO.setCode(groupeLight.getCode());
		}
	}
    
    /*PROTECTED REGION ID(_Z8W3wLdcEeCrCZp8iGNNVw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

