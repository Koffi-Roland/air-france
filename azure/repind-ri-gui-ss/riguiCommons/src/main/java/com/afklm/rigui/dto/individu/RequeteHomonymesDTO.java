package com.afklm.rigui.dto.individu;

/*PROTECTED REGION ID(_LECaoKYvEeCKrNoEFaUiPg i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : RequeteHomonymesDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RequeteHomonymesDTO  {
        
    /**
     * codeAppliMetier
     */
    private String codeAppliMetier;
        
        
    /**
     * typeRechIndividus
     */
    private String typeRechIndividus;
        
        
    /**
     * nom
     */
    private String nom;
        
        
    /**
     * typeRechNom
     */
    private String typeRechNom;
        
        
    /**
     * prenom
     */
    private String prenom;
        
        
    /**
     * typeRechPrenom
     */
    private String typeRechPrenom;
        
        
    /**
     * civilite
     */
    private String civilite;
        
        
    /**
     * sexe
     */
    private String sexe;
        
        
    /**
     * dateNaissance
     */
    private Date dateNaissance;
        
        
    /**
     * noRue
     */
    private String noRue;
        
        
    /**
     * complementAdresse
     */
    private String complementAdresse;
        
        
    /**
     * localite
     */
    private String localite;
        
        
    /**
     * codePostal
     */
    private String codePostal;
        
        
    /**
     * typeRechCodePostal
     */
    private String typeRechCodePostal;
        
        
    /**
     * ville
     */
    private String ville;
        
        
    /**
     * typeRechVille
     */
    private String typeRechVille;
        
        
    /**
     * codeProvince
     */
    private String codeProvince;
        
        
    /**
     * codePays
     */
    private String codePays;
        
        
    /**
     * telephone
     */
    private String telephone;
        
        
    /**
     * typeRechTelephone
     */
    private String typeRechTelephone;
        
        
    /**
     * agenceIATA
     */
    private String agenceIATA;
        
        
    /**
     * restriction
     */
    private String restriction;
        
        
    /**
     * email
     */
    private String email;
        

    /*PROTECTED REGION ID(_LECaoKYvEeCKrNoEFaUiPg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RequeteHomonymesDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCodeAppliMetier codeAppliMetier
     * @param pTypeRechIndividus typeRechIndividus
     * @param pNom nom
     * @param pTypeRechNom typeRechNom
     * @param pPrenom prenom
     * @param pTypeRechPrenom typeRechPrenom
     * @param pCivilite civilite
     * @param pSexe sexe
     * @param pDateNaissance dateNaissance
     * @param pNoRue noRue
     * @param pComplementAdresse complementAdresse
     * @param pLocalite localite
     * @param pCodePostal codePostal
     * @param pTypeRechCodePostal typeRechCodePostal
     * @param pVille ville
     * @param pTypeRechVille typeRechVille
     * @param pCodeProvince codeProvince
     * @param pCodePays codePays
     * @param pTelephone telephone
     * @param pTypeRechTelephone typeRechTelephone
     * @param pAgenceIATA agenceIATA
     * @param pRestriction restriction
     * @param pEmail email
     */
    public RequeteHomonymesDTO(String pCodeAppliMetier, String pTypeRechIndividus, String pNom, String pTypeRechNom, String pPrenom, String pTypeRechPrenom, String pCivilite, String pSexe, Date pDateNaissance, String pNoRue, String pComplementAdresse, String pLocalite, String pCodePostal, String pTypeRechCodePostal, String pVille, String pTypeRechVille, String pCodeProvince, String pCodePays, String pTelephone, String pTypeRechTelephone, String pAgenceIATA, String pRestriction, String pEmail) {
        this.codeAppliMetier = pCodeAppliMetier;
        this.typeRechIndividus = pTypeRechIndividus;
        this.nom = pNom;
        this.typeRechNom = pTypeRechNom;
        this.prenom = pPrenom;
        this.typeRechPrenom = pTypeRechPrenom;
        this.civilite = pCivilite;
        this.sexe = pSexe;
        this.dateNaissance = pDateNaissance;
        this.noRue = pNoRue;
        this.complementAdresse = pComplementAdresse;
        this.localite = pLocalite;
        this.codePostal = pCodePostal;
        this.typeRechCodePostal = pTypeRechCodePostal;
        this.ville = pVille;
        this.typeRechVille = pTypeRechVille;
        this.codeProvince = pCodeProvince;
        this.codePays = pCodePays;
        this.telephone = pTelephone;
        this.typeRechTelephone = pTypeRechTelephone;
        this.agenceIATA = pAgenceIATA;
        this.restriction = pRestriction;
        this.email = pEmail;
    }

    /**
     *
     * @return agenceIATA
     */
    public String getAgenceIATA() {
        return this.agenceIATA;
    }

    /**
     *
     * @param pAgenceIATA agenceIATA value
     */
    public void setAgenceIATA(String pAgenceIATA) {
        this.agenceIATA = pAgenceIATA;
    }

    /**
     *
     * @return civilite
     */
    public String getCivilite() {
        return this.civilite;
    }

    /**
     *
     * @param pCivilite civilite value
     */
    public void setCivilite(String pCivilite) {
        this.civilite = pCivilite;
    }

    /**
     *
     * @return codeAppliMetier
     */
    public String getCodeAppliMetier() {
        return this.codeAppliMetier;
    }

    /**
     *
     * @param pCodeAppliMetier codeAppliMetier value
     */
    public void setCodeAppliMetier(String pCodeAppliMetier) {
        this.codeAppliMetier = pCodeAppliMetier;
    }

    /**
     *
     * @return codePays
     */
    public String getCodePays() {
        return this.codePays;
    }

    /**
     *
     * @param pCodePays codePays value
     */
    public void setCodePays(String pCodePays) {
        this.codePays = pCodePays;
    }

    /**
     *
     * @return codePostal
     */
    public String getCodePostal() {
        return this.codePostal;
    }

    /**
     *
     * @param pCodePostal codePostal value
     */
    public void setCodePostal(String pCodePostal) {
        this.codePostal = pCodePostal;
    }

    /**
     *
     * @return codeProvince
     */
    public String getCodeProvince() {
        return this.codeProvince;
    }

    /**
     *
     * @param pCodeProvince codeProvince value
     */
    public void setCodeProvince(String pCodeProvince) {
        this.codeProvince = pCodeProvince;
    }

    /**
     *
     * @return complementAdresse
     */
    public String getComplementAdresse() {
        return this.complementAdresse;
    }

    /**
     *
     * @param pComplementAdresse complementAdresse value
     */
    public void setComplementAdresse(String pComplementAdresse) {
        this.complementAdresse = pComplementAdresse;
    }

    /**
     *
     * @return dateNaissance
     */
    public Date getDateNaissance() {
        return this.dateNaissance;
    }

    /**
     *
     * @param pDateNaissance dateNaissance value
     */
    public void setDateNaissance(Date pDateNaissance) {
        this.dateNaissance = pDateNaissance;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param pEmail email value
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }

    /**
     *
     * @return localite
     */
    public String getLocalite() {
        return this.localite;
    }

    /**
     *
     * @param pLocalite localite value
     */
    public void setLocalite(String pLocalite) {
        this.localite = pLocalite;
    }

    /**
     *
     * @return noRue
     */
    public String getNoRue() {
        return this.noRue;
    }

    /**
     *
     * @param pNoRue noRue value
     */
    public void setNoRue(String pNoRue) {
        this.noRue = pNoRue;
    }

    /**
     *
     * @return nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     *
     * @param pNom nom value
     */
    public void setNom(String pNom) {
        this.nom = pNom;
    }

    /**
     *
     * @return prenom
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     *
     * @param pPrenom prenom value
     */
    public void setPrenom(String pPrenom) {
        this.prenom = pPrenom;
    }

    /**
     *
     * @return restriction
     */
    public String getRestriction() {
        return this.restriction;
    }

    /**
     *
     * @param pRestriction restriction value
     */
    public void setRestriction(String pRestriction) {
        this.restriction = pRestriction;
    }

    /**
     *
     * @return sexe
     */
    public String getSexe() {
        return this.sexe;
    }

    /**
     *
     * @param pSexe sexe value
     */
    public void setSexe(String pSexe) {
        this.sexe = pSexe;
    }

    /**
     *
     * @return telephone
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     *
     * @param pTelephone telephone value
     */
    public void setTelephone(String pTelephone) {
        this.telephone = pTelephone;
    }

    /**
     *
     * @return typeRechCodePostal
     */
    public String getTypeRechCodePostal() {
        return this.typeRechCodePostal;
    }

    /**
     *
     * @param pTypeRechCodePostal typeRechCodePostal value
     */
    public void setTypeRechCodePostal(String pTypeRechCodePostal) {
        this.typeRechCodePostal = pTypeRechCodePostal;
    }

    /**
     *
     * @return typeRechIndividus
     */
    public String getTypeRechIndividus() {
        return this.typeRechIndividus;
    }

    /**
     *
     * @param pTypeRechIndividus typeRechIndividus value
     */
    public void setTypeRechIndividus(String pTypeRechIndividus) {
        this.typeRechIndividus = pTypeRechIndividus;
    }

    /**
     *
     * @return typeRechNom
     */
    public String getTypeRechNom() {
        return this.typeRechNom;
    }

    /**
     *
     * @param pTypeRechNom typeRechNom value
     */
    public void setTypeRechNom(String pTypeRechNom) {
        this.typeRechNom = pTypeRechNom;
    }

    /**
     *
     * @return typeRechPrenom
     */
    public String getTypeRechPrenom() {
        return this.typeRechPrenom;
    }

    /**
     *
     * @param pTypeRechPrenom typeRechPrenom value
     */
    public void setTypeRechPrenom(String pTypeRechPrenom) {
        this.typeRechPrenom = pTypeRechPrenom;
    }

    /**
     *
     * @return typeRechTelephone
     */
    public String getTypeRechTelephone() {
        return this.typeRechTelephone;
    }

    /**
     *
     * @param pTypeRechTelephone typeRechTelephone value
     */
    public void setTypeRechTelephone(String pTypeRechTelephone) {
        this.typeRechTelephone = pTypeRechTelephone;
    }

    /**
     *
     * @return typeRechVille
     */
    public String getTypeRechVille() {
        return this.typeRechVille;
    }

    /**
     *
     * @param pTypeRechVille typeRechVille value
     */
    public void setTypeRechVille(String pTypeRechVille) {
        this.typeRechVille = pTypeRechVille;
    }

    /**
     *
     * @return ville
     */
    public String getVille() {
        return this.ville;
    }

    /**
     *
     * @param pVille ville value
     */
    public void setVille(String pVille) {
        this.ville = pVille;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_LECaoKYvEeCKrNoEFaUiPg) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("codeAppliMetier", getCodeAppliMetier())
            .append("typeRechIndividus", getTypeRechIndividus())
            .append("nom", getNom())
            .append("typeRechNom", getTypeRechNom())
            .append("prenom", getPrenom())
            .append("typeRechPrenom", getTypeRechPrenom())
            .append("civilite", getCivilite())
            .append("sexe", getSexe())
            .append("dateNaissance", getDateNaissance())
            .append("noRue", getNoRue())
            .append("complementAdresse", getComplementAdresse())
            .append("localite", getLocalite())
            .append("codePostal", getCodePostal())
            .append("typeRechCodePostal", getTypeRechCodePostal())
            .append("ville", getVille())
            .append("typeRechVille", getTypeRechVille())
            .append("codeProvince", getCodeProvince())
            .append("codePays", getCodePays())
            .append("telephone", getTelephone())
            .append("typeRechTelephone", getTypeRechTelephone())
            .append("agenceIATA", getAgenceIATA())
            .append("restriction", getRestriction())
            .append("email", getEmail())
            .toString();
    }

    /*PROTECTED REGION ID(_LECaoKYvEeCKrNoEFaUiPg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
