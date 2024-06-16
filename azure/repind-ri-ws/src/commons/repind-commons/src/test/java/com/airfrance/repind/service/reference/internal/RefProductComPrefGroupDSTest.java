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
import java.util.List;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RefProductComPrefGroupDSTest {

	@Autowired
	private RefComPrefGroupInfoDS refComPrefGroupInfoDS;
	
	@Autowired
	private RefComPrefDgtDS refComPrefDgtDS;
	
	@Autowired
	private RefComPrefGroupDS refComPrefGroupDS;
	
	@Autowired
	private RefProductComPrefGroupDS refProductComPrefGroupDS;
	
	@Autowired
	private RefProductDS refProductDS;

	@Test
	@Rollback(true)	
	public void testCreateFind() throws JrafDomainException {
		
		RefComPrefGroupInfoDTO refComPrefGroupInfo = new RefComPrefGroupInfoDTO();
		
		refComPrefGroupInfo.setCode("MANDATORY_FB");
		refComPrefGroupInfo.setDateCreation(new Date());
		refComPrefGroupInfo.setDateModification(new Date());
		refComPrefGroupInfo.setDefaultOption("Y");
		refComPrefGroupInfo.setLibelleEN("Blabla EN");
		refComPrefGroupInfo.setLibelleFR("Blabla FR");
		refComPrefGroupInfo.setMandatoryOption("Y");
		refComPrefGroupInfo.setSignatureCreation("TESTMZ_C");
		refComPrefGroupInfo.setSignatureModification("TESTMZ_M");
		refComPrefGroupInfo.setSiteCreation("QVI_C");
		refComPrefGroupInfo.setSiteModification("QVI_M");

		refComPrefGroupInfoDS.create(refComPrefGroupInfo);
		
		RefComPrefDomainDTO refComPrefDomain = new RefComPrefDomainDTO();
		refComPrefDomain.setCodeDomain("F");
		
		RefComPrefGTypeDTO refComPrefGType = new RefComPrefGTypeDTO();
		refComPrefGType.setCodeGType("C");
		
		RefComPrefTypeDTO refComPrefType = new RefComPrefTypeDTO();
		refComPrefType.setCodeType("FB_PROG");
		
		RefComPrefDgtDTO refComPrefDgt = new RefComPrefDgtDTO();
		refComPrefDgt.setDomain(refComPrefDomain);
		refComPrefDgt.setGroupType(refComPrefGType);
		refComPrefDgt.setType(refComPrefType);
		refComPrefDgt.setDateCreation(new Date());
		refComPrefDgt.setDateModification(new Date());
		refComPrefDgt.setDescription("Test creation simple");
		refComPrefDgt.setSignatureCreation("TESTMZ_C");
		refComPrefDgt.setSignatureModification("TESTMZ_M");
		refComPrefDgt.setSiteCreation("QVI_C");
		refComPrefDgt.setSiteModification("QVI_M");
		
		refComPrefDgtDS.create(refComPrefDgt);
		
		
		RefComPrefGroupIdDTO refComPrefGroupId = new RefComPrefGroupIdDTO();
		refComPrefGroupId.setRefComPrefGroupInfo(refComPrefGroupInfo);
		refComPrefGroupId.setRefComPrefDgt(refComPrefDgt);
		
		RefComPrefGroupDTO refComPrefGroup = new RefComPrefGroupDTO();
		refComPrefGroup.setDateCreation(new Date());
		refComPrefGroup.setDateModification(new Date());
		refComPrefGroup.setRefComPrefGroupId(refComPrefGroupId);
		refComPrefGroup.setSignatureCreation("TESTMZ_C");
		refComPrefGroup.setSignatureModification("TESTMZ_M");
		refComPrefGroup.setSiteCreation("QVI_C");
		refComPrefGroup.setSiteModification("QVI_M");
		
		refComPrefGroupDS.create(refComPrefGroup);
		RefProductDTO example = new RefProductDTO();
		example.setProductId(1);
		RefProductDTO refProduct = refProductDS.get(example);
		
		RefProductComPrefGroupIdDTO refProductComPrefGroupId = new RefProductComPrefGroupIdDTO();
		refProductComPrefGroupId.setRefComPrefGroupInfo(refComPrefGroupInfo);
		refProductComPrefGroupId.setRefProduct(refProduct);
		
		RefProductComPrefGroupDTO refProductComPrefGroup = new RefProductComPrefGroupDTO();
		refProductComPrefGroup.setRefProductComPrefGroupId(refProductComPrefGroupId);
		refProductComPrefGroup.setDateCreation(new Date());
		refProductComPrefGroup.setDateModification(new Date());
		refProductComPrefGroup.setSignatureCreation("TESTMZ_C");
		refProductComPrefGroup.setSignatureModification("TESTMZ_M");
		refProductComPrefGroup.setSiteCreation("QVI_C");
		refProductComPrefGroup.setSiteModification("QVI_M");
		
		refProductComPrefGroupDS.create(refProductComPrefGroup);

		
		RefProductComPrefGroupDTO refProductComPrefGroupFromDB = refProductComPrefGroupDS.getById(refProductComPrefGroupId);
		
		Assert.assertNotNull(refProductComPrefGroupFromDB);
	}
	
	
	@Test
	@Rollback(true)	
	public void testGetAllRefComPrefGroupByProductId() throws JrafDomainException {
		
		RefComPrefGroupInfoDTO refComPrefGroupInfo = new RefComPrefGroupInfoDTO();
		
		refComPrefGroupInfo.setCode("MANDATORY_FB");
		refComPrefGroupInfo.setDateCreation(new Date());
		refComPrefGroupInfo.setDateModification(new Date());
		refComPrefGroupInfo.setDefaultOption("Y");
		refComPrefGroupInfo.setLibelleEN("Blabla EN");
		refComPrefGroupInfo.setLibelleFR("Blabla FR");
		refComPrefGroupInfo.setMandatoryOption("Y");
		refComPrefGroupInfo.setSignatureCreation("TESTMZ_C");
		refComPrefGroupInfo.setSignatureModification("TESTMZ_M");
		refComPrefGroupInfo.setSiteCreation("QVI_C");
		refComPrefGroupInfo.setSiteModification("QVI_M");

		refComPrefGroupInfoDS.create(refComPrefGroupInfo);
		
		RefComPrefDomainDTO refComPrefDomain = new RefComPrefDomainDTO();
		refComPrefDomain.setCodeDomain("F");
		
		RefComPrefGTypeDTO refComPrefGType = new RefComPrefGTypeDTO();
		refComPrefGType.setCodeGType("C");
		
		RefComPrefTypeDTO refComPrefType = new RefComPrefTypeDTO();
		refComPrefType.setCodeType("FB_PROG");
		
		RefComPrefDgtDTO refComPrefDgt = new RefComPrefDgtDTO();
		refComPrefDgt.setDomain(refComPrefDomain);
		refComPrefDgt.setGroupType(refComPrefGType);
		refComPrefDgt.setType(refComPrefType);
		refComPrefDgt.setDateCreation(new Date());
		refComPrefDgt.setDateModification(new Date());
		refComPrefDgt.setDescription("Test creation simple");
		refComPrefDgt.setSignatureCreation("TESTMZ_C");
		refComPrefDgt.setSignatureModification("TESTMZ_M");
		refComPrefDgt.setSiteCreation("QVI_C");
		refComPrefDgt.setSiteModification("QVI_M");
		
		refComPrefDgtDS.create(refComPrefDgt);
		
		
		RefComPrefGroupIdDTO refComPrefGroupId = new RefComPrefGroupIdDTO();
		refComPrefGroupId.setRefComPrefGroupInfo(refComPrefGroupInfo);
		refComPrefGroupId.setRefComPrefDgt(refComPrefDgt);
		
		RefComPrefGroupDTO refComPrefGroup = new RefComPrefGroupDTO();
		refComPrefGroup.setDateCreation(new Date());
		refComPrefGroup.setDateModification(new Date());
		refComPrefGroup.setRefComPrefGroupId(refComPrefGroupId);
		refComPrefGroup.setSignatureCreation("TESTMZ_C");
		refComPrefGroup.setSignatureModification("TESTMZ_M");
		refComPrefGroup.setSiteCreation("QVI_C");
		refComPrefGroup.setSiteModification("QVI_M");
		
		refComPrefGroupDS.create(refComPrefGroup);
		RefProductDTO example = new RefProductDTO();
		example.setProductId(1);
		RefProductDTO refProduct = refProductDS.get(example);
		
		RefProductComPrefGroupIdDTO refProductComPrefGroupId = new RefProductComPrefGroupIdDTO();
		refProductComPrefGroupId.setRefComPrefGroupInfo(refComPrefGroupInfo);
		refProductComPrefGroupId.setRefProduct(refProduct);
		
		RefProductComPrefGroupDTO refProductComPrefGroup = new RefProductComPrefGroupDTO();
		refProductComPrefGroup.setRefProductComPrefGroupId(refProductComPrefGroupId);
		refProductComPrefGroup.setDateCreation(new Date());
		refProductComPrefGroup.setDateModification(new Date());
		refProductComPrefGroup.setSignatureCreation("TESTMZ_C");
		refProductComPrefGroup.setSignatureModification("TESTMZ_M");
		refProductComPrefGroup.setSiteCreation("QVI_C");
		refProductComPrefGroup.setSiteModification("QVI_M");
		
		refProductComPrefGroupDS.create(refProductComPrefGroup);

		
		List<RefComPrefGroupDTO> listRefComPrefGroup = refProductComPrefGroupDS.getAllRefComPrefGroupByProductId(refProduct);
		
		Assert.assertEquals(1, listRefComPrefGroup.size());
	}
		
	
	@Test
	@Rollback(true)	
	public void testDelete() throws JrafDomainException {
		
		RefComPrefGroupInfoDTO refComPrefGroupInfo = new RefComPrefGroupInfoDTO();
		
		refComPrefGroupInfo.setCode("MANDATORY_FB");
		refComPrefGroupInfo.setDateCreation(new Date());
		refComPrefGroupInfo.setDateModification(new Date());
		refComPrefGroupInfo.setDefaultOption("Y");
		refComPrefGroupInfo.setLibelleEN("Blabla EN");
		refComPrefGroupInfo.setLibelleFR("Blabla FR");
		refComPrefGroupInfo.setMandatoryOption("Y");
		refComPrefGroupInfo.setSignatureCreation("TESTMZ_C");
		refComPrefGroupInfo.setSignatureModification("TESTMZ_M");
		refComPrefGroupInfo.setSiteCreation("QVI_C");
		refComPrefGroupInfo.setSiteModification("QVI_M");

		refComPrefGroupInfoDS.create(refComPrefGroupInfo);
		
		RefComPrefDomainDTO refComPrefDomain = new RefComPrefDomainDTO();
		refComPrefDomain.setCodeDomain("F");
		
		RefComPrefGTypeDTO refComPrefGType = new RefComPrefGTypeDTO();
		refComPrefGType.setCodeGType("C");
		
		RefComPrefTypeDTO refComPrefType = new RefComPrefTypeDTO();
		refComPrefType.setCodeType("FB_PROG");
		
		RefComPrefDgtDTO refComPrefDgt = new RefComPrefDgtDTO();
		refComPrefDgt.setDomain(refComPrefDomain);
		refComPrefDgt.setGroupType(refComPrefGType);
		refComPrefDgt.setType(refComPrefType);
		refComPrefDgt.setDateCreation(new Date());
		refComPrefDgt.setDateModification(new Date());
		refComPrefDgt.setDescription("Test creation simple");
		refComPrefDgt.setSignatureCreation("TESTMZ_C");
		refComPrefDgt.setSignatureModification("TESTMZ_M");
		refComPrefDgt.setSiteCreation("QVI_C");
		refComPrefDgt.setSiteModification("QVI_M");
		
		refComPrefDgtDS.create(refComPrefDgt);
		
		
		RefComPrefGroupIdDTO refComPrefGroupId = new RefComPrefGroupIdDTO();
		refComPrefGroupId.setRefComPrefGroupInfo(refComPrefGroupInfo);
		refComPrefGroupId.setRefComPrefDgt(refComPrefDgt);
		
		RefComPrefGroupDTO refComPrefGroup = new RefComPrefGroupDTO();
		refComPrefGroup.setDateCreation(new Date());
		refComPrefGroup.setDateModification(new Date());
		refComPrefGroup.setRefComPrefGroupId(refComPrefGroupId);
		refComPrefGroup.setSignatureCreation("TESTMZ_C");
		refComPrefGroup.setSignatureModification("TESTMZ_M");
		refComPrefGroup.setSiteCreation("QVI_C");
		refComPrefGroup.setSiteModification("QVI_M");
		
		refComPrefGroupDS.create(refComPrefGroup);
		RefProductDTO example = new RefProductDTO();
		example.setProductId(1);
		RefProductDTO refProduct = refProductDS.get(example);
		
		RefProductComPrefGroupIdDTO refProductComPrefGroupId = new RefProductComPrefGroupIdDTO();
		refProductComPrefGroupId.setRefComPrefGroupInfo(refComPrefGroupInfo);
		refProductComPrefGroupId.setRefProduct(refProduct);
		
		RefProductComPrefGroupDTO refProductComPrefGroup = new RefProductComPrefGroupDTO();
		refProductComPrefGroup.setRefProductComPrefGroupId(refProductComPrefGroupId);
		refProductComPrefGroup.setDateCreation(new Date());
		refProductComPrefGroup.setDateModification(new Date());
		refProductComPrefGroup.setSignatureCreation("TESTMZ_C");
		refProductComPrefGroup.setSignatureModification("TESTMZ_M");
		refProductComPrefGroup.setSiteCreation("QVI_C");
		refProductComPrefGroup.setSiteModification("QVI_M");
		
		refProductComPrefGroupDS.create(refProductComPrefGroup);

		refProductComPrefGroupDS.delete(refProductComPrefGroup);
		
		RefProductComPrefGroupDTO refProductComPrefGroupFromDB = refProductComPrefGroupDS.getById(refProductComPrefGroupId);
		
		Assert.assertNull(refProductComPrefGroupFromDB);
	}
	
}
