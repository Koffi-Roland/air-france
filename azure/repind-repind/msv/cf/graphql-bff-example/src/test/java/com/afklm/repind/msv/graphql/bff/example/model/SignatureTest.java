package com.afklm.repind.msv.graphql.bff.example.model;

import com.afklm.repind.common.enums.SignatureEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class SignatureTest {

    private static final String SIGNATURE_CREATION = "ISI";
    private final String SITE_CREATION = "WEB";


    @Test
    @DisplayName("Unit test on signature")
    public void getSignatureTest() throws ParseException {
        final Date dateCreation = new SimpleDateFormat("yyyy-MM-dd").parse("2009-12-01");

        List<Signature> signatures = this.buildMockSignature();
        assertAll(
                () -> assertEquals(signatures.get(0).getSignature(), SIGNATURE_CREATION),
                () -> assertEquals(signatures.get(0).getSignatureType(), SignatureEnum.CREATION.toString()),
                () -> assertEquals(signatures.get(0).getSignatureSite(), SITE_CREATION),
                () -> assertEquals(signatures.get(0).getDate(), dateCreation.toInstant().atOffset(ZoneOffset.UTC))
        );
    }

    private List<Signature> buildMockSignature() throws ParseException {
        final Date dateCreation = new SimpleDateFormat("yyyy-MM-dd").parse("2009-12-01");

        List<Signature> signatures = new ArrayList<>();
        Signature signatureCreation = new Signature();
        signatureCreation.setSignature(SIGNATURE_CREATION);
        signatureCreation.setSignatureSite(SITE_CREATION);
        signatureCreation.setSignatureType(SignatureEnum.CREATION.toString());
        signatureCreation.setDate(dateCreation.toInstant().atOffset(ZoneOffset.UTC));
        signatures.add(signatureCreation);

        return signatures;
    }
}
