package com.breixo.library.infrastructure.adapter.output.repository.fine;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Fine Update Repository Integration Test. */
class FineUpdateRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The fine update repository. */
    @Autowired
    FineUpdateRepository fineUpdateRepository;

    /**
     * Test execute when command is valid then update and return fine.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan/insert-loan.sql",
            "classpath:sql/integration/fine/insert-fine.sql"
    })
    void testExecute_whenCommandIsValid_thenUpdateAndReturnFine() {

        // Given
        final var paidAt = LocalDateTime.of(2026, 2, 1, 10, 0);
        final var updateFineCommand = UpdateFineCommand.builder()
                .id(600)
                .amountEuros(new BigDecimal("12.50"))
                .status(FineStatus.PAID)
                .paidAt(paidAt)
                .build();

        // When
        final var fine = this.fineUpdateRepository.execute(updateFineCommand);

        // Then
        assertEquals(600, fine.id());
        assertEquals(FineStatus.PAID, fine.status());
    }
}
