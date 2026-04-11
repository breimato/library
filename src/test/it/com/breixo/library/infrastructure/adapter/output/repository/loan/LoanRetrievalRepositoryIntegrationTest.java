package com.breixo.library.infrastructure.adapter.output.repository.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/** The Class Loan Retrieval Repository Integration Test. */
class LoanRetrievalRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The loan retrieval repository. */
    @Autowired
    LoanRetrievalRepository loanRetrievalRepository;

    /**
     * Test find when id matches then return loan.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan/insert-loan.sql"
    })
    void testFind_whenIdMatches_thenReturnLoan() {

        // Given
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(300).build();

        // When
        final var loanList = this.loanRetrievalRepository.find(loanSearchCriteriaCommand);

        // Then
        assertFalse(CollectionUtils.isEmpty(loanList));
        assertEquals(300, loanList.getFirst().id());
        assertEquals(100, loanList.getFirst().userId());
    }
}
