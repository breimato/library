package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** The Class Reservation Mark Notified Repository Integration Test. */
class ReservationMarkNotifiedRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The reservation mark notified repository. */
    @Autowired
    ReservationMarkNotifiedRepository reservationMarkNotifiedRepository;

    /**
     * Test mark notified by book id when pending exists then update rows.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/reservation/insert-reservation.sql"
    })
    void testMarkNotifiedByBookId_whenPendingExists_thenUpdateRows() {

        // When
        final var updatedCount = this.reservationMarkNotifiedRepository.markNotifiedByBookId(200);

        // Then
        assertTrue(updatedCount >= 1);
    }
}
