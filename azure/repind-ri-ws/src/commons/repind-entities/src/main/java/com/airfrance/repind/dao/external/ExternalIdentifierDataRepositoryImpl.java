package com.airfrance.repind.dao.external;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.external.ExternalIdentifierData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class ExternalIdentifierDataRepositoryImpl implements ExternalIdentifierDataRepositoryCustom {

	private static final Log log = LogFactory.getLog(ExternalIdentifierDataRepositoryImpl.class);

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<ExternalIdentifierData> findExternalIdentifierData(long identifierId) throws JrafDaoException {
		log.debug("START findExternalIdentifier : " + System.currentTimeMillis());

		// Request of PNM_ID
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from ExternalIdentifierData ");
		strQuery.append("where identifier_id = ? ");
		Query myquery = entityManager.createQuery(strQuery.toString());
		myquery.setParameter(1, identifierId);
		List<ExternalIdentifierData> results;

		try {
			results = (List<ExternalIdentifierData>) myquery.getResultList();
		} catch (NoResultException e) {
			results = null;
		} catch (Exception e) {
			throw new JrafDaoException(e);
		}

		log.debug("END findExternalIdentifier : " + System.currentTimeMillis());

		return results;
	}
}
