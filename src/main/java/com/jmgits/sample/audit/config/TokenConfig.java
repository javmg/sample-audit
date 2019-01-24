package com.jmgits.sample.audit.config;

import com.jmgits.sample.audit.handler.TokenHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.DatatypeConverter;

@Configuration
public class TokenConfig {

    @Bean
    public TokenHandler tokenHandler(@Value("${application.token.secret}") String secret){

        return new TokenHandler(DatatypeConverter.parseBase64Binary(secret));

    }
}
