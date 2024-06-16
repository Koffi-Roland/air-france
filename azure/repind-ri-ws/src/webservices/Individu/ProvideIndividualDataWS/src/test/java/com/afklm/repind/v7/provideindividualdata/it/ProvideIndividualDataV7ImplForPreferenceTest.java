package com.afklm.repind.v7.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v7.provideindividualdata.ProvideIndividualDataV7Impl;
import com.afklm.repind.v7.provideindividualdata.helpers.PreferenceHelper;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v7.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v7.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.PreferenceV2;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProvideIndividualDataV7ImplForPreferenceTest {

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "T412211";
	private static final String APP_CODE = "B2C";
	
	@Autowired
	@Qualifier("passenger_ProvideIndividualData-v07Bean")
	private ProvideIndividualDataV7Impl provideIndividualDataV7Impl;
	
	@Autowired
	protected PreferenceHelper preferenceHelperV7;
	
	@Autowired
	private PreferenceDS preferenceDSMock;
	
	@Autowired
	private PreferenceDS preferenceDS;
	
	private PreferenceHelper preferenceHelperMock;
	
	private RequestorV2 initRequestor() {
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setMatricule(MATRICULE);
		requestor.setApplicationCode(APP_CODE);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		
		return requestor;
	}
	
	@Test
	public void provideIndividualDataByGIN_PreferenceOK() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// Init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000017925");
		request.setOption("GIN");
		request.getScopeToProvide().add("PREFERENCE");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getPreferenceResponse());
		Assert.assertNotNull(response.getPreferenceResponse().getPreference());
		Assert.assertEquals("TPC", response.getPreferenceResponse().getPreference().get(0).getType());
		
		Assert.assertNotNull(response.getPreferenceResponse().getPreference().get(0).getPreferenceDatas());
		
		Assert.assertEquals("900000017925", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("W"));
	}

	@Test
	@Ignore
	//TODO update preference method for DOCT
	public void provideIndividualDataByGIN_PreferenceDOCTOK() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// Init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400272037383");
		request.setOption("GIN");
