package com.afklm.repind.v1.createorupdatecomprefbasedonpermws.it.helper;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.adresse.Usage_mediumRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.profil.ProfilsRepository;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Usage_medium;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.profil.Profils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;

@Service("CreateOrUpdateComPrefHelper")
public class CreateOrUpdateComPrefHelper {

	@Autowired
	private IndividuRepository individuRepository;
	
	@Autowired
	private ProfilsRepository profilsRepository;
	
	@Autowired
	private PostalAddressRepository postalAddressRepository;
	
	@Autowired
	private Usage_mediumRepository usageMediumRepository;

	public CreateOrUpdateComPrefHelper() {
		
	}

	@Transactional
	@Rollback(true)
	public String generateIndividualForTest(boolean createProfil, String languageProfil, boolean createAddress, String statutAddress, String codePays, boolean isISI) throws InvalidParameterException, JrafDaoException {
		return generateIndividualForTest("199999999999", createProfil, languageProfil, createAddress, statutAddress, codePays, isISI);
	}
	
	@Transactional
	@Rollback(true)
	public String generateIndividualForTest(String gin, boolean createProfil, String languageProfil, boolean createAddress, String statutAddress, String codePays, boolean isISI) throws InvalidParameterException, JrafDaoException {
		if (languageProfil == null) languageProfil = "FR";
		if (statutAddress == null) statutAddress = "V";
		if (codePays == null) codePays = "FR";
		if (gin == null) gin = "999999999999";
		
		Individu individu = new Individu();

		individu.setSexe("M");
		individu.setDateNaissance(new Date());
		individu.setDateCreation(new Date());
		individu.setSignatureCreation("REPIND");
		individu.setSiteCreation("QVI");
		individu.setStatutIndividu("V");
		individu.setCivilite("MR");
		individu.setNomSC("TEST COM");
		individu.setPrenomSC("TEST PREF");
		individu.setSgin(gin);
		individu.setNonFusionnable("N");
		individu.setType("I");
		individu.setCommunicationpreferences(new HashSet<CommunicationPreferences>());
		individuRepository.saveAndFlush(individu);

		if (createProfil) {
			Profils profils = new Profils();
			profils.setSgin(individu.getSgin());
			profils.setSmailing_autorise("T");
			profils.setSrin("1");
			profils.setSsolvabilite("O");
			profils.setScode_langue(languageProfil);
			profils.setIversion(1);
			profilsRepository.saveAndFlush(profils);
			individu.setProfils(profils);
			individuRepository.saveAndFlush(individu);
		}
		
		if (createAddress) {
			PostalAddress postalAddress = new PostalAddress();
			postalAddress.setSgin(gin);
			postalAddress.setVersion(1);
			postalAddress.setScode_medium("D");
			postalAddress.setSstatut_medium(statutAddress);
			postalAddress.setSsite_creation("QVI");
			postalAddress.setSignature_creation("TESTRI");
			postalAddress.setDdate_creation(new Date());
			postalAddress.setIcod_err(0);
			postalAddress.setScode_pays(codePays);
			postalAddress.setDdate_modification(new Date());
			postalAddressRepository.saveAndFlush(postalAddress);
			
			if (isISI) {
				Usage_medium usageMedium = new Usage_medium();
				usageMedium.setSain_adr(postalAddress.getSain());
				usageMedium.setInum(1);
				usageMedium.setScode_application("ISI");
				usageMedium.setSrole1("M");
				usageMediumRepository.saveAndFlush(usageMedium);
				postalAddress.setUsage_medium(new HashSet<Usage_medium>());
				postalAddress.getUsage_medium().add(usageMedium);
				postalAddressRepository.saveAndFlush(postalAddress);
			}
			
			individu.setPostaladdress(new HashSet<PostalAddress>());
			individu.getPostaladdress().add(postalAddress);
			individuRepository.saveAndFlush(individu);
		}
		
		return individu.getSgin();
	}
}
