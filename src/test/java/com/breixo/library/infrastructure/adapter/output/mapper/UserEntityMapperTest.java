package com.breixo.library.infrastructure.adapter.output.mapper;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.model.user.UserStatus;
import com.breixo.library.infrastructure.adapter.output.entities.UserEntity;
import com.breixo.library.infrastructure.mapper.UserStatusMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

/** The Class User Entity Mapper Test. */
@ExtendWith(MockitoExtension.class)
class UserEntityMapperTest {

    /** The user entity mapper. */
    @InjectMocks
    UserEntityMapperImpl userEntityMapper;

    /** The user status mapper. */
    @Mock
    UserStatusMapper userStatusMapper;

    /**
     * Test to user when user entity is valid then return mapped user.
     */
    @Test
    void testToUser_whenUserEntityIsValid_thenReturnMappedUser() {
        // Given
        final var userEntity = Instancio.create(UserEntity.class);
        userEntity.setStatus(UserStatus.ACTIVE.name().toLowerCase());

        // When
        when(this.userStatusMapper.toUserStatus(userEntity.getStatus())).thenReturn(UserStatus.ACTIVE);
        final var user = this.userEntityMapper.toUser(userEntity);

        // Then
        assertNotNull(user);
        assertEquals(userEntity.getId(), user.id());
        assertEquals(userEntity.getName(), user.name());
        assertEquals(userEntity.getEmail(), user.email());
        assertEquals(userEntity.getPhone(), user.phone());
        assertEquals(UserStatus.ACTIVE, user.status());
        assertEquals(userEntity.getCreatedAt(), user.createdAt());
        assertEquals(userEntity.getUpdatedAt(), user.updatedAt());
    }

    /**
     * Test to user when user entity is null then return null.
     */
    @Test
    void testToUser_whenUserEntityIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.userEntityMapper.toUser(null));
    }

    /**
     * Test to user entity when create user command is valid then return mapped user entity.
     */
    @Test
    void testToUserEntity_whenCreateUserCommandIsValid_thenReturnMappedUserEntity() {
        // Given
        final var command = CreateUserCommand.builder().name("John").email("john@example.com").phone("123456789").build();

        // When
        final var userEntity = this.userEntityMapper.toUserEntity(command);

        // Then
        assertNotNull(userEntity);
        assertEquals(command.name(), userEntity.getName());
        assertEquals(command.email(), userEntity.getEmail());
        assertEquals(command.phone(), userEntity.getPhone());
        assertEquals("active", userEntity.getStatus());
    }

    /**
     * Test to user entity when create user command is null then return null.
     */
    @Test
    void testToUserEntity_whenCreateUserCommandIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.userEntityMapper.toUserEntity(null));
    }
}
