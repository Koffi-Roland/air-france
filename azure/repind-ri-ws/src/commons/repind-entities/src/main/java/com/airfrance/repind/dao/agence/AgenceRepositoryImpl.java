package com.airfrance.repind.dao.agence;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.agence.OfficeID;
import com.airfrance.repind.entity.agence.SearchByIdEnum;
import com.airfrance.repind.exception.TooManyResultsDaoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;


public class AgenceRepositoryImpl implements AgenceRepositoryCustom {

	private static final Log log = LogFactory.getLog(AgenceRepositoryImpl.class);

	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	@Autowired
	private OfficeIDRepository officeIDRepository;

	@SuppressWarnings("unchecked")
	public List<Agence> findByNumero(String pNumero) {
		log.debug("START findByNumero : " + System.currentTimeMillis());
        
        Query myquery = entityManager.createQuery(" SELECT DISTINCT ag FROM Agence ag LEFT JOIN FETCH ag.offices LEFT JOIN FETCH ag.numerosIdent ni WHERE ni.numero = :numero ");
        
        myquery.setParameter("numero", pNumero);
        
        List<Agence> results = (List<Agence>) myquery.getResultList();

		log.debug("END findByNumero : " + System.currentTimeMillis());
        return results;
    }

    @SuppressWarnings("rawtypes")
	public Agence findAgenceByStringRequest(StringBuilder request, String optionValue, Date searchDate, String optionType) throws JrafDaoException {
    	Agence result = null;
    	
    	Query myquery = entityManager.createQuery(request.toString());
    	if(optionValue != null && !"".equalsIgnoreCase(optionValue))
    		myquery.setParameter("paramValue", optionValue);
    	if(optionType != null && !"".equalsIgnoreCase(optionType))
    		myquery.setParameter("paramType", optionType);
    	if(searchDate != null)
    		myquery.setParameter("paramSearchDate", searchDate);
    	
    	
    	List listResults = myquery.getResultList();
		if(!listResults.isEmpty()){
			if (listResults.size() == 1) {
				result = (Agence) listResults.get(0);
            } else {
            	String msg = "Many objects match with " + optionType+ " = " + optionValue;
            	throw new TooManyResultsDaoException(msg);
            }
			
		}
    	
    	return result;
    }
    
  
    
    @SuppressWarnings("rawtypes")
	public Agence findAgenceByNativeStringRequest(StringBuilder request, String optionValue, Date searchDate, String optionType) throws JrafDaoException {
    	Agence result = null;
    	
    	Query myquery = entityManager.createNativeQuery(request.toString());
    	if(optionValue != null && !"".equalsIgnoreCase(optionValue))
    		myquery.setParameter("paramValue", optionValue);
    	if(optionType != null && !"".equalsIgnoreCase(optionType))
    		myquery.setParameter("paramType", optionType);
    	if(searchDate != null)
    		myquery.setParameter("paramSearchDate", searchDate);
    	
    	
    	List listResults = myquery.getResultList();
		if(!listResults.isEmpty()){
			if (listResults.size() == 1) {
				result = (Agence) listResults.get(0);
            } else {
            	String msg = "Many objects match with " + optionType+ " = " + optionValue;
            	throw new TooManyResultsDaoException(msg);
            }
			
		}
    	
    	return result;
    }
    /**
     *  Find an agency by options
     * @param optionType 
     * @param optionValue
     * @param scopeToProvide
     * @return agence
     */
    public Agence findAgencyByOptions(String optionType, String optionValue, Date searchDate, List<String> scopeToProvide) throws JrafDaoException {
		log.debug("START findByGDS : " + System.currentTimeMillis());

    	Agence result = null;
    	StringBuilder hql = new StringBuilder("");
    	StringBuilder hqlSearchDate = new StringBuilder("");
    	if(searchDate != null)
    		hqlSearchDate = new StringBuilder(" AND (noIdent.dateFermeture is null or noIdent.dateFermeture > :paramSearchDate ) AND noIdent.dateOuverture < :paramSearchDate ");

    	// Si aucune option en entrée ou si TA (tous)
    	if("TA".equalsIgnoreCase(optionType) || "".equalsIgnoreCase(optionType) || optionType == null)
    	{	
    		// On recherche d'abord par GIN
    		hql.append(" SELECT DISTINCT ag FROM Agence ag WHERE ag.gin = :paramValue ");  
    		
    		result = findAgenceByStringRequest(hql, optionValue, null, null);
    		
    		// Si on a rien trouvé on recherche par NUMERO IDENT
    		if(result == null)
    		{
    			//RAP-78: On autorise la recherche par 7 digits pour le type TA
    			if ("TA".equalsIgnoreCase(optionType) && optionValue.length() == 7) {
    				optionValue += Integer.parseInt(optionValue) % 7;
    			}
    			
    			hql = new StringBuilder("");
    			hql.append(" SELECT DISTINCT ag FROM Agence ag JOIN ag.numerosIdent noIdent ");  
	        	hql.append(" WHERE noIdent.numero = :paramValue ").append(hqlSearchDate);  
	        	result = findAgenceByStringRequest(hql, optionValue, searchDate, null);
    		}
    	} else {
    		SearchByIdEnum searchByIdEnum = SearchByIdEnum.getEnumByString(optionType);
    		if (searchByIdEnum == null) {
    	    	throw new JrafDaoException("OptionType '" + optionType + "' not allowed for agencies");
    	    }
    		switch(searchByIdEnum) {
    		// Option Type = GIN 
    		case GIN : 
    			hql.append(" SELECT DISTINCT ag FROM Agence ag WHERE ag.gin = :paramValue ");    
    			result = findAgenceByStringRequest(hql, optionValue, null, null);
    			break;

    			// Option Type = NUMERO IDENT
    		case IATA :
    		case KLMAGENTCODE : 
    		case ATAF :
    		case ARC : 
    		case FORMERAGENCYNUMBER : 
    		case AGENCYWITHOUTAGREEMENT : 
    			//RAP-78: On autorise la recherche par 7 digits pour le type IA et AG
    			if ((("IA".equalsIgnoreCase(optionType)) || ("AG".equalsIgnoreCase(optionType))) && optionValue.length() == 7) {
    				optionValue += Integer.parseInt(optionValue) % 7;
    			}
    			hql.append(" SELECT DISTINCT ag FROM Agence ag JOIN ag.numerosIdent noIdent ");  
    			hql.append(" WHERE noIdent.numero = :paramValue AND noIdent.type = :paramType").append(hqlSearchDate);  
    			result = findAgenceByStringRequest(hql, optionValue, searchDate, optionType);
    			break;

    			// Option Type = OFFICE ID (GDS)
    		default :
    			hql.append(" SELECT DISTINCT ag FROM Agence ag JOIN ag.offices offid ");  
    			hql.append(" WHERE offid.codeGDS = :paramType AND offid.officeID = :paramValue");  
    			result = findAgenceByStringRequest(hql, optionValue, null, optionType);
    			break;

    		}
    	}
    	if(result != null)
    	{
    		result = findAgencyByGin(result.getGin(),scopeToProvide);
    	}
        
		log.debug("END findPMByOptions : " + System.currentTimeMillis());

    	return result;
    }
    
