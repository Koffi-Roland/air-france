package com.afklm.repind.msv.search.gin.by.phone.service.encoder;

import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.enums.IndividuStatusEnum;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.msv.search.gin.by.phone.wrapper.WrapperSearchGinByPhoneResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

@Service
public class SearchGinByPhoneEncoder {

    private IndividuRepository individuRepository;

    public WrapperSearchGinByPhoneResponse decode(Collection<Telecoms> iPhones){
        WrapperSearchGinByPhoneResponse response = new WrapperSearchGinByPhoneResponse();
        response.addGins(iPhones.stream().map(i -> i.getIndividu().getGin()).collect(Collectors.toList()));
        return response;
    }
}
