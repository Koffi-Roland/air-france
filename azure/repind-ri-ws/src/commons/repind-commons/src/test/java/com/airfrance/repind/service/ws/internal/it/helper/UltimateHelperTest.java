package com.airfrance.repind.service.ws.internal.it.helper;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.ws.internal.helpers.UltimateException;
import com.airfrance.repind.service.ws.internal.helpers.UltimateHelperV8;
import com.airfrance.repind.service.ws.internal.type.UltimateCustomerTypeCode;
import com.airfrance.repind.service.ws.internal.type.UltimateFamilyTypeCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UltimateHelperTest extends UltimateHelperV8 {

	private abstract class UltimateHelperTesting extends UltimateHelperV8 {
		public UltimateHelperTesting() {
			super();
		}
	}
	
	public static SignatureDTO GetSignature(){
		SignatureDTO signature = new SignatureDTO();
		signature.setApplicationCode("testUlti");
		signature.setDate(new Date());
		signature.setHeure("11");
		signature.setIpAddress("127.0.0.1");
		signature.setSignature("testUlti");
		signature.setSite("sophia");
		signature.setTypeSignature("test");
		return signature;
	}

	public class UltimateLinkTestingException extends UltimateHelperV8.Ultimate {

		protected UltimateLinkTestingException(final String delegatorGin, final String delegateGin,
				final String delegationType, final String delegationStatus, final SignatureDTO signatureAPP,
				final String managingCompany, final String gin) {
			super(delegatorGin, delegateGin, delegationType, delegationStatus, signatureAPP, managingCompany, gin);
		}

		@Override
		protected void _process(final IndividuDS individu, final DelegationDataDS delegationData, final RoleDS role,
				final CommunicationPreferencesDS communicationPreferences,
				final VariablesRepository variablesRepository) throws UltimateException {
			throw new UltimateException("toto");

		}
	}

	public class UltimateLinkTestingSuccess extends UltimateHelperV8.Ultimate {

		protected UltimateLinkTestingSuccess(final String delegatorGin, final String delegateGin,
				final String delegationType, final String delegationStatus, final SignatureDTO signatureAPP,
				final String managingCompany, final String gin) {
			super(delegatorGin, delegateGin, delegationType, delegationStatus, signatureAPP, managingCompany, gin);
		}

		@Override
		protected void _process(final IndividuDS individu, final DelegationDataDS delegationData, final RoleDS role,
				final CommunicationPreferencesDS communicationPreferences,
				final VariablesRepository variablesRepository) {
		}
	}
	
	@Category(com.airfrance.repind.util.TestCategory.Fast.class)
	@Test
	@Transactional
	@Rollback(true)
	public final void testLinkUltimateFamilyException() throws JrafDomainException, UltimateException {
		final List<DelegationDataDTO> delegations = new ArrayList<DelegationDataDTO>();
		final DelegationDataDTO delegation = new DelegationDataDTO();
		delegation.setType(UltimateFamilyTypeCode.ULTIMATE_FAMILY.getUltimateFamilyCode());
		delegation.setDelegateDTO(new IndividuDTO());
		delegation.setDelegatorDTO(new IndividuDTO());
		final DelegationDataDTO delegationNotUltimate = new DelegationDataDTO();
		delegations.add(delegationNotUltimate);
		delegations.add(delegation);

		final UltimateHelperV8 ultimateHelper = new UltimateHelperTesting() {
			@Override
			protected Ultimate CreateUltimateInstance(final String delegatorGin, final String delegateGin,
					final String delegationType, final String delegationStatus, final SignatureDTO signature,
					final String managingCompany, final String gin) {
				return new UltimateLinkTestingException(delegatorGin, delegateGin, delegationType, delegationStatus,
						signature, managingCompany, gin);
			}
		};
		try {
			ultimateHelper.createUltimateFamilyLinks(delegations, GetSignature(), null, "");
			Assert.fail();
		} catch (final UltimateException e) {
		}
	}

	@Test
	public final void testLinkUltimateFamilySuccess() throws JrafDomainException, UltimateException {
		final List<DelegationDataDTO> delegations = new ArrayList<DelegationDataDTO>();
		final DelegationDataDTO delegation = new DelegationDataDTO();
		delegation.setType(UltimateFamilyTypeCode.ULTIMATE_FAMILY.getUltimateFamilyCode());
		delegation.setDelegateDTO(new IndividuDTO());
		delegation.setDelegatorDTO(new IndividuDTO());
		final DelegationDataDTO delegationNotUltimate = new DelegationDataDTO();
		delegations.add(delegationNotUltimate);
		delegations.add(delegation);

		final UltimateHelperV8 ultimateHelper = new UltimateHelperTesting() {
			@Override
			protected Ultimate CreateUltimateInstance(final String delegatorGin, final String delegateGin,
					final String delegationType, final String delegationStatus, final SignatureDTO signature,
					final String managingCompany, final String gin) {
				return new UltimateLinkTestingSuccess(delegatorGin, delegateGin, delegationType, delegationStatus,
						signature, managingCompany, gin);
			}
		};

		final int processed = ultimateHelper.createUltimateFamilyLinks(delegations, GetSignature(), null, "");
		Assert.assertEquals(1, processed);
	}
	
	
	@Test
	public final void testLinkUltimateFamilyNull() throws JrafDomainException, UltimateException {
		final int processed = new UltimateHelperV8().createUltimateFamilyLinks(null, GetSignature(), null, "");
		Assert.assertEquals(0, processed);
	}


	@Test
	public final void testNonUltimateDelegations() {

		final List<DelegationDataDTO> testList = new ArrayList<DelegationDataDTO>();
		final List<String> successList = new ArrayList<String>();

		DelegationDataDTO element = new DelegationDataDTO();
		element.setType("tata");
		element.setSender("0");
		testList.add(element);
		successList.add("0");

		element = new DelegationDataDTO();
		element.setType(UltimateFamilyTypeCode.ULTIMATE_FAMILY.getUltimateFamilyCode());
		element.setSender("1");
		testList.add(element);

		element = new DelegationDataDTO();
		element.setType(UltimateCustomerTypeCode.ULTIMATE_PLATINIUM.getUltimateCustomerCode());
		element.setSender("2");
		testList.add(element);
		successList.add("2");

		element = new DelegationDataDTO();
		element.setType(UltimateCustomerTypeCode.ULTIMATE_PLATINIUM_FOR_LIFE.getUltimateCustomerCode());
		element.setSender("3");
		testList.add(element);
		successList.add("3");

		element = new DelegationDataDTO();
		element.setType(UltimateCustomerTypeCode.ULTIMATE_SKIPPER.getUltimateCustomerCode());
		element.setSender("4");
		testList.add(element);
		successList.add("4");

		element = new DelegationDataDTO();
		element.setType("toto");
		element.setSender("5");
		testList.add(element);
		successList.add("5");

		final List<DelegationDataDTO> list = UltimateHelperV8.NonUltimateDelegations(testList);
		Assert.assertEquals(successList.size(), list.size());
		for (final DelegationDataDTO delegation : list) {
			if (!successList.contains(delegation.getSender())) {
				Assert.fail();
			}
		}
	}
	
	
	@Test
	public final void testNonUltimateDelegationsNullCase() {
		final List<DelegationDataDTO> list = UltimateHelperV8.NonUltimateDelegations(null);
		Assert.assertNull(list);
	}
	
	@Test
	public final void testUltimateDelegationsNullCase() {
		final List<DelegationDataDTO> list = UltimateHelperV8.UltimateDelegations(null);
		Assert.assertNull(list);		
	}
	
	@Test
	public final void testUltimateDelegations() {

		final List<DelegationDataDTO> testList = new ArrayList<DelegationDataDTO>();
		final List<String> successList = new ArrayList<String>();

		DelegationDataDTO element = new DelegationDataDTO();
		element.setType("tata");
		element.setSender("0");
		testList.add(element);

		element = new DelegationDataDTO();
		element.setType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());
		element.setSender("1");
		testList.add(element);

		element = new DelegationDataDTO();
		element.setType(UltimateCustomerTypeCode.ULTIMATE_PLATINIUM.getUltimateCustomerCode());
		element.setSender("2");
		testList.add(element);

		element = new DelegationDataDTO();
		element.setType(UltimateCustomerTypeCode.ULTIMATE_PLATINIUM_FOR_LIFE.getUltimateCustomerCode());
		element.setSender("3");
		testList.add(element);

		element = new DelegationDataDTO();
		element.setType(UltimateCustomerTypeCode.ULTIMATE_SKIPPER.getUltimateCustomerCode());
		element.setSender("4");
		testList.add(element);
		
		element = new DelegationDataDTO();
		element.setType(UltimateFamilyTypeCode.ULTIMATE_FAMILY.getUltimateFamilyCode());
		element.setSender("5");
		testList.add(element);
		successList.add("5");

		element = new DelegationDataDTO();
		element.setType("toto");
		element.setSender("6");
		testList.add(element);

		final List<DelegationDataDTO> list = UltimateHelperV8.UltimateDelegations(testList);
		Assert.assertEquals(successList.size(), list.size());
		for (final DelegationDataDTO delegation : list) {
			if (!successList.contains(delegation.getSender())) {
				Assert.fail();
			}
		}
	}

}