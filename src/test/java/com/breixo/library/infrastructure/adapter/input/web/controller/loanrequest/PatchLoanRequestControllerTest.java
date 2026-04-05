package com.breixo.library.infrastructure.adapter.input.web.controller.loanrequest;

import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.port.input.loanrequest.UpdateLoanRequestUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanRequestV1Response;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanRequestV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.LoanRequestResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.PatchLoanRequestRequestMapper;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Patch Loan Request Controller Test. */
@ExtendWith(MockitoExtension.class)
class PatchLoanRequestControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/loan-requests/{id}";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The patch loan request controller. */
    @InjectMocks
    PatchLoanRequestController patchLoanRequestController;

    /** The update loan request use case. */
    @Mock
    UpdateLoanRequestUseCase updateLoanRequestUseCase;

    /** The patch loan request request mapper. */
    @Mock
    PatchLoanRequestRequestMapper patchLoanRequestRequestMapper;

    /** The loan request response mapper. */
    @Mock
    LoanRequestResponseMapper loanRequestResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        var auth = new UsernamePasswordAuthenticationToken(
            1, null, List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.patchLoanRequestController).build();
    }

    /** Tear down. */
    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    /**
     * Test patch loan request v1 when request is valid then return ok response.
     */
    @Test
    void testPatchLoanRequestV1_whenRequestIsValid_thenReturnOkResponse() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var patchLoanRequestV1Request = Instancio.create(PatchLoanRequestV1Request.class);
        final var updateLoanRequestCommand = Instancio.create(UpdateLoanRequestCommand.class);
        final var loanRequest = Instancio.create(LoanRequest.class);
        final var loanRequestV1Response = Instancio.create(LoanRequestV1Response.class);

        // When
        when(this.patchLoanRequestRequestMapper.toUpdateLoanRequestCommand(id, patchLoanRequestV1Request))
                .thenReturn(updateLoanRequestCommand);
        when(this.updateLoanRequestUseCase.execute(any())).thenReturn(loanRequest);
        when(this.loanRequestResponseMapper.toLoanRequestV1Response(loanRequest)).thenReturn(loanRequestV1Response);

        this.mockMvc.perform(patch(URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(patchLoanRequestV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.patchLoanRequestRequestMapper, times(1)).toUpdateLoanRequestCommand(id, patchLoanRequestV1Request);
        verify(this.updateLoanRequestUseCase, times(1)).execute(any());
        verify(this.loanRequestResponseMapper, times(1)).toLoanRequestV1Response(loanRequest);
    }
}
