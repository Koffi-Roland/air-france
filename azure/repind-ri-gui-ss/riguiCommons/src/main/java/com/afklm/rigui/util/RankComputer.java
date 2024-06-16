package com.afklm.rigui.util;

import com.afklm.rigui.dto.adresse.EmailDTO;
import com.afklm.rigui.dto.adresse.PostalAddressDTO;
import com.afklm.rigui.dto.adresse.TelecomsDTO;
import com.afklm.rigui.dto.individu.IndividuDTO;
import com.afklm.rigui.dto.individu.adh.searchindividualbymulticriteria.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Set;


public class RankComputer {
	private static final Log log = LogFactory.getLog(RankComputer.class);

    public static int computeRankIndividu(SearchIndividualByMulticriteriaRequestDTO request, IndividualMulticriteriaDTO individuHomonym)
    {
		// Calcul du Critere de pertinence de l'individu renvoyé (RANKING):
		log.debug("Calcul du Ranking de l'Individu");

		// Default value is 0 since this means that there is no match found in any kind of ways
		int relevance = 0;

		// Get individual to compare to
		IndividuDTO homonym = individuHomonym.getIndividu();
		PostalAddressDTO homonymAddress = homonym.getAdrPostPriviligie();

		// Get request information
		IdentityDTO identity = request.getIdentity();
		String prenom = null;
		String nom = null;
		if (identity != null) {
			prenom = identity.getFirstName();
			nom = identity.getLastName();
		}
		ContactDTO contact = request.getContact();
		String phoneNumber = null;
		String email = null;
		String countryCode = null;
		PostalAddressBlocDTO postalAddress = null;
		
		// REPIND-1264 : Add Search for Social by Type and Identifier
		IdentificationDTO identification = request.getIdentification();
		
		if (contact != null) {
			phoneNumber = contact.getPhoneNumber();
			email = contact.getEmail();
			countryCode = contact.getCountryCode();
			
			if (contact.getPostalAddressBloc() == null)
				contact.setPostalAddressBloc(postalAddress);
			
			postalAddress = contact.getPostalAddressBloc();
		}
		
		// Process cases
		// REPIND-1264 : Add Search for Social by Type and Identifier
		// REPIND-1286 : Add a check on each field to know if it is empty of not
		if (identification != null && identification.getIdentificationType() != null && identification.getIdentificationValue() != null) {
				
				// On ne recherche que du Strict sur les Identifications
				if (!"".equals(identification.getIdentificationValue())) {				
					// REPIND-1495 : Add a Search on NAME and Social for 100%
					if (nom != null && !"".equals(nom) && prenom != null && !"".equals(prenom)) {
						
						relevance = 100;	
						
						// Check whether first name and last name are strict or like 
						boolean firstNameLike = false;
						boolean lastNameLike = false;

						if ((homonym.getNomSC().toUpperCase()).equals(nom.toUpperCase()))
						{}
						else if ((homonym.getNomSC().toUpperCase()).contains(nom.toUpperCase()))
							lastNameLike = true;

						if ((homonym.getPrenomSC().toUpperCase()).equals(prenom.toUpperCase()))
						{}
						else if ((homonym.getPrenomSC().toUpperCase()).contains(prenom.toUpperCase()))
							firstNameLike = true;
						
						if (firstNameLike || lastNameLike)
							relevance = relevance - 10;
						
					} else {
						relevance = 90;	
					}
				}
			
		} else if (identity == null || prenom == null || nom == null) {	// Cas SANS NOM et/ou SANS PRENOM

			// Check phone number, email and postal address
			if ((comparePhoneNumbers(phoneNumber, countryCode, homonym.getTelecoms()))
					|| compareEmails(email, homonym.getEmaildto()))
				relevance = 70;
			else if (compareAdresses(postalAddress, homonymAddress))
				relevance = 60;
		
		} else {	// Cas AVEC NOM et PRENOM
			
			// Check whether first name and last name are strict or like 
			boolean firstNameLike = false;
			boolean lastNameLike = false;
			
			if ((homonym.getNomSC().toUpperCase()).equals(nom.toUpperCase()))
			{}
			else if ((homonym.getNomSC().toUpperCase()).contains(nom.toUpperCase()))
				lastNameLike = true;

			if ((homonym.getPrenomSC().toUpperCase()).equals(prenom.toUpperCase()))
			{}
			else if ((homonym.getPrenomSC().toUpperCase()).contains(prenom.toUpperCase()))
				firstNameLike = true;

			// There is only one case where we tolerate both first and last name are like
			if (firstNameLike && lastNameLike
					&& postalAddress != null && homonymAddress != null) {
				if (compareStrict(postalAddress.getCountryCode(), homonymAddress.getScode_pays()))
					relevance = 30;

			} else {
				// Check email and phone number
				if (compareEmails(email, homonym.getEmaildto())
						|| comparePhoneNumbers(phoneNumber, countryCode, homonym.getTelecoms())) {

					relevance = 100;

					if (firstNameLike || lastNameLike)
						relevance = relevance - 10;

				} else {
					// Check birthdates
					Date requestBirthday = identity.getBirthday();
					Date homonymBirthday = homonym.getDateNaissance();

					if (isDatesEqual(requestBirthday, homonymBirthday)) {

						if (compareAdresses(postalAddress, homonymAddress)) {
							relevance = 100;

							if (firstNameLike || lastNameLike)
								relevance = relevance - 10;
							
						} else {
							if (postalAddress == null)
								relevance = 65;
							else {
								if (!lastNameLike && postalAddress != null && homonymAddress != null) {

									if (compareStrict(postalAddress.getCountryCode(), homonymAddress.getScode_pays())) {
										
										if (compareStrict(postalAddress.getCity(), homonymAddress.getSville())
												&& (compareLike(postalAddress.getNumberAndStreet(), homonymAddress.getSno_et_rue())
														|| compareStrict(postalAddress.getZipCode(), homonymAddress.getScode_postal()))) {
											relevance = 90;
										} else {
											if (compareLike(postalAddress.getCity(), homonymAddress.getSville())
													|| compareLike(postalAddress.getZipCode(), homonymAddress.getScode_postal())) {
												relevance = 80;
											} else {
												if(!firstNameLike)
													relevance = 70;
											}
										}
										if (relevance != 0 && firstNameLike)
											relevance = relevance - 10;
									}
								}
							}
						}

					} else {	// No birthdates
						if (!lastNameLike) {
							if (!firstNameLike && compareAdresses(postalAddress, homonymAddress)) {
								relevance = 90;
							} else {
								if (postalAddress != null && homonymAddress != null
										&& compareStrict(postalAddress.getCountryCode(), homonymAddress.getScode_pays())) {
									
									if (!firstNameLike
											&& (compareLike(postalAddress.getZipCode(), homonymAddress.getScode_postal())
													|| compareLike(postalAddress.getCity(), homonymAddress.getSville()))) {
										relevance = 60;
									} else {
										relevance = 50;
										if (firstNameLike) {
											relevance = relevance - 10;
										}
									}
								}
							}
						// REPIND-845 : Add 40% for LastName L + FirstName S + CountryCode S (-10% on FirstStrict LastStrict)
						} else {
							if (postalAddress != null && homonymAddress != null
									&& compareStrict(postalAddress.getCountryCode(), homonymAddress.getScode_pays())) {
								// CountryCode S
								
								if (!firstNameLike
										&& (compareLike(postalAddress.getZipCode(), homonymAddress.getScode_postal())
												|| compareLike(postalAddress.getCity(), homonymAddress.getSville()))) {
									relevance = 50;			// LastName L + FirstName S + ZipCode S / City S								
								} else {
									relevance = 40;			// LastName L + FirstName S + CountryCode S 
									
									if (firstNameLike)		// LastName L + FirstName L + CountryCode S
										relevance = relevance - 10;
								}
							} 
						}
						
						// REPIND-1675 : Add only FirstName / LastName search with lower relevancy
						if(identity != null && 
			            		identity.getFirstName() != null && !"".equals(identity.getFirstName()) &&  
			            		identity.getLastName() != null && !"".equals(identity.getLastName()) &&
			            	identity.getBirthday() == null &&
			            	(contact == null || contact.isEmpty()) &&             	
			            	identification == null && 
			            	relevance == 0) {				// In order to be sure that not in previous action
							
							if (firstNameLike) {
								if (lastNameLike) {
									relevance = 25;				// LastName L + FirstName L
								} else {
									relevance = 35;				// LastName S + FirstName L
								}
							} else {
								if (lastNameLike) {
									relevance = 30;				// LastName L + FirstName S
								} else {
									relevance = 40;				// LastName S + FirstName S
								}
							}
						}
					}
				}
			}
		}

		return relevance;
    }
    

