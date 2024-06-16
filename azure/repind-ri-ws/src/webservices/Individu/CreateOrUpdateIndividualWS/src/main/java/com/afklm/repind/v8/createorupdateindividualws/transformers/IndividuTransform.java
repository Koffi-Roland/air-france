package com.afklm.repind.v8.createorupdateindividualws.transformers;

public class IndividuTransform {

	public static String clientNumberToString(String clientNumber) {
		if (clientNumber != null){
			String gin = clientNumber;
			while(gin.length() < 12){
				gin='0'+gin;
			}
			return gin;
		}
		return null;
	}
}
