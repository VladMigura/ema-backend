package com.itechart.ema.configuration;

import com.itechart.ema.security.TokenAuthenticationProvider;
import com.itechart.ema.security.TokenFilter;
import com.itechart.ema.security.TokenValidator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import static com.itechart.ema.api.AuthApiController.LOGIN_PATH;
import static com.itechart.ema.api.AuthApiController.REFRESH_TOKEN_PATH;
import static com.itechart.ema.api.ServiceApiController.HEALTH_CHECK_PATH;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Import(SecurityProblemSupport.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenValidator tokenValidator;
    private final SecurityProblemSupport problemSupport;
    private final TokenAuthenticationProvider tokenAuthenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable();
        httpSecurity
                .cors();
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity
                .addFilterBefore(new TokenFilter(tokenValidator), BasicAuthenticationFilter.class);
        httpSecurity
                .authenticationProvider(tokenAuthenticationProvider);
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport);
        httpSecurity.authorizeRequests()
                .antMatchers(OPTIONS, "/**").permitAll()
                .antMatchers(GET, HEALTH_CHECK_PATH).permitAll()
                .antMatchers(POST, LOGIN_PATH).permitAll()
                .antMatchers(POST, REFRESH_TOKEN_PATH).permitAll()
                .antMatchers("/api/v1/**").authenticated();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(tokenAuthenticationProvider);
    }

}
