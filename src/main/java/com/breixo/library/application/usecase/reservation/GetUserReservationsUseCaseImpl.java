package com.breixo.library.application.usecase.reservation;

import java.util.List;

import com.breixo.library.domain.command.reservation.GetUserReservationsCommand;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.reservation.GetUserReservationsUseCase;
import com.breixo.library.domain.port.input.user.AuthorizationService;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Get User Reservations Use Case Impl. */
@Component
@RequiredArgsConstructor
public class GetUserReservationsUseCaseImpl implements GetUserReservationsUseCase {

    /** The authorization service. */
    private final AuthorizationService authorizationService;

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The reservation retrieval persistence port. */
    private final ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    /** {@inheritDoc} */
    @Override
    public List<Reservation> execute(@Valid @NotNull final GetUserReservationsCommand getUserReservationsCommand) {

        this.authorizationService.requireOwnResourceOrRole(
                getUserReservationsCommand.requesterId(),
                getUserReservationsCommand.userId(),
                UserRole.MANAGER);

        this.userRetrievalPersistencePort.findById(getUserReservationsCommand.userId());

        return this.reservationRetrievalPersistencePort.findByUserId(getUserReservationsCommand.userId());
    }
}
