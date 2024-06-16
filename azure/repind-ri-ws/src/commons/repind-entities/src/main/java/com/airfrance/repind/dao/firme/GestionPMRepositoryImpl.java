package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.GestionPM;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * <p>Title : IGestionPMDAO.java</p>
 * BO: GestionPM
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class GestionPMRepositoryImpl implements GestionPMRepositoryCustom {

	  /** logger */
    private static final Log log = LogFactory.getLog(GestionPMRepositoryImpl.class);

    /** Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
    
	@SuppressWarnings("unchecked")
	@Override
	public List<GestionPM> findByPMGereeGin(String gin, Integer firstResultIndex, Integer maxResults)
			throws JrafDaoException {
		log.debug("START findByPMGeranteGin: " + System.currentTimeMillis());
		
		// Prepare return
		List<GestionPM> result = null;

		// Get hibernate session
        Session hSession = ((Session) entityManager.getDelegate());
        
        // Create criteria
        Criteria criteria = null;
        try {
            criteria = hSession.createCriteria(GestionPM.class);
            criteria.add(Restrictions.eq("personneMoraleGeree.gin", gin));
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
			result = (List<GestionPM>) criteria.list();
		} catch(NoResultException e) {
			log.warn("No address found for PM GIN=" + gin);
		} catch(Exception e) {
			throw new JrafDaoException(e);
		}
		
		log.debug("END findByPMGeranteGin: " + System.currentTimeMillis());
        
		return result;
	}

}
