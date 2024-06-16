package com.afklm.repind.msv.provide.identification.data.ut.helper;


import com.afklm.repind.msv.provide.identification.data.helper.ProvideHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProvideHelperTest {
    @Autowired
    private ProvideHelper provideHelper;

    @Test
    void testRemoveSpecialCharacter(){
        Assertions.assertEquals("Bequille",provideHelper.removeSpecialCharacter("Béquille"));
        Assertions.assertEquals("Hello world !!!",provideHelper.removeSpecialCharacter("Hello world !!!"));
        Assertions.assertEquals("Aout", provideHelper.removeSpecialCharacter("Août"));
    }
}
