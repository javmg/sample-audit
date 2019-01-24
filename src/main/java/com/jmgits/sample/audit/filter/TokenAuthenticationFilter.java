package com.jmgits.sample.audit.filter;

import com.jmgits.sample.audit.handler.TokenHandler;
import com.jmgits.sample.audit.util.SecurityUtils;
import com.jmgits.sample.audit.view.TokenData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jmgits.sample.audit.util.Constant.HEADER_AUTH_TOKEN;
import static org.springframework.util.StringUtils.isEmpty;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenHandler tokenHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authToken = request.getHeader(HEADER_AUTH_TOKEN);

        if (!isEmpty(authToken)) {

            TokenData tokenData = tokenHandler.getData(authToken);

            SecurityUtils.setTokenData(tokenData);
        }

        chain.doFilter(request, response);
    }

}
