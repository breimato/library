package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetUserIdV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.UserResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get User Id Controller. */
@RestController
@RequiredArgsConstructor
public class GetUserIdController implements GetUserIdV1Api {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The user response mapper. */
    private final UserResponseMapper userResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<UserV1ResponseDto> getUserIdV1(final Long id) {

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(id).build();

        final var user = this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)
                .orElseThrow(() -> new UserException(
                        ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR));

        final var userV1ResponseDto = this.userResponseMapper.toUserV1Response(user);

        return ResponseEntity.ok(userV1ResponseDto);
    }
}
