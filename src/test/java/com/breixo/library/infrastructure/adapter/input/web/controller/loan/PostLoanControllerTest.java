package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.input.loan.CreateLoanUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostLoanV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.PostLoanRequestMapper;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Post Loan Controller Test. */
@ExtendWith(MockitoExtension.class)
class PostLoanControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/loans";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The post loan controller. */
    @InjectMocks
    PostLoanController postLoanController;

    /** The create loan use case. */
    @Mock
    CreateLoanUseCase createLoanUseCase;

    /** The post loan request mapper. */
    @Mock
    PostLoanRequestMapper postLoanRequestMapper;

    /** The loan response mapper. */
    @Mock
    LoanResponseMapper loanResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.postLoanController).build();
    }

    /**
     * Test post loan v 1 when request is valid then return created response.
     */
    @Test
    void testPostLoanV1_whenRequestIsValid_thenReturnCreatedResponse() throws Exception {

        // Given
        final var postLoanV1Request = Instancio.create(PostLoanV1Request.class);
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
        final var loan = Instancio.create(Loan.class);
        final var loanV1ResponseDto = Instancio.create(LoanV1ResponseDto.class);

        // When
        when(this.postLoanRequestMapper.toCreateLoanCommand(postLoanV1Request)).thenReturn(createLoanCommand);
        when(this.createLoanUseCase.execute(createLoanCommand)).thenReturn(loan);
        when(this.loanResponseMapper.toLoanV1Response(loan)).thenReturn(loanV1ResponseDto);

        this.mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(postLoanV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.postLoanRequestMapper, times(1)).toCreateLoanCommand(postLoanV1Request);
        verify(this.createLoanUseCase, times(1)).execute(createLoanCommand);
        verify(this.loanResponseMapper, times(1)).toLoanV1Response(loan);
    }
}
