package com.airfrance.repind.service.ws.internal.ut.helper;

import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.ws.PostalAddressContentDTO;
import com.airfrance.repind.dto.ws.PostalAddressPropertiesDTO;
import com.airfrance.repind.dto.ws.PostalAddressRequestDTO;
import com.airfrance.repind.service.ws.internal.helpers.PostalAddressHelper;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;



public class PostalAddressHelperTest extends PostalAddressHelper {
	
	@Before
	public void setUp() {
//		postalAddressDS = EasyMock.createMock(IPostalAddressDS.class);
	}
	
	@Test
	public void test_upcastPostalAddressFieldSuccessOK1() {
		PostalAddressRequestDTO parDTO = initNewAddress();
		upcastPostalAddressField(parDTO);
		
		Assert.assertEquals(parDTO.getPostalAddressContentDTO().getAdditionalInformation().toUpperCase(), parDTO.getPostalAddressContentDTO().getAdditionalInformation());
		Assert.assertEquals(parDTO.getPostalAddressContentDTO().getNumberAndStreet().toUpperCase(), parDTO.getPostalAddressContentDTO().getNumberAndStreet());
		Assert.assertEquals(parDTO.getPostalAddressContentDTO().getCity().toUpperCase(), parDTO.getPostalAddressContentDTO().getCity());
		Assert.assertEquals(parDTO.getPostalAddressContentDTO().getZipCode().toUpperCase(), parDTO.getPostalAddressContentDTO().getZipCode());
		Assert.assertEquals(parDTO.getPostalAddressContentDTO().getCountryCode().toUpperCase(), parDTO.getPostalAddressContentDTO().getCountryCode());
		Assert.assertEquals(parDTO.getPostalAddressPropertiesDTO().getMediumCode().toUpperCase(), parDTO.getPostalAddressPropertiesDTO().getMediumCode());
		Assert.assertEquals(parDTO.getPostalAddressPropertiesDTO().getMediumStatus().toUpperCase(), parDTO.getPostalAddressPropertiesDTO().getMediumStatus());
	}
	
	@Test
	public void test_upcastPostalAddressFieldOK2() {
		PostalAddressRequestDTO parDTO = initNewAddress();
		upcastPostalAddressField(parDTO);
		
		if (!NumberUtils.isNumber(parDTO.getPostalAddressContentDTO().getAdditionalInformation())) {
			Assert.assertNotEquals(parDTO.getPostalAddressContentDTO().getAdditionalInformation().toLowerCase(), parDTO.getPostalAddressContentDTO().getAdditionalInformation());
		}
		Assert.assertNotEquals(parDTO.getPostalAddressContentDTO().getNumberAndStreet().toLowerCase(), parDTO.getPostalAddressContentDTO().getNumberAndStreet());
		Assert.assertNotEquals(parDTO.getPostalAddressContentDTO().getCity().toLowerCase(), parDTO.getPostalAddressContentDTO().getCity());
		if (!NumberUtils.isNumber(parDTO.getPostalAddressContentDTO().getZipCode())) {
			Assert.assertNotEquals(parDTO.getPostalAddressContentDTO().getZipCode().toLowerCase(), parDTO.getPostalAddressContentDTO().getZipCode());
		}
		Assert.assertNotEquals(parDTO.getPostalAddressContentDTO().getCountryCode().toLowerCase(), parDTO.getPostalAddressContentDTO().getCountryCode());
		Assert.assertNotEquals(parDTO.getPostalAddressPropertiesDTO().getMediumCode().toLowerCase(), parDTO.getPostalAddressPropertiesDTO().getMediumCode());
		Assert.assertNotEquals(parDTO.getPostalAddressPropertiesDTO().getMediumStatus().toLowerCase(), parDTO.getPostalAddressPropertiesDTO().getMediumStatus());
	}

	@Test
	public void test_usageExistOK1() {
		PostalAddressDTO adr = new PostalAddressDTO();
		Usage_mediumDTO usg = new Usage_mediumDTO();
		usg.setInum(01);
		usg.setScode_application("ISI");
		usg.setSrole1("M");
		Set<Usage_mediumDTO> usageList = new HashSet<Usage_mediumDTO>();
		usageList.add(usg);
		adr.setUsage_mediumdto(usageList);
		
		boolean result = this.usageExists(adr, 01, "ISI");
		Assert.assertTrue(result);
	}
	
	@Test
	public void test_usageExistOK2() {
		PostalAddressDTO adr = new PostalAddressDTO();
		Usage_mediumDTO usg = new Usage_mediumDTO();
		usg.setScode_application("ISI");
		usg.setSrole1("M");
		Set<Usage_mediumDTO> usageList = new HashSet<Usage_mediumDTO>();
		usageList.add(usg);
		adr.setUsage_mediumdto(usageList);
		
		boolean result = this.usageExists(adr, null, "ISI");
		Assert.assertTrue(result);
	}
	
	@Test
	public void test_usageExistOK3() {
		PostalAddressDTO adr = new PostalAddressDTO();
		Usage_mediumDTO usg = new Usage_mediumDTO();
		usg.setInum(02);
		usg.setScode_application("BDC");
		Set<Usage_mediumDTO> usageList = new HashSet<Usage_mediumDTO>();
		usageList.add(usg);
		adr.setUsage_mediumdto(usageList);
		
		boolean result = this.usageExists(adr, null, "BDC");
		Assert.assertTrue(result);
	}
	
	@Test
	public void test_usageExistKO1() {
		PostalAddressDTO adr = new PostalAddressDTO();
		Usage_mediumDTO usg = new Usage_mediumDTO();
		usg.setInum(02);
		usg.setScode_application("BDC");
		Set<Usage_mediumDTO> usageList = new HashSet<Usage_mediumDTO>();
		usageList.add(usg);
		adr.setUsage_mediumdto(usageList);
		
		boolean result = this.usageExists(adr, 01, "BDC");
		Assert.assertFalse(result);
	}
	
	@Test
	public void test_usageExistKO2() {
		PostalAddressDTO adr = new PostalAddressDTO();
		Usage_mediumDTO usg = new Usage_mediumDTO();
		usg.setScode_application("BDC");
		Set<Usage_mediumDTO> usageList = new HashSet<Usage_mediumDTO>();
		usageList.add(usg);
		adr.setUsage_mediumdto(usageList);
		
		boolean result = this.usageExists(adr, 01, "BDC");
		Assert.assertFalse(result);
	}
	
	private PostalAddressRequestDTO initNewAddress() {
		PostalAddressRequestDTO parDTO = new PostalAddressRequestDTO();
		PostalAddressPropertiesDTO papDTO = new PostalAddressPropertiesDTO();
		PostalAddressContentDTO pacDTO = new PostalAddressContentDTO();
		parDTO.setPostalAddressContentDTO(pacDTO);
		parDTO.setPostalAddressPropertiesDTO(papDTO);
		
		pacDTO.setAdditionalInformation("test");
		pacDTO.setNumberAndStreet("45 rue de Paris");
		pacDTO.setCity("Roissy");
		pacDTO.setZipCode("95747");
		pacDTO.setCountryCode("fr");
		
		papDTO.setMediumCode("d");
		papDTO.setMediumStatus("v");
		
		return parDTO;
	}
}
