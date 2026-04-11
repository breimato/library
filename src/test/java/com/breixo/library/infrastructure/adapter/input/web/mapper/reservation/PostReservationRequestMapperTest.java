package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.breixo.library.infrastructure.adapter.input.web.dto.PostReservationV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.common.DateMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Post Reservation Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PostReservationRequestMapperTest {

    /** The post reservation request mapper. */
    @InjectMocks
    PostReservationRequestMapperImpl postReservationRequestMapper;

    /** The date mapper. */
    @Mock
    DateMapper dateMapper;

    /**
     * Test to create reservation command when request is valid then return command.
     */
    @Test
    void testToCreateReservationCommand_whenRequestIsValid_thenReturnCommand() {

        // Given
        final var postReservationV1Request = Instancio.create(PostReservationV1Request.class);
        final var expiresAt = Instancio.create(LocalDateTime.class);

        // When
        when(this.dateMapper.toLocalDateTime(postReservationV1Request.getExpiresAt())).thenReturn(expiresAt);
        final var createReservationCommand = this.postReservationRequestMapper
                .toCreateReservationCommand(postReservationV1Request);

        // Then
        verify(this.dateMapper, times(1)).toLocalDateTime(postReservationV1Request.getExpiresAt());
        assertNotNull(createReservationCommand);
        assertEquals(postReservationV1Request.getUserId(), createReservationCommand.userId());
        assertEquals(postReservationV1Request.getBookId(), createReservationCommand.bookId());
        assertEquals(postReservationV1Request.getLoanId(), createReservationCommand.loanId());
        assertEquals(postReservationV1Request.getStatus(), createReservationCommand.statusId());
        assertEquals(expiresAt, createReservationCommand.expiresAt());
    }

    /**
     * Test to create reservation command when loan id is null then return command with null loan id.
     */
    @Test
    void testToCreateReservationCommand_whenLoanIdIsNull_thenReturnCommandWithNullLoanId() {

        // Given
        final var postReservationV1Request = Instancio.create(PostReservationV1Request.class);
        postReservationV1Request.setLoanId(null);
        final var expiresAt = Instancio.create(LocalDateTime.class);

        // When
        when(this.dateMapper.toLocalDateTime(postReservationV1Request.getExpiresAt())).thenReturn(expiresAt);
        final var createReservationCommand = this.postReservationRequestMapper
                .toCreateReservationCommand(postReservationV1Request);

        // Then
        verify(this.dateMapper, times(1)).toLocalDateTime(postReservationV1Request.getExpiresAt());
        assertNotNull(createReservationCommand);
        assertNull(createReservationCommand.loanId());
    }

    /**
     * Test to create reservation command when request is null then return null.
     */
    @Test
    void testToCreateReservationCommand_whenRequestIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.postReservationRequestMapper.toCreateReservationCommand(null));
    }
}
