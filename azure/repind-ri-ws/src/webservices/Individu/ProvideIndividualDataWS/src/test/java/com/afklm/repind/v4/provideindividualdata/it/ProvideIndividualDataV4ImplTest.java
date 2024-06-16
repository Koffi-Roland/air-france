package com.afklm.repind.v4.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v4.provideindividualdata.ProvideIndividualDataV4Impl;
import com.afklm.repind.v4.provideindividualdata.helpers.PaymentPreferencesDataHelper;
import com.afklm.repind.v4.provideindividualdata.type.ScopeEnum;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v4.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v4.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v4.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v4.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000418.v4.siccommontype.Requestor;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProvideIndividualDataV4ImplTest extends ProvideIndividualDataV4Impl {

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T730890";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "T730890";
	private static final String APP_CODE = "B2C";
	
	private Requestor initRequestor() {
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setMatricule(MATRICULE);
		requestor.setApplicationCode(APP_CODE);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		
		return requestor;
	}
	
	/**
	 * Create a provide's request with a individual of type F in our database
	 * @result this method will be return a BusinessErrorCode (the individual must not appear in the response).
	 *         BusinessErrorCode <code>ERROR_001</code>
	 */
	@Test
	@Transactional
	public void provideIndividualData_provideIndividualDataByIdentifierStatutF(){
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		//TODO : create a individu and update with statut F
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("110000028901");
		request.setOption("GIN");
		
		try{
			ProvideIndividualInformationResponse response = this.provideIndividualDataByIdentifier(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode().toString(), BusinessErrorCodeEnum.ERROR_001.toString());
		} catch (SystemException e) {
			Assert.fail();
		}	
	}
	
	/**
	 * Create a provide's request with a individual not exist our database
	 * @result this method will be return a BusinessErrorCode (the individual must not appear in the response).
	 *         BusinessErrorCode <code>ERROR_001</code>
	 */
	@Test
	@Transactional
	public void provideIndividualData_provideIndividualDataByIdentifierStatutFNotExist(){
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		//TODO : create a individu and update with statut F
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("000000000000");
		request.setOption("GIN");
		
		try{
			ProvideIndividualInformationResponse response = this.provideIndividualDataByIdentifier(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode().toString(), BusinessErrorCodeEnum.ERROR_001.toString());
		} catch (SystemException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void provideIndividualDataByGIN_EmailInvalid() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// Init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400518660834");
		request.setOption("GIN");
		
		ProvideIndividualInformationResponse response = this.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals(1, response.getEmailResponse().size());
		
		Assert.assertEquals("400518660834", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		// Assert.assertTrue(response.getIndividualResponse().getIndividualInformations().getPopulationType().contains("W"));
	}		
	
	@Test
	public void provideIndividualDataByGin_REPIND_PP_inError() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException, UtfException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		requestor.setScope(ScopeEnum.ALL_DATA.toString());
		
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.setRequestor(requestor);
		
		PaymentPreferencesDataHelper paymentPreferencesDataHelperMock = Mockito.mock(PaymentPreferencesDataHelper.class);
		this.setPaymentPreferencesDataHelperV4(paymentPreferencesDataHelperMock);
		Mockito.doThrow(new JrafDomainException("INTERNAL ERROR")).when(paymentPreferencesDataHelperMock).provideMaskedPaymentPreferences(Mockito.anyString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = this.provideIndividualDataByIdentifier(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertTrue(response.getAccountDataResponse().getAccountData().getPercentageFullProfil() <= 75);
	}

	@Test
	@Transactional
	public void provideIndividualData_ForMergeWithWarningWithoutAccountData()
			throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("110000012801");
		request.setOption("GIN");

		ProvideIndividualInformationResponse response = provideIndividualDataByIdentifier(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getWarningResponse());
		Assert.assertNotNull(response.getWarningResponse().get(0));
		Assert.assertNotNull(response.getWarningResponse().get(0).getWarning());
		Assert.assertEquals("INDIVIDUAL MERGED", response.getWarningResponse().get(0).getWarning().getWarningCode());
		Assert.assertEquals("400003729376", response.getWarningResponse().get(0).getWarning().getWarningDetails());
	}
}
