package com.afklm.repind.common.entity.individual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ComplementaryInformationEntityTest {

    @Test
    void testClass() {
        ComplementaryInformationEntity complementaryInformationEntity = new ComplementaryInformationEntity();
        // set
        complementaryInformationEntity.setKey("key");
        complementaryInformationEntity.setType("type");
        complementaryInformationEntity.setValue("value");
        complementaryInformationEntity.setDelegationDataId("delegationDataId");
        complementaryInformationEntity.setDelegationDataInfoId("delegationDataInfoId");
        // get
        assertEquals("key", complementaryInformationEntity.getKey());
        assertEquals("type", complementaryInformationEntity.getType());
        assertEquals("value", complementaryInformationEntity.getValue());
        assertEquals("delegationDataId", complementaryInformationEntity.getDelegationDataId());
        assertEquals("delegationDataInfoId", complementaryInformationEntity.getDelegationDataInfoId());
    }

}
