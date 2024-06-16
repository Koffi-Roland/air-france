package com.afklm.repind.msv.doctor.role.client.role.global.model;

import com.afklm.repind.msv.doctor.role.model.AirLineCode;
import com.afklm.repind.msv.doctor.role.model.RelationModel;
import com.afklm.repind.msv.doctor.role.model.Speciality;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
public class DoctorRoleResponseModelTest {

    @Test
    public void insertEntityTest(){

        DoctorRoleResponseModel doctorRoleResponseModel = new DoctorRoleResponseModel("gin", "roleId", "type", "doctorId", "endDateRole", "siteCreation","signatureSourceCreation", "signatureDateCreation", "siteModification", "signatureSourceModification", "signatureDateModification", "status", Speciality.builder().value("speciality").build(), AirLineCode.builder().value("airLineCode").build(),  new ArrayList<String>());

        Assert.assertNotNull(doctorRoleResponseModel.getGin());
        Assert.assertNotNull(doctorRoleResponseModel.getRoleId());
        Assert.assertNotNull(doctorRoleResponseModel.getType());
        Assert.assertNotNull(doctorRoleResponseModel.getDoctorId());
        Assert.assertNotNull(doctorRoleResponseModel.getEndDateRole());
        Assert.assertNotNull(doctorRoleResponseModel.getSiteCreation());
        Assert.assertNotNull(doctorRoleResponseModel.getSignatureSourceCreation());
        Assert.assertNotNull(doctorRoleResponseModel.getSignatureDateCreation());
        Assert.assertNotNull(doctorRoleResponseModel.getSiteModification());
        Assert.assertNotNull(doctorRoleResponseModel.getSignatureSourceModification());
        Assert.assertNotNull(doctorRoleResponseModel.getSignatureDateModification());
        Assert.assertNotNull(doctorRoleResponseModel.getStatus());
        Assert.assertNotNull(doctorRoleResponseModel.getAirLineCode());
        Assert.assertNotNull(doctorRoleResponseModel.getLanguages());
    }

}
