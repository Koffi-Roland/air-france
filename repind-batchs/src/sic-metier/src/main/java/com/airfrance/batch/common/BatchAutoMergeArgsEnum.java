package com.airfrance.batch.common;

import com.airfrance.batch.compref.migklsub.enums.RequirementEnum;

public enum BatchAutoMergeArgsEnum {

    f(RequirementEnum.MANDATORY);

    private RequirementEnum requirement;

    public RequirementEnum getRequirement() {
        return requirement;
    }

    BatchAutoMergeArgsEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }
}
