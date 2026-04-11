package com.breixo.library.infrastructure.adapter.output.repository.user;

import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.model.user.enums.UserStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class User Retrieval Repository Integration Test. */
class UserRetrievalRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The user retrieval repository. */
    @Autowired
    UserRetrievalRepository userRetrievalRepository;

    /**
     * Test find by id when user exists then return user.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql"
    })
    void testFindById_whenUserExists_thenReturnUser() {

        // When
        final var user = this.userRetrievalRepository.findById(100).orElseThrow();

        // Then
        assertEquals(100, user.id());
        assertEquals("Integration User", user.name());
        assertEquals("integration.user@example.com", user.email());
        assertEquals(UserStatus.ACTIVE, user.status());
        assertEquals(UserRole.NORMAL, user.role());
    }
}
