package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import com.breixo.library.domain.port.output.fine.FineDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.DeleteFineV1Api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Delete Fine Controller. */
@RestController
@RequiredArgsConstructor
public class DeleteFineController implements DeleteFineV1Api {

    /** The Fine Deletion Persistence Port. */
    private final FineDeletionPersistencePort fineDeletionPersistencePort;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<Void> deleteFineV1(final Integer id) {

        this.fineDeletionPersistencePort.execute(id);

        return ResponseEntity.noContent().build();
    }
}
