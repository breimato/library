package com.breixo.library.infrastructure.adapter.input.web.controller.reservation;

import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.port.input.reservation.UpdateReservationUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchReservationV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.ReservationV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.PatchReservationRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.ReservationResponseMapper;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

/** The Class Patch Reservation Controller Test. */
@ExtendWith(MockitoExtension.class)
class PatchReservationControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/reservations/{id}";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The patch reservation controller. */
    @InjectMocks
    PatchReservationController patchReservationController;

    /** The update reservation use case. */
    @Mock
    UpdateReservationUseCase updateReservationUseCase;

    /** The patch reservation request mapper. */
    @Mock
    PatchReservationRequestMapper patchReservationRequestMapper;

    /** The reservation response mapper. */
    @Mock
    ReservationResponseMapper reservationResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        var auth = new UsernamePasswordAuthenticationToken(
            1, null, List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.patchReservationController).build();
    }

    /** Tear down. */
    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    /**
     * Test patch reservation v 1 when request is valid then return ok response.
     */
    @Test
    void testPatchReservationV1_whenRequestIsValid_thenReturnOkResponse() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var patchReservationV1Request = Instancio.create(PatchReservationV1Request.class);
        final var updateReservationCommand = Instancio.create(UpdateReservationCommand.class);
        final var reservation = Instancio.create(Reservation.class);
        final var reservationV1Response = Instancio.create(ReservationV1Response.class);

        // When
        when(this.patchReservationRequestMapper.toUpdateReservationCommand(id, patchReservationV1Request))
                .thenReturn(updateReservationCommand);
        when(this.updateReservationUseCase.execute(any())).thenReturn(reservation);
        when(this.reservationResponseMapper.toReservationV1Response(reservation)).thenReturn(reservationV1Response);

        this.mockMvc.perform(patch(URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(patchReservationV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.patchReservationRequestMapper, times(1)).toUpdateReservationCommand(id, patchReservationV1Request);
        verify(this.updateReservationUseCase, times(1)).execute(any());
        verify(this.reservationResponseMapper, times(1)).toReservationV1Response(reservation);
    }
}
