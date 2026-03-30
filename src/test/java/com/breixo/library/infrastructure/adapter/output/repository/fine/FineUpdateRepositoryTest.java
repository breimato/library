package com.breixo.library.infrastructure.adapter.output.repository.fine;

import java.util.List;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.domain.exception.FineException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.infrastructure.adapter.output.entities.FineEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.FineEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.FineMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Fine Update Repository Test. */
@ExtendWith(MockitoExtension.class)
class FineUpdateRepositoryTest {

    /** The fine update repository. */
    @InjectMocks
    FineUpdateRepository fineUpdateRepository;

    /** The fine my batis mapper. */
    @Mock
    FineMyBatisMapper fineMyBatisMapper;

    /** The fine entity mapper. */
    @Mock
    FineEntityMapper fineEntityMapper;

    /**
     * Test execute when command is valid then return updated fine.
     */
    @Test
    void testExecute_whenCommandIsValid_thenReturnUpdatedFine() {

        // Given
        final var updateFineCommand = Instancio.create(UpdateFineCommand.class);
        final var fineEntity = Instancio.create(FineEntity.class);
        final var updatedFineEntity = Instancio.create(FineEntity.class);
        final var fine = Instancio.create(Fine.class);
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(updateFineCommand.id()).build();

        // When
        when(this.fineEntityMapper.toFineEntity(updateFineCommand)).thenReturn(fineEntity);
        when(this.fineMyBatisMapper.find(fineSearchCriteriaCommand)).thenReturn(List.of(updatedFineEntity));
        when(this.fineEntityMapper.toFine(updatedFineEntity)).thenReturn(fine);
        final var result = this.fineUpdateRepository.execute(updateFineCommand);

        // Then
        verify(this.fineEntityMapper, times(1)).toFineEntity(updateFineCommand);
        verify(this.fineMyBatisMapper, times(1)).update(fineEntity);
        verify(this.fineMyBatisMapper, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineEntityMapper, times(1)).toFine(updatedFineEntity);
        assertEquals(fine, result);
    }

    /**
     * Test execute when update throws exception then throw fine exception.
     */
    @Test
    void testExecute_whenUpdateThrowsException_thenThrowFineException() {

        // Given
        final var updateFineCommand = Instancio.create(UpdateFineCommand.class);
        final var fineEntity = Instancio.create(FineEntity.class);

        // When
        when(this.fineEntityMapper.toFineEntity(updateFineCommand)).thenReturn(fineEntity);
        doThrow(new RuntimeException()).when(this.fineMyBatisMapper).update(fineEntity);
        final var fineException = assertThrows(FineException.class,
                () -> this.fineUpdateRepository.execute(updateFineCommand));

        // Then
        verify(this.fineEntityMapper, times(1)).toFineEntity(updateFineCommand);
        verify(this.fineMyBatisMapper, times(1)).update(fineEntity);
        assertEquals(ExceptionMessageConstants.FINE_UPDATE_ERROR_CODE_ERROR, fineException.getCode());
        assertEquals(ExceptionMessageConstants.FINE_UPDATE_ERROR_MESSAGE_ERROR, fineException.getMessage());
    }
}
