package com.afklm.repind.msv.doctor.attributes.criteria.role;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class IndividuCriteria {

    private String gin;
    private String doctorStatus;
    private String doctorId;
    private String speciality;

}
