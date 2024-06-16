package com.afklm.rigui.spring.rest.controllers;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.model.individual.ModelBasicIndividualData;
import com.afklm.rigui.services.MergeStatistiquesService;
import com.afklm.rigui.services.helper.MergeStatistiquesHelper;
import com.afklm.rigui.spring.configuration.SecurityConfiguration;
import com.afklm.rigui.wrapper.searches.WrapperIndividualSearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MergeStatistiquesControllerTest {

    @Mock
    private MergeStatistiquesService mergeStatistiquesService;

    @Mock
    private MergeStatistiquesHelper mergeStatistiquesHelper;

    @InjectMocks
    private MergeStatistiquesController mergeStatistiquesController;

    @Test
    void getMergeStatistiques() {
        WrapperIndividualSearch result = new WrapperIndividualSearch();
        result.count = 1;
        ModelBasicIndividualData data = new ModelBasicIndividualData();
        data.setFirstname("TOTO");
        result.data = List.of(data);
        when(mergeStatistiquesService.getMergeStatistiques(any(), any())).thenReturn(result);
        WrapperIndividualSearch response = mergeStatistiquesController.getMergeStatistiques();
        assertEquals(1, response.count);
        assertEquals("TOTO", response.data.get(0).getFirstname());
    }

    @Test
    void getTrackingExportByGin() throws JrafDomainException {
        when(mergeStatistiquesService.getMergeStatistiquesExported()).thenReturn(List.of());
        when(mergeStatistiquesHelper.generateCSV(any())).thenReturn(null);
        ResponseEntity<InputStreamResource> response = mergeStatistiquesController.getTrackingExportByGin();
        assertEquals(404, response.getStatusCodeValue());
    }
}
