package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.model.user.User;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1Dto;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

/** The Class Patch User Response Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PatchUserResponseMapperTest {

    /** The patch user response mapper. */
    @InjectMocks
    PatchUserResponseMapperImpl patchUserResponseMapper;

    /** The user mapper. */
    @Mock
    UserMapper userMapper;

    /**
     * Test to patch user v1 response when user is valid then return mapped response.
     */
    @Test
    void testToPatchUserV1Response_whenUserIsValid_thenReturnMappedResponse() {
        // Given
        final var user = Instancio.create(User.class);
        final var userV1Dto = Instancio.create(UserV1Dto.class);

        // When
        when(this.userMapper.toUserV1(user)).thenReturn(userV1Dto);
        final var patchUserV1ResponseDto = this.patchUserResponseMapper.toPatchUserV1Response(user);

        // Then
        assertNotNull(patchUserV1ResponseDto);
    }

    /**
     * Test to patch user v1 response when user is null then return null.
     */
    @Test
    void testToPatchUserV1Response_whenUserIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.patchUserResponseMapper.toPatchUserV1Response(null));
    }
}
