package com.afklm.rigui.dto.compref;

import com.afklm.rigui.dto.ws.RequestorDTO;

import java.util.List;

public class PermissionsDTO  {
        
    
	private String gin;
	
	private List<PermissionDTO> permission;
	
	// usefull for tracking REPIND-1060 
	private RequestorDTO requestorDTO;
	
	public PermissionsDTO() {
		super();
	}

	public PermissionsDTO(String gin, List<PermissionDTO> permission) {
		super();
		this.gin = gin;
		this.permission = permission;
	}

	public String getGin() {
		return gin;
	}

	public void setGin(String gin) {
		this.gin = gin;
	}

	public List<PermissionDTO> getPermission() {
		return permission;
	}

	public void setPermission(List<PermissionDTO> permission) {
		this.permission = permission;
	}

	public RequestorDTO getRequestorDTO() {
		return requestorDTO;
	}

	public void setRequestorDTO(RequestorDTO requestorDTO) {
		this.requestorDTO = requestorDTO;
	}

	@Override
	public String toString() {
		return "PermissionsDTO [gin=" + gin + ", permission=" + permission + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gin == null) ? 0 : gin.hashCode());
		result = prime * result + ((permission == null) ? 0 : permission.hashCode());
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
		PermissionsDTO other = (PermissionsDTO) obj;
		if (gin == null) {
			if (other.gin != null)
				return false;
		} else if (!gin.equals(other.gin))
			return false;
		if (permission == null) {
			if (other.permission != null)
				return false;
		} else if (!permission.equals(other.permission))
			return false;
	
		return true;
	}
}
