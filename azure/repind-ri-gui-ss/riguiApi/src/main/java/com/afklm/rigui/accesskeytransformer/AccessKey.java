package com.afklm.rigui.accesskeytransformer;

import java.io.Serializable;

/**
 * AccessKey
 * 
 * Allows fine grained authorization
 * 
 * @author m405991
 *
 */
public class AccessKey implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private String key = "";
    private String idProfil = "";
    private String type = "";
    private String value = "";
    

    /**
     * Empty constructor
     */
    public AccessKey() {
        //Empty Constructor
    }
    
    
	/**
	 * Constructor
	 * @param pAccessKey The acces key
	 * @param pIdProfil The Id profil
	 * @param pType the type
	 * @param pValue the value
	 */
    public AccessKey(String pAccessKey, String pIdProfil, String pType, String pValue) {
        this.key = pAccessKey;
        this.idProfil = pIdProfil;
        this.type = pType;
        this.value = pValue;
    }
    
    
    public String getIdProfil() {
        return idProfil;
    }


    public void setIdProfil(String idProfil) {
        this.idProfil = idProfil;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public String getAccessKey() {
        return key;
    }
    public void setAccessKey(String accessKey) {
        this.key = accessKey;
    }
}
