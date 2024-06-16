package com.afklm.repind.msv.provide.contract.data.ut;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.contract.data.controller.Controller;
import com.afklm.repind.msv.provide.contract.data.models.stubs.Contract;
import com.afklm.repind.msv.provide.contract.data.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ControllerTest {
    private Controller controller;

    @Mock
    private ContractService contractService;

    @BeforeEach
    void setup(){
        //Non-empty list for success
        when(contractService.getContractList("CIN","9876543210")).thenReturn(List.of(new Contract()));
        when(contractService.getContractList("GIN","9876543210")).thenReturn(List.of(new Contract()));
        //Empty list for failure
        when(contractService.getContractList("CIN","1234567891")).thenReturn(new ArrayList<>());
        when(contractService.getContractList("GIN","1234567891")).thenReturn(new ArrayList<>());

        controller = new Controller(contractService);
    }

    @Test
    void testInNotFoundCase() {
        assertThrows(BusinessException.class,() -> controller.getAllContract("CIN","1234567891"));
        assertThrows(BusinessException.class,() -> controller.getAllContract("GIN","1234567891"));
    }

    @Test
    void testInIncorrectTypeCase() {
        assertThrows(BusinessException.class,() -> controller.getAllContract("Non-Existent type","1"));
        assertThrows(BusinessException.class,() -> controller.getAllContract(null,"1"));
    }

    @Test
    void testInIncorrectGinCase() {
        assertThrows(BusinessException.class,() -> controller.getAllContract("GIN","123465789435486789798797987897979"));
    }

    @Test
    void testInIncorrectCinCase() {
        assertAll(
                () -> assertThrows(BusinessException.class,() -> controller.getAllContract("CIN","123465789435486789798797987897979")),
                () -> assertThrows(BusinessException.class,() -> controller.getAllContract("CIN","1"))
        );
    }

    @Test
    void testMissingIdentifierCase() {
        assertThrows(BusinessException.class,() -> controller.getAllContract("CIN",null));
    }

    @Test
    void testNormalCase() {
        List<Contract> cinList = contractService.getContractList("CIN","9876543210");
        List<Contract> ginList = contractService.getContractList("GIN","9876543210");

        assertAll(
                () -> assertEquals(1, cinList.size()),
                () -> assertEquals(1, ginList.size())
        );
    }

    @Test
    void testCheckParameterCinInAllCase() throws BusinessException {
        String cinMA8 = "012345AF";
        String cin8 = "01234567";
        String cin9 = "012346578";
        String cin10 = "0123456789";
        String cin11 = "01234567891";
        String cin12 = "012345678912";
        String cin15 = "012345678912345";
        String cin20 = "ksuZiJecGeq1cUDo4IoM";
        String badCin7 = "0123456";
        String badCin13 = "0123456789123";
        String badCin21 = "000000000000000000000";

        assertEquals(cinMA8,controller.checkParameter("CIN",cinMA8));
        assertEquals("0000"+cin8,controller.checkParameter("CIN",cin8));
        assertEquals("000"+cin9,controller.checkParameter("CIN",cin9));
        assertEquals("00"+cin10,controller.checkParameter("CIN",cin10));
        assertEquals("0"+cin11,controller.checkParameter("CIN",cin11));
        assertEquals(cin12,controller.checkParameter("CIN",cin12));
        assertEquals(cin15,controller.checkParameter("CIN",cin15));

        assertThrows(BusinessException.class,() -> controller.checkParameter("CIN",null));
        assertThrows(BusinessException.class,() -> controller.checkParameter("CIN",badCin13));
        assertThrows(BusinessException.class,() -> controller.checkParameter("CIN",badCin7));
        assertThrows(BusinessException.class,() -> controller.checkParameter("CIN",badCin21));
    }

    @Test
    void testCheckParameterGinInAllCase() throws BusinessException {
        String ginInferior = "0123456789";
        String ginGood = "012345678912";
        String ginSuperior = "0123456789123";

        assertEquals("00"+ginInferior,controller.checkParameter("GIN",ginInferior));
        assertEquals(ginGood,controller.checkParameter("GIN",ginGood));
        assertThrows(BusinessException.class,() -> controller.checkParameter("CIN",ginSuperior));
    }
}
