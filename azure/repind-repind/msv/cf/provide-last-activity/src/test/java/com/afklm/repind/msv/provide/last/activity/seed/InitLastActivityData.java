
package com.afklm.repind.msv.provide.last.activity.seed;

import com.afklm.repind.msv.provide.last.activity.model.LastActivityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;

/**
 * Init data for last activity
 */
@Service
public class InitLastActivityData {

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

    /**
     * Init last activity data
     *
     * @return LastActivityModel
     */
    public LastActivityModel initLastActivityData()
    {
        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return LastActivityModel.builder()
                .id(1L)
                .gin(GIN)
                .sourceModification(TABLE_MODIFICATION)
                .siteModification(MODIFICATION_SITE)
                .signatureModification(SIGNATURE_MODIFICATION)
                .dateModification(date)
                .build();
    }

}
