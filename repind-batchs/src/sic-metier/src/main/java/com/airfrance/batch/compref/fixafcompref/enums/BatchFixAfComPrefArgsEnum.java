package com.airfrance.batch.compref.fixafcompref.enums;

import com.airfrance.batch.compref.migklsub.enums.RequirementEnum;

public enum BatchFixAfComPrefArgsEnum {

    f(RequirementEnum.MANDATORY),
    p(RequirementEnum.MANDATORY),
    o(RequirementEnum.MANDATORY);

    BatchFixAfComPrefArgsEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    private RequirementEnum requirement;

    public RequirementEnum getRequirement() {
        return this.requirement;
    }
}
