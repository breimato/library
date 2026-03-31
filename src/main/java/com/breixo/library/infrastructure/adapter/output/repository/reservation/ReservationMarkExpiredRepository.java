package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.domain.port.output.reservation.ReservationMarkExpiredPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mybatis.ReservationMyBatisMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Reservation Mark Expired Repository. */
@Component
@RequiredArgsConstructor
public class ReservationMarkExpiredRepository implements ReservationMarkExpiredPersistencePort {

    /** The Reservation My Batis Mapper. */
    private final ReservationMyBatisMapper reservationMyBatisMapper;

    /** {@inheritDoc} */
    @Override
    public int markExpired() {
        return this.reservationMyBatisMapper.markExpired();
    }
}
