package com.breixo.library.infrastructure.adapter.input.web.controller.reservation;

import com.breixo.library.domain.port.output.reservation.ReservationDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.DeleteReservationV1Api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/** The Class Delete Reservation Controller. */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class DeleteReservationController implements DeleteReservationV1Api {

    /** The Reservation Deletion Persistence Port. */
    private final ReservationDeletionPersistencePort reservationDeletionPersistencePort;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<Void> deleteReservationV1(final Integer id) {

        this.reservationDeletionPersistencePort.execute(id);

        return ResponseEntity.noContent().build();
    }
}
