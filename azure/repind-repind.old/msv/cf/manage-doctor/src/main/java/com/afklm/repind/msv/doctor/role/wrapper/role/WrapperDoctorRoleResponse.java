package com.afklm.repind.msv.doctor.role.wrapper.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class WrapperDoctorRoleResponse {

	private String contractNumber;

}
