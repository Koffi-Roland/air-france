package com.afklm.spring.security.habile.roles;

import com.afklm.spring.security.habile.properties.Ss4hProperties;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

public abstract class RolesMockTest {
    @SpyBean
    protected Ss4hProperties mockedRoles;

    protected List<String> authoritiesOf(String... auth) {
        return Stream.of(auth).collect(Collectors.toList());
    }

    @BeforeEach
    public void setup() {
        when(mockedRoles.getRoles())
                .thenReturn(Stream.of(new Object[][]{
                        {"P_TEST_USER", Collections.singletonList("USER")},
                        {"P_TEST_ADMIN", Arrays.asList("USER", "ADMIN")},
                        {"P_TEST_EMPTY", Collections.emptyList()},
                        {"P_TEST_DUPLICATE", Arrays.asList("USER", "USER", "ADMIN", "USER")}})
                        .collect(Collectors.toMap(key -> (String) key[0], roles -> (List<String>) roles[1])));
    }
}
