package com.afklm.repind.msv.manage.individual.identifier.util;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.individual.Individu;

import java.util.Date;

public class GenerateTestData {

    public static Individu createIndividualForTest(String sgin) {
        Individu individu = new Individu();
        Date birthDate = new Date();
        individu.setDateNaissance(birthDate);
        individu.setDateCreation(new Date());
        individu.setSignatureCreation("REPIND");
        individu.setSiteCreation("QVI");
        individu.setStatutIndividu("V");
        individu.setCivilite("MR");
        individu.setNom("TEST COM");
        individu.setPrenom("TEST PREF");
        individu.setGin(sgin);
        individu.setNonFusionnable("N");
        individu.setType("I");
        individu.setSexe("M");
        return individu;
    }

    public static AccountIdentifier createAccountIdentifierForTest(String sgin, String email, String fbContract) {
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setSgin(sgin);
        accountIdentifier.setEmailIdentifier(email);
        accountIdentifier.setFbIdentifier(fbContract);
        accountIdentifier.setSignatureCreation("REPIND MS");
        accountIdentifier.setSiteCreation("QVI");
        accountIdentifier.setSignatureModification("REPIND MS");
        accountIdentifier.setSiteModification("QVI");

        return accountIdentifier;
    }

    private GenerateTestData() {
        throw new IllegalStateException("Utility class");
    }
}
