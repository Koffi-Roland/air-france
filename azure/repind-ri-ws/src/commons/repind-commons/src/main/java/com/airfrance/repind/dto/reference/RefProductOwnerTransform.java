package com.airfrance.repind.dto.reference;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefOwner;
import com.airfrance.repind.entity.reference.RefProduct;
import com.airfrance.repind.entity.reference.RefProductOwner;
import com.airfrance.repind.entity.reference.RefProductOwnerId;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefProductOwnerTransform.java</p>
 * transformation bo <-> dto pour un(e) RefProductOwner
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefProductOwnerTransform {

    /*PROTECTED REGION ID(_50GLYP2MEeaexJbSRqCy0Q u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefProductOwnerTransform() {
    }
    /**
     * dto -> bo for a RefProductOwner
     * @param refProductOwnerDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefProductOwner dto2BoLight(RefProductOwnerDTO refProductOwnerDTO) throws JrafDomainException {
        // instanciation du BO
        RefProductOwner refProductOwner = new RefProductOwner();
        dto2BoLight(refProductOwnerDTO, refProductOwner);

        // on retourne le BO
        return refProductOwner;
    }

    /**
     * dto -> bo for a refProductOwner
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refProductOwnerDTO dto
     * @param refProductOwner bo
     */
    public static void dto2BoLight(RefProductOwnerDTO refProductOwnerDTO, RefProductOwner refProductOwner) {
    
        /*PROTECTED REGION ID(dto2BoLight_50GLYP2MEeaexJbSRqCy0Q) ENABLED START*/
        
        dto2BoLightImpl(refProductOwnerDTO,refProductOwner);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refProductOwner
     * @param refProductOwnerDTO dto
     * @param refProductOwner bo
     */
    private static void dto2BoLightImpl(RefProductOwnerDTO refProductOwnerDTO, RefProductOwner refProductOwner){
    
        // property of RefProductOwnerDTO
        refProductOwner.setId(dto2BoEmbeddedEntity(refProductOwnerDTO));
        refProductOwner.setDateCreation(refProductOwnerDTO.getDateCreation());
        refProductOwner.setSiteCreation(refProductOwnerDTO.getSiteCreation());
        refProductOwner.setSignatureCreation(refProductOwnerDTO.getSignatureCreation());
        refProductOwner.setDateModification(refProductOwnerDTO.getDateModification());
        refProductOwner.setSiteModification(refProductOwnerDTO.getSiteModification());
        refProductOwner.setSignatureModification(refProductOwnerDTO.getSignatureModification());
    
    }

    /**
     * bo -> dto for a refProductOwner
     * @param pRefProductOwner bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefProductOwnerDTO bo2DtoLight(RefProductOwner pRefProductOwner) throws JrafDomainException {
        // instanciation du DTO
        RefProductOwnerDTO refProductOwnerDTO = new RefProductOwnerDTO();
        bo2DtoLight(pRefProductOwner, refProductOwnerDTO);
        // on retourne le dto
        return refProductOwnerDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refProductOwner bo
     * @param refProductOwnerDTO dto
     */
    public static void bo2DtoLight(
        RefProductOwner refProductOwner,
        RefProductOwnerDTO refProductOwnerDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_50GLYP2MEeaexJbSRqCy0Q) ENABLED START*/

        bo2DtoLightImpl(refProductOwner, refProductOwnerDTO);
        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refProductOwner bo
     * @param refProductOwnerDTO dto
     */
    private static void bo2DtoLightImpl(RefProductOwner refProductOwner,
        RefProductOwnerDTO refProductOwnerDTO){
    

        // simple properties
        refProductOwnerDTO.setId(bo2DtoEmbeddedEntity(refProductOwner));

        refProductOwnerDTO.setDateCreation(refProductOwner.getDateCreation());
        refProductOwnerDTO.setSiteCreation(refProductOwner.getSiteCreation());
        refProductOwnerDTO.setSignatureCreation(refProductOwner.getSignatureCreation());
        refProductOwnerDTO.setDateModification(refProductOwner.getDateModification());
        refProductOwnerDTO.setSiteModification(refProductOwner.getSiteModification());
        refProductOwnerDTO.setSignatureModification(refProductOwner.getSignatureModification());
    
    }
    
    /*PROTECTED REGION ID(_50GLYP2MEeaexJbSRqCy0Q u m - Tr) ENABLED START*/
    /**
     * Transform the two objects from the OneToOne association from bo to DTO
     * @param bo refProductOwner
     * @param dto refProductOwnerDTO
     * @throws JrafDomainException
     */
    public static void bo2DtoOneToOneRelation(RefProductOwnerId bo, RefProductOwnerIdDTO dto){
    	RefOwnerDTO roDto = dto.getRefOwner() == null ? new RefOwnerDTO() : dto.getRefOwner();
    	RefOwnerTransform.bo2DtoLight(bo.getRefOwner(), roDto);
    	dto.setRefOwner(roDto);
    	
    	RefProductDTO rpDto = dto.getRefProduct() == null ? new RefProductDTO() : dto.getRefProduct();
    	RefProductTransform.bo2DtoLight(bo.getRefProduct(), rpDto);
    	dto.setRefProduct(rpDto);
    }

    /**
     * Transform the two objects from the OneToOne association from DTO to BO
     * @param bo refProductOwner
     * @param dto refProductOwnerDTO
     * @throws JrafDomainException
     */
    public static void dto2BoOneToOneRelation(RefProductOwnerIdDTO dto, RefProductOwnerId bo) {
    	RefOwner ro = bo.getRefOwner() == null ? new RefOwner() : bo.getRefOwner();
    	RefOwnerTransform.dto2BoLight(dto.getRefOwner(), ro);
    	bo.setRefOwner(ro);
    	
    	RefProduct rp = bo.getRefProduct() == null ? new RefProduct() : bo.getRefProduct();
    	RefProductTransform.dto2BoLight(dto.getRefProduct(), rp);
    	bo.setRefProduct(rp);
    }
    
    public static RefProductOwnerId dto2BoEmbeddedEntity(RefProductOwnerDTO dto) {
    	RefProductOwnerId rpoId = new RefProductOwnerId();
    	RefProductOwnerIdDTO idDto;
    	if (dto.getId() == null) {
    		idDto = new RefProductOwnerIdDTO();
    		idDto.setRefOwner(dto.getId().getRefOwner());
    		idDto.setRefProduct(dto.getId().getRefProduct());
    	} else {
    		idDto = dto.getId();
    	}
    	
    	dto2BoOneToOneRelation(idDto, rpoId);

    	return rpoId;
    }
    
    public static RefProductOwnerIdDTO bo2DtoEmbeddedEntity(RefProductOwner bo) {
    	RefProductOwnerIdDTO idDto = new RefProductOwnerIdDTO();
    	RefProductOwnerId id;
    	if (bo.getId() == null) {
    		id = new RefProductOwnerId();
    		id.setRefOwner(bo.getId().getRefOwner());
    		id.setRefProduct(bo.getId().getRefProduct());
    	} else {
    		id = bo.getId();
    	}
    	
    	bo2DtoOneToOneRelation(id, idDto);

    	return idDto;
    }
    
    /*PROTECTED REGION END*/
}

