package com.afklm.repind.msv.search.gin.by.lastname.firstname.util;

import com.afklm.repind.msv.search.gin.by.lastname.firstname.entity.Individu;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.wrapper.WrapperSearchGinByLastnameAndFirstnameResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class GenerateTestData {

    public static Individu createIndividual() {
        Individu indiv = new Individu();
        indiv.setGin("012345678999");
        indiv.setCivilite("Mr");
        indiv.setNom("Doe");
        indiv.setPrenom("John");
        indiv.setDateNaissance(Date.from(LocalDate.of(1980, 5, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return indiv;
    }

    public static List<Individu> createIndividualList(int size) {

        List<Individu> indivList = new ArrayList<>();
        if (size <= 0) {
            size = 1;
        }
        for (int i = 0; i < size; ++i) {
            indivList.add(createIndividual());
        }
        return indivList;
    }

    public static WrapperSearchGinByLastnameAndFirstnameResponse createGinList(int size) {

        WrapperSearchGinByLastnameAndFirstnameResponse wrapper = new WrapperSearchGinByLastnameAndFirstnameResponse();
        Collection<String> indivList = new ArrayList<>();
        if (size <= 0) {
            size = 1;
        }
        for (int i = 0; i < size; ++i) {
            indivList.add("012345678999");
        }
        wrapper.addGins(indivList);

        return wrapper;
    }

    public static ResponseEntity<WrapperSearchGinByLastnameAndFirstnameResponse> createGinListResponse(int size){
        return new ResponseEntity<>(createGinList(size), HttpStatus.OK);
    }

    private GenerateTestData() {
        throw new IllegalStateException("Utility class");
    }
}
