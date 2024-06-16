package com.afklm.rigui.dto.reference;

/*PROTECTED REGION ID(_san6wP2KEeaexJbSRqCy0Q i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefProduct;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefProductTransform.java</p>
 * transformation bo <-> dto pour un(e) RefProduct
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefProductTransform {

    /*PROTECTED REGION ID(_san6wP2KEeaexJbSRqCy0Q u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefProductTransform() {
    }
    /**
     * dto -> bo for a RefProduct
     * @param refProductDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefProduct dto2BoLight(RefProductDTO refProductDTO) throws JrafDomainException {
        // instanciation du BO
        RefProduct refProduct = new RefProduct();
        dto2BoLight(refProductDTO, refProduct);

        // on retourne le BO
        return refProduct;
    }

    /**
     * dto -> bo for a refProduct
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refProductDTO dto
     * @param refProduct bo
     */
    public static void dto2BoLight(RefProductDTO refProductDTO, RefProduct refProduct) {
    
        /*PROTECTED REGION ID(dto2BoLight_san6wP2KEeaexJbSRqCy0Q) ENABLED START*/
        
        dto2BoLightImpl(refProductDTO,refProduct);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refProduct
     * @param refProductDTO dto
     * @param refProduct bo
     */
    private static void dto2BoLightImpl(RefProductDTO refProductDTO, RefProduct refProduct){
    
        // property of RefProductDTO
        refProduct.setProductId(refProductDTO.getProductId());
        refProduct.setContractType(refProductDTO.getContractType());
        refProduct.setProductType(refProductDTO.getProductType());
        refProduct.setSubProductType(refProductDTO.getSubProductType());
        refProduct.setProductName(refProductDTO.getProductName());
        refProduct.setContractId(refProductDTO.getContractId());
        refProduct.setGenerateComPref(refProductDTO.getGenerateComPref());
        refProduct.setAssociatedComPref(refProductDTO.getAssociatedComPrefDomain());
        refProduct.setDateCreation(refProductDTO.getDateCreation());
        refProduct.setSiteCreation(refProductDTO.getSiteCreation());
        refProduct.setSignatureCreation(refProductDTO.getSignatureCreation());
        refProduct.setDateModification(refProductDTO.getDateModification());
        refProduct.setSiteModification(refProductDTO.getSiteModification());
        refProduct.setSignatureModification(refProductDTO.getSignatureModification());
    
    }

    /**
     * bo -> dto for a refProduct
     * @param pRefProduct bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefProductDTO bo2DtoLight(RefProduct pRefProduct) throws JrafDomainException {
        // instanciation du DTO
        RefProductDTO refProductDTO = new RefProductDTO();
        bo2DtoLight(pRefProduct, refProductDTO);
        // on retourne le dto
        return refProductDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refProduct bo
     * @param refProductDTO dto
     */
    public static void bo2DtoLight(
        RefProduct refProduct,
        RefProductDTO refProductDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_san6wP2KEeaexJbSRqCy0Q) ENABLED START*/

        bo2DtoLightImpl(refProduct, refProductDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refProduct bo
     * @param refProductDTO dto
     */
    private static void bo2DtoLightImpl(RefProduct refProduct,
        RefProductDTO refProductDTO){
    

        // simple properties
        refProductDTO.setProductId(refProduct.getProductId());
        refProductDTO.setContractType(refProduct.getContractType());
        refProductDTO.setProductType(refProduct.getProductType());
        refProductDTO.setSubProductType(refProduct.getSubProductType());
        refProductDTO.setProductName(refProduct.getProductName());
        refProductDTO.setContractId(refProduct.getContractId());
        refProductDTO.setGenerateComPref(refProduct.getGenerateComPref());
        refProductDTO.setAssociatedComPrefDomain(refProduct.getAssociatedComPref());
        refProductDTO.setDateCreation(refProduct.getDateCreation());
        refProductDTO.setSiteCreation(refProduct.getSiteCreation());
        refProductDTO.setSignatureCreation(refProduct.getSignatureCreation());
        refProductDTO.setDateModification(refProduct.getDateModification());
        refProductDTO.setSiteModification(refProduct.getSiteModification());
        refProductDTO.setSignatureModification(refProduct.getSignatureModification());
    
    }
    
    /*PROTECTED REGION ID(_san6wP2KEeaexJbSRqCy0Q u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

