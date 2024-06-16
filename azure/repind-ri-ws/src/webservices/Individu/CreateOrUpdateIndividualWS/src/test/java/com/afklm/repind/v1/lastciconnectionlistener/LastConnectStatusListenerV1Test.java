package com.afklm.repind.v1.lastciconnectionlistener;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.soa.stubs.w002577.v1.xsd0.LastConnectDtl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class LastConnectStatusListenerV1Test {

	@Autowired
	private LastConnectStatusListenerV1 testListener;
	
	@Test(expected = Test.None.class)
	@Rollback(true)
	public void testLastConnectStatusNotification(){
		LastConnectDtl lastConnectDetails = null;
		testListener.lastConnectStatusNotification(lastConnectDetails);
		
		lastConnectDetails = new LastConnectDtl();
		lastConnectDetails.setGin("TEST_1");
		lastConnectDetails.setLastConnectionDate(new Date());
		testListener.lastConnectStatusNotification(lastConnectDetails);
	}
}
