package com.afklm.repind.msv.mapping.services.it;

import com.afklm.repind.msv.mapping.criteria.MappingLanguageCriteria;
import com.afklm.repind.msv.mapping.criteria.MappingTableForContextCriteria;
import com.afklm.repind.msv.mapping.model.MappingLanguageModel;
import com.afklm.repind.msv.mapping.services.MappingLanguageService;
import com.afklm.repind.msv.mapping.services.exception.ServiceException;
import com.afklm.repind.msv.mapping.wrapper.WrapperMappingTableForContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MappingLanguageServiceTest extends MappingLanguageService {

	/** logger */
	private static final Log log = LogFactory.getLog(MappingLanguageServiceTest.class);

	@Autowired
	private MappingLanguageService mappingLanguageService;

	@Test
	void MappingLanguageService_provideMappingFromLanguage_TestOkByNoneIso() throws ServiceException {
		log.info("START...");

		MappingLanguageCriteria mappingLanguageCriteria = new MappingLanguageCriteria("BR", "BR", "CSM", false);

		MappingLanguageModel response = mappingLanguageService.provideMappingFromLanguage(mappingLanguageCriteria);

		Assert.assertNotNull(response);

		Assert.assertNotNull(response.getCodeLanguageISO());
		Assert.assertNotNull(response.getCodeLanguageNoISO());
		Assert.assertNotNull(response.getContext());
		Assert.assertNotNull(response.getMarket());

		log.info("STOP.");
	}

	@Test
	void MappingLanguageService_provideMappingFromLanguage_TestNotFoundByNoneIso() throws ServiceException {
		log.info("START...");

		MappingLanguageCriteria mappingLanguageCriteria = new MappingLanguageCriteria("BR", "BR", "NOTFOUND", false);

		try {
			MappingLanguageModel response = mappingLanguageService.provideMappingFromLanguage(mappingLanguageCriteria);
		} catch (ServiceException e) {
			Assert.assertEquals( HttpStatus.NOT_FOUND, e.getStatus());
		}

		log.info("STOP.");
	}

	@Test
	void MappingLanguageService_provideMappingFromLanguage_TestOkByIso() throws ServiceException {
		log.info("START...");

		MappingLanguageCriteria mappingLanguageCriteria = new MappingLanguageCriteria("BR", "PT", "CSM", true);

		MappingLanguageModel response = mappingLanguageService.provideMappingFromLanguage(mappingLanguageCriteria);

		Assert.assertNotNull(response);

		Assert.assertNotNull(response.getCodeLanguageISO());
		Assert.assertNotNull(response.getCodeLanguageNoISO());
		Assert.assertNotNull(response.getContext());
		Assert.assertNotNull(response.getMarket());

		log.info("STOP.");
	}

	@Test
	void MappingLanguageService_provideMappingFromLanguage_TestNotFoundByIso() throws ServiceException {
		log.info("START...");

		MappingLanguageCriteria mappingLanguageCriteria = new MappingLanguageCriteria("BR", "BR", "NOTFOUND", true);

		try {
			MappingLanguageModel response = mappingLanguageService.provideMappingFromLanguage(mappingLanguageCriteria);
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		}

		log.info("STOP.");
	}


	@Test
	void MappingLanguageService_ProvideMappingTableByContext_CSMValue() throws ServiceException {

		log.info("START...");

		MappingTableForContextCriteria mappingTableForContextCriteria = new MappingTableForContextCriteria("CSM");

		List<WrapperMappingTableForContext> response = mappingLanguageService
				.provideMappingTableByContext(mappingTableForContextCriteria);

		Assert.assertNotNull(response);

		WrapperMappingTableForContext res = (WrapperMappingTableForContext) response.get(0);
		Assert.assertNotNull(res);

		Assert.assertNotNull(res.getContext());
		Assert.assertEquals("CSM", res.getContext());
		Assert.assertNotNull(res.getMappingLanguages());

		Assert.assertNotNull(res.getMappingLanguages().get(0).getCodeLanguageISO());
		Assert.assertNotNull(res.getMappingLanguages().get(0).getCodeLanguageNoISO());
		Assert.assertNotNull(res.getMappingLanguages().get(0).getMarket());

		log.info("STOP.");
	}

	@Test
	void MappingLanguageService_ProvideMappingTableByContext_NotFoundValue() throws ServiceException {

		log.info("START...");

		MappingTableForContextCriteria mappingTableForContextCriteria = new MappingTableForContextCriteria("NOTFOUND");
		try {
			List<WrapperMappingTableForContext> response = mappingLanguageService
					.provideMappingTableByContext(mappingTableForContextCriteria);
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		}

		log.info("STOP.");
	}

	@Test
	void MappingLanguageService_ProvideMappingTableByContext_ALLCONTEXTSValue() throws ServiceException {

		log.info("START...");

		MappingTableForContextCriteria mappingTableForContextCriteria = new MappingTableForContextCriteria(
				"ALLCONTEXTS");

		List<WrapperMappingTableForContext> response = mappingLanguageService
				.provideMappingTableByContext(mappingTableForContextCriteria);

		Assert.assertNotNull(response);

		WrapperMappingTableForContext res = (WrapperMappingTableForContext) response.get(0);
		Assert.assertNotNull(res);

		Assert.assertNotNull(res.getContext());
		Assert.assertNotNull(res.getMappingLanguages());

		Assert.assertNotNull(res.getMappingLanguages().get(0).getCodeLanguageISO());
		Assert.assertNotNull(res.getMappingLanguages().get(0).getCodeLanguageNoISO());
		Assert.assertNotNull(res.getMappingLanguages().get(0).getMarket());

		int nbResults = response.size();
		mappingTableForContextCriteria = new MappingTableForContextCriteria("CSM");
		response = mappingLanguageService.provideMappingTableByContext(mappingTableForContextCriteria);

		//The number must be at least equals or greater than CSM size
		Assert.assertTrue(nbResults >= response.size());

		log.info("STOP.");
	}
}