	/**
     * Compares the two given dates.
     * 
     * @param date1
     * @param date2
     * 
     * @return true if dates are equal, false else or when date1 and/or date2 is null
     */
    private static boolean isDatesEqual(Date date1, Date date2) {
    	
    	boolean datesEqual = false;
    	
    	if (date1 != null && date2 != null)
    		datesEqual = (date1.getTime() == date2.getTime());
    	
    	return datesEqual;
    }
    

	private static boolean compareAdresses(PostalAddressBlocDTO postalContent, PostalAddressDTO adrPostPriviligie) {
		
		// Initialize return value
		Boolean matching = false;
		
		if (postalContent != null && adrPostPriviligie != null
				&& compareLike(postalContent.getNumberAndStreet(), adrPostPriviligie.getSno_et_rue())
				&& compareStrict(postalContent.getCity(), adrPostPriviligie.getSville())
				&& compareStrict(postalContent.getZipCode(), adrPostPriviligie.getScode_postal())
				&& compareStrict(postalContent.getCountryCode(), adrPostPriviligie.getScode_pays())) {
			matching = true;
		}
		
		return matching;
	}
	
	
	private static boolean compareStrict(String compared, String reference) {

		// Initialize return value
		Boolean matching = false;

		if (compared != null && reference != null) {
			String compared_ = removeDiacritic(compared);

			if (compared_ != null && compared_.equalsIgnoreCase(reference))
				matching = true;
		}

		return matching;
	}
    
    
    private static boolean compareLike(String compared, String reference) {

    	boolean matching = false;
    	
    	if (compared != null && reference != null) {
			String compared_ = removeDiacritic(compared);

			if (compared_ != null
					&& (compared_.toLowerCase().contains(reference.toLowerCase())
							|| reference.toLowerCase().contains(compared_.toLowerCase())))
				matching = true;
    	}
    	
    	return matching;
	}
	
	
	/**
	 * Mirror of the unicode table from 00c0 to 017f without diacritics.
	 */
	private static final String tab00c0 = "AAAAAAACEEEEIIII" +
	    "DNOOOOO\u00d7\u00d8UUUUYI\u00df" +
	    "aaaaaaaceeeeiiii" +
	    "\u00f0nooooo\u00f7\u00f8uuuuy\u00fey" +
	    "AaAaAaCcCcCcCcDd" +
	    "DdEeEeEeEeEeGgGg" +
	    "GgGgHhHhIiIiIiIi" +
	    "IiJjJjKkkLlLlLlL" +
	    "lLlNnNnNnnNnOoOo" +
	    "OoOoRrRrRrSsSsSs" +
	    "SsTtTtTtUuUuUuUu" +
	    "UuUuWwYyYZzZzZzF";

