package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.port.input.fine.UpdateFineUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1Response;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchFineV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.FineResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.PatchFineRequestMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Patch Fine Controller Test. */
@ExtendWith(MockitoExtension.class)
class PatchFineControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/fines/{id}";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The patch fine controller. */
    @InjectMocks
    PatchFineController patchFineController;

    /** The update fine use case. */
    @Mock
    UpdateFineUseCase updateFineUseCase;

    /** The patch fine request mapper. */
    @Mock
    PatchFineRequestMapper patchFineRequestMapper;

    /** The fine response mapper. */
    @Mock
    FineResponseMapper fineResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.patchFineController).build();
    }

    /**
     * Test patch fine v 1 when request is valid then return ok response.
     */
    @Test
    void testPatchFineV1_whenRequestIsValid_thenReturnOkResponse() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var patchFineV1Request = Instancio.create(PatchFineV1Request.class);
        final var updateFineCommand = Instancio.create(UpdateFineCommand.class);
        final var fine = Instancio.create(Fine.class);
        final var fineV1Response = Instancio.create(FineV1Response.class);

        // When
        when(this.patchFineRequestMapper.toUpdateFineCommand(id, patchFineV1Request)).thenReturn(updateFineCommand);
        when(this.updateFineUseCase.execute(updateFineCommand)).thenReturn(fine);
        when(this.fineResponseMapper.toFineV1Response(fine)).thenReturn(fineV1Response);

        this.mockMvc.perform(patch(URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(patchFineV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.patchFineRequestMapper, times(1)).toUpdateFineCommand(id, patchFineV1Request);
        verify(this.updateFineUseCase, times(1)).execute(updateFineCommand);
        verify(this.fineResponseMapper, times(1)).toFineV1Response(fine);
    }
}
