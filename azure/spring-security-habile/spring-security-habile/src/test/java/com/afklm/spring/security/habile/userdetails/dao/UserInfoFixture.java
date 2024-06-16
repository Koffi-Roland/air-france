package com.afklm.spring.security.habile.userdetails.dao;

import com.afklm.soa.stubs.w000479.v2.HblWsBusinessException;
import com.afklm.spring.security.habile.oidc.UserInfo;

import org.springframework.web.client.RestClientException;

import java.util.Arrays;

public class UserInfoFixture {

    public static UserInfo response(String subject, String... roles) {
        UserInfo invocationResponse = new UserInfo();
        invocationResponse.setSub(subject);
        invocationResponse.setFamily_name("lastName");
        invocationResponse.setGiven_name("firstName");
        invocationResponse.setEmail("email");
        invocationResponse.setProfile(Arrays.asList(roles));
        return invocationResponse;
    }

    public static RestClientException restClientException() {
        return new RestClientException("sample error message");
    }

    public static HblWsBusinessException webServiceBizException() {
        return new HblWsBusinessException("Sample error message", null);
    }
}
