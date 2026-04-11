package com.breixo.library.infrastructure.adapter.output.repository.user;

import java.util.Objects;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.model.user.enums.UserStatus;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class User Creation Repository Integration Test. */
class UserCreationRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The user creation repository. */
    @Autowired
    UserCreationRepository userCreationRepository;

    /**
     * Test execute when command is valid then persist and return user.
     */
    @Test
    @Sql("classpath:sql/integration/common/reset-data.sql")
    void testExecute_whenCommandIsValid_thenPersistAndReturnUser() {

        // Given
        final var createUserCommand = Instancio.create(CreateUserCommand.class);

        // When
        final var user = this.userCreationRepository.execute(createUserCommand);

        // Then
        final var userRole = Objects.isNull(createUserCommand.role())
                ? UserRole.NORMAL
                : createUserCommand.role();

        assertEquals(1, user.id());
        assertEquals(createUserCommand.name(), user.name());
        assertEquals(createUserCommand.email(), user.email());
        assertEquals(createUserCommand.phone(), user.phone());
        assertEquals(UserStatus.ACTIVE, user.status());
        assertEquals(userRole, user.role());
    }
}
