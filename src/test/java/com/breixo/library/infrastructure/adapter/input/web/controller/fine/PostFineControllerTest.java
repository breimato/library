package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import com.breixo.library.domain.command.fine.CreateFineCommand;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.port.output.fine.FineCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1Response;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostFineV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.FineResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.PostFineRequestMapper;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Post Fine Controller Test. */
@ExtendWith(MockitoExtension.class)
class PostFineControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/fines";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The post fine controller. */
    @InjectMocks
    PostFineController postFineController;

    /** The fine creation persistence port. */
    @Mock
    FineCreationPersistencePort fineCreationPersistencePort;

    /** The post fine request mapper. */
    @Mock
    PostFineRequestMapper postFineRequestMapper;

    /** The fine response mapper. */
    @Mock
    FineResponseMapper fineResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.postFineController).build();
    }

    /**
     * Test post fine v 1 when request is valid then return created response.
     */
    @Test
    void testPostFineV1_whenRequestIsValid_thenReturnCreatedResponse() throws Exception {

        // Given
        final var postFineV1Request = Instancio.create(PostFineV1Request.class);
        final var createFineCommand = Instancio.create(CreateFineCommand.class);
        final var fine = Instancio.create(Fine.class);
        final var fineV1Response = Instancio.create(FineV1Response.class);

        // When
        when(this.postFineRequestMapper.toCreateFineCommand(postFineV1Request)).thenReturn(createFineCommand);
        when(this.fineCreationPersistencePort.execute(createFineCommand)).thenReturn(fine);
        when(this.fineResponseMapper.toFineV1Response(fine)).thenReturn(fineV1Response);

        this.mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(postFineV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.postFineRequestMapper, times(1)).toCreateFineCommand(postFineV1Request);
        verify(this.fineCreationPersistencePort, times(1)).execute(createFineCommand);
        verify(this.fineResponseMapper, times(1)).toFineV1Response(fine);
    }
}
