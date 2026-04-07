package com.breixo.library.infrastructure.adapter.output.repository.fine;

import java.util.List;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Fine Retrieval Repository Test. */
@ExtendWith(MockitoExtension.class)
class FineRetrievalRepositoryTest {

    /** The Fine Retrieval Repository. */
    @InjectMocks
    FineRetrievalRepository fineRetrievalRepository;

    /** The Fine My Batis Mapper. */
    @Mock
    FineMyBatisMapper fineMyBatisMapper;

    /** The Fine Entity Mapper. */
    @Mock
    FineEntityMapper fineEntityMapper;

    /**
     * Test find when fine found then return fine list.
     */
    @Test
    void testFind_whenFineFound_thenReturnFineList() {

        // Given
        final var fineSearchCriteriaCommand = Instancio.create(FineSearchCriteriaCommand.class);
        final var fineEntity = Instancio.create(FineEntity.class);
        final var fine = Instancio.create(Fine.class);

        // When
        when(this.fineMyBatisMapper.find(fineSearchCriteriaCommand)).thenReturn(List.of(fineEntity));
        when(this.fineEntityMapper.toFineList(List.of(fineEntity))).thenReturn(List.of(fine));
        final var result = this.fineRetrievalRepository.find(fineSearchCriteriaCommand);

        // Then
        verify(this.fineMyBatisMapper, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineEntityMapper, times(1)).toFineList(List.of(fineEntity));
        assertEquals(List.of(fine), result);
    }

    /**
     * Test find when fine not found then return empty list.
     */
    @Test
    void testFind_whenFineNotFound_thenReturnEmptyList() {

        // Given
        final var fineSearchCriteriaCommand = Instancio.create(FineSearchCriteriaCommand.class);

        // When
        when(this.fineMyBatisMapper.find(fineSearchCriteriaCommand)).thenReturn(List.of());
        when(this.fineEntityMapper.toFineList(List.of())).thenReturn(List.of());
        final var result = this.fineRetrievalRepository.find(fineSearchCriteriaCommand);

        // Then
        verify(this.fineMyBatisMapper, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineEntityMapper, times(1)).toFineList(List.of());
        assertTrue(result.isEmpty());
    }

    /**
     * Test find by user id when fines exist then return fines.
     */
    @Test
    void testFindByUserId_whenFinesExist_thenReturnFines() {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().userId(userId).build();
        final var fineEntity = Instancio.create(FineEntity.class);
        final var fine = Instancio.create(Fine.class);

        // When
        when(this.fineMyBatisMapper.find(fineSearchCriteriaCommand)).thenReturn(List.of(fineEntity));
        when(this.fineEntityMapper.toFineList(List.of(fineEntity))).thenReturn(List.of(fine));
        final var result = this.fineRetrievalRepository.findByUserId(userId);

        // Then
        verify(this.fineMyBatisMapper, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineEntityMapper, times(1)).toFineList(List.of(fineEntity));
        assertEquals(List.of(fine), result);
    }
}
