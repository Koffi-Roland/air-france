package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.reference.RefPermissionsQuestionDTO;
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
public class RefPermissionsQuestionDSTest {

	@Autowired
	private RefPermissionsQuestionDS refPermissionsQuestionDS;

	@Test
	@Rollback(true)	
	public void testCreateFind() throws InvalidParameterException {
		
		RefPermissionsQuestionDTO refPermissionsQuestionDTO = new RefPermissionsQuestionDTO();
		refPermissionsQuestionDTO.setName("FOR_TEST");
		refPermissionsQuestionDTO.setQuestion("Voulez-vous vous abonner aux communications ?");
		refPermissionsQuestionDTO.setQuestionEN("Would you like to receive communications ?");
		refPermissionsQuestionDTO.setDateCreation(new Date());
		refPermissionsQuestionDTO.setSiteCreation("QVI");
		refPermissionsQuestionDTO.setSignatureCreation("TESTMZ");
		
		refPermissionsQuestionDS.create(refPermissionsQuestionDTO);
			
		RefPermissionsQuestionDTO refPermissionsQuestionDTOFromDB = refPermissionsQuestionDS.getById(refPermissionsQuestionDTO.getId());
		
		Assert.assertNotNull(refPermissionsQuestionDTOFromDB.getQuestion());
		Assert.assertNotNull(refPermissionsQuestionDTOFromDB.getQuestionEN());
	}
	
	
	@Test
	@Rollback(true)	
	public void testUpdate() throws InvalidParameterException {
		
		RefPermissionsQuestionDTO refPermissionsQuestionDTO = new RefPermissionsQuestionDTO();
		refPermissionsQuestionDTO.setName("FLYING_BLUE");
		refPermissionsQuestionDTO.setQuestion("1");
		refPermissionsQuestionDTO.setQuestionEN("1");
		refPermissionsQuestionDTO.setDateCreation(new Date());
		refPermissionsQuestionDTO.setSiteCreation("QVI_C");
		refPermissionsQuestionDTO.setSignatureCreation("TESTMZ_C");
		
		refPermissionsQuestionDS.create(refPermissionsQuestionDTO);
				
		refPermissionsQuestionDTO.setQuestion("2");
		refPermissionsQuestionDTO.setQuestionEN("2");
		refPermissionsQuestionDTO.setDateModification(new Date());
		refPermissionsQuestionDTO.setSiteModification("QVI_M");
		refPermissionsQuestionDTO.setSignatureModification("TESTMZ_M");
		
		refPermissionsQuestionDS.update(refPermissionsQuestionDTO);
		
		Assert.assertEquals("2", refPermissionsQuestionDTO.getQuestion());
		Assert.assertEquals("2", refPermissionsQuestionDTO.getQuestionEN());
	}
	
	@Test
	@Rollback(true)	
	public void testDelete() throws InvalidParameterException {
		
		RefPermissionsQuestionDTO refPermissionsQuestionDTO = new RefPermissionsQuestionDTO();
		refPermissionsQuestionDTO.setName("FLYING_BLUE_2");
		refPermissionsQuestionDTO.setQuestion("Voulez-vous vous abonner aux communications Flying Blue ?");
		refPermissionsQuestionDTO.setQuestionEN("Would you like to receive the Flying Blue communications ?");
		refPermissionsQuestionDTO.setDateCreation(new Date());
		refPermissionsQuestionDTO.setSiteCreation("QVI");
		refPermissionsQuestionDTO.setSignatureCreation("TESTMZ");
		
		refPermissionsQuestionDS.create(refPermissionsQuestionDTO);

		refPermissionsQuestionDS.delete(refPermissionsQuestionDTO);
		
		RefPermissionsQuestionDTO refPermissionsQuestionDTOFromDB = refPermissionsQuestionDS.getById(refPermissionsQuestionDTO.getId());
		
		Assert.assertNull(refPermissionsQuestionDTOFromDB);
	}
	
}
