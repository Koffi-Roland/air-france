package com.afklm.repind.msv.doctor.role.client.role.global.model;

import com.afklm.repind.msv.doctor.role.model.AirLineCode;
import com.afklm.repind.msv.doctor.role.model.RelationModel;
import com.afklm.repind.msv.doctor.role.client.core.HttpResponseModel;
import com.afklm.repind.msv.doctor.role.model.Speciality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRoleResponseModel extends HttpResponseModel {

    private String gin;
    private String roleId;
    private String type;
    private String doctorId;
    private String endDateRole;
    private String siteCreation;
    private String signatureSourceCreation;
    private String signatureDateCreation;
    private String siteModification;
    private String signatureSourceModification;
    private String signatureDateModification;
    private String status;
    private Speciality speciality;
    private AirLineCode airLineCode;
    private List<String> languages ;
}
