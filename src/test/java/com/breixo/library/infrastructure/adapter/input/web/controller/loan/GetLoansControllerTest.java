package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanMapper;

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

/** The Class Get Loans Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetLoansControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/loans";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get loans controller. */
    @InjectMocks
    GetLoansController getLoansController;

    /** The loan retrieval persistence port. */
    @Mock
    LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The loan mapper. */
    @Mock
    LoanMapper loanMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getLoansController).build();
    }

    /**
     * Test get loans v 1 when loans exist then return ok response.
     */
    @Test
    void testGetLoansV1_whenLoansExist_thenReturnOkResponse() throws Exception {
        // Given
        final var userId = Instancio.create(Integer.class);
        final var loans = Instancio.createList(Loan.class);
        final var loanV1DtoList = Instancio.createList(LoanV1Dto.class);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().userId(userId).build();

        // When
        when(this.loanRetrievalPersistencePort.findAll(loanSearchCriteriaCommand)).thenReturn(loans);
        when(this.loanMapper.toLoanV1List(loans)).thenReturn(loanV1DtoList);

        this.mockMvc.perform(get(URL).param("userId", userId.toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).findAll(loanSearchCriteriaCommand);
        verify(this.loanMapper, times(1)).toLoanV1List(loans);
    }
}
