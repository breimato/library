package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.command.loan.GetUserLoansCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.input.loan.GetUserLoansUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoansV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.GetUserLoansRequestMapper;
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

/** The Class Get User Loans Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetUserLoansControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/users/{id}/loans";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get user loans controller. */
    @InjectMocks
    GetUserLoansController getUserLoansController;

    /** The get user loans use case. */
    @Mock
    GetUserLoansUseCase getUserLoansUseCase;

    /** The get user loans request mapper. */
    @Mock
    GetUserLoansRequestMapper getUserLoansRequestMapper;

    /** The loan mapper. */
    @Mock
    LoanMapper loanMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getUserLoansController).build();
    }

    /**
     * Test get user loans v 1 when valid request then return loans.
     */
    @Test
    void testGetUserLoansV1_whenValidRequest_thenReturnLoans() throws Exception {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var requesterId = Instancio.create(Integer.class);
        final var getUserLoansCommand = Instancio.create(GetUserLoansCommand.class);
        final var loans = Instancio.createList(Loan.class);
        final var loanV1DtoList = Instancio.createList(LoanV1Dto.class);

        // When
        when(this.getUserLoansRequestMapper.toGetUserLoansCommand(requesterId, userId)).thenReturn(getUserLoansCommand);
        when(this.getUserLoansUseCase.execute(getUserLoansCommand)).thenReturn(loans);
        when(this.loanMapper.toLoanV1List(loans)).thenReturn(loanV1DtoList);

        this.mockMvc.perform(get(URL, userId)
                        .header("X-Requester-Id", requesterId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getUserLoansRequestMapper, times(1)).toGetUserLoansCommand(requesterId, userId);
        verify(this.getUserLoansUseCase, times(1)).execute(getUserLoansCommand);
        verify(this.loanMapper, times(1)).toLoanV1List(loans);
    }
}
