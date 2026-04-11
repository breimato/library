package com.breixo.library.infrastructure.adapter.output.repository.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** The Class Loan Mark Overdue Repository Integration Test. */
class LoanMarkOverdueRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The loan mark overdue repository. */
    @Autowired
    LoanMarkOverdueRepository loanMarkOverdueRepository;

    /** The loan retrieval repository. */
    @Autowired
    LoanRetrievalRepository loanRetrievalRepository;

    /**
     * Test mark overdue when past due then update rows.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan/insert-loan-overdue.sql"
    })
    void testMarkOverdue_whenPastDue_thenUpdateRows() {

        // When
        final var updatedCount = this.loanMarkOverdueRepository.markOverdue();

        // Then
        assertTrue(updatedCount >= 1);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(303).build();
        final var loanList = this.loanRetrievalRepository.find(loanSearchCriteriaCommand);
        assertFalse(CollectionUtils.isEmpty(loanList));
        assertEquals(LoanStatus.OVERDUE, loanList.getFirst().status());
    }
}
