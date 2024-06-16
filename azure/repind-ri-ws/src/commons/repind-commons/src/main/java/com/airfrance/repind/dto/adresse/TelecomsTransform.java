
package com.airfrance.repind.dto.adresse;

/*PROTECTED REGION ID(_uZqOIDOhEeCokvyNKVv2PQ i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.dto.telecom.NormalizePhoneNumberDTO;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.individu.Individu;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : TelecomsTransform.java</p>
 * transformation bo <-> dto pour un(e) Telecoms
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class TelecomsTransform {

    /*PROTECTED REGION ID(_uZqOIDOhEeCokvyNKVv2PQ u var - Tr) ENABLED START*/
	private static Log LOGGER  = LogFactory.getLog(TelecomsTransform.class);
	/*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private TelecomsTransform() {
    }
    /**
     * dto -> bo for a Telecoms
     * @param telecomsDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Telecoms dto2BoLight(TelecomsDTO telecomsDTO) throws JrafDomainException {
        // instanciation du BO
        Telecoms telecoms = new Telecoms();
        dto2BoLight(telecomsDTO, telecoms);

        // on retourne le BO
        return telecoms;
    }

    /**
     * dto -> bo for a telecoms
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param telecomsDTO dto
     * @param telecoms bo
     */
    public static void dto2BoLight(TelecomsDTO telecomsDTO, Telecoms telecoms) {
    
        /*PROTECTED REGION ID(dto2BoLight_uZqOIDOhEeCokvyNKVv2PQ) ENABLED START*/
        
        dto2BoLightImpl(telecomsDTO,telecoms);
        telecoms.setCountryCode(telecomsDTO.getCountryCode());
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a telecoms
     * @param telecomsDTO dto
     * @param telecoms bo
     */
    private static void dto2BoLightImpl(TelecomsDTO telecomsDTO, Telecoms telecoms){
    
        // property of TelecomsDTO
        telecoms.setSain(telecomsDTO.getSain());
        telecoms.setSgin(telecomsDTO.getSgin());
        telecoms.setVersion(telecomsDTO.getVersion());
        telecoms.setScode_medium(telecomsDTO.getScode_medium());
        telecoms.setSstatut_medium(telecomsDTO.getSstatut_medium());
        telecoms.setSnumero(telecomsDTO.getSnumero());
        telecoms.setSdescriptif_complementaire(telecomsDTO.getSdescriptif_complementaire());
        telecoms.setSterminal(telecomsDTO.getSterminal());
        telecoms.setScode_region(telecomsDTO.getScode_region());
        telecoms.setSindicatif(telecomsDTO.getSindicatif());
        telecoms.setSsignature_modification(telecomsDTO.getSsignature_modification());
        telecoms.setSsite_modification(telecomsDTO.getSsite_modification());
        telecoms.setDdate_modification(telecomsDTO.getDdate_modification());
        telecoms.setSsignature_creation(telecomsDTO.getSsignature_creation());
        telecoms.setSsite_creation(telecomsDTO.getSsite_creation());
        telecoms.setDdate_creation(telecomsDTO.getDdate_creation());
        telecoms.setIcle_role(telecomsDTO.getIcle_role());
        telecoms.setIkey_temp(telecomsDTO.getIkey_temp());
        telecoms.setSnormalized_country(telecomsDTO.getSnormalized_country());
        telecoms.setSnormalized_numero(telecomsDTO.getSnormalized_numero());
        telecoms.setSforcage(telecomsDTO.getSforcage());
        telecoms.setSvalidation(telecomsDTO.getSvalidation());
        telecoms.setSnorm_nat_phone_number(telecomsDTO.getSnorm_nat_phone_number());
        telecoms.setSnorm_nat_phone_number_clean(telecomsDTO.getSnorm_nat_phone_number_clean());
        telecoms.setSnorm_inter_country_code(telecomsDTO.getSnorm_inter_country_code());
        telecoms.setSnorm_inter_phone_number(telecomsDTO.getSnorm_inter_phone_number());
        telecoms.setSnorm_terminal_type_detail(telecomsDTO.getSnorm_terminal_type_detail());
        telecoms.setIsnormalized(telecomsDTO.getIsnormalized());
        telecoms.setDdate_invalidation(telecomsDTO.getDdate_invalidation());
    }

    /**
     * bo -> dto for a telecoms
     * @param pTelecoms bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static TelecomsDTO bo2DtoLight(Telecoms pTelecoms) throws JrafDomainException {
        // instanciation du DTO
        TelecomsDTO telecomsDTO = new TelecomsDTO();
        bo2DtoLight(pTelecoms, telecomsDTO);
        // on retourne le dto
        return telecomsDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param telecoms bo
     * @param telecomsDTO dto
     */
    public static void bo2DtoLight(
        Telecoms telecoms,
        TelecomsDTO telecomsDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_uZqOIDOhEeCokvyNKVv2PQ) ENABLED START*/

        bo2DtoLightImpl(telecoms, telecomsDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param telecoms bo
     * @param telecomsDTO dto
     */
    private static void bo2DtoLightImpl(Telecoms telecoms,
        TelecomsDTO telecomsDTO){
    

        // simple properties
        telecomsDTO.setSain(telecoms.getSain());
        telecomsDTO.setSgin(telecoms.getSgin());
        telecomsDTO.setVersion(telecoms.getVersion());
        telecomsDTO.setScode_medium(telecoms.getScode_medium());
        telecomsDTO.setSstatut_medium(telecoms.getSstatut_medium());
        telecomsDTO.setSnumero(telecoms.getSnumero());
        telecomsDTO.setSdescriptif_complementaire(telecoms.getSdescriptif_complementaire());
        telecomsDTO.setSterminal(telecoms.getSterminal());
        telecomsDTO.setScode_region(telecoms.getScode_region());
        telecomsDTO.setSindicatif(telecoms.getSindicatif());
        telecomsDTO.setSsignature_modification(telecoms.getSsignature_modification());
        telecomsDTO.setSsite_modification(telecoms.getSsite_modification());
        telecomsDTO.setDdate_modification(telecoms.getDdate_modification());
        telecomsDTO.setSsignature_creation(telecoms.getSsignature_creation());
        telecomsDTO.setSsite_creation(telecoms.getSsite_creation());
        telecomsDTO.setDdate_creation(telecoms.getDdate_creation());
        telecomsDTO.setIcle_role(telecoms.getIcle_role());
        telecomsDTO.setIkey_temp(telecoms.getIkey_temp());
        telecomsDTO.setSnormalized_country(telecoms.getSnormalized_country());
        telecomsDTO.setSnormalized_numero(telecoms.getSnormalized_numero());
        telecomsDTO.setSforcage(telecoms.getSforcage());
        telecomsDTO.setSvalidation(telecoms.getSvalidation());
        telecomsDTO.setSnorm_nat_phone_number(telecoms.getSnorm_nat_phone_number());
        telecomsDTO.setSnorm_nat_phone_number_clean(telecoms.getSnorm_nat_phone_number_clean());
        telecomsDTO.setSnorm_inter_country_code(telecoms.getSnorm_inter_country_code());
        telecomsDTO.setSnorm_inter_phone_number(telecoms.getSnorm_inter_phone_number());
        telecomsDTO.setSnorm_terminal_type_detail(telecoms.getSnorm_terminal_type_detail());
        telecomsDTO.setIsnormalized(telecoms.getIsnormalized());
        telecomsDTO.setDdate_invalidation(telecoms.getDdate_invalidation());    
    }
    
    /*PROTECTED REGION ID(_uZqOIDOhEeCokvyNKVv2PQ u m - Tr) ENABLED START*/

    public static Telecoms dto2Bo(TelecomsDTO telecomsDTO) throws JrafDomainException {

    	if(telecomsDTO==null) {
    		return null;
    	}
    	
    	Telecoms telecoms = new Telecoms();
        dto2BoLight(telecomsDTO, telecoms);
        dto2BoLink(telecoms, telecomsDTO);

        return telecoms;
    }
    
    public static TelecomsDTO bo2Dto(Telecoms telecoms) throws JrafDomainException {

    	if(telecoms==null) {
    		return null;
    	}
    	
    	TelecomsDTO telecomsDTO = new TelecomsDTO();
        bo2DtoLight(telecoms, telecomsDTO);
        bo2DtoLink(telecomsDTO, telecoms);

        return telecomsDTO;
    }
    
    /**
     * @param listTelecom
     * @return
     * @throws JrafDomainException
     */
    public static Set<TelecomsDTO> bo2Dto(Set<Telecoms> listTelecom) throws JrafDomainException {
    	if(listTelecom != null) {
    		Set<TelecomsDTO> listTelecomsDTO = new HashSet<TelecomsDTO>();
    		for(Telecoms telecom : listTelecom) {
    			listTelecomsDTO.add(bo2DtoLight(telecom));
    		}
    		return listTelecomsDTO;
    	} else {
    		return null;
    	}
    }
    
    /**
     * @param listTelecom
     * @return
     * @throws JrafDomainException
     */
    public static Set<TelecomsDTO> bo2DtoValidTelecoms(Set<Telecoms> listTelecom) throws JrafDomainException {
    	if(listTelecom != null) {
    		Set<TelecomsDTO> listTelecomsDTO = new HashSet<TelecomsDTO>();
    		for(Telecoms telecom : listTelecom) {
    			if (telecom.getSterminal()!= null && !telecom.getSterminal().equalsIgnoreCase("X")){
    				if (telecom.getSstatut_medium().equalsIgnoreCase("V") || telecom.getSstatut_medium().equalsIgnoreCase("P")){
    					listTelecomsDTO.add(bo2DtoLight(telecom));
    				}	
    			}
    		}
    		return listTelecomsDTO;
    	} else {
    		return null;
    	}
    }
    
    /**
     * @param listTelecomDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<Telecoms> dto2Bo(Set<TelecomsDTO> listTelecomDTO) throws JrafDomainException {
    	if(listTelecomDTO != null) {
    		Set<Telecoms> listTelecoms = new HashSet<Telecoms>();
    		for(TelecomsDTO telecomdto : listTelecomDTO) {
    			listTelecoms.add(dto2BoLight(telecomdto));
    		}
    		return listTelecoms;
    	} else {
    		return null;
    	}
    }
    
    public static void dto2BoLink(Telecoms telecoms, TelecomsDTO telecomsDTO) {
    	
    	IndividuDTO individuDTO = telecomsDTO.getIndividudto();
    	
    	if(individuDTO!=null) {
			try {
				Individu individu = IndividuTransform.dto2BoLight(individuDTO);
				telecoms.setIndividu(individu);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
			
    	}
    	
    }
    
    public static Set<TelecomsDTO> bo2DtoLight(Set<Telecoms> listTelecom) throws JrafDomainException {
    	
    	if(listTelecom==null) {
    		return null;
    	}
    		
    	Set<TelecomsDTO> listTelecomsDTO = new HashSet<TelecomsDTO>();
    	
		for(Telecoms telecom : listTelecom) {
			listTelecomsDTO.add(bo2DtoLight(telecom));
		}
		
    	return listTelecomsDTO;
    	
    }
    
    public static void bo2DtoLink(TelecomsDTO telecomsDTO, Telecoms telecoms) {
    	
    	Individu individu = telecoms.getIndividu();
    	
    	if(individu!=null) {
			try {
				IndividuDTO individuDTO = IndividuTransform.bo2DtoLight(individu);
				telecomsDTO.setIndividudto(individuDTO);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
			
    	}
    	
    }
    
    /**
     * Transform a NormalizePhoneNumberDTO into a TelecomsDTO
     * 
     * @param telecomsDTO
     * @param normalizePhoneNumberDTO
     */
    public static void transform(TelecomsDTO telecomsDTO, NormalizePhoneNumberDTO normalizePhoneNumberDTO) {
		
		if(telecomsDTO==null || normalizePhoneNumberDTO==null) {
			return;
		}
		
		telecomsDTO.setSindicatif(normalizePhoneNumberDTO.getNormalizedInternationalCountryCode());
		telecomsDTO.setSnumero(normalizePhoneNumberDTO.getNormalizedNationalPhoneNumberClean());
		telecomsDTO.setSnorm_inter_country_code(normalizePhoneNumberDTO.getNormalizedInternationalCountryCode());
		telecomsDTO.setSnorm_inter_phone_number(normalizePhoneNumberDTO.getNormalizedInternationalPhoneNumber());
		telecomsDTO.setSnorm_nat_phone_number(normalizePhoneNumberDTO.getNormalizedNationalPhoneNumber());
		telecomsDTO.setSnorm_nat_phone_number_clean(normalizePhoneNumberDTO.getNormalizedNationalPhoneNumberClean());
		telecomsDTO.setSnorm_terminal_type_detail(normalizePhoneNumberDTO.getNormalizedTerminalTypeDetail());
		
	}
    
    /**
     * Transform a NormalizePhoneNumberDTO into a ProspectTelecomsDTO
     * 
     * @param telecomsDTO
     * @param normalizePhoneNumberDTO
     */

    public static void prospectTelecomTransform(TelecomsDTO telecomsDTO, NormalizePhoneNumberDTO normalizePhoneNumberDTO) {
		
		if(telecomsDTO==null || normalizePhoneNumberDTO==null) {
			return;
		}
		
		telecomsDTO.setCountryCode(normalizePhoneNumberDTO.getNormalizedInternationalCountryCode());
		telecomsDTO.setSindicatif(normalizePhoneNumberDTO.getNormalizedInternationalCountryCode());
		telecomsDTO.setSnumero(normalizePhoneNumberDTO.getNormalizedNationalPhoneNumberClean());
		telecomsDTO.setSnorm_inter_country_code(normalizePhoneNumberDTO.getNormalizedInternationalCountryCode());
		telecomsDTO.setSnorm_inter_phone_number(normalizePhoneNumberDTO.getNormalizedInternationalPhoneNumber());
		telecomsDTO.setSnorm_nat_phone_number(normalizePhoneNumberDTO.getNormalizedNationalPhoneNumber());
		telecomsDTO.setSnorm_nat_phone_number_clean(normalizePhoneNumberDTO.getNormalizedNationalPhoneNumberClean());
		telecomsDTO.setSnorm_terminal_type_detail(normalizePhoneNumberDTO.getNormalizedTerminalTypeDetail());


		}
		
   

    /*PROTECTED REGION END*/
}


