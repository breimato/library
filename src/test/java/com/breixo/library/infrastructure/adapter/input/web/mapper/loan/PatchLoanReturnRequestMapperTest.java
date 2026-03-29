package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanReturnV1Request;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Patch Loan Return Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PatchLoanReturnRequestMapperTest {

    /** The patch loan return request mapper. */
    @InjectMocks
    PatchLoanReturnRequestMapperImpl patchLoanReturnRequestMapper;

    /**
     * Test to update loan return command when request is valid then return mapped command.
     */
    @Test
    void testToUpdateLoanReturnCommand_whenRequestIsValid_thenReturnMappedCommand() {
        // Given
        final var id = Instancio.create(Integer.class);
        final var patchLoanReturnV1Request = Instancio.create(PatchLoanReturnV1Request.class);

        // When
        final var updateLoanReturnCommand = this.patchLoanReturnRequestMapper.toUpdateLoanReturnCommand(id, patchLoanReturnV1Request);

        // Then
        assertNotNull(updateLoanReturnCommand);
        assertEquals(id, updateLoanReturnCommand.id());
        assertEquals(patchLoanReturnV1Request.getReturnDate(), updateLoanReturnCommand.returnDate());
    }

    /**
     * Test to update loan return command when both params are null then return null.
     */
    @Test
    void testToUpdateLoanReturnCommand_whenBothParamsAreNull_thenReturnNull() {
        // When / Then
        assertNull(this.patchLoanReturnRequestMapper.toUpdateLoanReturnCommand(null, null));
    }

    /**
     * Test to update loan return command when id is null then return command with null id.
     */
    @Test
    void testToUpdateLoanReturnCommand_whenIdIsNull_thenReturnCommandWithNullId() {
        // Given
        final var patchLoanReturnV1Request = Instancio.create(PatchLoanReturnV1Request.class);

        // When
        final var updateLoanReturnCommand = this.patchLoanReturnRequestMapper.toUpdateLoanReturnCommand(null, patchLoanReturnV1Request);

        // Then
        assertNotNull(updateLoanReturnCommand);
        assertNull(updateLoanReturnCommand.id());
        assertEquals(patchLoanReturnV1Request.getReturnDate(), updateLoanReturnCommand.returnDate());
    }

    /**
     * Test to update loan return command when request is null then return command with only id.
     */
    @Test
    void testToUpdateLoanReturnCommand_whenRequestIsNull_thenReturnCommandWithOnlyId() {
        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var updateLoanReturnCommand = this.patchLoanReturnRequestMapper.toUpdateLoanReturnCommand(id, null);

        // Then
        assertNotNull(updateLoanReturnCommand);
        assertEquals(id, updateLoanReturnCommand.id());
        assertNull(updateLoanReturnCommand.returnDate());
    }
}
