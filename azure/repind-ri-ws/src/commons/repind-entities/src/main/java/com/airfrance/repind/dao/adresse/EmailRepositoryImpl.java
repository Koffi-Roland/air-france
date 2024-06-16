package com.airfrance.repind.dao.adresse;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.entity.adresse.Email;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;


public class EmailRepositoryImpl implements EmailRepositoryCustom {

	private static final Log log = LogFactory.getLog(EmailRepositoryImpl.class);

	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	public void invalidOnEmail(Email email) throws JrafDaoException {
		log.debug("START invalid : " + System.currentTimeMillis());

		Date today = new Date();
		int nextVersion = email.getVersion() + 1;

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("update SIC2.EMAILS ");
		strQuery.append("set");
		strQuery.append(" SSTATUT_MEDIUM=:invalid, ");
		strQuery.append(" SSIGNATURE_MODIFICATION=:signatureModification, ");
		strQuery.append(" DDATE_MODIFICATION=:currentDate, ");
		strQuery.append(" SSITE_MODIFICATION=:siteModification, ");
		strQuery.append(" IVERSION=:version ");
		strQuery.append("where SAIN=:sain ");

		final Query myQuery = entityManager.createNativeQuery(strQuery.toString(), Email.class);
		myQuery.setParameter("invalid", MediumStatusEnum.INVALID.toString());
		myQuery.setParameter("signatureModification",email.getSignatureModification());
		myQuery.setParameter("siteModification", email.getSiteModification());
		myQuery.setParameter("currentDate", today);
		myQuery.setParameter("version", nextVersion);
		myQuery.setParameter("sain", email.getSain());

		try {
			myQuery.executeUpdate();
		} catch (Exception e) {
			throw new JrafDaoException(e.getMessage());
		}
	}

	/**
	 * Check existing association between a GIN and an email 
	 * 
	 * @param gin 
	 * @param email customer email in lower case
	 * @return boolean result of the matching association
	 *
	 */
	public boolean isGinEmailExist(String gin, String email) {
		log.debug("START isGinEmailExist : " + System.currentTimeMillis());

		StringBuffer buffer = new StringBuffer("select count(1) from Email em ");
		buffer.append("where em.sgin = :pGin ");
		buffer.append("and em.email = :pEmail ");
		buffer.append("and em.codeMedium = 'D' ");
		buffer.append("and em.statutMedium in ('I', 'V') ");

		Query query = entityManager.createQuery(buffer.toString());
		query.setParameter("pGin", gin);
		query.setParameter("pEmail", email);

		Long temp = (Long) query.getSingleResult();
		int result = temp.intValue();

		log.debug("END isGinEmailExist : " + System.currentTimeMillis());

		return (result != 0); 
	}

}
