package com.afklm.repind.msv.manage.external.identifier.ut;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.entity.identifier.ExternalIdentifierDataEntity;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifierData;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.Signature;
import com.afklm.repind.msv.manage.external.identifier.transformer.ExternalIdentifierTransformer;
import com.afklm.repind.msv.manage.external.identifier.utils.BuildData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ExternalIdentifierTransformerTest {
    ExternalIdentifierTransformer externalIdentifierTransformer;

    @BeforeEach
    void setup() {
        externalIdentifierTransformer = new ExternalIdentifierTransformer();
    }

    @Test
    void createSignatureTest() {
        Signature res = externalIdentifierTransformer.createSignature(BuildData.buildSignatureElement());

        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertAll(
                () -> assertEquals("QVI", res.getCreation().getSite()),
                () -> assertEquals("RI_TEAM", res.getCreation().getSignature()),
                () -> assertEquals(date, res.getCreation().getDate()),
                () -> assertEquals("QVI", res.getModification().getSite()),
                () -> assertEquals("RI_TEAM", res.getModification().getSignature()),
                () -> assertEquals(date, res.getModification().getDate())
        );
    }

    @Test
    void buildExternalIdentifierDataListTest() {
        List<ExternalIdentifierData> res = externalIdentifierTransformer.buildExternalIdentifierDataList(BuildData.buildListExternalIdentifierDataEntity());

        assertAll(
                () -> assertEquals(1, res.size()),
                () -> assertEquals("key", res.get(0).getKey()),
                () -> assertEquals("value", res.get(0).getValue())
        );
    }

    @Test
    void buildExternalIdentifierTest() {
        ExternalIdentifier externalIdentifierEntity = BuildData.buildExternalIdentifierEntityList().get(0);
        List<ExternalIdentifierDataEntity> externalIdentifierDataEntityList = BuildData.buildListExternalIdentifierDataEntity();
        com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifier res = externalIdentifierTransformer.buildExternalIdentifier(externalIdentifierEntity,externalIdentifierDataEntityList);

        assertAll(
                () -> assertEquals("cust-le",res.getIdentifier()),
                () -> assertEquals("PNM_ID", res.getType()),
                () -> assertEquals(1,res.getExternalIdentifierData().size())
        );
    }
}
