package com.airfrance.repind.service;

import com.airfrance.repind.dto.adresse.EmailDTO;

import java.util.Calendar;
import java.util.Date;

public class TestEntitiesGenerator {

	public static final String signature = "UT";
	public static final String site = "VALBONNE";
	public static final Date today = new Date();

	public static final String NO_EXCEPTION_TO_BE_THROWN = "No exception is supposed to be thrown!";
	public static final String AN_EXCEPTION_TO_BE_THROWN = "An exception is supposed to be thrown!";






	
	public static EmailDTO createEmailDTO(String mail, String statusMedium, String autorisation, String codeMedium) {
		EmailDTO email = new EmailDTO();
		email.setStatutMedium(statusMedium);
		email.setEmail(mail);
		email.setAutorisationMailing(autorisation);
		email.setCodeMedium(codeMedium);
		email.setSignatureCreation(signature);
		email.setDateCreation(addDays(-10));
		email.setSiteCreation(site);
		return email;
	}
	


	




	public static Date addDays(int noOfDays) {
		Calendar openDate = Calendar.getInstance();
		openDate.setTime(today);
		openDate.add(Calendar.DATE, noOfDays);
		return openDate.getTime();
	}

}
