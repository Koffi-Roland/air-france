package com.afklm.spring.security.habile.proxy;

import org.springframework.util.Base64Utils;

public class BasicAuthFixture {

    public static final String USERNAME = "junit-userId";
    public static final String PASSWORD = "junit-password";

    public static String getBasicAuthHeader() {
        return "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes());
    }
}
