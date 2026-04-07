package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.port.output.book.BookDeletionPersistencePort;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Delete Book Controller Test. */
@ExtendWith(MockitoExtension.class)
class DeleteBookControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/books/{id}";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The delete book controller. */
    @InjectMocks
    DeleteBookController deleteBookController;

    /** The book deletion persistence port. */
    @Mock
    BookDeletionPersistencePort bookDeletionPersistencePort;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.deleteBookController).build();
    }

    /**
     * Test delete book v 1 when called then return no content response.
     */
    @Test
    void testDeleteBookV1_whenCalled_thenReturnNoContentResponse() throws Exception {
        
        // Given
        final var id = Instancio.create(Integer.class);
        final var requesterId = Instancio.create(Integer.class);

        // When
        this.mockMvc.perform(delete(URL, id)
                        .header("X-Requester-Id", requesterId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Then
        verify(this.bookDeletionPersistencePort, times(1)).execute(id);
    }
}
