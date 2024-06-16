package com.airfrance.batch.common.enums;


import com.airfrance.batch.common.utils.RequirementEnum;

public enum BatchInvalidationArgsEnum {

    f(RequirementEnum.MANDATORY),
    C(RequirementEnum.OPTIONAL),
    t(RequirementEnum.OPTIONAL),
    l(RequirementEnum.OPTIONAL),
    d(RequirementEnum.OPTIONAL),
    s(RequirementEnum.OPTIONAL),
    force(RequirementEnum.OPTIONAL);

    BatchInvalidationArgsEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    private RequirementEnum requirement;

    public RequirementEnum getRequirement() {
        return this.requirement;
    }
}
