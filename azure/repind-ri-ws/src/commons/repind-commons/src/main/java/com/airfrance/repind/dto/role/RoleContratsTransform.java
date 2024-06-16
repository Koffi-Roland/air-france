package com.airfrance.repind.dto.role;

/*PROTECTED REGION ID(_i7fq0PceEd-Kx8TJdz7fGw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.entity.role.RoleContrats;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : RoleContratsTransform.java</p>
 * transformation bo <-> dto pour un(e) RoleContrats
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RoleContratsTransform {

    /*PROTECTED REGION ID(_i7fq0PceEd-Kx8TJdz7fGw u var - Tr) ENABLED START*/
	private static Log LOGGER  = LogFactory.getLog(RoleContratsTransform.class);
	/*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RoleContratsTransform() {
    }
    /**
     * dto -> bo for a RoleContrats
     * @param roleContratsDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RoleContrats dto2BoLight(RoleContratsDTO roleContratsDTO) throws JrafDomainException {
        // instanciation du BO
        RoleContrats roleContrats = new RoleContrats();
        dto2BoLight(roleContratsDTO, roleContrats);
        dto2BoLink(roleContratsDTO, roleContrats);      //TODO cette ligne ne devrait pas être dans cette méthode
        // on retourne le BO
        return roleContrats;
    }

    /**
     * dto -> bo for a roleContrats
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param roleContratsDTO dto
     * @param roleContrats bo
     */
    public static void dto2BoLight(RoleContratsDTO roleContratsDTO, RoleContrats roleContrats) {
    
        /*PROTECTED REGION ID(dto2BoLight_i7fq0PceEd-Kx8TJdz7fGw) ENABLED START*/
        
        dto2BoLightImpl(roleContratsDTO,roleContrats);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a roleContrats
     * @param roleContratsDTO dto
     * @param roleContrats bo
     */
    private static void dto2BoLightImpl(RoleContratsDTO roleContratsDTO, RoleContrats roleContrats){
    
        // property of RoleContratsDTO
        roleContrats.setSrin(roleContratsDTO.getSrin());
        roleContrats.setVersion(roleContratsDTO.getVersion());
        roleContrats.setNumeroContrat(roleContratsDTO.getNumeroContrat());
        roleContrats.setGin(roleContratsDTO.getGin());
        roleContrats.setEtat(roleContratsDTO.getEtat());
        roleContrats.setTypeContrat(roleContratsDTO.getTypeContrat());
        roleContrats.setSousType(roleContratsDTO.getSousType());
        roleContrats.setTier(roleContratsDTO.getTier());
        roleContrats.setCodeCompagnie(roleContratsDTO.getCodeCompagnie());
        roleContrats.setVersionProduit(roleContratsDTO.getVersionProduit());
        roleContrats.setDateFinValidite(roleContratsDTO.getDateFinValidite());
        roleContrats.setDateDebutValidite(roleContratsDTO.getDateDebutValidite());
        roleContrats.setFamilleTraitement(roleContratsDTO.getFamilleTraitement());
        roleContrats.setFamilleProduit(roleContratsDTO.getFamilleProduit());
        roleContrats.setCleRole(roleContratsDTO.getCleRole());
        roleContrats.setDateCreation(roleContratsDTO.getDateCreation());
        roleContrats.setSignatureCreation(roleContratsDTO.getSignatureCreation());
        roleContrats.setDateModification(roleContratsDTO.getDateModification());
        roleContrats.setSignatureModification(roleContratsDTO.getSignatureModification());
        roleContrats.setSiteCreation(roleContratsDTO.getSiteCreation());
        roleContrats.setSiteModification(roleContratsDTO.getSiteModification());
        roleContrats.setAgenceIATA(roleContratsDTO.getAgenceIATA());
        roleContrats.setIata(roleContratsDTO.getIata());
        roleContrats.setSourceAdhesion(roleContratsDTO.getSourceAdhesion());
        roleContrats.setPermissionPrime(roleContratsDTO.getPermissionPrime());
        roleContrats.setSoldeMiles(roleContratsDTO.getSoldeMiles());
        roleContrats.setMilesQualif(roleContratsDTO.getMilesQualif());
        roleContrats.setMilesQualifPrec(roleContratsDTO.getMilesQualifPrec());
        roleContrats.setSegmentsQualif(roleContratsDTO.getSegmentsQualif());
        roleContrats.setSegmentsQualifPrec(roleContratsDTO.getSegmentsQualifPrec());
        roleContrats.setCuscoCreated(roleContratsDTO.getCuscoCreated());
        roleContrats.setMemberType(roleContratsDTO.getMemberType());
    
    }

    /**
     * bo -> dto for a roleContrats
     * @param pRoleContrats bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RoleContratsDTO bo2Dto(RoleContrats pRoleContrats) throws JrafDomainException {	// FB2.0
    	return bo2Dto(pRoleContrats, true);
    }
    
    public static RoleContratsDTO bo2Dto(RoleContrats pRoleContrats, boolean isFBRecognitionActivate) throws JrafDomainException {
        // instanciation du DTO
        RoleContratsDTO roleContratsDTO = new RoleContratsDTO();
        bo2DtoLight(pRoleContrats, roleContratsDTO, isFBRecognitionActivate);
        bo2DtoLink(pRoleContrats, roleContratsDTO);
        // on retourne le dto
        return roleContratsDTO;
    }
    
    /**
     * bo -> dto for a roleContrats
     * @param pRoleContrats bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RoleContratsDTO bo2DtoLight(RoleContrats pRoleContrats) throws JrafDomainException {
        // instanciation du DTO
        RoleContratsDTO roleContratsDTO = new RoleContratsDTO();
        bo2DtoLight(pRoleContrats, roleContratsDTO);
        // on retourne le dto
        return roleContratsDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param roleContrats bo
     * @param roleContratsDTO dto
     */
    public static void bo2DtoLight(RoleContrats roleContrats, RoleContratsDTO roleContratsDTO) {
    	bo2DtoLight(roleContrats, roleContratsDTO, true);
    }
    
    public static void bo2DtoLight(RoleContrats roleContrats, RoleContratsDTO roleContratsDTO, boolean isFBRecognitionActivate) {
        /*PROTECTED REGION ID(bo2DtoLight_i7fq0PceEd-Kx8TJdz7fGw) ENABLED START*/
        bo2DtoLightImpl(roleContrats, roleContratsDTO, isFBRecognitionActivate);
        /*PROTECTED REGION END*/
    }

    private static void bo2DtoLightImpl(RoleContrats roleContrats,
        RoleContratsDTO roleContratsDTO, boolean isFBRecognitionActivate){

        // simple properties
        roleContratsDTO.setSrin(roleContrats.getSrin());
        roleContratsDTO.setVersion(roleContrats.getVersion());
        roleContratsDTO.setNumeroContrat(roleContrats.getNumeroContrat());
        roleContratsDTO.setGin(roleContrats.getGin());
        roleContratsDTO.setEtat(roleContrats.getEtat());
        roleContratsDTO.setTypeContrat(roleContrats.getTypeContrat());
        roleContratsDTO.setDateFinValidite(roleContrats.getDateFinValidite());
        roleContratsDTO.setDateDebutValidite(roleContrats.getDateDebutValidite());
        roleContratsDTO.setCleRole(roleContrats.getCleRole());
        roleContratsDTO.setDateCreation(roleContrats.getDateCreation());
        roleContratsDTO.setSignatureCreation(roleContrats.getSignatureCreation());
        roleContratsDTO.setDateModification(roleContrats.getDateModification());
        roleContratsDTO.setSignatureModification(roleContrats.getSignatureModification());
        roleContratsDTO.setSiteCreation(roleContrats.getSiteCreation());
        roleContratsDTO.setSiteModification(roleContrats.getSiteModification());
        roleContratsDTO.setSousType(roleContrats.getSousType());
        roleContratsDTO.setCodeCompagnie(roleContrats.getCodeCompagnie());
        roleContratsDTO.setFamilleProduit(roleContrats.getFamilleProduit());

    }
    
    /*PROTECTED REGION ID(_i7fq0PceEd-Kx8TJdz7fGw u m - Tr) ENABLED START*/

    /**
     * @param listRoleContrats
     * @return
     * @throws JrafDomainException
     */
    public static List<RoleContratsDTO> bo2Dto(List<RoleContrats> listRoleContrats) throws JrafDomainException {
    	return bo2Dto(listRoleContrats, true);
    }
    
    public static List<RoleContratsDTO> bo2Dto(List<RoleContrats> listRoleContrats, boolean isFBRecognitionActivate) throws JrafDomainException {
    	if(listRoleContrats != null) {
    		List<RoleContratsDTO> listRoleContratsDTO = new ArrayList<RoleContratsDTO>();
    		for(RoleContrats roleContrat : listRoleContrats) {
    			listRoleContratsDTO.add(bo2Dto(roleContrat, isFBRecognitionActivate));
    		}
    		return listRoleContratsDTO;
    	} else {
    		return null;
    	}
    }
    
    /**
     * @param listRoleContrats
     * @return
     * @throws JrafDomainException
     */
    public static Set<RoleContratsDTO> bo2Dto(Set<RoleContrats> listRoleContrats) throws JrafDomainException {
    	return bo2Dto(listRoleContrats, true);
    }
    
    public static Set<RoleContratsDTO> bo2Dto(Set<RoleContrats> listRoleContrats, boolean isFBRecognitionActivate) throws JrafDomainException {
    	if(listRoleContrats != null) {
    		Set<RoleContratsDTO> listRoleContratsDTO = new HashSet<RoleContratsDTO>();
    		for(RoleContrats roleContrat : listRoleContrats) {
    			listRoleContratsDTO.add(bo2Dto(roleContrat, isFBRecognitionActivate));
    		}
    		return listRoleContratsDTO;
    	} else {
    		return null;
    	}
    }

    
    public static Set<RoleContratsDTO> bo2DtoLight(Set<RoleContrats> listRoleContrats) throws JrafDomainException {
    	if(listRoleContrats != null) {
    		Set<RoleContratsDTO> listRoleContratsDTO = new HashSet<RoleContratsDTO>();
    		for(RoleContrats roleContrat : listRoleContrats) {
    			listRoleContratsDTO.add(bo2DtoLight(roleContrat));
    		}
    		return listRoleContratsDTO;
    	} else {
    		return null;
    	}
    }
    /**
     * @param listRoleContratsDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<RoleContrats> dto2Bo(Set<RoleContratsDTO> listRoleContratsDTO) throws JrafDomainException {
    	if(listRoleContratsDTO != null) {
    		Set<RoleContrats> listRoleContrats = new HashSet<RoleContrats>();
    		for(RoleContratsDTO roleContratdto : listRoleContratsDTO) {
    			listRoleContrats.add(dto2BoLight(roleContratdto));
    		}
    		return listRoleContrats;
    	} else {
    		return null;
    	}
    }

    public static List<RoleContrats> dto2Bo(List<RoleContratsDTO> listRoleContratsDTO) throws JrafDomainException {
    	if(listRoleContratsDTO != null) {
    		List<RoleContrats> listRoleContrats = new ArrayList<RoleContrats>();
    		for(RoleContratsDTO roleContratdto : listRoleContratsDTO) {
    			listRoleContrats.add(dto2BoLight(roleContratdto));
    		}
    		return listRoleContrats;
    	} else {
    		return null;
    	}
    }
    
    /**
     * @param roleContrats
     * @param roleContratsDTO
     * @return
     */
    public static void bo2DtoLink(RoleContrats roleContrats, RoleContratsDTO roleContratsDTO) {
    	
    	BusinessRole businessRole = roleContrats.getBusinessrole();
    	
    	if(businessRole!=null) {
			try {
				BusinessRoleDTO businessRoleDTO = BusinessRoleTransform.bo2DtoLight(businessRole);
				roleContratsDTO.setBusinessroledto(businessRoleDTO);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
    	}
    	
    	Individu individu = roleContrats.getIndividu();
    	
    	if(individu!=null) {
			try {
				IndividuDTO individuDTO = IndividuTransform.bo2DtoLight(individu);
				roleContratsDTO.setIndividudto(individuDTO);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
    	}
    	
    }
    
    /**
     * @param roleContratsDTO
     * @param roleContrats
     * @return
     */
    public static void dto2BoLink(RoleContratsDTO roleContratsDTO,RoleContrats roleContrats) {
    	
    	BusinessRoleDTO businessRoleDTO = roleContratsDTO.getBusinessroledto();
    	
    	if(businessRoleDTO!=null) {
			try {
				BusinessRole businessRole = BusinessRoleTransform.dto2BoLight(businessRoleDTO);
				roleContrats.setBusinessrole(businessRole);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
    	}
    	
    	IndividuDTO individuDTO = roleContratsDTO.getIndividudto();
    	
    	if(individuDTO!=null) {
			try {
				Individu individu = IndividuTransform.dto2BoLight(individuDTO);
				roleContrats.setIndividu(individu);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
    	}
    	
    }

	public static RoleContratsDTO boToDto(Tuple tuple) {
		RoleContratsDTO roleContratsDTO = new RoleContratsDTO();

		roleContratsDTO.setGin((String) tuple.get(0));
		roleContratsDTO.setNumeroContrat((String) tuple.get(1));

		return roleContratsDTO;
	}

	/* PROTECTED REGION END */
}
