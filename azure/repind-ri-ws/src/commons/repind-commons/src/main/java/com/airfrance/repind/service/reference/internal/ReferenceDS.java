package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dao.reference.ErrorRepository;
import com.airfrance.repind.dao.reference.RefComPrefRepository;
import com.airfrance.repind.dto.reference.*;
import com.airfrance.repind.entity.reference.*;
import com.airfrance.repind.util.service.PaginationResult;
import com.airfrance.repind.util.service.PaginationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


/**
 * <p>Title : ReferenceDS.java</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Service
public class ReferenceDS {

    /** logger */
    private static final Log log = LogFactory.getLog(ReferenceDS.class);

    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;


    /** main dao */
    @Autowired
    private RefComPrefRepository refComPrefRepository;

    @Autowired
    private ErrorRepository errorRepository;

    @Transactional(readOnly=true)
    public RefComPrefDTO get(RefComPrefDTO dto) throws JrafDomainException {
      return get(dto.getRefComprefId());
    }

    @Transactional(readOnly=true)
    public RefComPrefDTO get(Integer id) throws JrafDomainException {
    	  Optional<RefComPref> refComPref = refComPrefRepository.findById(id);
          if (!refComPref.isPresent()) {
          	return null;
          }
          return RefComPrefTransform.bo2DtoLight(refComPref.get());
    }


    public RefComPrefRepository getRefComPrefRepository() {
		return refComPrefRepository;
	}

	public void setRefComPrefRepository(RefComPrefRepository refComPrefRepository) {
		this.refComPrefRepository = refComPrefRepository;
	}

	/**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_Qh2wUIvMEeaToe1kllon2ggem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    /** 
     * getRefComPref
     * @param domain in String
     * @param groupType in String
     * @param type in String
     * @return The getRefComPref as <code>List<RefComPref></code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public List<RefComPref> getRefComPref(String domain, String groupType, String type, String market) throws JrafDomainException {
        /*PROTECTED REGION ID(_mBlFgMamEeKA2b5enmBS_w) ENABLED START*/
    	List<RefComPref> refComPrefs = null;
        RefComPref refComPref = new RefComPref();
        if(domain!=null) {
        	RefComPrefDomain rcpDomain = new RefComPrefDomain();
        	rcpDomain.setCodeDomain(domain);
        	refComPref.setDomain(rcpDomain);
        }
        if(groupType!=null) {
        	RefComPrefGType rcpGroupType = new RefComPrefGType();
        	rcpGroupType.setCodeGType(groupType);
        	refComPref.setComGroupeType(rcpGroupType);
        }
        if(type!=null) {
        	RefComPrefType rcpType = new RefComPrefType();
        	rcpType.setCodeType(type);
        	refComPref.setComType(rcpType);
        }
        if(market!=null) {
        	refComPref.setMarket(market);
        }
        refComPrefs = refComPrefRepository.findAll(Example.of(refComPref));
        return refComPrefs;
        /*PROTECTED REGION END*/
    }

    /** 
     * getRefComPref
     * @param domain in String
     * @param groupType in String
     * @param type in String
     * @return The getRefComPref as <code>List<RefComPref></code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public List<RefComPref> getRefComPref(String domain, String groupType, String type) throws JrafDomainException {
        /*PROTECTED REGION ID(_mBlFgMamEeKA2b5enmBS_w) ENABLED START*/
    	
        return getRefComPref(domain, groupType, type, null);
        /*PROTECTED REGION END*/
    }
    
    /** 
     * callError
     * @param numError in String
     * @param details in String
     * @return The callError as <code>String</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public String callError(String numError, String details) throws JrafDomainException {
        /*PROTECTED REGION ID(_WUZE0IJTEeKhdftDNws56g) ENABLED START*/
    	ErrorDTO refError = new ErrorDTO();
    	if(numError.length()>3)
			numError = numError.substring(numError.length()-3); //récupération du code erreur
		
    	refError.setErrorCode(numError);
    	if(details!= null && details.length()>0)
    		return findByExample(refError).get(0).getLabelUK()+": "+details;
    	else
    		return findByExample(refError).get(0).getLabelUK();
        /*PROTECTED REGION END*/
    }
    
    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly=true)
    public List<ErrorDTO> findByExample(ErrorDTO dto) throws JrafDomainException {
        /*PROTECTED REGION ID(_TUSJ0IJTEeKhdftDNws56g DS-CM findByExample1) ENABLED START*/
        List<ErrorDTO> objFounds = null;
        objFounds = findByExample(dto, new Integer(-1), new Integer(-1));
        return objFounds;
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly=true)
    public List<ErrorDTO> findByExample(ErrorDTO dto, Integer nbLignes,
            Integer indexDepart) throws JrafDomainException {
        /*PROTECTED REGION ID(_TUSJ0IJTEeKhdftDNws56g DS-CM findByExample2) ENABLED START*/
        List<ErrorDTO> objFounds = null;
        objFounds = findByExample(dto, nbLignes, indexDepart, Boolean.FALSE,
                Boolean.FALSE);
        return objFounds;
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly=true)
    public List<ErrorDTO> findByExample(ErrorDTO dto, Integer nbLignes,
            Integer indexDepart, Boolean ignoreCase, Boolean likeMode)
            throws JrafDomainException {
        /*PROTECTED REGION ID(_TUSJ0IJTEeKhdftDNws56g DS-CM findWhere) ENABLED START*/
        List boFounds = null;
        List<ErrorDTO> dtoFounds = null;
        ErrorDTO refErreurDTO = null;
        com.airfrance.repind.entity.reference.Error refErreur = null;
        // transformation light dto -> bo
        refErreur = ErrorTransform.dto2BoLight(dto);

        // execution du find
        boFounds = (List) errorRepository.findAll(Example.of(refErreur));

        // transformation bo -> DTO
        if (boFounds != null) {
            dtoFounds = new ArrayList<ErrorDTO>(boFounds.size());
            Iterator i = boFounds.iterator();
            while (i.hasNext()) {
                refErreur = (com.airfrance.repind.entity.reference.Error) i.next();
                refErreurDTO = ErrorTransform.bo2DtoLight(refErreur);
                dtoFounds.add(refErreurDTO);
            }
        }
        return dtoFounds;
        /*PROTECTED REGION END*/
    }
    
   
     /** 
      * refComPrefExist
      * @param domain in String
      * @param groupType in String
      * @param type in String
      * @return The refComPrefExist as <code>Boolean</code>
      * @throws JrafDomainException en cas d'exception
      */
    @Transactional(readOnly=true)
     public Boolean refComPrefExist(String domain, String groupType, String type, String market) throws JrafDomainException {
    	 /*PROTECTED REGION ID(_0yZaYMbmEeKA2b5enmBS_w) ENABLED START*/
    	 List<RefComPref> refComPrefs = null;
     	
     	if (market == null) {     	
     		refComPrefs = getRefComPref(domain,groupType,type);
     	} else {
     		refComPrefs = getRefComPref(domain,groupType,type,market);
     	}
     	
     	if(refComPrefs !=null && !refComPrefs.isEmpty())
     		return true;
     	else
     		return false;
         /*PROTECTED REGION END*/
     }

     
     /** 
     * refComPrefExist
     * @param domain in String
     * @param groupType in String
     * @param type in String
     * @return The refComPrefExist as <code>Boolean</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public Boolean refComPrefExist(String domain, String groupType, String type) throws JrafDomainException {
        /*PROTECTED REGION ID(_0yZaYMbmEeKA2b5enmBS_w) ENABLED START*/
    	List<RefComPref> refComPrefs = null;
    	refComPrefs = getRefComPref(domain,groupType,type);
    	if(refComPrefs !=null && !refComPrefs.isEmpty())
    		return true;
    	else
    		return false;
        /*PROTECTED REGION END*/
    }
    
    /** 
     * refComPrefExistForIndividual
     * @param domain in String
     * @param comGroupType in String
     * @param comType in String
     * @return The refComPrefExistForIndividual as <code>Boolean</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public Boolean refComPrefExistForIndividual(String domain, String comGroupType, String comType) throws JrafDomainException {
        /*PROTECTED REGION ID(_sPSrQIvNEeaToe1kllon2g) ENABLED START*/
    	return refComPrefExist(domain,comGroupType,comType);
        /*PROTECTED REGION END*/
    }

    /*PROTECTED REGION ID(_Qh2wUIvMEeaToe1kllon2g u m) ENABLED START*/
    
	
	public List<RefComPrefDTO> provideComPref(String domain, String language, String market) throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from SIC2.VIEW_REF_COMPREF ");
		strQuery.append(" where DOMAIN= ");
		if(domain == null || domain.isEmpty())
		{
			strQuery.append(" SOME(SELECT REF_COMPREF_DOMAIN.SCODE_DOMAIN from REF_COMPREF_DOMAIN) ");
		}
		else
		{
			strQuery.append(" :domain ");
		}
		if(market !=null && !market.isEmpty())
		{
			strQuery.append(" and (MARKET=:market or MARKET='*')");
		}
		if(language !=null && !language.isEmpty())
		{
			strQuery.append(" and (:language IN (DEFAULT_LANGUAGE_1, DEFAULT_LANGUAGE_2, DEFAULT_LANGUAGE_3, DEFAULT_LANGUAGE_4, DEFAULT_LANGUAGE_5, DEFAULT_LANGUAGE_6, DEFAULT_LANGUAGE_7, DEFAULT_LANGUAGE_8 ,DEFAULT_LANGUAGE_9, DEFAULT_LANGUAGE_10) or DEFAULT_LANGUAGE_1 = '*' )");
		}
		
		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString(), RefComPref.class);
		if(domain != null && !domain.isEmpty())
		{
			myQuery.setParameter("domain", domain);
		}
		if(market !=null && !market.isEmpty())
		{
			myQuery.setParameter("market", market);
		}
		if(language !=null && !language.isEmpty())
		{
			myQuery.setParameter("language", language);
		}
		List<RefComPref> result;
		List<RefComPrefDTO> listResultDTO = new ArrayList<RefComPrefDTO>();

		try {
			result = (List<RefComPref>) myQuery.getResultList();
			for(RefComPref element : result)
			{
				RefComPrefDTO resultDTO=new RefComPrefDTO();
				resultDTO=RefComPrefTransform.bo2Dto(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			result = null;
		}

		return listResultDTO;
	}
		
	
	public PaginationResult<RefComPrefDTO> provideRefComPrefWithPagination(String domain, String language, String market, int index, int maxResults) throws JrafDomainException {
		PaginationResult<RefComPrefDTO> paginationResult = new PaginationResult<RefComPrefDTO>();
		
		Long totalResultsFound = refComPrefRepository.countRefComPref(domain, language, market);
		
		PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_COMPREF, totalResultsFound);
		
		List<RefComPref> listRefComPref = refComPrefRepository.provideRefComPrefWithPagination(domain, language, market, paginationResult.getIndex(), paginationResult.getMaxResults());
		
    	for (RefComPref refComPref : listRefComPref) {
    		paginationResult.getListResults().add(RefComPrefTransform.bo2Dto(refComPref));
    	}
    	
    	return paginationResult;
	}
    
	
	public List<RefComPrefDomainDTO> provideComPrefDomain() throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from SIC2.REF_COMPREF_DOMAIN ");		
		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString(), RefComPrefDomain.class);
		
		List<RefComPrefDomain> result;
		List<RefComPrefDomainDTO> listResultDTO = new ArrayList<RefComPrefDomainDTO>();

		try {
			result = (List<RefComPrefDomain>) myQuery.getResultList();
			for(RefComPrefDomain element : result)
			{
				RefComPrefDomainDTO resultDTO=new RefComPrefDomainDTO();
				resultDTO=RefComPrefDomainTransform.bo2DtoLight(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			result = null;
		}

		return listResultDTO;
	}
	
	public boolean existCodeDomain(String codeDomain) throws JrafDomainException{
		Query query = getEntityManager().createNativeQuery("select 1 from SIC2.REF_COMPREF_DOMAIN D where D.SCODE_DOMAIN = :domain ");
		query.setParameter("domain", codeDomain);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) { return false; }		
	}
	
	
	public List<RefComPrefTypeDTO> provideComPrefType() throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from SIC2.REF_COMPREF_TYPE ");		
		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString(), RefComPrefType.class);
		
		List<RefComPrefType> result;
		List<RefComPrefTypeDTO> listResultDTO = new ArrayList<RefComPrefTypeDTO>();

		try {
			result = (List<RefComPrefType>) myQuery.getResultList();
			for(RefComPrefType element : result)
			{
				RefComPrefTypeDTO resultDTO=new RefComPrefTypeDTO();
				resultDTO=RefComPrefTypeTransform.bo2DtoLight(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			result = null;
		}

		return listResultDTO;
	}
	
	
	public List<RefComPrefGTypeDTO> provideComPrefGType() throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from SIC2.REF_COMPREF_GTYPE ");		
		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString(), RefComPrefGType.class);
		
		List<RefComPrefGType> result;
		List<RefComPrefGTypeDTO> listResultDTO = new ArrayList<RefComPrefGTypeDTO>();

		try {
			result = (List<RefComPrefGType>) myQuery.getResultList();
			for(RefComPrefGType element : result)
			{
				RefComPrefGTypeDTO resultDTO=new RefComPrefGTypeDTO();
				resultDTO=RefComPrefGTypeTransform.bo2DtoLight(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			result = null;
		}

		return listResultDTO;
	}
	
	
	public List<RefComPrefMediaDTO> provideComPrefMedia() throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from SIC2.REF_COMPREF_MEDIA ");		
		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString(), RefComPrefMedia.class);
		
		List<RefComPrefMedia> result;
		List<RefComPrefMediaDTO> listResultDTO = new ArrayList<RefComPrefMediaDTO>();

		try {
			result = (List<RefComPrefMedia>) myQuery.getResultList();
			for(RefComPrefMedia element : result)
			{
				RefComPrefMediaDTO resultDTO=new RefComPrefMediaDTO();
				resultDTO=RefComPrefMediaTransform.bo2DtoLight(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			result = null;
		}

		return listResultDTO;
	}
	
	
	public List<RefComPrefCountryMarketDTO> provideComPrefCountryMarket(String countryMarket) throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from SIC2.REF_COMPREF_COUNTRY_MARKET ");
		if(countryMarket != null && !countryMarket.isEmpty())
		{
			strQuery.append(" WHERE CODE_PAYS=:countryMarket ");
		}
		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString(), RefComPrefCountryMarket.class);
		if(countryMarket != null && !countryMarket.isEmpty())
		{
			myQuery.setParameter("countryMarket", countryMarket);
		}
		
		List<RefComPrefCountryMarket> result;
		List<RefComPrefCountryMarketDTO> listResultDTO = new ArrayList<RefComPrefCountryMarketDTO>();

		try {
			result = (List<RefComPrefCountryMarket>) myQuery.getResultList();
			for(RefComPrefCountryMarket element : result)
			{
				RefComPrefCountryMarketDTO resultDTO=new RefComPrefCountryMarketDTO();
				resultDTO=RefComPrefCountryMarketTransform.bo2DtoLight(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			result = null;
		}

		return listResultDTO;
	}

	// GENERIC OUTPUT : COEE / LABEL FR / LABEL EN
	
	
	public List<RefGenericCodeLabelsTypeDTO> provideGenericComPrefDomain() throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select SCODE_DOMAIN, SLIBELLE_DOMAIN, SLIBELLE_DOMAIN_EN from SIC2.REF_COMPREF_DOMAIN ");		
		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString());
		
		List<RefComPrefDomain> result;
		List<RefGenericCodeLabelsTypeDTO> listResultDTO = new ArrayList<RefGenericCodeLabelsTypeDTO>();

		try {
			result = myQuery.getResultList();
			for(Object element : result)
			{
				RefGenericCodeLabelsTypeDTO resultDTO=new RefGenericCodeLabelsTypeDTO();
				resultDTO=RefGenericCodeLabelsTypeTransform.bo2DtoLight(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			result = null;
		}

		return listResultDTO;
	}

	
	public List<RefGenericCodeLabelsTypeDTO> provideGenericComPrefType() throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select SCODE_TYPE, SLIBELLE_TYPE, SLIBELLE_TYPE_EN from SIC2.REF_COMPREF_TYPE ");		
		                 
		final Query myQuery = getEntityManager().createNativeQuery( strQuery.toString() );
		
		List<RefComPrefType> result;
		List<RefGenericCodeLabelsTypeDTO> listResultDTO = new ArrayList<RefGenericCodeLabelsTypeDTO>();

		try {
			result = myQuery.getResultList();

			for(Object element : result)
			{
				RefGenericCodeLabelsTypeDTO resultDTO=new RefGenericCodeLabelsTypeDTO();
				resultDTO=RefGenericCodeLabelsTypeTransform.bo2DtoLight(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			result = null;
		}

		return listResultDTO;
	}
	
	
	public List<RefGenericCodeLabelsTypeDTO> provideGenericComPrefGType() throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select SCODE_GTYPE, SLIBELLE_GTYPE, SLIBELLE_GTYPE_EN from SIC2.REF_COMPREF_GTYPE ");		
		                 
		final Query myQuery = getEntityManager().createNativeQuery( strQuery.toString() );
		
		List<RefComPrefGType> result;
		List<RefGenericCodeLabelsTypeDTO> listResultDTO = new ArrayList<RefGenericCodeLabelsTypeDTO>();

		try {
			result = myQuery.getResultList();

			for(Object element : result)
			{
				RefGenericCodeLabelsTypeDTO resultDTO=new RefGenericCodeLabelsTypeDTO();
				resultDTO=RefGenericCodeLabelsTypeTransform.bo2DtoLight(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			result = null;
		}

		return listResultDTO;
	}
	
	
	public List<RefGenericCodeLabelsTypeDTO> provideGenericComPrefMedia() throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select SCODE_MEDIA, SLIBELLE_MEDIA, SLIBELLE_MEDIA_EN from SIC2.REF_COMPREF_MEDIA ");		
		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString());
		
		List<RefComPrefMedia> result;
		List<RefGenericCodeLabelsTypeDTO> listResultDTO = new ArrayList<RefGenericCodeLabelsTypeDTO>();

		try {
			result = myQuery.getResultList();
			
			for(Object element : result)
			{
				RefGenericCodeLabelsTypeDTO resultDTO=new RefGenericCodeLabelsTypeDTO();
				resultDTO=RefGenericCodeLabelsTypeTransform.bo2DtoLight(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			result = null;
		}

		return listResultDTO;
	}
	
	/**
	 * TABLE name in parameter should be linked to a table in database with the columns SCODE/LIBELLE/SLIBELLE_EN
	 */
	public List<RefGenericCodeLabelsTypeDTO> provideGenericSimpleQuery(String tableName) throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		List<RefGenericCodeLabelsTypeDTO> listResultDTO = null;
		
		// REPIND-1701 : Due to SONAR to avoid NulPointerException
		if (tableName != null) {
			
			tableName = tableName.toUpperCase();
			
			if (!Pattern.matches("^[A-Z_]*$", tableName)) {						// Controle ALPHABETIQUE ONLY et Underscore
				throw new JrafDomainException("SQL Injection detected...");
			}
		
			switch (tableName) {
				case "PAYS":
					strQuery.append("select SCODE_PAYS AS SCODE, SLIBELLE_PAYS AS SLIBELLE, SLIBELLE_PAYS_EN AS SLIBELLE_EN from SIC2.PAYS");
				break;
				case "LANGUES":
					strQuery.append("select SCODE_LANGUE AS SCODE, SLIBELLE_LANGUE AS SLIBELLE, SLIBELLE_LANGUE_EN AS SLIBELLE_EN from SIC2.LANGUES");
				break;
				case "REF_PREFERENCE_TYPE":
					strQuery.append("select SCODE, SLIBELLE_FR AS SLIBELLE, SLIBELLE_EN from SIC2.REF_PREFERENCE_TYPE");
				break;
				case "REF_PREFERENCE_DATA_KEY":
					strQuery.append("select SCODE, SLIBELLE_FR AS SLIBELLE, SLIBELLE_EN from SIC2.REF_PREFERENCE_DATA_KEY");
				break;
				case "DOM_PRO":
					strQuery.append("select SCODE_PROFESSIONNEL AS SCODE, SLIBELLE_DOMAINE AS SLIBELLE, SLIBELLE_DOMAINE_EN AS SLIBELLE_EN from SIC2.DOM_PRO");
				break;
				case "REF_CONSENT_TYPE_DATA_TYPE":
					strQuery.append("select SCONSENT_TYPE || '.' || SCONSENT_DATA_TYPE AS SCODE, SCONSENT_TYPE AS SLIBELLE, SCONSENT_DATA_TYPE AS SLIBELLE_EN from SIC2.REF_CONSENT_TYPE_DATA_TYPE");
				break;
				default:
					strQuery.append("select SCODE, SLIBELLE, SLIBELLE_EN from SIC2." + tableName);
				break;
			}
			final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString());
			
			List<RefComPrefDomain> result;
			listResultDTO = new ArrayList<RefGenericCodeLabelsTypeDTO>();
	
			try {
				result = myQuery.getResultList();
				for(Object element : result)
				{
					RefGenericCodeLabelsTypeDTO resultDTO=new RefGenericCodeLabelsTypeDTO();
					resultDTO=RefGenericCodeLabelsTypeTransform.bo2DtoLight(element);
					listResultDTO.add(resultDTO);
				}
			} catch (NoResultException e) {
				result = null;
			}
		}

		return listResultDTO;
	}

	public List<RefGenericCodeLabelsTypeDTO> provideGenericPermissionsQuestion() throws JrafDomainException {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select REF_PERMISSIONS_QUESTION_ID, SQUESTION, SQUESTION_EN from SIC2.REF_PERMISSIONS_QUESTION");		
		final Query myQuery = getEntityManager().createNativeQuery(strQuery.toString());
		
		List<RefComPrefDomain> result;
		List<RefGenericCodeLabelsTypeDTO> listResultDTO = new ArrayList<RefGenericCodeLabelsTypeDTO>();

		try {
			result = myQuery.getResultList();
			for(Object element : result)
			{
				RefGenericCodeLabelsTypeDTO resultDTO=new RefGenericCodeLabelsTypeDTO();
				resultDTO=RefGenericCodeLabelsTypeTransform.bo2DtoLight(element);
				listResultDTO.add(resultDTO);
			}
		} catch (NoResultException e) {
			result = null;
		}

		return listResultDTO;
	}
		
	// add your custom methods here if necessary
    /*PROTECTED REGION END*/
}
