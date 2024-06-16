package com.afklm.spring.security.habile;

import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_VERIFICATION;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.startsWith;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpringSecurityHabileMessageCodeTest {

    @Test
    public void testToString() {
        Assertions.assertEquals(SS4H_MSG_VERIFICATION.getMnemonic(), SS4H_MSG_VERIFICATION.toString());
    }

    @Test
    public void testFormatWithWrongNumberOfarguments() {
        final String format = SS4H_MSG_VERIFICATION.format();
        MatcherAssert.assertThat(format, startsWith(SS4H_MSG_VERIFICATION.getMnemonic()));
        MatcherAssert.assertThat(format, endsWith(SS4H_MSG_VERIFICATION.getLabel()));
    }

}
