package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.UpdateBookCommand;
import com.breixo.library.domain.port.output.BookUpdatePersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchBookV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchBookV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.PatchBookRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.PatchBookResponseMapper;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Patch Book Controller Test. */
@ExtendWith(MockitoExtension.class)
class PatchBookControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/books/{id}";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The patch book controller. */
    @InjectMocks
    PatchBookController patchBookController;

    /** The book update persistence port. */
    @Mock
    BookUpdatePersistencePort bookUpdatePersistencePort;

    /** The patch book request mapper. */
    @Mock
    PatchBookRequestMapper patchBookRequestMapper;

    /** The patch book response mapper. */
    @Mock
    PatchBookResponseMapper patchBookResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.patchBookController).build();
    }

    /**
     * Test patch book v 1 when book exists then return ok response.
     */
    @Test
    void testPatchBookV1_whenBookExists_thenReturnOkResponse() throws Exception {
        // Given
        final var id = Instancio.create(Long.class);
        final var patchBookV1Request = Instancio.create(PatchBookV1Request.class);
        final var updateBookCommand = Instancio.create(UpdateBookCommand.class);
        final var book = Instancio.create(Book.class);
        final var patchBookV1Response = Instancio.create(PatchBookV1Response.class);

        // When
        when(this.patchBookRequestMapper.toUpdateBookCommand(eq(id), any(PatchBookV1Request.class))).thenReturn(updateBookCommand);
        when(this.bookUpdatePersistencePort.execute(updateBookCommand)).thenReturn(book);
        when(this.patchBookResponseMapper.toPatchBookV1Response(book)).thenReturn(patchBookV1Response);

        this.mockMvc.perform(patch(URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(patchBookV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.patchBookRequestMapper, times(1)).toUpdateBookCommand(eq(id), any(PatchBookV1Request.class));
        verify(this.bookUpdatePersistencePort, times(1)).execute(updateBookCommand);
        verify(this.patchBookResponseMapper, times(1)).toPatchBookV1Response(book);
    }
}
