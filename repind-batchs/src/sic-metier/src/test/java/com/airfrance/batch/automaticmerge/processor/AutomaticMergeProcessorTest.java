package com.airfrance.batch.automaticmerge.processor;

import com.airfrance.batch.automaticmerge.model.OutputRecord;
import com.airfrance.batch.automaticmerge.service.IndividusDS;
import com.airfrance.batch.common.wrapper.WrapperProvideIndividualScoreResponse;
import com.airfrance.repind.entity.individu.Individu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutomaticMergeProcessorTest {

    @Mock
    private IndividusDS individusDS;


    @InjectMocks
    private AutomaticMergeProcessor automaticMergeProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetMultipleIndividusTarget() {
        List<WrapperProvideIndividualScoreResponse> individualScores = List.of(
                new WrapperProvideIndividualScoreResponse("123", 10.0),
                new WrapperProvideIndividualScoreResponse("456", 8.0),
                new WrapperProvideIndividualScoreResponse("789", 10.0)
        );

        List<WrapperProvideIndividualScoreResponse> result = automaticMergeProcessor.getMultipleIndividusTarget(individualScores, 9.0);

        assertEquals(2, result.size());
        assertEquals("123", result.get(0).getGin());
        assertEquals("789", result.get(1).getGin());
    }

    @Test
    void testGetMaxScore() {
        List<WrapperProvideIndividualScoreResponse> individualScores = List.of(
                new WrapperProvideIndividualScoreResponse("123", 10.0),
                new WrapperProvideIndividualScoreResponse("456", 8.0),
                new WrapperProvideIndividualScoreResponse("789", 12.0)
        );

        Double result = automaticMergeProcessor.getMaxScore(individualScores);

        assertNotNull(result);
        assertEquals(Optional.of(individualScores.get(2).getScore()).get(), result);
    }

    @Test
    void testCreateOutputRecord(){
        List<WrapperProvideIndividualScoreResponse> individualScores = List.of(
                new WrapperProvideIndividualScoreResponse("123", 10.0),
                new WrapperProvideIndividualScoreResponse("456", 8.0),
                new WrapperProvideIndividualScoreResponse("789", 12.0)
        );
        String ginTarget = individualScores.get(2).getGin(); // with max score in the list

        OutputRecord result = automaticMergeProcessor.createOutputRecord(ginTarget, individualScores);

        assertNotNull(result);
        assertEquals(ginTarget, result.getGinTarget());
        assertEquals(
                String.join(
                        ",",
                        individualScores.get(0).getGin(),
                        individualScores.get(1).getGin()),
                result.getGinSource());
        assertNull(result.getMergeDateAsString());
    }

    @Test
    void testFindGinTargetByRecentModificationDate() throws ParseException {
        List<WrapperProvideIndividualScoreResponse> individualScores = List.of(
                new WrapperProvideIndividualScoreResponse("123", 10.0),
                new WrapperProvideIndividualScoreResponse("789", 10.0)
        );

        Individu idv1 = getMockedGin("123", "01-01-2020");
        Individu idv2 = getMockedGin("789", "01-01-2024");

        when(individusDS.getIndividuByGin("123")).thenReturn(idv1);
        when(individusDS.getIndividuByGin("789")).thenReturn(idv2);

        String ginTargetByDateModification = automaticMergeProcessor.findGinTargetByRecentModificationDate(individualScores);

        assertNotNull(ginTargetByDateModification);
        assertEquals(idv2.getSgin(), ginTargetByDateModification);
    }

    @Test
    void testDefineGinTarget_singleMaxScore(){
        List<WrapperProvideIndividualScoreResponse> individualScores = List.of(
                new WrapperProvideIndividualScoreResponse("123", 10.0)
        );

        String ginTarget = automaticMergeProcessor.decideGinTarget(individualScores);

        assertNotNull(ginTarget);
        assertEquals(individualScores.get(0).getGin(), ginTarget);
    }

    @Test
    void testDefineGinTarget_multipleMaxScore() throws ParseException {
        List<WrapperProvideIndividualScoreResponse> individualScores = List.of(
                new WrapperProvideIndividualScoreResponse("123", 10.0),
                new WrapperProvideIndividualScoreResponse("789", 10.0)
        );

        Individu idv1 = getMockedGin("123", "01-01-2020");
        Individu idv2 = getMockedGin("789", "01-01-2024");

        when(individusDS.getIndividuByGin("123")).thenReturn(idv1);
        when(individusDS.getIndividuByGin("789")).thenReturn(idv2);

        String ginTarget = automaticMergeProcessor.decideGinTarget(individualScores);

        assertNotNull(ginTarget);
        assertEquals(individualScores.get(1).getGin(), ginTarget);
    }

    @Test
    void testExtractSkippedGinsByMS(){
        List<String> inputGins = List.of("123", "456", "789");
        List<WrapperProvideIndividualScoreResponse> individualScoresFromMS = List.of(
                new WrapperProvideIndividualScoreResponse("123", 10.0),
                new WrapperProvideIndividualScoreResponse("456", 9.0)
        );

        List<String> result = automaticMergeProcessor.extractSkippedGinsByMs(inputGins, individualScoresFromMS);

        assertNotNull(result);
        assertThat(result)
                .hasSize(1)
                .contains("789");

    }

    private static Individu getMockedGin(String gin, String dateModif) throws ParseException {
        Individu idv = new Individu();
        idv.setSgin(gin);
        idv.setGinFusion(null);
        idv.setDateModification(new SimpleDateFormat("dd-MM-yyyy").parse(dateModif));
        return idv;
    }

}
