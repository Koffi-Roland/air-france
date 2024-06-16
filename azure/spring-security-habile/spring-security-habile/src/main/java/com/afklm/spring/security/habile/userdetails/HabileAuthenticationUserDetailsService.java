package com.afklm.spring.security.habile.userdetails;

import static com.afklm.spring.security.habile.GlobalConfiguration.ANONYMOUS;
import static com.afklm.spring.security.habile.GlobalConfiguration.EMPTY_STRING;
import static com.afklm.spring.security.habile.GlobalConfiguration.ROLE_PREFIX;
import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_RT_INCONSISTANCY_AUTH_USER_SEC_USER;
import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_RT_INCONSISTANCY_SM_USER_W000479;
import static com.afklm.spring.security.habile.oidc.JwtUtils.JWT_PREFIX;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.afklm.spring.security.habile.HabilePrincipal;
import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.oidc.JwtUtils;
import com.afklm.spring.security.habile.properties.Ss4hProperties;
import com.afklm.spring.security.habile.userdetails.dao.UserRetrieverDao;
import com.afklm.spring.security.habile.userdetails.dao.UserRetrieverDaoImplPing;

import jakarta.annotation.PostConstruct;

/**
 * HabileAuthenticationUserDetailsService
 * 
 * Authentication service based on Habile data
 * 
 * @author tecc
 *
 */
public class HabileAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabileAuthenticationUserDetailsService.class);

    @Autowired
    private Ss4hProperties rolesAndPermissions;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired(required = false)
    private UserRetrieverDao userSoaDao;

    @Autowired
    @Qualifier("habileUserRetrieverDaoImplPing")
    private UserRetrieverDao userPingDao;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        LOGGER.debug("Authenticating user '{}'", token.getName());
        HabilePrincipal habilePrincipal;

        Optional<String> jwt = extractJwt(token);
        if (isHabileAnonymous(token)) {
            habilePrincipal = new HabilePrincipal(ANONYMOUS, ANONYMOUS, ANONYMOUS, ANONYMOUS, rolesAndPermissions.getAnonymousRoles());
        } else if (jwt.isPresent()) {
            habilePrincipal = userPingDao.getUser(jwt.get(), null);
            if (!habilePrincipal.getUserId().equalsIgnoreCase(token.getPrincipal().toString())) {
                throw new UsernameNotFoundException(SS4H_MSG_RT_INCONSISTANCY_AUTH_USER_SEC_USER.format(habilePrincipal.getUserId(), token.getPrincipal().toString()));
            }
        } else {
            if (userSoaDao == null) {
                throw new UsernameNotFoundException(SS4H_MSG_RT_INCONSISTANCY_SM_USER_W000479.format());
            }
            habilePrincipal = userSoaDao.getUser(token.getName(), (String) token.getCredentials());
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        habilePrincipal.getProfiles().forEach(profile -> processProfile(profile, authorities));

        HabileUserDetails habileUserDetails = new HabileUserDetails(
                token.getName(),
                habilePrincipal.getLastName(),
                habilePrincipal.getFirstName(),
                habilePrincipal.getEmail(),
                authorities,
                jwt.orElse(null));

        LOGGER.debug("Customizing principal with class '{}'", customUserDetailsService.getClass().getName());

        return customUserDetailsService.enrichUserDetails(habileUserDetails);
    }

    /**
     * Checks if the Principal defined in the PreAuthenticatedToken is equals to "anonymous"
     * 
     * @param token the {@link PreAuthenticatedAuthenticationToken}
     * @return if the user is "anonymous"
     */
    private static boolean isHabileAnonymous(PreAuthenticatedAuthenticationToken token) {
        return EMPTY_STRING.equals(token.getPrincipal()) || ANONYMOUS.equals(token.getPrincipal());
    }

    /**
     * Checks if the Credentials contained in the PreAuthenticatedToken is prefixed by {@link JwtUtils.JWT_PREFIX},
     * if so returns the JWT value (ie. the content of the Credentials where the prefix has been removed), otherwise
     * returns null.
     * 
     * @param token the {@link PreAuthenticatedAuthenticationToken}
     * @return the JWT if conditions met, null otherwise
     */
    private static Optional<String> extractJwt(PreAuthenticatedAuthenticationToken token) {
        return Optional.ofNullable(token.getCredentials())
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(creds -> creds.startsWith(JWT_PREFIX))
                .map(creds -> creds.substring(JWT_PREFIX.length()));
    }

    /**
     * Process a profile by adding it as a role and then retrieve all sub elements
     * in the YAML file and add them without ROLE_ prefix (ie. as permission)
     * 
     * @param profileName
     * @param authorities
     */
    private void processProfile(String profileName, Collection<GrantedAuthority> authorities) {
        String habileProfile = profileName;
        if (!habileProfile.startsWith(ROLE_PREFIX)) {
            habileProfile = ROLE_PREFIX + profileName;
        }
        List<String> permissions = rolesAndPermissions.getPermissions(profileName);
        if (permissions != null) {
            LOGGER.debug("...adding profile '{}'", habileProfile);
            authorities.add(new SimpleGrantedAuthority(habileProfile));

            permissions.stream().map(SimpleGrantedAuthority::new).forEach(authorities::add);
        }
    }

    /**
     * When no <b>w000479</b> bean is present, then no {@link UserRetrieverDao} bean targeting the
     * <b>w000479</b> is present and Spring injects the {@link UserRetrieverDaoImplPing} bean into the
     * <b>userDao</b> property.<br/>
     * This method makes sure that in such circumstances the <b>userDao</b> which targets the
     * <b>w000479</b> is set to null.
     */
    @PostConstruct
    public void checkUserRetrieverDaos() {
        if (this.userSoaDao instanceof UserRetrieverDaoImplPing) {
            this.userSoaDao = null;
        }
    }
}