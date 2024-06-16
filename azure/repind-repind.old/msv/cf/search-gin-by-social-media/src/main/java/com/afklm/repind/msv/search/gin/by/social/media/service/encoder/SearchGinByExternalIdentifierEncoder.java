package com.afklm.repind.msv.search.gin.by.social.media.service.encoder;

import com.afklm.repind.msv.search.gin.by.social.media.entity.ExternalIdentifier;
import com.afklm.repind.msv.search.gin.by.social.media.wrapper.WrapperSearchGinByExternalIdentifierResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchGinByExternalIdentifierEncoder {

    public WrapperSearchGinByExternalIdentifierResponse decodeExternalIdentifier(Collection<ExternalIdentifier> externalIdentifierCollection){
        WrapperSearchGinByExternalIdentifierResponse response = new WrapperSearchGinByExternalIdentifierResponse();
        response.addGins(externalIdentifierCollection.stream().map(ExternalIdentifier::getSgin).collect(Collectors.toList()));
        return response;
    }
}
