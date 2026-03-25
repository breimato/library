package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import java.util.Optional;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.BookResponseMapper;

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
import static org.mockito.Mockito.verifyNoInteractions;
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

    /** The book response mapper. */
    @Mock
    BookResponseMapper bookResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getBookIdController).build();
    }

    /**
     * Test get book id v 1 when book exists then return ok response.
     */
    @Test
    void testGetBookIdV1_whenBookExists_thenReturnOkResponse() throws Exception {
        // Given
        final var id = Instancio.create(Long.class);
        final var book = Instancio.create(Book.class);
        final var bookV1ResponseDto = Instancio.create(BookV1ResponseDto.class);
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(id).build();

        // When
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(Optional.of(book));
        when(this.bookResponseMapper.toBookV1Response(book)).thenReturn(bookV1ResponseDto);

        this.mockMvc.perform(get(URL, id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookResponseMapper, times(1)).toBookV1Response(book);
    }

    /**
     * Test get book id v 1 when book not found then throw book exception.
     */
    @Test
    void testGetBookIdV1_whenBookNotFound_thenThrowBookException() {
        // Given
        final var id = Instancio.create(Long.class);
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(id).build();

        // When
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(Optional.empty());
        final var bookException = assertThrows(BookException.class,
                () -> this.getBookIdController.getBookIdV1(id));

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verifyNoInteractions(this.bookResponseMapper);
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR, bookException.getCode());
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR, bookException.getMessage());
    }
}
