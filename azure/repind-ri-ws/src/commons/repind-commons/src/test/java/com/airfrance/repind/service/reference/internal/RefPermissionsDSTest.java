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
public class RefPermissionsDSTest {

	@Autowired
	private RefPermissionsDS refPermissionsDS;
	
	@Autowired
	private RefComPrefDgtDS refComPrefDgtDS;
	
	@Autowired
	private RefPermissionsQuestionDS refPermissionsQuestionDS;
	
	@Test
	@Rollback(true)
	public void testCreateFind() throws JrafDomainException {
		
		RefComPrefDomainDTO refComPrefDomain = new RefComPrefDomainDTO();
		refComPrefDomain.setCodeDomain("F");
		
		RefComPrefGTypeDTO refComPrefGType = new RefComPrefGTypeDTO();
		refComPrefGType.setCodeGType("C");
		
		RefComPrefTypeDTO refComPrefType = new RefComPrefTypeDTO();
		refComPrefType.setCodeType("FB_CC");

		RefComPrefDgtDTO refComPrefDgt = new RefComPrefDgtDTO();
		refComPrefDgt.setDomain(refComPrefDomain);
		refComPrefDgt.setGroupType(refComPrefGType);
		refComPrefDgt.setType(refComPrefType);
		refComPrefDgt.setDescription("Blabla");
		refComPrefDgt.setDateCreation(new Date());
		refComPrefDgt.setSignatureCreation("TESTMZ_C");
		refComPrefDgt.setSiteCreation("QVI_C");
		refComPrefDgt.setDateModification(new Date());
		refComPrefDgt.setSignatureModification("TESTMZ_M");
		refComPrefDgt.setSiteModification("QVI_M");
		refComPrefDgtDS.create(refComPrefDgt);
		
		RefPermissionsQuestionDTO refPermissionsQuestion = new RefPermissionsQuestionDTO();
		refPermissionsQuestion.setDateCreation(new Date());
		refPermissionsQuestion.setName("FLYING_BLUE_X");
		refPermissionsQuestion.setQuestion("Blabla en FR");
		refPermissionsQuestion.setQuestionEN("Blabla en En");
		refPermissionsQuestion.setSignatureCreation("TESTMZ_C");
		refPermissionsQuestion.setSiteCreation("QVI_C");
		refPermissionsQuestionDS.create(refPermissionsQuestion);
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTMZ_C");
		refPermissions.setSiteCreation("QVI_C");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTMZ_M");
		refPermissions.setSiteModification("QVI_M");
		
		refPermissionsDS.create(refPermissions);
		
		RefPermissionsDTO refPermissionsFromDB = refPermissionsDS.getById(refPermissionsId);
		
		Assert.assertNotNull(refPermissionsFromDB.getRefPermissionsId().getQuestionId());
		Assert.assertNotNull(refPermissionsFromDB.getRefPermissionsId().getRefComPrefDgt());
	}
	
	@Test
	@Rollback(true)	
	public void testDelete() throws JrafDomainException {
		
		RefComPrefDomainDTO refComPrefDomain = new RefComPrefDomainDTO();
		refComPrefDomain.setCodeDomain("F");
		
		RefComPrefGTypeDTO refComPrefGType = new RefComPrefGTypeDTO();
		refComPrefGType.setCodeGType("C");
		
		RefComPrefTypeDTO refComPrefType = new RefComPrefTypeDTO();
		refComPrefType.setCodeType("FB_CC");

		RefComPrefDgtDTO refComPrefDgt = new RefComPrefDgtDTO();
		refComPrefDgt.setDomain(refComPrefDomain);
		refComPrefDgt.setGroupType(refComPrefGType);
		refComPrefDgt.setType(refComPrefType);
		refComPrefDgt.setDescription("Blabla");
		refComPrefDgt.setDateCreation(new Date());
		refComPrefDgt.setSignatureCreation("TESTMZ_C");
		refComPrefDgt.setSiteCreation("QVI_C");
		refComPrefDgt.setDateModification(new Date());
		refComPrefDgt.setSignatureModification("TESTMZ_M");
		refComPrefDgt.setSiteModification("QVI_M");
		refComPrefDgtDS.create(refComPrefDgt);
		
		RefPermissionsQuestionDTO refPermissionsQuestion = new RefPermissionsQuestionDTO();
		refPermissionsQuestion.setDateCreation(new Date());
		refPermissionsQuestion.setName("FLYING_BLUE_X");
		refPermissionsQuestion.setQuestion("Blabla en FR");
		refPermissionsQuestion.setQuestionEN("Blabla en En");
		refPermissionsQuestion.setSignatureCreation("TESTMZ_C");
		refPermissionsQuestion.setSiteCreation("QVI_C");
		refPermissionsQuestionDS.create(refPermissionsQuestion);
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTMZ_C");
		refPermissions.setSiteCreation("QVI_C");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTMZ_M");
		refPermissions.setSiteModification("QVI_M");
		
		refPermissionsDS.create(refPermissions);
		
		refPermissionsDS.delete(refPermissions);
		
		RefPermissionsDTO refPermissionsFromDB = refPermissionsDS.getById(refPermissionsId);
		
		Assert.assertNull(refPermissionsFromDB);
	}
	
	
	@Test
	@Rollback(true)
	public void testGetAllComPrefDGTByPermissionsQuestionId() throws JrafDomainException {
		
		RefComPrefDomainDTO refComPrefDomain = new RefComPrefDomainDTO();
		refComPrefDomain.setCodeDomain("F");
		
		RefComPrefGTypeDTO refComPrefGType = new RefComPrefGTypeDTO();
		refComPrefGType.setCodeGType("C");
		
		RefComPrefTypeDTO refComPrefType = new RefComPrefTypeDTO();
		refComPrefType.setCodeType("FB_CC");

		RefComPrefDgtDTO refComPrefDgt = new RefComPrefDgtDTO();
		refComPrefDgt.setDomain(refComPrefDomain);
		refComPrefDgt.setGroupType(refComPrefGType);
		refComPrefDgt.setType(refComPrefType);
		refComPrefDgt.setDescription("Blabla");
		refComPrefDgt.setDateCreation(new Date());
		refComPrefDgt.setSignatureCreation("TESTMZ_C");
		refComPrefDgt.setSiteCreation("QVI_C");
		refComPrefDgt.setDateModification(new Date());
		refComPrefDgt.setSignatureModification("TESTMZ_M");
		refComPrefDgt.setSiteModification("QVI_M");
		refComPrefDgtDS.create(refComPrefDgt);
		
		RefPermissionsQuestionDTO refPermissionsQuestion = new RefPermissionsQuestionDTO();
		refPermissionsQuestion.setDateCreation(new Date());
		refPermissionsQuestion.setName("FLYING_BLUE_X");
		refPermissionsQuestion.setQuestion("Blabla en FR");
		refPermissionsQuestion.setQuestionEN("Blabla en En");
		refPermissionsQuestion.setSignatureCreation("TESTMZ_C");
		refPermissionsQuestion.setSiteCreation("QVI_C");
		refPermissionsQuestionDS.create(refPermissionsQuestion);
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTMZ_C");
		refPermissions.setSiteCreation("QVI_C");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTMZ_M");
		refPermissions.setSiteModification("QVI_M");
		
		refPermissionsDS.create(refPermissions);
		
		List<RefComPrefDgtDTO> listRefComPrefDgt = refPermissionsDS.getAllComPrefDGTByPermissionsQuestionId(refPermissionsQuestion.getId());
		
		Assert.assertEquals(1, listRefComPrefDgt.size());
	}
	
}
