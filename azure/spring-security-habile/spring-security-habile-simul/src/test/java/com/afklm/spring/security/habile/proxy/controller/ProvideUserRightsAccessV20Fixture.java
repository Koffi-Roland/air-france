package com.afklm.spring.security.habile.proxy.controller;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.common.systemfault.v1.SystemFault;
import com.afklm.soa.stubs.w000479.v2.HblWsBusinessException;
import com.afklm.soa.stubs.w000479.v2.hblwsfault.HblWsBusinessFault;
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

    public static HblWsBusinessException businessException() {
        HblWsBusinessFault bf = new HblWsBusinessFault();
        bf.setCode(1);
        bf.setMessage("JUNIT-MESSAGE");

        HblWsBusinessException be = new HblWsBusinessException("Junit system exception", bf);
        return be;
    }

    public static SystemException systemException() {
        SystemFault faultInfo = new SystemFault();
        faultInfo.setErrorCode("JUNIT-001");
        faultInfo.setFaultDescription("JUNIT-DESCRIPTION");
        SystemException se = new SystemException("Junit system exception", faultInfo);
        return se;
    }

    public static WebServiceException webServiceException() {
        return new WebServiceException();
    }
}
