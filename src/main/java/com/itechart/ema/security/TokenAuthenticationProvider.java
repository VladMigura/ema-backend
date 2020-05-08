package com.itechart.ema.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final TokenProvider tokenProvider;
    private final TokenValidator tokenValidator;

    @Override
    @SuppressWarnings("PMD")
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        var tokenAuthentication = (TokenAuthentication) authentication;
        var accessToken = tokenAuthentication.getName();

        if (tokenValidator.validateToken(accessToken)) {
            var userDetails = tokenProvider.buildUserDetailsByToken(accessToken);
            tokenAuthentication.setUserDetails(userDetails);
            tokenAuthentication.setAuthenticated(true);
        } else {
            tokenAuthentication.setAuthenticated(false);
        }
        return tokenAuthentication;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }

}
