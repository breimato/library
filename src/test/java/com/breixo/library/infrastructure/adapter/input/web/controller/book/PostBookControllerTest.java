package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.CreateBookCommand;
import com.breixo.library.domain.port.output.BookCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.BookMapper;

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

    /** The book mapper. */
    @Mock
    BookMapper bookMapper;

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
        final var createBookCommand = Instancio.create(CreateBookCommand.class);
        final var book = Instancio.create(Book.class);
        final var bookV1Dto = Instancio.create(BookV1Dto.class);

        // When
        when(this.bookMapper.toCreateBookCommand(any(PostBookV1Request.class))).thenReturn(createBookCommand);
        when(this.bookCreationPersistencePort.execute(createBookCommand)).thenReturn(book);
        when(this.bookMapper.toBookV1(book)).thenReturn(bookV1Dto);

        this.mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(postBookV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.bookMapper, times(1)).toCreateBookCommand(any(PostBookV1Request.class));
        verify(this.bookCreationPersistencePort, times(1)).execute(createBookCommand);
        verify(this.bookMapper, times(1)).toBookV1(book);
    }
}
