package com.afklm.repind.msv.doctor.attributes.criteria.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Date;

@Getter @With
@AllArgsConstructor
@NoArgsConstructor
public class DeleteDoctorRoleCriteria {
    private String roleId;
    private String signatureSource;
    private Date signatureDate;
}
