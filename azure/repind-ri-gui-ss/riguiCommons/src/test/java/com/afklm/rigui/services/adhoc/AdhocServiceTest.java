package com.afklm.rigui.services.adhoc;

import com.afklm.rigui.model.individual.ModelAdhoc;
import com.afklm.rigui.model.individual.requests.ModelAdhocRequest;
import com.afklm.rigui.services.helper.AdhocValidatorHelper;
import com.afklm.rigui.wrapper.adhoc.WrapperAdhoc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class AdhocServiceTest {

    private final AdhocService adhocService = new AdhocService(new AdhocValidatorHelper());
    @Test
    void validateValid() {
        ModelAdhocRequest request = new ModelAdhocRequest();
        ModelAdhoc adhoc = new ModelAdhoc();
        // valid item
        adhoc.setId(0);
        adhoc.setEmailAddress("toto@gmail.com");
        adhoc.setGin("123456789012");
        adhoc.setCin("1234567890");
        adhoc.setFirstname("toto");
        adhoc.setSurname("titi");
        adhoc.setCivility("MR");
        adhoc.setBirthdate("22-02-1990");
        adhoc.setCountryCode("FR");
        adhoc.setLanguageCode("FR");
        adhoc.setSubscriptionType("AF");
        adhoc.setDomain("S");
        adhoc.setGroupType("N");
        adhoc.setStatus("Y");
        adhoc.setSource("source");
        adhoc.setDateOfConsent("22-02-1990");
        adhoc.setPreferredDepartureAirport("CDG");

        request.setList(List.of(adhoc));
        WrapperAdhoc result = adhocService.validate(request, "AF");
        // Should get no result
        assertEquals(0, result.getResult().size());
    }
    @Test
    void validateInvalid() {
        ModelAdhocRequest request = new ModelAdhocRequest();
        ModelAdhoc adhoc = new ModelAdhoc();
        // invalid item
        adhoc.setId(0);
        adhoc.setEmailAddress("totogmail.com");
        adhoc.setGin("1234ab6789012");
        adhoc.setCin("67890");
        adhoc.setFirstname("toto");
        adhoc.setSurname("titi");
        adhoc.setCivility("MR");
        adhoc.setBirthdate("22-02-1990");
        adhoc.setCountryCode("FR");
        adhoc.setLanguageCode("FR");
        adhoc.setSubscriptionType("AF");
        adhoc.setDomain("S");
        adhoc.setGroupType("N");
        adhoc.setStatus("Y");
        adhoc.setSource("source");
        adhoc.setDateOfConsent("22-02-1990");
        adhoc.setPreferredDepartureAirport("CDG");

        request.setList(List.of(adhoc));
        WrapperAdhoc result = adhocService.validate(request, "AF");
        // Should get a result
        assertEquals(1, result.getResult().size());
        assertFalse(result.getResult().get(0).getErrors().isEmpty());
        assertEquals(3, result.getResult().get(0).getErrors().size());
        assertEquals("EMAIL_ADDRESS", result.getResult().get(0).getErrors().get(0));
        assertEquals("GIN", result.getResult().get(0).getErrors().get(1));
        assertEquals("CIN", result.getResult().get(0).getErrors().get(2));
        // Assert structure object
        assertEquals(adhoc.getId(),  result.getResult().get(0).getId());
        assertEquals(adhoc.getEmailAddress(),  result.getResult().get(0).getEmailAddress());
        assertEquals(adhoc.getGin(),  result.getResult().get(0).getGin());
        assertEquals(adhoc.getCin(),  result.getResult().get(0).getCin());
        assertEquals(adhoc.getFirstname(),  result.getResult().get(0).getFirstname());
        assertEquals(adhoc.getSurname(),  result.getResult().get(0).getSurname());
        assertEquals(adhoc.getCivility(),  result.getResult().get(0).getCivility());
        assertEquals(adhoc.getBirthdate(),  result.getResult().get(0).getBirthdate());
        assertEquals(adhoc.getCountryCode(),  result.getResult().get(0).getCountryCode());
        assertEquals(adhoc.getLanguageCode(),  result.getResult().get(0).getLanguageCode());
        assertEquals(adhoc.getSubscriptionType(),  result.getResult().get(0).getSubscriptionType());
        assertEquals(adhoc.getDomain(),  result.getResult().get(0).getDomain());
        assertEquals(adhoc.getGroupType(),  result.getResult().get(0).getGroupType());
        assertEquals(adhoc.getStatus(),  result.getResult().get(0).getStatus());
        assertEquals(adhoc.getSource(),  result.getResult().get(0).getSource());
        assertEquals(adhoc.getDateOfConsent(),  result.getResult().get(0).getDateOfConsent());
        assertEquals(adhoc.getPreferredDepartureAirport(),  result.getResult().get(0).getPreferredDepartureAirport());
    }

    @Test
    void validateInvalidAndInvalid() {
        ModelAdhocRequest request = new ModelAdhocRequest();

        ModelAdhoc valid = new ModelAdhoc();
        // valid item
        valid.setId(0);
        valid.setEmailAddress("toto@gmail.com");
        valid.setGin("123456789012");
        valid.setCin("1234567890");
        valid.setFirstname("toto");
        valid.setSurname("titi");
        valid.setCivility("MR");
        valid.setBirthdate("22-02-1990");
        valid.setCountryCode("FR");
        valid.setLanguageCode("FR");
        valid.setSubscriptionType("AF");
        valid.setDomain("S");
        valid.setGroupType("N");
        valid.setStatus("Y");
        valid.setSource("source");
        valid.setDateOfConsent("22-02-1990");
        valid.setPreferredDepartureAirport("CDG");

        ModelAdhoc invalid = new ModelAdhoc();
        // invalid item
        invalid.setId(0);
        invalid.setEmailAddress("totogmail.com");
        invalid.setGin("1234ab6789012");
        invalid.setCin("67890");
        invalid.setFirstname("toto");
        invalid.setSurname("titi");
        invalid.setCivility("MR");
        invalid.setBirthdate("22-02-1990");
        invalid.setCountryCode("FR");
        invalid.setLanguageCode("FR");
        invalid.setSubscriptionType("AF");
        invalid.setDomain("S");
        invalid.setGroupType("N");
        invalid.setStatus("Y");
        invalid.setSource("source");
        invalid.setDateOfConsent("22-02-1990");
        invalid.setPreferredDepartureAirport("CDG");

        request.setList(List.of(valid, invalid));
        WrapperAdhoc result = adhocService.validate(request, "AF");
        // Should get a result and it should be the invalid one
        assertEquals(1, result.getResult().size());
        assertFalse(result.getResult().get(0).getErrors().isEmpty());
        assertEquals(3, result.getResult().get(0).getErrors().size());
        assertEquals("EMAIL_ADDRESS", result.getResult().get(0).getErrors().get(0));
        assertEquals("GIN", result.getResult().get(0).getErrors().get(1));
        assertEquals("CIN", result.getResult().get(0).getErrors().get(2));
    }

    @Test
    void upload() {
        ModelAdhocRequest request = new ModelAdhocRequest();
        ModelAdhoc adhoc = new ModelAdhoc();
        // valid item
        adhoc.setId(0);
        adhoc.setEmailAddress("toto@gmail.com");
        adhoc.setGin("123456789012");
        adhoc.setCin("1234567890");
        adhoc.setFirstname("toto");
        adhoc.setSurname("titi");
        adhoc.setCivility("MR");
        adhoc.setBirthdate("22-02-1990");
        adhoc.setCountryCode("fr");
        adhoc.setLanguageCode("fr");
        adhoc.setSubscriptionType("AF");
        adhoc.setDomain("S");
        adhoc.setGroupType("N");
        adhoc.setStatus("Y");
        adhoc.setSource("source");
        adhoc.setDateOfConsent("22-02-1990");
        adhoc.setPreferredDepartureAirport("cdg");

        request.setList(List.of(adhoc));
        boolean ok = adhocService.upload(request, "AF");
        assertTrue(ok);
    }
}
