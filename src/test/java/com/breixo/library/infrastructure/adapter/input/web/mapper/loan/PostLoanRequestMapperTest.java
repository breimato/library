package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.infrastructure.adapter.input.web.dto.PostLoanV1Request;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Post Loan Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PostLoanRequestMapperTest {

    /** The post loan request mapper. */
    @InjectMocks
    PostLoanRequestMapperImpl postLoanRequestMapper;

    /**
     * Test to create loan command when request is valid then return mapped command.
     */
    @Test
    void testToCreateLoanCommand_whenRequestIsValid_thenReturnMappedCommand() {

        // Given
        final var postLoanV1Request = Instancio.create(PostLoanV1Request.class);

        // When
        final var createLoanCommand = this.postLoanRequestMapper.toCreateLoanCommand(postLoanV1Request);

        // Then
        assertNotNull(createLoanCommand);
        assertEquals(postLoanV1Request.getUserId(), createLoanCommand.userId());
        assertEquals(postLoanV1Request.getBookId(), createLoanCommand.bookId());
        assertEquals(postLoanV1Request.getDueDate(), createLoanCommand.dueDate());
    }

    /**
     * Test to create loan command when request is null then return null.
     */
    @Test
    void testToCreateLoanCommand_whenRequestIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.postLoanRequestMapper.toCreateLoanCommand(null));
    }
}
