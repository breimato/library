package com.breixo.library.infrastructure.adapter.input.web.controller.reservation;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetReservationsV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.ReservationV1;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.GetReservationsV1RequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.ReservationMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
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

/** The Class Get Reservations Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetReservationsControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/reservations";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The get reservations controller. */
    @InjectMocks
    GetReservationsController getReservationsController;

    /** The reservation retrieval persistence port. */
    @Mock
    ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    /** The get reservations V1 request mapper. */
    @Mock
    GetReservationsV1RequestMapper getReservationsV1RequestMapper;

    /** The reservation mapper. */
    @Mock
    ReservationMapper reservationMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.getReservationsController).build();
    }

    /**
     * Test get reservations v 1 when no criteria then return all reservations.
     */
    @Test
    void testGetReservationsV1_whenNoCriteria_thenReturnAllReservations() throws Exception {

        // Given
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().build();
        final var reservations = Instancio.createList(Reservation.class);
        final var reservationV1DtoList = Instancio.createList(ReservationV1.class);

        // When
        when(this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(null))
                .thenReturn(reservationSearchCriteriaCommand);
        when(this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand)).thenReturn(reservations);
        when(this.reservationMapper.toReservationV1List(reservations)).thenReturn(reservationV1DtoList);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getReservationsV1RequestMapper, times(1)).toReservationSearchCriteriaCommand(null);
        verify(this.reservationRetrievalPersistencePort, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationMapper, times(1)).toReservationV1List(reservations);
    }

    /**
     * Test get reservations v 1 when criteria provided then return filtered reservations.
     */
    @Test
    void testGetReservationsV1_whenCriteriaProvided_thenReturnFilteredReservations() throws Exception {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var getReservationsV1Request = new GetReservationsV1Request();
        getReservationsV1Request.setUserId(userId);
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().userId(userId).build();
        final var reservations = Instancio.createList(Reservation.class);
        final var reservationV1DtoList = Instancio.createList(ReservationV1.class);

        // When
        when(this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(getReservationsV1Request))
                .thenReturn(reservationSearchCriteriaCommand);
        when(this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand)).thenReturn(reservations);
        when(this.reservationMapper.toReservationV1List(reservations)).thenReturn(reservationV1DtoList);

        this.mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getReservationsV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.getReservationsV1RequestMapper, times(1))
                .toReservationSearchCriteriaCommand(getReservationsV1Request);
        verify(this.reservationRetrievalPersistencePort, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationMapper, times(1)).toReservationV1List(reservations);
    }
}
