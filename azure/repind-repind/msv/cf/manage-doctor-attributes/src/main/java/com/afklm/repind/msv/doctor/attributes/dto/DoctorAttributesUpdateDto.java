package com.afklm.repind.msv.doctor.attributes.dto;

import com.afklm.repind.msv.doctor.attributes.model.AirLineCode;
import com.afklm.repind.msv.doctor.attributes.model.Language;
import com.afklm.repind.msv.doctor.attributes.model.Speciality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAttributesUpdateDto {

    private String roleId;

    private Set<Language> languages = new HashSet<>();

    private Speciality speciality;

    private AirLineCode airLineCode;

    private String doctorStatus;

    private String doctorId;

    private String endDateRole;

    private String signatureSourceModification;

    private String siteModification;

}
