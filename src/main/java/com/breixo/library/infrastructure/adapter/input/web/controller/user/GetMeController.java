package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.UserResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.security.AuthenticatedUserContextHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Me Controller. */
@RestController
@RequestMapping("/v1/library/users")
@RequiredArgsConstructor
public class GetMeController {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The user response mapper. */
    private final UserResponseMapper userResponseMapper;

    /**
     * Get the authenticated user's own profile.
     *
     * @return the authenticated user's profile
     */
    @GetMapping("/me")
    public ResponseEntity<UserV1ResponseDto> getMe() {

        final var userId = AuthenticatedUserContextHelper.getAuthenticatedUserId();

        final var user = this.userRetrievalPersistencePort.findById(userId);

        final var userV1ResponseDto = this.userResponseMapper.toUserV1Response(user);

        return ResponseEntity.ok(userV1ResponseDto);
    }
}
