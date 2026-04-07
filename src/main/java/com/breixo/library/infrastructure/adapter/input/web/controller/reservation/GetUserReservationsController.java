package com.breixo.library.infrastructure.adapter.input.web.controller.reservation;

import com.breixo.library.domain.port.input.reservation.GetUserReservationsUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.GetUserReservationsV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetReservationsV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.GetUserReservationsRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.ReservationMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get User Reservations Controller. */
@RestController
@RequiredArgsConstructor
public class GetUserReservationsController implements GetUserReservationsV1Api {

    /** The get user reservations use case. */
    private final GetUserReservationsUseCase getUserReservationsUseCase;

    /** The get user reservations request mapper. */
    private final GetUserReservationsRequestMapper getUserReservationsRequestMapper;

    /** The reservation mapper. */
    private final ReservationMapper reservationMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetReservationsV1Response> getUserReservationsV1(final Integer id, final Integer xRequesterId) {

        final var getUserReservationsCommand =
                this.getUserReservationsRequestMapper.toGetUserReservationsCommand(xRequesterId, id);

        final var reservations = this.getUserReservationsUseCase.execute(getUserReservationsCommand);

        final var reservationV1List = this.reservationMapper.toReservationV1List(reservations);

        final var getReservationsV1Response =
                GetReservationsV1Response.builder().reservations(reservationV1List).build();

        return ResponseEntity.ok(getReservationsV1Response);
    }
}
