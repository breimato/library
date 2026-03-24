package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import java.util.Optional;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetUserIdV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.GetUserResponseMapper;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Get User Id Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetUserIdControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/users/{id}";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get user id controller. */
    @InjectMocks
    GetUserIdController getUserIdController;

    /** The user retrieval persistence port. */
    @Mock
    UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The get user response mapper. */
    @Mock
    GetUserResponseMapper getUserResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getUserIdController).build();
    }

    /**
     * Test get user id v1 when user exists then return ok response.
     */
    @Test
    void testGetUserIdV1_whenUserExists_thenReturnOkResponse() throws Exception {
        // Given
        final var id = Instancio.create(Long.class);
        final var user = Instancio.create(User.class);
        final var getUserIdV1Response = Instancio.create(GetUserIdV1Response.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(id).build();

        // When
        when(this.userRetrievalPersistencePort.execute(userSearchCriteriaCommand)).thenReturn(Optional.of(user));
        when(this.getUserResponseMapper.toGetUserIdV1Response(user)).thenReturn(getUserIdV1Response);

        this.mockMvc.perform(get(URL, id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).execute(userSearchCriteriaCommand);
        verify(this.getUserResponseMapper, times(1)).toGetUserIdV1Response(user);
    }

    /**
     * Test get user id v1 when user not found then throw user exception.
     */
    @Test
    void testGetUserIdV1_whenUserNotFound_thenThrowUserException() {
        // Given
        final var id = Instancio.create(Long.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(id).build();

        // When
        when(this.userRetrievalPersistencePort.execute(userSearchCriteriaCommand)).thenReturn(Optional.empty());
        final var userException = assertThrows(UserException.class,
                () -> this.getUserIdController.getUserIdV1(id));

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).execute(userSearchCriteriaCommand);
        verifyNoInteractions(this.getUserResponseMapper);
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR, userException.getCode());
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR, userException.getMessage());
    }
}
