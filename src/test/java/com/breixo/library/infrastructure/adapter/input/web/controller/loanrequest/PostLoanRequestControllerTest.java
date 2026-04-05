package com.breixo.library.infrastructure.adapter.input.web.controller.loanrequest;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.port.input.loanrequest.CreateLoanRequestUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanRequestV1Response;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostLoanRequestV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.LoanRequestResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.PostLoanRequestRequestMapper;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Post Loan Request Controller Test. */
@ExtendWith(MockitoExtension.class)
class PostLoanRequestControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/loan-requests";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The post loan request controller. */
    @InjectMocks
    PostLoanRequestController postLoanRequestController;

    /** The create loan request use case. */
    @Mock
    CreateLoanRequestUseCase createLoanRequestUseCase;

    /** The post loan request request mapper. */
    @Mock
    PostLoanRequestRequestMapper postLoanRequestRequestMapper;

    /** The loan request response mapper. */
    @Mock
    LoanRequestResponseMapper loanRequestResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        var auth = new UsernamePasswordAuthenticationToken(
            1, null, List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.postLoanRequestController).build();
    }

    /** Tear down. */
    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    /**
     * Test post loan request v1 when request is valid then return created response.
     */
    @Test
    void testPostLoanRequestV1_whenRequestIsValid_thenReturnCreatedResponse() throws Exception {

        // Given
        final var postLoanRequestV1Request = Instancio.create(PostLoanRequestV1Request.class);
        final var createLoanRequestCommand = Instancio.create(CreateLoanRequestCommand.class);
        final var loanRequest = Instancio.create(LoanRequest.class);
        final var loanRequestV1Response = Instancio.create(LoanRequestV1Response.class);

        // When
        when(this.postLoanRequestRequestMapper.toCreateLoanRequestCommand(postLoanRequestV1Request))
                .thenReturn(createLoanRequestCommand);
        when(this.createLoanRequestUseCase.execute(any())).thenReturn(loanRequest);
        when(this.loanRequestResponseMapper.toLoanRequestV1Response(loanRequest)).thenReturn(loanRequestV1Response);

        this.mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(postLoanRequestV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.postLoanRequestRequestMapper, times(1)).toCreateLoanRequestCommand(postLoanRequestV1Request);
        verify(this.createLoanRequestUseCase, times(1)).execute(any());
        verify(this.loanRequestResponseMapper, times(1)).toLoanRequestV1Response(loanRequest);
    }
}
