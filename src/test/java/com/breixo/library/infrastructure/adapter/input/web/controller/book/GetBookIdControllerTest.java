package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import java.util.List;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.FindBookCommand;
import com.breixo.library.domain.port.output.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.BookMapper;

import com.breixo.library.infrastructure.adapter.input.web.handler.GlobalExceptionHandler;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Get Book Id Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetBookIdControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/books/{id}";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get book id controller. */
    @InjectMocks
    GetBookIdController getBookIdController;

    /** The book retrieval persistence port. */
    @Mock
    BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The book mapper. */
    @Mock
    BookMapper bookMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getBookIdController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    /**
     * Test get book id v 1 when book exists then return ok response.
     */
    @Test
    void testGetBookIdV1_whenBookExists_thenReturnOkResponse() throws Exception {
        // Given
        final var id = Instancio.create(Long.class);
        final var book = Instancio.create(Book.class);
        final var bookV1Dto = Instancio.create(BookV1Dto.class);

        // When
        when(this.bookRetrievalPersistencePort.execute(any(FindBookCommand.class))).thenReturn(List.of(book));
        when(this.bookMapper.toBookV1(book)).thenReturn(bookV1Dto);

        this.mockMvc.perform(get(URL, id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).execute(any(FindBookCommand.class));
        verify(this.bookMapper, times(1)).toBookV1(book);
    }

    /**
     * Test get book id v 1 when book not found then return not found response.
     */
    @Test
    void testGetBookIdV1_whenBookNotFound_thenReturnNotFoundResponse() throws Exception {
        // Given
        final var id = Instancio.create(Long.class);

        // When
        when(this.bookRetrievalPersistencePort.execute(any(FindBookCommand.class))).thenReturn(List.of());

        this.mockMvc.perform(get(URL, id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).execute(any(FindBookCommand.class));
    }
}
