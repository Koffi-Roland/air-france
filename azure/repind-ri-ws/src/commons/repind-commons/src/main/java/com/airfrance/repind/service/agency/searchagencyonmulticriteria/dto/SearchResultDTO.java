package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

public class SearchResultDTO implements Comparable<SearchResultDTO> {

	/*==========================================*/
	/*           INSTANCE VARIABLES             */
	/*==========================================*/
	private String gin;
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
