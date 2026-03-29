package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.port.output.loan.LoanDeletionPersistencePort;

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

/** The Class Delete Loan Controller Test. */
@ExtendWith(MockitoExtension.class)
class DeleteLoanControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/loans/{id}";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The delete loan controller. */
    @InjectMocks
    DeleteLoanController deleteLoanController;

    /** The loan deletion persistence port. */
    @Mock
    LoanDeletionPersistencePort loanDeletionPersistencePort;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.deleteLoanController).build();
    }

    /**
     * Test delete loan v 1 when called then return no content response.
     */
    @Test
    void testDeleteLoanV1_whenCalled_thenReturnNoContentResponse() throws Exception {
        
        // Given
        final var id = Instancio.create(Integer.class);

        // When
        this.mockMvc.perform(delete(URL, id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Then
        verify(this.loanDeletionPersistencePort, times(1)).execute(id);
    }
}
