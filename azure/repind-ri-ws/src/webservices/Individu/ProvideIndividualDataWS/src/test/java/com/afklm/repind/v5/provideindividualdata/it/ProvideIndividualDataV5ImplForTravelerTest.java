package com.afklm.repind.v5.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v5.provideindividualdata.ProvideIndividualDataV5Impl;
import com.afklm.repind.v5.provideindividualdata.type.ScopesToProvideEnum;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v5.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v5.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v5.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000418.v5.siccommontype.Requestor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProvideIndividualDataV5ImplForTravelerTest {

	private static final Log logger = LogFactory.getLog(ProvideIndividualDataV5ImplForTravelerTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private final String OPTION_SAMPLE = "AC";	
	private final String FB_NUMBER_SAMPLE = "002053750936";
	private final String OPTION_GIN = "GIN";

	@Autowired
	@Qualifier("passenger_ProvideIndividualData-v05Bean")
	private ProvideIndividualDataV5Impl provideIndividualDataV5Impl;

	@Test
	public void testProvideIndividualDataByIdentifier() throws BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request = new ProvideIndividualInformationRequest();
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		request.setRequestor(requestor);

		request.setIdentificationNumber(FB_NUMBER_SAMPLE);
		request.setOption(OPTION_SAMPLE);			


		ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("bardurklakstein", response.getIndividualResponse().getIndividualInformations().getPersonalIdentifier());
	}

	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testProvideIndividualDataByIdentifier_Individu() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request = new ProvideIndividualInformationRequest();
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		request.setRequestor(requestor);

		request.setIdentificationNumber("400411992060");
		request.setOption(OPTION_GIN);			

		ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("CHRISTINE", response.getIndividualResponse().getNormalizedName().getFirstName());
		Assert.assertEquals("BERTIN", response.getIndividualResponse().getNormalizedName().getLastName());
		Assert.assertEquals("400411992060", response.getIndividualResponse().getIndividualInformations().getIdentifier());

		request.setIdentificationNumber("400045002872");
		response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("BERNARD", response.getIndividualResponse().getNormalizedName().getFirstName());
		Assert.assertEquals("JOHARANE", response.getIndividualResponse().getNormalizedName().getLastName());
		Assert.assertEquals("400045002872", response.getIndividualResponse().getIndividualInformations().getIdentifier());
	}

	@Test
	public void testProvideIndividualDataByIdentifier_TravelerOnly() throws SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request = new ProvideIndividualInformationRequest();
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		request.setRequestor(requestor);
		request.setIdentificationNumber("200000000012");
		request.setOption(OPTION_GIN);			

		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}

	}

	@Test
	public void testProvideIndividualDataByIdentifier_ExternalIDOnly() throws SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request = new ProvideIndividualInformationRequest();
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		request.setRequestor(requestor);
		request.setIdentificationNumber("700000000000");
		request.setOption(OPTION_GIN);			

		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		}
	}


	@Test
	public void testProvideIndividualDataByIdentifier_IndividuWithRoleTraveler() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request = new ProvideIndividualInformationRequest();
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		request.setRequestor(requestor);
		request.setIdentificationNumber("400042100204");
		request.setOption(OPTION_GIN);			

		ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertEquals("FRANCOISE", response.getIndividualResponse().getNormalizedName().getFirstName());
		Assert.assertEquals("ARMENICO", response.getIndividualResponse().getNormalizedName().getLastName());
		Assert.assertEquals("400042100204", response.getIndividualResponse().getIndividualInformations().getIdentifier());
	}

	@Test
	public void testProvideIndividualDataByIdentifier_WhiteWingerOnly() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request = new ProvideIndividualInformationRequest();
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		request.setRequestor(requestor);
		request.setIdentificationNumber("900000168519");
		request.setOption(OPTION_GIN);			

//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			// Assert.assertEquals("FRANCOISE", response.getIndividualResponse().getNormalizedName().getFirstName());
			// Assert.assertEquals("ARMENICO", response.getIndividualResponse().getNormalizedName().getLastName());
			Assert.assertEquals("900000168519", response.getIndividualResponse().getIndividualInformations().getIdentifier());
			
// REPIND-555 : After migration, Prospect is WhiteWinger comming from AF SICUTF8 ! So we have to catch them			
/*			Assert.fail();
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		};
*/
	}

	@Test
	public void testProvideIndividualDataByIdentifier_WhiteWingerOnlyWithTravelerRole() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request = new ProvideIndividualInformationRequest();
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		request.setRequestor(requestor);
		request.setIdentificationNumber("900000168528");
		request.setOption(OPTION_GIN);			

//		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);

			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividualResponse());
			Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
			// Assert.assertEquals("FRANCOISE", response.getIndividualResponse().getNormalizedName().getFirstName());
			// Assert.assertEquals("ARMENICO", response.getIndividualResponse().getNormalizedName().getLastName());
			Assert.assertEquals("900000168528", response.getIndividualResponse().getIndividualInformations().getIdentifier());
			
// REPIND-555 : After migration, Prospect is WhiteWinger comming from AF SICUTF8 ! So we have to catch them					
/*			Assert.fail();
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		};
*/		
	}	

	@Test
	public void testProvideIndividualDataByIdentifier_AFProspectWithTravelerRole() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request = new ProvideIndividualInformationRequest();
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		request.setRequestor(requestor);
		request.setIdentificationNumber("900000000831");
		request.setOption(OPTION_GIN);			

		try {
			ProvideIndividualInformationResponse response = provideIndividualDataV5Impl.provideIndividualDataByIdentifier(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException bException) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_001, bException.getFaultInfo().getBusinessError().getErrorCode());
		};
		/*
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations());
		Assert.assertTrue("Ne devrait pas être un individu 'normal'", PopulationTypeEnum.WHITE_WINGERS.toString()
				.equals(response.getIndividualResponse().getIndividualInformations().getPopulationType()));
		*/
	}	

}
