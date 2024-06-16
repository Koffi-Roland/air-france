package com.airfrance.repind.dao.zone;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.zone.PmZone;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : IPmZoneDAO.java</p>
 * BO: PmZone
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class PmZoneRepositoryImpl implements PmZoneRepositoryCustom  {
	private static final String AGENCE_TABLE = "AGENCE";
	private static final String ETABLISSEMENT_TABLE = "ETABLISSEMENT";


	/** logger */
	private static final Log log = LogFactory.getLog(PmZoneRepositoryImpl.class);

	/** Entity Manager*/
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Override
	public List<PmZone> findByPMGin(String gin) throws JrafDaoException {
		// TODO: Test this method
		log.debug("START findByPMGin: " + System.currentTimeMillis());

		// Prepare return
		List<PmZone> result = null;

		// Get hibernate session
		Session hSession = ((Session) entityManager.getDelegate());

		// Create criteria
		Criteria criteria = null;
		try {
			criteria = hSession.createCriteria(PmZone.class);
			criteria.add(Restrictions.eq("personneMorale.gin", gin));
		} catch (Exception e) {
			throw new JrafDaoException("Technical error!", e);
		}

		// Get results
		try {
			result = (List<PmZone>) criteria.list();
		} catch(NoResultException e) {
			log.warn("No PM Zone found for PM GIN=" + gin);
		} catch(Exception e) {
			throw new JrafDaoException(e);
		}

		log.debug("END findByPMGin: " + System.currentTimeMillis());

		return result;

		/*PROTECTED REGION ID(_5oPAoLbNEeCrCZp8iGNNVwDAO - IDAO) ENABLED START*/
		// add your custom methods here if necessary
		/*PROTECTED REGION END*/
	}

	@Override
	public List<PmZone> findAgencyZvLinks(int zv0, int zv1, int zv2, int zv3, LocalDate date) {
		StringBuilder queryStr = new StringBuilder();

		queryStr.append("SELECT PM_ZONE.* " + 
				"FROM PM_ZONE " + 
				"INNER JOIN ZONE_VENTE ON ZONE_VENTE.IGIN = PM_ZONE.IGIN_ZONE " + 
				"WHERE PM_ZONE.SGIN IN ( " + 
				"    SELECT pmz.SGIN " + 
				"    FROM PM_ZONE pmz " + 
				"    INNER JOIN AGENCE a ON a.SGIN = pmz.SGIN " + 
				"    INNER JOIN ZONE_VENTE zv ON zv.IGIN = pmz.IGIN_ZONE " + 
				"    WHERE (pmz.DDATE_OUVERTURE IS NULL OR TRUNC(pmz.DDATE_OUVERTURE) <= TRUNC(:date)) " + 
				"    AND (pmz.DDATE_FERMETURE IS NULL OR TRUNC(pmz.DDATE_FERMETURE) >= TRUNC(:date)) " + 
				"    AND pmz.SLIEN_PRIVILEGIE = 'O' " + 
				"    AND zv.ZV0 = :zv0 AND zv.ZV1 = :zv1 AND zv.ZV2 = :zv2 AND zv.ZV3 = :zv3 " + 
				") " + 
				"AND (PM_ZONE.DDATE_FERMETURE IS NULL OR TRUNC(PM_ZONE.DDATE_FERMETURE) >= TRUNC(SYSDATE)) " +
				"AND PM_ZONE.SLIEN_PRIVILEGIE = 'O'");

		Query query = entityManager.createNativeQuery(queryStr.toString(), PmZone.class);
		query.setParameter("zv0", zv0);
		query.setParameter("zv1", zv1);
		query.setParameter("zv2", zv2);
		query.setParameter("zv3", zv3);
		query.setParameter("date", date);

		return query.getResultList();
	}

	@Override
	public List<PmZone> findFirmZcLinks(String zc1, String zc2, String zc3, String zc4, String zc5, LocalDate date) {
		return findZcLinks(ETABLISSEMENT_TABLE, zc1, zc2, zc3, zc4, zc5, date);
	}

	@Override
	public List<PmZone> findAgencyZcLinks(String zc1, String zc2, String zc3, String zc4, String zc5, LocalDate date) {
		return findZcLinks(AGENCE_TABLE, zc1, zc2, zc3, zc4, zc5, date);
	}
	
	private List<PmZone> findZcLinks(String pmTable, String zc1, String zc2, String zc3, String zc4, String zc5, LocalDate date) {
		StringBuilder queryStr = new StringBuilder();

		queryStr.append("SELECT PM_ZONE.* " + 
				"FROM PM_ZONE " + 
				"INNER JOIN ZONE_COMM ON ZONE_COMM.IGIN = PM_ZONE.IGIN_ZONE " + 
				"WHERE PM_ZONE.SGIN IN ( " + 
				"    SELECT pmz.SGIN " + 
				"    FROM PM_ZONE pmz " + 
				"    INNER JOIN " + pmTable + " pm ON pm.SGIN = pmz.SGIN " + 
				"    INNER JOIN ZONE_COMM zc ON zc.IGIN = pmz.IGIN_ZONE " + 
				"    WHERE (pmz.DDATE_OUVERTURE IS NULL OR TRUNC(pmz.DDATE_OUVERTURE) <= TRUNC(:date)) " + 
				"    AND (pmz.DDATE_FERMETURE IS NULL OR TRUNC(pmz.DDATE_FERMETURE) >= TRUNC(:date)) " + 
				"    AND pmz.SLIEN_PRIVILEGIE = 'O' " + 
				"    AND zc.SZC1 = :zc1 AND zc.SZC2 = :zc2 AND zc.SZC3 = :zc3 AND zc.SZC4 = :zc4 AND zc.SZC5 = :zc5 " + 
				") " + 
				"AND (PM_ZONE.DDATE_FERMETURE IS NULL OR TRUNC(PM_ZONE.DDATE_FERMETURE) >= TRUNC(SYSDATE))" +
				"AND PM_ZONE.SLIEN_PRIVILEGIE = 'O'");

		Query query = entityManager.createNativeQuery(queryStr.toString(), PmZone.class);
		query.setParameter("zc1", zc1);
		query.setParameter("zc2", zc2);
		query.setParameter("zc3", zc3);
		query.setParameter("zc4", zc4);
		query.setParameter("zc5", zc5);
		query.setParameter("date", date);

		return query.getResultList();
	}
}
