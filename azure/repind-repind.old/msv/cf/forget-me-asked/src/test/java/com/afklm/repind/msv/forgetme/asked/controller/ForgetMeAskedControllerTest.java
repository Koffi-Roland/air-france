package com.afklm.repind.msv.forgetme.asked.controller;

import com.afklm.repind.msv.forgetme.asked.entity.ForgottenIndividual;
import com.afklm.repind.msv.forgetme.asked.repository.IForgottenIndividualRepository;
import com.afklm.repind.msv.forgetme.asked.service.ForgetMeAskedService;
import com.afklm.repind.msv.forgetme.asked.service.encoder.ForgetMeAskedEncoder;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

@WebMvcTest(controllers = ForgetMeAskedController.class)
@Slf4j
@AutoConfigureWebClient
public class ForgetMeAskedControllerTest {

    @Autowired
    private MockMvc mvc;

    @SpyBean
    private ForgetMeAskedService forgetMeAskedService;

    @SpyBean
    private ForgetMeAskedEncoder forgetMeAskedEncoder;

    @MockBean
    private IForgottenIndividualRepository forgottenIndividualRepository;


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

    @Test
    public void shouldFindAllForgetMeAsked() throws Exception {
        Date dateMinusOne = new LocalDate(DateTimeZone.UTC).minusDays(2).toDate();
        BDDMockito.given(this.forgottenIndividualRepository.findForgottenIndividualsByContextIs("A")).willReturn(Arrays.asList(new ForgottenIndividual().withIdentifier("123456789").withModificationDate(new Date()),new ForgottenIndividual().withIdentifier("987654321").withModificationDate(dateMinusOne)));

        this.mvc.perform(MockMvcRequestBuilders.get("/forget-me-asked/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.askedIndividuals.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.askedIndividuals.[0].gin", Matchers.is("123456789")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.askedIndividuals.[0].creationDate", Matchers.is(simpleDateFormat.format(new Date()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.askedIndividuals.[1].gin", Matchers.is("987654321")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.askedIndividuals.[1].creationDate", Matchers.is(simpleDateFormat.format(dateMinusOne))));
    }

}
