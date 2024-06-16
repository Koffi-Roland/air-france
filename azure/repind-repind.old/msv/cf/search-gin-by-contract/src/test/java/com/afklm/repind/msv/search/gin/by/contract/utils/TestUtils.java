package com.afklm.repind.msv.search.gin.by.contract.utils;

import com.afklm.repind.msv.search.gin.by.contract.entity.BusinessRole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestUtils {

    public static Collection<BusinessRole> createBusinessRoleCollection(int size) {

        List<BusinessRole> result = new ArrayList<BusinessRole>();

        for (int i = 0; i < size; i++) {
            result.add(createBusinessRole());
        }

        return result;
    }

    private static BusinessRole createBusinessRole() {
        BusinessRole br = new BusinessRole();
        br.setGinInd("400412345678");
        br.setNumeroContrat("12345678");

        return br;
    }
}
