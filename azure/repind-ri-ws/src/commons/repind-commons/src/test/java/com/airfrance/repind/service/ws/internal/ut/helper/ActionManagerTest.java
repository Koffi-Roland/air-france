package com.airfrance.repind.service.ws.internal.ut.helper;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



@ContextConfiguration(locations="classpath:/config/application-context-spring-test.xml")
@Transactional(transactionManager="transactionManagerRepind")
public class ActionManagerTest {
	
	@Test
	public void test_modifySignatureForGroup() throws SystemException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		CommunicationPreferencesDS communicationPreferencesDS = new CommunicationPreferencesDS();
		Method method = CommunicationPreferencesDS.class.getDeclaredMethod("modifySignatureForGroup", String.class);
		method.setAccessible(true);
		
		String result = (String) method.invoke(communicationPreferencesDS, "ABCDEFGHIJKLMNOP");
		Assert.assertEquals("ABCDEFGHIJKLMN/G", result);
		
		result = (String) method.invoke(communicationPreferencesDS, "ABCDEFGHIJKLMNO");
		Assert.assertEquals("ABCDEFGHIJKLMN/G", result);
		
		result = (String) method.invoke(communicationPreferencesDS, "ABCDEFGHIJKLMN");
		Assert.assertEquals("ABCDEFGHIJKLMN/G", result);
		
		result = (String) method.invoke(communicationPreferencesDS, "ABCDEFGHIJKLM");
		Assert.assertEquals("ABCDEFGHIJKLM/G", result);
		
		result = (String) method.invoke(communicationPreferencesDS, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		Assert.assertEquals("ABCDEFGHIJKLMN/G", result);
		
		result = (String) method.invoke(communicationPreferencesDS, "ABCD");
		Assert.assertEquals("ABCD/G", result);
	}
	
	
	
}
