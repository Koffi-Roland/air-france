package com.afklm.repind.msv.search.individual.v2.service;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.individual.client.search.contract.RiSearchGinByContractClient;
import com.afklm.repind.msv.search.individual.client.search.email.RiSearchGinByEmailClient;
import com.afklm.repind.msv.search.individual.client.search.lastnameandfirstname.RiSearchGinByLastnameAndFirstnameClient;
import com.afklm.repind.msv.search.individual.client.search.phone.RiSearchGinByPhoneNumberClient;
import com.afklm.repind.msv.search.individual.client.search.socialMedia.RiSearchGinBySocialMediaClient;
import com.afklm.repind.msv.search.individual.model.SearchGinsResponse;
import com.afklm.repind.msv.search.individual.model.error.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SearchServiceV2Test {

    @InjectMocks
    private SearchServiceV2 searchServiceV2;

    @Mock
    private RiSearchGinByEmailClient riSearchGinByEmailClient;

    @Mock
    private RiSearchGinByContractClient riSearchGinByContractClient;

    @Mock
    private RiSearchGinByPhoneNumberClient riSearchGinByPhoneNumberClient;

    @Mock
    private RiSearchGinBySocialMediaClient riSearchGinBySocialMediaClient;

    @Mock
    private RiSearchGinByLastnameAndFirstnameClient riSearchGinByLastnameAndFirstnameClient;

    private SearchGinsResponse searchGinsResponse;

    @BeforeEach
    public void setup() {
        searchGinsResponse = new SearchGinsResponse();
    }

    @Test
    void testSearchByCin() throws BusinessException {
        searchGinsResponse.getGins().addAll( Arrays.asList("gin1", "gin2"));
        when(riSearchGinByContractClient.execute(any())).thenReturn(searchGinsResponse);
        List<String> result = searchServiceV2.searchBy("1234AZ", null, null, null, null, null, null, false);

        assertThat(result).hasSize(2);
    }

    @Test
    void testSearchByEmail() throws BusinessException {
        searchGinsResponse.getGins().addAll( Arrays.asList("gin1", "gin2"));
        when(riSearchGinByEmailClient.execute(any())).thenReturn(searchGinsResponse);

        List<String> result = searchServiceV2.searchBy( null, "email@airfrance.fr" , null, null, null, null, null, false);

        assertThat(result).hasSize(2);
    }

    @Test
    void testSearchByPhone() throws BusinessException {
        searchGinsResponse.getGins().addAll( Arrays.asList("gin1", "gin2"));
        when(riSearchGinByPhoneNumberClient.execute(any())).thenReturn(searchGinsResponse);

        List<String> result = searchServiceV2.searchBy(null, null, "+33611223344", null, null, null, null, false);

        assertThat(result).hasSize(2);
    }

    @Test
    void testSearchByExternalIdentifier() throws BusinessException {
        searchGinsResponse.getGins().addAll( Arrays.asList("gin1", "gin2"));
        when(riSearchGinBySocialMediaClient.execute(any())).thenReturn(searchGinsResponse);

        List<String> result = searchServiceV2.searchBy( null, null, null, "CF8DA6C4-AF7A-467B-8D5F-CC32141CCB09", "PNM_ID", null, null, false);

        assertThat(result).hasSize(2);
    }

    @Test
    void testSearchByLastnameFirstname() throws BusinessException {
        searchGinsResponse.getGins().addAll( Arrays.asList("gin1", "gin2"));
        when(riSearchGinByLastnameAndFirstnameClient.execute(any())).thenReturn(searchGinsResponse);

        List<String> result = searchServiceV2.searchBy( null, null, null, null, null, "CAROLINE", "LE PEN", false);

        assertThat(result).hasSize(2);
    }

    @Test
    void testSearchByEmpty() throws BusinessException {
        searchGinsResponse.getGins().addAll(Collections.emptyList());
        when(riSearchGinByPhoneNumberClient.execute(any())).thenReturn(searchGinsResponse);

        List<String> result = searchServiceV2.searchBy(null, null, "+33611223344", null, null, null, null, false);

        assertThat(result).isEmpty();
    }

    @Test
    void testSearchByException() {
        Throwable exception = assertThrows(BusinessException.class, () -> searchServiceV2.searchBy(null, null, null, null, null, null, null, false));
        assertEquals(ErrorMessage.ERROR_MESSAGE_400.getDescription(), exception.getMessage());
    }

}
