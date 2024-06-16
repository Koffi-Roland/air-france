package com.airfrance.batch.updateMarketLanguage.enums;


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
