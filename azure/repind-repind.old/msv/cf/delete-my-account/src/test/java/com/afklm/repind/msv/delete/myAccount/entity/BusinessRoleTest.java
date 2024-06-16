package com.afklm.repind.msv.delete.myAccount.entity;

import com.afklm.repind.common.entity.role.BusinessRole;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class BusinessRoleTest {

    @Test
    void insertEntityTest(){
        BusinessRole businessRole = new BusinessRole();
        businessRole.setCleRole(1);
        businessRole.setGinInd("ginInd");
        businessRole.setNumeroContrat("num");

        Assert.assertNotNull(businessRole.getCleRole());
        Assert.assertNotNull(businessRole.getGinInd());
        Assert.assertNotNull(businessRole.getNumeroContrat());

    }

}