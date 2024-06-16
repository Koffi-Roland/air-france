package com.afklm.repind.msv.provide.contract.data.helper;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.entity.role.RoleTravelers;
import com.afklm.repind.common.entity.role.RoleUCCR;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class GenerateTestData {
    public static RoleTravelers createRoleTravelers() {
        RoleTravelers res = new RoleTravelers();

        res.setMatchingRecognitionCode("test");
        res.setLastRecognitionDate(Date.from(LocalDate.of(2023,5,11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setSignatureCreation("RI");
        res.setSiteCreation("QVI");
        res.setDateCreation(Date.from(LocalDate.of(2023,5,11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setSignatureModification("RI");
        res.setSiteModification("QVI");
        res.setCleRole(123987547);
        res.setDateModification(Date.from(LocalDate.of(2023,5,11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setGin("123456789123");

        return res;
    }

    public static RoleUCCR createRoleUCCR() {
        RoleUCCR res = new RoleUCCR();

        res.setUccrId("012345678");
        res.setCorporateEnvironmentID("876543210");
        res.setSignatureCreation("RI");
        res.setSiteCreation("QVI");
        res.setDateCreation(Date.from(LocalDate.of(2023,5,11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setSignatureModification("RI");
        res.setSiteModification("QVI");
        res.setCleRole(123987549);
        res.setDateModification(Date.from(LocalDate.of(2023,5,11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setEtat("X");
        res.setType("UC");
        res.setDebutValidite(Date.from(LocalDate.of(2023,5,11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setFinValidite(Date.from(LocalDate.of(2023,5,11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setGin("123456789123");

        return res;
    }

    public static RoleContract createRoleContrat() {
        RoleContract res = new RoleContract();
        Individu ind = new Individu();
        ind.setGin("123456789123");

        res.setSignatureCreation("RI");
        res.setSiteCreation("QVI");
        res.setCleRole(123987550);
        res.setDateCreation(Date.from(LocalDate.of(2023,5,11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setSignatureModification("RI");
        res.setSiteModification("QVI");
        res.setDateModification(Date.from(LocalDate.of(2023,5,11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setEtat("C");
        res.setDateDebutValidite(Date.from(LocalDate.of(2023,5,11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setDateFinValidite(Date.from(LocalDate.of(2023,5,11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setIata("01234562");
        res.setTypeContrat("MA");
        res.setSousType("GOLD");
        res.setCodeCompagnie("AF");
        res.setNumeroContrat("98765432131212345678");
        res.setIndividu(ind);

        return res;
    }

    public static List<BusinessRole> createBusinessRoleList() {
        BusinessRole traveler = new BusinessRole();
        traveler.setCleRole(123987547);
        traveler.setNumeroContrat("9876543210");
        traveler.setGinInd("123456789123");
        traveler.setType("T");

        BusinessRole uccr = new BusinessRole();
        uccr.setCleRole(123987549);
        uccr.setNumeroContrat("012345678");
        uccr.setGinInd("123456789123");
        uccr.setType("U");

        BusinessRole contrat = new BusinessRole();
        contrat.setCleRole(123987550);
        contrat.setNumeroContrat("98765432131212345678");
        contrat.setGinInd("123456789123");
        contrat.setType("C");

        BusinessRole doctor = new BusinessRole();
        doctor.setCleRole(123987551);
        doctor.setNumeroContrat("9876543214");
        doctor.setGinInd("123456789123");
        doctor.setType("D");

        return List.of(traveler, uccr, contrat, doctor);
    }

    //This one is used to allow insertion of new BusinessRole in DB
    public static Individu buildIndividualInd() {
        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Individu res = new Individu();

        res.setDateNaissance(Date.from(LocalDate.of(1960, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        res.setCivilite("MR");
        res.setNonFusionnable("N");
        res.setTierUtiliseCommePiege("N");
        res.setPrenomTypo1("JOHN");
        res.setPrenom("JOHN");
        res.setNomTypo1("SMITH");
        res.setNom("SMITH");
        res.setAliasPrenom("JOHN");
        res.setSecondPrenom("MICHAEL");
        res.setAliasNom1("SMITH");
        res.setSexe("M");
        res.setGin("123456789123");
        res.setType("I");
        res.setStatutIndividu("V");
        res.setCodeTitre("CJU");
        res.setVersion(3);
        res.setIdentifiantPersonnel("1020014902");
        res.setDateCreation(date);
        res.setDateModification(date);
        res.setSignatureCreation("WEB");
        res.setSignatureModification("WEB");
        res.setSiteCreation("ISI");
        res.setSiteModification("ISI");

        return res;
    }
}
