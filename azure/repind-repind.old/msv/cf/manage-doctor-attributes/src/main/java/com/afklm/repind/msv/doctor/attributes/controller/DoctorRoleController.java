package com.afklm.repind.msv.doctor.attributes.controller;

import com.afklm.repind.msv.doctor.attributes.controller.checker.DoctorRoleChecker;
import com.afklm.repind.msv.doctor.attributes.criteria.role.IndividuCriteria;
import com.afklm.repind.msv.doctor.attributes.model.DoctorAttributesModel;
import com.afklm.repind.msv.doctor.attributes.service.DoctorRoleService;
import com.afklm.repind.msv.doctor.attributes.utils.exception.BusinessException;
import com.afklm.repind.msv.doctor.attributes.wrapper.role.WrapperCreateDoctorRoleResponse;
import com.afklm.repind.msv.doctor.attributes.wrapper.role.WrapperDeleteDoctorRoleResponse;
import com.afklm.repind.msv.doctor.attributes.wrapper.role.WrapperRetrieveDoctorRoleResponse;
import com.afklm.repind.msv.doctor.attributes.wrapper.role.WrapperUpdateDoctorRoleResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

@RestController
@Slf4j
@RequestMapping(value = "/role")
public class DoctorRoleController {

    @Autowired
    private DoctorRoleService doctorRoleService;

    @Autowired
    private DoctorRoleChecker doctorRoleChecker;

