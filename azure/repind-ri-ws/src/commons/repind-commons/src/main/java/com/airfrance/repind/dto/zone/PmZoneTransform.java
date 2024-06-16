package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_NoNowLdgEeCrCZp8iGNNVw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.agence.AgenceDTO;
import com.airfrance.repind.dto.agence.AgenceTransform;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.zone.PmZone;

import java.util.LinkedHashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : PmZoneTransform.java</p>
 * transformation bo <-> dto pour un(e) PmZone
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class PmZoneTransform {

    /*PROTECTED REGION ID(_NoNowLdgEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private PmZoneTransform() {
    }
    /**
     * dto -> bo for a PmZone
     * @param pmZoneDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static PmZone dto2BoLight(PmZoneDTO pmZoneDTO) throws JrafDomainException {
        // instanciation du BO
        PmZone pmZone = new PmZone();
        dto2BoLight(pmZoneDTO, pmZone);

        // on retourne le BO
        return pmZone;
    }

    /**
     * dto -> bo for a pmZone
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param pmZoneDTO dto
     * @param pmZone bo
     */
    public static void dto2BoLight(PmZoneDTO pmZoneDTO, PmZone pmZone) {
    
        /*PROTECTED REGION ID(dto2BoLight_NoNowLdgEeCrCZp8iGNNVw) ENABLED START*/

        dto2BoLightImpl(pmZoneDTO, pmZone);

        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a pmZone
     * @param pmZoneDTO dto
     * @param pmZone bo
     */
    private static void dto2BoLightImpl(PmZoneDTO pmZoneDTO, PmZone pmZone){
    
        // property of PmZoneDTO
        pmZone.setCle(pmZoneDTO.getCle());
        pmZone.setLienPrivilegie(pmZoneDTO.getLienPrivilegie());
        pmZone.setDateOuverture(pmZoneDTO.getDateOuverture());
        pmZone.setDateFermeture(pmZoneDTO.getDateFermeture());
        pmZone.setOrigine(pmZoneDTO.getOrigine());
        pmZone.setDateModif(pmZoneDTO.getDateModif());
        pmZone.setSignature(pmZoneDTO.getSignature());
        pmZone.setUsage(pmZoneDTO.getUsage());
    
    }

    /**
     * bo -> dto for a pmZone
     * @param pPmZone bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static PmZoneDTO bo2DtoLight(PmZone pPmZone) throws JrafDomainException {
        // instanciation du DTO
        PmZoneDTO pmZoneDTO = new PmZoneDTO();
        bo2DtoLight(pPmZone, pmZoneDTO);
        // on retourne le dto
        return pmZoneDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param pmZone bo
     * @param pmZoneDTO dto
     */
    public static void bo2DtoLight(
        PmZone pmZone,
        PmZoneDTO pmZoneDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_NoNowLdgEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(pmZone, pmZoneDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param pmZone bo
     * @param pmZoneDTO dto
     */
    private static void bo2DtoLightImpl(PmZone pmZone,
        PmZoneDTO pmZoneDTO){
    

        // simple properties
        pmZoneDTO.setCle(pmZone.getCle());
        pmZoneDTO.setLienPrivilegie(pmZone.getLienPrivilegie());
        pmZoneDTO.setDateOuverture(pmZone.getDateOuverture());
        pmZoneDTO.setDateFermeture(pmZone.getDateFermeture());
        pmZoneDTO.setOrigine(pmZone.getOrigine());
        pmZoneDTO.setDateModif(pmZone.getDateModif());
        pmZoneDTO.setSignature(pmZone.getSignature());
        pmZoneDTO.setUsage(pmZone.getUsage());
    
    }
    
    /*PROTECTED REGION ID(_NoNowLdgEeCrCZp8iGNNVw u m - Tr) ENABLED START*/
    public static Set<PmZoneDTO> bo2Dto(Set<PmZone> pmZone) throws JrafDomainException {

        if (pmZone != null) {
        	Set<PmZoneDTO> listPmZoneDTO = new LinkedHashSet<PmZoneDTO>();
            for (PmZone pmz : pmZone) {
                listPmZoneDTO.add(bo2Dto(pmz));
            }
            return listPmZoneDTO;
        } else {
            return null;
        }
    }

    /**
     * @param listBusinessRole
     * @return
     * @throws JrafDomainException
     */
 public static PmZoneDTO bo2Dto(PmZone pmZone) throws JrafDomainException {
        
        if (pmZone != null) {
            
            PmZoneDTO pmZoneDTO = bo2DtoLight(pmZone);
            if (pmZone.getZoneDecoup() != null) {                
                pmZoneDTO.setZoneDecoup(ZoneDecoupTransform.bo2DtoLight(pmZone.getZoneDecoup()));
            }
            
            if (pmZone.getPersonneMorale() != null) 
            {
            	if (pmZone.getPersonneMorale().getClass().equals(Agence.class))
            		pmZoneDTO.setPersonneMorale(AgenceTransform.bo2DtoLight((Agence) pmZone.getPersonneMorale()));
            }
            
            return pmZoneDTO;
        } else {
            
            return null;
        }
    }
    
    /**
     * dto -> bo for a PmZone calls dto2BoLight in a protected region so the user can override this without
     * 
     * @param pmZoneDTO
     *            dto
     * @param pmZone
     *            bo
     */
 public static PmZone dto2Bo(PmZoneDTO pmZoneDTO) throws JrafDomainException {

     PmZone pmZone = new PmZone();
     pmZone = dto2BoLight(pmZoneDTO);

     if (pmZoneDTO.getZoneDecoup() != null)
     	pmZone.setZoneDecoup(ZoneDecoupTransform.dto2BoLight(pmZoneDTO.getZoneDecoup()));

     if (pmZoneDTO.getZoneDecoup().getPmZones() != null)
     	pmZone.setZoneDecoup(ZoneDecoupTransform.dto2BoLight(pmZoneDTO.getZoneDecoup()));

     if (pmZoneDTO.getPersonneMorale() != null)
     {
     	if (pmZoneDTO.getPersonneMorale().getClass().equals(AgenceDTO.class))        
     		pmZone.setPersonneMorale(AgenceTransform.dto2BoLight((AgenceDTO) pmZoneDTO.getPersonneMorale()));
     }

     // on retourne le bo
     return pmZone;
 }
    /*PROTECTED REGION END*/
    
    public static Set<PmZone> dto2Bo(Set<PmZoneDTO> pmZoneDTO) throws JrafDomainException {

        if (pmZoneDTO != null) {
        	Set<PmZone> listPmZone = new LinkedHashSet<PmZone>();
            for (PmZoneDTO pmzDTO : pmZoneDTO) {
                listPmZone.add(dto2Bo(pmzDTO));
            }
            return listPmZone;
        }
        return null;
    }
}

