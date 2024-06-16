package com.airfrance.repind.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_dHvXIDRhEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : CreateModifyIndividualResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class CreateModifyIndividualResponseDTO  {
        
    /**
     * adressepostale
     */
    private Set<AdressePostaleDTO> adressepostale;
        
        
    /**
     * email
     */
    private Set<EmailDTO> email;
        
        
    /**
     * individu
     */
    private InfosIndividuDTO individu;
        
        
    /**
     * profil
     */
    private ProfilAvecCodeFonctionValideDTO profil;
        
        
    /**
     * telecom
     */
    private Set<TelecomDTO> telecom;
        
        
    /**
     * titrecivil
     */
    private TitreCivilDTO titrecivil;

    /**
     * informationResponseDTO
     */
    private Set<InformationResponseDTO> informationResponse;
    
    /**
     * postalAddressResponseDTO
     */
    private Set<PostalAddressResponseDTO> postalAddressResponse;

    /**
     * gin
     */
    private String gin;

    /**
     * Succes
     */
    private Boolean success;
    
    /*PROTECTED REGION ID(_dHvXIDRhEeCc7ZsKsK1lbQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    
    /** 
     * full constructor
     */
    public CreateModifyIndividualResponseDTO() {
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

    
    
    
    public Set<InformationResponseDTO> getInformationResponse() {
    	if (informationResponse == null)  {
    		informationResponse = new HashSet<InformationResponseDTO>();
    	} 
		return informationResponse;
	}

	public void setInformationResponse(
			Set<InformationResponseDTO> informationResponse) {
		this.informationResponse = informationResponse;
	}
	
	
	public Set<PostalAddressResponseDTO> getPostalAddressResponse() {
    	if (postalAddressResponse == null)  {
    		postalAddressResponse = new HashSet<PostalAddressResponseDTO>();
    	} 
		return postalAddressResponse;
	}

	public void setPostalAddressResponse(
			Set<PostalAddressResponseDTO> postalAddressResponse) {
		this.postalAddressResponse = postalAddressResponse;
	}

	public String getGin() {
		return gin;
	}

	public void setGin(String gin) {
		this.gin = gin;
	}
	
	

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_dHvXIDRhEeCc7ZsKsK1lbQ) ENABLED START*/
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

    /*PROTECTED REGION ID(_dHvXIDRhEeCc7ZsKsK1lbQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
