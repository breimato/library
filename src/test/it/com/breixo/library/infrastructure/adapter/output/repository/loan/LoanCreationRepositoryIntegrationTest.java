package com.breixo.library.infrastructure.adapter.output.repository.loan;

import java.time.LocalDate;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Loan Creation Repository Integration Test. */
class LoanCreationRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The loan creation repository. */
    @Autowired
    LoanCreationRepository loanCreationRepository;

    /**
     * Test execute when command is valid then persist and return loan.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql"
    })
    void testExecute_whenCommandIsValid_thenPersistAndReturnLoan() {

        // Given
        final var dueDate = LocalDate.now().plusDays(14);
        final var createLoanCommand = CreateLoanCommand.builder()
                .userId(100)
                .bookId(200)
                .dueDate(dueDate)
                .build();

        // When
        final var loan = this.loanCreationRepository.execute(createLoanCommand);

        // Then
        assertEquals(1, loan.id());
        assertEquals(100, loan.userId());
        assertEquals(200, loan.bookId());
        assertEquals(dueDate, loan.dueDate());
        assertEquals(LoanStatus.ACTIVE, loan.status());
    }
}
