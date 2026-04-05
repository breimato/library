package com.breixo.library.infrastructure.adapter.input.web.security;

import com.breixo.library.domain.model.user.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/** The Class Jwt Token Provider. */
@Component
public class JwtTokenProvider {

    /** The claim key for user role. */
    private static final String CLAIM_ROLE = "role";

    /** The secret key. */
    private final SecretKey secretKey;

    /** The expiration in milliseconds. */
    private final long expirationMs;

    /**
     * Instantiates a new jwt token provider.
     *
     * @param secret       the jwt secret
     * @param expirationMs the expiration in milliseconds
     */
    public JwtTokenProvider(@Value("${jwt.secret}") final String secret,
                            @Value("${jwt.expiration-ms}") final long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * Generate token.
     *
     * @param userId the user id
     * @param role   the user role
     * @return the JWT string
     */
    public String generateToken(final Integer userId, final UserRole role) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim(CLAIM_ROLE, role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + this.expirationMs))
                .signWith(this.secretKey)
                .compact();
    }

    /**
     * Get user id from token.
     *
     * @param token the JWT token
     * @return the user id
     */
    public Integer getUserId(final String token) {
        return Integer.valueOf(this.parseClaims(token).getSubject());
    }

    /**
     * Get user role from token.
     *
     * @param token the JWT token
     * @return the user role
     */
    public UserRole getUserRole(final String token) {
        final var roleName = this.parseClaims(token).get(CLAIM_ROLE, String.class);
        return UserRole.valueOf(roleName);
    }

    /**
     * Check if token is valid.
     *
     * @param token the JWT token
     * @return true if valid
     */
    public boolean isValid(final String token) {
        try {
            this.parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Parse claims.
     *
     * @param token the JWT token
     * @return the claims
     */
    private Claims parseClaims(final String token) {
        return Jwts.parser()
                .verifyWith(this.secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
