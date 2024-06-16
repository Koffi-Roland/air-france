package com.afklm.repind.msv.doctor.role.client.role.create.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CreateDoctorRoleRequestTest {

    @Test
    public void insertEntityTest(){

        CreateDoctorRoleRequest createDoctorRoleRequest = new CreateDoctorRoleRequest("gin", "roleId", "doctorId", "doctorStatus", "endDateRole", "airLineCode", "signatureCreation", "siteCreation", "speciality");

        Assert.assertNotNull(createDoctorRoleRequest.getGin());
        Assert.assertNotNull(createDoctorRoleRequest.getRoleId());
        Assert.assertNotNull(createDoctorRoleRequest.getDoctorId());
        Assert.assertNotNull(createDoctorRoleRequest.getDoctorStatus());
        Assert.assertNotNull(createDoctorRoleRequest.getEndDateRole());
        Assert.assertNotNull(createDoctorRoleRequest.getSiteCreation());
        Assert.assertNotNull(createDoctorRoleRequest.getSignatureSource());
        Assert.assertNotNull(createDoctorRoleRequest.getAirLineCode());
        Assert.assertNotNull(createDoctorRoleRequest.getSpeciality());

    }
}
