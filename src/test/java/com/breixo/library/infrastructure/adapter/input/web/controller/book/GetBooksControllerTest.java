package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.BookMapper;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Get Books Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetBooksControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/books";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get books controller. */
    @InjectMocks
    GetBooksController getBooksController;

    /** The book retrieval persistence port. */
    @Mock
    BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The book mapper. */
    @Mock
    BookMapper bookMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getBooksController).build();
    }

    /**
     * Test get books v 1 when books exist then return ok response.
     */
    @Test
    void testGetBooksV1_whenBooksExist_thenReturnOkResponse() throws Exception {
        // Given
        final var books = Instancio.createList(Book.class);
        final var bookV1DtoList = Instancio.createList(BookV1Dto.class);

        // When
        when(this.bookRetrievalPersistencePort.findAll()).thenReturn(books);
        when(this.bookMapper.toBookV1List(books)).thenReturn(bookV1DtoList);

        this.mockMvc.perform(get(URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).findAll();
        verify(this.bookMapper, times(1)).toBookV1List(books);
    }
}
