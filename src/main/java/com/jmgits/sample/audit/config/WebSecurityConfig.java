package com.jmgits.sample.audit.config;

import com.jmgits.sample.audit.filter.TokenAuthenticationFilter;
import com.jmgits.sample.audit.handler.TokenHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private final TokenHandler tokenHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .cors().and()
                .csrf().disable().authorizeRequests();

        registry

                .antMatchers("/**").permitAll()
                .antMatchers("/api/tokens/**").permitAll()

                .antMatchers("/api/**").authenticated()

                .and()

                .addFilterBefore(new TokenAuthenticationFilter(tokenHandler), UsernamePasswordAuthenticationFilter.class)

                .httpBasic().disable() //

                .sessionManagement().sessionCreationPolicy(STATELESS);
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT user.username as username, user.password as password, user.enabled as enabled FROM user WHERE user.username = ?")
                .authoritiesByUsernameQuery("SELECT user.username as username, 'USER' as authority FROM user WHERE user.username = ?");
        ;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
