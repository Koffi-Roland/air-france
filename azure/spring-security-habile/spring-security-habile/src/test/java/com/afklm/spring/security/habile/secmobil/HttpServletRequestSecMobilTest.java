package com.afklm.spring.security.habile.secmobil;

import com.afklm.spring.security.habile.GenericHeaders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServletRequestSecMobilTest {

    // Override SM_USER with secgw_user
    private final String smUser = "mXXXXXX";
    private HttpServletRequestSecMobil wrappedRequest;

    @BeforeEach
    public void setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        wrappedRequest = new HttpServletRequestSecMobil(request, smUser);
    }

    @Test
    public void testGetHeader() {
        assertThat(wrappedRequest.getHeader(GenericHeaders.SM_USER)).isEqualTo(smUser);
    }

    @Test
    public void testGetHeaders() {
        assertThat(Collections.list(wrappedRequest.getHeaders(GenericHeaders.SM_USER))).containsExactly(smUser);
    }

    @Test
    public void tesGetHeaderNames() {
        // Test getHeaderNames
        assertThat(Collections.list(wrappedRequest.getHeaderNames())).containsExactlyInAnyOrder(GenericHeaders.SM_USER, GenericHeaders.SM_SESSION);
    }
}
