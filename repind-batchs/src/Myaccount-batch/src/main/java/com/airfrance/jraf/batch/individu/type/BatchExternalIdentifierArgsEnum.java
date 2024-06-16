package com.airfrance.jraf.batch.individu.type;

import com.airfrance.jraf.batch.common.type.RequirementEnum;

public enum BatchExternalIdentifierArgsEnum {

	f(RequirementEnum.MANDATORY),
	C(RequirementEnum.OPTIONAL);
	
	private RequirementEnum requirement;
	
	private BatchExternalIdentifierArgsEnum(RequirementEnum requirement) {
		this.requirement = requirement;
	}

	public RequirementEnum getRequirement() {
		return this.requirement;
	}
}
