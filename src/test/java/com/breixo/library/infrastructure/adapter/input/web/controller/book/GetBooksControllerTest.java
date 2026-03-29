package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetBooksV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.BookMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.GetBooksV1RequestMapper;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Get Books Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetBooksControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/books";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get books controller. */
    @InjectMocks
    GetBooksController getBooksController;

    /** The book retrieval persistence port. */
    @Mock
    BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The get books V1 request mapper. */
    @Mock
    GetBooksV1RequestMapper getBooksV1RequestMapper;

    /** The book mapper. */
    @Mock
    BookMapper bookMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getBooksController).build();
    }

    /**
     * Test get books v 1 when no criteria then return all books.
     */
    @Test
    void testGetBooksV1_whenNoCriteria_thenReturnAllBooks() throws Exception {
        
        // Given
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().build();
        final var books = Instancio.createList(Book.class);
        final var bookV1DtoList = Instancio.createList(BookV1Dto.class);

        // When
        when(this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(null)).thenReturn(bookSearchCriteriaCommand);
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(books);
        when(this.bookMapper.toBookV1List(books)).thenReturn(bookV1DtoList);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getBooksV1RequestMapper, times(1)).toBookSearchCriteriaCommand(null);
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookMapper, times(1)).toBookV1List(books);
    }

    /**
     * Test get books v 1 when criteria provided then return filtered books.
     */
    @Test
    void testGetBooksV1_whenCriteriaProvided_thenReturnFilteredBooks() throws Exception {
        
        // Given
        final var getBooksV1Request = Instancio.create(GetBooksV1Request.class);
        getBooksV1Request.setAuthor("Martin");
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder()
                .author("Martin")
                .build();
        final var books = Instancio.createList(Book.class);
        final var bookV1DtoList = Instancio.createList(BookV1Dto.class);

        // When
        when(this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(getBooksV1Request)).thenReturn(bookSearchCriteriaCommand);
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(books);
        when(this.bookMapper.toBookV1List(books)).thenReturn(bookV1DtoList);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getBooksV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getBooksV1RequestMapper, times(1)).toBookSearchCriteriaCommand(getBooksV1Request);
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookMapper, times(1)).toBookV1List(books);
    }

    /**
     * Test get books v 1 when id provided then return matching books.
     */
    @Test
    void testGetBooksV1_whenIdProvided_thenReturnMatchingBooks() throws Exception {
        
        // Given
        final var id = Instancio.create(Integer.class);
        final var getBooksV1Request = Instancio.create(GetBooksV1Request.class);
        getBooksV1Request.setId(id);
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder()
                .id(id)
                .build();
        final var books = Instancio.createList(Book.class);
        final var bookV1DtoList = Instancio.createList(BookV1Dto.class);

        // When
        when(this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(getBooksV1Request)).thenReturn(bookSearchCriteriaCommand);
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(books);
        when(this.bookMapper.toBookV1List(books)).thenReturn(bookV1DtoList);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getBooksV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getBooksV1RequestMapper, times(1)).toBookSearchCriteriaCommand(getBooksV1Request);
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookMapper, times(1)).toBookV1List(books);
    }
}
