package com.airfrance.batch.common.helper;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.reference.RefTypExtIDDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.reference.internal.RefTypExtIDDS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchExternalIdentifierHelperTest {

    @InjectMocks
    private BatchExternalIdentifierHelper batchExternalIdentifierHelper;

    @Mock
    private ExternalIdentifierDS eids;

    @Mock
    private IndividuDS indds;

    @Mock
    private RefTypExtIDDS rteidds;

    @Test
    void checkInit() {
        assertNotNull(batchExternalIdentifierHelper);
    }

    @Test
    void createOrUpdateIndividualAndExtId() throws JrafDomainException {
        IndividuDTO dto = new IndividuDTO();
        dto.setSgin("GIN");
        when(indds.getByGin("GIN")).thenReturn(dto);
        ExternalIdentifierDTO externalIdentifierDTO = new ExternalIdentifierDTO();
        when(eids.existExternalIdentifier("EXTERNAL", "TYPE")).thenReturn(externalIdentifierDTO);
        batchExternalIdentifierHelper.createOrUpdateIndividualAndExtId("GIN", "EXTERNAL", "TYPE", "AIRLINE", "NAME");
        verify(eids, times(1)).updateExternalIdentifier((IndividuDTO) any(), any(), any());
    }

    @Test
    public void getAllTypeExtId() throws JrafDomainException {
        RefTypExtIDDTO refTypExtIDDTO = new RefTypExtIDDTO();
        refTypExtIDDTO.setExtID("EXTID");
        when(rteidds.findAll()).thenReturn(List.of(refTypExtIDDTO));
        List<String> result = batchExternalIdentifierHelper.getAllTypeExtId();
        assertEquals(1, result.size());
        assertEquals("EXTID", result.get(0));
    }

}
