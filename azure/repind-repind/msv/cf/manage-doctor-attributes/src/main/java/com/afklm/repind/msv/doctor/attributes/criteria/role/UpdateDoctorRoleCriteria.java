package com.afklm.repind.msv.doctor.attributes.criteria.role;

import com.afklm.repind.msv.doctor.attributes.model.AirLineCode;
import com.afklm.repind.msv.doctor.attributes.model.Language;
import com.afklm.repind.msv.doctor.attributes.model.Speciality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter @With
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDoctorRoleCriteria {
    private String roleId;
    private Date endDateRole;
    private String doctorStatus;
    private String signatureSourceModification;
    private String siteModification;
    private Date signatureDateModification;
    private String doctorId;
    private Speciality speciality;
    private AirLineCode airLineCode;
    private Set<Language> languages = new HashSet<>();

}
