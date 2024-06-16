package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.reference.RefComPrefDTO;
import com.airfrance.repind.dto.reference.RefGenericCodeLabelsTypeDTO;
import com.airfrance.repind.util.service.PaginationResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ReferenceDSTest {

	@Autowired
	private ReferenceDS referenceDS;

	@Test(expected = Test.None.class /* no exception expected */)
	public void testProvideComPrefWithPagination() throws JrafDomainException {
		
		PaginationResult<RefComPrefDTO> result = referenceDS.provideRefComPrefWithPagination(null, null, null, 5, 100);
		System.out.println("Fin");
	}

	@Test
	public void testProvideGenenericSimpleQuery_PAYS() throws JrafDomainException {
		List<RefGenericCodeLabelsTypeDTO> result = referenceDS.provideGenericSimpleQuery("PAYS");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(258, result.size());
	}
	
	@Test
	public void testProvideGenenericSimpleQuery_LANGUES() throws JrafDomainException {
		List<RefGenericCodeLabelsTypeDTO> result = referenceDS.provideGenericSimpleQuery("LANGUES");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(187, result.size());
	}

	@Test
	public void testProvideGenenericSimpleQuery_LANGUES_Lower() throws JrafDomainException {
		List<RefGenericCodeLabelsTypeDTO> result = referenceDS.provideGenericSimpleQuery("langues");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(187, result.size());
	}
	
	@Test
	public void testProvideGenenericSimpleQuery_REF_ORIGINE() throws JrafDomainException {
		List<RefGenericCodeLabelsTypeDTO> result = referenceDS.provideGenericSimpleQuery("REF_ORIGINE");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(189, result.size());
	}

	@Test
	public void testProvideGenenericSimpleQuery_REF_ORIGINE_Lower() throws JrafDomainException {
		List<RefGenericCodeLabelsTypeDTO> result = referenceDS.provideGenericSimpleQuery("REF_origine");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(189, result.size());
	}

	@Test
	public void testProvideGenenericSimpleQuery_REFIncorrect() throws JrafDomainException {
		
		try {
			referenceDS.provideGenericSimpleQuery("REF_0RIGINE");

		} catch (JrafDomainException ex) {
			
			Assert.assertNotNull(ex);
			Assert.assertEquals("SQL Injection detected...", ex.getMessage());
		}		
	}
 
	@Test
	public void testProvideGenenericSimpleQuery_REFIncorrect_Lower() throws JrafDomainException {
		
		try {
			referenceDS.provideGenericSimpleQuery("REF_0rigine");

		} catch (JrafDomainException ex) {
			
			Assert.assertNotNull(ex);
			Assert.assertEquals("SQL Injection detected...", ex.getMessage());
		}		
	}
}
