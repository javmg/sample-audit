package com.jmgits.sample.audit.util;

import com.jmgits.sample.audit.view.Principal;
import com.jmgits.sample.audit.view.PrincipalAuthentication;
import com.jmgits.sample.audit.view.TokenData;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.stream.Collectors;

public final class SecurityUtils {

    public static void setTokenData(TokenData tokenData) {

        Principal principal = new Principal();

        principal.setUsername(tokenData.getUsername());
        principal.setAuthorities(tokenData.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        principal.setTokenData(tokenData);

        SecurityContextHolder.getContext().setAuthentication(new PrincipalAuthentication(principal));

    }

    public static Optional<TokenData> findTokenData() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !PrincipalAuthentication.class.isAssignableFrom(authentication.getClass())) {
            return Optional.empty();
        }

        TokenData tokenData = ((Principal) authentication.getPrincipal()).getTokenData();

        return Optional.of(tokenData);
    }

    public static TokenData getTokenData() {

        return findTokenData().orElseThrow(() ->
                new AuthenticationCredentialsNotFoundException("No authentication found")
        );
    }
}
