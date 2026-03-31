package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.infrastructure.adapter.output.mybatis.ReservationMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Reservation Mark Expired Repository Test. */
@ExtendWith(MockitoExtension.class)
class ReservationMarkExpiredRepositoryTest {

    /** The reservation mark expired repository. */
    @InjectMocks
    ReservationMarkExpiredRepository reservationMarkExpiredRepository;

    /** The reservation my batis mapper. */
    @Mock
    ReservationMyBatisMapper reservationMyBatisMapper;

    /**
     * Test mark expired when reservations are expired then return updated count.
     */
    @Test
    void testMarkExpired_whenReservationsAreExpired_thenReturnUpdatedCount() {

        // Given
        final var expectedCount = Instancio.create(Integer.class);

        // When
        when(this.reservationMyBatisMapper.markExpired()).thenReturn(expectedCount);
        final var result = this.reservationMarkExpiredRepository.markExpired();

        // Then
        verify(this.reservationMyBatisMapper, times(1)).markExpired();
        assertEquals(expectedCount, result);
    }

    /**
     * Test mark expired when no reservations are expired then return zero.
     */
    @Test
    void testMarkExpired_whenNoReservationsAreExpired_thenReturnZero() {

        // Given
        final var expectedCount = 0;

        // When
        when(this.reservationMyBatisMapper.markExpired()).thenReturn(expectedCount);
        final var result = this.reservationMarkExpiredRepository.markExpired();

        // Then
        verify(this.reservationMyBatisMapper, times(1)).markExpired();
        assertEquals(expectedCount, result);
    }
}
