package com.airfrance.repind.service.individu.internal.ut;

import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.service.individu.internal.PurgeIndividualDS;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
@NotThreadSafe
public class PurgeIndividualDSTest {

	@Autowired
	private PurgeIndividualDS purgeIndividualDs;

	@Test
	public void setNumberDatabaseCPUCoreTest() throws IllegalAccessException, IllegalArgumentException,
	InvocationTargetException, NoSuchMethodException, SecurityException {
		purgeIndividualDs.setNumberDatabaseCPUCore(0);
		Assert.assertEquals(1, purgeIndividualDs.getNumberDatabaseCPUCore());
		purgeIndividualDs.setNumberDatabaseCPUCore(-1);
		Assert.assertEquals(1, purgeIndividualDs.getNumberDatabaseCPUCore());
		purgeIndividualDs.setNumberDatabaseCPUCore(200);
		Assert.assertEquals(
				(int) ReflectionTestUtils.getField(purgeIndividualDs, "MAX_NUMBER_DATABASE_CPU_CORE"),
				purgeIndividualDs.getNumberDatabaseCPUCore());
		purgeIndividualDs.setNumberDatabaseCPUCore(200);
		Assert.assertEquals(
				(int) ReflectionTestUtils.getField(purgeIndividualDs, "MAX_NUMBER_DATABASE_CPU_CORE"),
				purgeIndividualDs.getNumberDatabaseCPUCore());
		purgeIndividualDs.setNumberDatabaseCPUCore(
				(int) ReflectionTestUtils.getField(purgeIndividualDs, "MAX_NUMBER_DATABASE_CPU_CORE") + 1);
	}

	@Test
	public void setNumberDatabasePoolTest() throws IllegalAccessException, IllegalArgumentException,
	InvocationTargetException, NoSuchMethodException, SecurityException {
		purgeIndividualDs.setNumberDatabasePool(0);
		Assert.assertEquals(1, purgeIndividualDs.getNumberDatabasePool());
		purgeIndividualDs.setNumberDatabasePool(-1);
		Assert.assertEquals(1, purgeIndividualDs.getNumberDatabasePool());
		purgeIndividualDs.setNumberDatabasePool(200);
		Assert.assertEquals((int) ReflectionTestUtils.getField(purgeIndividualDs, "MAX_DATABASE_POOL"),
				purgeIndividualDs.getNumberDatabasePool());
		purgeIndividualDs.setNumberDatabasePool(
				(int) ReflectionTestUtils.getField(purgeIndividualDs, "MAX_DATABASE_POOL") + 1);
		Assert.assertEquals((int) ReflectionTestUtils.getField(purgeIndividualDs, "MAX_DATABASE_POOL"),
				purgeIndividualDs.getNumberDatabasePool());
	}
}
