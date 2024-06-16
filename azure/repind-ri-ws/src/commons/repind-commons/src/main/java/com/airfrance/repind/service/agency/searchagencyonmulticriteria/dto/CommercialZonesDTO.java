package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

import java.util.Date;

/**
 * Commercial zones DTO
 * @author t950700
 *
 */
public class CommercialZonesDTO {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
    protected String zc1;
    protected String zc2;
    protected String zc3;
    protected String zc4;
    protected String zc5;
    private String zoneSubtype;
	private String natureZone;
	private String privilegedLink;
	private Date linkStartDate;
	private Date linkEndDate;
	private Date zcStartDate;
	private Date zcEndDate;
    
    /*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/

    /**
     * Gets the value of the zc1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZc1() {
        return zc1;
    }

    /**
     * Sets the value of the zc1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZc1(String value) {
        this.zc1 = value;
    }

    /**
     * Gets the value of the zc2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZc2() {
        return zc2;
    }

    /**
     * Sets the value of the zc2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZc2(String value) {
        this.zc2 = value;
    }

    /**
     * Gets the value of the zc3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZc3() {
        return zc3;
    }

    /**
     * Sets the value of the zc3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZc3(String value) {
        this.zc3 = value;
    }

    /**
     * Gets the value of the zc4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZc4() {
        return zc4;
    }

    /**
     * Sets the value of the zc4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZc4(String value) {
        this.zc4 = value;
    }

    /**
     * Gets the value of the zc5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZc5() {
        return zc5;
    }

    /**
     * Sets the value of the zc5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZc5(String value) {
        this.zc5 = value;
    }

	public String getZoneSubtype() {
		return zoneSubtype;
	}

	public void setZoneSubtype(String zoneSubtype) {
		this.zoneSubtype = zoneSubtype;
	}

	public String getNatureZone() {
		return natureZone;
	}

	public void setNatureZone(String natureZone) {
		this.natureZone = natureZone;
	}

	public String getPrivilegedLink() {
		return privilegedLink;
	}

	public void setPrivilegedLink(String privilegedLink) {
		this.privilegedLink = privilegedLink;
	}

	public Date getLinkStartDate() {
		return linkStartDate;
	}

	public void setLinkStartDate(Date linkStartDate) {
		this.linkStartDate = linkStartDate;
	}

	public Date getLinkEndDate() {
		return linkEndDate;
	}

	public void setLinkEndDate(Date linkEndDate) {
		this.linkEndDate = linkEndDate;
	}

	public Date getZcStartDate() {
		return zcStartDate;
	}

	public void setZcStartDate(Date zcStartDate) {
		this.zcStartDate = zcStartDate;
	}

	public Date getZcEndDate() {
		return zcEndDate;
	}

	public void setZcEndDate(Date zcEndDate) {
		this.zcEndDate = zcEndDate;
	}

}
