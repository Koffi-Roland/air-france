package com.airfrance.repind.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_-kDD8DRjEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : HabilitationDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class HabilitationDTO  {
        
    /**
     * userId
     */
    private String userId;
        
        
    /**
     * pwd
     */
    private String pwd;
        
        
    /**
     * qalPwd
     */
    private String qalPwd;
        
        
    /**
     * keyCrypt
     */
    private String keyCrypt;
        
        
    /**
     * newPwd
     */
    private String newPwd;
        
        
    /**
     * role
     */
    private String role;
        

    /*PROTECTED REGION ID(_-kDD8DRjEeCc7ZsKsK1lbQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public HabilitationDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pUserId userId
     * @param pPwd pwd
     * @param pQalPwd qalPwd
     * @param pKeyCrypt keyCrypt
     * @param pNewPwd newPwd
     * @param pRole role
     */
    public HabilitationDTO(String pUserId, String pPwd, String pQalPwd, String pKeyCrypt, String pNewPwd, String pRole) {
        this.userId = pUserId;
        this.pwd = pPwd;
        this.qalPwd = pQalPwd;
        this.keyCrypt = pKeyCrypt;
        this.newPwd = pNewPwd;
        this.role = pRole;
    }

    /**
     *
     * @return keyCrypt
     */
    public String getKeyCrypt() {
        return this.keyCrypt;
    }

    /**
     *
     * @param pKeyCrypt keyCrypt value
     */
    public void setKeyCrypt(String pKeyCrypt) {
        this.keyCrypt = pKeyCrypt;
    }

    /**
     *
     * @return newPwd
     */
    public String getNewPwd() {
        return this.newPwd;
    }

    /**
     *
     * @param pNewPwd newPwd value
     */
    public void setNewPwd(String pNewPwd) {
        this.newPwd = pNewPwd;
    }

    /**
     *
     * @return pwd
     */
    public String getPwd() {
        return this.pwd;
    }

    /**
     *
     * @param pPwd pwd value
     */
    public void setPwd(String pPwd) {
        this.pwd = pPwd;
    }

    /**
     *
     * @return qalPwd
     */
    public String getQalPwd() {
        return this.qalPwd;
    }

    /**
     *
     * @param pQalPwd qalPwd value
     */
    public void setQalPwd(String pQalPwd) {
        this.qalPwd = pQalPwd;
    }

    /**
     *
     * @return role
     */
    public String getRole() {
        return this.role;
    }

    /**
     *
     * @param pRole role value
     */
    public void setRole(String pRole) {
        this.role = pRole;
    }

    /**
     *
     * @return userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     *
     * @param pUserId userId value
     */
    public void setUserId(String pUserId) {
        this.userId = pUserId;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_-kDD8DRjEeCc7ZsKsK1lbQ) ENABLED START*/
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
            .append("userId", getUserId())
            .append("pwd", getPwd())
            .append("qalPwd", getQalPwd())
            .append("keyCrypt", getKeyCrypt())
            .append("newPwd", getNewPwd())
            .append("role", getRole())
            .toString();
    }

    /*PROTECTED REGION ID(_-kDD8DRjEeCc7ZsKsK1lbQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
