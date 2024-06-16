package com.airfrance.batch.common.enums;

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
