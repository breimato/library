package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import com.breixo.library.domain.port.input.fine.UpdateFineUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PatchFineV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1Response;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchFineV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.FineResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.PatchFineRequestMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Patch Fine Controller. */
@RestController
@RequiredArgsConstructor
public class PatchFineController implements PatchFineV1Api {

    /** The update fine use case. */
    private final UpdateFineUseCase updateFineUseCase;

    /** The patch fine request mapper. */
    private final PatchFineRequestMapper patchFineRequestMapper;

    /** The fine response mapper. */
    private final FineResponseMapper fineResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<FineV1Response> patchFineV1(final Integer id, final PatchFineV1Request patchFineV1Request) {

        final var updateFineCommand = this.patchFineRequestMapper.toUpdateFineCommand(id, patchFineV1Request);

        final var fine = this.updateFineUseCase.execute(updateFineCommand);

        final var fineV1Response = this.fineResponseMapper.toFineV1Response(fine);

        return ResponseEntity.ok(fineV1Response);
    }
}
