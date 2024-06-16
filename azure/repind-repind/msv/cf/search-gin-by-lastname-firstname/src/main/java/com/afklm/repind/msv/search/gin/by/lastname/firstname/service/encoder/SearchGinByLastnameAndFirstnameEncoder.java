package com.afklm.repind.msv.search.gin.by.lastname.firstname.service.encoder;

import com.afklm.repind.msv.search.gin.by.lastname.firstname.entity.Individu;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.wrapper.WrapperSearchGinByLastnameAndFirstnameResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchGinByLastnameAndFirstnameEncoder {

    public WrapperSearchGinByLastnameAndFirstnameResponse decode(Collection<Individu> iIndividus){
        WrapperSearchGinByLastnameAndFirstnameResponse response = new WrapperSearchGinByLastnameAndFirstnameResponse();
        response.addGins(iIndividus.stream().map(Individu::getGin).collect(Collectors.toList()));
        return response;
    }
}
