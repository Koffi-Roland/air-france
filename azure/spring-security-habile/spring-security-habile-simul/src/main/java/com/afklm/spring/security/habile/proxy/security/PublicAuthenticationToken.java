package com.afklm.spring.security.habile.proxy.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class PublicAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -4792529433765389900L;

    public PublicAuthenticationToken() {
        super(null);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return "principal";
    }

}
