package com.afklm.repind.msv.provide.identification.data.ut.transform;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.msv.provide.identification.data.transform.AccountTransform;
import com.afklm.repind.msv.provide.identification.data.utils.GenerateTestData;
import com.afklm.soa.stubs.r000378.v1.model.IdentifierData;
import com.afklm.soa.stubs.r000378.v1.model.Signature;
import com.afklm.soa.stubs.r000378.v1.model.SignatureCreation;
import com.afklm.soa.stubs.r000378.v1.model.SignatureModification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AccountTransformTest {

    @Autowired
    private AccountTransform accountTransform;

    @Test
    void testIdentifierData(){
        AccountIdentifier accountIdentifier = GenerateTestData.buildAccountIdentifier();

        List<IdentifierData> identifierDataList = new ArrayList<>();
        identifierDataList.add(accountTransform.setIdentifierData(accountIdentifier.getAccountId(),accountTransform.setSignature(accountIdentifier),"A"));
        identifierDataList.add(accountTransform.setIdentifierData(accountIdentifier.getFbIdentifier(),accountTransform.setSignature(accountIdentifier),"F"));
        identifierDataList.add(accountTransform.setIdentifierData(accountIdentifier.getEmailIdentifier(),accountTransform.setSignature(accountIdentifier),"E"));

        for(IdentifierData identifierData : identifierDataList){
            switch (identifierData.getType()) {
                case "A":
                    Assertions.assertEquals("786442AH",identifierData.getIdentifier());
                    break;
                case "E":
                    Assertions.assertEquals("jsmith@fwellc.com",identifierData.getIdentifier());
                    break;
                case "F":
                    Assertions.assertEquals("002101358216",identifierData.getIdentifier());
                    break;
            }
        }
    }

    @Test
    void testSetSignature(){
        LocalDate date = LocalDate.of(2013, 12, 6);
        Signature signatureATester = accountTransform.setSignature(GenerateTestData.buildAccountIdentifier());

        Signature signatureAttendue = new Signature();
        SignatureCreation signatureCreationAttendue = new SignatureCreation();
        signatureCreationAttendue.setSignature("CREATE ACCOUNT");
        signatureCreationAttendue.setDate(date);
        signatureCreationAttendue.setSite("S09372");

        SignatureModification signatureModificationAttendue = new SignatureModification();
        signatureModificationAttendue.setDate(date);
        signatureModificationAttendue.setSignature("AUTHENT MYACCNT");
        signatureModificationAttendue.setSite("SIC WS");

        signatureAttendue.setSignatureCreation(signatureCreationAttendue);
        signatureAttendue.setSignatureModification(signatureModificationAttendue);

        Assertions.assertEquals(signatureAttendue.getSignatureCreation().getSignature(),signatureATester.getSignatureCreation().getSignature());
        Assertions.assertEquals(signatureAttendue.getSignatureCreation().getSite(),signatureATester.getSignatureCreation().getSite());
        Assertions.assertEquals(signatureAttendue.getSignatureCreation().getDate(),signatureATester.getSignatureCreation().getDate());

        Assertions.assertEquals(signatureAttendue.getSignatureModification().getSignature(),signatureATester.getSignatureModification().getSignature());
        Assertions.assertEquals(signatureAttendue.getSignatureModification().getSite(),signatureATester.getSignatureModification().getSite());
        Assertions.assertEquals(signatureAttendue.getSignatureModification().getDate(),signatureATester.getSignatureModification().getDate());
    }
}
