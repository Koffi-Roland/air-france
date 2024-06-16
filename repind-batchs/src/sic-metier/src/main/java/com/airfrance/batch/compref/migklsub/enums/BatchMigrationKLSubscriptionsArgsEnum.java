package com.airfrance.batch.compref.migklsub.enums;

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
