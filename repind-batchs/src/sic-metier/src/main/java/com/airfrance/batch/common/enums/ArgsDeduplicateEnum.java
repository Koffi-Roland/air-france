package com.airfrance.batch.common.enums;

import com.airfrance.batch.utils.RequirementEnum;
import lombok.Getter;

/**
 * Deduplicate Arguments
 */
@Getter
public enum ArgsDeduplicateEnum {
    G(RequirementEnum.MANDATORY),
    D(RequirementEnum.MANDATORY),
    T(RequirementEnum.OPTIONAL);


    ArgsDeduplicateEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    private final RequirementEnum requirement;
}
