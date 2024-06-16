package com.afklm.repind.msv.search.gin.by.social.media.service;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.social.media.entity.ExternalIdentifier;
import com.afklm.repind.msv.search.gin.by.social.media.model.error.BusinessError;
import com.afklm.repind.msv.search.gin.by.social.media.repository.IExternalIdentifierRepository;
import com.afklm.repind.msv.search.gin.by.social.media.repository.IReferentielExtIdRepository;
import com.afklm.repind.msv.search.gin.by.social.media.service.encoder.SearchGinByExternalIdentifierEncoder;
import com.afklm.repind.msv.search.gin.by.social.media.wrapper.WrapperSearchGinByExternalIdentifierResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Service
@Slf4j
@AllArgsConstructor
public class SearchGinByExternalIdentifierService {
    /**
     * service for SearchByExternalIdentifier
     */

    private IExternalIdentifierRepository externalIdentifierRepository;
    private IReferentielExtIdRepository referentielExtIdRepository;
    private SearchGinByExternalIdentifierEncoder searchGinByExternalIdentifierEncoder;

    @Transactional(readOnly = true)
    public ResponseEntity<WrapperSearchGinByExternalIdentifierResponse> search(String externalIdentifier, String externalType) throws BusinessException{
        /**
         * search the GINs corresponding to the parameters
         * @params externalIdentifier and externalType
         * @return response
         * @throws businessException if no GIN found or if no externalType found
         */

        //Get short code from option in REF_TYP_EXT_ID Table
        if(referentielExtIdRepository.existsByOption(externalType)){
            externalType = referentielExtIdRepository.findByOption(externalType).getId();

            //Gin search in External Identifier Table
            Collection<ExternalIdentifier> externalIdentifierCollection = externalIdentifierRepository.findByIdentifierAndType(externalIdentifier , externalType);

            if(externalIdentifierCollection.isEmpty()){
                throw new BusinessException(BusinessError.GIN_NOT_FOUND);
            }

            return new ResponseEntity<>( searchGinByExternalIdentifierEncoder.decodeExternalIdentifier(externalIdentifierCollection) , HttpStatus.OK);

        }

        else{
            throw new BusinessException(BusinessError.UNKNOWN_EXTERNAL_ID_TYPE);
        }
    }

}
