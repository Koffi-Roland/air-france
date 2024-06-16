package com.airfrance.repind.dao.zone;

import com.airfrance.repind.entity.zone.LienIntCpZd;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.entity.zone.ZoneVente;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class LienIntCpZdRepositoryImpl implements LienIntCpZdRepositoryCustom {

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	private static final String NATIVE_QUERY_SELECT_USAGE = "SELECT cpzd.* FROM LIEN_INT_CP_ZD cpzd WHERE cpzd.SUSAGE =:usage";
	private static final String NATIVE_QUERY_SELECT_USAGE_COMMERCIAL_ZONE = "SELECT cpzd.* FROM LIEN_INT_CP_ZD cpzd , ZONE_DECOUP zd, ZONE_COMM zc WHERE cpzd.SUSAGE=:usage "
			+ "AND zd.IGIN = zc.IGIN AND cpzd.IGIN_ZONE = zd.IGIN ";
	private static final String NATIVE_QUERY_SELECT_USAGE_COMMERCIAL_ZONE_GIN = "SELECT cpzd.* FROM LIEN_INT_CP_ZD cpzd WHERE cpzd.SUSAGE=:usage AND cpzd.IGIN_ZONE =:igin ";
	private static final String CLAUSE_COUNTRY = " cpzd.SCODE_PAYS =:countryCode ";
	private static final String CLAUSE_CITY = " cpzd.SCODE_VILLE =:cityCode";
	private static final String CLAUSE_ZC1 = " zc.SZC1=:zc1 ";
	private static final String CLAUSE_ZC2 = " zc.SZC1=:zc1 AND zc.SZC2=:zc2";
	private static final String CLAUSE_ZC3 = " zc.SZC1=:zc1 AND zc.SZC2=:zc2 AND zc.SZC3=:zc3";
	private static final String CLAUSE_ZC4 = " zc.SZC1=:zc1 AND zc.SZC2=:zc2 AND zc.SZC3=:zc3 AND zc.SZC4=:zc4";
	private static final String CLAUSE_ZC5 = " zc.SZC1=:zc1 AND zc.SZC2=:zc2 AND zc.SZC3=:zc3 AND zc.SZC4=:zc4 AND zc.SZC5=:zc5";
	private static final String CLAUSE_AND = " AND";
	private static final String CLAUSE_ACTIVE = " AND (cpzd.DDATE_FIN_LIEN IS NULL OR cpzd.DDATE_DEB_LIEN <= SYSDATE) AND (cpzd.DDATE_FIN_LIEN IS NULL OR cpzd.DDATE_FIN_LIEN >= SYSDATE) ";
	private static final String NATIVE_QUERY_SELECT_USAGE_SALES_ZONE = "SELECT cpzd.* FROM LIEN_INT_CP_ZD cpzd , ZONE_DECOUP zd, ZONE_VENTE zv WHERE cpzd.SUSAGE=:usage "
			+ "AND zd.IGIN = zv.IGIN AND cpzd.IGIN_ZONE = zd.IGIN ";
	private static final String NATIVE_QUERY_SELECT_USAGE_SALES_ZONE_GIN = "SELECT cpzd.* FROM LIEN_INT_CP_ZD cpzd WHERE cpzd.SUSAGE=:usage AND cpzd.IGIN_ZONE =:igin ";
	private static final String CLAUSE_ZV0 = " zv.ZV0=:zv0 ";
	private static final String CLAUSE_ZV1 = " zv.ZV0=:zv0 AND zv.ZV1=:zv1";
	private static final String CLAUSE_ZV2 = " zv.ZV0=:zv0 AND zv.ZV1=:zv1 AND zv.ZV2=:zv2";
	private static final String CLAUSE_ZV3 = " zv.ZV0=:zv0 AND zv.ZV1=:zv1 AND zv.ZV2=:zv2 AND zv.ZV3=:zv3";

	@Override
	@SuppressWarnings("unchecked")
	public List<LienIntCpZd> findByUsageCountryCommercialZone(final String countryCode, final ZoneComm commercialZone,
			@NotNull final String usage) {
		String queryStr = createCountryCommercialZoneQuery(countryCode, commercialZone, false);
		Query query = entityManager.createNativeQuery(queryStr, LienIntCpZd.class);
		setCountryCommercialZoneParams(query, countryCode, commercialZone, usage);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LienIntCpZd> findActiveByUsageCountryCommercialZone(final String countryCode,
			final ZoneComm commercialZone, @NotNull final String usage) {
		String queryStr = createCountryCommercialZoneQuery(countryCode, commercialZone, true);
		Query query = entityManager.createNativeQuery(queryStr, LienIntCpZd.class);
		setCountryCommercialZoneParams(query, countryCode, commercialZone, usage);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LienIntCpZd> findByUsageCountrySalesZone(String countryCode, ZoneVente salesZone,
			@NotNull String usage) {
		String queryStr = createCountrySalesZoneQuery(countryCode, salesZone, false);
		Query query = entityManager.createNativeQuery(queryStr, LienIntCpZd.class);
		setCountrySalesZoneParams(query, countryCode, salesZone, usage);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LienIntCpZd> findActiveByUsageCountrySalesZone(String countryCode, ZoneVente salesZone,
			@NotNull String usage) {
		String queryStr = createCountrySalesZoneQuery(countryCode, salesZone, true);
		Query query = entityManager.createNativeQuery(queryStr, LienIntCpZd.class);
		setCountrySalesZoneParams(query, countryCode, salesZone, usage);
		return query.getResultList();
	}

	@Override
	public List<LienIntCpZd> findByCityCode(String cityCode,String usage) {

		String queryStr = createCityCodeQuery(cityCode);
		Query query = entityManager.createNativeQuery(queryStr, LienIntCpZd.class);
		setCityCodeParams(query,cityCode ,usage);

		return query.getResultList();
	}

	private String createCityCodeQuery(final String cityCode) {
		StringBuilder queryStr = new StringBuilder();
		queryStr.append(NATIVE_QUERY_SELECT_USAGE);
		queryStr.append(CLAUSE_AND).append(CLAUSE_CITY);

		return queryStr.toString();
	}

	private void setCityCodeParams(Query query, String cityCode,String usage) {
		query.setParameter("usage", usage);
		query.setParameter("cityCode", cityCode);
	}

	private String createCountryCommercialZoneQuery(final String countryCode, final ZoneComm commercialZone,
			boolean activeOnly) {
		StringBuilder queryStr = new StringBuilder();
		if (commercialZone != null) {
			if (commercialZone.getGin() != null) {
				queryStr.append(NATIVE_QUERY_SELECT_USAGE_COMMERCIAL_ZONE_GIN);
			} else {
				queryStr.append(NATIVE_QUERY_SELECT_USAGE_COMMERCIAL_ZONE);
				if (StringUtils.isNotEmpty(commercialZone.getZc5())) {
					queryStr.append(CLAUSE_AND).append(CLAUSE_ZC5);
				} else if (StringUtils.isNotEmpty(commercialZone.getZc4())) {
					queryStr.append(CLAUSE_AND).append(CLAUSE_ZC4);
				} else if (StringUtils.isNotEmpty(commercialZone.getZc3())) {
					queryStr.append(CLAUSE_AND).append(CLAUSE_ZC3);
				} else if (StringUtils.isNotEmpty(commercialZone.getZc2())) {
					queryStr.append(CLAUSE_AND).append(CLAUSE_ZC2);
				} else if (StringUtils.isNotEmpty(commercialZone.getZc1())) {
					queryStr.append(CLAUSE_AND).append(CLAUSE_ZC1);
				}
			}
		} else {
			queryStr.append(NATIVE_QUERY_SELECT_USAGE);
		}

		if (StringUtils.isNotEmpty(countryCode)) {
			queryStr.append(CLAUSE_AND).append(CLAUSE_COUNTRY);
		}

		if (activeOnly) {
			queryStr.append(CLAUSE_ACTIVE);
		}

		return queryStr.toString();
	}

	private void setCountryCommercialZoneParams(Query query, String countryCode, ZoneComm commercialZone,
			String usage) {
		query.setParameter("usage", usage);
		if (StringUtils.isNotEmpty(countryCode)) {
			query.setParameter("countryCode", countryCode);
		}
		if (commercialZone != null) {
			Long gin = commercialZone.getGin();
			if (gin != null) {
				query.setParameter("igin", gin);
			} else {
				String zc5 = commercialZone.getZc5();
				String zc4 = commercialZone.getZc4();
				String zc3 = commercialZone.getZc3();
				String zc2 = commercialZone.getZc2();
				String zc1 = commercialZone.getZc1();
				if (StringUtils.isNotEmpty(zc1)) {
					query.setParameter("zc1", zc1);
				}
				if (StringUtils.isNotEmpty(zc2)) {
					query.setParameter("zc2", zc2);
				}
				if (StringUtils.isNotEmpty(zc3)) {
					query.setParameter("zc3", zc3);
				}
				if (StringUtils.isNotEmpty(zc4)) {
					query.setParameter("zc4", zc4);
				}
				if (StringUtils.isNotEmpty(zc5)) {
					query.setParameter("zc5", zc5);
				}
			}
		}
	}

	private String createCountrySalesZoneQuery(final String countryCode, final ZoneVente salesZone,
			boolean activeOnly) {
		StringBuilder queryStr = new StringBuilder();
		if (salesZone != null) {
			if (salesZone.getGin() != null) {
				queryStr.append(NATIVE_QUERY_SELECT_USAGE_SALES_ZONE_GIN);
			} else {
				queryStr.append(NATIVE_QUERY_SELECT_USAGE_SALES_ZONE);
				if (salesZone.getZv3() != null) {
					queryStr.append(CLAUSE_AND).append(CLAUSE_ZV3);
				} else if (salesZone.getZv2() != null) {
					queryStr.append(CLAUSE_AND).append(CLAUSE_ZV2);
				} else if (salesZone.getZv1() != null) {
					queryStr.append(CLAUSE_AND).append(CLAUSE_ZV1);
				} else if (salesZone.getZv0() != null) {
					queryStr.append(CLAUSE_AND).append(CLAUSE_ZV0);
				}
			}
		} else {
			queryStr.append(NATIVE_QUERY_SELECT_USAGE);
		}

		if (StringUtils.isNotEmpty(countryCode)) {
			queryStr.append(CLAUSE_AND).append(CLAUSE_COUNTRY);
		}

		if (activeOnly) {
			queryStr.append(CLAUSE_ACTIVE);
		}

		return queryStr.toString();
	}

	private void setCountrySalesZoneParams(Query query, String countryCode, ZoneVente salesZones, String usage) {
		query.setParameter("usage", usage);
		if (StringUtils.isNotEmpty(countryCode)) {
			query.setParameter("countryCode", countryCode);
		}
		if (salesZones != null) {
			Long gin = salesZones.getGin();
			if (gin != null) {
				query.setParameter("igin", gin);
			} else {
				Integer zv3 = salesZones.getZv3();
				Integer zv2 = salesZones.getZv2();
				Integer zv1 = salesZones.getZv1();
				Integer zv0 = salesZones.getZv0();

				if (zv3 != null) {
					query.setParameter("zv3", zv3);
				}
				if (zv2 != null) {
					query.setParameter("zv2", zv2);
				}
				if (zv1 != null) {
					query.setParameter("zv1", zv1);
				}
				if (zv0 != null) {
					query.setParameter("zv0", zv0);
				}
			}
		}
	}

	@Override
	public List<LienIntCpZd> findCityWithClosedZv3ByDate(LocalDate closeDate) {
		StringBuilder queryStr = new StringBuilder();

		queryStr.append("SELECT cpzd.* " + 
				"FROM LIEN_INT_CP_ZD cpzd " + 
				"INNER JOIN ZONE_DECOUP zd ON zd.IGIN = cpzd.IGIN_ZONE " + 
				"INNER JOIN ZONE_VENTE zv ON zv.IGIN = cpzd.IGIN_ZONE " + 
				"WHERE cpzd.DDATE_DEB_LIEN <= :closedate AND (cpzd.DDATE_FIN_LIEN IS NULL OR cpzd.DDATE_FIN_LIEN >= :closedate) AND zd.DDATE_FERMETURE <= :closedate");

		Query query = entityManager.createNativeQuery(queryStr.toString(), LienIntCpZd.class);
		query.setParameter("closedate", closeDate);

		return query.getResultList();
	}

	@Override
	public List<LienIntCpZd> findAllCityByZV3(int zv0, int zv1, int zv2, int zv3) {
		StringBuilder queryStr = new StringBuilder();

		queryStr.append("SELECT * " + 
				"FROM LIEN_INT_CP_ZD " + 
				"WHERE SCODE_VILLE IN ( " + 
				"    SELECT SCODE_VILLE " + 
				"    FROM LIEN_INT_CP_ZD cpzd " + 
				"    INNER JOIN ZONE_VENTE zv ON zv.IGIN = cpzd.IGIN_ZONE " + 
				"    WHERE (cpzd.DDATE_DEB_LIEN IS NULL OR TRUNC(cpzd.DDATE_DEB_LIEN) <= TRUNC(SYSDATE)) " +
				"    AND (cpzd.DDATE_FIN_LIEN IS NULL OR TRUNC(cpzd.DDATE_FIN_LIEN) >= TRUNC(SYSDATE)) " + 
				"    AND zv.ZV0 = :zv0 AND zv.ZV1 = :zv1 AND zv.ZV2 = :zv2 AND zv.ZV3 = :zv3 " + 
				") " + 
				"AND (DDATE_FIN_LIEN IS NULL OR TRUNC(DDATE_FIN_LIEN) >= TRUNC(SYSDATE))");

		Query query = entityManager.createNativeQuery(queryStr.toString(), LienIntCpZd.class);
		query.setParameter("zv0", zv0);
		query.setParameter("zv1", zv1);
		query.setParameter("zv2", zv2);
		query.setParameter("zv3", zv3);

		return query.getResultList();
	}

	@Override
	public List<LienIntCpZd> findAllZv3ByCities(List<String> cities) {
		StringBuilder queryStr = new StringBuilder();

		queryStr.append("SELECT * " + 
				"FROM LIEN_INT_CP_ZD " + 
				"WHERE SCODE_VILLE IN :cities " +
				"AND (DDATE_FIN_LIEN IS NULL OR TRUNC(DDATE_FIN_LIEN) >= TRUNC(SYSDATE))");

		Query query = entityManager.createNativeQuery(queryStr.toString(), LienIntCpZd.class);
		query.setParameter("cities", cities);

		return query.getResultList();
	}

}
