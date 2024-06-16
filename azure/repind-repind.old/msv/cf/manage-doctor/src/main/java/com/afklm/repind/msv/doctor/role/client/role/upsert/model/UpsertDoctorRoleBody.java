package com.afklm.repind.msv.doctor.role.client.role.upsert.model;

import com.afklm.repind.msv.doctor.role.model.RelationModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class UpsertDoctorRoleBody {
    public List<RelationModel> relationsList;
}