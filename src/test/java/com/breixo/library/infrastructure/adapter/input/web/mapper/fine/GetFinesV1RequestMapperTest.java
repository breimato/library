package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get Fines V1 Request Mapper Test. */
class GetFinesV1RequestMapperTest {

    /** The get Fines V1 request mapper. */
    final GetFinesV1RequestMapper getFinesV1RequestMapper = new GetFinesV1RequestMapperImpl();

    /** Test mapping when AllFilters then return matching criteria. */
    @Test
    void testToFineSearchCriteriaCommand_whenAllFilters_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var loanId = Instancio.create(Integer.class);
        final var status = Instancio.create(Integer.class);

        // When
        final var fineSearchCriteriaCommand = this.getFinesV1RequestMapper.toFineSearchCriteriaCommand(id, loanId, status);

        // Then
        assertNotNull(fineSearchCriteriaCommand);
        assertEquals(id, fineSearchCriteriaCommand.getId());
        assertEquals(loanId, fineSearchCriteriaCommand.getLoanId());
        assertEquals(status, fineSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when NoFilters then return matching criteria. */
    @Test
    void testToFineSearchCriteriaCommand_whenNoFilters_thenReturnMatchingCriteria() {

        // Given

        // When
        final var fineSearchCriteriaCommand = this.getFinesV1RequestMapper.toFineSearchCriteriaCommand(null, null, null);

        // Then
        assertNotNull(fineSearchCriteriaCommand);
        assertNull(fineSearchCriteriaCommand.getId());
        assertNull(fineSearchCriteriaCommand.getLoanId());
        assertNull(fineSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Id then return matching criteria. */
    @Test
    void testToFineSearchCriteriaCommand_whenId_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var fineSearchCriteriaCommand = this.getFinesV1RequestMapper.toFineSearchCriteriaCommand(id, null, null);

        // Then
        assertNotNull(fineSearchCriteriaCommand);
        assertEquals(id, fineSearchCriteriaCommand.getId());
        assertNull(fineSearchCriteriaCommand.getLoanId());
        assertNull(fineSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when LoanId then return matching criteria. */
    @Test
    void testToFineSearchCriteriaCommand_whenLoanId_thenReturnMatchingCriteria() {

        // Given
        final var loanId = Instancio.create(Integer.class);

        // When
        final var fineSearchCriteriaCommand = this.getFinesV1RequestMapper.toFineSearchCriteriaCommand(null, loanId, null);

        // Then
        assertNotNull(fineSearchCriteriaCommand);
        assertNull(fineSearchCriteriaCommand.getId());
        assertEquals(loanId, fineSearchCriteriaCommand.getLoanId());
        assertNull(fineSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Status then return matching criteria. */
    @Test
    void testToFineSearchCriteriaCommand_whenStatus_thenReturnMatchingCriteria() {

        // Given
        final var status = Instancio.create(Integer.class);

        // When
        final var fineSearchCriteriaCommand = this.getFinesV1RequestMapper.toFineSearchCriteriaCommand(null, null, status);

        // Then
        assertNotNull(fineSearchCriteriaCommand);
        assertNull(fineSearchCriteriaCommand.getId());
        assertNull(fineSearchCriteriaCommand.getLoanId());
        assertEquals(status, fineSearchCriteriaCommand.getStatusId());
    }
}
