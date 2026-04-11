package com.breixo.library.infrastructure.adapter.output.repository.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** The Class Loan Deletion Repository Integration Test. */
class LoanDeletionRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The loan deletion repository. */
    @Autowired
    LoanDeletionRepository loanDeletionRepository;

    /** The loan retrieval repository. */
    @Autowired
    LoanRetrievalRepository loanRetrievalRepository;

    /**
     * Test execute when id exists then delete loan.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan/insert-loan.sql"
    })
    void testExecute_whenIdExists_thenDeleteLoan() {

        // When
        this.loanDeletionRepository.execute(300);

        // Then
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(300).build();
        final var loanList = this.loanRetrievalRepository.find(loanSearchCriteriaCommand);
        assertTrue(CollectionUtils.isEmpty(loanList));
    }
}
