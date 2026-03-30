package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import com.breixo.library.infrastructure.adapter.input.web.dto.PostFineV1Request;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Post Fine Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PostFineRequestMapperTest {

    /** The post fine request mapper. */
    @InjectMocks
    PostFineRequestMapperImpl postFineRequestMapper;

    /**
     * Test to create fine command when request is valid then return command.
     */
    @Test
    void testToCreateFineCommand_whenRequestIsValid_thenReturnCommand() {

        // Given
        final var postFineV1Request = Instancio.create(PostFineV1Request.class);

        // When
        final var createFineCommand = this.postFineRequestMapper.toCreateFineCommand(postFineV1Request);

        // Then
        assertNotNull(createFineCommand);
        assertEquals(postFineV1Request.getLoanId(), createFineCommand.loanId());
        assertEquals(postFineV1Request.getAmountEuros(), createFineCommand.amountEuros().doubleValue());
        assertEquals(postFineV1Request.getStatus(), createFineCommand.statusId());
    }

    /**
     * Test to create fine command when amount euros is null then return command with null amount.
     */
    @Test
    void testToCreateFineCommand_whenAmountEurosIsNull_thenReturnCommandWithNullAmount() {

        // Given
        final var postFineV1Request = Instancio.create(PostFineV1Request.class);
        postFineV1Request.setAmountEuros(null);

        // When
        final var createFineCommand = this.postFineRequestMapper.toCreateFineCommand(postFineV1Request);

        // Then
        assertNotNull(createFineCommand);
        assertEquals(postFineV1Request.getLoanId(), createFineCommand.loanId());
        assertEquals(postFineV1Request.getStatus(), createFineCommand.statusId());
        assertNull(createFineCommand.amountEuros());
    }

    /**
     * Test to create fine command when request is null then return null.
     */
    @Test
    void testToCreateFineCommand_whenRequestIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.postFineRequestMapper.toCreateFineCommand(null));
    }
}
