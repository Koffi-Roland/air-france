package com.afklm.repind.msv.delete.myAccount.entity;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.role.RoleContract;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class RoleContratsTest {


    @Test
    void insertEntityTest(){
        Individu individu = new Individu();
        individu.setGin("gin");

        RoleContract roleContrats = new RoleContract();
        roleContrats.setSrin("srin");
        roleContrats.setCleRole(1);
        roleContrats.setNumeroContrat("numContract");
        roleContrats.setIndividu(individu);
        roleContrats.setEtat("etat");
        roleContrats.setTypeContrat("typeContract");
        roleContrats.setVersion(1);

        Assert.assertNotNull(roleContrats.getSrin());
        Assert.assertNotNull(roleContrats.getVersion());
        Assert.assertNotNull(roleContrats.getNumeroContrat());
        Assert.assertNotNull(roleContrats.getIndividu());
        Assert.assertNotNull(roleContrats.getEtat());
        Assert.assertNotNull(roleContrats.getTypeContrat());
        Assert.assertNotNull(roleContrats.getCleRole());
    }
}