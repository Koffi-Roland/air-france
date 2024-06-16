package com.afklm.rigui.services.resources;

import com.afklm.rigui.model.individual.ModelCommunicationPreference;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.builder.w000442.CommPrefRequestBuilder;
import com.afklm.rigui.services.helper.resources.CommPrefHelper;
import com.afklm.rigui.services.resources.CommunicationPreferenceService;
import com.afklm.rigui.spring.TestConfiguration;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.ComunicationPreferencesRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd8.RequestorV2;
import com.afklm.rigui.dao.individu.CommunicationPreferencesRepository;
import com.afklm.rigui.entity.individu.CommunicationPreferences;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class CommPrefServiceTest {

	private static final String GIN_TESTED = "400392073372";
	private static final String FLYING_BLUE_DOMAIN = "F";
	private static final String SALES_DOMAIN = "S";
	private static final String PRIVACY_DOMAIN = "P";

	private static final Integer COMM_PREF_ID_TESTED = 106156482;
	private static final String YES = "Y";
	private static final String NO = "N";

	@Autowired
	private CommunicationPreferenceService commPrefService;

	@Autowired
	private CommunicationPreferencesRepository commPrefRepository;

	@Autowired
	private CommPrefHelper commPrefHelper;

	@Autowired
	private CommPrefRequestBuilder requestBuilder;

	@BeforeEach
	public void init() {
		// Set the optin YES for the commPref
		CommunicationPreferences comPref = commPrefRepository.findById(COMM_PREF_ID_TESTED).get();
		comPref.setSubscribe(YES);
		//commPrefRepository.updateComPref(comPref);
	}


	@Test
	void test_getAll() {

		List<ModelCommunicationPreference> modelCommunicationPreferences = null;

		try {
			modelCommunicationPreferences = commPrefService.getAll(GIN_TESTED);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		// Verify that the individual has got 7 commPrefs
		assert modelCommunicationPreferences != null;
		Assertions.assertEquals(3, modelCommunicationPreferences.size());

		// Verify that the commPrefs have the good domain
		Assertions.assertEquals(1, countCommPrefByDomain(modelCommunicationPreferences, FLYING_BLUE_DOMAIN));
		Assertions.assertEquals(1, countCommPrefByDomain(modelCommunicationPreferences, PRIVACY_DOMAIN));
		Assertions.assertEquals(1, countCommPrefByDomain(modelCommunicationPreferences, SALES_DOMAIN));

	}

	@Transactional
	@Test
	void test_update() {

		// Load the entity before the update and convert it to a model
		CommunicationPreferences entityBeforeUpdate = commPrefRepository.getReferenceById(COMM_PREF_ID_TESTED);
		ModelCommunicationPreference modelBeforeUpdate = commPrefHelper.buildCommPrefModel(entityBeforeUpdate);

		// Check that the OPTIN is at Y
		Assertions.assertEquals(YES, modelBeforeUpdate.getSubscribe());

		// Set the subscribe to N (to simulate a change of the OPTIN)
		entityBeforeUpdate.setSubscribe(NO);

		//commPrefRepository.updateComPref(entityBeforeUpdate);

		CommunicationPreferences entityAfterUpdate = commPrefRepository.getReferenceById(COMM_PREF_ID_TESTED);
		ModelCommunicationPreference modelAfterUpdate = commPrefHelper.buildCommPrefModel(entityAfterUpdate);

		// Verify that the optin is NO after the update
		Assertions.assertEquals(NO, modelAfterUpdate.getSubscribe());

	}

	@Transactional
	@Test
	void requestBuilderTest() {
		CommunicationPreferences entityBeforeUpdate = commPrefRepository.getReferenceById(COMM_PREF_ID_TESTED);
		ModelCommunicationPreference modelBeforeUpdate = commPrefHelper.buildCommPrefModel(entityBeforeUpdate);

		// build a CreateUpdateIndividualRequest with compref of type different than 'S'
		modelBeforeUpdate.setDomain(FLYING_BLUE_DOMAIN);
		CreateUpdateIndividualRequest request = requestBuilder.buildUpdateRequest(GIN_TESTED, modelBeforeUpdate);

		// build a CreateUpdateIndividualRequest with compref of type 'S'
		modelBeforeUpdate.setDomain(SALES_DOMAIN);
		CreateUpdateIndividualRequest requestDomainS = requestBuilder.buildUpdateRequest(GIN_TESTED, modelBeforeUpdate);

		// check if any properties which doesn't have to be updated the  didn't changed
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("REPIND/IHM");
		Assertions.assertEquals(requestor.getChannel(), request.getRequestor().getChannel());
		Assertions.assertEquals(requestor.getSite(), request.getRequestor().getSite());
		Assertions.assertEquals(requestor.getSignature(), request.getRequestor().getSignature());
		Assertions.assertEquals("U", request.getUpdateCommunicationPrefMode());
		Assertions.assertEquals(GIN_TESTED, request.getIndividualRequest().getIndividualInformations().getIdentifier());

		// there should be no market language in a request's compref which is not of domain 'S'
		List<ComunicationPreferencesRequest> communicationPreferences = request.getComunicationPreferencesRequest();
		boolean isThereMarketLanguages = false;
		isThereMarketLanguages = isThereMarketLanguages(modelBeforeUpdate, communicationPreferences,
						isThereMarketLanguages);
		Assertions.assertFalse(isThereMarketLanguages);


		// there should be market language(s) in a request's compref which is of domain 'S'
		List<ComunicationPreferencesRequest> communicationPreferencesS =
						requestDomainS.getComunicationPreferencesRequest();

		isThereMarketLanguages = isThereMarketLanguages(modelBeforeUpdate, communicationPreferencesS,
						isThereMarketLanguages);
		Assertions.assertTrue(isThereMarketLanguages);
	}

	/**
	 * check if there is a marketLanguage in a communicationPreferenceRequest corresponding to the
	 * communicationPreference model
	 *
	 * @param modelBeforeUpdate the model of a communicationPreference
	 * @param communicationPreferencesRequests
	 * @param isThereMarketLanguages indicates if there is marketLanguages in
	 * @return
	 */
	private boolean isThereMarketLanguages(final ModelCommunicationPreference modelBeforeUpdate,
					final List<ComunicationPreferencesRequest> communicationPreferencesRequests,
					boolean isThereMarketLanguages) {
		for(ComunicationPreferencesRequest comunicationPreferencesRequest : communicationPreferencesRequests) {
			String domain = comunicationPreferencesRequest.getCommunicationPreferences().getDomain();
			String comGroupType =
							comunicationPreferencesRequest.getCommunicationPreferences().getCommunicationGroupeType();
			String comType = comunicationPreferencesRequest.getCommunicationPreferences().getCommunicationType();
			String subChannel = comunicationPreferencesRequest.getCommunicationPreferences().getSubscriptionChannel();

			if (domain.equals(modelBeforeUpdate.getDomain()) && comGroupType.equals(modelBeforeUpdate.getComGroupType())
							&& comType.equals(modelBeforeUpdate.getComType()) && subChannel != null && subChannel.equals(modelBeforeUpdate.getChannel())) {
				isThereMarketLanguages =
								comunicationPreferencesRequest.getCommunicationPreferences().getMarketLanguage() != null;
				break;
			}
		}
		return isThereMarketLanguages;
	}

	private int countCommPrefByDomain(List<ModelCommunicationPreference> commPrefs, String domain) {

		int count = 0;

		for (ModelCommunicationPreference commPref : commPrefs) {
			if (domain.equals(commPref.getDomain())) count++;
		}

		return count;

	}

}
