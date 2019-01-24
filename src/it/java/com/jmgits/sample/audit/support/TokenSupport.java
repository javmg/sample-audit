package com.jmgits.sample.audit.support;

import com.jmgits.sample.audit.handler.TokenHandler;
import com.jmgits.sample.audit.view.TokenData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import static com.jmgits.sample.audit.util.Constant.HEADER_AUTH_TOKEN;
import static java.util.Collections.singleton;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class TokenSupport {

    private final TokenHandler tokenHandler;

    public <T> HttpEntity<T> toRequest(T body) {

        return toRequest("user1", body);
    }

    public <T> HttpEntity<T> toRequest(String username, T body) {

        String authToken = getAuthToken(username);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(APPLICATION_JSON);
        headers.set(HEADER_AUTH_TOKEN, authToken);

        return new HttpEntity<>(body, headers);
    }

    //
    // private

    private String getAuthToken(String username) {

        TokenData tokenData = new TokenData();
        tokenData.setUsername(username);
        tokenData.setAuthorities(singleton("USER"));

        return tokenHandler.createToken(tokenData);
    }
}
