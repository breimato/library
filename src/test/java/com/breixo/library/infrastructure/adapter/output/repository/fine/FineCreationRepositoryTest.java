package com.breixo.library.infrastructure.adapter.output.repository.fine;

import java.util.List;

import com.breixo.library.domain.command.fine.CreateFineCommand;
import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
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

/** The Class Fine Creation Repository Test. */
@ExtendWith(MockitoExtension.class)
class FineCreationRepositoryTest {

    /** The Fine Creation Repository. */
    @InjectMocks
    FineCreationRepository fineCreationRepository;

    /** The Fine My Batis Mapper. */
    @Mock
    FineMyBatisMapper fineMyBatisMapper;

    /** The Fine Entity Mapper. */
    @Mock
    FineEntityMapper fineEntityMapper;

    /**
     * Test execute when command is valid then return created fine.
     */
    @Test
    void testExecute_whenCommandIsValid_thenReturnCreatedFine() {

        // Given
        final var createFineCommand = Instancio.create(CreateFineCommand.class);
        final var fineEntity = Instancio.create(FineEntity.class);
        final var createdFineEntity = Instancio.create(FineEntity.class);
        final var fine = Instancio.create(Fine.class);
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(fineEntity.getId()).build();

        // When
        when(this.fineEntityMapper.toFineEntity(createFineCommand)).thenReturn(fineEntity);
        when(this.fineMyBatisMapper.find(fineSearchCriteriaCommand)).thenReturn(List.of(createdFineEntity));
        when(this.fineEntityMapper.toFine(createdFineEntity)).thenReturn(fine);
        final var result = this.fineCreationRepository.execute(createFineCommand);

        // Then
        verify(this.fineEntityMapper, times(1)).toFineEntity(createFineCommand);
        verify(this.fineMyBatisMapper, times(1)).insert(fineEntity);
        verify(this.fineMyBatisMapper, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineEntityMapper, times(1)).toFine(createdFineEntity);
        assertEquals(fine, result);
    }

    /**
     * Test execute when insert throws exception then throw fine exception.
     */
    @Test
    void testExecute_whenInsertThrowsException_thenThrowFineException() {

        // Given
        final var createFineCommand = Instancio.create(CreateFineCommand.class);
        final var fineEntity = Instancio.create(FineEntity.class);

        // When
        when(this.fineEntityMapper.toFineEntity(createFineCommand)).thenReturn(fineEntity);
        doThrow(new RuntimeException()).when(this.fineMyBatisMapper).insert(fineEntity);
        final var fineException = assertThrows(FineException.class,
                () -> this.fineCreationRepository.execute(createFineCommand));

        // Then
        verify(this.fineEntityMapper, times(1)).toFineEntity(createFineCommand);
        verify(this.fineMyBatisMapper, times(1)).insert(fineEntity);
        assertEquals(ExceptionMessageConstants.FINE_CREATION_ERROR_CODE_ERROR, fineException.getCode());
        assertEquals(ExceptionMessageConstants.FINE_CREATION_ERROR_MESSAGE_ERROR, fineException.getMessage());
    }
}
