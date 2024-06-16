package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

/**
 * DTO
 * Represents the minimum required 
 * firm informations. If its variables match
 * with search criteria, firm informations are loaded
 * as a FirmDTO instance
 * 
 * @author t950700
 *
 */
public class SearchResultDTO implements Comparable<SearchResultDTO> {

	/*==========================================*/
	/*           INSTANCE VARIABLES             */
	/*==========================================*/
	private String gin;
	private FirmTypeEnum firmType;
	private int rate;
	
	/*==========================================*/
	/*           CONSTRUCTORS                   */
	/*==========================================*/
	
	public SearchResultDTO() {
		super();
	}
	
	/*==========================================*/
	/*                 ACCESSORS                */
	/*==========================================*/
	public String getGin() {
		return gin;
	}

	public void setGin(String gin) {
		this.gin = gin;
	}

	public FirmTypeEnum getFirmType() {
		return firmType;
	}

	public void setFirmType(FirmTypeEnum firmType) {
		this.firmType = firmType;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	
	/*==========================================*/
	/*       COMPARABLE IMPLEMENTATION          */
	/*==========================================*/
	
	/**
	 * Needed to avoid duplicated gins in the result
	 */
	public int compareTo(SearchResultDTO o) {
		SearchResultDTO other = (SearchResultDTO)o;
		int result = 0;
		if((this.rate - other.getRate()) == 0)
		{
			result = this.getGin().compareToIgnoreCase(other.getGin());
		}
		else
		{
			result = (this.rate - other.getRate());
			if(this.getGin().compareToIgnoreCase(other.getGin()) == 0)
			{
				result = 0;
			}
		}
		return ((-1) * result);
	}
	
	public boolean equals(Object o) {
	      return (o instanceof SearchResultDTO) && ((SearchResultDTO)o).getGin().equalsIgnoreCase(this.getGin());
	  }

	  public int hashCode() {
	      return gin.hashCode();
	  }

}
