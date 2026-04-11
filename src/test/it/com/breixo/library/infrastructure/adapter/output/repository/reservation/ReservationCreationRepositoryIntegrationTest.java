package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.breixo.library.domain.command.reservation.CreateReservationCommand;
import com.breixo.library.domain.model.reservation.enums.ReservationStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Reservation Creation Repository Integration Test. */
class ReservationCreationRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The reservation creation repository. */
    @Autowired
    ReservationCreationRepository reservationCreationRepository;

    /**
     * Test execute when command is valid then persist and return reservation.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql"
    })
    void testExecute_whenCommandIsValid_thenPersistAndReturnReservation() {

        // Given
        final var expiresAt = LocalDateTime.of(LocalDate.now().plusDays(20), LocalTime.NOON);
        final var createReservationCommand = CreateReservationCommand.builder()
                .userId(100)
                .bookId(200)
                .loanId(null)
                .expiresAt(expiresAt)
                .statusId(ReservationStatus.PENDING.getId())
                .build();

        // When
        final var reservation = this.reservationCreationRepository.execute(createReservationCommand);

        // Then
        assertEquals(1, reservation.id());
        assertEquals(100, reservation.userId());
        assertEquals(200, reservation.bookId());
        assertEquals(ReservationStatus.PENDING, reservation.status());
    }
}
