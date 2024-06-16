package com.airfrance.batch.common.enums;

public enum BatchSicUpdateNotificationArgsEnum {

    f(RequirementEnum.OPTIONAL), 
    l(RequirementEnum.OPTIONAL), 
    i(RequirementEnum.OPTIONAL), 
    s(RequirementEnum.OPTIONAL);

  

    BatchSicUpdateNotificationArgsEnum(RequirementEnum requirement) { 

        this .requirement = requirement; 

    } 

    private RequirementEnum requirement; 

  

    public RequirementEnum getRequirement() { 

        return this .requirement; 

    } 


}
