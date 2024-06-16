package com.afklm.repind.common.entity.identifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AccountIdentifierTest {

    @Test
    void getId() {
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setId(123);
        assertEquals(123, accountIdentifier.getId());
    }

    @Test
    void getAccountId() {
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setAccountId("123");
        assertEquals("123", accountIdentifier.getAccountId());
    }

    @Test
    void getSgin() {
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setSgin("123");
        assertEquals("123", accountIdentifier.getSgin());
    }

    @Test
    void getFbIdentifier() {
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setFbIdentifier("123");
        assertEquals("123", accountIdentifier.getFbIdentifier());
    }

    @Test
    void getEmailIdentifier() {
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setEmailIdentifier("123");
        assertEquals("123", accountIdentifier.getEmailIdentifier());
    }

    @Test
    void getSocialNetworkId() {
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setSocialNetworkId("123");
        assertEquals("123", accountIdentifier.getSocialNetworkId());
    }

    @Test
    void getLastSocialNetworkId() {
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setLastSocialNetworkId("123");
        assertEquals("123", accountIdentifier.getLastSocialNetworkId());
    }
}