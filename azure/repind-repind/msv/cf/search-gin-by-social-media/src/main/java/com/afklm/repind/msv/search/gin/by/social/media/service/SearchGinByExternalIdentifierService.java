package com.afklm.repind.msv.search.gin.by.social.media.service;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.enums.IndividuStatusEnum;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierRepository;
import com.afklm.repind.common.repository.identifier.ReferentielExtIdRepository;
import com.afklm.repind.msv.search.gin.by.social.media.model.error.BusinessError;
import com.afklm.repind.msv.search.gin.by.social.media.service.encoder.SearchGinByExternalIdentifierEncoder;
import com.afklm.repind.msv.search.gin.by.social.media.wrapper.WrapperSearchGinByExternalIdentifierResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class SearchGinByExternalIdentifierService {
    /**
     * service for SearchByExternalIdentifier
     */

    private ExternalIdentifierRepository externalIdentifierRepository;
    private ReferentielExtIdRepository referentielExtIdRepository;
    private SearchGinByExternalIdentifierEncoder searchGinByExternalIdentifierEncoder;

    @Transactional(readOnly = true)
    public ResponseEntity<WrapperSearchGinByExternalIdentifierResponse> search(String externalIdentifier, String externalType, boolean merge) throws BusinessException {
        /**
         * search the GINs corresponding to the parameters
         * @params externalIdentifier and externalType
         * @return response
         * @throws businessException if no GIN found or if no externalType found
         */

        //Get short code from option in REF_TYP_EXT_ID Table
        if (referentielExtIdRepository.existsByOption(externalType)) {
            externalType = referentielExtIdRepository.findByOption(externalType).getId();

            Collection<ExternalIdentifier> externalIdentifierCollection = externalIdentifierRepository.findByIdentifierAndTypeAndIndividuIsNotNull(externalIdentifier, externalType)
                    .stream().filter(item -> this.filterIndividualMerged(item, merge))
                    .collect(Collectors.toSet());
            log.info("Search by social media : {} results found", externalIdentifierCollection.size());
            return new ResponseEntity<>(searchGinByExternalIdentifierEncoder.decodeExternalIdentifier(externalIdentifierCollection), HttpStatus.OK);
        } else {
            throw new BusinessException(BusinessError.UNKNOWN_EXTERNAL_ID_TYPE);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public boolean filterIndividualMerged(ExternalIdentifier e, boolean merge) {
        return merge || e.getIndividu() != null && e.getIndividu().getGin() != null && !e.getIndividu().getStatutIndividu().equals(IndividuStatusEnum.MERGED.toString());
    }

}
