package com.breixo.library.infrastructure.adapter.output.repository.fine;

import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/** The Class Fine Retrieval Repository Integration Test. */
class FineRetrievalRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The fine retrieval repository. */
    @Autowired
    FineRetrievalRepository fineRetrievalRepository;

    /**
     * Test find by user id when fines exist then return list.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql",
            "classpath:sql/integration/book/insert-book.sql",
            "classpath:sql/integration/loan/insert-loan.sql",
            "classpath:sql/integration/fine/insert-fine.sql"
    })
    void testFindByUserId_whenFinesExist_thenReturnList() {

        // When
        final var fineList = this.fineRetrievalRepository.findByUserId(100);

        // Then
        assertFalse(CollectionUtils.isEmpty(fineList));
        assertEquals(600, fineList.getFirst().id());
    }
}
