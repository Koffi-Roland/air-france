package com.afklm.spring.security.habile.helper;

import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_PUBLIC_URLS;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.afklm.spring.security.habile.properties.Ss4hProperties;

/**
 * Helper dealing with public resources: both default public patterns & client defined public end-points
 */
@Component
public class PublicResourcesHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublicResourcesHelper.class);
    @Autowired
    private Ss4hProperties afklSecurityProperties;

    /**
     * Creates the list holding all resources that are public (including URL patterns & client defined end-points).
     * 
     * @return the list of public resources
     */
    public List<String> getPublicResources() {
        // this needs to be done in case of SOA RR provider
        StringJoiner joiner = new StringJoiner("', '", "'", "'");
        // handle public end-points
        List<String> publicEndpoints = Stream.concat(getDefaultPublicPatterns().stream(), afklSecurityProperties.getPublicEndpoints().stream())
            .filter(Objects::nonNull)
            .filter(StringUtils::hasText)
            .collect(Collectors.toList());

        publicEndpoints.forEach(joiner::add);
        LOGGER.info(SS4H_MSG_PUBLIC_URLS.format(joiner));
        return publicEndpoints;
    }

    /**
     * Return the URL patterns that are unprotected by default. Change with care.
     *
     * @return the URL patterns
     */
    private List<String> getDefaultPublicPatterns() {
        return Arrays.asList("/ws/**", "/**/*.css", "/**/*.gif", "/**/*.png", "/**/*.jpeg", "/**/*.jpg");
    }
}
