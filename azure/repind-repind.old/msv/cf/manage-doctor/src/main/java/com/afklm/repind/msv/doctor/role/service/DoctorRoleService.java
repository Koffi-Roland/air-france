package com.afklm.repind.msv.doctor.role.service;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.doctor.role.client.role.create.CreateDoctorRoleClient;
import com.afklm.repind.msv.doctor.role.client.role.delete.DeleteDoctorRoleClient;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import com.afklm.repind.msv.doctor.role.client.role.retrieve.RetrieveDoctorRoleClient;
import com.afklm.repind.msv.doctor.role.client.role.upsert.UpsertDoctorRoleClient;
import com.afklm.repind.msv.doctor.role.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.criteria.role.DeleteDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.criteria.role.RetrieveDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.criteria.role.UpsertDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.entity.BusinessRole;
import com.afklm.repind.msv.doctor.role.model.error.BusinessError;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class DoctorRoleService {


    private final BusinessRoleRepository businessRoleRepository;


    private final DeleteDoctorRoleClient deleteDoctorRoleClient;


    private final CreateDoctorRoleClient createDoctorRoleClient;


    private final UpsertDoctorRoleClient upsertDoctorRoleClient;


    private final RetrieveDoctorRoleClient retrieveDoctorRoleClient;


    private final RetrieveRoleEncoder retrieveRoleEncoder;


    private final CreateRoleEncoder createRoleEncoder;


    private final UpsertRoleEncoder upsertRoleEncoder;


    private final DeleteRoleEncoder deleteRoleEncoder;

    public DoctorRoleService(BusinessRoleRepository businessRoleRepository, DeleteDoctorRoleClient deleteDoctorRoleClient, CreateDoctorRoleClient createDoctorRoleClient, UpsertDoctorRoleClient upsertDoctorRoleClient, RetrieveDoctorRoleClient retrieveDoctorRoleClient, RetrieveRoleEncoder retrieveRoleEncoder, CreateRoleEncoder createRoleEncoder, UpsertRoleEncoder upsertRoleEncoder, DeleteRoleEncoder deleteRoleEncoder) {
        this.businessRoleRepository = businessRoleRepository;
        this.deleteDoctorRoleClient = deleteDoctorRoleClient;
        this.createDoctorRoleClient = createDoctorRoleClient;
        this.upsertDoctorRoleClient = upsertDoctorRoleClient;
        this.retrieveDoctorRoleClient = retrieveDoctorRoleClient;
        this.retrieveRoleEncoder = retrieveRoleEncoder;
        this.createRoleEncoder = createRoleEncoder;
        this.upsertRoleEncoder = upsertRoleEncoder;
        this.deleteRoleEncoder = deleteRoleEncoder;
    }


    @Transactional(readOnly = true)
    public ResponseEntity<WrapperRetrieveDoctorRoleResponse> retrieve(RetrieveDoctorRoleCriteria retrieveDoctorRoleCriteria) throws BusinessException {
        log.info("retrieve doctor role service : {}", retrieveDoctorRoleCriteria.getRoleId());

        DoctorRoleResponseModel retrieveDoctorRoleResponse = retrieveDoctorRoleClient.execute(retrieveRoleEncoder.encode(retrieveDoctorRoleCriteria.getRoleId()));
        WrapperRetrieveDoctorRoleResponse wrapperRetrieveDoctorRoleResponse = retrieveRoleEncoder.decode(retrieveDoctorRoleResponse);
        return new ResponseEntity<>(wrapperRetrieveDoctorRoleResponse, HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<WrapperCreateDoctorRoleResponse> create(CreateDoctorRoleCriteria createDoctorRoleCriteria) throws BusinessException, NoSuchAlgorithmException {
        log.info("create doctor role service : {}", createDoctorRoleCriteria.getGin());
        Optional<BusinessRole> businessRuleByGinInd = businessRoleRepository.findBusinessRuleByGinIndAndType(createDoctorRoleCriteria.getGin() , IConstants.DOCTOR_TYPE);
        if(businessRuleByGinInd.isPresent()){
            log.info("retrieve doctor role service  error : {}", createDoctorRoleCriteria.getGin());
            throw new BusinessException(BusinessError.ROLE_ALREADY_EXISTS);
        }

        BusinessRole businessRole = createRoleEncoder.encode(createDoctorRoleCriteria);
        businessRoleRepository.save(businessRole);

        try{
            DoctorRoleResponseModel createDoctorRoleResponse = createDoctorRoleClient.execute(createRoleEncoder.encodeCreateDoctorRequest(businessRole.getNumeroContrat() , createDoctorRoleCriteria));
            WrapperCreateDoctorRoleResponse wrapperCreateDoctorRoleResponse = createRoleEncoder.decode(createDoctorRoleResponse);

            return new ResponseEntity<>(wrapperCreateDoctorRoleResponse, HttpStatus.OK);
        }
        catch (ResourceAccessException e){
            if (e.getCause().getClass().equals(SocketTimeoutException.class)){
                DeleteDoctorRoleCriteria iDeleteDoctorRoleCriteria = new DeleteDoctorRoleCriteria(businessRole.getNumeroContrat());
                deleteDoctorRoleClient.execute(deleteRoleEncoder.encode(iDeleteDoctorRoleCriteria));
            }

            throw new ResourceAccessException(Objects.requireNonNull(e.getMessage()));
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<WrapperUpsertDoctorRoleResponse> upsert(UpsertDoctorRoleCriteria upsertDoctorRoleCriteria) throws BusinessException {
        // Build response (only gin and time of the query)
        log.info("update doctor role service : {}", upsertDoctorRoleCriteria.getRoleId());

        DoctorRoleResponseModel upsertDoctorRoleResponse = upsertDoctorRoleClient.execute(upsertRoleEncoder.encodeUpdateDoctorRequest(upsertDoctorRoleCriteria.getRoleId(), upsertDoctorRoleCriteria));

        WrapperUpsertDoctorRoleResponse wrapperUpsertDoctorRoleResponse = upsertRoleEncoder.decode(upsertDoctorRoleResponse);
        return new ResponseEntity<>(wrapperUpsertDoctorRoleResponse, HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<WrapperDoctorRoleResponse> delete(DeleteDoctorRoleCriteria iDeleteDoctorRoleCriteria) throws BusinessException {
        log.info("delete doctor role service : {}", iDeleteDoctorRoleCriteria.getRoleId());

        DoctorRoleResponseModel deleteDoctorRoleResponse = deleteDoctorRoleClient.execute(deleteRoleEncoder.encode(iDeleteDoctorRoleCriteria));
        WrapperDoctorRoleResponse wrapperDoctorRoleResponse = deleteRoleEncoder.decode(deleteDoctorRoleResponse);

        return new ResponseEntity<>(wrapperDoctorRoleResponse, HttpStatus.OK);
    }
}
