package com.afklm.repind.msv.doctor.role.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CheckerUtilsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static String ROLE_ID = "123456789";

    public void throwException(Class iException , String iMessage){
        thrown.expect(iException);
        thrown.expectMessage(iMessage);
    }

    @Test
    public void roleIdCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals(ROLE_ID , CheckerUtils.roleIdChecker(ROLE_ID));
    }

    @Test
    public void roleIdCheckerNullTest() throws BusinessException {
        throwException(BusinessException.class , "Role id is mandatory");
        CheckerUtils.roleIdChecker(null);
    }

    @Test
    public void roleIdCheckerEmptyTest() throws BusinessException {
        throwException(BusinessException.class , "Role id is mandatory");
        CheckerUtils.roleIdChecker("");
    }

    @Test
    public void ginCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals("012345678910" , CheckerUtils.ginChecker("012345678910"));
    }

    @Test
    public void ginCheckerNullTest() throws BusinessException {
        throwException(BusinessException.class , "Gin is mandatory");
        CheckerUtils.ginChecker(null);
    }

    @Test
    public void ginCheckerEmptyTest() throws BusinessException {
        throwException(BusinessException.class , "Gin is mandatory");
        CheckerUtils.ginChecker("");
    }

    @Test
    public void ginCheckerLengthTest() throws BusinessException {
        throwException(BusinessException.class , "Invalid value for the 'gin' parameter, length must be equal to 12");
        CheckerUtils.ginChecker("123456789102345");
    }


    @Test
    public void dateCheckerBadFormatTest() throws BusinessException {
        throwException(BusinessException.class , "Invalid value for the 'endDateRole' parameter, format must be equal to yyyy-MM-dd'T'HH:mm:ss'Z'");
        CheckerUtils.dateChecker("2020-:54:44Z");
    }

    @Test
    public void typeCheckerSuccessTest() throws BusinessException{
        Assert.assertEquals( "D" , CheckerUtils.typeChecker("D"));
        Assert.assertEquals( "D" , CheckerUtils.typeChecker("d"));
    }

    @Test
    public void typeCheckerNullTest() throws BusinessException{
        throwException(BusinessException.class , "Type is mandatory");
        CheckerUtils.typeChecker(null);
    }

    @Test
    public void typeCheckerEmptyTest() throws BusinessException{
        throwException(BusinessException.class , "Type is mandatory");
        CheckerUtils.typeChecker("");
    }

    @Test
    public void typeCheckerBadFormatTest() throws BusinessException{
        throwException(BusinessException.class , "Invalid value for the 'type' parameter, length must be equal to 1");
        CheckerUtils.typeChecker("DOC");
    }

    @Test
    public void doctorIdCheckerSuccessTest() throws BusinessException{
        Assert.assertEquals( "123456789452" , CheckerUtils.doctorIdChecker("123456789452"));
        Assert.assertEquals( "5698745632155411" , CheckerUtils.doctorIdChecker("5698745632155411"));
    }

    @Test
    public void doctorIdCheckerNullTest() throws BusinessException{
        throwException(BusinessException.class , "Doctor id is mandatory");
        CheckerUtils.doctorIdChecker(null);
    }

    @Test
    public void doctorIdCheckerEmptyTest() throws BusinessException{
        throwException(BusinessException.class , "Doctor id is mandatory");
        CheckerUtils.doctorIdChecker("");
    }

    @Test
    public void doctorIdCheckerBadFormatTest() throws BusinessException{
        throwException(BusinessException.class , "Invalid value for the 'doctorId' parameter, length must be equal to 20");
        CheckerUtils.doctorIdChecker("44444444444444444444444444444444444444444444");
    }

    @Test
    public void siteCheckerSuccessTest() throws BusinessException{
        Assert.assertEquals( "Test" , CheckerUtils.siteChecker("Test"));
    }

    @Test
    public void siteCheckerNullTest() throws BusinessException{
        throwException(BusinessException.class , "Site Creation is mandatory");
        CheckerUtils.siteChecker(null);
    }

    @Test
    public void siteCheckerEmptyTest() throws BusinessException{
        throwException(BusinessException.class , "Site Creation is mandatory");
        CheckerUtils.siteChecker("");
    }

    @Test
    public void signatureCheckerSuccessTest() throws BusinessException{
        Assert.assertEquals( "Test" , CheckerUtils.signatureChecker("Test"));
    }

    @Test
    public void signatureCheckerNullTest() throws BusinessException{
        throwException(BusinessException.class , "Signature Creation is mandatory");
        CheckerUtils.signatureChecker(null);
    }

    @Test
    public void signatureCheckerEmptyTest() throws BusinessException{
        throwException(BusinessException.class , "Signature Creation is mandatory");
        CheckerUtils.signatureChecker("");
    }

    @Test
    public void specialityCheckerSuccessTest() throws BusinessException{
        Assert.assertEquals( "TEST" , CheckerUtils.specialityChecker("Test"));
    }

    @Test
    public void specialityCheckerNullTest() throws BusinessException{
        throwException(BusinessException.class , "Speciality is mandatory");
        CheckerUtils.specialityChecker(null);
    }

    @Test
    public void specialityCheckerEmptyTest() throws BusinessException{
        throwException(BusinessException.class , "Speciality is mandatory");
        CheckerUtils.specialityChecker("");
    }

    @Test
    public void relationModelsCheckerNullTest() throws BusinessException{
        throwException(BusinessException.class , "Relation list is mandatory");
        CheckerUtils.relationModelsChecker(null);
    }


    @Test
    public void airLineCodeCheckerSuccessTest() throws BusinessException{
        Assert.assertEquals( "AF",CheckerUtils.airLineCodeChecker("AF"));
        Assert.assertEquals( "AF",CheckerUtils.airLineCodeChecker("af"));
        Assert.assertEquals( "KL",CheckerUtils.airLineCodeChecker("KL"));
        Assert.assertEquals( "KL",CheckerUtils.airLineCodeChecker("kl"));
    }

    @Test
    public void airLineCodeCheckerNullTest() throws BusinessException{
        throwException(BusinessException.class , "Air line code is mandatory");
        CheckerUtils.airLineCodeChecker(null);
    }

    @Test
    public void airLineCodeCheckerEmptyTest() throws BusinessException{
        throwException(BusinessException.class , "Air line code is mandatory");
        CheckerUtils.airLineCodeChecker("");
    }

    @Test
    public void airLineCodeCheckerBadFormatTest() throws BusinessException{
        throwException(BusinessException.class , "Invalid value for the 'airLineCode' parameter, value must be 'AF' or 'KL'");
        CheckerUtils.airLineCodeChecker("LL");
        CheckerUtils.airLineCodeChecker("dsds");
    }

    @Test
    public void doctorStatusCheckerSuccessTest() throws BusinessException{
        Assert.assertEquals( "V",CheckerUtils.doctorStatusChecker("V"));
    }

    @Test
    public void doctorStatusCheckerNullTest() throws BusinessException{
        throwException(BusinessException.class , "Doctor status is mandatory");
        CheckerUtils.doctorStatusChecker(null);
    }

    @Test
    public void doctorStatusCheckerEmptyTest() throws BusinessException{
        throwException(BusinessException.class , "Doctor status is mandatory");
        CheckerUtils.doctorStatusChecker("");
    }

    @Test
    public void doctorStatusCheckerBadFormatTest() throws BusinessException{
        throwException(BusinessException.class , "Invalid value for the 'doctorStatus' parameter, value must be 'V' (Valid)");
        CheckerUtils.doctorStatusChecker("VV");
    }

}
