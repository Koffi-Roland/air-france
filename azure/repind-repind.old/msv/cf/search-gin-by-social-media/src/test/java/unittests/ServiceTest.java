package unittests;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.social.media.entity.ExternalIdentifier;
import com.afklm.repind.msv.search.gin.by.social.media.entity.ReferentielExternalIdentifier;
import com.afklm.repind.msv.search.gin.by.social.media.repository.IExternalIdentifierRepository;
import com.afklm.repind.msv.search.gin.by.social.media.repository.IReferentielExtIdRepository;
import com.afklm.repind.msv.search.gin.by.social.media.service.SearchGinByExternalIdentifierService;
import com.afklm.repind.msv.search.gin.by.social.media.service.encoder.SearchGinByExternalIdentifierEncoder;
import com.afklm.repind.msv.search.gin.by.social.media.wrapper.WrapperSearchGinByExternalIdentifierResponse;
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
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ServiceTest {

    @Mock
    private IExternalIdentifierRepository externalIdentifierRepository;

    @Mock
    private IReferentielExtIdRepository referentielExtIdRepository;

    @Mock
    private SearchGinByExternalIdentifierEncoder searchGinByExternalIdentifierEncoder;

    @InjectMocks
    private SearchGinByExternalIdentifierService searchGinByExternalIdentifierService;

    private final String externalIdentifier = "843835678640";

    private final String externalType = "PNM";

    private final String gin = "400401474125";

    private final HttpStatus status = HttpStatus.OK;

    @Test
    void searchTest() throws BusinessException{
        when(referentielExtIdRepository.existsByOption(externalType))
                .thenReturn(true);

        when(referentielExtIdRepository.findByOption(externalType))
                .thenReturn(buildMockedReferentiel());

        when(externalIdentifierRepository.findByIdentifierAndType(externalIdentifier , externalType))
                .thenReturn(buildMockedCollection());

        when(searchGinByExternalIdentifierEncoder.decodeExternalIdentifier(buildMockedCollection()))
                .thenReturn(buildMockedResponse());

        ResponseEntity<WrapperSearchGinByExternalIdentifierResponse> response =
                searchGinByExternalIdentifierService.search(externalIdentifier , externalType);

        assertEquals(status, response.getStatusCode());
        assertEquals(gin, response.getBody().getGins().get(0));
    }

    @Test
    void unknownExternalIdTypeTest() throws BusinessException{
        when(referentielExtIdRepository.existsByOption(externalType))
                .thenReturn(false);

        BusinessException expectedException = assertThrows(BusinessException.class, () -> {
            searchGinByExternalIdentifierService.search(externalIdentifier , externalType);
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

        when(externalIdentifierRepository.findByIdentifierAndType(externalIdentifier , externalType))
                .thenReturn(new ArrayList<>());

        BusinessException expectedException = assertThrows(BusinessException.class, () -> {
            searchGinByExternalIdentifierService.search(externalIdentifier , externalType);
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

        externalId.setSgin(gin);
        externalIds.add(externalId);
        return externalIds;
    }

    private WrapperSearchGinByExternalIdentifierResponse buildMockedResponse(){
        List<String> gins = new ArrayList<>();
        gins.add(gin);

        WrapperSearchGinByExternalIdentifierResponse response = new WrapperSearchGinByExternalIdentifierResponse();
        response.addGins(gins);

        return response;
    }
}

