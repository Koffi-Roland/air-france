package com.afklm.repind.msv.doctor.role.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.doctor.role.controller.checker.DoctorRoleChecker;
import com.afklm.repind.msv.doctor.role.model.DoctorAttributesModel;
import com.afklm.repind.msv.doctor.role.service.DoctorRoleService;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperCreateDoctorRoleResponse;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperDoctorRoleResponse;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperRetrieveDoctorRoleResponse;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperUpsertDoctorRoleResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.security.NoSuchAlgorithmException;

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
        return doctorRoleService.retrieve(doctorRoleChecker.checkRetrieveDoctorRole(roleId));
    }

    @ApiOperation(value = "Create doctor role for an individu", notes = "Create doctor role", response = WrapperDoctorRoleResponse.class)
    @PostMapping(value = "/")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing request  :<br />"
                    + "- business.400.001 : Gin is mandatory"
                    + "- business.400.002 : Type is mandatory"
                    + "- business.400.004 : Site Creation is mandatory"
                    + "- business.400.005 : Signature Creation is mandatory"
                    + "- business.400.006 : Role id is mandatory"
                    + "- business.400.007 : Doctor id is mandatory"
                    + "- business.400.008 : Air line code is mandatory"
                    + "- business.400.009 : Doctor status is mandatory"
                    + "- business.400.010 : Relation type is mandatory"
                    + "- business.400.011 : Relation value is mandatory"
                    + "- business.400.012 : Relation list is mandatory"
                    + "- business.400.013 : Signature Date is mandatory"
                    + "- business.400.014 : Speciality is mandatory"),
            @ApiResponse(code = 403, message = "Bad request :<br />"
                    + "- business.403.001 : Individu must have Flying blue contract to has doctor role"
                    + "- business.403.002 : Gin provided does not match with the gin's doctor role"
                    + "- business.403.003 : Cannot update/retrieve role with Suppressed Status"
                    + "- business.403.004 : Cannot update/retrieve attributes with suppressed role"),
            @ApiResponse(code = 404, message = "Not found : <br />"
                    + "- business.404.001 : Gin not found"
                    + "- business.404.002 : Type not found"
                    + "- business.404.003 : Contract number not found"),
            @ApiResponse(code = 409, message = "Parameter already exists: <br />"
                    + "- business.409.001 : Contract number already exists"
                    + "- business.409.002 : Doctor id already exists"),
            @ApiResponse(code = 412, message = "Invalid parameter: <br />"
                    + "- business.412.001 : Invalid value for the 'gin' parameter, length must be equal to 12"
                    + "- business.412.002 : Invalid value for the 'type' parameter, length must be equal to 1"
                    + "- business.412.003 : Invalid value for the 'endDateRole' parameter, format must be equal to yyyy-MM-dd'T'HH:mm:ss'Z'"
                    + "- business.412.004 : Invalid value for the 'doctorId' parameter, length must be equal to 20"
                    + "- business.412.005 : Invalid value for the 'airLineCode' parameter, value must be 'AF' or 'KL'"
                    + "- business.412.006 : Invalid value for the 'doctorStatus' parameter, value must be 'V' (Valid)"
                    + "- business.412.007 : Invalid value for the 'type' of relation model"
                    + "- business.412.008 : Invalid value for the 'values' of relation model"
                    + "- business.412.009 : Incorrect number of type values has been sent"
                    + "- business.412.010 : Invalid value for the 'signature Date' parameter, format must be equal to yyyy-MM-dd'T'HH:mm:ss'Z'"
                    + "- business.412.011 : Incorrect value of speciality has been sent"
                    + "- business.412.012 : Invalid number of specialities values has been sent, must be 1")
    })
    public ResponseEntity<WrapperCreateDoctorRoleResponse> createDoctorRole(
            @ApiParam(required = true, name = "gin", value = "Gin of an individual" , example = "123456789123")  @Size(min = 12 ,max = 12) @RequestParam(required = true) final String gin,
            @ApiParam(required = true, name = "type", value = "Role type", example = "D") @RequestParam(required = true) final String type,
            @ApiParam(required = true, name = "doctorStatus", value = "Doctor status") @RequestParam(required = true) final String doctorStatus,
            @ApiParam(required = false, name = "endDateRole", value = "End Date Role") @RequestParam(required = false) final String endDateRole,
            @ApiParam(required = true, name = "doctorId", value = "Doctor id") @Size(min=1 , max=20) @RequestParam(required = true) final String doctorId,
            @ApiParam(required = true, name = "airLineCode", value = "Air Line Code") @RequestParam(required = true) final String airLineCode,
            @ApiParam(required = true, name = "signatureSourceCreation", value = "Signature Source Creation" , example = "REPIND") @RequestParam(required = true) final String signatureSourceCreation,
            @ApiParam(required = true, name = "signatureSiteCreation", value = "Signature Site Creation") @RequestParam(required = true) final String signatureSiteCreation,
            @ApiParam(required = true, name = "speciality", value = "Doctor Speciality") @RequestParam(required = true) final String speciality,
            @ApiParam(required = true, name = "relations", value = "Doctor's attributes using key/values") @RequestBody(required = true) final DoctorAttributesModel iBody) throws NoSuchAlgorithmException, BusinessException {

        return doctorRoleService.create(doctorRoleChecker.checkCreateDoctorRole(gin,type,doctorStatus,endDateRole,doctorId,airLineCode, signatureSourceCreation, signatureSiteCreation, speciality, iBody));
    }

    @ApiOperation(value = "Update doctor role for an individu", notes = "Update doctor role", response = WrapperDoctorRoleResponse.class)
    @PutMapping(value = "/{roleId}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing request  :<br />"
                    + "- business.400.001 : Gin is mandatory"
                    + "- business.400.002 : Type is mandatory"
                    + "- business.400.004 : Site Creation is mandatory"
                    + "- business.400.005 : Signature Creation is mandatory"
                    + "- business.400.006 : Role id is mandatory"
                    + "- business.400.007 : Doctor id is mandatory"
                    + "- business.400.008 : Air line code is mandatory"
                    + "- business.400.009 : Doctor status is mandatory"
                    + "- business.400.010 : Relation type is mandatory"
                    + "- business.400.011 : Relation value is mandatory"
                    + "- business.400.012 : Relation list is mandatory"
                    + "- business.400.013 : Signature Date is mandatory"
                    + "- business.400.014 : Speciality is mandatory"),
            @ApiResponse(code = 403, message = "Bad request :<br />"
                    + "- business.403.001 : Individu must have Flying blue contract to has doctor role"
                    + "- business.403.002 : Gin provided does not match with the gin's doctor role"
                    + "- business.403.003 : Cannot update/retrieve role with Suppressed Status"
                    + "- business.403.004 : Cannot update/retrieve attributes with suppressed role"),
            @ApiResponse(code = 404, message = "Not found : <br />"
                    + "- business.404.001 : Gin not found"
                    + "- business.404.002 : Type not found"
                    + "- business.404.003 : Contract number not found"),
            @ApiResponse(code = 409, message = "Parameter already exists: <br />"
                    + "- business.409.001 : Contract number already exists"
                    + "- business.409.002 : Doctor id already exists"),
            @ApiResponse(code = 412, message = "Invalid parameter: <br />"
                    + "- business.412.001 : Invalid value for the 'gin' parameter, length must be equal to 12"
                    + "- business.412.002 : Invalid value for the 'type' parameter, length must be equal to 1"
                    + "- business.412.003 : Invalid value for the 'endDateRole' parameter, format must be equal to yyyy-MM-dd'T'HH:mm:ss'Z'"
                    + "- business.412.004 : Invalid value for the 'doctorId' parameter, length must be equal to 20"
                    + "- business.412.005 : Invalid value for the 'airLineCode' parameter, value must be 'AF' or 'KL'"
                    + "- business.412.006 : Invalid value for the 'doctorStatus' parameter, value must be 'V' (Valid)"
                    + "- business.412.007 : Invalid value for the 'type' of relation model"
                    + "- business.412.008 : Invalid value for the 'values' of relation model"
                    + "- business.412.009 : Incorrect number of type values has been sent"
                    + "- business.412.010 : Invalid value for the 'signature Date' parameter, format must be equal to yyyy-MM-dd'T'HH:mm:ss'Z'"
                    + "- business.412.011 : Incorrect value of speciality has been sent"
                    + "- business.412.012 : Invalid number of specialities values has been sent, must be 1")
    })
    public ResponseEntity<WrapperUpsertDoctorRoleResponse> updateDoctorRole(
            @ApiParam(required = true, name = "roleId", value = "Doctor role id") @PathVariable final String roleId,
            @ApiParam(required = true, name = "endDateRole", value = "End Date Role", example = "2030-01-01T00:00:00Z" , format = "yyyy-MM-dd'T'HH:mm:ss'Z'") @RequestParam(required = true) final String endDateRole,
            @ApiParam(required = true, name = "doctorStatus", value = "Doctor status", example = "V") @Size(min = 1 ,max = 1) @RequestParam final String doctorStatus,
            @ApiParam(required = true, name = "speciality", value = "Doctor Speciality") @RequestParam(required = true) final String speciality,
            @ApiParam(required = true, name = "siteModification", value = "Site Modification" ,  example = "REPIND") @RequestParam(required = true) final String siteModification,
            @ApiParam(required = true, name = "signatureSource", value = "Signature Modification" , example = "REPIND") @RequestParam(required = true) final String signatureSource,
            @ApiParam(required = true, name = "doctorId", value = "Doctor id" , example = "12312312312312312312")  @Size(min = 1 ,max = 20) @RequestParam(required = true) final String doctorId,
            @ApiParam(required = true, name = "airLineCode", value = "Air Line Code" , example = "AF")  @Size(min = 1 ,max = 2) @RequestParam(required = true) final String airLineCode,
            @ApiParam(required = true, name = "relations", value = "Doctor's attributes using key/values") @RequestBody(required = true) final DoctorAttributesModel iBody) throws BusinessException {

        return doctorRoleService.upsert(doctorRoleChecker.checkUpsertDoctorRole(roleId, endDateRole , doctorStatus , siteModification, signatureSource,doctorId , airLineCode, speciality,iBody));
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
    public ResponseEntity<WrapperDoctorRoleResponse> deleteDoctorRole(
            @ApiParam(required = true, name = "roleId", value = "Doctor role id") @PathVariable final String roleId) throws BusinessException {

        return doctorRoleService.delete(doctorRoleChecker.checkDeleteDoctorRole(roleId));
    }
}
