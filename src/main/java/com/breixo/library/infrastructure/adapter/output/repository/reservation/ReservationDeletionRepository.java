package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.domain.port.output.reservation.ReservationDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mybatis.ReservationMyBatisMapper;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Reservation Deletion Repository. */
@Component
@RequiredArgsConstructor
public class ReservationDeletionRepository implements ReservationDeletionPersistencePort {

    /** The Reservation My Batis Mapper. */
    private final ReservationMyBatisMapper reservationMyBatisMapper;

    /** {@inheritDoc} */
    @Override
    public void execute(final Integer id) {
        this.reservationMyBatisMapper.delete(id);
    }
}
