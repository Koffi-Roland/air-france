package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;

import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessException;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;


/**
 * Builder class
 * Creates an HQL query from a request
 * using hql.properties and Spring's PropertyPlaceholderConfigurer
 * See build method below
 * @author t950700
 *
 */
@Service
public class BuildQueryDS extends AbstractDS implements IBuildQueryDS {

	private static final int queryMaxResults = 100;
	private static final int queryPageSize = 20;
	private static final String queryJoinSynonymes = " INNER JOIN p.synonymes AS synonymes WITH (1=1) ";
	private static final String queryLoadFirm = " SELECT p FROM PersonneMorale p LEFT JOIN FETCH p.postalAddresses address LEFT JOIN FETCH p.telecoms LEFT JOIN FETCH p.emails LEFT JOIN FETCH p.synonymes LEFT JOIN FETCH p.pmZones WHERE (p.gin = :ginValue)  ";
	
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
    private EntityManager entityManager;
	
	private static Log LOGGER  = LogFactory.getLog(BuildQueryDS.class);
	
	public BuildQueryDS() {
		super();
	}
	
	/**
	 * Build an HQL query from a request
	 * @param requestDTO
	 * @throws BusinessException 
	 */
	public void buildQuery(RequestDTO requestDTO) throws BusinessException
	{
		
		Map<String, String> sqlKeyValueMapping = new HashMap<String, String>();
		StringBuffer queryString = new StringBuffer();
		
		handleContextSelect(queryString, requestDTO);	
		handleAddressJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleEmailJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleTelecomJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleSynonymesJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleIdentJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleContractJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleZcJoin(queryString, requestDTO);
		
		queryString.append(" WHERE (p.class NOT IN (Agence)) AND (p.statut NOT IN ('x','X','t','T')) ");
		
		handleZcWhere(queryString, requestDTO,sqlKeyValueMapping);
		handleNameWhere(queryString, requestDTO, sqlKeyValueMapping);
		handleGinWhere(queryString, requestDTO, sqlKeyValueMapping);
		
		handleOrderBy(queryString, requestDTO);
		
		/*
		 * Setting the request properties
		 */
		requestDTO.setQueryPageSize(queryPageSize);
		requestDTO.setQueryMaxResults(queryMaxResults);
		requestDTO.setLoadFirmHqlQuery(queryLoadFirm);
		
		/*
		 * Limit relevance for automatic search
		 */
		requestDTO.setQueryAutoSearchLimit(0);
		requestDTO.setQueryAutoSearchHasLimit(false);
		
		Query query = entityManager.createQuery(queryString.toString());
		for(Map.Entry<String, String> entry : sqlKeyValueMapping.entrySet()){
			LOGGER.debug(entry.getKey() +":"+ entry.getValue());
			query.setParameter(entry.getKey(), entry.getValue());
		}
				
		LOGGER.debug("SearchFirmOnMultiCriteria | HQL QUERY | " + queryString.toString());
		requestDTO.setSearchHqlQuery(query);
	}
	
	public void buildQueryV2(RequestDTO requestDTO) throws BusinessException
	{
		
		Map<String, String> sqlKeyValueMapping = new HashMap<String, String>();
		StringBuffer queryString = new StringBuffer();
		
		handleContextSelect(queryString, requestDTO);	
		handleAddressJoinV2(queryString, requestDTO, sqlKeyValueMapping);
		handleEmailJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleTelecomJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleSynonymesJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleIdentJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleContractJoin(queryString, requestDTO, sqlKeyValueMapping);
		handleZcJoin(queryString, requestDTO);
		
		queryString.append(" WHERE (p.class NOT IN (Agence)) AND (p.statut NOT IN ('x','X','t','T')) ");
		
		handleZcWhere(queryString, requestDTO,sqlKeyValueMapping);
		handleGinWhere(queryString, requestDTO, sqlKeyValueMapping);
		
		handleOrderBy(queryString, requestDTO);
		
		/*
		 * Setting the request properties
		 */
		requestDTO.setQueryPageSize(queryPageSize);
		requestDTO.setQueryMaxResults(queryMaxResults);
		requestDTO.setLoadFirmHqlQuery(queryLoadFirm);
		
		/*
		 * Limit relevance for automatic search
		 */
		requestDTO.setQueryAutoSearchLimit(0);
		requestDTO.setQueryAutoSearchHasLimit(false);
		
		Query query = entityManager.createQuery(queryString.toString());
		for(Map.Entry<String, String> entry : sqlKeyValueMapping.entrySet()){
			LOGGER.debug(entry.getKey() +":"+ entry.getValue());
			query.setParameter(entry.getKey(), entry.getValue());
		}
				
		LOGGER.debug("SearchFirmOnMultiCriteriaV2 | HQL QUERY | " + queryString.toString());
		requestDTO.setSearchHqlQuery(query);
	}

