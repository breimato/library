package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.port.input.user.UpdateUserUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PatchUserV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchUserV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.PatchUserRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.UserResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Patch User Controller. */
@RestController
@RequiredArgsConstructor
public class PatchUserController implements PatchUserV1Api {

    /** The update user use case. */
    private final UpdateUserUseCase updateUserUseCase;

    /** The patch user request mapper. */
    private final PatchUserRequestMapper patchUserRequestMapper;

    /** The user response mapper. */
    private final UserResponseMapper userResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<UserV1ResponseDto> patchUserV1(final Integer id, final PatchUserV1Request patchUserV1RequestDto) {

        final var updateUserCommand = this.patchUserRequestMapper.toUpdateUserCommand(id, patchUserV1RequestDto);

        final var user = this.updateUserUseCase.execute(updateUserCommand);

        final var userV1ResponseDto = this.userResponseMapper.toUserV1Response(user);

        return ResponseEntity.ok(userV1ResponseDto);
    }
}
