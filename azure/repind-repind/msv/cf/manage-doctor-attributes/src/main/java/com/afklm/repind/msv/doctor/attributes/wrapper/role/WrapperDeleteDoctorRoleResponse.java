package com.afklm.repind.msv.doctor.attributes.wrapper.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperDeleteDoctorRoleResponse {

    private String roleId;

}
