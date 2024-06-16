package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.accesskeytransformer.AccessKey;
import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.spring.rest.resources.SecurityInfo;
import com.afklm.cati.common.springsecurity.userdetailsservice.AuthenticatedUserDTO;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000479.v1.HblWsBusinessException;
import com.afklm.soa.stubs.w000479.v1.ProvideUserRightsAccessV10;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRQ;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AF-KLM
 */
@RestController
@RequestMapping("/securities")
@CrossOrigin
public class SecurityController {

    @Autowired(required = false)
    ProvideUserRightsAccessV10 provideUserRightsAccess;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SecurityController.class);

    final UserProfilesAccessKey accessKeyConfiguration;

    public SecurityController(UserProfilesAccessKey accessKeyConfiguration) {
        this.accessKeyConfiguration = accessKeyConfiguration;
    }


    /**
     * @param authenticatedUserDTO the information of the user authenticated
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public UserProfilesAccessKey securitiesGet(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO) {
        List<String> profiles = authenticatedUserDTO.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority) // extraction of the name of the authority
                .filter(authName -> authName.startsWith("ROLE_")) // // a profile is prefixed by ROLE_
                .map(authName -> authName.substring(5)) // removal of the ROLE_ prefix
                .collect(Collectors.toList());

        UserProfilesAccessKey res = new UserProfilesAccessKey();

        res.setAccessKeyConfiguration(
                accessKeyConfiguration.getAccessKeyConfiguration()
                        .stream()
                        .filter(pak -> profiles.contains(pak.getProfile()))
                        .collect(Collectors.toList()));

        return res;
    }

    /**
     * Retrieve connected user
     *
     * @param authenticatedUserDTO the information of the user authenticated
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/user", produces = "application/json; charset=utf-8")
    public AuthenticatedUserDTO securitiesGetUser(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO) {
        return authenticatedUserDTO;
    }


    /**
     * Retrieve firstname / lastname of connected user
     *
     * @param authenticatedUserDTO the information of the user authenticated
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/username", produces = "application/json; charset=utf-8")
    public ProvideUserRightsAccessRS securitiesGetUserName(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO) {
        ProvideUserRightsAccessRQ provideUserRightsAccessRQ = new ProvideUserRightsAccessRQ();
        provideUserRightsAccessRQ.setUserId(authenticatedUserDTO.getUsername());
        ProvideUserRightsAccessRS provideUserRightsAccessRS;
        try {
            provideUserRightsAccessRS = provideUserRightsAccess.provideUserRightsAccess(provideUserRightsAccessRQ);
            return provideUserRightsAccessRS;
        } catch (HblWsBusinessException | SystemException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public SecurityInfo me(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO) {
        SecurityInfo res = this.extractSecurityInfo(authenticatedUserDTO);
        if (authenticatedUserDTO != null)
            LOGGER.info("Security Called for User : " + authenticatedUserDTO.getUsername());
        return res;
    }

    private SecurityInfo extractSecurityInfo(AuthenticatedUserDTO user) {
        if (user == null) return null;
        SecurityInfo wrapper = new SecurityInfo();
        UserProfilesAccessKey userProfilesAccessKey = user.getUserProfilesAccessKey();
        List<String> roles = new ArrayList<>();
        List<String> permissions = new ArrayList<>();
        for (ProfileAccessKey profileAccessKey : userProfilesAccessKey.getAccessKeyConfiguration()) {
            permissions.add(profileAccessKey.getProfile());
            int accessKeyLstLength = profileAccessKey.getAccessKeyLst().length;
            for (int i = 0; i < accessKeyLstLength; i++) {
                AccessKey item = profileAccessKey.getAccessKeyLst()[i];
                roles.add(item.getAccessKey());
            }
        }
        wrapper.setFirstname(user.getFirstName());
        wrapper.setLastname(user.getLastName());
        wrapper.setRoles(roles);
        wrapper.setPermissions(permissions);
        return wrapper;
    }


}
