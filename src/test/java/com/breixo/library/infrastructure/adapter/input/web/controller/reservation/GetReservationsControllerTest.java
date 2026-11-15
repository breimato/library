package com.breixo.library.infrastructure.adapter.input.web.controller.reservation;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.ReservationMapper;
import com.breixo.library.infrastructure.adapter.input.web.dto.ReservationV1;
import com.breixo.library.infrastructure.adapter.input.web.mapper.reservation.GetReservationsV1RequestMapperImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** The Class Get Reservations Controller Test. */
@ExtendWith(MockitoExtension.class)
class GetReservationsControllerTest {

    /** The retrieval persistence port. */
    @Mock
    ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    /** The response mapper. */
    @Mock
    ReservationMapper reservationMapper;

    /** The mock mvc. */
    MockMvc mockMvc;

    /** The object mapper. */
    final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    /** Set up the controller with a real request mapper. */
    @BeforeEach
    void setUp() {

        final var getReservationsV1RequestMapper = new GetReservationsV1RequestMapperImpl();
        final var getReservationsController = new GetReservationsController(this.reservationRetrievalPersistencePort, getReservationsV1RequestMapper, this.reservationMapper);
        this.mockMvc = MockMvcBuilders.standaloneSetup(getReservationsController).build();
    }

    /** Test GET when AllFilters then return matching Reservations. */
    @Test
    void testGetReservationsV1_whenAllFilters_thenReturnMatchingReservations() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var userId = Instancio.create(Integer.class);
        final var userIdQueryValue = userId.toString();
        final var bookId = Instancio.create(Integer.class);
        final var bookIdQueryValue = bookId.toString();
        final var loanId = Instancio.create(Integer.class);
        final var loanIdQueryValue = loanId.toString();
        final var status = Instancio.create(Integer.class);
        final var statusQueryValue = status.toString();
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().id(id).userId(userId).bookId(bookId).loanId(loanId).statusId(status).build();
        final var reservations = Instancio.createList(Reservation.class);
        final var reservationV1List = Instancio.createList(ReservationV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(reservationV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand)).thenReturn(reservations);
        when(this.reservationMapper.toReservationV1List(reservations)).thenReturn(reservationV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations")
                        .queryParam("id", idQueryValue)
                        .queryParam("userId", userIdQueryValue)
                        .queryParam("bookId", bookIdQueryValue)
                        .queryParam("loanId", loanIdQueryValue)
                        .queryParam("status", statusQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationMapper, times(1)).toReservationV1List(reservations);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("reservations"));
    }

    /** Test GET when NoFilters then return matching Reservations. */
    @Test
    void testGetReservationsV1_whenNoFilters_thenReturnMatchingReservations() throws Exception {

        // Given
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().build();
        final var reservations = Instancio.createList(Reservation.class);
        final var reservationV1List = Instancio.createList(ReservationV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(reservationV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand)).thenReturn(reservations);
        when(this.reservationMapper.toReservationV1List(reservations)).thenReturn(reservationV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationMapper, times(1)).toReservationV1List(reservations);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("reservations"));
    }

    /** Test GET when Id then return matching Reservations. */
    @Test
    void testGetReservationsV1_whenId_thenReturnMatchingReservations() throws Exception {

        // Given
        final var id = Instancio.create(Integer.class);
        final var idQueryValue = id.toString();
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().id(id).build();
        final var reservations = Instancio.createList(Reservation.class);
        final var reservationV1List = Instancio.createList(ReservationV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(reservationV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand)).thenReturn(reservations);
        when(this.reservationMapper.toReservationV1List(reservations)).thenReturn(reservationV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations")
                        .queryParam("id", idQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationMapper, times(1)).toReservationV1List(reservations);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("reservations"));
    }

    /** Test GET when UserId then return matching Reservations. */
    @Test
    void testGetReservationsV1_whenUserId_thenReturnMatchingReservations() throws Exception {

        // Given
        final var userId = Instancio.create(Integer.class);
        final var userIdQueryValue = userId.toString();
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().userId(userId).build();
        final var reservations = Instancio.createList(Reservation.class);
        final var reservationV1List = Instancio.createList(ReservationV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(reservationV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand)).thenReturn(reservations);
        when(this.reservationMapper.toReservationV1List(reservations)).thenReturn(reservationV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations")
                        .queryParam("userId", userIdQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationMapper, times(1)).toReservationV1List(reservations);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("reservations"));
    }

    /** Test GET when BookId then return matching Reservations. */
    @Test
    void testGetReservationsV1_whenBookId_thenReturnMatchingReservations() throws Exception {

        // Given
        final var bookId = Instancio.create(Integer.class);
        final var bookIdQueryValue = bookId.toString();
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().bookId(bookId).build();
        final var reservations = Instancio.createList(Reservation.class);
        final var reservationV1List = Instancio.createList(ReservationV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(reservationV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand)).thenReturn(reservations);
        when(this.reservationMapper.toReservationV1List(reservations)).thenReturn(reservationV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations")
                        .queryParam("bookId", bookIdQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationMapper, times(1)).toReservationV1List(reservations);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("reservations"));
    }

    /** Test GET when LoanId then return matching Reservations. */
    @Test
    void testGetReservationsV1_whenLoanId_thenReturnMatchingReservations() throws Exception {

        // Given
        final var loanId = Instancio.create(Integer.class);
        final var loanIdQueryValue = loanId.toString();
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().loanId(loanId).build();
        final var reservations = Instancio.createList(Reservation.class);
        final var reservationV1List = Instancio.createList(ReservationV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(reservationV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand)).thenReturn(reservations);
        when(this.reservationMapper.toReservationV1List(reservations)).thenReturn(reservationV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations")
                        .queryParam("loanId", loanIdQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationMapper, times(1)).toReservationV1List(reservations);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("reservations"));
    }

    /** Test GET when Status then return matching Reservations. */
    @Test
    void testGetReservationsV1_whenStatus_thenReturnMatchingReservations() throws Exception {

        // Given
        final var status = Instancio.create(Integer.class);
        final var statusQueryValue = status.toString();
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder().statusId(status).build();
        final var reservations = Instancio.createList(Reservation.class);
        final var reservationV1List = Instancio.createList(ReservationV1.class);
        final var expectedJson = this.objectMapper.writeValueAsString(reservationV1List);
        final var expectedJsonNode = this.objectMapper.readTree(expectedJson);

        // When
        when(this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand)).thenReturn(reservations);
        when(this.reservationMapper.toReservationV1List(reservations)).thenReturn(reservationV1List);
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations")
                        .queryParam("status", statusQueryValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationMapper, times(1)).toReservationV1List(reservations);
        final var responseContent = mvcResult.getResponse().getContentAsString();
        final var actualJsonNode = this.objectMapper.readTree(responseContent);
        assertEquals(expectedJsonNode, actualJsonNode.get("reservations"));
    }

    /** Test GET when id is invalid then return bad request. */
    @Test
    void testGetReservationsV1_whenIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations").queryParam("id", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.reservationRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when userId is invalid then return bad request. */
    @Test
    void testGetReservationsV1_whenUserIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations").queryParam("userId", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.reservationRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when bookId is invalid then return bad request. */
    @Test
    void testGetReservationsV1_whenBookIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations").queryParam("bookId", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.reservationRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when loanId is invalid then return bad request. */
    @Test
    void testGetReservationsV1_whenLoanIdIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations").queryParam("loanId", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.reservationRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    /** Test GET when status is invalid then return bad request. */
    @Test
    void testGetReservationsV1_whenStatusIsInvalid_thenReturnBadRequest() throws Exception {

        // When
        final var mvcResult = this.mockMvc.perform(get("/v1/library/reservations").queryParam("status", "invalid"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        verifyNoInteractions(this.reservationRetrievalPersistencePort);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }
}
