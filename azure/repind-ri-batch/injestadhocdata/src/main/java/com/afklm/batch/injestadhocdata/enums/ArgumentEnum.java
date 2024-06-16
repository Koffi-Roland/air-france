package com.afklm.batch.injestadhocdata.enums;


import com.airfrance.batch.common.enums.RequirementEnum;

public enum ArgumentEnum {
	f(RequirementEnum.MANDATORY),
    p(RequirementEnum.MANDATORY),
    o(RequirementEnum.MANDATORY);
	
    private RequirementEnum requirement;

    ArgumentEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    public RequirementEnum getRequirement() {
        return this.requirement;
    }
}
