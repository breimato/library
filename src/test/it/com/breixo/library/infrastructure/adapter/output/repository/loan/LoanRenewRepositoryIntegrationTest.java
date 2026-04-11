package com.breixo.library.infrastructure.adapter.output.repository.loan;

import java.time.LocalDate;

import com.breixo.library.domain.command.loan.UpdateLoanRenewCommand;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Loan Renew Repository Integration Test. */
class LoanRenewRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The loan renew repository. */
    @Autowired
    LoanRenewRepository loanRenewRepository;

    /**
     * Test execute when command is valid then renew loan.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan/insert-loan.sql"
    })
    void testExecute_whenCommandIsValid_thenRenewLoan() {

        // Given
        final var newDueDate = LocalDate.now().plusDays(30);
        final var updateLoanRenewCommand = UpdateLoanRenewCommand.builder()
                .id(300)
                .dueDate(newDueDate)
                .build();

        // When
        final var loan = this.loanRenewRepository.execute(updateLoanRenewCommand);

        // Then
        assertEquals(300, loan.id());
        assertEquals(newDueDate, loan.dueDate());
        assertEquals(LoanStatus.ACTIVE, loan.status());
    }
}
