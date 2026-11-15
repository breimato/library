package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.BookMapper;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.GetBooksV1RequestMapperImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Get Books Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetBooksControllerTest {

    /** The retrieval persistence port. */
    @Mock
    BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The response mapper. */
    @Mock
    BookMapper bookMapper;

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    /** Set up the controller with a real request mapper. */
    @BeforeEach
    void setUp() {

        final var getBooksV1RequestMapper = new GetBooksV1RequestMapperImpl();
        final var getBooksController = new GetBooksController(this.bookRetrievalPersistencePort, getBooksV1RequestMapper, this.bookMapper);
        this.mockMvc = MockMvcBuilders.standaloneSetup(getBooksController).build();
    }

    /** Test GET when AllFilters then return matching Books. */
    @Test
    void testGetBooksV1_whenAllFilters_thenReturnMatchingBooks() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var isbn = "María & José + / ?";
        final var title = "María & José + / ?";
        final var author = "María & José + / ?";
        final var genre = "María & José + / ?";
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(id).isbn(isbn).title(title).author(author).genre(genre).build();
        final var books = Instancio.createList(Book.class);
        final var bookV1List = Instancio.createList(BookV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(bookV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(books);
        when(this.bookMapper.toBookV1List(books)).thenReturn(bookV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/books")
                        .queryParam("id", idQueryValue)
                        .queryParam("isbn", isbn)
                        .queryParam("title", title)
                        .queryParam("author", author)
                        .queryParam("genre", genre)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookMapper, times(1)).toBookV1List(books);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("books"));
    }

    /** Test GET when NoFilters then return matching Books. */
    @Test
    void testGetBooksV1_whenNoFilters_thenReturnMatchingBooks() throws Exception {

        // Given
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().build();
        final var books = Instancio.createList(Book.class);
        final var bookV1List = Instancio.createList(BookV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(bookV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(books);
        when(this.bookMapper.toBookV1List(books)).thenReturn(bookV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookMapper, times(1)).toBookV1List(books);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("books"));
    }

    /** Test GET when Id then return matching Books. */
    @Test
    void testGetBooksV1_whenId_thenReturnMatchingBooks() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(id).build();
        final var books = Instancio.createList(Book.class);
        final var bookV1List = Instancio.createList(BookV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(bookV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(books);
        when(this.bookMapper.toBookV1List(books)).thenReturn(bookV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/books")
                        .queryParam("id", idQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookMapper, times(1)).toBookV1List(books);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("books"));
    }

    /** Test GET when Isbn then return matching Books. */
    @Test
    void testGetBooksV1_whenIsbn_thenReturnMatchingBooks() throws Exception {

        // Given
        final var isbn = "María & José + / ?";
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().isbn(isbn).build();
        final var books = Instancio.createList(Book.class);
        final var bookV1List = Instancio.createList(BookV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(bookV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(books);
        when(this.bookMapper.toBookV1List(books)).thenReturn(bookV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/books")
                        .queryParam("isbn", isbn)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookMapper, times(1)).toBookV1List(books);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("books"));
    }

    /** Test GET when Title then return matching Books. */
    @Test
    void testGetBooksV1_whenTitle_thenReturnMatchingBooks() throws Exception {

        // Given
        final var title = "María & José + / ?";
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().title(title).build();
        final var books = Instancio.createList(Book.class);
        final var bookV1List = Instancio.createList(BookV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(bookV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(books);
        when(this.bookMapper.toBookV1List(books)).thenReturn(bookV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/books")
                        .queryParam("title", title)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookMapper, times(1)).toBookV1List(books);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("books"));
    }

    /** Test GET when Author then return matching Books. */
    @Test
    void testGetBooksV1_whenAuthor_thenReturnMatchingBooks() throws Exception {

        // Given
        final var author = "María & José + / ?";
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().author(author).build();
        final var books = Instancio.createList(Book.class);
        final var bookV1List = Instancio.createList(BookV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(bookV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(books);
        when(this.bookMapper.toBookV1List(books)).thenReturn(bookV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/books")
                        .queryParam("author", author)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookMapper, times(1)).toBookV1List(books);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("books"));
    }

    /** Test GET when Genre then return matching Books. */
    @Test
    void testGetBooksV1_whenGenre_thenReturnMatchingBooks() throws Exception {

        // Given
        final var genre = "María & José + / ?";
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().genre(genre).build();
        final var books = Instancio.createList(Book.class);
        final var bookV1List = Instancio.createList(BookV1Dto.class);
        final var expectedJson = this.objectMapper.writeValueAsString(bookV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(books);
        when(this.bookMapper.toBookV1List(books)).thenReturn(bookV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/books")
                        .queryParam("genre", genre)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookMapper, times(1)).toBookV1List(books);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("books"));
    }

    /** Test GET when id is invalid then return bad request. */
    @Test
    void testGetBooksV1_whenIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/books").queryParam("id", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.bookRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }
}
