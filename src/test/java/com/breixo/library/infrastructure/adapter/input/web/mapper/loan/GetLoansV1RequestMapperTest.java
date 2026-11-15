package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get Loans V1 Request Mapper Test. */
class GetLoansV1RequestMapperTest {

    /** The get Loans V1 request mapper. */
    final GetLoansV1RequestMapper getLoansV1RequestMapper = new GetLoansV1RequestMapperImpl();

    /** Test mapping when AllFilters then return matching criteria. */
    @Test
    void testToLoanSearchCriteriaCommand_whenAllFilters_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var status = Instancio.create(Integer.class);

        // When
        final var loanSearchCriteriaCommand = this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(id, userId, bookId, status);

        // Then
        assertNotNull(loanSearchCriteriaCommand);
        assertEquals(id, loanSearchCriteriaCommand.getId());
        assertEquals(userId, loanSearchCriteriaCommand.getUserId());
        assertEquals(bookId, loanSearchCriteriaCommand.getBookId());
        assertEquals(status, loanSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when NoFilters then return matching criteria. */
    @Test
    void testToLoanSearchCriteriaCommand_whenNoFilters_thenReturnMatchingCriteria() {

        // Given

        // When
        final var loanSearchCriteriaCommand = this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(null, null, null, null);

        // Then
        assertNotNull(loanSearchCriteriaCommand);
        assertNull(loanSearchCriteriaCommand.getId());
        assertNull(loanSearchCriteriaCommand.getUserId());
        assertNull(loanSearchCriteriaCommand.getBookId());
        assertNull(loanSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Id then return matching criteria. */
    @Test
    void testToLoanSearchCriteriaCommand_whenId_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var loanSearchCriteriaCommand = this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(id, null, null, null);

        // Then
        assertNotNull(loanSearchCriteriaCommand);
        assertEquals(id, loanSearchCriteriaCommand.getId());
        assertNull(loanSearchCriteriaCommand.getUserId());
        assertNull(loanSearchCriteriaCommand.getBookId());
        assertNull(loanSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when UserId then return matching criteria. */
    @Test
    void testToLoanSearchCriteriaCommand_whenUserId_thenReturnMatchingCriteria() {

        // Given
        final var userId = Instancio.create(Integer.class);

        // When
        final var loanSearchCriteriaCommand = this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(null, userId, null, null);

        // Then
        assertNotNull(loanSearchCriteriaCommand);
        assertNull(loanSearchCriteriaCommand.getId());
        assertEquals(userId, loanSearchCriteriaCommand.getUserId());
        assertNull(loanSearchCriteriaCommand.getBookId());
        assertNull(loanSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when BookId then return matching criteria. */
    @Test
    void testToLoanSearchCriteriaCommand_whenBookId_thenReturnMatchingCriteria() {

        // Given
        final var bookId = Instancio.create(Integer.class);

        // When
        final var loanSearchCriteriaCommand = this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(null, null, bookId, null);

        // Then
        assertNotNull(loanSearchCriteriaCommand);
        assertNull(loanSearchCriteriaCommand.getId());
        assertNull(loanSearchCriteriaCommand.getUserId());
        assertEquals(bookId, loanSearchCriteriaCommand.getBookId());
        assertNull(loanSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Status then return matching criteria. */
    @Test
    void testToLoanSearchCriteriaCommand_whenStatus_thenReturnMatchingCriteria() {

        // Given
        final var status = Instancio.create(Integer.class);

        // When
        final var loanSearchCriteriaCommand = this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(null, null, null, status);

        // Then
        assertNotNull(loanSearchCriteriaCommand);
        assertNull(loanSearchCriteriaCommand.getId());
        assertNull(loanSearchCriteriaCommand.getUserId());
        assertNull(loanSearchCriteriaCommand.getBookId());
        assertEquals(status, loanSearchCriteriaCommand.getStatusId());
    }
}
