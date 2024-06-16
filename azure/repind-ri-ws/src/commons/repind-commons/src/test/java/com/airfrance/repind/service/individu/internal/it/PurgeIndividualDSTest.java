package com.airfrance.repind.service.individu.internal.it;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.service.individu.internal.PurgeIndividualDS;
import com.airfrance.repind.service.individu.internal.PurgeIndividualDS.BackupWriteFileCallback;
import com.airfrance.repind.service.individu.internal.PurgeIndividualDS.PurgeMode;
import com.airfrance.repind.service.individu.internal.PurgeIndividualResult;
import net.jcip.annotations.NotThreadSafe;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
@NotThreadSafe
class PurgeIndividualDSTest {
	private static final Log logger = LogFactory.getLog(PurgeIndividualDSTest.class);

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	private PurgeIndividualDS purgeIndividualDs;

	@Autowired
	private ApplicationContext context;

	@Test
	@Transactional
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	@Rollback(true)
	//@Disabled
	void includeIndividuSQLValidityTest() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE) VALUES "
							+ "('999999999990', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2200/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'T')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE) VALUES "
							+ "('999999999991', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2200/01/01', 'yyyy/mm/dd'), 'tata', 'QVI', 'tata', 'I')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE) VALUES "
							+ "('999999999992', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2200/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'W')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE) VALUES "
							+ "('999999999993', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2200/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'Y')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE) VALUES "
							+ "('999999999994', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'I')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE) VALUES "
							+ "('999999999995', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'W')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
							"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE) VALUES "
									+ "('999999999996', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'T')")
					.executeUpdate();
		} catch (Exception e) {
		}

		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String formattedDate = currentDate.format(formatter);
		try {
			entityManager.createNativeQuery(
							"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE) VALUES "
									+ "('999999999997', 0, 'MR', 'M', 'O',TO_DATE('2023/09/19', 'yyyy/mm/dd'), 'tata',TO_DATE('2023/09/19', 'yyyy/mm/dd'), 'tata', 'totot', 'BATCH_QVI', 'I')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
							"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE) VALUES "
									+ "('999999999998', 0, 'MR', 'M', 'O',TO_DATE('2023/09/19', 'yyyy/mm/dd'), 'tata',null, 'tata', 'totot', 'BATCH_QVI', 'T')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
							"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE) VALUES "
									+ "('999999999989', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'BATCH_QVI', 'W')")
					.executeUpdate();
		} catch (Exception e) {
		}
		final Method method = PurgeIndividualDS.class.getDeclaredMethod("_includeIndividu", Session.class);
		method.setAccessible(true);
		method.invoke(purgeIndividualDs, ((Session) entityManager.getDelegate()));

		BigDecimal result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '999999999990'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '999999999991'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '999999999992'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '999999999993'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '999999999994'")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '999999999995'")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '999999999996'")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '999999999997'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '999999999998'")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '999999999989'")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());

	}

	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	//@Disabled
	void excludeCommprefSQLValidityTest() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION) VALUES "
							+ "('000000000085', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION) VALUES "
							+ "('000000000086', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata')")
					.executeUpdate();
		} catch (Exception e) {
		}

		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION) VALUES "
							+ "('000000000087', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata')")
					.executeUpdate();
		} catch (Exception e) {
		}

		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION) VALUES "
							+ "('000000000088', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata')")
					.executeUpdate();
		} catch (Exception e) {
		}

		try {
			// Sales optin
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.COMMUNICATION_PREFERENCES (COM_PREF_ID, SGIN, DOMAIN, SUBSCRIBE, MODIFICATION_DATE) VALUES "
							+ "(4,'000000000085', 'S', 'Y', TO_DATE('2017/01/01', 'yyyy/mm/dd'))")
					.executeUpdate();
		} catch (Exception e) {
		}

		try {
			// Sales optout for less than 6 months
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.COMMUNICATION_PREFERENCES (COM_PREF_ID, SGIN, DOMAIN, SUBSCRIBE, MODIFICATION_DATE) VALUES "
							+ "(5,'000000000086', 'S', 'N', TO_DATE('2200/01/01', 'yyyy/mm/dd'))")
					.executeUpdate();
		} catch (Exception e) {
		}

		try {
			// Sales optout for more than 6 months
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.COMMUNICATION_PREFERENCES (COM_PREF_ID, SGIN, DOMAIN, SUBSCRIBE, MODIFICATION_DATE) VALUES "
							+ "(6,'000000000087', 'S', 'N', TO_DATE('2017/01/01', 'yyyy/mm/dd'))")
					.executeUpdate();
		} catch (Exception e) {
		}

		try {
			// Not sales
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.COMMUNICATION_PREFERENCES (COM_PREF_ID, SGIN, DOMAIN, SUBSCRIBE, MODIFICATION_DATE) VALUES "
							+ "(7,'000000000088', 'F', 'Y', TO_DATE('2017/01/01', 'yyyy/mm/dd'))")
					.executeUpdate();
		} catch (Exception e) {
		}

		final Method method = PurgeIndividualDS.class.getDeclaredMethod("_excludeCommpref", Session.class);
		method.setAccessible(true);
		method.invoke(purgeIndividualDs, ((Session) entityManager.getDelegate()));

		BigDecimal result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000085'")
				.getSingleResult();
		Assertions.assertNotEquals(0, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000086'")
				.getSingleResult();
		Assertions.assertNotEquals(0, result.intValue());

		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000087'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000088'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
	}

	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	//@Disabled
	void excludeSondeSQLValidityTest() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		try {
			entityManager.createNativeQuery("INSERT INTO SIC2.SONDE_NEWTEST (SGIN) VALUES ('000000000000')")
					.executeUpdate();
		} catch (Exception e) {
		}
		BigDecimal result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000000'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
		final Method method = PurgeIndividualDS.class.getDeclaredMethod("_excludeSonde", Session.class);
		method.setAccessible(true);
		method.invoke(purgeIndividualDs, ((Session) entityManager.getDelegate()));
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000000'")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
	}

	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	//@Disabled
	void excludeProfilAFSQLValidityTest() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.PROFIL_MERE (ICLE_PRF, SGIN_PM, SGIN_IND, STYPE) VALUES (0, '000000000000' ,'000000000000', 'AF')")
					.executeUpdate();
		} catch (Exception e) {
		}
		BigDecimal result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000000'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());

		final Method method = PurgeIndividualDS.class.getDeclaredMethod("_excludeProfilAF", Session.class);
		method.setAccessible(true);
		method.invoke(purgeIndividualDs, ((Session) entityManager.getDelegate()));

		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000000'")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
	}

	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	//@Disabled
	void excludeBusinessRoleSQLValidityTest() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION) VALUES "
							+ "('000000000031', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION) VALUES "
							+ "('000000000032', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.BUSINESS_ROLE (ICLE_ROLE, SGIN_IND, STYPE) VALUES (0, '000000000031','C')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.BUSINESS_ROLE (ICLE_ROLE, SGIN_IND, STYPE) VALUES (1, '000000000032','X')")
					.executeUpdate();
		} catch (Exception e) {
		}
		BigDecimal result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000031'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000032'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());

		final Method method = PurgeIndividualDS.class.getDeclaredMethod("_excludeBusinessRole", Session.class);
		method.setAccessible(true);
		method.invoke(purgeIndividualDs, ((Session) entityManager.getDelegate()));
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000031'")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000032'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
	}

	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	//@Disabled
	void excludeMembreSQLValidityTest() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.PERS_MORALE (SGIN, IVERSION, SNOM, SSTATUT, STYPE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION) VALUES "
							+ "('000000000001', 0, 'toto', 'A', 'E',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION) VALUES "
							+ "('000000000002', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.MEMBRE (IKEY, SGIN_PM, SGIN_INDIVIDUS ,DDATE_CREATION, SSIGNATURE_CREATION) VALUES (0, '000000000001', '000000000002','01/01/17', 'tata')")
					.executeUpdate();
		} catch (Exception e) {
		}

		final Method method = PurgeIndividualDS.class.getDeclaredMethod("_excludeMembre", Session.class);
		method.setAccessible(true);
		method.invoke(purgeIndividualDs, ((Session) entityManager.getDelegate()));
		BigDecimal result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_EXCLUDE_BR1 where gin = '000000000002'")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
	}

	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	void includeMergedIndividualSQLValidityTest() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			entityManager.createNativeQuery(
							"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE, SSTATUT_INDIVIDU) VALUES "
									+ "('000000000083', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'I', 'T')")
					.executeUpdate();
		} catch (Exception e) {
		}
		try {
			entityManager.createNativeQuery(
							"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE, SSTATUT_INDIVIDU) VALUES "
									+ "('000000000084', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'I', 'V')")
					.executeUpdate();
		} catch (Exception e) {
		}
		final Method method = PurgeIndividualDS.class.getDeclaredMethod("_includeMergedIndividu", Session.class);
		method.setAccessible(true);
		method.invoke(purgeIndividualDs, ((Session) entityManager.getDelegate()));

		BigDecimal result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '000000000083'")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("SELECT count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR1 where gin = '000000000084'")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
	}

	@Transactional
	void deleteTemporaryTable() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = PurgeIndividualDS.class.getDeclaredMethod("_deleteTemporaryTables");
		method.setAccessible(true);
		try {
			method.invoke(purgeIndividualDs);
		} catch (Exception e) {
			PurgeIndividualDSTest.logger.error(ExceptionUtils.getStackTrace(e));
		}
	}

	@Test
	@Transactional
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	@Disabled("Only if necessary - not working")
	void purgeDeleteTest() throws MissingParameterException, InvalidParameterException, NoSuchMethodException,
			SecurityException, InterruptedException, ExecutionException {
		PurgeIndividualResult result = purgeIndividualDs.process();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		c.add(Calendar.MONTH, -24);
		entityManager.createNativeQuery("UPDATE IND_TO_PURGE SET detected_date = :date")
				.setParameter("date", c.getTime()).executeUpdate();
		purgeIndividualDs.setNumberDatabasePool(12);
		purgeIndividualDs.setBatchSize(1000);
		purgeIndividualDs.setMode(PurgeMode.SAFE_DELETE.getMode());
		purgeIndividualDs.setBackupWriteFileCallback(new PrintCallback());
		result = purgeIndividualDs.process();
		Assertions.assertEquals(0, result.getErrorsPhysicalPurge().size());
	}

	private class PrintCallback implements BackupWriteFileCallback {
		@Override
		public void writeBackupFile(String filename, Map<String, String> content) throws Exception {
			for (final Map.Entry<String, String> entry : content.entrySet()) {
				PurgeIndividualDSTest.logger.info(entry.getKey() + " : " + entry.getValue());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	@Test
	@Disabled("Only if necessary - not working")
	void purgeSelectTest() throws Exception {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		PurgeIndividualDSTest.logger.error("initial:" + c.getTimeInMillis());

		ReflectionTestUtils.setField(purgeIndividualDs, "_currentDate", date);
		PurgeIndividualResult result = purgeIndividualDs.process();
		Assertions.assertNotNull(result);
		int toPurgeSize = result.getGinsToPurgeLater().size();
		PurgeIndividualDSTest.logger.info(toPurgeSize);
		/*
		 * recherche de tous les gins non purgeables donc status non F X D, dans le cas
		 * contraire ils sont purgeables
		 */
		try {
			entityManager.createNativeQuery("DROP TABLE sic2.TMP_NON_PURGE_11").executeUpdate();
		} catch (Exception e) {
		}
		entityManager.createNativeQuery(
				"CREATE TABLE sic2.TMP_NON_PURGE_11 TABLESPACE SIC_IND AS select SGIN, STYPE from sic2.INDIVIDUS_ALL where SSTATUT_INDIVIDU IS NULL OR ( SSTATUT_INDIVIDU not in ('X','D'))")
				.executeUpdate();
		/*
		 * Regle tous ceux qui sont T reconnus depuis moins de 2 ans ne sont pas
		 * purgeables, les autres le sont...
		 */
		try {
			entityManager.createNativeQuery("DROP TABLE sic2.TMP_NON_PURGE_12").executeUpdate();
		} catch (Exception e) {
		}

		c.setTime(date);
		c.add(Calendar.MONTH, -24);

		entityManager.createNativeQuery(
				"CREATE TABLE sic2.TMP_NON_PURGE_12 TABLESPACE SIC_IND AS select  distinct ta.SGIN 	from sic2.TMP_NON_PURGE_11 ta inner join sic2.ROLE_TRAVELERS tb on ta.SGIN = tb.SGIN where ta.STYPE = 'T' and tb.DLAST_RECOGNITION_DATE > "
						+ " (timestamp '1970-01-01 00:00:00 GMT' + numtodsinterval(" + c.getTimeInMillis() / 1000
						+ ", 'SECOND')) at time zone 'Europe/Paris'")
				.executeUpdate();

		/* Regle tous ceux qui sont <> T, <> I, <> W ne sont pas purgeables */
		try {
			entityManager.createNativeQuery("DROP TABLE sic2.TMP_NON_PURGE_13").executeUpdate();
		} catch (Exception e) {
		}
		entityManager.createNativeQuery(
				"CREATE TABLE sic2.TMP_NON_PURGE_13 TABLESPACE SIC_IND AS select  distinct SGIN from sic2.TMP_NON_PURGE_11 where STYPE not in ('T', 'I', 'W' )")
				.executeUpdate();
		/*
		 * Regle tous ceux qui sont = I, = W et s'ils sont modifiés il y a moins de 3
		 * mois ils ne sont pas purgeables
		 */
		try {
			entityManager.createNativeQuery("DROP TABLE sic2.TMP_NON_PURGE_141").executeUpdate();
		} catch (Exception e) {
		}

		c.setTime(date);
		c.add(Calendar.MONTH, -3);
		entityManager.createNativeQuery(
				"CREATE TABLE sic2.TMP_NON_PURGE_141 TABLESPACE SIC_IND AS select  distinct ta.SGIN	from sic2.TMP_NON_PURGE_11 ta inner join sic2.INDIVIDUS_ALL tb on ta.SGIN = tb.SGIN where ta.STYPE in ( 'I', 'W' ) AND  tb.DDATE_MODIFICATION > "
						+ "(timestamp '1970-01-01 00:00:00 GMT' + numtodsinterval(" + c.getTimeInMillis() / 1000
						+ ", 'SECOND')) at time zone 'Europe/Paris' AND tb.SSITE_MODIFICATION <> 'BATCH_QVI'")
				.executeUpdate();
		/* Regle tous ceux qui sont = I, = W et membre qui ne sont pas purgeables */
		try {
			entityManager.createNativeQuery("DROP TABLE sic2.TMP_NON_PURGE_142").executeUpdate();
		} catch (Exception e) {
		}
		entityManager.createNativeQuery(
				"CREATE TABLE sic2.TMP_NON_PURGE_142 TABLESPACE SIC_IND AS select  distinct SGIN from sic2.TMP_NON_PURGE_11 inner join sic2.MEMBRE on SGIN = SGIN_INDIVIDUS where STYPE in ( 'I', 'W' )")
				.executeUpdate();
		/*
		 * Regle tous ceux qui sont = I, = W et usage client zzz qui ne sont pas
		 * purgeables
		 */
		try {
			entityManager.createNativeQuery("DROP TABLE sic2.TMP_NON_PURGE_143").executeUpdate();
		} catch (Exception e) {
		}
		/*
		 * Tous ceux qui sont USAGE_CLIENT (SCL, AMF, FBD, FIH, FIS, FBC, IHM, ADM, WEB,
		 * SML, B2C, CCD )
		 */
		entityManager.createNativeQuery(
				"CREATE TABLE sic2.TMP_NON_PURGE_143 TABLESPACE SIC_IND AS select distinct ta.SGIN from sic2.TMP_NON_PURGE_11 ta inner join sic2.USAGE_CLIENTS tb on tb.SGIN = ta.SGIN where ta.STYPE in ( 'I', 'W' ) AND tb.SCODE not in ('SCL', 'AMF', 'FBD', 'FIH', 'FIS', 'FBC', 'IHM', 'ADM', 'WEB', 'SML', 'B2C', 'CCD' )")
				.executeUpdate();
		/*
		 * Regle tous ceux qui sont = I, = W et ayant un profil AF ne sont pas
		 * purgeables
		 */
		try {
			entityManager.createNativeQuery("DROP TABLE sic2.TMP_NON_PURGE_144").executeUpdate();
		} catch (Exception e) {
		}
		entityManager.createNativeQuery(
				"CREATE TABLE sic2.TMP_NON_PURGE_144 TABLESPACE SIC_IND AS select  distinct SGIN from sic2.TMP_NON_PURGE_11 ta inner join sic2.PROFIL_MERE tb on ta.SGIN = tb.SGIN_IND inner join sic2.PROFIL_AF tc on tb.ICLE_PRF = tc.ICLE_PRF where ta.STYPE in ( 'I', 'W' )")
				.executeUpdate();

		/*
		 * Regle tous ceux qui sont = I, = W et n'ayant pas une com pref S updaté à N il
		 * y a moins de 6 mois ne sont pas purgeables
		 */
		try {
			entityManager.createNativeQuery("DROP TABLE sic2.TMP_NON_PURGE_145").executeUpdate();
		} catch (Exception e) {
		}

		c.setTime(date);
		PurgeIndividualDSTest.logger.error("comm pref time:" + c.getTimeInMillis());
		c.add(Calendar.MONTH, -6);
		PurgeIndividualDSTest.logger.error("comm pref time:" + c.getTimeInMillis());
		entityManager.createNativeQuery(
				"CREATE TABLE sic2.TMP_NON_PURGE_145 TABLESPACE SIC_IND AS select SGIN from sic2.TMP_NON_PURGE_11 where STYPE in ( 'I', 'W' ) intersect select SGIN from sic2.COMMUNICATION_PREFERENCES where DOMAIN = 'S' AND (SUBSCRIBE = 'Y' OR (SUBSCRIBE = 'N' and MODIFICATION_DATE > "
						+ "(timestamp '1970-01-01 00:00:00 GMT' + numtodsinterval(" + c.getTimeInMillis() / 1000
						+ ", 'SECOND')) at time zone 'Europe/Paris' ) ) ")
				.executeUpdate();

		/* Tous ceux qui ne sont pas new test */
		try {
			entityManager.createNativeQuery("DROP TABLE sic2.TMP_NON_PURGE_146").executeUpdate();
		} catch (Exception e) {
		}
		entityManager.createNativeQuery(
				"CREATE TABLE sic2.TMP_NON_PURGE_146 TABLESPACE SIC_IND AS select  distinct  ta.SGIN from sic2.TMP_NON_PURGE_11 ta inner join  sic2.SONDE_NEWTEST tb on ta.SGIN = tb.SGIN where STYPE in ( 'I', 'W' )")
				.executeUpdate();
		/*
		 * Regle tous ceux qui sont = I, = W et n'ontpas de contrats ne sont pas
		 * purgeables
		 */
		try {
			entityManager.createNativeQuery("DROP TABLE sic2.TMP_NON_PURGE_147").executeUpdate();
		} catch (Exception e) {
		}
		entityManager.createNativeQuery(
				"CREATE TABLE sic2.TMP_NON_PURGE_147 TABLESPACE SIC_IND AS select distinct ta.SGIN from sic2.TMP_NON_PURGE_11 ta inner join sic2.BUSINESS_ROLE tb on ta.SGIN = tb.sgin_ind where ta.STYPE in ( 'I', 'W' ) and tb.stype in ('C', 'U', 'G', 'A')")
				.executeUpdate();

		/* Les gins non purgeable sont */
		try {
			entityManager.createNativeQuery("DROP TABLE sic2.TMP_NON_PURGE_UNION").executeUpdate();
		} catch (Exception e) {
		}
		entityManager.createNativeQuery(
				"CREATE TABLE sic2.TMP_NON_PURGE_UNION TABLESPACE SIC_IND AS select SGIN from sic2.TMP_NON_PURGE_12	UNION select SGIN from sic2.TMP_NON_PURGE_13 UNION select SGIN from sic2.TMP_NON_PURGE_141 UNION select SGIN from sic2.TMP_NON_PURGE_142 UNION select SGIN from sic2.TMP_NON_PURGE_143 UNION select SGIN from sic2.TMP_NON_PURGE_144 UNION select SGIN from sic2.TMP_NON_PURGE_145 UNION select SGIN from sic2.TMP_NON_PURGE_146 UNION select SGIN from sic2.TMP_NON_PURGE_147")
				.executeUpdate();

		List<String> abnormalityType1 = entityManager.createNativeQuery(
				"select distinct gin from (select SGIN as gin from SIC2.INDIVIDUS_ALL minus select sgin as gin from SIC2.TMP_NON_PURGE_UNION minus select gin from SIC2.TEMPORARY_PURGE_DISTINCT_IND minus select gin from SIC2.TEMPORARY_PURGE_INCLUDE_BR4)")
				.getResultList();
		List<String> abnormalityType2 = entityManager.createNativeQuery(
				"select distinct gin from (select sgin as gin from SIC2.TMP_NON_PURGE_UNION intersect select gin from SIC2.TEMPORARY_PURGE_DISTINCT_IND)")
				.getResultList();
		List<String> abnormalityType3 = entityManager.createNativeQuery(
				"select distinct gin from (select sgin as gin from SIC2.TMP_NON_PURGE_UNION intersect select gin from SIC2.TEMPORARY_PURGE_INCLUDE_BR4)")
				.getResultList();

		PurgeIndividualDSTest.logger.error("abnormalityType1: " + abnormalityType1.size());
		for (String gin : abnormalityType1) {
			PurgeIndividualDSTest.logger.error(gin);
		}
		PurgeIndividualDSTest.logger.error("abnormalityType2: " + abnormalityType2.size());
		for (String gin : abnormalityType2) {
			PurgeIndividualDSTest.logger.error(gin);
		}
		PurgeIndividualDSTest.logger.error("abnormalityType3: " + abnormalityType3.size());
		for (String gin : abnormalityType3) {
			PurgeIndividualDSTest.logger.error(gin);

		}
		Assertions.assertEquals(0, abnormalityType1.size());
		Assertions.assertEquals(0, abnormalityType2.size());
		Assertions.assertEquals(0, abnormalityType3.size());
	}

	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	//@Disabled
	void deleteOldExcludedIndividualFromPurgeTest() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.IND_NOT_TO_PURGE (gin, updated_date, signature) VALUES ('000000000000', TO_DATE('1998/07/09', 'yyyy/mm/dd'), 'bla') ")
					.executeUpdate();
		} catch (Exception e) {
		}
		BigDecimal result = (BigDecimal) entityManager
				.createNativeQuery("select count(1) from SIC2.IND_NOT_TO_PURGE where gin = '000000000000' ")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
		final Method method = PurgeIndividualDS.class.getDeclaredMethod("_deleteOldExcludedIndividualFromPurge");
		method.setAccessible(true);
		ReflectionTestUtils.setField(purgeIndividualDs, "entityManager", entityManager);
		method.invoke(purgeIndividualDs);
		result = (BigDecimal) entityManager
				.createNativeQuery("select count(1) from SIC2.IND_NOT_TO_PURGE where gin = '000000000000' ")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
	}

	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	//@Disabled
	void deleteOldProtectedOCPIndividualFromPurgeTest() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.IND_NOT_TO_PURGE (gin, updated_date, signature) VALUES ('000000000000', TO_DATE('2003/07/09', 'yyyy/mm/dd'), 'PROT_OCP') ")
					.executeUpdate();
		} catch (Exception e) {
		}
		BigDecimal result = (BigDecimal) entityManager
				.createNativeQuery("select count(1) from SIC2.IND_NOT_TO_PURGE where gin = '000000000000' ")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
		final Method method = PurgeIndividualDS.class.getDeclaredMethod("_deleteOldExcludedProtectedByOCPIndividualFromPurge");
		method.setAccessible(true);
		ReflectionTestUtils.setField(purgeIndividualDs, "entityManager", entityManager);
		method.invoke(purgeIndividualDs);
		result = (BigDecimal) entityManager
				.createNativeQuery("select count(1) from SIC2.IND_NOT_TO_PURGE where gin = '000000000000' ")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
	}
	
	@BeforeEach
	@Transactional
	void before() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		purgeIndividualDs = new PurgeIndividualDS();
		ReflectionTestUtils.setField(purgeIndividualDs, "entityManager", entityManager);
		purgeIndividualDs.setContext(context);
		entityManager.createNativeQuery("truncate table SIC2.IND_TO_PURGE").executeUpdate();
		entityManager.createNativeQuery("truncate table SIC2.TEMPORARY_IND_TO_PURGE").executeUpdate();
		deleteTemporaryTable();
	}

	@Test
	@Transactional
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	@Rollback(true)
	//@Disabled
	void includeObsoleteIndividuSQLValidityTest() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE, SSTATUT_INDIVIDU) VALUES "
							+ "('000000000017', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'I', 'D')")
					.executeUpdate();
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE, SSTATUT_INDIVIDU) VALUES "
							+ "('000000000018', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'I', 'V')")
					.executeUpdate();
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE, SSTATUT_INDIVIDU) VALUES "
							+ "('000000000019', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'I', 'X')")
					.executeUpdate();
		} catch (Exception e) {
		}

		final Method method = PurgeIndividualDS.class.getDeclaredMethod("_includeObsoleteIndividu", Session.class);
		method.setAccessible(true);
		method.invoke(purgeIndividualDs, ((Session) entityManager.getDelegate()));

		BigDecimal result = (BigDecimal) entityManager
				.createNativeQuery("select count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR2 where gin = '000000000017' ")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("select count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR2 where gin = '000000000018' ")
				.getSingleResult();
		Assertions.assertEquals(0, result.intValue());
		result = (BigDecimal) entityManager
				.createNativeQuery("select count(1) from SIC2.TEMPORARY_PURGE_INCLUDE_BR2 where gin = '000000000019' ")
				.getSingleResult();
		Assertions.assertEquals(1, result.intValue());
	}

	@Test
	@Transactional
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 3 mins
	@Disabled("Only if necessary - not working")
	void deleteListTest()
			throws InvalidParameterException, MissingParameterException, InterruptedException, ExecutionException {
		ArrayList<String> list = new ArrayList<>();
		list.add("000000000024");
		list.add("000000000006");
		list.add("000000000007");
		list.add("000000000008");
		list.add("000000000022");
		list.add("000000000025");
		list.add("000000000026");
		list.add("000000000027");
		list.add("000000000028");
		list.add("000000000001");
		list.add("000000000002");
		list.add("000000000003");
		list.add("000000000004");
		purgeIndividualDs.setBackupWriteFileCallback(new CallbackBackup());
		Map<List<String>, String> result = purgeIndividualDs.physicalDeletion(list, false);
		for (final Map.Entry<List<String>, String> entry : result.entrySet()) {
			PurgeIndividualDSTest.logger.error(entry.getValue());
		}
		Assertions.assertEquals(0, result.size());
	}

	private class CallbackBackup implements BackupWriteFileCallback {

		@Override
		public void writeBackupFile(String filename, Map<String, String> content) throws Exception {
			for (final Map.Entry<String, String> entry : content.entrySet()) {
				PurgeIndividualDSTest.logger.error(entry.getKey() + " : " + entry.getValue());
			}
			Assertions.assertTrue(content.isEmpty());
		}
	}

	@Test
	//@Disabled
	// Every time you add or remove a table, you need to update this test and the
	// batch
	void checkTablesTest() {
		List<String> knownTables = new ArrayList<String>() {
			{
				add("PM_SBT");
				add("REF_MONNAIE");
				add("REF_DELEGAT");
				add("ROLE_GP_TRC");
				add("REF_ROLE_IND_ZC");
				add("DATEMAJ_LIENZCFIRME");
				add("ROLE_GP");
				add("LANGUES");
				add("REF_NIV_TIER_FB");
				add("REF_SBT");
				add("JOUR_OUV");
				add("TMP_SOC_ID");
				add("REF_TYP_HIERARCHIE");
				add("REF_AUTORIS_MAIL");
				add("IND_SS_TRIBU");
				add("DQCRM_ROLLBACK_LINK");
				add("REF_BDM_LANGUAGES");
				add("REF_REST_RECH_IND");
				add("DATES_EXTRACTION_DWHCOM");
				add("BLOCAGE_ACCES");
				add("REF_MARKET_COUNTRY_LANGUAGE");
				add("REF_LIEN_TYPAGEN_DOM");
				add("CIE_ALLIEE");
				add("FONCTION");
				add("TRACKING_REF_PERMISSIONS");
				add("IND_AV_TRIBU");
				add("METIERS");
				add("REF_PORT");
				add("FORGOTTEN_INDIVIDUAL");
				add("REF_COD_DOC");
				add("MEMBRE");
				add("COMPTEUR_DEFUSION");
				add("DQ_CONSENT_KL_BIO");
				add("PROVINCE");
				add("REF_COD_GSA");
				add("MBR_TEL");
				add("DQ_CONSENT_KL_PERS_SERV");
				add("DQ_INIT_CONSENT");
				add("REF_CIVILITE");
				add("REF_DELEGATION_TYPE");
				add("AGENCE");
				add("DQCRM_SECOND_JOB");
				add("BKP_PERS_MORALE_BIS");
				add("REF_TYP_AGEN");
				add("ROLE_FIRME");
				add("REF_FCTPRO_OWNER");
				add("SMP_AUTO_DISCOVERY_ITEM_");
				add("BKP_PERS_MORALE");
				add("LIEN_GP_TRC2");
				add("REF_PREFERENCE_KEY_TYPE");
				add("INDIVIDUS_ERREUR");
				add("TITRE_CIVILS");
				add("LIEN_GP_TRC");
				add("PREFERENCE_OLD");
				add("ROLE_MEMBRE_BKP");
				add("KLM_CORPORATE");
				add("ADR_TO_NORMALIZE");
				add("REF_COMFORT");
				add("FIRMES_LAX_BKP");
				add("INDIVIDUS_ALL_BKP_TMP");
				add("REF_TYP_LIEN_IND_ZC");
				add("STAR_UF_DROP");
				add("TRACE_MERGE");
				add("INDIVIDUS_ALL_BACKUP");
				add("TMP_FB3_AUTH_WHITELIST");
				add("SERVICE_ADHESION");
				add("TELECOMS");
				add("REF_HANDICAP_TYPE");
				add("TMP_NON_PURGE_13");
				add("REF_STATUT_INDIVIDU");
				add("REF_TYP_REGR");
				add("SOUS_PRODUIT_DROP");
				add("ZONE_DECOUP");
				add("REF_TYP_CHIFFRE");
				add("SMP_AD_DISCOVERED_NODES_");
				add("ZD_ASSOCIEE");
				add("REF_TRAITMNT");
				add("TEMPORARY_PURGE_EXCLUDE_BR2");
				add("SMP_AD_NODES_");
				add("TMP_NON_PURGE_142");
				add("REF_COL_SIC_DROP");
				add("REF_TYP_SEG_F");
				add("REF_MERGE_EXCLUSION_DROP");
				add("REF_CHALANDISE");
				add("REF_TYP_RJT");
				add("PREFERENCE");
				add("PR_QUALITATIF");
				add("ROBOT_IND_D");
				add("REF_TYP_REGROUP_DROP");
				add("PROFIL_MERE");
				add("REF_HANDICAP_TYPE_CODE");
				add("TMP_NON_PURGE_143");
				add("EMAILS");
				add("REF_VILLE_IATA");
				add("PROFIL_FACT");
				add("TMP_NON_PURGE_12");
				add("CONSENT");
				add("DQ_CONSENT_AF_PERS_SERV");
				add("REF_BDM_AIRPORTS_DROP");
				add("TMP_NON_PURGE_11");
				add("DQ_COM_PREF_S_NO_ML");
				add("DQ_CONSENT_KL_PERS_OFR");
				add("ENTREPRISE");
				add("HANDICAP_DATA");
				add("REF_BDM_COUNTRIES");
				add("REF_MERGE_CASE_DROP");
				add("DQCRM_ACTIVE_NFEM");
				add("REF_PREFERENCE_DATA_KEY");
				add("REF_HANDICAP_TYPE_CODE_DATA");
				add("HANDICAP");
				add("RESTRUCT_RA2");
				add("PROFIL_AF");
				add("REF_COMPREF_GROUP");
				add("REF_MERGE_RULE_DROP");
				add("REF_ZONE_VENTE_BKP");
				add("ZZ_BKP_REF_LIST");
				add("TMP_SOCIAL_MEDIA");
				add("ROBOT_IND_V");
				add("REF_PRODUCT_COMPREF_GROUP");
				add("DQ_DUPLICATE_CONSENT");
				add("ACCOUNT_TEMP");
				add("REF_DELEGATION_INFO_DATA_KEY");
				add("REF_COMPREF_DGT");
				add("DQ_DUPLICATE_ML");
				add("REF_COMPREF_GROUP_INFO");
				add("TT_DELETE_CASCADE");
				add("DQ_ACCOUNT_DATA_TO_DELETE");
				add("ROBOT_IND_V_FB_ONLY_ADR_POST");
				add("SMP_REP_EXEC_STAT");
				add("REF_COMPREF_ML");
				add("REF_COMPREF_TYPE");
				add("ROLE_RCS");
				add("ROBOT_IND_V_MA_ONLY");
				add("ROBOT_IND_V_FB_ONLY");
				add("REF_STATUTPM");
				add("ROBOT_IND_V_GP");
				add("CP");
				add("ROBOT_IND_V_ABONNE");
				add("REF_SS_DOMAINE");
				add("DQCRM_ACCENT_FIRSTNAME");
				add("FAQ_QUESTION");
				add("ROBOT_IND_V_TRAVELER");
				add("TMP_WW2RI_SUBSCRIPTION");
				add("DQ_ROLE_CONTRACT_TO_DELETE");
				add("REF_EXT_ID_USED_BY_DROP");
				add("REF_INFERRED_TYPE");
				add("DQ_GIN_SNKL_LINK");
				add("REF_TYP_AGRE");
				add("H_HABILITATION");
				add("INDIVIDUS_ALL");
				add("HAB_PRF_USER");
				add("DQ_DUPLICATE_SNKL");
				add("DOUBLONS_FP");
				add("REF_STATUT_IATA");
				add("TMP_NON_PURGE_UNION");
				add("DQ_DUPLICATE_AF_ML");
				add("REF_TYP_SEG_A");
				add("HAB_APPLI");
				add("REF_TYP_BR_DROP");
				add("TMP_NON_PURGE_145");
				add("REF_TYP_RESEAU");
				add("REF_STYP_CTR");
				add("GROUPE_DOUBLONS");
				add("CI_BAMBOO_DOCKER_IMAGES");
				add("REF_HANDICAP_DATA_KEY");
				add("TMP_NON_PURGE_146");
				add("HAB_IND_ZONE");
				add("TMP_DEL_1660224785451_0");
				add("HAB_USER");
				add("RJT_PM");
				add("REF_PRODUCT");
				add("TMP_DEL_1664365414114_0");
				add("DQ_MGE_SAME_EMAIL_TEST");
				add("RESEAU");
				add("DQCRM_GP_NOT_EXIST");
				add("TMP_NON_PURGE_144");
				add("DQCRM_MARKET_ID_TO_DELETE");
				add("REF_DRINK_WELCOME");
				add("TRIGGER_CHANGE_AUT_MAILING");
				add("REF_PRODUCT_OWNER");
				add("DQCRM_IMPORT_WW2RI_MAIL_MATCH");
				add("PREFILLED_NUMBERS_DATA");
				add("DQCRM_TMP_SNAF");
				add("REF_OWNER");
				add("REF_DEMARCH");
				add("ZZ_REF_LIST");
				add("REF_DETAILS_KEY");
				add("REF_JOUR_OUV");
				add("INDIVIDU_SECOND_FA_ACTIVATED");
				add("DQ_DUPLICATE_SNAF");
				add("REF_CONSENT_DATA_TYPE");
				add("REF_ERREUR");
				add("AUTHORISATION_WOPA");
				add("REF_TYP_EVENT");
				add("CHANNEL_TO_CHECK");
				add("ZC_LOCK");
				add("ROLE_CONTRATS2");
				add("KPI_REFERENCE");
				add("ENV_VAR");
				add("REF_CONSENT_TYPE_DATA_TYPE");
				add("REF_COMPREF_GTYPE");
				add("KPI_DATA");
				add("ACCOUNT_DATA_OLD");
				add("REF_CONSENT_TYPE");
				add("STATUS");
				add("REF_ERREUR_OLD_DROP");
				add("REF_TEL_NORM_DROP");
				add("ALERT");
				add("EMAILS_BCK");
				add("REF_LOCALISA");
				add("ACCOUNT_DATA");
				add("HAB_IHM");
				add("PM_ZONE");
				add("REF_ORIGINE");
				add("LIENSVILLESZV");
				add("GESTION_PM");
				add("PAYS");
				add("REF_NAT_RESO");
				add("INT_CODE_POSTAUX");
				add("DOM_PRO");
				add("H_TYPE_USER");
				add("REF_NAT_LIEN");
				add("LIEN_INT_CP_ZD");
				add("CHIFFRE");
				add("USAGE_CLIENTS");
				add("H_DROITS");
				add("FORMALIZED_ADR");
				add("BUSINESS_ROLE");
				add("PREF_REPAS");
				add("HAB_SESSION");
				add("CODE_POSTAL_Z1Z4_BKP");
				add("PERS_MORALE");
				add("APPLI_ZD_DROP");
				add("CODE_INDUS");
				add("BSP_OTHER_DATA_BKP");
				add("ROLE_AGENCE");
				add("KLM_CORPORATE_REJECTS");
				add("CAT_MED");
				add("REF_NIV_TIER_C");
				add("REF_DOMAINE");
				add("TYPE_CONTRAT_TC_SEQ");
				add("DATES_REPLICATIONS");
				add("TRIGGER_CHANGE_INDIVIDUS");
				add("CARTE_PAI");
				add("PLAN_TABLE");
				add("MEMBRE_RESEAU");
				add("REF_TYP_FIRME");
				add("ACTIVITE");
				add("MBR_ADR");
				add("TRACE_WOPA");
				add("PASSE_DROIT");
				add("LIEN_IND_RI");
				add("REF_ACCOUNT_TYPE");
				add("LIEN_MOD_SSPROD");
				add("REF_CODE_TITRE");
				add("REF_TYP_VENT");
				add("PRODUIT_LIGNE");
				add("EXTERNAL_IDENTIFIER_DATA");
				add("REF_TYP_PM");
				add("REF_SYNONYME");
				add("PRE_JOUR");
				add("REF_SEXE");
				add("REF_ETAT_ROLE_CTR");
				add("LOADING_TYPE_BKP");
				add("ROLE_CONTRATS");
				add("REF_CODE_GDS");
				add("COMMUNICATION_PREFERENCES");
				add("PR_DEMARCHAGE");
				add("PURGE_AGENCES");
				add("Z1Z4_ZV1ZV4_BKP");
				add("MBR_EMAIL");
				add("PROFIL_FIRME");
				add("ADR_LINE_FORMAT");
				add("REF_COD_REGR_DROP");
				add("REF_DELEGATION_INFO_KEY_TYPE");
				add("PR_CTRL_VENTE_BKP");
				add("PROFIL_FINANC");
				add("INDIVIDU_NOTIFICATION");
				add("REF_STA_CONT_SEQ");
				add("REF_FCT_IND_ZC");
				add("PR_CONTENTIEUX");
				add("DELEGATION_DATA_INFO");
				add("REF_TYP_TC_SEQ");
				add("REF_PREFERENCE_TYPE");
				add("REF_BDM_SEATS");
				add("CLIENT_IPC");
				add("IND_ZONE");
				add("DQ_CONSENT_AF_BIO");
				add("REJETS_ADR");
				add("REF_TYP_RECH_PM");
				add("REF_POL_VOYAGE");
				add("CHAINED_ROWS");
				add("SMP_SERVICE_GROUP_DEFN_");
				add("SMP_AD_ADDRESSES_");
				add("ZONE_VENTE");
				add("SMP_SERVICE_ITEM_");
				add("SEGMENTATION");
				add("ZONE");
				add("ZONE_COMM");
				add("SMP_UPDATESERVICES_CALLED_");
				add("DQ_MGE_SAME_NOMPRE_GIN_TEST");
				add("REF_CODE_SOURCE");
				add("Z1Z4_Z5_BKP");
				add("SYNONYME");
				add("QUALIFLYER");
				add("REF_CODE_SUPPORT");
				add("SIT_MARITALES");
				add("REF_TYP_INFRA");
				add("REF_DELEGATION_INFO_TYPE");
				add("REF_APP_ZD_DROP");
				add("DQ_MGE_SAME_NOMPRE_TEST");
				add("REF_COMFORT_PAID");
				add("ETABLISSEMENT");
				add("DQ_MGE_IND_TELECOM_TEST");
				add("PROFIL_BANQ");
				add("SMP_SERVICE_GROUP_ITEM_");
				add("REF_TYP_TERM");
				add("ADR_GIN_MOVERS");
				add("TMP_WW2RI_PROFILE_SUBS");
				add("TYPE_MODULE_DROP");
				add("REF_TYP_SEG");
				add("ACCOUNT_DATA_RB");
				add("REF_BDM_MEALS");
				add("ROBOT_IND_P");
				add("DQCRM_IMPORT_WW2RI");
				add("REF_UNSTRUCTURED_DATA_OWNER");
				add("DQ_CONSENT_AF_PERS_OFR");
				add("ROBOT_IND_X");
				add("REF_FNA");
				add("REF_UNSTRUCTURED_DATA_TYPE");
				add("DQ_GIN_CONSENT_TO_DELETE");
				add("TMP_NON_PURGE_141");
				add("ROBOT_IND_V_FUSIONE");
				add("REF_HANDICAP_CODE");
				add("PREFERENCE_DATA");
				add("REF_TYP_NUMID");
				add("CONSENT_DATA");
				add("REF_CHANNEL_COMMUNICATION");
				add("REF_MEALS");
				add("REF_TYP_MAIL");
				add("REF_CHANNEL_CHECKIN");
				add("DQCRM_ROLLBACK");
				add("TR_EMPLOYE");
				add("REF_TABLE_SIC_DROP");
				add("REF_CULTURAL");
				add("IND_NOT_TO_PURGE");
				add("SMP_AD_PARMS_");
				add("REF_DRINK_BEVERAGES");
				add("RJT_PROBABLE");
				add("REF_TYP_CTR");
				add("ROBOT_IND_T");
				add("REF_BDM_CITIES_DROP");
				add("REF_TYP_ZONE");
				add("ROBOT_IND_NON_FUSION");
				add("RJT_VILLES_GDS");
				add("ROLE_UCCR");
				add("TEMP_OPTOUT_COMPREF");
				add("REF_VILLEISO_BKP");
				add("INDIVIDUS2");
				add("REF_MERGE_LIST_DROP");
				add("REF_READING");
				add("REF_CONSENT_DEFAULT");
				add("REF_TABLE_DROP");
				add("REF_MEALS_PAID");
				add("TRACKING_PERMISSIONS");
				add("REF_SIEGE");
				add("REF_PERMISSIONS");
				add("REF_PERMISSIONS_QUESTION");
				add("REF_VILLES_ANNEXES");
				add("GROUPE");
				add("ROBOT_IND_V_FB_ONLY_TELECOM");
				add("DQ_CONSENT_PARTN_PERS_OFR");
				add("DQ_MGE_DUP_CTRL");
				add("DQCRM_ACCENT_LASTNAME");
				add("H_SS_INTERFACE");
				add("ROBOT_IND_V_FB_ONLY_EMAIL");
				add("TMP_WW2RI_PROFILE");
				add("ALERT_DATA");
				add("HAB_PRF_TYPE");
				add("TMP_WW2RI");
				add("FIDELIO_885");
				add("REF_INFERRED_KEY_TYPE");
				add("ROBOT_IND_V_AMEX");
				add("H_INTERFACE");
				add("REF_SEATS");
				add("REF_ALERT");
				add("REF_INFERRED_STATUS");
				add("DQ_GIN_SNAF_LINK");
				add("REF_COMPREF_MEDIA");
				add("HAB_USER_TYPE");
				add("TMP_TRIGGER_CHANGE_INSERT");
				add("REF_COMPREF_COUNTRY_MARKET");
				add("INFERRED_DATA");
				add("REF_STYP_ZON");
				add("REF_CODE_INVAL_PHONE");
				add("FAQ_REPONSE");
				add("REF_COMPREF");
				add("MARKET_LANGUAGE");
				add("TMP_NON_PURGE_147");
				add("REF_INFERRED_DATA_KEY");
				add("REF_COMPREF_DOMAIN");
				add("UPLOAD");
				add("EMAILS_TEMP_G");
				add("DQ_MGE_SAME_TELECOM_TEST");
				add("SONDE_NEWTEST");
				add("DQCRM_COM_PREF_ID_TO_DELETE");
				add("REF_STA_MED");
				add("REF_NIV_SEG_A");
				add("PID_IP");
				add("BSP_DATA");
				add("REF_STA_JURI");
				add("PAYS_Z1Z4_BKP");
				add("DQCRM_IMPORT_WW2RI_MATCH");
				add("REF_STATUT_PERS");
				add("REF_LETTRE");
				add("DQCRM_IMPORT_WW2RI_CIN_MATCH");
				add("OFFICE_ID");
				add("FCT_PRO");
				add("REF_GRP_APP");
				add("PLANET_MEP_1");
				add("INFERRED");
				add("REF_FCT_METIER_DROP");
				add("DONNEES_FINA_BKP");
				add("REF_TYP_EXT_ID");
				add("TMP_TRIGGER_CHANGE_IND_INSERT");
				add("DELEGATION_DATA");
				add("REF_INT_ID_CONTEXT");
				add("DOCUMENTATION");
				add("EXTERNAL_IDENTIFIER");
				add("DEPRECATED");
				add("NUMERO_IDENT");
				add("DQCRM_GP_EXIST");
				add("DBG_PASSWORD");
				add("NOUVEAUTE");
				add("SOCIAL_NETWORK_DATA");
				add("PREFILLED_NUMBERS");
				add("REF_NIV_SEG");
				add("REF_NIV_SEG_F");
				add("APPLI_FUSION");
				add("AUTHORIZED_EMAIL");
				add("LEXIQUE");
				add("REF_PROVINCE");
				add("TOAD_PLAN_TABLE");
				add("LETTRE_COMPT");
				add("REF_NAT_ZONE");
				add("USAGE_MEDIUMS");
				add("RLOG_ADR_POST");
				add("DOUBLONS");
				add("PM_REGROUP_BKP");
				add("OBJ_SUPP");
				add("DATE_REPLIC");
				add("ADR_POST");
				add("PROFILS");
				add("REF_TRACKING");
				add("REF_COD_REGR");
				add("REF_PCS_SCORE");
				add("REF_PCS_FACTOR");
				add("DQ_MGE_SAME_NOMPRE_CANDIDATES");
				add("DQ_MGE_SAME_NOMPRE");
				add("HISTORIZATION_KPI");
				add("REF_LP_DRINK_BEVERAGES");
				add("REF_LP_MEALS");
				add("TRIGGER_INSERTION");
				add("BATCH_REJECTS");
				add("BATCH_REPORTS");
				add("TRACKING");
				add("TEMPORARY_PURGE_INCLUDE_BR3");
				add("TEMPORARY_PURGE_EXCLUDE_BR3");
				add("ROLE_TRAVELERS");
				add("TEMPORARY_PURGE_DISTINCT_IND");
				add("TEMPORARY_PURGE_EXCLUDE_BR1");
				add("TRIGGER_CHANGE");
				add("TEMPORARY_PURGE_INCLUDE_BR2");
				add("IND_TO_PURGE");
				add("HTE_EXTERNAL_IDENTIFIER");
				add("HTE_EMAILS");
				add("HT_PERS_MORALE_BKP");
				add("HT_ZONE_DECOUP");
				add("HT_PERS_MORALE");
				add("HT_ZONE_DECOUP_BKP");
				add("HTE_REF_TYP_EXT_ID");
				add("HTE_TELECOMS");
				add("HTE_BUSINESS_ROLE");
				add("ZONE_FINANC");
				add("TEMPORARY_PURGE_INCLUDE_BR1");
				add("TEMPORARY_IND_TO_PURGE");
			}
		};

		List<String> ignore = new ArrayList<String>() {
			{
				add("TRACE_MERGE");

			}
		};
		@SuppressWarnings("unchecked")
		List<String> tablesInDB = entityManager
				.createNativeQuery("select table_name from all_tables WHERE OWNER='SIC2'").getResultList();
		for (String knownTable : knownTables) {
			if (!tablesInDB.contains(knownTable)) {
				Assertions.fail(knownTable);
			}
		}
		for (String tableInDB : tablesInDB) {
			if (!knownTables.contains(tableInDB) && !ignore.contains(tableInDB) &&
					!tableInDB.matches("TMP.*")  &&
					!tableInDB.matches("REF_.*")  &&
					// Because of INDIVIDUS_TEMP_G
					!tableInDB.matches(".*_TEMP_.*")  &&
					!tableInDB.matches("DQCRM_.*")) {
				Assertions.fail(tableInDB);
			}
		}
	}
}
