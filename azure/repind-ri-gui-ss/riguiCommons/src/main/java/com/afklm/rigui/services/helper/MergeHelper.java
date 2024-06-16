package com.afklm.rigui.services.helper;

import com.afklm.soa.stubs.w001274.v1.MergeStaffGinV1;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceV1;
import com.afklm.rigui.entity.role.BusinessRole;
import com.afklm.rigui.entity.role.RoleContrats;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class MergeHelper {

    @Autowired
    public DozerBeanMapper dozerBeanMapper;

    @Autowired
    public ProvideFBContractMergePreferenceV1 hachikoService;

    @Autowired
    public MergeStaffGinV1 consumerW001274v01;

    public boolean checkHasFBContract(List<RoleContrats> rcs) {
        if (CollectionUtils.isEmpty(rcs)) {
            return false;
        } else {
            for (RoleContrats role : rcs) {
                if ("FP".equals(role.getTypeContrat())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkHasMAContract(List<RoleContrats> rcs) {
        if (CollectionUtils.isEmpty(rcs)) {
            return false;
        } else {
            for (RoleContrats role : rcs) {
                if ("MA".equals(role.getTypeContrat())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkHasGPRole(List<BusinessRole> brs) {

        if (CollectionUtils.isEmpty(brs)) {
            return false;
        } else {
            for (BusinessRole br : brs) {
                if ("G".equals(br.getType())) {
                    return true;
                }
            }
        }
        return false;
    }
}

