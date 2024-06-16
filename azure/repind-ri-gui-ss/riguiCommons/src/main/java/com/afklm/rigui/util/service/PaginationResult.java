package com.afklm.rigui.util.service;

import java.util.ArrayList;
import java.util.List;

public class PaginationResult<T> {

	private int index;
	private int maxResults;
	private int totalResults;
	private List<T> listResults;
	
	public PaginationResult() {
		super();
	}

	public PaginationResult(int index, int maxResults, int totalResults, List<T> listResults) {
		super();
		this.index = index;
		this.maxResults = maxResults;
		this.totalResults = totalResults;
		this.listResults = listResults;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public List<T> getListResults() {
		if (listResults == null) {
			listResults = new ArrayList<T>();
		}
		
		return listResults;
	}

	public void setListResults(List<T> listResults) {
		this.listResults = listResults;
	}

	@Override
	public String toString() {
		return "PaginationResult [index=" + index + ", maxResults=" + maxResults + ", totalResults=" + totalResults
				+ ", listResults=" + listResults + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((listResults == null) ? 0 : listResults.hashCode());
		result = prime * result + maxResults;
		result = prime * result + totalResults;
		return result;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaginationResult other = (PaginationResult) obj;
		if (index != other.index)
			return false;
		if (listResults == null) {
			if (other.listResults != null)
				return false;
		} else if (!listResults.equals(other.listResults))
			return false;
		if (maxResults != other.maxResults)
			return false;
		if (totalResults != other.totalResults)
			return false;
		return true;
	}
}
