package com.afklm.spring.security.habile;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class HabilePrincipalFixture {

    public static HabilePrincipal habilePrincipal(String id, String... profilesToBeInjected) {
        String userId = "JUNIT-" + id;
        String firstName = "firstName_" + id;
        String lastName = "lastName_" + id;
        String email = lastName + "@fxture.org";
        List<String> profiles = new ArrayList<>();
        for (String profile : profilesToBeInjected) {
            profiles.add(profile);
        }

        HabilePrincipal habilePrincipal = new HabilePrincipal(userId, firstName, lastName, email, profiles);
        return habilePrincipal;
    }

    public static UsernameNotFoundException exception() {
        return new UsernameNotFoundException("JUNIT");
    }
}
