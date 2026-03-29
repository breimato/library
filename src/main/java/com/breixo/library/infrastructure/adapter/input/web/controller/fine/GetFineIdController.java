package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.exception.FineException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetFineIdV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.FineResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Fine Id Controller. */
@RestController
@RequiredArgsConstructor
public class GetFineIdController implements GetFineIdV1Api {

    /** The Fine Retrieval Persistence Port. */
    private final FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The Fine Response Mapper. */
    private final FineResponseMapper fineResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<FineV1Response> getFineIdV1(final Integer id) {

        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(id).build();

        final var fine = this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)
                .orElseThrow(() -> new FineException(
                        ExceptionMessageConstants.FINE_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.FINE_NOT_FOUND_MESSAGE_ERROR));

        final var fineV1Response = this.fineResponseMapper.toFineV1Response(fine);

        return ResponseEntity.ok(fineV1Response);
    }
}
