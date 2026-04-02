package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanRequestV1Request;
import com.breixo.library.infrastructure.mapper.LoanRequestStatusMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

/** The Class Patch Loan Request Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PatchLoanRequestRequestMapperTest {

    /** The patch loan request request mapper. */
    @InjectMocks
    PatchLoanRequestRequestMapperImpl patchLoanRequestRequestMapper;

    /** The loan request status mapper. */
    @Mock
    LoanRequestStatusMapper loanRequestStatusMapper;

    /**
     * Test to update loan request command when request is valid then return command.
     */
    @Test
    void testToUpdateLoanRequestCommand_whenRequestIsValid_thenReturnCommand() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var patchLoanRequestV1Request = Instancio.create(PatchLoanRequestV1Request.class);
        final var loanRequestStatus = Instancio.create(LoanRequestStatus.class);

        // When
        when(this.loanRequestStatusMapper.toLoanRequestStatus(patchLoanRequestV1Request.getStatus()))
                .thenReturn(loanRequestStatus);
        final var updateLoanRequestCommand =
                this.patchLoanRequestRequestMapper.toUpdateLoanRequestCommand(id, patchLoanRequestV1Request);

        // Then
        assertNotNull(updateLoanRequestCommand);
        assertEquals(id, updateLoanRequestCommand.id());
        assertEquals(loanRequestStatus, updateLoanRequestCommand.status());
        assertEquals(patchLoanRequestV1Request.getRejectionReason(), updateLoanRequestCommand.rejectionReason());
    }

    /**
     * Test to update loan request command when both params are null then return null.
     */
    @Test
    void testToUpdateLoanRequestCommand_whenBothParamsAreNull_thenReturnNull() {
        // When / Then
        assertNull(this.patchLoanRequestRequestMapper.toUpdateLoanRequestCommand(null, null));
    }

    /**
     * Test to update loan request command when request is null then return command with only id.
     */
    @Test
    void testToUpdateLoanRequestCommand_whenRequestIsNull_thenReturnCommandWithOnlyId() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var updateLoanRequestCommand =
                this.patchLoanRequestRequestMapper.toUpdateLoanRequestCommand(id, null);

        // Then
        assertNotNull(updateLoanRequestCommand);
        assertEquals(id, updateLoanRequestCommand.id());
        assertNull(updateLoanRequestCommand.status());
        assertNull(updateLoanRequestCommand.rejectionReason());
    }
}
