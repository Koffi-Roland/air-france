package com.afklm.batch.updateMarketLanguage.enums;


import com.airfrance.batch.common.enums.RequirementEnum;

public enum BatchUpdateMarketLanguageEnum {
    f(RequirementEnum.MANDATORY),
    o(RequirementEnum.MANDATORY);

    BatchUpdateMarketLanguageEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    private RequirementEnum requirement;

    public RequirementEnum getRequirement() {
        return this.requirement;
    }

}
