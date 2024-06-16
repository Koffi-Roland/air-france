package com.afklm.repind.provideindividualreferencetable.v2;


import com.afklm.config.WebTestConfig;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001588.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001588.v2.data.ProvideIndividualReferenceTableRequest;
import com.afklm.soa.stubs.w001588.v2.data.ProvideIndividualReferenceTableResponse;
import com.afklm.soa.stubs.w001588.v2.request.Pagination;
import com.afklm.soa.stubs.w001588.v2.siccommontype.Requestor;
import org.junit.Assert;
import org.junit.Before;
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
public class ProvideIndividualReferenceTableV2ImplTest extends ProvideIndividualReferenceTableV2Impl {
	
	private Requestor requestor;
	
	@Before
	public void toDo_Before() {
		requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("TEST RI");
	}
	
	@Test
	public void testTableRefPermissionsQuestion() throws BusinessErrorBlocBusinessException, SystemException {
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_PERMISSIONS_QUESTION");
		request.setRequestor(requestor);
		request.setPagination(pagination);
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
		
		Assert.assertEquals(1, response.getSubRefTable().size());
		Assert.assertEquals(1, response.getSubRefTable().get(0).getRow().size());
		Assert.assertTrue(response.getTotalResults() > 0);
	}
	
	@Test
	public void testTableRefPermissions() throws BusinessErrorBlocBusinessException, SystemException {
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_PERMISSIONS");
		request.setRequestor(requestor);
		request.setPagination(pagination);
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
		
		Assert.assertEquals(2, response.getSubRefTable().size());
		Assert.assertTrue(response.getSubRefTable().get(0).getRow().size() > 0);
		Assert.assertTrue(response.getSubRefTable().get(1).getRow().size() > 0);
		Assert.assertTrue(response.getTotalResults() > 0);
	}
	
	@Test
	public void testTableRefComPrefCountryMarket() throws BusinessErrorBlocBusinessException, SystemException {
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF_COUNTRY_MARKET");
		request.setRequestor(requestor);
		request.setPagination(pagination);
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
		
		Assert.assertEquals(1, response.getSubRefTable().size());
		Assert.assertEquals(1, response.getSubRefTable().get(0).getRow().size());
		Assert.assertTrue(response.getTotalResults() > 0);
	}
	
	@Test
	public void testTableRefComPrefCountryMarket_Empty() throws BusinessErrorBlocBusinessException, SystemException {
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF_COUNTRY_MARKET");
		request.setRequestor(requestor);
		request.setPagination(pagination);
		request.setCountry("M1");
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
		
		Assert.assertEquals(0, response.getSubRefTable().size());
		Assert.assertTrue(response.getTotalResults() == 0);
	}
	
	@Test
	public void testTableRefComPref() throws BusinessErrorBlocBusinessException, SystemException {
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF");
		request.setRequestor(requestor);
		request.setPagination(pagination);
		
		ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
		
		Assert.assertEquals(1, response.getSubRefTable().size());
		Assert.assertEquals(1, response.getSubRefTable().get(0).getRow().size());
		Assert.assertTrue(response.getTotalResults() > 0);
	}

