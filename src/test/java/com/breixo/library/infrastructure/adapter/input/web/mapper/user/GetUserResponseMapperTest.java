package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.model.user.User;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1Dto;

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

/** The Class Get User Response Mapper Test. */
@ExtendWith(MockitoExtension.class)
class GetUserResponseMapperTest {

    /** The get user response mapper. */
    @InjectMocks
    GetUserResponseMapperImpl getUserResponseMapper;

    /** The user mapper. */
    @Mock
    UserMapper userMapper;

    /**
     * Test to get user id v1 response when user is valid then return mapped response.
     */
    @Test
    void testToGetUserIdV1Response_whenUserIsValid_thenReturnMappedResponse() {
        // Given
        final var user = Instancio.create(User.class);
        final var userV1Dto = Instancio.create(UserV1Dto.class);

        // When
        when(this.userMapper.toUserV1(user)).thenReturn(userV1Dto);
        final var getUserIdV1Response = this.getUserResponseMapper.toGetUserIdV1Response(user);

        // Then
        verify(this.userMapper, times(1)).toUserV1(user);
        assertNotNull(getUserIdV1Response);
        assertEquals(userV1Dto, getUserIdV1Response.getUser());
    }

    /**
     * Test to get user id v1 response when user is null then return null.
     */
    @Test
    void testToGetUserIdV1Response_whenUserIsNull_thenReturnNull() {
        assertNull(this.getUserResponseMapper.toGetUserIdV1Response(null));
    }
}
