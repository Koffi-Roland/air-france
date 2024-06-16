package com.afklm.spring.security.habile.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.oidc.UserInfo;
import com.afklm.spring.security.habile.utils.StreamHelper;

import reactor.core.publisher.Mono;

/**
 * Abstract class defining the retrieval of a {@link HabileUserDetails} from its employee id.<br/>
 * Populated fields are:
 * <ul>
 * <li>userId (employee id)</li>
 * <li>last name</li>
 * <li>first name</li>
 * <li>list of profiles owned by the employee</li>
 * </ul>
 *
 * @param <T> represents either the V1 or the V2 request data type
 * @author TECC
 */
public abstract class AbstractUserRetriever {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUserRetriever.class);

    /**
     * This function describes the logic to retrieve the user from UA2. It works for both V1 and V2.
     *
     * @param username     of the user that should be retrieved
     * @param token provided by the proxy and needed by the V2 to authenticate the request
     * @return a reactive wrap of the retrieved user and it's data
     */
    public Mono<HabileUserDetails> getUser(String username, String token) {
        return Mono.just(username).zipWith(Mono.just(token))
                .doOnNext(useless -> LOGGER.trace("Between request and call"))
                .flatMap(tuple -> callHabile(tuple.getT1(), tuple.getT2()))
                .onErrorMap(throwable -> {
                    LOGGER.error("User {} could not be found : ", username, throwable);
                    throw new UsernameNotFoundException("This user could not be retrieved");
                })
                .map(result -> compose(token, result));
    }

    protected abstract Mono<UserInfo> callHabile(String username, String token);

    private HabileUserDetails compose(String token, UserInfo response) {
        return new HabileUserDetails(
                response.getSub(), 
                response.getFamily_name(), 
                response.getGiven_name(), 
                response.getEmail(),
                StreamHelper.transform(response.getProfile(), SimpleGrantedAuthority::new),
                token);
    }
}
