package com.airfrance.batch.contract.deletecontract.processor;

import com.airfrance.batch.contract.deletecontract.model.DeleteBusinessRoleUCCROutputModel;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.entity.role.RoleUCCR;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteBusinessRoleUCCRProcessorTest {

    @Mock
    protected BusinessRoleDS businessRoleDS;

    @InjectMocks
    private DeleteBusinessRoleUCCRProcessor deleteBusinessRoleUCCRProcessor;

    @Test
    public void testProcessWithBR() throws Exception{

        List<BusinessRoleDTO> listBR = new ArrayList<>();
        listBR.add(exampleBR());

        when(businessRoleDS.findByGinAndType("1","U")).thenReturn(listBR);

        DeleteBusinessRoleUCCROutputModel result = deleteBusinessRoleUCCRProcessor.process(roleUCCRExampleWithBR());

        assertNotNull(result.getBusinessRoleDTO());
    }

    @Test
    public void testProcessWithoutBR() throws Exception{
        DeleteBusinessRoleUCCROutputModel result = deleteBusinessRoleUCCRProcessor.process(roleUCCRExampleWithoutBR());

        assertNull(result.getBusinessRoleDTO());
    }

    public RoleUCCR roleUCCRExampleWithBR(){
        RoleUCCR role = new RoleUCCR();

        role.setUccrID("bla1");
        role.setGin("1");
        role.setCleRole(0);

        return role;
    }

    public BusinessRoleDTO exampleBR(){
        BusinessRoleDTO businessRoleDTO = new BusinessRoleDTO();

        businessRoleDTO.setNumeroContrat("bla1");
        businessRoleDTO.setCleRole(0);
        businessRoleDTO.setGinInd("1");
        businessRoleDTO.setType("U");

        return businessRoleDTO;
    }

    public RoleUCCR roleUCCRExampleWithoutBR(){
        RoleUCCR role = new RoleUCCR();

        role.setUccrID("bla2");
        role.setGin("2");

        return role;
    }
}
