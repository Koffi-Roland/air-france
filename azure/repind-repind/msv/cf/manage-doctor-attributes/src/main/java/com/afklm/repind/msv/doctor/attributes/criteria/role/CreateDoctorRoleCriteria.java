package com.afklm.repind.msv.doctor.attributes.criteria.role;

import com.afklm.repind.msv.doctor.attributes.model.AirLineCode;
import com.afklm.repind.msv.doctor.attributes.model.Language;
import com.afklm.repind.msv.doctor.attributes.model.Speciality;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRoleCriteria {

    private String roleId;

    private Set<Language> languages = new HashSet<>();

    private Speciality speciality;

    private AirLineCode airLineCode;

    private String gin;

    private Date endDateRole;

    private String doctorStatus;

    private String doctorId;

    private String signatureSourceCreation;

    private String siteCreation;

    private Date signatureDateCreation;


}
