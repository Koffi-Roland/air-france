package com.afklm.repind.msv.automatic.merge;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.entity.contact.UsageMedium;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.enums.MediumStatusEnum;
import com.afklm.repind.common.repository.contact.EmailRepository;
import com.afklm.repind.common.repository.contact.PostalAddressRepository;
import com.afklm.repind.common.repository.contact.TelecomsRepository;
import com.afklm.repind.common.repository.contact.UsageMediumRepository;
import com.afklm.repind.common.repository.individual.AccountDataRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.role.BusinessRoleRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.common.repository.role.RoleGPRepository;
import com.afklm.repind.msv.automatic.merge.config.TestConfig;
import com.afklm.repind.msv.automatic.merge.config.logging.RequestFilter;
import com.afklm.repind.msv.automatic.merge.model.FullRestErrorMapper;
import com.afklm.repind.msv.automatic.merge.model.RoleUsageMediumEnum;
import com.afklm.repind.msv.automatic.merge.model.StatusEnum;
import com.afklm.repind.msv.automatic.merge.model.UsageMediumEnum;
import com.afklm.repind.msv.automatic.merge.service.*;
import com.afklm.repind.msv.automatic.merge.helper.MergeHelper;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperMergeRequestBloc;
import com.afklm.soa.spring.consumer.WebServiceConsumer;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceV1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import com.afklm.repind.msv.automatic.merge.service.BeanMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(SpringExtension.class)
class AutomaticMergeControllerTest {

	MockMvc mockMvc;

	@Autowired
	AutomaticMergeService automaticMergeService;

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	private MergeHelper mergeHelper;

	@Autowired
	IndividualService individualService;

	@Autowired
	RoleContractRepository roleContractRepository;

	@Autowired
	BusinessRoleRepository businessRoleRepository;

	@Autowired
	public BeanMapper BeanMapper;

	@Autowired
	public TelecomsRepository telecomsRepository;

	@Autowired
	public EmailRepository emailRepository;

	@Autowired
	public AddressService addressService;

	@Autowired
	public AccountDataRepository accountDataRepository;

	@Autowired
	public RoleGPRepository roleGPRepository;

	@Autowired
	public IndividuRepository individuRepository;

	@Autowired
	private UsageMediumRepository usageMediumRepository;

	@Autowired
	private PostalAddressRepository postalAddressRepository;

	@Autowired
	public EmailService emailService;

	@Autowired
	private WebServiceConsumer<ProvideFBContractMergePreferenceV1> provideFBContractMergePreferenceV1;

	private static final String STATUT_MEDIUM = "V";
	private static final String CODE_MEDIUM = "L";
	private static final String TYPE_EMAILS = "EMAILS";
	private static final String TYPE_TELECOMS = "TELECOMS";
	private static final String TYPE_ADDRESSES = "ADDRESSES";
	private static final String GIN_SOURCE = "999999999123";
	private static final String GIN_TARGET = "999999999124";

	private static Individu individuSource = null;
	private static Individu individuTarget = null;

	private final ObjectMapper mapper = new ObjectMapper();

	private List<Individu> indiviualsCreated;
	private List<UsageMedium> usageCreated;
	private List<EmailEntity> emailsCreated;
	private List<Telecoms> telecomsCreated;
	private List<PostalAddress> postalAddressesCreated;

	@BeforeEach
	public void initData() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilters(new RequestFilter())
				.build();
		indiviualsCreated = new ArrayList<>();
		usageCreated = new ArrayList<>();
		emailsCreated = new ArrayList<>();
		postalAddressesCreated = new ArrayList<>();
		telecomsCreated = new ArrayList<>();

		individuSource = new Individu();
		individuSource.setGin(GIN_SOURCE);

		individuTarget = new Individu();
		individuTarget.setGin(GIN_TARGET);

