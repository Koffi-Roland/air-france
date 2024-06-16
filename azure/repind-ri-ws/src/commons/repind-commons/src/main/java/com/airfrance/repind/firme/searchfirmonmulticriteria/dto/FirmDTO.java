package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.CommercialZonesCorporateDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.MemberCorporateDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO
 * Firm returned as an element of search operation
 * @author t950700
 *
 */
public class FirmDTO {

	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
    protected int relevance;
    protected FirmInformationsDTO firmInformations;
    protected List<TelecomDTO> telecoms;
    protected EmailAddressDTO emailAddress;
    protected List<EmailDTO> email;
    protected List<TelecomBlocDTO> telecomBloc;
    protected List<MemberCorporateDTO> memberCorporate;
	protected CommercialZonesCorporateDTO commercialZonesCorporate;
    
    
    /*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
    

	/**
     * Gets the value of the relevance property.
     * 
     */
    public int getRelevance() {
        return relevance;
    }

    /**
     * Sets the value of the relevance property.
     * 
     */
    public void setRelevance(int value) {
        this.relevance = value;
    }

    /**
     * Gets the value of the firmInformations property.
     * 
     * @return
     *     possible object is
     *     {@link FirmInformations }
     *     
     */
    public FirmInformationsDTO getFirmInformations() {
        return firmInformations;
    }

    /**
     * Sets the value of the firmInformations property.
     * 
     * @param value
     *     allowed object is
     *     {@link FirmInformations }
     *     
     */
    public void setFirmInformations(FirmInformationsDTO value) {
        this.firmInformations = value;
    }

    /**
     * Gets the value of the telecoms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the telecoms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelecoms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Telecom }
     * 
     * 
     */
    public List<TelecomDTO> getTelecoms() {
        if (telecoms == null) {
            telecoms = new ArrayList<TelecomDTO>();
        }
        return this.telecoms;
    }

    /**
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link EmailAddress }
     *     
     */
    public EmailAddressDTO getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailAddress }
     *     
     */
    public void setEmailAddress(EmailAddressDTO value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the email property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Email }
     * 
     * 
     */
    public List<EmailDTO> getEmail() {
        if (email == null) {
            email = new ArrayList<EmailDTO>();
        }
        return this.email;
    }

    /**
     * Gets the value of the telecomBloc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the telecomBloc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelecomBloc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TelecomBloc }
     * 
     * 
     */
    public List<TelecomBlocDTO> getTelecomBloc() {
        if (telecomBloc == null) {
            telecomBloc = new ArrayList<TelecomBlocDTO>();
        }
        return this.telecomBloc;
    }

    public CommercialZonesCorporateDTO getCommercialZonesCorporate() {
		return commercialZonesCorporate;
	}

	public void setCommercialZonesCorporate(
			CommercialZonesCorporateDTO commercialZonesCorporate) {
		this.commercialZonesCorporate = commercialZonesCorporate;
	}

	public List<MemberCorporateDTO> getMemberCorporate() {
		return memberCorporate;
	}

	public void setMemberCorporate(List<MemberCorporateDTO> memberCorporate) {
		this.memberCorporate = memberCorporate;
	}
	
}

