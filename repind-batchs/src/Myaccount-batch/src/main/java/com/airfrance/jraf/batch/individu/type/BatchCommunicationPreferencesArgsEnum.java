package com.airfrance.jraf.batch.individu.type;

import com.airfrance.jraf.batch.common.type.RequirementEnum;

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
