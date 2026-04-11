package com.breixo.library.infrastructure.adapter.output.repository.fine;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** The Class Fine Deletion Repository Integration Test. */
class FineDeletionRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The fine deletion repository. */
    @Autowired
    FineDeletionRepository fineDeletionRepository;

    /** The fine retrieval repository. */
    @Autowired
    FineRetrievalRepository fineRetrievalRepository;

    /**
     * Test execute when id exists then delete fine.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan/insert-loan.sql",
            "classpath:sql/integration/fine/insert-fine.sql"
    })
    void testExecute_whenIdExists_thenDeleteFine() {

        // When
        this.fineDeletionRepository.execute(600);

        // Then
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(600).build();
        final var fineList = this.fineRetrievalRepository.find(fineSearchCriteriaCommand);
        assertTrue(CollectionUtils.isEmpty(fineList));
    }
}
