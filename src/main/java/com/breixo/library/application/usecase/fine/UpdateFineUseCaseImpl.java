package com.breixo.library.application.usecase.fine;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.domain.exception.FineException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.port.input.fine.UpdateFineUseCase;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.domain.port.output.fine.FineUpdatePersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

/** The Class Update Fine Use Case Impl. */
@Component
@RequiredArgsConstructor
public class UpdateFineUseCaseImpl implements UpdateFineUseCase {

    /** The fine retrieval persistence port. */
    private final FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The fine update persistence port. */
    private final FineUpdatePersistencePort fineUpdatePersistencePort;

    /** {@inheritDoc} */
    @Override
    public Fine execute(@Valid @NotNull final UpdateFineCommand updateFineCommand) {

        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(updateFineCommand.id()).build();
        final var fines = this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(fines)) {
            throw new FineException(
                    ExceptionMessageConstants.FINE_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.FINE_NOT_FOUND_MESSAGE_ERROR);
        }

        return this.fineUpdatePersistencePort.execute(updateFineCommand);
    }
}