    @ApiOperation(value = "Retrieve doctor role for an individu", notes = "Retrieve doctor role", response = WrapperRetrieveDoctorRoleResponse.class)
    @GetMapping(value = "/{roleId}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing request  :<br />"
                    + "- business.400.006 : Role id is mandatory"),
            @ApiResponse(code = 403, message = "Bad request :<br />"
                    + "- business.403.003 : Cannot update/retrieve/delete role with Suppressed Status"),
            @ApiResponse(code = 404, message = "Not found : <br />"
                    + "- business.404.003 : Role not found")
    })
    public ResponseEntity<WrapperRetrieveDoctorRoleResponse> retrieveDoctorRole(
            @ApiParam(required = true, name = "roleId", value = "Doctor role id") @PathVariable final String roleId) throws BusinessException {

        return doctorRoleService.retrieveDoctorRole(doctorRoleChecker.checkRetrieveDoctorRole(roleId));
    }

    @ApiOperation(value = "Create doctor role for an individu", notes = "Create doctor role", response = WrapperCreateDoctorRoleResponse.class)
    @PostMapping(value = "/{roleId}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing request  :<br />"
                    + "- business.400.001 : Gin is mandatory"
                    + "- business.400.004 : Site Creation is mandatory"
                    + "- business.400.005 : Signature Creation is mandatory"
                    + "- business.400.006 : Role id is mandatory"
                    + "- business.400.007 : Doctor id is mandatory"
                    + "- business.400.008 : Air line code is mandatory"
                    + "- business.400.009 : Doctor status is mandatory"
                    + "- business.400.010 : Relation type is mandatory"
                    + "- business.400.011 : Relation value is mandatory"
                    + "- business.400.012 : Relation list is mandatory"
                    + "- business.400.014 : Speciality is mandatory"),
            @ApiResponse(code = 403, message = "Bad request :<br />"
                    + "- business.403.001 : Individu must have Flying blue contract to has doctor role"),
            @ApiResponse(code = 404, message = "Not found : <br />"
                    + "- business.404.003 : Role not found"),
            @ApiResponse(code = 409, message = "Parameter already exists: <br />"
                    + "- business.409.001 : Role already exists"
                    + "- business.409.002 : Doctor id already exists"),
            @ApiResponse(code = 412, message = "Invalid parameter: <br />"
                    + "- business.412.001 : Invalid value for the 'gin' parameter, length must be equal to 12"
                    + "- business.412.003 : Invalid value for the 'endDateRole' parameter, format must be equal to yyyy-MM-dd'T'HH:mm:ss'Z'"
                    + "- business.412.004 : Invalid value for the 'doctorId' parameter, length must be equal to 20"
                    + "- business.412.005 : Invalid value for the 'airLineCode' parameter, value must be 'AF' or 'KL'"
                    + "- business.412.006 : Invalid value for the 'doctorStatus' parameter, value must be 'V' (Valid)"
                    + "- business.412.007 : Invalid value for the 'type' of relation model"
                    + "- business.412.008 : Invalid value for the 'values' of relation model"
                    + "- business.412.009 : Incorrect number of type values has been sent"
                    + "- business.412.011 : Incorrect value of speciality has been sent"
                    + "- business.412.012 : Invalid number of specialities values has been sent, must be 1"
                    + "- business.412.013 : Incorrect value of 'signatureSource' parameter, value must be 'CAPI' or 'CBS'")
    })
    public ResponseEntity<WrapperRetrieveDoctorRoleResponse> createDoctorRole(
            @ApiParam(required = true, name = "gin", value = "Gin of an individual" , example = "123456789123")  @Size(min = 12 ,max = 12) @RequestParam(required = true) final String gin,
            @ApiParam(required = true, name = "doctorStatus", value = "Doctor status") @RequestParam(required = true) final String doctorStatus,
            @ApiParam(required = false, name = "endDateRole", value = "End Date Role") @RequestParam(required = false) final String endDateRole,
            @ApiParam(required = true, name = "doctorId", value = "Doctor id") @Size(min=1 , max=20) @RequestParam(required = true) final String doctorId,
            @ApiParam(required = true, name = "airLineCode", value = "Air Line Code") @RequestParam(required = true) final String airLineCode,
            @ApiParam(required = true, name = "roleId", value = "Role id") @PathVariable final String roleId,
            @ApiParam(required = true, name = "signatureSourceCreation", value = "Signature Source Creation" , example = "REPIND") @RequestParam(required = true) final String signatureSource,
            @ApiParam(required = true, name = "siteCreation", value = "Site Creation" ,  example = "REPIND") @RequestParam(required = true) final String siteCreation,
            @ApiParam(required = true, name = "speciality", value = "Doctor Speciality") @RequestParam(required = true) final String speciality,
            @ApiParam(required = true, name = "relations", value = "Doctor's attributes using key/values") @RequestBody(required = true) final DoctorAttributesModel iBody) throws BusinessException {

        IndividuCriteria individuCriteria = new IndividuCriteria()
                .withGin(gin)
                .withDoctorStatus(doctorStatus)
                .withDoctorId(doctorId)
                .withSpeciality(speciality);
        return doctorRoleService.createDoctorRole(doctorRoleChecker.checkCreateDoctorRole(individuCriteria, endDateRole, airLineCode, roleId, signatureSource, siteCreation, iBody));
    }

    @ApiOperation(value = "Update doctor role for an individu", notes = "Update doctor role", response = WrapperUpdateDoctorRoleResponse.class)
    @PutMapping(value = "/{roleId}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing request  :<br />"
                    + "- business.400.004 : Site Creation is mandatory"
                    + "- business.400.005 : Signature Creation is mandatory"
                    + "- business.400.006 : Role id is mandatory"
                    + "- business.400.007 : Doctor id is mandatory"
                    + "- business.400.008 : Air line code is mandatory"
                    + "- business.400.009 : Doctor status is mandatory"
                    + "- business.400.010 : Relation type is mandatory"
                    + "- business.400.011 : Relation value is mandatory"
                    + "- business.400.012 : Relation list is mandatory"
                    + "- business.400.014 : Speciality is mandatory"),
            @ApiResponse(code = 403, message = "Bad request :<br />"
                    + "- business.403.001 : Individu must have Flying blue contract to has doctor role"),
            @ApiResponse(code = 404, message = "Not found : <br />"
                    + "- business.404.003 : Role not found"),
            @ApiResponse(code = 409, message = "Parameter already exists: <br />"
                    + "- business.409.001 : Role already exists"
                    + "- business.409.002 : Doctor id already exists"),
            @ApiResponse(code = 412, message = "Invalid parameter: <br />"
                    + "- business.412.001 : Invalid value for the 'gin' parameter, length must be equal to 12"
                    + "- business.412.003 : Invalid value for the 'endDateRole' parameter, format must be equal to yyyy-MM-dd'T'HH:mm:ss'Z'"
                    + "- business.412.004 : Invalid value for the 'doctorId' parameter, length must be equal to 20"
                    + "- business.412.005 : Invalid value for the 'airLineCode' parameter, value must be 'AF' or 'KL'"
                    + "- business.412.006 : Invalid value for the 'doctorStatus' parameter, value must be 'V' (Valid)"
                    + "- business.412.007 : Invalid value for the 'type' of relation model"
                    + "- business.412.008 : Invalid value for the 'values' of relation model"
                    + "- business.412.009 : Incorrect number of type values has been sent"
                    + "- business.412.011 : Incorrect value of speciality has been sent"
                    + "- business.412.012 : Invalid number of specialities values has been sent, must be 1"
                    + "- business.412.013 : Incorrect value of 'signatureSource' parameter, value must be 'CAPI' or 'CBS'")
    })
    public ResponseEntity<WrapperUpdateDoctorRoleResponse> updateDoctorRole(
            @ApiParam(required = true, name = "roleId", value = "Role id") @PathVariable final String roleId,
            @ApiParam(required = true, name = "doctorStatus", value = "Doctor status") @RequestParam(required = true) final String doctorStatus,
            @ApiParam(required = false, name = "endDateRole", value = "End Date Role") @RequestParam(required = false) final String endDateRole,
            @ApiParam(required = true, name = "doctorId", value = "Doctor id") @Size(min=1 , max=20) @RequestParam(required = true) final String doctorId,
            @ApiParam(required = true, name = "speciality", value = "Doctor Speciality") @RequestParam(required = true) final String speciality,
            @ApiParam(required = true, name = "airLineCode", value = "Air Line Code") @RequestParam(required = true) final String airLineCode,
            @ApiParam(required = true, name = "signatureSource", value = "Signature Source Modification" , example = "REPIND") @RequestParam(required = true) final String signatureSource,
            @ApiParam(required = true, name = "siteModification", value = "Signature site Modification") @RequestParam(required = true) final String siteModification,
            @ApiParam(required = true, name = "relations", value = "Doctor's attributes using key/values") @RequestBody(required = true) final DoctorAttributesModel iBody) throws BusinessException {

        return doctorRoleService.updateDoctorRole(doctorRoleChecker.checkUpdateDoctorRole(roleId, doctorStatus,endDateRole,doctorId,speciality, airLineCode, signatureSource, siteModification, iBody));
    }

    @ApiOperation(value = "Delete doctor role for an individu", notes = "delete doctor role")
    @DeleteMapping(value = "/{roleId}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing request  :<br />"
                    + "- business.400.006 : Role id is mandatory"),
            @ApiResponse(code = 403, message = "Bad request :<br />"
                    + "- business.403.003 : Cannot update/retrieve/delete role with Suppressed Status"),
            @ApiResponse(code = 404, message = "Not found : <br />"
                    + "- business.404.003 : Role not found")
    })
    public ResponseEntity<WrapperDeleteDoctorRoleResponse> deleteDoctorAttributes(
            @ApiParam(required = true, name = "roleId", value = "Doctor role id") @PathVariable final String roleId) throws BusinessException {

        return doctorRoleService.deleteDoctorRole(doctorRoleChecker.checkDeleteDoctorRole(roleId));
    }
}
