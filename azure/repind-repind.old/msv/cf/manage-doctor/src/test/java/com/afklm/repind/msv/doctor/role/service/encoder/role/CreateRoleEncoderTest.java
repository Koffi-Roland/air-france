package com.afklm.repind.msv.doctor.role.service.encoder.role;

import com.afklm.repind.msv.doctor.role.client.role.create.model.CreateDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.util.GenerateTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateRoleEncoderTest {


    @Test
    void testEncodeCreateDoctorRequest() {

        CreateRoleEncoder createRoleEncoder  = new CreateRoleEncoder();

        CreateDoctorRoleRequest createDoctorRoleRequest = createRoleEncoder.encodeCreateDoctorRequest("159753AD64ER82", GenerateTestData.createDoctorRole());
        
        assertNotNull(createDoctorRoleRequest);

    }

}
