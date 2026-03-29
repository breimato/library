package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import java.util.Optional;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.exception.FineException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.FineResponseMapper;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Get Fine Id Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetFineIdControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/fines/{id}";

    /** The Mock Mvc. */
    MockMvc mockMvc;

    /** The Get Fine Id Controller. */
    @InjectMocks
    GetFineIdController getFineIdController;

    /** The Fine Retrieval Persistence Port. */
    @Mock
    FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The Fine Response Mapper. */
    @Mock
    FineResponseMapper fineResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getFineIdController).build();
    }

    /**
     * Test get fine id v 1 when fine exists then return ok response.
     */
    @Test
    void testGetFineIdV1_whenFineExists_thenReturnOkResponse() throws Exception {
        
        // Given
        final var id = Instancio.create(Integer.class);
        final var fine = Instancio.create(Fine.class);
        final var fineV1Response = Instancio.create(FineV1Response.class);
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(id).build();

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(Optional.of(fine));
        when(this.fineResponseMapper.toFineV1Response(fine)).thenReturn(fineV1Response);

        this.mockMvc.perform(get(URL, id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineResponseMapper, times(1)).toFineV1Response(fine);
    }

    /**
     * Test get fine id v 1 when fine not found then throw fine exception.
     */
    @Test
    void testGetFineIdV1_whenFineNotFound_thenThrowFineException() {
        
        // Given
        final var id = Instancio.create(Integer.class);
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(id).build();

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(Optional.empty());
        final var fineException = assertThrows(FineException.class,
                () -> this.getFineIdController.getFineIdV1(id));

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineResponseMapper, times(0)).toFineV1Response(null);
        assertEquals(ExceptionMessageConstants.FINE_NOT_FOUND_CODE_ERROR, fineException.getCode());
        assertEquals(ExceptionMessageConstants.FINE_NOT_FOUND_MESSAGE_ERROR, fineException.getMessage());
    }
}
