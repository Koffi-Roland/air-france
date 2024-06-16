package com.afklm.repind.v8.createorupdateindividual.ut.helper;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.helpers.PreferenceCheckHelper;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.PreferenceRequest;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PreferenceDataV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PreferenceDatasV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PreferenceV2;
import com.airfrance.ref.exception.InvalidParameterException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class PreferenceHelperTest {

	@Autowired
	protected PreferenceCheckHelper preferenceHelper;
	
	private static final String INVALIDPARAMETER = "Invalid parameter: ";

	@Test
	public void testPreferenceHelperControlProspectPreferenceWithoutType() {
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		PreferenceRequest prefReq = new PreferenceRequest();
		
		PreferenceV2 prefV2 = new PreferenceV2();
		PreferenceDatasV2 prefDatasV2 = new PreferenceDatasV2();
		PreferenceDataV2 preferredAirport = new PreferenceDataV2();
		preferredAirport.setKey("preferredAirport");
		preferredAirport.setValue("NCE");
		
		prefDatasV2.getPreferenceData().add(preferredAirport);
		
		prefV2.setPreferenceDatas(prefDatasV2);
		
		prefReq.getPreference().add(prefV2);
		
		request.setPreferenceRequest(prefReq);
		
		try {
			preferenceHelper.controlProspectPreference(request);
			// INVALID PARAMETER EXCEPTION
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertEquals(INVALIDPARAMETER+"Prospect preference type must not be null", e.getMessage());
		}
		
	}

	@Test
	public void testPreferenceHelperControlProspectPreferenceTooMuchPreferences() {
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		PreferenceRequest prefReq = new PreferenceRequest();
		
		PreferenceV2 pref1 = new PreferenceV2();
		PreferenceV2 pref2 = new PreferenceV2();
		prefReq.getPreference().add(pref1);
		prefReq.getPreference().add(pref2);
		
		request.setPreferenceRequest(prefReq);
		
		try {
			preferenceHelper.controlProspectPreference(request);
			// INVALID PARAMETER EXCEPTION
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertEquals(INVALIDPARAMETER+"Only 1 preference of type TPC is allowed for prospect", e.getMessage());
		}
		
	}

	@Test
	public void testPreferenceHelperControlProspectPreferenceWrongTypeForUpdate() {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		PreferenceRequest prefReq = new PreferenceRequest();
		
		PreferenceV2 prefV2 = new PreferenceV2();
		prefV2.setType("TPA");
		prefV2.setId("10145");
		
		PreferenceDatasV2 prefDatasV2 = new PreferenceDatasV2();
		PreferenceDataV2 preferredAirport = new PreferenceDataV2();
		preferredAirport.setKey("preferredAirport");
		preferredAirport.setValue("NCE");

// REPIND-768 : We do not need anymore to identify origin of the preference
//		PreferenceDataV2 type = new PreferenceDataV2();
//		type.setKey("type");
//		type.setValue("WWP");
//		prefDatasV2.getPreferenceData().add(type);
		
		prefDatasV2.getPreferenceData().add(preferredAirport);
		
		prefV2.setPreferenceDatas(prefDatasV2);
		
		prefReq.getPreference().add(prefV2);
		
		request.setPreferenceRequest(prefReq);
		
		try {
			preferenceHelper.controlProspectPreference(request);
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertEquals(INVALIDPARAMETER+"Only TPC type is allowed for prospect preference", e.getMessage());
		}
		
	}

	@Test
	@Ignore // REPIND-768 : We do not need anymore to identify origin of the preference
	public void testPreferenceHelperControlProspectPreferenceWrongTypePrefDatas() {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		PreferenceRequest prefReq = new PreferenceRequest();
		
		PreferenceV2 prefV2 = new PreferenceV2();
		prefV2.setType("TPC");
		
		PreferenceDatasV2 prefDatasV2 = new PreferenceDatasV2();
		PreferenceDataV2 preferredAirport = new PreferenceDataV2();
		preferredAirport.setKey("preferredAirport");
		preferredAirport.setValue("NCE");
		
		PreferenceDataV2 type = new PreferenceDataV2();
		type.setKey("type");
		type.setValue("WWC");

		prefDatasV2.getPreferenceData().add(type);
		prefDatasV2.getPreferenceData().add(preferredAirport);
		
		prefV2.setPreferenceDatas(prefDatasV2);
		
		prefReq.getPreference().add(prefV2);
		
		request.setPreferenceRequest(prefReq);
		
		try {
			preferenceHelper.controlProspectPreference(request);
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertEquals(INVALIDPARAMETER+"Only WWP for type is allowed for prospect preference data", e.getMessage());
		}
		
	}

	@Test
	@Ignore
	// REPIND-768 : We do not need anymore to identify origin of the preference
	public void testPreferenceHelperControlProspectPreferenceNoTypePrefDatas() {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		PreferenceRequest prefReq = new PreferenceRequest();
		
		PreferenceV2 prefV2 = new PreferenceV2();
		prefV2.setType("TPC");
		
		PreferenceDatasV2 prefDatasV2 = new PreferenceDatasV2();
		PreferenceDataV2 preferredAirport = new PreferenceDataV2();
		preferredAirport.setKey("preferredAirport");
		preferredAirport.setValue("NCE");
		prefDatasV2.getPreferenceData().add(preferredAirport);
		
		prefV2.setPreferenceDatas(prefDatasV2);
		
		prefReq.getPreference().add(prefV2);
		
		request.setPreferenceRequest(prefReq);
		
		try {
			preferenceHelper.controlProspectPreference(request);
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertEquals(INVALIDPARAMETER+"type is mandatory for prospect preference data", e.getMessage());
		}
		
	}

	@Test
	public void testPreferenceHelperControlProspectPreferenceCreateOK() {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		PreferenceRequest prefReq = new PreferenceRequest();
		
		PreferenceV2 prefV2 = new PreferenceV2();
		prefV2.setType("TPC");
		
		PreferenceDatasV2 prefDatasV2 = new PreferenceDatasV2();
		PreferenceDataV2 preferredAirport = new PreferenceDataV2();
		preferredAirport.setKey("preferredAirport");
		preferredAirport.setValue("NCE");
	
// REPIND-768 : We do not need anymore to identify origin of the preference		
//		PreferenceDataV2 type = new PreferenceDataV2();
//		type.setKey("type");
//		type.setValue("WWP");
//		prefDatasV2.getPreferenceData().add(type);
		
		prefDatasV2.getPreferenceData().add(preferredAirport);
		
		prefV2.setPreferenceDatas(prefDatasV2);
		
		prefReq.getPreference().add(prefV2);
		
		request.setPreferenceRequest(prefReq);
		
		try {
			preferenceHelper.controlProspectPreference(request);
			//No exception
			assertTrue(true);
		} catch (InvalidParameterException e) {
			assertTrue(false);
		}
		
	}

	@Test
	public void testPreferenceHelperControlProspectPreferenceUpdateOK() {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		PreferenceRequest prefReq = new PreferenceRequest();
		
		PreferenceV2 prefV2 = new PreferenceV2();
		prefV2.setType("TPC");
		prefV2.setId("121345");
		
		PreferenceDatasV2 prefDatasV2 = new PreferenceDatasV2();
		PreferenceDataV2 preferredAirport = new PreferenceDataV2();
		preferredAirport.setKey("preferredAirport");
		preferredAirport.setValue("NCE");
		
// REPIND-768 : We do not need anymore to identify origin of the preference
//		PreferenceDataV2 type = new PreferenceDataV2();
//		type.setKey("type");
//		type.setValue("WWP");
//		prefDatasV2.getPreferenceData().add(type);
		
		prefDatasV2.getPreferenceData().add(preferredAirport);
		
		prefV2.setPreferenceDatas(prefDatasV2);
		
		prefReq.getPreference().add(prefV2);
		
		request.setPreferenceRequest(prefReq);
		
		try {
			preferenceHelper.controlProspectPreference(request);
			//No exception
			assertTrue(true);
		} catch (InvalidParameterException e) {
			assertTrue(false);
		}
		
	}

	@Test
	public void testPreferenceHelperControlProspectPreferenceDeleteOK() {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		PreferenceRequest prefReq = new PreferenceRequest();
		
		PreferenceV2 prefV2 = new PreferenceV2();
		prefV2.setType("TPC");
		prefV2.setId("121345");
		
		PreferenceDatasV2 prefDatasV2 = new PreferenceDatasV2();
		prefV2.setPreferenceDatas(prefDatasV2);
		
		prefReq.getPreference().add(prefV2);
		
		request.setPreferenceRequest(prefReq);
		
		try {
			preferenceHelper.controlProspectPreference(request);
			//No exception
			assertTrue(true);
		} catch (InvalidParameterException e) {
			assertTrue(false);
		}
		
	}
		
}
