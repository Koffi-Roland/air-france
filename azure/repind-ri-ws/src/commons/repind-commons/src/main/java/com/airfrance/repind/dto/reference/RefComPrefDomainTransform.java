package com.airfrance.repind.dto.reference;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefComPrefDomain;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefComPrefDomainTransform.java</p>
 * transformation bo <-> dto pour un(e) RefComPrefDomain
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefComPrefDomainTransform {

    /*PROTECTED REGION ID(_1sT54DSaEeaR_YJoHRGtPg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefComPrefDomainTransform() {
    }
    /**
     * dto -> bo for a RefComPrefDomain
     * @param refComPrefDomainDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefComPrefDomain dto2BoLight(RefComPrefDomainDTO refComPrefDomainDTO) throws JrafDomainException {
        // instanciation du BO
        RefComPrefDomain refComPrefDomain = new RefComPrefDomain();
        dto2BoLight(refComPrefDomainDTO, refComPrefDomain);

        // on retourne le BO
        return refComPrefDomain;
    }

    /**
     * dto -> bo for a refComPrefDomain
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPrefDomainDTO dto
     * @param refComPrefDomain bo
     */
    public static void dto2BoLight(RefComPrefDomainDTO refComPrefDomainDTO, RefComPrefDomain refComPrefDomain) {
    
        /*PROTECTED REGION ID(dto2BoLight_1sT54DSaEeaR_YJoHRGtPg) ENABLED START*/
        
        dto2BoLightImpl(refComPrefDomainDTO,refComPrefDomain);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refComPrefDomain
     * @param refComPrefDomainDTO dto
     * @param refComPrefDomain bo
     */
    private static void dto2BoLightImpl(RefComPrefDomainDTO refComPrefDomainDTO, RefComPrefDomain refComPrefDomain){
    
        // property of RefComPrefDomainDTO
        refComPrefDomain.setCodeDomain(refComPrefDomainDTO.getCodeDomain());
        refComPrefDomain.setLibelleDomain(refComPrefDomainDTO.getLibelleDomain());
        refComPrefDomain.setLibelleDomainEN(refComPrefDomainDTO.getLibelleDomainEN());
        refComPrefDomain.setSignatureModification(refComPrefDomainDTO.getSignatureModification());
        refComPrefDomain.setSiteModification(refComPrefDomainDTO.getSiteModification());
        refComPrefDomain.setDateModification(refComPrefDomainDTO.getDateModification());
        refComPrefDomain.setSignatureCreation(refComPrefDomainDTO.getSignatureCreation());
        refComPrefDomain.setSiteCreation(refComPrefDomainDTO.getSiteCreation());
        refComPrefDomain.setDateCreation(refComPrefDomainDTO.getDateCreation());
    
    }

    /**
     * bo -> dto for a refComPrefDomain
     * @param pRefComPrefDomain bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefComPrefDomainDTO bo2DtoLight(RefComPrefDomain pRefComPrefDomain) throws JrafDomainException {
        // instanciation du DTO
        RefComPrefDomainDTO refComPrefDomainDTO = new RefComPrefDomainDTO();
        bo2DtoLight(pRefComPrefDomain, refComPrefDomainDTO);
        // on retourne le dto
        return refComPrefDomainDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPrefDomain bo
     * @param refComPrefDomainDTO dto
     */
    public static void bo2DtoLight(
        RefComPrefDomain refComPrefDomain,
        RefComPrefDomainDTO refComPrefDomainDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_1sT54DSaEeaR_YJoHRGtPg) ENABLED START*/

        bo2DtoLightImpl(refComPrefDomain, refComPrefDomainDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refComPrefDomain bo
     * @param refComPrefDomainDTO dto
     */
    private static void bo2DtoLightImpl(RefComPrefDomain refComPrefDomain,
        RefComPrefDomainDTO refComPrefDomainDTO){
    

        // simple properties
        refComPrefDomainDTO.setCodeDomain(refComPrefDomain.getCodeDomain());
        refComPrefDomainDTO.setLibelleDomain(refComPrefDomain.getLibelleDomain());
        refComPrefDomainDTO.setLibelleDomainEN(refComPrefDomain.getLibelleDomainEN());
        refComPrefDomainDTO.setSignatureModification(refComPrefDomain.getSignatureModification());
        refComPrefDomainDTO.setSiteModification(refComPrefDomain.getSiteModification());
        refComPrefDomainDTO.setDateModification(refComPrefDomain.getDateModification());
        refComPrefDomainDTO.setSignatureCreation(refComPrefDomain.getSignatureCreation());
        refComPrefDomainDTO.setSiteCreation(refComPrefDomain.getSiteCreation());
        refComPrefDomainDTO.setDateCreation(refComPrefDomain.getDateCreation());
    
    }
    
    /*PROTECTED REGION ID(_1sT54DSaEeaR_YJoHRGtPg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

