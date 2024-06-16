package com.afklm.repind.msv.search.individual.v2.controller;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.individual.v2.controller.checker.SearchIndividualCheckerV2;
import com.afklm.repind.msv.search.individual.v2.service.SearchServiceV2;
import com.afklm.repind.msv.search.individual.v2.wrapper.WrapperSearchIndividualV2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SearchRestControllerTest {

    @InjectMocks
    private SearchRestControllerV2 searchRest;

    @Mock
    private SearchServiceV2 searchServiceV2;

    @Spy
    private SearchIndividualCheckerV2 checker;

    @Test
    void testSearchUrl() throws BusinessException {

        when(searchServiceV2.searchBy("123456789012", null, null,  null, null, "first", "last")).thenReturn(Arrays.asList("012345678999","012345678999"));
        ResponseEntity<Collection<WrapperSearchIndividualV2>> response = searchRest.search( "123456789012", null, null, null, null, "first", "last", null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertThat(response.getBody()).hasSize(2)
                .extracting("gin")
                .containsExactlyInAnyOrder("012345678999","012345678999");
    }

}
