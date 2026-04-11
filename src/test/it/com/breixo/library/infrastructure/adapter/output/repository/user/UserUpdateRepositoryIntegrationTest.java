package com.breixo.library.infrastructure.adapter.output.repository.user;

import com.breixo.library.domain.command.user.UpdateUserCommand;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.model.user.enums.UserStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class User Update Repository Integration Test. */
class UserUpdateRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The user update repository. */
    @Autowired
    UserUpdateRepository userUpdateRepository;

    /**
     * Test execute when command is valid then update and return user.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/user/insert-user.sql"
    })
    void testExecute_whenCommandIsValid_thenUpdateAndReturnUser() {

        // Given
        final var updateUserCommand = UpdateUserCommand.builder()
                .id(100)
                .name("Updated Integration Name")
                .phone("+34999111222")
                .status(UserStatus.ACTIVE)
                .role(UserRole.MANAGER)
                .build();

        // When
        final var user = this.userUpdateRepository.execute(updateUserCommand);

        // Then
        assertEquals(100, user.id());
        assertEquals("Updated Integration Name", user.name());
        assertEquals("+34999111222", user.phone());
        assertEquals(UserRole.MANAGER, user.role());
    }
}
