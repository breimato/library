package com.breixo.library.infrastructure.adapter.output.mapper.loanrequest;

import java.util.List;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import com.breixo.library.infrastructure.adapter.output.entities.LoanRequestEntity;
import com.breixo.library.infrastructure.mapper.LoanRequestStatusMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Loan Request Entity Mapper Test. */
@ExtendWith(MockitoExtension.class)
class LoanRequestEntityMapperTest {

    /** The loan request entity mapper. */
    @InjectMocks
    LoanRequestEntityMapperImpl loanRequestEntityMapper;

    /** The loan request status mapper. */
    @Mock
    LoanRequestStatusMapper loanRequestStatusMapper;

    /**
     * Test to loan request when entity is valid then return mapped loan request.
     */
    @Test
    void testToLoanRequest_whenEntityIsValid_thenReturnMappedLoanRequest() {

        // Given
        final var loanRequestEntity = Instancio.create(LoanRequestEntity.class);
        loanRequestEntity.setStatusId(LoanRequestStatus.PENDING.getId());

        // When
        when(this.loanRequestStatusMapper.toLoanRequestStatus(loanRequestEntity.getStatusId()))
                .thenReturn(LoanRequestStatus.PENDING);
        final var loanRequest = this.loanRequestEntityMapper.toLoanRequest(loanRequestEntity);

        // Then
        verify(this.loanRequestStatusMapper, times(1)).toLoanRequestStatus(loanRequestEntity.getStatusId());
        assertNotNull(loanRequest);
        assertEquals(loanRequestEntity.getId(), loanRequest.id());
        assertEquals(loanRequestEntity.getUserId(), loanRequest.userId());
        assertEquals(loanRequestEntity.getBookId(), loanRequest.bookId());
        assertEquals(loanRequestEntity.getRequestDate(), loanRequest.requestDate());
        assertEquals(loanRequestEntity.getApprovalDate(), loanRequest.approvalDate());
        assertEquals(LoanRequestStatus.PENDING, loanRequest.status());
        assertEquals(loanRequestEntity.getRejectionReason(), loanRequest.rejectionReason());
    }

    /**
     * Test to loan request when entity is null then return null.
     */
    @Test
    void testToLoanRequest_whenEntityIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanRequestEntityMapper.toLoanRequest(null));
    }

    /**
     * Test to loan request entity when create command is valid then return mapped entity.
     */
    @Test
    void testToLoanRequestEntity_whenCreateCommandIsValid_thenReturnMappedEntity() {

        // Given
        final var createLoanRequestCommand = Instancio.create(CreateLoanRequestCommand.class);

        // When
        final var loanRequestEntity = this.loanRequestEntityMapper.toLoanRequestEntity(createLoanRequestCommand);

        // Then
        assertNotNull(loanRequestEntity);
        assertEquals(createLoanRequestCommand.userId(), loanRequestEntity.getUserId());
        assertEquals(createLoanRequestCommand.bookId(), loanRequestEntity.getBookId());
    }

    /**
     * Test to loan request entity when create command is null then return null.
     */
    @Test
    void testToLoanRequestEntity_whenCreateCommandIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanRequestEntityMapper.toLoanRequestEntity((CreateLoanRequestCommand) null));
    }

    /**
     * Test to loan request entity when update command is valid then return mapped entity.
     */
    @Test
    void testToLoanRequestEntity_whenUpdateCommandIsValid_thenReturnMappedEntity() {

        // Given
        final var updateLoanRequestCommand = Instancio.create(UpdateLoanRequestCommand.class);

        // When
        when(this.loanRequestStatusMapper.toStatusId(updateLoanRequestCommand.status()))
                .thenReturn(updateLoanRequestCommand.status().getId());
        final var loanRequestEntity = this.loanRequestEntityMapper.toLoanRequestEntity(updateLoanRequestCommand);

        // Then
        verify(this.loanRequestStatusMapper, times(1)).toStatusId(updateLoanRequestCommand.status());
        assertNotNull(loanRequestEntity);
        assertEquals(updateLoanRequestCommand.id(), loanRequestEntity.getId());
        assertEquals(updateLoanRequestCommand.status().getId(), loanRequestEntity.getStatusId());
        assertEquals(updateLoanRequestCommand.rejectionReason(), loanRequestEntity.getRejectionReason());
    }

    /**
     * Test to loan request entity when update command is null then return null.
     */
    @Test
    void testToLoanRequestEntity_whenUpdateCommandIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanRequestEntityMapper.toLoanRequestEntity((UpdateLoanRequestCommand) null));
    }

    /**
     * Test to loan request list when entities are valid then return mapped list.
     */
    @Test
    void testToLoanRequestList_whenEntitiesAreValid_thenReturnMappedList() {

        // Given
        final var loanRequestEntity = Instancio.create(LoanRequestEntity.class);
        loanRequestEntity.setStatusId(LoanRequestStatus.PENDING.getId());
        final var loanRequestEntities = List.of(loanRequestEntity);

        // When
        when(this.loanRequestStatusMapper.toLoanRequestStatus(loanRequestEntity.getStatusId()))
                .thenReturn(LoanRequestStatus.PENDING);
        final var loanRequests = this.loanRequestEntityMapper.toLoanRequestList(loanRequestEntities);

        // Then
        verify(this.loanRequestStatusMapper, times(1)).toLoanRequestStatus(loanRequestEntity.getStatusId());
        assertNotNull(loanRequests);
        assertEquals(1, loanRequests.size());
        assertEquals(loanRequestEntity.getId(), loanRequests.getFirst().id());
    }

    /**
     * Test to loan request list when list is empty then return empty list.
     */
    @Test
    void testToLoanRequestList_whenListIsEmpty_thenReturnEmptyList() {
        // When
        final var loanRequests = this.loanRequestEntityMapper.toLoanRequestList(List.of());

        // Then
        assertNotNull(loanRequests);
        assertEquals(0, loanRequests.size());
    }

    /**
     * Test to loan request list when list is null then return null.
     */
    @Test
    void testToLoanRequestList_whenListIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanRequestEntityMapper.toLoanRequestList(null));
    }
}
