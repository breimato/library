package com.breixo.library.domain.service;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.ReservationException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/** The Class Reservation Policy Validation Service Impl. */
@Service
@RequiredArgsConstructor
public class ReservationPolicyValidationServiceImpl implements ReservationPolicyValidationService {

    /** The reservation retrieval persistence port. */
    private final ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    /** {@inheritDoc} */
    @Override
    public void checkPrecedence(@NotNull final Integer userId, @NotNull final Integer bookId) {

        final var activeReservations = this.getActiveReservations(bookId);

        final var hasReservationFromOtherUser = activeReservations.stream()
                .anyMatch(reservation -> BooleanUtils.isFalse(reservation.userId().equals(userId)));

        if (hasReservationFromOtherUser) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_MESSAGE_ERROR);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void checkNoActiveReservation(@NotNull final Integer userId, @NotNull final Integer bookId) {

        final var activeReservations = this.getActiveReservations(bookId);

        final var hasActiveReservation = activeReservations.stream()
                .anyMatch(reservation -> userId.equals(reservation.userId()));

        if (hasActiveReservation) {
            throw new ReservationException(
                    ExceptionMessageConstants.RESERVATION_ALREADY_EXISTS_CODE_ERROR,
                    ExceptionMessageConstants.RESERVATION_ALREADY_EXISTS_MESSAGE_ERROR);
        }
    }

    /**
     * Get active reservations.
     *
     * @param bookId the book id
     * @return the active reservations
     */
    private Collection<Reservation> getActiveReservations(final Integer bookId) {

        final var pendingReservationList = this.reservationRetrievalPersistencePort.getPendingByBookId(bookId);
        final var notifiedReservationList = this.reservationRetrievalPersistencePort.getNotifiedByBookId(bookId);

        return CollectionUtils.union(pendingReservationList, notifiedReservationList);
    }
}
