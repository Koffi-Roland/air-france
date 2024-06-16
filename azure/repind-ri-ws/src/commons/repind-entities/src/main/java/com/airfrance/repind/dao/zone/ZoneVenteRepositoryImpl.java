package com.airfrance.repind.dao.zone;

import com.airfrance.repind.entity.zone.ZoneVente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ZoneVenteRepositoryImpl implements ZoneVenteRepositoryCustom {

	private static final String NATIVE_QUERY_LIENSVILLESZV_LIKE = "SELECT SZVALPHA FROM LIENSVILLESZV WHERE SNOM_VILLE LIKE :city AND SCODE_PAYS =:countryCode ORDER BY INBRE DESC";
	private static final String NATIVE_QUERY_LIENSVILLESZV_EXACT = "SELECT SZVALPHA FROM LIENSVILLESZV WHERE SNOM_VILLE =:city AND SCODE_PAYS =:countryCode ORDER BY INBRE DESC";
	private static final String NATIVE_QUERY_HIERARCHY_BASE = "SELECT ZD.IGIN FROM ZONE_DECOUP ZD,ZONE_VENTE ZV WHERE ZV.IGIN=ZD.IGIN AND ZV.IGIN<>:gin AND DDATE_OUVERTURE<=:openDate AND DDATE_FERMETURE IS NULL AND ZV0 =:zv0";
	private static final String NATIVE_QUERY_HIERARCHY_Z0 = " AND ZV1 IS NULL AND ZV2 IS NULL AND ZV3 IS NULL";
	private static final String NATIVE_QUERY_HIERARCHY_Z1 = " AND ZV1=:zv1 AND ZV2 IS NULL AND ZV3 IS NULL";
	private static final String NATIVE_QUERY_HIERARCHY_Z2 = " AND ZV1=:zv1 AND ZV2=:zv2 AND ZV3 IS NULL";
	private static final String NATIVE_QUERY_HIERARCHY_Z3 = " AND ZV1=:zv1 AND ZV2=:zv2 AND ZV3=:zv3";

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public String getZvAlphaForCity(@NotNull final String city, @NotNull final String countryCode) {
		Query query = entityManager.createNativeQuery(NATIVE_QUERY_LIENSVILLESZV_EXACT);
		query.setParameter("city", city);
		query.setParameter("countryCode", countryCode);
		List<String> zvAlphaList = query.getResultList();

		if (zvAlphaList == null || zvAlphaList.isEmpty()) {
			query = entityManager.createNativeQuery(NATIVE_QUERY_LIENSVILLESZV_LIKE);
			query.setParameter("city", city + "%");
			query.setParameter("countryCode", countryCode);
			zvAlphaList = query.getResultList();
		}

		if (zvAlphaList != null && !zvAlphaList.isEmpty()) {
			return zvAlphaList.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getHeirarchyGins(ZoneVente zoneVente) {
		List<Long> gins = new ArrayList<>();
		Integer zv0 = zoneVente.getZv0();
		if (zv0 != null) {
			StringBuilder queryStr = new StringBuilder(NATIVE_QUERY_HIERARCHY_BASE);
			queryStr.append(NATIVE_QUERY_HIERARCHY_Z0);
			Query query = entityManager.createNativeQuery(queryStr.toString());
			setGinQueryParams(zoneVente, query);
			gins.addAll(query.getResultList());

			Integer zv1 = zoneVente.getZv1();
			if (zv1 != null) {
				queryStr = new StringBuilder(NATIVE_QUERY_HIERARCHY_BASE);
				queryStr.append(NATIVE_QUERY_HIERARCHY_Z1);
				query = entityManager.createNativeQuery(queryStr.toString());
				setGinQueryParams(zoneVente, query);
				query.setParameter("zv1", zv1);
				gins.addAll(query.getResultList());
			}

			Integer zv2 = zoneVente.getZv2();
			if (zv2 != null) {
				queryStr = new StringBuilder(NATIVE_QUERY_HIERARCHY_BASE);
				queryStr.append(NATIVE_QUERY_HIERARCHY_Z2);
				query = entityManager.createNativeQuery(queryStr.toString());
				setGinQueryParams(zoneVente, query);
				query.setParameter("zv1", zv1);
				query.setParameter("zv2", zv2);
				gins.addAll(query.getResultList());
			}

			Integer zv3 = zoneVente.getZv3();
			if (zv3 != null) {
				queryStr = new StringBuilder(NATIVE_QUERY_HIERARCHY_BASE);
				queryStr.append(NATIVE_QUERY_HIERARCHY_Z3);
				query = entityManager.createNativeQuery(queryStr.toString());
				setGinQueryParams(zoneVente, query);
				query.setParameter("zv1", zv1);
				query.setParameter("zv2", zv2);
				query.setParameter("zv3", zv3);
				gins.addAll(query.getResultList());
			}

		}
		return gins;
	}

	private void setGinQueryParams(ZoneVente zoneVente, Query query) {
		query.setParameter("gin", zoneVente.getGin());
		query.setParameter("openDate", zoneVente.getDateOuverture(), TemporalType.DATE);
		query.setParameter("zv0", zoneVente.getZv0());
	}

}
