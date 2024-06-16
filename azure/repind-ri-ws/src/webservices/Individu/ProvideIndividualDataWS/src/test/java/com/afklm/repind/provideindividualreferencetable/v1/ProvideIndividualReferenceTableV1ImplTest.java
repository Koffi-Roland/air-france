package com.afklm.repind.provideindividualreferencetable.v1;


import com.afklm.config.WebTestConfig;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001588.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001588.v1.data.ProvideIndividualReferenceTableRequest;
import com.afklm.soa.stubs.w001588.v1.data.ProvideIndividualReferenceTableResponse;
import com.afklm.soa.stubs.w001588.v1.response.BusinessError;
import com.afklm.soa.stubs.w001588.v1.response.BusinessErrorBloc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;



@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProvideIndividualReferenceTableV1ImplTest extends ProvideIndividualReferenceTableV1Impl {
	
	@Test
	public void testTableRefSexe() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_SEXE");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
		
		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(3, response.getRefGenericCodeLabelsType().size());
	}

	@Test
	public void testTableRefCivility() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_CIVILITE");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(5, response.getRefGenericCodeLabelsType().size());
	}

	@Test
	public void testTableRefRienDuTout() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_RIENDUTOUT");
	
		try {
			this.provideIndividualReferenceTable(request);

		} catch (BusinessErrorBlocBusinessException ex) {
		
			Assert.assertNotNull(ex);
			BusinessErrorBloc beb = (BusinessErrorBloc) ex.getFaultInfo() ;
			Assert.assertNotNull(beb);
			BusinessError be = (BusinessError) beb.getBusinessError();
			Assert.assertEquals("ERROR_932", be.getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", be.getErrorLabel());
			Assert.assertEquals("Invalid parameter: Table not found", be.getErrorDetail());
		}
	}
	
	@Test
	public void testTableRefCodeTitre() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_CODE_TITRE");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(79, response.getRefGenericCodeLabelsType().size());
	}

	@Test
	public void testTableRefStatutIndividu() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_STATUT_INDIVIDU");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(6, response.getRefGenericCodeLabelsType().size());
	}
	
	@Test
	public void testTableRefPermissionsQuestion() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_PERMISSIONS_QUESTION");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		// Assert.assertEquals(374, response.getRefGenericCodeLabelsType().size());
		// Cela change trop pour poser une condition
	}

	@Test
	public void testTablePays() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("PAYS");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(258, response.getRefGenericCodeLabelsType().size());
	}

	@Test
	public void testTableLangues() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("LANGUES");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(187, response.getRefGenericCodeLabelsType().size());
	}

	
	@Test
	public void testTableBDMCountries() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_BDM_COUNTRIES");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(268, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableBDMLanguages() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_BDM_LANGUAGES");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(3, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableBDMMeals() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_BDM_MEALS");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(23, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableBDMSeats() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_BDM_SEATS");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(2, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableChannelCheckin() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_CHANNEL_CHECKIN");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(2, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableChannelComm() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_CHANNEL_COMMUNICATION");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(3, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableComfort() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMFORT");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(2, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableComfortPaid() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMFORT_PAID");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(3, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableCultural() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_CULTURAL");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(4, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableDrinkBeverages() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_DRINK_BEVERAGES");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(10, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableDrinkWelcome() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_DRINK_WELCOME");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(5, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableMeals() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_MEALS");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(5, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableMealsPaid() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_MEALS_PAID");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(6, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableReading() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_READING");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(15, response.getRefGenericCodeLabelsType().size());
	}
	

	@Test
	public void testTableSeats() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_SEATS");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(5, response.getRefGenericCodeLabelsType().size());
	}
	
	@Test
	public void testTableHandicapType() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_HANDICAP_TYPE");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(response.getRefGenericCodeLabelsType().size() > 1);
	}
	
	@Test
	public void testTableHandicapCode() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_HANDICAP_CODE");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(response.getRefGenericCodeLabelsType().size() > 1);
	}
	
	@Test
	public void testTableHandicapDataKey() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_HANDICAP_DATA_KEY");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(response.getRefGenericCodeLabelsType().size() > 1);
	}
	
	@Test
	public void testTableEtatRoleCTR() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_ETAT_ROLE_CTR");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(response.getRefGenericCodeLabelsType().size() > 1);
	}
	
	@Test
	public void testTableAutorisatioNMailing() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_AUTORIS_MAIL");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(response.getRefGenericCodeLabelsType().size() > 1);
	}
	
	@Test
	public void testTableTierFB() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_NIV_TIER_FB");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(response.getRefGenericCodeLabelsType().size() > 1);
	}
	
	@Test
	public void testTablePreferenceType() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_PREFERENCE_TYPE");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(20, response.getRefGenericCodeLabelsType().size());
	}
	
	@Test
	public void testTablePreferenceDataKey() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_PREFERENCE_DATA_KEY");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(186, response.getRefGenericCodeLabelsType().size());
	}
	
	@Test
	public void testTableDomPro() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("DOM_PRO");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(response.getRefGenericCodeLabelsType().size() > 1);
	}

	// REPIND-1811 : Add ReferenceTable for DWH on CONSENT
	
	@Test
	public void testTable_RefConsentType() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_CONSENT_TYPE");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(20, response.getRefGenericCodeLabelsType().size());
	}

	@Test
	public void testTable_RefConsentDataType() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_CONSENT_DATA_TYPE");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(28, response.getRefGenericCodeLabelsType().size());
	}

	@Test
	public void testTable_RefConsentTypeDataType() throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_CONSENT_TYPE_DATA_TYPE");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);

		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals(44, response.getRefGenericCodeLabelsType().size());
	}
}
