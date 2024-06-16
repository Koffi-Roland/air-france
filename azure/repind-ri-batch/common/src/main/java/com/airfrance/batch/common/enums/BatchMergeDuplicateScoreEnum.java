package com.airfrance.batch.common.enums;


import com.airfrance.batch.common.utils.RequirementEnum;

public enum BatchMergeDuplicateScoreEnum {
    I(RequirementEnum.MANDATORY),
    O(RequirementEnum.MANDATORY);

    BatchMergeDuplicateScoreEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    private RequirementEnum requirement;

    public RequirementEnum getRequirement() {
        return this.requirement;
    }

}
