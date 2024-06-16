package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.reference.RefComPrefGroupInfoDTO;
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
public class RefComPrefGroupInfoDSTest {

	@Autowired
	private RefComPrefGroupInfoDS refComPrefGroupInfoDS;

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
		
		Assert.assertNotNull(refComPrefGroupInfo.getId());
		
		RefComPrefGroupInfoDTO refComPrefGroupInfoFromDB = refComPrefGroupInfoDS.getById(refComPrefGroupInfo.getId());
		
		Assert.assertEquals("Y", refComPrefGroupInfoFromDB.getMandatoryOption());
		Assert.assertEquals("Y", refComPrefGroupInfoFromDB.getDefaultOption());
	}
		
	
	@Test
	@Rollback(true)	
	public void testUpdate() throws JrafDomainException {
		
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
		
		RefComPrefGroupInfoDTO refComPrefGroupInfoNew = refComPrefGroupInfoDS.getById(refComPrefGroupInfo.getId());
		refComPrefGroupInfoNew.setDefaultOption("N");
		refComPrefGroupInfoNew.setMandatoryOption("N");
		refComPrefGroupInfoDS.update(refComPrefGroupInfoNew);
		
		refComPrefGroupInfo = refComPrefGroupInfoDS.getById(refComPrefGroupInfoNew.getId());
		
		Assert.assertEquals("N", refComPrefGroupInfo.getMandatoryOption());
		Assert.assertEquals("N", refComPrefGroupInfo.getDefaultOption());
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
		
		refComPrefGroupInfoDS.delete(refComPrefGroupInfo);
		
		RefComPrefGroupInfoDTO refComPrefGroupInfoFroMDB = refComPrefGroupInfoDS.getById(refComPrefGroupInfo.getId());
		
		Assert.assertNull(refComPrefGroupInfoFroMDB);
	}
	
}
