package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.command.loan.UpdateLoanRenewCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.input.loan.UpdateLoanRenewUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanRenewV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.PatchLoanRenewRequestMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Patch Loan Renew Controller Test. */
@ExtendWith(MockitoExtension.class)
class PatchLoanRenewControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/loans/{id}/renew";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The patch loan renew controller. */
    @InjectMocks
    PatchLoanRenewController patchLoanRenewController;

    /** The update loan renew use case. */
    @Mock
    UpdateLoanRenewUseCase updateLoanRenewUseCase;

    /** The patch loan renew request mapper. */
    @Mock
    PatchLoanRenewRequestMapper patchLoanRenewRequestMapper;

    /** The loan response mapper. */
    @Mock
    LoanResponseMapper loanResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.patchLoanRenewController).build();
    }

    /**
     * Test patch loan renew v 1 when loan exists then return ok response.
     */
    @Test
    void testPatchLoanRenewV1_whenLoanExists_thenReturnOkResponse() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var patchLoanRenewV1Request = Instancio.create(PatchLoanRenewV1Request.class);
        final var updateLoanRenewCommand = Instancio.create(UpdateLoanRenewCommand.class);
        final var loan = Instancio.create(Loan.class);
        final var loanV1ResponseDto = Instancio.create(LoanV1ResponseDto.class);

        // When
        when(this.patchLoanRenewRequestMapper.toUpdateLoanRenewCommand(id, patchLoanRenewV1Request))
                .thenReturn(updateLoanRenewCommand);
        when(this.updateLoanRenewUseCase.execute(updateLoanRenewCommand)).thenReturn(loan);
        when(this.loanResponseMapper.toLoanV1Response(loan)).thenReturn(loanV1ResponseDto);

        this.mockMvc.perform(patch(URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(patchLoanRenewV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.patchLoanRenewRequestMapper, times(1)).toUpdateLoanRenewCommand(id, patchLoanRenewV1Request);
        verify(this.updateLoanRenewUseCase, times(1)).execute(updateLoanRenewCommand);
        verify(this.loanResponseMapper, times(1)).toLoanV1Response(loan);
    }
}
