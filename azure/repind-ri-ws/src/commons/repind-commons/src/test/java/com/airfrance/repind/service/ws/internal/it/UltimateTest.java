package com.airfrance.repind.service.ws.internal.it;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.ws.internal.helpers.UltimateException;
import com.airfrance.repind.service.ws.internal.helpers.UltimateHelperV8;
import com.airfrance.repind.service.ws.internal.type.UltimateBusinessErrorCode;
import com.airfrance.repind.service.ws.internal.type.UltimateCustomerTypeCode;
import com.airfrance.repind.service.ws.internal.type.UltimateFamilyTypeCode;
import com.airfrance.repind.service.ws.internal.type.UltimateInputErrorCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class UltimateTest extends UltimateHelperV8 {

	private abstract class UltimateTesting extends UltimateHelperV8.Ultimate {
		public UltimateTesting(final String delegatorGin, final String delegateGin, final String delegationType,
				final String delegationStatus, final SignatureDTO signatureAPP, final String managingCompany,
				final String gin) {
			super(delegatorGin, delegateGin, delegationType, delegationStatus, signatureAPP, managingCompany, gin);
		}

	}

	private static final Log LOGGER = LogFactory.getLog(UltimateTest.class);
	private static final String STUB_DELEGATION_STATUS = "A";
	private static final String STUB_DELEGATOR_GIN = "20";
	private static final String STUB_DELEGATE_GIN = "21";
	private static final String STUB_DELEGATION_TYPE = "UF";
	private static SignatureDTO STUB_SIGNATURE;
	private static final String STUB_MANAGING_COMPANY = null;
	private static final String DELEGATOR_FB = "d5legatorTroFB3";
	private static final String DELEGATE_FB = "d5legateTroFB3";
	private static int MAXIMUM_FAMILY_SIZE;
	private static String _delegatorSGin = null;
	private static String _delegateSGin = null;

	private Ultimate _ultimate;

	private IndividuDS _mockIndividuDS;

	private DelegationDataDS _mockDelegationDataDS;
	private RoleDS _mockRoleDS;
	private CommunicationPreferencesDS _mockCommunicationPreferencesDS;

	@Autowired
	private DelegationDataDS delegationDataDS;

	@Autowired
	private IndividuDS individuDS;

	@Autowired
	private AccountDataDS accountDataDS;

	@Autowired
	private AccountDataRepository accountDataRepository;

	@Autowired
	private RoleDS roleDS;

	@Autowired
	private CommunicationPreferencesDS communicationPreferencesDS;

	@Autowired
	private BusinessRoleDS businessRoleDS;

	@Autowired
	private VariablesRepository variablesRepository;

	private void databaseInitialisation() throws JrafDomainException {
		final IndividuDTO delegate = new IndividuDTO();
		delegate.setCivilite("MR");
		delegate.setDateCreation(new Date());
		delegate.setNonFusionnable("N");
		delegate.setSexe("M");
		delegate.setSignatureCreation("sign");
		delegate.setSiteCreation("sophia");
		delegate.setStatutIndividu("V");
		delegate.setType("E");
		delegate.setVersion(0);

		final IndividuDTO delegator = new IndividuDTO();
		delegator.setCivilite("MR");
		delegator.setDateCreation(new Date());
		delegator.setNonFusionnable("N");
		delegator.setSexe("M");
		delegator.setSignatureCreation("sign");
		delegator.setSiteCreation("sophia");
		delegator.setStatutIndividu("V");
		delegator.setType("E");
		delegator.setVersion(0);

		final String delegateGin = individuDS.createAnIndividualExternal(delegate);
		final String delegatorGin = individuDS.createAnIndividualExternal(delegator);

		final AccountDataDTO delegatorAccountData = new AccountDataDTO();
		delegatorAccountData.setFbIdentifier(UltimateTest.DELEGATOR_FB);
		delegatorAccountData.setIndividudto(delegator);
		delegatorAccountData.setAccountIdentifier("d5legato");
		delegatorAccountData.setEmailIdentifier("d5legator@neowutran.net");

		final AccountDataDTO delegateAccountData = new AccountDataDTO();
		delegateAccountData.setFbIdentifier(UltimateTest.DELEGATE_FB);
		delegateAccountData.setIndividudto(delegate);
		delegateAccountData.setAccountIdentifier("d5legate");
		delegatorAccountData.setEmailIdentifier("d5legate@neowutran.net");

		delegate.setSgin(delegateGin);
		delegator.setSgin(delegatorGin);

		UltimateTest.LOGGER.info("delegateGin:" + delegateGin);
		UltimateTest.LOGGER.info("delegatorGin:" + delegatorGin);

		delegate.setAccountdatadto(delegateAccountData);
		delegator.setAccountdatadto(delegatorAccountData);

		accountDataDS.create(delegateAccountData);
		accountDataDS.create(delegatorAccountData);

		UltimateTest._delegatorSGin = delegatorGin;
		UltimateTest._delegateSGin = delegateGin;

		final RoleContratsDTO delegatorContrat = new RoleContratsDTO();
		delegatorContrat.setCodeCompagnie(UltimateTest.STUB_MANAGING_COMPANY);
		delegatorContrat.setGin(UltimateTest._delegatorSGin);
		delegatorContrat.setNumeroContrat(UltimateTest.DELEGATOR_FB);
		delegatorContrat.setTypeContrat("FP");
		delegatorContrat.setEtat("C");

		final BusinessRoleDTO delegatorBusinessRole = new BusinessRoleDTO();
		delegatorBusinessRole.setNumeroContrat(UltimateTest.DELEGATOR_FB);
		delegatorBusinessRole.setDateCreation(UltimateTest.STUB_SIGNATURE.getDate());
		delegatorBusinessRole.setSignatureCreation(UltimateTest.STUB_SIGNATURE.getSignature());
		delegatorBusinessRole.setSiteCreation(UltimateTest.STUB_SIGNATURE.getSite());
		delegatorBusinessRole.setDateModification(UltimateTest.STUB_SIGNATURE.getDate());
		delegatorBusinessRole.setSignatureModification(UltimateTest.STUB_SIGNATURE.getSignature());
		delegatorBusinessRole.setSiteModification(UltimateTest.STUB_SIGNATURE.getSite());
		delegatorBusinessRole.setGinInd(UltimateTest._delegatorSGin);
		delegatorBusinessRole.setType("C");
		final String delegatorBusinessKey = businessRoleDS.createABusinessRole(delegatorBusinessRole);
		delegatorBusinessRole.setCleRole(Integer.parseInt(delegatorBusinessKey));
		delegatorContrat.setBusinessroledto(delegatorBusinessRole);

		final RoleContratsDTO delegateContrat = new RoleContratsDTO();
		delegateContrat.setMemberType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());
		delegateContrat.setCodeCompagnie(UltimateTest.STUB_MANAGING_COMPANY);
		delegateContrat.setGin(UltimateTest._delegateSGin);
		delegateContrat.setNumeroContrat(UltimateTest.DELEGATE_FB);
		delegateContrat.setTypeContrat("FP");
		delegateContrat.setEtat("C");

		final BusinessRoleDTO delegateBusinessRole = new BusinessRoleDTO();
		delegateBusinessRole.setNumeroContrat(UltimateTest.DELEGATE_FB);
		delegateBusinessRole.setDateCreation(UltimateTest.STUB_SIGNATURE.getDate());
		delegateBusinessRole.setSignatureCreation(UltimateTest.STUB_SIGNATURE.getSignature());
		delegateBusinessRole.setSiteCreation(UltimateTest.STUB_SIGNATURE.getSite());
		delegateBusinessRole.setDateModification(UltimateTest.STUB_SIGNATURE.getDate());
		delegateBusinessRole.setSignatureModification(UltimateTest.STUB_SIGNATURE.getSignature());
		delegateBusinessRole.setSiteModification(UltimateTest.STUB_SIGNATURE.getSite());
		delegateBusinessRole.setGinInd(UltimateTest._delegateSGin);
		delegateBusinessRole.setType("C");
		final String delegateBusinessKey = businessRoleDS.createABusinessRole(delegateBusinessRole);
		delegateBusinessRole.setCleRole(Integer.parseInt(delegateBusinessKey));
		delegateContrat.setBusinessroledto(delegateBusinessRole);

		roleDS.create(delegatorContrat);
		roleDS.create(delegateContrat);

	}

	public void getDelegatorGinAndDelegateGin() throws Exception {

		final AccountData delegateAccount = accountDataRepository.findByFbIdentifier(UltimateTest.DELEGATE_FB);
		final AccountData delegatorAccount = accountDataRepository.findByFbIdentifier(UltimateTest.DELEGATOR_FB);
		
		if (delegateAccount == null || delegatorAccount == null) {
			databaseInitialisation();
			return;
		}

		UltimateTest._delegatorSGin = delegatorAccount.getSgin();
		UltimateTest._delegateSGin = delegateAccount.getSgin();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		UltimateTest.STUB_SIGNATURE = new SignatureDTO();
		UltimateTest.STUB_SIGNATURE.setApplicationCode("testUlti");
		UltimateTest.STUB_SIGNATURE.setDate(new Date());
		UltimateTest.STUB_SIGNATURE.setHeure("11");
		UltimateTest.STUB_SIGNATURE.setIpAddress("127.0.0.1");
		UltimateTest.STUB_SIGNATURE.setSignature("testUlti");
		UltimateTest.STUB_SIGNATURE.setSite("sophia");
		UltimateTest.STUB_SIGNATURE.setTypeSignature("test");

		getDelegatorGinAndDelegateGin();

		_mockIndividuDS = EasyMock.createNiceMock(IndividuDS.class);
		_mockDelegationDataDS = EasyMock.createNiceMock(DelegationDataDS.class);
		_mockRoleDS = EasyMock.createNiceMock(RoleDS.class);
		_mockCommunicationPreferencesDS = EasyMock.createNiceMock(CommunicationPreferencesDS.class);
		
		final RoleContratsDTO delegatorRole = new RoleContratsDTO();
		final RoleContratsDTO delegateRole = new RoleContratsDTO();
		delegatorRole.setMemberType("TT");
		delegateRole.setMemberType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());

		EasyMock.expect(_mockRoleDS.findRoleContratsFP(UltimateTest.DELEGATOR_FB)).andReturn(delegatorRole);
		EasyMock.expect(_mockRoleDS.findRoleContratsFP(UltimateTest.DELEGATE_FB)).andReturn(delegateRole);
		EasyMock.replay(_mockRoleDS);

		Constructor<Ultimate> constructor;
		constructor = Ultimate.class.getDeclaredConstructor(String.class, String.class, String.class, String.class,
				SignatureDTO.class, String.class, String.class);
		constructor.setAccessible(true);
		_ultimate = constructor.newInstance(UltimateTest.STUB_DELEGATOR_GIN, UltimateTest.STUB_DELEGATE_GIN,
				UltimateTest.STUB_DELEGATION_TYPE, UltimateTest.STUB_DELEGATION_STATUS, UltimateTest.STUB_SIGNATURE,
				UltimateTest.STUB_MANAGING_COMPANY, UltimateTest.STUB_DELEGATE_GIN);

		ReflectionTestUtils.setField(_ultimate, "_roleDS", _mockRoleDS);
		ReflectionTestUtils.setField(_ultimate, "_communicationPreferencesDS", _mockCommunicationPreferencesDS);
		ReflectionTestUtils.setField(_ultimate, "_individuDS", _mockIndividuDS);
		ReflectionTestUtils.setField(_ultimate, "_delegationDataDS", _mockDelegationDataDS);
		ReflectionTestUtils.setField(_ultimate, "_variablesRepository", variablesRepository);
		UltimateTest.MAXIMUM_FAMILY_SIZE = Integer
				.parseInt(variablesRepository.findById("ULTIMATE_FAMILY_MAX_SIZE").get().getEnvValue());

	}

	@Test
	@Transactional
	@Rollback(true)
	public final void testCreateDelegationData() throws Exception {

		ReflectionTestUtils.setField(_ultimate, "_delegationDataDS", delegationDataDS);
		ReflectionTestUtils.setField(_ultimate, "_individuDS", individuDS);
		ReflectionTestUtils.setField(_ultimate, "_delegatorGin", UltimateTest._delegatorSGin);
		ReflectionTestUtils.setField(_ultimate, "_delegateGin", UltimateTest._delegateSGin);
		ReflectionTestUtils.setField(_ultimate, "_originGin", UltimateTest._delegateSGin);

		final IndividuDTO delegate = individuDS.getByGin(UltimateTest._delegateSGin);
		final IndividuDTO delegator = individuDS.getByGin(UltimateTest._delegatorSGin);

		ReflectionTestUtils.setField(_ultimate, "_delegate", delegate);
		ReflectionTestUtils.setField(_ultimate, "_delegator", delegator);

		final Method method = _ultimate.getClass().getDeclaredMethod("_createDelegation");
		method.setAccessible(true);
		method.invoke(_ultimate);

		final DelegationDataDTO delegation = new DelegationDataDTO();
		delegation.setDelegateDTO(delegate);
		delegation.setDelegatorDTO(delegator);
		delegation.setType(UltimateTest.STUB_DELEGATION_TYPE);
		delegation.setStatus(UltimateTest.STUB_DELEGATION_STATUS);
		if (delegationDataDS.findDelegation(delegation) == null) {
			Assert.fail();
		}

	}

	@Test
	public final void testIsDelegateAlreadyLinkedToAUltimateCustomerCase0Link()
			throws JrafDomainException, NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

		final List<DelegationDataDTO> limitList = new ArrayList<>();
		EasyMock.expect(_mockDelegationDataDS.findDelegate(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(limitList);
		EasyMock.replay(_mockDelegationDataDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isDelegatorAlreadyLinkedToAUltimateCustomer");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		Assert.assertEquals(false, result);
	}

	@Test
	public final void testIsDelegateAlreadyLinkedToAUltimateCustomerCase1Link()
			throws JrafDomainException, NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

		final List<DelegationDataDTO> limitList = new ArrayList<>();
		final DelegationDataDTO delegation = new DelegationDataDTO();
		delegation.setType(UltimateFamilyTypeCode.ULTIMATE_FAMILY.getUltimateFamilyCode());
		limitList.add(delegation);

		EasyMock.expect(_mockDelegationDataDS.findDelegate(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(limitList);
		EasyMock.replay(_mockDelegationDataDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isDelegatorAlreadyLinkedToAUltimateCustomer");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		Assert.assertEquals(true, result);
	}

	@Test
	public final void testIsDelegateAlreadyLinkedToAUltimateCustomerCaseMoreThan1Link()
			throws JrafDomainException, NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

		final List<DelegationDataDTO> limitList = new ArrayList<>();
		final DelegationDataDTO delegationData1 = new DelegationDataDTO();
		final DelegationDataDTO delegationData2 = new DelegationDataDTO();
		delegationData1.setType(UltimateFamilyTypeCode.ULTIMATE_FAMILY.getUltimateFamilyCode());
		delegationData2.setType(UltimateFamilyTypeCode.ULTIMATE_FAMILY.getUltimateFamilyCode());
		limitList.add(delegationData1);
		limitList.add(delegationData2);

		EasyMock.expect(_mockDelegationDataDS.findDelegate(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(limitList);
		EasyMock.replay(_mockDelegationDataDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isDelegatorAlreadyLinkedToAUltimateCustomer");
		method.setAccessible(true);

		try {
			method.invoke(_ultimate);
			Assert.fail();

		} catch (final InvocationTargetException e) {
			if (e.getCause().getClass() != UltimateException.class) {
				Assert.fail("Exception: " + e.getCause().getClass());
			}
		}
	}

	@Test
	public final void testIsInputDelegateDoesntExist()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegatorAccount.setFbIdentifier(UltimateTest.DELEGATOR_FB);
		delegator.setAccountdatadto(delegatorAccount);

		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(delegator);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andThrow(new JrafDomainException(""));
		EasyMock.replay(_mockIndividuDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");
		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(UltimateInputErrorCode.DELEGATE_DOESNT_EXIST, lastError);
		Assert.assertEquals(false, result);

	}

	@Test
	public final void testIsInputDelegatorDoesntExistNull()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegatorAccount.setFbIdentifier(UltimateTest.DELEGATOR_FB);
		delegator.setAccountdatadto(delegatorAccount);

		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(delegator);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(null);
		EasyMock.replay(_mockIndividuDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");
		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(UltimateInputErrorCode.DELEGATOR_DOESNT_EXIST, lastError);
		Assert.assertEquals(false, result);

	}

	@Test
	public final void testIsInputDelegatorDoesntExist()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegatorAccount.setFbIdentifier("toto");
		delegator.setAccountdatadto(delegatorAccount);
		final IndividuDTO delegate = new IndividuDTO();
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN))
				.andThrow(new JrafDomainException(""));
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(delegate);
		EasyMock.replay(_mockIndividuDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");
		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(UltimateInputErrorCode.DELEGATOR_DOESNT_EXIST, lastError);
		Assert.assertEquals(false, result);

	}

	@Test
	public final void testIsInputDelegateDoesntExistNull()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegatorAccount.setFbIdentifier(UltimateTest.DELEGATOR_FB);
		delegator.setAccountdatadto(delegatorAccount);

		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(delegator);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(null);
		EasyMock.replay(_mockIndividuDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");
		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(UltimateInputErrorCode.DELEGATE_DOESNT_EXIST, lastError);
		Assert.assertEquals(false, result);

	}

	@Test
	public final void testIsInputValidDelegateAccountDataDoesntExist()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		ReflectionTestUtils.setField(_ultimate, "_delegationType", "Lulz");

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegatorAccount.setFbIdentifier(UltimateTest.DELEGATOR_FB);
		delegator.setAccountdatadto(delegatorAccount);
		final IndividuDTO delegate = new IndividuDTO();
		delegate.setType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());

		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(delegator);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(delegate);
		EasyMock.replay(_mockIndividuDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");

		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(UltimateInputErrorCode.DELEGATE_DOESNT_HAVE_ACCOUNT_DATA, lastError);
		Assert.assertEquals(false, result);

	}

	@Test
	public final void testIsInputValidDelegateIsNotFlyingBlue()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegatorAccount.setFbIdentifier(UltimateTest.DELEGATOR_FB);
		delegator.setAccountdatadto(delegatorAccount);
		final IndividuDTO delegate = new IndividuDTO();
		final AccountDataDTO delegateAccount = new AccountDataDTO();
		delegate.setAccountdatadto(delegateAccount);
		delegate.setType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(delegator);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(delegate);
		EasyMock.replay(_mockIndividuDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);

		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");
		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(UltimateInputErrorCode.DELEGATE_IS_NOT_A_FLYING_BLUE_CUSTOMER, lastError);
		Assert.assertEquals(false, result);

	}

	@Test
	public final void testIsInputValidDelegateIsUltimate()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegatorAccount.setFbIdentifier(UltimateTest.DELEGATOR_FB);
		delegatorAccount.setSgin(UltimateTest.STUB_DELEGATE_GIN);
		delegator.setAccountdatadto(delegatorAccount);
		delegator.setType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());
		final IndividuDTO delegate = new IndividuDTO();
		final AccountDataDTO delegateAccount = new AccountDataDTO();
		delegateAccount.setFbIdentifier(UltimateTest.DELEGATE_FB);
		delegateAccount.setSgin(UltimateTest.STUB_DELEGATE_GIN);
		delegate.setType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());
		delegate.setAccountdatadto(delegateAccount);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(delegator);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(delegate);
		EasyMock.replay(_mockIndividuDS);

		EasyMock.reset(_mockRoleDS);
		final RoleContratsDTO delegatorRole = new RoleContratsDTO();
		delegatorRole.setMemberType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());
		final RoleContratsDTO delegateRole = new RoleContratsDTO();
		delegateRole.setMemberType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());
		EasyMock.expect(_mockRoleDS.findRoleContratsFP(UltimateTest.DELEGATOR_FB)).andReturn(delegatorRole);
		EasyMock.expect(_mockRoleDS.findRoleContratsFP(UltimateTest.DELEGATE_FB)).andReturn(delegateRole);
		EasyMock.replay(_mockRoleDS);

		CommunicationPreferencesDTO communicationPreferences = new CommunicationPreferencesDTO();
		communicationPreferences.setDomain("U");
		communicationPreferences.setComGroupType("S");
		communicationPreferences.setComType("UL_PS");
		communicationPreferences.setSubscribe("Y");
		communicationPreferences.setDateOptin(new Date());

		EasyMock.expect(_mockCommunicationPreferencesDS.findComPrefId(UltimateTest.STUB_DELEGATOR_GIN, "U", "S", "UL_PS")).andReturn(null);
		EasyMock.expect(_mockCommunicationPreferencesDS.findComPrefId(UltimateTest.STUB_DELEGATE_GIN, "U", "S", "UL_PS")).andReturn(communicationPreferences);
		EasyMock.replay(_mockCommunicationPreferencesDS);
		
		
		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);

		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");
		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(UltimateInputErrorCode.DELEGATOR_IS_ULTIMATE_CUSTOMER_HIMSELF, lastError);
		Assert.assertEquals(false, result);

	}

	@Test
	public final void testUltimate_WithDomainN_IsValidSuccess()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		final IndividuDTO delegate = new IndividuDTO();
		final AccountDataDTO delegateAccount = new AccountDataDTO();
		delegateAccount.setFbIdentifier(UltimateTest.DELEGATE_FB);
		delegateAccount.setSgin(UltimateTest.STUB_DELEGATE_GIN);
		delegate.setAccountdatadto(delegateAccount);
		delegate.setType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegatorAccount.setFbIdentifier(UltimateTest.DELEGATOR_FB);
		delegatorAccount.setSgin(UltimateTest.STUB_DELEGATOR_GIN);
		delegator.setAccountdatadto(delegatorAccount);
		delegator.setType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());

		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(delegate);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(delegator);
		EasyMock.replay(_mockIndividuDS);

		CommunicationPreferencesDTO communicationPreferencesDelegate = new CommunicationPreferencesDTO();
		communicationPreferencesDelegate.setDomain("U");
		communicationPreferencesDelegate.setComGroupType("S");
		communicationPreferencesDelegate.setComType("UL_PS");
		communicationPreferencesDelegate.setSubscribe("Y");
		communicationPreferencesDelegate.setDateOptin(new Date());

		CommunicationPreferencesDTO communicationPreferencesDelegator = new CommunicationPreferencesDTO();
		communicationPreferencesDelegator.setDomain("U");
		communicationPreferencesDelegator.setComGroupType("S");
		communicationPreferencesDelegator.setComType("UL_PS");
		communicationPreferencesDelegator.setSubscribe("N");
		communicationPreferencesDelegator.setDateOptin(new Date());

		EasyMock.expect(_mockCommunicationPreferencesDS.findComPrefId(UltimateTest.STUB_DELEGATE_GIN, "U", "S", "UL_PS")).andReturn(communicationPreferencesDelegate);
		EasyMock.expect(_mockCommunicationPreferencesDS.findComPrefId(UltimateTest.STUB_DELEGATOR_GIN, "U", "S", "UL_PS")).andReturn(communicationPreferencesDelegator);
		EasyMock.replay(_mockCommunicationPreferencesDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);

		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");

		UltimateTest.LOGGER.info(lastError);
		Assert.assertNull(lastError);
		Assert.assertTrue(result);
	}

	@Test
	public final void testIsInputValidDelegatorAccountDataDoesntExist()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		ReflectionTestUtils.setField(_ultimate, "_delegationType", "Lulz");

		final IndividuDTO delegator = new IndividuDTO();
		final IndividuDTO delegate = new IndividuDTO();
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(delegator);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(delegate);
		EasyMock.replay(_mockIndividuDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");

		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(UltimateInputErrorCode.DELEGATOR_DOESNT_HAVE_ACCOUNT_DATA, lastError);
		Assert.assertEquals(false, result);

	}

	@Test
	public final void testIsInputValidDelegatorIsNotFlyingBlue()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegator.setAccountdatadto(delegatorAccount);
		final IndividuDTO delegate = new IndividuDTO();
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(delegator);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(delegate);
		EasyMock.replay(_mockIndividuDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");
		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(UltimateInputErrorCode.DELEGATOR_IS_NOT_A_FLYING_BLUE_CUSTOMER, lastError);
		Assert.assertEquals(false, result);

	}

	@Test
	public final void testIsInputValidDelegatorNotUltimate()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegatorAccount.setFbIdentifier(UltimateTest.DELEGATOR_FB);
		delegatorAccount.setSgin(UltimateTest.STUB_DELEGATOR_GIN);
		delegator.setAccountdatadto(delegatorAccount);
		delegator.setType("tata");
		final IndividuDTO delegate = new IndividuDTO();
		final AccountDataDTO delegateAccount = new AccountDataDTO();
		delegateAccount.setFbIdentifier(UltimateTest.DELEGATE_FB);
		delegateAccount.setSgin(UltimateTest.STUB_DELEGATE_GIN);
		delegate.setAccountdatadto(delegateAccount);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(delegator);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(delegate);
		EasyMock.replay(_mockIndividuDS);

		
