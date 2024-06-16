package com.afklm.repind.msv.search.gin.by.lastname.firstname.service;

import com.afklm.repind.msv.search.gin.by.lastname.firstname.entity.Individu;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.util.StatusEnum;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.repository.IndividuRepository;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.service.encoder.SearchGinByLastnameAndFirstnameEncoder;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.wrapper.WrapperSearchGinByLastnameAndFirstnameResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

    private final String gin = "400401474125";
    private final String ginMerged = "400401474126";
    private final HttpStatus status = HttpStatus.OK;
    private final String prenom = "Test";
    private final String nom = "test";

    @Test
    void searchTest(){
        when(individuRepo.findByNomAndPrenomAndStatutIndividuIn(prenom, nom,
                Arrays.asList(StatusEnum.VALIDATED.getName(), StatusEnum.PENDING.getName())))
                .thenReturn((List<Individu>) buildMockedCollection());

        when(encoder.decode(any()))
                .thenReturn(buildMockedResponse());

        ResponseEntity<WrapperSearchGinByLastnameAndFirstnameResponse> response =
                searchService.search(nom, prenom, false);

        Assertions.assertEquals(status, response.getStatusCode());
        Assertions.assertEquals(gin, response.getBody().getGins().get(0));
    }

    @Test
    void searchTestMerged(){
        when(individuRepo.findByNomAndPrenomAndStatutIndividuIn(prenom, nom,
                Arrays.asList(StatusEnum.VALIDATED.getName(), StatusEnum.PENDING.getName(),StatusEnum.MERGED.getName())))
                .thenReturn((List<Individu>) buildMockedCollectionMerged());

        when(encoder.decode(any()))
                .thenReturn(buildMockedResponseMerged());

        ResponseEntity<WrapperSearchGinByLastnameAndFirstnameResponse> response =
                searchService.search(nom, prenom, true);

        Assertions.assertEquals(status, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getGins().contains(gin));
        Assertions.assertTrue(response.getBody().getGins().contains(ginMerged));
    }


    private Collection<Individu> buildMockedCollection() {
        List<Individu> list = new ArrayList<>();
        Individu individu = new Individu();

        individu.setGin(gin);
        individu.setStatutIndividu(StatusEnum.VALIDATED.toString());

        list.add(individu);
        return list;
    }

    private Collection<Individu> buildMockedCollectionMerged() {
        List<Individu> list = new ArrayList<>();
        Individu individu = new Individu();

        individu.setGin(gin);
        individu.setStatutIndividu(StatusEnum.VALIDATED.toString());

        Individu individuMerged = new Individu();

        individuMerged.setGin(ginMerged);
        individuMerged.setStatutIndividu(StatusEnum.MERGED.toString());

        list.add(individu);
        list.add(individuMerged);
        return list;
    }

    private WrapperSearchGinByLastnameAndFirstnameResponse buildMockedResponse() {
        List<String> gins = new ArrayList<>();
        gins.add(gin);

        WrapperSearchGinByLastnameAndFirstnameResponse response = new WrapperSearchGinByLastnameAndFirstnameResponse();
        response.addGins(gins);

        return response;
    }

    private WrapperSearchGinByLastnameAndFirstnameResponse buildMockedResponseMerged() {
        List<String> gins = new ArrayList<>();
        gins.add(gin);
        gins.add(ginMerged);

        WrapperSearchGinByLastnameAndFirstnameResponse response = new WrapperSearchGinByLastnameAndFirstnameResponse();
        response.addGins(gins);

        return response;
    }

}