//		request.setPopulationTargeted("A");
		request.getScopeToProvide().add("ALL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getPreferenceResponse());
		Assert.assertNotNull(response.getPreferenceResponse().getPreference());
		Assert.assertEquals("Preferences number is not the expected", 2, response.getPreferenceResponse().getPreference().size());
		
		Assert.assertNotNull(response.getPreferenceResponse().getPreference().get(0));
		Assert.assertNotNull(response.getPreferenceResponse().getPreference().get(0).getPreferenceDatas());
		
		Assert.assertNotNull(response.getPreferenceResponse().getPreference().get(1));
		Assert.assertNotNull(response.getPreferenceResponse().getPreference().get(1).getPreferenceDatas());
		
		Assert.assertEquals("400272037383", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("I"));
	}
	
	
	@Test
	public void provideIndividualDataByGIN_AllPreferences_RI() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		List<PreferenceDTO> preferenceDTOList = new ArrayList<PreferenceDTO>();
		
		PreferenceDTO preferenceDTOPIC = new PreferenceDTO();
		preferenceDTOPIC.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		preferenceDTOPIC.setType("PIC");
		preferenceDTOPIC.getPreferenceDataDTO().add(new PreferenceDataDTO("bluebizNumber", "10"));
		preferenceDTOPIC.getPreferenceDataDTO().add(new PreferenceDataDTO("FFPNumber", "100"));
		preferenceDTOPIC.getPreferenceDataDTO().add(new PreferenceDataDTO("bluebizNumber", "1000"));
		preferenceDTOList.add(preferenceDTOPIC);
		
		PreferenceDTO preferenceDTOTCC = new PreferenceDTO();
		preferenceDTOTCC.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		preferenceDTOTCC.setType("TCC");
		preferenceDTOTCC.getPreferenceDataDTO().add(new PreferenceDataDTO("civility", "MR"));
		preferenceDTOTCC.getPreferenceDataDTO().add(new PreferenceDataDTO("dateOfBirth", "01/01/1980"));
		preferenceDTOTCC.getPreferenceDataDTO().add(new PreferenceDataDTO("email", "test@email.com"));
		preferenceDTOTCC.getPreferenceDataDTO().add(new PreferenceDataDTO("firstName", "Jean"));
		preferenceDTOTCC.getPreferenceDataDTO().add(new PreferenceDataDTO("lastName", "Bon"));
		preferenceDTOTCC.getPreferenceDataDTO().add(new PreferenceDataDTO("bluebizNumber", "10"));
		preferenceDTOTCC.getPreferenceDataDTO().add(new PreferenceDataDTO("FFPNumber", "100"));
		preferenceDTOTCC.getPreferenceDataDTO().add(new PreferenceDataDTO("bluebizNumber", "1000"));
		preferenceDTOList.add(preferenceDTOTCC);
		
		PreferenceDTO preferenceDTOTPC = new PreferenceDTO();
		preferenceDTOTPC.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		preferenceDTOTPC.setType("TPC");
		preferenceDTOTPC.getPreferenceDataDTO().add(new PreferenceDataDTO("arrivalAirport", "NCE"));
		preferenceDTOTPC.getPreferenceDataDTO().add(new PreferenceDataDTO("arrivalCity", "NCE"));
		preferenceDTOTPC.getPreferenceDataDTO().add(new PreferenceDataDTO("boardingPass", "10"));
		preferenceDTOTPC.getPreferenceDataDTO().add(new PreferenceDataDTO("customerLeisure", "A"));
		preferenceDTOTPC.getPreferenceDataDTO().add(new PreferenceDataDTO("departureAirport", "NCE"));
		preferenceDTOTPC.getPreferenceDataDTO().add(new PreferenceDataDTO("departureCity", "NCE"));
		preferenceDTOTPC.getPreferenceDataDTO().add(new PreferenceDataDTO("meal", "Salt"));
		preferenceDTOTPC.getPreferenceDataDTO().add(new PreferenceDataDTO("seat", "W"));
		preferenceDTOTPC.getPreferenceDataDTO().add(new PreferenceDataDTO("travelClass", "E"));
		preferenceDTOList.add(preferenceDTOTPC);
		
		PreferenceDTO preferenceDTOECC = new PreferenceDTO();
		preferenceDTOECC.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		preferenceDTOECC.setType("ECC");
		preferenceDTOECC.getPreferenceDataDTO().add(new PreferenceDataDTO("email", "email@test.com"));
		preferenceDTOECC.getPreferenceDataDTO().add(new PreferenceDataDTO("firstName", "Jean"));
		preferenceDTOECC.getPreferenceDataDTO().add(new PreferenceDataDTO("lastName", "Bon"));
		preferenceDTOECC.getPreferenceDataDTO().add(new PreferenceDataDTO("phoneNumber", "0123456"));
		preferenceDTOList.add(preferenceDTOECC);
		
		PreferenceDTO preferenceDTOHDC = new PreferenceDTO();
		preferenceDTOHDC.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		preferenceDTOHDC.setType("HDC");
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("codeHCP1", "WCHR"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("codeHCP2", "WCHR"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("codeHCP3", "WCHR"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("codeMat1", "WCMP"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("codeMat2", "WCMP"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("dogGuideBreed", "labrador"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("dogGuideFlag", "Y"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("dogGuideWeight", "10"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("height1", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("height2", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("heightPlie1", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("heightPlie2", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("length1", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("length2", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("lengthPlie1", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("lengthPlie2", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaCCAccomp", "A"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaCCDateEnd", "31/12/2018"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaCCDateStart", "01/01/2018"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaCCFlag", "Y"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaCCTxt", "T"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaLCAccomp", "A"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaLCDateEnd", "31/12/2018"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaLCDateStart", "01/01/2018"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaLCFlag", "Y"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaLCTxt", "T"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaMCAccomp", "A"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaMCDateEnd", "31/12/2018"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaMCDateStart", "01/01/2018"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaMCFlag", "Y"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("medaMCTxt", "T"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("otherMat", "E"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("oxygFlag", "Y"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("oxygOutput", "10"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("weight1", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("weight2", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("width1", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("width2", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("widthPlie1", "100"));
		preferenceDTOHDC.getPreferenceDataDTO().add(new PreferenceDataDTO("widthPlie2", "100"));
		preferenceDTOList.add(preferenceDTOHDC);
		
		preferenceDSMock = Mockito.mock(PreferenceDS.class);
		ReflectionTestUtils.setField(provideIndividualDataV7Impl, "preferenceDS", preferenceDSMock);
		Mockito.doReturn(preferenceDTOList).when(preferenceDSMock).findByExample(Mockito.any(PreferenceDTO.class));
		
		ReflectionTestUtils.setField(provideIndividualDataV7Impl, "preferenceHelperV7", preferenceHelperV7);
		
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400272037383");
		request.setOption("GIN");
		request.getScopeToProvide().add("MARKETING");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertEquals(5, response.getPreferenceResponse().getPreference().size());
		
		for (PreferenceV2 preference : response.getPreferenceResponse().getPreference()) {
			if (preference.getType().equalsIgnoreCase("TPC")) {
				Assert.assertEquals(9, preference.getPreferenceDatas().getPreferenceData().size());
			} else if (preference.getType().equalsIgnoreCase("PIC")) {
				Assert.assertEquals(3, preference.getPreferenceDatas().getPreferenceData().size());
			} else if (preference.getType().equalsIgnoreCase("ECC")) {
				Assert.assertEquals(4, preference.getPreferenceDatas().getPreferenceData().size());
			} else if (preference.getType().equalsIgnoreCase("TCC")) {
				Assert.assertEquals(8, preference.getPreferenceDatas().getPreferenceData().size());
			} else if (preference.getType().equalsIgnoreCase("HDC")) {
				Assert.assertEquals(40, preference.getPreferenceDatas().getPreferenceData().size());
			}
		}
	}
	/*@Test
	public void provideIndividualDataByGIN_TooMannyPreferences() throws JrafDomainException, SystemException {
		List<PreferenceDTO> preferenceDTOList = new ArrayList<PreferenceDTO>();
		
		for (int i = 0; i < 150; i++) {
			PreferenceDTO preferenceDTO = new PreferenceDTO();
			preferenceDTO.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
			preferenceDTO.setType("TDC");
			preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("nationality", "FR"));
			preferenceDTOList.add(preferenceDTO);
		}
		
		preferenceDSMock = Mockito.mock(IPreferenceDS.class);
		provideIndividualDataV7Impl.setPreferenceDS(preferenceDSMock);
		Mockito.doReturn(preferenceDTOList).when(preferenceDSMock).findByExample(Mockito.any(PreferenceDTO.class));
		
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400272037383");
		request.setOption("GIN");
		request.getScopeToProvide().add("PREFERENCE");
		
		
		try {
			provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals("Too many preferences to provide", e.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}*/
}	

