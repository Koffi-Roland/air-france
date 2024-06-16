package com.afklm.rigui.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class MergeStatistiquesRepositoryImpl implements MergeStatistiquesRepositoryCustom {
	@PersistenceContext(unitName = "entityManagerFactorySic")
	EntityManager entityManager;

	@Override
	public List<Object[]> findByGinMergeNotNull() {
		Query query = entityManager.createNativeQuery(
				"Select ind1.SGIN, ind1.SSIGNATURE_MODIFICATION, ind1.DDATE_MODIFICATION, ind1.SSITE_MODIFICATION, ind1.SGIN_FUSION, ind2.SNOM, ind2.SPRENOM from (select SGIN, SGIN_FUSION, SSIGNATURE_MODIFICATION, DDATE_MODIFICATION, SSITE_MODIFICATION from INDIVIDUS_ALL where SGIN_FUSION is not null and DDATE_MODIFICATION >= current_date - 365) ind1 inner join INDIVIDUS_ALL ind2 on ind1.SGIN_FUSION = ind2.SGIN order by DDATE_MODIFICATION DESC");
		//Optimise answer to returned paquet of 1000 instead of 50
		query.setHint("org.hibernate.fetchSize", "1000");
		return query.getResultList();
	}
}
