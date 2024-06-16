package com.afklm.cati.common.resources;


import com.afklm.cati.common.spring.rest.resources.RefProductResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class RefProductResourceTest {

    private static final Integer ID = 2;
    private static final Long TECH_ID = 2L;
    private static final String TEST = "Test";

    @Test
    @DisplayName("Unit test ref  product resource")
    public void productResourceTest() {
        RefProductResource refProductResource = this.mockRefProductResource();
        assertAll(
                () -> assertEquals(refProductResource.getProductName(), TEST),
                () -> assertEquals(refProductResource.getProductType(), TEST),
                () -> assertEquals(refProductResource.getProductId(), ID),
                () -> assertEquals(refProductResource.getTechId(), TECH_ID)
        );
    }
    private RefProductResource mockRefProductResource() {

        RefProductResource refProductResource = new RefProductResource();
        Date date = new Date();
        refProductResource.setTechId(TECH_ID);
        refProductResource.setProductId(ID);
        refProductResource.setProductName(TEST);
        refProductResource.setProductType(TEST);
        return refProductResource;
    }
}
