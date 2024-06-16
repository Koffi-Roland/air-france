package com.afklm.spring.security.habile.userdetails.dao;

import com.afklm.soa.stubs.w000479.v1.HblWsBusinessException;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS.ProfileList;

import jakarta.xml.ws.WebServiceException;

public class ProvideUserRightsAccessV10Fixture {

    public static ProvideUserRightsAccessRS response() {
        ProvideUserRightsAccessRS invocationResponse = new ProvideUserRightsAccessRS();
        invocationResponse.setFirstName("firstName");
        invocationResponse.setLastName("lastName");
        ProfileList profiles = new ProfileList();
        profiles.getProfileName().add("P_TEST");
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
