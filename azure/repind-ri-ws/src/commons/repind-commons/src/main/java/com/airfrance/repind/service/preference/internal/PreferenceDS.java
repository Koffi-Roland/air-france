package com.airfrance.repind.service.preference.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.MultiplePreferencesException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.preference.PreferenceRepository;
import com.airfrance.repind.dao.reference.RefPreferenceKeyTypeRepository;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.dto.preference.PreferenceTransform;
import com.airfrance.repind.dto.reference.RefGenericCodeLabelsTypeDTO;
import com.airfrance.repind.dto.reference.RefPreferenceDataKeyDTO;
import com.airfrance.repind.dto.reference.RefPreferenceTypeDTO;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.entity.preference.PreferenceData;
import com.airfrance.repind.entity.reference.RefPreferenceKeyType;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.service.reference.internal.RefPreferenceDataKeyDS;
import com.airfrance.repind.service.reference.internal.RefPreferenceKeyDS;
import com.airfrance.repind.service.reference.internal.RefPreferenceTypeDS;
import com.airfrance.repind.service.reference.internal.ReferenceDS;
import com.airfrance.repind.util.SicStringUtils;
import com.airfrance.repindutf8.util.SicUtf8StringUtils;
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

    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;


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
    
    public PreferenceRepository getPreferenceRepository() {
		return preferenceRepository;
	}

	public void setPreferenceRepository(PreferenceRepository preferenceRepository) {
		this.preferenceRepository = preferenceRepository;
	}

	/**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


/*PROTECTED REGION ID(_3tUyAE9jEeevnICwxQHWbw u m) ENABLED START*/
    // add your custom methods here if necessary
   /** 
     * getValueByGinAndKey
     * @param gin in String
     * @param key in String
     * @return The getValueByGinAndKey as <code>String</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public String getValueByGinAndKey(String gin, String key) throws JrafDomainException {

        throw new UnsupportedOperationException();

    }
    


    /*PROTECTED REGION ID(_s-Xg8Hp4EeahwOaeAgHXkw u m) ENABLED START*/
    // add your custom methods here if necessary
    
    /** 
     * findBy
     * @param prefDto in PreferenceDTO
     * @return The findBy as <code>List</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public List<Preference> findByAll(PreferenceDTO prefDto) throws JrafDomainException {
        /*PROTECTED REGION ID(_CZCycE7lEeCFM4AYw8gmuw pref) ENABLED START*/
        String whereClause = "";
        String test = "=";
        String upper="upper";
        
        StringBuffer buffer = new StringBuffer("select p from Preference p where ");
        
		List<Preference> result = new ArrayList<Preference>();
		
		buffer.append("(p.gin) ").append(test).append(upper).append(" (:gin)");
		buffer.append(" and ").append("(p.type) ").append(test).append(upper).append(" (:type)");

		whereClause = buffer.toString();
		Query query = getEntityManager().createQuery(whereClause);
		query.setParameter("gin", prefDto.getGin());
		query.setParameter("type", prefDto.getType().toUpperCase());
		result = query.getResultList();
		log.info("Preferences trouvées :"+result.size());
		return result;
        /*PROTECTED REGION END*/
    }
    

    @Transactional(readOnly=true)
    public PreferenceDTO findByPreferenceId(Long preferenceId) throws JrafDomainException {
    	
    	StringBuffer buffer = new StringBuffer("select p from Preference p where ");
    	buffer.append("p.preferenceId = :prefId ");
    	
    	Query query = getEntityManager().createQuery(buffer.toString());
    	query.setParameter("prefId", preferenceId);
    	
    	Preference result = (Preference) query.getSingleResult();
    	log.info("Preferences trouvée :"+result.toString());
		return PreferenceTransform.bo2DtoLight(result);
		
    }
    
    public List<Preference> findByGinAndTypeAndNumber(String gin, String type, String number) {
    	return preferenceRepository.findByGinAndTypeAndNumber(gin, type, number);
    }
    
    public List<Preference> findByGinAndType(String gin, String type) {
    	return preferenceRepository.findByGinAndType(gin, type);
    }

    // REPIND-768 : Create preferences for prospect
    @Transactional(readOnly=true)
    public void checkProspectPreferencesToCreate(IndividuDTO indDTO) throws JrafDomainException {

		// Verifier la validite des preferences saisies (doublons cles, cles existantes, ...)
		checkPreferences(indDTO);
        
    }


    // Delete all preference data of preference
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void deleteAllPreferenceData(String preferenceId) {
    	StringBuffer buffer = new StringBuffer("delete from PREFERENCE_DATA pd where ");
    	buffer.append("pd.PREFERENCE_ID = :prefId ");
    	
    	Query query = getEntityManager().createNativeQuery(buffer.toString());
    	query.setParameter("prefId", preferenceId);
    	
    	query.executeUpdate();
    	log.info("DELETE PREF DATA with " + preferenceId);
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateIndividualPreferences(IndividuDTO individuFromWS, IndividuDTO individuFromDB, com.airfrance.repind.dto.individu.SignatureDTO signFromWs) throws JrafDomainException {
		if (individuFromWS.getPreferenceDTO() == null) {
			return;
		}
		
		// Static variable to count the number of travelDoc inserted 
   		// each variable is incremented every creation of associated preference in the same service call	
   		Map<String, Integer> sessionCount = new HashMap<String, Integer>();
   		sessionCount.put("tdcSessionCount", 0);
   		sessionCount.put("tacSessionCount", 0);
   		sessionCount.put("hdcSessionCount", 0);
   		sessionCount.put("tpcSessionCount", 0);
   		sessionCount.put("picSessionCount", 0);
   		sessionCount.put("tccSessionCount", 0);
   		sessionCount.put("eccSessionCount", 0);
   		sessionCount.put("apcSessionCount", 0);
   		sessionCount.put("gpcSessionCount", 0);
   		
		dataKeyList = refPreferenceDataKeyDS.findAll();
		
		// Verifier la validite des preferences saisies (doublons cles, cles existantes, ...)
		checkPreferences(individuFromWS);

/*		FIRST DETECT IS IT IS UTF-8 AFTER WE STORE WHAT HAVE BEEN DETECT3ED AS UTF-8
		// Get all preferenceData with an utf value
		// List<PreferenceDataDTO> preferencesDataUTF = utfHelper.utfValues(individuFromWS.getPreferenceDTO());
		
 		// Stock in UTF-8 database
		try {
			utfHelper.process(individuFromWS.getSgin(), preferencesDataUTF, signFromWs);
		} catch (UtfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
*/		
		List<String> riPreferenceTypeList = getRiPreferenceTypeList();

		for (PreferenceDTO prefDtoFromWs : individuFromWS.getPreferenceDTO()) {
			Long prefToReplace = null;
			prefDtoFromWs.setGin(individuFromWS.getSgin());
			
			// ===  REPIND-729 : Only handle preference of type TDC / TAC / APC --> Other type call BDM  ===
   			// ===  REPIND-555 : Add TPC for prospect only
			if (prefDtoFromWs.getType() == null) { 
   				continue;
   			}
			
			// REPIND-1225: TPC are now allowed for Individual (not on Prospect)
			/*if (("TPC".equalsIgnoreCase(prefDtoFromWs.getType()) && !"W".equals(individuFromDB.getType())) || !isRiScope(prefDtoFromWs.getType())) {
				continue;
			}*/
			// Test if the preference type is in the RI scope
			if (!containsType(prefDtoFromWs.getType(), riPreferenceTypeList)) {
				continue;
			}

			// ==============================================================================================
			
			// Verifier si le couple type/numero existe deja
			prefToReplace = controlDuplicatePreferences(prefDtoFromWs, individuFromDB.getPreferenceDTO());
			
			// Case 1 : Creation = preferenceID is null
			if (prefDtoFromWs.getPreferenceId() == null && prefToReplace == null) {
   				//control if number of preference for this type is reached
   				int prefTypeNumber = countPreferenceTypeNumber(prefDtoFromWs.getType(), individuFromDB);
   				checkMaxPreferenceCount(prefDtoFromWs.getType(), prefTypeNumber, sessionCount);

				processCreation(prefDtoFromWs, individuFromDB, signFromWs);
				continue;
			}
			else {
				// Case 4 : Replace = preferenceId is null but preference already exists
				if (prefDtoFromWs.getPreferenceId() == null && prefToReplace != null) {
					processReplace(prefDtoFromWs, prefToReplace, signFromWs);
					continue;
				}
			}
			
			// Case 2 : Delete = preferenceID not null and preferenceDatas null
			if (prefDtoFromWs.getPreferenceId() != null && (prefDtoFromWs.getPreferenceDataDTO() == null || prefDtoFromWs.getPreferenceDataDTO().isEmpty())) {
				processDeletion(prefDtoFromWs, individuFromDB, signFromWs);
			}
			// Case 3 : Update = preferenceID not null and preferenceDatas not null 
			else if ((prefDtoFromWs.getPreferenceId() != null && prefDtoFromWs.getPreferenceDataDTO() != null && !prefDtoFromWs.getPreferenceDataDTO().isEmpty())) {
				processUpdate(prefDtoFromWs, signFromWs);
			}
		}
    }
    
    // Old process is in replace mode when creating travel doc
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
   	public void updateIndividualPreferencesOldProcess(IndividuDTO individuFromWS, IndividuDTO individuFromDB, com.airfrance.repind.dto.individu.SignatureDTO signFromWs) throws JrafDomainException {
   		if (individuFromWS.getPreferenceDTO() == null) {
   			return;
   		}
   		
   		// Static variable to count the number of travelDoc inserted 
   		// each variable is incremented every creation of associated preference in the same service call   		
   		Map<String, Integer> sessionCount = new HashMap<String, Integer>();
   		sessionCount.put("tdcSessionCount", 0);
   		sessionCount.put("tacSessionCount", 0);
   		sessionCount.put("hdcSessionCount", 0);
   		sessionCount.put("tpcSessionCount", 0);
   		sessionCount.put("picSessionCount", 0);
   		sessionCount.put("tccSessionCount", 0);
   		sessionCount.put("eccSessionCount", 0);
   		sessionCount.put("apcSessionCount", 0);
   		sessionCount.put("gpcSessionCount", 0);

   		dataKeyList = refPreferenceDataKeyDS.findAll();
   		
   		// Verifier la validite des preferences saisies (doublons cles, cles existantes, ...)
   		checkPreferences(individuFromWS);
   		
   		List<String> riPreferenceTypeList = getRiPreferenceTypeList();
   		
   		//REPIND-1430: We remove only Preferences provided in input. Others will remains unchanged.
   		//Equivalent process to DeleteMarketingData
   		if (individuFromWS.getPreferenceDTO() != null && individuFromWS.getPreferenceDTO().size() == 0 && individuFromDB.getPreferenceDTO() != null) {
   			for (PreferenceDTO prefFromDB: individuFromDB.getPreferenceDTO()) {

   				// Test if the preference type is in the RI scope
				if (containsType(prefFromDB.getType(), riPreferenceTypeList)) {
					preferenceRepository.deleteById(prefFromDB.getPreferenceId());
				}
			}
   		}
   		
   		for (PreferenceDTO prefDtoFromWs : individuFromWS.getPreferenceDTO()) {
   			Long prefToReplace = null;
   			prefDtoFromWs.setGin(individuFromWS.getSgin());
   			
   			// ===  REPIND-729 : Only handle preference of type TDC / TAC / APC --> Other type call BDM  ===
   			// ===  REPIND-555 : Add TPC for prospect only
			if (prefDtoFromWs.getType() == null) { 
   				continue;
   			}
			
			// REPIND-1225: TPC are now allowed for Individual (not on Prospect)
			/*if (("TPC".equalsIgnoreCase(prefDtoFromWs.getType()) && !"W".equals(individuFromDB.getType())) || !isRiScope(prefDtoFromWs.getType())) {
				continue;
			}*/
			// Test if the preference type is in the RI scope
			if (!containsType(prefDtoFromWs.getType(), riPreferenceTypeList)) {
				continue;
			}
			
			// ==============================================================================================

   			// Remove all individual documents in DB and replace with input (TDC, APC)
			//REPIND-1430: We remove only Preferences provided in input. Others will remains unchanged.
   			List<PreferenceDTO> prefListToRemove = new ArrayList<PreferenceDTO>();

			if (individuFromDB.getPreferenceDTO() != null) {
				for (PreferenceDTO prefFromDB : individuFromDB.getPreferenceDTO()) {
					// Test if the preference type is in the RI scope
					if (containsType(prefFromDB.getType(), riPreferenceTypeList)
							&& prefFromDB.getType().equalsIgnoreCase(prefDtoFromWs.getType())) {

						prefListToRemove.add(prefFromDB);
						preferenceRepository.deleteById(prefFromDB.getPreferenceId());
					}
				}
			}
   			individuFromDB.getPreferenceDTO().removeAll(prefListToRemove);
   			
   			prefToReplace = controlDuplicatePreferences(prefDtoFromWs, individuFromDB.getPreferenceDTO());
   			
   			// Case 1 : Creation = preferenceID is null
   			if (prefDtoFromWs.getPreferenceId() == null && prefToReplace == null) {
   				//control if number of preference for this type is reached
   				int prefTypeNumber = countPreferenceTypeNumber(prefDtoFromWs.getType(), individuFromDB);
   				checkMaxPreferenceCount(prefDtoFromWs.getType(), prefTypeNumber, sessionCount);
   				
   				processCreation(prefDtoFromWs, individuFromDB, signFromWs);
   				continue;
   			}
   			else {
   				// Case 4 : Replace = preferenceId is null but preference already exists
   				if (prefDtoFromWs.getPreferenceId() == null && prefToReplace != null) {
   					processReplace(prefDtoFromWs, prefToReplace, signFromWs);
   					continue;
   				}
   			}
   			
   			// Case 2 : Update = preferenceID not null and preferenceDatas not null
   			if ((prefDtoFromWs.getPreferenceId() != null && prefDtoFromWs.getPreferenceDataDTO() != null && !prefDtoFromWs.getPreferenceDataDTO().isEmpty())) {
   				processUpdate(prefDtoFromWs, signFromWs);
   				continue;
   			}
   			else {
	   			// Case 3 : Delete = preferenceID not null and preferenceDatas null
	   			if (prefDtoFromWs.getPreferenceId() != null && (prefDtoFromWs.getPreferenceDataDTO() == null || prefDtoFromWs.getPreferenceDataDTO().isEmpty())) {
	   				processDeletion(prefDtoFromWs, individuFromDB, signFromWs);
	   				continue;
	   			}
   			}
   		}
    }

	// TODO ajouter arg String individualType pour detreminer si c'est un prospect
	private List<String> getRiPreferenceTypeList() {
		List<RefPreferenceTypeDTO> riPreferenceList = refPreferenceTypeDS.getAll();

		List<String> riPreferenceTypeList = riPreferenceList.stream().map(RefPreferenceTypeDTO::getCode)
				.collect(Collectors.toList());
		return riPreferenceTypeList;
	}

	private boolean containsType(String type, List<String> typeList) {
		return typeList.contains(type.toUpperCase());
	}

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

	private int countPreferenceTypeNumber(String type, IndividuDTO individuFromDB) {
		int count = 0;
		if (individuFromDB.getPreferenceDTO() != null && !individuFromDB.getPreferenceDTO().isEmpty()) {
			for (PreferenceDTO prefDtoFromDB : individuFromDB.getPreferenceDTO()) {
				if (type.equalsIgnoreCase(prefDtoFromDB.getType())) {
					count ++;
				}
			}
		}
		return count;
	}

	private void checkPreferences(IndividuDTO individuFromWS) throws JrafDomainException {
		if (individuFromWS.getPreferenceDTO() == null) {
			return;
		}
		
		for (PreferenceDTO prefDto : individuFromWS.getPreferenceDTO()) {
			// Check validity of preference type
			if (prefDto.getType() == null) {
				throw new MissingParameterException("Missing type of preference");
			}
			if (!prefTypeExistsInDB(prefDto.getType())) {
				throw new InvalidParameterException("Unknown preference type: " + prefDto.getType());
			}
			
			// Check validity of preference data key for the type and check compliance of each value
			if (prefDto.getPreferenceDataDTO() != null && !prefDto.getPreferenceDataDTO().isEmpty()) {
				// Get information of each key
				List<RefPreferenceKeyType> refPreferenceDataListForType = refPreferenceKeyTypeRepository.findByType(prefDto.getType().toUpperCase());
				List<String> mandatoryKeyListForType = refPreferenceKeyTypeRepository.getMandatoryKeyForType(prefDto.getType().toUpperCase());
				
				// Get all the key used by the calling service 
				List<String> keyListForType = new ArrayList<String>();
				for (RefPreferenceKeyType refPreferenceData : refPreferenceDataListForType) {
					keyListForType.add(refPreferenceData.getKey());
				}
				
				List<String> normalizedMandatoryKeyList = new ArrayList<String>();
				
				if (dataKeyList == null) {
					dataKeyList = refPreferenceDataKeyDS.findAll();
				}
				
				// Normalization of keys used 
				for (String key : mandatoryKeyListForType) {
					for (RefPreferenceDataKeyDTO dataKey : dataKeyList) {
						if (key.equalsIgnoreCase(dataKey.getCode())) {
							key = dataKey.getNormalizedKey();
							normalizedMandatoryKeyList.add(key);
							break;
						}
					}
				}
				
				// List of key must not be null if type exists
				if (keyListForType == null || keyListForType.isEmpty()) {
					throw new InvalidParameterException("No key found for the preference type " + prefDto.getType());
				}
				
				List<String> usedKey = new ArrayList<String>();
				
				// Set to check duplicate keys
				Set<String> setKey = new HashSet<String>();
				for (PreferenceDataDTO prefDataDto : prefDto.getPreferenceDataDTO()) {
					if (!keyListForType.contains(StringUtils.upperCase(prefDataDto.getKey()))) {
						throw new InvalidParameterException("Unknown preference data key: " + prefDataDto.getKey());
					}
					usedKey.add(StringUtils.upperCase(prefDataDto.getKey()));
					// Here we check duplicate values
					if (!setKey.add(StringUtils.upperCase(prefDataDto.getKey()))) {
						throw new InvalidParameterException("Key: " + prefDataDto.getKey() + " already used");
					}
					// Control value compliance with SSD
					controlValue(refPreferenceDataListForType, prefDataDto.getKey(), prefDataDto.getValue());
				}
				
				// Prepare error message if missing mandatory keys
				for (String normalizedMandatoryKey : normalizedMandatoryKeyList) {
					if (!usedKey.contains(normalizedMandatoryKey.toUpperCase())) {
						throw new MissingParameterException(normalizedMandatoryKey + " for type " + prefDto.getType() + " is mandatory");
					}
				}
			}
		}
		
		//Control duplicate Preference at Creation (Input Webservice only)    	
    	for (String typeToProcess : preferenceTypesToProcess.keySet()) {
    		List<List<PreferenceDataDTO>> listOfKeys = new ArrayList<List<PreferenceDataDTO>>();
    		
    		for (PreferenceDTO pref : individuFromWS.getPreferenceDTO()) {
    			List<PreferenceDataDTO> listOfPreferencesData = new ArrayList<PreferenceDataDTO>();
    			
    			if (pref.getType().equalsIgnoreCase(typeToProcess)) {
    				for (PreferenceDataDTO prefData : pref.getPreferenceDataDTO()) {
    					if (preferenceTypesToProcess.get(typeToProcess).contains(StringUtils.upperCase(prefData.getKey()))) {
    						listOfPreferencesData.add(prefData);
    					}
    				}
    			}
    			if (!listOfPreferencesData.isEmpty()) listOfKeys.add(listOfPreferencesData);
    		}
    		
    		for (List<PreferenceDataDTO> listOfPrefsData : listOfKeys) {
    			removeDuplicatePreferences(typeToProcess, listOfPrefsData, individuFromWS);
    		}
    	}
    	
    	//Prevent unwatend update of block in case of multiple blocks in input
    	//We don't know which block will be used for the update
    	checkNumberOfBlockPreferenceByType(individuFromWS);
	}
	
	private void checkNumberOfBlockPreferenceByType(IndividuDTO individuDTOFromWS) throws NumberFormatException, JrafDomainException{
		if(individuDTOFromWS.getPreferenceDTO() == null
				|| individuDTOFromWS.getPreferenceDTO().isEmpty()){
			return;
		}
		
		int TYPE_GPC = 0;
		int TYPE_PIC = 0;
		int TYPE_TPC = 0;
		int TYPE_HDC = 0;
		int TYPE_APC = 0;
		
		for(int i=0; i<individuDTOFromWS.getPreferenceDTO().size(); i++){
			
			PreferenceDTO preferenceDTO = individuDTOFromWS.getPreferenceDTO().get(i);
			
			if(preferenceDTO.getType().equalsIgnoreCase("GPC")){
				TYPE_GPC++;
				maxGPC = maxGPC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_GPC).getEnvValue()) : maxGPC;
				if(TYPE_GPC > maxGPC){
					throw new MultiplePreferencesException("Maximum number of preferences of type GPC reached for this individual");
				}
			}
			
			if(preferenceDTO.getType().equalsIgnoreCase("PIC")){
				TYPE_PIC++;
				maxPIC = maxPIC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_PIC).getEnvValue()) : maxPIC;
				if(TYPE_PIC > maxPIC){
					throw new MultiplePreferencesException("Maximum number of preferences of type PIC reached for this individual");
				}
			}
			
			if(preferenceDTO.getType().equalsIgnoreCase("TPC")){
				TYPE_TPC++;
				maxTPC = maxTPC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_TPC).getEnvValue()) : maxTPC;
				if(TYPE_TPC > maxTPC){
					throw new MultiplePreferencesException("Maximum number of preferences of type TPC reached for this individual");
				}
			}
			
			if(preferenceDTO.getType().equalsIgnoreCase("HDC")){
				TYPE_HDC++;
				maxTDC = maxTDC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_TDC).getEnvValue()) : maxTDC;
				if(TYPE_HDC > maxTDC){
					throw new MultiplePreferencesException("Maximum number of preferences of type HDC reached for this individual");
				}
			}
			
			if(preferenceDTO.getType().equalsIgnoreCase("APC")){
				TYPE_APC++;
				maxAPC = maxAPC == null ? Integer.valueOf(variablesDS.getByEnvKey(MAX_APC).getEnvValue()) : maxAPC;
				if(TYPE_APC > maxAPC){
					throw new MultiplePreferencesException("Maximum number of preferences of type APC reached for this individual");
				}
			}
			
		}
	}
	
	// Control if we have multiple duplicated Preferences in input, in order to only store one preference
	private void removeDuplicatePreferences(String typeToProcess, List<PreferenceDataDTO> listOfPrefsData, IndividuDTO individuFromWS) throws InvalidParameterException {
		Iterator<PreferenceDTO> it = individuFromWS.getPreferenceDTO().iterator();
		boolean found = false;
		
		while (it.hasNext()) {
			PreferenceDTO element = it.next();
			int prefDataFound = 0;
			
			if (element.getType().equalsIgnoreCase(typeToProcess)) {
				for (PreferenceDataDTO prefDataDto : element.getPreferenceDataDTO()) {
					for (PreferenceDataDTO pPrefDataDto : listOfPrefsData) {
						if (pPrefDataDto.equals(prefDataDto)) {
							prefDataFound++;
						}
					}
				}
				if (prefDataFound == listOfPrefsData.size() && !found) {
					found = true;
					continue;
				}
				if (prefDataFound == listOfPrefsData.size() && found) {
					if (typeToProcess.equalsIgnoreCase("TAC")) {	//Special code for TAC Only
						if (listOfPrefsData.get(0).getValue().equalsIgnoreCase("R")) {	//Only Exception for type R (only one), D type could have multiple address
							throw new InvalidParameterException("Only one Address of Residence is allowed for TAC");	
						}
					} else {	//Other types we remove them
						it.remove();	
					}
				}
			}
		}
	}
	
	private void controlValue(List<RefPreferenceKeyType> dataListForType, String key, String value) throws InvalidParameterException, MissingParameterException {
		if (value != null) {
			for (RefPreferenceKeyType refKey: dataListForType) {
				if (key.equalsIgnoreCase(refKey.getKey())) {
					
					// Check mandatory field : Column SCONDITION
					if ("M".equalsIgnoreCase(refKey.getCondition()) && (value == null || "".equals(value))) {
						throw new MissingParameterException("Value for the key " + key + " is mandatory");
					}
					// Check value length for String (only if not REF_TABLE) : Column SDATA_TYPE
					if ("String".equalsIgnoreCase(refKey.getDataType()) && value != null && ( 
						(value.length() < refKey.getMinLength()) || (value.length() > refKey.getMaxLength()) || StringUtils.isEmpty(value))) {
						throw new InvalidParameterException("Invalid value length of key '" + key + "'");
					}
					// Check value format
					if ("Date".equalsIgnoreCase(refKey.getDataType())) {
						if (!SicStringUtils.isValidPreferenceData(value)) {
							throw new InvalidParameterException("Invalid date format for the key " + key + ". Expected format is 'dd/MM/yyyy'");
						}
					}
					if ("Boolean".equalsIgnoreCase(refKey.getDataType())) {
						// TODO attn verifier et affiner liste de booleens possible
						if (!SicStringUtils.isBoolean(value)) {
							throw new InvalidParameterException("Unknown value for the key " + key);
						}
					}
					// REPIND-1701 : Check LIST linked to REF_xxxx table
					if (refKey != null && refKey.getDataType() != null && refKey.getDataType().startsWith("REF_")) {

						List <RefGenericCodeLabelsTypeDTO>	ref = null;
						try {
							// We catch the data linked by the type to check the value
							ref = (List<RefGenericCodeLabelsTypeDTO>) referenceDS.provideGenericSimpleQuery(refKey.getDataType());
						} catch (JrafDomainException e) {
							log.error("Unknow value store for DataType '" + refKey.getDataType() + "' ");
						}
						
						// There is a reference table loaded in order to check validity of the value
						if (ref != null) {
							
							boolean allChecked = true;
							// Check if we have many than one on the LIST
							value = CleanSplitCode(value);							
							String[] values = value.split(BDM_LIST_SEPARATOR);
							
							// REPIND-1727 : Check SINGLE/MULTIPLE
							if (values != null && 	// Lenght should be between MIN and MAX, else Exception
									(values.length < refKey.getMinLength() || values.length > refKey.getMaxLength())) {
								throw new InvalidParameterException("Invalid occurence " + values.length + " for the key " + key);

							// Check is OKAY, We could check the Value !
							} else {
							
							
								for (String v : values) { 
									boolean checked = false;
									
									// REPIND-1701 : Put in upper case
									if (v != null && !v.isEmpty()) { 
									
										// We have to check the value with list of data autorized
										for (RefGenericCodeLabelsTypeDTO aRef : ref) {
										
											// If we catch the right value => SUCCESS
											if (v.toUpperCase().equals(aRef.getCode())) {
												checked = true;
											}
										}
									}
									
									allChecked &= checked;
									
									if (!checked) {
										break;
									}
								}
							
								// If we do not match any value of the reference table => FAIL
								if (!allChecked) {
									throw new InvalidParameterException("Unknown value for the key " + key);
								}
							}
						}
						
//						if (!SicStringUtils.isBoolean(value)) {
//							throw new InvalidParameterException("Unknown value for the key " + key);
//						}
					}
					
					// REPIND-1727 : Replace by the REF_TABLE value on database 
					// Check if Value is authorized for the Key
					// if (PreferenceDataValueEnum.isKeyExists(key)) {
					//	if (!PreferenceDataValueEnum.getAuthorizedValues(key).contains(value)) {
					//		throw new InvalidParameterException("Invalid value for the key " + key + ".");
					//	}
					// }
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

	private boolean prefTypeExistsInDB(String type) throws JrafDomainException {
		List<RefPreferenceTypeDTO> refPrefTypeList = refPreferenceTypeDS.findByType(type);
		if (refPrefTypeList == null) {
			log.debug("preference type not found");
			return false;
		}
		else {
			log.debug("found " + refPrefTypeList.size() + " preferences of type " + type);
			return (refPrefTypeList.size() > 0);
		}
	}

	/* REPIND-729: no more used
	private boolean prefKeyExistsInDB(String skey) throws JrafDomainException {
		List<RefPreferenceKeyDTO> refPrefKeyListDTO = refPreferenceKeyDS.findByKey(skey);
    	if (refPrefKeyListDTO == null) {
    		return false;
    	}
    	else {
    		return (refPrefKeyListDTO.size() > 0);
    	}
	}
	*/

	private void updateFieldPreferenceForCreate(PreferenceDTO prefDto, com.airfrance.repind.dto.individu.SignatureDTO signFromWs) {
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
	
	private void updateFieldPreferenceForReplace(PreferenceDTO prefDto, com.airfrance.repind.dto.individu.SignatureDTO signFromWs, Preference preference) {
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
	
	private void updateFieldPreferenceForUpdate(PreferenceDTO prefDto, com.airfrance.repind.dto.individu.SignatureDTO signFromWs, Preference preference) {
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
