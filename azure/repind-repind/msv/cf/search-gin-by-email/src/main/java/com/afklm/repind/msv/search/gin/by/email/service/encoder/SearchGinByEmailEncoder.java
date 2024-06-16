package com.afklm.repind.msv.search.gin.by.email.service.encoder;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.msv.search.gin.by.email.wrapper.WrapperSearchGinByEmailResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchGinByEmailEncoder {

    public WrapperSearchGinByEmailResponse decode(Collection<EmailEntity> iEmails){
        WrapperSearchGinByEmailResponse response = new WrapperSearchGinByEmailResponse();
        response.addGins(iEmails.stream().map(eml -> eml.getIndividu().getGin()).distinct().collect(Collectors.toList()));
        return response;
    }
}
