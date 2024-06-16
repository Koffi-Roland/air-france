package com.afklm.rigui.services.helper;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdhocValidatorHelperTest {

    AdhocValidatorHelper adhocValidatorHelper = new AdhocValidatorHelper();
    @Test
    void isEmailValid() {
        assertTrue(adhocValidatorHelper.isEmailValid("toto@gmail.com"));
        assertTrue(adhocValidatorHelper.isEmailValid("a@g.com"));
        assertFalse(adhocValidatorHelper.isEmailValid("totogmail.com"));
        assertFalse(adhocValidatorHelper.isEmailValid("afa@@t.com"));
        assertFalse(adhocValidatorHelper.isEmailValid("toto@@gmail.com"));
        assertFalse(adhocValidatorHelper.isEmailValid("toto012345678901234567890123456789012345678901234567890123456789@gmail.com"));
        assertFalse(adhocValidatorHelper.isEmailValid("a@g.f"));
    }

    @Test
    void isGINValid() {
        assertTrue(adhocValidatorHelper.isGINValid("123456789012"));
        assertFalse(adhocValidatorHelper.isGINValid("12a456789012"));
        assertFalse(adhocValidatorHelper.isGINValid("23456789012"));
        assertFalse(adhocValidatorHelper.isGINValid("1234567890123"));
    }

    @Test
    void isCINValid() {
        assertTrue(adhocValidatorHelper.isCINValid("1234567890"));
        assertTrue(adhocValidatorHelper.isCINValid("001234567890"));
        assertFalse(adhocValidatorHelper.isCINValid("ab1234567890"));
        assertFalse(adhocValidatorHelper.isCINValid("12345"));
        assertFalse(adhocValidatorHelper.isCINValid("1234567890123456789"));
    }

    @Test
    void isFirstnameValid() {
        assertTrue(adhocValidatorHelper.isFirstnameValid("toto"));
        assertTrue(adhocValidatorHelper.isFirstnameValid("Jean-Marie"));
        assertTrue(adhocValidatorHelper.isFirstnameValid("Toto"));
        assertFalse(adhocValidatorHelper.isFirstnameValid("abc平仮名"));
        assertFalse(adhocValidatorHelper.isFirstnameValid(""));
        assertFalse(adhocValidatorHelper.isFirstnameValid("   "));
        assertFalse(adhocValidatorHelper.isFirstnameValid("Totowithaverylongfirstnamethatisovertwentyfivecharacter"));
    }

    @Test
    void isSurnameValid() {
        assertTrue(adhocValidatorHelper.isSurnameValid("titi"));
        assertTrue(adhocValidatorHelper.isFirstnameValid("De-lor-des-bois"));
        assertTrue(adhocValidatorHelper.isSurnameValid("Tito"));
        assertFalse(adhocValidatorHelper.isSurnameValid("abc平仮名"));
        assertFalse(adhocValidatorHelper.isSurnameValid(""));
        assertFalse(adhocValidatorHelper.isSurnameValid("   "));
        assertFalse(adhocValidatorHelper.isSurnameValid("Titiowithaverylongfirstnamethatisovertwentyfivecharacter"));
    }

    @Test
    void isCivilityValid() {
        assertTrue(adhocValidatorHelper.isCivilityValid("MR"));
        assertTrue(adhocValidatorHelper.isCivilityValid("MS"));
        assertFalse(adhocValidatorHelper.isCivilityValid("ms"));
        assertFalse(adhocValidatorHelper.isCivilityValid("MRA"));
    }

    @Test
    void isBirthDateValid() {
        assertTrue(adhocValidatorHelper.isBirthDateValid("22-02-1990"));
        assertFalse(adhocValidatorHelper.isBirthDateValid("22/02/1990"));
        assertFalse(adhocValidatorHelper.isBirthDateValid("22021990"));
        assertFalse(adhocValidatorHelper.isBirthDateValid("34-02-1990"));
        assertFalse(adhocValidatorHelper.isBirthDateValid("22-25-1990")); // weird cornercase
    }

    @Test
    void isCountryCodeValid() {
        assertTrue(adhocValidatorHelper.isCountryCodeValid("FR"));
        assertFalse(adhocValidatorHelper.isCountryCodeValid("F"));
        assertFalse(adhocValidatorHelper.isCountryCodeValid("FRA"));
        assertFalse(adhocValidatorHelper.isCountryCodeValid("fr"));
        assertFalse(adhocValidatorHelper.isCountryCodeValid("fra"));
        assertFalse(adhocValidatorHelper.isCountryCodeValid("f名"));
        assertFalse(adhocValidatorHelper.isCountryCodeValid("f"));
        assertFalse(adhocValidatorHelper.isCountryCodeValid("12")); // OK ?
    }

    @Test
    void isLanguageCodeValid() {
        assertTrue(adhocValidatorHelper.isLanguageCodeValid("FR"));
        assertFalse(adhocValidatorHelper.isLanguageCodeValid("fr"));
        assertFalse(adhocValidatorHelper.isLanguageCodeValid("fra"));
        assertFalse(adhocValidatorHelper.isLanguageCodeValid("FRA"));
        assertFalse(adhocValidatorHelper.isLanguageCodeValid("f名"));
        assertFalse(adhocValidatorHelper.isLanguageCodeValid("f"));
        assertFalse(adhocValidatorHelper.isLanguageCodeValid("12"));
    }

    @Test
    void isSubscriptionTypeValid() {
        assertTrue(adhocValidatorHelper.isSubscriptionTypeValid("KL", "KL"));
        assertTrue(adhocValidatorHelper.isSubscriptionTypeValid("KL_PART", "KL"));
        assertTrue(adhocValidatorHelper.isSubscriptionTypeValid("AF", "AF"));
        assertFalse(adhocValidatorHelper.isSubscriptionTypeValid("AF", "KL"));
        assertFalse(adhocValidatorHelper.isSubscriptionTypeValid("KL", "TI"));
    }

    @Test
    void isDomainValid() {
        assertTrue(adhocValidatorHelper.isDomainValid("S"));
        assertFalse(adhocValidatorHelper.isDomainValid("s"));
        assertFalse(adhocValidatorHelper.isDomainValid("1"));
    }

    @Test
    void isGroupTypeValid() {
        assertTrue(adhocValidatorHelper.isGroupTypeValid("N"));
        assertFalse(adhocValidatorHelper.isDomainValid("n"));
        assertFalse(adhocValidatorHelper.isDomainValid("1"));
    }

    @Test
    void isStatusValid() {
        assertTrue(adhocValidatorHelper.isStatusValid("Y"));
        assertTrue(adhocValidatorHelper.isStatusValid("N"));
        assertFalse(adhocValidatorHelper.isStatusValid("A"));
        assertFalse(adhocValidatorHelper.isStatusValid("y"));
        assertFalse(adhocValidatorHelper.isStatusValid("1"));
    }

    @Test
    void isSourceValid() {
        assertTrue(adhocValidatorHelper.isSourceValid("abc"));
        assertTrue(adhocValidatorHelper.isSourceValid("1234"));
        assertTrue(adhocValidatorHelper.isSourceValid("1234a"));
        assertTrue(adhocValidatorHelper.isSourceValid("a"));
        assertFalse(adhocValidatorHelper.isSourceValid("abcedghaaeazojsdazzaerzaeaezaezaea"));
    }

    @Test
    void isDateOfConsentValid() {
        assertTrue(adhocValidatorHelper.isBirthDateValid("22-02-1990"));
        assertFalse(adhocValidatorHelper.isBirthDateValid("22/02/1990"));
        assertFalse(adhocValidatorHelper.isBirthDateValid("22021990"));
        assertFalse(adhocValidatorHelper.isBirthDateValid("34-02-1990"));
        assertFalse(adhocValidatorHelper.isBirthDateValid("22-25-1990")); // weird cornercase
    }

    @Test
    void isPreferredDepartureAirport() {
        assertTrue(adhocValidatorHelper.isPreferredDepartureAirport("CDG"));
        assertFalse(adhocValidatorHelper.isPreferredDepartureAirport("cDG"));
        assertFalse(adhocValidatorHelper.isPreferredDepartureAirport("cd"));
        assertFalse(adhocValidatorHelper.isPreferredDepartureAirport("cdgg"));
        assertFalse(adhocValidatorHelper.isPreferredDepartureAirport("cd名"));
    }
}
