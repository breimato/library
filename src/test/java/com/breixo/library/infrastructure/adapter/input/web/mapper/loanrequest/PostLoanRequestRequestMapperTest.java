package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import com.breixo.library.infrastructure.adapter.input.web.dto.PostLoanRequestV1Request;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Post Loan Request Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PostLoanRequestRequestMapperTest {

    /** The post loan request request mapper. */
    @InjectMocks
    PostLoanRequestRequestMapperImpl postLoanRequestRequestMapper;

    /**
     * Test to create loan request command when request is valid then return command.
     */
    @Test
    void testToCreateLoanRequestCommand_whenRequestIsValid_thenReturnCommand() {

        // Given
        final var postLoanRequestV1Request = Instancio.create(PostLoanRequestV1Request.class);

        // When
        final var createLoanRequestCommand =
                this.postLoanRequestRequestMapper.toCreateLoanRequestCommand(postLoanRequestV1Request);

        // Then
        assertNotNull(createLoanRequestCommand);
        assertEquals(postLoanRequestV1Request.getUserId(), createLoanRequestCommand.userId());
        assertEquals(postLoanRequestV1Request.getBookId(), createLoanRequestCommand.bookId());
    }

    /**
     * Test to create loan request command when request is null then return null.
     */
    @Test
    void testToCreateLoanRequestCommand_whenRequestIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.postLoanRequestRequestMapper.toCreateLoanRequestCommand(null));
    }
}
