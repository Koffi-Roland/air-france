package com.afklm.repind.common.entity.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
class RoleContractTest {

    @Test
    void testEquals() {
        RoleContract roleContract = new RoleContract();
        roleContract.setSrin("123456789");
        RoleContract roleContract2 = new RoleContract();
        roleContract2.setSrin("123456789");
        RoleContract roleContract3 = new RoleContract();
        roleContract3.setSrin("123456782229");
        RoleContract roleContract4 = new RoleContract();
        assertEquals(roleContract, roleContract);
        assertEquals(roleContract, roleContract2);
        assertNotEquals(roleContract, roleContract4);
        assertNotEquals(null, roleContract);
        assertNotEquals(roleContract, roleContract3);
    }

    @Test
    void getSrin() {
        RoleContract roleContract = new RoleContract();
        roleContract.setSrin("123456");
        assertEquals("123456", roleContract.getSrin());
    }

    @Test
    void getVersion() {
        RoleContract roleContract = new RoleContract();
        roleContract.setVersion(123);
        assertEquals(123, roleContract.getVersion());
    }

    @Test
    void getNumeroContrat() {
        RoleContract roleContract = new RoleContract();
        roleContract.setNumeroContrat("123456");
        assertEquals("123456", roleContract.getNumeroContrat());
    }

    @Test
    void getEtat() {
        RoleContract roleContract = new RoleContract();
        roleContract.setEtat("123456");
        assertEquals("123456", roleContract.getEtat());
    }

    @Test
    void getTypeContrat() {
        RoleContract roleContract = new RoleContract();
        roleContract.setTypeContrat("123456");
        assertEquals("123456", roleContract.getTypeContrat());
    }

    @Test
    void getSousType() {
        RoleContract roleContract = new RoleContract();
        roleContract.setSousType("123456");
        assertEquals("123456", roleContract.getSousType());
    }

    @Test
    void getTier() {
        RoleContract roleContract = new RoleContract();
        roleContract.setTier("123456");
        assertEquals("123456", roleContract.getTier());
    }

    @Test
    void getCodeCompagnie() {
        RoleContract roleContract = new RoleContract();
        roleContract.setCodeCompagnie("123456");
        assertEquals("123456", roleContract.getCodeCompagnie());
    }

    @Test
    void getVersionProduit() {
        RoleContract roleContract = new RoleContract();
        roleContract.setVersionProduit(1);
        assertEquals(1, roleContract.getVersionProduit());
    }

    @Test
    void getDateFinValidite() {
        Date date = new Date();
        RoleContract roleContract = new RoleContract();
        roleContract.setDateFinValidite(date);
        assertEquals(date, roleContract.getDateFinValidite());
    }

    @Test
    void getDateDebutValidite() {
        Date date = new Date();
        RoleContract roleContract = new RoleContract();
        roleContract.setDateDebutValidite(date);
        assertEquals(date, roleContract.getDateDebutValidite());
    }

    @Test
    void getFamilleTraitement() {
        RoleContract roleContract = new RoleContract();
        roleContract.setFamilleTraitement("123");
        assertEquals("123", roleContract.getFamilleTraitement());
    }

    @Test
    void getFamilleProduit() {
        RoleContract roleContract = new RoleContract();
        roleContract.setFamilleProduit("123");
        assertEquals("123", roleContract.getFamilleProduit());
    }

    @Test
    void getCleRole() {
        RoleContract roleContract = new RoleContract();
        roleContract.setCleRole(123);
        assertEquals(123, roleContract.getCleRole());
    }

    @Test
    void getDateCreation() {
        Date date = new Date();
        RoleContract roleContract = new RoleContract();
        roleContract.setDateCreation(date);
        assertEquals(date, roleContract.getDateCreation());
    }

    @Test
    void getSignatureCreation() {
        RoleContract roleContract = new RoleContract();
        roleContract.setSignatureCreation("123");
        assertEquals("123", roleContract.getSignatureCreation());
    }

    @Test
    void getDateModification() {
        Date date = new Date();
        RoleContract roleContract = new RoleContract();
        roleContract.setDateModification(date);
        assertEquals(date, roleContract.getDateModification());
    }

    @Test
    void getSignatureModification() {
        RoleContract roleContract = new RoleContract();
        roleContract.setSignatureModification("123");
        assertEquals("123", roleContract.getSignatureModification());
    }

    @Test
    void getSiteCreation() {
        RoleContract roleContract = new RoleContract();
        roleContract.setSiteCreation("123");
        assertEquals("123", roleContract.getSiteCreation());
    }

    @Test
    void getSiteModification() {
        RoleContract roleContract = new RoleContract();
        roleContract.setSiteModification("123");
        assertEquals("123", roleContract.getSiteModification());
    }

    @Test
    void getAgenceIATA() {
        RoleContract roleContract = new RoleContract();
        roleContract.setAgenceIATA("123");
        assertEquals("123", roleContract.getAgenceIATA());
    }

    @Test
    void getIata() {
        RoleContract roleContract = new RoleContract();
        roleContract.setIata("123");
        assertEquals("123", roleContract.getIata());
    }

    @Test
    void getSourceAdhesion() {
        RoleContract roleContract = new RoleContract();
        roleContract.setSourceAdhesion("123");
        assertEquals("123", roleContract.getSourceAdhesion());
    }

    @Test
    void getPermissionPrime() {
        RoleContract roleContract = new RoleContract();
        roleContract.setPermissionPrime("123");
        assertEquals("123", roleContract.getPermissionPrime());
    }

    @Test
    void getSoldeMiles() {
        RoleContract roleContract = new RoleContract();
        roleContract.setSoldeMiles(123);
        assertEquals(123, roleContract.getSoldeMiles());
    }

    @Test
    void getMilesQualif() {
        RoleContract roleContract = new RoleContract();
        roleContract.setMilesQualif(123);
        assertEquals(123, roleContract.getMilesQualif());
    }

    @Test
    void getMilesQualifPrec() {
        RoleContract roleContract = new RoleContract();
        roleContract.setMilesQualifPrec(123);
        assertEquals(123, roleContract.getMilesQualifPrec());
    }

    @Test
    void getSegmentsQualif() {
        RoleContract roleContract = new RoleContract();
        roleContract.setSegmentsQualif(123);
        assertEquals(123, roleContract.getSegmentsQualif());
    }

    @Test
    void getSegmentsQualifPrec() {
        RoleContract roleContract = new RoleContract();
        roleContract.setSegmentsQualifPrec(123);
        assertEquals(123, roleContract.getSegmentsQualifPrec());
    }

    @Test
    void getCuscoCreated() {
        RoleContract roleContract = new RoleContract();
        roleContract.setCuscoCreated("123");
        assertEquals("123", roleContract.getCuscoCreated());
    }

    @Test
    void getMemberType() {
        RoleContract roleContract = new RoleContract();
        roleContract.setMemberType("123");
        assertEquals("123", roleContract.getMemberType());
    }
}