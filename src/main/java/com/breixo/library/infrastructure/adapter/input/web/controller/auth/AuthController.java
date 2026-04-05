package com.breixo.library.infrastructure.adapter.input.web.controller.auth;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.AuthorizationException;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.security.JwtTokenProvider;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The Class Auth Controller. */
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The password encoder. */
    private final PasswordEncoder passwordEncoder;

    /** The jwt token provider. */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Login.
     *
     * @param loginRequest the login request
     * @return the JWT token response
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody final LoginRequest loginRequest) {

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder()
                .email(loginRequest.email())
                .build();

        final var users = this.userRetrievalPersistencePort.find(userSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(users)) {
            throw new AuthorizationException(
                    ExceptionMessageConstants.AUTH_INVALID_CREDENTIALS_CODE_ERROR,
                    ExceptionMessageConstants.AUTH_INVALID_CREDENTIALS_MESSAGE_ERROR);
        }

        final var user = users.getFirst();

        if (!this.passwordEncoder.matches(loginRequest.password(), user.passwordHash())) {
            throw new AuthorizationException(
                    ExceptionMessageConstants.AUTH_INVALID_CREDENTIALS_CODE_ERROR,
                    ExceptionMessageConstants.AUTH_INVALID_CREDENTIALS_MESSAGE_ERROR);
        }

        final var token = this.jwtTokenProvider.generateToken(user.id(), user.role());

        return ResponseEntity.ok(LoginResponse.builder().token(token).build());
    }

    /**
     * The Record Login Request.
     *
     * @param email    The email.
     * @param password The password.
     */
    public record LoginRequest(@NotBlank String email, @NotBlank String password) {}

    /**
     * The Record Login Response.
     *
     * @param token The JWT token.
     */
    @Builder
    public record LoginResponse(String token) {}
}
