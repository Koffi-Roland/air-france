package com.airfrance.repind.dao.firme;

/*PROTECTED REGION ID(_ZJUfQLbCEeCrCZp8iGNNVwDAO DAO I i) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.Entreprise;
import com.airfrance.repind.entity.firme.ScopeToProvideFirmEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : IEntrepriseDAO.java</p>
 * BO: Entreprise
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class EntrepriseRepositoryImpl implements EntrepriseRepositoryCustom {

	private static final Log log = LogFactory.getLog(EntrepriseRepositoryImpl.class);

	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	@Override
	public Entreprise findByGinWithAllCollections(String pGin) throws JrafDaoException {
		return findByGinWithAllCollections(pGin, null);
	}

	@Override
	public Entreprise findByGinWithAllCollections(String pGin, List<String> scopeToProvide) throws JrafDaoException {
		// le gin est requis
        Assert.hasText(pGin, "'pGin' must not be empty");
        
		log.debug("BEGIN findByGinWithAllCollections(" + pGin + ") at " + System.currentTimeMillis());
                
        Entreprise result = null;

        StringBuilder hql = new StringBuilder(" SELECT entreprise FROM Entreprise entreprise ");
        hql.append(" LEFT JOIN FETCH entreprise.parent ");
        
        if(scopeToProvide!=null && (scopeToProvide.contains(ScopeToProvideFirmEnum.POSTAL_ADDRESSES.name()) || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH entreprise.postalAddresses ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("TELECOMS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH entreprise.telecoms ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("EMAILS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH entreprise.emails ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("CONTRACTS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH entreprise.businessRoles br LEFT JOIN FETCH br.roleFirme LEFT JOIN FETCH br.roleAgence ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("SYNONYMS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH entreprise.synonymes");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("KEY_NUMBERS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH entreprise.numerosIdent");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("COMMERCIAL_ZONES") || scopeToProvide.contains("SALES_ZONES") || scopeToProvide.contains("FINANCIAL_ZONES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH entreprise.pmZones pmz LEFT JOIN FETCH pmz.zoneDecoup ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("NUMBERS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH entreprise.chiffres");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("MARKET_CHOICES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH entreprise.segmentations");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("AGENCIES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH entreprise.personnesMoralesGerantes ");
        }       
        hql.append(" WHERE entreprise.gin = :param ");  
        
        Query myquery = entityManager.createQuery(hql.toString());
        myquery.setParameter("param", pGin);

        @SuppressWarnings("unchecked")
        List<Entreprise> results = (List<Entreprise>) myquery.getResultList();
        
        // FIXME Dangereux mais seul moyen trouvé pour que des updates ne soient pas exécutés 
        // sur les PM gérantes remontées
        //getEntityManager().clear();
        
        if (!results.isEmpty()) {
            
            // ignores multiple results
            result = results.get(0);
        }
		
		log.debug("END findByGinWithAllCollections at " + System.currentTimeMillis());
        
        return result;
	}

	@Override
	public Entreprise findUniqueBySiren(String pSiren) throws JrafDaoException {
		 Entreprise result = null;
	        
	        Query myquery = entityManager.createQuery(" SELECT entr FROM Entreprise entr WHERE entr.siren = :paramValue ");
	        myquery.setParameter("paramValue", pSiren);
	        myquery.setMaxResults(2);
	        
	        @SuppressWarnings("unchecked")
	        List<Entreprise> results = (List<Entreprise>) myquery.getResultList();
	        
	        switch (results.size()) {
	        case 0:
	            break;
	        case 1:
	            result = results.get(0);
	            break;
	        default:
	            throw new JrafDaoException("Several companies are sharing the siren number " + pSiren);
	        }
	        
	        return result;
	}

	@Override
	public Entreprise findAnyBySiren(String pSiren) throws JrafDaoException {
		Entreprise result = null;
        
        Query myquery = entityManager.createQuery(" SELECT entr FROM Entreprise entr WHERE entr.siren = :paramValue ");
        myquery.setParameter("paramValue", pSiren);
        myquery.setMaxResults(2);
        
        @SuppressWarnings("unchecked")
        List<Entreprise> results = (List<Entreprise>) myquery.getResultList();
        
        switch (results.size()) {
        case 0:
            break;
        default:
            result = results.get(0);
            break;
        }

        return result;
	}

	@Override
	public List<String> findClosed() throws JrafDaoException {
		log.debug("START findClosed : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery(" SELECT DISTINCT e.gin FROM Entreprise e " +
        		"WHERE e.statut = 'X'");
        
        List<String> results = (List<String>) myquery.getResultList();

		log.debug("END findClosed : " + System.currentTimeMillis());

        return results;
	}

	@Override
	public List<String> findClosedNotSginPereOnAgency() throws JrafDaoException {
		log.debug("START findClosedNotSginPereOnAgency : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery(" SELECT DISTINCT e.gin FROM Entreprise e WHERE NOT EXISTS (SELECT a FROM Agence a WHERE a.parent.gin = e.gin) AND e.statut = 'X' ");
        
        List<String> results = (List<String>) myquery.getResultList();

		log.debug("END findClosedNotSginPereOnAgency : " + System.currentTimeMillis());

        return results;
	}

   
}
