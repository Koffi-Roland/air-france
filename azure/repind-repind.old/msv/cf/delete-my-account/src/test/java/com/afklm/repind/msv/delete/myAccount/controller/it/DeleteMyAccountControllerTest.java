package com.afklm.repind.msv.delete.myAccount.controller.it;

import com.afklm.repind.common.entity.ConfigEntityScan;
import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.individual.IndividualEntityScan;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.individual.IndividualRepositoryScan;
import com.afklm.repind.common.repository.role.BusinessRoleRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.msv.delete.myAccount.controller.checher.DeleteMyAccountChecker;
import com.afklm.repind.msv.delete.myAccount.model.error.BusinessError;
import com.afklm.repind.msv.delete.myAccount.service.DeleteMyAccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan(
        basePackages = {
                "com.afklm.repind.common.config.vault",
                "com.afklm.repind.common.entity",
                "com.afklm.repind.common.repository",
                "com.afklm.repind.common.service",
                "com.afklm.repind.msv.delete.myAccount.service",
                "com.afklm.repind.msv.delete.myAccount.controller"})
@EntityScan(basePackageClasses = {
        ConfigEntityScan.class
})
@EnableJpaRepositories(basePackageClasses = {
        IndividualRepositoryScan.class
})
class DeleteMyAccountControllerTest {

    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    DeleteMyAccountService deleteMyAccountService;

    @Autowired
    DeleteMyAccountChecker deleteMyAccountChecker;

    @Autowired
    AccountIdentifierRepository accountIdentifierRepository;

    @Autowired
    BusinessRoleRepository businessRoleRepository;

    @Autowired
    IndividuRepository individuRepository;

    @Autowired
    RoleContractRepository roleContratsRepository;

    String GIN = "999999999125";
    String FBInd = "123456";
    String numContract = "123456";
    Integer CLEROLE = 12457;
    String MA_CONTRACT = "MA";
    private List<Individu> individualsCreated;
    private List<AccountIdentifier> aisCreated;
    private List<BusinessRole> brsCreated;
    private List<RoleContract> rlsCreated;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        individualsCreated = new ArrayList<>();
        aisCreated = new ArrayList<>();
        brsCreated = new ArrayList<>();
        rlsCreated = new ArrayList<>();
        individualsCreated.add(createIndividuForTest(GIN));
    }

    @AfterEach
    public void clean() {
        if (!rlsCreated.isEmpty()) {
            this.roleContratsRepository.deleteAll(rlsCreated);
        }
        if (!brsCreated.isEmpty()) {
            this.businessRoleRepository.deleteAll(brsCreated);
        }
        if (!aisCreated.isEmpty()) {
            this.accountIdentifierRepository.deleteAll(aisCreated);
        }
        individuRepository.deleteById(individualsCreated.get(0).getGin());
    }

    @Test
     void deleteMyAccountContractAndDataException() throws Exception {
        MvcResult result = mockMvc.perform(delete("/delete-my-account/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("gin", GIN))
                .andExpect(status().isNotFound()).andReturn();

        assertEquals(result.getResolvedException().getMessage(), BusinessError.ACCOUNT_DATA_NOT_FOUND.getRestError().getDescription());
    }


    @Test
    @Transactional(rollbackFor = Exception.class)
    void deleteMyAccountContractAndData() throws Exception {
        creationData();

        MvcResult result = mockMvc.perform(delete("/delete-my-account/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("gin", GIN))
                .andExpect(status().isOk()).andReturn();

        assertEquals(accountIdentifierRepository.findBySgin(GIN), null);
        assertEquals(businessRoleRepository.findById(CLEROLE), Optional.empty());
        assertNull(roleContratsRepository.findByIndividuGinAndTypeContrat(GIN, MA_CONTRACT));
    }


    private void creationData() {

        Individu individu = individualsCreated.get(0);

        AccountIdentifier accountData = new AccountIdentifier();
        accountData.setSgin(GIN);
        accountData.setSignatureCreation("REPIND MS");
        accountData.setSiteCreation("QVI");
        accountData.setSignatureModification("REPIND MS");
        accountData.setSiteModification("QVI");

        BusinessRole businessRole = new BusinessRole();
        businessRole.setCleRole(CLEROLE);
        businessRole.setGinInd(GIN);

        RoleContract roleContrats = new RoleContract();
        roleContrats.setSrin("123456");
        roleContrats.setVersion(0);
        roleContrats.setNumeroContrat(numContract);
        roleContrats.setIndividu(individu);
        roleContrats.setEtat("C");
        roleContrats.setCleRole(CLEROLE);
        roleContrats.setTypeContrat(MA_CONTRACT);

        this.aisCreated.add(accountIdentifierRepository.saveAndFlush(accountData));
        this.brsCreated.add(businessRoleRepository.saveAndFlush(businessRole));
        this.rlsCreated.add(roleContratsRepository.saveAndFlush(roleContrats));
    }
    private Individu createIndividuForTest(String sgin) {
        Individu individu = new Individu();
        Date birthDate = new Date();
        individu.setSexe("M");
        individu.setDateNaissance(birthDate);
        individu.setDateCreation(new Date());
        individu.setSignatureCreation("REPIND");
        individu.setSiteCreation("QVI");
        individu.setStatutIndividu("V");
        individu.setCivilite("MR");
        individu.setGin(sgin);
        individu.setNonFusionnable("N");
        individu.setType("I");
        return individuRepository.saveAndFlush(individu);
    }

}