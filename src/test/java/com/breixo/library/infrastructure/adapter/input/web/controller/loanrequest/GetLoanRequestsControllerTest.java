package com.breixo.library.infrastructure.adapter.input.web.controller.loanrequest;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.LoanRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanRequestV1;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.GetLoanRequestsV1RequestMapperImpl;

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

/** The Class Get LoanRequests Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetLoanRequestsControllerTest {

    /** The retrieval persistence port. */
    @Mock
    LoanRequestRetrievalPersistencePort loanRequestRetrievalPersistencePort;

    /** The response mapper. */
    @Mock
    LoanRequestMapper loanRequestMapper;

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    /** Set up the controller with a real request mapper. */
    @BeforeEach
    void setUp() {

        final var getLoanRequestsV1RequestMapper = new GetLoanRequestsV1RequestMapperImpl();
        final var getLoanRequestsController = new GetLoanRequestsController(this.loanRequestRetrievalPersistencePort, getLoanRequestsV1RequestMapper, this.loanRequestMapper);
        this.mockMvc = MockMvcBuilders.standaloneSetup(getLoanRequestsController).build();
    }

    /** Test GET when AllFilters then return matching LoanRequests. */
    @Test
    void testGetLoanRequestsV1_whenAllFilters_thenReturnMatchingLoanRequests() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var userId = Instancio.create(Integer.class);
        final var userIdQueryValue = userId.toString();
        final var bookId = Instancio.create(Integer.class);
        final var bookIdQueryValue = bookId.toString();
        final var status = Instancio.create(Integer.class);
        final var statusQueryValue = status.toString();
        final var loanRequestSearchCriteriaCommand = LoanRequestSearchCriteriaCommand.builder().id(id).userId(userId).bookId(bookId).statusId(status).build();
        final var loanRequests = Instancio.createList(LoanRequest.class);
        final var loanRequestV1List = Instancio.createList(LoanRequestV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanRequestV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRequestRetrievalPersistencePort.find(loanRequestSearchCriteriaCommand)).thenReturn(loanRequests);
        when(this.loanRequestMapper.toLoanRequestV1List(loanRequests)).thenReturn(loanRequestV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loan-requests")
                        .queryParam("id", idQueryValue)
                        .queryParam("userId", userIdQueryValue)
                        .queryParam("bookId", bookIdQueryValue)
                        .queryParam("status", statusQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRequestRetrievalPersistencePort, times(1)).find(loanRequestSearchCriteriaCommand);
        verify(this.loanRequestMapper, times(1)).toLoanRequestV1List(loanRequests);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loanRequests"));
    }

    /** Test GET when NoFilters then return matching LoanRequests. */
    @Test
    void testGetLoanRequestsV1_whenNoFilters_thenReturnMatchingLoanRequests() throws Exception {

        // Given
        final var loanRequestSearchCriteriaCommand = LoanRequestSearchCriteriaCommand.builder().build();
        final var loanRequests = Instancio.createList(LoanRequest.class);
        final var loanRequestV1List = Instancio.createList(LoanRequestV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanRequestV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRequestRetrievalPersistencePort.find(loanRequestSearchCriteriaCommand)).thenReturn(loanRequests);
        when(this.loanRequestMapper.toLoanRequestV1List(loanRequests)).thenReturn(loanRequestV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loan-requests")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRequestRetrievalPersistencePort, times(1)).find(loanRequestSearchCriteriaCommand);
        verify(this.loanRequestMapper, times(1)).toLoanRequestV1List(loanRequests);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loanRequests"));
    }

    /** Test GET when Id then return matching LoanRequests. */
    @Test
    void testGetLoanRequestsV1_whenId_thenReturnMatchingLoanRequests() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var loanRequestSearchCriteriaCommand = LoanRequestSearchCriteriaCommand.builder().id(id).build();
        final var loanRequests = Instancio.createList(LoanRequest.class);
        final var loanRequestV1List = Instancio.createList(LoanRequestV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanRequestV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRequestRetrievalPersistencePort.find(loanRequestSearchCriteriaCommand)).thenReturn(loanRequests);
        when(this.loanRequestMapper.toLoanRequestV1List(loanRequests)).thenReturn(loanRequestV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loan-requests")
                        .queryParam("id", idQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRequestRetrievalPersistencePort, times(1)).find(loanRequestSearchCriteriaCommand);
        verify(this.loanRequestMapper, times(1)).toLoanRequestV1List(loanRequests);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loanRequests"));
    }

    /** Test GET when UserId then return matching LoanRequests. */
    @Test
    void testGetLoanRequestsV1_whenUserId_thenReturnMatchingLoanRequests() throws Exception {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var userIdQueryValue = userId.toString();
        final var loanRequestSearchCriteriaCommand = LoanRequestSearchCriteriaCommand.builder().userId(userId).build();
        final var loanRequests = Instancio.createList(LoanRequest.class);
        final var loanRequestV1List = Instancio.createList(LoanRequestV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanRequestV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRequestRetrievalPersistencePort.find(loanRequestSearchCriteriaCommand)).thenReturn(loanRequests);
        when(this.loanRequestMapper.toLoanRequestV1List(loanRequests)).thenReturn(loanRequestV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loan-requests")
                        .queryParam("userId", userIdQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRequestRetrievalPersistencePort, times(1)).find(loanRequestSearchCriteriaCommand);
        verify(this.loanRequestMapper, times(1)).toLoanRequestV1List(loanRequests);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loanRequests"));
    }

    /** Test GET when BookId then return matching LoanRequests. */
    @Test
    void testGetLoanRequestsV1_whenBookId_thenReturnMatchingLoanRequests() throws Exception {

        // Given
        final var bookId = Instancio.create(Integer.class);
        final var bookIdQueryValue = bookId.toString();
        final var loanRequestSearchCriteriaCommand = LoanRequestSearchCriteriaCommand.builder().bookId(bookId).build();
        final var loanRequests = Instancio.createList(LoanRequest.class);
        final var loanRequestV1List = Instancio.createList(LoanRequestV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanRequestV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRequestRetrievalPersistencePort.find(loanRequestSearchCriteriaCommand)).thenReturn(loanRequests);
        when(this.loanRequestMapper.toLoanRequestV1List(loanRequests)).thenReturn(loanRequestV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loan-requests")
                        .queryParam("bookId", bookIdQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRequestRetrievalPersistencePort, times(1)).find(loanRequestSearchCriteriaCommand);
        verify(this.loanRequestMapper, times(1)).toLoanRequestV1List(loanRequests);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loanRequests"));
    }

    /** Test GET when Status then return matching LoanRequests. */
    @Test
    void testGetLoanRequestsV1_whenStatus_thenReturnMatchingLoanRequests() throws Exception {

        // Given
        final var status = Instancio.create(Integer.class);
        final var statusQueryValue = status.toString();
        final var loanRequestSearchCriteriaCommand = LoanRequestSearchCriteriaCommand.builder().statusId(status).build();
        final var loanRequests = Instancio.createList(LoanRequest.class);
        final var loanRequestV1List = Instancio.createList(LoanRequestV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanRequestV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRequestRetrievalPersistencePort.find(loanRequestSearchCriteriaCommand)).thenReturn(loanRequests);
        when(this.loanRequestMapper.toLoanRequestV1List(loanRequests)).thenReturn(loanRequestV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loan-requests")
                        .queryParam("status", statusQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRequestRetrievalPersistencePort, times(1)).find(loanRequestSearchCriteriaCommand);
        verify(this.loanRequestMapper, times(1)).toLoanRequestV1List(loanRequests);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loanRequests"));
    }

    /** Test GET when id is invalid then return bad request. */
    @Test
    void testGetLoanRequestsV1_whenIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loan-requests").queryParam("id", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.loanRequestRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when userId is invalid then return bad request. */
    @Test
    void testGetLoanRequestsV1_whenUserIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loan-requests").queryParam("userId", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.loanRequestRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when bookId is invalid then return bad request. */
    @Test
    void testGetLoanRequestsV1_whenBookIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loan-requests").queryParam("bookId", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.loanRequestRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when status is invalid then return bad request. */
    @Test
    void testGetLoanRequestsV1_whenStatusIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loan-requests").queryParam("status", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.loanRequestRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }
}
