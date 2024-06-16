package com.afklm.repind.helpers.ut;

import com.afklm.repind.helpers.ProvideIndividualDataHelper;
import com.airfrance.ref.type.DelegationTypeEnum;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProvideIndividualDataHelperTest {
	
	private List<DelegationDataDTO> delegationList;
	
	@Before
	public void prepareInputData() {
		delegationList = new ArrayList<DelegationDataDTO>();
		
		DelegationDataDTO DelegationDataUM = new DelegationDataDTO();
		DelegationDataUM.setType(DelegationTypeEnum.UNACOMPAGNED_MINOR.toString());
		delegationList.add(DelegationDataUM);
		
		DelegationDataDTO DelegationDataUA = new DelegationDataDTO();
		DelegationDataUA.setType(DelegationTypeEnum.UNACOMPAGNED_MINOR_ATTENDANT.toString());
		delegationList.add(DelegationDataUA);
		
		DelegationDataDTO DelegationDataUF = new DelegationDataDTO();
		DelegationDataUF.setType(DelegationTypeEnum.ULTIMATE_FAMILLY.toString());
		delegationList.add(DelegationDataUF);
		
		DelegationDataDTO DelegationDataTM = new DelegationDataDTO();
		DelegationDataTM.setType(DelegationTypeEnum.TRAVEL_MANAGER.toString());
		delegationList.add(DelegationDataTM);
	}

	@Test
	public void testDeleteDelegationFromReponseByType() {
		Assert.assertEquals(4, delegationList.size());
		
		ProvideIndividualDataHelper.deleteDelegationFromReponseByType(delegationList, DelegationTypeEnum.UNACOMPAGNED_MINOR);
		ProvideIndividualDataHelper.deleteDelegationFromReponseByType(delegationList, DelegationTypeEnum.UNACOMPAGNED_MINOR_ATTENDANT);
		
		Assert.assertEquals(2, delegationList.size());
	}
	
	@Test
	public void testDeleteDelegationFromReponseByTypesDelegation() {
		Assert.assertEquals(4, delegationList.size());
		
		List<DelegationTypeEnum> delegeationTypesToRemove = new ArrayList<DelegationTypeEnum>(Arrays.asList(DelegationTypeEnum.UNACOMPAGNED_MINOR, DelegationTypeEnum.UNACOMPAGNED_MINOR_ATTENDANT));
		ProvideIndividualDataHelper.deleteDelegationFromReponseByTypesDelegation(delegationList, delegeationTypesToRemove);
		
		Assert.assertEquals(2, delegationList.size());
	}
}
