package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1qTziMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : UsageClientDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class UsageClientDTO  {
        
    /**
     * srin
     */
    private String srin;
        
        
    /**
     * metier
     */
    private String metier;
        
        
    /**
     * modifAutorisee
     */
    private String modifAutorisee;
        
        
    /**
     * dateModif
     */
    private Date dateModif;
        

    /*PROTECTED REGION ID(_b1qTziMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public UsageClientDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pSrin srin
     * @param pMetier metier
     * @param pModifAutorisee modifAutorisee
     * @param pDateModif dateModif
     */
    public UsageClientDTO(String pSrin, String pMetier, String pModifAutorisee, Date pDateModif) {
        this.srin = pSrin;
        this.metier = pMetier;
        this.modifAutorisee = pModifAutorisee;
        this.dateModif = pDateModif;
    }

    /**
     *
     * @return dateModif
     */
    public Date getDateModif() {
        return this.dateModif;
    }

    /**
     *
     * @param pDateModif dateModif value
     */
    public void setDateModif(Date pDateModif) {
        this.dateModif = pDateModif;
    }

    /**
     *
     * @return metier
     */
    public String getMetier() {
        return this.metier;
    }

    /**
     *
     * @param pMetier metier value
     */
    public void setMetier(String pMetier) {
        this.metier = pMetier;
    }

    /**
     *
     * @return modifAutorisee
     */
    public String getModifAutorisee() {
        return this.modifAutorisee;
    }

    /**
     *
     * @param pModifAutorisee modifAutorisee value
     */
    public void setModifAutorisee(String pModifAutorisee) {
        this.modifAutorisee = pModifAutorisee;
    }

    /**
     *
     * @return srin
     */
    public String getSrin() {
        return this.srin;
    }

    /**
     *
     * @param pSrin srin value
     */
    public void setSrin(String pSrin) {
        this.srin = pSrin;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1qTziMUEeCWJOBY8f-ONQ) ENABLED START*/
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
            .append("srin", getSrin())
            .append("metier", getMetier())
            .append("modifAutorisee", getModifAutorisee())
            .append("dateModif", getDateModif())
            .toString();
    }

    /*PROTECTED REGION ID(_b1qTziMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
