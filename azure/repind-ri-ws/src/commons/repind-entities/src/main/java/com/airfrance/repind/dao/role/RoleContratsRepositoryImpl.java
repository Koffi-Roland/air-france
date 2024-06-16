package com.airfrance.repind.dao.role;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import java.util.List;


public class RoleContratsRepositoryImpl implements RoleContratsRepositoryCustom {

	private static final Log log = LogFactory.getLog(RoleContratsRepositoryImpl.class);
	
	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	public int getNumberFPContractsOrOthersByGin(String gin, String contract) throws JrafDaoException {
		log.debug("START getNumberFPContractsOrOthersByGin : " + System.currentTimeMillis());

		StringBuffer buffer = new StringBuffer("select count(1) from RoleContrats rc ");
		buffer.append("where rc.gin = :pGin ");
		buffer.append("and rc.etat not in ('I', 'A') ");
		if (contract.equals("FP")) {
			buffer.append("and rc.typeContrat = 'FP' ");
		} else {
			buffer.append("and rc.typeContrat != 'FP' ");
		}

		Query query = entityManager.createQuery(buffer.toString());
		query.setParameter("pGin", gin);

		Long result = (Long) query.getSingleResult();

		log.debug("END getNumberFPContractsOrOthersByGin : " + System.currentTimeMillis());

		return result.intValue();
	}
	
	public boolean isFlyingBlueOrMyAccountByGin(String gin, String contract) throws JrafDaoException {
		log.debug("START isFlyingBlueOrMyAccountByGin : " + System.currentTimeMillis());

		StringBuffer buffer = new StringBuffer("select count(1) from RoleContrats rc ");
		buffer.append("where rc.gin = :pGin ");
		buffer.append("and rc.etat not in ('X', 'A') ");
		buffer.append("and rc.typeContrat = :pContract ");

		Query query = entityManager.createQuery(buffer.toString());
		query.setParameter("pGin", gin);
		query.setParameter("pContract", contract);

		Long temp = (Long) query.getSingleResult();
		int result = temp.intValue();

		log.debug("END isFlyingBlueOrMyAccountByGin : " + System.currentTimeMillis());

		return (result != 0); // FB or MA if != 0

	}

	/**
	 * Check existing association between a GIN and a CIN (FB or MA) 
	 * 
	 * @param gin 
	 * @param cin contract number (requires 12 digits for FB and 8 chars for MA)
	 * @return boolean result of the matching association
	 *
	 */
	public boolean isGinAndCinFbMaExist(String gin, String cin) {
		log.debug("START isGinAndCinFbMaExist : " + System.currentTimeMillis());

		StringBuffer buffer = new StringBuffer("select count(1) from RoleContrats rc ");
		buffer.append("where rc.gin = :pGin ");
		buffer.append("and rc.numeroContrat = :pCin ");

		Query query = entityManager.createQuery(buffer.toString());
		query.setParameter("pGin", gin);
		query.setParameter("pCin", cin);

		Long temp = (Long) query.getSingleResult();
		int result = temp.intValue();

		log.debug("END isGinAndCinFbMaExist : " + System.currentTimeMillis());

		return (result != 0); 
	}

	public boolean isUniqueGinAndCinAndEmailExist(String gin, String cin, String email) {
		log.debug("START isUniqueGinAndCinAndEmailExist : " + System.currentTimeMillis());

		StringBuffer buffer = new StringBuffer("select count(1) from RoleContrats rc, Email e ");
		buffer.append("where rc.gin = :pGin ");
		buffer.append("and rc.numeroContrat = :pCin ");
		buffer.append("and rc.gin = e.sgin ");
		buffer.append("and e.email = :pEmail ");

		Query query = entityManager.createQuery(buffer.toString());
		query.setParameter("pGin", gin);
		query.setParameter("pCin", cin);
		query.setParameter("pEmail", email);

		Long temp = (Long) query.getSingleResult();
		int result = temp.intValue();

		log.debug("END isUniqueGinAndCinAndEmailExist : " + System.currentTimeMillis());

		return (result == 1);
	}

	/**
	 * 
	 * @param contractType
	 * @return The list of individual with valid and active FB contract and don't
	 *         have an account data in ACCOUNT_DATA table
	 */
	public List<Tuple> findRoleContratsByTypeWithoutAccountData(@Param("contractType") String contractType) {
		log.debug("START findRoleContratsByTypeWithoutAccountData : " + System.currentTimeMillis());

		StringBuilder queryStr = new StringBuilder("SELECT i.sgin, rc.numeroContrat ");
		queryStr.append("FROM RoleContrats rc, Individu i ");
		queryStr.append("LEFT JOIN AccountData ad ON i.sgin = ad.sgin ");
		queryStr.append("WHERE ad.sgin IS NULL ");
		queryStr.append("AND i.sgin = rc.gin ");
		queryStr.append("AND rc.typeContrat = :contractType ");
		queryStr.append("AND rc.dateDebutValidite < SYSDATE ");
		queryStr.append("AND (rc.dateFinValidite IS NULL OR rc.dateFinValidite > SYSDATE) ");
		queryStr.append("AND rc.etat IN ('C', 'P') ");
		queryStr.append("AND i.statutIndividu in ('P', 'V') ");
		//queryStr.append("AND ROWNUM < 5");

		log.debug("END findRoleContratsByTypeWithoutAccountData : " + System.currentTimeMillis());

		TypedQuery<Tuple> query = entityManager.createQuery(queryStr.toString(), Tuple.class);
		query.setParameter("contractType", contractType);
		return query.getResultList();
	}
}
