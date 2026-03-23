package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetUserIdV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetUserIdV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.GetUserResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get User Id Controller. */
@RestController
@RequiredArgsConstructor
public class GetUserIdController implements GetUserIdV1Api {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The get user response mapper. */
    private final GetUserResponseMapper getUserResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetUserIdV1Response> getUserIdV1(final Long id) {

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(id).build();

        final var user = this.userRetrievalPersistencePort.execute(userSearchCriteriaCommand);

        final var getUserIdV1Response = this.getUserResponseMapper.toGetUserIdV1Response(user);

        return ResponseEntity.ok(getUserIdV1Response);
    }
}
