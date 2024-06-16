package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds;

import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.BusinessException;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.RequestDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;


@Service("AgenceBuildQueryDS")
public class BuildQueryDS extends AbstractDS {
	
	private static final int queryMaxResults = 100;
	private static final int queryPageSize = 20;
	private static final boolean queryAutoHasLimitRelevance = false;
	private static final int queryAutoLimitRelevance = 60;
	private static final String queryLoadAgency = " SELECT p FROM PersonneMorale p LEFT JOIN FETCH p.numerosIdent ident LEFT JOIN FETCH p.postalAddresses address LEFT JOIN FETCH p.telecoms LEFT JOIN FETCH p.emails LEFT JOIN FETCH p.synonymes LEFT JOIN FETCH p.pmZones WHERE (p.gin = :ginValue) ";
	private static Log LOGGER  = LogFactory.getLog(BuildQueryDS.class);
	
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
    private EntityManager entityManager;

	public BuildQueryDS() {
		super();
	}

	/**
	 * Build an HQL COUNT query from a request
	 * 
	 * @param requestDTO
	 * @throws BusinessException
	 */
	public void buildCountQuery(RequestDTO requestDTO) throws BusinessException {
		Map<String, String> sqlKeyValueMapping = new HashMap<String, String>();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT COUNT(p) FROM Agence p ");
		handleAddressJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleEmailJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleTelecomJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleIdentJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleContractJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleZcJoin(queryString, requestDTO);

		handleInitialWhere(queryString, requestDTO);

		handleZcWhere(queryString, requestDTO, sqlKeyValueMapping);
		handleNameWhere(queryString, requestDTO, sqlKeyValueMapping);

		Query query = entityManager.createQuery(queryString.toString());
		for (Map.Entry<String, String> entry : sqlKeyValueMapping.entrySet()) {
			LOGGER.debug(entry.getKey() + ":" + entry.getValue());
			query.setParameter(entry.getKey(), entry.getValue());
		}

		// UPDATING REQUEST DTO
		requestDTO.setCountHqlQuery(query);
	}

	/**
	 * Build an HQL query from a request
	 * 
	 * @param requestDTO
	 * @throws BusinessException
	 */
	public void buildQuery(RequestDTO requestDTO) throws BusinessException {
		Map<String, String> sqlKeyValueMapping = new HashMap<String, String>();
		StringBuffer queryString = new StringBuffer();

		queryString.append("SELECT p FROM Agence p ");
		handleAddressJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleEmailJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleTelecomJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleIdentJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleContractJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleZcJoin(queryString, requestDTO);

		handleInitialWhere(queryString, requestDTO);
		// ajout 23/07/????
		handleIdentAgencyType(queryString, requestDTO, sqlKeyValueMapping);

		handleZcWhere(queryString, requestDTO, sqlKeyValueMapping);
		handleNameWhere(queryString, requestDTO, sqlKeyValueMapping);
		handleGinWhere(queryString, requestDTO, sqlKeyValueMapping);

		handleOrderBy(queryString, requestDTO);

		this.setRequestProperties(requestDTO);

		this.limitRelevance(requestDTO);
		this.setHqlQuery(queryString, sqlKeyValueMapping, requestDTO);
	}

