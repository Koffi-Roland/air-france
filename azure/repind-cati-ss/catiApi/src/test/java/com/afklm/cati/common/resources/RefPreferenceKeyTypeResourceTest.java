package com.afklm.cati.common.resources;

import com.afklm.cati.common.spring.rest.resources.RefPreferenceKeyTypeResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class RefPreferenceKeyTypeResourceTest {

    private static final Integer ID = 2;
    private static final String KEY = "kdmdpkeekddk023df";
    private static final String TEST = "Test";

    @Test
    @DisplayName("Unit test ref  product resource")
    public void preferenceKeyTypeResourceTest() {
        RefPreferenceKeyTypeResource refPreferenceKeyTypeResource = this.mockRefPreferenceKeyTypeResource();
        assertAll(
                () -> assertEquals(refPreferenceKeyTypeResource.getKey(), KEY),
                () -> assertEquals(refPreferenceKeyTypeResource.getDataType(), TEST),
                () -> assertEquals(refPreferenceKeyTypeResource.getCondition(), TEST),
                () -> assertEquals(refPreferenceKeyTypeResource.getMaxLength(), ID),
                () -> assertEquals(refPreferenceKeyTypeResource.getMinLength(), ID)
        );
    }
    private RefPreferenceKeyTypeResource mockRefPreferenceKeyTypeResource()
    {
        RefPreferenceKeyTypeResource refPreferenceKeyTypeResource = new RefPreferenceKeyTypeResource();
        refPreferenceKeyTypeResource.setKey(KEY);
        refPreferenceKeyTypeResource.setDataType(TEST);
        refPreferenceKeyTypeResource.setCondition(TEST);
        refPreferenceKeyTypeResource.setMaxLength(ID);
        refPreferenceKeyTypeResource.setMinLength(ID);

        return refPreferenceKeyTypeResource;
    }
}
