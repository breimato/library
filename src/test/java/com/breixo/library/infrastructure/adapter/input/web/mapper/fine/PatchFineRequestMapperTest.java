package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import java.time.LocalDateTime;

import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchFineV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.common.DateMapper;
import com.breixo.library.infrastructure.mapper.FineStatusMapper;

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

/** The Class Patch Fine Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PatchFineRequestMapperTest {

    /** The patch fine request mapper. */
    @InjectMocks
    PatchFineRequestMapperImpl patchFineRequestMapper;

    /** The fine status mapper. */
    @Mock
    FineStatusMapper fineStatusMapper;

    /** The date mapper. */
    @Mock
    DateMapper dateMapper;

    /**
     * Test to update fine command when request is valid then return command.
     */
    @Test
    void testToUpdateFineCommand_whenRequestIsValid_thenReturnCommand() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var patchFineV1Request = Instancio.create(PatchFineV1Request.class);
        final var fineStatus = Instancio.create(FineStatus.class);
        final var paidAt = Instancio.create(LocalDateTime.class);

        // When
        when(this.fineStatusMapper.toFineStatus(patchFineV1Request.getStatus())).thenReturn(fineStatus);
        when(this.dateMapper.toLocalDateTime(patchFineV1Request.getPaidAt())).thenReturn(paidAt);
        final var updateFineCommand = this.patchFineRequestMapper.toUpdateFineCommand(id, patchFineV1Request);

        // Then
        verify(this.fineStatusMapper, times(1)).toFineStatus(patchFineV1Request.getStatus());
        verify(this.dateMapper, times(1)).toLocalDateTime(patchFineV1Request.getPaidAt());
        assertNotNull(updateFineCommand);
        assertEquals(id, updateFineCommand.id());
        assertEquals(fineStatus, updateFineCommand.status());
        assertEquals(paidAt, updateFineCommand.paidAt());
    }

    /**
     * Test to update fine command when request fields are null then return command with nulls.
     */
    @Test
    void testToUpdateFineCommand_whenRequestFieldsAreNull_thenReturnCommandWithNulls() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var patchFineV1Request = new PatchFineV1Request();

        // When
        final var updateFineCommand = this.patchFineRequestMapper.toUpdateFineCommand(id, patchFineV1Request);

        // Then
        assertNotNull(updateFineCommand);
        assertEquals(id, updateFineCommand.id());
        assertNull(updateFineCommand.amountEuros());
        assertNull(updateFineCommand.status());
        assertNull(updateFineCommand.paidAt());
    }

    /**
     * Test to update fine command when id and request are null then return null.
     */
    @Test
    void testToUpdateFineCommand_whenIdAndRequestAreNull_thenReturnNull() {
        // When / Then
        assertNull(this.patchFineRequestMapper.toUpdateFineCommand(null, null));
    }

    /**
     * Test to update fine command when id is null and request exists then return command.
     */
    @Test
    void testToUpdateFineCommand_whenIdIsNullAndRequestExists_thenReturnCommand() {

        // Given
        final var patchFineV1Request = new PatchFineV1Request();

        // When
        final var updateFineCommand = this.patchFineRequestMapper.toUpdateFineCommand(null, patchFineV1Request);

        // Then
        assertNotNull(updateFineCommand);
        assertNull(updateFineCommand.id());
    }

    /**
     * Test to update fine command when id exists and request is null then return command.
     */
    @Test
    void testToUpdateFineCommand_whenIdExistsAndRequestIsNull_thenReturnCommand() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var updateFineCommand = this.patchFineRequestMapper.toUpdateFineCommand(id, null);

        // Then
        assertNotNull(updateFineCommand);
        assertEquals(id, updateFineCommand.id());
        assertNull(updateFineCommand.amountEuros());
        assertNull(updateFineCommand.status());
        assertNull(updateFineCommand.paidAt());
    }
}
