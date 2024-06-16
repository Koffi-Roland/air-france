package com.afklm.batch.fixafcompref.enums;


import com.airfrance.batch.common.enums.RequirementEnum;

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