    /**
     *  Find an agency by Gin
     * @param gin 
     * @param scopeToProvide
     * @return agence
     */
    public Agence findAgencyByGin(String gin, List<String> scopeToProvide) throws JrafDaoException {
    	
		log.debug("START findAgencyByGin : " + System.currentTimeMillis());

    	Agence result = null;
    	Agence finalResult = null;
    	
    	//StringBuilder hqlByGin = new StringBuilder(" SELECT oid.* FROM AGENCE ag LEFT JOIN OFFICE_ID oid ON ag.SGIN = oid.SGIN WHERE ag.SGIN = '130000405042' ORDER BY rownum");
    	StringBuilder hqlByGin = new StringBuilder(" SELECT DISTINCT ag FROM Agence ag");
    	
    	// RAP-215 : SOLARIS MIG sort on office ID by rownum
    	//scopeToProvde contient GDS
    	//if(scopeToProvide!=null && (scopeToProvide.contains("GDS") || scopeToProvide.contains("ALL"))){
    		//hqlByGin.append(" LEFT JOIN FETCH ag.offices");
        //}
    	
    	// scopeToProvde contient POSTAL_ADDRESSES
    	if(scopeToProvide!=null && (scopeToProvide.contains("POSTAL_ADDRESSES") || scopeToProvide.contains("ALL"))){
    		hqlByGin.append(" LEFT JOIN FETCH ag.postalAddresses");
        }
        
    	// scopeToProvde contient TELECOMS
        if(scopeToProvide!=null && (scopeToProvide.contains("TELECOMS") || scopeToProvide.contains("ALL"))){
        	hqlByGin.append(" LEFT JOIN FETCH ag.telecoms");
        }
        
        // scopeToProvde contient EMAILS
        if(scopeToProvide!=null && (scopeToProvide.contains("EMAILS") || scopeToProvide.contains("ALL"))){
        	hqlByGin.append(" LEFT JOIN FETCH ag.emails");
        }
        
        // scopeToProvde contient MARKET_CHOICES
        if(scopeToProvide!=null && (scopeToProvide.contains("MARKET_CHOICES") || scopeToProvide.contains("ALL"))){
        	hqlByGin.append(" LEFT JOIN FETCH ag.segmentations");
        }
        
        // scopeToProvde contient ALLIED_COMPANIES
        if(scopeToProvide!=null && (scopeToProvide.contains("ALLIED_COMPANIES") || scopeToProvide.contains("ALL"))){
        	hqlByGin.append(" LEFT JOIN FETCH ag.compagniesAlliees");
        }
    	
        // scopeToProvde contient SYNONYMS
        if(scopeToProvide!=null && (scopeToProvide.contains("SYNONYMS") || scopeToProvide.contains("ALL"))){
        	hqlByGin.append(" LEFT JOIN FETCH ag.synonymes");
        }
        
        // scopeToProvde contient KEY_NUMBERS
        if(scopeToProvide!=null && (scopeToProvide.contains("KEY_NUMBERS") || scopeToProvide.contains("ALL"))){
        	hqlByGin.append(" LEFT JOIN FETCH ag.numerosIdent noIdent");
        }
        
        hqlByGin.append(" WHERE ag.gin = :paramValue");          
        
    	
    	finalResult = findAgenceByStringRequest(hqlByGin,gin,null,null);
        
        if(scopeToProvide!=null && (scopeToProvide.contains("SEQUOIA_CONTRACTS") || scopeToProvide.contains("ALL"))){
        	hqlByGin = new StringBuilder(" SELECT DISTINCT ag From Agence ag JOIN FETCH ag.businessRoles br " +
        			" JOIN br.roleAgence ra " +
        			"WHERE ag.gin = :paramValue ");
        	result = findAgenceByStringRequest(hqlByGin,gin,null,null);
        	if(result != null)
        		finalResult.setBusinessRoles(result.getBusinessRoles());
        }
        
        // scopeToProvde contient COMMERCIAL_ZONES ou SALES_ZONES ou FINANCIAL_ZONES
        if(scopeToProvide!=null && (scopeToProvide.contains("COMMERCIAL_ZONES") || scopeToProvide.contains("SALES_ZONES") || scopeToProvide.contains("FINANCIAL_ZONES") || scopeToProvide.contains("ALL"))){
        	hqlByGin = new StringBuilder(" SELECT DISTINCT ag FROM Agence ag JOIN FETCH ag.pmZones WHERE ag.gin = :paramValue ");
            result = findAgenceByStringRequest(hqlByGin,gin,null,null);
            if(result != null)
            	finalResult.setPmZones(result.getPmZones());
        }
        
        // scopeToProvde contient NETWORKS
        if(scopeToProvide!=null && (scopeToProvide.contains("NETWORKS") || scopeToProvide.contains("ALL"))){
        	hqlByGin = new StringBuilder(" SELECT DISTINCT ag FROM Agence ag JOIN FETCH ag.reseaux WHERE ag.gin = :paramValue ");
        	result = findAgenceByStringRequest(hqlByGin,gin,null,null);
        	if(result != null)
        		finalResult.setReseaux(result.getReseaux());
        }
        
        // scopeToProvde contient COMPANIES
        if(scopeToProvide!=null && (scopeToProvide.contains("COMPANIES") || scopeToProvide.contains("ALL"))){
        	hqlByGin = new StringBuilder(" SELECT DISTINCT ag FROM Agence ag JOIN FETCH ag.personnesMoralesGerees WHERE ag.gin = :paramValue ");
        	result = findAgenceByStringRequest(hqlByGin,gin,null,null);
        	if(result != null)
        		finalResult.setPersonnesMoralesGerees(result.getPersonnesMoralesGerees());
        }
        
        // scopeToProvde contient CONTENTIOUS
        if(scopeToProvide!=null && (scopeToProvide.contains("CONTENTIOUS") || scopeToProvide.contains("ALL"))){
        	hqlByGin = new StringBuilder(" SELECT DISTINCT ag FROM Agence ag JOIN FETCH ag.profils WHERE ag.gin = :paramValue ");
        	result = findAgenceByStringRequest(hqlByGin,gin,null,null);
        	if(result != null)
        		finalResult.setProfils(result.getProfils());
        }
        
        //RAP-215 special case with scope contains GDS, ordering office id elements by row number
        if(scopeToProvide!=null && (scopeToProvide.contains("GDS") || scopeToProvide.contains("ALL"))){
        	 hqlByGin = new StringBuilder(" SELECT DISTINCT oid FROM OfficeID oid WHERE oid.agence.gin = :paramValue ORDER BY rownum");
        	 List<OfficeID> results = officeIDRepository.findByAgenceGinOrderByRowNum(gin);
        	 
        	 if (results != null)
        	 {
        		CopyOnWriteArraySet<OfficeID> resultSet = new CopyOnWriteArraySet<OfficeID>();
        		 for (OfficeID off : results)
        		 {
        			 resultSet.add(off);
        		 }
        		finalResult.setOffices(resultSet);
        	 }
        }
        	
        
		log.debug("END findAgencyByGin : " + System.currentTimeMillis());
    	
    	return finalResult;
    }

	public Integer getVersionByNumIdent(String pIdentValue) throws JrafDaoException {
		Integer result = null;
		
		log.debug("Start getVersion : " + System.currentTimeMillis());

		pIdentValue += Integer.parseInt(pIdentValue) % 7;

		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ag.version FROM Agence ag JOIN ag.numerosIdent noIdent ");  
		hql.append(" WHERE noIdent.numero = :paramIdent AND noIdent.type = :paramType");

		Query myquery = entityManager.createQuery(hql.toString());

		myquery.setParameter("paramIdent", pIdentValue);
		myquery.setParameter("paramType", "IA");
		
		try {
			result = (Integer) myquery.getSingleResult();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		log.debug("Start getVersion : " + System.currentTimeMillis());

		return result;
	}
	
	public void refresh(Agence agence) {
		entityManager.refresh(agence);
	}

}
