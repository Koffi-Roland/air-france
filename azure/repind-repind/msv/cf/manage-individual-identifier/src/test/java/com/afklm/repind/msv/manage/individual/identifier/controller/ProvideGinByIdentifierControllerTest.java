package com.afklm.repind.msv.manage.individual.identifier.controller;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.individual.IndividualEntityScan;
import com.afklm.repind.common.model.error.FormatError;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.individual.IndividualRepositoryScan;
import com.afklm.repind.msv.manage.individual.identifier.model.FullRestErrorMapper;
import com.afklm.repind.msv.manage.individual.identifier.model.error.BusinessError;
import com.afklm.repind.msv.manage.individual.identifier.response.WrapperFindGinByIdentifierResponse;
import com.afklm.repind.msv.manage.individual.identifier.util.GenerateTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EntityScan(basePackageClasses = {
        IndividualEntityScan.class
})
@EnableJpaRepositories(basePackageClasses = {
        IndividualRepositoryScan.class
})
class ProvideGinByIdentifierControllerTest {

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private AccountIdentifierRepository accountIdentifierRepository;

    @Autowired
    private IndividuRepository individuRepository;

    private MockMvc mockMvc;
    private List<AccountIdentifier> accountIdentifierList;
    private List<Individu> individuList;

    private static final String EMAIL = "test@integrationtest.repind";
    private static final String SGIN = "999999999125";
    private static final String FB_CONTRACT = "001234567890";

    @BeforeEach
    public void initData() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
        accountIdentifierList = new ArrayList<>();
        individuList = new ArrayList<>();

        Individu indiv = GenerateTestData.createIndividualForTest(SGIN);
        AccountIdentifier accountIdentifier = GenerateTestData.createAccountIdentifierForTest(SGIN, EMAIL, FB_CONTRACT);
        individuList.add(individuRepository.save(indiv));
        accountIdentifierList.add(accountIdentifierRepository.save(accountIdentifier));
    }

    @AfterEach
    public void clean() {
        this.accountIdentifierRepository.deleteAll(accountIdentifierList);
        this.individuRepository.deleteAll(individuList);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    void getGinByEmail() throws Exception {
        MvcResult result = mockMvc.perform(get(String.format("/email/%s", EMAIL))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        WrapperFindGinByIdentifierResponse wrapperFindGinByIdentifierResponse = new ObjectMapper().readValue(response, WrapperFindGinByIdentifierResponse.class);
        Assert.assertEquals(SGIN, wrapperFindGinByIdentifierResponse.getGin());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    void getGinByEmail_notFound() throws Exception {
        MvcResult result = mockMvc.perform(get(String.format("/email/%s", "test@testintegrationfailed.repind"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        FullRestErrorMapper restError = new ObjectMapper().readValue(response, FullRestErrorMapper.class);
        Assert.assertEquals(BusinessError.GIN_NOT_FOUND.getRestError().getCode(), restError.getError().getCode());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    void getGinByEmail_formatInvalid() throws Exception {
        MvcResult result = mockMvc.perform(get(String.format("/email/%s", "testtestintegrationfailed"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        FullRestErrorMapper restError = new ObjectMapper().readValue(response, FullRestErrorMapper.class);
        Assert.assertEquals(FormatError.API_EMAIL_MISMATCH.getRestError().getCode(), restError.getError().getCode());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    void getGinByCin() throws Exception {
        MvcResult result = mockMvc.perform(get(String.format("/contract/%s", FB_CONTRACT))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        WrapperFindGinByIdentifierResponse wrapperFindGinByIdentifierResponse = new ObjectMapper().readValue(response, WrapperFindGinByIdentifierResponse.class);
        Assert.assertEquals(SGIN, wrapperFindGinByIdentifierResponse.getGin());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    void getGinByCin_notFound() throws Exception {
        MvcResult result = mockMvc.perform(get(String.format("/contract/%s", "001234567899"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        FullRestErrorMapper restError = new ObjectMapper().readValue(response, FullRestErrorMapper.class);
        Assert.assertEquals(BusinessError.GIN_NOT_FOUND.getRestError().getCode(), restError.getError().getCode());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    void getGinByCin_formatInvalid() throws Exception {
        MvcResult result = mockMvc.perform(get(String.format("/contract/%s", "0123"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        FullRestErrorMapper restError = new ObjectMapper().readValue(response, FullRestErrorMapper.class);
        Assert.assertEquals(FormatError.API_CIN_MISMATCH.getRestError().getCode(), restError.getError().getCode());
    }
}
