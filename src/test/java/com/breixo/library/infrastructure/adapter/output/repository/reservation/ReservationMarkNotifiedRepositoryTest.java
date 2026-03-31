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

/** The Class Reservation Mark Notified Repository Test. */
@ExtendWith(MockitoExtension.class)
class ReservationMarkNotifiedRepositoryTest {

    /** The reservation mark notified repository. */
    @InjectMocks
    ReservationMarkNotifiedRepository reservationMarkNotifiedRepository;

    /** The reservation my batis mapper. */
    @Mock
    ReservationMyBatisMapper reservationMyBatisMapper;

    /**
     * Test mark notified by book id when book has pending reservations then return updated count.
     */
    @Test
    void testMarkNotifiedByBookId_whenBookHasPendingReservations_thenReturnUpdatedCount() {

        // Given
        final var bookId = Instancio.create(Integer.class);
        final var expectedCount = Instancio.create(Integer.class);

        // When
        when(this.reservationMyBatisMapper.markNotifiedByBookId(bookId)).thenReturn(expectedCount);
        final var result = this.reservationMarkNotifiedRepository.markNotifiedByBookId(bookId);

        // Then
        verify(this.reservationMyBatisMapper, times(1)).markNotifiedByBookId(bookId);
        assertEquals(expectedCount, result);
    }

    /**
     * Test mark notified by book id when no pending reservations then return zero.
     */
    @Test
    void testMarkNotifiedByBookId_whenNoPendingReservations_thenReturnZero() {

        // Given
        final var bookId = Instancio.create(Integer.class);

        // When
        when(this.reservationMyBatisMapper.markNotifiedByBookId(bookId)).thenReturn(0);
        final var result = this.reservationMarkNotifiedRepository.markNotifiedByBookId(bookId);

        // Then
        verify(this.reservationMyBatisMapper, times(1)).markNotifiedByBookId(bookId);
        assertEquals(0, result);
    }
}
