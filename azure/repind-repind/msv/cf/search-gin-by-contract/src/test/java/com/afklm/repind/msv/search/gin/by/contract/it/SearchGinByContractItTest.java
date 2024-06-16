package com.afklm.repind.msv.search.gin.by.contract.it;

import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.msv.search.gin.by.contract.service.SearchGinByContractService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchGinByContractItTest {

    @Autowired
    SearchGinByContractService searchGinByContractService;

    @Test
    public void testSearchByBdcContractNumber() {
        Collection<BusinessRole> businessRoles = searchGinByContractService.complementarySearch("900000031");
        Optional<BusinessRole> businessRole = businessRoles.stream().findFirst();
        assertTrue(businessRole.isPresent() && businessRole.get().getNumeroContrat().length() == 12
                && businessRole.get().getNumeroContrat().substring(0, 3).equals("000"));

    }


}
