package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostUserV1RequestDto;
import com.breixo.library.infrastructure.mapper.UserRoleMapper;

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

/** The Class Post User Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PostUserRequestMapperTest {

    /** The post user request mapper. */
    @InjectMocks
    PostUserRequestMapperImpl postUserRequestMapper;

    /** The user role mapper. */
    @Mock
    UserRoleMapper userRoleMapper;

    /**
     * Test to create user command when request is valid then return mapped command.
     */
    @Test
    void testToCreateUserCommand_whenRequestIsValid_thenReturnMappedCommand() {

        // Given
        final var request = Instancio.create(PostUserV1RequestDto.class);
        final var userRole = Instancio.create(UserRole.class);

        // When
        when(this.userRoleMapper.toUserRole(request.getRole())).thenReturn(userRole);
        final var createUserCommand = this.postUserRequestMapper.toCreateUserCommand(request);

        // Then
        assertNotNull(createUserCommand);
        assertEquals(request.getName(), createUserCommand.name());
        assertEquals(request.getEmail(), createUserCommand.email());
        assertEquals(request.getPhone(), createUserCommand.phone());
        assertEquals(userRole, createUserCommand.role());
    }

    /**
     * Test to create user command when request is null then return null.
     */
    @Test
    void testToCreateUserCommand_whenRequestIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.postUserRequestMapper.toCreateUserCommand(null));
    }
}
