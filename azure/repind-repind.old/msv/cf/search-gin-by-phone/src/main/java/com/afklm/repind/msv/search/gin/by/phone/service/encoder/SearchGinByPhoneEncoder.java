package com.afklm.repind.msv.search.gin.by.phone.service.encoder;

import com.afklm.repind.msv.search.gin.by.phone.entity.Telecoms;
import com.afklm.repind.msv.search.gin.by.phone.wrapper.WrapperSearchGinByPhoneResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchGinByPhoneEncoder {

    public WrapperSearchGinByPhoneResponse decode(Collection<Telecoms> iPhones){
        WrapperSearchGinByPhoneResponse response = new WrapperSearchGinByPhoneResponse();
        response.addGins(iPhones.stream().map(Telecoms::getSgin).collect(Collectors.toList()));
        return response;
    }
}
