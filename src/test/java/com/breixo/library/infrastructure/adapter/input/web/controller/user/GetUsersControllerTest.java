package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.UserMapper;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Get Users Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetUsersControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/users";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get users controller. */
    @InjectMocks
    GetUsersController getUsersController;

    /** The user retrieval persistence port. */
    @Mock
    UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The user mapper. */
    @Mock
    UserMapper userMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getUsersController).build();
    }

    /**
     * Test get users v 1 when no criteria then return all users.
     */
    @Test
    void testGetUsersV1_whenNoCriteria_thenReturnAllUsers() throws Exception {
        // Given
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().build();
        final var users = Instancio.createList(User.class);
        final var userV1DtoList = Instancio.createList(UserV1Dto.class);

        // When
        when(this.userRetrievalPersistencePort.findAll(userSearchCriteriaCommand)).thenReturn(users);
        when(this.userMapper.toUserV1List(users)).thenReturn(userV1DtoList);

        this.mockMvc.perform(get(URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).findAll(userSearchCriteriaCommand);
        verify(this.userMapper, times(1)).toUserV1List(users);
    }

    /**
     * Test get users v 1 when criteria provided then return filtered users.
     */
    @Test
    void testGetUsersV1_whenCriteriaProvided_thenReturnFilteredUsers() throws Exception {
        // Given
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder()
                .name("John")
                .build();
        final var users = Instancio.createList(User.class);
        final var userV1DtoList = Instancio.createList(UserV1Dto.class);

        // When
        when(this.userRetrievalPersistencePort.findAll(userSearchCriteriaCommand)).thenReturn(users);
        when(this.userMapper.toUserV1List(users)).thenReturn(userV1DtoList);

        this.mockMvc.perform(get(URL).param("name", "John").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).findAll(userSearchCriteriaCommand);
        verify(this.userMapper, times(1)).toUserV1List(users);
    }
}
