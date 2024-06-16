package com.airfrance.batch.common.utils;

import com.airfrance.ref.exception.telecom.InvalidationTelecomException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
public class CheckFileFormatInvSMSTest {

    private final CheckFileFormatInvSMS checkFileFormatInvSMS = new CheckFileFormatInvSMS();
    @Test
    public void isActionValid() {
        assertTrue(checkFileFormatInvSMS.isActionValid("I"));
    }

    @Test
    public void isCommunicationReturnCodeValid() {
        assertFalse(checkFileFormatInvSMS.isCommunicationReturnCodeValid("example"));
    }

    @Test
    public void isHeaderValid() {
        assertTrue(checkFileFormatInvSMS.isHeaderValid("CRMP"));
        assertFalse(checkFileFormatInvSMS.isHeaderValid("NOT_CRMP"));
    }

    @Test
    public void isAccountNumberValid() {
        assertTrue(checkFileFormatInvSMS.isAccountNumberValid("123456AB"));
        assertFalse(checkFileFormatInvSMS.isAccountNumberValid("123456ABCD"));
        assertFalse(checkFileFormatInvSMS.isAccountNumberValid("1234568AB"));
    }

    @Test
    public void isCommunicationReturnCodeCorrect() throws InvalidationTelecomException {
        assertTrue(checkFileFormatInvSMS.isCommunicationReturnCodeCorrect("R3002000"));
        assertThrows(Exception.class, () -> checkFileFormatInvSMS.isCommunicationReturnCodeCorrect("F3002000"));
    }
    @Test
    public void isHeaderCRMPUSH() {
        assertTrue(checkFileFormatInvSMS.isHeaderCRMPUSH("CRMP"));
        assertFalse(checkFileFormatInvSMS.isHeaderCRMPUSH("CRMP_NOT"));
    }
    @Test
    public void isHeaderROC() {
        assertTrue(checkFileFormatInvSMS.isHeaderROC("ROC"));
        assertFalse(checkFileFormatInvSMS.isHeaderROC("ROC_NOT"));
    }
    @Test
    public void isPhoneNumberValid() {
        assertTrue(checkFileFormatInvSMS.isPhoneNumberValid("1234556778"));
        assertFalse(checkFileFormatInvSMS.isPhoneNumberValid("012345567789012345567789012345567789012345567789012345567789012345567789012345567789012345567789012345567789012345567789"));
    }
    @Test
    public void isClientNumberValid() {
        assertTrue(checkFileFormatInvSMS.isClientNumberValid("400754487986"));
        assertFalse(checkFileFormatInvSMS.isClientNumberValid("4007544879866219494"));
    }
    @Test
    public void isContactValid() {
        assertTrue(checkFileFormatInvSMS.isContactValid("E"));
        assertFalse(checkFileFormatInvSMS.isContactValid("I"));
    }

    @Test
    public void setErrorCode() {
        checkFileFormatInvSMS.setInvalErrorCodeAppNb(0);
        assertEquals(0,checkFileFormatInvSMS.getInvalErrorCodeAppNb());
        checkFileFormatInvSMS.setInvalErrorCodeEmptyNb(1);
        assertEquals(1,checkFileFormatInvSMS.getInvalErrorCodeEmptyNb());
        checkFileFormatInvSMS.setInvalErrorCodeTooLongNb(2);
        assertEquals(2,checkFileFormatInvSMS.getInvalErrorCodeTooLongNb());
        checkFileFormatInvSMS.setInvalErrorCodeTooShortNb(3);
        assertEquals(3,checkFileFormatInvSMS.getInvalErrorCodeTooShortNb());
    }
}
