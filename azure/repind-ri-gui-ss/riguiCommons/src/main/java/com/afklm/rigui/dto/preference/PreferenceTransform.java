package com.afklm.rigui.dto.preference;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.preference.Preference;
import com.afklm.rigui.entity.preference.PreferenceData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : PreferenceTransform.java</p>
 * transformation bo <-> dto pour un(e) Preference
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class PreferenceTransform {

    /*PROTECTED REGION ID(_A1c1sHZ_EeauaOCU3jazIg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private PreferenceTransform() {
    }
    /**
     * dto -> bo for a Preference
     * @param preferenceDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Preference dto2BoLight(PreferenceDTO preferenceDTO) throws JrafDomainException {
        // instanciation du BO
        Preference preference = new Preference();
        dto2BoLight(preferenceDTO, preference);

        // on retourne le BO
        return preference;
    }

    /**
     * dto -> bo for a preference
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param preferenceDTO dto
     * @param preference bo
     * @throws JrafDomainException 
     */
    public static void dto2BoLight(PreferenceDTO preferenceDTO, Preference preference) throws JrafDomainException {
    
        /*PROTECTED REGION ID(dto2BoLight_A1c1sHZ_EeauaOCU3jazIg) ENABLED START*/
        
        dto2BoLightImpl(preferenceDTO,preference);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a preference
     * @param preferenceDTO dto
     * @param preference bo
     * @throws JrafDomainException 
     */
    private static void dto2BoLightImpl(PreferenceDTO preferenceDTO, Preference preference) throws JrafDomainException{
    
        // property of PreferenceDTO
        preference.setPreferenceId(preferenceDTO.getPreferenceId());
        preference.setGin(preferenceDTO.getGin());
        preference.setType(preferenceDTO.getType());
        preference.setLink(preferenceDTO.getLink());
        preference.setDateCreation(preferenceDTO.getDateCreation());
        preference.setSignatureCreation(preferenceDTO.getSignatureCreation());
        preference.setSiteCreation(preferenceDTO.getSiteCreation());
        preference.setDateModification(preferenceDTO.getDateModification());
        preference.setSignatureModification(preferenceDTO.getSignatureModification());
        preference.setSiteModification(preferenceDTO.getSiteModification());
        
        if (preferenceDTO.getPreferenceDataDTO() != null && !preferenceDTO.getPreferenceDataDTO().isEmpty()) {
        	Set<PreferenceData> datas = new HashSet<PreferenceData>();
        	for (PreferenceDataDTO prefDataDto : preferenceDTO.getPreferenceDataDTO()) {
        		PreferenceData data = PreferenceDataTransform.dto2BoLight(prefDataDto);
        		data.setPreference(preference);
        		datas.add(data);
        	}
        	preference.setPreferenceData(datas);
        }
    }

    /**
     * bo -> dto for a preference
     * @param pPreference bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static PreferenceDTO bo2DtoLight(Preference pPreference) throws JrafDomainException {
        // instanciation du DTO
        PreferenceDTO preferenceDTO = new PreferenceDTO();
        bo2DtoLight(pPreference, preferenceDTO);
        // on retourne le dto
        return preferenceDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param preference bo
     * @param preferenceDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLight(
        Preference preference,
        PreferenceDTO preferenceDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_A1c1sHZ_EeauaOCU3jazIg) ENABLED START*/

        bo2DtoLightImpl(preference, preferenceDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param preference bo
     * @param preferenceDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImpl(Preference preference,
        PreferenceDTO preferenceDTO) throws JrafDomainException{
    

        // simple properties
        preferenceDTO.setPreferenceId(preference.getPreferenceId());
        preferenceDTO.setGin(preference.getGin());
        preferenceDTO.setType(preference.getType());
        preferenceDTO.setLink(preference.getLink());
        preferenceDTO.setDateCreation(preference.getDateCreation());
        preferenceDTO.setSignatureCreation(preference.getSignatureCreation());
        preferenceDTO.setSiteCreation(preference.getSiteCreation());
        preferenceDTO.setDateModification(preference.getDateModification());
        preferenceDTO.setSignatureModification(preference.getSignatureModification());
        preferenceDTO.setSiteModification(preference.getSiteModification());
        
        if (preference.getPreferenceData() != null && !preference.getPreferenceData().isEmpty()) {
        	Set<PreferenceDataDTO> datas = new HashSet<PreferenceDataDTO>();
        	for (PreferenceData prefData : preference.getPreferenceData()) {
        		PreferenceDataDTO data = PreferenceDataTransform.bo2DtoLight(prefData);
        		data.setPreferenceDTO(preferenceDTO);
        		datas.add(data);
        	}
        	preferenceDTO.setPreferenceDataDTO(datas);
        }
    
    }
    
    /*PROTECTED REGION ID(_A1c1sHZ_EeauaOCU3jazIg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    public static List<PreferenceDTO> bo2Dto(Set<Preference> listPreference) throws JrafDomainException {
    	if (listPreference != null) {
    		List<PreferenceDTO> listPreferenceDTO = new ArrayList<PreferenceDTO>();
    		for (Preference preferenceBO : listPreference) {
    			PreferenceDTO preferenceDTO = bo2DtoLight(preferenceBO);
    			listPreferenceDTO.add(preferenceDTO);
    		}
    		return listPreferenceDTO;
    	} 
    	else {
    		return null;
    	}
    }
    
    public static List<Preference> dto2Bo(List<PreferenceDTO> listPreferenceDTO) throws JrafDomainException {
    	if (listPreferenceDTO != null) {
    		List<Preference> listPreference = new ArrayList<Preference>();
    		for (PreferenceDTO preferenceDTO : listPreferenceDTO) {
    			Preference preference = dto2BoLight(preferenceDTO);
    			listPreference.add(preference);
    		}
    		return listPreference;
    	} 
    	else {
    		return null;
    	}
    }
    /*PROTECTED REGION END*/
}

