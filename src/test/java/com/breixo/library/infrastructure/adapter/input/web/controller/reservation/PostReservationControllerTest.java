package com.breixo.library.infrastructure.adapter.input.web.controller.reservation;

import com.breixo.library.domain.command.reservation.CreateReservationCommand;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.port.output.reservation.ReservationCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostReservationV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.ReservationV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.PostReservationRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.ReservationResponseMapper;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Post Reservation Controller Test. */
@ExtendWith(MockitoExtension.class)
class PostReservationControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/reservations";

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The post reservation controller. */
    @InjectMocks
    PostReservationController postReservationController;

    /** The reservation creation persistence port. */
    @Mock
    ReservationCreationPersistencePort reservationCreationPersistencePort;

    /** The post reservation request mapper. */
    @Mock
    PostReservationRequestMapper postReservationRequestMapper;

    /** The reservation response mapper. */
    @Mock
    ReservationResponseMapper reservationResponseMapper;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.postReservationController).build();
    }

    /**
     * Test post reservation v 1 when request is valid then return created response.
     */
    @Test
    void testPostReservationV1_whenRequestIsValid_thenReturnCreatedResponse() throws Exception {

        // Given
        final var postReservationV1Request = Instancio.create(PostReservationV1Request.class);
        final var createReservationCommand = Instancio.create(CreateReservationCommand.class);
        final var reservation = Instancio.create(Reservation.class);
        final var reservationV1Response = Instancio.create(ReservationV1Response.class);

        // When
        when(this.postReservationRequestMapper.toCreateReservationCommand(postReservationV1Request))
                .thenReturn(createReservationCommand);
        when(this.reservationCreationPersistencePort.execute(createReservationCommand)).thenReturn(reservation);
        when(this.reservationResponseMapper.toReservationV1Response(reservation)).thenReturn(reservationV1Response);

        this.mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(postReservationV1Request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Then
        verify(this.postReservationRequestMapper, times(1)).toCreateReservationCommand(postReservationV1Request);
        verify(this.reservationCreationPersistencePort, times(1)).execute(createReservationCommand);
        verify(this.reservationResponseMapper, times(1)).toReservationV1Response(reservation);
    }
}
