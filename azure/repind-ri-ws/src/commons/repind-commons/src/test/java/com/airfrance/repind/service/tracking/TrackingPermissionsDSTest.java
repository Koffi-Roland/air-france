package com.airfrance.repind.service.tracking;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.reference.RefPermissionsQuestionDTO;
import com.airfrance.repind.dto.tracking.TrackingPermissionsDTO;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.reference.internal.RefPermissionsQuestionDS;
import com.airfrance.repind.service.tracking.internal.TrackingPermissionsDS;
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
public class TrackingPermissionsDSTest {

	@Autowired
	private TrackingPermissionsDS trackingPermissionsDS;
	
	@Autowired
	private RefPermissionsQuestionDS refPermissionsQuestionDS;
	
	@Autowired
	private IndividuDS individuDS;

	@Test
	@Rollback(true)	
	public void testCreateFind() throws JrafDomainException {
		
		RefPermissionsQuestionDTO refPermissionsQuestionDTO = new RefPermissionsQuestionDTO();
		refPermissionsQuestionDTO.setName("FLYING_BLUE");
		refPermissionsQuestionDTO.setQuestion("Voulez-vous vous abonner aux communications Flying Blue ?");
		refPermissionsQuestionDTO.setQuestionEN("Would you like to receive the Flying Blue communications ?");
		refPermissionsQuestionDTO.setDateCreation(new Date());
		refPermissionsQuestionDTO.setSiteCreation("QVI");
		refPermissionsQuestionDTO.setSignatureCreation("TESTMZ");
		
		refPermissionsQuestionDS.create(refPermissionsQuestionDTO);
		
		IndividuDTO individuDTO = individuDS.getByGin("400228258587");
		
		TrackingPermissionsDTO trackingPermissionsDTO = new TrackingPermissionsDTO();
		trackingPermissionsDTO.setSgin(individuDTO);
		trackingPermissionsDTO.setPermissionQuestionId(refPermissionsQuestionDTO);
		
		trackingPermissionsDTO.setConsent("Y");
		trackingPermissionsDTO.setDateConsent(new Date());
		trackingPermissionsDTO.setDateCreation(new Date());
		trackingPermissionsDTO.setDateModification(new Date());
		trackingPermissionsDTO.setSignatureCreation("TESTMZ");
		trackingPermissionsDTO.setSignatureModification("TESTMZ");
		trackingPermissionsDTO.setSiteCreation("QVI");
		trackingPermissionsDTO.setSiteModification("QVI");
		
		trackingPermissionsDS.create(trackingPermissionsDTO);
		
		
		TrackingPermissionsDTO trackingPermissionsDTOFromDB = trackingPermissionsDS.getById(trackingPermissionsDTO.getId());
		
		Assert.assertNotNull(trackingPermissionsDTOFromDB.getSgin());
		Assert.assertNotNull(trackingPermissionsDTOFromDB.getPermissionQuestionId());
	}
	
	
	@Test
	@Rollback(true)	
	public void testUpdate() throws JrafDomainException {
		
		RefPermissionsQuestionDTO refPermissionsQuestionDTO = new RefPermissionsQuestionDTO();
		refPermissionsQuestionDTO.setName("FLYING_BLUE");
		refPermissionsQuestionDTO.setQuestion("Voulez-vous vous abonner aux communications Flying Blue ?");
		refPermissionsQuestionDTO.setQuestionEN("Would you like to receive the Flying Blue communications ?");
		refPermissionsQuestionDTO.setDateCreation(new Date());
		refPermissionsQuestionDTO.setSiteCreation("QVI");
		refPermissionsQuestionDTO.setSignatureCreation("TESTMZ");
		
		refPermissionsQuestionDS.create(refPermissionsQuestionDTO);
		
		IndividuDTO individuDTO = individuDS.getByGin("400228258587");
		
		TrackingPermissionsDTO trackingPermissionsDTO = new TrackingPermissionsDTO();
		trackingPermissionsDTO.setSgin(individuDTO);
		trackingPermissionsDTO.setPermissionQuestionId(refPermissionsQuestionDTO);
		
		trackingPermissionsDTO.setConsent("Y");
		trackingPermissionsDTO.setDateConsent(new Date());
		trackingPermissionsDTO.setDateCreation(new Date());
		trackingPermissionsDTO.setDateModification(new Date());
		trackingPermissionsDTO.setSignatureCreation("TESTMZ");
		trackingPermissionsDTO.setSignatureModification("TESTMZ");
		trackingPermissionsDTO.setSiteCreation("QVI");
		trackingPermissionsDTO.setSiteModification("QVI");
		
		trackingPermissionsDS.create(trackingPermissionsDTO);
		
		trackingPermissionsDTO.setConsent("N");
		
		trackingPermissionsDS.update(trackingPermissionsDTO);
		
		TrackingPermissionsDTO trackingPermissionsDTOFromDB = trackingPermissionsDS.getById(trackingPermissionsDTO.getId());
		
		Assert.assertEquals("N", trackingPermissionsDTOFromDB.getConsent());
	}
	
	@Test
	@Rollback(true)	
	public void testDelete() throws JrafDomainException {
		
		RefPermissionsQuestionDTO refPermissionsQuestionDTO = new RefPermissionsQuestionDTO();
		refPermissionsQuestionDTO.setName("FLYING_BLUE");
		refPermissionsQuestionDTO.setQuestion("Voulez-vous vous abonner aux communications Flying Blue ?");
		refPermissionsQuestionDTO.setQuestionEN("Would you like to receive the Flying Blue communications ?");
		refPermissionsQuestionDTO.setDateCreation(new Date());
		refPermissionsQuestionDTO.setSiteCreation("QVI");
		refPermissionsQuestionDTO.setSignatureCreation("TESTMZ");
		
		refPermissionsQuestionDS.create(refPermissionsQuestionDTO);
		
		IndividuDTO individuDTO = individuDS.getByGin("400228258587");
		
		TrackingPermissionsDTO trackingPermissionsDTO = new TrackingPermissionsDTO();
		trackingPermissionsDTO.setSgin(individuDTO);
		trackingPermissionsDTO.setPermissionQuestionId(refPermissionsQuestionDTO);
		
		trackingPermissionsDTO.setConsent("Y");
		trackingPermissionsDTO.setDateConsent(new Date());
		trackingPermissionsDTO.setDateCreation(new Date());
		trackingPermissionsDTO.setDateModification(new Date());
		trackingPermissionsDTO.setSignatureCreation("TESTMZ");
		trackingPermissionsDTO.setSignatureModification("TESTMZ");
		trackingPermissionsDTO.setSiteCreation("QVI");
		trackingPermissionsDTO.setSiteModification("QVI");
		
		trackingPermissionsDS.create(trackingPermissionsDTO);
		
		trackingPermissionsDS.delete(trackingPermissionsDTO);
		
		TrackingPermissionsDTO trackingPermissionsDTOFromDB = trackingPermissionsDS.getById(trackingPermissionsDTO.getId());
		
		Assert.assertNull(trackingPermissionsDTOFromDB);
	}
	
}
