package com.breixo.library.infrastructure.adapter.output.repository.user;

import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** The Class User Deletion Repository Integration Test. */
class UserDeletionRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The user deletion repository. */
    @Autowired
    UserDeletionRepository userDeletionRepository;

    /** The user retrieval repository. */
    @Autowired
    UserRetrievalRepository userRetrievalRepository;

    /**
     * Test execute when id exists then delete user.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql"
    })
    void testExecute_whenIdExists_thenDeleteUser() {

        // When
        this.userDeletionRepository.execute(100);

        // Then
        assertTrue(this.userRetrievalRepository.findById(100).isEmpty());
    }
}
