package com.airfrance.repind.util;

import com.airfrance.ref.exception.InvalidMailingCodeException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.NATFieldsEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {
	
	private static final String ENDS_WITH_REGEX = ")$";
	private static final String AT_REGEX = ")@";
	private static final String OR_REGEX = "|";
	private static final String NON_CATURING_GROUP_REGEX = "(?:";
	private static final String LOCAL_PART_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*";
	private static final String LOCAL_PART_INSIDE_QUOTES_REGEX = "\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\"";
	private static final String DOMAIN_ALPHA_REGEX = "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
	private static final String DOMAIN_IP_ADDRESS_REGEX = "\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]";
	
	/**
	 * Validates email based on RFC 2822 standard email validation rules
	 * 
	 * @param email to be validated
	 * @return if a valid email <code>true</code>, otherwise <code>false</code>
	 */
	public static boolean isValidEmail(String email) {
		if (email.length() > 60) {
			return false;
		}

		String regex = NON_CATURING_GROUP_REGEX + LOCAL_PART_REGEX + OR_REGEX + LOCAL_PART_INSIDE_QUOTES_REGEX + AT_REGEX
				+ NON_CATURING_GROUP_REGEX + DOMAIN_ALPHA_REGEX + OR_REGEX + DOMAIN_IP_ADDRESS_REGEX + ENDS_WITH_REGEX;

		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();
	}

	/**
	 * Check if email optin is valid
	 * @param emailBloc
	 * @return Exception if email optin is null or different from N,A,T
	 * @throws InvalidMailingCodeException
	 */
	public static void checkNAT(EmailDTO emailBloc) throws InvalidMailingCodeException {
		if (emailBloc != null) {
			if (emailBloc.getAutorisationMailing() != null) {
				String nat = emailBloc.getAutorisationMailing();
				if (!NATFieldsEnum.AIRFRANCE.getValue().equalsIgnoreCase(nat) && 
					!NATFieldsEnum.NONE.getValue().equalsIgnoreCase(nat) &&
					!NATFieldsEnum.ALL.getValue().equalsIgnoreCase(nat)) {
					
					throw new InvalidMailingCodeException("Unknown authorization code (emailOptin) ");
				}
				
			}
			else {
				emailBloc.setAutorisationMailing(NATFieldsEnum.NONE.getValue());
			}
		}
	}
	
	public static void setDefaultValueEmails(List<EmailDTO> emailList) {
		if(emailList == null || emailList.isEmpty()) {return;}

		for (EmailDTO emailFromWS : emailList) {	
			// Set default value for NAT
			if (StringUtils.isEmpty(emailFromWS.getAutorisationMailing())) {
				emailFromWS.setAutorisationMailing(NATFieldsEnum.NONE.getValue());
			}
			if(StringUtils.isEmpty(emailFromWS.getStatutMedium())) {
				emailFromWS.setStatutMedium(MediumStatusEnum.VALID.toString());
			}
		}			
	}
}
