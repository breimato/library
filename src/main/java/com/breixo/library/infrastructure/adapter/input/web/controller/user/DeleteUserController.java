package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.port.output.user.UserDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.DeleteUserV1Api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Delete User Controller. */
@RestController
@RequiredArgsConstructor
public class DeleteUserController implements DeleteUserV1Api {

    /** The user deletion persistence port. */
    private final UserDeletionPersistencePort userDeletionPersistencePort;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<Void> deleteUserV1(final Integer id) {

        this.userDeletionPersistencePort.execute(id);

        return ResponseEntity.noContent().build();
    }
}
