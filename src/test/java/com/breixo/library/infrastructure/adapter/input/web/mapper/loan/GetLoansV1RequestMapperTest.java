package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoansV1Request;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get Loans V1 Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class GetLoansV1RequestMapperTest {

    /** The get loans V1 request mapper. */
    @InjectMocks
    GetLoansV1RequestMapperImpl getLoansV1RequestMapper;

    /**
     * Test to loan search criteria command when request is valid then return mapped command.
     */
    @Test
    void testToLoanSearchCriteriaCommand_whenRequestIsValid_thenReturnMappedCommand() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var status = Instancio.create(Integer.class);
        final var getLoansV1Request = Instancio.create(GetLoansV1Request.class);
        getLoansV1Request.setId(id);
        getLoansV1Request.setUserId(userId);
        getLoansV1Request.setBookId(bookId);
        getLoansV1Request.setStatus(status);

        // When
        final var loanSearchCriteriaCommand = this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(getLoansV1Request);

        // Then
        assertNotNull(loanSearchCriteriaCommand);
        assertEquals(id, loanSearchCriteriaCommand.getId());
        assertEquals(userId, loanSearchCriteriaCommand.getUserId());
        assertEquals(bookId, loanSearchCriteriaCommand.getBookId());
        assertEquals(status, loanSearchCriteriaCommand.getStatusId());
    }

    /**
     * Test to loan search criteria command when request is null then return default command.
     */
    @Test
    void testToLoanSearchCriteriaCommand_whenRequestIsNull_thenReturnDefaultCommand() {

        // When
        final var loanSearchCriteriaCommand = this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(null);

        // Then
        assertNotNull(loanSearchCriteriaCommand);
        assertNull(loanSearchCriteriaCommand.getId());
        assertNull(loanSearchCriteriaCommand.getUserId());
        assertNull(loanSearchCriteriaCommand.getBookId());
        assertNull(loanSearchCriteriaCommand.getStatusId());
    }
}
