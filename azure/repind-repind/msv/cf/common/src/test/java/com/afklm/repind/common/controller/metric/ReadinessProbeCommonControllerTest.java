package com.afklm.repind.common.controller.metric;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ReadinessProbeCommonController.class)
@AutoConfigureMockMvc
@Slf4j
public class ReadinessProbeCommonControllerTest {

    // https://www.baeldung.com/spring-boot-testing
    // Since the controller is not added to a SpringBootApplication in common we need to specify the class in SpringBootTest
    // We use the mock environment to test the endpoint

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldGETStatus200() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/infra/healthcheck"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldGETStatus404() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/infra/healthcheck-wrong-url"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
