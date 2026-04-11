package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.infrastructure.adapter.input.web.dto.ReservationV1;
import com.breixo.library.infrastructure.adapter.input.web.dto.ReservationV1Response;

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

/** The Class Reservation Response Mapper Test. */
@ExtendWith(MockitoExtension.class)
class ReservationResponseMapperTest {

    /** The reservation response mapper. */
    @InjectMocks
    ReservationResponseMapperImpl reservationResponseMapper;

    /** The reservation mapper. */
    @Mock
    ReservationMapper reservationMapper;

    /**
     * Test to reservation V1 response when reservation is valid then return mapped response.
     */
    @Test
    void testToReservationV1Response_whenReservationIsValid_thenReturnMappedResponse() {

        // Given
        final var reservation = Instancio.create(Reservation.class);
        final var reservationV1 = Instancio.create(ReservationV1.class);

        // When
        when(this.reservationMapper.toReservationV1(reservation)).thenReturn(reservationV1);
        final var reservationV1Response = this.reservationResponseMapper.toReservationV1Response(reservation);

        // Then
        verify(this.reservationMapper, times(1)).toReservationV1(reservation);
        assertNotNull(reservationV1Response);
        assertEquals(reservationV1, reservationV1Response.getReservation());
    }

    /**
     * Test to reservation V1 response when reservation is null then return null.
     */
    @Test
    void testToReservationV1Response_whenReservationIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.reservationResponseMapper.toReservationV1Response(null));
    }
}
