package com.afklm.repind.common.entity.contact;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class PostalAddressTest {

    @Test
    void testClass() {
        PostalAddress postalAddress = new PostalAddress();
        // Set
        postalAddress.setCodePostal("codePostal");
        postalAddress.setAin("ain");
        postalAddress.setCleRole(1);
        postalAddress.setDenvoiPostal(new Date(1999,1,1));
        postalAddress.setCodeMedium("codeMedium");
        postalAddress.setSenvoiPostal("senvoiPostal");
        postalAddress.setCodeAppliSending("appliSending");
        postalAddress.setCodePays("codePays");
        postalAddress.setRaisonSociale("raisonSociale");
        postalAddress.setCodeProvince("codeProvince");
        postalAddress.setCodErr(2);
        postalAddress.setCodErrDetaille("codeErrDetaille");
        postalAddress.setCodErrSimple("codeErrSimple");
        postalAddress.setCodWarning(3);
        postalAddress.setComplementAdresse("complementAdresse");
        postalAddress.setDateFonctionnel(new Date(1999,1,2));
        postalAddress.setDateModification(new Date(1999,1,3));
        postalAddress.setDescriptifComplementaire("descriptifComplementaire");
        postalAddress.setForcage("forcage");
        postalAddress.setFormalizedAdrList(Set.of("formalizedAdrList"));
        postalAddress.setIndAdr("inAdr");
        postalAddress.setKeyTemp(4);
        postalAddress.setLocalite("localLite");
        postalAddress.setNoEtRue("noEtRue");
        postalAddress.setVille("ville");
        postalAddress.setUsageMedium(Set.of(new UsageMedium()));
        postalAddress.setTypeInvalidite("typeInvalidite");
        postalAddress.setStatutMedium("statusMedium");
        postalAddress.setSiteModification("siteModification");
        postalAddress.setVersion(5);
        postalAddress.setToBeReprocessed(true);
        postalAddress.setSiteFonctionnel("siteFonctionnel");
        postalAddress.setSiteCreation("siteCreation");
        postalAddress.setSignatureModification("signatureModification");
        postalAddress.setSignatureFonctionnel("signatureFonctionnel");
        postalAddress.setSignatureCreation("signatureCreation");
        postalAddress.setScodeAppSend("ScodeAppSend");
        postalAddress.setReprocessTime(new Date(1999,1,5));
        postalAddress.setReprocessError("reprocessError");
        postalAddress.setReprocessAppliId("reprocessAppliId");
        // get
        assertEquals("codePostal",  postalAddress.getCodePostal());
        assertEquals("ain",  postalAddress.getAin());
        assertEquals(1,  postalAddress.getCleRole());
        assertEquals(new Date(1999,1,1),  postalAddress.getDenvoiPostal());
        assertEquals("codeMedium",  postalAddress.getCodeMedium());
        assertEquals("senvoiPostal",  postalAddress.getSenvoiPostal());
        assertEquals("appliSending",  postalAddress.getCodeAppliSending());
        assertEquals("codePays",  postalAddress.getCodePays());
        assertEquals("raisonSociale",  postalAddress.getRaisonSociale());
        assertEquals("codeProvince",  postalAddress.getCodeProvince());
        assertEquals(2,  postalAddress.getCodErr());
        assertEquals("codeErrDetaille",  postalAddress.getCodErrDetaille());
        assertEquals("codeErrSimple",  postalAddress.getCodErrSimple());
        assertEquals(3,  postalAddress.getCodWarning());
        assertEquals("complementAdresse",  postalAddress.getComplementAdresse());
        assertEquals(new Date(1999,1,2),  postalAddress.getDateFonctionnel());
        assertEquals(new Date(1999,1,3),  postalAddress.getDateModification());
        assertEquals("descriptifComplementaire",  postalAddress.getDescriptifComplementaire());
        assertEquals("forcage",  postalAddress.getForcage());
        assertEquals(Set.of("formalizedAdrList"),  postalAddress.getFormalizedAdrList());
        assertEquals("inAdr",  postalAddress.getIndAdr());
        assertEquals(4,  postalAddress.getKeyTemp());
        assertEquals("localLite",  postalAddress.getLocalite());
        assertEquals("noEtRue",  postalAddress.getNoEtRue());
        assertEquals("ville",  postalAddress.getVille());
        assertEquals("typeInvalidite",  postalAddress.getTypeInvalidite());
        assertEquals("statusMedium",  postalAddress.getStatutMedium());
        assertEquals("siteModification",  postalAddress.getSiteModification());
        assertEquals(5,  postalAddress.getVersion());
        assertEquals("siteFonctionnel",  postalAddress.getSiteFonctionnel());
        assertEquals("signatureCreation",  postalAddress.getSignatureCreation());
        assertEquals("ScodeAppSend",  postalAddress.getScodeAppSend());
        assertEquals(new Date(1999,1,5),  postalAddress.getReprocessTime());
        assertEquals("reprocessError",  postalAddress.getReprocessError());
        assertEquals("reprocessAppliId",  postalAddress.getReprocessAppliId());
    }
}
