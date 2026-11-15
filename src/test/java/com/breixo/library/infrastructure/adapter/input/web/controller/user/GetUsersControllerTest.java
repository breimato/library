package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.UserMapper;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.user.GetUsersV1RequestMapperImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Get Users Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetUsersControllerTest {

    /** The retrieval persistence port. */
    @Mock
    UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The response mapper. */
    @Mock
    UserMapper userMapper;

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    /** Set up the controller with a real request mapper. */
    @BeforeEach
    void setUp() {

        final var getUsersV1RequestMapper = new GetUsersV1RequestMapperImpl();
        final var getUsersController = new GetUsersController(this.userRetrievalPersistencePort, getUsersV1RequestMapper, this.userMapper);
        this.mockMvc = MockMvcBuilders.standaloneSetup(getUsersController).build();
    }

    /** Test GET when AllFilters then return matching Users. */
    @Test
    void testGetUsersV1_whenAllFilters_thenReturnMatchingUsers() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var name = "María & José + / ?";
        final var email = "María & José + / ?";
        final var status = Instancio.create(Integer.class);
        final var statusQueryValue = status.toString();
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(id).name(name).email(email).statusId(status).build();
        final var users = Instancio.createList(User.class);
        final var userV1List = Instancio.createList(UserV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(userV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(users);
        when(this.userMapper.toUserV1List(users)).thenReturn(userV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/users")
                        .queryParam("id", idQueryValue)
                        .queryParam("name", name)
                        .queryParam("email", email)
                        .queryParam("status", statusQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).find(userSearchCriteriaCommand);
        verify(this.userMapper, times(1)).toUserV1List(users);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("users"));
    }

    /** Test GET when NoFilters then return matching Users. */
    @Test
    void testGetUsersV1_whenNoFilters_thenReturnMatchingUsers() throws Exception {

        // Given
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().build();
        final var users = Instancio.createList(User.class);
        final var userV1List = Instancio.createList(UserV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(userV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(users);
        when(this.userMapper.toUserV1List(users)).thenReturn(userV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).find(userSearchCriteriaCommand);
        verify(this.userMapper, times(1)).toUserV1List(users);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("users"));
    }

    /** Test GET when Id then return matching Users. */
    @Test
    void testGetUsersV1_whenId_thenReturnMatchingUsers() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(id).build();
        final var users = Instancio.createList(User.class);
        final var userV1List = Instancio.createList(UserV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(userV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(users);
        when(this.userMapper.toUserV1List(users)).thenReturn(userV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/users")
                        .queryParam("id", idQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).find(userSearchCriteriaCommand);
        verify(this.userMapper, times(1)).toUserV1List(users);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("users"));
    }

    /** Test GET when Name then return matching Users. */
    @Test
    void testGetUsersV1_whenName_thenReturnMatchingUsers() throws Exception {

        // Given
        final var name = "María & José + / ?";
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().name(name).build();
        final var users = Instancio.createList(User.class);
        final var userV1List = Instancio.createList(UserV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(userV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(users);
        when(this.userMapper.toUserV1List(users)).thenReturn(userV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/users")
                        .queryParam("name", name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).find(userSearchCriteriaCommand);
        verify(this.userMapper, times(1)).toUserV1List(users);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("users"));
    }

    /** Test GET when Email then return matching Users. */
    @Test
    void testGetUsersV1_whenEmail_thenReturnMatchingUsers() throws Exception {

        // Given
        final var email = "María & José + / ?";
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().email(email).build();
        final var users = Instancio.createList(User.class);
        final var userV1List = Instancio.createList(UserV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(userV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(users);
        when(this.userMapper.toUserV1List(users)).thenReturn(userV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/users")
                        .queryParam("email", email)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).find(userSearchCriteriaCommand);
        verify(this.userMapper, times(1)).toUserV1List(users);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("users"));
    }

    /** Test GET when Status then return matching Users. */
    @Test
    void testGetUsersV1_whenStatus_thenReturnMatchingUsers() throws Exception {

        // Given
        final var status = Instancio.create(Integer.class);
        final var statusQueryValue = status.toString();
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().statusId(status).build();
        final var users = Instancio.createList(User.class);
        final var userV1List = Instancio.createList(UserV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(userV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(users);
        when(this.userMapper.toUserV1List(users)).thenReturn(userV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/users")
                        .queryParam("status", statusQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).find(userSearchCriteriaCommand);
        verify(this.userMapper, times(1)).toUserV1List(users);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("users"));
    }

    /** Test GET when id is invalid then return bad request. */
    @Test
    void testGetUsersV1_whenIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/users").queryParam("id", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.userRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when status is invalid then return bad request. */
    @Test
    void testGetUsersV1_whenStatusIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/users").queryParam("status", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.userRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }
}
