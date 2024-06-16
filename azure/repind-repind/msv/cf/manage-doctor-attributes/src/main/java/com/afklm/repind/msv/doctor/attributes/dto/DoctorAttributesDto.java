package com.afklm.repind.msv.doctor.attributes.dto;

import com.afklm.repind.msv.doctor.attributes.model.AirLineCode;
import com.afklm.repind.msv.doctor.attributes.model.Language;
import com.afklm.repind.msv.doctor.attributes.model.Speciality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAttributesDto {

    private String roleId;

    private Set<Language> languages = new HashSet<>();

    private Speciality speciality;

    private AirLineCode airLineCode;

    private String gin;

    private String endDateRole;

    private String doctorStatus;

    private String doctorId;

    private String signatureSourceCreation;

    private String siteCreation;

}
