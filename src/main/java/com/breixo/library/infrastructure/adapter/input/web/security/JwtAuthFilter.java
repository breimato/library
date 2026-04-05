package com.breixo.library.infrastructure.adapter.input.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/** The Class Jwt Auth Filter. */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    /** The Authorization header name. */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /** The Bearer prefix. */
    private static final String BEARER_PREFIX = "Bearer ";

    /** The jwt token provider. */
    private final JwtTokenProvider jwtTokenProvider;

    /** {@inheritDoc} */
    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        final var token = this.extractToken(request);

        if (token != null && this.jwtTokenProvider.isValid(token)) {
            final var userId = this.jwtTokenProvider.getUserId(token);
            final var role = this.jwtTokenProvider.getUserRole(token);

            final var authority = new SimpleGrantedAuthority("ROLE_" + role.name());
            final var authentication = new UsernamePasswordAuthenticationToken(userId, null, List.of(authority));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extract token from request header.
     *
     * @param request the http request
     * @return the token or null
     */
    private String extractToken(final HttpServletRequest request) {
        final var header = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
