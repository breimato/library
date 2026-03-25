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

/** The Class Post User Response Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PostUserResponseMapperTest {

    /** The post user response mapper. */
    @InjectMocks
    UserResponseMapperImpl userResponseMapper;

    /** The user mapper. */
    @Mock
    UserMapper userMapper;

    /**
     * Test to post user v1 response when user is valid then return mapped response.
     */
    @Test
    void testToPostUserV1Response_whenUserIsValid_thenReturnMappedResponse() {
        // Given
        final var user = Instancio.create(User.class);
        final var userV1Dto = Instancio.create(UserV1Dto.class);

        // When
        when(this.userMapper.toUserV1(user)).thenReturn(userV1Dto);
        final var response = this.userResponseMapper.toUserV1Response(user);

        // Then
        assertNotNull(response);
    }

    /**
     * Test to post user v1 response when user is null then return null.
     */
    @Test
    void testToPostUserV1Response_whenUserIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.userResponseMapper.toUserV1Response(null));
    }
}
