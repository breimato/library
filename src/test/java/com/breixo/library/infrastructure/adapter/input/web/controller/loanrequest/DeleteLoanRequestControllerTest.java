package com.breixo.library.infrastructure.adapter.input.web.controller.loanrequest;

import com.breixo.library.domain.port.output.loanrequest.LoanRequestDeletionPersistencePort;

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

/** The Class Delete Loan Request Controller Test. */
@ExtendWith(MockitoExtension.class)
class DeleteLoanRequestControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/loan-requests/{id}";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The delete loan request controller. */
    @InjectMocks
    DeleteLoanRequestController deleteLoanRequestController;

    /** The loan request deletion persistence port. */
    @Mock
    LoanRequestDeletionPersistencePort loanRequestDeletionPersistencePort;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.deleteLoanRequestController).build();
    }

    /**
     * Test delete loan request v1 when loan request exists then return no content.
     */
    @Test
    void testDeleteLoanRequestV1_whenLoanRequestExists_thenReturnNoContent() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        this.mockMvc.perform(delete(URL, id))
                .andExpect(status().isNoContent());

        // Then
        verify(this.loanRequestDeletionPersistencePort, times(1)).execute(id);
    }
}
