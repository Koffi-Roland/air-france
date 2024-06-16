package com.airfrance.ref.util;

import com.afklm.soa.stubs.w000410.v2.MarketingDataBusinessException;
import com.afklm.soa.stubs.w000410.v2.common.MarketingDataBusinessFault;
import com.airfrance.ref.exception.MarketingErrorException;

public final class MarketingPreferencesUtils {

	// Check on Exception if we can find a NOT FOUND or if it is a real fault
	public static boolean MarketingPreferenceNotFoundException(MarketingErrorException mee) {
		
		// Buy default, this is a Business Exception as used to be in the past history of this WS
		boolean MarketingPreferenceNotFound = false;
		
		if (mee != null) {
			
			try {
				// Search the cause
				MarketingDataBusinessException mdbe = (MarketingDataBusinessException) mee.getCause();
			
				MarketingPreferenceNotFound = MarketingPreferenceNotFoundDataException(mdbe);
			
			// If there is a ClassCast Exception for MarketingDataBusinessException, we log an ERROR
			} catch (Exception ex) {
				
			}
		}

		return MarketingPreferenceNotFound;
	}

	// Check on Exception if we can find a NOT FOUND or if it is a real fault
	public static boolean MarketingPreferenceNotFoundDataException(MarketingDataBusinessException mdbe) {
		
		// Buy default, this is a Business Exception as used to be in the past history of this WS
		boolean MarketingPreferenceNotFound = false;
		
		if (mdbe != null) {
			
			try {
				// Search the cause			
				if (mdbe != null) {
				
					MarketingDataBusinessFault mdbf = mdbe.getFaultInfo();
				
					if (mdbf != null) {
						
						String mdbfgfd = mdbf.getFaultDescription();
						// Search the NOT FOUND keyword
						if (mdbfgfd != null && mdbfgfd.toUpperCase().contains("NOT FOUND") ) {
							
							MarketingPreferenceNotFound = true;
						}
					}
				}
			
			// If there is a ClassCast Exception for MarketingDataBusinessException, we log an ERROR
			} catch (Exception ex) {
				
			}
		}

		return MarketingPreferenceNotFound;
	}
	
}
