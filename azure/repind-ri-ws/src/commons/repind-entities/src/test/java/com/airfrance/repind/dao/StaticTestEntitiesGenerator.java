package com.airfrance.repind.dao;


import com.airfrance.ref.type.YesNoFlagEnum;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.entity.reference.Reseau;

import java.util.Date;

/**
 * This class generates test entities for DAOs in static.
 * 
 * @author Ghayth AYARI
 *
 */
public class StaticTestEntitiesGenerator {

	public static final String signature = "GhaythTest";
	public static final String site = "VALBONNE";
	public static final Date dateSignature = new Date();

	public static final String NO_EXCEPTION_TO_BE_THROWN = "No exception is supposed to be thrown!";
	public static final String AN_EXCEPTION_TO_BE_THROWN = "An exception is supposed to be thrown!";


	public static CommunicationPreferences createTestCommunicationPreferences() {

		// Create a test entity and fill it with data
		CommunicationPreferences testCommunicationPreferences = new CommunicationPreferences();
		testCommunicationPreferences.setComGroupType("N");
		testCommunicationPreferences.setGin("123");
		testCommunicationPreferences.setComType("AF");
		testCommunicationPreferences.setSubscribe("Y");
		testCommunicationPreferences.setDomain("S");
		testCommunicationPreferences.setCreationSite(site);
		testCommunicationPreferences.setCreationSignature(signature);
		testCommunicationPreferences.setCreationDate(dateSignature);
		testCommunicationPreferences.setDateOptin(new Date());
		testCommunicationPreferences.setModificationDate(dateSignature);
		testCommunicationPreferences.setModificationSignature(signature);
		testCommunicationPreferences.setModificationSite(site);
		testCommunicationPreferences.setChannel("B2C");
		testCommunicationPreferences.setMedia1("E");

		return testCommunicationPreferences;
	}


	public static MarketLanguage createTestMarketLanguage() {

		// Create a test entity and fill it with data
		MarketLanguage testMarketLanguage = new MarketLanguage();
		testMarketLanguage.setMarket("MAR");
		testMarketLanguage.setOptIn("Y");
		testMarketLanguage.setCreationSignature(signature);
		testMarketLanguage.setCreationSite(site);
		
		return testMarketLanguage;
	}


	public static Individu createTestIndividu() {

		// Create a test entity and fill it with data
		Individu individu = new Individu();
		individu.setNom("AYARI");
		individu.setPrenom("Ghayth");
		individu.setSgin("1234567");
		individu.setCivilite("MR");
		individu.setSexe("M");
		individu.setType("I");
		individu.setStatutIndividu("V");
		individu.setMandatoryDBFields();
		individu.setSiteModification(site);
		individu.setSiteCreation(site);
		individu.setSignatureCreation(signature);
		individu.setSignatureModification(signature);
		individu.setDateModification(dateSignature);
		individu.setDateCreation(dateSignature);

		return individu;
	}







	
	

	
	
	/**
	 * Creates a test unity Email.
	 * 
	 * @return
	 */
	public static Email createTestEmail() {

		// Create a test entity
		Email email = new Email();
		email.setCodeMedium("F");
		email.setStatutMedium("V");
		email.setSiteCreation(site);
		email.setSiteModification(site);
		email.setSignatureModification(signature);
		email.setSignatureCreation(signature);
		email.setDateCreation(dateSignature);
		email.setDateModification(dateSignature);
		email.setAutorisationMailing("A");
		email.setEmail("ghayari-ext@airfrance.fr");
		
		return email;
	}
	
	
	public static Telecoms createTestTelecoms() {

		// Create a test entity
		Telecoms telecoms = new Telecoms();
		telecoms.setScode_medium("F");
		telecoms.setSstatut_medium("V");
		telecoms.setSsite_creation(StaticTestEntitiesGenerator.site);
		telecoms.setSsite_modification(StaticTestEntitiesGenerator.site);
		telecoms.setSsignature_modification(StaticTestEntitiesGenerator.signature);
		telecoms.setSsignature_creation(StaticTestEntitiesGenerator.signature);
		telecoms.setDdate_creation(StaticTestEntitiesGenerator.dateSignature);
		telecoms.setDdate_modification(StaticTestEntitiesGenerator.dateSignature);
		telecoms.setSnumero("0612345687");
		telecoms.setSterminal("T");
		
		return telecoms;
	}
	

	
	

	

	

	

	


	
	

	
	public static PostalAddress createTestAddressWithoutPm() {
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setScode_medium("F");
		postalAddress.setSstatut_medium("V");
		postalAddress.setSsite_creation(site);
		postalAddress.setSsite_modification(site);
		postalAddress.setSsignature_modification(signature);
		postalAddress.setSignature_creation(signature);
		postalAddress.setDdate_creation(dateSignature);
		postalAddress.setDdate_modification(dateSignature);
		postalAddress.setIcod_err(0);
		
		return postalAddress;
	}

	
	public static Reseau createTestReseau() {
		Reseau network = new Reseau();
		network.setDateCreation(dateSignature);
		network.setCode("TSTNWK");
		network.setNom("TEST NETWORK");
		network.setType("R");
		network.setNature("1");
		network.setPays("US");
		return network;
	}


	
	
	public static MarketLanguage createTestMarketLanguage(String market, String language, Integer comPrefId) {
		MarketLanguage testMarketLanguage = new MarketLanguage();
		testMarketLanguage.setMarket(market);
		testMarketLanguage.setMarket(language);
		testMarketLanguage.setOptIn(YesNoFlagEnum.YES.toString());
		testMarketLanguage.setCreationSignature(signature);
		testMarketLanguage.setCreationSite(site);
		testMarketLanguage.setComPrefId(comPrefId);
		
		return testMarketLanguage;
	}
}
