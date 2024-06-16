package com.afklm.spring.security.habile.demo.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(DemoTestConfiguration.class)
@ActiveProfiles("DemoTest")
@WebMvcTest(controllers = {DemoController.class})
public class DemoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(authorities = "VIEW")
    public void testUserWithAuthority() throws Exception {
        mvc.perform(get("/api/user"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testUserWithoutAuthority() throws Exception {
        mvc.perform(get("/api/user"))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "UPDATE")
    public void testAdminWithAuthority() throws Exception {
        mvc.perform(get("/api/admin"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testAdminWithoutAuthority() throws Exception {
        mvc.perform(get("/api/admin"))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "UPDATE")
    public void testUpdateWithAuthority() throws Exception {
        mvc.perform(post("/api/update").with(csrf()))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testUpdateWithoutAuthority() throws Exception {
        mvc.perform(post("/api/update").with(csrf()))
            .andDo(print())
            .andExpect(status().isForbidden());
    }
}

@Configuration
@Profile("DemoTest")
@EnableMethodSecurity
class DemoTestConfiguration { }
