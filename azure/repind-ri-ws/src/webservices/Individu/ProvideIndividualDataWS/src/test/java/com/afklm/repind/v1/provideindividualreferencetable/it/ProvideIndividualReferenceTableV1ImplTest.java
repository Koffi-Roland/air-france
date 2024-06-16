package com.afklm.repind.v1.provideindividualreferencetable.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.provideindividualreferencetable.v1.ProvideIndividualReferenceTableV1Impl;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001588.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001588.v1.commontype.Requestor;
import com.afklm.soa.stubs.w001588.v1.data.ProvideIndividualReferenceTableRequest;
import com.afklm.soa.stubs.w001588.v1.data.ProvideIndividualReferenceTableResponse;
import com.afklm.soa.stubs.w001588.v1.response.BusinessErrorCodeEnum;
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

import java.util.ArrayList;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProvideIndividualReferenceTableV1ImplTest {

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T730890";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "T730890";
	private static final String APP_CODE = "B2C";
	
	private static final String REF_COMPREF_DOMAIN="REF_COMPREF_DOMAIN";
	private static final String REF_COMPREF_TYPE="REF_COMPREF_TYPE";
	private static final String REF_COMPREF_GTYPE="REF_COMPREF_GTYPE";
	private static final String REF_COMPREF="REF_COMPREF";
	private static final String REF_COMPREF_COUNTRY_MARKET="REF_COMPREF_COUNTRY_MARKET";
	private static final String REF_COMPREF_MEDIA="REF_COMPREF_MEDIA";
	
	@Autowired
	@Qualifier("passenger_ProvideIndividualReferenceTable-v01Bean")
	private ProvideIndividualReferenceTableV1Impl provideIndividualReferenceTableV1Impl;
	
	private Requestor initRequestor() {
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setMatricule(MATRICULE);
		requestor.setApplicationCode(APP_CODE);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		
		return requestor;
	}

	@Test
	public void ProvideIndividualReferenceTableV1Domain() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF_DOMAIN);
		
		ArrayList<String> listDomain = new ArrayList<String>();
		listDomain.add("S");
		listDomain.add("F");
		listDomain.add("P");
		listDomain.add("U");
		
		ProvideIndividualReferenceTableResponse response=provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getRefCompref().isEmpty());
		Assert.assertTrue(response.getRefComprefMarketCountry().isEmpty());

		// Check if generic output is fully filled
		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(listDomain.contains(response.getRefGenericCodeLabelsType().get(0).getCode()));
		Assert.assertTrue(listDomain.contains(response.getRefGenericCodeLabelsType().get(1).getCode()));
		Assert.assertTrue(listDomain.contains(response.getRefGenericCodeLabelsType().get(2).getCode()));
		Assert.assertTrue(listDomain.contains(response.getRefGenericCodeLabelsType().get(3).getCode()));		
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1UltimateDomainExist() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF_DOMAIN);
		request.setDomain("U");
		
		ArrayList<String> listDomain = new ArrayList<String>();
		listDomain.add("S");
		listDomain.add("F");
		listDomain.add("P");
		listDomain.add("U");
		
		ProvideIndividualReferenceTableResponse response=provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		Assert.assertTrue(listDomain.contains(response.getRefGenericCodeLabelsType().get(0).getCode()));
		Assert.assertTrue(listDomain.contains(response.getRefGenericCodeLabelsType().get(1).getCode()));
		Assert.assertTrue(listDomain.contains(response.getRefGenericCodeLabelsType().get(2).getCode()));
		Assert.assertTrue(listDomain.contains(response.getRefGenericCodeLabelsType().get(3).getCode()));

	}
	
	@Test
	public void ProvideIndividualReferenceTableV1Type() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF_TYPE);
		
		ArrayList<String> listType = new ArrayList<String>();
		listType.add("AF");
		listType.add("HOP");
		listType.add("KL");
		listType.add("KL_IFLY");
		listType.add("FB_ESS");
		listType.add("FB_PROG");
		listType.add("FB_NEWS");
		listType.add("FB_CC");
		listType.add("FB_PART");
		listType.add("KLFDMAG");
		listType.add("KQ");
		listType.add("SB");
		listType.add("RO");
		listType.add("TO");
		listType.add("TEL");
		listType.add("UL_PS");	// Nouveaute car TU failed, je le rajoute
		
		ProvideIndividualReferenceTableResponse response=provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getRefGenericCodeLabelsType());		
		Assert.assertTrue(response.getRefCompref().isEmpty());
		Assert.assertTrue(response.getRefComprefMarketCountry().isEmpty());
		
		// Check if generic output is fully filled
		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(0).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(1).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(2).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(3).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(4).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(5).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(6).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(7).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(8).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(9).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(10).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(11).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(12).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(13).getCode()));
		Assert.assertTrue(listType.contains(response.getRefGenericCodeLabelsType().get(14).getCode()));
		
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1GType() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF_GTYPE);
		
		ArrayList<String> listGType = new ArrayList<String>();
		listGType.add("N");
		listGType.add("M");
		listGType.add("C");
		listGType.add("S");	// Nouveaute , TU failed... Du coup, je le rajoute...
		
		ProvideIndividualReferenceTableResponse response=provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(response.getRefCompref().isEmpty());
		Assert.assertTrue(response.getRefComprefMarketCountry().isEmpty());
		
		// Check if generic output is fully filled		
		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(listGType.contains(response.getRefGenericCodeLabelsType().get(0).getCode()));
		Assert.assertTrue(listGType.contains(response.getRefGenericCodeLabelsType().get(1).getCode()));
		Assert.assertTrue(listGType.contains(response.getRefGenericCodeLabelsType().get(2).getCode()));
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1Media() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF_MEDIA);
		
		ProvideIndividualReferenceTableResponse response=provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertTrue(response.getRefCompref().isEmpty());
		Assert.assertTrue(response.getRefComprefMarketCountry().isEmpty());
		// Check if generic output is fully filled				
		Assert.assertNotNull(response.getRefGenericCodeLabelsType());
		Assert.assertEquals("E", response.getRefGenericCodeLabelsType().get(0).getCode());
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1CountryMarket() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF_COUNTRY_MARKET);
		request.setCountry("FR");
		
		ProvideIndividualReferenceTableResponse response=provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getRefComprefMarketCountry());
		Assert.assertTrue(response.getRefCompref().isEmpty());
		Assert.assertEquals(1, response.getRefComprefMarketCountry().size());
	}
	
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void ProvideIndividualReferenceTableV1ComPrefWithoutRestriction() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF);
		
		ProvideIndividualReferenceTableResponse response=provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getRefCompref());
		Assert.assertTrue(response.getRefComprefMarketCountry().isEmpty());
		Assert.assertTrue(response.getRefCompref().size()>500);
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1ComPrefWithRestriction() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF);
		request.setLanguage("FR");
		request.setMarket("FR");
		request.setDomain("F");
		ProvideIndividualReferenceTableResponse response=provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getRefCompref());
		Assert.assertTrue(response.getRefComprefMarketCountry().isEmpty());
		Assert.assertTrue(response.getRefCompref().size()>=1);
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1MarketJoker() throws BusinessErrorBlocBusinessException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF);
		request.setMarket("FR");
		request.setLanguage("FR");
		request.setDomain("U"); // REF_COMPREF U has market = '*' and defaultLanguage1 = '*'
		
		ProvideIndividualReferenceTableResponse response = provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getRefCompref());
		Assert.assertEquals("UL_PS", response.getRefCompref().get(0).getType());
		Assert.assertEquals("U", response.getRefCompref().get(0).getDomain());
		Assert.assertEquals("S", response.getRefCompref().get(0).getGroupeType());
		Assert.assertEquals("*", response.getRefCompref().get(0).getMarket());
		Assert.assertEquals("*", response.getRefCompref().get(0).getDefaultLanguage1());
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1TableNameInvalid() throws SystemException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName("AAAAA");

		try {
			ProvideIndividualReferenceTableResponse response = provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1TableMarketInvalid() throws SystemException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF);
		request.setMarket("AAAAA");

		try {
			ProvideIndividualReferenceTableResponse response = provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1LanguageInvalid() throws SystemException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF);
		request.setLanguage("AAAAA");

		try {
			ProvideIndividualReferenceTableResponse response = provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1DomainInvalid() throws SystemException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF);
		request.setDomain("AAAAA");

		try {
			ProvideIndividualReferenceTableResponse response = provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1MarketCountryInvalid() throws SystemException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF);
		request.setCountry("AAAAA");

		try {
			ProvideIndividualReferenceTableResponse response = provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1WithoutTableName() throws SystemException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());


		try {
			ProvideIndividualReferenceTableResponse response = provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_133, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}
	
	@Test
	public void ProvideIndividualReferenceTableV1ResponseEmpty() throws SystemException, BusinessErrorBlocBusinessException {
		ProvideIndividualReferenceTableRequest request = new ProvideIndividualReferenceTableRequest();
		
		request.setRequestor(initRequestor());
		request.setTableName(REF_COMPREF);
		request.setDomain("S");
		request.setMarket("ES");
		request.setLanguage("IT");
		ProvideIndividualReferenceTableResponse response = provideIndividualReferenceTableV1Impl.provideIndividualReferenceTable(request);

		Assert.assertTrue(!response.getRefCompref().isEmpty());					// We have add a PROMO ALERT 
		Assert.assertTrue(response.getRefComprefMarketCountry().isEmpty());
	}
}
