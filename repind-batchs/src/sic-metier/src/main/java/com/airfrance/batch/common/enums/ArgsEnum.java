package com.airfrance.batch.common.enums;


import com.airfrance.batch.utils.RequirementEnum;
import lombok.Getter;

@Getter
public enum ArgsEnum {
    I(RequirementEnum.MANDATORY),
    O(RequirementEnum.MANDATORY);

    ArgsEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    private final RequirementEnum requirement;
}
