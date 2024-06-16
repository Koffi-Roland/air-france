package com.afklm.batch.migklsub.enums;

import com.airfrance.batch.common.enums.RequirementEnum;

public enum BatchMigrationKLSubscriptionsArgsEnum {

    f(RequirementEnum.MANDATORY),
    p(RequirementEnum.MANDATORY),
    o(RequirementEnum.MANDATORY),
    m(RequirementEnum.MANDATORY);

    BatchMigrationKLSubscriptionsArgsEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    private RequirementEnum requirement;

    public RequirementEnum getRequirement() {
        return this.requirement;
    }
}
