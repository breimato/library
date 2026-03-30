package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import java.util.List;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.model.reservation.enums.ReservationStatus;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.ReservationEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.ReservationMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Reservation Retrieval Repository. */
@Component
@RequiredArgsConstructor
public class ReservationRetrievalRepository implements ReservationRetrievalPersistencePort {

    /** The Reservation My Batis Mapper. */
    private final ReservationMyBatisMapper reservationMyBatisMapper;

    /** The Reservation Entity Mapper. */
    private final ReservationEntityMapper reservationEntityMapper;

    /** {@inheritDoc} */
    @Override
    public List<Reservation> getPendingByBookId(@NotNull final Integer bookId) {

        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder()
                .bookId(bookId)
                .statusId(ReservationStatus.PENDING.getId())
                .build();

        return this.find(reservationSearchCriteriaCommand);
    }

    /** {@inheritDoc} */
    @Override
    public List<Reservation> getNotifiedByBookId(@NotNull final Integer bookId) {

        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder()
                .bookId(bookId)
                .statusId(ReservationStatus.NOTIFIED.getId())
                .build();

        return this.find(reservationSearchCriteriaCommand);
    }

    /** {@inheritDoc} */
    @Override
    public List<Reservation> find(@Valid @NotNull final ReservationSearchCriteriaCommand reservationSearchCriteriaCommand) {

        final var reservationEntities = this.reservationMyBatisMapper.find(reservationSearchCriteriaCommand);

        return this.reservationEntityMapper.toReservationList(reservationEntities);
    }
}
