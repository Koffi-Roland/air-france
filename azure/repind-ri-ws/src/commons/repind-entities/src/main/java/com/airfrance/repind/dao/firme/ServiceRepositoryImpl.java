package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;


public class ServiceRepositoryImpl implements ServiceRepositoryCustom {

	private static final Log log = LogFactory.getLog(ServiceRepositoryImpl.class);

	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
	
	public List<Service> findClosedNotModifiedSince(int numberOfDays) throws JrafDaoException {
		log.debug("START findClosedNotModifiedSince : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery(" SELECT s FROM Service s " +
        		"LEFT JOIN FETCH s.businessRoles br " +
        		"LEFT JOIN FETCH br.roleFirme " +
        		"LEFT JOIN FETCH br.roleRcs " +
        		"WHERE s.statut = 'X' AND s.dateModification <= :sinceDate ");
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -numberOfDays);
        myquery.setParameter("sinceDate", cal.getTime());
        
        @SuppressWarnings("unchecked")
		List<Service> results = (List<Service>) myquery.getResultList();

		log.debug("END findClosedNotModifiedSince : " + System.currentTimeMillis());

        return results;
    }
	
	public List<Service> findClosedNotModifiedSinceNotSginPereOnAgency(int numberOfDays) throws JrafDaoException {
		log.debug("START findClosedNotModifiedSinceNotSginPereOnAgency : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery(" SELECT s FROM Service s LEFT JOIN FETCH s.businessRoles br LEFT JOIN FETCH br.roleFirme LEFT JOIN FETCH br.roleRcs WHERE NOT EXISTS (SELECT a FROM Agence a WHERE a.parent.gin=s.gin) AND s.statut = 'X' AND s.dateModification <= :sinceDate ");
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -numberOfDays);
        myquery.setParameter("sinceDate", cal.getTime());
        
        @SuppressWarnings("unchecked")
		List<Service> results = (List<Service>) myquery.getResultList();

		log.debug("END findClosedNotModifiedSinceNotSginPereOnAgency : " + System.currentTimeMillis());

        return results;
    }
	
    public Service findByGinWithAllCollections(final String pGin) throws JrafDaoException {
    	return findByGinWithAllCollections(pGin, null);
    }
    
public Service findByGinWithAllCollections(final String pGin, List<String> scopeToProvide) throws JrafDaoException {
        
        // le gin est requis
        Assert.hasText(pGin, "'pGin' must not be empty");
        
		log.debug("BEGIN findByGinWithAllCollections(" + pGin + ") at " + System.currentTimeMillis());
        
        Service result = null;

        StringBuilder hql = new StringBuilder(" SELECT pm FROM Service pm ");        
        hql.append(" LEFT JOIN FETCH pm.parent p1 ");
        hql.append(" LEFT JOIN FETCH p1.parent p2 ");
        hql.append(" LEFT JOIN FETCH p2.parent");      
        if(scopeToProvide!=null && (scopeToProvide.contains("POSTAL_ADDRESSES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.postalAddresses ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("TELECOMS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.telecoms ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("EMAILS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.emails ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("CONTRACTS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.businessRoles br LEFT JOIN FETCH br.roleFirme LEFT JOIN FETCH br.roleAgence ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("SYNONYMS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.synonymes");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("KEY_NUMBERS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.numerosIdent");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("COMMERCIAL_ZONES") || scopeToProvide.contains("SALES_ZONES") || scopeToProvide.contains("FINANCIAL_ZONES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.pmZones pmz LEFT JOIN FETCH pmz.zoneDecoup ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("NUMBERS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.chiffres");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("MARKET_CHOICES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.segmentations");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("AGENCIES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.personnesMoralesGerantes ");
        }      
        hql.append(" WHERE pm.gin = :param ");
        
        Query myquery = entityManager.createQuery(hql.toString());
        myquery.setParameter("param", pGin);


        @SuppressWarnings("unchecked")
        List<Service> results = (List<Service>) myquery.getResultList();
        
        // FIXME Dangereux mais seul moyen trouvé pour que des updates ne soient pas exécutés 
        // sur les PM gérantes remontées
        // getEntityManager().clear();
        
        if (!results.isEmpty()) {
            
            // ignores multiple results
            result = results.get(0);
        }
        
		log.debug("END findByGinWithAllCollections at " + System.currentTimeMillis());
        //keepInitializedCollections(result);
        return result;
    }

	public void refresh(Service service) {
		entityManager.refresh(service);
	}
}
