package com.afklm.rigui.services.preference.internal;

import com.afklm.rigui.exception.InvalidParameterException;
import com.afklm.rigui.exception.MissingParameterException;
import com.afklm.rigui.exception.MultiplePreferencesException;
import com.afklm.rigui.exception.jraf.JrafDaoException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.dao.preference.PreferenceRepository;
import com.afklm.rigui.dao.reference.RefPreferenceKeyTypeRepository;
import com.afklm.rigui.dto.individu.IndividuDTO;
import com.afklm.rigui.dto.individu.SignatureDTO;
import com.afklm.rigui.dto.preference.PreferenceDTO;
import com.afklm.rigui.dto.preference.PreferenceDataDTO;
import com.afklm.rigui.dto.preference.PreferenceTransform;
import com.afklm.rigui.dto.reference.RefGenericCodeLabelsTypeDTO;
import com.afklm.rigui.dto.reference.RefPreferenceDataKeyDTO;
import com.afklm.rigui.dto.reference.RefPreferenceTypeDTO;
import com.afklm.rigui.entity.preference.Preference;
import com.afklm.rigui.entity.preference.PreferenceData;
import com.afklm.rigui.entity.reference.RefPreferenceKeyType;
import com.afklm.rigui.services.environnement.internal.VariablesDS;
import com.afklm.rigui.services.reference.internal.RefPreferenceDataKeyDS;
import com.afklm.rigui.services.reference.internal.RefPreferenceKeyDS;
import com.afklm.rigui.services.reference.internal.RefPreferenceTypeDS;
import com.afklm.rigui.services.reference.internal.ReferenceDS;
import com.afklm.rigui.util.SicStringUtils;
import com.afklm.rigui.repindutf8.util.SicUtf8StringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>Title : PreferencesDS.java</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Service
public class PreferenceDS {

    /** logger */
    private static final Log log = LogFactory.getLog(PreferenceDS.class);
    
    /** max number of Travel Doc = TDC */
    private static final String MAX_TDC = "MAX_TDC";
    private Integer maxTDC;
    /** max number of Travel Address = TAC */
    private static final String MAX_TAC = "MAX_TAC";
    private Integer maxTAC;
    /** max number of Travel Companion Content = TCC */
    private static final String MAX_TCC = "MAX_TCC";
    private Integer maxTCC;
    /** max number of Emergency Contact Content = ECC */
    private static final String MAX_ECC = "MAX_ECC";
    private Integer maxECC;
    /** max number of Personal Information = PIC */
    private static final String MAX_PIC = "MAX_PIC";
    private Integer maxPIC;
    /** max number of Travel Preference = TPC */
    private static final String MAX_TPC = "MAX_TPC";
    private Integer maxTPC;
    /** max number of Handicap = HDC */
    private static final String MAX_HDC = "MAX_HDC";
    private Integer maxHDC;
    /** max number of Apis Data Content = APC */
    private static final String MAX_APC = "MAX_APC";
    private Integer maxAPC;
    /** max number of Gift Preference Content = GPC */
    private static final String MAX_GPC = "MAX_GPC";
    private Integer maxGPC;
    
    private static final String BDM_LIST_SEPARATOR = ";";
    
    private static final List<String> ULTIMATE_PREFERENCES = new ArrayList<>(Arrays.asList("UCO", "UFD", "UFB", "ULS", "ULO", "UMU", "UOB", "UPM", "UST", "UTS", "UTF"));
    
    /**
     * Use to know which Keys is used for detecting duplicated input preferences
     */
	private static final List<String> ECC_Keys = new ArrayList<>(Arrays.asList("FIRSTNAME", "LASTNAME"));
	private static final List<String> TCC_Keys = new ArrayList<>(Arrays.asList("FIRSTNAME", "LASTNAME"));
	private static final List<String> TDC_Keys = new ArrayList<>(Arrays.asList("NUMBER", "TYPE"));
	private static final List<String> TAC_Keys = new ArrayList<>(Arrays.asList("TYPE"));
	
	private static final Map<String, List<String>> preferenceTypesToProcess;
	static { 
		preferenceTypesToProcess = new HashMap<String, List<String>>() {{ 
			put("ECC", ECC_Keys);
			put("TCC", TCC_Keys);
			put("TDC", TDC_Keys);
			put("TAC", TAC_Keys);
		}}; 
	}
    
    private List<RefPreferenceDataKeyDTO> dataKeyList;

