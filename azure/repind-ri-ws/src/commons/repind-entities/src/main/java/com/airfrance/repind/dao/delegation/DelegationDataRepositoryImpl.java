package com.airfrance.repind.dao.delegation;

import com.airfrance.ref.type.DelegationActionEnum;
import com.airfrance.repind.entity.delegation.DelegationData;
import com.airfrance.repind.entity.individu.Individu;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class DelegationDataRepositoryImpl implements DelegationDataRepositoryCustom {

	private static final Log log = LogFactory.getLog(DelegationDataRepositoryImpl.class);

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<DelegationData> findDelegator(String gin) {

		log.debug("START findDelegator : " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select del from DelegationData del ");
		strQuery.append("left outer join del.delegate ind ");
		strQuery.append("where ind.sgin = :sgin ");
		strQuery.append("and (del.status = :sa or del.status = :si) ");

		Query myquery = entityManager.createQuery(strQuery.toString());
		myquery.setParameter("sgin", gin);
		myquery.setParameter("sa", DelegationActionEnum.ACCEPTED.toString());
		myquery.setParameter("si", DelegationActionEnum.INVITED.toString());
		List<DelegationData> results = myquery.getResultList();

		log.debug("END findDelegator : " + System.currentTimeMillis());

		return results;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DelegationData> findDelegate(String gin) {

		log.debug("START findDelegate : " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select del from DelegationData del ");
		strQuery.append("left outer join del.delegator ind ");
		strQuery.append("where ind.sgin = :sgin ");
		strQuery.append("and (del.status = :sa or del.status = :si) ");

		Query myquery = entityManager.createQuery(strQuery.toString());
		myquery.setParameter("sgin", gin);
		myquery.setParameter("sa", DelegationActionEnum.ACCEPTED.toString());
		myquery.setParameter("si", DelegationActionEnum.INVITED.toString());

		List<DelegationData> results = myquery.getResultList();
		log.debug("END findDelegate : " + System.currentTimeMillis());
		return results;
	}

	@Override
	public DelegationData findDelegation(DelegationData delegation) {

		log.debug("START findDelegation : " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from SIC2.DELEGATION_DATA ");
		strQuery.append("where STYPE=:type ");
		strQuery.append("and SGIN_DELEGATOR=:delegatorGin ");
		strQuery.append("and SGIN_DELEGATE=:delegateGin ");
		strQuery.append("and STATUS!=:statusC ");
		strQuery.append("and STATUS!=:statusR ");
		strQuery.append("and STATUS!=:statusD ");

		Query myquery = entityManager.createNativeQuery(strQuery.toString(), DelegationData.class);
		myquery.setParameter("delegatorGin", delegation.getDelegator().getSgin());
		myquery.setParameter("delegateGin", delegation.getDelegate().getSgin());
		myquery.setParameter("type", delegation.getType());
		myquery.setParameter("statusD", DelegationActionEnum.DELETED.toString());
		myquery.setParameter("statusR", DelegationActionEnum.REJECTED.toString());
		myquery.setParameter("statusC", DelegationActionEnum.CANCELLED.toString());

		DelegationData result;

		try {
			result = (DelegationData) myquery.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}

		log.debug("END findDelegation : " + System.currentTimeMillis());

		return result;
	}

	/**
	 * Return if the type for delegation is existing or not
	 *
	 * @param type
	 * @return
	 */
	@Override
	public boolean isDelegationTypeValid(String type) {

		log.debug("START isDelegationTypeValid : " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select * from SIC2.REF_DELEGATION_TYPE ");
		strQuery.append("where SCODE=:type ");

		Query myquery = entityManager.createNativeQuery(strQuery.toString());
		myquery.setParameter("type", type);

		try {
			myquery.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}

		log.debug("END isDelegationTypeValid : " + System.currentTimeMillis());

		return true;
	}

	@Override
	public int getDelegateNumberByGin(Individu i) {

		log.debug("START getDelegateNumberByGin : " + System.currentTimeMillis());

		StringBuffer buffer = new StringBuffer("select count(1) from DelegationData d ");
		buffer.append("where d.delegate = :pIndividu ");

		Query query = entityManager.createQuery(buffer.toString());
		query.setParameter("pIndividu", i);

		Long temp = (Long) query.getSingleResult();
		int result = temp.intValue();

		log.debug("END getDelegateNumberByGin : " + System.currentTimeMillis());

		return result;

	}

	@Override
	public int getDelegatorNumberByGin(Individu i) {

		log.debug("START getDelegatorNumberByGin : " + System.currentTimeMillis());

		StringBuffer buffer = new StringBuffer("select count(1) from DelegationData d ");
		buffer.append("where d.delegator = :pIndividu ");

		Query query = entityManager.createQuery(buffer.toString());
		query.setParameter("pIndividu", i);

		Long temp = (Long) query.getSingleResult();
		int result = temp.intValue();

		log.debug("END getDelegatorNumberByGin : " + System.currentTimeMillis());

		return result;
	}
}
