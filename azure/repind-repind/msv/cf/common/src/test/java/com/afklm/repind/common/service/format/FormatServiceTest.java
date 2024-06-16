package com.afklm.repind.common.service.format;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class FormatServiceTest {

    private FormatService formatService = new FormatService();

    @Test
    void isValidMail() {
        Assertions.assertTrue(formatService.isValidMail("valid@gmail.com"));
    }

    @Test
    void isValidMail_Invalid() {
        Assertions.assertFalse(formatService.isValidMail("novalidgmail.com"));
    }

    @Test
    void isValidMail_Empty() {
        Assertions.assertFalse(formatService.isValidMail(""));
    }

    @Test
    void isValidCIN() {
        Assertions.assertTrue(formatService.isValidCIN("0000123456"));
        Assertions.assertTrue(formatService.isValidCIN("000000123456"));
    }

    @Test
    void isValidCIN_InvalidSize() {
        Assertions.assertFalse(formatService.isValidCIN("22222222323223253456"));
        Assertions.assertFalse(formatService.isValidCIN("53456"));
    }

    @Test
    void isValidCIN_Empty() {
        Assertions.assertFalse(formatService.isValidCIN(""));
    }

    @Test
    void formatCIN() {
        Assertions.assertEquals("000000123456", formatService.formatCIN("123456"));
        Assertions.assertEquals("999924800220", formatService.formatCIN("999924800220"));
    }

    @Test
    void normalizeEmail() {
        Assertions.assertEquals("test@gmail.com", formatService.normalizeEmail("TesT@gmail.com"));
        Assertions.assertEquals("test@gmail.com", formatService.normalizeEmail("    TesT@gmail.com    "));
        Assertions.assertNull(formatService.normalizeEmail(null));
    }
}
