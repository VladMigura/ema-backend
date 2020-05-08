package com.itechart.ema.security.impl;

import com.itechart.ema.configuration.AppConfiguration;
import com.itechart.ema.security.TokenValidator;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;

import static io.jsonwebtoken.io.Decoders.BASE64;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenValidatorImpl implements TokenValidator {

    private final AppConfiguration appConfiguration;
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(BASE64.decode(appConfiguration.getJwt().getBase64Secret()));
    }

    @Override
    public boolean validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (final JwtException e) {
            log.debug("Invalid token!");
        }
        return false;
    }

}
