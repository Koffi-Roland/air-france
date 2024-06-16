package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_OgFr8LdgEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : ZoneCommDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ZoneCommDTO extends ZoneDecoupDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 8846271168604460731L;


	/**
     * zc1
     */
    private String zc1;
        
        
    /**
     * zc2
     */
    private String zc2;
        
        
    /**
     * zc3
     */
    private String zc3;
        
        
    /**
     * zc4
     */
    private String zc4;
        
        
    /**
     * zc5
     */
    private String zc5;
        
        
    /**
     * liensZv
     */
    private Set<LienZvZcDTO> liensZv;
        

    /*PROTECTED REGION ID(_OgFr8LdgEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ZoneCommDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pZc1 zc1
     * @param pZc2 zc2
     * @param pZc3 zc3
     * @param pZc4 zc4
     * @param pZc5 zc5
     */
    public ZoneCommDTO(String pZc1, String pZc2, String pZc3, String pZc4, String pZc5) {
        this.zc1 = pZc1;
        this.zc2 = pZc2;
        this.zc3 = pZc3;
        this.zc4 = pZc4;
        this.zc5 = pZc5;
    }

    /**
     *
     * @return liensZv
     */
    public Set<LienZvZcDTO> getLiensZv() {
        return this.liensZv;
    }

    /**
     *
     * @param pLiensZv liensZv value
     */
    public void setLiensZv(Set<LienZvZcDTO> pLiensZv) {
        this.liensZv = pLiensZv;
    }

    /**
     *
     * @return zc1
     */
    public String getZc1() {
        return this.zc1;
    }

    /**
     *
     * @param pZc1 zc1 value
     */
    public void setZc1(String pZc1) {
        this.zc1 = pZc1;
    }

    /**
     *
     * @return zc2
     */
    public String getZc2() {
        return this.zc2;
    }

    /**
     *
     * @param pZc2 zc2 value
     */
    public void setZc2(String pZc2) {
        this.zc2 = pZc2;
    }

    /**
     *
     * @return zc3
     */
    public String getZc3() {
        return this.zc3;
    }

    /**
     *
     * @param pZc3 zc3 value
     */
    public void setZc3(String pZc3) {
        this.zc3 = pZc3;
    }

    /**
     *
     * @return zc4
     */
    public String getZc4() {
        return this.zc4;
    }

    /**
     *
     * @param pZc4 zc4 value
     */
    public void setZc4(String pZc4) {
        this.zc4 = pZc4;
    }

    /**
     *
     * @return zc5
     */
    public String getZc5() {
        return this.zc5;
    }

    /**
     *
     * @param pZc5 zc5 value
     */
    public void setZc5(String pZc5) {
        this.zc5 = pZc5;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_OgFr8LdgEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("zc1=").append(getZc1());
        buffer.append(",");
        buffer.append("zc2=").append(getZc2());
        buffer.append(",");
        buffer.append("zc3=").append(getZc3());
        buffer.append(",");
        buffer.append("zc4=").append(getZc4());
        buffer.append(",");
        buffer.append("zc5=").append(getZc5());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_OgFr8LdgEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
