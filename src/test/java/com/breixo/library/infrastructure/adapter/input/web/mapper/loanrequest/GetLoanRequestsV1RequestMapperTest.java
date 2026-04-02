package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoanRequestsV1Request;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get Loan Requests V1 Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class GetLoanRequestsV1RequestMapperTest {

    /** The get loan requests V1 request mapper. */
    @InjectMocks
    GetLoanRequestsV1RequestMapperImpl getLoanRequestsV1RequestMapper;

    /**
     * Test to loan request search criteria command when request is valid then return mapped command.
     */
    @Test
    void testToLoanRequestSearchCriteriaCommand_whenRequestIsValid_thenReturnMappedCommand() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var status = Instancio.create(Integer.class);
        final var getLoanRequestsV1Request = Instancio.create(GetLoanRequestsV1Request.class);
        getLoanRequestsV1Request.setId(id);
        getLoanRequestsV1Request.setUserId(userId);
        getLoanRequestsV1Request.setBookId(bookId);
        getLoanRequestsV1Request.setStatus(status);

        // When
        final var loanRequestSearchCriteriaCommand =
                this.getLoanRequestsV1RequestMapper.toLoanRequestSearchCriteriaCommand(getLoanRequestsV1Request);

        // Then
        assertNotNull(loanRequestSearchCriteriaCommand);
        assertEquals(id, loanRequestSearchCriteriaCommand.getId());
        assertEquals(userId, loanRequestSearchCriteriaCommand.getUserId());
        assertEquals(bookId, loanRequestSearchCriteriaCommand.getBookId());
        assertEquals(status, loanRequestSearchCriteriaCommand.getStatusId());
    }

    /**
     * Test to loan request search criteria command when request is null then return default command.
     */
    @Test
    void testToLoanRequestSearchCriteriaCommand_whenRequestIsNull_thenReturnDefaultCommand() {

        // When
        final var loanRequestSearchCriteriaCommand =
                this.getLoanRequestsV1RequestMapper.toLoanRequestSearchCriteriaCommand(null);

        // Then
        assertNotNull(loanRequestSearchCriteriaCommand);
        assertNull(loanRequestSearchCriteriaCommand.getId());
        assertNull(loanRequestSearchCriteriaCommand.getUserId());
        assertNull(loanRequestSearchCriteriaCommand.getBookId());
        assertNull(loanRequestSearchCriteriaCommand.getStatusId());
    }
}
