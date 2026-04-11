package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import java.time.LocalDateTime;

import com.breixo.library.domain.model.reservation.enums.ReservationStatus;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchReservationV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
import com.breixo.library.infrastructure.mapper.ReservationStatusMapper;

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

/** The Class Patch Reservation Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PatchReservationRequestMapperTest {

    /** The patch reservation request mapper. */
    @InjectMocks
    PatchReservationRequestMapperImpl patchReservationRequestMapper;

    /** The reservation status mapper. */
    @Mock
    ReservationStatusMapper reservationStatusMapper;

    /** The date mapper. */
    @Mock
    DateMapper dateMapper;

    /**
     * Test to update reservation command when request is valid then return command.
     */
    @Test
    void testToUpdateReservationCommand_whenRequestIsValid_thenReturnCommand() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var patchReservationV1Request = Instancio.create(PatchReservationV1Request.class);
        final var reservationStatus = Instancio.create(ReservationStatus.class);
        final var expiresAt = Instancio.create(LocalDateTime.class);

        // When
        when(this.reservationStatusMapper.toReservationStatus(patchReservationV1Request.getStatus()))
                .thenReturn(reservationStatus);
        when(this.dateMapper.toLocalDateTime(patchReservationV1Request.getExpiresAt())).thenReturn(expiresAt);
        final var updateReservationCommand = this.patchReservationRequestMapper
                .toUpdateReservationCommand(id, patchReservationV1Request);

        // Then
        verify(this.reservationStatusMapper, times(1)).toReservationStatus(patchReservationV1Request.getStatus());
        verify(this.dateMapper, times(1)).toLocalDateTime(patchReservationV1Request.getExpiresAt());
        assertNotNull(updateReservationCommand);
        assertEquals(id, updateReservationCommand.id());
        assertEquals(patchReservationV1Request.getLoanId(), updateReservationCommand.loanId());
        assertEquals(reservationStatus, updateReservationCommand.status());
        assertEquals(expiresAt, updateReservationCommand.expiresAt());
    }

    /**
     * Test to update reservation command when request fields are null then return command with nulls.
     */
    @Test
    void testToUpdateReservationCommand_whenRequestFieldsAreNull_thenReturnCommandWithNulls() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var patchReservationV1Request = PatchReservationV1Request.builder().build();

        // When
        final var updateReservationCommand = this.patchReservationRequestMapper
                .toUpdateReservationCommand(id, patchReservationV1Request);

        // Then
        assertNotNull(updateReservationCommand);
        assertEquals(id, updateReservationCommand.id());
        assertNull(updateReservationCommand.loanId());
        assertNull(updateReservationCommand.status());
        assertNull(updateReservationCommand.expiresAt());
    }

    /**
     * Test to update reservation command when id and request are null then return null.
     */
    @Test
    void testToUpdateReservationCommand_whenIdAndRequestAreNull_thenReturnNull() {
        // When / Then
        assertNull(this.patchReservationRequestMapper.toUpdateReservationCommand(null, null));
    }

    /**
     * Test to update reservation command when request is null then return command without patch fields.
     */
    @Test
    void testToUpdateReservationCommand_whenRequestIsNull_thenReturnCommandWithoutPatchFields() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var updateReservationCommand = this.patchReservationRequestMapper
                .toUpdateReservationCommand(id, null);

        // Then
        assertNotNull(updateReservationCommand);
        assertEquals(id, updateReservationCommand.id());
        assertNull(updateReservationCommand.loanId());
        assertNull(updateReservationCommand.status());
        assertNull(updateReservationCommand.expiresAt());
    }

    /**
     * Test to update reservation command when id is null and request is not null then return command with null id.
     */
    @Test
    void testToUpdateReservationCommand_whenIdIsNullAndRequestIsNotNull_thenReturnCommandWithNullId() {

        // Given
        final var patchReservationV1Request = Instancio.create(PatchReservationV1Request.class);
        final var reservationStatus = Instancio.create(ReservationStatus.class);
        final var expiresAt = Instancio.create(LocalDateTime.class);

        // When
        when(this.reservationStatusMapper.toReservationStatus(patchReservationV1Request.getStatus()))
                .thenReturn(reservationStatus);
        when(this.dateMapper.toLocalDateTime(patchReservationV1Request.getExpiresAt())).thenReturn(expiresAt);
        final var updateReservationCommand = this.patchReservationRequestMapper
                .toUpdateReservationCommand(null, patchReservationV1Request);

        // Then
        verify(this.reservationStatusMapper, times(1)).toReservationStatus(patchReservationV1Request.getStatus());
        verify(this.dateMapper, times(1)).toLocalDateTime(patchReservationV1Request.getExpiresAt());
        assertNotNull(updateReservationCommand);
        assertNull(updateReservationCommand.id());
        assertEquals(patchReservationV1Request.getLoanId(), updateReservationCommand.loanId());
        assertEquals(reservationStatus, updateReservationCommand.status());
        assertEquals(expiresAt, updateReservationCommand.expiresAt());
    }
}
