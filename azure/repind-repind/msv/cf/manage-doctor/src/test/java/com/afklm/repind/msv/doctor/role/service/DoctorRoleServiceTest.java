package com.afklm.repind.msv.doctor.role.service;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.doctor.role.client.role.create.CreateDoctorRoleClient;
import com.afklm.repind.msv.doctor.role.client.role.create.model.CreateDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.client.role.delete.DeleteDoctorRoleClient;
import com.afklm.repind.msv.doctor.role.client.role.delete.model.DeleteDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import com.afklm.repind.msv.doctor.role.client.role.retrieve.RetrieveDoctorRoleClient;
import com.afklm.repind.msv.doctor.role.client.role.retrieve.model.RetrieveDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.client.role.upsert.UpsertDoctorRoleClient;
import com.afklm.repind.msv.doctor.role.client.role.upsert.model.UpsertDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.criteria.role.DeleteDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.criteria.role.RetrieveDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.criteria.role.UpsertDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.entity.BusinessRole;
import com.afklm.repind.msv.doctor.role.model.error.ErrorMessage;
import com.afklm.repind.msv.doctor.role.repository.BusinessRoleRepository;
import com.afklm.repind.msv.doctor.role.service.encoder.role.CreateRoleEncoder;
import com.afklm.repind.msv.doctor.role.service.encoder.role.DeleteRoleEncoder;
import com.afklm.repind.msv.doctor.role.service.encoder.role.RetrieveRoleEncoder;
import com.afklm.repind.msv.doctor.role.service.encoder.role.UpsertRoleEncoder;
import com.afklm.repind.msv.doctor.role.utils.IConstants;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperCreateDoctorRoleResponse;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperDoctorRoleResponse;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperRetrieveDoctorRoleResponse;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperUpsertDoctorRoleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
 class DoctorRoleServiceTest {

    @Mock
    private BusinessRoleRepository businessRoleRepository;

    @Mock
    private DeleteDoctorRoleClient deleteDoctorRoleClient;

    @Mock
    private CreateDoctorRoleClient createDoctorRoleClient;

    @Mock
    private UpsertDoctorRoleClient upsertDoctorRoleClient;

    @Mock
    private RetrieveDoctorRoleClient retrieveDoctorRoleClient;

    @Mock
    private RetrieveRoleEncoder retrieveRoleEncoder;

    @Mock
    private CreateRoleEncoder createRoleEncoder;

    @Mock
    private UpsertRoleEncoder upsertRoleEncoder;

    @Mock
    private DeleteRoleEncoder deleteRoleEncoder;

    private DoctorRoleService doctorRoleService;

    @BeforeEach
    public void setUp() {
        doctorRoleService = new DoctorRoleService( businessRoleRepository, deleteDoctorRoleClient, createDoctorRoleClient, upsertDoctorRoleClient, retrieveDoctorRoleClient, retrieveRoleEncoder, createRoleEncoder, upsertRoleEncoder, deleteRoleEncoder);
    }

    private static final String roleId = "123456";
    private static final String roleIdUpdate = "654321";
    private static final String gin = "012345678912";

    @Test
    void retrieve() throws BusinessException {
        RetrieveDoctorRoleCriteria retrieveDoctorRoleCriteria = new RetrieveDoctorRoleCriteria();
        retrieveDoctorRoleCriteria.withRoleId(roleId);
        RetrieveDoctorRoleRequest retrieveDoctorRoleRequest = new RetrieveDoctorRoleRequest();
        retrieveDoctorRoleRequest.setRoleId(roleId);
        DoctorRoleResponseModel retrieveDoctorRoleResponse = new DoctorRoleResponseModel();
        retrieveDoctorRoleResponse.setRoleId(roleId);
        WrapperRetrieveDoctorRoleResponse wrapperRetrieveDoctorRoleResponse =new WrapperRetrieveDoctorRoleResponse();
        wrapperRetrieveDoctorRoleResponse.setContractNumber(roleId);

        when(retrieveRoleEncoder.encode(retrieveDoctorRoleCriteria.getRoleId())).thenReturn(retrieveDoctorRoleRequest);
        when(retrieveDoctorRoleClient.execute(retrieveDoctorRoleRequest)).thenReturn(retrieveDoctorRoleResponse);
        when(retrieveRoleEncoder.decode(retrieveDoctorRoleResponse)).thenReturn(wrapperRetrieveDoctorRoleResponse);

        ResponseEntity<WrapperRetrieveDoctorRoleResponse> response = doctorRoleService.retrieve(retrieveDoctorRoleCriteria);

        assertEquals(retrieveDoctorRoleResponse.getRoleId(), response.getBody().getContractNumber());
    }

    @Test
    void create() throws NoSuchAlgorithmException, BusinessException {
        CreateDoctorRoleCriteria createDoctorRoleCriteria = new CreateDoctorRoleCriteria()
                .withGin(gin)
                .withDoctorId("010203")
                .withDoctorStatus("V")
                .withAirLineCode("AF")
                .withEndDateRole("2036-01-01T00:00:00Z")
                .withSpeciality("CARD")
                .withType("D");
        BusinessRole businessRole = new BusinessRole();
        businessRole.setNumeroContrat(roleId);
        DoctorRoleResponseModel createDoctorRoleResponse = new DoctorRoleResponseModel();
        createDoctorRoleResponse.setRoleId(roleId);
        createDoctorRoleResponse.setGin(gin);
        createDoctorRoleResponse.setDoctorId("159az753fg28bb");
        createDoctorRoleResponse.setStatus("V");
        CreateDoctorRoleRequest createDoctorRoleRequest = new CreateDoctorRoleRequest();
        createDoctorRoleRequest.setRoleId(roleId);
        WrapperCreateDoctorRoleResponse wrapperCreateDoctorRoleResponse = new WrapperCreateDoctorRoleResponse();
        wrapperCreateDoctorRoleResponse.setContractNumber(roleId);

        when(businessRoleRepository.findBusinessRuleByGinIndAndType(createDoctorRoleCriteria.getGin() , IConstants.DOCTOR_TYPE)).thenReturn(Optional.empty());
        when(createRoleEncoder.encode(createDoctorRoleCriteria)).thenReturn(businessRole);
        when(createRoleEncoder.encodeCreateDoctorRequest(businessRole.getNumeroContrat() , createDoctorRoleCriteria)).thenReturn(createDoctorRoleRequest);
        when(createDoctorRoleClient.execute(createDoctorRoleRequest)).thenReturn(createDoctorRoleResponse);
        when(createRoleEncoder.decode(createDoctorRoleResponse)).thenReturn(wrapperCreateDoctorRoleResponse);

        doctorRoleService.create(createDoctorRoleCriteria);

        verify(businessRoleRepository).save(businessRole);
    }

    @Test
    void createWithException(){

        CreateDoctorRoleCriteria createDoctorRoleCriteria = new CreateDoctorRoleCriteria();
        createDoctorRoleCriteria.withGin(gin);

        BusinessRole businessRole = new BusinessRole();
        businessRole.setNumeroContrat(roleId);

        Optional<BusinessRole> businessRuleByGinInd = Optional.of(businessRole);

        when(businessRoleRepository.findBusinessRuleByGinIndAndType(createDoctorRoleCriteria.getGin() , IConstants.DOCTOR_TYPE)).thenReturn(businessRuleByGinInd);

        Throwable exception = assertThrows(BusinessException.class, () -> doctorRoleService.create(createDoctorRoleCriteria));


        assertEquals(ErrorMessage.ERROR_MESSAGE_409_001.getDescription(), exception.getMessage());
    }

    @Test
    void createWithTimeOut() throws NoSuchAlgorithmException, BusinessException {
        CreateDoctorRoleCriteria createDoctorRoleCriteria = new CreateDoctorRoleCriteria()
                .withGin(gin)
                .withDoctorId("010203")
                .withDoctorStatus("V")
                .withAirLineCode("AF")
                .withEndDateRole("2036-01-01T00:00:00Z")
                .withSpeciality("CARD")
                .withType("D");
        BusinessRole businessRole = new BusinessRole();
        businessRole.setNumeroContrat(roleId);
        DoctorRoleResponseModel createDoctorRoleResponse = new DoctorRoleResponseModel();
        createDoctorRoleResponse.setRoleId(roleId);
        createDoctorRoleResponse.setGin(gin);
        createDoctorRoleResponse.setDoctorId("159az753fg28bb");
        createDoctorRoleResponse.setStatus("V");
        CreateDoctorRoleRequest createDoctorRoleRequest = new CreateDoctorRoleRequest();
        createDoctorRoleRequest.setRoleId(roleId);
        WrapperCreateDoctorRoleResponse wrapperCreateDoctorRoleResponse = new WrapperCreateDoctorRoleResponse();
        wrapperCreateDoctorRoleResponse.setContractNumber(roleId);
        DeleteDoctorRoleCriteria iDeleteDoctorRoleCriteria = new DeleteDoctorRoleCriteria().withRoleId(roleId);
        DeleteDoctorRoleRequest deleteDoctorRoleRequest = new DeleteDoctorRoleRequest();
        deleteDoctorRoleRequest.setRoleId(roleId);
        DoctorRoleResponseModel deleteDoctorRoleResponse = new DoctorRoleResponseModel();
        deleteDoctorRoleResponse.setRoleId(roleId);
        WrapperDoctorRoleResponse wrapperDoctorRoleResponse = new WrapperDoctorRoleResponse();
        wrapperDoctorRoleResponse.setContractNumber(roleId);
        SocketTimeoutException socketTimeoutException = new SocketTimeoutException("Time out");
        ResourceAccessException resourceAccessException = new ResourceAccessException("Time out",socketTimeoutException);

        when(businessRoleRepository.findBusinessRuleByGinIndAndType(createDoctorRoleCriteria.getGin() , IConstants.DOCTOR_TYPE)).thenReturn(Optional.empty());
        when(createRoleEncoder.encode(createDoctorRoleCriteria)).thenReturn(businessRole);
        when(createRoleEncoder.encodeCreateDoctorRequest(businessRole.getNumeroContrat() , createDoctorRoleCriteria)).thenReturn(createDoctorRoleRequest);
        when(createDoctorRoleClient.execute(createDoctorRoleRequest)).thenAnswer( invocation -> { throw resourceAccessException; });
        when(createRoleEncoder.decode(createDoctorRoleResponse)).thenReturn(wrapperCreateDoctorRoleResponse);
        when(deleteRoleEncoder.encode(iDeleteDoctorRoleCriteria)).thenReturn(deleteDoctorRoleRequest);
        when(deleteDoctorRoleClient.execute(deleteDoctorRoleRequest)).thenReturn(deleteDoctorRoleResponse);
        when(deleteRoleEncoder.decode(deleteDoctorRoleResponse)).thenReturn(wrapperDoctorRoleResponse);

        Throwable exception = assertThrows(ResourceAccessException.class, () -> doctorRoleService.create(createDoctorRoleCriteria));

        assertEquals(ResourceAccessException.class, exception.getClass());
    }

    @Test
    void upsert() throws BusinessException {
        UpsertDoctorRoleCriteria upsertDoctorRoleCriteria = new UpsertDoctorRoleCriteria();
        upsertDoctorRoleCriteria.withRoleId(roleIdUpdate);
        UpsertDoctorRoleRequest upsertDoctorRoleRequest = new UpsertDoctorRoleRequest();
        upsertDoctorRoleRequest.setRoleId(roleIdUpdate);
        DoctorRoleResponseModel upsertDoctorRoleResponse = new DoctorRoleResponseModel();
        upsertDoctorRoleResponse.setRoleId(roleIdUpdate);
        WrapperUpsertDoctorRoleResponse wrapperUpsertDoctorRoleResponse = new WrapperUpsertDoctorRoleResponse();
        wrapperUpsertDoctorRoleResponse.setContractNumber(roleIdUpdate);

        when(upsertRoleEncoder.encodeUpdateDoctorRequest(upsertDoctorRoleCriteria.getRoleId(), upsertDoctorRoleCriteria)).thenReturn(upsertDoctorRoleRequest);
        when(upsertDoctorRoleClient.execute(upsertDoctorRoleRequest)).thenReturn(upsertDoctorRoleResponse);
        when(upsertRoleEncoder.decode(upsertDoctorRoleResponse)).thenReturn(wrapperUpsertDoctorRoleResponse);

        ResponseEntity<WrapperUpsertDoctorRoleResponse> response = doctorRoleService.upsert(upsertDoctorRoleCriteria);


        assertEquals(upsertDoctorRoleResponse.getRoleId(), response.getBody().getContractNumber());

    }

    @Test
    void delete() throws BusinessException {
        DeleteDoctorRoleCriteria iDeleteDoctorRoleCriteria = new DeleteDoctorRoleCriteria().withRoleId(roleId);
        DeleteDoctorRoleRequest deleteDoctorRoleRequest = new DeleteDoctorRoleRequest();
        deleteDoctorRoleRequest.setRoleId(roleId);
        DoctorRoleResponseModel deleteDoctorRoleResponse = new DoctorRoleResponseModel();
        deleteDoctorRoleResponse.setRoleId(roleId);
        WrapperDoctorRoleResponse wrapperDoctorRoleResponse = new WrapperDoctorRoleResponse();
        wrapperDoctorRoleResponse.setContractNumber(roleId);

        when(deleteRoleEncoder.encode(iDeleteDoctorRoleCriteria)).thenReturn(deleteDoctorRoleRequest);
        when(deleteDoctorRoleClient.execute(deleteDoctorRoleRequest)).thenReturn(deleteDoctorRoleResponse);
        when(deleteRoleEncoder.decode(deleteDoctorRoleResponse)).thenReturn(wrapperDoctorRoleResponse);

        ResponseEntity<WrapperDoctorRoleResponse> response = doctorRoleService.delete(iDeleteDoctorRoleCriteria);

        assertEquals(iDeleteDoctorRoleCriteria.getRoleId(), response.getBody().getContractNumber());




    }
}
