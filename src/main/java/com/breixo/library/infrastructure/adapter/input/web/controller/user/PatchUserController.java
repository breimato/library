package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.port.input.user.UpdateUserUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PatchUserV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchUserV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchUserV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.PatchUserRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.PatchUserResponseMapper;

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

    /** The patch user response mapper. */
    private final PatchUserResponseMapper patchUserResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<PatchUserV1Response> patchUserV1(final Long id, final PatchUserV1Request patchUserV1RequestDto) {

        final var updateUserCommand = this.patchUserRequestMapper.toUpdateUserCommand(id, patchUserV1RequestDto);

        final var user = this.updateUserUseCase.execute(updateUserCommand);

        final var patchUserV1ResponseDto = this.patchUserResponseMapper.toPatchUserV1Response(user);

        return ResponseEntity.ok(patchUserV1ResponseDto);
    }
}
