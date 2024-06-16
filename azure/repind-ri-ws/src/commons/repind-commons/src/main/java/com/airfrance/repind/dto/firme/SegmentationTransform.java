package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_c5msALdcEeCrCZp8iGNNVw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.Segmentation;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : SegmentationTransform.java</p>
 * transformation bo <-> dto pour un(e) Segmentation
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class SegmentationTransform {

    /*PROTECTED REGION ID(_c5msALdcEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private SegmentationTransform() {
    }
    /**
     * dto -> bo for a Segmentation
     * @param segmentationDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Segmentation dto2BoLight(SegmentationDTO segmentationDTO) throws JrafDomainException {
        // instanciation du BO
        Segmentation segmentation = new Segmentation();
        dto2BoLight(segmentationDTO, segmentation);

        // on retourne le BO
        return segmentation;
    }

    /**
     * dto -> bo for a segmentation
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param segmentationDTO dto
     * @param segmentation bo
     */
    public static void dto2BoLight(SegmentationDTO segmentationDTO, Segmentation segmentation) {
    
        /*PROTECTED REGION ID(dto2BoLight_c5msALdcEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoLightImpl(segmentationDTO,segmentation);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a segmentation
     * @param segmentationDTO dto
     * @param segmentation bo
     */
    private static void dto2BoLightImpl(SegmentationDTO segmentationDTO, Segmentation segmentation){
    
        // property of SegmentationDTO
        segmentation.setCle(segmentationDTO.getCle());
        segmentation.setType(segmentationDTO.getType());
        segmentation.setNiveau(segmentationDTO.getNiveau());
        segmentation.setDateEntree(segmentationDTO.getDateEntree());
        segmentation.setDateSortie(segmentationDTO.getDateSortie());
        segmentation.setPotentiel(segmentationDTO.getPotentiel());
        segmentation.setMontant(segmentationDTO.getMontant());
        segmentation.setMonnaie(segmentationDTO.getMonnaie());
        segmentation.setPolitiqueVoyage(segmentationDTO.getPolitiqueVoyage());
    
    }

    /**
     * bo -> dto for a segmentation
     * @param pSegmentation bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static SegmentationDTO bo2DtoLight(Segmentation pSegmentation) throws JrafDomainException {
        // instanciation du DTO
        SegmentationDTO segmentationDTO = new SegmentationDTO();
        bo2DtoLight(pSegmentation, segmentationDTO);
        // on retourne le dto
        return segmentationDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param segmentation bo
     * @param segmentationDTO dto
     */
    public static void bo2DtoLight(
        Segmentation segmentation,
        SegmentationDTO segmentationDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_c5msALdcEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(segmentation, segmentationDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param segmentation bo
     * @param segmentationDTO dto
     */
    private static void bo2DtoLightImpl(Segmentation segmentation,
        SegmentationDTO segmentationDTO){
    

        // simple properties
        segmentationDTO.setCle(segmentation.getCle());
        segmentationDTO.setType(segmentation.getType());
        segmentationDTO.setNiveau(segmentation.getNiveau());
        segmentationDTO.setDateEntree(segmentation.getDateEntree());
        segmentationDTO.setDateSortie(segmentation.getDateSortie());
        segmentationDTO.setPotentiel(segmentation.getPotentiel());
        segmentationDTO.setMontant(segmentation.getMontant());
        segmentationDTO.setMonnaie(segmentation.getMonnaie());
        segmentationDTO.setPolitiqueVoyage(segmentation.getPolitiqueVoyage());
    
    }
    
    /*PROTECTED REGION ID(_c5msALdcEeCrCZp8iGNNVw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    
    /**
     * @param listSegmentationDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<Segmentation> dto2Bo(Set<SegmentationDTO> listSegmentationDTO) throws JrafDomainException {
    	if(listSegmentationDTO != null) 
    	{
    		Set<Segmentation> listSegmentation = new HashSet<Segmentation>();
    		for(SegmentationDTO SegmentationDTO : listSegmentationDTO) 
    		{
    			listSegmentation.add(dto2BoLight(SegmentationDTO));
    		}
    		return listSegmentation;
    	} 
    	else 
    	{
    		return null;
    	}
    }

    /**
     * @param listSegmentation
     * @return
     * @throws JrafDomainException
     */
    public static Set<SegmentationDTO> bo2Dto(Set<Segmentation> listSegmentation) throws JrafDomainException {
    	if(listSegmentation != null) 
    	{
    		Set<SegmentationDTO> listSegmentationDTO = new LinkedHashSet<SegmentationDTO>();
    		for(Segmentation Segmentation : listSegmentation) 
    		{
    			listSegmentationDTO.add(bo2DtoLight(Segmentation));
    		}
    		return listSegmentationDTO;
    	} 
    	else 
    	{
    		return null;
    	}
    }
    
    /*PROTECTED REGION END*/
}

