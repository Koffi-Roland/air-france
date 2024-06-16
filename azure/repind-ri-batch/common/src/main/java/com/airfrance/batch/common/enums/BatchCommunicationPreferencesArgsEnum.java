package com.airfrance.batch.common.enums;

public enum BatchCommunicationPreferencesArgsEnum {

	t(RequirementEnum.OPTIONAL), 
	l(RequirementEnum.OPTIONAL);

	BatchCommunicationPreferencesArgsEnum(RequirementEnum requirement) {
		this.requirement = requirement;
	}

	private RequirementEnum requirement;

	public RequirementEnum getRequirement() {
		return this.requirement;
	}
}
