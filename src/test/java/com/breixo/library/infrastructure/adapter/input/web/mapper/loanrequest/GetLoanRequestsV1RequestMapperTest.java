package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get LoanRequests V1 Request Mapper Test. */
class GetLoanRequestsV1RequestMapperTest {

    /** The get LoanRequests V1 request mapper. */
    final GetLoanRequestsV1RequestMapper getLoanRequestsV1RequestMapper = new GetLoanRequestsV1RequestMapperImpl();

    /** Test mapping when AllFilters then return matching criteria. */
    @Test
    void testToLoanRequestSearchCriteriaCommand_whenAllFilters_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var status = Instancio.create(Integer.class);

        // When
        final var loanRequestSearchCriteriaCommand = this.getLoanRequestsV1RequestMapper.toLoanRequestSearchCriteriaCommand(id, userId, bookId, status);

        // Then
        assertNotNull(loanRequestSearchCriteriaCommand);
        assertEquals(id, loanRequestSearchCriteriaCommand.getId());
        assertEquals(userId, loanRequestSearchCriteriaCommand.getUserId());
        assertEquals(bookId, loanRequestSearchCriteriaCommand.getBookId());
        assertEquals(status, loanRequestSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when NoFilters then return matching criteria. */
    @Test
    void testToLoanRequestSearchCriteriaCommand_whenNoFilters_thenReturnMatchingCriteria() {

        // Given

        // When
        final var loanRequestSearchCriteriaCommand = this.getLoanRequestsV1RequestMapper.toLoanRequestSearchCriteriaCommand(null, null, null, null);

        // Then
        assertNotNull(loanRequestSearchCriteriaCommand);
        assertNull(loanRequestSearchCriteriaCommand.getId());
        assertNull(loanRequestSearchCriteriaCommand.getUserId());
        assertNull(loanRequestSearchCriteriaCommand.getBookId());
        assertNull(loanRequestSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Id then return matching criteria. */
    @Test
    void testToLoanRequestSearchCriteriaCommand_whenId_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var loanRequestSearchCriteriaCommand = this.getLoanRequestsV1RequestMapper.toLoanRequestSearchCriteriaCommand(id, null, null, null);

        // Then
        assertNotNull(loanRequestSearchCriteriaCommand);
        assertEquals(id, loanRequestSearchCriteriaCommand.getId());
        assertNull(loanRequestSearchCriteriaCommand.getUserId());
        assertNull(loanRequestSearchCriteriaCommand.getBookId());
        assertNull(loanRequestSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when UserId then return matching criteria. */
    @Test
    void testToLoanRequestSearchCriteriaCommand_whenUserId_thenReturnMatchingCriteria() {

        // Given
        final var userId = Instancio.create(Integer.class);

        // When
        final var loanRequestSearchCriteriaCommand = this.getLoanRequestsV1RequestMapper.toLoanRequestSearchCriteriaCommand(null, userId, null, null);

        // Then
        assertNotNull(loanRequestSearchCriteriaCommand);
        assertNull(loanRequestSearchCriteriaCommand.getId());
        assertEquals(userId, loanRequestSearchCriteriaCommand.getUserId());
        assertNull(loanRequestSearchCriteriaCommand.getBookId());
        assertNull(loanRequestSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when BookId then return matching criteria. */
    @Test
    void testToLoanRequestSearchCriteriaCommand_whenBookId_thenReturnMatchingCriteria() {

        // Given
        final var bookId = Instancio.create(Integer.class);

        // When
        final var loanRequestSearchCriteriaCommand = this.getLoanRequestsV1RequestMapper.toLoanRequestSearchCriteriaCommand(null, null, bookId, null);

        // Then
        assertNotNull(loanRequestSearchCriteriaCommand);
        assertNull(loanRequestSearchCriteriaCommand.getId());
        assertNull(loanRequestSearchCriteriaCommand.getUserId());
        assertEquals(bookId, loanRequestSearchCriteriaCommand.getBookId());
        assertNull(loanRequestSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Status then return matching criteria. */
    @Test
    void testToLoanRequestSearchCriteriaCommand_whenStatus_thenReturnMatchingCriteria() {

        // Given
        final var status = Instancio.create(Integer.class);

        // When
        final var loanRequestSearchCriteriaCommand = this.getLoanRequestsV1RequestMapper.toLoanRequestSearchCriteriaCommand(null, null, null, status);

        // Then
        assertNotNull(loanRequestSearchCriteriaCommand);
        assertNull(loanRequestSearchCriteriaCommand.getId());
        assertNull(loanRequestSearchCriteriaCommand.getUserId());
        assertNull(loanRequestSearchCriteriaCommand.getBookId());
        assertEquals(status, loanRequestSearchCriteriaCommand.getStatusId());
    }
}
