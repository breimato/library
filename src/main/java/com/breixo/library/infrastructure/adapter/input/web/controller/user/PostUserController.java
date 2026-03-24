package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.port.input.user.CreateUserUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PostUserV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostUserV1RequestDto;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostUserV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.PostUserRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.PostUserResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Post User Controller. */
@RestController
@RequiredArgsConstructor
public class PostUserController implements PostUserV1Api {

    /** The create user use case. */
    private final CreateUserUseCase createUserUseCase;

    /** The post user request mapper. */
    private final PostUserRequestMapper postUserRequestMapper;

    /** The post user response mapper. */
    private final PostUserResponseMapper postUserResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<PostUserV1ResponseDto> postUserV1(final PostUserV1RequestDto postUserV1RequestDto) {

        final var createUserCommand = this.postUserRequestMapper.toCreateUserCommand(postUserV1RequestDto);

        final var user = this.createUserUseCase.execute(createUserCommand);

        final var postUserV1ResponseDto = this.postUserResponseMapper.toPostUserV1Response(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(postUserV1ResponseDto);
    }
}
