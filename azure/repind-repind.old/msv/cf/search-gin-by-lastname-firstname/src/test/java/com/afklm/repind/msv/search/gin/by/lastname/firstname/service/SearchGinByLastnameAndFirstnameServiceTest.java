package com.afklm.repind.msv.search.gin.by.lastname.firstname.service;

import com.afklm.repind.msv.search.gin.by.lastname.firstname.repository.IndividuRepository;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.service.encoder.SearchGinByLastnameAndFirstnameEncoder;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.util.GenerateTestData;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.wrapper.WrapperSearchGinByLastnameAndFirstnameResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SearchGinByLastnameAndFirstnameServiceTest {

    @InjectMocks
    private SearchGinByLastnameAndFirstnameService searchService;

    @Mock
    private IndividuRepository individuRepo;

    @Mock
    SearchGinByLastnameAndFirstnameEncoder encoder;

    @Mock
    WrapperSearchGinByLastnameAndFirstnameResponse wrapper;

    @BeforeEach
    public void setup() {
    }

    @Test
    void testSearch(){
        when(individuRepo.findByNomAndPrenomAndStatutIndividuIn(any(), any(), any())).thenReturn(GenerateTestData.createIndividualList(1));
        when(encoder.decode(any())).thenReturn(GenerateTestData.createGinList(1));
        ResponseEntity<WrapperSearchGinByLastnameAndFirstnameResponse> result = searchService.search("TEST", "Test");

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
