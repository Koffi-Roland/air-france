package com.afklm.repind.common.entity.profile.compliance.score;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class RefPcsScoreTest {

    @Test
    void testClass() {
        RefPcsScore item = new RefPcsScore();
        //set
        item.setScore(1);
        item.setCode("code");
        item.setLibelle("libelle");
        item.setCodeFactor("codeFactor");
        //get
        assertEquals(1, item.getScore());
        assertEquals("code", item.getCode());
        assertEquals("libelle", item.getLibelle());
        assertEquals("codeFactor", item.getCodeFactor());
    }
}
