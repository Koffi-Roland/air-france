package com.afklm.spring.security.habile.roles;

import com.afklm.spring.security.habile.properties.Ss4hProperties;
import com.afklm.spring.security.habile.utils.StreamHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.afklm.spring.security.habile.GlobalConfiguration.ROLE_PREFIX;

/**
 * This class allows to enrich the roles a user has with the permissions the application gives to these roles.
 */
@Service
public class RolesEnricher {
    private static final Logger LOGGER = LoggerFactory.getLogger(RolesEnricher.class);
    private Ss4hProperties afklSecurityProperties;

    @Autowired
    public RolesEnricher(Ss4hProperties afklSecurityProperties) {
        this.afklSecurityProperties = afklSecurityProperties;
    }

    /**
     * Takes the roles the user has and expands them with the permissions associated to these roles by the application
     * using the SS4H Reactive filter.
     *
     * @param baseRoles list of roles the user has
     * @return expanded list of roles and permissions;
     * empty if the only specified role by the application gives no permissions or if there was a processing error
     */
    public List<String> enrich(List<String> baseRoles) {
        LOGGER.debug("Starting role enrichment for user");

        try {
            List<String> enrichedRoles = StreamHelper.applyTo(baseRoles, stream -> stream
                    .flatMap(role -> Stream.concat(
                            Stream.of(ROLE_PREFIX + role),
                            afklSecurityProperties.getPermissions(role).stream()))
                    .distinct());
            LOGGER.debug("Successfully enriched the authorities : {}", String.join(" - ", enrichedRoles));
            return enrichedRoles;
        } catch (Exception e) {
            LOGGER.error("Could not enrich the authorities :", e);
            return Collections.emptyList();
        }
    }
}
