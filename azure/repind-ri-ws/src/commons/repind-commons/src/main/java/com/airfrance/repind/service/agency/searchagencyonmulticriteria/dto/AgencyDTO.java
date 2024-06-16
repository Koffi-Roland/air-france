package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Agency DTO
 * @author t950700
 *
 */
public class AgencyDTO {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	private int relevance;
	private AgencyInformationsDTO agencyInformationsDTO;
	private List<TelecomBlocDTO> telecomBlocDTO;
	private List<EmailDTO> emailDTO;
	private List<CommercialZonesDTO> commercialZonesAgencyDTO;

	 
	/*===============================================*/
	/*                CONSTRUCTORS                   */
	/*===============================================*/
	
	public AgencyDTO() {
		super();
	}
	
	public AgencyDTO(int relevance,
                     AgencyInformationsDTO agencyInformationsDTO,
                     List<TelecomBlocDTO> telecomBlocDTO, List<EmailDTO> emailDTO) {
		super();
		this.relevance = relevance;
		this.agencyInformationsDTO = agencyInformationsDTO;
		this.telecomBlocDTO = telecomBlocDTO;
		this.emailDTO = emailDTO;
	}

	
	/*===============================================*/
	/*                ACCESSORS                      */
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
     * Gets the value of the agencyInformations property.
     * 
     * @return
     *     possible object is
     *     {@link AgencyInformations }
     *     
     */
    public AgencyInformationsDTO getAgencyInformationsDTO() {
        return agencyInformationsDTO;
    }

    /**
     * Sets the value of the agencyInformations property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgencyInformations }
     *     
     */
    public void setAgencyInformationsDTO(AgencyInformationsDTO value) {
        this.agencyInformationsDTO = value;
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
    public List<TelecomBlocDTO> getTelecomBlocDTO() {
        if (telecomBlocDTO == null) {
        	telecomBlocDTO = new ArrayList<TelecomBlocDTO>();
        }
        return this.telecomBlocDTO;
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
    public List<EmailDTO> getEmailDTO() {
        if (emailDTO == null) {
            emailDTO = new ArrayList<EmailDTO>();
        }
        return this.emailDTO;
    }

    /**
     * Gets the value of the commercialZones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commercialZones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommercialZonesAgencyDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommercialZones }
     * 
     * 
     */
    public List<CommercialZonesDTO> getCommercialZonesAgencyDTO() {
    	if (commercialZonesAgencyDTO == null) {
    		commercialZonesAgencyDTO = new ArrayList<CommercialZonesDTO>();
    	}
    	return commercialZonesAgencyDTO;
    }
}
