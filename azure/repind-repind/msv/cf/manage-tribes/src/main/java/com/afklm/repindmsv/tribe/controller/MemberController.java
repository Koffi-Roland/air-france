package com.afklm.repindmsv.tribe.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repindmsv.tribe.controller.checker.MemberChecker;
import com.afklm.repindmsv.tribe.model.StatusEnum;
import com.afklm.repindmsv.tribe.services.MemberService;
import com.afklm.repindmsv.tribe.wrapper.WrapperMemberResponse;
import com.afklm.repindmsv.tribe.wrapper.WrapperTribeResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/manage-tribes")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberChecker memberChecker;

    @ApiOperation(value = "Add a member",
            notes = "Add a member to a tribe.<br />"
                    + "The application must be allowed to manage the tribe.<br />"
                    + "See list of allowed applications.", response = WrapperMemberResponse.class)
    @PostMapping(value = "member")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request :<br />"
                    + "- business.400.001 : Gin is missing<br />"
                    + "- business.400.005 : Application is missing<br />"
                    + "- business.400.006 : TribeId is missing"),
            @ApiResponse(code = 404, message = "Not found :<br />"
                    + "- business.404.002 : Tribe not found"),
            @ApiResponse(code = 412, message = "Precondition failed :<br />"
                    + "- business.412.001 : Invalid value for the 'gin' parameter, length must be equal to 12")
    })
    public ResponseEntity<WrapperMemberResponse> addMember(
            @ApiParam(required = true, name = "tribeId", value = "Identifiant of the tribe where to add the member")
            @RequestParam(value = "tribeId", required = true) final String tribeId,
            @ApiParam(required = true, name = "gin", value = "Gin of the individual to add to the tribe")
            @RequestParam(value = "gin", required = true) final String gin,
            @ApiParam(required = true, name = "application", value = "Signature to add a member")
            @RequestParam(value = "application", required = true) final String application)
            throws BusinessException {

        return memberService.addMember(memberChecker.checkAddMemberCriteria(tribeId, gin, application));
    }

    @ApiOperation(value = "Update a relationship",
            notes = "Update status of a relationship between a tribe and an individual.<br />"
                    + "The application must be allowed to manage the tribe.<br />"
                    + "See list of allowed applications.", response = WrapperTribeResponse.class)
    @PutMapping(value = "member")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request :<br />"
                    + "- business.400.001 : Gin is missing<br />"
                    + "- business.400.005 : Application is missing<br />"
                    + "- business.400.006 : TribeId is missing<br />"
                    + "- business.400.007 : Status is missing"),
            @ApiResponse(code = 404, message = "Not found :<br />"
                    + "- business.404.002 : Tribe not found<br />"
                    + "- business.404.003 : Member not found"),
            @ApiResponse(code = 412, message = "Precondition failed :<br />"
                    + "- business.412.001 : Invalid value for the 'gin' parameter, length must be equal to 12")
    })
    public ResponseEntity<WrapperMemberResponse> updateRelationStatus(
            @ApiParam(required = true, name = "tribeId", value = "Identifiant of the tribe where to update the status of the relationship")
            @RequestParam(value = "tribeId", required = true) final String tribeId,
            @ApiParam(required = true, name = "gin", value = "Gin of the individual who needs to update the status of the relationship")
            @RequestParam(value = "gin", required = true) final String gin,
            @ApiParam(required = true, name = "status", value = "New status of the relationship. Status allowed: V (Validated), P (Pending), R (Refused), D (Deleted)")
            @RequestParam(value = "status", required = true) final String status,
            @ApiParam(required = true, name = "application", value = "Signature for updating a relationship")
            @RequestParam(value = "application", required = true) final String application)
            throws BusinessException {

        return memberService.updateStatusRelation(memberChecker.checkUpdateMemberCriteria(tribeId, gin, status, application));
    }


    @ApiOperation(value = "Delete a member",
            notes = "Delete a member of a tribe.<br />"
                    + "The application must be allowed to manage the tribe.<br />"
                    + "See list of allowed applications.", response = WrapperTribeResponse.class)
    @DeleteMapping(value = "member")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request :<br />"
                    + "- business.400.001 : Gin is missing<br />"
                    + "- business.400.005 : Application is missing<br />"
                    + "- business.400.006 : TribeId is missing<br />"),
            @ApiResponse(code = 404, message = "Not found :<br />"
                    + "- business.404.002 : Tribe not found<br />"
                    + "- business.404.003 : Member not found"),
            @ApiResponse(code = 412, message = "Precondition failed :<br />"
                    + "- business.412.001 : Invalid value for the 'gin' parameter, length must be equal to 12")
    })
    public ResponseEntity<WrapperMemberResponse> deleteMember(
            @ApiParam(required = true, name = "tribeId", value = "Identifiant of the tribe where the member must be removed")
            @RequestParam(value = "tribeId", required = true) final String tribeId,
            @ApiParam(required = true, name = "gin", value = "Gin of the individual to remove from the tribe")
            @RequestParam(value = "gin", required = true) final String gin,
            @ApiParam(required = true, name = "application", value = "Signature to delete a relationship")
            @RequestParam(value = "application", required = true) final String application)
            throws BusinessException {

        return memberService.updateStatusRelation(memberChecker.checkUpdateMemberCriteria(tribeId, gin, StatusEnum.DELETED.getName(), application));
    }

}