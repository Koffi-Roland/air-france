package com.afklm.cati.common.resources;

import com.afklm.cati.common.spring.rest.resources.SecurityInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class SecurityInfoTest {
    private static final String ROLE = "ROLE_ADMIN_COMMPREF";
    private static final String PERMISSIONS = "P_CATI_SUPERADMIN";
    private static final String LASTNAME = "cati";
    private static final String FIRSTNAME = "cati";

    @Test
    @DisplayName("Unit test security resource")
    public void securityResourceTest()
    {
        SecurityInfo  securityInfo = this.mockSecurityInfo();
        assertAll(
                () -> assertEquals(securityInfo.getFirstname(), FIRSTNAME),
                () -> assertEquals(securityInfo.getLastname(), LASTNAME),
                () -> assertEquals(securityInfo.getRoles(), List.of(ROLE)),
                () -> assertThat(securityInfo.getRoles().size()).isEqualTo(1),
                () -> assertThat(securityInfo.getPermissions().size()).isEqualTo(1),
                () -> assertEquals(securityInfo.getPermissions(), List.of(PERMISSIONS))
        );
    }
    private SecurityInfo mockSecurityInfo()
    {
        SecurityInfo securityInfo = new SecurityInfo();
        securityInfo.setFirstname(FIRSTNAME);
        securityInfo.setLastname(LASTNAME);
        securityInfo.setRoles(List.of(ROLE));
        securityInfo.setPermissions(List.of(PERMISSIONS));

        return securityInfo;
    }
}
