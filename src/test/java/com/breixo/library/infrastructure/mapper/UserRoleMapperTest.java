package com.breixo.library.infrastructure.mapper;

import com.breixo.library.domain.model.user.enums.UserRole;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class User Role Mapper Test. */
@ExtendWith(MockitoExtension.class)
class UserRoleMapperTest {

    /** The user role mapper. */
    @InjectMocks
    UserRoleMapperImpl userRoleMapper;

    /**
     * Test to role id when role is normal then return zero.
     */
    @Test
    void testToRoleId_whenRoleIsNormal_thenReturnZero() {
        // When / Then
        assertEquals(UserRole.NORMAL.getId(), this.userRoleMapper.toRoleId(UserRole.NORMAL));
    }

    /**
     * Test to role id when role is manager then return one.
     */
    @Test
    void testToRoleId_whenRoleIsManager_thenReturnOne() {
        // When / Then
        assertEquals(UserRole.MANAGER.getId(), this.userRoleMapper.toRoleId(UserRole.MANAGER));
    }

    /**
     * Test to role id when role is admin then return two.
     */
    @Test
    void testToRoleId_whenRoleIsAdmin_thenReturnTwo() {
        // When / Then
        assertEquals(UserRole.ADMIN.getId(), this.userRoleMapper.toRoleId(UserRole.ADMIN));
    }

    /**
     * Test to role id when role is null then return zero (normal default).
     */
    @Test
    void testToRoleId_whenRoleIsNull_thenReturnZero() {
        // When / Then
        assertEquals(UserRole.NORMAL.getId(), this.userRoleMapper.toRoleId(null));
    }

    /**
     * Test to user role when integer id is normal then return normal.
     */
    @Test
    void testToUserRole_whenIntegerIdIsNormal_thenReturnNormal() {
        // When / Then
        assertEquals(UserRole.NORMAL, this.userRoleMapper.toUserRole(UserRole.NORMAL.getId()));
    }

    /**
     * Test to user role when integer id is manager then return manager.
     */
    @Test
    void testToUserRole_whenIntegerIdIsManager_thenReturnManager() {
        // When / Then
        assertEquals(UserRole.MANAGER, this.userRoleMapper.toUserRole(UserRole.MANAGER.getId()));
    }

    /**
     * Test to user role when integer id is admin then return admin.
     */
    @Test
    void testToUserRole_whenIntegerIdIsAdmin_thenReturnAdmin() {
        // When / Then
        assertEquals(UserRole.ADMIN, this.userRoleMapper.toUserRole(UserRole.ADMIN.getId()));
    }

    /**
     * Test to user role when integer id is null then return null.
     */
    @Test
    void testToUserRole_whenIntegerIdIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.userRoleMapper.toUserRole(null));
    }
}
