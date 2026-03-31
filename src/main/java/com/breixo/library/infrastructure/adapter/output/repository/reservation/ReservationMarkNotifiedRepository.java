package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.domain.port.output.reservation.ReservationMarkNotifiedPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mybatis.ReservationMyBatisMapper;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Reservation Mark Notified Repository. */
@Component
@RequiredArgsConstructor
public class ReservationMarkNotifiedRepository implements ReservationMarkNotifiedPersistencePort {

    /** The Reservation My Batis Mapper. */
    private final ReservationMyBatisMapper reservationMyBatisMapper;

    /** {@inheritDoc} */
    @Override
    public int markNotifiedByBookId(@NotNull final Integer bookId) {
        return this.reservationMyBatisMapper.markNotifiedByBookId(bookId);
    }
}
