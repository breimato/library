package com.breixo.library.infrastructure.adapter.input.web.controller.reservation;

import com.breixo.library.domain.command.reservation.GetUserReservationsCommand;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.port.input.reservation.GetUserReservationsUseCase;
import com.breixo.library.infrastructure.adapter.input.web.dto.ReservationV1;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.GetUserReservationsRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.ReservationMapper;

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

/** The Class Get User Reservations Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetUserReservationsControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/users/{id}/reservations";

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get user reservations controller. */
    @InjectMocks
    GetUserReservationsController getUserReservationsController;

    /** The get user reservations use case. */
    @Mock
    GetUserReservationsUseCase getUserReservationsUseCase;

    /** The get user reservations request mapper. */
    @Mock
    GetUserReservationsRequestMapper getUserReservationsRequestMapper;

    /** The reservation mapper. */
    @Mock
    ReservationMapper reservationMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getUserReservationsController).build();
    }

    /**
     * Test get user reservations v 1 when valid request then return reservations.
     */
    @Test
    void testGetUserReservationsV1_whenValidRequest_thenReturnReservations() throws Exception {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var requesterId = Instancio.create(Integer.class);
        final var getUserReservationsCommand = Instancio.create(GetUserReservationsCommand.class);
        final var reservations = Instancio.createList(Reservation.class);
        final var reservationV1List = Instancio.createList(ReservationV1.class);

        // When
        when(this.getUserReservationsRequestMapper.toGetUserReservationsCommand(requesterId, userId))
                .thenReturn(getUserReservationsCommand);
        when(this.getUserReservationsUseCase.execute(getUserReservationsCommand)).thenReturn(reservations);
        when(this.reservationMapper.toReservationV1List(reservations)).thenReturn(reservationV1List);

        this.mockMvc.perform(get(URL, userId)
                        .header("X-Requester-Id", requesterId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getUserReservationsRequestMapper, times(1)).toGetUserReservationsCommand(requesterId, userId);
        verify(this.getUserReservationsUseCase, times(1)).execute(getUserReservationsCommand);
        verify(this.reservationMapper, times(1)).toReservationV1List(reservations);
    }
}
