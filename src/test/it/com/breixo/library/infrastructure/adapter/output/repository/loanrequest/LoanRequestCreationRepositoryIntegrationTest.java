package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Loan Request Creation Repository Integration Test. */
class LoanRequestCreationRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The loan request creation repository. */
    @Autowired
    LoanRequestCreationRepository loanRequestCreationRepository;

    /**
     * Test execute when command is valid then persist and return loan request.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql"
    })
    void testExecute_whenCommandIsValid_thenPersistAndReturnLoanRequest() {

        // Given
        final var createLoanRequestCommand = CreateLoanRequestCommand.builder()
                .requesterId(1)
                .userId(100)
                .bookId(200)
                .build();

        // When
        final var loanRequest = this.loanRequestCreationRepository.execute(createLoanRequestCommand);

        // Then
        assertEquals(1, loanRequest.id());
        assertEquals(100, loanRequest.userId());
        assertEquals(200, loanRequest.bookId());
        assertEquals(LoanRequestStatus.PENDING, loanRequest.status());
    }
}
