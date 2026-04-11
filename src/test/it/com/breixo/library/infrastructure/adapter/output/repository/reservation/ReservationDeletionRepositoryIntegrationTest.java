package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** The Class Reservation Deletion Repository Integration Test. */
class ReservationDeletionRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The reservation deletion repository. */
    @Autowired
    ReservationDeletionRepository reservationDeletionRepository;

    /** The reservation retrieval repository. */
    @Autowired
    ReservationRetrievalRepository reservationRetrievalRepository;

    /**
     * Test execute when id exists then delete reservation.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/reservation/insert-reservation.sql"
    })
    void testExecute_whenIdExists_thenDeleteReservation() {

        // When
        this.reservationDeletionRepository.execute(400);

        // Then
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().id(400).build();
        final var reservationList = this.reservationRetrievalRepository.find(reservationSearchCriteriaCommand);
        assertTrue(CollectionUtils.isEmpty(reservationList));
    }
}
