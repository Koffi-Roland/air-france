package com.airfrance.repind.dto.role;

/*PROTECTED REGION ID(_YiSLILd7EeSM_NEE6QydtQ i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.role.ContratTcSeq;

/*PROTECTED REGION END*/

/**
 * <p>Title : ContratTcSeqTransform.java</p>
 * transformation bo <-> dto pour un(e) ContratTcSeq
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ContratTcSeqTransform {

    /*PROTECTED REGION ID(_YiSLILd7EeSM_NEE6QydtQ u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ContratTcSeqTransform() {
    }
    /**
     * dto -> bo for a ContratTcSeq
     * @param contratTcSeqDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ContratTcSeq dto2BoLight(ContratTcSeqDTO contratTcSeqDTO) throws JrafDomainException {
        // instanciation du BO
        ContratTcSeq contratTcSeq = new ContratTcSeq();
        dto2BoLight(contratTcSeqDTO, contratTcSeq);

        // on retourne le BO
        return contratTcSeq;
    }

    /**
     * dto -> bo for a contratTcSeq
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param contratTcSeqDTO dto
     * @param contratTcSeq bo
     */
    public static void dto2BoLight(ContratTcSeqDTO contratTcSeqDTO, ContratTcSeq contratTcSeq) {
    
        /*PROTECTED REGION ID(dto2BoLight_YiSLILd7EeSM_NEE6QydtQ) ENABLED START*/
        
        dto2BoLightImpl(contratTcSeqDTO,contratTcSeq);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a contratTcSeq
     * @param contratTcSeqDTO dto
     * @param contratTcSeq bo
     */
    private static void dto2BoLightImpl(ContratTcSeqDTO contratTcSeqDTO, ContratTcSeq contratTcSeq){
    
        // property of ContratTcSeqDTO
        contratTcSeq.setNumeroTc(contratTcSeqDTO.getNumeroTc());
        contratTcSeq.setCodeType(contratTcSeqDTO.getCodeType());
        contratTcSeq.setNomTc(contratTcSeqDTO.getNomTc());
        contratTcSeq.setDateDebut(contratTcSeqDTO.getDateDebut());
        contratTcSeq.setDateFin(contratTcSeqDTO.getDateFin());
    
    }

    /**
     * bo -> dto for a contratTcSeq
     * @param pContratTcSeq bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ContratTcSeqDTO bo2DtoLight(ContratTcSeq pContratTcSeq) throws JrafDomainException {
        // instanciation du DTO
        ContratTcSeqDTO contratTcSeqDTO = new ContratTcSeqDTO();
        bo2DtoLight(pContratTcSeq, contratTcSeqDTO);
        // on retourne le dto
        return contratTcSeqDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param contratTcSeq bo
     * @param contratTcSeqDTO dto
     */
    public static void bo2DtoLight(
        ContratTcSeq contratTcSeq,
        ContratTcSeqDTO contratTcSeqDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_YiSLILd7EeSM_NEE6QydtQ) ENABLED START*/

        bo2DtoLightImpl(contratTcSeq, contratTcSeqDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param contratTcSeq bo
     * @param contratTcSeqDTO dto
     */
    private static void bo2DtoLightImpl(ContratTcSeq contratTcSeq,
        ContratTcSeqDTO contratTcSeqDTO){
    

        // simple properties
        contratTcSeqDTO.setNumeroTc(contratTcSeq.getNumeroTc());
        contratTcSeqDTO.setCodeType(contratTcSeq.getCodeType());
        contratTcSeqDTO.setNomTc(contratTcSeq.getNomTc());
        contratTcSeqDTO.setDateDebut(contratTcSeq.getDateDebut());
        contratTcSeqDTO.setDateFin(contratTcSeq.getDateFin());
    
    }
    
    /*PROTECTED REGION ID(_YiSLILd7EeSM_NEE6QydtQ u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