    /** main dao */
    @Autowired
    private PreferenceRepository preferenceRepository;

    /*PROTECTED REGION ID(_3tUyAE9jEeevnICwxQHWbw u var) ENABLED START*/
    // add your custom variables here if necessary

    @Autowired
    @Qualifier("referenceDS")
    private ReferenceDS referenceDS;
    
    @Autowired
    private RefPreferenceKeyTypeRepository refPreferenceKeyTypeRepository;
	
    @Autowired
    @Qualifier("refPreferenceKeyDS")
    private RefPreferenceKeyDS refPreferenceKeyDS;
    
    @Autowired
    @Qualifier("refPreferenceTypeDS")
    private RefPreferenceTypeDS refPreferenceTypeDS;
    
    // @Autowired
    // private UtfHelper utfHelper;
    
    @Autowired
    @Qualifier("refPreferenceDataKeyDS")
    private RefPreferenceDataKeyDS refPreferenceDataKeyDS;

	@Autowired
	private VariablesDS variablesDS;

    private Long controlDuplicatePreferences(PreferenceDTO prefDtoFromWs, List<PreferenceDTO> existingPreferenceDTOList) throws JrafDomainException {
		Long duplicatePrefIdentifier = null;
    	if (existingPreferenceDTOList == null || existingPreferenceDTOList.isEmpty()) {
    		return duplicatePrefIdentifier;
    	}
    	
    	//Control duplicate Preference at Update (Between Database and Input Webservice)
    	for (PreferenceDTO existingPrefDTO : existingPreferenceDTOList) {
    		if (existingPrefDTO.getType() != null && prefDtoFromWs.getType() != null && existingPrefDTO.getType().equalsIgnoreCase(prefDtoFromWs.getType())) { // Same type
    			if (existingPrefDTO.getPreferenceDataDTO() != null && prefDtoFromWs.getPreferenceDataDTO() != null) {
	    			switch (existingPrefDTO.getType().toUpperCase()) {
	    			case "TDC":
	    				String typeTDCFromDB = "";
	    				String typeTDCFromWS = "";
	    				String numberValueFromDB = "";
	    				String numberValueFromWS = "";
	    				
	    				for (PreferenceDataDTO prefDataDto : existingPrefDTO.getPreferenceDataDTO()) {
	    					if (isKeyType(prefDataDto.getKey())) {
	    						if (prefDataDto.getValue() != null) {
	    							typeTDCFromDB = prefDataDto.getValue();
	    						}
	    					}
	    					if (isKeyNumber(prefDataDto.getKey())) {
	    						if (prefDataDto.getValue() != null) {
	    							numberValueFromDB = prefDataDto.getValue();
	    						}
	    					}
	    				}
	    				
	    				if (typeTDCFromDB != null && numberValueFromDB != null) {
	    					for (PreferenceDataDTO prefDataDtoFromWS : prefDtoFromWs.getPreferenceDataDTO()) {
	    						if (isKeyType(prefDataDtoFromWS.getKey())) {
	    							typeTDCFromWS = prefDataDtoFromWS.getValue();
	    						}
	    						if (isKeyNumber(prefDataDtoFromWS.getKey())) {
	    							numberValueFromWS = prefDataDtoFromWS.getValue();
	    						}
	    					}
	    				}
	    				
	    				if (typeTDCFromDB.equalsIgnoreCase(typeTDCFromWS) && numberValueFromDB.equalsIgnoreCase(numberValueFromWS)) {
	    					duplicatePrefIdentifier = existingPrefDTO.getPreferenceId();
	    				}
	    				// Exception for APIS data which is limited to one entry
	    				//
	    				if ("AP".equalsIgnoreCase(typeTDCFromDB) && "AP".equalsIgnoreCase(typeTDCFromWS)) {
	    					duplicatePrefIdentifier = existingPrefDTO.getPreferenceId();
	    				}
	    				break;
	    			
	    			case "TAC":
	    				String typeTACFromDB = "";
	    				String typeTACFromWS = "";
	    				
	    				for (PreferenceDataDTO prefDataDto : existingPrefDTO.getPreferenceDataDTO()) {
	    					if (isKeyType(prefDataDto.getKey())) {
	    						if (prefDataDto.getValue() != null) {
	    							typeTACFromDB = prefDataDto.getValue();
	    						}
	    					}
	    				}
	    				
	    				if (typeTACFromDB != null) {
	    					for (PreferenceDataDTO prefDataDtoFromWS : prefDtoFromWs.getPreferenceDataDTO()) {
	    						if (isKeyType(prefDataDtoFromWS.getKey())) {
	    							typeTACFromWS = prefDataDtoFromWS.getValue();
	    						}
	    					}
	    				}
	    				
	    				if ("R".equalsIgnoreCase(typeTACFromDB) && "R".equalsIgnoreCase(typeTACFromWS)) {
	    					duplicatePrefIdentifier = existingPrefDTO.getPreferenceId();
	    				}
	    				break;
	    			case "TPC":
	    				if (existingPrefDTO.getType() != null && "TPC".equalsIgnoreCase(existingPrefDTO.getType())) {
	    					duplicatePrefIdentifier = existingPrefDTO.getPreferenceId();
	    				}
	    				break;
	    			case "APC":
	    				String knownTravelerNumberAPCFromDB = "";
	    				String knownTravelerNumberAPCFromWS = "";
	    				
	    				for (PreferenceDataDTO prefDataDto : existingPrefDTO.getPreferenceDataDTO()) {
	    					if (isKeyKnownTravelerNumber(prefDataDto.getKey())) {
	    						if (prefDataDto.getValue() != null) {
	    							knownTravelerNumberAPCFromDB = prefDataDto.getValue();
	    						}
	    					}
	    				}
	    				
	    				if (knownTravelerNumberAPCFromDB != null) {
	    					for (PreferenceDataDTO prefDataDtoFromWS : prefDtoFromWs.getPreferenceDataDTO()) {
	    						if (isKeyKnownTravelerNumber(prefDataDtoFromWS.getKey())) {
	    							knownTravelerNumberAPCFromWS = prefDataDtoFromWS.getValue();
	    						}
	    					}
	    				}
	    				
	    				if (!"".equalsIgnoreCase(knownTravelerNumberAPCFromDB) && knownTravelerNumberAPCFromWS.equalsIgnoreCase(knownTravelerNumberAPCFromDB)) {
	    					duplicatePrefIdentifier = existingPrefDTO.getPreferenceId();
	    				}
	    				break;
	    			case "GPC":
	    				if (existingPrefDTO.getType() != null && "GPC".equalsIgnoreCase(existingPrefDTO.getType())) {
	    					duplicatePrefIdentifier = existingPrefDTO.getPreferenceId();
	    				}
	    			case "PIC":
	    				if (existingPrefDTO.getType() != null && "PIC".equalsIgnoreCase(existingPrefDTO.getType())) {
	    					duplicatePrefIdentifier = existingPrefDTO.getPreferenceId();
	    				}
	    				break;
	    			case "HDC":
	    				if (existingPrefDTO.getType() != null && "HDC".equalsIgnoreCase(existingPrefDTO.getType())) {
	    					duplicatePrefIdentifier = existingPrefDTO.getPreferenceId();
	    				}
	    				break;
	    			case "TCC":
	    				String firstnameTCCFromDB = "";
	    				String firstnameTCCFromWS = "";
	    				String lastnameTCCFromDB = "";
	    				String lastnameTCCFromWS = "";
	    				
	    				for (PreferenceDataDTO prefDataDto : existingPrefDTO.getPreferenceDataDTO()) {
	    					if (isKeyFirstName(prefDataDto.getKey())) {
	    						if (prefDataDto.getValue() != null) {
	    							firstnameTCCFromDB = prefDataDto.getValue();
	    						}
	    					}
	    					if (isKeyLastName(prefDataDto.getKey())) {
	    						if (prefDataDto.getValue() != null) {
	    							lastnameTCCFromDB = prefDataDto.getValue();
	    						}
	    					}
	    				}
	    				
	    				if (firstnameTCCFromDB != null && lastnameTCCFromDB != null) {
	    					for (PreferenceDataDTO prefDataDtoFromWS : prefDtoFromWs.getPreferenceDataDTO()) {
	    						if (isKeyFirstName(prefDataDtoFromWS.getKey())) {
	    							firstnameTCCFromWS = prefDataDtoFromWS.getValue();
	    						}
	    						if (isKeyLastName(prefDataDtoFromWS.getKey())) {
	    							lastnameTCCFromWS = prefDataDtoFromWS.getValue();
	    						}
	    					}
	    				}
	    				
	    				if (firstnameTCCFromDB.equals(firstnameTCCFromWS) && lastnameTCCFromDB.equals(lastnameTCCFromWS)) {
	    					duplicatePrefIdentifier = existingPrefDTO.getPreferenceId();
	    				}
	    				break;
	    			case "ECC":
	    				String firstnameECCFromDB = "";
	    				String firstnameECCFromWS = "";
	    				String lastnameECCFromDB = "";
	    				String lastnameECCFromWS = "";
	    				
	    				for (PreferenceDataDTO prefDataDto : existingPrefDTO.getPreferenceDataDTO()) {
	    					if (isKeyFirstName(prefDataDto.getKey())) {
	    						if (prefDataDto.getValue() != null) {
	    							firstnameECCFromDB = prefDataDto.getValue();
	    						}
	    					}
	    					if (isKeyLastName(prefDataDto.getKey())) {
	    						if (prefDataDto.getValue() != null) {
	    							lastnameECCFromDB = prefDataDto.getValue();
	    						}
	    					}
	    				}
	    				
	    				if (firstnameECCFromDB != null && lastnameECCFromDB != null) {
	    					for (PreferenceDataDTO prefDataDtoFromWS : prefDtoFromWs.getPreferenceDataDTO()) {
	    						if (isKeyFirstName(prefDataDtoFromWS.getKey())) {
	    							firstnameECCFromWS = prefDataDtoFromWS.getValue();
	    						}
	    						if (isKeyLastName(prefDataDtoFromWS.getKey())) {
	    							lastnameECCFromWS = prefDataDtoFromWS.getValue();
	    						}
	    					}
	    				}
	    				
	    				if (firstnameECCFromDB.equals(firstnameECCFromWS) && lastnameECCFromDB.equals(lastnameECCFromWS)) {
	    					duplicatePrefIdentifier = existingPrefDTO.getPreferenceId();
	    				}
	    				break;
	    			// REPIND-1578 : Ultimate migration from BDM to RI - No Business rules
	    			case "UCO":
	    			// case "UCU":
	    			case "UFB":
	    			case "UFD":
	    			case "ULO":
	    			case "ULS":
	    			case "UMU":
	    			case "UOB":
	    			case "UPM":
	    			case "UST":
	    			case "UTF":
	    			case "UTS":
	    				break;
	    			default:
	    				break;
	    			}
    			}
    		}
    	}
    	return duplicatePrefIdentifier;
	}

