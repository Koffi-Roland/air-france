package com.afklm.rigui.services;

import com.afklm.rigui.criteria.merge.MergeStatistiquesCriteria;
import com.afklm.rigui.model.individual.ModelBasicIndividualMergeStatistiquesData;
import com.afklm.rigui.model.individual.requests.ModelMergeStatistiques;
import com.afklm.rigui.repository.MergeStatistiquesRepository;
import com.afklm.rigui.spring.TestConfiguration;
import com.afklm.rigui.wrapper.searches.WrapperIndividualSearch;
import org.dozer.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MergeStatistiquesServiceTest {

    @InjectMocks
    private MergeStatistiquesService mergeStatistiquesService;

    @Mock
    private Mapper dozerBeanMapper;

    @Mock
    private MergeStatistiquesRepository mergeStatistiquesRepository;

    @Test
    public void getMergeStatistiques() {
        MergeStatistiquesCriteria criteria = new MergeStatistiquesCriteria(3);
        PageRequest pageable = PageRequest.of(0, 500, Sort.Direction.DESC, "dateModification");
        ModelMergeStatistiques modelMergeStatistiques = new ModelMergeStatistiques(
                "ginMerged",
                "signatureModification",
                new Date(),
                "site",
                "ginValid",
                "firstname",
                "lastname",
                "statusginvalid",
                "civility");
        when(mergeStatistiquesRepository.findByGinMergeNotNull(pageable, criteria.getDayInPast())).thenReturn(List.of(modelMergeStatistiques));
        ModelBasicIndividualMergeStatistiquesData data = new ModelBasicIndividualMergeStatistiquesData();
        data.setGinMerged("ginMerged");
        data.setFirstname("Toto");
        when(dozerBeanMapper.map(eq(modelMergeStatistiques), any())).thenReturn(data);
        WrapperIndividualSearch result = mergeStatistiquesService.getMergeStatistiques(criteria, pageable);
        assertEquals(1, result.data.size());
        assertEquals("Toto", result.data.get(0).getFirstname());
    }

    @Test
    public void getMergeStatistiquesExported() {
        when(mergeStatistiquesRepository.findByGinMergeNotNull()).thenReturn(List.of());
        List<Object[]> result = mergeStatistiquesService.getMergeStatistiquesExported();
        assertEquals(0, result.size());
    }
}
