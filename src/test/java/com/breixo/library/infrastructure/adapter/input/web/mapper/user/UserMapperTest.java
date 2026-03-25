package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import java.util.List;

import com.breixo.library.domain.model.user.User;
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

    /** The user status mapper. */
    @Mock
    UserStatusMapper userStatusMapper;

    /**
     * Test to user v1 when user is valid then return mapped dto.
     */
    @Test
    void testToUserV1_whenUserIsValid_thenReturnMappedDto() {
        // Given
        final var user = Instancio.create(User.class);

        // When
        when(this.userStatusMapper.toStatusId(user.status())).thenReturn(user.status().getId());
        final var userV1Dto = this.userMapper.toUserV1(user);

        // Then
        assertNotNull(userV1Dto);
        assertEquals(user.id(), userV1Dto.getId());
        assertEquals(user.name(), userV1Dto.getName());
        assertEquals(user.email(), userV1Dto.getEmail());
        assertEquals(user.phone(), userV1Dto.getPhone());
        assertEquals(user.status().getId(), userV1Dto.getStatus());
    }

    /**
     * Test to user v1 when user is null then return null.
     */
    @Test
    void testToUserV1_whenUserIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.userMapper.toUserV1(null));
    }

    /**
     * Test to user v1 list when users are valid then return mapped dto list.
     */
    @Test
    void testToUserV1List_whenUsersAreValid_thenReturnMappedDtoList() {
        // Given
        final var user = Instancio.create(User.class);
        final var users = List.of(user);

        // When
        when(this.userStatusMapper.toStatusId(user.status())).thenReturn(user.status().getId());
        final var userV1DtoList = this.userMapper.toUserV1List(users);

        // Then
        verify(this.userStatusMapper, times(1)).toStatusId(user.status());
        assertNotNull(userV1DtoList);
        assertEquals(1, userV1DtoList.size());
        assertEquals(user.id(), userV1DtoList.getFirst().getId());
    }

    /**
     * Test to user v1 list when users list is empty then return empty list.
     */
    @Test
    void testToUserV1List_whenUsersListIsEmpty_thenReturnEmptyList() {
        // When
        final var userV1DtoList = this.userMapper.toUserV1List(List.of());

        // Then
        assertNotNull(userV1DtoList);
        assertEquals(0, userV1DtoList.size());
    }

    /**
     * Test to user v1 list when users list is null then return null.
     */
    @Test
    void testToUserV1List_whenUsersListIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.userMapper.toUserV1List(null));
    }
}
