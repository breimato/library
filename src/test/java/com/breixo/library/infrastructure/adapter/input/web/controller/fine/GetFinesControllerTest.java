package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import java.util.List;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetFinesV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.FineMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.GetFinesV1RequestMapper;

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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Get Fines Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetFinesControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/fines";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get fines controller. */
    @InjectMocks
    GetFinesController getFinesController;

    /** The fine retrieval persistence port. */
    @Mock
    FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The get fines V1 request mapper. */
    @Mock
    GetFinesV1RequestMapper getFinesV1RequestMapper;

    /** The fine mapper. */
    @Mock
    FineMapper fineMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        var auth = new UsernamePasswordAuthenticationToken(
            1, null, List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getFinesController).build();
    }

    /** Tear down. */
    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    /**
     * Test get fines v 1 when request is valid then return ok response.
     */
    @Test
    void testGetFinesV1_whenRequestIsValid_thenReturnOkResponse() throws Exception {

        // Given
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().build();
        final var fines = Instancio.createList(Fine.class);
        final var fineV1List = Instancio.createList(FineV1.class);

        // When
        when(this.getFinesV1RequestMapper.toFineSearchCriteriaCommand(null)).thenReturn(fineSearchCriteriaCommand);
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(fines);
        when(this.fineMapper.toFineV1List(fines)).thenReturn(fineV1List);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getFinesV1RequestMapper, times(1)).toFineSearchCriteriaCommand(null);
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineMapper, times(1)).toFineV1List(fines);
    }

    /**
     * Test get fines v 1 when request is null then return ok response.
     */
    @Test
    void testGetFinesV1_whenRequestIsNull_thenReturnOkResponse() throws Exception {

        // Given
        final var getFinesV1Request = Instancio.create(GetFinesV1Request.class);
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().build();
        final var fines = Instancio.createList(Fine.class);
        final var fineV1List = Instancio.createList(FineV1.class);

        // When
        when(this.getFinesV1RequestMapper.toFineSearchCriteriaCommand(getFinesV1Request)).thenReturn(fineSearchCriteriaCommand);
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(fines);
        when(this.fineMapper.toFineV1List(fines)).thenReturn(fineV1List);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getFinesV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getFinesV1RequestMapper, times(1)).toFineSearchCriteriaCommand(getFinesV1Request);
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineMapper, times(1)).toFineV1List(fines);
    }
}