		this.initTestData();
	}

	@AfterEach
	public void clean() {
		this.emailRepository.deleteAll(emailsCreated);
		this.telecomsRepository.deleteAll(telecomsCreated);
		this.usageMediumRepository.deleteAll(usageCreated);
		postalAddressesCreated.forEach(adrP -> this.postalAddressRepository.deleteById(adrP.getAin()));
		indiviualsCreated.forEach(indiv -> this.individuRepository.deleteById(indiv.getGin()));
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualProvideForMerge_MergeAdrP_SourceAAdrISIMDV_TargetBAdrISIMDV() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue("123 street ISI M not same");
		postalAddress.setIndividu(indivTarget);
		postalAddress.setVersion(1);
		postalAddress.setCodeMedium("D");
		postalAddress.setStatutMedium("V");
		postalAddress.setSiteCreation("RI");
		postalAddress.setSignatureCreation("TESTRI");
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(0);
		postalAddress.setCodePays("FR");
		postalAddress.setDateModification(new Date());
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication(UsageMediumEnum.ISI.getName());
		usageMedium.setRole1(RoleUsageMediumEnum.M.getName());
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivTarget.getPostalAddresses().add(postalAddress);
		individuRepository.saveAndFlush(indivTarget);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(blocAddresses);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());

		// Check Postal Address target
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividu(indivTarget);
		Assert.assertEquals(2, postalAddresses.size());
		List<PostalAddress> expectedAddress = new ArrayList<>();
		expectedAddress.add(indivSource.getPostalAddresses().stream().findFirst().get());
		PostalAddress postalAddressHistorize = postalAddress;
		postalAddressHistorize.setStatutMedium(MediumStatusEnum.TEMPORARY.toString());
		expectedAddress.add(postalAddressHistorize);
		containAddresses(expectedAddress, postalAddresses);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualProvideForMerge_MergeAdrP_SourceAAdrISIMDV_TargetBAdrISIMPV() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue("123 street ISI M not same");
		postalAddress.setIndividu(indivTarget);
		postalAddress.setVersion(1);
		postalAddress.setCodeMedium("P");
		postalAddress.setStatutMedium("V");
		postalAddress.setSiteCreation("RI");
		postalAddress.setSignatureCreation("TESTRI");
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(0);
		postalAddress.setCodePays("FR");
		postalAddress.setDateModification(new Date());
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication(UsageMediumEnum.ISI.getName());
		usageMedium.setRole1(RoleUsageMediumEnum.M.getName());
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivTarget.getPostalAddresses().add(postalAddress);
		individuRepository.saveAndFlush(indivTarget);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(blocAddresses);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());

		// Check Postal Address target
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividu(indivTarget);
		Assert.assertEquals(2, postalAddresses.size());
		List<PostalAddress> expectedAddress = new ArrayList<>();
		expectedAddress.add(mergeHelper.getNewPostalAddress(indivSource.getPostalAddresses().stream().findFirst().get()));
		expectedAddress.add(mergeHelper.getNewPostalAddress(postalAddress));
		containAddresses(expectedAddress, postalAddresses);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualProvideForMerge_MergeAdrP_SourceAAdrISIMDV_TargetBAdrISICDV() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue("123 street ISI C");
		postalAddress.setIndividu(indivTarget);
		postalAddress.setVersion(1);
		postalAddress.setCodeMedium("D");
		postalAddress.setStatutMedium("V");
		postalAddress.setSiteCreation("RI");
		postalAddress.setSignatureCreation("TESTRI");
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(0);
		postalAddress.setCodePays("FR");
		postalAddress.setDateModification(new Date());
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication(UsageMediumEnum.ISI.getName());
		usageMedium.setRole1(RoleUsageMediumEnum.C.getName());
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivTarget.getPostalAddresses().add(postalAddress);
		individuRepository.saveAndFlush(indivTarget);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(blocAddresses);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());

		// Check Postal Address target
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividu(indivTarget);
		Assert.assertEquals(2, postalAddresses.size());
		List<PostalAddress> expectedAddress = new ArrayList<>();
		expectedAddress.add(indivSource.getPostalAddresses().stream().findFirst().get());
		PostalAddress postalAddressAlreadyOnTarget = mergeHelper.getNewPostalAddress(postalAddress);
		expectedAddress.add(postalAddressAlreadyOnTarget);
		containAddresses(expectedAddress, postalAddresses);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualProvideForMerge_MergeAdrP_SourceAAdrFIDDV_TargetBAdrFIDDV() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		// Change usage
		UsageMedium usage = indivSource.getPostalAddresses().stream().findFirst().get().getUsageMedium().stream().findFirst().get();
		usage.setCodeApplication("FID");
		usage.setRole1("1");
		this.usageMediumRepository.saveAndFlush(usage);

		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue("123 street ISI C");
		postalAddress.setVersion(1);
		postalAddress.setCodeMedium("D");
		postalAddress.setStatutMedium("V");
		postalAddress.setSiteCreation("RI");
		postalAddress.setSignatureCreation("TESTRI");
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(0);
		postalAddress.setCodePays("FR");
		postalAddress.setDateModification(new Date());
		postalAddress.setIndividu(indivTarget);
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication("FID");
		usageMedium.setRole1("4");
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivTarget.getPostalAddresses().add(postalAddress);
		individuRepository.saveAndFlush(indivTarget);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(blocAddresses);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());

		// Check Postal Address target
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividu(indivTarget).stream().toList();
		Assert.assertEquals(2, postalAddresses.size());
		List<PostalAddress> expectedAddress = new ArrayList<>();
		expectedAddress.add(indivSource.getPostalAddresses().stream().findFirst().get());
		PostalAddress postalAddressHistorize = mergeHelper.getNewPostalAddress(postalAddress);
		postalAddressHistorize.setStatutMedium(MediumStatusEnum.TEMPORARY.toString());
		expectedAddress.add(postalAddressHistorize);
		containAddresses(expectedAddress, postalAddresses);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualProvideForMerge_MergeAdrP_SourceAAdrFIDDV_TargetAAdrFIDDV() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		PostalAddress adrPSource = indivSource.getPostalAddresses().stream().findFirst().get();
		// Change usage
		UsageMedium usage = adrPSource.getUsageMedium().stream().findFirst().get();
		usage.setCodeApplication("FID");
		usage.setRole1("1");
		this.usageMediumRepository.saveAndFlush(usage);

		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue(adrPSource.getNoEtRue());
		postalAddress.setVersion(adrPSource.getVersion());
		postalAddress.setCodeMedium(adrPSource.getCodeMedium());
		postalAddress.setStatutMedium(adrPSource.getStatutMedium());
		postalAddress.setSiteCreation(adrPSource.getSiteCreation());
		postalAddress.setSignatureCreation(adrPSource.getSignatureCreation());
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(adrPSource.getCodErr());
		postalAddress.setCodePays(adrPSource.getCodePays());
		postalAddress.setDateModification(new Date());
		postalAddress.setIndividu(indivTarget);
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication("FID");
		usageMedium.setRole1("2");
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivTarget.getPostalAddresses().add(postalAddress);
		individuRepository.saveAndFlush(indivTarget);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(blocAddresses);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());

		// Check Postal Address target
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividu(indivTarget).stream().toList();
		Assert.assertEquals(2, postalAddresses.size());
		List<PostalAddress> expectedAddress = new ArrayList<>();
		PostalAddress postalAddressCreated = mergeHelper.getNewPostalAddress(indivSource.getPostalAddresses().stream().findFirst().get());
		UsageMedium usageCreated =  mergeHelper.getNewUsageMedium(postalAddressCreated.getUsageMedium().stream().findFirst().get());
		usageCreated.setRole1("2");
		usageCreated.setCodeApplication("FID");
		postalAddressCreated.getUsageMedium().clear();
		postalAddressCreated.getUsageMedium().add(usageCreated);
		expectedAddress.add(postalAddressCreated);
		PostalAddress postalAddressHistorize = mergeHelper.getNewPostalAddress(postalAddress);
		postalAddressHistorize.setStatutMedium(MediumStatusEnum.TEMPORARY.toString());
		expectedAddress.add(postalAddressHistorize);
		containAddresses(expectedAddress, postalAddresses);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualProvideForMerge_MergeAdrP_SourceAAdrFIDDV_TargetAAdrBDCDV() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		PostalAddress adrPSource = indivSource.getPostalAddresses().stream().findFirst().get();
		// Change usage
		UsageMedium usage = adrPSource.getUsageMedium().stream().findFirst().get();
		usage.setCodeApplication("FID");
		usage.setRole1("1");
		this.usageMediumRepository.saveAndFlush(usage);

		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();
		PostalAddress postalAddress = createPostalAddress(adrPSource, GIN_TARGET);
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication("BDC");
		usageMedium.setRole1("1");
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivTarget.getPostalAddresses().add(postalAddress);
		individuRepository.saveAndFlush(indivTarget);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(blocAddresses);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk()).andReturn();

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());

		// Check Postal Address target
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividu(indivTarget).stream().toList();
		Assert.assertEquals(2, postalAddresses.size());
		List<PostalAddress> expectedAddress = new ArrayList<>();
		PostalAddress postalAddressCreated = mergeHelper.getNewPostalAddress(adrPSource);
		postalAddressCreated.getUsageMedium().add(usageMedium);
		expectedAddress.add(postalAddressCreated);
		PostalAddress postalAddressHistorize = mergeHelper.getNewPostalAddress(postalAddress);
		postalAddressHistorize.setStatutMedium(MediumStatusEnum.TEMPORARY.toString());
		expectedAddress.add(postalAddressHistorize);
		containAddresses(expectedAddress, postalAddresses);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualProvideForMerge_MergeAdrP_SourceAAdrISICDV_TargetAAdrISIMDV() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		// Change usage
		PostalAddress adrPSource = indivSource.getPostalAddresses().stream().findFirst().get();
		UsageMedium usage = adrPSource.getUsageMedium().stream().findFirst().get();
		usage.setCodeApplication(UsageMediumEnum.ISI.getName());
		usage.setRole1(RoleUsageMediumEnum.C.getName());
		this.usageMediumRepository.saveAndFlush(usage);

		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue(adrPSource.getNoEtRue());
		postalAddress.setVersion(adrPSource.getVersion());
		postalAddress.setCodeMedium(adrPSource.getCodeMedium());
		postalAddress.setStatutMedium(adrPSource.getStatutMedium());
		postalAddress.setSiteCreation(adrPSource.getSiteCreation());
		postalAddress.setSignatureCreation(adrPSource.getSignatureCreation());
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(adrPSource.getCodErr());
		postalAddress.setCodePays(adrPSource.getCodePays());
		postalAddress.setDateModification(new Date());
		postalAddress.setIndividu(indivTarget);
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication(UsageMediumEnum.ISI.getName());
		usageMedium.setRole1(RoleUsageMediumEnum.M.getName());
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivTarget.getPostalAddresses().add(postalAddress);
		individuRepository.saveAndFlush(indivTarget);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(blocAddresses);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());

		// Check Postal Address target
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividu(indivTarget).stream().toList();
		Assert.assertEquals(2, postalAddresses.size());
		List<PostalAddress> expectedAddress = new ArrayList<>();
		PostalAddress postalAddressOnSource = mergeHelper.getNewPostalAddress(indivSource.getPostalAddresses().stream().findFirst().get());
		postalAddressOnSource.getUsageMedium().stream().findFirst().get().setRole1(RoleUsageMediumEnum.M.getName());
		expectedAddress.add(postalAddressOnSource);
		PostalAddress postalAddressOnTarget = mergeHelper.getNewPostalAddress(postalAddress);
		postalAddressOnTarget.setStatutMedium(MediumStatusEnum.TEMPORARY.toString());
		expectedAddress.add(postalAddressOnTarget);
		containAddresses(expectedAddress, postalAddresses);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualProvideForMerge_MergeAdrP_SourceAAdrISIMDV_TargetAAdrISICDV() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		PostalAddress adrPSource = indivSource.getPostalAddresses().stream().findFirst().get();

		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();
		PostalAddress postalAddress = createPostalAddress(adrPSource, GIN_TARGET);
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication(UsageMediumEnum.ISI.getName());
		usageMedium.setRole1(RoleUsageMediumEnum.C.getName());
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivTarget.getPostalAddresses().add(postalAddress);
		individuRepository.saveAndFlush(indivTarget);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(blocAddresses);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());

		// Check Postal Address target
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividu(indivTarget).stream().toList();
		Assert.assertEquals(2, postalAddresses.size());
		List<PostalAddress> expectedAddress = new ArrayList<>();
		PostalAddress postalAddressOnSource = mergeHelper.getNewPostalAddress(adrPSource);
		expectedAddress.add(postalAddressOnSource);
		PostalAddress postalAddressOnTarget = mergeHelper.getNewPostalAddress(postalAddress);
		postalAddressOnTarget.setStatutMedium(MediumStatusEnum.TEMPORARY.toString());
		expectedAddress.add(postalAddressOnTarget);
		containAddresses(expectedAddress, postalAddresses);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualProvideForMerge_MergeAdrP_SourceAAdrFIDDV_TargetAAdrISIMAndBAdrFIDDV() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		// Change usage
		PostalAddress postalAddressSource = indivSource.getPostalAddresses().stream().findFirst().get();
		UsageMedium usage = postalAddressSource.getUsageMedium().stream().findFirst().get();
		usage.setCodeApplication("FID");
		usage.setRole1("1");
		this.usageMediumRepository.saveAndFlush(usage);

		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue("123 street FID 2");
		postalAddress.setVersion(1);
		postalAddress.setCodeMedium("D");
		postalAddress.setStatutMedium("V");
		postalAddress.setSiteCreation("RI");
		postalAddress.setSignatureCreation("TESTRI");
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(0);
		postalAddress.setCodePays("FR");
		postalAddress.setDateModification(new Date());
		postalAddress.setIndividu(indivTarget);
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication("FID");
		usageMedium.setRole1("2");
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		PostalAddress postalAddressTargetISIM = createPostalAddress(postalAddressSource, GIN_TARGET);
		postalAddressTargetISIM = postalAddressRepository.save(postalAddressTargetISIM);
		usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddressTargetISIM.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication(UsageMediumEnum.ISI.getName());
		usageMedium.setRole1(RoleUsageMediumEnum.M.getName());
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddressTargetISIM.getUsageMedium().add(usageMedium);

		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddressTargetISIM));

		indivTarget.getPostalAddresses().add(postalAddress);
		indivTarget.getPostalAddresses().add(postalAddressTargetISIM);
		individuRepository.saveAndFlush(indivTarget);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(blocAddresses);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assertions.assertEquals("T", indivSource.getStatutIndividu());
		Assertions.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assertions.assertNotNull(indivSource.getDateFusion());
		Assertions.assertNotNull(indivSource.getDateModification());
		Assertions.assertEquals("QVI", indivSource.getSiteModification());
		Assertions.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assertions.assertEquals(1, indivSource.getEmails().size());
		Assertions.assertEquals(1, indivSource.getPostalAddresses().size());
		Assertions.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assertions.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assertions.assertNull(indivTarget.getGinFusion());

		// Check Postal Address target
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividu(indivTarget).stream().toList();
		Assertions.assertEquals(3, postalAddresses.size());
		List<PostalAddress> expectedAddress = new ArrayList<>();
		PostalAddress postalAddressCreated = mergeHelper.getNewPostalAddress(postalAddressSource);
		postalAddressCreated.getUsageMedium().add(mergeHelper.getNewUsageMedium(usageMedium));
		expectedAddress.add(postalAddressCreated);
		PostalAddress postalAddressHistorize = mergeHelper.getNewPostalAddress(postalAddress);
		postalAddressHistorize.setStatutMedium(MediumStatusEnum.TEMPORARY.toString());
		expectedAddress.add(postalAddressHistorize);
		PostalAddress postalAddressHistorizeISIM = mergeHelper.getNewPostalAddress(postalAddressTargetISIM);
		postalAddressHistorize.setStatutMedium(MediumStatusEnum.TEMPORARY.toString());
		expectedAddress.add(postalAddressHistorizeISIM);

		containAddresses(expectedAddress, postalAddresses);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualProvideForMerge_MergeAdrP_SourceAAdrFIDDV_TargetBAdrFIDDVAndBDCDVAndISIMDV() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		// Change usage
		PostalAddress postalAddressSource = indivSource.getPostalAddresses().stream().findFirst().get();
		UsageMedium usage = postalAddressSource.getUsageMedium().stream().findFirst().get();
		usage.setCodeApplication("FID");
		usage.setRole1("1");
		this.usageMediumRepository.saveAndFlush(usage);

		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue("123 street BDC AND FID");
		postalAddress.setVersion(1);
		postalAddress.setCodeMedium("D");
		postalAddress.setStatutMedium("V");
		postalAddress.setSiteCreation("RI");
		postalAddress.setSignatureCreation("TESTRI");
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(0);
		postalAddress.setCodePays("FR");
		postalAddress.setDateModification(new Date());
		postalAddress.setIndividu(indivTarget);
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication("FID");
		usageMedium.setRole1("0");
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);

		usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication("BDC");
		usageMedium.setRole1("1");
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);

		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivTarget.getPostalAddresses().add(postalAddress);
		individuRepository.saveAndFlush(indivTarget);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(blocAddresses);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());

		// Check Postal Address target
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividu(indivTarget).stream().toList();
		Assert.assertEquals(2, postalAddresses.size());
		List<PostalAddress> expectedAddress = new ArrayList<>();
		PostalAddress postalAddressCreated = mergeHelper.getNewPostalAddress(postalAddressSource);
		expectedAddress.add(postalAddressCreated);
		PostalAddress postalAddressBDC = mergeHelper.getNewPostalAddress(postalAddress);
		UsageMedium usageBDC =  mergeHelper.getNewUsageMedium(postalAddressBDC.getUsageMedium().stream().findFirst().get());
		usageBDC.setRole1("1");
		usageBDC.setCodeApplication("BDC");
		postalAddressBDC.getUsageMedium().clear();
		postalAddressBDC.getUsageMedium().add(usageBDC);
		expectedAddress.add(postalAddressBDC);

		containAddresses(expectedAddress, postalAddresses);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualProvideForMerge_NotSamePerson() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivSource.setPrenom("John");
		indivSource.setNom("Smith");
		indivSource = individuRepository.saveAndFlush(indivSource);


		MvcResult result = mockMvc.perform(get(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		WrapperMerge resume = new ObjectMapper().readValue(response, WrapperMerge.class);
		Assert.assertFalse(resume.isSamePerson);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualMerge() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();

		// Email
		List<String> idEmails = new ArrayList<>();
		idEmails.add(indivSource.getEmails().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc bloc = new WrapperMergeRequestBloc();
		bloc.setType(TYPE_EMAILS);
		bloc.setIdentifiants(idEmails);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		// Telecom
		List<String> idTelecoms = new ArrayList<>();
		idTelecoms.add(indivSource.getTelecoms().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocTelecoms = new WrapperMergeRequestBloc();
		blocTelecoms.setType(TYPE_TELECOMS);
		blocTelecoms.setIdentifiants(idTelecoms);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(bloc);
		blocs.add(blocAddresses);
		blocs.add(blocTelecoms);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());
		List<EmailEntity> emailTarget = this.emailRepository.findByIndividu(indivTarget).stream().toList();
		Assert.assertEquals(1, emailTarget.size());
		Assert.assertEquals(indivSource.getEmails().stream().findFirst().get().getEmail(), emailTarget.get(0).getEmail());
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividu(indivTarget).stream().toList();
		Assert.assertEquals(1, postalAddresses.size());
		Assert.assertEquals(indivSource.getPostalAddresses().stream().findFirst().get().getNoEtRue(), postalAddresses.get(0).getNoEtRue());
		List<Telecoms> telecoms = this.telecomsRepository.findByIndividuGin(GIN_TARGET).stream().toList();
		Assert.assertEquals(1, telecoms.size());
		Assert.assertEquals(indivSource.getTelecoms().stream().findFirst().get().getNumero(), telecoms.get(0).getNumero());
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualMerge_WithOneAddressTwoUsage() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue("123 street BDC");
		postalAddress.setVersion(1);
		postalAddress.setCodeMedium("D");
		postalAddress.setStatutMedium("V");
		postalAddress.setSiteCreation("BDC");
		postalAddress.setSignatureCreation("TESTRI");
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(0);
		postalAddress.setCodePays("FR");
		postalAddress.setDateModification(new Date());
		postalAddress.setIndividu(indivSource);
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageBDC = new UsageMedium();
		usageBDC.setAinAdr(postalAddress.getAin());
		usageBDC.setNum(1);
		usageBDC.setCodeApplication("BDC");
		usageBDC.setRole1("1");
		usageBDC = usageMediumRepository.saveAndFlush(usageBDC);
		this.usageCreated.add(usageBDC);
		UsageMedium usageISI = new UsageMedium();
		usageISI.setAinAdr(postalAddress.getAin());
		usageISI.setNum(1);
		usageISI.setCodeApplication(UsageMediumEnum.ISI.getName());
		usageISI.setRole1(RoleUsageMediumEnum.M.getName());
		usageISI = usageMediumRepository.saveAndFlush(usageISI);
		this.usageCreated.add(usageISI);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageBDC);
		postalAddress.getUsageMedium().add(usageISI);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivSource.getPostalAddresses().clear();
		indivSource.getPostalAddresses().add(postalAddress);
		individuRepository.saveAndFlush(indivSource);

		// Email
		List<String> idEmails = new ArrayList<>();
		idEmails.add(indivSource.getEmails().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc bloc = new WrapperMergeRequestBloc();
		bloc.setType(TYPE_EMAILS);
		bloc.setIdentifiants(idEmails);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		indivSource.getPostalAddresses().stream().forEach(el -> idPostalAddresses.add(el.getAin()));
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		// Telecom
		List<String> idTelecoms = new ArrayList<>();
		idTelecoms.add(indivSource.getTelecoms().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocTelecoms = new WrapperMergeRequestBloc();
		blocTelecoms.setType(TYPE_TELECOMS);
		blocTelecoms.setIdentifiants(idTelecoms);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(bloc);
		blocs.add(blocAddresses);
		blocs.add(blocTelecoms);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());
		List<EmailEntity> emailTarget = this.emailRepository.findByIndividuGin(GIN_TARGET).stream().toList();
		Assert.assertEquals(1, emailTarget.size());
		Assert.assertEquals(indivSource.getEmails().stream().findFirst().get().getEmail(), emailTarget.get(0).getEmail());
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividuGin(GIN_TARGET).stream().toList();
		Assert.assertEquals(1, postalAddresses.size());
		Assert.assertEquals(1, postalAddresses.get(0).getUsageMedium().size());
		Assert.assertEquals(UsageMediumEnum.ISI.getName(), postalAddresses.get(0).getUsageMedium().stream().findFirst().get().getCodeApplication());

		List<Telecoms> telecoms = this.telecomsRepository.findByIndividuGin(GIN_TARGET).stream().toList();
		Assert.assertEquals(1, telecoms.size());
		Assert.assertEquals(indivSource.getTelecoms().stream().findFirst().get().getNumero(), telecoms.get(0).getNumero());
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualMerge_WithOneBDCAddress() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		PostalAddress expectedAddress = indivSource.getPostalAddresses().stream().findFirst().get();
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue("123 street BDC");
		postalAddress.setVersion(1);
		postalAddress.setCodeMedium("D");
		postalAddress.setStatutMedium("V");
		postalAddress.setSiteCreation("BDC");
		postalAddress.setSignatureCreation("TESTRI");
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(0);
		postalAddress.setCodePays("FR");
		postalAddress.setDateModification(new Date());
		postalAddress.setIndividu(indivSource);
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication("BDC");
		usageMedium.setRole1(RoleUsageMediumEnum.M.getName());
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivSource.getPostalAddresses().add(postalAddress);
		individuRepository.saveAndFlush(indivSource);

		// Email
		List<String> idEmails = new ArrayList<>();
		idEmails.add(indivSource.getEmails().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc bloc = new WrapperMergeRequestBloc();
		bloc.setType(TYPE_EMAILS);
		bloc.setIdentifiants(idEmails);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		indivSource.getPostalAddresses().stream().forEach(el -> idPostalAddresses.add(el.getAin()));
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		// Telecom
		List<String> idTelecoms = new ArrayList<>();
		idTelecoms.add(indivSource.getTelecoms().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocTelecoms = new WrapperMergeRequestBloc();
		blocTelecoms.setType(TYPE_TELECOMS);
		blocTelecoms.setIdentifiants(idTelecoms);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(bloc);
		blocs.add(blocAddresses);
		blocs.add(blocTelecoms);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(2, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());
		List<EmailEntity> emailTarget = this.emailRepository.findByIndividuGin(GIN_TARGET).stream().toList();
		Assert.assertEquals(1, emailTarget.size());
		Assert.assertEquals(indivSource.getEmails().stream().findFirst().get().getEmail(), emailTarget.get(0).getEmail());
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividuGin(GIN_TARGET).stream().toList();
		Assert.assertEquals(2, postalAddresses.size());
		Assert.assertEquals(expectedAddress.getNoEtRue(), postalAddresses.get(0).getNoEtRue());
		List<Telecoms> telecoms = this.telecomsRepository.findByIndividuGin(GIN_TARGET).stream().toList();
		Assert.assertEquals(1, telecoms.size());
		Assert.assertEquals(indivSource.getTelecoms().stream().findFirst().get().getNumero(), telecoms.get(0).getNumero());
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void errorIndividualMerge_InvalidStatus() throws Exception {
		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();
		// Change the status of the source individual
		indivSource.setStatutIndividu("T");
		indivSource = individuRepository.saveAndFlush(indivSource);

		// Email
		List<String> idEmails = new ArrayList<>();
		idEmails.add(indivSource.getEmails().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc bloc = new WrapperMergeRequestBloc();
		bloc.setType(TYPE_EMAILS);
		bloc.setIdentifiants(idEmails);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		// Telecom
		List<String> idTelecoms = new ArrayList<>();
		idTelecoms.add(indivSource.getTelecoms().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocTelecoms = new WrapperMergeRequestBloc();
		blocTelecoms.setType(TYPE_TELECOMS);
		blocTelecoms.setIdentifiants(idTelecoms);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(bloc);
		blocs.add(blocAddresses);
		blocs.add(blocTelecoms);

		String params = mapper.writeValueAsString(blocs);

		MvcResult result = mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isNotAcceptable())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		FullRestErrorMapper error = new ObjectMapper().readValue(response, FullRestErrorMapper.class);
		Assert.assertEquals("business.412.007", error.getError().getCode());
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void testIndividualMerge_ReplaceElementsInTarget() throws Exception {
		Individu indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Add telecoms to target
		Telecoms targetTelecom = createTestTelecoms(indivTarget, "0666666666");
		Telecoms invalidTargetTelecom = createTestTelecoms(indivTarget, "0666666667");
		invalidTargetTelecom.setStatutMedium("I");
		this.telecomsCreated.add(this.telecomsRepository.save(targetTelecom));
		this.telecomsCreated.add(this.telecomsRepository.save(invalidTargetTelecom));
		indivTarget.getTelecoms().add(targetTelecom);
		indivTarget.getTelecoms().add(invalidTargetTelecom);

		// Add emails to target
		EmailEntity targetEmail = createTestEmail(GIN_TARGET, "testvalid@airfrance.com");
		EmailEntity invalidTargetEmail = createTestEmail(GIN_TARGET, "testinvalid@airfrance.com");
		invalidTargetEmail.setStatutMedium(MediumStatusEnum.INVALID.toString());
		this.emailsCreated.add(this.emailRepository.save(targetEmail));
		this.emailsCreated.add(this.emailRepository.save(invalidTargetEmail));
		indivTarget.getEmails().add(targetEmail);
		indivTarget.getEmails().add(invalidTargetEmail);

		this.individuRepository.saveAndFlush(indivTarget);


		Individu indivSource = individuRepository.findById(GIN_SOURCE).get();

		// Email
		List<String> idEmails = new ArrayList<>();
		idEmails.add(indivSource.getEmails().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc bloc = new WrapperMergeRequestBloc();
		bloc.setType(TYPE_EMAILS);
		bloc.setIdentifiants(idEmails);

		// Postal address
		List<String> idPostalAddresses = new ArrayList<>();
		idPostalAddresses.add(indivSource.getPostalAddresses().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocAddresses = new WrapperMergeRequestBloc();
		blocAddresses.setType(TYPE_ADDRESSES);
		blocAddresses.setIdentifiants(idPostalAddresses);

		// Telecom
		List<String> idTelecoms = new ArrayList<>();
		idTelecoms.add(indivSource.getTelecoms().stream().findFirst().get().getAin());
		WrapperMergeRequestBloc blocTelecoms = new WrapperMergeRequestBloc();
		blocTelecoms.setType(TYPE_TELECOMS);
		blocTelecoms.setIdentifiants(idTelecoms);

		List<WrapperMergeRequestBloc> blocs = new ArrayList<>();
		blocs.add(bloc);
		blocs.add(blocAddresses);
		blocs.add(blocTelecoms);

		String params = mapper.writeValueAsString(blocs);

		mockMvc.perform(post(String.format("/merge/%s/%s", GIN_SOURCE, GIN_TARGET))
						.contentType(MediaType.APPLICATION_JSON)
						.content(params))
				.andExpect(status().isOk());

		indivSource = individuRepository.findById(GIN_SOURCE).get();
		indivTarget = individuRepository.findById(GIN_TARGET).get();

		// Check source data
		Assert.assertEquals("T", indivSource.getStatutIndividu());
		Assert.assertEquals(GIN_TARGET, indivSource.getGinFusion());
		Assert.assertNotNull(indivSource.getDateFusion());
		Assert.assertNotNull(indivSource.getDateModification());
		Assert.assertEquals("QVI", indivSource.getSiteModification());
		Assert.assertEquals("MS FUSION", indivSource.getSignatureModification());
		Assert.assertEquals(1, indivSource.getEmails().size());
		Assert.assertEquals(1, indivSource.getPostalAddresses().size());
		Assert.assertEquals(1, indivSource.getTelecoms().size());

		// Check target data
		Assert.assertEquals(StatusEnum.VALIDATED.getName(), indivTarget.getStatutIndividu());
		Assert.assertNull(indivTarget.getGinFusion());

		// Check Email target
		List<EmailEntity> emailTarget = this.emailRepository.findByIndividuGinAndStatutMediumIn(GIN_TARGET, Arrays.asList(MediumStatusEnum.VALID.toString(), MediumStatusEnum.INVALID.toString()));
		Assert.assertEquals(2, emailTarget.size());
		emailTarget = this.emailRepository.findByIndividuGinAndStatutMediumIn(GIN_TARGET, Arrays.asList(MediumStatusEnum.HISTORIZED.toString()));
		Assert.assertEquals(1, emailTarget.size());
		Assert.assertEquals(targetEmail.getEmail(), emailTarget.get(0).getEmail());
		// Check Telecom target
		List<Telecoms> telecoms = this.telecomsRepository.findByIndividuGinAndStatutMediumIn(GIN_TARGET, Arrays.asList(MediumStatusEnum.VALID.toString(), MediumStatusEnum.INVALID.toString()));
		Assert.assertEquals(2, telecoms.size());
		telecoms = this.telecomsRepository.findByIndividuGinAndStatutMediumIn(GIN_TARGET, Arrays.asList(MediumStatusEnum.HISTORIZED.toString()));
		Assert.assertEquals(1, telecoms.size());
		Assert.assertEquals(targetTelecom.getNumero(), telecoms.get(0).getNumero());

		// Check Postal Address target
		List<PostalAddress> postalAddresses = this.postalAddressRepository.findByIndividuGin(GIN_TARGET).stream().toList();
		Assert.assertEquals(1, postalAddresses.size());
		Assert.assertEquals(indivSource.getPostalAddresses().stream().findFirst().get().getNoEtRue(), postalAddresses.get(0).getNoEtRue());
	}


	private void initTestData() throws Exception {
		// Create first individual
		Individu indivSource = this.createIndividuForTest(GIN_SOURCE);
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue("123 street");
		postalAddress.setIndividu(indivSource);
		postalAddress.setVersion(1);
		postalAddress.setCodeMedium("D");
		postalAddress.setStatutMedium("V");
		postalAddress.setSiteCreation("QVI");
		postalAddress.setSignatureCreation("TESTRI");
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(0);
		postalAddress.setCodePays("FR");
		postalAddress.setDateModification(new Date());
		postalAddress = postalAddressRepository.save(postalAddress);

		UsageMedium usageMedium = new UsageMedium();
		usageMedium.setAinAdr(postalAddress.getAin());
		usageMedium.setNum(1);
		usageMedium.setCodeApplication(UsageMediumEnum.ISI.getName());
		usageMedium.setRole1(RoleUsageMediumEnum.M.getName());
		usageMedium = usageMediumRepository.saveAndFlush(usageMedium);
		this.usageCreated.add(usageMedium);
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.getUsageMedium().add(usageMedium);
		this.postalAddressesCreated.add(postalAddressRepository.saveAndFlush(postalAddress));

		indivSource.setPostalAddresses(new HashSet<>());
		indivSource.getPostalAddresses().add(postalAddress);

		EmailEntity email = createTestEmail(indivSource.getGin(),"firstindiv@gmail.com");
		email = this.emailRepository.saveAndFlush(email);
		Set<EmailEntity> emails = new HashSet<>();
		emails.add(email);
		this.emailsCreated.add(email);
		indivSource.setEmails(emails);

		Telecoms telecom = createTestTelecoms(indivSource, "0612345687");
		this.telecomsCreated.add(this.telecomsRepository.save(telecom));
		indivSource.getTelecoms().add(telecom);
		this.indiviualsCreated.add(individuRepository.saveAndFlush(indivSource));

		// Create second individual
		Individu indivTarget = this.createIndividuForTest(GIN_TARGET);
		indivTarget.setPostalAddresses(new HashSet<>());
		this.indiviualsCreated.add(individuRepository.saveAndFlush(indivTarget));
	}

	private Individu createIndividuForTest(String sgin) throws Exception {
		Individu individu = new Individu();
		Date birthDate = new Date();
		individu.setSexe(RoleUsageMediumEnum.M.getName());
		individu.setDateNaissance(birthDate);
		individu.setDateCreation(new Date());
		individu.setSignatureCreation("REPIND");
		individu.setSiteCreation("QVI");
		individu.setStatutIndividu("V");
		individu.setCivilite("MR");
		individu.setNomTypo1("TEST COM");
		individu.setPrenomTypo1("TEST PREF");
		individu.setGin(sgin);
		individu.setNonFusionnable("N");
		individu.setType("I");
		individu.setCommunicationPreferences(new HashSet<>());
		individu.setDelegateList(new HashSet<>());
		individu.setDelegatorList(new HashSet<>());
		individu.setExternalIdentifiers(new HashSet<>());
		individu.setPreferences(new HashSet<>());
		individu.setAlerts(new HashSet<>());
		individu.setCommunicationPreferences(new HashSet<>());
		individu.setRoleContracts(new HashSet<>());
		individu.setTelecoms(new HashSet<>());
		individu.setEmails(new HashSet<>());
		individu.setPostalAddress(new HashSet<>());
		return individuRepository.saveAndFlush(individu);
	}

	private static Telecoms createTestTelecoms(Individu individu, String numero) {
		Telecoms telecoms = new Telecoms();
		telecoms.setCodeMedium("P");
		telecoms.setStatutMedium(StatusEnum.VALIDATED.getName());
		telecoms.setSiteCreation("REPIND");
		telecoms.setSiteModification("REPIND");
		telecoms.setSignatureModification("TESTRI");
		telecoms.setSignatureCreation("TESTRI");
		telecoms.setDateCreation(new Date());
		telecoms.setDateModification(new Date());
		telecoms.setNumero(numero);
		telecoms.setTerminal("T");
		telecoms.setIndividu(individu);

		return telecoms;
	}

	private EmailEntity createTestEmail(String sgin, String value){
		EmailEntity email = new EmailEntity();
		Individu individu = individuRepository.getReferenceById(sgin);

		email.setStatutMedium(STATUT_MEDIUM);
		email.setCodeMedium(CODE_MEDIUM);
		email.setEmail(value);
		email.setIndividu(individu);
		email.setAutorisationMailing("T");
		email.setSignatureCreation("TEST");
		email.setSiteCreation("REPIND");
		email.setDateCreation(new Date());

		return email;
	}

	public boolean areEquals(UsageMedium first, UsageMedium second) {
		return first.getCodeApplication().equals(second.getCodeApplication()) &&
				first.getRole1().equals(second.getRole1());
	}

	public void containAddresses(List<PostalAddress> expectedAddresses, List<PostalAddress> addresses){
		for(PostalAddress postalAddress: expectedAddresses){
			Optional<PostalAddress> optAdr = addresses.stream().filter(el -> addressService.areEquals(postalAddress, el) && usagesAreEquals(postalAddress.getUsageMedium(), el.getUsageMedium())).findFirst();
			if(!optAdr.isPresent()){
				Assert.fail("Addresses not contains one expected address");
			}
		}
	}

	public boolean usagesAreEquals(Set<UsageMedium> expectedUsages, Set<UsageMedium> usages){
		for(UsageMedium usage: expectedUsages){
			Optional<UsageMedium> optAdr = usages.stream().filter(el -> areEquals(usage, el)).findFirst();
			if(!optAdr.isPresent()){
				return false;
			}
		}
		return true;
	}

	private PostalAddress createPostalAddress(PostalAddress adrPSource, String sgin){
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setNoEtRue(adrPSource.getNoEtRue());
		postalAddress.setVersion(adrPSource.getVersion());
		postalAddress.setCodeMedium(adrPSource.getCodeMedium());
		postalAddress.setStatutMedium(adrPSource.getStatutMedium());
		postalAddress.setSiteCreation(adrPSource.getSiteCreation());
		postalAddress.setSignatureCreation(adrPSource.getSignatureCreation());
		postalAddress.setDateCreation(new Date());
		postalAddress.setCodErr(adrPSource.getCodErr());
		postalAddress.setCodePays(adrPSource.getCodePays());
		postalAddress.setDateModification(new Date());
		postalAddress.setUsageMedium(new HashSet<>());
		postalAddress.setIndividu(individuRepository.getReferenceById(sgin));
		return postalAddressRepository.save(postalAddress);
	}
}
