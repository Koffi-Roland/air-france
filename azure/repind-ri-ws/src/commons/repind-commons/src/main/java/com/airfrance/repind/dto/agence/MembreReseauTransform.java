package com.airfrance.repind.dto.agence;

/*PROTECTED REGION ID(_0VRuCWk1EeGhB9497mGnHw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.reference.ReseauTransform;
import com.airfrance.repind.entity.agence.MembreReseau;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : MembreReseauTransform.java</p>
 * transformation bo <-> dto pour un(e) MembreReseau
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class MembreReseauTransform {

    /*PROTECTED REGION ID(_0VRuCWk1EeGhB9497mGnHw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private MembreReseauTransform() {
    }
    /**
     * dto -> bo for a MembreReseau
     * @param membreReseauDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static MembreReseau dto2BoLight(MembreReseauDTO membreReseauDTO) throws JrafDomainException {
        // instanciation du BO
        MembreReseau membreReseau = new MembreReseau();
        dto2BoLight(membreReseauDTO, membreReseau);

        // on retourne le BO
        return membreReseau;
    }

    /**
     * dto -> bo for a membreReseau
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param membreReseauDTO dto
     * @param membreReseau bo
     * @throws JrafDomainException 
     */
    public static void dto2BoLight(MembreReseauDTO membreReseauDTO, MembreReseau membreReseau) throws JrafDomainException {
    
        /*PROTECTED REGION ID(dto2BoLight_0VRuCWk1EeGhB9497mGnHw) ENABLED START*/
        
        dto2BoLightImpl(membreReseauDTO,membreReseau);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a membreReseau
     * @param membreReseauDTO dto
     * @param membreReseau bo
     * @throws JrafDomainException 
     */
    private static void dto2BoLightImpl(MembreReseauDTO membreReseauDTO, MembreReseau membreReseau) throws JrafDomainException{
    
        // property of MembreReseauDTO
        membreReseau.setDateDebut(membreReseauDTO.getDateDebut());
        membreReseau.setDateFin(membreReseauDTO.getDateFin());
        membreReseau.setCle(membreReseauDTO.getCle());
        if(membreReseauDTO.getAgence() != null) {
        	membreReseau.setAgence(AgenceTransform.dto2BoLight(membreReseauDTO.getAgence()));
        }

		if (membreReseauDTO.getReseau() != null) {
			membreReseau.setReseau(ReseauTransform.dto2BoLight(membreReseauDTO.getReseau()));
		}
    
    }

    /**
     * bo -> dto for a membreReseau
     * @param pMembreReseau bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static MembreReseauDTO bo2DtoLight(MembreReseau pMembreReseau) throws JrafDomainException {
        // instanciation du DTO
        MembreReseauDTO membreReseauDTO = new MembreReseauDTO();
        bo2DtoLight(pMembreReseau, membreReseauDTO);
        // on retourne le dto
        return membreReseauDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param membreReseau bo
     * @param membreReseauDTO dto
     */
    public static void bo2DtoLight(
        MembreReseau membreReseau,
        MembreReseauDTO membreReseauDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_0VRuCWk1EeGhB9497mGnHw) ENABLED START*/

        bo2DtoLightImpl(membreReseau, membreReseauDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param membreReseau bo
     * @param membreReseauDTO dto
     */
    private static void bo2DtoLightImpl(MembreReseau membreReseau,
        MembreReseauDTO membreReseauDTO){
    

        // simple properties
        membreReseauDTO.setDateDebut(membreReseau.getDateDebut());
        membreReseauDTO.setDateFin(membreReseau.getDateFin());
        membreReseauDTO.setCle(membreReseau.getCle());
    
    }
    
    /*PROTECTED REGION ID(_0VRuCWk1EeGhB9497mGnHw u m - Tr) ENABLED START*/
    /**
     * @param dtos
     * @return bos
     * @throws JrafDomainException
     */
    public static Set<MembreReseau> dto2Bo(Set<MembreReseauDTO> dtos) throws JrafDomainException {
        
        if (dtos != null) {
            
            Set<MembreReseau> bos = new HashSet<MembreReseau>();
            for (MembreReseauDTO dto : dtos) {
                
                MembreReseau bo = dto2BoLight(dto);
				if (dto.getReseau() != null) {
					bo.setReseau(ReseauTransform.dto2BoLight(dto.getReseau()));
				}
				
				bos.add(bo);
            }
            return bos;
            
        } else {
            
            return null;
        }
    }
    
	/**
	 * @param dto
	 * @return bo
	 * @throws JrafDomainException
	 */
	public static MembreReseau dto2Bo(MembreReseauDTO dto) throws JrafDomainException {

		if (dto != null) {

			MembreReseau bo = dto2BoLight(dto);
			if (dto.getReseau() != null) {
				bo.setReseau(ReseauTransform.dto2BoLight(dto.getReseau()));
			}

			return bo;

		} else {

			return null;
		}
	}

    /**
     * @param listNumeroIdent
     * @return
     * @throws JrafDomainException
     */
	public static Set<MembreReseauDTO> bo2Dto(Set<MembreReseau> bos) throws JrafDomainException {

		if (bos != null) {

			Set<MembreReseauDTO> dtos = new HashSet<>();
			for (MembreReseau bo : bos) {

				MembreReseauDTO dto = bo2DtoLight(bo);
				if (bo.getReseau() != null) {
					dto.setReseau(ReseauTransform.bo2DtoLight(bo.getReseau()));
					dtos.add(dto);
				}
			}
			return dtos;

		} else {

			return null;
		}
	}
	
    /**
     * @param MembreReseau
     * @return
     * @throws JrafDomainException
     */
	public static MembreReseauDTO bo2Dto(MembreReseau bo) throws JrafDomainException {

		if (bo != null) {
			MembreReseauDTO dto = bo2DtoLight(bo);
			if (bo.getReseau() != null) {
				dto.setReseau(ReseauTransform.bo2DtoLight(bo.getReseau()));

			}
			return dto;

		} else {

			return null;
		}
	}

    /*PROTECTED REGION END*/
}

