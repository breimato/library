package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.command.user.UpdateUserCommand;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.input.user.UpdateUserUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchUserV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.PatchUserRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.UserResponseMapper;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Patch User Controller Test. */
@ExtendWith(MockitoExtension.class)
class PatchUserControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/users/{id}";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The patch user controller. */
    @InjectMocks
    PatchUserController patchUserController;

    /** The update user use case. */
    @Mock
    UpdateUserUseCase updateUserUseCase;

    /** The patch user request mapper. */
    @Mock
    PatchUserRequestMapper patchUserRequestMapper;

    /** The user response mapper. */
    @Mock
    UserResponseMapper userResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        var auth = new UsernamePasswordAuthenticationToken(
            1, null, List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.patchUserController).build();
    }

    /** Tear down. */
    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    /**
     * Test patch user v1 when user exists then return ok response.
     */
    @Test
    void testPatchUserV1_whenUserExists_thenReturnOkResponse() throws Exception {
        
        // Given
        final var id = Instancio.create(Integer.class);
        final var patchUserV1RequestDto = Instancio.create(PatchUserV1Request.class);
        final var updateUserCommand = Instancio.create(UpdateUserCommand.class);
        final var user = Instancio.create(User.class);
        final var userV1ResponseDto = Instancio.create(UserV1ResponseDto.class);

        // When
        when(this.patchUserRequestMapper.toUpdateUserCommand(id, patchUserV1RequestDto)).thenReturn(updateUserCommand);
        when(this.updateUserUseCase.execute(any())).thenReturn(user);
        when(this.userResponseMapper.toUserV1Response(user)).thenReturn(userV1ResponseDto);

        this.mockMvc.perform(patch(URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(patchUserV1RequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.patchUserRequestMapper, times(1)).toUpdateUserCommand(id, patchUserV1RequestDto);
        verify(this.updateUserUseCase, times(1)).execute(any());
        verify(this.userResponseMapper, times(1)).toUserV1Response(user);
    }
}
