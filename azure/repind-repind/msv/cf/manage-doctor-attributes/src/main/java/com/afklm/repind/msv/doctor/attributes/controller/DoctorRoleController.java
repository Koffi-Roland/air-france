package com.afklm.repind.msv.doctor.attributes.controller;

import com.afklm.repind.msv.doctor.attributes.controller.checker.DoctorRoleChecker;
import com.afklm.repind.msv.doctor.attributes.dto.DoctorAttributesDto;
import com.afklm.repind.msv.doctor.attributes.dto.DoctorAttributesUpdateDto;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/role")
public class DoctorRoleController {

    /**
     * Doctor role service - inject by spring
     */
    private final DoctorRoleService doctorRoleService;
    /**
     * Doctor role checker - inject by spring
     */
    private final DoctorRoleChecker doctorRoleChecker;

    /**
     * Retrieve doctor role according to the given role id
     *
     * @param roleId Doctor role id
     * @return Role Doctor role
     * @throws BusinessException Business exception
     */
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
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing request  :<br />"
                    + "- business.400.001 : Gin is mandatory"
                    + "- business.400.004 : Site Creation is mandatory"
                    + "- business.400.005 : Signature Creation is mandatory"
                    + "- business.400.006 : Role id is mandatory"
                    + "- business.400.007 : Doctor id is mandatory"
                    + "- business.400.008 : Air line code is mandatory"
                    + "- business.400.009 : Doctor status is mandatory"
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

            @ApiParam(required = true, name = "DoctorAttributesDto", value = "Doctor's attributes") @RequestBody(required = true) final DoctorAttributesDto doctorAttributesDto) throws BusinessException {

        return doctorRoleService.createDoctorRole(doctorRoleChecker.checkCreateDoctorRole(doctorAttributesDto));
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
            @ApiParam(required = true, name = "DoctorAttributesModel", value = "Doctor's attributes") @RequestBody(required = true) final DoctorAttributesUpdateDto doctorAttributesUpdateDto) throws BusinessException {

        return doctorRoleService.updateDoctorRole(doctorRoleChecker.checkUpdateDoctorRole(roleId, doctorAttributesUpdateDto));
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
