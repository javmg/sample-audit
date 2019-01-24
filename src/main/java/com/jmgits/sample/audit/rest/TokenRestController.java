package com.jmgits.sample.audit.rest;

import com.jmgits.sample.audit.handler.TokenHandler;
import com.jmgits.sample.audit.view.TokenData;
import com.jmgits.sample.audit.view.TokenRequest;
import com.jmgits.sample.audit.view.TokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@Api(tags = "Tokens")
@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class TokenRestController {

    private final AuthenticationManager authenticationManager;

    private final TokenHandler tokenHandler;

    @ApiOperation(value = "Create a token")
    @PostMapping
    @ResponseStatus(CREATED)
    public TokenResponse create(@Validated @RequestBody TokenRequest tokenRequest, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword());

        Authentication result = authenticationManager.authenticate(authentication);

        UserDetails principal = (UserDetails) result.getPrincipal();

        TokenData tokenData = new TokenData();

        tokenData.setUsername(principal.getUsername());
        tokenData.setAuthorities(principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet())
        );

        return new TokenResponse(tokenHandler.createToken(tokenData));
    }
}
