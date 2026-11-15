package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.FineMapper;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.GetFinesV1RequestMapperImpl;

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

/** The Class Get Fines Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetFinesControllerTest {

    /** The retrieval persistence port. */
    @Mock
    FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The response mapper. */
    @Mock
    FineMapper fineMapper;

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    /** Set up the controller with a real request mapper. */
    @BeforeEach
    void setUp() {

        final var getFinesV1RequestMapper = new GetFinesV1RequestMapperImpl();
        final var getFinesController = new GetFinesController(this.fineRetrievalPersistencePort, getFinesV1RequestMapper, this.fineMapper);
        this.mockMvc = MockMvcBuilders.standaloneSetup(getFinesController).build();
    }

    /** Test GET when AllFilters then return matching Fines. */
    @Test
    void testGetFinesV1_whenAllFilters_thenReturnMatchingFines() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var loanId = Instancio.create(Integer.class);
        final var loanIdQueryValue = loanId.toString();
        final var status = Instancio.create(Integer.class);
        final var statusQueryValue = status.toString();
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(id).loanId(loanId).statusId(status).build();
        final var fines = Instancio.createList(Fine.class);
        final var fineV1List = Instancio.createList(FineV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(fineV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(fines);
        when(this.fineMapper.toFineV1List(fines)).thenReturn(fineV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/fines")
                        .queryParam("id", idQueryValue)
                        .queryParam("loanId", loanIdQueryValue)
                        .queryParam("status", statusQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineMapper, times(1)).toFineV1List(fines);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("fines"));
    }

    /** Test GET when NoFilters then return matching Fines. */
    @Test
    void testGetFinesV1_whenNoFilters_thenReturnMatchingFines() throws Exception {

        // Given
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().build();
        final var fines = Instancio.createList(Fine.class);
        final var fineV1List = Instancio.createList(FineV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(fineV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(fines);
        when(this.fineMapper.toFineV1List(fines)).thenReturn(fineV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/fines")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineMapper, times(1)).toFineV1List(fines);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("fines"));
    }

    /** Test GET when Id then return matching Fines. */
    @Test
    void testGetFinesV1_whenId_thenReturnMatchingFines() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(id).build();
        final var fines = Instancio.createList(Fine.class);
        final var fineV1List = Instancio.createList(FineV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(fineV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(fines);
        when(this.fineMapper.toFineV1List(fines)).thenReturn(fineV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/fines")
                        .queryParam("id", idQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineMapper, times(1)).toFineV1List(fines);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("fines"));
    }

    /** Test GET when LoanId then return matching Fines. */
    @Test
    void testGetFinesV1_whenLoanId_thenReturnMatchingFines() throws Exception {

        // Given
        final var loanId = Instancio.create(Integer.class);
        final var loanIdQueryValue = loanId.toString();
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().loanId(loanId).build();
        final var fines = Instancio.createList(Fine.class);
        final var fineV1List = Instancio.createList(FineV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(fineV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(fines);
        when(this.fineMapper.toFineV1List(fines)).thenReturn(fineV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/fines")
                        .queryParam("loanId", loanIdQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineMapper, times(1)).toFineV1List(fines);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("fines"));
    }

    /** Test GET when Status then return matching Fines. */
    @Test
    void testGetFinesV1_whenStatus_thenReturnMatchingFines() throws Exception {

        // Given
        final var status = Instancio.create(Integer.class);
        final var statusQueryValue = status.toString();
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().statusId(status).build();
        final var fines = Instancio.createList(Fine.class);
        final var fineV1List = Instancio.createList(FineV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(fineV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(fines);
        when(this.fineMapper.toFineV1List(fines)).thenReturn(fineV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/fines")
                        .queryParam("status", statusQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineMapper, times(1)).toFineV1List(fines);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("fines"));
    }

    /** Test GET when id is invalid then return bad request. */
    @Test
    void testGetFinesV1_whenIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/fines").queryParam("id", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.fineRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when loanId is invalid then return bad request. */
    @Test
    void testGetFinesV1_whenLoanIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/fines").queryParam("loanId", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.fineRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when status is invalid then return bad request. */
    @Test
    void testGetFinesV1_whenStatusIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/fines").queryParam("status", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.fineRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }
}
