package com.afklm.spring.security.habile.proxy;

import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.PING_JWT_SCOPES;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.afklm.spring.security.habile.oidc.JwtUtils;

import io.jsonwebtoken.Jwts;

/**
 * Convenient class to hold static methods dealing with a JWT.
 * 
 * @author m408461
 *
 */
public class JwtBuilder {
    
    /**
     * Creates a JWT.
     * 
     * @param subject - user concerned by the JWT
     * @param issuer - URL of the issuer to be injected in the JWT
     * @param jwtTtl - TTL of the JWT
     * @return
     */
    public static String createJWT(String subject, String issuer, int jwtTtl) {
        Calendar c = Calendar.getInstance();
        // Let's set the JWT Claims
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtUtils.SCOPE_KEY, PING_JWT_SCOPES);
        io.jsonwebtoken.JwtBuilder builder = Jwts.builder()
            .setClaims(claims)
            .setId(HabileProxyConstants.JWT_ID)
            .setIssuedAt(c.getTime())
            .setSubject(subject)
            .setIssuer(issuer)
            .signWith(HabileProxyConstants.getSigningKey(), HabileProxyConstants.JWT_ALGORITHM);

        c.add(Calendar.SECOND, jwtTtl);
        builder.setExpiration(c.getTime());

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

}
