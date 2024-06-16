package com.afklm.cati.common.resources;

import com.afklm.cati.common.spring.rest.resources.EntryPointResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class EntryPointResourceTest {

    private static final String APPLICATION_NAME = "CATI";
    private static final Long TECH_ID = 1L;

    @Test
    @DisplayName("Unit test on entry point resource")
    public void entryPointresourceTest()
    {
        EntryPointResource entryPointResource = this.mockEntryPointResource();
        assertAll(
                () -> assertEquals(entryPointResource.getApplicationName(), APPLICATION_NAME),
                () -> assertEquals(entryPointResource.getTechId(), TECH_ID)
                );
    }

    private EntryPointResource mockEntryPointResource()
    {
        EntryPointResource entryPointResource = new EntryPointResource();
        entryPointResource.setApplicationName(APPLICATION_NAME);
        entryPointResource.setTechId(TECH_ID);
        return entryPointResource;
    }
}
