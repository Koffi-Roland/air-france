package com.afklm.repind.msv.doctor.attributes.criteria.role;


import com.afklm.repind.msv.doctor.attributes.model.Speciality;
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
    private Speciality speciality;

}
