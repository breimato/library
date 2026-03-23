package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import java.time.ZoneOffset;

import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.UserStatus;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
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

/** The Class User Mapper Test. */
@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    /** The user mapper. */
    @InjectMocks
    UserMapperImpl userMapper;

    /** The date mapper. */
    @Mock
    DateMapper dateMapper;

    /** The user status mapper. */
    @Mock
    UserStatusMapper userStatusMapper;

    /**
     * Test to user v1 when status is active then return mapped dto with active status.
     */
    @Test
    void testToUserV1_whenStatusIsActive_thenReturnMappedDtoWithActiveStatus() {
        // Given
        final var base = Instancio.create(User.class);
        final var user = new User(base.id(), base.name(), base.email(), base.phone(),
                base.membershipExpires(), UserStatus.ACTIVE, base.createdAt(), base.updatedAt());

        // When
        when(this.dateMapper.toOffsetDateTime(user.createdAt())).thenReturn(user.createdAt().atOffset(ZoneOffset.UTC));
        when(this.dateMapper.toOffsetDateTime(user.updatedAt())).thenReturn(user.updatedAt().atOffset(ZoneOffset.UTC));
        when(this.userStatusMapper.toStatusId(UserStatus.ACTIVE)).thenReturn(UserStatus.ACTIVE.getId());
        final var userV1Dto = this.userMapper.toUserV1(user);

        // Then
        verify(this.dateMapper, times(1)).toOffsetDateTime(user.createdAt());
        verify(this.dateMapper, times(1)).toOffsetDateTime(user.updatedAt());
        assertNotNull(userV1Dto);
        assertEquals(user.id(), userV1Dto.getId());
        assertEquals(user.name(), userV1Dto.getName());
        assertEquals(user.email(), userV1Dto.getEmail());
        assertEquals(user.phone(), userV1Dto.getPhone());
        assertEquals(user.membershipExpires(), userV1Dto.getMembershipExpires());
        assertEquals(UserStatus.ACTIVE.getId(), userV1Dto.getStatus());
        assertEquals(user.createdAt().atOffset(ZoneOffset.UTC), userV1Dto.getCreatedAt());
        assertEquals(user.updatedAt().atOffset(ZoneOffset.UTC), userV1Dto.getUpdatedAt());
    }

    /**
     * Test to user v1 when status is suspended then return mapped dto with suspended status.
     */
    @Test
    void testToUserV1_whenStatusIsSuspended_thenReturnMappedDtoWithSuspendedStatus() {
        // Given
        final var base = Instancio.create(User.class);
        final var user = new User(base.id(), base.name(), base.email(), base.phone(),
                base.membershipExpires(), UserStatus.SUSPENDED, base.createdAt(), base.updatedAt());

        // When
        when(this.dateMapper.toOffsetDateTime(user.createdAt())).thenReturn(user.createdAt().atOffset(ZoneOffset.UTC));
        when(this.dateMapper.toOffsetDateTime(user.updatedAt())).thenReturn(user.updatedAt().atOffset(ZoneOffset.UTC));
        when(this.userStatusMapper.toStatusId(UserStatus.SUSPENDED)).thenReturn(UserStatus.SUSPENDED.getId());
        final var userV1Dto = this.userMapper.toUserV1(user);

        // Then
        assertNotNull(userV1Dto);
        assertEquals(UserStatus.SUSPENDED.getId(), userV1Dto.getStatus());
    }

    /**
     * Test to user v1 when status is blocked then return mapped dto with blocked status.
     */
    @Test
    void testToUserV1_whenStatusIsBlocked_thenReturnMappedDtoWithBlockedStatus() {
        // Given
        final var base = Instancio.create(User.class);
        final var user = new User(base.id(), base.name(), base.email(), base.phone(),
                base.membershipExpires(), UserStatus.BLOCKED, base.createdAt(), base.updatedAt());

        // When
        when(this.dateMapper.toOffsetDateTime(user.createdAt())).thenReturn(user.createdAt().atOffset(ZoneOffset.UTC));
        when(this.dateMapper.toOffsetDateTime(user.updatedAt())).thenReturn(user.updatedAt().atOffset(ZoneOffset.UTC));
        when(this.userStatusMapper.toStatusId(UserStatus.BLOCKED)).thenReturn(UserStatus.BLOCKED.getId());
        final var userV1Dto = this.userMapper.toUserV1(user);

        // Then
        assertNotNull(userV1Dto);
        assertEquals(UserStatus.BLOCKED.getId(), userV1Dto.getStatus());
    }

    /**
     * Test to user v1 when user is null then return null.
     */
    @Test
    void testToUserV1_whenUserIsNull_thenReturnNull() {
        assertNull(this.userMapper.toUserV1(null));
    }
}
