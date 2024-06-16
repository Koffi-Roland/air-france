package com.afklm.batch.event.individu.ut;

import com.afklm.soa.stubs.w001539.v1.sicindividutype.ContractV2;
import com.airfrance.batch.common.NotifyIndividualTransform;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class NotifyAdhocIndividualTransformTest {
	
	@Test
	public void transformToContractTest_NormalCase() {
		
		// On initialise le DTO avant transformation
		RoleContratsDTO dto = fillDefaultDto();
		
		ContractV2 ws = NotifyIndividualTransform.transformToContract(dto);
				
		Assert.assertNotNull(ws);		
		Assert.assertNotNull(ws.getVersion());
		Assert.assertEquals("20", ws.getVersion());
	}

	@Test
	public void transformToContractTest_3DigitCase() {
		
		// On initialise le DTO avant transformation
		RoleContratsDTO dto = fillDefaultDto();
		dto.setVersion(200);
		
		ContractV2 ws = NotifyIndividualTransform.transformToContract(dto);
				
		Assert.assertNotNull(ws);		
		Assert.assertNotNull(ws.getVersion());
		Assert.assertEquals("00", ws.getVersion());
	}
	
	private RoleContratsDTO fillDefaultDto() {
		
		RoleContratsDTO dto = new RoleContratsDTO();
		dto.setAgenceIATA("pAgenceIATA");
		// dto.setBusinessroledto(pBusinessroledto);
		dto.setCleRole(12345);
		
		dto.setCodeCompagnie("pCodeCompagnie");
		dto.setCuscoCreated("pCuscoCreated");
		// dto.setDateCreation("pDateCreation");
		dto.setEtat("pEtat");
		dto.setFamilleProduit("pFamilleProduit");
		dto.setFamilleTraitement("pFamilleTraitement");
		dto.setGin("pGin");
		dto.setIata("pIata");
		// dto.setIndividudto(pIndividudto);
		dto.setMemberType("memberType");
		dto.setMilesQualif(1);
		dto.setMilesQualifPrec(2);
		dto.setNumeroContrat("pNumeroContrat");
		dto.setPermissionPrime("pPermissionPrime");
		dto.setSegmentsQualif(100);
		dto.setSegmentsQualifPrec(101);
		dto.setSignatureCreation("pSignatureCreation");
		dto.setSignatureModification("pSignatureModification");
		dto.setSiteCreation("pSiteCreation");
		dto.setSiteModification("pSiteModification");
		dto.setSoldeMiles(10);
		dto.setSourceAdhesion("pSourceAdhesion");
		dto.setSousType("pSousType");
		dto.setSrin("pSrin");
		dto.setTier("pTier");
		dto.setTypeContrat("pTypeContrat");
		dto.setVersion(20);
		dto.setVersionProduit(21);
		
		return dto;
		
	}
}
