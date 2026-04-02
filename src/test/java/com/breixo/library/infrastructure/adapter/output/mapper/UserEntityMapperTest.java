package com.breixo.library.infrastructure.adapter.output.mapper;

import java.util.List;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.command.user.UpdateUserCommand;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.model.user.enums.UserStatus;
import com.breixo.library.infrastructure.adapter.output.entities.UserEntity;
import com.breixo.library.infrastructure.mapper.UserRoleMapper;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    /** The user role mapper. */
    @Mock
    UserRoleMapper userRoleMapper;

    /**
     * Test to user when user entity is valid then return mapped user.
     */
    @Test
    void testToUser_whenUserEntityIsValid_thenReturnMappedUser() {

        // Given
        final var userEntity = Instancio.create(UserEntity.class);
        userEntity.setStatusId(UserStatus.ACTIVE.getId());
        userEntity.setRoleId(UserRole.NORMAL.getId());

        // When
        when(this.userStatusMapper.toUserStatus(userEntity.getStatusId())).thenReturn(UserStatus.ACTIVE);
        when(this.userRoleMapper.toUserRole(userEntity.getRoleId())).thenReturn(UserRole.NORMAL);
        final var user = this.userEntityMapper.toUser(userEntity);

        // Then
        verify(this.userStatusMapper, times(1)).toUserStatus(userEntity.getStatusId());
        verify(this.userRoleMapper, times(1)).toUserRole(userEntity.getRoleId());
        assertNotNull(user);
        assertEquals(userEntity.getId(), user.id());
        assertEquals(userEntity.getName(), user.name());
        assertEquals(userEntity.getEmail(), user.email());
        assertEquals(userEntity.getPhone(), user.phone());
        assertEquals(UserStatus.ACTIVE, user.status());
        assertEquals(UserRole.NORMAL, user.role());
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
        final var createUserCommand = Instancio.create(CreateUserCommand.class);

        // When
        when(this.userRoleMapper.toRoleId(createUserCommand.role())).thenReturn(createUserCommand.role().getId());
        final var userEntity = this.userEntityMapper.toUserEntity(createUserCommand);

        // Then
        verify(this.userRoleMapper, times(1)).toRoleId(createUserCommand.role());
        assertNotNull(userEntity);
        assertEquals(createUserCommand.name(), userEntity.getName());
        assertEquals(createUserCommand.email(), userEntity.getEmail());
        assertEquals(createUserCommand.phone(), userEntity.getPhone());
        assertEquals(createUserCommand.role().getId(), userEntity.getRoleId());
    }

    /**
     * Test to user entity when create user command is null then return null.
     */
    @Test
    void testToUserEntity_whenCreateUserCommandIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.userEntityMapper.toUserEntity((CreateUserCommand) null));
    }

    /**
     * Test to user entity when update user command is valid then return mapped user entity.
     */
    @Test
    void testToUserEntity_whenUpdateUserCommandIsValid_thenReturnMappedUserEntity() {

        // Given
        final var updateUserCommand = Instancio.create(UpdateUserCommand.class);

        // When
        when(this.userStatusMapper.toStatusId(updateUserCommand.status())).thenReturn(updateUserCommand.status().getId());
        when(this.userRoleMapper.toRoleId(updateUserCommand.role())).thenReturn(updateUserCommand.role().getId());
        final var userEntity = this.userEntityMapper.toUserEntity(updateUserCommand);

        // Then
        verify(this.userStatusMapper, times(1)).toStatusId(updateUserCommand.status());
        verify(this.userRoleMapper, times(1)).toRoleId(updateUserCommand.role());
        assertNotNull(userEntity);
        assertEquals(updateUserCommand.id(), userEntity.getId());
        assertEquals(updateUserCommand.name(), userEntity.getName());
        assertEquals(updateUserCommand.phone(), userEntity.getPhone());
        assertEquals(updateUserCommand.status().getId(), userEntity.getStatusId());
        assertEquals(updateUserCommand.role().getId(), userEntity.getRoleId());
    }

    /**
     * Test to user entity when update user command is null then return null.
     */
    @Test
    void testToUserEntity_whenUpdateUserCommandIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.userEntityMapper.toUserEntity((UpdateUserCommand) null));
    }

    /**
     * Test to user list when user entities are valid then return mapped user list.
     */
    @Test
    void testToUserList_whenUserEntitiesAreValid_thenReturnMappedUserList() {

        // Given
        final var userEntity = Instancio.create(UserEntity.class);
        userEntity.setStatusId(UserStatus.ACTIVE.getId());
        userEntity.setRoleId(UserRole.NORMAL.getId());
        final var userEntities = List.of(userEntity);

        // When
        when(this.userStatusMapper.toUserStatus(userEntity.getStatusId())).thenReturn(UserStatus.ACTIVE);
        when(this.userRoleMapper.toUserRole(userEntity.getRoleId())).thenReturn(UserRole.NORMAL);
        final var users = this.userEntityMapper.toUserList(userEntities);

        // Then
        verify(this.userStatusMapper, times(1)).toUserStatus(userEntity.getStatusId());
        verify(this.userRoleMapper, times(1)).toUserRole(userEntity.getRoleId());
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(userEntity.getId(), users.getFirst().id());
    }

    /**
     * Test to user list when user entities list is empty then return empty list.
     */
    @Test
    void testToUserList_whenUserEntitiesListIsEmpty_thenReturnEmptyList() {
        // When
        final var users = this.userEntityMapper.toUserList(List.of());

        // Then
        assertNotNull(users);
        assertEquals(0, users.size());
    }

    /**
     * Test to user list when user entities list is null then return null.
     */
    @Test
    void testToUserList_whenUserEntitiesListIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.userEntityMapper.toUserList(null));
    }
}
