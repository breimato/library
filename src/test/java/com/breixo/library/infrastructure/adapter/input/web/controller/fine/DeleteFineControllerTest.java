package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import com.breixo.library.domain.port.output.fine.FineDeletionPersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Delete Fine Controller Test. */
@ExtendWith(MockitoExtension.class)
class DeleteFineControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/fines/{id}";

    /** The Mock Mvc. */
    MockMvc mockMvc;

    /** The Delete Fine Controller. */
    @InjectMocks
    DeleteFineController deleteFineController;

    /** The Fine Deletion Persistence Port. */
    @Mock
    FineDeletionPersistencePort fineDeletionPersistencePort;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.deleteFineController).build();
    }

    /**
     * Test delete fine v 1 when fine exists then return no content.
     */
    @Test
    void testDeleteFineV1_whenFineExists_thenReturnNoContent() throws Exception {
        
        // Given
        final var id = Instancio.create(Integer.class);

        // When
        this.mockMvc.perform(delete(URL, id))
                .andExpect(status().isNoContent());

        // Then
        verify(this.fineDeletionPersistencePort, times(1)).execute(id);
    }
}
