package com.breixo.library.infrastructure.adapter.output.mapper;

import java.util.List;

import com.breixo.library.domain.command.fine.CreateFineCommand;
import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.infrastructure.adapter.output.entities.FineEntity;
import com.breixo.library.infrastructure.mapper.FineStatusMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Fine Entity Mapper Test. */
@ExtendWith(MockitoExtension.class)
class FineEntityMapperTest {

    /** The fine entity mapper. */
    @InjectMocks
    FineEntityMapperImpl fineEntityMapper;

    /** The fine status mapper. */
    @Mock
    FineStatusMapper fineStatusMapper;

    /**
     * Test to fine when fine entity is valid then return mapped fine.
     */
    @Test
    void testToFine_whenFineEntityIsValid_thenReturnMappedFine() {

        // Given
        final var fineEntity = Instancio.create(FineEntity.class);
        final var fineStatus = Instancio.create(FineStatus.class);

        // When
        when(this.fineStatusMapper.toFineStatus(fineEntity.getStatusId())).thenReturn(fineStatus);
        final var fine = this.fineEntityMapper.toFine(fineEntity);

        // Then
        verify(this.fineStatusMapper, times(1)).toFineStatus(fineEntity.getStatusId());
        assertNotNull(fine);
        assertEquals(fineEntity.getId(), fine.id());
        assertEquals(fineEntity.getLoanId(), fine.loanId());
        assertEquals(fineEntity.getAmountEuros(), fine.amountEuros());
        assertEquals(fineStatus, fine.status());
        assertEquals(fineEntity.getPaidAt(), fine.paidAt());
    }

    /**
     * Test to fine when fine entity is null then return null.
     */
    @Test
    void testToFine_whenFineEntityIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.fineEntityMapper.toFine(null));
    }

    /**
     * Test to fine list when fine entities are valid then return mapped fine list.
     */
    @Test
    void testToFineList_whenFineEntitiesAreValid_thenReturnMappedFineList() {

        // Given
        final var fineEntity = Instancio.create(FineEntity.class);
        final var fineEntities = List.of(fineEntity);
        final var fineStatus = Instancio.create(FineStatus.class);

        // When
        when(this.fineStatusMapper.toFineStatus(fineEntity.getStatusId())).thenReturn(fineStatus);
        final var fines = this.fineEntityMapper.toFineList(fineEntities);

        // Then
        assertNotNull(fines);
        assertEquals(1, fines.size());
        assertEquals(fineEntity.getId(), fines.getFirst().id());
    }

    /**
     * Test to fine list when fine entities list is empty then return empty list.
     */
    @Test
    void testToFineList_whenFineEntitiesListIsEmpty_thenReturnEmptyList() {
        // When
        final var fines = this.fineEntityMapper.toFineList(List.of());

        // Then
        assertNotNull(fines);
        assertEquals(0, fines.size());
    }

    /**
     * Test to fine list when fine entities list is null then return null.
     */
    @Test
    void testToFineList_whenFineEntitiesListIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.fineEntityMapper.toFineList(null));
    }

    /**
     * Test to fine entity when create fine command is valid then return mapped fine entity.
     */
    @Test
    void testToFineEntity_whenCreateFineCommandIsValid_thenReturnMappedFineEntity() {

        // Given
        final var createFineCommand = Instancio.create(CreateFineCommand.class);

        // When
        final var fineEntity = this.fineEntityMapper.toFineEntity(createFineCommand);

        // Then
        assertNotNull(fineEntity);
        assertEquals(createFineCommand.loanId(), fineEntity.getLoanId());
        assertEquals(createFineCommand.amountEuros(), fineEntity.getAmountEuros());
        assertEquals(createFineCommand.statusId(), fineEntity.getStatusId());
    }

    /**
     * Test to fine entity when create fine command is null then return null.
     */
    @Test
    void testToFineEntity_whenCreateFineCommandIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.fineEntityMapper.toFineEntity(null));
    }
}
