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

import java.util.ArrayList;
import java.util.Date;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RefComPrefDgtDSTest {

	@Autowired
	private RefComPrefDgtDS refComPrefDgtDS;
	
	@Test
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
		
		RefComPrefDgtDTO refComPrefDgtDTOFromDB = new RefComPrefDgtDTO();
		refComPrefDgtDTOFromDB.setRefComPrefDgtId(refComPrefDgtDTO.getRefComPrefDgtId());
		refComPrefDgtDTOFromDB = refComPrefDgtDS.get(refComPrefDgtDTOFromDB.getRefComPrefDgtId());
		
		Assert.assertEquals("Test creation simple", refComPrefDgtDTOFromDB.getDescription());
	}
	
	@Test
	@Rollback(true)
	public void testCreateFindWithMarketLanguage() throws JrafDomainException {
		
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
		refComPrefDgtDTO.setDescription("Test creation linked");
		refComPrefDgtDTO.setSignatureCreation("TESTMZ_C");
		refComPrefDgtDTO.setSignatureModification("TESTMZ_M");
		refComPrefDgtDTO.setSiteCreation("QVI_C");
		refComPrefDgtDTO.setSiteModification("QVI_M");
		
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
		
		refComPrefDgtDTO.setRefComPrefMls(new ArrayList<RefComPrefMlDTO>());
		refComPrefDgtDTO.getRefComPrefMls().add(refComPrefMlDTO);
		
		refComPrefDgtDS.createWithMarketLanguage(refComPrefDgtDTO);
		
		RefComPrefDgtDTO refComPrefDgtDTOFromDB = new RefComPrefDgtDTO();
		refComPrefDgtDTOFromDB = refComPrefDgtDS.getWithLinkedData(refComPrefDgtDTO.getRefComPrefDgtId());
		
		Assert.assertEquals("Test creation linked", refComPrefDgtDTOFromDB.getDescription());
		Assert.assertNotNull(refComPrefDgtDTOFromDB.getRefComPrefMls());
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
		
		refComPrefDgtDTO.setDescription("Test creation updated");
		
		refComPrefDgtDS.update(refComPrefDgtDTO);
				
		RefComPrefDgtDTO refComPrefDgtDTOFromDB = new RefComPrefDgtDTO();
		refComPrefDgtDTOFromDB = refComPrefDgtDS.get(refComPrefDgtDTO.getRefComPrefDgtId());
		
		Assert.assertEquals("Test creation updated", refComPrefDgtDTOFromDB.getDescription());
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
		refComPrefDgtDTO.setDescription("Test creation linked");
		refComPrefDgtDTO.setSignatureCreation("TESTMZ_C");
		refComPrefDgtDTO.setSignatureModification("TESTMZ_M");
		refComPrefDgtDTO.setSiteCreation("QVI_C");
		refComPrefDgtDTO.setSiteModification("QVI_M");
		
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
		
		refComPrefDgtDTO.setRefComPrefMls(new ArrayList<RefComPrefMlDTO>());
		refComPrefDgtDTO.getRefComPrefMls().add(refComPrefMlDTO);
		
		refComPrefDgtDS.createWithMarketLanguage(refComPrefDgtDTO);

		refComPrefDgtDS.delete(refComPrefDgtDTO);
		
		RefComPrefDgtDTO refComPrefDgtDTOFromDB = new RefComPrefDgtDTO();
		refComPrefDgtDTOFromDB = refComPrefDgtDS.get(refComPrefDgtDTO.getRefComPrefDgtId());

		Assert.assertNull(refComPrefDgtDTOFromDB);
	}
	
}
