package com.airfrance.repind.service.encryption.internal;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.util.EncryptionUtils;
import com.airfrance.repind.util.PwdContainer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncryptionDS {

    /** logger */
    private static final Log log = LogFactory.getLog(EncryptionDS.class);

	/**
	 * Constante to get name from DB
	 */
	private final static String GENERATE_PASSWORD_PATTERNS_DELIMITER_FLAG = "generatepasswordpatternsdelimiter";
	private final static String GENERATE_PASSWORD_PATTERNS_TO_MATCH = "generatepasswordpatternstomatch";
	private final static String PASSWORD_PATTERNS_TO_MATCH = "passwordpatternstomatch";
	private final static String GENERATE_PASSWORD_PATTERN = "generatepasswordpattern";
	private final static String MIN_LENGTH_ROBUST_PASSWORD = "minlengthrobustpassword";
	private final static String MAX_LENGTH_ROBUST_PASSWORD = "maxlengthrobustpassword";
	private final static String PINCODELENGTH = "pincodelength";
	private final static String ROBUST_PASSWORD_GENERATE_ACTIVATED = "ROBUST_PASSWORD_GENERATE_ACTIVATED";
	private final static String ROBUST_PASSWORD_AUTHENTICATE_ACTIVATED = "ROBUST_PASSWORD_AUTHENTICATE_ACTIVATED";
	private final static String ROBUST_PASSWORD_ERROR_ACTIVATED = "ROBUST_PASSWORD_ERROR_ACTIVATED";


	@Autowired
	private VariablesDS variablesDS;

    /*PROTECTED REGION ID(_MWN5oMLIEeeQFOj9cAq5gg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * empty Constructor
     */
	public EncryptionDS() {
	}


	/**
	 * Call the getCryptedGeneratePassword form EncryptionUtils using grammar,
	 * patternToMatch and delimiter from DB.
	 * 
	 * @param gin
	 * @return
	 * @throws JrafDomainException
	 * @throws JrafApplicativeException
	 */
	public PwdContainer getCryptedGeneratePassword(String gin) throws JrafDomainException, JrafApplicativeException {

		if (isRobustPasswordGenerateActivated()) {
			String passwordGrammarDefinition = this.getPatternFromDB();
			String passwordPatterns = this.getGeneratePatternToMatchFromDB();
			String delimiter = this.getPatternDelimiterFromDB();
			int minLengthRobustPassword = this.getMinLengthRobustPasswordFromDB();

			//maxLength is not usefull for generate
			passwordGrammarDefinition = this.concatLengthToGrammar(passwordGrammarDefinition, minLengthRobustPassword,
					minLengthRobustPassword);

			return EncryptionUtils.getCryptedGeneratePassword(gin, passwordGrammarDefinition, passwordPatterns,
					delimiter);
		} else {
			int pincodelength = this.getPincodelength();
			return EncryptionUtils.getCryptedGeneratePassword(gin, pincodelength);
		}

	}

	/**
	 * If the robust password is activated, call
	 * isPasswordCompliantRobustPassword from EncryptionUtils
	 * 
	 * @param password
	 * @return
	 * @throws JrafDomainException
	 */
	public boolean isPasswordCompliant(String password) throws JrafDomainException {
		
		String passwordGrammarDefinition = this.getPatternFromDB();
		String passwordPatterns = this.getPatternToMatchFromDB();
		String delimiter = this.getPatternDelimiterFromDB();
		
		int minLengthRobustPassword = this.getMinLengthRobustPasswordFromDB();
		int maxLengthRobustPassword = this.getMaxLengthRobustPasswordFromDB();

		passwordGrammarDefinition = this.concatLengthToGrammar(passwordGrammarDefinition, minLengthRobustPassword,
				maxLengthRobustPassword);

		return EncryptionUtils.isPasswordCompliantRobustPassword(password, passwordGrammarDefinition, passwordPatterns,
				delimiter);
	}

	/**
	 * Concat the minLength and if existing the max length with the regex
	 * grammar.
	 * 
	 * @param minLength
	 * @param maxLength
	 * @return the String concat
	 * @throws JrafDomainException
	 */
	private String concatLengthToGrammar(String grammar, int minLength, int maxLength) throws JrafDomainException {
		if (minLength < 0 || maxLength < minLength) {
			throw new JrafDomainException("minLength or maxLength isn't a valid value!");
		}
		if (StringUtils.isNotBlank(grammar)) {
			return grammar + "{" + minLength + "," + maxLength + "}";
		}
		return grammar;
	}

	private String getPatternFromDB() throws JrafDomainException {
		return variablesDS.getByEnvKey(GENERATE_PASSWORD_PATTERN).getEnvValue();
	}

	private String getPatternToMatchFromDB() throws JrafDomainException {
		return variablesDS.getByEnvKey(PASSWORD_PATTERNS_TO_MATCH).getEnvValue();
	}

	/**
	 * Get pattern from DB for generate only
	 * 
	 * @return
	 * @throws JrafDomainException
	 */
	private String getGeneratePatternToMatchFromDB() throws JrafDomainException {
		return variablesDS.getByEnvKey(GENERATE_PASSWORD_PATTERNS_TO_MATCH).getEnvValue();
	}

	/**
	 * Get pattern Robust Password (rules don't used to generate)
	 * 
	 * @return
	 * @throws JrafDomainException
	 */
	private String getPatternDelimiterFromDB() throws JrafDomainException {
		return variablesDS.getByEnvKey(GENERATE_PASSWORD_PATTERNS_DELIMITER_FLAG).getEnvValue();
	}

	private int getMinLengthRobustPasswordFromDB() throws JrafDomainException {
		return Integer.parseInt(variablesDS.getByEnvKey(MIN_LENGTH_ROBUST_PASSWORD).getEnvValue());
	}

	private int getMaxLengthRobustPasswordFromDB() throws JrafDomainException {
		return Integer.parseInt(variablesDS.getByEnvKey(MAX_LENGTH_ROBUST_PASSWORD).getEnvValue());
	}

	private int getPincodelength() throws JrafDomainException {
		return Integer.parseInt(variablesDS.getByEnvKey(PINCODELENGTH).getEnvValue());
	}

	public boolean isRobustPasswordGenerateActivated() throws JrafDomainException {
		return Boolean.valueOf(variablesDS.getByEnvKey(ROBUST_PASSWORD_GENERATE_ACTIVATED).getEnvValue());
	}

	public boolean isRobustPasswordAuthenticateActivated() throws JrafDomainException {
		return Boolean
				.valueOf(variablesDS.getByEnvKey(ROBUST_PASSWORD_AUTHENTICATE_ACTIVATED).getEnvValue());
	}

	public boolean isRobustPasswordErrorModeActivated() throws JrafDomainException {
		return Boolean.valueOf(variablesDS.getByEnvKey(ROBUST_PASSWORD_ERROR_ACTIVATED).getEnvValue());
	}

    /*PROTECTED REGION ID(_MWN5oMLIEeeQFOj9cAq5gg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}
