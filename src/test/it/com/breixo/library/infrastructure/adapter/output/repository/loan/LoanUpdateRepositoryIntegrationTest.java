package com.breixo.library.infrastructure.adapter.output.repository.loan;

import java.time.LocalDate;

import com.breixo.library.domain.command.loan.UpdateLoanReturnCommand;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Loan Update Repository Integration Test. */
class LoanUpdateRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The loan update repository. */
    @Autowired
    LoanUpdateRepository loanUpdateRepository;

    /**
     * Test execute when command is valid then return loan.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan/insert-loan.sql"
    })
    void testExecute_whenCommandIsValid_thenReturnLoan() {

        // Given
        final var returnDate = LocalDate.now();
        final var updateLoanReturnCommand = UpdateLoanReturnCommand.builder()
                .id(300)
                .returnDate(returnDate)
                .build();

        // When
        final var loan = this.loanUpdateRepository.execute(updateLoanReturnCommand);

        // Then
        assertEquals(300, loan.id());
        assertEquals(LoanStatus.RETURNED, loan.status());
        assertEquals(returnDate, loan.returnDate());
    }
}
