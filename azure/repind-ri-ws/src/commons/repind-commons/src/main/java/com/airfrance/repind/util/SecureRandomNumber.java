package com.airfrance.repind.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecureRandomNumber {
	
	/** logger */
    private static final Log log = LogFactory.getLog(SecureRandomNumber.class);
	
	private static final String SHA1_PRNG = "SHA1PRNG";
		
	public static final char ALPHA_NUM [] = {
          '0', '1', '2', '3', '4', '5', '6', '7',
          '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
          'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
          'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
          'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
          'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
          'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
          'U', 'V', 'W', 'X', 'Y', 'Z'
        };
	
	public static final char NUM [] = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9'
      };
	
	// This isn't thread safe but we probably don't really care
	// since all we're doing is reading a bunch of random numbers
	// out of the generator.
	private static final SecureRandom secureRandom;
	
	static {
	    try {
	    	secureRandom = SecureRandom.getInstance(SHA1_PRNG);
	    } catch ( NoSuchAlgorithmException e ) {
	        throw new Error(e);
	    }
	}

	
	/**
	 * Get the number of next random bits in this SecureRandom
	 * generators' sequence.
	 * @param size : how many random carac you want
	 * @param possibleValues : possible values you want (numeric, alpha numeric, ...)
	 * @return an random secure String
	 * @throws IllegalArgumentException if the arg isn't divisible by eight
	 */
	public static String getNextSecureRandom (int size, char possibleValues[]) {	
		String toReturn = "";
	    for(int i=0 ; i<size ; i++) {
	    	int value = secureRandom.nextInt(possibleValues.length);
	    	toReturn += possibleValues[value];
	    }
	    return toReturn;
	}
	
	
    /**
     * An example showing how to use SecureRandomNumber.
     * @param args
     */
	public static void main ( String [] args ) {
		for ( int i = 0; i < 10; i++ ) {
			String value = SecureRandomNumber.getNextSecureRandom(12, NUM);
			
			log.debug("SecureRandomNumber = " + value);
		}
	}
}
