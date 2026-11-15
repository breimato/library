package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanMapper;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.GetLoansV1RequestMapperImpl;

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

/** The Class Get Loans Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetLoansControllerTest {

    /** The retrieval persistence port. */
    @Mock
    LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The response mapper. */
    @Mock
    LoanMapper loanMapper;

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    /** Set up the controller with a real request mapper. */
    @BeforeEach
    void setUp() {

        final var getLoansV1RequestMapper = new GetLoansV1RequestMapperImpl();
        final var getLoansController = new GetLoansController(this.loanRetrievalPersistencePort, getLoansV1RequestMapper, this.loanMapper);
        this.mockMvc = MockMvcBuilders.standaloneSetup(getLoansController).build();
    }

    /** Test GET when AllFilters then return matching Loans. */
    @Test
    void testGetLoansV1_whenAllFilters_thenReturnMatchingLoans() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var userId = Instancio.create(Integer.class);
        final var userIdQueryValue = userId.toString();
        final var bookId = Instancio.create(Integer.class);
        final var bookIdQueryValue = bookId.toString();
        final var status = Instancio.create(Integer.class);
        final var statusQueryValue = status.toString();
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(id).userId(userId).bookId(bookId).statusId(status).build();
        final var loans = Instancio.createList(Loan.class);
        final var loanV1List = Instancio.createList(LoanV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loans);
        when(this.loanMapper.toLoanV1List(loans)).thenReturn(loanV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loans")
                        .queryParam("id", idQueryValue)
                        .queryParam("userId", userIdQueryValue)
                        .queryParam("bookId", bookIdQueryValue)
                        .queryParam("status", statusQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanMapper, times(1)).toLoanV1List(loans);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loans"));
    }

    /** Test GET when NoFilters then return matching Loans. */
    @Test
    void testGetLoansV1_whenNoFilters_thenReturnMatchingLoans() throws Exception {

        // Given
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().build();
        final var loans = Instancio.createList(Loan.class);
        final var loanV1List = Instancio.createList(LoanV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loans);
        when(this.loanMapper.toLoanV1List(loans)).thenReturn(loanV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loans")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanMapper, times(1)).toLoanV1List(loans);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loans"));
    }

    /** Test GET when Id then return matching Loans. */
    @Test
    void testGetLoansV1_whenId_thenReturnMatchingLoans() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(id).build();
        final var loans = Instancio.createList(Loan.class);
        final var loanV1List = Instancio.createList(LoanV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loans);
        when(this.loanMapper.toLoanV1List(loans)).thenReturn(loanV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loans")
                        .queryParam("id", idQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanMapper, times(1)).toLoanV1List(loans);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loans"));
    }

    /** Test GET when UserId then return matching Loans. */
    @Test
    void testGetLoansV1_whenUserId_thenReturnMatchingLoans() throws Exception {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var userIdQueryValue = userId.toString();
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().userId(userId).build();
        final var loans = Instancio.createList(Loan.class);
        final var loanV1List = Instancio.createList(LoanV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loans);
        when(this.loanMapper.toLoanV1List(loans)).thenReturn(loanV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loans")
                        .queryParam("userId", userIdQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanMapper, times(1)).toLoanV1List(loans);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loans"));
    }

    /** Test GET when BookId then return matching Loans. */
    @Test
    void testGetLoansV1_whenBookId_thenReturnMatchingLoans() throws Exception {

        // Given
        final var bookId = Instancio.create(Integer.class);
        final var bookIdQueryValue = bookId.toString();
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().bookId(bookId).build();
        final var loans = Instancio.createList(Loan.class);
        final var loanV1List = Instancio.createList(LoanV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loans);
        when(this.loanMapper.toLoanV1List(loans)).thenReturn(loanV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loans")
                        .queryParam("bookId", bookIdQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanMapper, times(1)).toLoanV1List(loans);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loans"));
    }

    /** Test GET when Status then return matching Loans. */
    @Test
    void testGetLoansV1_whenStatus_thenReturnMatchingLoans() throws Exception {

        // Given
        final var status = Instancio.create(Integer.class);
        final var statusQueryValue = status.toString();
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().statusId(status).build();
        final var loans = Instancio.createList(Loan.class);
        final var loanV1List = Instancio.createList(LoanV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(loanV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loans);
        when(this.loanMapper.toLoanV1List(loans)).thenReturn(loanV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loans")
                        .queryParam("status", statusQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanMapper, times(1)).toLoanV1List(loans);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("loans"));
    }

    /** Test GET when id is invalid then return bad request. */
    @Test
    void testGetLoansV1_whenIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loans").queryParam("id", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.loanRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when userId is invalid then return bad request. */
    @Test
    void testGetLoansV1_whenUserIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loans").queryParam("userId", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.loanRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when bookId is invalid then return bad request. */
    @Test
    void testGetLoansV1_whenBookIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loans").queryParam("bookId", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.loanRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when status is invalid then return bad request. */
    @Test
    void testGetLoansV1_whenStatusIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/loans").queryParam("status", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.loanRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }
}
