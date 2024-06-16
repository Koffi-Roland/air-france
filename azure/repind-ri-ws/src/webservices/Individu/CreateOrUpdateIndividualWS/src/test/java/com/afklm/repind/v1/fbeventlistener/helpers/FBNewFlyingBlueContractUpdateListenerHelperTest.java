package com.afklm.repind.v1.fbeventlistener.helpers;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.utils.FbLevelDetailTypeEnum;
import com.afklm.repind.utils.FbLevelLabelEnum;
import com.afklm.repind.v1.fbeventlistener.FBNewFlyingBlueContractUpdateListenerV1;
import com.afklm.soa.stubs.w001815.v2.xsd1.*;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.preference.PreferenceRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.util.SicStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 
 * @author T412211
 *
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class FBNewFlyingBlueContractUpdateListenerHelperTest {

	@Autowired
	protected FBNewFlyingBlueContractUpdateListenerV1 fb;
	
	@Autowired
	protected RoleContratsRepository roleContratsRepository;
	
	@Autowired
	private CommunicationPreferencesRepository communicationPreferencesRepository;
	
	@Autowired
	private PreferenceRepository preferenceRepository;
	
	private static final String GIN_TEST_DOWNGRADE = "400255047252";
	private static final String CIN_TEST_DOWNGRADE = "001649551104";
	
	private static final String GIN_TEST_UPGRADE = "400351954574";
	private static final String CIN_TEST_UPGRADE = "002075815113";
	
	private static final List<String> ULTIMATE_PREFERENCES = new ArrayList<>(Arrays.asList("UCO", "UFD", "UFB", "ULS", "ULO", "UMU", "UOB", "UPM", "UST", "UTS", "UTF"));
	
	@Before
	public void setUp() {

	}

	@Test(expected = Test.None.class /* no exception expected */)
	@Rollback(true)
	@Ignore
	public void sendUpdatedContractTest(){
		
		FBNContractUpdateEvent event = new FBNContractUpdateEvent();
		event.setSequenceNumber(2226249);
		event.setEventCreationTime(new Date());
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setGin(400375220640l);
		individualBlock.setCin(2086793412);
		
		event.setIndividualBlock(individualBlock);
		
		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelLabel(FbLevelLabelEnum.EXPLORER.value());
		FBLevelStructure fbLevelStructure = new FBLevelStructure();
		fbLevelStructure.setFbLevelDetailRanking(1);
		fbLevelStructure.setFbLevelDetailType(FbLevelDetailTypeEnum.TIER.value());
		fbLevelStructure.setFbLevelDetailValue("A");
		fbIdentificationBlock.setPriority1A(0);
		fbIdentificationBlock.setPtc("ADT");
		fbIdentificationBlock.setFbLogoUrl("https://afkl-test.bynder.com/m/047c884d44dcee3d/original/fb30_Explorer.png");
		fbIdentificationBlock.setFbLogoCode("FB30_FB_A");
		FBPropertyExtension extension = new FBPropertyExtension();
		extension.setKey("FB_LEVEL");
		extension.setValue("FB_A");
		fbIdentificationBlock.getIdentificationExtensions().add(extension);
		
		event.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("V");
		contractBlock.setStatusReason("V1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		FBPropertyExtension extensionContract = new FBPropertyExtension();
		extensionContract.setKey("WEL-BONUS");
		extensionContract.setValue("false");
		contractBlock.getContractExtensions().add(extensionContract);
		
		event.setContractBlock(contractBlock);
		
		TierAndSubtierBlock tierAndSubTierBlock = new TierAndSubtierBlock();
		
		CurrentTierCharacteristics tierCharacteristics = new CurrentTierCharacteristics();
		tierCharacteristics.setCurrentTierCode("A");
		tierCharacteristics.setCurrentTierLabel("Explorer");
		tierCharacteristics.setCurrentStartValidityDate(new Date());
		
		tierAndSubTierBlock.setCurrentTierCharacteristics(tierCharacteristics);
		
		NextTierLevel nextTierLevel = new NextTierLevel();
		nextTierLevel.setNextTierLevelCode("B");
		nextTierLevel.setNextTierLevelLabel("Silver");
		nextTierLevel.setMissingXPToUpgradeToNextTier(100);
		nextTierLevel.setPercentageThresholdToUpgradeToNextTier(0);
		nextTierLevel.setNextTierLevelThreshold(100);
		
		tierAndSubTierBlock.setNextTierLevel(nextTierLevel);
		
		event.setTierAndSubtierBlock(tierAndSubTierBlock);
		
		AccountBlock accountBlock = new AccountBlock();
		Miles miles = new Miles();
		miles.setGlobalAmountMilesBalance(0);
		miles.setFirstActivityDate(new Date());
		miles.setLastActivityDate(new Date());
		miles.setLastDebitCreditOperationDate(new Date());
		MilesCharacteristics milesCharacteristic = new MilesCharacteristics();
		milesCharacteristic.setMilesType("COMMERCIAL");
		milesCharacteristic.setMilesAmount(0);
		milesCharacteristic.setIsProtected(false);
		
		miles.getMilesCharacteristics().add(milesCharacteristic);
		
		milesCharacteristic = new MilesCharacteristics();
		milesCharacteristic.setMilesType("FLIGHT");
		milesCharacteristic.setMilesAmount(0);
		milesCharacteristic.setIsProtected(false);
		
		miles.getMilesCharacteristics().add(milesCharacteristic);
		
		QualificationUnit qualificationUnit = new QualificationUnit();
		qualificationUnit.setQualifyingCounterType(FbLevelDetailTypeEnum.TIER.value());
		qualificationUnit.setCounterUnit("XP_TIER");
		qualificationUnit.setCounterGlobalBalance(0);
		qualificationUnit.setStartDateValidityQPeriod(new Date());
		qualificationUnit.setEndDateValidityQPeriod(new Date());
		
		accountBlock.getQualificationUnits().add(qualificationUnit);
		
		accountBlock.setMiles(miles);
	
		event.setAccountBlock(accountBlock);
		
		AuthorizationBlock authorizationBlock = new AuthorizationBlock();
		authorizationBlock.setEarnAuthorization(true);
		authorizationBlock.setBurnAuthorization(true);
		authorizationBlock.setShareAndFlyAuthorization(false);
		
		event.setAuthorizationBlock(authorizationBlock);
		

		  
		fb.sendUpdatedContract(event);
	}

	@Test(expected = Test.None.class /* no exception expected */)
	@Rollback(true)
//	@Transactional(value="myTxManager")
	public void sendUpdatedContract_TIER_Ultimate_Test(){
		
		FBNContractUpdateEvent event = new FBNContractUpdateEvent();
		event.setSequenceNumber(3226249);
		event.setEventCreationTime(new Date());
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setGin(400000039083L);
		individualBlock.setCin(1269741545);
		
		event.setIndividualBlock(individualBlock);
		
		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		FBLevelStructure fbLevelStructure = new FBLevelStructure();
		fbLevelStructure.setFbLevelDetailRanking(1);
		fbLevelStructure.setFbLevelDetailType(FbLevelDetailTypeEnum.TIER.value());
		fbLevelStructure.setFbLevelDetailValue("ULTIMATE");
		
		TierAndSubtierBlock tastb = new TierAndSubtierBlock();
		CurrentTierCharacteristics tc = new CurrentTierCharacteristics();
		tc.setCurrentStartValidityDate(new GregorianCalendar(2010, 1, 1).getTime());	// 01/02/2010
		tastb.setCurrentTierCharacteristics(tc);
		event.setTierAndSubtierBlock(tastb);
		
		fbIdentificationBlock.setFbLevelEndValidityDate(new GregorianCalendar(2020, 1, 1).getTime());	// 01/02/2020
		fbIdentificationBlock.getFbLevelStructure().add(fbLevelStructure);
		
		event.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("V");
		
		event.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(event);
	}

	@Test(expected = Test.None.class /* no exception expected */)
// 	@Rollback(true)
//	@Transactional(value="myTxManager")
	public void sendUpdatedContract_ATTRIBUTES_Ultimate_Test(){
		
		FBNContractUpdateEvent event = new FBNContractUpdateEvent();
		event.setSequenceNumber(3226249);
		event.setEventCreationTime(new Date());
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setGin(400000039083L);
		individualBlock.setCin(1269741545);
		
		event.setIndividualBlock(individualBlock);
		
		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		FBLevelStructure fbLevelStructure = new FBLevelStructure();
		fbLevelStructure.setFbLevelDetailRanking(1);
		fbLevelStructure.setFbLevelDetailType("ATTRIBUTE");
		fbLevelStructure.setFbLevelDetailValue("ULTIMATE");
		
		AttributesBlock ab = new AttributesBlock();
		CurrentTierAttribute ta = new CurrentTierAttribute();
		ta.setCurrentAttributeStartValidityDate(new GregorianCalendar(2010, 2, 2).getTime());	// 02/03/2010
		ta.setCurrentAttributeCode(fbLevelStructure.getFbLevelDetailValue()); 							// On match
		
		ab.getCurrentTierAttribute().add(ta);
		event.setAttributesBlock(ab);
		
		fbIdentificationBlock.setFbLevelEndValidityDate(new GregorianCalendar(2020, 2, 2).getTime());	// 02/03/2020
		fbIdentificationBlock.getFbLevelStructure().add(fbLevelStructure);
		
		event.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("V");
		
		event.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(event);
	}

	@Test(expected = Test.None.class /* no exception expected */)
	public void transformFBNewContractUpdateEventToRequestTest(){
		FBNContractUpdateEvent contract = new FBNContractUpdateEvent();
		
		String site = "QVI";
		String signature = "TEST";
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setCin(123456789012L);
		individualBlock.setGin(121234567890L);
		contract.setIndividualBlock(individualBlock);
		
		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		contract.setFbIdentificationBlock(fbIdentificationBlock);
		
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("V");
		
		contract.setContractBlock(contractBlock);

		
		fb.sendUpdatedContract(contract);
		
	}


	
	@Test
	@Rollback(true)
	public void transformFBNewContractUpdateEventFromConfirmToConfirmTest() throws JrafDaoException{
		FBNContractUpdateEvent contract = new FBNContractUpdateEvent();
		
		Long cin = 1326462291L;
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setCin(cin);
		contract.setIndividualBlock(individualBlock);

		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		contract.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("V");
		contractBlock.setStatusReason("V1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		contract.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(contract);
		
		RoleContrats roleFb = roleContratsRepository.findRoleContratsByNumContract(SicStringUtils.addingZeroToTheLeft(cin.toString(), 12));
		
		Assert.assertNotNull(roleFb);
		Assert.assertNotNull(roleFb.getEtat());
		Assert.assertNotNull("C", roleFb.getEtat());
	}

	@Test
	@Rollback(true)
	public void transformFBNewContractUpdateEventFromConfirmToCanceledTest() throws JrafDaoException {
		FBNContractUpdateEvent contract = new FBNContractUpdateEvent();
		
		Long cin = 1728201516L;
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setCin(cin);
		contract.setIndividualBlock(individualBlock);

		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		contract.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("C");
		contractBlock.setStatusReason("C1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		contract.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(contract);
		
		RoleContrats roleFb = roleContratsRepository.findRoleContratsByNumContract(SicStringUtils.addingZeroToTheLeft(cin.toString(), 12));
		
		Assert.assertNotNull(roleFb);
		Assert.assertNotNull(roleFb.getEtat());
		Assert.assertNotNull("A", roleFb.getEtat());
	}

	@Test
	@Rollback(true)
	public void transformFBNewContractUpdateEventFromConfirmedToSuspendedTest() throws JrafDaoException{
		FBNContractUpdateEvent contract = new FBNContractUpdateEvent();
		
		Long cin = 1100839385L;
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setCin(cin);
		contract.setIndividualBlock(individualBlock);

		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		contract.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("S");
		contractBlock.setStatusReason("S1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		contract.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(contract);
		
		RoleContrats roleFb = roleContratsRepository.findRoleContratsByNumContract(SicStringUtils.addingZeroToTheLeft(cin.toString(), 12));
		
		Assert.assertNotNull(roleFb);
		Assert.assertNotNull(roleFb.getEtat());
		Assert.assertNotNull("S", roleFb.getEtat());
	}
	
	
	@Test
	@Rollback(true)
	public void transformFBNewContractUpdateEventFromAnnuledToConfirmTest() throws JrafDaoException{
		FBNContractUpdateEvent contract = new FBNContractUpdateEvent();
		
		Long cin = 1031878841L;
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setCin(cin);
		contract.setIndividualBlock(individualBlock);

		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		contract.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("V");
		contractBlock.setStatusReason("V1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		contract.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(contract);
		
		RoleContrats roleFb = roleContratsRepository.findRoleContratsByNumContract(SicStringUtils.addingZeroToTheLeft(cin.toString(), 12));
		
		Assert.assertNotNull(roleFb);
		Assert.assertNotNull(roleFb.getEtat());
		Assert.assertNotNull("C", roleFb.getEtat());
	}

	@Test
	@Rollback(true)
	public void transformFBNewContractUpdateEventFromAnnuledToCanceledTest() throws JrafDaoException {
		FBNContractUpdateEvent contract = new FBNContractUpdateEvent();
		
		Long cin = 2075481773L;
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setCin(cin);
		contract.setIndividualBlock(individualBlock);

		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		contract.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("C");
		contractBlock.setStatusReason("C1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		contract.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(contract);
		
		RoleContrats roleFb = roleContratsRepository.findRoleContratsByNumContract(SicStringUtils.addingZeroToTheLeft(cin.toString(), 12));
		
		Assert.assertNotNull(roleFb);
		Assert.assertNotNull(roleFb.getEtat());
		Assert.assertNotNull("A", roleFb.getEtat());
	}

	@Test
	@Rollback(true)
	public void transformFBNewContractUpdateEventFromAnnuledToSuspendedTest() throws JrafDaoException{
		FBNContractUpdateEvent contract = new FBNContractUpdateEvent();
		
		Long cin = 1023905152L;
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setCin(cin);
		contract.setIndividualBlock(individualBlock);

		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		contract.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("S");
		contractBlock.setStatusReason("S1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		contract.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(contract);
		
		RoleContrats roleFb = roleContratsRepository.findRoleContratsByNumContract(SicStringUtils.addingZeroToTheLeft(cin.toString(), 12));
		
		Assert.assertNotNull(roleFb);
		Assert.assertNotNull(roleFb.getEtat());
		Assert.assertNotNull("S", roleFb.getEtat());
	}
	

	

	// ACTION FROM SUSPENDED
	
	
	@Test
	@Rollback(true)
	public void transformFBNewContractUpdateEventFromSuspendedToConfirmTest() throws JrafDaoException{
		FBNContractUpdateEvent contract = new FBNContractUpdateEvent();
		
		Long cin = 2097692445L;
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setCin(cin);
		contract.setIndividualBlock(individualBlock);

		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		contract.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("V");
		contractBlock.setStatusReason("V1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		contract.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(contract);
		
		RoleContrats roleFb = roleContratsRepository.findRoleContratsByNumContract(SicStringUtils.addingZeroToTheLeft(cin.toString(), 12));
		
		Assert.assertNotNull(roleFb);
		Assert.assertNotNull(roleFb.getEtat());
		Assert.assertNotNull("C", roleFb.getEtat());
	}

	@Test
	@Rollback(true)
	public void transformFBNewContractUpdateEventFromSuspendedToCanceledTest() throws JrafDaoException {
		FBNContractUpdateEvent contract = new FBNContractUpdateEvent();
		
		Long cin = 2024195982L;
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setCin(cin);
		contract.setIndividualBlock(individualBlock);

		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		contract.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("C");
		contractBlock.setStatusReason("C1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		contract.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(contract);
		
		RoleContrats roleFb = roleContratsRepository.findRoleContratsByNumContract(SicStringUtils.addingZeroToTheLeft(cin.toString(), 12));
		
		Assert.assertNotNull(roleFb);
		Assert.assertNotNull(roleFb.getEtat());
		Assert.assertNotNull("A", roleFb.getEtat());
	}

	@Test
	@Rollback(true)
	public void transformFBNewContractUpdateEventFromSuspendedToSuspendedTest() throws JrafDaoException{
		FBNContractUpdateEvent contract = new FBNContractUpdateEvent();
		
		Long cin = 2027095426L;
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setCin(cin);
		contract.setIndividualBlock(individualBlock);

		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		contract.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("S");
		contractBlock.setStatusReason("S1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		contract.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(contract);
		
		RoleContrats roleFb = roleContratsRepository.findRoleContratsByNumContract(SicStringUtils.addingZeroToTheLeft(cin.toString(), 12));
		
		Assert.assertNotNull(roleFb);
		Assert.assertNotNull(roleFb.getEtat());
		Assert.assertNotNull("S", roleFb.getEtat());
	}
	
	@Test
	@Rollback(true)
	public void testDowngradeUltimate() throws JrafDaoException {
		
		List<CommunicationPreferences> ultimateComprefs = communicationPreferencesRepository.findComPrefIdByDomain(GIN_TEST_DOWNGRADE, "U");
		Assert.assertEquals(1, ultimateComprefs.size());
		
		List<Preference> preferences = preferenceRepository.findByGin(GIN_TEST_DOWNGRADE);
		List<Preference> ultimatePreferences = preferences.stream().filter(it -> ULTIMATE_PREFERENCES.contains(it.getType())).collect(Collectors.toList());
		Assert.assertEquals(2, ultimatePreferences.size());
		
		FBNContractUpdateEvent fbnEvent = new FBNContractUpdateEvent();
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setGin(Long.parseLong(GIN_TEST_DOWNGRADE));
		individualBlock.setCin(Long.parseLong(CIN_TEST_DOWNGRADE));
		fbnEvent.setIndividualBlock(individualBlock);
		
		AttributesBlock attributesBlock = new AttributesBlock();
		FBPropertyExtension fbPropertyExtension = new FBPropertyExtension();
		fbPropertyExtension.setKey("ULTIMATE");
		fbPropertyExtension.setValue("DOWNGRADE");
		attributesBlock.getAttributeExtensions().add(fbPropertyExtension);
		fbnEvent.setAttributesBlock(attributesBlock);
		
		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		fbnEvent.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("S");
		contractBlock.setStatusReason("S1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		fbnEvent.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(fbnEvent);
		
		ultimateComprefs = communicationPreferencesRepository.findComPrefIdByDomain(GIN_TEST_DOWNGRADE, "U");
		Assert.assertEquals(0, ultimateComprefs.size());
		
		preferences = preferenceRepository.findByGin(GIN_TEST_DOWNGRADE);
		ultimatePreferences = preferences.stream().filter(it -> ULTIMATE_PREFERENCES.contains(it.getType())).collect(Collectors.toList());
		Assert.assertEquals(0, ultimatePreferences.size());
	}
	
	@Test
	@Rollback(true)
	public void testDowngradeUltimateNoAtribute() throws JrafDaoException {
		
		List<CommunicationPreferences> ultimateComprefs = communicationPreferencesRepository.findComPrefIdByDomain(GIN_TEST_DOWNGRADE, "U");
		Assert.assertEquals(1, ultimateComprefs.size());
		
		List<Preference> preferences = preferenceRepository.findByGin(GIN_TEST_DOWNGRADE);
		List<Preference> ultimatePreferences = preferences.stream().filter(it -> ULTIMATE_PREFERENCES.contains(it.getType())).collect(Collectors.toList());
		Assert.assertEquals(2, ultimatePreferences.size());
		
		FBNContractUpdateEvent fbnEvent = new FBNContractUpdateEvent();
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setGin(Long.parseLong(GIN_TEST_DOWNGRADE));
		individualBlock.setCin(Long.parseLong(CIN_TEST_DOWNGRADE));
		fbnEvent.setIndividualBlock(individualBlock);
				
		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		fbnEvent.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("S");
		contractBlock.setStatusReason("S1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		fbnEvent.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(fbnEvent);
		
		ultimateComprefs = communicationPreferencesRepository.findComPrefIdByDomain(GIN_TEST_DOWNGRADE, "U");
		Assert.assertEquals(1, ultimateComprefs.size());
		
		preferences = preferenceRepository.findByGin(GIN_TEST_DOWNGRADE);
		ultimatePreferences = preferences.stream().filter(it -> ULTIMATE_PREFERENCES.contains(it.getType())).collect(Collectors.toList());
		Assert.assertEquals(2, ultimatePreferences.size());
	}
	
	@Test
	@Rollback(true)
	public void testUpgradeUltimate() throws JrafDaoException {
		
		List<CommunicationPreferences> ultimateComprefs = communicationPreferencesRepository.findComPrefIdByDomain(GIN_TEST_UPGRADE, "U");
		Assert.assertEquals(0, ultimateComprefs.size());
				
		FBNContractUpdateEvent fbnEvent = new FBNContractUpdateEvent();
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setGin(Long.parseLong(GIN_TEST_UPGRADE));
		individualBlock.setCin(Long.parseLong(CIN_TEST_UPGRADE));
		fbnEvent.setIndividualBlock(individualBlock);
		
		AttributesBlock attributesBlock = new AttributesBlock();
		FBPropertyExtension fbPropertyExtension = new FBPropertyExtension();
		fbPropertyExtension.setKey("ULTIMATE");
		fbPropertyExtension.setValue("UPGRADE");
		attributesBlock.getAttributeExtensions().add(fbPropertyExtension);
		fbnEvent.setAttributesBlock(attributesBlock);
		
		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		fbnEvent.setFbIdentificationBlock(fbIdentificationBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("S");
		contractBlock.setStatusReason("S1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		fbnEvent.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(fbnEvent);
		
		ultimateComprefs = communicationPreferencesRepository.findComPrefIdByDomain(GIN_TEST_UPGRADE, "U");
		Assert.assertEquals(1, ultimateComprefs.size());
	}
	
	@Test
	@Rollback(true)
	public void testUpgradeHippo() throws JrafDaoException {
		
		List<CommunicationPreferences> ultimateComprefs = communicationPreferencesRepository.findComPrefIdByDomain(GIN_TEST_UPGRADE, "U");
		Assert.assertEquals(0, ultimateComprefs.size());
				
		FBNContractUpdateEvent fbnEvent = new FBNContractUpdateEvent();
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setGin(Long.parseLong(GIN_TEST_UPGRADE));
		individualBlock.setCin(Long.parseLong(CIN_TEST_UPGRADE));
		fbnEvent.setIndividualBlock(individualBlock);
		
		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		fbnEvent.setFbIdentificationBlock(fbIdentificationBlock);
		
		FBPropertiesBlock fbPropertiesBlock = new FBPropertiesBlock();
		FBProperty fbProperty = new FBProperty();
		fbProperty.setPropertyCode("HIPPOCAMPE");
		fbProperty.setPropertyType("CLUB");
		fbPropertiesBlock.getProperty().add(fbProperty);
		fbnEvent.setFbPropertiesBlock(fbPropertiesBlock);
		
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("S");
		contractBlock.setStatusReason("S1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		fbnEvent.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(fbnEvent);
		
		ultimateComprefs = communicationPreferencesRepository.findComPrefIdByDomain(GIN_TEST_UPGRADE, "U");
		Assert.assertEquals(1, ultimateComprefs.size());
	}
	
	@Test
	@Rollback(true)
	public void testUpgradeNoAttribute() throws JrafDaoException {
		
		List<CommunicationPreferences> ultimateComprefs = communicationPreferencesRepository.findComPrefIdByDomain(GIN_TEST_UPGRADE, "U");
		Assert.assertEquals(0, ultimateComprefs.size());
				
		FBNContractUpdateEvent fbnEvent = new FBNContractUpdateEvent();
		
		IndividualBlock individualBlock = new IndividualBlock();
		individualBlock.setGin(Long.parseLong(GIN_TEST_UPGRADE));
		individualBlock.setCin(Long.parseLong(CIN_TEST_UPGRADE));
		fbnEvent.setIndividualBlock(individualBlock);
		
		FBIdentificationBlock fbIdentificationBlock = new FBIdentificationBlock();
		fbIdentificationBlock.setFbLevelEndValidityDate(new Date());
		fbnEvent.setFbIdentificationBlock(fbIdentificationBlock);
			
		ContractBlock contractBlock = new ContractBlock();
		contractBlock.setContractStatus("S");
		contractBlock.setStatusReason("S1");
		contractBlock.setEnrollmentDate(new Date());
		contractBlock.setContractLastUpdate(new Date());
		
		fbnEvent.setContractBlock(contractBlock);
		
		fb.sendUpdatedContract(fbnEvent);
		
		ultimateComprefs = communicationPreferencesRepository.findComPrefIdByDomain(GIN_TEST_UPGRADE, "U");
		Assert.assertEquals(0, ultimateComprefs.size());
	}
	
}
