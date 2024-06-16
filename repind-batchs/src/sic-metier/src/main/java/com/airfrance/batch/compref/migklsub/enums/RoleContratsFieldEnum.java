package com.airfrance.batch.compref.migklsub.enums;

import com.airfrance.batch.utils.SicUtils;
import com.airfrance.repind.entity.role.RoleContrats;

import java.util.Arrays;
import java.util.function.Consumer;

public enum RoleContratsFieldEnum {
    GIN ("GIN"),
    CIN ( "CIN" );

    private String value;

    RoleContratsFieldEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoleContratsFieldEnum fromString(String iValue){
        return Arrays.stream(RoleContratsFieldEnum.values()).filter(x -> x.getValue().equals(iValue)).findFirst().orElse(null);
    }

    public static Consumer<Object> mapConsumer(RoleContratsFieldEnum iRolesContratsFieldEnum , RoleContrats ioRoleContrats){
        if(ioRoleContrats != null && iRolesContratsFieldEnum != null){
            switch (iRolesContratsFieldEnum){
                case CIN:
                    return (x) -> ioRoleContrats.setNumeroContrat(SicUtils.encodeCin((String)x));
                case GIN:
                    return (x) -> ioRoleContrats.setGin((String)x);
                default:
                    return null;
            }
        }
        return null;
    }
}
