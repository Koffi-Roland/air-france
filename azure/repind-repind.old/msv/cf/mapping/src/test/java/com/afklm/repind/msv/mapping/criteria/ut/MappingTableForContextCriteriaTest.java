package com.afklm.repind.msv.mapping.criteria.ut;

import com.afklm.repind.msv.mapping.criteria.MappingTableForContextCriteria;
import com.afklm.repind.msv.mapping.services.MappingLanguageService;
import com.afklm.repind.msv.mapping.services.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class MappingTableForContextCriteriaTest extends MappingLanguageService {
	
    /** logger */
    private static final Log log = LogFactory.getLog(MappingTableForContextCriteriaTest.class);

	@ParameterizedTest
	@ValueSource(strings = {"TOOMANYCHARINTHIS", "", "@-/"})
	void MappingTableForContextCriteriaTest_Context_PreconditionFailed() {

		log.info("START...");

		try {
			new MappingTableForContextCriteria("TOOMANYCHARINTHIS");
		} catch (ServiceException e) {
			Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
		}
		log.info("STOP.");
	}
}