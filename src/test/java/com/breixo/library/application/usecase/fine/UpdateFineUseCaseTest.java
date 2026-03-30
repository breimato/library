package com.breixo.library.application.usecase.fine;

import java.util.List;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.domain.exception.FineException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.domain.port.output.fine.FineUpdatePersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Update Fine Use Case Test. */
@ExtendWith(MockitoExtension.class)
class UpdateFineUseCaseTest {

    /** The update fine use case. */
    @InjectMocks
    UpdateFineUseCaseImpl updateFineUseCase;

    /** The fine retrieval persistence port. */
    @Mock
    FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The fine update persistence port. */
    @Mock
    FineUpdatePersistencePort fineUpdatePersistencePort;

    /**
     * Test execute when fine exists then update and return fine.
     */
    @Test
    void testExecute_whenFineExists_thenUpdateAndReturnFine() {

        // Given
        final var updateFineCommand = Instancio.create(UpdateFineCommand.class);
        final var existingFine = Instancio.create(Fine.class);
        final var updatedFine = Instancio.create(Fine.class);
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(updateFineCommand.id()).build();

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(List.of(existingFine));
        when(this.fineUpdatePersistencePort.execute(updateFineCommand)).thenReturn(updatedFine);
        final var result = this.updateFineUseCase.execute(updateFineCommand);

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineUpdatePersistencePort, times(1)).execute(updateFineCommand);
        assertEquals(updatedFine, result);
    }

    /**
     * Test execute when fine not found then throw fine exception.
     */
    @Test
    void testExecute_whenFineNotFound_thenThrowFineException() {

        // Given
        final var updateFineCommand = Instancio.create(UpdateFineCommand.class);
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(updateFineCommand.id()).build();

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(List.of());
        final var exception = assertThrows(FineException.class,
                () -> this.updateFineUseCase.execute(updateFineCommand));

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineUpdatePersistencePort, times(0)).execute(updateFineCommand);
        assertEquals(ExceptionMessageConstants.FINE_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
    }
}
