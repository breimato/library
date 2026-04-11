package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.domain.model.reservation.enums.ReservationStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Reservation Update Repository Integration Test. */
class ReservationUpdateRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The reservation update repository. */
    @Autowired
    ReservationUpdateRepository reservationUpdateRepository;

    /**
     * Test execute when command is valid then update and return reservation.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/reservation/insert-reservation.sql"
    })
    void testExecute_whenCommandIsValid_thenUpdateAndReturnReservation() {

        // Given
        final var updateReservationCommand = UpdateReservationCommand.builder()
                .id(400)
                .loanId(null)
                .expiresAt(null)
                .status(ReservationStatus.NOTIFIED)
                .build();

        // When
        final var reservation = this.reservationUpdateRepository.execute(updateReservationCommand);

        // Then
        assertEquals(400, reservation.id());
        assertEquals(ReservationStatus.NOTIFIED, reservation.status());
    }
}
