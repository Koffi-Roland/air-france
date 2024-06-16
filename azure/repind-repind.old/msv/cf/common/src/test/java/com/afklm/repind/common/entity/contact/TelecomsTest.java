package com.afklm.repind.common.entity.contact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class TelecomsTest {

    @Test
    void testClass() {
        Telecoms telecoms = new Telecoms();
        // Set
        telecoms.setAin("ain");
        telecoms.setCleRole(1);
        telecoms.setCodeMedium("codeMedium");
        telecoms.setCodeRegion("codeRegion");
        telecoms.setCountryCode("countryCode");
        telecoms.setForcage("forcage");
        telecoms.setDateCreation(new Date(1999,1,1));
        telecoms.setDateInvalidation(new Date(1999,1,2));
        telecoms.setDescriptifComplementaire("descriptifComplementaire");
        telecoms.setIsNormalized("true");
        telecoms.setKeyTemp(2);
        telecoms.setIndicatif("indicatif");
        telecoms.setNormalizedCountry("normalizedCountry");
        telecoms.setVersion(3);
        telecoms.setValidation("validation");
        telecoms.setTerminal("terminal");
        telecoms.setStatutMedium("statusMedium");
        telecoms.setSiteModification("siteModification");
        telecoms.setSiteCreation("siteCreation");
        telecoms.setSignatureModification("signatureModification");
        telecoms.setSignatureCreation("signatureCreation");
        telecoms.setNumero("numero");
        telecoms.setNormTerminalTypeDetail("normTerminalTypeDetail");
        telecoms.setNormNatPhoneNumberClean("normNatPhoneNumberClean");
        telecoms.setNormInterPhoneNumber("normInterPhoneNumber");
        telecoms.setNormInterCountryCode("normInterCountryCode");
        telecoms.setNormalizedNumero("normalizedNumero");
        telecoms.setDateModification(new Date(1999,1,3));
        // get
        assertEquals("ain",  telecoms.getAin());
        assertEquals(1,  telecoms.getCleRole());
        assertEquals("codeMedium",  telecoms.getCodeMedium());
        assertEquals("codeRegion",  telecoms.getCodeRegion());
        assertEquals("countryCode",  telecoms.getCountryCode());
        assertEquals("forcage",  telecoms.getForcage());
        assertEquals(new Date(1999,1,1),  telecoms.getDateCreation());
        assertEquals(new Date(1999,1,2),  telecoms.getDateInvalidation());
        assertEquals("descriptifComplementaire",  telecoms.getDescriptifComplementaire());
        assertEquals("true",  telecoms.getIsNormalized());
        assertEquals(2,  telecoms.getKeyTemp());
        assertEquals("indicatif",  telecoms.getIndicatif());
        assertEquals("normalizedCountry",  telecoms.getNormalizedCountry());
        assertEquals(3,  telecoms.getVersion());
        assertEquals("validation",  telecoms.getValidation());
        assertEquals("terminal",  telecoms.getTerminal());
        assertEquals("statusMedium",  telecoms.getStatutMedium());
        assertEquals("siteModification",  telecoms.getSiteModification());
        assertEquals("siteCreation",  telecoms.getSiteCreation());
        assertEquals("signatureModification",  telecoms.getSignatureModification());
        assertEquals("signatureCreation",  telecoms.getSignatureCreation());
        assertEquals("numero",  telecoms.getNumero());
        assertEquals("normTerminalTypeDetail",  telecoms.getNormTerminalTypeDetail());
        assertEquals("normNatPhoneNumberClean",  telecoms.getNormNatPhoneNumberClean());
        assertEquals("normInterPhoneNumber",  telecoms.getNormInterPhoneNumber());
        assertEquals("normInterCountryCode",  telecoms.getNormInterCountryCode());
        assertEquals("normalizedNumero",  telecoms.getNormalizedNumero());
        assertEquals(new Date(1999,1,3),  telecoms.getDateModification());
    }
}
