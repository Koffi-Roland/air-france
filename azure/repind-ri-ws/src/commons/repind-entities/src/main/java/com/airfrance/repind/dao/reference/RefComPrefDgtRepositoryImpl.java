package com.airfrance.repind.dao.reference;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RefComPrefDgtRepositoryImpl implements RefComPrefDgtRepositoryCustom {

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	private static final String NATIVE_QUERY_COUNT_DOMAIN_GROUP_TYPE = "SELECT count(1) FROM REF_COMPREF_DGT WHERE DOMAIN =:domain AND GROUP_TYPE =:groupType AND TYPE =:type";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int countByDomainGroupAndType(@NotNull String domain, @NotNull String groupType, @NotNull String type) {
		Query query = entityManager.createNativeQuery(NATIVE_QUERY_COUNT_DOMAIN_GROUP_TYPE);
		query.setParameter("domain", domain);
		query.setParameter("groupType", groupType);
		query.setParameter("type", type);
		return ((BigDecimal) query.getSingleResult()).intValue();
	}
}
