package com.airfrance.repind.dto.individu.myaccountcustomerdata;

/*PROTECTED REGION ID(_5QnHcFneEeCBGMNfpbqgjw i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.individu.adh.individualinformation.InfosIndividuDTO;
import org.apache.commons.lang.builder.ToStringBuilder;

/*PROTECTED REGION END*/

/**
 * <p>Title : MyAccountComDataResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MyAccountComDataResponseDTO  {
        
    /**
     * infosindividu
     */
    private InfosIndividuDTO infosindividu;
        
        
    /**
     * myaccountdatadto
     */
    private MyAccountDataDTO myaccountdatadto;
        

    /*PROTECTED REGION ID(_5QnHcFneEeCBGMNfpbqgjw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    
    /** 
     * full constructor
     */
    public MyAccountComDataResponseDTO() {
    }

    /**
     *
     * @return infosindividu
     */
    public InfosIndividuDTO getInfosindividu() {
        return this.infosindividu;
    }

    /**
     *
     * @param pInfosindividu infosindividu value
     */
    public void setInfosindividu(InfosIndividuDTO pInfosindividu) {
        this.infosindividu = pInfosindividu;
    }

    /**
     *
     * @return myaccountdatadto
     */
    public MyAccountDataDTO getMyaccountdatadto() {
        return this.myaccountdatadto;
    }

    /**
     *
     * @param pMyaccountdatadto myaccountdatadto value
     */
    public void setMyaccountdatadto(MyAccountDataDTO pMyaccountdatadto) {
        this.myaccountdatadto = pMyaccountdatadto;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_5QnHcFneEeCBGMNfpbqgjw) ENABLED START*/
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
            .toString();
    }

    /*PROTECTED REGION ID(_5QnHcFneEeCBGMNfpbqgjw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
