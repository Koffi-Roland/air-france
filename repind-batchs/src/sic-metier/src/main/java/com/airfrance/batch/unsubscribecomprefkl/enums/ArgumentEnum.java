package com.airfrance.batch.unsubscribecomprefkl.enums;

import com.airfrance.batch.utils.RequirementEnum;
import lombok.Getter;

@Getter
public enum ArgumentEnum {
	F(RequirementEnum.MANDATORY),
    I(RequirementEnum.MANDATORY),
    O(RequirementEnum.MANDATORY);
	
    private final RequirementEnum requirement;

    ArgumentEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }
}
