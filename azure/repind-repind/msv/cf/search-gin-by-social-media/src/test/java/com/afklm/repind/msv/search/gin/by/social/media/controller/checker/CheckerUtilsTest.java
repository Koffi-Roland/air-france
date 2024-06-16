package com.afklm.repind.msv.search.gin.by.social.media.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class CheckerUtilsTest {

    private static String EXTERNAL_IDENTIFIER_ID = "06146c2e-a722-47c4-97af-dd02e9d1d8fa";
    private static String EXTERNAL_IDENTIFIER_TYPE = "PNM_ID";

    @Test
    void roleIdCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals(EXTERNAL_IDENTIFIER_ID , CheckerUtils.checkExternalIdentifierId(EXTERNAL_IDENTIFIER_ID));
        Assert.assertEquals(EXTERNAL_IDENTIFIER_TYPE , CheckerUtils.checkExternalIdentifierType(EXTERNAL_IDENTIFIER_TYPE));
    }

    @Test
    void roleIdentifierCheckerNullTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> CheckerUtils.checkExternalIdentifierId(null));
        assertEquals("Missing Request Parameter", exception.getMessage());
    }

    @Test
    void roleTypeCheckerNullTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> CheckerUtils.checkExternalIdentifierType(null));
        assertEquals("Missing Request Parameter", exception.getMessage());
    }

    @Test
    void roleIdentifierCheckerEmptyTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> CheckerUtils.checkExternalIdentifierId(""));
        assertEquals("Missing Request Parameter", exception.getMessage());
    }

    @Test
    void roleTypeCheckerEmptyTest()  {
        Throwable exception = assertThrows(BusinessException.class, () -> CheckerUtils.checkExternalIdentifierType(""));
        assertEquals("Missing Request Parameter", exception.getMessage());
    }

    @Test
    void roleIdentifierCheckerBadFormatTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> CheckerUtils.checkExternalIdentifierId("123456789012345YY67890123456789012345YY678901234567890123" +
                "45YY67890123456789012345YY67890123456789012345YY67890123456789012345YY678901234567890123" +
                "45YY67890123456789012345YY67890123456789012345YY67890123456789012345YY678901234567890123" +
                "45YY67890123456789012345YY67890123456789012345YY67890123456789012345YY678901234567890123" +
                "45YY67890123456789012345YY67890123456789012345YY67890123456789012345YY678901234567890123" +
                "45YY67890123456789012345YY67890123456789012345YY67890123456789012345YY678901234567890123" +
                "45YY67890123456789012345YY67890123456789012345YY67890123456789012345YY678901234567890123" +
                "45YY67890123456789012345YY67890123456789012345YY67890123456789012345YY678901234567890123" +
                "45YY67890123456789012345YY67890"));
        assertEquals("Invalid value for the 'External Identifier' parameter, external identifier must be valid", exception.getMessage());
    }

}
