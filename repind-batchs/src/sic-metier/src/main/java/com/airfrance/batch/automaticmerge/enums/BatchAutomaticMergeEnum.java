package com.airfrance.batch.automaticmerge.enums;


import com.airfrance.batch.utils.RequirementEnum;

public enum BatchAutomaticMergeEnum {
    I(RequirementEnum.MANDATORY),
    O(RequirementEnum.MANDATORY);

    BatchAutomaticMergeEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    private RequirementEnum requirement;

    public RequirementEnum getRequirement() {
        return this.requirement;
    }

}