	// V2 of buildQuery
	public void buildQueryV2(RequestDTO requestDTO) throws BusinessException {
		Map<String, String> sqlKeyValueMapping = new HashMap<String, String>();
		StringBuffer queryString = new StringBuffer();

		queryString.append("SELECT p FROM Agence p ");
		handleAddressJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleEmailJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleTelecomJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleIdentJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleContractJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleZcJoin(queryString, requestDTO);

		// -> Version 2
		handleSynonymsJoin(queryString, requestDTO);

		handleInitialWhere(queryString, requestDTO);
		// ajout 23/07/????
		handleIdentAgencyType(queryString, requestDTO, sqlKeyValueMapping);

		handleZcWhere(queryString, requestDTO, sqlKeyValueMapping);
		handleNameWhere(queryString, requestDTO, sqlKeyValueMapping);

		// -> Version 2
		handleUsualNameWhere(queryString, requestDTO, sqlKeyValueMapping);

		handleGinWhere(queryString, requestDTO, sqlKeyValueMapping);
		handleOrderBy(queryString, requestDTO);
		
		
		/*SELECT DISTINCT P.SGIN,P.SNOM FROM SYNONYME SYN,
		PERS_MORALE P, ADR_POST ADR WHERE 1=1  AND (P.SACTIVITE_LOCAL != 'RA2' OR P.SACTIVITE_LOCAL IS NULL) AND P.STYPE='A'  AND P.SGIN=ADR.SGIN_PM  AND ADR.SCODE_MEDIUM = 'L'  AND ADR.SCODE_POSTAL LIKE '13011%' AND ADR.SCODE_PAYS = 'FR' 
		AND P.SGIN=SYN.SGIN AND SYN.SNOM LIKE 'HAVAS%' AND SYN.STYPE = 'U' 
		ORDER BY P.SNOM;*/
		
		/*queryString.append("SELECT DISTINCT p.SGIN , P.SNOM FROM synonymes SYN,");
		
		//Postal Address
		queryString.append("PERS_MORALE P, ADR_POST ADR WHERE 1=1  AND (P.SACTIVITE_LOCAL != 'RA2' OR P.SACTIVITE_LOCAL IS NULL) AND P.STYPE='A'  AND P.SGIN=ADR.SGIN_PM  AND ADR.SCODE_MEDIUM = 'L'  AND ADR.SCODE_POSTAL LIKE '13011%' AND ADR.SCODE_PAYS = 'FR' ");
		
		//Name
		queryString.append("AND P.SGIN=SYN.SGIN AND SYN.SNOM LIKE 'HAVAS%' AND SYN.STYPE = 'U'");
		
		queryString.append("ORDER BY P.SNOM");*/

		this.setRequestProperties(requestDTO);

		this.limitRelevance(requestDTO);
		this.setHqlQuery(queryString, sqlKeyValueMapping, requestDTO);
	}

	private void setRequestProperties(RequestDTO requestDTO) {
		/*
		 * Setting the request properties
		 */
		requestDTO.setQueryPageSize(queryPageSize);
		requestDTO.setQueryMaxResults(queryMaxResults);
		requestDTO.setLoadAgencyHqlQuery(queryLoadAgency);
	}

	private void limitRelevance(RequestDTO requestDTO) {
		/*
		 * Limit relevance for automatic search
		 */
		requestDTO.setQueryAutoSearchLimit(0);
		requestDTO.setQueryAutoSearchHasLimit(false);
		try {
			if ((queryAutoLimitRelevance >= 0) && (queryAutoLimitRelevance <= 100)) {
				if (queryAutoHasLimitRelevance) {
					requestDTO.setQueryAutoSearchLimit(queryAutoLimitRelevance);
					requestDTO.setQueryAutoSearchHasLimit(true);
				}
			}
		} catch (NumberFormatException ex) { }
	}

	private void setHqlQuery(StringBuffer queryString, Map<String, String> sqlKeyValueMapping,
							 RequestDTO requestDTO) {
		/*
		 * Setting the HQL query
		 */
		LOGGER.debug(queryString.toString());

		Query query = entityManager.createQuery(queryString.toString());
		for (Map.Entry<String, String> entry : sqlKeyValueMapping.entrySet()) {
			LOGGER.debug(entry.getKey() + ":" + entry.getValue());
			query.setParameter(entry.getKey(), entry.getValue());
		}

		requestDTO.setSearchHqlQuery(query);
	}

	/**
	 * Load firm properties from DB
	 * @return
	 */
	public String buildLoadRequest() {
		return queryLoadAgency;
	}

	/* =============================================== */
	/* PRIVATE METHODS */
	/* =============================================== */

