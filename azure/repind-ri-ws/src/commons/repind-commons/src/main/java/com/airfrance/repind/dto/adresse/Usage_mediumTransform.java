package com.airfrance.repind.dto.adresse;

/*PROTECTED REGION ID(_F-ddYDXgEeCq6pHdxM8RnQ i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.adresse.Usage_medium;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : Usage_mediumTransform.java</p>
 * transformation bo <-> dto pour un(e) Usage_medium
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class Usage_mediumTransform {

    /*PROTECTED REGION ID(_F-ddYDXgEeCq6pHdxM8RnQ u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * Constructeur prive
     */
    private Usage_mediumTransform() {
    }
    /**
     * dto -> bo pour une Usage_medium
     * @param usage_mediumDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Usage_medium dto2BoLight(Usage_mediumDTO usage_mediumDTO) throws JrafDomainException {
        // instanciation du BO
        Usage_medium usage_medium = new Usage_medium();
        dto2BoLight(usage_mediumDTO, usage_medium);

        // on retourne le BO
        return usage_medium;
    }

    /**
     * dto -> bo pour une usage_medium
     * @param usage_mediumDTO dto
     * @param usage_medium bo
     */
    public static void dto2BoLight(Usage_mediumDTO usage_mediumDTO, Usage_medium usage_medium) {
        // property of Usage_mediumDTO
        usage_medium.setSrin(usage_mediumDTO.getSrin());
        usage_medium.setSain_tel(usage_mediumDTO.getSain_tel());
        usage_medium.setSain_email(usage_mediumDTO.getSain_email());
        usage_medium.setScode_application(usage_mediumDTO.getScode_application());
        usage_medium.setSain_adr(usage_mediumDTO.getSain_adr());
        usage_medium.setInum(usage_mediumDTO.getInum());
        usage_medium.setScon(usage_mediumDTO.getScon());
        usage_medium.setSrole1(usage_mediumDTO.getSrole1());
        usage_medium.setSrole2(usage_mediumDTO.getSrole2());
        usage_medium.setSrole3(usage_mediumDTO.getSrole3());
        usage_medium.setSrole4(usage_mediumDTO.getSrole4());
        usage_medium.setSrole5(usage_mediumDTO.getSrole5());
    }

    /**
     * bo -> dto pour une usage_medium
     * @param pUsage_medium bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static Usage_mediumDTO bo2DtoLight(Usage_medium pUsage_medium) throws JrafDomainException {
        // instanciation du DTO
        Usage_mediumDTO usage_mediumDTO = new Usage_mediumDTO();
        bo2DtoLight(pUsage_medium, usage_mediumDTO);
        // on retourne le dto
        return usage_mediumDTO;
    }


    /**
     * Transforme un business object en DTO.
     * Methode dite light.
     * @param usage_medium bo
     * @param usage_mediumDTO dto
     */
    public static void bo2DtoLight(
        Usage_medium usage_medium,
        Usage_mediumDTO usage_mediumDTO) {


        // simple properties
        usage_mediumDTO.setSrin(usage_medium.getSrin());
        usage_mediumDTO.setSain_tel(usage_medium.getSain_tel());
        usage_mediumDTO.setSain_email(usage_medium.getSain_email());
        usage_mediumDTO.setScode_application(usage_medium.getScode_application());
        usage_mediumDTO.setSain_adr(usage_medium.getSain_adr());
        usage_mediumDTO.setInum(usage_medium.getInum());
        usage_mediumDTO.setScon(usage_medium.getScon());
        usage_mediumDTO.setSrole1(usage_medium.getSrole1());
        usage_mediumDTO.setSrole2(usage_medium.getSrole2());
        usage_mediumDTO.setSrole3(usage_medium.getSrole3());
        usage_mediumDTO.setSrole4(usage_medium.getSrole4());
        usage_mediumDTO.setSrole5(usage_medium.getSrole5());


    }
    
    public static Usage_medium dto2bo(Usage_mediumDTO dto) {
    	Usage_medium bo = null;
    	
    	if (dto != null) {
    		bo = new Usage_medium();
            dto2Bo2Params(dto, bo);
    	}
    	
    	return bo;
    }
    
    /*PROTECTED REGION ID(_F-ddYDXgEeCq6pHdxM8RnQ u m - Tr) ENABLED START*/
    /**
     * Ask both method : dto2BoLight & dto2BoLink
     * @param usage_mediumDTO
     * @param usage_medium
     */
    public static void dto2Bo2Params(Usage_mediumDTO usage_mediumDTO, Usage_medium usage_medium) {
    	dto2BoLight(usage_mediumDTO, usage_medium);
    	dto2BoLink(usage_mediumDTO, usage_medium);
    }
    
    /**
     * Ask both method : bo2DtoLight & bo2DtoLink
	 * @param usage_medium
	 * @param usage_mediumDTO
     */
    public static void bo2Dto(Usage_medium usage_medium, Usage_mediumDTO usage_mediumDTO) {
    	bo2DtoLight(usage_medium, usage_mediumDTO);
    	bo2DtoLink(usage_medium, usage_mediumDTO);
    }
    
	/**
	 * @param usage_medium
	 * @param usage_mediumDTO
	 */
	public static void bo2DtoLink(Usage_medium usage_medium, Usage_mediumDTO usage_mediumDTO) {
		//no links
	}

    /**
     * @param usage_mediumDTO
     * @param usage_medium
     */
    public static void dto2BoLink(Usage_mediumDTO usage_mediumDTO, Usage_medium usage_medium) {
    	//no links
	}
	
	
    /**
     * @param listUsage_medium
     * @return
     * @throws JrafDomainException
     */
    public static Set<Usage_mediumDTO> bo2Dto(Set<Usage_medium> listUsage_medium) throws JrafDomainException {
    	if(listUsage_medium != null) {
    		Set<Usage_mediumDTO> listUsage_mediumDTO = new HashSet<Usage_mediumDTO>();
    		for(Usage_medium usage_medium : listUsage_medium) {
    			Usage_mediumDTO usage_mediumDTO = new Usage_mediumDTO();
    	        bo2Dto(usage_medium, usage_mediumDTO);
    			listUsage_mediumDTO.add(usage_mediumDTO);
    		}
    		return listUsage_mediumDTO;
    	} else {
    		return null;
    	}
    }

    /**
     * @param listUsage_mediumDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<Usage_medium> dto2BoSet(Set<Usage_mediumDTO> listUsage_mediumDTO) throws JrafDomainException {
    	if(listUsage_mediumDTO != null) {
    		Set<Usage_medium> listUsage_medium = new HashSet<Usage_medium>();
    		for(Usage_mediumDTO usage_mediumdto : listUsage_mediumDTO) {
    			Usage_medium usage_medium = new Usage_medium();
                dto2Bo2Params(usage_mediumdto, usage_medium);
    			listUsage_medium.add(usage_medium);
    		}
    		return listUsage_medium;
    	} else {
    		return null;
    	}
    }
    /*PROTECTED REGION END*/
}