/*		CommunicationPreferencesDTO communicationPreferences = new CommunicationPreferencesDTO();
		communicationPreferences.setDomain("U");
		communicationPreferences.setComGroupType("S");
		communicationPreferences.setComType("UL_PS");
		communicationPreferences.setSubscribe("Y");
		communicationPreferences.setDateOptin(new Date());
*/
		EasyMock.expect(_mockCommunicationPreferencesDS.findComPrefId(UltimateTest.STUB_DELEGATOR_GIN, "U", "S", "UL_PS")).andReturn(null);
		EasyMock.expect(_mockCommunicationPreferencesDS.findComPrefId(UltimateTest.STUB_DELEGATE_GIN, "U", "S", "UL_PS")).andReturn(null);
		EasyMock.replay(_mockCommunicationPreferencesDS);
		
		EasyMock.reset(_mockRoleDS);
		final RoleContratsDTO delegatorRole = new RoleContratsDTO();
		delegatorRole.setMemberType("TT");
		final RoleContratsDTO delegateRole = new RoleContratsDTO();
		delegateRole.setMemberType("TT");
		EasyMock.expect(_mockRoleDS.findRoleContratsFP(UltimateTest.DELEGATOR_FB)).andReturn(delegatorRole);
		EasyMock.expect(_mockRoleDS.findRoleContratsFP(UltimateTest.DELEGATE_FB)).andReturn(delegateRole);
		EasyMock.replay(_mockRoleDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);

		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");
		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(UltimateInputErrorCode.DELEGATE_IS_NOT_A_ULTIMATE_CUSTOMER, lastError);
		Assert.assertEquals(false, result);

	}

	@Test
	public final void testIsInputValidSuccess()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegatorAccount.setFbIdentifier(UltimateTest.DELEGATOR_FB);
		delegatorAccount.setSgin(UltimateTest.STUB_DELEGATOR_GIN);
		delegator.setAccountdatadto(delegatorAccount);

		final IndividuDTO delegate = new IndividuDTO();
		final AccountDataDTO delegateAccount = new AccountDataDTO();
		delegateAccount.setFbIdentifier(UltimateTest.DELEGATE_FB);
		delegateAccount.setSgin(UltimateTest.STUB_DELEGATE_GIN);
		delegate.setAccountdatadto(delegateAccount);
		delegate.setType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());

		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(delegator);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(delegate);
		EasyMock.replay(_mockIndividuDS);
		
		CommunicationPreferencesDTO communicationPreferences = new CommunicationPreferencesDTO();
		communicationPreferences.setDomain("U");
		communicationPreferences.setComGroupType("S");
		communicationPreferences.setComType("UL_PS");
		communicationPreferences.setSubscribe("Y");
		communicationPreferences.setDateOptin(new Date());

		EasyMock.expect(_mockCommunicationPreferencesDS.findComPrefId(UltimateTest.STUB_DELEGATOR_GIN, "U", "S", "UL_PS")).andReturn(null);
		EasyMock.expect(_mockCommunicationPreferencesDS.findComPrefId(UltimateTest.STUB_DELEGATE_GIN, "U", "S", "UL_PS")).andReturn(communicationPreferences);
		EasyMock.replay(_mockCommunicationPreferencesDS);
		
		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);

		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");
		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(null, lastError);
		Assert.assertEquals(true, result);

	}

	@Test
	public final void testIsInputValidWrongUltimateType()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, JrafDomainException, InstantiationException {

		ReflectionTestUtils.setField(_ultimate, "_delegationType", "Lulz");

		final IndividuDTO delegate = new IndividuDTO();
		final AccountDataDTO delegateAccount = new AccountDataDTO();
		delegateAccount.setFbIdentifier(UltimateTest.DELEGATE_FB);
		delegateAccount.setSgin(UltimateTest.STUB_DELEGATE_GIN);
		delegate.setAccountdatadto(delegateAccount);
		delegate.setType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());

		final IndividuDTO delegator = new IndividuDTO();
		final AccountDataDTO delegatorAccount = new AccountDataDTO();
		delegatorAccount.setFbIdentifier(UltimateTest.DELEGATOR_FB);
		delegatorAccount.setSgin(UltimateTest.STUB_DELEGATOR_GIN);
		delegator.setAccountdatadto(delegatorAccount);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATOR_GIN)).andReturn(delegator);
		EasyMock.expect(_mockIndividuDS.getByGin(UltimateTest.STUB_DELEGATE_GIN)).andReturn(delegate);
		EasyMock.replay(_mockIndividuDS);

		
		CommunicationPreferencesDTO communicationPreferences = new CommunicationPreferencesDTO();
		communicationPreferences.setDomain("U");
		communicationPreferences.setComGroupType("S");
		communicationPreferences.setComType("UL_PS");
		communicationPreferences.setSubscribe("Y");
		communicationPreferences.setDateOptin(new Date());

		EasyMock.expect(_mockCommunicationPreferencesDS.findComPrefId(UltimateTest.STUB_DELEGATOR_GIN, "U", "S", "UL_PS")).andReturn(null);
		EasyMock.expect(_mockCommunicationPreferencesDS.findComPrefId(UltimateTest.STUB_DELEGATE_GIN, "U", "S", "UL_PS")).andReturn(communicationPreferences);
		EasyMock.replay(_mockCommunicationPreferencesDS);
		
		
		final Method method = _ultimate.getClass().getDeclaredMethod("_isInputValid");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		final UltimateInputErrorCode lastError = (UltimateInputErrorCode) ReflectionTestUtils.getField(_ultimate,
				"_lastError");

		UltimateTest.LOGGER.info(lastError);
		Assert.assertEquals(UltimateInputErrorCode.ULTIMATE_DELEGATION_TYPE_NOT_ACCEPTED, lastError);
		Assert.assertEquals(false, result);

	}

	@Test
	public final void testIsUltimateFamilyTooBigCaseOverSize() throws UltimateException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException, JrafDomainException,
			NoSuchMethodException, InvocationTargetException, InstantiationException {

		final int overLimit = UltimateTest.MAXIMUM_FAMILY_SIZE + 1;
		final List<DelegationDataDTO> overLimitList = new ArrayList<>();
		for (int i = 0; i < overLimit; i++) {
			final DelegationDataDTO delegation = new DelegationDataDTO();
			delegation.setType(UltimateFamilyTypeCode.ULTIMATE_FAMILY.getUltimateFamilyCode());
			overLimitList.add(delegation);
		}

		EasyMock.expect(_mockDelegationDataDS.findDelegator(UltimateTest.STUB_DELEGATE_GIN)).andReturn(overLimitList);
		EasyMock.replay(_mockDelegationDataDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isUltimateFamilyTooBig");
		method.setAccessible(true);

		try {
			method.invoke(_ultimate);
			Assert.fail();
		} catch (final InvocationTargetException e) {
			Assert.assertEquals(UltimateException.class, e.getCause().getClass());
		}
	}

	@Test
	public final void testIsUltimateFamilyTooBigCaseSameSize() throws UltimateException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException, JrafDomainException,
			NoSuchMethodException, InvocationTargetException, InstantiationException {

		final List<DelegationDataDTO> limitList = new ArrayList<>();
		for (int i = 0; i < UltimateTest.MAXIMUM_FAMILY_SIZE; i++) {
			final DelegationDataDTO delegation = new DelegationDataDTO();
			delegation.setType(UltimateFamilyTypeCode.ULTIMATE_FAMILY.getUltimateFamilyCode());
			limitList.add(delegation);
		}

		EasyMock.expect(_mockDelegationDataDS.findDelegator(UltimateTest.STUB_DELEGATE_GIN)).andReturn(limitList);
		EasyMock.replay(_mockDelegationDataDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isUltimateFamilyTooBig");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		Assert.assertEquals(true, result);
	}

	@Test
	public final void testIsUltimateFamilyTooBigCaseUnderSize() throws UltimateException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException, JrafDomainException,
			NoSuchMethodException, InvocationTargetException, InstantiationException {

		final int underLimit = UltimateTest.MAXIMUM_FAMILY_SIZE - 1;
		final IndividuDTO individuUnderLimit = new IndividuDTO();
		individuUnderLimit.setStatutIndividu(String.valueOf(underLimit));

		final DelegationDataDTO delegateTestUnderLimit = new DelegationDataDTO();
		delegateTestUnderLimit.setDelegatorDTO(individuUnderLimit);
		final List<DelegationDataDTO> underLimitList = new ArrayList<>();
		for (int i = 0; i < underLimit; i++) {
			final DelegationDataDTO delegation = new DelegationDataDTO();
			delegation.setType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());
			underLimitList.add(delegation);
		}

		EasyMock.expect(_mockDelegationDataDS.findDelegator(UltimateTest.STUB_DELEGATE_GIN)).andReturn(underLimitList);
		EasyMock.replay(_mockDelegationDataDS);

		final Method method = _ultimate.getClass().getDeclaredMethod("_isUltimateFamilyTooBig");
		method.setAccessible(true);

		final boolean result = (boolean) method.invoke(_ultimate);
		Assert.assertEquals(false, result);
	}

	@Test
	@Transactional
	@Rollback(true)
	public final void testProcessAlreadyLinkedToUltimateCustomer() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		final Ultimate ultimate = new UltimateTesting(UltimateTest.STUB_DELEGATOR_GIN, UltimateTest.STUB_DELEGATE_GIN,
				UltimateTest.STUB_DELEGATION_TYPE, UltimateTest.STUB_DELEGATION_STATUS, UltimateTest.STUB_SIGNATURE,
				UltimateTest.STUB_MANAGING_COMPANY, UltimateTest.STUB_DELEGATOR_GIN) {

			@Override
			protected boolean _isDelegatorAlreadyLinkedToAUltimateCustomer() {
				return true;
			}

			@Override
			protected boolean _isInputValid() {
				return true;
			}

			@Override
			protected boolean _isUltimateFamilyTooBig() throws UltimateException {
				return false;
			}

			@Override
			protected void _process(IndividuDS individu, final DelegationDataDS delegationData, final RoleDS role,
					final CommunicationPreferencesDS communicationPreferences, 
					final VariablesRepository variables) throws JrafDomainException, UltimateException {
				super._process(individu, delegationData, role, communicationPreferences, variables);
			}
		};

		ReflectionTestUtils.setField(ultimate, "_delegationDataDS", delegationDataDS);
		ReflectionTestUtils.setField(ultimate, "_individuDS", individuDS);
		ReflectionTestUtils.setField(ultimate, "_delegatorGin", UltimateTest._delegatorSGin);
		ReflectionTestUtils.setField(ultimate, "_delegateGin", UltimateTest._delegateSGin);
		final Method method = ultimate.getClass().getDeclaredMethod("_process", IndividuDS.class,
				DelegationDataDS.class, RoleDS.class, CommunicationPreferencesDS.class, VariablesRepository.class);
		method.setAccessible(true);

		try {
			method.invoke(ultimate, individuDS, delegationDataDS, _mockRoleDS, _mockCommunicationPreferencesDS, variablesRepository);
		} catch (final InvocationTargetException e) {
			if (e.getCause().getClass() != UltimateException.class || !e.getCause().getMessage()
					.contains(UltimateBusinessErrorCode.ALREADY_LINKED_TO_A_ULTIMATE_CUSTOMER.name())) {
				Assert.fail();
			}

		}

	}

	@Test
	@Transactional
	@Rollback(true)
	public final void testProcessFamilyTooBig1() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		final Ultimate ultimate = new UltimateTesting(UltimateTest.STUB_DELEGATOR_GIN, UltimateTest.STUB_DELEGATE_GIN,
				UltimateTest.STUB_DELEGATION_TYPE, UltimateTest.STUB_DELEGATION_STATUS, UltimateTest.STUB_SIGNATURE,
				UltimateTest.STUB_MANAGING_COMPANY, UltimateTest.STUB_DELEGATOR_GIN) {

			@Override
			protected boolean _isInputValid() {
				return true;
			}

			@Override
			protected boolean _isUltimateFamilyTooBig() {
				return true;
			}

			@Override
			protected void _process(final IndividuDS individu, final DelegationDataDS delegationData,
					final RoleDS role, final CommunicationPreferencesDS communicationPreferences, final VariablesRepository variables) throws JrafDomainException, UltimateException {
				super._process(individu, delegationData, role, communicationPreferences, variables);
			}
		};

		ReflectionTestUtils.setField(ultimate, "_delegationDataDS", delegationDataDS);
		ReflectionTestUtils.setField(ultimate, "_individuDS", individuDS);
		ReflectionTestUtils.setField(ultimate, "_delegatorGin", UltimateTest._delegatorSGin);
		ReflectionTestUtils.setField(ultimate, "_delegateGin", UltimateTest._delegateSGin);
		final Method method = ultimate.getClass().getDeclaredMethod("_process", IndividuDS.class,
				DelegationDataDS.class, RoleDS.class, CommunicationPreferencesDS.class, VariablesRepository.class);
		method.setAccessible(true);

		try {
			method.invoke(ultimate, individuDS, delegationDataDS, _mockRoleDS, _mockCommunicationPreferencesDS, variablesRepository);
		} catch (final InvocationTargetException e) {
			if (e.getCause().getClass() != UltimateException.class
					|| !e.getCause().getMessage().contains(UltimateBusinessErrorCode.TOO_MANY_FAMILY_MEMBERS.name())) {
				Assert.fail();
			}

		}

	}

	@Test
	@Transactional
	@Rollback(true)
	public final void testProcessFamilyTooBig2() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		final Ultimate ultimate = new UltimateTesting(UltimateTest.STUB_DELEGATOR_GIN, UltimateTest.STUB_DELEGATE_GIN,
				UltimateTest.STUB_DELEGATION_TYPE, UltimateTest.STUB_DELEGATION_STATUS, UltimateTest.STUB_SIGNATURE,
				UltimateTest.STUB_MANAGING_COMPANY, UltimateTest.STUB_DELEGATOR_GIN) {

			@Override
			protected boolean _isInputValid() {
				return true;
			}

			@Override
			protected boolean _isUltimateFamilyTooBig() throws UltimateException {
				throw new UltimateException("");
			}

			@Override
			protected void _process(final IndividuDS individu, final DelegationDataDS delegationData,
					final RoleDS role, final CommunicationPreferencesDS communicationPreferences, final VariablesRepository variables) throws JrafDomainException, UltimateException {
				super._process(individu, delegationData, role, communicationPreferences, variables);
			}
		};

		ReflectionTestUtils.setField(ultimate, "_delegationDataDS", delegationDataDS);
		ReflectionTestUtils.setField(ultimate, "_individuDS", individuDS);
		ReflectionTestUtils.setField(ultimate, "_delegatorGin", UltimateTest._delegatorSGin);
		ReflectionTestUtils.setField(ultimate, "_delegateGin", UltimateTest._delegateSGin);
		final Method method = ultimate.getClass().getDeclaredMethod("_process", IndividuDS.class,
				DelegationDataDS.class, RoleDS.class, CommunicationPreferencesDS.class, VariablesRepository.class);
		method.setAccessible(true);

		try {
			method.invoke(ultimate, individuDS, delegationDataDS, _mockRoleDS, _mockCommunicationPreferencesDS, variablesRepository);
		} catch (final InvocationTargetException e) {
			if (e.getCause().getClass() != UltimateException.class) {
				Assert.fail();
			}

		}

	}

	@Test
	@Transactional
	@Rollback(true)
	public final void testProcessInputValidationFailure() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		final Ultimate ultimate = new UltimateTesting(UltimateTest.STUB_DELEGATOR_GIN, UltimateTest.STUB_DELEGATE_GIN,
				UltimateTest.STUB_DELEGATION_TYPE, UltimateTest.STUB_DELEGATION_STATUS, UltimateTest.STUB_SIGNATURE,
				UltimateTest.STUB_MANAGING_COMPANY, UltimateTest.STUB_DELEGATOR_GIN) {

			@Override
			protected boolean _isInputValid() {
				return false;
			}

			@Override
			protected void _process(final IndividuDS individu, final DelegationDataDS delegationData,
					final RoleDS role, final CommunicationPreferencesDS communicationPreferences, final VariablesRepository variables) throws JrafDomainException, UltimateException {
				super._process(individu, delegationData, role, communicationPreferences, variables);
			}
		};

		ReflectionTestUtils.setField(ultimate, "_delegationDataDS", delegationDataDS);
		ReflectionTestUtils.setField(ultimate, "_individuDS", individuDS);
		ReflectionTestUtils.setField(ultimate, "_delegatorGin", UltimateTest._delegatorSGin);
		ReflectionTestUtils.setField(ultimate, "_delegateGin", UltimateTest._delegateSGin);
		ReflectionTestUtils.setField(ultimate, "_lastError", UltimateInputErrorCode.DELEGATE_DOESNT_EXIST);
		final Method method = ultimate.getClass().getDeclaredMethod("_process", IndividuDS.class,
				DelegationDataDS.class, RoleDS.class, CommunicationPreferencesDS.class, VariablesRepository.class);
		method.setAccessible(true);

		try {
			method.invoke(ultimate, individuDS, delegationDataDS, _mockRoleDS, _mockCommunicationPreferencesDS, variablesRepository);
		} catch (final InvocationTargetException e) {
			if (e.getCause().getClass() != UltimateException.class
					|| !e.getCause().getMessage().contains(UltimateInputErrorCode.DELEGATE_DOESNT_EXIST.name())) {
				Assert.fail();
			}

		}

	}

	@Test(expected = Test.None.class /* no exception expected */)
	@Transactional
	@Rollback(true)
	public final void testProcessSuccess() throws Exception {

		ReflectionTestUtils.setField(_ultimate, "_delegationDataDS", delegationDataDS);
		ReflectionTestUtils.setField(_ultimate, "_individuDS", individuDS);
		ReflectionTestUtils.setField(_ultimate, "_roleDS", roleDS);
		ReflectionTestUtils.setField(_ultimate, "_communicationPreferencesDS", communicationPreferencesDS);
		ReflectionTestUtils.setField(_ultimate, "_delegatorGin", UltimateTest._delegatorSGin);
		ReflectionTestUtils.setField(_ultimate, "_delegateGin", UltimateTest._delegateSGin);
		ReflectionTestUtils.setField(_ultimate, "_originGin", UltimateTest._delegateSGin);
		final Method method = _ultimate.getClass().getDeclaredMethod("_process", IndividuDS.class,
				DelegationDataDS.class, RoleDS.class, CommunicationPreferencesDS.class, VariablesRepository.class);
		method.setAccessible(true);
		method.invoke(_ultimate, individuDS, delegationDataDS, roleDS, communicationPreferencesDS, variablesRepository);

	}

}
