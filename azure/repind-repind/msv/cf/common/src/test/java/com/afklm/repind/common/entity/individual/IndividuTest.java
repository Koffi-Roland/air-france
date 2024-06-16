package com.afklm.repind.common.entity.individual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
class IndividuTest {

    @Test
    void testEquals() {
        Individu individu = new Individu();
        individu.setGin("123456789");
        Individu individu2 = new Individu();
        individu2.setGin("123456789");
        Individu individu3 = new Individu();
        individu3.setGin("123456782229");
        Individu individu4 = new Individu();
        assertEquals(individu, individu);
        assertEquals(individu, individu2);
        assertNotEquals(individu, individu4);
        assertNotEquals(null, individu);
        assertNotEquals(individu, individu3);
    }

    @Test
    void getGin() {
        Individu individu = new Individu();
        individu.setGin("123456789");
        assertEquals("123456789", individu.getGin());
    }

    @Test
    void getVersion() {
        Individu individu = new Individu();
        individu.setVersion(123);
        assertEquals(123, individu.getVersion());
    }

    @Test
    void getCivilite() {
        Individu individu = new Individu();
        individu.setCivilite("123456789");
        assertEquals("123456789", individu.getCivilite());
    }

    @Test
    void getMotDePasse() {
        Individu individu = new Individu();
        individu.setMotDePasse("123456789");
        assertEquals("123456789", individu.getMotDePasse());
    }

    @Test
    void getNom() {
        Individu individu = new Individu();
        individu.setGin("123456789");
        assertEquals("123456789", individu.getGin());
    }

    @Test
    void getAlias() {
        Individu individu = new Individu();
        individu.setAlias("123456789");
        assertEquals("123456789", individu.getAlias());
    }

    @Test
    void getPrenom() {
        Individu individu = new Individu();
        individu.setPrenom("123456789");
        assertEquals("123456789", individu.getPrenom());
    }

    @Test
    void getSecondPrenom() {
        Individu individu = new Individu();
        individu.setSecondPrenom("123456789");
        assertEquals("123456789", individu.getSecondPrenom());
    }

    @Test
    void getAliasPrenom() {
        Individu individu = new Individu();
        individu.setAliasPrenom("123456789");
        assertEquals("123456789", individu.getAliasPrenom());
    }

    @Test
    void getSexe() {
        Individu individu = new Individu();
        individu.setSexe("M");
        assertEquals("M", individu.getSexe());
    }

    @Test
    void getIdentifiantPersonnel() {
        Individu individu = new Individu();
        individu.setIdentifiantPersonnel("123456789");
        assertEquals("123456789", individu.getIdentifiantPersonnel());
    }

    @Test
    void getDateNaissance() {
        Date date = new Date();
        Individu individu = new Individu();
        individu.setDateNaissance(date);
        assertEquals(date, individu.getDateNaissance());
    }

    @Test
    void getStatutIndividu() {
        Individu individu = new Individu();
        individu.setStatutIndividu("V");
        assertEquals("V", individu.getStatutIndividu());
    }

    @Test
    void getCodeTitre() {
        Individu individu = new Individu();
        individu.setCodeTitre("123456789");
        assertEquals("123456789", individu.getCodeTitre());
    }

    @Test
    void getNationalite() {
        Individu individu = new Individu();
        individu.setNationalite("123456789");
        assertEquals("123456789", individu.getNationalite());
    }

    @Test
    void getAutreNationalite() {
        Individu individu = new Individu();
        individu.setAutreNationalite("123456789");
        assertEquals("123456789", individu.getAutreNationalite());
    }

    @Test
    void getNonFusionnable() {
        Individu individu = new Individu();
        individu.setNonFusionnable("123456789");
        assertEquals("123456789", individu.getNonFusionnable());
    }

    @Test
    void getSiteCreation() {
        Individu individu = new Individu();
        individu.setSiteCreation("123456789");
        assertEquals("123456789", individu.getSiteCreation());
    }

    @Test
    void getSignatureCreation() {
        Individu individu = new Individu();
        individu.setSignatureCreation("123456789");
        assertEquals("123456789", individu.getSignatureCreation());
    }

    @Test
    void getDateCreation() {
        Date date = new Date();
        Individu individu = new Individu();
        individu.setDateCreation(date);
        assertEquals(date, individu.getDateCreation());
    }

    @Test
    void getSiteModification() {
        Individu individu = new Individu();
        individu.setSiteModification("123456789");
        assertEquals("123456789", individu.getSiteModification());
    }

    @Test
    void getSignatureModification() {
        Individu individu = new Individu();
        individu.setSignatureModification("123456789");
        assertEquals("123456789", individu.getSignatureModification());
    }

    @Test
    void getDateModification() {
        Date date = new Date();
        Individu individu = new Individu();
        individu.setDateModification(date);
        assertEquals(date, individu.getDateModification());
    }

    @Test
    void getSiteFraudeur() {
        Individu individu = new Individu();
        individu.setSiteFraudeur("123456789");
        assertEquals("123456789", individu.getSiteFraudeur());
    }

