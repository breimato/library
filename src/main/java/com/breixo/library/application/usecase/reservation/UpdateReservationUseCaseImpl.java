package com.breixo.library.application.usecase.reservation;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.domain.exception.ReservationException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.port.input.reservation.UpdateReservationUseCase;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;
import com.breixo.library.domain.port.output.reservation.ReservationUpdatePersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** The Class Update Reservation Use Case Impl. */
@Component
@RequiredArgsConstructor
public class UpdateReservationUseCaseImpl implements UpdateReservationUseCase {

    /** The reservation retrieval persistence port. */
    private final ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    /** The reservation update persistence port. */
    private final ReservationUpdatePersistencePort reservationUpdatePersistencePort;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Reservation execute(@Valid @NotNull final UpdateReservationCommand updateReservationCommand) {

        this.validateReservationExists(updateReservationCommand);

        return this.reservationUpdatePersistencePort.execute(updateReservationCommand);
    }

    /**
     * Validate reservation exists.
     *
     * @param updateReservationCommand the update reservation command
     */
    private void validateReservationExists(final UpdateReservationCommand updateReservationCommand) {

        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder()
                .id(updateReservationCommand.id())
                .build();

        final var reservations = this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(reservations)) {
            throw new ReservationException(
                    ExceptionMessageConstants.RESERVATION_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.RESERVATION_NOT_FOUND_MESSAGE_ERROR);
        }
    }
}
