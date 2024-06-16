package com.afklm.repind.v1.lastciconnectionlistener;

import com.afklm.soa.stubs.w002577.v1.LastConnectStatusNotificationV10;
import com.afklm.soa.stubs.w002577.v1.xsd0.LastConnectDtl;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.Date;

/**
 * Listener to update the last connection date
 * 
 * @author T919922
 *
 */
@WebService(endpointInterface = "com.afklm.soa.stubs.w002577.v1.LastConnectStatusNotificationV10",
targetNamespace = "http://www.af-klm.com/services/customer/LastConnectStatusNotification-v1/wsdl",
serviceName = "LastConnectStatusNotification-v1_0",
portName = "LastConnectStatusNotification-v1_0-soap11jms")
@Slf4j
public class LastConnectStatusListenerV1 implements LastConnectStatusNotificationV10 {

	private static final String SITE_MODIFICATION = "QVI";
	private static final String SIGNATURE_MODIFICATION = "LCIC";

	@Autowired
	private MyAccountDS myAccountDs;

	@Override
	public void lastConnectStatusNotification(LastConnectDtl lastConnectDetails) {
		if (lastConnectDetails != null) {
			String gin = lastConnectDetails.getGin();
			Date lastConnecction = lastConnectDetails.getLastConnectionDate();
			log.info("GIN : " + gin + " Last connection date : " + lastConnecction );
			if (StringUtils.isNotBlank(gin) && lastConnecction != null) {
				log.info("Updating Last connection date for GIN : " + gin + " Last connection date : "
						+ lastConnecction);
				myAccountDs.updateLastConnectionDate(gin, lastConnecction, SITE_MODIFICATION, SIGNATURE_MODIFICATION);
			}
		}
	}
}
