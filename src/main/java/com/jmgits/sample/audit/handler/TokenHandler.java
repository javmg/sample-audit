package com.jmgits.sample.audit.handler;

import com.jmgits.sample.audit.exception.TokenException;
import com.jmgits.sample.audit.view.TokenData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class TokenHandler {

    private static final String TOKEN_ISSUER = "sample-audit";

    private static final long EXPIRY_IN_HOURS = 4;

    private final byte[] secret;

    public String createToken(TokenData data) {

        LocalDateTime now = LocalDateTime.now();

        Claims claims = Jwts.claims()
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(now.plusHours(EXPIRY_IN_HOURS).atZone(ZoneId.systemDefault()).toInstant()))
                .setSubject(data.getUsername());

        claims.put("username", data.getUsername());
        claims.put("authorities", data.getAuthorities());

        return Jwts.builder().setHeaderParam("typ", "JWT").setClaims(claims).signWith(HS512, secret).compact();
    }

    public TokenData getData(String token) {

        try {

            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

            TokenData tokenData = new TokenData();

            tokenData.setUsername(claims.get("username", String.class));
            tokenData.setAuthorities(new HashSet<>(ofNullable(claims.get("authorities", List.class)).orElse(Collections.emptyList())));

            return tokenData;

        } catch (JwtException e) {
            throw new TokenException("Invalid token", e);
        }
    }
}
