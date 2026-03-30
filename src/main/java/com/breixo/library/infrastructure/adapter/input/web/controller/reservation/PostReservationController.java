package com.breixo.library.infrastructure.adapter.input.web.controller.reservation;

import com.breixo.library.domain.port.output.reservation.ReservationCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.PostReservationV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostReservationV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.ReservationV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.PostReservationRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.ReservationResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Post Reservation Controller. */
@RestController
@RequiredArgsConstructor
public class PostReservationController implements PostReservationV1Api {

    /** The reservation creation persistence port. */
    private final ReservationCreationPersistencePort reservationCreationPersistencePort;

    /** The post reservation request mapper. */
    private final PostReservationRequestMapper postReservationRequestMapper;

    /** The reservation response mapper. */
    private final ReservationResponseMapper reservationResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<ReservationV1Response> postReservationV1(final PostReservationV1Request postReservationV1Request) {

        final var createReservationCommand = this.postReservationRequestMapper
                .toCreateReservationCommand(postReservationV1Request);

        final var reservation = this.reservationCreationPersistencePort.execute(createReservationCommand);

        final var reservationV1Response = this.reservationResponseMapper.toReservationV1Response(reservation);

        return ResponseEntity.status(HttpStatus.CREATED).body(reservationV1Response);
    }
}
