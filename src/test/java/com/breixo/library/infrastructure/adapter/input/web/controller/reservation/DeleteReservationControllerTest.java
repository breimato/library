package com.breixo.library.infrastructure.adapter.input.web.controller.reservation;

import com.breixo.library.domain.port.output.reservation.ReservationDeletionPersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Delete Reservation Controller Test. */
@ExtendWith(MockitoExtension.class)
class DeleteReservationControllerTest {

    /** The Constant URL. */
    static final String URL = "/v1/library/reservations/{id}";

    /** The Mock Mvc. */
    MockMvc mockMvc;

    /** The Delete Reservation Controller. */
    @InjectMocks
    DeleteReservationController deleteReservationController;

    /** The Reservation Deletion Persistence Port. */
    @Mock
    ReservationDeletionPersistencePort reservationDeletionPersistencePort;

    /** Sets the up. */
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.deleteReservationController).build();
    }

    /**
     * Test delete reservation v 1 when reservation exists then return no content.
     */
    @Test
    void testDeleteReservationV1_whenReservationExists_thenReturnNoContent() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        this.mockMvc.perform(delete(URL, id))
                .andExpect(status().isNoContent());

        // Then
        verify(this.reservationDeletionPersistencePort, times(1)).execute(id);
    }
}
