package com.airfrance.repind.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1qTwCMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : AliasSICDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class AliasSICDTO  {
        
    /**
     * nom
     */
    private String nom;
        
        
    /**
     * prenom
     */
    private String prenom;
        
        
    /**
     * civilite
     */
    private String civilite;
        

    /*PROTECTED REGION ID(_b1qTwCMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public AliasSICDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pNom nom
     * @param pPrenom prenom
     * @param pCivilite civilite
     */
    public AliasSICDTO(String pNom, String pPrenom, String pCivilite) {
        this.nom = pNom;
        this.prenom = pPrenom;
        this.civilite = pCivilite;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1qTwCMUEeCWJOBY8f-ONQ) ENABLED START*/
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
            .append("nom", getNom())
            .append("prenom", getPrenom())
            .append("civilite", getCivilite())
            .toString();
    }

    /*PROTECTED REGION ID(_b1qTwCMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
