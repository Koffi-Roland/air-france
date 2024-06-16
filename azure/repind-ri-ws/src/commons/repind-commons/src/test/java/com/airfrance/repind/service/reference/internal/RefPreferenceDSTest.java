package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.reference.RefPreferenceDataKeyDTO;
import com.airfrance.repind.dto.reference.RefPreferenceTypeDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RefPreferenceDSTest {

	@Autowired
	private RefPreferenceKeyTypeDS refPreferenceKeyTypeDS;
	
	@Autowired
	private RefPreferenceDataKeyDS refPreferenceDataKeyDS;
	
	@Autowired
	private RefPreferenceTypeDS refPreferenceTypeDS;
		
	/**
	 * REPIND-1124 - AC #2
	 **/
	@Test
	public void testBlueBizNumberKeyName() throws JrafDomainException {
		RefPreferenceDataKeyDTO example = new RefPreferenceDataKeyDTO();
		example.setCode("BLUEBIZNUMBER");
		
		RefPreferenceDataKeyDTO refPreferenceDataKeyDTO = refPreferenceDataKeyDS.get(example);
		
		Assert.assertEquals(refPreferenceDataKeyDTO.getNormalizedKey(), "bluebizNumber");
	}
	
	
	/**
	 * REPIND-1124 - AC #6
	 **/
	@Test
	public void testPreferenceTypeTUM() throws JrafDomainException {
		RefPreferenceTypeDTO example = new RefPreferenceTypeDTO();
		example.setCode("TUM");
		RefPreferenceTypeDTO result = refPreferenceTypeDS.get(example);
		
		Assert.assertNull(result);
	}
	
	/**
	 * REPIND-1124 - AC #5
	 **/
	@Test
	public void testPreferenceTypeTCP() throws JrafDomainException {
		RefPreferenceTypeDTO example = new RefPreferenceTypeDTO();
		example.setCode("TCP");
		RefPreferenceTypeDTO result = refPreferenceTypeDS.get(example);
		
		Assert.assertNull(result);
	}
	
	/**
	 * REPIND-1124 - AC #7
	 **/
	@Test
	public void testPreferenceTypeHDC() throws JrafDomainException {
		RefPreferenceTypeDTO example = new RefPreferenceTypeDTO();
		example.setCode("HDC");
		RefPreferenceTypeDTO result = refPreferenceTypeDS.get(example);
		
		Assert.assertNotNull(result);
	}
		
	/**
	 * REPIND-1124 - AC #9
	 **/
	@Test
	public void testPreferenceTypeTPC() throws JrafDomainException {
		RefPreferenceTypeDTO example = new RefPreferenceTypeDTO();
		example.setCode("TPC");
		RefPreferenceTypeDTO result = refPreferenceTypeDS.get(example);
		
		Assert.assertNotNull(result);
	}
	
		
	/**
	 * REPIND-1124 - AC #11
	 **/
	@Test
	public void testPreferenceTypePIC() throws JrafDomainException {
		RefPreferenceTypeDTO example = new RefPreferenceTypeDTO();
		example.setCode("PIC");
		RefPreferenceTypeDTO result = refPreferenceTypeDS.get(example);
		
		Assert.assertNotNull(result);
	}
		
	/**
	 * REPIND-1124 - AC #13
	 **/
	@Test
	public void testPreferenceTypeTCC() throws JrafDomainException {
		RefPreferenceTypeDTO example = new RefPreferenceTypeDTO();
		example.setCode("TCC");
		RefPreferenceTypeDTO result = refPreferenceTypeDS.get(example);
		
		Assert.assertNotNull(result);
	}
		
	/**
	 * REPIND-1124 - AC #15
	 **/
	@Test
	public void testPreferenceTypeECC() throws JrafDomainException {
		RefPreferenceTypeDTO example = new RefPreferenceTypeDTO();
		example.setCode("ECC");
		RefPreferenceTypeDTO result = refPreferenceTypeDS.get(example);
		
		Assert.assertNotNull(result);
	}
		
	/**
	 * REPIND-1222
	 **/
	@Test
	public void testPreferenceTypeGPC() throws JrafDomainException {
		RefPreferenceTypeDTO example = new RefPreferenceTypeDTO();
		example.setCode("GPC");
		RefPreferenceTypeDTO result = refPreferenceTypeDS.get(example);
		
		Assert.assertNotNull(result);
	}
}
