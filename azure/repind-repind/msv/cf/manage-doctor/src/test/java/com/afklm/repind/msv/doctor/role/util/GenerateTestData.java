package com.afklm.repind.msv.doctor.role.util;

import com.afklm.repind.msv.doctor.role.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.model.RelationModel;

import java.util.ArrayList;
import java.util.List;

public class GenerateTestData {


    public static CreateDoctorRoleCriteria createDoctorRole() {
        CreateDoctorRoleCriteria createDoctorRoleCriteria = new CreateDoctorRoleCriteria();
        createDoctorRoleCriteria.setGin("123456789000");
        createDoctorRoleCriteria.setDoctorId("1232561200502");
        createDoctorRoleCriteria.setDoctorStatus("V");
        createDoctorRoleCriteria.setType("D");
        createDoctorRoleCriteria.setEndDateRole("2036-01-01T00:00:00Z");
        createDoctorRoleCriteria.setAirLineCode("AF");
        createDoctorRoleCriteria.setSpeciality("CARD");
        createDoctorRoleCriteria.setSignatureSiteCreation("CAPI");
        createDoctorRoleCriteria.setSignatureSourceCreation("CAPI");

        List<RelationModel> relationList = new ArrayList<>();
        RelationModel relationModel = new RelationModel();
        List<String> values = new ArrayList<>();
        values.add("OT");
        relationModel.setType("SPEAK");
        relationModel.setValues(values);
        relationList.add(relationModel);

        createDoctorRoleCriteria.setRelationsList(relationList);

        return createDoctorRoleCriteria;
    }

}