	@Test
	public void provideIndividualReferenceTable_tableNotFoundTest() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("TABLE_NOT_EXIST");
		try {
			this.provideIndividualReferenceTable(request);
			Assert.assertFalse(true);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(e.getFaultInfo().getBusinessError().getErrorDetail().equals("Invalid parameter: Table not found"));
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_tableMissing() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("");
		try {
			this.provideIndividualReferenceTable(request);
			Assert.assertFalse(true);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(e.getFaultInfo().getBusinessError().getErrorDetail().equals("Missing parameter exception: Table name is mandatory"));
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_codeMissing() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF_DOMAIN");
		request.setDomain("");
		try {
			this.provideIndividualReferenceTable(request);
			Assert.assertFalse(true);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(e.getFaultInfo().getBusinessError().getErrorDetail().equals("Invalid parameter: Domain not found"));
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_codeNotFound() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF_DOMAIN");
		request.setDomain("CODE_NOT_FOUND");
		try {
			this.provideIndividualReferenceTable(request);
			Assert.assertFalse(true);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(e.getFaultInfo().getBusinessError().getErrorDetail().equals("Invalid parameter: Domain not found"));
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_languageNotValid() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF_DOMAIN");
		request.setDomain("S");
		request.setLanguage("LANGUAGE");
		try {
			this.provideIndividualReferenceTable(request);
			Assert.assertFalse(true);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(e.getFaultInfo().getBusinessError().getErrorDetail().equals("Invalid parameter: Language not found"));
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_codePaysNotValid() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF_DOMAIN");
		request.setDomain("S");
		request.setLanguage("EN");
		request.setCountry("XX");
		try {
			this.provideIndividualReferenceTable(request);
			Assert.assertFalse(true);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(e.getFaultInfo().getBusinessError().getErrorDetail().equals("Invalid parameter: Country not found"));
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_marketNotFound() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF_DOMAIN");
		request.setDomain("S");
		request.setLanguage("EN");
		request.setCountry("FR");
		request.setMarket("12314");
		try {
			this.provideIndividualReferenceTable(request);
			Assert.assertFalse(true);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(e.getFaultInfo().getBusinessError().getErrorDetail().equals("Invalid parameter: Market not found"));
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_REF_COMPREF_DOMAIN() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF_DOMAIN");
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		request.setPagination(pagination);
		
		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_REF_COMPREF_TYPE() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF_TYPE");
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		request.setPagination(pagination);
		
		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_REF_COMPREF_GTYPE() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF_GTYPE");
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		request.setPagination(pagination);
		
		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_REF_COMPREF_MEDIA() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_COMPREF_MEDIA");
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		request.setPagination(pagination);
		
		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void provideIndividualReferenceTable_PAYS() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("PAYS");
/*		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		request.setPagination(pagination);
*/		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			
			Assert.assertNotNull(response);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
			Assert.assertEquals("PAYS", response.getTableName());
			Assert.assertEquals(258, response.getTotalResults());
			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void provideIndividualReferenceTable_REF_PREFERENCE_KEY_TYPE() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_PREFERENCE_KEY_TYPE");
		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			
			Assert.assertNotNull(response);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
			Assert.assertEquals("REF_PREFERENCE_KEY_TYPE", response.getTableName());
			Assert.assertEquals(185, response.getTotalResults());
			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}	
	
	@Test
	public void provideIndividualReferenceTable_REF_HANDICAP_TYPE() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_HANDICAP_TYPE");
		
		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
			Assert.assertTrue(response.getSubRefTable().get(0).getRow().size() > 1);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_REF_HANDICAP_TYPE_Pagination() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_HANDICAP_TYPE");
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		request.setPagination(pagination);
		
		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
			Assert.assertTrue(response.getSubRefTable().get(0).getRow().size() == 1);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_REF_HANDICAP_CODE() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_HANDICAP_CODE");
		
		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
			Assert.assertTrue(response.getSubRefTable().get(0).getRow().size() > 1);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_REF_HANDICAP_CODE_Pagination() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_HANDICAP_CODE");
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		request.setPagination(pagination);
		
		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
			Assert.assertTrue(response.getSubRefTable().get(0).getRow().size() == 1);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_REF_HANDICAP_DATA_KEY() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_HANDICAP_DATA_KEY");
		
		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
			Assert.assertTrue(response.getSubRefTable().get(0).getRow().size() > 1);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void provideIndividualReferenceTable_REF_HANDICAP_DATA_KEY_Pagination() throws SystemException{
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		request.setTableName("REF_HANDICAP_DATA_KEY");
		Pagination pagination = new Pagination();
		pagination.setIndex(1);
		pagination.setMaxResults(1);
		request.setPagination(pagination);
		
		try {
			ProvideIndividualReferenceTableResponse response = this.provideIndividualReferenceTable(request);
			Assert.assertTrue(response.getSubRefTable().size() == 1);
			Assert.assertTrue(response.getSubRefTable().get(0).getRow().size() == 1);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue(false);
		}
	}
}
