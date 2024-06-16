package com.afklm.repind.msv.doctor.attributes.service;

import com.afklm.repind.msv.doctor.attributes.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.criteria.role.DeleteDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.criteria.role.RetrieveDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.criteria.role.UpdateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.entity.Role;
import com.afklm.repind.msv.doctor.attributes.model.error.BusinessError;
import com.afklm.repind.msv.doctor.attributes.repository.IRoleRepository;
import com.afklm.repind.msv.doctor.attributes.service.encoder.role.CreateRoleEncoder;
import com.afklm.repind.msv.doctor.attributes.service.encoder.role.DeleteRoleEncoder;
import com.afklm.repind.msv.doctor.attributes.service.encoder.role.RetrieveRoleEncoder;
import com.afklm.repind.msv.doctor.attributes.service.encoder.role.UpdateRoleEncoder;
import com.afklm.repind.msv.doctor.attributes.utils.DoctorStatus;
import com.afklm.repind.msv.doctor.attributes.utils.exception.BusinessException;
import com.afklm.repind.msv.doctor.attributes.wrapper.role.WrapperDeleteDoctorRoleResponse;
import com.afklm.repind.msv.doctor.attributes.wrapper.role.WrapperRetrieveDoctorRoleResponse;
import com.afklm.repind.msv.doctor.attributes.wrapper.role.WrapperUpdateDoctorRoleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorRoleService {

    private final IRoleRepository roleRepository;

    private final RetrieveRoleEncoder retrieveRoleEncoder;

    private final CreateRoleEncoder createRoleEncoder;

    private final UpdateRoleEncoder updateRoleEncoder;

    private final DeleteRoleEncoder deleteRoleEncoder;


    @Transactional(readOnly = true)
    public ResponseEntity<WrapperRetrieveDoctorRoleResponse> retrieveDoctorRole(RetrieveDoctorRoleCriteria iRetrieveDoctorRoleCriteria) throws BusinessException
    {
        log.info("retrive doctor attribute service : {}", iRetrieveDoctorRoleCriteria.getRoleId());
        Role role ;
        Optional<Role> roleOpt = roleRepository.findByRoleId(iRetrieveDoctorRoleCriteria.getRoleId());

        if(roleOpt.isPresent())
        {
             role = roleOpt.get();
        }
        else
        {
            log.info("retrive doctor attribute service error role not found: {}", iRetrieveDoctorRoleCriteria.getRoleId());
            throw new BusinessException(BusinessError.ROLE_NOT_FOUND);
        }

        if(DoctorStatus.X.getAcronyme().equals(role.getDoctorStatus()))
        {
            log.info("retrive doctor attribute service error CANNOT_UPDATE_RETRIEVE_DELETE_SUPPRESSED_ROLE : {}", iRetrieveDoctorRoleCriteria.getRoleId());
            throw new BusinessException(BusinessError.CANNOT_UPDATE_RETRIEVE_DELETE_SUPPRESSED_ROLE);
        }

        return new ResponseEntity<>(retrieveRoleEncoder.decode(role), HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<WrapperRetrieveDoctorRoleResponse> createDoctorRole(CreateDoctorRoleCriteria iCreateDoctorRoleCriteria) throws BusinessException
    {
        log.info("create doctor attribute service : {}", iCreateDoctorRoleCriteria.getRoleId());

        Optional<Role> roleOpt = roleRepository.findByRoleId(iCreateDoctorRoleCriteria.getRoleId());
        if(roleOpt.isPresent()){
            log.info("create doctor attribute service error ROLE_ALREADY_EXISTS : {}", iCreateDoctorRoleCriteria.getRoleId());
            throw new BusinessException(BusinessError.ROLE_ALREADY_EXISTS);
        }

        Role roleTemp = roleRepository.findByDoctorId(iCreateDoctorRoleCriteria.getDoctorId());
        if( roleTemp != null){
            log.info("create doctor attribute service error DOCTOR_ID_ALREADY_EXISTS : {}", iCreateDoctorRoleCriteria.getRoleId());
            throw new BusinessException(BusinessError.DOCTOR_ID_ALREADY_EXISTS);
        }
        Role role = createRoleEncoder.encode(iCreateDoctorRoleCriteria);
        roleRepository.save(role);
        return new ResponseEntity<>(retrieveRoleEncoder.decode(role), HttpStatus.OK);

    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<WrapperUpdateDoctorRoleResponse> updateDoctorRole(UpdateDoctorRoleCriteria iUpdateDoctorRoleCriteria) throws BusinessException {

        log.info("update doctor attribute service  : {}", iUpdateDoctorRoleCriteria.getRoleId());
        Optional<Role> roleOpt = roleRepository.findByRoleId(iUpdateDoctorRoleCriteria.getRoleId());
       Role role ;
       if(roleOpt.isPresent())
       {
            role = roleOpt.get();
       }
       else
       {
           log.info("update doctor attribute service error ROLE_NOT_FOUND: {}", iUpdateDoctorRoleCriteria.getRoleId());
           throw new BusinessException(BusinessError.ROLE_NOT_FOUND);
       }

        Role roleTemp = roleRepository.findByDoctorIdAndRoleIdNot(iUpdateDoctorRoleCriteria.getDoctorId(), role.getRoleId());
        if( roleTemp != null){
            log.info("update doctor attribute service error DOCTOR_ID_ALREADY_EXISTS: {}", iUpdateDoctorRoleCriteria.getRoleId());
            throw new BusinessException(BusinessError.DOCTOR_ID_ALREADY_EXISTS);
        }

        if(DoctorStatus.X.getAcronyme().equals(role.getDoctorStatus())){
            log.info("update doctor attribute service error CANNOT_UPDATE_RETRIEVE_DELETE_SUPPRESSED_ROLE: {}", iUpdateDoctorRoleCriteria.getRoleId());
            throw new BusinessException(BusinessError.CANNOT_UPDATE_RETRIEVE_DELETE_SUPPRESSED_ROLE);
        }

        updateRoleEncoder.encode(role , iUpdateDoctorRoleCriteria);

        roleRepository.save(role);

        return new ResponseEntity<>(updateRoleEncoder.decode(role), HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<WrapperDeleteDoctorRoleResponse> deleteDoctorRole(DeleteDoctorRoleCriteria deleteDoctorRoleCriteria) throws BusinessException {
        Optional<Role> roleOpt = roleRepository.findByRoleId(deleteDoctorRoleCriteria.getRoleId());

        Role role ;
        if(roleOpt.isPresent())
        {
            role = roleOpt.get();
        }
        else
        {
            throw new BusinessException(BusinessError.ROLE_NOT_FOUND);
        }

        if(DoctorStatus.X.getAcronyme().equals(role.getDoctorStatus())){
            throw new BusinessException(BusinessError.CANNOT_UPDATE_RETRIEVE_DELETE_SUPPRESSED_ROLE);
        }
        role.setDoctorStatus(DoctorStatus.X.getAcronyme());
        role.setSignatureSourceModification(deleteDoctorRoleCriteria.getSignatureSource());
        role.setSignatureDateModification(deleteDoctorRoleCriteria.getSignatureDate());
        role.setLastUpdate(new Date());
        roleRepository.save(role);

        return new ResponseEntity<>(deleteRoleEncoder.decode(role), HttpStatus.OK);
    }

}
