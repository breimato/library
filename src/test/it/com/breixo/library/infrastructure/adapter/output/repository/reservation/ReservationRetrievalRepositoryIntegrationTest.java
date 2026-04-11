package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/** The Class Reservation Retrieval Repository Integration Test. */
class ReservationRetrievalRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The reservation retrieval repository. */
    @Autowired
    ReservationRetrievalRepository reservationRetrievalRepository;

    /**
     * Test get pending by book id when pending exists then return list.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/reservation/insert-reservation.sql"
    })
    void testGetPendingByBookId_whenPendingExists_thenReturnList() {

        // When
        final var reservationList = this.reservationRetrievalRepository.getPendingByBookId(200);

        // Then
        assertFalse(CollectionUtils.isEmpty(reservationList));
        assertEquals(400, reservationList.getFirst().id());
    }
}
