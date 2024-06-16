package com.afklm.batch.individu.purge.ut;

import com.afklm.batch.individu.purge.BatchPurgeIndividual;
import com.airfrance.common.batch.config.WebConfigTestBatch;
import com.airfrance.repind.service.individu.internal.PurgeIndividualDS;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.zip.GZIPInputStream;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebConfigTestBatch.class)
@Transactional(value = "transactionManagerRepind")
public class BatchPurgeIndividualTest {

	@Autowired
	private PurgeIndividualDS purgeIndividualDs;

	@Test
	public void compressTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException, IOException {
		BatchPurgeIndividual batch = new BatchPurgeIndividual();
		final Method method = BatchPurgeIndividual.class.getDeclaredMethod("_compress", String.class);
		method.setAccessible(true);
		byte[] gzipResult = (byte[]) method.invoke(batch, "bonjour");
		GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(gzipResult));
		BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		String outStr = "";
		String line;
		while ((line = bf.readLine()) != null) {
			outStr += line;
		}
		Assert.assertEquals("bonjour", outStr);
	}

	@Test
	public void compressMissingParameterTest() throws Throwable {
		BatchPurgeIndividual batch = new BatchPurgeIndividual();
		final Method method = BatchPurgeIndividual.class.getDeclaredMethod("_compress", String.class);
		String str = null;
		method.setAccessible(true);
		byte[] result = (byte[]) method.invoke(batch, str);
		Assert.assertEquals(0, result.length);
	}
}
