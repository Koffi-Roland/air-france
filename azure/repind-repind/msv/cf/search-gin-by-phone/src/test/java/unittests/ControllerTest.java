package unittests;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.phone.controller.SearchGinByPhoneController;
import com.afklm.repind.msv.search.gin.by.phone.controller.checker.SearchGinByPhoneChecker;
import com.afklm.repind.msv.search.gin.by.phone.service.SearchGinByPhoneService;
import com.afklm.repind.msv.search.gin.by.phone.wrapper.WrapperSearchGinByPhoneResponse;
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
    private SearchGinByPhoneService searchGinByPhoneService;

    @Mock
    private SearchGinByPhoneChecker searchGinByPhoneChecker;

    @InjectMocks
    private SearchGinByPhoneController searchGinByPhoneController;
    private final String gin = "400401474125";
    private final HttpStatus status = HttpStatus.OK;
    private final String phoneNumber = "+69322464310";

    @Test
    void searchGinByPhoneTest() throws BusinessException {

        when(searchGinByPhoneChecker.checkSearchGinByPhone(phoneNumber))
                .thenReturn(phoneNumber);

        when(searchGinByPhoneService.search(phoneNumber, false))
                .thenReturn(buildMockedResponse());

        ResponseEntity<WrapperSearchGinByPhoneResponse> response =
                searchGinByPhoneController.searchGinByPhone(phoneNumber, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gin, response.getBody().getGins().get(0));
    }

    private ResponseEntity<WrapperSearchGinByPhoneResponse> buildMockedResponse() {
        List<String> gins = new ArrayList<>();
        gins.add(gin);

        WrapperSearchGinByPhoneResponse response = new WrapperSearchGinByPhoneResponse();
        response.addGins(gins);

        return new ResponseEntity<>(response, status);
    }
}
