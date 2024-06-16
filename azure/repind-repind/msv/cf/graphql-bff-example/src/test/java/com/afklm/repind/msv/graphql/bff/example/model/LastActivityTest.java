package com.afklm.repind.msv.graphql.bff.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class LastActivityTest {

    /**
     * Gin : Individual number
     */
    private static final String GIN = "110000038701";
    /**
     * Table source where modification originated
     */
    private static final String TABLE_MODIFICATION = "Individuals_all";
    /**
     * Signature modification
     */
    private static final String SIGNATURE_MODIFICATION = "REPIND/IHM";
    /**
     * Signature site
     */
    private static final String MODIFICATION_SITE = "KLM";


    @Test
    @DisplayName("Unit test on get last activity")
    public void getLastActivityTest()
    {
        LastActivity lastActivity = this.initLastActivity();
        assertAll(
                () -> assertEquals(lastActivity.getGin(), GIN),
                () -> assertEquals(lastActivity.getSourceModification(), TABLE_MODIFICATION),
                () -> assertEquals(lastActivity.getSignatureModification(), SIGNATURE_MODIFICATION),
                () -> assertEquals(lastActivity.getSiteModification(), MODIFICATION_SITE)
        );
    }
    /**
     * Init last activity data
     *
     * @return LastActivityModel
     */
    public LastActivity initLastActivity()
    {
        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return LastActivity.builder()
                .gin(GIN)
                .sourceModification(TABLE_MODIFICATION)
                .siteModification(MODIFICATION_SITE)
                .signatureModification(SIGNATURE_MODIFICATION)
                .dateModification(date)
                .build();
    }

}
