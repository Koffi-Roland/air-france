package com.airfrance.repind.util;


public class CheckUtils {

	/**
	 * checkIataNumber
	 * 
	 * 
	 * @param iataToCheck (enriched with 8th digit if missing)
	 * @return true if it's a valid Iata
	 */
	public static boolean checkIataNumber(String iataToCheck) {
		// Controle la presence du Check Digit. Si absent (7 cars on le calcule
		// et on
		// le rajoute.

		boolean validIata = false;

		if (SicStringUtils.isNumeric(iataToCheck)) {

			if (iataToCheck.length() < 8) {
				double checkCode = Double.valueOf(iataToCheck) % 7;
				iataToCheck += checkCode;
				validIata = true;
			} else {

				if (Double.valueOf(iataToCheck.substring(0, 7)) % 7 == Double
						.valueOf(iataToCheck.substring(7, 8))) {
					validIata = true;
				}
			}

		}

		return validIata;
	}
	
	
	
	public static boolean isInFranceLanguageRegion(String codePays){
		boolean francophoneCountry = false;
		
		
		
		
		return francophoneCountry;
	}
	
	
	
	
}
