package com.afklm.repind.common.entity.individual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ProfilsEntityTest {

    @Test
    void testClass() {
        ProfilsEntity profilsEntity = new ProfilsEntity();
        // set
        profilsEntity.setGin("123456789");
        profilsEntity.setCodeLangue("codeLangue");
        // get
        assertEquals("123456789", profilsEntity.getGin());
        assertEquals("codeLangue", profilsEntity.getCodeLangue());
    }
}
