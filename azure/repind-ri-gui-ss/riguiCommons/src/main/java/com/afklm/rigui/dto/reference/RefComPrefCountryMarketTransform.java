package com.afklm.rigui.dto.reference;

/*PROTECTED REGION ID(_WV5QsETtEeaz3NuRBl0yXw i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefComPrefCountryMarket;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefComPrefCountryMarketTransform.java</p>
 * transformation bo <-> dto pour un(e) RefComPrefCountryMarket
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefComPrefCountryMarketTransform {

    /*PROTECTED REGION ID(_WV5QsETtEeaz3NuRBl0yXw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefComPrefCountryMarketTransform() {
    }
    /**
     * dto -> bo for a RefComPrefCountryMarket
     * @param refComPrefCountryMarketDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefComPrefCountryMarket dto2BoLight(RefComPrefCountryMarketDTO refComPrefCountryMarketDTO) throws JrafDomainException {
        // instanciation du BO
        RefComPrefCountryMarket refComPrefCountryMarket = new RefComPrefCountryMarket();
        dto2BoLight(refComPrefCountryMarketDTO, refComPrefCountryMarket);

        // on retourne le BO
        return refComPrefCountryMarket;
    }

    /**
     * dto -> bo for a refComPrefCountryMarket
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPrefCountryMarketDTO dto
     * @param refComPrefCountryMarket bo
     */
    public static void dto2BoLight(RefComPrefCountryMarketDTO refComPrefCountryMarketDTO, RefComPrefCountryMarket refComPrefCountryMarket) {
    
        /*PROTECTED REGION ID(dto2BoLight_WV5QsETtEeaz3NuRBl0yXw) ENABLED START*/
        
        dto2BoLightImpl(refComPrefCountryMarketDTO,refComPrefCountryMarket);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refComPrefCountryMarket
     * @param refComPrefCountryMarketDTO dto
     * @param refComPrefCountryMarket bo
     */
    private static void dto2BoLightImpl(RefComPrefCountryMarketDTO refComPrefCountryMarketDTO, RefComPrefCountryMarket refComPrefCountryMarket){
    
        // property of RefComPrefCountryMarketDTO
        refComPrefCountryMarket.setCodePays(refComPrefCountryMarketDTO.getCodePays());
        refComPrefCountryMarket.setMarket(refComPrefCountryMarketDTO.getMarket());
        refComPrefCountryMarket.setSignatureModification(refComPrefCountryMarketDTO.getSignatureModification());
        refComPrefCountryMarket.setSiteModification(refComPrefCountryMarketDTO.getSiteModification());
        refComPrefCountryMarket.setDateModification(refComPrefCountryMarketDTO.getDateModification());
        refComPrefCountryMarket.setSignatureCreation(refComPrefCountryMarketDTO.getSignatureCreation());
        refComPrefCountryMarket.setSiteCreation(refComPrefCountryMarketDTO.getSiteCreation());
        refComPrefCountryMarket.setDateCreation(refComPrefCountryMarketDTO.getDateCreation());
    
    }

    /**
     * bo -> dto for a refComPrefCountryMarket
     * @param pRefComPrefCountryMarket bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefComPrefCountryMarketDTO bo2DtoLight(RefComPrefCountryMarket pRefComPrefCountryMarket) throws JrafDomainException {
        // instanciation du DTO
        RefComPrefCountryMarketDTO refComPrefCountryMarketDTO = new RefComPrefCountryMarketDTO();
        bo2DtoLight(pRefComPrefCountryMarket, refComPrefCountryMarketDTO);
        // on retourne le dto
        return refComPrefCountryMarketDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPrefCountryMarket bo
     * @param refComPrefCountryMarketDTO dto
     */
    public static void bo2DtoLight(
        RefComPrefCountryMarket refComPrefCountryMarket,
        RefComPrefCountryMarketDTO refComPrefCountryMarketDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_WV5QsETtEeaz3NuRBl0yXw) ENABLED START*/

        bo2DtoLightImpl(refComPrefCountryMarket, refComPrefCountryMarketDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refComPrefCountryMarket bo
     * @param refComPrefCountryMarketDTO dto
     */
    private static void bo2DtoLightImpl(RefComPrefCountryMarket refComPrefCountryMarket,
        RefComPrefCountryMarketDTO refComPrefCountryMarketDTO){
    

        // simple properties
        refComPrefCountryMarketDTO.setCodePays(refComPrefCountryMarket.getCodePays());
        refComPrefCountryMarketDTO.setMarket(refComPrefCountryMarket.getMarket());
        refComPrefCountryMarketDTO.setSignatureModification(refComPrefCountryMarket.getSignatureModification());
        refComPrefCountryMarketDTO.setSiteModification(refComPrefCountryMarket.getSiteModification());
        refComPrefCountryMarketDTO.setDateModification(refComPrefCountryMarket.getDateModification());
        refComPrefCountryMarketDTO.setSignatureCreation(refComPrefCountryMarket.getSignatureCreation());
        refComPrefCountryMarketDTO.setSiteCreation(refComPrefCountryMarket.getSiteCreation());
        refComPrefCountryMarketDTO.setDateCreation(refComPrefCountryMarket.getDateCreation());
    
    }
    
    /*PROTECTED REGION ID(_WV5QsETtEeaz3NuRBl0yXw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

