package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import com.breixo.library.domain.command.fine.GetUserFinesCommand;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.port.input.fine.GetUserFinesUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.FineMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.GetUserFinesRequestMapper;

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

/** The Class Get User Fines Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetUserFinesControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/users/{id}/fines";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get user fines controller. */
    @InjectMocks
    GetUserFinesController getUserFinesController;

    /** The get user fines use case. */
    @Mock
    GetUserFinesUseCase getUserFinesUseCase;

    /** The get user fines request mapper. */
    @Mock
    GetUserFinesRequestMapper getUserFinesRequestMapper;

    /** The fine mapper. */
    @Mock
    FineMapper fineMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getUserFinesController).build();
    }

    /**
     * Test get user fines v 1 when valid request then return fines.
     */
    @Test
    void testGetUserFinesV1_whenValidRequest_thenReturnFines() throws Exception {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var requesterId = Instancio.create(Integer.class);
        final var getUserFinesCommand = Instancio.create(GetUserFinesCommand.class);
        final var fines = Instancio.createList(Fine.class);
        final var fineV1List = Instancio.createList(FineV1.class);

        // When
        when(this.getUserFinesRequestMapper.toGetUserFinesCommand(requesterId, userId)).thenReturn(getUserFinesCommand);
        when(this.getUserFinesUseCase.execute(getUserFinesCommand)).thenReturn(fines);
        when(this.fineMapper.toFineV1List(fines)).thenReturn(fineV1List);

        this.mockMvc.perform(get(URL, userId)
                        .header("X-Requester-Id", requesterId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getUserFinesRequestMapper, times(1)).toGetUserFinesCommand(requesterId, userId);
        verify(this.getUserFinesUseCase, times(1)).execute(getUserFinesCommand);
        verify(this.fineMapper, times(1)).toFineV1List(fines);
    }
}
