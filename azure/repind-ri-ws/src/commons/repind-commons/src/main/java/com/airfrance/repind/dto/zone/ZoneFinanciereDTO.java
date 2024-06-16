package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_hs9CwEANEeS2wtWjh0gEaw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : ZoneFinanciereDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ZoneFinanciereDTO extends ZoneDecoupDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7677333027615109020L;


	/**
     * codePays
     */
    private String codePays;
        
        
    /**
     * codeUF
     */
    private String codeUF;
        
        
    /**
     * zoneGeo
     */
    private String zoneGeo;
        
        
    /**
     * codeVille
     */
    private String codeVille;
        

    /*PROTECTED REGION ID(_hs9CwEANEeS2wtWjh0gEaw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ZoneFinanciereDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCodePays codePays
     * @param pCodeUF codeUF
     * @param pZoneGeo zoneGeo
     * @param pCodeVille codeVille
     */
    public ZoneFinanciereDTO(String pCodePays, String pCodeUF, String pZoneGeo, String pCodeVille) {
        this.codePays = pCodePays;
        this.codeUF = pCodeUF;
        this.zoneGeo = pZoneGeo;
        this.codeVille = pCodeVille;
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
     * @return codeUF
     */
    public String getCodeUF() {
        return this.codeUF;
    }

    /**
     *
     * @param pCodeUF codeUF value
     */
    public void setCodeUF(String pCodeUF) {
        this.codeUF = pCodeUF;
    }

    /**
     *
     * @return codeVille
     */
    public String getCodeVille() {
        return this.codeVille;
    }

    /**
     *
     * @param pCodeVille codeVille value
     */
    public void setCodeVille(String pCodeVille) {
        this.codeVille = pCodeVille;
    }

    /**
     *
     * @return zoneGeo
     */
    public String getZoneGeo() {
        return this.zoneGeo;
    }

    /**
     *
     * @param pZoneGeo zoneGeo value
     */
    public void setZoneGeo(String pZoneGeo) {
        this.zoneGeo = pZoneGeo;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_hs9CwEANEeS2wtWjh0gEaw) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("codePays=").append(getCodePays());
        buffer.append(",");
        buffer.append("codeUF=").append(getCodeUF());
        buffer.append(",");
        buffer.append("zoneGeo=").append(getZoneGeo());
        buffer.append(",");
        buffer.append("codeVille=").append(getCodeVille());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_hs9CwEANEeS2wtWjh0gEaw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
