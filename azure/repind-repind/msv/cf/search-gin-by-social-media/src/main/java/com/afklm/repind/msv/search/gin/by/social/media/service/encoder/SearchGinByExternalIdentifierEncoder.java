package com.afklm.repind.msv.search.gin.by.social.media.service.encoder;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.msv.search.gin.by.social.media.wrapper.WrapperSearchGinByExternalIdentifierResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class SearchGinByExternalIdentifierEncoder {

    public WrapperSearchGinByExternalIdentifierResponse decodeExternalIdentifier(Collection<ExternalIdentifier> externalIdentifierCollection) {
        WrapperSearchGinByExternalIdentifierResponse response = new WrapperSearchGinByExternalIdentifierResponse();
        if(externalIdentifierCollection.isEmpty()) {
            response.addGins(Collections.emptyList());
        } else {
            response.addGins(externalIdentifierCollection.stream().map(e -> e.getIndividu().getGin()).collect(Collectors.toList()));
        }
        return response;
    }
}
