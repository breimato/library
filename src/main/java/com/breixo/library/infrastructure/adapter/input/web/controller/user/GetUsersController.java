package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetUsersV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetUsersV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.UserMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Users Controller. */
@RestController
@RequiredArgsConstructor
public class GetUsersController implements GetUsersV1Api {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The user mapper. */
    private final UserMapper userMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetUsersV1ResponseDto> getUsersV1(final String name, final String email, final Integer status) {

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder()
                .name(name).email(email).statusId(status).build();

        final var users = this.userRetrievalPersistencePort.findAll(userSearchCriteriaCommand);

        final var userV1DtoList = this.userMapper.toUserV1List(users);

        final var getUsersV1ResponseDto = GetUsersV1ResponseDto.builder().users(userV1DtoList).build();

        return ResponseEntity.ok(getUsersV1ResponseDto);
    }
}
