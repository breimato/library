package com.breixo.library.domain.service;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.ReservationException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;

/** The Class Reservation Policy Validation Service Impl Test. */
@ExtendWith(MockitoExtension.class)
class ReservationPolicyValidationServiceImplTest {

    /** The reservation retrieval persistence port. */
    @Mock
    ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    /** The reservation policy validation service. */
    @InjectMocks
    ReservationPolicyValidationServiceImpl reservationPolicyValidationServiceImpl;

    /**
     * Test check precedence when pending reservation from other user then throw
     * loan exception.
     */
    @Test
    void testCheckPrecedence_whenPendingReservationFromOtherUser_thenThrowLoanException() {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var otherUserId = Instancio.create(Integer.class);
        final var otherUserReservation = Instancio.of(Reservation.class)
                .set(field(Reservation.class, "userId"), otherUserId)
                .set(field(Reservation.class, "bookId"), bookId)
                .create();

        // When
        when(this.reservationRetrievalPersistencePort.getPendingByBookId(bookId))
                .thenReturn(List.of(otherUserReservation));
        when(this.reservationRetrievalPersistencePort.getNotifiedByBookId(bookId)).thenReturn(List.of());
        final var exception = assertThrows(LoanException.class,
                () -> this.reservationPolicyValidationServiceImpl.checkPrecedence(userId, bookId));

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).getPendingByBookId(bookId);
        assertEquals(ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_MESSAGE_ERROR,
                exception.getMessage());
    }

    /**
     * Test check precedence when reservation belongs to same user then does not
     * throw.
     */
    @Test
    void testCheckPrecedence_whenReservationBelongsToSameUser_thenDoesNotThrow() {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var ownReservation = Instancio.of(Reservation.class)
                .set(field(Reservation.class, "userId"), userId)
                .set(field(Reservation.class, "bookId"), bookId)
                .create();

        // When
        when(this.reservationRetrievalPersistencePort.getPendingByBookId(bookId)).thenReturn(List.of(ownReservation));
        when(this.reservationRetrievalPersistencePort.getNotifiedByBookId(bookId)).thenReturn(List.of());
        assertDoesNotThrow(() -> this.reservationPolicyValidationServiceImpl.checkPrecedence(userId, bookId));

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).getPendingByBookId(bookId);
        verify(this.reservationRetrievalPersistencePort, times(1)).getNotifiedByBookId(bookId);
    }

    /**
     * Test check no active reservation when active reservation exists then throw
     * reservation exception.
     */
    @Test
    void testCheckNoActiveReservation_whenActiveReservationExists_thenThrowReservationException() {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var ownReservation = Instancio.of(Reservation.class)
                .set(field(Reservation.class, "userId"), userId)
                .set(field(Reservation.class, "bookId"), bookId)
                .create();

        // When
        when(this.reservationRetrievalPersistencePort.getPendingByBookId(bookId)).thenReturn(List.of(ownReservation));
        when(this.reservationRetrievalPersistencePort.getNotifiedByBookId(bookId)).thenReturn(List.of());
        final var exception = assertThrows(ReservationException.class,
                () -> this.reservationPolicyValidationServiceImpl.checkNoActiveReservation(userId, bookId));

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).getPendingByBookId(bookId);
        assertEquals(ExceptionMessageConstants.RESERVATION_ALREADY_EXISTS_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.RESERVATION_ALREADY_EXISTS_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test check no active reservation when no active reservation then does not
     * throw.
     */
    @Test
    void testCheckNoActiveReservation_whenNoActiveReservation_thenDoesNotThrow() {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);

        // When
        when(this.reservationRetrievalPersistencePort.getPendingByBookId(bookId)).thenReturn(List.of());
        when(this.reservationRetrievalPersistencePort.getNotifiedByBookId(bookId)).thenReturn(List.of());
        assertDoesNotThrow(() -> this.reservationPolicyValidationServiceImpl.checkNoActiveReservation(userId, bookId));

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).getPendingByBookId(bookId);
        verify(this.reservationRetrievalPersistencePort, times(1)).getNotifiedByBookId(bookId);
    }
}
