package com.airfrance.batch.contract.deletecontract.model;

import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.entity.handicap.Handicap;
import com.airfrance.repind.entity.handicap.HandicapData;
import com.airfrance.repind.entity.role.RoleContrats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteContractsOutputModel {

    private RoleContrats roleContrats;
    private BusinessRoleDTO businessRoleDTO;
    private List<Handicap> handicapToDeleteList;
    private List<HandicapData> handicapDataToDeleteList;


}