    @Test
    void getSignatureFraudeur() {
        Individu individu = new Individu();
        individu.setSignatureFraudeur("123456789");
        assertEquals("123456789", individu.getSignatureFraudeur());
    }

    @Test
    void getDateModifFraudeur() {
        Date date = new Date();
        Individu individu = new Individu();
        individu.setDateModifFraudeur(date);
        assertEquals(date, individu.getDateModifFraudeur());
    }

    @Test
    void getSiteMotDePasse() {
        Individu individu = new Individu();
        individu.setSiteMotDePasse("123456789");
        assertEquals("123456789", individu.getSiteMotDePasse());
    }

    @Test
    void getSignatureMotDePasse() {
        Individu individu = new Individu();
        individu.setSignatureMotDePasse("123456789");
        assertEquals("123456789", individu.getSignatureMotDePasse());
    }

    @Test
    void getDateModifMotDePasse() {
        Date date = new Date();
        Individu individu = new Individu();
        individu.setDateModifMotDePasse(date);
        assertEquals(date, individu.getDateModifMotDePasse());
    }

    @Test
    void getFraudeurCarteBancaire() {
        Individu individu = new Individu();
        individu.setFraudeurCarteBancaire("123456789");
        assertEquals("123456789", individu.getFraudeurCarteBancaire());
    }

    @Test
    void getTierUtiliseCommePiege() {
        Individu individu = new Individu();
        individu.setTierUtiliseCommePiege("123456789");
        assertEquals("123456789", individu.getTierUtiliseCommePiege());
    }

    @Test
    void getAliasNom1() {
        Individu individu = new Individu();
        individu.setAliasNom1("123456789");
        assertEquals("123456789", individu.getAliasNom1());
    }

    @Test
    void getAliasNom2() {
        Individu individu = new Individu();
        individu.setAliasNom2("123456789");
        assertEquals("123456789", individu.getAliasNom2());
    }

    @Test
    void getAliasPrenom1() {
        Individu individu = new Individu();
        individu.setAliasPrenom1("123456789");
        assertEquals("123456789", individu.getAliasPrenom1());
    }

    @Test
    void getAliasPrenom2() {
        Individu individu = new Individu();
        individu.setAliasPrenom2("123456789");
        assertEquals("123456789", individu.getAliasPrenom2());
    }

    @Test
    void getAliasCivilite1() {
        Individu individu = new Individu();
        individu.setAliasCivilite1("123456789");
        assertEquals("123456789", individu.getAliasCivilite1());
    }

    @Test
    void getAliasCivilite2() {
        Individu individu = new Individu();
        individu.setAliasCivilite2("123456789");
        assertEquals("123456789", individu.getAliasCivilite2());
    }

    @Test
    void getIndicNomPrenom() {
        Individu individu = new Individu();
        individu.setIndicNomPrenom("123456789");
        assertEquals("123456789", individu.getIndicNomPrenom());
    }

    @Test
    void getIndicNom() {
        Individu individu = new Individu();
        individu.setIndicNom("123456789");
        assertEquals("123456789", individu.getIndicNom());
    }

    @Test
    void getIndcons() {
        Individu individu = new Individu();
        individu.setIndcons("123456789");
        assertEquals("123456789", individu.getIndcons());
    }

    @Test
    void getGinFusion() {
        Individu individu = new Individu();
        individu.setGinFusion("123456789");
        assertEquals("123456789", individu.getGinFusion());
    }

    @Test
    void getDateFusion() {
        Date date = new Date();
        Individu individu = new Individu();
        individu.setDateFusion(date);
        assertEquals(date, individu.getDateFusion());
    }

    @Test
    void getProvAmex() {
        Individu individu = new Individu();
        individu.setProvAmex("123456789");
        assertEquals("123456789", individu.getProvAmex());
    }

    @Test
    void getCieGest() {
        Individu individu = new Individu();
        individu.setCieGest("123456789");
        assertEquals("123456789", individu.getCieGest());
    }

    @Test
    void getNomTypo1() {
        Individu individu = new Individu();
        individu.setNomTypo1("123456789");
        assertEquals("123456789", individu.getNomTypo1());
    }

    @Test
    void getPrenomTypo1() {
        Individu individu = new Individu();
        individu.setPrenomTypo1("123456789");
        assertEquals("123456789", individu.getPrenomTypo1());
    }

    @Test
    void getNomTypo2() {
        Individu individu = new Individu();
        individu.setNomTypo2("123456789");
        assertEquals("123456789", individu.getNomTypo2());
    }

    @Test
    void getPrenomTypo2() {
        Individu individu = new Individu();
        individu.setPrenomTypo2("123456789");
        assertEquals("123456789", individu.getPrenomTypo2());
    }

    @Test
    void getType() {
        Individu individu = new Individu();
        individu.setType("123456789");
        assertEquals("123456789", individu.getType());
    }

    @Test
    void testToString() {
        Individu individu = new Individu();
        individu.setAliasPrenom("123456789");
        assertEquals("123456789", individu.getAliasPrenom());
    }
}