package com.breixo.library.infrastructure.adapter.input.web.controller.reservation;

import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.domain.port.input.reservation.UpdateReservationUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PatchReservationV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchReservationV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.ReservationV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.PatchReservationRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.ReservationResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.security.AuthenticatedUserContextHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Patch Reservation Controller. */
@RestController
@RequiredArgsConstructor
public class PatchReservationController implements PatchReservationV1Api {

    /** The update reservation use case. */
    private final UpdateReservationUseCase updateReservationUseCase;

    /** The patch reservation request mapper. */
    private final PatchReservationRequestMapper patchReservationRequestMapper;

    /** The reservation response mapper. */
    private final ReservationResponseMapper reservationResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<ReservationV1Response> patchReservationV1(
            final Integer id,
            final PatchReservationV1Request patchReservationV1Request) {

        final var baseCommand =
                this.patchReservationRequestMapper.toUpdateReservationCommand(id, patchReservationV1Request);

        final var updateReservationCommand = UpdateReservationCommand.builder()
                .id(baseCommand.id())
                .loanId(baseCommand.loanId())
                .expiresAt(baseCommand.expiresAt())
                .status(baseCommand.status())
                .authenticatedUserId(AuthenticatedUserContextHelper.getAuthenticatedUserId())
                .authenticatedUserRole(AuthenticatedUserContextHelper.getAuthenticatedUserRole())
                .build();

        final var reservation = this.updateReservationUseCase.execute(updateReservationCommand);

        final var reservationV1Response = this.reservationResponseMapper.toReservationV1Response(reservation);

        return ResponseEntity.ok(reservationV1Response);
    }
}
