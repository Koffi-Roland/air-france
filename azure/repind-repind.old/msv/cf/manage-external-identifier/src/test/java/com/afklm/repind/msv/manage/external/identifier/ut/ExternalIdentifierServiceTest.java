package com.afklm.repind.msv.manage.external.identifier.ut;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierDataRepository;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierRepository;
import com.afklm.repind.msv.manage.external.identifier.service.ExternalIdentifierService;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifier;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifierData;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifierResponse;
import com.afklm.repind.msv.manage.external.identifier.transformer.ExternalIdentifierTransformer;
import com.afklm.repind.msv.manage.external.identifier.utils.BuildData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExternalIdentifierServiceTest {

    ExternalIdentifierService externalIdentifierService;
    ExternalIdentifierTransformer externalIdentifierTransformer;
    @Mock
    ExternalIdentifierRepository externalIdentifierRepository;
    @Mock
    ExternalIdentifierDataRepository externalIdentifierDataRepository;

    @BeforeEach
    void setup() {
        externalIdentifierTransformer = new ExternalIdentifierTransformer();
        externalIdentifierRepository = mock(ExternalIdentifierRepository.class);
        externalIdentifierDataRepository = mock(ExternalIdentifierDataRepository.class);
        externalIdentifierService = new ExternalIdentifierService(externalIdentifierRepository, externalIdentifierDataRepository, externalIdentifierTransformer);

        when(externalIdentifierDataRepository.findAllByIdentifierId(11656L)).thenReturn(BuildData.buildListExternalIdentifierDataEntity());
        when(externalIdentifierRepository.findAllByIdentifierAndType("cust-le","PNM_ID")).thenReturn(BuildData.buildExternalIdentifierEntityList());
        when(externalIdentifierDataRepository.findAllByIdentifierId(1L)).thenReturn(BuildData.buildListExternalIdentifierDataEntity());
        when(externalIdentifierRepository.findAllByIdentifierAndType("cust-le2","KAKAO_ID")).thenReturn(new ArrayList<>());
        when(externalIdentifierRepository.findByIdentifierAndTypeAndIndividuGin("cust-le","PNM_ID","123456789")).thenReturn(BuildData.buildExternalIdentifierEntityList().get(0));
        when(externalIdentifierRepository.findByIdentifierAndTypeAndIndividuGin("cust-le2","KAKAO_ID","1234567890")).thenReturn(null);

    }

    @Nested
    @DisplayName("Set external identifier data response test")
    class SetExternalIdentifierDataResponseClassTest {
        @Test
        void testWithGoodData() {
            ExternalIdentifierResponse res = externalIdentifierService.setExternalIdentifierDataResponse(BuildData.buildExternalIdentifierEntityList());
            List<ExternalIdentifier> externalIdentifierList = res.getExternalIdentifierList();
            ExternalIdentifier externalIdentifier = externalIdentifierList.get(0);
            ExternalIdentifierData externalIdentifierData = externalIdentifier.getExternalIdentifierData().get(0);

            Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());

            assertAll(
                    () -> assertEquals(1, externalIdentifierList.size()),
                    () -> assertEquals("cust-le", externalIdentifier.getIdentifier()),
                    () -> assertEquals("PNM_ID", externalIdentifier.getType()),
                    () -> assertEquals(date, externalIdentifier.getSignature().getCreation().getDate()),
                    () -> assertEquals("QVI", externalIdentifier.getSignature().getCreation().getSite()),
                    () -> assertEquals("RI_TEAM", externalIdentifier.getSignature().getCreation().getSignature()),
                    () -> assertEquals(date, externalIdentifier.getSignature().getModification().getDate()),
                    () -> assertEquals("QVI", externalIdentifier.getSignature().getModification().getSite()),
                    () -> assertEquals("RI_TEAM", externalIdentifier.getSignature().getModification().getSignature()),
                    () -> assertEquals(1, externalIdentifier.getExternalIdentifierData().size()),
                    () -> assertEquals("key", externalIdentifierData.getKey()),
                    () -> assertEquals("value", externalIdentifierData.getValue()),
                    () -> assertEquals(date, externalIdentifierData.getSignature().getCreation().getDate()),
                    () -> assertEquals("QVI", externalIdentifierData.getSignature().getCreation().getSite()),
                    () -> assertEquals("RI_TEAM", externalIdentifierData.getSignature().getCreation().getSignature()),
                    () -> assertEquals(date, externalIdentifierData.getSignature().getModification().getDate()),
                    () -> assertEquals("QVI", externalIdentifierData.getSignature().getModification().getSite()),
                    () -> assertEquals("RI_TEAM", externalIdentifierData.getSignature().getModification().getSignature())
            );
        }

        @Test
        void testWithEmptyListOfExternalIdentifierEntity() {
            ExternalIdentifierResponse res = externalIdentifierService.setExternalIdentifierDataResponse(new ArrayList<>());

            assertAll(
                    () -> assertEquals(0, res.getExternalIdentifierList().size())
            );
        }
    }

    @Nested
    @DisplayName("Delete external identifier entity test")
    class deleteExternalIdentifierTest {
        @Test
        void testWithCorrectDataAndNullGin() throws BusinessException {
            assertDoesNotThrow(() -> externalIdentifierService.deleteExternalIdentifier("cust-le","PNM_ID",null));
        }

        @Test
        void testWithCorrectData() {
            assertDoesNotThrow(() -> externalIdentifierService.deleteExternalIdentifier("cust-le","PNM_ID","123456789"));
        }

        @Test
        void testWithInexistantDataAndNullGin() {
            assertThrows(BusinessException.class, () -> externalIdentifierService.deleteExternalIdentifier("cust-le2","KAKAO_ID",null));
        }

        @Test
        void testWithInexistantData() {
            assertThrows(BusinessException.class, () -> externalIdentifierService.deleteExternalIdentifier("cust-le2","KAKAO_ID","1234567980"));
        }
    }
}
