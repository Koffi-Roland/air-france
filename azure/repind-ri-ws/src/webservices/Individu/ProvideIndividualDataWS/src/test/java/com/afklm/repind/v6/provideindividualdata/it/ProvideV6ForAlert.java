package com.afklm.repind.v6.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v6.provideindividualdata.ProvideIndividualDataV6Impl;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v6.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v6.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v6.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v6.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000418.v6.sicindividutype.Alert;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.AlertDTO;
import com.airfrance.repind.dto.reference.RefAlertDTO;
import com.airfrance.repind.service.individu.internal.AlertDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.reference.internal.RefAlertDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// import com.airfrance.sicutf8.service.prospect.IProspectDS;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProvideV6ForAlert {

	private static final Log logger = LogFactory.getLog(ProvideV6ForAlert.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T698964";
	private static final String SITE = "QVI";
	private static final String GIN_INDIVIDUS = "400424668522";
	private static final String GIN_INDIVIDUS_PROSPECT = "900025059447";

	@Autowired
	@Qualifier("passenger_ProvideIndividualData-v06Bean")
	private ProvideIndividualDataV6Impl provideIndividualDataV6Impl;

	@Autowired
	protected IndividuDS individuDS;
	
//	@Autowired
//	protected IProspectDS prospectDS;

	@Autowired
	protected CommunicationPreferencesDS CommunicationPreferencesDS;
	
	@Autowired
	protected AlertDS alertDS;
	
	@Autowired
	protected RefAlertDS refAlertDS;

	private List<RefAlertDTO> listRefInp;
	private List<RefAlertDTO> listRefEnv;

	/*
	 * Create alert for random Email
	 */
	@Test
	public void test_ProvideAlertForIndividual() throws BusinessErrorBlocBusinessException, SystemException, JrafDomainException {

		logger.info("Test ProvideAlertForIndividual start...");
		
		ProvideIndividualInformationRequest request = generateMandatoryRequest();
		request.setIdentificationNumber(GIN_INDIVIDUS);
		request.setOption("GIN");

		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		if(response.getAlertResponse() == null){
			Assert.assertTrue(false);
		}else{
			List<Alert> listResponse = response.getAlertResponse().getAlert();
			AlertDTO search = new AlertDTO();
			search.setSgin(GIN_INDIVIDUS);
			search.setOptIn("Y");
			List<AlertDTO> listBDD = alertDS.findByExample(search);
			if(listResponse.size() == listBDD.size()){ //TODO : a finir
				Assert.assertTrue(true);
			}else{
				Assert.assertTrue(false);
			}
			logger.info("Test CreateAlertForIndividual stop...");
		}
	}
	
	/*
	 * Create alert for random Email
	 */
	@Test
	public void test_ProvideAlertForProspect() throws BusinessErrorBlocBusinessException, SystemException, JrafDomainException {

		logger.info("Test ProvideAlertForProspect start...");
		
		ProvideIndividualInformationRequest request = generateMandatoryRequest();
		request.setIdentificationNumber(GIN_INDIVIDUS_PROSPECT);
		request.setOption("GIN");

		ProvideIndividualInformationResponse response = provideIndividualDataV6Impl.provideIndividualDataByIdentifier(request);
		if(response.getAlertResponse() == null){
			Assert.assertTrue(false);
		}else{
			List<Alert> listResponse = response.getAlertResponse().getAlert();
			List<AlertDTO> listBDD = alertDS.findAlert(GIN_INDIVIDUS_PROSPECT);
			if(listResponse.size() == listBDD.size()){ //TODO : a finir
				Assert.assertTrue(listResponse.size() == listBDD.size());
			}else{
				Assert.assertTrue(listResponse.size() == listBDD.size());
			}
			logger.info("Test CreateAlertForIndividual stop...");
		}
	}

	private ProvideIndividualInformationRequest generateMandatoryRequest() {
		
		ProvideIndividualInformationRequest ret =  new ProvideIndividualInformationRequest();
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		ret.setRequestor(requestor);
		
		ret.getScopeToProvide().add("ALERT");
		
		return ret;
	}

}
