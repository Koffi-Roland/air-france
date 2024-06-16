package com.afklm.repind.msv.doctor.role.criteria;

import com.afklm.repind.msv.doctor.role.model.RelationModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Set;

@Getter
@With
@NoArgsConstructor
@AllArgsConstructor
public class UpsertDoctorAttributesCriteria {

    private String roleId;

    private Set<RelationModel> relationsList;
}
