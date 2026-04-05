package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.port.input.user.CreateUserUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PostUserV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostUserV1RequestDto;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.PostUserRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.UserResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

/** The Class Post User Controller. */
@RestController
@RequiredArgsConstructor
public class PostUserController implements PostUserV1Api {

    /** The create user use case. */
    private final CreateUserUseCase createUserUseCase;

    /** The post user request mapper. */
    private final PostUserRequestMapper postUserRequestMapper;

    /** The user response mapper. */
    private final UserResponseMapper userResponseMapper;

    /** The password encoder. */
    private final PasswordEncoder passwordEncoder;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<UserV1ResponseDto> postUserV1(final PostUserV1RequestDto postUserV1RequestDto) {

        final var baseCommand = this.postUserRequestMapper.toCreateUserCommand(postUserV1RequestDto);

        final var createUserCommand = CreateUserCommand.builder()
                .name(baseCommand.name())
                .email(baseCommand.email())
                .phone(baseCommand.phone())
                .role(baseCommand.role())
                .passwordHash(this.passwordEncoder.encode(postUserV1RequestDto.getPassword()))
                .build();

        final var user = this.createUserUseCase.execute(createUserCommand);

        final var userV1ResponseDto = this.userResponseMapper.toUserV1Response(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userV1ResponseDto);
    }
}
