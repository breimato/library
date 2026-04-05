package com.breixo.library.infrastructure.adapter.input.web.controller.loanrequest;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoanRequestsV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanRequestV1;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.GetLoanRequestsV1RequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.LoanRequestMapper;

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

/** The Class Get Loan Requests Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetLoanRequestsControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/loan-requests";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get loan requests controller. */
    @InjectMocks
    GetLoanRequestsController getLoanRequestsController;

    /** The loan request retrieval persistence port. */
    @Mock
    LoanRequestRetrievalPersistencePort loanRequestRetrievalPersistencePort;

    /** The get loan requests V1 request mapper. */
    @Mock
    GetLoanRequestsV1RequestMapper getLoanRequestsV1RequestMapper;

    /** The loan request mapper. */
    @Mock
    LoanRequestMapper loanRequestMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        var auth = new UsernamePasswordAuthenticationToken(
            1, null, List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getLoanRequestsController).build();
    }

    /** Tear down. */
    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    /**
     * Test get loan requests v1 when no criteria then return all loan requests.
     */
    @Test
    void testGetLoanRequestsV1_whenNoCriteria_thenReturnAllLoanRequests() throws Exception {

        // Given
        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().build();
        final var loanRequests = Instancio.createList(LoanRequest.class);
        final var loanRequestV1List = Instancio.createList(LoanRequestV1.class);

        // When
        when(this.getLoanRequestsV1RequestMapper.toLoanRequestSearchCriteriaCommand(null)).thenReturn(searchCriteria);
        when(this.loanRequestRetrievalPersistencePort.find(searchCriteria)).thenReturn(loanRequests);
        when(this.loanRequestMapper.toLoanRequestV1List(loanRequests)).thenReturn(loanRequestV1List);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getLoanRequestsV1RequestMapper, times(1)).toLoanRequestSearchCriteriaCommand(null);
        verify(this.loanRequestRetrievalPersistencePort, times(1)).find(searchCriteria);
        verify(this.loanRequestMapper, times(1)).toLoanRequestV1List(loanRequests);
    }

    /**
     * Test get loan requests v1 when criteria provided then return filtered loan requests.
     */
    @Test
    void testGetLoanRequestsV1_whenCriteriaProvided_thenReturnFilteredLoanRequests() throws Exception {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var getLoanRequestsV1Request = new GetLoanRequestsV1Request();
        getLoanRequestsV1Request.setUserId(userId);
        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().userId(userId).build();
        final var loanRequests = Instancio.createList(LoanRequest.class);
        final var loanRequestV1List = Instancio.createList(LoanRequestV1.class);

        // When
        when(this.getLoanRequestsV1RequestMapper.toLoanRequestSearchCriteriaCommand(getLoanRequestsV1Request))
                .thenReturn(searchCriteria);
        when(this.loanRequestRetrievalPersistencePort.find(searchCriteria)).thenReturn(loanRequests);
        when(this.loanRequestMapper.toLoanRequestV1List(loanRequests)).thenReturn(loanRequestV1List);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getLoanRequestsV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getLoanRequestsV1RequestMapper, times(1))
                .toLoanRequestSearchCriteriaCommand(getLoanRequestsV1Request);
        verify(this.loanRequestRetrievalPersistencePort, times(1)).find(searchCriteria);
        verify(this.loanRequestMapper, times(1)).toLoanRequestV1List(loanRequests);
    }
}
