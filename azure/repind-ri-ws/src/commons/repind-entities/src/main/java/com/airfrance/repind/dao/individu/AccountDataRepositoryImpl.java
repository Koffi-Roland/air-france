package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.individu.AccountData;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AccountDataRepositoryImpl implements AccountDataRepositoryCustom {


	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	public String getMyAccountIdentifier() {
		Integer cycle = 1000000;
		Integer nbcar = 26;
		Query qry = entityManager.createNativeQuery(
				"select SIC2.ISEQ_NUM_MYACCOUNT.NEXTVAL from dual");
		Integer seq = ((BigDecimal) qry.getResultList().get(0)).intValue();
		// Integer alpha_rk = (seq/cycle);
		StringBuffer result = new StringBuffer(StringUtils.leftPad(""
				+ (seq % cycle), 6, '0'));
		result.append((char) (65 + (((seq / cycle) / nbcar) % nbcar)));
		result.append((char) (65 + ((seq / cycle) % nbcar)));
		return result.toString();
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.airfrance.repind.dao.individu.IAccountDataDAO#findFbIdentifierAndGin(com.airfrance.repind.entity.individu.AccountData)
	 */
	public Object[] findFbIdentifierAndGin(AccountData pAccountData) {

		Assert.notNull(pAccountData);
		Assert.isTrue(pAccountData.getAccountIdentifier() != null
				|| pAccountData.getEmailIdentifier() != null
				|| pAccountData.getFbIdentifier() != null
				|| pAccountData.getPersonnalizedIdentifier() != null
				|| pAccountData.getSocialNetworkId() != null);


		StringBuilder jpql =  new StringBuilder(" select acc.fbIdentifier, acc.sgin from AccountData acc ");

		Query query = null;

		if (pAccountData.getAccountIdentifier() != null) {

			jpql.append( " where acc.accountIdentifier = :param ");
			query = entityManager.createQuery(jpql.toString());
			query.setParameter("param", pAccountData.getAccountIdentifier());

		} else if (pAccountData.getEmailIdentifier() != null) {

			jpql.append( " where acc.emailIdentifier = :param ");
			query = entityManager.createQuery(jpql.toString());
			query.setParameter("param", pAccountData.getEmailIdentifier());

		} else if (pAccountData.getFbIdentifier() != null) {

			jpql.append( " where acc.fbIdentifier = :param ");
			query = entityManager.createQuery(jpql.toString());
			query.setParameter("param", pAccountData.getFbIdentifier());

		} else if (pAccountData.getPersonnalizedIdentifier() != null) {

			jpql.append( " where acc.personnalizedIdentifier = :param ");
			query = entityManager.createQuery(jpql.toString());
			query.setParameter("param", pAccountData.getPersonnalizedIdentifier());

		} else {
			jpql.append( " where acc.socialNetworkId = :param ");
			query = entityManager.createQuery(jpql.toString());
			query.setParameter("param", pAccountData.getSocialNetworkId()); 
		}

		@SuppressWarnings("unchecked")
		List<Object[]> results = (List<Object[]>) query.getResultList();
		Assert.notNull(results);

		// REPIND-433 : Si rien n'est trouvé dans Social Network Data, on va chercher dans External Identifier
		if (pAccountData.getSocialNetworkId() != null && results.isEmpty()) {

			// Cas ou il n y a pas de Account_Data
			// StringBuilder jpql2 =  new StringBuilder("select gin, gin from ExternalIdentifier where TYPE='GIGYA_ID' AND IDENTIFIER = :param ");
			StringBuilder jpql2 =  new StringBuilder("select Acc.fbIdentifier, Ei.gin from ExternalIdentifier Ei, AccountData Acc where Ei.gin = Acc.sgin AND Ei.type='GIGYA_ID' AND Ei.identifier = :param ");
			Query query2 = null;
			query2 = entityManager.createQuery(jpql2.toString());
			query2.setParameter("param", pAccountData.getSocialNetworkId());
			@SuppressWarnings("unchecked")
			List<Object[]> results2 = (List<Object[]>) query2.getResultList();

			// Si isEmpty, c est que pas de External Identifier ou alors pas de Compte Account_Data !
			return results2.isEmpty() ? null : results2.get(0);
		} else {
			return results.isEmpty() ? null : results.get(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.airfrance.repind.dao.individu.IAccountDataDAO#findFbIdentifierAndGin(com.airfrance.repind.entity.individu.AccountData)
	 */
	public Object[] findFbIdentifierAndGinV2(AccountData pAccountData) {

		Assert.notNull(pAccountData);
		Assert.isTrue(pAccountData.getAccountIdentifier() != null
				|| pAccountData.getEmailIdentifier() != null
				|| pAccountData.getFbIdentifier() != null
				|| pAccountData.getPersonnalizedIdentifier() != null
				|| pAccountData.getSocialNetworkId() != null);


		StringBuilder jpql =  new StringBuilder(" select acc.fbIdentifier, acc.sgin from AccountData acc ");

		Query query = null;

		if (pAccountData.getAccountIdentifier() != null) {

			jpql.append( " where acc.accountIdentifier = :param ");
			query = entityManager.createQuery(jpql.toString());
			query.setParameter("param", pAccountData.getAccountIdentifier());

		} else if (pAccountData.getEmailIdentifier() != null) {

			jpql.append( " where acc.emailIdentifier = :param ");
			query = entityManager.createQuery(jpql.toString());
			query.setParameter("param", pAccountData.getEmailIdentifier());

		} else if (pAccountData.getFbIdentifier() != null) {

			jpql.append( " where acc.fbIdentifier = :param ");
			query = entityManager.createQuery(jpql.toString());
			query.setParameter("param", pAccountData.getFbIdentifier());

		} else if (pAccountData.getPersonnalizedIdentifier() != null) {

			jpql.append( " where acc.personnalizedIdentifier = :param ");
			query = entityManager.createQuery(jpql.toString());
			query.setParameter("param", pAccountData.getPersonnalizedIdentifier());

		} else {
			jpql.append( " where acc.socialNetworkId = :param ");
			query = entityManager.createQuery(jpql.toString());
			query.setParameter("param", pAccountData.getSocialNetworkId()); 
		}

		@SuppressWarnings("unchecked")
		List<Object[]> results = (List<Object[]>) query.getResultList();
		Assert.notNull(results);

		// REPIND-433 : Si rien n'est trouvé dans Social Network Data, on va chercher dans External Identifier
		if (pAccountData.getSocialNetworkId() != null && results.isEmpty()) {

			// Cas ou il n y a pas de Account_Data
			// StringBuilder jpql2 =  new StringBuilder("select gin, gin from ExternalIdentifier where TYPE='GIGYA_ID' AND IDENTIFIER = :param ");
			// TODO : On a fait sauter le filtre sur le type='GIGYA_ID', de ce fait tous les REF_TYP_EXT_ID sont autorisés 
			StringBuilder jpql2 =  new StringBuilder("select Acc.fbIdentifier, Ei.gin from ExternalIdentifier Ei, AccountData Acc where Ei.gin = Acc.sgin AND Ei.identifier = :param ");
			Query query2 = null;
			query2 = entityManager.createQuery(jpql2.toString());
			query2.setParameter("param", pAccountData.getSocialNetworkId());
			@SuppressWarnings("unchecked")
			List<Object[]> results2 = (List<Object[]>) query2.getResultList();

			// Si isEmpty, c est que pas de External Identifier ou alors pas de Compte Account_Data !
			return results2.isEmpty() ? null : results2.get(0);
		} else {
			return results.isEmpty() ? null : results.get(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.airfrance.repind.dao.individu.IAccountDataDAO#findSimplePropertiesByGin(java.lang.String)
	 */
	public List<Map<String,?>> findSimplePropertiesByGin(String pGin) {

		Assert.notNull(pGin);

		StringBuilder jpql = new StringBuilder(" select new Map( ");
		jpql.append(" accountData.status as status, ");
		jpql.append(" accountData.individu.statutIndividu as individu_statutIndividu, ");
		jpql.append(" accountData.individu.ginFusion as individu_ginFusion, ");
		jpql.append(" accountData.fbIdentifier as fbIdentifier, ");
		jpql.append(" accountData.password as password, ");
		jpql.append(" accountData.temporaryPwd as temporaryPwd, ");
		jpql.append(" accountData.temporaryPwdEndDate as temporaryPwdEndDate, ");
		jpql.append(" accountData.nbFailureAuthentification as nbFailureAuthentification, ");
		jpql.append(" accountData.accountLockedDate as accountLockedDate, ");
		jpql.append(" accountData.passwordToChange as passwordToChange, ");
		jpql.append(" accountData.individu.type as type ");			// Add individual type
		jpql.append(" ) from AccountData accountData left join accountData.individu where accountData.sgin = :param ");

		Query query = entityManager.createQuery(jpql.toString());
		query.setParameter("param", pGin);

		@SuppressWarnings("unchecked")
		List<Map<String,?>> results = (List<Map<String,?>>) query.getResultList();

		return results;
		//return results.isEmpty() ? null : results.get(0);
	}

	
}
