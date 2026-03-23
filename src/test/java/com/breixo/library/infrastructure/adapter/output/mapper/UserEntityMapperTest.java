package com.breixo.library.infrastructure.adapter.output.mapper;

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
        assertEquals(userEntity.getMembershipExpires(), user.membershipExpires());
        assertEquals(UserStatus.ACTIVE, user.status());
        assertEquals(userEntity.getCreatedAt(), user.createdAt());
        assertEquals(userEntity.getUpdatedAt(), user.updatedAt());
    }

    /**
     * Test to user when user entity is null then return null.
     */
    @Test
    void testToUser_whenUserEntityIsNull_thenReturnNull() {
        assertNull(this.userEntityMapper.toUser(null));
    }
}
