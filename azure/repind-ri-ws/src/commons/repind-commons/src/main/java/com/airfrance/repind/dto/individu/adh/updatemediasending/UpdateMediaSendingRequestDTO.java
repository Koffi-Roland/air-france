package com.airfrance.repind.dto.individu.adh.updatemediasending;

/*PROTECTED REGION ID(_OHB-EBE5EeGcpcJ8ihBdPQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : UpdateMediaSendingRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class UpdateMediaSendingRequestDTO  {
        
    /**
     * comMedia
     */
    private String comMedia;
        
        
    /**
     * sgin
     */
    private String sgin;
        

    /*PROTECTED REGION ID(_OHB-EBE5EeGcpcJ8ihBdPQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public UpdateMediaSendingRequestDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pComMedia comMedia
     * @param pSgin sgin
     */
    public UpdateMediaSendingRequestDTO(String pComMedia, String pSgin) {
        this.comMedia = pComMedia;
        this.sgin = pSgin;
    }

    /**
     *
     * @return comMedia
     */
    public String getComMedia() {
        return this.comMedia;
    }

    /**
     *
     * @param pComMedia comMedia value
     */
    public void setComMedia(String pComMedia) {
        this.comMedia = pComMedia;
    }

    /**
     *
     * @return sgin
     */
    public String getSgin() {
        return this.sgin;
    }

    /**
     *
     * @param pSgin sgin value
     */
    public void setSgin(String pSgin) {
        this.sgin = pSgin;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_OHB-EBE5EeGcpcJ8ihBdPQ) ENABLED START*/
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
            .append("comMedia", getComMedia())
            .append("sgin", getSgin())
            .toString();
    }

    /*PROTECTED REGION ID(_OHB-EBE5EeGcpcJ8ihBdPQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
