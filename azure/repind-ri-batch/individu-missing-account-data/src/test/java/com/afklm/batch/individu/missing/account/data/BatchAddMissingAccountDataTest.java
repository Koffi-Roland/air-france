package com.afklm.batch.individu.missing.account.data;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.common.config.WebConfigBatchRepind;
import com.airfrance.common.batch.config.WebConfigTestBatch;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebConfigTestBatch.class)
@Transactional(value = "transactionManagerRepind")
public class BatchAddMissingAccountDataTest {

	private final static Log log = LogFactory.getLog(BatchAddMissingAccountDataTest.class);

	@Test
	@Rollback(true)
	public void testNominal() throws Exception {
		log.info("Lancement du batch...");

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(WebConfigBatchRepind.class);
		IBatch batch = (IBatch) ctx.getBean("batchAddMissingAccountData");

		try {
			log.info("Batch execution...");
			batch.execute();
			ctx.close();

			BatchAddMissingAccountData batchAddMissingAccountData = (BatchAddMissingAccountData) batch;
			Assert.assertEquals(batchAddMissingAccountData.getElementNumber(),
					batchAddMissingAccountData.getNewAccountDataNumber()
							+ batchAddMissingAccountData.getRemainingGinNumber());

		} catch (JrafDomainException | IOException e) {
			log.fatal("Erreur lors de l'execution de BatchAddMissingAccountData");
			if (ctx != null) {
				ctx.close();
			}
			Assert.fail(e.getMessage());
			System.exit(1);
		}
	}
}
