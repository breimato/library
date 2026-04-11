package com.breixo.library.infrastructure.adapter.output.repository.fine;

import java.math.BigDecimal;

import com.breixo.library.domain.command.fine.CreateFineCommand;
import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Fine Creation Repository Integration Test. */
class FineCreationRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The fine creation repository. */
    @Autowired
    FineCreationRepository fineCreationRepository;

    /**
     * Test execute when command is valid then persist and return fine.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan/insert-loan.sql"
    })
    void testExecute_whenCommandIsValid_thenPersistAndReturnFine() {

        // Given
        final var amountEuros = new BigDecimal("5.00");
        final var createFineCommand = CreateFineCommand.builder()
                .loanId(300)
                .amountEuros(amountEuros)
                .statusId(FineStatus.PENDING.getId())
                .build();

        // When
        final var fine = this.fineCreationRepository.execute(createFineCommand);

        // Then
        assertEquals(1, fine.id());
        assertEquals(300, fine.loanId());
        assertEquals(0, fine.amountEuros().compareTo(amountEuros));
        assertEquals(FineStatus.PENDING, fine.status());
    }
}
