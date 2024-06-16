package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds;

import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.BusinessException;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.RequestDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;


@Service("AgenceBuildQueryOldDS")
/**
 * Class to build a query to search Agency like ADH was doing
 * 
 * @author M421262
 *
 */
public class BuildQueryOldDS extends AbstractDS {
	
	private static final int queryMaxResults = 100;
	private static final int queryPageSize = 20;
	private static final String queryLoadAgency = " SELECT p FROM PersonneMorale p LEFT JOIN FETCH p.numerosIdent ident LEFT JOIN FETCH p.postalAddresses address LEFT JOIN FETCH p.telecoms LEFT JOIN FETCH p.emails LEFT JOIN FETCH p.synonymes LEFT JOIN FETCH p.pmZones WHERE (p.gin = :ginValue) ";
	private static Log LOGGER  = LogFactory.getLog(BuildQueryOldDS.class);
	
	@Autowired
	@Qualifier("entityManagerFactoryRepind")
	private EntityManagerFactory entityManagerFactory;

	public BuildQueryOldDS() {
		super();
	}

	/**
	 * Build an HQL query from a request
	 * 
	 * @param requestDTO
	 * @throws BusinessException
	 */
	public void buildOldQuery(RequestDTO requestDTO) throws BusinessException {
		Map<String, String> sqlKeyValueMapping = new HashMap<String, String>();
		StringBuffer queryString = new StringBuffer();
		StringBuffer querySynString = new StringBuffer();
		StringBuffer queryWhere = new StringBuffer(" Where ");
		StringBuffer queryFrom = new StringBuffer();

		// 2 Process search for ADH, with Ident and other
		if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
			queryString.append("SELECT disctinct(p.sgin) FROM PERS_MORALE p ");

			// add criteria for ident
			handleIdentJoin(queryFrom, queryWhere, requestDTO, sqlKeyValueMapping);

			//add criteria for address
			handleAddress(queryWhere, queryFrom, requestDTO, sqlKeyValueMapping);

			queryString.append(queryFrom);
			queryString.append(queryWhere);

		} else {
			queryString.append("SELECT DISTINCT p.sgin FROM PERS_MORALE p, AGENCE agv ");

			//Default statut, option not available in WS
			queryWhere
					.append(" 1=1 and p.stype='A' AND agv.sgin=p.sgin and p.sstatut = 'A' and agv.sagence_ra2 = 'N' ");

			//Add Ident criteria for agency type
			handleIdentAgencyType(queryWhere, requestDTO, sqlKeyValueMapping);

			//Add ZC criteria to search agency
			handleZc(queryWhere, queryFrom, requestDTO, sqlKeyValueMapping);

			//Add address criteria
			handleAddress(queryWhere, queryFrom, requestDTO, sqlKeyValueMapping);

			//Dupplicate commons where and from for request syn
			StringBuffer querySynWhere = new StringBuffer(queryWhere);
			StringBuffer querySynFrom = new StringBuffer(queryFrom);

			// Add name criteria
			handleNameWhere(queryWhere, requestDTO, sqlKeyValueMapping);

			//Dupplicate common query for syn
			querySynString = new StringBuffer(queryString);

			queryString.append(queryFrom);
			queryString.append(queryWhere);

			//Add criteria Synonyme for querySyn
			handleSyn(querySynWhere, querySynFrom, requestDTO);
			querySynString.append(querySynFrom);
			querySynString.append(querySynWhere);
		}

		this.setRequestProperties(requestDTO);
		this.setHqlQuery(queryString, sqlKeyValueMapping, requestDTO);

