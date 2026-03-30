package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.infrastructure.adapter.output.mybatis.ReservationMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/** The Class Reservation Deletion Repository Test. */
@ExtendWith(MockitoExtension.class)
class ReservationDeletionRepositoryTest {

    /** The reservation deletion repository. */
    @InjectMocks
    ReservationDeletionRepository reservationDeletionRepository;

    /** The reservation my batis mapper. */
    @Mock
    ReservationMyBatisMapper reservationMyBatisMapper;

    /**
     * Test execute when id is valid then delete reservation.
     */
    @Test
    void testExecute_whenIdIsValid_thenDeleteReservation() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        this.reservationDeletionRepository.execute(id);

        // Then
        verify(this.reservationMyBatisMapper, times(1)).delete(id);
    }
}
