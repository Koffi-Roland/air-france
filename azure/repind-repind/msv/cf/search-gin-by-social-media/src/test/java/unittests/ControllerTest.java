package unittests;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.social.media.controller.SearchGinByExternalIdentifierController;
import com.afklm.repind.msv.search.gin.by.social.media.controller.checker.SearchGinByExternalIdentifierChecker;
import com.afklm.repind.msv.search.gin.by.social.media.service.SearchGinByExternalIdentifierService;
import com.afklm.repind.msv.search.gin.by.social.media.wrapper.WrapperSearchGinByExternalIdentifierResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ControllerTest {

    @Mock
    private SearchGinByExternalIdentifierService searchGinByExternalIdentifierService;

    @Mock
    private SearchGinByExternalIdentifierChecker searchGinByExternalIdentifierChecker;

    @InjectMocks
    private SearchGinByExternalIdentifierController searchGinByExternalIdentifierController;

    private final String externalIdentifierId = "843835678640";

    private final String id = "843835678640";

    private final String externalIdentifierType = "PNM";

    private final String type = "PNM";

    private final String gin = "400401474125";

    private final HttpStatus status = HttpStatus.OK;

    @Test
    void searchGinByExternalIdentifierTest() throws BusinessException{
        when(searchGinByExternalIdentifierChecker.checkSearchGinByExternalIdentifierId(externalIdentifierId))
                .thenReturn(id);

        when(searchGinByExternalIdentifierChecker.checkSearchGinByExternalIdentifierType(externalIdentifierType))
                .thenReturn(type);

        when(searchGinByExternalIdentifierService.search(id, type, false))
                .thenReturn(buildMockedResponse());

        ResponseEntity<WrapperSearchGinByExternalIdentifierResponse> response =
                searchGinByExternalIdentifierController.searchGinByExternalIdentifier(externalIdentifierId,externalIdentifierType, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gin, response.getBody().getGins().get(0));
    }

    private ResponseEntity<WrapperSearchGinByExternalIdentifierResponse> buildMockedResponse(){
        List<String> gins = new ArrayList<>();
        gins.add(gin);

        WrapperSearchGinByExternalIdentifierResponse response = new WrapperSearchGinByExternalIdentifierResponse();
        response.addGins(gins);

        return new ResponseEntity<>(response, status);
    }
}
