package com.airfrance.batch.contract.deletecontract.processor;

import com.airfrance.batch.contract.deletecontract.model.DeleteBusinessRoleUCCROutputModel;
import com.airfrance.batch.contract.deletecontract.model.DeleteContractsOutputModel;
import com.airfrance.repind.dao.handicap.HandicapDataRepository;
import com.airfrance.repind.dao.handicap.HandicapRepository;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.entity.handicap.Handicap;
import com.airfrance.repind.entity.handicap.HandicapData;
import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.entity.role.RoleUCCR;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteContractsBatchProcessorTest {

    @Mock
    protected BusinessRoleDS businessRoleDS;

    @Mock
    protected HandicapRepository handicapRepository;

    @Mock
    protected HandicapDataRepository handicapDataRepository;

    @InjectMocks
    private DeleteContractsBatchProcessor deleteContractsBatchProcessor;

    @Test
    public void testProcessWithBRAndHandicap() throws Exception{

        when(businessRoleDS.findByGinAndType("1","C")).thenReturn(exampleBRList());
        when(handicapRepository.findByGin("1")).thenReturn(exampleHandicapList());
        when(handicapDataRepository.findByHandicapHandicapId(0L)).thenReturn(exampleHandicapDataList());

        DeleteContractsOutputModel result = deleteContractsBatchProcessor.process(roleContratsExampleWithBRAndHandicap());

        assertNotNull(result.getBusinessRoleDTO());
        assertEquals(result.getHandicapToDeleteList().size(),1);
        assertEquals(result.getHandicapDataToDeleteList().size(),1);
    }

    @Test
    public void testProcessWithBRWithoutHandicap() throws Exception{

        when(businessRoleDS.findByGinAndType("2","C")).thenReturn(exampleBRList2());

        DeleteContractsOutputModel result = deleteContractsBatchProcessor.process(roleContratsExampleWithBRWithoutHandicap());

        assertNotNull(result.getBusinessRoleDTO());
        assertNull(result.getHandicapToDeleteList());
        assertNull(result.getHandicapDataToDeleteList());
    }

    @Test
    public void testProcessWithoutBRWithHandicap() throws Exception{

        when(handicapRepository.findByGin("3")).thenReturn(exampleHandicapList2());

        DeleteContractsOutputModel result = deleteContractsBatchProcessor.process(roleContratsExampleWithoutBRWithHandicap());

        assertNull(result.getBusinessRoleDTO());
        assertEquals(result.getHandicapToDeleteList().size(),1);
        assertEquals(result.getHandicapDataToDeleteList().size(),0);
    }

    @Test
    public void testProcessWithoutBRAndHandicap() throws Exception{
        DeleteContractsOutputModel result = deleteContractsBatchProcessor.process(roleContratsExampleWithoutBRAndHandicap());

        assertNull(result.getBusinessRoleDTO());
        assertNull(result.getHandicapToDeleteList());
        assertNull(result.getHandicapDataToDeleteList());
    }

    public RoleContrats roleContratsExampleWithBRAndHandicap(){
        RoleContrats role = new RoleContrats();

        role.setGin("1");
        role.setNumeroContrat("bla1");
        role.setTypeContrat("S");

        return role;
    }

    public RoleContrats roleContratsExampleWithBRWithoutHandicap(){
        RoleContrats role = new RoleContrats();

        role.setGin("2");
        role.setNumeroContrat("bla2");
        role.setTypeContrat("random");

        return role;
    }

    public RoleContrats roleContratsExampleWithoutBRWithHandicap(){
        RoleContrats role = new RoleContrats();

        role.setGin("3");
        role.setNumeroContrat("bla3");
        role.setTypeContrat("S");

        return role;
    }

    public RoleContrats roleContratsExampleWithoutBRAndHandicap(){
        RoleContrats role = new RoleContrats();

        role.setGin("4");
        role.setNumeroContrat("bla4");
        role.setTypeContrat("random");

        return role;
    }

    public List<BusinessRoleDTO> exampleBRList(){
        BusinessRoleDTO businessRoleDTO = new BusinessRoleDTO();

        businessRoleDTO.setNumeroContrat("bla1");
        businessRoleDTO.setGinInd("1");
        businessRoleDTO.setType("C");

        List<BusinessRoleDTO> listBR = new ArrayList<>();
        listBR.add(businessRoleDTO);

        return listBR;
    }

    public List<BusinessRoleDTO> exampleBRList2(){
        BusinessRoleDTO businessRoleDTO = new BusinessRoleDTO();

        businessRoleDTO.setNumeroContrat("bla2");
        businessRoleDTO.setGinInd("2");
        businessRoleDTO.setType("C");

        List<BusinessRoleDTO> listBR = new ArrayList<>();
        listBR.add(businessRoleDTO);

        return listBR;
    }

    public List<Handicap> exampleHandicapList(){
        Handicap handicap = new Handicap();

        handicap.setGin("1");
        handicap.setHandicapId(0L);

        List<Handicap> listHandicap = new ArrayList<>();
        listHandicap.add(handicap);

        return listHandicap;
    }

    public List<Handicap> exampleHandicapList2(){
        Handicap handicap = new Handicap();

        handicap.setGin("3");
        handicap.setHandicapId(1L);

        List<Handicap> listHandicap = new ArrayList<>();
        listHandicap.add(handicap);

        return listHandicap;
    }

    public List<HandicapData> exampleHandicapDataList(){
        HandicapData handicapData = new HandicapData();

        handicapData.setHandicapDataId(0L);

        List<HandicapData> listHandicapData = new ArrayList<>();
        listHandicapData.add(handicapData);

        return listHandicapData;
    }
}
