package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.CreateBookCommand;
import com.breixo.library.domain.model.vo.Isbn;
import com.breixo.library.domain.port.output.BookCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.PostBookRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.PostBookResponseMapper;

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

import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Post Book Controller Test. */
@ExtendWith(MockitoExtension.class)
class PostBookControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/books";

    /** The Constant VALID_ISBN. */
    static final String VALID_ISBN = "9780134685991";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The post book controller. */
    @InjectMocks
    PostBookController postBookController;

    /** The book creation persistence port. */
    @Mock
    BookCreationPersistencePort bookCreationPersistencePort;

    /** The post book request mapper. */
    @Mock
    PostBookRequestMapper postBookRequestMapper;

    /** The post book response mapper. */
    @Mock
    PostBookResponseMapper postBookResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.postBookController).build();
    }

    /**
     * Test post book v 1 when request is valid then return created response.
     */
    @Test
    void testPostBookV1_whenRequestIsValid_thenReturnCreatedResponse() throws Exception {
        // Given
        final var postBookV1Request = Instancio.create(PostBookV1Request.class);
        final var createBookCommand = Instancio.of(CreateBookCommand.class)
                .set(field(CreateBookCommand.class, "isbn"), new Isbn(VALID_ISBN))
                .create();
        final var book = Instancio.of(Book.class)
                .set(field(Book.class, "isbn"), new Isbn(VALID_ISBN))
                .create();
        final var postBookV1Response = Instancio.create(PostBookV1Response.class);

        // When
        when(this.postBookRequestMapper.toCreateBookCommand(any(PostBookV1Request.class))).thenReturn(createBookCommand);
        when(this.bookCreationPersistencePort.execute(createBookCommand)).thenReturn(book);
        when(this.postBookResponseMapper.toPostBookV1Response(book)).thenReturn(postBookV1Response);

        this.mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(postBookV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.postBookRequestMapper, times(1)).toCreateBookCommand(any(PostBookV1Request.class));
        verify(this.bookCreationPersistencePort, times(1)).execute(createBookCommand);
        verify(this.postBookResponseMapper, times(1)).toPostBookV1Response(book);
    }
}
