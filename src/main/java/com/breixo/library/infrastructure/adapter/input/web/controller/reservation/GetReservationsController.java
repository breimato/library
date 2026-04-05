package com.breixo.library.infrastructure.adapter.input.web.controller.reservation;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetReservationsV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetReservationsV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetReservationsV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.GetReservationsV1RequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.ReservationMapper;
import com.breixo.library.infrastructure.adapter.input.web.security.AuthenticatedUserContextHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Reservations Controller. */
@RestController
@RequiredArgsConstructor
public class GetReservationsController implements GetReservationsV1Api {

    /** The reservation retrieval persistence port. */
    private final ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    /** The get reservations V1 request mapper. */
    private final GetReservationsV1RequestMapper getReservationsV1RequestMapper;

    /** The reservation mapper. */
    private final ReservationMapper reservationMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetReservationsV1Response> getReservationsV1(final GetReservationsV1Request getReservationsV1Request) {

        var reservationSearchCriteriaCommand =
                this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(getReservationsV1Request);

        if (UserRole.NORMAL.equals(AuthenticatedUserContextHelper.getAuthenticatedUserRole())) {
            reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder()
                    .id(reservationSearchCriteriaCommand.getId())
                    .bookId(reservationSearchCriteriaCommand.getBookId())
                    .loanId(reservationSearchCriteriaCommand.getLoanId())
                    .statusId(reservationSearchCriteriaCommand.getStatusId())
                    .userId(AuthenticatedUserContextHelper.getAuthenticatedUserId())
                    .build();
        }

        final var reservations = this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand);

        final var reservationV1DtoList = this.reservationMapper.toReservationV1List(reservations);

        final var getReservationsV1Response =
                GetReservationsV1Response.builder().reservations(reservationV1DtoList).build();

        return ResponseEntity.ok(getReservationsV1Response);
    }
}
