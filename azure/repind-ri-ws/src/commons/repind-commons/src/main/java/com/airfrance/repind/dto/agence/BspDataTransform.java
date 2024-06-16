package com.airfrance.repind.dto.agence;

/*PROTECTED REGION ID(_APi7YPqZEeaFPPuBEnmKFQ i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.agence.BspData;

/*PROTECTED REGION END*/

/**
 * <p>Title : BspDataTransform.java</p>
 * transformation bo <-> dto pour un(e) BspData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class BspDataTransform {

    /*PROTECTED REGION ID(_APi7YPqZEeaFPPuBEnmKFQ u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private BspDataTransform() {
    }
    /**
     * dto -> bo for a BspData
     * @param bspDataDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static BspData dto2BoLight(BspDataDTO bspDataDTO) throws JrafDomainException {
        // instanciation du BO
        BspData bspData = new BspData();
        dto2BoLight(bspDataDTO, bspData);

        // on retourne le BO
        return bspData;
    }

    /**
     * dto -> bo for a bspData
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param bspDataDTO dto
     * @param bspData bo
     */
    public static void dto2BoLight(BspDataDTO bspDataDTO, BspData bspData) {
    
        /*PROTECTED REGION ID(dto2BoLight_APi7YPqZEeaFPPuBEnmKFQ) ENABLED START*/
        
        dto2BoLightImpl(bspDataDTO,bspData);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a bspData
     * @param bspDataDTO dto
     * @param bspData bo
     */
    private static void dto2BoLightImpl(BspDataDTO bspDataDTO, BspData bspData){
    
        // property of BspDataDTO
        bspData.setBspDataId(bspDataDTO.getBspDataId());
        bspData.setGin(bspDataDTO.getGin());
        bspData.setType(bspDataDTO.getType());
        bspData.setCompany(bspDataDTO.getCompany());
        bspData.setAuthorization(bspDataDTO.getAuthorization());
    
    }

    /**
     * bo -> dto for a bspData
     * @param pBspData bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static BspDataDTO bo2DtoLight(BspData pBspData) throws JrafDomainException {
        // instanciation du DTO
        BspDataDTO bspDataDTO = new BspDataDTO();
        bo2DtoLight(pBspData, bspDataDTO);
        // on retourne le dto
        return bspDataDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param bspData bo
     * @param bspDataDTO dto
     */
    public static void bo2DtoLight(
        BspData bspData,
        BspDataDTO bspDataDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_APi7YPqZEeaFPPuBEnmKFQ) ENABLED START*/

        bo2DtoLightImpl(bspData, bspDataDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param bspData bo
     * @param bspDataDTO dto
     */
    private static void bo2DtoLightImpl(BspData bspData,
        BspDataDTO bspDataDTO){
    

        // simple properties
        bspDataDTO.setBspDataId(bspData.getBspDataId());
        bspDataDTO.setGin(bspData.getGin());
        bspDataDTO.setType(bspData.getType());
        bspDataDTO.setCompany(bspData.getCompany());
        bspDataDTO.setAuthorization(bspData.getAuthorization());
    
    }
    
    /*PROTECTED REGION ID(_APi7YPqZEeaFPPuBEnmKFQ u m - Tr) ENABLED START*/
    public static BspData dto2Bo(BspDataDTO bspDataDTO) throws JrafDomainException {
        // instanciation du BO
        BspData bspData = new BspData();
        dto2Bo(bspDataDTO, bspData);

        // on retourne le BO
        return bspData;
    }
    
    private static void dto2Bo(BspDataDTO bspDataDTO, BspData bspData) {
		
    	dto2BoImpl(bspDataDTO,bspData);
		
	}
    
    private static void dto2BoImpl(BspDataDTO bspDataDTO, BspData bspData){
        
        // property of BspDataDTO
        bspData.setBspDataId(bspDataDTO.getBspDataId());
        bspData.setGin(bspDataDTO.getGin());
        bspData.setType(bspDataDTO.getType());
        bspData.setCompany(bspDataDTO.getCompany());
        bspData.setAuthorization(bspDataDTO.getAuthorization());
        bspData.setDateCreation(bspDataDTO.getCreationDate());
        bspData.setSignatureCreation(bspDataDTO.getCreationSignature());
        bspData.setSiteCreation(bspDataDTO.getCreationSite());
        bspData.setDateModification(bspDataDTO.getModificationDate());
        bspData.setSignatureModification(bspDataDTO.getModificationSignature());
        bspData.setSiteModification(bspDataDTO.getModificationSite());
    
    }
    /*PROTECTED REGION END*/
	public static BspDataDTO bo2Dto(BspData element) {
		 BspDataDTO bspDataDTO = new BspDataDTO();
	        bo2Dto(element, bspDataDTO);
	        // on retourne le dto
	        return bspDataDTO;
	}
	private static void bo2Dto(BspData element, BspDataDTO bspDataDTO) {

		 // simple properties
        bspDataDTO.setBspDataId(element.getBspDataId());
        bspDataDTO.setGin(element.getGin());
        bspDataDTO.setType(element.getType());
        bspDataDTO.setCompany(element.getCompany());
        bspDataDTO.setAuthorization(element.getAuthorization());
        bspDataDTO.setCreationDate(element.getDateCreation());
        bspDataDTO.setCreationSignature(element.getSignatureCreation());
        bspDataDTO.setCreationSite(element.getSiteCreation());
        bspDataDTO.setModificationDate(element.getDateModification());
        bspDataDTO.setModificationSignature(element.getSignatureModification());
        bspDataDTO.setModificationSite(element.getSiteModification());
	}
}

