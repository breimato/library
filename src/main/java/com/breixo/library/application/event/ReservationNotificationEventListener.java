package com.breixo.library.application.event;

import com.breixo.library.domain.event.LoanReturnedDomainEvent;
import com.breixo.library.domain.port.output.reservation.ReservationMarkNotifiedPersistencePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/** The Class Reservation Notification Event Listener. */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationNotificationEventListener {

    /** The reservation mark notified persistence port. */
    private final ReservationMarkNotifiedPersistencePort reservationMarkNotifiedPersistencePort;

    /**
     * Handle loan returned event.
     *
     * @param loanReturnedDomainEvent the loan returned domain event
     */
    @EventListener
    public void handleLoanReturnedEvent(final LoanReturnedDomainEvent loanReturnedDomainEvent) {

        final var notifiedCount = this.reservationMarkNotifiedPersistencePort
                .markNotifiedByBookId(loanReturnedDomainEvent.bookId());

        log.info("Reservations notified for book {}: {}", loanReturnedDomainEvent.bookId(), notifiedCount);
    }
}
