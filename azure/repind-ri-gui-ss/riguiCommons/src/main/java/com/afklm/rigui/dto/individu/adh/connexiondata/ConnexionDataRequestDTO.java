package com.afklm.rigui.dto.individu.adh.connexiondata;

/*PROTECTED REGION ID(_4dKtwC3rEeC7hbdMKof7lA i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : ConnexionDataRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ConnexionDataRequestDTO  {
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * personalQuestion
     */
    private String personalQuestion;
        
        
    /**
     * personalAnswer
     */
    private String personalAnswer;
        
        
    /**
     * password
     */
    private String password;
        
        
    /**
     * initialPasswordFlag
     */
    private String initialPasswordFlag;
        
        
    /**
     * connexionid
     */
    private Set<ConnexionIdDTO> connexionid;
        

    /*PROTECTED REGION ID(_4dKtwC3rEeC7hbdMKof7lA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ConnexionDataRequestDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     * @param pPersonalQuestion personalQuestion
     * @param pPersonalAnswer personalAnswer
     * @param pPassword password
     * @param pInitialPasswordFlag initialPasswordFlag
     */
    public ConnexionDataRequestDTO(String pGin, String pPersonalQuestion, String pPersonalAnswer, String pPassword, String pInitialPasswordFlag) {
        this.gin = pGin;
        this.personalQuestion = pPersonalQuestion;
        this.personalAnswer = pPersonalAnswer;
        this.password = pPassword;
        this.initialPasswordFlag = pInitialPasswordFlag;
    }

    /**
     *
     * @return connexionid
     */
    public Set<ConnexionIdDTO> getConnexionid() {
        return this.connexionid;
    }

    /**
     *
     * @param pConnexionid connexionid value
     */
    public void setConnexionid(Set<ConnexionIdDTO> pConnexionid) {
        this.connexionid = pConnexionid;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return initialPasswordFlag
     */
    public String getInitialPasswordFlag() {
        return this.initialPasswordFlag;
    }

    /**
     *
     * @param pInitialPasswordFlag initialPasswordFlag value
     */
    public void setInitialPasswordFlag(String pInitialPasswordFlag) {
        this.initialPasswordFlag = pInitialPasswordFlag;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     *
     * @param pPassword password value
     */
    public void setPassword(String pPassword) {
        this.password = pPassword;
    }

    /**
     *
     * @return personalAnswer
     */
    public String getPersonalAnswer() {
        return this.personalAnswer;
    }

    /**
     *
     * @param pPersonalAnswer personalAnswer value
     */
    public void setPersonalAnswer(String pPersonalAnswer) {
        this.personalAnswer = pPersonalAnswer;
    }

    /**
     *
     * @return personalQuestion
     */
    public String getPersonalQuestion() {
        return this.personalQuestion;
    }

    /**
     *
     * @param pPersonalQuestion personalQuestion value
     */
    public void setPersonalQuestion(String pPersonalQuestion) {
        this.personalQuestion = pPersonalQuestion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_4dKtwC3rEeC7hbdMKof7lA) ENABLED START*/
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
            .append("gin", getGin())
            .append("personalQuestion", getPersonalQuestion())
            .append("personalAnswer", getPersonalAnswer())
            .append("password", getPassword())
            .append("initialPasswordFlag", getInitialPasswordFlag())
            .toString();
    }

    /*PROTECTED REGION ID(_4dKtwC3rEeC7hbdMKof7lA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
