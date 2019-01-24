package com.jmgits.sample.audit.view;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PrincipalAuthentication implements Authentication {

    private final Principal principal;

    private boolean authenticated = true;

    public PrincipalAuthentication(Principal principal) {
        this.principal = principal;
    }

    @Override
    public String getName() {
        return principal.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return principal.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return principal.getPassword();
    }

    @Override
    public Principal getDetails() {
        return principal;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