	private boolean isKeyNumber(String key) {
		return "number".equalsIgnoreCase(key);
	}

	private boolean isKeyType(String key) {
		return "type".equalsIgnoreCase(key);
	}

	private boolean isKeyKnownTravelerNumber(String key) {
		return "knownTravelerNumber".equalsIgnoreCase(key);
	}
	
	private boolean isKeyFirstName(String key) {
		return "firstName".equalsIgnoreCase(key);
	}
	
	private boolean isKeyLastName(String key) {
		return "lastName".equalsIgnoreCase(key);
	}

	// CREATE
	private void processCreation(PreferenceDTO prefDtoFromWs, IndividuDTO individuFromDB, SignatureDTO signFromWs) throws JrafDomainException {
		
		// Step 2 : update fields 
		updateFieldPreferenceForCreate(prefDtoFromWs, signFromWs);
		
		// Step 3 : clean values
		cleanValues(prefDtoFromWs);
		
		// Step 4 : normalize key, save and persist
		Preference preference = PreferenceTransform.dto2BoLight(prefDtoFromWs);
		normalizePreferenceDataKey(preference);
		preferenceRepository.saveAndFlush(preference);
		
	}

	private void cleanValues(PreferenceDTO prefDtoFromWs) {
		for (PreferenceDataDTO prefDataDto : prefDtoFromWs.getPreferenceDataDTO()) {
			if (prefDataDto.getKey() != null) {
				prefDataDto.setValue(SicStringUtils
						.replaceNonPrintableChars(SicUtf8StringUtils.ReplaceSpecialChar(prefDataDto.getValue())));
			}
		}
	}

