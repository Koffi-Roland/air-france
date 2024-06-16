package com.airfrance.repind.dto.zone;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.zone.LienIntCpZd;

/*PROTECTED REGION END*/

/**
 * <p>Title : LienIntCpZdTransform.java</p>
 * transformation bo <-> dto pour un(e) LienIntCpZd
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class LienIntCpZdTransform {

    /*PROTECTED REGION ID(_mtzHMDO6EeKT_JQCdHEO1w u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private LienIntCpZdTransform() {
    }
    /**
     * dto -> bo for a LienIntCpZd
     * @param lienIntCpZdDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static LienIntCpZd dto2BoLight(LienIntCpZdDTO lienIntCpZdDTO) throws JrafDomainException {
        // instanciation du BO
        LienIntCpZd lienIntCpZd = new LienIntCpZd();
        dto2BoLight(lienIntCpZdDTO, lienIntCpZd);

        // on retourne le BO
        return lienIntCpZd;
    }

    /**
     * dto -> bo for a lienIntCpZd
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param lienIntCpZdDTO dto
     * @param lienIntCpZd bo
     */
    public static void dto2BoLight(LienIntCpZdDTO lienIntCpZdDTO, LienIntCpZd lienIntCpZd) {
    
        /*PROTECTED REGION ID(dto2BoLight_mtzHMDO6EeKT_JQCdHEO1w) ENABLED START*/
        
        dto2BoLightImpl(lienIntCpZdDTO,lienIntCpZd);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a lienIntCpZd
     * @param lienIntCpZdDTO dto
     * @param lienIntCpZd bo
     */
    private static void dto2BoLightImpl(LienIntCpZdDTO lienIntCpZdDTO, LienIntCpZd lienIntCpZd){
    
        // property of LienIntCpZdDTO
        lienIntCpZd.setCle(lienIntCpZdDTO.getCle());
        lienIntCpZd.setSignatureMaj(lienIntCpZdDTO.getSignatureMaj());
        lienIntCpZd.setDateMaj(lienIntCpZdDTO.getDateMaj());
        lienIntCpZd.setDateFinLien(lienIntCpZdDTO.getDateFinLien());
        lienIntCpZd.setDateDebutLien(lienIntCpZdDTO.getDateDebutLien());
        lienIntCpZd.setUsage(lienIntCpZdDTO.getUsage());
        lienIntCpZd.setCodeVille(lienIntCpZdDTO.getCodeVille());
        lienIntCpZd.setCodeProvince(lienIntCpZdDTO.getCodeProvince());
        lienIntCpZd.setCodePays(lienIntCpZdDTO.getCodePays());
    
    }

    /**
     * bo -> dto for a lienIntCpZd
     * @param pLienIntCpZd bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static LienIntCpZdDTO bo2DtoLight(LienIntCpZd pLienIntCpZd) throws JrafDomainException {
        // instanciation du DTO
        LienIntCpZdDTO lienIntCpZdDTO = new LienIntCpZdDTO();
        bo2DtoLight(pLienIntCpZd, lienIntCpZdDTO);
        // on retourne le dto
        return lienIntCpZdDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param lienIntCpZd bo
     * @param lienIntCpZdDTO dto
     */
    public static void bo2DtoLight(
        LienIntCpZd lienIntCpZd,
        LienIntCpZdDTO lienIntCpZdDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_mtzHMDO6EeKT_JQCdHEO1w) ENABLED START*/

        bo2DtoLightImpl(lienIntCpZd, lienIntCpZdDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param lienIntCpZd bo
     * @param lienIntCpZdDTO dto
     */
    private static void bo2DtoLightImpl(LienIntCpZd lienIntCpZd,
        LienIntCpZdDTO lienIntCpZdDTO){
    

        // simple properties
        lienIntCpZdDTO.setCle(lienIntCpZd.getCle());
        lienIntCpZdDTO.setSignatureMaj(lienIntCpZd.getSignatureMaj());
        lienIntCpZdDTO.setDateMaj(lienIntCpZd.getDateMaj());
        lienIntCpZdDTO.setDateFinLien(lienIntCpZd.getDateFinLien());
        lienIntCpZdDTO.setDateDebutLien(lienIntCpZd.getDateDebutLien());
        lienIntCpZdDTO.setUsage(lienIntCpZd.getUsage());
        lienIntCpZdDTO.setCodeVille(lienIntCpZd.getCodeVille());
        lienIntCpZdDTO.setCodeProvince(lienIntCpZd.getCodeProvince());
        lienIntCpZdDTO.setCodePays(lienIntCpZd.getCodePays());
    
    }
    
    /*PROTECTED REGION ID(_mtzHMDO6EeKT_JQCdHEO1w u m - Tr) ENABLED START*/
    
    /**
     * Transforme un business object en DTO.
     * @param lienIntCpZd bo
     * @param lienIntCpZdDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2Dto(LienIntCpZd lienIntCpZd, LienIntCpZdDTO lienIntCpZdDTO) throws JrafDomainException {
    	
    	bo2DtoLight(lienIntCpZd, lienIntCpZdDTO);
    	
    	if(lienIntCpZd.getZoneDecoup() != null){
    	    
    	    lienIntCpZdDTO.setZoneDecoup(ZoneDecoupTransform.bo2DtoLight(lienIntCpZd.getZoneDecoup()));
    	}
    }
    
    /*PROTECTED REGION END*/
    
    public static void bo2DtoWithIntervalleCodesPostaux(LienIntCpZd lienIntCpZd, LienIntCpZdDTO lienIntCpZdDTO) throws JrafDomainException {
    	
    	bo2DtoLight(lienIntCpZd, lienIntCpZdDTO);
    	
    	if(lienIntCpZd.getZoneDecoup() != null){
    	    
    	    lienIntCpZdDTO.setZoneDecoup(ZoneDecoupTransform.bo2DtoLight(lienIntCpZd.getZoneDecoup()));
    	}
    	
    	if (lienIntCpZd.getIntervalleCodesPostaux() != null){
    	    
    	    lienIntCpZdDTO.setIntervalleCodesPostaux(IntervalleCodesPostauxTransform.bo2DtoLight(lienIntCpZd.getIntervalleCodesPostaux()));
    	}
    }
}

