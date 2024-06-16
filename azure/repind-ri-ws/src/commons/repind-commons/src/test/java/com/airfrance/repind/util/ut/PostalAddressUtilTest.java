package com.airfrance.repind.util.ut;

import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.util.PostalAddressUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class PostalAddressUtilTest {
	
	private PostalAddressDTO initAddress (String rue, String complement, String lieuDit, String zip, String ville, String codeProvince, String pays) {
		PostalAddressDTO paDTO = new PostalAddressDTO();
		
		paDTO.setSno_et_rue(rue.toUpperCase());
		paDTO.setScomplement_adresse(complement.toUpperCase());
		paDTO.setSlocalite(lieuDit.toUpperCase());
		paDTO.setScode_postal(zip.toUpperCase());
		paDTO.setSville(ville.toUpperCase());
		paDTO.setScode_province(codeProvince.toUpperCase());
		paDTO.setScode_pays(pays.toUpperCase());
		
		return paDTO;
	}
	
	private void initUsage(PostalAddressDTO adr, String appli, String role, Integer num) {
		Set<Usage_mediumDTO> usageList = new HashSet<Usage_mediumDTO>();
		Usage_mediumDTO usage = new Usage_mediumDTO();
		usage.setScode_application(appli);
		usage.setSrole1(role);
		usage.setInum(num);
		usageList.add(usage);
		adr.setUsage_mediumdto(usageList);
	}
	
	@Test
	public void adrUtilTestSameAddress_noDifferenceOK() {
		PostalAddressDTO left = initAddress("45 rue de Paris", "", "", "95747", "Roissy", "", "FR");
		PostalAddressDTO right = initAddress("45 RUE de PARIS", "", "", "95747", "ROISSY", "", "fr");
		
		boolean result = PostalAddressUtil.sameAddress(left, right);
		
		Assert.assertNotNull(result);
		Assert.assertTrue(result);
	}
	
	@Test
	public void adrUtilTestSsameAddress_differentStreetNumberKO() {
		PostalAddressDTO left = initAddress("44 rue de Paris", "", "", "95747", "Roissy", "", "FR");
		PostalAddressDTO right = initAddress("45 RUE de PARIS", "", "", "95747", "ROISSY", "", "fr");
		
		boolean result = PostalAddressUtil.sameAddress(left, right);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}
	
	@Test
	public void adrUtilTestSsameAddress_differentComplementOK() {
		PostalAddressDTO left = initAddress("45 rue de Paris", "Gate 2", "", "95747", "Roissy", "", "FR");
		PostalAddressDTO right = initAddress("45 RUE de PARIS", "", "", "95747", "ROISSY", "", "fr");
		
		boolean result = PostalAddressUtil.sameAddress(left, right);
		
		Assert.assertNotNull(result);
		Assert.assertTrue(result);
	}
	
	@Test
	public void adrUtilTestSsameAddress_differentZipCodeKO() {
		PostalAddressDTO left = initAddress("45 rue de Paris", "", "", "95700", "Roissy", "", "FR");
		PostalAddressDTO right = initAddress("45 RUE de PARIS", "", "", "95747", "ROISSY", "", "fr");
		
		boolean result = PostalAddressUtil.sameAddress(left, right);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}
	
	@Test
	public void adrUtilTestSsameAddress_differentCityKO() {
		PostalAddressDTO left = initAddress("45 rue de Paris", "", "", "95747", "Roissy-En-France", "", "FR");
		PostalAddressDTO right = initAddress("45 RUE de PARIS", "", "", "95747", "ROISSY", "", "fr");
		
		boolean result = PostalAddressUtil.sameAddress(left, right);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}
	
	@Test
	public void adrUtilTestSsameAddress_differentCountryKO() {
		PostalAddressDTO left = initAddress("45 rue de Paris", "", "", "95747", "Roissy", "", "");
		PostalAddressDTO right = initAddress("45 RUE de PARIS", "", "", "95747", "ROISSY", "", "fr");
		
		boolean result = PostalAddressUtil.sameAddress(left, right);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result);
	}
}