	/**
	 * Returns string without diacritics - 7 bit approximation.
	 *
	 * @param source string to convert
	 * @return corresponding string without diacritics
	 */
	public static String removeDiacritic(String source) {
		if (source == null)
			return null;
	    char[] vysl = new char[source.length()];
	    char one;
	    for (int i = 0; i < source.length(); i++) {
	        one = source.charAt(i);
	        if (one >= '\u00c0' && one <= '\u017f') {
	            one = tab00c0.charAt((int) one - '\u00c0');
	        }
	        vysl[i] = one;
	    }
	    return new String(vysl);
	}

	private static boolean compareEmails(String email, Set<EmailDTO> emaildto) {
		
		Boolean matching = false;
		
		if(emaildto != null && email != null) {
			for (EmailDTO myemail: emaildto) {
				if (email != null && myemail != null){
					if( email.equals(myemail.getEmail()))
						matching = true;
				}
			}
		}
		
		return matching;
	}

	private static boolean comparePhoneNumbers(String phoneNumber, String countryCode,
			Set<TelecomsDTO> telecoms){
		
		Boolean matching = false;
		
		if (telecoms != null && phoneNumber != null) {
			for (TelecomsDTO myphonenumber: telecoms) {
				
				if(myphonenumber != null) {
					if (phoneNumber.equals(myphonenumber.getSnorm_inter_phone_number()))	
		        		matching = true;
					
					if (matching == false && countryCode != null) {
						if (phoneNumber.equals(myphonenumber.getSnorm_nat_phone_number_clean()) && countryCode.equals(myphonenumber.getSnorm_inter_country_code()))	
			        		matching = true;
					}
				}
	        }
		}

		return matching;
	}
	
}
