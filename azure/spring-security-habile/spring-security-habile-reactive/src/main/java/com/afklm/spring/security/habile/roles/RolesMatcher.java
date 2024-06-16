package com.afklm.spring.security.habile.roles;

import com.afklm.spring.security.habile.GlobalConfiguration;
import com.afklm.spring.security.habile.properties.Ss4hProperties;
import com.afklm.spring.security.habile.utils.StreamHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * This class allows to filter the roles a user has with the permissions the application requires.
 */
@Service
public class RolesMatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(RolesMatcher.class);
    private Ss4hProperties afklSecurityProperties;

    @Autowired
    public RolesMatcher(Ss4hProperties afklSecurityProperties) {
        this.afklSecurityProperties = afklSecurityProperties;
    }

    /**
     * Takes the roles the user has and filters them with the roles the application
     * using the SS4H Reactive filter requires.
     *
     * @param baseRoles list of roles the user has
     * @return filter list of roles;
     * empty if nothing matches or if there was a processing error
     */
    public List<String> match(List<String> baseRoles) {
        try {
            LOGGER.debug("Starting role matching on {} roles", baseRoles.size());

            if (baseRoles.containsAll(GlobalConfiguration.getAnonymousRoles())) {
                LOGGER.debug("Anonymous user detected, breaking authentication flow");
                return afklSecurityProperties.getAnonymousRoles();
            }

            List<String> authList = StreamHelper.applyTo(baseRoles, stream -> stream.filter(role -> afklSecurityProperties.getRoles().containsKey(role)));
            LOGGER.debug("Successfully matched the following authorities : {}", String.join(" - ", authList));
            return authList;
        } catch (Exception e) {
            LOGGER.error("Could not match the authorities :", e);
            return Collections.emptyList();
        }
    }
}
