package com.airfrance.repind.dao.external;

import com.airfrance.ref.exception.external.ExternalIdentifierLinkedToDifferentIndividualException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.type.YesNoFlagEnum;
import com.airfrance.ref.type.external.ExternalIdentifierDataKeyEnum;
import com.airfrance.ref.type.external.ExternalIdentifierTypeEnum;
import com.airfrance.repind.entity.external.ExternalIdentifier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class ExternalIdentifierRepositoryImpl implements ExternalIdentifierRepositoryCustom {

	private static final Log log = LogFactory.getLog(ExternalIdentifierRepositoryImpl.class);

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	@SuppressWarnings(value = { "unchecked" })
	public List<ExternalIdentifier> findExternalIdentifier(String gin) throws JrafDaoException {

		log.debug("START findExternalIdentifier : " + System.currentTimeMillis());

		// Request of PNM_ID
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select ext from ExternalIdentifier ext ");
		strQuery.append("left outer join ext.individu ind ");
		strQuery.append("left outer join ext.externalIdentifierDataList dat ");
		strQuery.append("where ind.sgin = :gin ");
		strQuery.append("and ext.type = :type ");
		strQuery.append("and (dat.key = :key and dat.value = :value) ");

		Query myquery = entityManager.createQuery(strQuery.toString());
		myquery.setParameter("gin", gin);
		myquery.setParameter("type", ExternalIdentifierTypeEnum.PNM_ID.toString());
		myquery.setParameter("key", ExternalIdentifierDataKeyEnum.OPTIN.toString());
		myquery.setParameter("value", YesNoFlagEnum.YES.toString());

		List<ExternalIdentifier> results;

		try {
			// Catch PNM_ID
			results = (List<ExternalIdentifier>) myquery.getResultList();

		} catch (NoResultException e) {
			results = null;
		} catch (Exception e) {
			throw new JrafDaoException(e);
		}

		log.debug("END findExternalIdentifier : " + System.currentTimeMillis());

		return results;

	}

	@SuppressWarnings(value = { "unchecked" })
	public List<ExternalIdentifier> findExternalIdentifierPNMAndGIGYA(String gin) throws JrafDaoException {

		log.debug("START findExternalIdentifierPNMAndGIGYA : " + System.currentTimeMillis());

		List<ExternalIdentifier> results = findExternalIdentifier(gin);

		// Request of GIGYA_ID
		StringBuilder strQuery2 = new StringBuilder();
		strQuery2.append("select ext2 from ExternalIdentifier ext2 ");
		strQuery2.append("left outer join ext2.individu ind2 ");
		strQuery2.append("where ind2.sgin = :gin ");
		strQuery2.append("and ext2.type = :type ");

		Query myquery2 = entityManager.createQuery(strQuery2.toString());
		myquery2.setParameter("gin", gin);
		myquery2.setParameter("type", ExternalIdentifierTypeEnum.GIGYA_ID.toString());

		try {
			// Add the GIGYA_ID
			results.addAll(myquery2.getResultList());

		} catch (NoResultException e) {
			results = null;
		} catch (Exception e) {
			throw new JrafDaoException(e);
		}

		log.debug("END findExternalIdentifierPNMAndGIGYA : " + System.currentTimeMillis());

		return results;
	}

	@SuppressWarnings(value = { "unchecked" })
	public List<ExternalIdentifier> findExternalIdentifierALL(String gin) throws JrafDaoException {

		log.debug("START findExternalIdentifierALL : " + System.currentTimeMillis());

		List<ExternalIdentifier> results = findExternalIdentifier(gin);

		// Request of PNM_ID / GIGYA_ID / FACEBOOK_ID / TWITTER_ID
		StringBuilder strQuery2 = new StringBuilder();
		strQuery2.append("select ext2 from ExternalIdentifier ext2 ");
		strQuery2.append("left outer join ext2.individu ind2 ");
		strQuery2.append("where ind2.sgin = :gin ");
		strQuery2.append("and ext2.type != :type ");

		Query myquery2 = entityManager.createQuery(strQuery2.toString());
		myquery2.setParameter("gin", gin);
		myquery2.setParameter("type", ExternalIdentifierTypeEnum.PNM_ID.toString());

		try {
			// Add the PNM_ID / GIGYA_ID / FACEBOOK_ID / TWITTER_ID
			results.addAll(myquery2.getResultList());

		} catch (NoResultException e) {
			results = null;
		} catch (Exception e) {
			throw new JrafDaoException(e);
		}

		log.debug("END findExternalIdentifierALL : " + System.currentTimeMillis());

		return results;
	}

	public ExternalIdentifier findExternalIdentifier(ExternalIdentifier externalIdentifier) throws JrafDaoException {

		log.debug("START findExternalIdentifier : " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from SIC2.EXTERNAL_IDENTIFIER ");
		strQuery.append("where SGIN=:sgin ");
		strQuery.append("and IDENTIFIER=:identifier ");
		strQuery.append("and TYPE=:type ");

		Query myquery = entityManager.createNativeQuery(strQuery.toString(), ExternalIdentifier.class);
		myquery.setParameter("sgin", externalIdentifier.getIndividu().getSgin());
		myquery.setParameter("identifier", externalIdentifier.getIdentifier());
		myquery.setParameter("type", externalIdentifier.getType());

		ExternalIdentifier result;

		try {
			result = (ExternalIdentifier) myquery.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		} catch (Exception e) {
			throw new JrafDaoException(e);
		}

		log.debug("END findExternalIdentifier : " + System.currentTimeMillis());

		return result;

	}

	// Count a number of ExternalIdentifier linke to an Individual with an OPTIN set
	// to YES
	public Long countExternalIdentifier(String gin, ExternalIdentifierTypeEnum type) throws JrafDaoException {

		log.debug("START countExternalIdentifier : " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select count(ext) from ExternalIdentifier ext ");
		strQuery.append("left outer join ext.individu ind ");
		strQuery.append("left outer join ext.externalIdentifierDataList dat ");
		strQuery.append("where ind.sgin = :gin ");
		strQuery.append("and ext.type = :type ");
		strQuery.append("and (dat.key = :key and dat.value = :value) ");

		Query myquery = entityManager.createQuery(strQuery.toString());
		myquery.setParameter("gin", gin);
		myquery.setParameter("type", type.toString());
		myquery.setParameter("key", ExternalIdentifierDataKeyEnum.OPTIN.toString());
		myquery.setParameter("value", YesNoFlagEnum.YES.toString());

		Long result;

		try {
			result = (Long) myquery.getSingleResult();
		} catch (NoResultException e) {
			result = 0l;
		} catch (Exception e) {
			throw new JrafDaoException(e);
		}

		log.debug("END countExternalIdentifier : " + System.currentTimeMillis());

		return result;

	}

	public ExternalIdentifier existExternalIdentifier(String extId, String type)
			throws JrafDaoException, ExternalIdentifierLinkedToDifferentIndividualException {
		ExternalIdentifier existingExt = null;

		log.debug("START existExternalIdentifierByType: " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();

		strQuery.append("select distinct ext from ExternalIdentifier ext ");
		strQuery.append("where ext.identifier = :identifier ");
		strQuery.append("and ext.type = :type ");

		Query myquery = entityManager.createQuery(strQuery.toString());
		myquery.setParameter("identifier", extId);
		myquery.setParameter("type", type);

		try {
			existingExt = (ExternalIdentifier) myquery.getSingleResult();

		} catch (NoResultException e) {
			existingExt = null;

		} catch (NonUniqueResultException e) {
			// On recoit plus d'un résultat
			// On leve une erreur - 713 INVALID EXTERNAL IDENTIFIER
			throw new ExternalIdentifierLinkedToDifferentIndividualException(extId);

		} catch (Exception e) {
			throw new JrafDaoException(e);
		}

		log.debug("END existExternalIdentifierByGIGYA : " + System.currentTimeMillis());

		return existingExt;
	}

	public String existExternalIdentifierByGIGYA(String gigya)
			throws JrafDaoException, ExternalIdentifierLinkedToDifferentIndividualException {

		String existingGIN = "";

		log.debug("START existExternalIdentifierByGIGYA : " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();

		strQuery.append("select distinct ext.gin from ExternalIdentifier ext ");
		strQuery.append("left outer join ext.externalIdentifierDataList dat ");
		strQuery.append("where ext.identifier = :identifier ");
		strQuery.append("and ext.type = 'GIGYA_ID' ");
		strQuery.append("and dat.key like 'USED_BY_%' ");
		strQuery.append("and dat.value != 'N' ");

		Query myquery = entityManager.createQuery(strQuery.toString());
		myquery.setParameter("identifier", gigya);

		try {
			existingGIN = (String) myquery.getSingleResult();

		} catch (NoResultException e) {
			existingGIN = "";

		} catch (NonUniqueResultException e) {
			// On recoit plus d'un résultat
			// On leve une erreur - 713 INVALID EXTERNAL IDENTIFIER
			throw new ExternalIdentifierLinkedToDifferentIndividualException(gigya);

		} catch (Exception e) {
			throw new JrafDaoException(e);
		}

		log.debug("END existExternalIdentifierByGIGYA : " + System.currentTimeMillis());

		return existingGIN;
	}

	public boolean removeExternalIdentifierNotIn(String gin, String externalIdentifierListToKeep,
			String modificationSignature, String modificationSite) throws JrafDaoException {

		boolean updated = false;

		log.debug("START removeExternalIdentifierNotIn : " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("update EXTERNAL_IDENTIFIER_DATA ");
		strQuery.append("set VALUE = 'N', ");
		strQuery.append("MODIFICATION_DATE = :modificationDate, ");
		strQuery.append("MODIFICATION_SIGNATURE = :modificationSignature, ");
		strQuery.append("MODIFICATION_SITE = :modificationSite ");
		strQuery.append("where KEY like 'USED_BY_%' ");
		strQuery.append("and IDENTIFIER_ID in (");

		strQuery.append("select IDENTIFIER_ID ");
		strQuery.append("from EXTERNAL_IDENTIFIER ");
		strQuery.append("where ");
		strQuery.append("SGIN = :gin ");
		strQuery.append("and IDENTIFIER not in ( :externalIdentifierList ) ");
		strQuery.append("and TYPE='GIGYA_ID' ");

		strQuery.append(")");

		Query myquery = entityManager.createNativeQuery(strQuery.toString(), ExternalIdentifier.class);
		myquery.setParameter("gin", gin);
		myquery.setParameter("externalIdentifierList", externalIdentifierListToKeep);
		myquery.setParameter("modificationDate", new Date());
		myquery.setParameter("modificationSignature", modificationSignature);
		myquery.setParameter("modificationSite", modificationSite);

		try {
			int retour = myquery.executeUpdate();
			updated = (retour > 0);

		} catch (NoResultException e) {
			updated = false;
		} catch (Exception e) {
			throw new JrafDaoException(e);
		}

		log.debug("END removeExternalIdentifierNotIn : " + System.currentTimeMillis());

		return updated;

	}

	@Override
	public int getNumberExternalIdentifierByGin(String gin) throws JrafDaoException {
		log.debug("START getNumberExternalIdentifierByGin : " + System.currentTimeMillis());
		StringBuffer buffer = new StringBuffer("select count(1) from ExternalIdentifier ei ");
		buffer.append("where ei.gin = :pGin ");

		Query query = entityManager.createQuery(buffer.toString());
		query.setParameter("pGin", gin);

		Long temp = (Long) query.getSingleResult();
		int result = temp.intValue();
		log.debug("END getNumberExternalIdentifierByGin : " + System.currentTimeMillis());
		return result;
	}
}
