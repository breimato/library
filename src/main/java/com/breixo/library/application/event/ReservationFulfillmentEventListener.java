package com.breixo.library.application.event;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.domain.event.LoanCreatedDomainEvent;
import com.breixo.library.domain.model.reservation.enums.ReservationStatus;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;
import com.breixo.library.domain.port.output.reservation.ReservationUpdatePersistencePort;

import lombok.RequiredArgsConstructor;

/**
 * The Class Reservation Fulfillment Event Listener.
 */
@Component
@RequiredArgsConstructor
public class ReservationFulfillmentEventListener {

    /** The reservation retrieval persistence port. */
    private final ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    /** The reservation update persistence port. */
    private final ReservationUpdatePersistencePort reservationUpdatePersistencePort;

    /**
     * Handle loan created event.
     *
     * @param loanCreatedDomainEvent the loan created domain event
     */
    @EventListener
    public void handleLoanCreatedEvent(final LoanCreatedDomainEvent loanCreatedDomainEvent) {

        final var isPendingCompleted = this.completePendingReservation(loanCreatedDomainEvent);

        if (BooleanUtils.isFalse(isPendingCompleted)) {
            this.completeNotifiedReservation(loanCreatedDomainEvent);
        }
    }

    /**
     * Complete pending reservation.
     *
     * @param loanCreatedDomainEvent the loan created domain event
     * @return true, if pending reservation was completed
     */
    private boolean completePendingReservation(final LoanCreatedDomainEvent loanCreatedDomainEvent) {

        final var pendingList = this.reservationRetrievalPersistencePort
                .getPendingByBookId(loanCreatedDomainEvent.bookId());

        final var userPendingReservation = pendingList.stream()
                .filter(reservation -> reservation.userId().equals(loanCreatedDomainEvent.userId()))
                .findFirst();

        if (userPendingReservation.isPresent()) {

            this.completeReservation(userPendingReservation.get().id(), loanCreatedDomainEvent.loanId());

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Complete notified reservation.
     *
     * @param loanCreatedDomainEvent the loan created domain event
     */
    private void completeNotifiedReservation(final LoanCreatedDomainEvent loanCreatedDomainEvent) {

        final var notifiedList = this.reservationRetrievalPersistencePort
                .getNotifiedByBookId(loanCreatedDomainEvent.bookId());

        final var userNotifiedReservation = notifiedList.stream()
                .filter(reservation -> reservation.userId().equals(loanCreatedDomainEvent.userId()))
                .findFirst();

        userNotifiedReservation
                .ifPresent(reservation -> this.completeReservation(reservation.id(), loanCreatedDomainEvent.loanId()));
    }

    /**
     * Complete reservation.
     *
     * @param reservationId the reservation id
     * @param loanId        the loan id
     */
    private void completeReservation(final Integer reservationId, final Integer loanId) {

        final var updateReservationCommand = UpdateReservationCommand.builder()
                .id(reservationId)
                .loanId(loanId)
                .status(ReservationStatus.FULFILLED)
                .build();

        this.reservationUpdatePersistencePort.execute(updateReservationCommand);
    }
}
