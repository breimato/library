package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetFinesV1Request;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get Fines V1 Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class GetFinesV1RequestMapperTest {

    /** The get fines V1 request mapper. */
    @InjectMocks
    GetFinesV1RequestMapperImpl getFinesV1RequestMapper;

    /**
     * Test to fine search criteria command when request is valid then return command.
     */
    @Test
    void testToFineSearchCriteriaCommand_whenRequestIsValid_thenReturnCommand() {

        // Given
        final var getFinesV1Request = Instancio.create(GetFinesV1Request.class);
        final var statusId = Instancio.create(Integer.class);
        getFinesV1Request.setStatus(statusId);

        // When
        final var fineSearchCriteriaCommand = this.getFinesV1RequestMapper.toFineSearchCriteriaCommand(getFinesV1Request);

        // Then
        assertNotNull(fineSearchCriteriaCommand);
        assertEquals(getFinesV1Request.getId(), fineSearchCriteriaCommand.getId());
        assertEquals(getFinesV1Request.getLoanId(), fineSearchCriteriaCommand.getLoanId());
        assertEquals(statusId, fineSearchCriteriaCommand.getStatusId());
    }

    /**
     * Test to fine search criteria command when request is null then return default command.
     */
    @Test
    void testToFineSearchCriteriaCommand_whenRequestIsNull_thenReturnDefaultCommand() {

        // When
        final var fineSearchCriteriaCommand = this.getFinesV1RequestMapper.toFineSearchCriteriaCommand(null);

        // Then
        assertNotNull(fineSearchCriteriaCommand);
        assertNull(fineSearchCriteriaCommand.getId());
        assertNull(fineSearchCriteriaCommand.getLoanId());
        assertNull(fineSearchCriteriaCommand.getStatusId());
    }
}
