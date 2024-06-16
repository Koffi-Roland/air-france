package com.afklm.repind.msv.manage.external.identifier.ut;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.manage.external.identifier.controller.ExternalIdentifierController;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifierResponse;
import com.afklm.repind.msv.manage.external.identifier.service.ExternalIdentifierService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExternalIdentifierControllerTest {
    @Mock
    private ExternalIdentifierService externalIdentifierService;
    private ExternalIdentifierController externalIdentifierController;

    @BeforeEach
    void setup() {
        externalIdentifierService = mock(ExternalIdentifierService.class);
        externalIdentifierController = new ExternalIdentifierController(externalIdentifierService);
    }

    @Nested
    @DisplayName("/{gin} test for provide part of the MS")
    class provideTest {
        @Test
        void testWithCorrectData() throws BusinessException {
            List<ExternalIdentifier> externalIdentifierEntityList = new ArrayList<>();
            externalIdentifierEntityList.add(new ExternalIdentifier());
            ExternalIdentifierResponse tmp = new ExternalIdentifierResponse();
            when(externalIdentifierService.getExternalIdentifierListByGin("012345678912")).thenReturn(externalIdentifierEntityList);
            when(externalIdentifierService.setExternalIdentifierDataResponse(externalIdentifierEntityList)).thenReturn(tmp);

            ResponseEntity<ExternalIdentifierResponse> res = externalIdentifierController.getExternalContactDataByGin("012345678912");

            assertAll(
                    () -> Assertions.assertEquals(HttpStatus.OK, res.getStatusCode()),
                    () -> Assertions.assertEquals(tmp, res.getBody())
            );
        }

        @Test
        void testWithTooLongGin() {
            assertThrows(BusinessException.class, () -> externalIdentifierController.getExternalContactDataByGin("012345674448912"));
        }

        @Test
        void testWithInexistantGin() {
            when(externalIdentifierService.getExternalIdentifierListByGin("16azd1a6zd1a")).thenReturn(new ArrayList<>());
            assertThrows(BusinessException.class, () -> externalIdentifierController.getExternalContactDataByGin("16azd1a6zd1a"));
        }
    }

    @Nested
    @DisplayName("/ test for delete part of the MS")
    class deleteTest {
        @Test
        void testWithCorrectDataAndNullGin() throws BusinessException {
            ResponseEntity<String> res = externalIdentifierController
                    .deleteExternalContactDataByIdentifierIdAndTypeAndGin("cust-le", "KAKAO_ID", null);
            assertAll(
                    () -> assertEquals("Deletion completed", res.getBody()),
                    () -> assertEquals(HttpStatus.OK, res.getStatusCode())
            );
        }

        @Test
        void testWithCorrectData() throws BusinessException {
            ResponseEntity<String> res = externalIdentifierController
                    .deleteExternalContactDataByIdentifierIdAndTypeAndGin("cust-le", "KAKAO_ID", "12313");
            assertAll(
                    () -> assertEquals("Deletion completed", res.getBody()),
                    () -> assertEquals(HttpStatus.OK, res.getStatusCode())
            );
        }

        @Test
        void testWithMissingIdentifierId() {
            assertThrows(BusinessException.class, () -> externalIdentifierController
                    .deleteExternalContactDataByIdentifierIdAndTypeAndGin(null, "KAKAO_ID", "12313"));
        }

        @Test
        void testWithMissingType() {
            assertThrows(BusinessException.class, () -> externalIdentifierController
                    .deleteExternalContactDataByIdentifierIdAndTypeAndGin("cust-le", null, "12313"));
        }

        @Test
        void testWithWrongType() {
            assertThrows(BusinessException.class, () -> externalIdentifierController
                    .deleteExternalContactDataByIdentifierIdAndTypeAndGin("cust-le", "454azdazdazdza", "12313"));
        }

        @Test
        void testWithTooLongGin(){
            assertThrows(BusinessException.class, () -> externalIdentifierController
                    .deleteExternalContactDataByIdentifierIdAndTypeAndGin("cust-le", "KAKAO_ID", "12313123132131231231321323"));
        }
    }
}
