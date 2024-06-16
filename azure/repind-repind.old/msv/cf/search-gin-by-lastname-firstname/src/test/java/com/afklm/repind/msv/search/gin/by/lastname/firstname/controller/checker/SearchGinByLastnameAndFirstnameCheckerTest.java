package com.afklm.repind.msv.search.gin.by.lastname.firstname.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
class SearchGinByLastnameAndFirstnameCheckerTest {

    @InjectMocks
    private SearchGinByLastnameAndFirstnameChecker searchGinChecker;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    void lastnameCheckerSuccessTest(){
        try {
            searchGinChecker.checkSearchGinByLastname("TEST");
            //ok
        } catch (BusinessException e) {
            fail("Should not throw a BusinessException");
        }

        try {
            searchGinChecker.checkSearchGinByLastname("TEST2");
            fail("Should throw a BusinessException");
        } catch (BusinessException e) {
            //ok
        }
    }

    @Test
    void firstnameCheckerSuccessTest() {
        try {
            searchGinChecker.checkSearchGinByFirstname("Test");
            //ok
        } catch (BusinessException e) {
            fail("Should not throw a BusinessException");
        }

        try {
            searchGinChecker.checkSearchGinByFirstname("Test2");
            fail("Should throw a BusinessException");
        } catch (BusinessException e) {
            //ok
        }
    }
}
