package com.afklm.repind.msv.preferences.services.builder;

public class ModelPreferenceData {
	
	private Long preferenceDataId;
    private String key;
    private String value;
    //private ModelPreference preferenceDTO;
    
	public Long getPreferenceDataId() {
		return preferenceDataId;
	}
	public void setPreferenceDataId(Long preferenceDataId) {
		this.preferenceDataId = preferenceDataId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	/*public ModelPreference getPreferenceDTO() {
		return preferenceDTO;
	}
	public void setPreferenceDTO(ModelPreference preferenceDTO) {
		this.preferenceDTO = preferenceDTO;
	}*/

    
}
