package com.afklm.spring.security.habile.userdetails.dao;

import com.afklm.soa.stubs.w000479.v2.HblWsBusinessException;
import com.afklm.soa.stubs.w000479.v2.provideuserrightsaccessv2.ProvideUserRightsAccessRS;
import com.afklm.soa.stubs.w000479.v2.provideuserrightsaccessv2.ProvideUserRightsAccessRS.ProfileList;

import jakarta.xml.ws.WebServiceException;

public class ProvideUserRightsAccessV20Fixture {

    public static ProvideUserRightsAccessRS response() {
        ProvideUserRightsAccessRS invocationResponse = new ProvideUserRightsAccessRS();
        invocationResponse.setFirstName("firstName");
        invocationResponse.setLastName("lastName");
        invocationResponse.setEmail("email");
        ProfileList profiles = new ProfileList();
        profiles.getProfileName().add("profile");
        invocationResponse.setProfileList(profiles);
        return invocationResponse;
    }

    public static WebServiceException webServiceException() {
        return new WebServiceException();
    }

    public static HblWsBusinessException webServiceBizException() {
        return new HblWsBusinessException("Sample error message", null);
    }
}
