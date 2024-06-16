package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.Chiffre;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ChiffreRepositoryImpl implements ChiffreRepositoryCustom {

	private static final Log log = LogFactory.getLog(ChiffreRepositoryImpl.class);

	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
	
		    
	@Override
	public List<Chiffre> findByPMGin(String gin, Integer firstResultIndex, Integer maxResults) throws JrafDaoException {
		log.debug("START findPMNumbersByGin: " + System.currentTimeMillis());
		
		// Prepare return
		List<Chiffre> result = null;

		// Get hibernate session
        Session hSession = ((Session) entityManager.getDelegate());
        
        // Create criteria
        Criteria criteria = null;
        try {
            criteria = hSession.createCriteria(Chiffre.class);
            criteria.add(Restrictions.eq("personneMorale.gin", gin));
            if (firstResultIndex != null) {
                criteria.setFirstResult(firstResultIndex);
            }
            if (maxResults != null) {
                criteria.setMaxResults(maxResults);
            }
        } catch (Exception e) {
        	log.error(e);
        }
        
        if (criteria == null)
        	return result;
        
        // Get results
		try {
			result = (List<Chiffre>) criteria.list();
		} catch(NoResultException e) {
			log.warn("No numbers found for PM GIN=" + gin);
		} catch(Exception e) {
			throw new JrafDaoException(e);
		}
		
		log.debug("END findPMNumbersByGin: " + System.currentTimeMillis());
        
		return result;
	}

}
