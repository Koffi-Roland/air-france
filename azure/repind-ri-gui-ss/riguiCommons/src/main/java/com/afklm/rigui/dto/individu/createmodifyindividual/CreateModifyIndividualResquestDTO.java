package com.afklm.rigui.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_ZN1GUDRhEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : CreateModifyIndividualResquestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class CreateModifyIndividualResquestDTO  {
        
    /**
     * adressepostale
     */
    private Set<AdressePostaleDTO> adressepostale;
        
        
    /**
     * email
     */
    private Set<EmailDTO> email;
        
        
    /**
     * habilitation
     */
    private HabilitationDTO habilitation;
        
        
    /**
     * individu
     */
    private InfosIndividuDTO individu;
        
        
    /**
     * profil
     */
    private ProfilAvecCodeFonctionValideDTO profil;
        
        
    /**
     * requete
     */
    private RequeteDTO requete;
        
        
    /**
     * signature
     */
    private SignatureDTO signature;
        
        
    /**
     * telecom
     */
    private Set<TelecomDTO> telecom;
        
        
    /**
     * titrecivil
     */
    private TitreCivilDTO titrecivil;
        

    /**
     * process
     */
    private String process;
    
    
    /*PROTECTED REGION ID(_ZN1GUDRhEeCc7ZsKsK1lbQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    
    /** 
     * full constructor
     */
    public CreateModifyIndividualResquestDTO() {
    }

    /**
     *
     * @return adressepostale
     */
    public Set<AdressePostaleDTO> getAdressepostale() {
        return this.adressepostale;
    }

    /**
     *
     * @param pAdressepostale adressepostale value
     */
    public void setAdressepostale(Set<AdressePostaleDTO> pAdressepostale) {
        this.adressepostale = pAdressepostale;
    }

    /**
     *
     * @return email
     */
    public Set<EmailDTO> getEmail() {
        return this.email;
    }

    /**
     *
     * @param pEmail email value
     */
    public void setEmail(Set<EmailDTO> pEmail) {
        this.email = pEmail;
    }

    /**
     *
     * @return habilitation
     */
    public HabilitationDTO getHabilitation() {
        return this.habilitation;
    }

    /**
     *
     * @param pHabilitation habilitation value
     */
    public void setHabilitation(HabilitationDTO pHabilitation) {
        this.habilitation = pHabilitation;
    }

    /**
     *
     * @return individu
     */
    public InfosIndividuDTO getIndividu() {
        return this.individu;
    }

    /**
     *
     * @param pIndividu individu value
     */
    public void setIndividu(InfosIndividuDTO pIndividu) {
        this.individu = pIndividu;
    }

    /**
     *
     * @return profil
     */
    public ProfilAvecCodeFonctionValideDTO getProfil() {
        return this.profil;
    }

    /**
     *
     * @param pProfil profil value
     */
    public void setProfil(ProfilAvecCodeFonctionValideDTO pProfil) {
        this.profil = pProfil;
    }

    /**
     *
     * @return requete
     */
    public RequeteDTO getRequete() {
        return this.requete;
    }

    /**
     *
     * @param pRequete requete value
     */
    public void setRequete(RequeteDTO pRequete) {
        this.requete = pRequete;
    }

    /**
     *
     * @return signature
     */
    public SignatureDTO getSignature() {
        return this.signature;
    }

    /**
     *
     * @param pSignature signature value
     */
    public void setSignature(SignatureDTO pSignature) {
        this.signature = pSignature;
    }

    /**
     *
     * @return telecom
     */
    public Set<TelecomDTO> getTelecom() {
        return this.telecom;
    }

    /**
     *
     * @param pTelecom telecom value
     */
    public void setTelecom(Set<TelecomDTO> pTelecom) {
        this.telecom = pTelecom;
    }

    /**
     *
     * @return titrecivil
     */
    public TitreCivilDTO getTitrecivil() {
        return this.titrecivil;
    }

    /**
     *
     * @param pTitrecivil titrecivil value
     */
    public void setTitrecivil(TitreCivilDTO pTitrecivil) {
        this.titrecivil = pTitrecivil;
    }

    
    
    public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	/**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_ZN1GUDRhEeCc7ZsKsK1lbQ) ENABLED START*/
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

    /*PROTECTED REGION ID(_ZN1GUDRhEeCc7ZsKsK1lbQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