		//We need to create first request before adding new key for construct SynRequest to used the same
		handleSynPutSqlKey(requestDTO, sqlKeyValueMapping);
		this.setHqlSynQuery(querySynString, sqlKeyValueMapping, requestDTO);
	}

	private void setRequestProperties(RequestDTO requestDTO) {
		/*
		 * Setting the request properties
		 */
		requestDTO.setQueryPageSize(queryPageSize);
		requestDTO.setQueryMaxResults(queryMaxResults);
		requestDTO.setLoadAgencyHqlQuery(queryLoadAgency);
	}

	private void setHqlQuery(StringBuffer queryString,
			Map<String, String> sqlKeyValueMapping,
							 RequestDTO requestDTO) {
		/*
		 * Setting the HQL query
		 */
		LOGGER.debug(queryString.toString());

		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = em.createNativeQuery(queryString.toString());
		for (Map.Entry<String, String> entry : sqlKeyValueMapping.entrySet()) {
			LOGGER.debug(entry.getKey() + ":" + entry.getValue());
			query.setParameter(entry.getKey(), entry.getValue());
		}

		requestDTO.setSearchHqlQuery(query);
	}

	private void setHqlSynQuery(StringBuffer querySynString, Map<String, String> sqlKeyValueMapping,
			RequestDTO requestDTO) {

		EntityManager em = entityManagerFactory.createEntityManager();
		Query querySyn = em.createNativeQuery(querySynString.toString());
		for (Map.Entry<String, String> entry : sqlKeyValueMapping.entrySet()) {
			LOGGER.debug(entry.getKey() + ":" + entry.getValue());
			querySyn.setParameter(entry.getKey(), entry.getValue());
		}

		requestDTO.setCountHqlQuery(querySyn);
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
	 * Generates HQL condition based on name By default is a named is pass but
	 * not the conditionSet use Like
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleNameWhere(StringBuffer queryWhere, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		if (BuildConditionsDS.isNameConditionSet(requestDTO)) {
			if (BuildConditionsDS.isNameStrictConditionSet(requestDTO)) {
				queryWhere.append(" AND (p.snom LIKE :nom ) ");
				sqlKeyValueMapping.put("nom", requestDTO.getIdentity().getName());
			} else {
				queryWhere.append(" AND (p.snom LIKE :nom )  ");
				sqlKeyValueMapping.put("nom", requestDTO.getIdentity().getName() + "%");
			}
		}
	}

	/**
	 * Generates HQL condition based on commercial zones
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleZc(StringBuffer queryWhere, StringBuffer queryFrom, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		//Add ZC criteria to search agency
		if (BuildConditionsDS.isZC1ConditionSet(requestDTO) || BuildConditionsDS.isZC2ConditionSet(requestDTO)
				|| BuildConditionsDS.isZC3ConditionSet(requestDTO) || BuildConditionsDS.isZC4ConditionSet(requestDTO)
				|| BuildConditionsDS.isZC5ConditionSet(requestDTO)) {
			queryFrom.append(", PM_ZONE pmz, ZONE_DECOUP ZDHAB, ZONE_COMM ZCHAB");
			queryWhere.append("AND p.SGIN=pmz.SGIN AND PMZ.IGIN_ZONE=ZDHAB.IGIN AND ZDHAB.IGIN=ZCHAB.IGIN ");

			queryWhere.append("AND (pmz.DDATE_OUVERTURE IS NULL OR pmz.DDATE_OUVERTURE <= SYSDATE) ");
			queryWhere.append("AND (pmz.DDATE_FERMETURE IS NULL OR pmz.DDATE_FERMETURE >= SYSDATE) ");

			if (BuildConditionsDS.isZC1ConditionSet(requestDTO)) {
				queryWhere.append("AND ZCHAB.szc1= :zc1 ");
				sqlKeyValueMapping.put("zc1", requestDTO.getCommercialZones().getZc1());
			}
			if (BuildConditionsDS.isZC2ConditionSet(requestDTO)) {
				queryWhere.append("AND ZCHAB.szc2= :zc2 ");
				sqlKeyValueMapping.put("zc2", requestDTO.getCommercialZones().getZc2());
			}
			if (BuildConditionsDS.isZC3ConditionSet(requestDTO)) {
				queryWhere.append("AND ZCHAB.szc3= :zc3 ");
				sqlKeyValueMapping.put("zc3", requestDTO.getCommercialZones().getZc3());
			}
			if (BuildConditionsDS.isZC4ConditionSet(requestDTO)) {
				queryWhere.append("AND ZCHAB.szc4= :zc4 ");
				sqlKeyValueMapping.put("zc4", requestDTO.getCommercialZones().getZc4());
			}
			if (BuildConditionsDS.isZC5ConditionSet(requestDTO)) {
				queryWhere.append("AND ZCHAB.szc5= :zc5 ");
				sqlKeyValueMapping.put("zc5", requestDTO.getCommercialZones().getZc5());
			}
		}
	}

	/**
	 * Generate HQL condition by contact
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleAddress(StringBuffer queryWhere, StringBuffer queryFrom, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		if (BuildConditionsDS.isCountryConditionSet(requestDTO)
				|| BuildConditionsDS.isCityStrictConditionSet(requestDTO)
				|| BuildConditionsDS.isCityLikeConditionSet(requestDTO)
				|| BuildConditionsDS.isZipLikeConditionSet(requestDTO)
				|| BuildConditionsDS.isZipStrictConditionSet(requestDTO)) {

			queryFrom.append(", ADR_POST adr ");
			queryWhere.append("AND p.sgin=adr.sgin_pm AND adr.scode_medium = 'L' ");

			if (BuildConditionsDS.isCountryConditionSet(requestDTO)) {
				queryWhere.append(" AND (adr.scode_pays = :country_code ) ");
				sqlKeyValueMapping.put("country_code",
						requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			}

			//CITY : Like, Strict, if not define like by default
			if (BuildConditionsDS.isCityConditionSet(requestDTO)) {
				if (BuildConditionsDS.isCityStrictConditionSet(requestDTO)) {
					queryWhere.append(" AND (adr.sville = :city ) ");
					sqlKeyValueMapping.put("city", requestDTO.getContacts().getPostalAddressBloc().getCity());
				} else {
					queryWhere.append(" AND (adr.sville LIKE :city )  ");
					sqlKeyValueMapping.put("city", requestDTO.getContacts().getPostalAddressBloc().getCity() + "%");
				}
			}

			//ZIP : Like, Strict, if not define like by default
			if (BuildConditionsDS.isZipConditionSet(requestDTO)) {
				if (BuildConditionsDS.isZipStrictConditionSet(requestDTO)) {
					queryWhere.append(" AND (adr.scode_postal = :code_postal ) ");
					sqlKeyValueMapping.put("code_postal", requestDTO.getContacts().getPostalAddressBloc().getZipCode());
				} else {
					queryWhere.append(" AND (adr.scode_postal LIKE :code_postal ) ");
					sqlKeyValueMapping.put("code_postal",
							requestDTO.getContacts().getPostalAddressBloc().getZipCode() + "%");
				}
			}
		}
	}

	/**
	 * Handles search by identification value
	 * 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleIdentJoin(StringBuffer queryFrom, StringBuffer queryWhere, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		if (BuildConditionsDS.isSirenConditionSet(requestDTO)) {
			queryFrom.append(", ETABLISSEMENT e ");
			queryWhere.append(" p.sgin = e. sgin and e.ssiret = :siret ");
			sqlKeyValueMapping.put("siret", requestDTO.getIdentification().getIdentificationValue());
		} else {
			queryFrom.append(", NUMERO_IDENT n ,AGENCE A ");
			queryWhere.append(" p.sgin = n.sgin and p.sgin=a.sgin and a.sagence_ra2='N' and n.snumero = :ident ");
			sqlKeyValueMapping.put("ident", requestDTO.getIdentification().getIdentificationValue());
			//No all type in java allowed
			queryWhere.append(" and n.stype = :type ");
			sqlKeyValueMapping.put("type", requestDTO.getIdentification().getIdentificationType());
		}
	}

	private void handleIdentAgencyType(StringBuffer queryWhere, RequestDTO requestDTO,
			Map<String, String> sqlKeyValueMapping) {
		if (BuildConditionsDS.isAgenceTypeConditionSet(requestDTO)) {
			queryWhere.append("and agv.stype = :p_type ");
			sqlKeyValueMapping.put("p_type", requestDTO.getIdentity().getAgenceType());
		}
	}

	private void handleSyn(StringBuffer querySynWhere, StringBuffer querySynFrom, RequestDTO requestDTO) {
		querySynFrom.append(",SYNONYME syn ");
		querySynWhere.append(" and p.sgin=syn.sgin AND syn.snom like :nom AND syn.stype =:type ");
	}

	private void handleSynPutSqlKey(RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping) {
		sqlKeyValueMapping.put("nom", requestDTO.getIdentity().getName() + "%");
		sqlKeyValueMapping.put("type", requestDTO.getIdentity().getNameType());
	}

	public String getNextTypeNomSearch(String typeNomSearch) {
		if ("R".equals(typeNomSearch)) { // cas de recherche sur raison sociale
			return "M";
		} else if ("M".equals(typeNomSearch)) {
			return "U";
		} else if ("U".equals(typeNomSearch)) {
			return "S";
		} else if ("S".equals(typeNomSearch)) {
			return "";
		}

		return null;
	}
}
