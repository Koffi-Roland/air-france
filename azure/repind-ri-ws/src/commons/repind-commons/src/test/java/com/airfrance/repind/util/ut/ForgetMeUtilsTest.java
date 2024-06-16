package com.airfrance.repind.util.ut;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.ws.IndividualInformationsDTO;
import com.airfrance.repind.dto.ws.IndividualRequestDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.util.ForgetMeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ForgetMeUtilsTest extends ForgetMeUtils {
	
	/** logger */
    private static final Log log = LogFactory.getLog(ForgetMeUtilsTest.class);
	
	@Autowired
	private ForgetMeUtils forgetMeUtils;
	
	/*
	 * REPIND-946
	 */
	@Test
	public void testCheckFieldNull(){
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		IndividualRequestDTO individualRequestDTO = new IndividualRequestDTO();
		IndividualInformationsDTO individualInformationsDTO = new IndividualInformationsDTO();
		individualInformationsDTO.setIdentifier(null);
		individualRequestDTO.setIndividualInformationsDTO(individualInformationsDTO);
		request.setIndividualRequestDTO(individualRequestDTO);
		
		Assert.assertFalse(this.checkField(request));
	}
	
	/*
	 * REPIND-946
	 */
	@Test
	public void testCheckFieldEmpty(){
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		IndividualRequestDTO individualRequestDTO = new IndividualRequestDTO();
		IndividualInformationsDTO individualInformationsDTO = new IndividualInformationsDTO();
		individualInformationsDTO.setIdentifier("");
		individualRequestDTO.setIndividualInformationsDTO(individualInformationsDTO);
		request.setIndividualRequestDTO(individualRequestDTO);
		
		Assert.assertFalse(this.checkField(request));
	}
	
	/*
	 * REPIND-946
	 */
	@Test
	public void testGetGin(){
		CreateUpdateIndividualRequestDTO requestDTO = new CreateUpdateIndividualRequestDTO();
		
		IndividualRequestDTO individualRequestDTO = new IndividualRequestDTO();
		IndividualInformationsDTO individualInformationsDTO = new IndividualInformationsDTO();
		individualInformationsDTO.setIdentifier("0000");
		individualRequestDTO.setIndividualInformationsDTO(individualInformationsDTO);
		requestDTO.setIndividualRequestDTO(individualRequestDTO);
		
		Assert.assertEquals(forgetMeUtils.getGin(requestDTO), "0000");
	}
	
	@Test
	public void testForgetMeUtils_deleteExternalIdentifier() {
		// Init test
		IndividuDTO dto = new IndividuDTO();
		List<ExternalIdentifierDTO> extIdList = new ArrayList<ExternalIdentifierDTO>();
		dto.setExternalIdentifierList(extIdList);
		
		ExternalIdentifierDTO ext1 = new ExternalIdentifierDTO();
		extIdList.add(ext1);
		ExternalIdentifierDTO ext2 = new ExternalIdentifierDTO();
		extIdList.add(ext2);
		
		ext1.setIdentifier("TestU");
		ext2.setIdentifier("TestU");
		
		// Execute test
		try {
			this.deleteExternalIdentifierToForget(dto);
			Assert.assertNotNull(dto);
			Assert.assertTrue(dto.getExternalIdentifierList().isEmpty());
			
		} catch (JrafDomainException e) {
			log.error(e.getMessage());
			Assert.fail();
		}
	}
}
