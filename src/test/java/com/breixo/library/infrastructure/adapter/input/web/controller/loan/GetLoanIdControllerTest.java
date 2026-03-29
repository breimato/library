package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import java.util.Optional;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanResponseMapper;

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

/** The Class Get Loan Id Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetLoanIdControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/loans/{id}";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get loan id controller. */
    @InjectMocks
    GetLoanIdController getLoanIdController;

    /** The loan retrieval persistence port. */
    @Mock
    LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The loan response mapper. */
    @Mock
    LoanResponseMapper loanResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getLoanIdController).build();
    }

    /**
     * Test get loan id v 1 when loan exists then return ok response.
     */
    @Test
    void testGetLoanIdV1_whenLoanExists_thenReturnOkResponse() throws Exception {
        // Given
        final var id = Instancio.create(Integer.class);
        final var loan = Instancio.create(Loan.class);
        final var loanV1ResponseDto = Instancio.create(LoanV1ResponseDto.class);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(id).build();

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(Optional.of(loan));
        when(this.loanResponseMapper.toLoanV1Response(loan)).thenReturn(loanV1ResponseDto);

        this.mockMvc.perform(get(URL, id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanResponseMapper, times(1)).toLoanV1Response(loan);
    }

    /**
     * Test get loan id v 1 when loan not found then throw loan exception.
     */
    @Test
    void testGetLoanIdV1_whenLoanNotFound_thenThrowLoanException() {
        // Given
        final var id = Instancio.create(Integer.class);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(id).build();

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(Optional.empty());
        final var loanException = assertThrows(LoanException.class,
                () -> this.getLoanIdController.getLoanIdV1(id));

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verifyNoInteractions(this.loanResponseMapper);
        assertEquals(ExceptionMessageConstants.LOAN_NOT_FOUND_CODE_ERROR, loanException.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE_ERROR, loanException.getMessage());
    }
}
