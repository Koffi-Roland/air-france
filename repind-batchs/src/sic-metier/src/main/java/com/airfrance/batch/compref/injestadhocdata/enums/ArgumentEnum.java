package com.airfrance.batch.compref.injestadhocdata.enums;

import com.airfrance.batch.utils.RequirementEnum;

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
