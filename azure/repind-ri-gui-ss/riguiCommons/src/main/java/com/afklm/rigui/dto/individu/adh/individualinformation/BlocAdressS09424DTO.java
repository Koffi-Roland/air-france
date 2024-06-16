package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1qUBCMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : BlocAdressS09424DTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class BlocAdressS09424DTO  {
        
    /**
     * adressPrincipale
     */
    private String adressPrincipale;
        
        
    /**
     * adressepostale4emeligneadrsic
     */
    private AdressePostale4emeLigneAdrSICDTO adressepostale4emeligneadrsic;
        
        
    /**
     * adressepostale4emeligneadrsicdto
     */
    private AdressePostale4emeLigneAdrSICDTO adressepostale4emeligneadrsicdto;
        
        
    /**
     * signaturesic
     */
    private Set<SignatureSICDTO> signaturesic;
        
        
    /**
     * usagemedium
     */
    private Set<UsageMediumDTO> usagemedium;
        

    /*PROTECTED REGION ID(_b1qUBCMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public BlocAdressS09424DTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pAdressPrincipale adressPrincipale
     */
    public BlocAdressS09424DTO(String pAdressPrincipale) {
        this.adressPrincipale = pAdressPrincipale;
    }

    /**
     *
     * @return adressPrincipale
     */
    public String getAdressPrincipale() {
        return this.adressPrincipale;
    }

    /**
     *
     * @param pAdressPrincipale adressPrincipale value
     */
    public void setAdressPrincipale(String pAdressPrincipale) {
        this.adressPrincipale = pAdressPrincipale;
    }

    /**
     *
     * @return adressepostale4emeligneadrsic
     */
    public AdressePostale4emeLigneAdrSICDTO getAdressepostale4emeligneadrsic() {
        return this.adressepostale4emeligneadrsic;
    }

    /**
     *
     * @param pAdressepostale4emeligneadrsic adressepostale4emeligneadrsic value
     */
    public void setAdressepostale4emeligneadrsic(AdressePostale4emeLigneAdrSICDTO pAdressepostale4emeligneadrsic) {
        this.adressepostale4emeligneadrsic = pAdressepostale4emeligneadrsic;
    }

    /**
     *
     * @return adressepostale4emeligneadrsicdto
     */
    public AdressePostale4emeLigneAdrSICDTO getAdressepostale4emeligneadrsicdto() {
        return this.adressepostale4emeligneadrsicdto;
    }

    /**
     *
     * @param pAdressepostale4emeligneadrsicdto adressepostale4emeligneadrsicdto value
     */
    public void setAdressepostale4emeligneadrsicdto(AdressePostale4emeLigneAdrSICDTO pAdressepostale4emeligneadrsicdto) {
        this.adressepostale4emeligneadrsicdto = pAdressepostale4emeligneadrsicdto;
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
     * @return usagemedium
     */
    public Set<UsageMediumDTO> getUsagemedium() {
        return this.usagemedium;
    }

    /**
     *
     * @param pUsagemedium usagemedium value
     */
    public void setUsagemedium(Set<UsageMediumDTO> pUsagemedium) {
        this.usagemedium = pUsagemedium;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1qUBCMUEeCWJOBY8f-ONQ) ENABLED START*/
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
            .append("adressPrincipale", getAdressPrincipale())
            .toString();
    }

    /*PROTECTED REGION ID(_b1qUBCMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
