package com.airfrance.repind.dto.compref;

import java.util.List;

public class InformationsDTO  {
        
    
	private int informationsId;
	
	private List<InformationDTO> information;

	public InformationsDTO() {
		super();
	}

	public InformationsDTO(int informationsId, List<InformationDTO> information) {
		super();
		this.informationsId = informationsId;
		this.information = information;
	}

	public int getInformationsId() {
		return informationsId;
	}

	public void setInformationsId(int informationsId) {
		this.informationsId = informationsId;
	}

	public List<InformationDTO> getInformation() {
		return information;
	}

	public void setInformation(List<InformationDTO> information) {
		this.information = information;
	}

	@Override
	public String toString() {
		return "InformationsDTO [informationsId=" + informationsId + ", information=" + information + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((information == null) ? 0 : information.hashCode());
		result = prime * result + informationsId;
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
		InformationsDTO other = (InformationsDTO) obj;
		if (information == null) {
			if (other.information != null)
				return false;
		} else if (!information.equals(other.information))
			return false;
		if (informationsId != other.informationsId)
			return false;
		return true;
	}
}
