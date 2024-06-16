package com.afklm.repind.msv.doctor.role.client.role.create.model;

import com.afklm.repind.msv.doctor.role.model.RelationModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRoleBody {
    public List<RelationModel> relationsList;
}