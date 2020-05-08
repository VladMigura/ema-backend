package com.itechart.ema.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@AllArgsConstructor
public class TokenFilter implements Filter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final TokenValidator tokenValidator;

    @Override
    public void init(final FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) request;
        var token = resolveToken(httpServletRequest);
        var tokenAuthentication = new TokenAuthentication(token);
        if (token != null && tokenValidator.validateToken(token)) {
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(final HttpServletRequest request) {
        var bearerToken = trim(request.getHeader(AUTHORIZATION));
        if (isNotEmpty(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public void destroy() {
    }

}
