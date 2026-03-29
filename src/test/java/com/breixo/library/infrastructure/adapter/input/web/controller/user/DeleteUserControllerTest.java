package com.breixo.library.infrastructure.adapter.input.web.controller.user;

import com.breixo.library.domain.port.output.user.UserDeletionPersistencePort;

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

/** The Class Delete User Controller Test. */
@ExtendWith(MockitoExtension.class)
class DeleteUserControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/users/{id}";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The delete user controller. */
    @InjectMocks
    DeleteUserController deleteUserController;

    /** The user deletion persistence port. */
    @Mock
    UserDeletionPersistencePort userDeletionPersistencePort;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.deleteUserController).build();
    }

    /**
     * Test delete user v1 when called then return no content response.
     */
    @Test
    void testDeleteUserV1_whenCalled_thenReturnNoContentResponse() throws Exception {
        
        // Given
        final var id = Instancio.create(Integer.class);

        // When
        this.mockMvc.perform(delete(URL, id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Then
        verify(this.userDeletionPersistencePort, times(1)).execute(id);
    }
}
