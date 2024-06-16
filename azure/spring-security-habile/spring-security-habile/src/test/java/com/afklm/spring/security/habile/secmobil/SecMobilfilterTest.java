package com.afklm.spring.security.habile.secmobil;

import com.afklm.spring.security.habile.GenericHeaders;
import com.afklm.spring.security.habile.HabilePrincipal;
import com.afklm.spring.security.habile.HabilePrincipalFixture;
import com.afklm.spring.security.habile.userdetails.dao.UserRetrieverDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SecMobilfilterTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserRetrieverDao userRetrieverDao;

    @MockBean
    private SecMobilChecker secMobilCheckerMock;

    private MockMvc mockMvc;

    final private String TEST_SECMOBIL = "test-secmobil";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        HabilePrincipal value = HabilePrincipalFixture.habilePrincipal(TEST_SECMOBIL);
        Mockito.when(userRetrieverDao.getUser(Mockito.anyString(), Mockito.anyString())).thenReturn(value);
    }

    @Test
    public void testSecMobilNominal() throws Exception {

        mockMvc.perform(get("/me")
            .header(GenericHeaders.SECGW_USER, TEST_SECMOBIL)
            .header(GenericHeaders.SECGW_TS, "test_ts")
            .header(GenericHeaders.SECGW_CHECK, "test_check"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(authenticated().withUsername(TEST_SECMOBIL));

        Mockito.verify(secMobilCheckerMock).checkSecMobilHeaders(Mockito.any(HttpServletRequest.class));
        Mockito.verifyNoMoreInteractions(secMobilCheckerMock);
    }

    @Test
    public void testSecMobilHeaderCheckFail() throws Exception {
        Mockito.doThrow(new SecurityException("JUNIT")).when(secMobilCheckerMock).checkSecMobilHeaders(Mockito.any(HttpServletRequest.class));

        mockMvc.perform(get("/me")
            .header(GenericHeaders.SECGW_USER, "test")
            .header(GenericHeaders.SECGW_TS, "test")
            .header(GenericHeaders.SECGW_CHECK, "test"))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(unauthenticated());
    }

    @Test
    public void testSecMobilHeadersTakePrecedenceOverSMUSER() throws Exception {
        String TEST_SM_USER = "test_sm_user";

        mockMvc.perform(get("/me").header(GenericHeaders.SECGW_USER, TEST_SECMOBIL)
            .header(GenericHeaders.SECGW_TS, "test_ts")
            .header(GenericHeaders.SECGW_CHECK, "test_check")
            .header(GenericHeaders.SM_USER, TEST_SM_USER))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(authenticated().withUsername(TEST_SECMOBIL));

        Mockito.verify(secMobilCheckerMock).checkSecMobilHeaders(Mockito.any(HttpServletRequest.class));
        Mockito.verifyNoMoreInteractions(secMobilCheckerMock);
    }
}
