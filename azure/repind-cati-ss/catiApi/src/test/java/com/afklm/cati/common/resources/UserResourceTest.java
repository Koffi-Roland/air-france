package com.afklm.cati.common.resources;

import com.afklm.cati.common.spring.rest.resources.UserResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class UserResourceTest {

    private static final String EMAIL = "cati@airfrance.com";
    private static final String LASTNAME = "cati";
    private static final String FIRSTNAME = "cati";
    private static final Long TECH_ID = 2L;

    @Test
    @DisplayName("Unit test user resource")
    public void userResourceTest()
    {
        UserResource userResource = this.mockUserResource();
        assertAll(
                () -> assertEquals(userResource.getMail(), EMAIL),
                () -> assertEquals(userResource.getLastName(), LASTNAME),
                () -> assertEquals(userResource.getFirstName(), FIRSTNAME),
                () -> assertEquals(userResource.getTechId(), TECH_ID)
        );
    }
    private UserResource mockUserResource()
    {
        UserResource userResource = new UserResource();
        userResource.setMail(EMAIL);
        userResource.setFirstName(FIRSTNAME);
        userResource.setLastName(LASTNAME);
        userResource.setTechId(TECH_ID);
        return userResource;
    }
}
