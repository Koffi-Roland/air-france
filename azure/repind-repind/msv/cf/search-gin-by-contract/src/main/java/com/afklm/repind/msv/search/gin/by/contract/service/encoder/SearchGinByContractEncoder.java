package com.afklm.repind.msv.search.gin.by.contract.service.encoder;

import com.afklm.repind.common.entity.individual.Individu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.msv.search.gin.by.contract.wrapper.WrapperSearchGinByContractResponse;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchGinByContractEncoder {

    public WrapperSearchGinByContractResponse decode(Collection<BusinessRole> iContract){
        WrapperSearchGinByContractResponse response = new WrapperSearchGinByContractResponse();
        Collection<Individu> individus = iContract.stream().map(BusinessRole::getIndividu).collect(Collectors.toList());
        Collection<String> gins = individus.stream().map(Individu::getGin).collect(Collectors.toList());
        response.addGins(gins);
        return response;
    }
}