	private void checkMaxPreferenceCount(String type, int prefTypeNumber, Map<String, Integer> sessionCount) throws NumberFormatException, JrafDomainException {
		if ("TDC".equalsIgnoreCase(type)) {
			int tdcCount = sessionCount.get("tdcSessionCount") + prefTypeNumber;
			maxTDC = maxTDC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_TDC).getEnvValue()) : maxTDC;
			if (tdcCount >= maxTDC) {
				throw new MultiplePreferencesException("Maximum number of preferences of type " + type + " reached for this individual");
			}
			else {
				// Increment this variable to count number of travel doc
				sessionCount.put("tdcSessionCount", sessionCount.get("tdcSessionCount") + 1);
			}
		}
		if ("TAC".equalsIgnoreCase(type)) {
			int tacCount = sessionCount.get("tacSessionCount") + prefTypeNumber;
			maxTAC = maxTAC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_TAC).getEnvValue()) : maxTAC;
			if (tacCount >= maxTAC) {
				throw new MultiplePreferencesException("Maximum number of preferences of type " + type + " reached for this individual");
			}
			else {
				sessionCount.put("tacSessionCount", sessionCount.get("tacSessionCount") + 1);
			}
		}
		if ("HDC".equalsIgnoreCase(type)) {
			int hdcCount = sessionCount.get("hdcSessionCount") + prefTypeNumber;
			maxHDC = maxHDC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_HDC).getEnvValue()) : maxHDC;
			if (hdcCount >= maxHDC) {
				throw new MultiplePreferencesException("Maximum number of preferences of type " + type + " reached for this individual");
			}
			else {
				sessionCount.put("hdcSessionCount", sessionCount.get("hdcSessionCount") + 1);
			}
		}
		if ("TPC".equalsIgnoreCase(type)) {
			int tpcCount = sessionCount.get("tpcSessionCount") + prefTypeNumber;
			maxTPC = maxTPC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_TPC).getEnvValue()) : maxTPC;
			if (tpcCount >= maxTPC) {
				throw new MultiplePreferencesException("Maximum number of preferences of type " + type + " reached for this individual");
			}
			else {
				sessionCount.put("tpcSessionCount", sessionCount.get("tpcSessionCount") + 1);
			}
		}
		if ("PIC".equalsIgnoreCase(type)) {
			int picCount = sessionCount.get("picSessionCount") + prefTypeNumber;
			maxPIC = maxPIC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_PIC).getEnvValue()) : maxPIC;
			if (picCount >= maxPIC) {
				throw new MultiplePreferencesException("Maximum number of preferences of type " + type + " reached for this individual");
			}
			else {
				sessionCount.put("picSessionCount", sessionCount.get("picSessionCount") + 1);
			}
		}
		if ("TCC".equalsIgnoreCase(type)) {
			int tccCount = sessionCount.get("tccSessionCount") + prefTypeNumber;
			maxTCC = maxTCC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_TCC).getEnvValue()) : maxTCC;
			if (tccCount >= maxTCC) {
				throw new MultiplePreferencesException("Maximum number of preferences of type " + type + " reached for this individual");
			}
			else {
				sessionCount.put("tccSessionCount", sessionCount.get("tccSessionCount") + 1);
			}
		}
		if ("ECC".equalsIgnoreCase(type)) {
			int eccCount = sessionCount.get("eccSessionCount") + prefTypeNumber;
			maxECC = maxECC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_ECC).getEnvValue()) : maxECC;
			if (eccCount >= maxECC) {
				throw new MultiplePreferencesException("Maximum number of preferences of type " + type + " reached for this individual");
			}
			else {
				sessionCount.put("eccSessionCount", sessionCount.get("eccSessionCount") + 1);
			}
		}
		if ("APC".equalsIgnoreCase(type)) {
			int eccCount = sessionCount.get("apcSessionCount") + prefTypeNumber;
			maxAPC = maxAPC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_APC).getEnvValue()) : maxAPC;
			if (eccCount >= maxAPC) {
				throw new MultiplePreferencesException("Maximum number of preferences of type " + type + " reached for this individual");
			}
			else {
				sessionCount.put("apcSessionCount", sessionCount.get("apcSessionCount") + 1);
			}
		}
		if ("GPC".equalsIgnoreCase(type)) {
			int gpcCount = sessionCount.get("gpcSessionCount") + prefTypeNumber;
			maxGPC = maxGPC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_GPC).getEnvValue()) : maxGPC;
			if (gpcCount >= maxGPC) {
				throw new MultiplePreferencesException("Maximum number of preferences of type " + type + " reached for this individual");
			}
			else {
				sessionCount.put("gpcSessionCount", sessionCount.get("gpcSessionCount") + 1);
			}
		}
		// TODO later: control for other type of preferences
	}
	
	// REPLACE
	private void processReplace(PreferenceDTO prefDtoFromWs, Long prefToReplaceId, SignatureDTO signFromWs) throws JrafDomainException {
		// Step 1 : find preference into DB
		Preference foundPreference = preferenceRepository.findByIdAndGin(prefToReplaceId, prefDtoFromWs.getGin());	
		
		// Step 2 : update fields and normalize key
		if (foundPreference != null) {
			cleanValues(prefDtoFromWs);
			updateFieldPreferenceForReplace(prefDtoFromWs, signFromWs, foundPreference);
			normalizePreferenceDataKey(foundPreference);
			// Step 3 : save and persist
			preferenceRepository.saveAndFlush(foundPreference);
			log.info("preference_id " + prefToReplaceId + " replaced");
		}
		else {
			throw new InvalidParameterException("Error while creating a preference which already exists with ID " + prefToReplaceId);
		}
	}
	
	// UPDATE
	private void processUpdate(PreferenceDTO prefDtoFromWs, SignatureDTO signFromWs) throws JrafDomainException {
    	
    	// Step 1 : find preference into DB
		Preference foundPreference = preferenceRepository.findByIdAndGin(prefDtoFromWs.getPreferenceId(), prefDtoFromWs.getGin());	
		
		// Step 2 : update fields and normalize key
		if (foundPreference != null) {
			cleanValues(prefDtoFromWs);
			updateFieldPreferenceForUpdate(prefDtoFromWs, signFromWs, foundPreference);
			
			if (foundPreference.getPreferenceData() != null && foundPreference.getPreferenceData().size() > 0) {
				normalizePreferenceDataKey(foundPreference);
				// Step 3 : save and persist
				preferenceRepository.saveAndFlush(foundPreference);
			} else {
				//REPIND-1788 - If the last SubPref has been deleted, we delete also the Preference
				preferenceRepository.delete(foundPreference);
				log.info("DELETE LAST PREF for " + foundPreference.getType() + " with " + foundPreference.getPreferenceId());
			}
		}
		else {
			throw new InvalidParameterException("Preference Id: " + prefDtoFromWs.getPreferenceId() + " not found for GIN " + prefDtoFromWs.getGin());
		}
	}

	// DELETE
	private void processDeletion(PreferenceDTO prefDtoFromWs, IndividuDTO individuFromDB, SignatureDTO signFromWs) throws JrafDaoException, InvalidParameterException {
		// Step 1 : find preference into DB
		Preference foundPreference = preferenceRepository.findByIdAndGin(prefDtoFromWs.getPreferenceId(), prefDtoFromWs.getGin());	
		
		// Step 2 : delete if pref found 
		if (foundPreference != null) {
			preferenceRepository.delete(foundPreference);
			log.info("DELETE PREF for " + prefDtoFromWs.getType() + " with " + prefDtoFromWs.getPreferenceId());
		}
		else {
			throw new InvalidParameterException("Preference Id: " + prefDtoFromWs.getPreferenceId() + " not found for GIN " + prefDtoFromWs.getGin());
		}
	}

	// Get the normalized key from database for the key set by user and replace it
	public void normalizePreferenceDataKey(Preference preference) throws JrafDomainException {
		if (preference == null) {
			throw new JrafDomainRollbackException("Preference is null");
		}
		
		if (dataKeyList == null) {
			dataKeyList = refPreferenceDataKeyDS.findAll();
		}
		
		if (preference.getPreferenceData() != null) {
			for (PreferenceData prefData : preference.getPreferenceData()) {
				for (RefPreferenceDataKeyDTO dataKey : dataKeyList) {
					if (prefData.getKey() != null && dataKey.getCode() != null && dataKey.getCode().equalsIgnoreCase(prefData.getKey())) {
						prefData.setKey(dataKey.getNormalizedKey());
						break;
					}
				}
			}
		}
	}

	private String CleanSplitCode(String list) {
		String returnList = list;
		
		if (returnList != null) {		
			
			returnList = returnList.trim();					// Let's Trim values
			returnList = returnList.replace(" ", "");		// We catch only CODE so no Space
			
			while (returnList.contains(";;")) {
				returnList = returnList.replace(";;", ";");
			}
			
			if (returnList.startsWith(";") ) {				// Change ";XX..." in "XX..."
				returnList = returnList.substring(1);
			}

			if (returnList.endsWith(";") ) {				// Change "...XX;" in "...XX"
				returnList = returnList.substring(0, returnList.length() - 1);
			}
		}
		
		return returnList;
	}

	private void updateFieldPreferenceForCreate(PreferenceDTO prefDto, com.afklm.rigui.dto.individu.SignatureDTO signFromWs) {
		if (signFromWs.getDate() != null) {
			prefDto.setDateCreation(signFromWs.getDate());
		}
		else {
			prefDto.setDateCreation(new Date());
		}
		prefDto.setSignatureCreation(signFromWs.getSignature());
		prefDto.setSiteCreation(signFromWs.getSite());
		
		// REPIND-1734 : Add Date on PREFERENCE DATA
		for (PreferenceDataDTO pdd : prefDto.getPreferenceDataDTO()) {
			if (pdd != null) {
				if (signFromWs.getDate() != null) {
					pdd.setDateCreation(signFromWs.getDate());
				} else {
					pdd.setDateCreation(new Date());
				}
				pdd.setSignatureCreation(signFromWs.getSignature());
				pdd.setSiteCreation(signFromWs.getSite());
			}
		}
	}
	
	private void updateFieldPreferenceForReplace(PreferenceDTO prefDto, com.afklm.rigui.dto.individu.SignatureDTO signFromWs, Preference preference) {
		// Preference 
		if (prefDto.getType() != null) {
			preference.setType(prefDto.getType());
		}
		if (prefDto.getLink() != null) {
			preference.setLink(prefDto.getLink());
		}
		
		// Preference data

		// Creation signature
		Date dateCreation = null;
		String signatureCreation = null;
		String siteCreation = null;

		Set<PreferenceData> preferenceDataList = preference.getPreferenceData();
		if (preferenceDataList != null && !preferenceDataList.isEmpty()) {

			// Collect creation signatures
			dateCreation = preferenceDataList.iterator().next().getDateCreation();
			signatureCreation = preferenceDataList.iterator().next().getSignatureCreation();
			siteCreation = preferenceDataList.iterator().next().getSiteCreation();

			preferenceDataList.clear();
		}
		if (prefDto.getPreferenceDataDTO() != null) {
			for (PreferenceDataDTO prefDataDto : prefDto.getPreferenceDataDTO()) {
				PreferenceData data = new PreferenceData();
				data.setKey(prefDataDto.getKey());
				data.setValue(prefDataDto.getValue());

				// Assign creation signatures (to resolve no creation signatures bugs : REPIND-2935)
				data.setDateCreation(dateCreation);
				data.setSignatureCreation(signatureCreation);
				data.setSiteCreation(siteCreation);
				
				// REPIND-1734 : Add Date on PREFERENCE DATA
				if (signFromWs.getDate() != null) {
					data.setDateModification(signFromWs.getDate());
				} else {
					data.setDateModification(new Date());
				}
				data.setSignatureModification(signFromWs.getSignature());
				data.setSiteModification(signFromWs.getSite());

				data.setPreference(preference);
				// REPIND-1546 : NPE on SONAR
				if (preferenceDataList != null) {
					preferenceDataList.add(data);
				}
			}
		}
		
		// Signature
		if (signFromWs.getDate() != null) {
			preference.setDateModification(signFromWs.getDate());
		}
		else {
			preference.setDateModification(new Date());
		}
		preference.setSignatureModification(signFromWs.getSignature());
		preference.setSiteModification(signFromWs.getSite());

	}
	
	private void updateFieldPreferenceForUpdate(PreferenceDTO prefDto, com.afklm.rigui.dto.individu.SignatureDTO signFromWs, Preference preference) {
		// Preference 
		if (prefDto.getType() != null) {
			preference.setType(prefDto.getType());
		}
		if (prefDto.getLink() != null) {
			preference.setLink(prefDto.getLink());
		}
		
		//  Preference data
		// Preference data from DB
		Set<PreferenceData> preferenceDataList = preference.getPreferenceData();
		// Preference data from WS
		Set<PreferenceDataDTO> preferenceDataDTOList = prefDto.getPreferenceDataDTO();
		
		if (preferenceDataList != null && !preferenceDataList.isEmpty() && preferenceDataDTOList != null) {
			Collection<PreferenceData> prefDataToRemove = new ArrayList<PreferenceData>();
			Collection<PreferenceDataDTO> prefDataDtoToRemove = new ArrayList<PreferenceDataDTO>();
			
			for (PreferenceData prefDataFromDB: preferenceDataList) {
				for (PreferenceDataDTO prefDataDtoFromWS : preferenceDataDTOList){
					if (prefDataDtoFromWS.getKey().equalsIgnoreCase(prefDataFromDB.getKey())) {
						// We keep the new value of the key
						if (prefDataDtoFromWS.getValue() != null) {
							prefDataFromDB.setValue(prefDataDtoFromWS.getValue());
							prefDataDtoToRemove.add(prefDataDtoFromWS);
							
							// REPIND-1734 : Add Date on PREFERENCE DATA
							if (signFromWs.getDate() != null) {
								prefDataFromDB.setDateModification(signFromWs.getDate());
							} else {
								prefDataFromDB.setDateModification(new Date());
							}
							prefDataFromDB.setSignatureModification(signFromWs.getSignature());
							prefDataFromDB.setSiteModification(signFromWs.getSite());
						}
						else {  // value is null, we delete the key
							log.info("DELETE PREF DATA for " + prefDataFromDB.getKey() + " with " + prefDataFromDB.getPreference().getPreferenceId() + " data " +  prefDataFromDB.getPreferenceDataId());
							prefDataToRemove.add(prefDataFromDB);
							prefDataDtoToRemove.add(prefDataDtoFromWS);
						}
					}
				}
			}
			
			preferenceDataList.removeAll(prefDataToRemove);
			preferenceDataDTOList.removeAll(prefDataDtoToRemove);
			
			// We complete the existing list in DB with new keys
			for (PreferenceDataDTO prefDataDto : prefDto.getPreferenceDataDTO()) {
				PreferenceData data = new PreferenceData();
				data.setKey(prefDataDto.getKey());
				data.setValue(prefDataDto.getValue());
				data.setPreference(preference);
				preferenceDataList.add(data);
			}
		}
		
		// Signature
		if (signFromWs.getDate() != null) {
			preference.setDateModification(signFromWs.getDate());
		}
		else {
			preference.setDateModification(new Date());
		}
		preference.setSignatureModification(signFromWs.getSignature());
		preference.setSiteModification(signFromWs.getSite());

	}

    
    public int getPreferencesNumberByGin(String sgin) {
        return preferenceRepository.getPreferencesNumberByGin(sgin).intValue();
    }
	
	class Bundle{
		private int count;
		
		public Bundle(int count) {
			super();
			this.count = count;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
	}
	
	
	@Deprecated
	@Transactional

	public PreferenceDTO get(PreferenceDTO dto) throws JrafDomainException {
		return get(dto.getPreferenceId());
	}

	
	@Transactional
	public PreferenceDTO get(Long id) throws JrafDomainException {
		Optional<Preference> pref = preferenceRepository.findById(id);
		if (!pref.isPresent()) {
			return null;
		}
		return PreferenceTransform.bo2DtoLight(pref.get());
	}
	
	
	@Transactional
	public void create(PreferenceDTO prefToCreateDTO) throws JrafDomainException {
		Preference email = PreferenceTransform.dto2BoLight(prefToCreateDTO);
		preferenceRepository.saveAndFlush(email);
		PreferenceTransform.bo2DtoLight(email, prefToCreateDTO);		
	}

	
	@Transactional

	public void remove(PreferenceDTO prefDTO) throws JrafDomainException {
		preferenceRepository.deleteById(prefDTO.getPreferenceId());		
	}

	
	@Transactional
	public List<PreferenceDTO> findByExample(PreferenceDTO preferenceDTO) throws JrafDomainException {
		Preference email = PreferenceTransform.dto2BoLight(preferenceDTO);
		List<PreferenceDTO> result = new ArrayList<>();
		for (Preference found : preferenceRepository.findAll(Example.of(email))) {
			result.add(PreferenceTransform.bo2DtoLight(found));
			}
		return result;
	}
	
	//REPIND-1747: Downgrade Ultimate
	public void deleteUltimatePreferences(String gin) {
		List<Preference> listPreferences = preferenceRepository.findByGin(gin);
		
		List<Preference> ultimatePreferences = listPreferences.stream().filter(it -> ULTIMATE_PREFERENCES.contains(it.getType())).collect(Collectors.toList());
		
		if (ultimatePreferences != null) {
			for (Preference prefToDelete : ultimatePreferences) {
				preferenceRepository.delete(prefToDelete);
			}
		}
	}
}
