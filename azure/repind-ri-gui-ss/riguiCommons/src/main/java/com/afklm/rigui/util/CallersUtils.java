package com.afklm.rigui.util;

import org.springframework.stereotype.Component;

@Component
public class CallersUtils {
	
	private final static String CCP_CONTEXT = "CCP";
	
	private static final String CAPI_CONSUMER = "W21375138";
	private static final String SIC_CONSUMER = "W85875644";
	private static final String REPIND_CONSUMER = "W04776476";
	
	public boolean isAuthorized(String consumerId, String context) {
		
		if (CAPI_CONSUMER.equalsIgnoreCase(consumerId)) {
			if (CCP_CONTEXT.equalsIgnoreCase(context)) {
				return true;
			}
		} else if (REPIND_CONSUMER.equalsIgnoreCase(consumerId) || SIC_CONSUMER.equalsIgnoreCase(consumerId)) {
			return true;
		}
		
		return false;
	}
}