	/**
	 * Load firm properties from DB
	 * @param personneMorale
	 * @return
	 */
	public String buildLoadRequest(){	
		return queryLoadFirm;
	}
		
	/**
	 * Generate the initial HQL "Select"
	 *   according to the asked type (in the context)
	 * @param queryString
	 * @param requestDTO
	 * @throws BusinessException 
	 */
	private void handleContextSelect(StringBuffer queryString, RequestDTO requestDTO) throws BusinessException
	{
		if(BuildConditionsDS.isFirmTypeGroupsConditionSet(requestDTO))
		{
			queryString.append(" SELECT p FROM Groupe p  ");
		}
		if(BuildConditionsDS.isFirmTypeCompaniesConditionSet(requestDTO))
		{
			queryString.append(" SELECT p FROM Entreprise p  ");
		}
		if(BuildConditionsDS.isFirmTypeFirmsConditionSet(requestDTO))
		{
			queryString.append(" SELECT p FROM Etablissement p ");
		}
		if(BuildConditionsDS.isFirmTypeServicesConditionSet(requestDTO))
		{
			queryString.append(" SELECT p FROM Service p  ");
		}
		if(BuildConditionsDS.isFirmTypeAllConditionSet(requestDTO))
		{
			queryString.append( " SELECT p FROM PersonneMorale p ");
		}
		
	}
	
	
	/**
	 * Handles search by Email
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleEmailJoin(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping) {
		
		if(BuildConditionsDS.isEmailConditionSet(requestDTO))
		{
			queryString.append(" INNER JOIN p.emails AS email WITH (email.email = :email ) ");
			sqlKeyValueMapping.put("email", requestDTO.getContacts().getEmailBloc().getEmail());
		}
	}

	
	/**
	 * Handles search by phone number
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleTelecomJoin(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping) {
		String queryJoinTelecoms =  " INNER JOIN p.telecoms AS telecoms WITH (1=1) ";
		if(BuildConditionsDS.isNormalizedPhoneConditionSet(requestDTO))
		{
			queryString.append(queryJoinTelecoms);
			queryString.append(" AND ((telecoms.snumero = :telecom_numero_1 ) OR (telecoms.snumero = :telecom_numero_2 ) OR (telecoms.snorm_inter_phone_number = :telecom_numero_1 ) OR (telecoms.snorm_inter_phone_number = :telecom_numero_2 ) OR (telecoms.snorm_nat_phone_number = :telecom_numero_1 ) OR (telecoms.snorm_nat_phone_number = :telecom_numero_2 ) OR (telecoms.snorm_nat_phone_number_clean = :telecom_numero_1 ) OR (telecoms.snorm_nat_phone_number_clean = :telecom_numero_2 )) ");
			sqlKeyValueMapping.put("telecom_numero_1", requestDTO.getContacts().getPhoneBloc().getNormalizedPhoneNumber());
			sqlKeyValueMapping.put("telecom_numero_2", requestDTO.getContacts().getPhoneBloc().getPhoneNumber());
		}
		else if(BuildConditionsDS.isPhoneConditionSet(requestDTO))
		{
			queryString.append(queryJoinTelecoms);
			queryString.append(" AND (telecoms.snorm_nat_phone_number_clean LIKE :norm_nat_phone_number_clean )");
			sqlKeyValueMapping.put("norm_nat_phone_number_clean", requestDTO.getContacts().getPhoneBloc().getPhoneNumber());
		}
		
	}
		
	/**
	 * Generates HQL condition based on name
	 *    => USED IN LEGAL_NAME AND ALL_NAMES SEARCH 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleNameWhere(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping)
	{
		if(BuildConditionsDS.isNameTypeLegalConditionSet(requestDTO))
		{
			if(BuildConditionsDS.isNameStrictConditionSet(requestDTO))
			{
				strictSearchByName(queryString, requestDTO, sqlKeyValueMapping);
			}
			else if(BuildConditionsDS.isNameLikeConditionSet(requestDTO))
			{
				likeSearchByName(queryString, requestDTO, sqlKeyValueMapping);
			}
		}
	}
	
	
	
	/**
	 * Depends on handleName method
	 *  treats the case of "strict" search by name
	 * @param queryString
	 * @param requestDTO
	 */
	private void strictSearchByName(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping)
	{
		sqlKeyValueMapping.put("nom", requestDTO.getIdentity().getName());
		queryString.append( " AND (p.nom = :nom ) ");
	}
	
	
	/**
	 * Depends on handleName method
	 *  treats the case of "like" search by name
	 * @param queryString
	 * @param requestDTO
	 */
	private void likeSearchByName(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping)
	{
		queryString.append(" AND (p.nom LIKE :nom ) ");
		sqlKeyValueMapping.put("nom", requestDTO.getIdentity().getName()+"%");
	}
	
	
	/**
	 * Handles search by synonyms
	 * Synonyms are used if we are looking for USAL_NAME OR TRADE_NAME OR ALL NAMES
	 *   => NOT USED IN THE CASE OF LEGAL_NAME SEARCH
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleSynonymesJoin(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping) 
	{
		if(BuildConditionsDS.isNameStrictConditionSet(requestDTO))
		{
			handleStrictSynonymesJoin(queryString, requestDTO, sqlKeyValueMapping);
		}
		else if(BuildConditionsDS.isNameLikeConditionSet(requestDTO))
		{
			handleLikeSynonymesJoin(queryString, requestDTO, sqlKeyValueMapping);
		}
	}
	
	
	/**
	 * Depends on handleName method
	 *  treats the case of "strict" search by name
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleStrictSynonymesJoin(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping)
	{
		String type = null; 
		if(BuildConditionsDS.isNameTypeTradeConditionSet(requestDTO)){
			type = "M";
		}else if(BuildConditionsDS.isNameTypeUsualConditionSet(requestDTO)){	
			type = "U";
		}else{
			return;
		}
		
		queryString.append(queryJoinSynonymes);
		sqlKeyValueMapping.put("type", type);
		sqlKeyValueMapping.put("nom", requestDTO.getIdentity().getName());
		queryString.append(" AND ((synonymes.type = :type ) AND (synonymes.nom = :nom )) ");	
		
	}
	
	
	/**
	 * Depends on handleName method
	 *  treats the case of "like" search by name
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleLikeSynonymesJoin(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping)
	{				
		String type = null;
		if(BuildConditionsDS.isNameTypeTradeConditionSet(requestDTO)){
			type = "M";
		}else if(BuildConditionsDS.isNameTypeUsualConditionSet(requestDTO)){
			type = "U";
		}else{
			return;
		}
		
		queryString.append(queryJoinSynonymes);
		queryString.append(" AND ((synonymes.type = :type ) AND (synonymes.nom LIKE :nom )) ");
		sqlKeyValueMapping.put("type", type);
		sqlKeyValueMapping.put("nom", requestDTO.getIdentity().getName()+"%");
		
	}
	
	
	/**
	 * Generates HQL condition based on commercial zones 
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleZcJoin(StringBuffer queryString, RequestDTO requestDTO)
	{
		if(
				BuildConditionsDS.isZC1ConditionSet(requestDTO) ||
				BuildConditionsDS.isZC2ConditionSet(requestDTO) ||
				BuildConditionsDS.isZC3ConditionSet(requestDTO) ||
				BuildConditionsDS.isZC4ConditionSet(requestDTO) || 
				BuildConditionsDS.isZC5ConditionSet(requestDTO)
		){
			queryString.append(" INNER JOIN p.pmZones AS pmZones INNER JOIN pmZones.zoneDecoup AS zc ");
		}	
		
	}
	
	
	/**
	 * Handles search by commercial zones
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleZcWhere(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping)
	{
		if(BuildConditionsDS.isZC1ConditionSet(requestDTO))
		{
			queryString.append(" AND (zc.zc1 = :zc1)  ");
			sqlKeyValueMapping.put("zc1", requestDTO.getCommercialZones().getZc1());
		}
		if(BuildConditionsDS.isZC2ConditionSet(requestDTO)) 
		{
			queryString.append( " AND (zc.zc2 = :zc2) ");
			sqlKeyValueMapping.put("zc2", requestDTO.getCommercialZones().getZc2());
		}
		if(BuildConditionsDS.isZC3ConditionSet(requestDTO)) 
		{
			queryString.append( " AND (zc.zc3 = :zc3) ");
			sqlKeyValueMapping.put("zc3", requestDTO.getCommercialZones().getZc3());
		}
		if(BuildConditionsDS.isZC4ConditionSet(requestDTO)) 
		{
			queryString.append(" AND (zc.zc4 = :zc4)  ");
			sqlKeyValueMapping.put("zc4", requestDTO.getCommercialZones().getZc4());
		}
		if(BuildConditionsDS.isZC5ConditionSet(requestDTO)) 
		{
			queryString.append(" AND (zc.zc5 = :zc5) ");
			sqlKeyValueMapping.put("zc5", requestDTO.getCommercialZones().getZc5());
		}
		// On ne remonte que les ZC privilégiées
		if(BuildConditionsDS.isZC1ConditionSet(requestDTO)){
			queryString.append(" AND (pmZones.lienPrivilegie = 'O') AND (pmZones.dateFermeture is null OR pmZones.dateFermeture>current_date ) ");
		}
	}
	
	
	/**
	 * Generate HQL condition by contact
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleAddressJoin(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping)
	{
		if(
			BuildConditionsDS.isCountryConditionSet(requestDTO) ||
			BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO) || 
			BuildConditionsDS.isCityStrictConditionSet(requestDTO) ||
			BuildConditionsDS.isCityLikeConditionSet(requestDTO) ||
			BuildConditionsDS.isZipLikeConditionSet(requestDTO) ||
			BuildConditionsDS.isZipStrictConditionSet(requestDTO) ||
			BuildConditionsDS.isStateConditionSet(requestDTO)
		){
			queryString.append(" INNER JOIN p.postalAddresses AS address WITH (address.scode_medium = 'L') AND (address.sstatut_medium = 'V') ");
		}
			
		if(BuildConditionsDS.isCountryConditionSet(requestDTO))
		{			
			queryString.append(" AND (address.scode_pays LIKE :code_pays ) ");
			sqlKeyValueMapping.put("code_pays", requestDTO.getContacts().getPostalAddressBloc().getCountryCode()+"%");
		}	
		if(BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
		{
			queryString.append(" AND (address.sno_et_rue LIKE :no_et_rue ) ");
			sqlKeyValueMapping.put("no_et_rue", requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet()+"%");
		}
		if(BuildConditionsDS.isCityStrictConditionSet(requestDTO))
		{
			queryString.append(" AND (address.sville = :ville ) ");
			sqlKeyValueMapping.put("ville", requestDTO.getContacts().getPostalAddressBloc().getCity());
		}
		if(BuildConditionsDS.isCityLikeConditionSet(requestDTO))
		{
			queryString.append(" AND (address.sville LIKE :ville ) ");
			sqlKeyValueMapping.put("ville", requestDTO.getContacts().getPostalAddressBloc().getCity()+"%");
		}		
		if(BuildConditionsDS.isZipLikeConditionSet(requestDTO))
		{
			queryString.append(" AND (address.scode_postal LIKE :code_postal ) ");
			sqlKeyValueMapping.put("code_postal", requestDTO.getContacts().getPostalAddressBloc().getZipCode()+ "%");
		}
		if(BuildConditionsDS.isZipStrictConditionSet(requestDTO))
		{
			queryString.append(" AND (address.scode_postal = :code_postal ) ");
			sqlKeyValueMapping.put("code_postal", requestDTO.getContacts().getPostalAddressBloc().getZipCode());
		}
		if(BuildConditionsDS.isStateConditionSet(requestDTO))
		{
			queryString.append(" AND (address.scode_province LIKE :code_province )  ");
			sqlKeyValueMapping.put("code_province", requestDTO.getContacts().getPostalAddressBloc().getStateCode()+"%");
		}
	}
	
	/**
	 * Generate HQL condition by contact
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleAddressJoinV2(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping)
	{
		if(
			BuildConditionsDS.isCountryConditionSet(requestDTO) ||
			BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO) || 
			BuildConditionsDS.isCityStrictConditionSet(requestDTO) ||
			BuildConditionsDS.isCityLikeConditionSet(requestDTO) ||
			BuildConditionsDS.isZipLikeConditionSet(requestDTO) ||
			BuildConditionsDS.isZipStrictConditionSet(requestDTO) ||
			BuildConditionsDS.isStateConditionSet(requestDTO)
		){
			queryString.append(" INNER JOIN p.postalAddresses AS address WITH (address.scode_medium = 'L') AND (address.sstatut_medium IN ('V','T')) ");
		}
			
		if(BuildConditionsDS.isCountryConditionSet(requestDTO))
		{			
			queryString.append(" AND (address.scode_pays = :code_pays ) ");
			sqlKeyValueMapping.put("code_pays", requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
		}	
		if(BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
		{
			queryString.append(" AND (address.sno_et_rue LIKE :no_et_rue ) ");
			sqlKeyValueMapping.put("no_et_rue", requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet()+"%");
		}
		if(BuildConditionsDS.isCityStrictConditionSet(requestDTO))
		{
			queryString.append(" AND (address.sville = :ville ) ");
			sqlKeyValueMapping.put("ville", requestDTO.getContacts().getPostalAddressBloc().getCity());
		}
		if(BuildConditionsDS.isCityLikeConditionSet(requestDTO))
		{
			queryString.append(" AND (address.sville LIKE :ville ) ");
			sqlKeyValueMapping.put("ville", requestDTO.getContacts().getPostalAddressBloc().getCity()+"%");
		}		
		if(BuildConditionsDS.isZipLikeConditionSet(requestDTO))
		{
			queryString.append(" AND (address.scode_postal LIKE :code_postal ) ");
			sqlKeyValueMapping.put("code_postal", requestDTO.getContacts().getPostalAddressBloc().getZipCode()+ "%");
		}
		if(BuildConditionsDS.isZipStrictConditionSet(requestDTO))
		{
			queryString.append(" AND (address.scode_postal = :code_postal ) ");
			sqlKeyValueMapping.put("code_postal", requestDTO.getContacts().getPostalAddressBloc().getZipCode());
		}
		if(BuildConditionsDS.isStateConditionSet(requestDTO))
		{
			queryString.append(" AND (address.scode_province LIKE :code_province )  ");
			sqlKeyValueMapping.put("code_province", requestDTO.getContacts().getPostalAddressBloc().getStateCode()+"%");
		}
	}
	
	
	/**
	 * Handles search by identification value
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleIdentJoin(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping) {

		if((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
		{
			if((! BuildConditionsDS.isSiretConditionSet(requestDTO))
					&&	(! BuildConditionsDS.isSirenConditionSet(requestDTO))
					&&	(! BuildConditionsDS.isNcscConditionSet(requestDTO))
					&&	(! BuildConditionsDS.isGinConditionSet(requestDTO)))
			{
				queryString.append(" INNER JOIN p.numerosIdent AS ident WITH  ");

				if(BuildConditionsDS.isKeyNumberConditionSet(requestDTO))
				{
					queryString.append(" (ident.numero = :ident_numero )  ");
					sqlKeyValueMapping.put("ident_numero", requestDTO.getIdentification().getIdentificationValue());
				}
				else
				{
					queryString.append(" ((ident.type= :ident_type ) AND (ident.numero = :ident_numero )) ");
					sqlKeyValueMapping.put("ident_type", requestDTO.getIdentification().getIdentificationType());
					sqlKeyValueMapping.put("ident_numero", requestDTO.getIdentification().getIdentificationValue());
				}
			}
		}
	}
	
	
	/**
	 * Allows NCSC searching
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleContractJoin(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping) {
		if(		BuildConditionsDS.isIdentTypeConditionSet(requestDTO) && 
				BuildConditionsDS.isIdentValueConditionSet(requestDTO) &&
				BuildConditionsDS.isNcscConditionSet(requestDTO)
		  ){	
			queryString.append(" INNER JOIN p.businessRoles AS br INNER JOIN br.roleFirme rf WITH (rf.numero = :contract_numero) ");
			sqlKeyValueMapping.put("contract_numero", requestDTO.getIdentification().getIdentificationValue());
		}
	}
	
	/**
	 * Allows GIN searching
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleGinWhere(StringBuffer queryString, RequestDTO requestDTO, Map<String, String> sqlKeyValueMapping) {
		if(  BuildConditionsDS.isIdentTypeConditionSet(requestDTO)	&&
			 BuildConditionsDS.isIdentValueConditionSet(requestDTO) &&
			 BuildConditionsDS.isGinConditionSet(requestDTO)
		){
			queryString.append(" AND (p.gin = :gin )  ");
			sqlKeyValueMapping.put("gin", requestDTO.getIdentification().getIdentificationValue());
		}
	}

	/**
	 * Handles HQL query order (by GIN)
	 * @param queryString
	 * @param requestDTO
	 */
	private void handleOrderBy(StringBuffer queryString, RequestDTO requestDTO) {
		queryString.append(" ORDER BY p.gin");
		
	}

}
