package com.afklm.repind.msv.mapping.criteria.ut;

import com.afklm.repind.msv.mapping.criteria.MappingLanguageCriteria;
import com.afklm.repind.msv.mapping.services.MappingLanguageService;
import com.afklm.repind.msv.mapping.services.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class MappingLanguageCriteriaTest extends MappingLanguageService {
	
    /** logger */
    private static final Log log = LogFactory.getLog(MappingLanguageCriteriaTest.class);

	@Test
	void MappingLanguageCriteriaTest_Language_Too_Many_Char() throws ServiceException {

		log.info("START...");

		try {
			new MappingLanguageCriteria("BR", "TOOMANYCHARINTHIS", "CSM", false);
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
		}
		log.info("STOP.");
	}

	@Test
	void MappingLanguageCriteriaTest_Language_Not_Enought_Char() throws ServiceException {

		log.info("START...");

		try {
			new MappingLanguageCriteria("BR", "", "CSM", false);
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
		}
		log.info("STOP.");
	}

	@Test
	void MappingLanguageCriteriaTest_Language_Not_Alphanum_Char() throws ServiceException {

		log.info("START...");

		try {
			new MappingLanguageCriteria("BR", "@-/", "CSM", false);
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
		}
		log.info("STOP.");
	}

	@Test
	void MappingLanguageCriteriaTest_Market_Too_Many_Char() throws ServiceException {

		log.info("START...");

		try {
			new MappingLanguageCriteria("TOOMANYCHARINTHIS", "BR", "CSM", false);
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
		}
		log.info("STOP.");
	}

	@Test
	void MappingLanguageCriteriaTest_Market_Not_Enought_Char() throws ServiceException {

		log.info("START...");

		try {
			new MappingLanguageCriteria("", "BR", "CSM", false);
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
		}
		log.info("STOP.");
	}

	@Test
	void MappingLanguageCriteriaTest_Market_Not_Alphanum_Char() throws ServiceException {

		log.info("START...");

		try {
			new MappingLanguageCriteria("@-/", "BR", "CSM", false);
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
		}
		log.info("STOP.");
	}

	@Test
	void MappingLanguageCriteriaTest_Context_Too_Many_Char() throws ServiceException {

		log.info("START...");

		try {
			new MappingLanguageCriteria("BR", "BR", "TOOMANYCHARINTHIS", false);
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
		}
		log.info("STOP.");
	}

	@Test
	void MappingLanguageCriteriaTest_Context_Not_Enought_Char() throws ServiceException {

		log.info("START...");

		try {
			new MappingLanguageCriteria("BR", "BR", "", false);
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
		}
		log.info("STOP.");
	}

	@Test
	void MappingLanguageCriteriaTest_Context_Not_Alphanum_Char() throws ServiceException {

		log.info("START...");

		try {
			new MappingLanguageCriteria("BR", "BR", "@-/", false);
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
		}
		log.info("STOP.");
	}
}