package com.afklm.repind.msv.search.gin.by.social.media.service;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.entity.identifier.ReferentielExternalIdentifier;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierRepository;
import com.afklm.repind.common.repository.identifier.ReferentielExtIdRepository;
import com.afklm.repind.msv.search.gin.by.social.media.service.encoder.SearchGinByExternalIdentifierEncoder;
import com.afklm.repind.msv.search.gin.by.social.media.wrapper.WrapperSearchGinByExternalIdentifierResponse;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SearchGinByExternalIdentifierServiceTest {

    private static SearchGinByExternalIdentifierService service;
    @Mock
    private ExternalIdentifierRepository externalIdentifierRepository;
    @Mock
    private ReferentielExtIdRepository referentielExtIdRepository;

    @BeforeEach
    void init() {
        service = new SearchGinByExternalIdentifierService(
                externalIdentifierRepository,
                referentielExtIdRepository,
                new SearchGinByExternalIdentifierEncoder()
        );
    }

    private final static String EXTERNAL_TYPE_REF = "FB";
    private final static String EXTERNAL_TYPE_ID = "FACEBOOK_ID";
    private final static String EXTERNAL_ID = "000001";
    private final static String GIN_VALID = "0123456789";

    private final static String GIN_MERGED = "9876543210";
    @Test
    void searchEmpty() throws BusinessException {
        when(referentielExtIdRepository.existsByOption(EXTERNAL_TYPE_REF)).thenReturn(true);
        ReferentielExternalIdentifier ref = new ReferentielExternalIdentifier();
        ref.setId(EXTERNAL_TYPE_ID);
        when(referentielExtIdRepository.findByOption(EXTERNAL_TYPE_REF)).thenReturn(ref);
        when(externalIdentifierRepository.findByIdentifierAndTypeAndIndividuIsNotNull(EXTERNAL_ID, EXTERNAL_ID)).thenReturn(Collections.emptyList());
        ResponseEntity<WrapperSearchGinByExternalIdentifierResponse> response = service.search(EXTERNAL_ID, EXTERNAL_TYPE_REF, false);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(0,response.getBody().getGins().size());
    }

    @Test
    void searchMergedTrue() throws BusinessException {
        when(referentielExtIdRepository.existsByOption(EXTERNAL_TYPE_REF)).thenReturn(true);
        ReferentielExternalIdentifier ref = new ReferentielExternalIdentifier();
        ref.setId(EXTERNAL_TYPE_ID);
        when(referentielExtIdRepository.findByOption(EXTERNAL_TYPE_REF)).thenReturn(ref);
        when(externalIdentifierRepository.findByIdentifierAndTypeAndIndividuIsNotNull(EXTERNAL_ID, EXTERNAL_TYPE_ID)).thenReturn(buildExternalIdentifiers());
        ResponseEntity<WrapperSearchGinByExternalIdentifierResponse> response = service.search(EXTERNAL_ID, EXTERNAL_TYPE_REF, true);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(2,response.getBody().getGins().size());
        assertEquals(GIN_MERGED,response.getBody().getGins().get(0));
        assertEquals(GIN_VALID,response.getBody().getGins().get(1));
    }
    @Test
    void searchMergedFalse() throws BusinessException {
        when(referentielExtIdRepository.existsByOption(EXTERNAL_TYPE_REF)).thenReturn(true);
        ReferentielExternalIdentifier ref = new ReferentielExternalIdentifier();
        ref.setId(EXTERNAL_TYPE_ID);
        when(referentielExtIdRepository.findByOption(EXTERNAL_TYPE_REF)).thenReturn(ref);
        when(externalIdentifierRepository.findByIdentifierAndTypeAndIndividuIsNotNull(EXTERNAL_ID, EXTERNAL_TYPE_ID)).thenReturn(buildExternalIdentifiers());
        ResponseEntity<WrapperSearchGinByExternalIdentifierResponse> response = service.search(EXTERNAL_ID, EXTERNAL_TYPE_REF, false);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(1,response.getBody().getGins().size());
        assertEquals(GIN_VALID,response.getBody().getGins().get(0));
    }

    @NotNull
    private static List<ExternalIdentifier> buildExternalIdentifiers() {
        ExternalIdentifier externalIdentifierValid = new ExternalIdentifier();
        ExternalIdentifier externalIdentifierMerge = new ExternalIdentifier();
        Individu individuValid = new Individu();
        individuValid.setStatutIndividu("V");
        individuValid.setGin(GIN_VALID);
        Individu individuMerged = new Individu();
        individuMerged.setStatutIndividu("T");
        individuMerged.setGin(GIN_MERGED);
        externalIdentifierValid.setIdentifierId(1L);
        externalIdentifierValid.setIndividu(individuValid);
        externalIdentifierMerge.setIdentifierId(2L);
        externalIdentifierMerge.setIndividu(individuMerged);
        return List.of(externalIdentifierValid, externalIdentifierMerge);
    }
}
