package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoansV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.GetLoansV1RequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanMapper;

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

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get loans controller. */
    @InjectMocks
    GetLoansController getLoansController;

    /** The loan retrieval persistence port. */
    @Mock
    LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The get loans V1 request mapper. */
    @Mock
    GetLoansV1RequestMapper getLoansV1RequestMapper;

    /** The loan mapper. */
    @Mock
    LoanMapper loanMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        var auth = new UsernamePasswordAuthenticationToken(
            1, null, List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getLoansController).build();
    }

    /** Tear down. */
    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    /**
     * Test get loans v 1 when no criteria then return all loans.
     */
    @Test
    void testGetLoansV1_whenNoCriteria_thenReturnAllLoans() throws Exception {
        
        // Given
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().build();
        final var loans = Instancio.createList(Loan.class);
        final var loanV1DtoList = Instancio.createList(LoanV1Dto.class);

        // When
        when(this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(null)).thenReturn(loanSearchCriteriaCommand);
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loans);
        when(this.loanMapper.toLoanV1List(loans)).thenReturn(loanV1DtoList);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getLoansV1RequestMapper, times(1)).toLoanSearchCriteriaCommand(null);
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanMapper, times(1)).toLoanV1List(loans);
    }

    /**
     * Test get loans v 1 when criteria provided then return filtered loans.
     */
    @Test
    void testGetLoansV1_whenCriteriaProvided_thenReturnFilteredLoans() throws Exception {
        
        // Given
        final var userId = Instancio.create(Integer.class);
        final var getLoansV1Request = new GetLoansV1Request();
        getLoansV1Request.setUserId(userId);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().userId(userId).build();
        final var loans = Instancio.createList(Loan.class);
        final var loanV1DtoList = Instancio.createList(LoanV1Dto.class);

        // When
        when(this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(getLoansV1Request)).thenReturn(loanSearchCriteriaCommand);
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loans);
        when(this.loanMapper.toLoanV1List(loans)).thenReturn(loanV1DtoList);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getLoansV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getLoansV1RequestMapper, times(1)).toLoanSearchCriteriaCommand(getLoansV1Request);
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanMapper, times(1)).toLoanV1List(loans);
    }

    /**
     * Test get loans v 1 when id provided then return matching loans.
     */
    @Test
    void testGetLoansV1_whenIdProvided_thenReturnMatchingLoans() throws Exception {
        
        // Given
        final var id = Instancio.create(Integer.class);
        final var getLoansV1Request = new GetLoansV1Request();
        getLoansV1Request.setId(id);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(id).build();
        final var loans = Instancio.createList(Loan.class);
        final var loanV1DtoList = Instancio.createList(LoanV1Dto.class);

        // When
        when(this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(getLoansV1Request)).thenReturn(loanSearchCriteriaCommand);
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loans);
        when(this.loanMapper.toLoanV1List(loans)).thenReturn(loanV1DtoList);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getLoansV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getLoansV1RequestMapper, times(1)).toLoanSearchCriteriaCommand(getLoansV1Request);
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanMapper, times(1)).toLoanV1List(loans);
    }
}
