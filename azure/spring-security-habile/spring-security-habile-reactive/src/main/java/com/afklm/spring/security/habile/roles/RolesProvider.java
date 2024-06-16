package com.afklm.spring.security.habile.roles;

import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.user.CompleteHabileUserDetails;
import com.afklm.spring.security.habile.utils.StreamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Has the responsibility of transforming a {@link HabileUserDetails} into a {@link CompleteHabileUserDetails} with the roles
 * the apps requires and the one it gives.
 */
@Service
public class RolesProvider {
    private RolesMatcher matcher;
    private RolesEnricher enricher;

    @Autowired
    public RolesProvider(RolesMatcher matcher, RolesEnricher enricher) {
        this.matcher = matcher;
        this.enricher = enricher;
    }

    /**
     * Transforms a {@link HabileUserDetails} into a {@link CompleteHabileUserDetails} by enriching its roles with permissions the
     * app gives, and removes the one it doesn't need.
     *
     * @param user {@link HabileUserDetails} to be enriched with permissions
     * @return {@link CompleteHabileUserDetails} with the enriched permissions and filtered roles;
     * error signal if no matching role could be found
     */
    public Mono<CompleteHabileUserDetails> provideFor(HabileUserDetails user) {
        return Mono.just(user)
                .map(HabileUserDetails::getAuthorities)
                .map(l -> StreamHelper.transform(l, GrantedAuthority::getAuthority))
                .map(matcher::match)
                .map(enricher::enrich)
                .map(l -> StreamHelper.transform(l, SimpleGrantedAuthority::new))
                .zipWith(Mono.just(user), (authorities, userDetails)
                        -> CompleteHabileUserDetails.from(userDetails).withAllAuthorities(authorities));
    }
}
