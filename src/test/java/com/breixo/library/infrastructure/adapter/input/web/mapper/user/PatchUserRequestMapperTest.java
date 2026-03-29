package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.model.user.enums.UserStatus;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchUserV1Request;
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

/** The Class Patch User Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PatchUserRequestMapperTest {

    /** The patch user request mapper. */
    @InjectMocks
    PatchUserRequestMapperImpl patchUserRequestMapper;

    /** The user status mapper. */
    @Mock
    UserStatusMapper userStatusMapper;

    /**
     * Test to update user command when request is valid then return mapped command.
     */
    @Test
    void testToUpdateUserCommand_whenRequestIsValid_thenReturnMappedCommand() {
        
        // Given
        final var id = Instancio.create(Integer.class);
        final var patchUserV1RequestDto = Instancio.create(PatchUserV1Request.class);
        final var userStatus = Instancio.create(UserStatus.class);

        // When
        when(this.userStatusMapper.toUserStatus(patchUserV1RequestDto.getStatus())).thenReturn(userStatus);
        final var updateUserCommand = this.patchUserRequestMapper.toUpdateUserCommand(id, patchUserV1RequestDto);

        // Then
        assertNotNull(updateUserCommand);
        assertEquals(id, updateUserCommand.id());
        assertEquals(patchUserV1RequestDto.getName(), updateUserCommand.name());
        assertEquals(patchUserV1RequestDto.getPhone(), updateUserCommand.phone());
        assertEquals(userStatus, updateUserCommand.status());
    }

    /**
     * Test to update user command when both params are null then return null.
     */
    @Test
    void testToUpdateUserCommand_whenBothParamsAreNull_thenReturnNull() {
        // When / Then
        assertNull(this.patchUserRequestMapper.toUpdateUserCommand(null, null));
    }

    /**
     * Test to update user command when id is null then return command with null id.
     */
    @Test
    void testToUpdateUserCommand_whenIdIsNull_thenReturnCommandWithNullId() {
        
        // Given
        final var patchUserV1RequestDto = Instancio.create(PatchUserV1Request.class);
        final var userStatus = Instancio.create(UserStatus.class);

        // When
        when(this.userStatusMapper.toUserStatus(patchUserV1RequestDto.getStatus())).thenReturn(userStatus);
        final var updateUserCommand = this.patchUserRequestMapper.toUpdateUserCommand(null, patchUserV1RequestDto);

        // Then
        assertNotNull(updateUserCommand);
        assertNull(updateUserCommand.id());
        assertEquals(patchUserV1RequestDto.getName(), updateUserCommand.name());
        assertEquals(patchUserV1RequestDto.getPhone(), updateUserCommand.phone());
        assertEquals(userStatus, updateUserCommand.status());
    }

    /**
     * Test to update user command when request is null then return command with only id.
     */
    @Test
    void testToUpdateUserCommand_whenRequestIsNull_thenReturnCommandWithOnlyId() {
        
        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var updateUserCommand = this.patchUserRequestMapper.toUpdateUserCommand(id, null);

        // Then
        assertNotNull(updateUserCommand);
        assertEquals(id, updateUserCommand.id());
        assertNull(updateUserCommand.name());
        assertNull(updateUserCommand.phone());
        assertNull(updateUserCommand.status());
    }
}
