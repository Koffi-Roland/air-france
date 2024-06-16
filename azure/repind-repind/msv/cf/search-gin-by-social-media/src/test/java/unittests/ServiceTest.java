package unittests;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.entity.identifier.ReferentielExternalIdentifier;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.enums.IndividuStatusEnum;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierRepository;
import com.afklm.repind.common.repository.identifier.ReferentielExtIdRepository;
import com.afklm.repind.msv.search.gin.by.social.media.service.SearchGinByExternalIdentifierService;
import com.afklm.repind.msv.search.gin.by.social.media.service.encoder.SearchGinByExternalIdentifierEncoder;
import com.afklm.repind.msv.search.gin.by.social.media.wrapper.WrapperSearchGinByExternalIdentifierResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ServiceTest {

    @Mock
    private ExternalIdentifierRepository externalIdentifierRepository;

    @Mock
    private ReferentielExtIdRepository referentielExtIdRepository;

    @Mock
    private SearchGinByExternalIdentifierEncoder searchGinByExternalIdentifierEncoder;

    @InjectMocks
    private SearchGinByExternalIdentifierService searchGinByExternalIdentifierService;

    private final String externalIdentifier = "843835678640";

    private final String externalType = "PNM";

    private final String gin = "400401474125";
    private final String ginMerged = "400401474126";

    private final HttpStatus status = HttpStatus.OK;

    @Test
    void searchTest() throws BusinessException{
        when(referentielExtIdRepository.existsByOption(externalType))
                .thenReturn(true);

        when(referentielExtIdRepository.findByOption(externalType))
                .thenReturn(buildMockedReferentiel());

        when(externalIdentifierRepository.findByIdentifierAndTypeAndIndividuIsNotNull(externalIdentifier , externalType))
                .thenReturn(buildMockedCollection());

        when(searchGinByExternalIdentifierEncoder.decodeExternalIdentifier(any()))
                .thenReturn(buildMockedResponse());

        ResponseEntity<WrapperSearchGinByExternalIdentifierResponse> response =
                searchGinByExternalIdentifierService.search(externalIdentifier , externalType, false);

        assertEquals(status, response.getStatusCode());
        assertEquals(gin, response.getBody().getGins().get(0));
    }

    @Test
    void searchTestMerged() throws BusinessException{
        when(referentielExtIdRepository.existsByOption(externalType))
                .thenReturn(true);

        when(referentielExtIdRepository.findByOption(externalType))
                .thenReturn(buildMockedReferentiel());

        when(externalIdentifierRepository.findByIdentifierAndTypeAndIndividuIsNotNull(externalIdentifier , externalType))
                .thenReturn(buildMockedCollection());

        when(searchGinByExternalIdentifierEncoder.decodeExternalIdentifier(any()))
                .thenReturn(buildMockedResponseMerged());

        ResponseEntity<WrapperSearchGinByExternalIdentifierResponse> response =
                searchGinByExternalIdentifierService.search(externalIdentifier , externalType, true);

        assertEquals(status, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getGins().contains(gin));
        Assertions.assertTrue(response.getBody().getGins().contains(ginMerged));
    }

    @Test
    void unknownExternalIdTypeTest() throws BusinessException{
        when(referentielExtIdRepository.existsByOption(externalType))
                .thenReturn(false);

        BusinessException expectedException = assertThrows(BusinessException.class, () -> {
            searchGinByExternalIdentifierService.search(externalIdentifier , externalType, false);
        });

        assertEquals(HttpStatus.NOT_FOUND, expectedException.getStatus());
        assertEquals("External Identifier Type not found", expectedException.getMessage());
    }

    @Test
    void ginNotFoundTest() throws BusinessException{
        when(referentielExtIdRepository.existsByOption(externalType))
                .thenReturn(true);

        when(referentielExtIdRepository.findByOption(externalType))
                .thenReturn(buildMockedReferentiel());

        when(externalIdentifierRepository.findByIdentifierAndTypeAndIndividuIsNotNull(externalIdentifier , externalType))
                .thenReturn(new ArrayList<>());

        BusinessException expectedException = assertThrows(BusinessException.class, () -> {
            searchGinByExternalIdentifierService.search(externalIdentifier , externalType, false);
        });

        assertEquals(HttpStatus.NOT_FOUND, expectedException.getStatus());
        assertEquals("Not Found", expectedException.getMessage());
    }

    private ReferentielExternalIdentifier buildMockedReferentiel(){
        ReferentielExternalIdentifier referentielExternalIdentifier = new ReferentielExternalIdentifier();
        referentielExternalIdentifier.setId(externalType);
        return referentielExternalIdentifier;
    }

    private Collection<ExternalIdentifier> buildMockedCollection(){
        List<ExternalIdentifier> externalIds = new ArrayList<>();
        ExternalIdentifier externalId = new ExternalIdentifier();

        Individu individu =  new Individu();
        individu.setGin(gin);
        individu.setStatutIndividu(IndividuStatusEnum.VALID.toString());

        externalId.setIndividu(individu);

        ExternalIdentifier externalIdMerged = new ExternalIdentifier();

        Individu individuMerged =  new Individu();
        individuMerged.setGin(ginMerged);
        individuMerged.setStatutIndividu(IndividuStatusEnum.MERGED.toString());

        externalIdMerged.setIndividu(individuMerged);

        externalIds.add(externalId);
        externalIds.add(externalIdMerged);
        return externalIds;
    }

    private WrapperSearchGinByExternalIdentifierResponse buildMockedResponse(){
        List<String> gins = new ArrayList<>();
        gins.add(gin);

        WrapperSearchGinByExternalIdentifierResponse response = new WrapperSearchGinByExternalIdentifierResponse();
        response.addGins(gins);

        return response;
    }

    private WrapperSearchGinByExternalIdentifierResponse buildMockedResponseMerged(){
        List<String> gins = new ArrayList<>();
        gins.add(gin);
        gins.add(ginMerged);

        WrapperSearchGinByExternalIdentifierResponse response = new WrapperSearchGinByExternalIdentifierResponse();
        response.addGins(gins);

        return response;
    }
}

