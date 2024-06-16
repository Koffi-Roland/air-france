package com.afklm.spring.security.habile.user;

import static com.afklm.spring.security.habile.GlobalConfiguration.ANONYMOUS;
import static com.afklm.spring.security.habile.GlobalConfiguration.getAnonymousRoles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.userdetails.CustomUserDetailsService;
import com.afklm.spring.security.habile.utils.StreamHelper;

import reactor.core.publisher.Mono;

/**
 * This class wraps the use of the generic {@link AbstractUserRetriever} determined at runtime. This abstracts the fact there
 * might be different ways to get a user, and keeps the code called by the Authentication Manager close the
 * Spring Security API and its {@link org.springframework.security.core.userdetails.ReactiveUserDetailsService}.
 */
@Service
public class HabileUserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HabileUserDetailsService.class);

    private AbstractUserRetriever retriever;
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Constructs the user details service by providing the needed implementation of {@link AbstractUserRetriever}
     *
     * @param retriever an instance of {@link com.afklm.spring.security.habile.user.retriever.PingAccessUserRetriever}  (provided by the Spring framework)
     */
    @Autowired
    public HabileUserDetailsService(AbstractUserRetriever retriever, CustomUserDetailsService customUserDetailsService) {
        this.retriever = retriever;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Returns the {@link HabileUserDetails} found for a specific user by querying the UA2 repository
     *
     * @param username username to be searched in UA2
     * @param token    token used by the V2 only, to authenticate the request
     * @return a Mono of {@link HabileUserDetails} if the user could be found, or an error signal if not
     */
    public Mono<UserDetails> findByUsername(String username, String token) {
        if (isAnonymousUser(username, token)) {
            return Mono.just(createAnonymousUser());
        } else {
            return retriever.getUser(username, token)
                            .doOnNext(user -> LOGGER.debug("Looked up user data from service for {}", user))
                            .map(customUserDetailsService::enrichUserDetails);
        }
    }

    private static boolean isAnonymousUser(String username, String token) {
        return ANONYMOUS.equals(username);
    }

    private static HabileUserDetails createAnonymousUser() {
        return new HabileUserDetails(ANONYMOUS, ANONYMOUS, ANONYMOUS, StreamHelper.transform(getAnonymousRoles(), SimpleGrantedAuthority::new), null);
    }
}
