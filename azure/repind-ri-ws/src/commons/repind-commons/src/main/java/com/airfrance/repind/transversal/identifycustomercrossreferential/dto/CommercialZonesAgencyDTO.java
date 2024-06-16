package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

import java.util.Date;

/**
 * IdentifyCustomerCrossReferential Corporate ZC DTO
 * @author t950700
 *
 */
public class CommercialZonesAgencyDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String zoneSubtype;
	private String natureZone;
	private String privilegedLink;
	private Date linkStartDate;
	private Date linkEndDate;
	private Date zcStartDate;
	private Date zcEndDate;
	private String zc1;
	private String zc2;
	private String zc3;
	private String zc4;
	private String zc5;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public CommercialZonesAgencyDTO() {
		super();
	}
	
	public CommercialZonesAgencyDTO(String zc1, String zc2, String zc3,
			String zc4, String zc5) {
		super();
		this.zc1 = zc1;
		this.zc2 = zc2;
		this.zc3 = zc3;
		this.zc4 = zc4;
		this.zc5 = zc5;
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public String getZc1() {
		return zc1;
	}
	public void setZc1(String zc1) {
		this.zc1 = zc1;
	}
	public String getZc2() {
		return zc2;
	}
	public void setZc2(String zc2) {
		this.zc2 = zc2;
	}
	public String getZc3() {
		return zc3;
	}
	public void setZc3(String zc3) {
		this.zc3 = zc3;
	}
	public String getZc4() {
		return zc4;
	}
	public void setZc4(String zc4) {
		this.zc4 = zc4;
	}
	public String getZc5() {
		return zc5;
	}
	public void setZc5(String zc5) {
		this.zc5 = zc5;
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
