package com.afklm.repind.common.entity.profile.compliance.score;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class RefPcsFactorTest {

    @Test
    void testClass() {
        RefPcsFactor item = new RefPcsFactor();
        //set
        item.setFactor(1);
        item.setCode("code");
        item.setLibelle("libelle");
        item.setMaxPoints(2);
        //get
        assertEquals(1, item.getFactor());
        assertEquals("code", item.getCode());
        assertEquals("libelle", item.getLibelle());
        assertEquals(2, item.getMaxPoints());
    }
}
