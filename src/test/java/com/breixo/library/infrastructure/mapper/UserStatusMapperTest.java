package com.breixo.library.infrastructure.mapper;

import com.breixo.library.domain.model.user.UserStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class User Status Mapper Test. */
@ExtendWith(MockitoExtension.class)
class UserStatusMapperTest {

    /** The user status mapper. */
    @InjectMocks
    UserStatusMapperImpl userStatusMapper;

    /**
     * Test to status value when status is active then return zero.
     */
    @Test
    void testToStatusValue_whenStatusIsActive_thenReturnZero() {
        // When / Then
        assertEquals(UserStatus.ACTIVE.getId(), this.userStatusMapper.toStatusId(UserStatus.ACTIVE));
    }

    /**
     * Test to status value when status is suspended then return one.
     */
    @Test
    void testToStatusValue_whenStatusIsSuspended_thenReturnOne() {
        // When / Then
        assertEquals(UserStatus.SUSPENDED.getId(), this.userStatusMapper.toStatusId(UserStatus.SUSPENDED));
    }

    /**
     * Test to status value when status is blocked then return two.
     */
    @Test
    void testToStatusValue_whenStatusIsBlocked_thenReturnTwo() {
        // When / Then
        assertEquals(UserStatus.BLOCKED.getId(), this.userStatusMapper.toStatusId(UserStatus.BLOCKED));
    }

    /**
     * Test to user status when id is active then return active.
     */
    @Test
    void testToUserStatus_whenIdIsActive_thenReturnActive() {
        // When / Then
        assertEquals(UserStatus.ACTIVE, this.userStatusMapper.toUserStatus("active"));
    }

    /**
     * Test to user status when id is suspended then return suspended.
     */
    @Test
    void testToUserStatus_whenIdIsSuspended_thenReturnSuspended() {
        // When / Then
        assertEquals(UserStatus.SUSPENDED, this.userStatusMapper.toUserStatus("suspended"));
    }

    /**
     * Test to user status when id is blocked then return blocked.
     */
    @Test
    void testToUserStatus_whenIdIsBlocked_thenReturnBlocked() {
        // When / Then
        assertEquals(UserStatus.BLOCKED, this.userStatusMapper.toUserStatus("blocked"));
    }
}
