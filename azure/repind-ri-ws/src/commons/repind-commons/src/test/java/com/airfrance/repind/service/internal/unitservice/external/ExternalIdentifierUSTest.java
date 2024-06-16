package com.airfrance.repind.service.internal.unitservice.external;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001793.v1.InWeboLoginCreateV10;
import com.afklm.soa.stubs.w001793.v1.req.InWeboLoginCreateRequest;
import com.afklm.soa.stubs.w001793.v1.res.InWeboLoginCreateResponse;
import com.airfrance.ref.exception.external.SendAliasException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.dao.external.ExternalIdentifierRepository;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.entity.environnement.Variables;
import com.airfrance.repind.entity.external.ExternalIdentifier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ExternalIdentifierUSTest extends ExternalIdentifierUS {

	private static final Log log = LogFactory.getLog(ExternalIdentifierUSTest.class);

	@Autowired
	private ExternalIdentifierUS externalIdentifierUS;

	@Autowired
	private InWeboLoginCreateV10 consumerW001793v1;

	private VariablesRepository variablesRepositoryMock;

	private ExternalIdentifierRepository externalIdentifierRepositoryMock;
	
	@Before
	public void setUp() {
		variablesRepositoryMock = Mockito.mock(VariablesRepository.class);
		externalIdentifierUS.variablesRepository = variablesRepositoryMock;

		externalIdentifierRepositoryMock = Mockito.mock(ExternalIdentifierRepository.class);
		externalIdentifierUS.externalIdentifierRepository = externalIdentifierRepositoryMock;
	}

	@Test
	public void testCheckExistingAlias() {
		ExternalIdentifierUSTest.log.info("Test Method CheckExistingAlias");

		IndividuDTO individuDTO = new IndividuDTO();
		individuDTO.setExternalIdentifierList(new ArrayList<ExternalIdentifierDTO>());

		ExternalIdentifierDTO externalIdentifierPNM_ID = new ExternalIdentifierDTO();
		externalIdentifierPNM_ID.setType("PNM_ID");

		individuDTO.getExternalIdentifierList().add(externalIdentifierPNM_ID);

		boolean result = externalIdentifierUS.checkExistingAlias(individuDTO, "LA");
		Assert.assertFalse(result);

		ExternalIdentifierDTO externalIdentifierLA = new ExternalIdentifierDTO();
		externalIdentifierLA.setType("LA");

		individuDTO.getExternalIdentifierList().add(externalIdentifierLA);

		result = externalIdentifierUS.checkExistingAlias(individuDTO, "LA");
		Assert.assertTrue(result);
	}

	@Test
	public void testGenerateAlias() throws SendAliasException {
		ExternalIdentifierUSTest.log.info("Test Method GenerateAlias");

		List<String> listOfAlias = new ArrayList<>();

		for (int index = 1; index <= 10000; index++) {
			listOfAlias.add(externalIdentifierUS.generateAlias());
		}

		Assert.assertEquals(10000, listOfAlias.size());

		Set<String> listOfUnique = new HashSet<>();
		for (String alias : listOfAlias) {
			listOfUnique.add(alias);
		}

		Assert.assertEquals(10000, listOfUnique.size());
	}

	@Test
	public void TestSendAliasOK() throws JrafDaoException, SendAliasException, SystemException {

		Variables variableInWebo = new Variables();
		variableInWebo.setEnvKey("INWEBO_SERVICE_ID");
		variableInWebo.setEnvValue("3238");
		Mockito.doReturn(Optional.of(variableInWebo)).when(variablesRepositoryMock).findById(variableInWebo.getEnvKey());

		InWeboLoginCreateResponse loginCreateResult = new InWeboLoginCreateResponse();
		loginCreateResult.setId(100);
		loginCreateResult.setCode("200");
		Mockito.doReturn(loginCreateResult).when(consumerW001793v1)
				.inWeboLoginCreate(ArgumentMatchers.any(InWeboLoginCreateRequest.class));

		String id = externalIdentifierUS.sendAlias("alias");

		Assert.assertEquals("100", id);
	}


	@Test(expected = Test.None.class /* no exception expected */)
	@SuppressWarnings("unused")
	public void TestSendAliasVarInWeboKO() throws JrafDaoException, SystemException {

		/*Mockito.doReturn(null).when(variablesRepositoryMock).findById(ArgumentMatchers.any(Serializable.class));

		InWeboLoginCreateResponse loginCreateResult = new InWeboLoginCreateResponse();
		loginCreateResult.setId(100);
		loginCreateResult.setCode("200");
		Mockito.doReturn(loginCreateResult).when(consumerW001793v1Mock)
				.inWeboLoginCreate(ArgumentMatchers.any(InWeboLoginCreateRequest.class));

		String id = null;

		try {
			id = externalIdentifierUS.sendAlias("alias");
		} catch (SendAliasException e) {
			Assert.assertEquals("Error during storage : Unable to get serviceId number from DB.", e.getMessage());
		}*/
	}

	@Test
	@SuppressWarnings("unused")
	public void TestSendAliasInWeboNullResult() throws JrafDaoException, SendAliasException, SystemException {

		Variables variableInWebo = new Variables();
		variableInWebo.setEnvKey("INWEBO_SERVICE_ID");
		variableInWebo.setEnvValue("3238");
		Mockito.doReturn(Optional.of(variableInWebo)).when(variablesRepositoryMock).findById(variableInWebo.getEnvKey());

		Mockito.doReturn(null).when(consumerW001793v1)
				.inWeboLoginCreate(ArgumentMatchers.any(InWeboLoginCreateRequest.class));

		String id = null;

		try {
			id = externalIdentifierUS.sendAlias("alias");
		} catch (SendAliasException e) {
			Assert.assertEquals("Error during storage : Result is null.", e.getMessage());
		}
	}

	@Test
	@SuppressWarnings("unused")
	public void TestSendAliasInWeboMaxNumber() throws JrafDaoException, SendAliasException, SystemException {

		Variables variableInWebo = new Variables();
		variableInWebo.setEnvKey("INWEBO_SERVICE_ID");
		variableInWebo.setEnvValue("3238");
		Mockito.doReturn(Optional.of(variableInWebo)).when(variablesRepositoryMock).findById(variableInWebo.getEnvKey());

		InWeboLoginCreateResponse loginCreateResult = new InWeboLoginCreateResponse();
		loginCreateResult.setErr("NOK:full");
		Mockito.doReturn(loginCreateResult).when(consumerW001793v1)
				.inWeboLoginCreate(ArgumentMatchers.any(InWeboLoginCreateRequest.class));

		String id = null;

		try {
			id = externalIdentifierUS.sendAlias("alias");
		} catch (SendAliasException e) {
			Assert.assertEquals("Error during storage : Max number of users for the service has been reached.",
					e.getMessage());
		}
	}

	@Test
	@SuppressWarnings("unused")
	public void TestSendAliasInWeboAlreayExists() throws JrafDaoException, SendAliasException, SystemException {

		Variables variableInWebo = new Variables();
		variableInWebo.setEnvKey("INWEBO_SERVICE_ID");
		variableInWebo.setEnvValue("3238");
		Mockito.doReturn(Optional.of(variableInWebo)).when(variablesRepositoryMock).findById(variableInWebo.getEnvKey());

		InWeboLoginCreateResponse loginCreateResult = new InWeboLoginCreateResponse();
		loginCreateResult.setErr("NOK:loginexists");
		Mockito.doReturn(loginCreateResult).when(consumerW001793v1)
				.inWeboLoginCreate(ArgumentMatchers.any(InWeboLoginCreateRequest.class));

		String id = null;

		try {
			id = externalIdentifierUS.sendAlias("alias");
		} catch (SendAliasException e) {
			Assert.assertEquals("Error during storage : Login already exists.", e.getMessage());
		}
	}

	@Test
	@SuppressWarnings("unused")
	public void TestSendAliasInWeboInvalidParameter() throws JrafDaoException, SendAliasException, SystemException {

		Variables variableInWebo = new Variables();
		variableInWebo.setEnvKey("INWEBO_SERVICE_ID");
		variableInWebo.setEnvValue("3238");
		Mockito.doReturn(Optional.of(variableInWebo)).when(variablesRepositoryMock).findById(variableInWebo.getEnvKey());

		InWeboLoginCreateResponse loginCreateResult = new InWeboLoginCreateResponse();
		loginCreateResult.setErr("NOK:SN");
		Mockito.doReturn(loginCreateResult).when(consumerW001793v1)
				.inWeboLoginCreate(ArgumentMatchers.any(InWeboLoginCreateRequest.class));

		String id = null;

		try {
			id = externalIdentifierUS.sendAlias("alias");
		} catch (SendAliasException e) {
			Assert.assertEquals("Error during storage : Invalid input parameters.", e.getMessage());
		}
	}

	@Test
	@SuppressWarnings("unused")
	public void TestSendAliasInWeboServiceUnknown() throws JrafDaoException, SendAliasException, SystemException {

		Variables variableInWebo = new Variables();
		variableInWebo.setEnvKey("INWEBO_SERVICE_ID");
		variableInWebo.setEnvValue("3238");
		Mockito.doReturn(Optional.of(variableInWebo)).when(variablesRepositoryMock).findById(variableInWebo.getEnvKey());

		InWeboLoginCreateResponse loginCreateResult = new InWeboLoginCreateResponse();
		loginCreateResult.setErr("NOK:srv unknown");
		Mockito.doReturn(loginCreateResult).when(consumerW001793v1)
				.inWeboLoginCreate(ArgumentMatchers.any(InWeboLoginCreateRequest.class));

		String id = null;

		try {
			id = externalIdentifierUS.sendAlias("alias");
		} catch (SendAliasException e) {
			Assert.assertEquals("Error during storage : Wrong serviceId.", e.getMessage());
		}
	}

	@Test
	@SuppressWarnings("unused")
	public void TestSendAliasInWebError() throws JrafDaoException, SendAliasException, SystemException {

		Variables variableInWebo = new Variables();
		variableInWebo.setEnvKey("INWEBO_SERVICE_ID");
		variableInWebo.setEnvValue("3238");
		Mockito.doReturn(Optional.of(variableInWebo)).when(variablesRepositoryMock).findById(variableInWebo.getEnvKey());

		InWeboLoginCreateResponse loginCreateResult = new InWeboLoginCreateResponse();
		loginCreateResult.setErr("Unexpected error");
		Mockito.doReturn(loginCreateResult).when(consumerW001793v1)
				.inWeboLoginCreate(ArgumentMatchers.any(InWeboLoginCreateRequest.class));

		String id = null;

		try {
			id = externalIdentifierUS.sendAlias("alias");
		} catch (SendAliasException e) {
			Assert.assertEquals("Error during storage : Unexpected error", e.getMessage());
		}
	}

	@Test
	@SuppressWarnings("unused")
	public void TestSendAliasInWeboLoginNull() throws JrafDaoException, SendAliasException, SystemException {

		Variables variableInWebo = new Variables();
		variableInWebo.setEnvKey("INWEBO_SERVICE_ID");
		variableInWebo.setEnvValue("3238");
		Mockito.doReturn(Optional.of(variableInWebo)).when(variablesRepositoryMock).findById(variableInWebo.getEnvKey());

		InWeboLoginCreateResponse loginCreateResult = new InWeboLoginCreateResponse();
		Mockito.doReturn(loginCreateResult).when(consumerW001793v1)
				.inWeboLoginCreate(ArgumentMatchers.any(InWeboLoginCreateRequest.class));

		String id = null;

		try {
			id = externalIdentifierUS.sendAlias("alias");
		} catch (SendAliasException e) {
			Assert.assertEquals("Error during storage : LoginId returned is null.", e.getMessage());
		}
	}

	@Test
	public void TestGetValidAlias() throws JrafDomainException {

		List<ExternalIdentifier> listExternalIdentifier = new ArrayList<>();

		ExternalIdentifier externalIdentifierLA = new ExternalIdentifier();
		externalIdentifierLA.setType("LA");
		listExternalIdentifier.add(externalIdentifierLA);

		ExternalIdentifier externalIdentifierPNM_ID = new ExternalIdentifier();
		externalIdentifierPNM_ID.setType("PNM_ID");
		listExternalIdentifier.add(externalIdentifierPNM_ID);

		Mockito.doReturn(listExternalIdentifier).when(externalIdentifierRepositoryMock)
				.findExternalIdentifierALL(ArgumentMatchers.anyString());

		IndividuDTO individuDTO = new IndividuDTO();
		individuDTO.setSgin("400000000000");
		Set<ExternalIdentifierDTO> validAlias = externalIdentifierUS.getValidAlias(individuDTO);

		Assert.assertEquals(2, validAlias.size());
	}

}
