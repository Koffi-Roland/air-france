package com.airfrance.repind.service.delegation.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.delegation.DelegationDataRepository;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.entity.delegation.DelegationData;
import com.airfrance.repind.entity.delegation.DelegationDataInfo;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
// @ContextConfiguration(locations = {"classpath:config/application-context-spring-test.xml"})
// @Transactional
public class DelegationDataDSTest {

	public static String GIN = "000000000124";

	@Autowired
	IndividuDS individuDS;

	@Autowired
	DelegationDataDS delegationDataDS;

	@Test(expected = Test.None.class /* no exception expected */)
	// @Rollback(true)
	public void testFindBy() throws JrafDomainException {

		IndividuDTO delegator = individuDS.getByGin("400406769091");
		IndividuDTO delegate = individuDS.getByGin("000000000124");

		DelegationDataDTO delegationDataDTO = new DelegationDataDTO();
		delegationDataDTO.setStatus("A");
		delegationDataDTO.setType("TM");
		delegationDataDTO.setCreationDate(new Date());
		delegationDataDTO.setModificationDate(new Date());
		delegationDataDTO.setCreationSignature("JoC");
		delegationDataDTO.setModificationSignature("JoM");
		delegationDataDTO.setCreationSite("QVI");
		delegationDataDTO.setModificationSite("QVI");
		delegationDataDTO.setDelegateDTO(delegate);
		delegationDataDTO.setDelegatorDTO(delegator);

		List<DelegationDataDTO> delegationListDTO = new ArrayList<DelegationDataDTO>();
		delegationListDTO.add(delegationDataDTO);

//    	delegationDataDS.updateDelegateData("400406769091", delegationListDTO);
	}
    	AccountDataDS accountDataDSMock = Mockito.mock(AccountDataDS.class);

	@Test
	public void testFindDelegate() throws Exception {

		/* Mock for DAO findDelegate */
		DelegationData delegateUM = new DelegationData();
		delegateUM.setDelegationId(1);
		delegateUM.setType("UM");
		delegateUM.setStatus("A");

		Individu individuUM = new Individu();
		individuUM.setSgin("400406769092");
		delegateUM.setDelegate(individuUM);

		delegateUM.setDelegationDataInfo(new HashSet<DelegationDataInfo>());

		DelegationDataInfo delegationDataInfoTEL1 = new DelegationDataInfo();
		delegationDataInfoTEL1.setType("TEL");
		delegationDataInfoTEL1.setKey("phoneNumber");
		delegationDataInfoTEL1.setValue("0911");
		delegateUM.getDelegationDataInfo().add(delegationDataInfoTEL1);

		DelegationDataInfo delegationDataInfoPAC1 = new DelegationDataInfo();
		delegationDataInfoPAC1.setType("PAC");
		delegationDataInfoPAC1.setKey("country");
		delegationDataInfoPAC1.setValue("FR");
		delegateUM.getDelegationDataInfo().add(delegationDataInfoPAC1);

		DelegationData delegateUA = new DelegationData();
		delegateUA.setDelegationId(2);
		delegateUA.setType("UA");
		delegateUA.setStatus("A");

		Individu individuUA = new Individu();
		individuUA.setSgin("400406769093");
		delegateUA.setDelegate(individuUA);

		delegateUA.setDelegationDataInfo(new HashSet<DelegationDataInfo>());

		DelegationDataInfo delegationDataInfoTEL2 = new DelegationDataInfo();
		delegationDataInfoTEL2.setType("TEL");
		delegationDataInfoTEL2.setKey("phoneNumber");
		delegationDataInfoTEL2.setValue("0911");
		delegateUA.getDelegationDataInfo().add(delegationDataInfoTEL2);

		DelegationDataInfo delegationDataInfoPAC2 = new DelegationDataInfo();
		delegationDataInfoPAC2.setType("PAC");
		delegationDataInfoPAC2.setKey("country");
		delegationDataInfoPAC2.setValue("FR");
		delegateUA.getDelegationDataInfo().add(delegationDataInfoPAC2);

		List<DelegationData> listDelegationData = new ArrayList<DelegationData>();
		listDelegationData.add(delegateUA);
		listDelegationData.add(delegateUM);

		String targetGin = "400406769091";
		DelegationDataRepository delegationDataRepositoryMock = Mockito.mock(DelegationDataRepository.class);
		Mockito.doReturn(listDelegationData).when(delegationDataRepositoryMock).findDelegate(targetGin);

		/* Mock for fillIndividualDataForProvide */
		TelecomsDTO telecomDTO = new TelecomsDTO();
		telecomDTO.setSindicatif("33");
		telecomDTO.setScode_medium("D");
		telecomDTO.setSstatut_medium("V");
		telecomDTO.setSterminal("T");
		telecomDTO.setSnumero("0123456789");

		List<TelecomsDTO> listTelecoms = new ArrayList<TelecomsDTO>();
		listTelecoms.add(telecomDTO);

		TelecomDS telecomDSMock = Mockito.mock(TelecomDS.class);
		Mockito.doReturn(listTelecoms).when(telecomDSMock).findLatest(Mockito.any(String.class));

		AccountDataDTO accountDataDTO = new AccountDataDTO();
		accountDataDTO.setEmailIdentifier("Test@gmail.com");

		AccountDataDS accountDataDSMock = Mockito.mock(AccountDataDS.class);
		Mockito.doReturn(accountDataDTO).when(accountDataDSMock).getByGin(Mockito.any(String.class));

		// Settings mocks...
		DelegationDataDS delegationDataDSMock = new DelegationDataDS();
		
		delegationDataDSMock.setDelegationDataRepository(delegationDataRepositoryMock);
		delegationDataDSMock.setTelecomDS(telecomDSMock);
		delegationDataDSMock.setAccountDataDS(accountDataDSMock);

		/* Testing */
		List<DelegationDataDTO> listDelegate = delegationDataDSMock.findDelegate(targetGin);

		Assert.assertEquals(2, listDelegate.size());
		Assert.assertEquals(2, listDelegate.get(0).getDelegationDataInfoDTO().size());
		Assert.assertEquals(2, listDelegate.get(1).getDelegationDataInfoDTO().size());
	}

	/**
	 * CONTEXT UM : KIDSOLO Test if the delegation is in the context kidSolo
	 * REPIND-895
	 */
	@Test
	public void testAddTracking() {

		DelegationDataDS delegationDataDS = new DelegationDataDS();
		DelegationDataDTO delegationDataDTO = new DelegationDataDTO();

		delegationDataDTO.setType("UM");
		Assert.assertTrue(delegationDataDS.isDelegationTypeKidSolo(delegationDataDTO));

		delegationDataDTO.setType("UA");
		Assert.assertTrue(delegationDataDS.isDelegationTypeKidSolo(delegationDataDTO));

		delegationDataDTO.setType("XX");
		Assert.assertFalse(delegationDataDS.isDelegationTypeKidSolo(delegationDataDTO));
	}

}
