package com.airfrance.batch.detectduplicates.enums;


import com.airfrance.batch.updateMarketLanguage.enums.RequirementEnum;

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
