package com.afklm.spring.security.habile.proxy.controller;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.common.systemfault.v1.SystemFault;
import com.afklm.soa.stubs.w000479.v1.HblWsBusinessException;
import com.afklm.soa.stubs.w000479.v1.fault.HblWsBusinessFault;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS.ProfileList;

import jakarta.xml.ws.WebServiceException;

public class ProvideUserRightsAccessV10Fixture {

    public static ProvideUserRightsAccessRS response() {
        ProvideUserRightsAccessRS invocationResponse = new ProvideUserRightsAccessRS();
        invocationResponse.setFirstName("firstName");
        invocationResponse.setLastName("lastName");
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
