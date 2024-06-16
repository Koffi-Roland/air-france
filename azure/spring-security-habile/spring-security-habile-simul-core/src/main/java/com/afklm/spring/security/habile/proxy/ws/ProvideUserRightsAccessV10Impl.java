package com.afklm.spring.security.habile.proxy.ws;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000479.v1.HblWsBusinessException;
import com.afklm.soa.stubs.w000479.v1.ProvideUserRightsAccessV10;
import com.afklm.soa.stubs.w000479.v1.fault.HblWsBusinessFault;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRQ;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS;
import com.afklm.spring.security.habile.proxy.model.UserInformation;
import com.afklm.spring.security.habile.proxy.service.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.jws.WebService;

/**
 * ProvideUserRightsAccessV10 implementation for simulation
 * 
 * @author m405991
 *
 */
@Component
@WebService(endpointInterface = "com.afklm.soa.stubs.w000479.v1.ProvideUserRightsAccessV10")
public class ProvideUserRightsAccessV10Impl implements ProvideUserRightsAccessV10 {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvideUserRightsAccessV10Impl.class);

    @Autowired
    private ConfigurationService authoritiesConfigurationService;

    @Override
    public ProvideUserRightsAccessRS provideUserRightsAccess(ProvideUserRightsAccessRQ request) throws HblWsBusinessException, SystemException {
        LOGGER.info("Providing user rights access for user '{}'", request.getUserId());

        UserInformation userInformation = authoritiesConfigurationService.getUserInformation(request.getUserId());
        if (userInformation == null) {
            LOGGER.error("User '{}' does not exist in config file", request.getUserId());

            HblWsBusinessFault fault = new HblWsBusinessFault();
            fault.setCode(999);
            fault.setMessage("[SIMULATION] User " + request.getUserId() + " not found");
            throw new HblWsBusinessException("User does not exist !", fault);
        }

        ProvideUserRightsAccessRS response = new ProvideUserRightsAccessRS();
        response.setFirstName(userInformation.getFirstName());
        response.setLastName(userInformation.getLastName());

        ProvideUserRightsAccessRS.ProfileList profiles = new ProvideUserRightsAccessRS.ProfileList();
        profiles.getProfileName().addAll(userInformation.getProfiles());
        response.setProfileList(profiles);

        return response;
    }
}
