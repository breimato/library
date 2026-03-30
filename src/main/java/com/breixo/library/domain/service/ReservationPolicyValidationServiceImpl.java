package com.breixo.library.domain.service;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

/** The Class Reservation Policy Validation Service Impl. */
@Service
@RequiredArgsConstructor
public class ReservationPolicyValidationServiceImpl implements ReservationPolicyValidationService {

    /** The reservation retrieval persistence port. */
    private final ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    /** {@inheritDoc} */
    @Override
    public void checkReservationPrecedence(@NotNull final Integer userId, @NotNull final Integer bookId) {
        this.validatePendingReservationPrecedence(userId, bookId);
        this.validateNotifiedReservationPrecedence(userId, bookId);
    }

    /**
     * Validate pending reservation precedence.
     *
     * @param userId the user id
     * @param bookId the book id
     */
    private void validatePendingReservationPrecedence(final Integer userId, final Integer bookId) {

        final var pendingReservationList = this.reservationRetrievalPersistencePort.getPendingByBookId(bookId);

        final var hasPendingReservationsFromOtherUser = CollectionUtils.isNotEmpty(pendingReservationList)
                && pendingReservationList.stream().anyMatch(reservation -> BooleanUtils.isFalse(reservation.userId().equals(userId)));

        if (hasPendingReservationsFromOtherUser) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_MESSAGE_ERROR);
        }
    }

    /**
     * Validate notified reservation precedence.
     *
     * @param userId the user id
     * @param bookId the book id
     */
    private void validateNotifiedReservationPrecedence(final Integer userId, final Integer bookId) {

        final var notifiedReservationList = this.reservationRetrievalPersistencePort.getNotifiedByBookId(bookId);

        final var hasNotifiedReservationsFromOtherUser = CollectionUtils.isNotEmpty(notifiedReservationList)
                && notifiedReservationList.stream().anyMatch(reservation -> BooleanUtils.isFalse(reservation.userId().equals(userId)));

        if (hasNotifiedReservationsFromOtherUser) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_MESSAGE_ERROR);
        }
    }
}
