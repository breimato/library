package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.command.loan.UpdateLoanReturnCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.input.loan.UpdateLoanReturnUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanReturnV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.PatchLoanReturnRequestMapper;

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

/** The Class Patch Loan Return Controller Test. */
@ExtendWith(MockitoExtension.class)
class PatchLoanReturnControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/loans/{id}/return";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The patch loan return controller. */
    @InjectMocks
    PatchLoanReturnController patchLoanReturnController;

    /** The update loan return use case. */
    @Mock
    UpdateLoanReturnUseCase updateLoanReturnUseCase;

    /** The patch loan return request mapper. */
    @Mock
    PatchLoanReturnRequestMapper patchLoanReturnRequestMapper;

    /** The loan response mapper. */
    @Mock
    LoanResponseMapper loanResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.patchLoanReturnController).build();
    }

    /**
     * Test patch loan return v 1 when loan exists then return ok response.
     */
    @Test
    void testPatchLoanReturnV1_whenLoanExists_thenReturnOkResponse() throws Exception {
        // Given
        final var id = Instancio.create(Integer.class);
        final var patchLoanReturnV1Request = Instancio.create(PatchLoanReturnV1Request.class);
        final var updateLoanReturnCommand = Instancio.create(UpdateLoanReturnCommand.class);
        final var loan = Instancio.create(Loan.class);
        final var loanV1ResponseDto = Instancio.create(LoanV1ResponseDto.class);

        // When
        when(this.patchLoanReturnRequestMapper.toUpdateLoanReturnCommand(id, patchLoanReturnV1Request)).thenReturn(updateLoanReturnCommand);
        when(this.updateLoanReturnUseCase.execute(updateLoanReturnCommand)).thenReturn(loan);
        when(this.loanResponseMapper.toLoanV1Response(loan)).thenReturn(loanV1ResponseDto);

        this.mockMvc.perform(patch(URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(patchLoanReturnV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.patchLoanReturnRequestMapper, times(1)).toUpdateLoanReturnCommand(id, patchLoanReturnV1Request);
        verify(this.updateLoanReturnUseCase, times(1)).execute(updateLoanReturnCommand);
        verify(this.loanResponseMapper, times(1)).toLoanV1Response(loan);
    }
}
