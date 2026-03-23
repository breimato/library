package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.BookSearchCriteriaCommand;
import com.breixo.library.domain.port.output.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetBookIdV1Response;
import com.breixo.library.infrastructure.adapter.input.web.handler.GlobalExceptionHandler;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.GetBookResponseMapper;

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

    /** The get book response mapper. */
    @Mock
    GetBookResponseMapper getBookResponseMapper;

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
        final var getBookIdV1Response = Instancio.create(GetBookIdV1Response.class);

        // When
        when(this.bookRetrievalPersistencePort.execute(any(BookSearchCriteriaCommand.class))).thenReturn(book);
        when(this.getBookResponseMapper.toGetBookIdV1Response(book)).thenReturn(getBookIdV1Response);

        this.mockMvc.perform(get(URL, id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).execute(any(BookSearchCriteriaCommand.class));
        verify(this.getBookResponseMapper, times(1)).toGetBookIdV1Response(book);
    }

    /**
     * Test get book id v 1 when book not found then return not found response.
     */
    @Test
    void testGetBookIdV1_whenBookNotFound_thenReturnNotFoundResponse() throws Exception {
        // Given
        final var id = Instancio.create(Long.class);

        // When
        when(this.bookRetrievalPersistencePort.execute(any(BookSearchCriteriaCommand.class)))
                .thenThrow(new BookException(
                        ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR));

        this.mockMvc.perform(get(URL, id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).execute(any(BookSearchCriteriaCommand.class));
    }
}
