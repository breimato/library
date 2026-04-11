package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Loan Request Update Repository Integration Test. */
class LoanRequestUpdateRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The loan request update repository. */
    @Autowired
    LoanRequestUpdateRepository loanRequestUpdateRepository;

    /**
     * Test execute when command is valid then update and return loan request.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan_request/insert-loan-request.sql"
    })
    void testExecute_whenCommandIsValid_thenUpdateAndReturnLoanRequest() {

        // Given
        final var updateLoanRequestCommand = UpdateLoanRequestCommand.builder()
                .requesterId(1)
                .id(500)
                .status(LoanRequestStatus.APPROVED)
                .rejectionReason(null)
                .build();

        // When
        final var loanRequest = this.loanRequestUpdateRepository.execute(updateLoanRequestCommand);

        // Then
        assertEquals(500, loanRequest.id());
        assertEquals(LoanRequestStatus.APPROVED, loanRequest.status());
    }
}
