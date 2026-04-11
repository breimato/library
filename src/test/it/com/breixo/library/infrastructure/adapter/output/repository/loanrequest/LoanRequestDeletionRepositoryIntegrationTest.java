package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** The Class Loan Request Deletion Repository Integration Test. */
class LoanRequestDeletionRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The loan request deletion repository. */
    @Autowired
    LoanRequestDeletionRepository loanRequestDeletionRepository;

    /** The loan request retrieval repository. */
    @Autowired
    LoanRequestRetrievalRepository loanRequestRetrievalRepository;

    /**
     * Test execute when id exists then delete loan request.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan_request/insert-loan-request.sql"
    })
    void testExecute_whenIdExists_thenDeleteLoanRequest() {

        // When
        this.loanRequestDeletionRepository.execute(500);

        // Then
        final var loanRequestSearchCriteriaCommand = LoanRequestSearchCriteriaCommand.builder().id(500).build();
        final var loanRequestList = this.loanRequestRetrievalRepository.find(loanRequestSearchCriteriaCommand);
        assertTrue(CollectionUtils.isEmpty(loanRequestList));
    }
}
