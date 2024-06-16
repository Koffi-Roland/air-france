package com.afklm.batch.adrInvalidBarecode.enums;


import com.airfrance.batch.common.utils.RequirementEnum;

public enum BatchAdrInvalidBarecodeEnum {
    I(RequirementEnum.MANDATORY),
    O(RequirementEnum.MANDATORY);

    BatchAdrInvalidBarecodeEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    private RequirementEnum requirement;

    public RequirementEnum getRequirement() {
        return this.requirement;
    }

}
