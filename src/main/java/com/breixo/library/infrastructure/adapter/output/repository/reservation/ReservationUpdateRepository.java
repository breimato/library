package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.domain.exception.ReservationException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.port.output.reservation.ReservationUpdatePersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.ReservationEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.ReservationMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Reservation Update Repository. */
@Component
@RequiredArgsConstructor
public class ReservationUpdateRepository implements ReservationUpdatePersistencePort {

    /** The Reservation My Batis Mapper. */
    private final ReservationMyBatisMapper reservationMyBatisMapper;

    /** The Reservation Entity Mapper. */
    private final ReservationEntityMapper reservationEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Reservation execute(@Valid @NotNull final UpdateReservationCommand updateReservationCommand) {
        this.update(updateReservationCommand);
        return this.find(updateReservationCommand.id());
    }

    /**
     * Update.
     *
     * @param updateReservationCommand the update reservation command.
     */
    private void update(final UpdateReservationCommand updateReservationCommand) {

        final var reservationEntity = this.reservationEntityMapper.toReservationEntity(updateReservationCommand);

        try {
            this.reservationMyBatisMapper.update(reservationEntity);

        } catch (final Exception exception) {
            throw new ReservationException(
                    ExceptionMessageConstants.RESERVATION_UPDATE_ERROR_CODE_ERROR,
                    ExceptionMessageConstants.RESERVATION_UPDATE_ERROR_MESSAGE_ERROR);
        }
    }

    /**
     * Find.
     *
     * @param id the reservation identifier.
     * @return the reservation.
     */
    private Reservation find(final Integer id) {

        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().id(id).build();

        final var reservationEntities = this.reservationMyBatisMapper.find(reservationSearchCriteriaCommand);

        return this.reservationEntityMapper.toReservation(reservationEntities.getFirst());
    }
}
