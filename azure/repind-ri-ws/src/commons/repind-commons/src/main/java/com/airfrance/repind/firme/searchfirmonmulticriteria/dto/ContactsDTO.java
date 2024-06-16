package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;


/**
 * Contacts DTO
 * @author t950700
 *
 */
public class ContactsDTO {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	protected PhoneBlocDTO phoneBloc;
    protected EmailBlocDTO emailBloc;
    protected PostalAddressBlocDTO postalAddressBloc;

    /*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
    
    /**
     * Gets the value of the phoneBloc property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneBloc }
     *     
     */
    public PhoneBlocDTO getPhoneBloc() {
        return phoneBloc;
    }

    /**
     * Sets the value of the phoneBloc property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneBloc }
     *     
     */
    public void setPhoneBloc(PhoneBlocDTO value) {
        this.phoneBloc = value;
    }

    /**
     * Gets the value of the emailBloc property.
     * 
     * @return
     *     possible object is
     *     {@link EmailBloc }
     *     
     */
    public EmailBlocDTO getEmailBloc() {
        return emailBloc;
    }

    /**
     * Sets the value of the emailBloc property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailBloc }
     *     
     */
    public void setEmailBloc(EmailBlocDTO value) {
        this.emailBloc = value;
    }

    /**
     * Gets the value of the postalAddressBloc property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddressBloc }
     *     
     */
    public PostalAddressBlocDTO getPostalAddressBloc() {
        return postalAddressBloc;
    }

    /**
     * Sets the value of the postalAddressBloc property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddressBloc }
     *     
     */
    public void setPostalAddressBloc(PostalAddressBlocDTO value) {
        this.postalAddressBloc = value;
    }
}
