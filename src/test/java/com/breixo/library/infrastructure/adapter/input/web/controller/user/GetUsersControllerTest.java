package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetUsersV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.GetUsersV1RequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.UserMapper;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Get Users Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetUsersControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/users";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get users controller. */
    @InjectMocks
    GetUsersController getUsersController;

    /** The user retrieval persistence port. */
    @Mock
    UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The get users V1 request mapper. */
    @Mock
    GetUsersV1RequestMapper getUsersV1RequestMapper;

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
        when(this.getUsersV1RequestMapper.toUserSearchCriteriaCommand(null)).thenReturn(userSearchCriteriaCommand);
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(users);
        when(this.userMapper.toUserV1List(users)).thenReturn(userV1DtoList);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getUsersV1RequestMapper, times(1)).toUserSearchCriteriaCommand(null);
        verify(this.userRetrievalPersistencePort, times(1)).find(userSearchCriteriaCommand);
        verify(this.userMapper, times(1)).toUserV1List(users);
    }

    /**
     * Test get users v 1 when criteria provided then return filtered users.
     */
    @Test
    void testGetUsersV1_whenCriteriaProvided_thenReturnFilteredUsers() throws Exception {
        
        // Given
        final var getUsersV1Request = new GetUsersV1Request();
        getUsersV1Request.setName("John");
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder()
                .name("John")
                .build();
        final var users = Instancio.createList(User.class);
        final var userV1DtoList = Instancio.createList(UserV1Dto.class);

        // When
        when(this.getUsersV1RequestMapper.toUserSearchCriteriaCommand(getUsersV1Request)).thenReturn(userSearchCriteriaCommand);
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(users);
        when(this.userMapper.toUserV1List(users)).thenReturn(userV1DtoList);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getUsersV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getUsersV1RequestMapper, times(1)).toUserSearchCriteriaCommand(getUsersV1Request);
        verify(this.userRetrievalPersistencePort, times(1)).find(userSearchCriteriaCommand);
        verify(this.userMapper, times(1)).toUserV1List(users);
    }

    /**
     * Test get users v 1 when id provided then return matching users.
     */
    @Test
    void testGetUsersV1_whenIdProvided_thenReturnMatchingUsers() throws Exception {
        
        // Given
        final var id = Instancio.create(Integer.class);
        final var getUsersV1Request = new GetUsersV1Request();
        getUsersV1Request.setId(id);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder()
                .id(id)
                .build();
        final var users = Instancio.createList(User.class);
        final var userV1DtoList = Instancio.createList(UserV1Dto.class);

        // When
        when(this.getUsersV1RequestMapper.toUserSearchCriteriaCommand(getUsersV1Request)).thenReturn(userSearchCriteriaCommand);
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(users);
        when(this.userMapper.toUserV1List(users)).thenReturn(userV1DtoList);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getUsersV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getUsersV1RequestMapper, times(1)).toUserSearchCriteriaCommand(getUsersV1Request);
        verify(this.userRetrievalPersistencePort, times(1)).find(userSearchCriteriaCommand);
        verify(this.userMapper, times(1)).toUserV1List(users);
    }
}
