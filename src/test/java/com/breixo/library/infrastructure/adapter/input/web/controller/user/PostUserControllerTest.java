package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.input.user.CreateUserUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostUserV1RequestDto;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostUserV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.PostUserRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.PostUserResponseMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Post User Controller Test. */
@ExtendWith(MockitoExtension.class)
class PostUserControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/users";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The post user controller. */
    @InjectMocks
    PostUserController postUserController;

    /** The create user use case. */
    @Mock
    CreateUserUseCase createUserUseCase;

    /** The post user request mapper. */
    @Mock
    PostUserRequestMapper postUserRequestMapper;

    /** The post user response mapper. */
    @Mock
    PostUserResponseMapper postUserResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.postUserController).build();
    }

    /**
     * Test post user v1 when request is valid then return created response.
     */
    @Test
    void testPostUserV1_whenRequestIsValid_thenReturnCreatedResponse() throws Exception {
        // Given
        final var postUserV1RequestDto = Instancio.create(PostUserV1RequestDto.class);
        final var createUserCommand = Instancio.create(CreateUserCommand.class);
        final var user = Instancio.create(User.class);
        final var postUserV1ResponseDto = Instancio.create(PostUserV1ResponseDto.class);

        // When
        when(this.postUserRequestMapper.toCreateUserCommand(postUserV1RequestDto)).thenReturn(createUserCommand);
        when(this.createUserUseCase.execute(createUserCommand)).thenReturn(user);
        when(this.postUserResponseMapper.toPostUserV1Response(user)).thenReturn(postUserV1ResponseDto);

        this.mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(postUserV1RequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.postUserRequestMapper, times(1)).toCreateUserCommand(postUserV1RequestDto);
        verify(this.createUserUseCase, times(1)).execute(createUserCommand);
        verify(this.postUserResponseMapper, times(1)).toPostUserV1Response(user);
    }
}