	/**
	 * Handles search by Email
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleEmailJoin(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {

		if (BuildConditionsDS.isEmailConditionSet(requestDTO)) {
			queryString.append(" INNER JOIN p.emails AS email WITH (email.email LIKE :email ) ");
			sqlKeyValueMapping.put("email", requestDTO.getContacts().getEmailBloc().getEmail());
		}
	}

	/**
	 * Handles search by phone number
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleTelecomJoin(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		String queryJoinTelecoms = " INNER JOIN p.telecoms AS telecoms WITH (1=1) ";
		if (BuildConditionsDS.isNormalizedPhoneConditionSet(requestDTO)) {
			queryString.append(queryJoinTelecoms);
			queryString.append(
					" AND ((telecoms.snumero LIKE :phone ) OR (telecoms.snorm_inter_phone_number LIKE :phone ) OR (telecoms.snorm_nat_phone_number LIKE :phone ) OR (telecoms.snorm_nat_phone_number_clean LIKE :phone )) ");
			sqlKeyValueMapping.put("phone", requestDTO.getContacts().getPhoneBloc().getNormalizedPhoneNumber());
		} else if (BuildConditionsDS.isPhoneConditionSet(requestDTO)) {
			queryString.append(queryJoinTelecoms);
			queryString.append(" AND (telecoms.snorm_nat_phone_number_clean LIKE :phone ) ");
			sqlKeyValueMapping.put("phone", requestDTO.getContacts().getPhoneBloc().getPhoneNumber());
		}
	}

	/**
	 * Add an initial Where Clause (1=1)
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleInitialWhere(StringBuffer queryString, RequestDTO requestDTO) {
		queryString.append(" WHERE (p.statut NOT IN ('x','X','t','T')) ");
	}

	/**
	 * Generates HQL condition based on name
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleNameWhere(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		if (BuildConditionsDS.isNameConditionSet(requestDTO)
				&& !BuildConditionsDS.isNameTypeConditionIsUsual(requestDTO)) {
			if (BuildConditionsDS.isNameStrictConditionSet(requestDTO)) {
				strictSearchByName(queryString, requestDTO, sqlKeyValueMapping);
			} else if (BuildConditionsDS.isNameLikeConditionSet(requestDTO)) {
				likeSearchByName(queryString, requestDTO, sqlKeyValueMapping);
			}
		}
	}

	/**
	 * Depends on handleName method treats the case of "like" search by name
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void likeSearchByName(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		queryString.append(" AND (p.nom LIKE :nom )  ");
		sqlKeyValueMapping.put("nom", requestDTO.getIdentity().getName() + "%");
	}

	/**
	 * Depends on handleName method treats the case of "strict" search by usual
	 * name
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void strictSearchByName(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		queryString.append(" AND (p.nom LIKE :nom ) ");
		sqlKeyValueMapping.put("nom", requestDTO.getIdentity().getName());
	}

	/**
	 * Generates HQL condition based on commercial zones
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleZcJoin(StringBuffer queryString, RequestDTO requestDTO) {
		if (BuildConditionsDS.isZC1ConditionSet(requestDTO) || BuildConditionsDS.isZC2ConditionSet(requestDTO)
				|| BuildConditionsDS.isZC3ConditionSet(requestDTO) || BuildConditionsDS.isZC4ConditionSet(requestDTO)
				|| BuildConditionsDS.isZC5ConditionSet(requestDTO)) {
			queryString.append(" INNER JOIN p.pmZones AS pmZones INNER JOIN pmZones.zoneDecoup AS zc ");
		}
	}

	/**
	 * Handles search by commercial zones
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleZcWhere(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		if (BuildConditionsDS.isZC1ConditionSet(requestDTO)) {
			queryString.append(" AND (zc.zc1 = :zc1 ) ");
			sqlKeyValueMapping.put("zc1", requestDTO.getCommercialZones().getZc1());
		}
		if (BuildConditionsDS.isZC2ConditionSet(requestDTO)) {
			queryString.append(" AND (zc.zc2 = :zc2 ) ");
			sqlKeyValueMapping.put("zc2", requestDTO.getCommercialZones().getZc2());
		}
		if (BuildConditionsDS.isZC3ConditionSet(requestDTO)) {
			queryString.append(" AND (zc.zc3 = :zc3 ) ");
			sqlKeyValueMapping.put("zc3", requestDTO.getCommercialZones().getZc3());
		}
		if (BuildConditionsDS.isZC4ConditionSet(requestDTO)) {
			queryString.append(" AND (zc.zc4 = :zc4 ) ");
			sqlKeyValueMapping.put("zc4", requestDTO.getCommercialZones().getZc4());
		}
		if (BuildConditionsDS.isZC5ConditionSet(requestDTO)) {
			queryString.append(" AND (zc.zc5 = :zc5 ) ");
			sqlKeyValueMapping.put("zc5", requestDTO.getCommercialZones().getZc5());
		}
	}

	/**
	 * Generate HQL condition by contact
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleAddressJoin(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		if (BuildConditionsDS.isCountryConditionSet(requestDTO)
				|| BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO)
				|| BuildConditionsDS.isCityStrictConditionSet(requestDTO)
				|| BuildConditionsDS.isCityLikeConditionSet(requestDTO)				
				|| BuildConditionsDS.isZipLikeConditionSet(requestDTO)
				|| BuildConditionsDS.isZipStrictConditionSet(requestDTO)				
				|| BuildConditionsDS.isStateConditionSet(requestDTO)) {
			queryString.append(
					" INNER JOIN p.postalAddresses AS address WITH (address.scode_medium = 'L') AND (address.sstatut_medium = 'V') ");
		}

		if (BuildConditionsDS.isCountryConditionSet(requestDTO)) {
			queryString.append(" AND (address.scode_pays = :country_code ) ");
			sqlKeyValueMapping.put("country_code",
					requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
		}
		if (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO)) {
			queryString.append(" AND (address.sno_et_rue LIKE :no_et_rue ) ");
			sqlKeyValueMapping.put("no_et_rue",
					requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet() + "%");
		}
		
		//CITY : Like, Strict
		if (BuildConditionsDS.isCityStrictConditionSet(requestDTO)) {
			queryString.append(" AND (address.sville = :city ) ");
			sqlKeyValueMapping.put("city", requestDTO.getContacts().getPostalAddressBloc().getCity());
		}
		else if (BuildConditionsDS.isCityLikeConditionSet(requestDTO)) {
			queryString.append(" AND (address.sville LIKE :city )  ");
			sqlKeyValueMapping.put("city", requestDTO.getContacts().getPostalAddressBloc().getCity() + "%");
		}
	
		
		//ZIP : Like, Strict
		if (BuildConditionsDS.isZipLikeConditionSet(requestDTO)) {
			queryString.append(" AND (address.scode_postal LIKE :code_postal ) ");
			//String sZipCode = requestDTO.getContacts().getPostalAddressBloc().getZipCode().substring(0, 2); 
			sqlKeyValueMapping.put("code_postal", requestDTO.getContacts().getPostalAddressBloc().getZipCode() + "%");
		}
		else if (BuildConditionsDS.isZipStrictConditionSet(requestDTO)) {
			queryString.append(" AND (address.scode_postal = :code_postal ) ");
			sqlKeyValueMapping.put("code_postal", requestDTO.getContacts().getPostalAddressBloc().getZipCode());
		}
		
		
		
		
		if (BuildConditionsDS.isStateConditionSet(requestDTO)) {
			queryString.append(" AND (address.scode_province = :state_code ) ");
			sqlKeyValueMapping.put("state_code", requestDTO.getContacts().getPostalAddressBloc().getStateCode());
		}
		
	}

	/**
	 * Handles search by identification value
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleIdentJoin(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		if (requestDTO.getIdentification() != null) {
			if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
					&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
				if ((!BuildConditionsDS.isSiretConditionSet(requestDTO))
						&& (!BuildConditionsDS.isSirenConditionSet(requestDTO))
						&& (!BuildConditionsDS.isNcscConditionSet(requestDTO))
						&& (!BuildConditionsDS.isGinConditionSet(requestDTO))) {
					queryString.append(" INNER JOIN p.numerosIdent AS ident WITH (1=1) ");
					if (BuildConditionsDS.isKeyNumberConditionSet(requestDTO)) {
						queryString.append(" AND (ident.numero= :ident_numero ) ");
						sqlKeyValueMapping.put("ident_numero", requestDTO.getIdentification().getIdentificationValue());
					} else {
						queryString.append(" AND ((ident.type= :type ) AND (ident.numero = :ident_numero )) ");
						sqlKeyValueMapping.put("type", requestDTO.getIdentification().getIdentificationType());
						sqlKeyValueMapping.put("ident_numero", requestDTO.getIdentification().getIdentificationValue());
					}
				}
			}
		}
	}

	/**
	 * Allows NCSC searching
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleContractJoin(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {

		if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))
				&& BuildConditionsDS.isNcscConditionSet(requestDTO)) {
			queryString.append(
					" INNER JOIN p.businessRoles AS br INNER JOIN br.roleFirme rf WITH (rf.numero LIKE :rf_numero) ");
			sqlKeyValueMapping.put("rf_numero", requestDTO.getIdentification().getIdentificationValue());
		}
	}

	/**
	 * Allows GIN searching
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleGinWhere(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
			if (BuildConditionsDS.isGinConditionSet(requestDTO)) {
				queryString.append(" AND (p.gin = :gin ) ");
				sqlKeyValueMapping.put("gin", requestDTO.getIdentification().getIdentificationValue());
			}
		}
	}

	/**
	 * Handles HQL query order (by GIN)
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleOrderBy(StringBuffer queryString, RequestDTO requestDTO) {
		queryString.append(" ORDER BY p.gin");

	}

	private void handleIdentAgencyType(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		if (BuildConditionsDS.isAgenceTypeConditionSet(requestDTO)) {
			queryString.append(" AND (p.type= :p_type) ");
			sqlKeyValueMapping.put("p_type", requestDTO.getIdentity().getAgenceType());
		}
	}

	/**
	 * Allows SYNONYMES searching
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleSynonymsJoin(StringBuffer queryString, RequestDTO requestDTO) {
		if (BuildConditionsDS.isNameConditionSet(requestDTO)
				&& BuildConditionsDS.isNameTypeConditionIsUsual(requestDTO)) {
			queryString.append(" INNER JOIN p.synonymes AS syn ");
		}
	}

	/**
	 * Generates HQL condition based on usual name
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleUsualNameWhere(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		if (BuildConditionsDS.isNameConditionSet(requestDTO)
				&& BuildConditionsDS.isNameTypeConditionIsUsual(requestDTO)) {
			if (BuildConditionsDS.isNameStrictConditionSet(requestDTO)) {
				strictSearchByUsualName(queryString, requestDTO, sqlKeyValueMapping);
			} else if (BuildConditionsDS.isNameLikeConditionSet(requestDTO)) {
				likeSearchByUsualName(queryString, requestDTO, sqlKeyValueMapping);
			}
		}
	}

	private final String usualNameSyonymeType = "U";

	/**
	 * Depends on handleName method treats the case of "strict" search by usual
	 * name
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void strictSearchByUsualName(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		queryString.append(" AND (syn.nom LIKE :usualName) AND (syn.type = :usualNameSynType)");
		sqlKeyValueMapping.put("usualName", requestDTO.getIdentity().getName());
		sqlKeyValueMapping.put("usualNameSynType", usualNameSyonymeType);
		;
	}

	/**
	 * Depends on handleName method treats the case of "like" search by usual
	 * name
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void likeSearchByUsualName(StringBuffer queryString, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		queryString.append(" AND (syn.nom LIKE :usualName) AND (syn.type = :usualNameSynType)");
		sqlKeyValueMapping.put("usualName", requestDTO.getIdentity().getName() + "%");
		sqlKeyValueMapping.put("usualNameSynType", usualNameSyonymeType);
	}
}
