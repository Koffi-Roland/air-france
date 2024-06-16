package com.afklm.batch.deletecontract.model;

import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.entity.role.RoleUCCR;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBusinessRoleUCCROutputModel {

    private RoleUCCR roleUCCR;
    private BusinessRoleDTO businessRoleDTO;


}

