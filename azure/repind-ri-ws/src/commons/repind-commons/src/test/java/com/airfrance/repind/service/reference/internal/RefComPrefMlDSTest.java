package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.reference.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RefComPrefMlDSTest {

	@Autowired
	private RefComPrefDgtDS refComPrefDgtDS;
	
	@Autowired
	private RefComPrefMlDS refComPrefMlDS;

	@Test(expected = Test.None.class /* no exception expected */)
	@Rollback(true)
	public void testCreateFind() throws JrafDomainException {
		
		RefComPrefDomainDTO refComPrefDomainDTO = new RefComPrefDomainDTO();
		refComPrefDomainDTO.setCodeDomain("F");
		
		RefComPrefGTypeDTO refComPrefGTypeDTO = new RefComPrefGTypeDTO();
		refComPrefGTypeDTO.setCodeGType("C");
		
		RefComPrefTypeDTO refComPrefTypeDTO = new RefComPrefTypeDTO();
		refComPrefTypeDTO.setCodeType("FB_PROG");
		
		RefComPrefDgtDTO refComPrefDgtDTO = new RefComPrefDgtDTO();
		refComPrefDgtDTO.setDomain(refComPrefDomainDTO);
		refComPrefDgtDTO.setGroupType(refComPrefGTypeDTO);
		refComPrefDgtDTO.setType(refComPrefTypeDTO);
		refComPrefDgtDTO.setDateCreation(new Date());
		refComPrefDgtDTO.setDateModification(new Date());
		refComPrefDgtDTO.setDescription("Test creation simple");
		refComPrefDgtDTO.setSignatureCreation("TESTMZ_C");
		refComPrefDgtDTO.setSignatureModification("TESTMZ_M");
		refComPrefDgtDTO.setSiteCreation("QVI_C");
		refComPrefDgtDTO.setSiteModification("QVI_M");
		
		refComPrefDgtDS.create(refComPrefDgtDTO);
		
		RefComPrefMlDTO refComPrefMlDTO = new RefComPrefMlDTO();
		refComPrefMlDTO.setDateCreation(new Date());
		refComPrefMlDTO.setDateModification(new Date());
		refComPrefMlDTO.setDefaultLanguage1("FR");
		refComPrefMlDTO.setFieldA("N");
		refComPrefMlDTO.setFieldN("N");
		refComPrefMlDTO.setFieldT("N");
		refComPrefMlDTO.setMandatoryOption("O");
		refComPrefMlDTO.setMarket("FR");
		refComPrefMlDTO.setMedia("E");
		refComPrefMlDTO.setSignatureCreation("TESTMZ_C");
		refComPrefMlDTO.setSignatureModification("TESTMZ_M");
		refComPrefMlDTO.setSiteCreation("QVI_C");
		refComPrefMlDTO.setSiteModification("QVI_M");
		refComPrefMlDTO.setRefComPrefDgt(refComPrefDgtDTO);
		
		refComPrefMlDS.create(refComPrefMlDTO);
	}
		
	
	@Test
	@Rollback(true)	
	public void testUpdate() throws JrafDomainException {
		
		RefComPrefDomainDTO refComPrefDomainDTO = new RefComPrefDomainDTO();
		refComPrefDomainDTO.setCodeDomain("F");
		
		RefComPrefGTypeDTO refComPrefGTypeDTO = new RefComPrefGTypeDTO();
		refComPrefGTypeDTO.setCodeGType("C");
		
		RefComPrefTypeDTO refComPrefTypeDTO = new RefComPrefTypeDTO();
		refComPrefTypeDTO.setCodeType("FB_PROG");
		
		RefComPrefDgtDTO refComPrefDgtDTO = new RefComPrefDgtDTO();
		refComPrefDgtDTO.setDomain(refComPrefDomainDTO);
		refComPrefDgtDTO.setGroupType(refComPrefGTypeDTO);
		refComPrefDgtDTO.setType(refComPrefTypeDTO);
		refComPrefDgtDTO.setDateCreation(new Date());
		refComPrefDgtDTO.setDateModification(new Date());
		refComPrefDgtDTO.setDescription("Test creation simple");
		refComPrefDgtDTO.setSignatureCreation("TESTMZ_C");
		refComPrefDgtDTO.setSignatureModification("TESTMZ_M");
		refComPrefDgtDTO.setSiteCreation("QVI_C");
		refComPrefDgtDTO.setSiteModification("QVI_M");
		
		refComPrefDgtDS.create(refComPrefDgtDTO);
		
		RefComPrefMlDTO refComPrefMlDTO = new RefComPrefMlDTO();
		refComPrefMlDTO.setDateCreation(new Date());
		refComPrefMlDTO.setDateModification(new Date());
		refComPrefMlDTO.setDefaultLanguage1("FR");
		refComPrefMlDTO.setFieldA("N");
		refComPrefMlDTO.setFieldN("N");
		refComPrefMlDTO.setFieldT("N");
		refComPrefMlDTO.setMandatoryOption("O");
		refComPrefMlDTO.setMarket("FR");
		refComPrefMlDTO.setMedia("E");
		refComPrefMlDTO.setSignatureCreation("TESTMZ_C");
		refComPrefMlDTO.setSignatureModification("TESTMZ_M");
		refComPrefMlDTO.setSiteCreation("QVI_C");
		refComPrefMlDTO.setSiteModification("QVI_M");
		refComPrefMlDTO.setRefComPrefDgt(refComPrefDgtDTO);
		
		refComPrefMlDS.create(refComPrefMlDTO);
		
		refComPrefMlDTO.setMarket("EN");
		refComPrefMlDTO.setDefaultLanguage1("EN");
		
		refComPrefMlDS.update(refComPrefMlDTO);
		
		RefComPrefMlDTO refComPrefMlDTOFromDB = new RefComPrefMlDTO();
		refComPrefMlDTOFromDB = refComPrefMlDS.getById(refComPrefMlDTO.getRefComPrefMlId());
		
		Assert.assertEquals("EN", refComPrefMlDTOFromDB.getMarket());
		Assert.assertEquals("EN", refComPrefMlDTOFromDB.getDefaultLanguage1());
	}
	
	@Test
	@Rollback(true)
	public void testDelete() throws JrafDomainException {
		
		RefComPrefDomainDTO refComPrefDomainDTO = new RefComPrefDomainDTO();
		refComPrefDomainDTO.setCodeDomain("F");
		
		RefComPrefGTypeDTO refComPrefGTypeDTO = new RefComPrefGTypeDTO();
		refComPrefGTypeDTO.setCodeGType("C");
		
		RefComPrefTypeDTO refComPrefTypeDTO = new RefComPrefTypeDTO();
		refComPrefTypeDTO.setCodeType("FB_PROG");
		
		RefComPrefDgtDTO refComPrefDgtDTO = new RefComPrefDgtDTO();
		refComPrefDgtDTO.setDomain(refComPrefDomainDTO);
		refComPrefDgtDTO.setGroupType(refComPrefGTypeDTO);
		refComPrefDgtDTO.setType(refComPrefTypeDTO);
		refComPrefDgtDTO.setDateCreation(new Date());
		refComPrefDgtDTO.setDateModification(new Date());
		refComPrefDgtDTO.setDescription("Test creation simple");
		refComPrefDgtDTO.setSignatureCreation("TESTMZ_C");
		refComPrefDgtDTO.setSignatureModification("TESTMZ_M");
		refComPrefDgtDTO.setSiteCreation("QVI_C");
		refComPrefDgtDTO.setSiteModification("QVI_M");
		
		refComPrefDgtDS.create(refComPrefDgtDTO);
		
		RefComPrefMlDTO refComPrefMlDTO = new RefComPrefMlDTO();
		refComPrefMlDTO.setDateCreation(new Date());
		refComPrefMlDTO.setDateModification(new Date());
		refComPrefMlDTO.setDefaultLanguage1("FR");
		refComPrefMlDTO.setFieldA("N");
		refComPrefMlDTO.setFieldN("N");
		refComPrefMlDTO.setFieldT("N");
		refComPrefMlDTO.setMandatoryOption("O");
		refComPrefMlDTO.setMarket("FR");
		refComPrefMlDTO.setMedia("E");
		refComPrefMlDTO.setSignatureCreation("TESTMZ_C");
		refComPrefMlDTO.setSignatureModification("TESTMZ_M");
		refComPrefMlDTO.setSiteCreation("QVI_C");
		refComPrefMlDTO.setSiteModification("QVI_M");
		refComPrefMlDTO.setRefComPrefDgt(refComPrefDgtDTO);
		
		refComPrefMlDS.create(refComPrefMlDTO);
		
		refComPrefMlDS.delete(refComPrefMlDTO);
		
		RefComPrefMlDTO refComPrefMlDTOFromDB = new RefComPrefMlDTO();
		refComPrefMlDTOFromDB = refComPrefMlDS.getById(refComPrefMlDTO.getRefComPrefMlId());
		
		Assert.assertNull(refComPrefMlDTOFromDB);
	}
}
