package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential Corporate ZC DTO
 * @author t950700
 *
 */
public class CommercialZonesCorporateDTO {

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	private String zc1;
	private String zc2;
	private String zc3;
	private String zc4;
	private String zc5;
	private String zoneSubtype;
	private String natureZone;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public CommercialZonesCorporateDTO() {
		super();
	}
	
	public CommercialZonesCorporateDTO(String zc1, String zc2, String zc3,
                                       String zc4, String zc5, String zoneSubtype, String natureZone) {
		super();
		this.zc1 = zc1;
		this.zc2 = zc2;
		this.zc3 = zc3;
		this.zc4 = zc4;
		this.zc5 = zc5;
		this.zoneSubtype = zoneSubtype;
		this.natureZone = natureZone;
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
}
