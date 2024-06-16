package com.airfrance.batch.common.utils;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class CheckFileFormatInvEmailTest {

    private final CheckFileFormatInvEmail checkFileFormatInvEmail = new CheckFileFormatInvEmail();

    @Test
    public void isActionValid() {
        assertTrue(checkFileFormatInvEmail.isActionValid("U"));
        assertFalse(checkFileFormatInvEmail.isActionValid("NOT_U"));
    }

    @Test
    public void isContactValid() {
        assertTrue(checkFileFormatInvEmail.isContactValid("E"));
        assertFalse(checkFileFormatInvEmail.isContactValid("I"));
    }

    @Test
    public void isClientNumberValid() {
        assertTrue(checkFileFormatInvEmail.isClientNumberValid("400754487986"));
        assertFalse(checkFileFormatInvEmail.isClientNumberValid("4007544879866219494"));
    }

    @Test
    public void isAccountNumberValid() {
        assertTrue(checkFileFormatInvEmail.isAccountNumberValid("123456AB"));
        assertFalse(checkFileFormatInvEmail.isAccountNumberValid("123456ABCD"));
        assertFalse(checkFileFormatInvEmail.isAccountNumberValid("1234568AB"));
    }

    @Test
    public void isMarketValid() {
        assertTrue(checkFileFormatInvEmail.isMarketValid("400754487986"));
        assertFalse(checkFileFormatInvEmail.isMarketValid("4007544879866219494"));
    }

    @Test
    public void isHeaderValid() {
        assertTrue(checkFileFormatInvEmail.isHeaderValid("CRMPUSH"));
        assertFalse(checkFileFormatInvEmail.isHeaderValid("NOT_CRMPUSH"));
    }

    @Test
    public void isHeaderFBSPUSH() {
        assertTrue(checkFileFormatInvEmail.isHeaderFBSPUSH("FBSPUSH"));
        assertFalse(checkFileFormatInvEmail.isHeaderFBSPUSH("NOT_FBSPUSH"));
    }

    @Test
    public void isHeaderCRMPUSH() {
        assertTrue(checkFileFormatInvEmail.isHeaderCRMPUSH("CRMPUSH"));
        assertFalse(checkFileFormatInvEmail.isHeaderCRMPUSH("NOT_CRMPUSH"));
    }

    @Test
    public void isCommunicationReturnCodeValid() {
        assertTrue(checkFileFormatInvEmail.isCommunicationReturnCodeValid("003"));
        assertFalse(checkFileFormatInvEmail.isCommunicationReturnCodeValid("003ABC"));
    }

    @Test
    public void isCommunicationReturnCodeCoherent() {
        assertTrue(checkFileFormatInvEmail.isCommunicationReturnCodeCoherent("003", "I"));
        assertFalse(checkFileFormatInvEmail.isCommunicationReturnCodeCoherent("003", "A"));
    }

    @Test
    public void isCommunication10() {
        assertTrue(checkFileFormatInvEmail.isCommunication10("010"));
        assertFalse(checkFileFormatInvEmail.isCommunication10("009"));
    }

    @Test
    public void isEmailCoherentWithContact() {
        assertTrue(checkFileFormatInvEmail.isEmailCoherentWithContact("email@toto.com", "E"));
        assertFalse(checkFileFormatInvEmail.isEmailCoherentWithContact("email@toto.com", "E"));
    }

    @Test
    public void isCommunication8or10() {
        assertTrue(checkFileFormatInvEmail.isCommunication8or10("008"));
        assertTrue(checkFileFormatInvEmail.isCommunication8or10("010"));
        assertFalse(checkFileFormatInvEmail.isCommunication8or10("009"));
    }

    @Test
    public void isCodeLangueValid() {
        assertTrue(checkFileFormatInvEmail.isCodeLangueValid("FR"));
        assertTrue(checkFileFormatInvEmail.isCodeLangueValid("AE"));
        assertFalse(checkFileFormatInvEmail.isCodeLangueValid("ABC"));
    }
}
