package unittests;

import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.enums.IndividuStatusEnum;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.contact.TelecomsRepository;
import com.afklm.repind.msv.search.gin.by.phone.service.SearchGinByPhoneService;
import com.afklm.repind.msv.search.gin.by.phone.service.encoder.SearchGinByPhoneEncoder;
import com.afklm.repind.msv.search.gin.by.phone.wrapper.WrapperSearchGinByPhoneResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ServiceTest {

    @Mock
    private TelecomsRepository telecomsRepository;

    @Mock
    private SearchGinByPhoneEncoder searchGinByPhoneEncoder;

    @InjectMocks
    private SearchGinByPhoneService searchGinByPhoneService;

    private final String gin = "400401474125";
    private final String ginMerged = "400401474126";
    private final HttpStatus status = HttpStatus.OK;
    private final String phoneNumber = "+69322464310";

    @Test
    void searchTest() throws BusinessException {

        when(telecomsRepository.findByNormInterPhoneNumberAndStatutMediumInAndCodeMediumInAndIndividuIsNotNull(phoneNumber, SearchGinByPhoneService.I_STATUS, SearchGinByPhoneService.I_SCODES))
                .thenReturn(buildMockedCollection());

        when(searchGinByPhoneEncoder.decode(any()))
                .thenReturn(buildMockedResponse());

        ResponseEntity<WrapperSearchGinByPhoneResponse> response =
                searchGinByPhoneService.search(phoneNumber, false);

        Assertions.assertEquals(status, response.getStatusCode());
        Assertions.assertEquals(gin, response.getBody().getGins().get(0));
    }

    @Test
    void searchTestMerged() throws BusinessException {

        when(telecomsRepository.findByNormInterPhoneNumberAndStatutMediumInAndCodeMediumInAndIndividuIsNotNull(phoneNumber, SearchGinByPhoneService.I_STATUS, SearchGinByPhoneService.I_SCODES))
                .thenReturn(buildMockedCollection());

        when(searchGinByPhoneEncoder.decode(any()))
                .thenReturn(buildMockedResponseMerged());

        ResponseEntity<WrapperSearchGinByPhoneResponse> response =
                searchGinByPhoneService.search(phoneNumber, true);

        Assertions.assertEquals(status, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getGins().contains(gin));
        Assertions.assertTrue(response.getBody().getGins().contains(ginMerged));
    }

    private Collection<Telecoms> buildMockedCollection() {
        List<Telecoms> list = new ArrayList<>();
        Telecoms telecoms = new Telecoms();

        Individu individu = new Individu();
        individu.setGin(gin);
        individu.setStatutIndividu(IndividuStatusEnum.VALID.toString());

        telecoms.setIndividu(individu);

        Telecoms telecomsMerged = new Telecoms();

        Individu individuMerged = new Individu();
        individuMerged.setGin(ginMerged);
        individuMerged.setStatutIndividu(IndividuStatusEnum.MERGED.toString());

        telecomsMerged.setIndividu(individuMerged);

        list.add(telecoms);
        list.add(telecomsMerged);
        return list;
    }


    private WrapperSearchGinByPhoneResponse buildMockedResponse() {
        List<String> gins = new ArrayList<>();
        gins.add(gin);

        WrapperSearchGinByPhoneResponse response = new WrapperSearchGinByPhoneResponse();
        response.addGins(gins);

        return response;
    }

    private WrapperSearchGinByPhoneResponse buildMockedResponseMerged() {
        List<String> gins = new ArrayList<>();
        gins.add(gin);
        gins.add(ginMerged);

        WrapperSearchGinByPhoneResponse response = new WrapperSearchGinByPhoneResponse();
        response.addGins(gins);

        return response;
    }
}

