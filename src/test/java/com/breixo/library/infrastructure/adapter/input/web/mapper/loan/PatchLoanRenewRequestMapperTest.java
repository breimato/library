package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanRenewV1Request;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Patch Loan Renew Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PatchLoanRenewRequestMapperTest {

    /** The patch loan renew request mapper. */
    @InjectMocks
    PatchLoanRenewRequestMapperImpl patchLoanRenewRequestMapper;

    /**
     * Test to update loan renew command when request is valid then return mapped command.
     */
    @Test
    void testToUpdateLoanRenewCommand_whenRequestIsValid_thenReturnMappedCommand() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var patchLoanRenewV1Request = Instancio.create(PatchLoanRenewV1Request.class);

        // When
        final var updateLoanRenewCommand = this.patchLoanRenewRequestMapper
                .toUpdateLoanRenewCommand(id, patchLoanRenewV1Request);

        // Then
        assertNotNull(updateLoanRenewCommand);
        assertEquals(id, updateLoanRenewCommand.id());
        assertEquals(patchLoanRenewV1Request.getDueDate(), updateLoanRenewCommand.dueDate());
    }

    /**
     * Test to update loan renew command when id is null then return command with null id.
     */
    @Test
    void testToUpdateLoanRenewCommand_whenIdIsNull_thenReturnCommandWithNullId() {

        // Given
        final var patchLoanRenewV1Request = Instancio.create(PatchLoanRenewV1Request.class);

        // When
        final var updateLoanRenewCommand = this.patchLoanRenewRequestMapper
                .toUpdateLoanRenewCommand(null, patchLoanRenewV1Request);

        // Then
        assertNotNull(updateLoanRenewCommand);
        assertNull(updateLoanRenewCommand.id());
        assertEquals(patchLoanRenewV1Request.getDueDate(), updateLoanRenewCommand.dueDate());
    }

    /**
     * Test to update loan renew command when request is null then return command with null due date.
     */
    @Test
    void testToUpdateLoanRenewCommand_whenRequestIsNull_thenReturnCommandWithNullDueDate() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var updateLoanRenewCommand = this.patchLoanRenewRequestMapper
                .toUpdateLoanRenewCommand(id, null);

        // Then
        assertNotNull(updateLoanRenewCommand);
        assertEquals(id, updateLoanRenewCommand.id());
        assertNull(updateLoanRenewCommand.dueDate());
    }

    /**
     * Test to update loan renew command when id and request are null then return null.
     */
    @Test
    void testToUpdateLoanRenewCommand_whenIdAndRequestAreNull_thenReturnNull() {
        // When / Then
        assertNull(this.patchLoanRenewRequestMapper.toUpdateLoanRenewCommand(null, null));
    }
}
