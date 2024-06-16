package com.airfrance.batch.automaticmerge.service;

import com.airfrance.batch.automaticmerge.model.OutputRecord;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.entity.individu.Individu;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndividusDSTest {

    @Mock
    private IndividuRepository individuRepository;
    @InjectMocks
    private IndividusDS individusDS;

    @Test
    void updateIndividuSource_ThrowsNotFoundException_IfIndividuNotFound() {
        String ginSource = "123";
        when(individuRepository.findBySgin(ginSource)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            individusDS.updateIndividuSource(ginSource, new OutputRecord());
        });
    }

    @Test
    void updateIndividuSource_UpdatesIndividu_IfFound() throws NotFoundException {
        String ginSource = "123";
        Individu individu = getMockIndividu();
        OutputRecord outputRecord = new OutputRecord("456", "123", new Date(), "2024-02-17");

        when(individuRepository.findBySgin(ginSource)).thenReturn(individu);

        individusDS.updateIndividuSource(ginSource, outputRecord);

        assertEquals(outputRecord.getGinTarget(), individu.getGinFusion());
        assertEquals(outputRecord.getMergeDate(), individu.getDateFusion());
        assertEquals(outputRecord.getMergeDate(), individu.getDateFusion());

        verify(individuRepository).saveAndFlush(individu);
    }

    @Test
    void getIndividuByGin_FoundInDB(){
        Individu mockIndividu = getMockIndividu();

        when(individuRepository.findBySgin(anyString())).thenReturn(mockIndividu);

        Individu result = individusDS.getIndividuByGin("123");

        assertNotNull(result);
        assertEquals(mockIndividu.getSgin(), result.getSgin() );
        assertEquals(mockIndividu.getNom(), result.getNom() );
        assertEquals(mockIndividu.getPrenom(), result.getPrenom() );
        assertEquals(mockIndividu.getSexe(), result.getSexe() );
        assertEquals(mockIndividu.getStatutIndividu(), result.getStatutIndividu() );
        assertEquals(mockIndividu.getDateNaissance(), result.getDateNaissance() );
    }
    @Test
    void getIndividuByGin_NotFound(){
        when(individuRepository.findBySgin(anyString())).thenReturn(null);

        Individu result = individusDS.getIndividuByGin("123");

        assertNull(result);
    }


    @NotNull
    private static Individu getMockIndividu() {
        Individu mockIndividu = new Individu();
        mockIndividu.setSgin("123");
        mockIndividu.setNom("Jadar");
        mockIndividu.setPrenom("Mohamed");
        mockIndividu.setSexe("M");
        mockIndividu.setStatutIndividu("V");
        mockIndividu.setDateNaissance(new Date());
        return mockIndividu;
    }


}
