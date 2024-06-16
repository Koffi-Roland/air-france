package com.afklm.repind.msv.doctor.attributes.controller.ut.checker;

import com.afklm.repind.msv.doctor.attributes.controller.checker.CheckerUtils;
import com.afklm.repind.msv.doctor.attributes.model.AirLineCode;
import com.afklm.repind.msv.doctor.attributes.model.RelationModel;
import com.afklm.repind.msv.doctor.attributes.model.error.ErrorMessage;
import com.afklm.repind.msv.doctor.attributes.utils.exception.BusinessException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class CheckerUtilsTest {
    public static final String DATETIME_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private static final String ROLE_ID = "123456789";
    private static final String GIN = "123456789000";

    public void throwException(Class iException, String iMessage) {
        thrown.expect(iException);
        thrown.expectMessage(iMessage);
    }

    @Test
    public void ginCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals(GIN, CheckerUtils.ginChecker(GIN));
    }

    @Test
    public void ginCheckerNullTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_001.getDescription());
        CheckerUtils.ginChecker(null);
    }

    @Test
    public void ginCheckerEmptyTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_001.getDescription());
        CheckerUtils.ginChecker("");
    }

    @Test
    public void ginCheckerBadFormatTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_412_001.getDescription());
        CheckerUtils.ginChecker("123456789123456789");
    }


    @Test
    public void roleIdCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals(ROLE_ID, CheckerUtils.roleIdChecker(ROLE_ID));
    }

    @Test
    public void roleIdCheckerNullTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_006.getDescription());
        CheckerUtils.roleIdChecker(null);
    }

    @Test
    public void roleIdCheckerEmptyTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_006.getDescription());
        CheckerUtils.roleIdChecker("");
    }

    @Test
    public void EndDateRoleDateCheckerSuccessTest() throws BusinessException, ParseException {
        Assert.assertEquals(new SimpleDateFormat(DATETIME_FORMATTER).parse("2020-01-01T02:54:44Z"), CheckerUtils.diplomaDateChecker("2020-01-01T02:54:44Z"));
    }

    @Test
    public void EndDateRoleDateCheckerNullTest() throws BusinessException {
        Assert.assertNull(null, CheckerUtils.diplomaDateChecker(null));
    }


    @Test
    public void EndDateRoleDateCheckerBadFormatTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_412_003.getDescription());
        CheckerUtils.diplomaDateChecker("2020-:54:44Z");
    }

    @Test
    public void signatureDateCheckerSuccessTest() throws BusinessException, ParseException {
        Assert.assertEquals(new SimpleDateFormat(DATETIME_FORMATTER).parse("2020-01-01T02:54:44Z"), CheckerUtils.signatureDateChecker("2020-01-01T02:54:44Z"));
    }


    @Test
    public void signatureDateCheckerBadFormatTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_412_010.getDescription());
        CheckerUtils.signatureDateChecker("2020-:54:44Z");
    }

    @Test
    public void typeCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals("D", CheckerUtils.typeChecker("D"));
        Assert.assertEquals("D", CheckerUtils.typeChecker("d"));
    }

    @Test
    public void typeCheckerNullTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_002.getDescription());
        CheckerUtils.typeChecker(null);
    }

    @Test
    public void typeCheckerEmptyTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_002.getDescription());
        CheckerUtils.typeChecker("");
    }

    @Test
    public void typeCheckerBadFormatTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_412_002.getDescription());
        CheckerUtils.typeChecker("DOC");
    }

    @Test
    public void doctorIdCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals("123456789452", CheckerUtils.doctorIdChecker("123456789452"));
        Assert.assertEquals("5698745632155411", CheckerUtils.doctorIdChecker("5698745632155411"));
    }

    @Test
    public void doctorIdCheckerNullTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_007.getDescription());
        CheckerUtils.doctorIdChecker(null);
    }

    @Test
    public void doctorIdCheckerEmptyTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_007.getDescription());
        CheckerUtils.doctorIdChecker("");
    }

    @Test
    public void doctorIdCheckerBadFormatTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_412_004.getDescription());
        CheckerUtils.doctorIdChecker("44444444444444444444444444444444444444444444");
    }

    @Test
    public void airLineCodeCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals(AirLineCode.builder().value("AF".toUpperCase()).build(), CheckerUtils.airLineCodeChecker("AF"));
        Assert.assertEquals(AirLineCode.builder().value("af".toUpperCase()).build(), CheckerUtils.airLineCodeChecker("af"));
        Assert.assertEquals(AirLineCode.builder().value("KL".toUpperCase()).build(), CheckerUtils.airLineCodeChecker("KL"));
        Assert.assertEquals(AirLineCode.builder().value("kl".toUpperCase()).build(), CheckerUtils.airLineCodeChecker("kl"));
    }

    @Test
    public void airLineCodeCheckerNullTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_008.getDescription());
        CheckerUtils.airLineCodeChecker(null);
    }

    @Test
    public void airLineCodeCheckerEmptyTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_008.getDescription());
        CheckerUtils.airLineCodeChecker("");
    }

    @Test
    public void airLineCodeCheckerBadFormatTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_412_005.getDescription());
        CheckerUtils.airLineCodeChecker("LL");
        CheckerUtils.airLineCodeChecker("dsds");
    }

    @Test
    public void doctorStatusCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals("V", CheckerUtils.doctorStatusChecker("V"));
    }

    @Test
    public void doctorStatusCheckerNullTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_009.getDescription());
        CheckerUtils.doctorStatusChecker(null);
    }

    @Test
    public void doctorStatusCheckerEmptyTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_400_009.getDescription());
        CheckerUtils.doctorStatusChecker("");
    }

    @Test
    public void doctorStatusCheckerBadFormatTest() throws BusinessException {
        throwException(BusinessException.class, ErrorMessage.ERROR_MESSAGE_412_006.getDescription());
        CheckerUtils.doctorStatusChecker("VV");
    }

    @Test
    public void relationModelsCheckerEmptyTest() throws BusinessException {
        throwException(BusinessException.class, "Incorrect number of type values has been sent");
        List<RelationModel> relationModels = new ArrayList<>();
        CheckerUtils.relationModelsChecker(relationModels);
    }
}
