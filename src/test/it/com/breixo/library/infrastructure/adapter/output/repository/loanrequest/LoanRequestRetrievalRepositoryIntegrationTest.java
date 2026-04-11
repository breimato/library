package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Loan Request Retrieval Repository Integration Test. */
class LoanRequestRetrievalRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The loan request retrieval repository. */
    @Autowired
    LoanRequestRetrievalRepository loanRequestRetrievalRepository;

    /**
     * Test find by id when loan request exists then return loan request.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan_request/insert-loan-request.sql"
    })
    void testFindById_whenLoanRequestExists_thenReturnLoanRequest() {

        // When
        final var loanRequest = this.loanRequestRetrievalRepository.findById(500).orElseThrow();

        // Then
        assertEquals(500, loanRequest.id());
        assertEquals(100, loanRequest.userId());
    }
}
