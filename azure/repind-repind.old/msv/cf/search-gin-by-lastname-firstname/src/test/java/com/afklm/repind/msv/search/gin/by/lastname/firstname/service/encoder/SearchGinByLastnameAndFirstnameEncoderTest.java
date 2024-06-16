package com.afklm.repind.msv.search.gin.by.lastname.firstname.service.encoder;

import com.afklm.repind.msv.search.gin.by.lastname.firstname.util.GenerateTestData;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.wrapper.WrapperSearchGinByLastnameAndFirstnameResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class SearchGinByLastnameAndFirstnameEncoderTest {

    @InjectMocks
    private SearchGinByLastnameAndFirstnameEncoder encoder;

    @Test
    void lastnameCheckerSuccessTest() {
        WrapperSearchGinByLastnameAndFirstnameResponse response = encoder.decode(GenerateTestData.createIndividualList(2));
        assertNotNull(response);
        assertEquals(2, response.getGins().size());
    }

}
