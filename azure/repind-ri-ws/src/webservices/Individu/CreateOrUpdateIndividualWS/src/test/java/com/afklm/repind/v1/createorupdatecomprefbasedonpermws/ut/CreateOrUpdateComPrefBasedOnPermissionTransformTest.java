package com.afklm.repind.v1.createorupdatecomprefbasedonpermws.ut;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v1.createorupdatecomprefbasedonpermws.transformers.CreateOrUpdateComPrefBasedOnPermissionTransform;
import com.afklm.soa.stubs.w001950.v1.ns3.Permission;
import com.afklm.soa.stubs.w001950.v1.ns4.PermissionRequest;
import com.afklm.soa.stubs.w001950.v1.ns6.CreateOrUpdateComPrefBasedOnPermissionRequest;
import com.afklm.soa.stubs.w001950.v1.ns6.CreateOrUpdateComPrefBasedOnPermissionResponse;
import com.afklm.soa.stubs.w001950.v1.ns7.LanguageCodesEnum;
import com.afklm.soa.stubs.w001950.v1.ns9.Requestor;
import com.airfrance.repind.dto.compref.InformationDTO;
import com.airfrance.repind.dto.compref.InformationsDTO;
import com.airfrance.repind.dto.compref.PermissionDTO;
import com.airfrance.repind.dto.compref.PermissionsDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class CreateOrUpdateComPrefBasedOnPermissionTransformTest {
	
	@Test
	public void testRequestWSToPermissionsDTO() {
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		
		Permission permission = new Permission();
		permission.setPermissionID("1");
		permission.setPermissionAnswer(true);
		permission.setMarket("FR");
		permission.setLanguage(LanguageCodesEnum.FR);
		
		Requestor requestor = new Requestor();
		requestor.setSignature("TEST");
		requestor.setSite("QVI");
		requestor.setChannel("B2C");
		
		request.setGin("000000000000");
		request.setPermissionRequest(new PermissionRequest());
		request.getPermissionRequest().getPermission().add(permission);
		request.setRequestor(requestor);
		
		PermissionsDTO permissionsDTO = CreateOrUpdateComPrefBasedOnPermissionTransform.requestWSToPermissionsDTO(request);
		
		Assert.assertEquals("000000000000", permissionsDTO.getGin());
		Assert.assertEquals("FR", permissionsDTO.getPermission().get(0).getLanguage());
		Assert.assertEquals("FR", permissionsDTO.getPermission().get(0).getMarket());
		Assert.assertEquals(true, permissionsDTO.getPermission().get(0).getAnswer());
		Assert.assertEquals("1", permissionsDTO.getPermission().get(0).getPermissionId());
		Assert.assertEquals("TEST", permissionsDTO.getRequestorDTO().getSignature());
		Assert.assertEquals("QVI", permissionsDTO.getRequestorDTO().getSite());
		Assert.assertEquals("B2C", permissionsDTO.getRequestorDTO().getChannel());
	}
	
	@Test
	public void testPermissionWStoPermissionDTO() {
		Permission permission = new Permission();
		permission.setLanguage(LanguageCodesEnum.FR);
		permission.setMarket("FR");
		permission.setPermissionAnswer(true);
		permission.setPermissionID("1");
		
		PermissionDTO permissionDTO = CreateOrUpdateComPrefBasedOnPermissionTransform.permissionWStoPermissionDTO(permission);
		
		Assert.assertEquals("FR", permissionDTO.getLanguage());
		Assert.assertEquals("FR", permissionDTO.getMarket());
		Assert.assertEquals("1", permissionDTO.getPermissionId());
		Assert.assertEquals(true, permissionDTO.getAnswer());
		
		permission = new Permission();
		permission.setLanguage(null);
		permission.setMarket(null);
		permission.setPermissionAnswer(true);
		permission.setPermissionID("1");
		
		permissionDTO = CreateOrUpdateComPrefBasedOnPermissionTransform.permissionWStoPermissionDTO(permission);
		
		Assert.assertEquals(null, permissionDTO.getLanguage());
		Assert.assertEquals(null, permissionDTO.getMarket());
		Assert.assertEquals("1", permissionDTO.getPermissionId());
		Assert.assertEquals(true, permissionDTO.getAnswer());
	}
	
	@Test
	public void testInformationsDTOtoInformationsWSList() {
		CreateOrUpdateComPrefBasedOnPermissionResponse response = new CreateOrUpdateComPrefBasedOnPermissionResponse();
		
		InformationsDTO informationsDTO1 = new InformationsDTO();
		informationsDTO1.setInformationsId(1);
		
		InformationDTO informationDT01 = new InformationDTO();
		informationDT01.setCode("0");
		informationDT01.setDetails("Com pref created");
		informationDT01.setName("F N FB_ESS");
		informationsDTO1.setInformation(new ArrayList<InformationDTO>());
		informationsDTO1.getInformation().add(informationDT01);
		
		InformationDTO informationDTO2 = new InformationDTO();
		informationDTO2.setCode("0");
		informationDTO2.setDetails("Com pref created");
		informationDTO2.setName("S N AF");
		informationsDTO1.getInformation().add(informationDTO2);
		
		InformationsDTO informationsDTO2 = new InformationsDTO();
		informationsDTO2.setInformationsId(1);
		
		InformationDTO informationDT03 = new InformationDTO();
		informationDT03.setCode("0");
		informationDT03.setDetails("Com pref created");
		informationDT03.setName("F N FB_ESS");
		informationsDTO2.setInformation(new ArrayList<InformationDTO>());
		informationsDTO2.getInformation().add(informationDT03);
		
		InformationDTO informationDTO4 = new InformationDTO();
		informationDTO4.setCode("0");
		informationDTO4.setDetails("Com pref created");
		informationDTO4.setName("S N AF");
		informationsDTO2.getInformation().add(informationDTO4);
		
		List<InformationsDTO> listInformationsDTO = new ArrayList<InformationsDTO>();
		listInformationsDTO.add(informationsDTO1);
		listInformationsDTO.add(informationsDTO2);
		
		CreateOrUpdateComPrefBasedOnPermissionTransform.informationsDTOtoInformationsWS(response, listInformationsDTO);
		
		Assert.assertEquals(2, response.getInformationResponse().getInformations().size());
		Assert.assertEquals(2, response.getInformationResponse().getInformations().get(0).getInformation().size());
		Assert.assertEquals(2, response.getInformationResponse().getInformations().get(1).getInformation().size());
	}
	
	@Test
	public void testInformationsDTOtoInformationsWS() {
		CreateOrUpdateComPrefBasedOnPermissionResponse response = new CreateOrUpdateComPrefBasedOnPermissionResponse();
		
		InformationsDTO informationsDTO = new InformationsDTO();
		informationsDTO.setInformationsId(1);
		
		InformationDTO informationDT01 = new InformationDTO();
		informationDT01.setCode("0");
		informationDT01.setDetails("Com pref created");
		informationDT01.setName("F N FB_ESS");
		informationsDTO.setInformation(new ArrayList<InformationDTO>());
		informationsDTO.getInformation().add(informationDT01);
		
		InformationDTO informationDTO2 = new InformationDTO();
		informationDTO2.setCode("0");
		informationDTO2.setDetails("Com pref created");
		informationDTO2.setName("S N AF");
		informationsDTO.getInformation().add(informationDTO2);
		
		CreateOrUpdateComPrefBasedOnPermissionTransform.informationsDTOtoInformationsWS(response, informationsDTO);
		
		Assert.assertEquals(2, response.getInformationResponse().getInformations().get(0).getInformation().size());
	}
}
