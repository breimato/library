package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import com.breixo.library.domain.port.output.fine.FineCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.PostFineV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1Response;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostFineV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.FineResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.PostFineRequestMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/** The Class Post Fine Controller. */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class PostFineController implements PostFineV1Api {

    /** The fine creation persistence port. */
    private final FineCreationPersistencePort fineCreationPersistencePort;

    /** The post fine request mapper. */
    private final PostFineRequestMapper postFineRequestMapper;

    /** The fine response mapper. */
    private final FineResponseMapper fineResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<FineV1Response> postFineV1(final PostFineV1Request postFineV1Request) {

        final var createFineCommand = this.postFineRequestMapper.toCreateFineCommand(postFineV1Request);

        final var fine = this.fineCreationPersistencePort.execute(createFineCommand);

        final var fineV1Response = this.fineResponseMapper.toFineV1Response(fine);

        return ResponseEntity.status(HttpStatus.CREATED).body(fineV1Response);
    }
}
