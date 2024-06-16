package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.Groupe;
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
 * <p>Title : IGroupeDAO.java</p>
 * BO: Groupe
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */

public class GroupeRepositoryImpl implements GroupeRepositoryCustom {
	
	  /** logger */
    private static final Log log = LogFactory.getLog(GroupeRepositoryImpl.class);

    /** Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	@Override
	public Groupe findByGinWithAllCollections(String pGin) throws JrafDaoException {
		return findByGinWithAllCollections(pGin,null);
	}

	@Override
	public Groupe findByGinWithAllCollections(String pGin, List<String> scopeToProvide) throws JrafDaoException {
		// le gin est requis
        Assert.hasText(pGin, "'pGin' must not be empty");
        
		log.debug("BEGIN findByGinWithAllCollections(" + pGin + ") at " + System.currentTimeMillis());
        
        Groupe result = null;

        StringBuilder hql = new StringBuilder(" SELECT pm FROM Groupe pm ");
        hql.append(" LEFT JOIN FETCH pm.parent ");// pour générer un warning si jamais le groupe a un pere
        
        if(scopeToProvide!=null && (scopeToProvide.contains(ScopeToProvideFirmEnum.POSTAL_ADDRESSES.name()) || scopeToProvide.contains("ALL"))){
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
        	hql.append(" LEFT JOIN FETCH pm.synonymes ");
        }
        if(scopeToProvide!=null && (scopeToProvide.contains("KEY_NUMBERS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.numerosIdent ");
        }
        if(scopeToProvide!=null && (scopeToProvide.contains("COMMERCIAL_ZONES") || scopeToProvide.contains("SALES_ZONES") || scopeToProvide.contains("FINANCIAL_ZONES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.pmZones pmz LEFT JOIN FETCH pmz.zoneDecoup ");
        }
        if(scopeToProvide!=null && (scopeToProvide.contains("NUMBERS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.chiffres ");
        }
        if(scopeToProvide!=null && (scopeToProvide.contains("MARKET_CHOICES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.segmentations ");
        }
        if(scopeToProvide!=null && (scopeToProvide.contains("AGENCIES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH pm.personnesMoralesGerantes ");
        }
        hql.append(" WHERE pm.gin = :param ");  
        
        
        Query myquery = entityManager.createQuery(hql.toString());
        myquery.setParameter("param", pGin);
        
        @SuppressWarnings("unchecked")
        List<Groupe> results = (List<Groupe>) myquery.getResultList();
        
        // FIXME Dangereux mais seul moyen trouvé pour que des updates ne soient pas exécutés 
        // sur les PM gérantes remontées
        // getEntityManager().clear();
        
        if (!results.isEmpty()) {
            
            // ignores multiple results
            result = results.get(0);
        }

		log.debug("END findByGinWithAllCollections at " + System.currentTimeMillis());
        
        return result;
	}

	
}
