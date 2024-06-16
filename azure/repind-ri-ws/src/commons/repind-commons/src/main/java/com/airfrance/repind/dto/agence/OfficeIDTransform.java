package com.airfrance.repind.dto.agence;

/*PROTECTED REGION ID(_0VRuD2k1EeGhB9497mGnHw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.agence.OfficeID;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/*PROTECTED REGION END*/

/**
 * <p>Title : OfficeIDTransform.java</p>
 * transformation bo <-> dto pour un(e) OfficeID
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class OfficeIDTransform {

    /*PROTECTED REGION ID(_0VRuD2k1EeGhB9497mGnHw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private OfficeIDTransform() {
    }
    /**
     * dto -> bo for a OfficeID
     * @param officeIDDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static OfficeID dto2BoLight(OfficeIDDTO officeIDDTO) throws JrafDomainException {
        // instanciation du BO
        OfficeID officeID = new OfficeID();
        dto2BoLight(officeIDDTO, officeID);

        // on retourne le BO
        return officeID;
    }

    /**
     * dto -> bo for a officeID
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param officeIDDTO dto
     * @param officeID bo
     */
    public static void dto2BoLight(OfficeIDDTO officeIDDTO, OfficeID officeID) {
    
        /*PROTECTED REGION ID(dto2BoLight_0VRuD2k1EeGhB9497mGnHw) ENABLED START*/
        
        dto2BoLightImpl(officeIDDTO,officeID);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a officeID
     * @param officeIDDTO dto
     * @param officeID bo
     */
    private static void dto2BoLightImpl(OfficeIDDTO officeIDDTO, OfficeID officeID){
    
        // property of OfficeIDDTO
        officeID.setDateLastResa(officeIDDTO.getDateLastResa());
        officeID.setDateMaj(officeIDDTO.getDateMaj());
        officeID.setCle(officeIDDTO.getCle());
        officeID.setCodeGDS(officeIDDTO.getCodeGDS());
        officeID.setLettreComptoir(officeIDDTO.getLettreComptoir());
        officeID.setMajManuelle(officeIDDTO.getMajManuelle());
        officeID.setOfficeID(officeIDDTO.getOfficeID());
        officeID.setSignatureMaj(officeIDDTO.getSignatureMaj());
        officeID.setSiteMaj(officeIDDTO.getSiteMaj());
    
    }

    /**
     * bo -> dto for a officeID
     * @param pOfficeID bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static OfficeIDDTO bo2DtoLight(OfficeID pOfficeID) throws JrafDomainException {
        // instanciation du DTO
        OfficeIDDTO officeIDDTO = new OfficeIDDTO();
        bo2DtoLight(pOfficeID, officeIDDTO);
        // on retourne le dto
        return officeIDDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param officeID bo
     * @param officeIDDTO dto
     */
    public static void bo2DtoLight(
        OfficeID officeID,
        OfficeIDDTO officeIDDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_0VRuD2k1EeGhB9497mGnHw) ENABLED START*/

        bo2DtoLightImpl(officeID, officeIDDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param officeID bo
     * @param officeIDDTO dto
     */
    private static void bo2DtoLightImpl(OfficeID officeID,
        OfficeIDDTO officeIDDTO){
    

        // simple properties
        officeIDDTO.setDateLastResa(officeID.getDateLastResa());
        officeIDDTO.setDateMaj(officeID.getDateMaj());
        officeIDDTO.setCle(officeID.getCle());
        officeIDDTO.setCodeGDS(officeID.getCodeGDS());
        officeIDDTO.setLettreComptoir(officeID.getLettreComptoir());
        officeIDDTO.setMajManuelle(officeID.getMajManuelle());
        officeIDDTO.setOfficeID(officeID.getOfficeID());
        officeIDDTO.setSignatureMaj(officeID.getSignatureMaj());
        officeIDDTO.setSiteMaj(officeID.getSiteMaj());
    
    }
    
    /*PROTECTED REGION ID(_0VRuD2k1EeGhB9497mGnHw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /**
     * @param listOfficeIDDTO
     * @return
     * @throws JrafDomainException
     */
    public static OfficeID dto2Bo(OfficeIDDTO officeIDDTO) throws JrafDomainException {
    	if(officeIDDTO != null) 
    	{
    		
    			OfficeID oid = dto2BoLight(officeIDDTO);
    			if(officeIDDTO.getAgence() != null){
    			Agence ag = new Agence();
    		    AgenceTransform.dto2BoLight(officeIDDTO.getAgence(),ag);
    		    oid.setAgence(ag);
    			}
    			
    		return oid;
    	} 
    	else 
    	{
    		return null;
    	}
    }
    
    /**
     * @param dtos
     * @return bos
     * @throws JrafDomainException
     */
    public static Set<OfficeID> dto2Bo(Set<OfficeIDDTO> dtos) throws JrafDomainException {

        Set<OfficeID> bos = new HashSet<OfficeID>();

        if (dtos != null) {
            
            for (OfficeIDDTO dto : dtos) {

                OfficeID bo = dto2Bo(dto);
                bos.add(bo);
            }
            return bos;
            
        } else {
            
            return null;
        }
    }

    /**
     * @param listOfficeID
     * @return
     * @throws JrafDomainException
     */
    public static OfficeIDDTO bo2Dto(OfficeID officeID) throws JrafDomainException {
    	if(officeID != null) 
    	{
    		OfficeIDDTO officeIDDTO = new OfficeIDDTO();
    		officeIDDTO = bo2DtoLight(officeID);
    		AgenceDTO ag = new AgenceDTO();
    		AgenceTransform.bo2DtoLight(officeID.getAgence(), ag);
    		
    		officeIDDTO.setAgence(ag);
    			
    		return officeIDDTO;
    	} 
    	else 
    	{
    		return null;
    	}
    }
    
    /**
     * @param bos
     * @return dtos
     * @throws JrafDomainException
     */
    public static Set<OfficeIDDTO> bo2Dto(Set<OfficeID> bos) throws JrafDomainException {

        CopyOnWriteArraySet<OfficeIDDTO> dtos = new CopyOnWriteArraySet<OfficeIDDTO>();

        if (bos != null) {

            for (OfficeID bo : bos) {
                
                OfficeIDDTO dto = bo2Dto(bo);
                dtos.add(dto);
            }
            return dtos;
            
        } else {
            
            return null;
        }
    }
    /*PROTECTED REGION END*/
}

