package com.afklm.cati.common.resources;

import com.afklm.cati.common.spring.rest.resources.HabileResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class HabileResourceTest {

    private static final String EMAIL = "cati@airfrance.com";
    private static final String USERNAME = "cati";
    private static final String LASTNAME = "cati";
    private static final String FIRSTNAME = "cati";
    private static final String ROLE = "ROLE_ADMIN_COMMPREF";
    private static final String PERMISSIONS = "P_CATI_SUPERADMIN";

    @Test
    @DisplayName("Unit test habile resource")
    public void habileResourceTest()
    {
        HabileResource habileResource = this.mockHabileResource();
        assertAll(
                () -> assertEquals(habileResource.getEmail(), EMAIL),
                () -> assertEquals(habileResource.getUserName(), USERNAME),
                () -> assertEquals(habileResource.getFirstName(), FIRSTNAME),
                () -> assertEquals(habileResource.getLastName(), LASTNAME),
                () -> assertEquals(habileResource.getRoles(), List.of(ROLE)),
                () -> assertThat(habileResource.getRoles().size()).isEqualTo(1),
                () -> assertThat(habileResource.getPermissions().size()).isEqualTo(1),
                () -> assertEquals(habileResource.getPermissions(), List.of(PERMISSIONS))
        );
    }
    private HabileResource mockHabileResource()
    {
        HabileResource habileResource = new HabileResource();
        habileResource.setEmail(EMAIL);
        habileResource.setUserName(USERNAME);
        habileResource.setLastName(LASTNAME);
        habileResource.setFirstName(FIRSTNAME);
        habileResource.setRoles(List.of(ROLE));
        habileResource.setPermissions(List.of(PERMISSIONS));

        return habileResource;
    }
}
