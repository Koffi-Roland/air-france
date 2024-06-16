package com.airfrance.repind.dto.compref;

public class PermissionDTO  {
        
    
	private String permissionId;
	
	private boolean answer;
	
	private String market;
	
	private String language;

	public PermissionDTO() {
		super();
	}

	public PermissionDTO(String permissionId, boolean answer, String market, String language) {
		super();
		this.permissionId = permissionId;
		this.answer = answer;
		this.market = market;
		this.language = language;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public boolean getAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "PermissionDTO [permissionId=" + permissionId + ", answer=" + answer + ", market=" + market
				+ ", language=" + language + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (answer ? 1231 : 1237);
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((market == null) ? 0 : market.hashCode());
		result = prime * result + ((permissionId == null) ? 0 : permissionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermissionDTO other = (PermissionDTO) obj;
		if (answer != other.answer)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (market == null) {
			if (other.market != null)
				return false;
		} else if (!market.equals(other.market))
			return false;
		if (permissionId == null) {
			if (other.permissionId != null)
				return false;
		} else if (!permissionId.equals(other.permissionId))
			return false;
		return true;
	}
}
