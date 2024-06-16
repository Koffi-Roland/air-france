package com.afklm.batch.detectduplicates.enums;


import com.airfrance.batch.common.utils.RequirementEnum;

public enum BatchDetectDuplicatesEnum {
    O(RequirementEnum.MANDATORY);

    BatchDetectDuplicatesEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    private RequirementEnum requirement;

    public RequirementEnum getRequirement() {
        return this.requirement;
    }

}
