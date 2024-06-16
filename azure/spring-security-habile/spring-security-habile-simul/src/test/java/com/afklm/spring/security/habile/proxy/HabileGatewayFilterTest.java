package com.afklm.spring.security.habile.proxy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class HabileGatewayFilterTest {

    @InjectMocks
    private HabileGatewayFilter habileGatewayFilter;

    @Test
    void testJWTGeneratedWhenNotPresent() {
        assertNotNull(habileGatewayFilter.getOrRefreshToken(null, "user"));
    }

    @Test
    void testJWTKeptWhenUsersMatchAndNotExpired() {
        String currentJWT = habileGatewayFilter.createJWT("user");
        assertEquals(currentJWT, habileGatewayFilter.getOrRefreshToken(currentJWT, "user"));
    }

    @Test
    void testJWTRegeneratedWhenUsersDontMatch() {
        ReflectionTestUtils.setField(habileGatewayFilter, "jwtTtl", 1);
        String currentJWT = habileGatewayFilter.createJWT("user");

        assertNotEquals(currentJWT, habileGatewayFilter.getOrRefreshToken(currentJWT, "admin"));
    }

    @Test
    void testJWTRegeneratedWhenExpired() throws InterruptedException {
        ReflectionTestUtils.setField(habileGatewayFilter, "jwtTtl", 1);
        String currentJWT = habileGatewayFilter.createJWT("user");
        Thread.sleep(2000);
        assertNotEquals(currentJWT, habileGatewayFilter.getOrRefreshToken(currentJWT, "user"));
    }
}
