package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.domain.command.reservation.CreateReservationCommand;
import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.port.output.reservation.ReservationCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.output.entities.ReservationEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.ReservationEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.ReservationMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Reservation Creation Repository. */
@Component
@RequiredArgsConstructor
public class ReservationCreationRepository implements ReservationCreationPersistencePort {

    /** The Reservation My Batis Mapper. */
    private final ReservationMyBatisMapper reservationMyBatisMapper;

    /** The Reservation Entity Mapper. */
    private final ReservationEntityMapper reservationEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Reservation execute(@Valid @NotNull final CreateReservationCommand createReservationCommand) {

        final var reservationEntity = this.insert(createReservationCommand);

        return this.find(reservationEntity.getId());
    }

    /**
     * Insert.
     *
     * @param createReservationCommand the create reservation command.
     * @return the reservation entity.
     */
    private ReservationEntity insert(final CreateReservationCommand createReservationCommand) {

        final var reservationEntity = this.reservationEntityMapper.toReservationEntity(createReservationCommand);

        this.reservationMyBatisMapper.insert(reservationEntity);

        return reservationEntity;
    }

    /**
     * Find.
     *
     * @param id the reservation identifier.
     * @return the reservation.
     */
    private Reservation find(final Integer id) {

        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().id(id).build();

        final var reservationEntity = this.reservationMyBatisMapper.find(reservationSearchCriteriaCommand).getFirst();

        return this.reservationEntityMapper.toReservation(reservationEntity);
    }
}
