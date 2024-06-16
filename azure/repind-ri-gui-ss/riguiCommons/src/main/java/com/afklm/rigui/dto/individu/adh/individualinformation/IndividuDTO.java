package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1hJ0yMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : IndividuDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class IndividuDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 7673413094976271955L;


	/**
     * aliassic
     */
    private Set<AliasSICDTO> aliassic;
        
        
    /**
     * infosindividu
     */
    private InfosIndividuDTO infosindividu;
        
        
    /**
     * profilaveccodefonctionvalidesicdto
     */
    private ProfilAvecCodeFonctionValideSICDTO profilaveccodefonctionvalidesicdto;
        
        
    /**
     * signaturesic
     */
    private Set<SignatureSICDTO> signaturesic;
        
        
    /**
     * titrecivilsic
     */
    private TitreCivilSICDTO titrecivilsic;
        
        
    /**
     * usageclient
     */
    private Set<UsageClientDTO> usageclient;
        

    /*PROTECTED REGION ID(_b1hJ0yMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    
    /** 
     * full constructor
     */
    public IndividuDTO() {
    }

    /**
     *
     * @return aliassic
     */
    public Set<AliasSICDTO> getAliassic() {
        return this.aliassic;
    }

    /**
     *
     * @param pAliassic aliassic value
     */
    public void setAliassic(Set<AliasSICDTO> pAliassic) {
        this.aliassic = pAliassic;
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
     * @return profilaveccodefonctionvalidesicdto
     */
    public ProfilAvecCodeFonctionValideSICDTO getProfilaveccodefonctionvalidesicdto() {
        return this.profilaveccodefonctionvalidesicdto;
    }

    /**
     *
     * @param pProfilaveccodefonctionvalidesicdto profilaveccodefonctionvalidesicdto value
     */
    public void setProfilaveccodefonctionvalidesicdto(ProfilAvecCodeFonctionValideSICDTO pProfilaveccodefonctionvalidesicdto) {
        this.profilaveccodefonctionvalidesicdto = pProfilaveccodefonctionvalidesicdto;
    }

    /**
     *
     * @return signaturesic
     */
    public Set<SignatureSICDTO> getSignaturesic() {
        return this.signaturesic;
    }

    /**
     *
     * @param pSignaturesic signaturesic value
     */
    public void setSignaturesic(Set<SignatureSICDTO> pSignaturesic) {
        this.signaturesic = pSignaturesic;
    }

    /**
     *
     * @return titrecivilsic
     */
    public TitreCivilSICDTO getTitrecivilsic() {
        return this.titrecivilsic;
    }

    /**
     *
     * @param pTitrecivilsic titrecivilsic value
     */
    public void setTitrecivilsic(TitreCivilSICDTO pTitrecivilsic) {
        this.titrecivilsic = pTitrecivilsic;
    }

    /**
     *
     * @return usageclient
     */
    public Set<UsageClientDTO> getUsageclient() {
        return this.usageclient;
    }

    /**
     *
     * @param pUsageclient usageclient value
     */
    public void setUsageclient(Set<UsageClientDTO> pUsageclient) {
        this.usageclient = pUsageclient;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1hJ0yMUEeCWJOBY8f-ONQ) ENABLED START*/
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
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_b1hJ0yMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
