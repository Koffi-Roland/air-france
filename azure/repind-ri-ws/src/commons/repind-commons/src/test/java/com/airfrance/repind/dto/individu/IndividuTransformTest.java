/**
 * 
 */
package com.airfrance.repind.dto.individu;

import com.airfrance.repind.entity.individu.IndividuLight;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author t444761
 *
 */
public class IndividuTransformTest {

	/**
	 * Test method for
	 * {@link com.airfrance.repind.dto.individu.IndividuTransform#bo2DtoLight(com.airfrance.repind.entity.individu.IndividuLight)}
	 * .
	 */
	@Test
	public void testBo2DtoLight() {
		
		// Declare local variable
		IndividuLight individuLight = null;
		
		// Test with null input
		assertNull(IndividuTransform.bo2DtoLight(individuLight));
		
		// Test with normal input
		individuLight = new IndividuLight();
		individuLight.setSgin("123");
        individuLight.setType("T");
        individuLight.setVersion(1);
        individuLight.setCivilite("MR");
        individuLight.setMotDePasse("mdp");
        individuLight.setNom("nom");
        individuLight.setAlias("alias");
        individuLight.setPrenom("prenom");
        individuLight.setSecondPrenom("second");
        individuLight.setAliasPrenom("alias prenom");
        individuLight.setSexe("M");
        individuLight.setIdentifiantPersonnel("id");
        individuLight.setDateNaissance(new Date(123));
        individuLight.setStatutIndividu("V");
        individuLight.setCodeTitre("code");
        individuLight.setNationalite("TN");
        individuLight.setAutreNationalite("FR");
        individuLight.setNonFusionnable("Y");
        individuLight.setSiteCreation("site creat");
        individuLight.setSignatureCreation("signa crea");
        individuLight.setDateCreation(new Date(456));
        individuLight.setSiteModification("site modif");
        individuLight.setSignatureModification("signa modif");
        individuLight.setDateModification(new Date(789));
        individuLight.setSiteFraudeur("site fraude");
        individuLight.setSignatureFraudeur("signa fraude");
        individuLight.setDateModifFraudeur(new Date(123456));
        individuLight.setSiteMotDePasse("site mdp");
        individuLight.setSignatureMotDePasse("signa mdp");
        individuLight.setDateModifMotDePasse(new Date(456879));
        individuLight.setFraudeurCarteBancaire("fraude CB");
        individuLight.setTierUtiliseCommePiege("tiers piege");
        individuLight.setAliasNom1("alias nom 1");
        individuLight.setAliasNom2("alias nom 2");
        individuLight.setAliasPrenom1("alias prenom 1");
        individuLight.setAliasPrenom2("alias prenom 2");
        individuLight.setAliasCivilite1("MS");
        individuLight.setAliasCivilite2("MRS");
        individuLight.setIndicNomPrenom("indic nom prenom");
        individuLight.setIndicNom("indic nom");
        individuLight.setIndcons("ind cons");
        individuLight.setGinFusion("gin fusion");
        individuLight.setDateFusion(new Date(123456879));
        individuLight.setProvAmex("prov amex");
        individuLight.setCieGest("cie gest");
        
		IndividuDTO individuDTO = IndividuTransform.bo2DtoLight(individuLight);
		assertEquals(individuLight.getSgin(), individuDTO.getSgin());
		assertEquals(individuLight.getType(), individuDTO.getType());
		assertEquals(individuLight.getVersion(), individuDTO.getVersion());
		assertEquals(individuLight.getCivilite(), individuDTO.getCivilite());
		assertEquals(individuLight.getMotDePasse(), individuDTO.getMotDePasse());
		assertEquals(individuLight.getNom(), individuDTO.getNom());
		assertEquals(individuLight.getPrenom(), individuDTO.getPrenom());
		assertEquals(individuLight.getSecondPrenom(), individuDTO.getSecondPrenom());
		assertEquals(individuLight.getAliasPrenom(), individuDTO.getAliasPrenom());
		assertEquals(individuLight.getSexe(), individuDTO.getSexe());
		assertEquals(individuLight.getIdentifiantPersonnel(), individuDTO.getIdentifiantPersonnel());
		assertEquals(individuLight.getDateNaissance(), individuDTO.getDateNaissance());
		assertEquals(individuLight.getStatutIndividu(), individuDTO.getStatutIndividu());
		assertEquals(individuLight.getCodeTitre(), individuDTO.getCodeTitre());
		assertEquals(individuLight.getNationalite(), individuDTO.getNationalite());
		assertEquals(individuLight.getAutreNationalite(), individuDTO.getAutreNationalite());
		assertEquals(individuLight.getNonFusionnable(), individuDTO.getNonFusionnable());
		assertEquals(individuLight.getSiteCreation(), individuDTO.getSiteCreation());
		assertEquals(individuLight.getSignatureCreation(), individuDTO.getSignatureCreation());
		assertEquals(individuLight.getDateCreation(), individuDTO.getDateCreation());
		assertEquals(individuLight.getSiteModification(), individuDTO.getSiteModification());
		assertEquals(individuLight.getSignatureModification(), individuDTO.getSignatureModification());
		assertEquals(individuLight.getDateModification(), individuDTO.getDateModification());
		assertEquals(individuLight.getSiteFraudeur(), individuDTO.getSiteFraudeur());
		assertEquals(individuLight.getSignatureFraudeur(), individuDTO.getSignatureFraudeur());
		assertEquals(individuLight.getDateModifFraudeur(), individuDTO.getDateModifFraudeur());
		assertEquals(individuLight.getSiteMotDePasse(), individuDTO.getSiteMotDePasse());
		assertEquals(individuLight.getSignatureMotDePasse(), individuDTO.getSignatureMotDePasse());
		assertEquals(individuLight.getDateModifMotDePasse(), individuDTO.getDateModifMotDePasse());
		assertEquals(individuLight.getFraudeurCarteBancaire(), individuDTO.getFraudeurCarteBancaire());
		assertEquals(individuLight.getTierUtiliseCommePiege(), individuDTO.getTierUtiliseCommePiege());
		assertEquals(individuLight.getAliasNom1(), individuDTO.getAliasNom1());
		assertEquals(individuLight.getAliasNom2(), individuDTO.getAliasNom2());
		assertEquals(individuLight.getAliasPrenom1(), individuDTO.getAliasPrenom1());
		assertEquals(individuLight.getAliasPrenom2(), individuDTO.getAliasPrenom2());
		assertEquals(individuLight.getAliasCivilite1(), individuDTO.getAliasCivilite1());
		assertEquals(individuLight.getAliasCivilite2(), individuDTO.getAliasCivilite2());
		assertEquals(individuLight.getIndicNomPrenom(), individuDTO.getIndicNomPrenom());
		assertEquals(individuLight.getIndicNom(), individuDTO.getIndicNom());
		assertEquals(individuLight.getIndcons(), individuDTO.getIndcons());
		assertEquals(individuLight.getGinFusion(), individuDTO.getGinFusion());
		assertEquals(individuLight.getDateFusion(), individuDTO.getDateFusion());
		assertEquals(individuLight.getProvAmex(), individuDTO.getProvAmex());
		assertEquals(individuLight.getCieGest(), individuDTO.getCieGest());
	}

}
