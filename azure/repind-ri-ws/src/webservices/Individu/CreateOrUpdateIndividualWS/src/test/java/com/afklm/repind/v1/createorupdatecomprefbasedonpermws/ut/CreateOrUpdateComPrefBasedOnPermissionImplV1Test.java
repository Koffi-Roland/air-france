package com.afklm.repind.v1.createorupdatecomprefbasedonpermws.ut;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v1.createorupdatecomprefbasedonpermws.CreateOrUpdateComPrefBasedOnPermissionImplV1;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001950.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001950.v1.ns3.Permission;
import com.afklm.soa.stubs.w001950.v1.ns4.PermissionRequest;
import com.afklm.soa.stubs.w001950.v1.ns6.CreateOrUpdateComPrefBasedOnPermissionRequest;
import com.afklm.soa.stubs.w001950.v1.ns9.Requestor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateComPrefBasedOnPermissionImplV1Test {
	
	@Autowired
	private CreateOrUpdateComPrefBasedOnPermissionImplV1 createOrUpdateComPrefBasedOnPermissionImplV1;
	
	private Requestor requestor;	
	
	@Before
	public void before() {
		requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSignature("TESTRI");
		requestor.setSite("QVI");
	}
	
	@Test
	public void testMissingRequestor() {
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin("999999999999");
		
		Permission permission = new Permission();
		permission.setPermissionID("241");
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		try {
			createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		} catch (BusinessErrorBlocBusinessException | SystemException e) {
			Assert.assertEquals("MISSING PARAMETERS", e.getMessage());
		}
	}
	
	@Test
	public void testMissingChannel() {
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin("999999999999");
		requestor.setChannel(null);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID("241");
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		try {
			createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		} catch (BusinessErrorBlocBusinessException | SystemException e) {
			Assert.assertEquals("MISSING PARAMETERS", e.getMessage());
		}
	}
	
	@Test
	public void testMissingSite() {
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin("999999999999");
		requestor.setSite(null);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID("241");
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		try {
			createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		} catch (BusinessErrorBlocBusinessException | SystemException e) {
			Assert.assertEquals("MISSING PARAMETERS", e.getMessage());
		}
	}
	
	@Test
	public void testMissingSignature() {
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin("999999999999");
		requestor.setSignature(null);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID("241");
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		try {
			createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		} catch (BusinessErrorBlocBusinessException | SystemException e) {
			Assert.assertEquals("MISSING PARAMETERS", e.getMessage());
		}
	}
	
	@Test
	public void testMissingPermissionRequest() {
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin("999999999999");
		request.setRequestor(requestor);
		
		try {
			createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		} catch (BusinessErrorBlocBusinessException | SystemException e) {
			Assert.assertEquals("MISSING PARAMETERS", e.getMessage());
		}
	}
	
	@Test
	public void testMissingPermissions() {
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin("999999999999");
		request.setRequestor(requestor);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		request.setPermissionRequest(permissionRequest);
		
		try {
			createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		} catch (BusinessErrorBlocBusinessException | SystemException e) {
			Assert.assertEquals("MISSING PARAMETERS", e.getMessage());
		}
	}
	
	@Test
	public void testMissingPermissionId() {
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin("999999999999");
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		try {
			createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		} catch (BusinessErrorBlocBusinessException | SystemException e) {
			Assert.assertEquals("MISSING PARAMETERS", e.getMessage());
		}
	}
	
	@Test
	public void testMissingDuplicatePermissionId() {
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin("999999999999");
		request.setRequestor(requestor);
		
		Permission permissionA = new Permission();
		permissionA.setPermissionID("100");
		permissionA.setPermissionAnswer(true);
		
		Permission permissionB = new Permission();
		permissionB.setPermissionID("100");
		permissionB.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permissionA);
		permissionRequest.getPermission().add(permissionB);
		
		request.setPermissionRequest(permissionRequest);
		
		try {
			createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		} catch (BusinessErrorBlocBusinessException | SystemException e) {
			Assert.assertEquals("INVALID PARAMETER", e.getMessage());
		}
	}
	
}
