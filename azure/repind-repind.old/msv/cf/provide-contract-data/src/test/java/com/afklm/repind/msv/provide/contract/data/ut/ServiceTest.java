package com.afklm.repind.msv.provide.contract.data.ut;

import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.enums.ContractType;
import com.afklm.repind.common.repository.role.BusinessRoleRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.common.repository.role.RoleTravelersRepository;
import com.afklm.repind.common.repository.role.RoleUCCRRepository;
import com.afklm.repind.msv.provide.contract.data.helper.GenerateTestData;
import com.afklm.repind.msv.provide.contract.data.models.stubs.Contract;
import com.afklm.repind.msv.provide.contract.data.models.stubs.SignatureElement;
import com.afklm.repind.msv.provide.contract.data.service.ContractService;
import com.afklm.repind.msv.provide.contract.data.transform.ContractTransform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ServiceTest {

    @Mock
    private BusinessRoleRepository businessRoleRepository;

    @Mock
    private RoleContractRepository roleContractRepository;

    @Mock
    private RoleUCCRRepository roleUCCRRepository;

    @Mock
    private RoleTravelersRepository roleTravelersRepository;

    private ContractService contractService;
    private ContractTransform contractTransform;

    @BeforeEach
    void setup() {
        contractTransform = new ContractTransform();

        when(businessRoleRepository.findByNumeroContrat("009876543210")).thenReturn(GenerateTestData.createBusinessRoleList().get(0));
        when(businessRoleRepository.findBusinessRolesByGinInd("123456789123")).thenReturn(GenerateTestData.createBusinessRoleList());
        when(roleContractRepository.findByCleRole(123987550)).thenReturn(GenerateTestData.createRoleContrat());
        when(roleUCCRRepository.findByCleRole(123987549)).thenReturn(GenerateTestData.createRoleUCCR());
        when(roleTravelersRepository.findByCleRole(123987547)).thenReturn(GenerateTestData.createRoleTravelers());

        BusinessRole tmp = new BusinessRole();
        tmp.setGinInd("789123465123");
        tmp.setCleRole(963852741);
        tmp.setType("C");

        BusinessRole tmp2 = new BusinessRole();
        tmp.setGinInd("789123465123");
        tmp.setCleRole(963852742);
        tmp.setType("T");

        BusinessRole tmp3 = new BusinessRole();
        tmp.setGinInd("789123465123");
        tmp.setCleRole(963852743);
        tmp.setType("U");

        when(businessRoleRepository.findBusinessRolesByGinInd("789123456123")).thenReturn(List.of(tmp,tmp2,tmp3));
        when(roleContractRepository.findByCleRole(963852741)).thenReturn(null);
        when(roleTravelersRepository.findByCleRole(963852742)).thenReturn(null);
        when(roleUCCRRepository.findByCleRole(963852743)).thenReturn(null);

        contractService = new ContractService(businessRoleRepository, roleContractRepository, roleUCCRRepository, roleTravelersRepository, contractTransform);
    }

    @Test
    void testMapBusinessRoleToContractWithGoodBusinessRoleContract(){
        Contract contract = contractService.mapBusinessRoleToContract(GenerateTestData.createBusinessRoleList().get(2));

        Contract expectedContract = new Contract();
        expectedContract.setContractNumber(GenerateTestData.createBusinessRoleList().get(2).getNumeroContrat());
        expectedContract.setContractType("MA");
        expectedContract.setValidityStartDate(LocalDate.of(2023,5,11));
        expectedContract.setValidityEndDate(LocalDate.of(2023,5,11));
        expectedContract.setContractStatus("C");
        expectedContract.setIataCode("012345678");
        expectedContract.setCompanyCode("AF");

        SignatureElement signatureCreation = new SignatureElement(LocalDate.of(2023,5,11), "RI", "QVI");
        expectedContract.setSignatureCreation(signatureCreation);
        SignatureElement signatureModification = new SignatureElement(LocalDate.of(2023,5,11), "RI", "QVI");
        expectedContract.setSignatureModification(signatureModification);


        assertAll(
                () -> assertEquals(expectedContract.getValidityEndDate(), contract.getValidityEndDate()),
                () -> assertEquals(expectedContract.getValidityStartDate(), contract.getValidityStartDate()),
                () -> assertEquals(expectedContract.getContractNumber(), contract.getContractNumber()),
                () -> assertEquals(expectedContract.getContractStatus(), contract.getContractStatus()),
                () -> assertEquals(expectedContract.getContractType(), contract.getContractType()),
                () -> assertEquals(expectedContract.getCorporateEnvironmentID(), contract.getCorporateEnvironmentID())
        );
        testSignatureBetweenTwoContract(expectedContract,contract);
    }

    @Test
    void testMapBusinessRoleToContractWithGoodBusinessRoleTraveler() {
        Contract contractTraveler = contractService.mapBusinessRoleToContract(GenerateTestData.createBusinessRoleList().get(0));

        Contract expectedTraveler = new Contract();
        expectedTraveler.setContractNumber(GenerateTestData.createBusinessRoleList().get(0).getNumeroContrat());
        expectedTraveler.setContractType(ContractType.ROLE_TRAVELERS.toString());
        expectedTraveler.setMatchingRecognition(GenerateTestData.createRoleTravelers().getMatchingRecognitionCode());
        expectedTraveler.setLastRecognitionDate(GenerateTestData.createRoleTravelers().getLastRecognitionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        SignatureElement signatureCreation = new SignatureElement(LocalDate.of(2023,5,11), "RI", "QVI");
        expectedTraveler.setSignatureCreation(signatureCreation);
        SignatureElement signatureModification = new SignatureElement(LocalDate.of(2023,5,11), "RI", "QVI");
        expectedTraveler.setSignatureModification(signatureModification);

        assertAll(
                () -> assertEquals(expectedTraveler.getValidityEndDate(), contractTraveler.getValidityEndDate()),
                () -> assertEquals(expectedTraveler.getValidityStartDate(), contractTraveler.getValidityStartDate()),
                () -> assertEquals(expectedTraveler.getIataCode(), contractTraveler.getIataCode()),
                () -> assertEquals(expectedTraveler.getContractStatus(), contractTraveler.getContractStatus()),
                () -> assertEquals(expectedTraveler.getContractType(), contractTraveler.getContractType()),
                () -> assertEquals(expectedTraveler.getContractSubType(), contractTraveler.getContractSubType()),
                () -> assertEquals(expectedTraveler.getCompanyCode(), contractTraveler.getCompanyCode())
        );
        testSignatureBetweenTwoContract(expectedTraveler,contractTraveler);
    }

    @Test
    void testMapBusinessRoleToContractWithGoodBusinessRoleUCCR() {
        Contract contractUCCR = contractService.mapBusinessRoleToContract(GenerateTestData.createBusinessRoleList().get(1));

        Contract expectedUCCR = new Contract();
        expectedUCCR.setContractNumber(GenerateTestData.createBusinessRoleList().get(1).getNumeroContrat());
        expectedUCCR.setContractType(ContractType.ROLE_UCCR.toString());
        expectedUCCR.setCorporateEnvironmentID("876543210");
        expectedUCCR.setValidityStartDate(LocalDate.of(2023,5,11));
        expectedUCCR.setValidityEndDate(LocalDate.of(2023,5,11));
        expectedUCCR.setContractStatus("X");

        SignatureElement signatureCreation = new SignatureElement(LocalDate.of(2023,5,11), "RI", "QVI");
        expectedUCCR.setSignatureCreation(signatureCreation);
        SignatureElement signatureModification = new SignatureElement(LocalDate.of(2023,5,11), "RI", "QVI");
        expectedUCCR.setSignatureModification(signatureModification);

        assertAll(
                () -> assertEquals(expectedUCCR.getValidityEndDate(), contractUCCR.getValidityEndDate()),
                () -> assertEquals(expectedUCCR.getValidityStartDate(), contractUCCR.getValidityStartDate()),
                () -> assertEquals(expectedUCCR.getContractNumber(), contractUCCR.getContractNumber()),
                () -> assertEquals(expectedUCCR.getContractStatus(), contractUCCR.getContractStatus()),
                () -> assertEquals(expectedUCCR.getContractType(), contractUCCR.getContractType()),
                () -> assertEquals(expectedUCCR.getCorporateEnvironmentID(), contractUCCR.getCorporateEnvironmentID())
        );
        testSignatureBetweenTwoContract(expectedUCCR,contractUCCR);
    }

    @Test
    void testMapBusinessRoleToContractWithGoodBusinessRoleDoctor() {
        Contract contractDoctor = contractService.mapBusinessRoleToContract(GenerateTestData.createBusinessRoleList().get(3));

        Contract expectedDoctor = new Contract();
        expectedDoctor.setContractNumber(GenerateTestData.createBusinessRoleList().get(3).getNumeroContrat());
        expectedDoctor.setContractType(ContractType.CONTRACT_DOCTOR.toString());

        assertAll(
                () -> assertEquals(GenerateTestData.createBusinessRoleList().get(3).getNumeroContrat(),contractDoctor.getContractNumber()),
                () -> assertEquals(ContractType.CONTRACT_DOCTOR.toString(),contractDoctor.getContractType())
        );
    }

    @Test
    void testMapBusinessRoleToContractWithInvalidType(){
        BusinessRole businessRole = GenerateTestData.createBusinessRoleList().get(0);

        businessRole.setType("zefzefnzejkfnzenfz");
        assertNull(contractService.mapBusinessRoleToContract(businessRole));

        businessRole.setType("");
        assertNull(contractService.mapBusinessRoleToContract(businessRole));

        businessRole.setType(null);
        assertNull(contractService.mapBusinessRoleToContract(businessRole));
    }

    @Test
    void testGetContractListWithCorrectGinAndCin(){
        List<Contract> cinList = contractService.getContractList("CIN","009876543210");
        assertEquals(GenerateTestData.createBusinessRoleList().size(),cinList.size());

        List<Contract> ginList = contractService.getContractList("GIN","123456789123");
        assertEquals(GenerateTestData.createBusinessRoleList().size(),ginList.size());
    }

    @Test
    void testGetContractListWithIncorrectIdentifier() {
        List<Contract> cinList = contractService.getContractList("CIN","1zadazdazdazd");
        assertTrue(cinList.isEmpty());

        List<Contract> ginList = contractService.getContractList("GIN","1234567891azdazdazdazd23");
        assertTrue(ginList.isEmpty());
    }

    void testSignatureBetweenTwoContract(Contract a, Contract b){
        assertAll(
                () -> assertEquals(a.getSignatureCreation().getSignature(),b.getSignatureCreation().getSignature()),
                () -> assertEquals(a.getSignatureCreation().getDate(),b.getSignatureCreation().getDate()),
                () -> assertEquals(a.getSignatureCreation().getSite(),b.getSignatureCreation().getSite()),
                () -> assertEquals(a.getSignatureModification().getSignature(),b.getSignatureModification().getSignature()),
                () -> assertEquals(a.getSignatureModification().getDate(),b.getSignatureModification().getDate()),
                () -> assertEquals(a.getSignatureModification().getSite(),b.getSignatureModification().getSite())
        );
    }

    @Test
    void testMapForAllTypeOfRoleWhenRoleIsNull(){
        List<BusinessRole> businessRoleList = contractService.findAllBusinessRoleByGin("789123456123");
        for(BusinessRole businessRole : businessRoleList) {
            assertNull(contractService.mapBusinessRoleToContract(businessRole));
        }
    }

    @Test
    void testGetContractListWhenRoleIsNull(){
        assertEquals(new ArrayList<Contract>(),contractService.getContractList("GIN","789123456123"));
    }
}
