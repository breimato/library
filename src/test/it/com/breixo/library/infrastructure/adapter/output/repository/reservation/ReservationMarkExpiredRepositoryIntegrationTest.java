package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.model.reservation.enums.ReservationStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** The Class Reservation Mark Expired Repository Integration Test. */
class ReservationMarkExpiredRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The reservation mark expired repository. */
    @Autowired
    ReservationMarkExpiredRepository reservationMarkExpiredRepository;

    /** The reservation retrieval repository. */
    @Autowired
    ReservationRetrievalRepository reservationRetrievalRepository;

    /**
     * Test mark expired when expired pending exists then update rows.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/reservation/insert-reservation-expired-candidate.sql"
    })
    void testMarkExpired_whenExpiredPendingExists_thenUpdateRows() {

        // When
        final var updatedCount = this.reservationMarkExpiredRepository.markExpired();

        // Then
        assertTrue(updatedCount >= 1);
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().id(401).build();
        final var reservationList = this.reservationRetrievalRepository.find(reservationSearchCriteriaCommand);
        assertFalse(CollectionUtils.isEmpty(reservationList));
        assertEquals(ReservationStatus.EXPIRED, reservationList.getFirst().status());
    }
}
