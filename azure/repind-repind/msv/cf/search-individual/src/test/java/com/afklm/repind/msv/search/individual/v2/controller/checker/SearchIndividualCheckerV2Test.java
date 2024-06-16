package com.afklm.repind.msv.search.individual.v2.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.individual.model.error.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
class SearchIndividualCheckerV2Test {

    @InjectMocks
    private SearchIndividualCheckerV2 checker;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    void testCheckSearchIndividual() throws BusinessException {
        Throwable exception = assertThrows(BusinessException.class, () -> checker.checkSearchIndividual(null, null, null, null, null, null, null));
        assertEquals(ErrorMessage.ERROR_MESSAGE_400.getDescription(), exception.getMessage());

        checker.checkSearchIndividual("12345789012", null , null, null, null, null, null);
    }

}
