package com.afklm.repind.common.entity.individual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class UsageClientEntityTest {

    @Test
    void testClass() {
        UsageClientEntity usageClientEntity = new UsageClientEntity();
        // set
        usageClientEntity.setGin("123456789");
        usageClientEntity.setCode("code");
        usageClientEntity.setSrin("srin");
        usageClientEntity.setAuthorizedModification("authorizedModification");
        usageClientEntity.setDateModification(new Date(1999,1,1));
        // get
        assertEquals("123456789", usageClientEntity.getGin());
        assertEquals("code", usageClientEntity.getCode());
        assertEquals("srin", usageClientEntity.getSrin());
        assertEquals("authorizedModification", usageClientEntity.getAuthorizedModification());
        assertEquals(new Date(1999,1,1), usageClientEntity.getDateModification());
    }
}
