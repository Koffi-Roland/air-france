package com.afklm.repind.msv.search.gin.by.contract.service.encoder;

import org.springframework.stereotype.Service;

import com.afklm.repind.msv.search.gin.by.contract.entity.BusinessRole;
import com.afklm.repind.msv.search.gin.by.contract.wrapper.WrapperSearchGinByContractResponse;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchGinByContractEncoder {

    public WrapperSearchGinByContractResponse decode(Collection<BusinessRole> iContract){
        WrapperSearchGinByContractResponse response = new WrapperSearchGinByContractResponse();
        response.addGins(iContract.stream().map(BusinessRole::getGinInd).collect(Collectors.toList()));
        return response;
    }
}
