package com.airfrance.jraf.batch.invalidation.type;

import com.airfrance.jraf.batch.common.type.RequirementEnum;

public enum BatchInvalidationArgsEnum {

    f(RequirementEnum.MANDATORY),    
    C(RequirementEnum.OPTIONAL), 
    t(RequirementEnum.OPTIONAL),      
    l(RequirementEnum.OPTIONAL),
    d(RequirementEnum.OPTIONAL),  
    s(RequirementEnum.OPTIONAL),
    force(RequirementEnum.OPTIONAL);

    BatchInvalidationArgsEnum(RequirementEnum requirement) {
        this.requirement = requirement;
    }

    private RequirementEnum requirement;

    public RequirementEnum getRequirement() {
        return this.requirement;
    }
}



