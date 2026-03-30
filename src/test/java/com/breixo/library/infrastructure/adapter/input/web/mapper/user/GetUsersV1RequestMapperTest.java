package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.infrastructure.adapter.input.web.dto.GetUsersV1Request;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get Users V1 Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class GetUsersV1RequestMapperTest {

    /** The get users V1 request mapper. */
    @InjectMocks
    GetUsersV1RequestMapperImpl getUsersV1RequestMapper;

    /**
     * Test to user search criteria command when request is valid then return mapped command.
     */
    @Test
    void testToUserSearchCriteriaCommand_whenRequestIsValid_thenReturnMappedCommand() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var name = Instancio.create(String.class);
        final var email = Instancio.create(String.class);
        final var status = Instancio.create(Integer.class);
        final var getUsersV1Request = Instancio.create(GetUsersV1Request.class);
        getUsersV1Request.setId(id);
        getUsersV1Request.setName(name);
        getUsersV1Request.setEmail(email);
        getUsersV1Request.setStatus(status);

        // When
        final var userSearchCriteriaCommand = this.getUsersV1RequestMapper.toUserSearchCriteriaCommand(getUsersV1Request);

        // Then
        assertNotNull(userSearchCriteriaCommand);
        assertEquals(id, userSearchCriteriaCommand.getId());
        assertEquals(name, userSearchCriteriaCommand.getName());
        assertEquals(email, userSearchCriteriaCommand.getEmail());
        assertEquals(status, userSearchCriteriaCommand.getStatusId());
    }

    /**
     * Test to user search criteria command when request is null then return default command.
     */
    @Test
    void testToUserSearchCriteriaCommand_whenRequestIsNull_thenReturnDefaultCommand() {

        // When
        final var userSearchCriteriaCommand = this.getUsersV1RequestMapper.toUserSearchCriteriaCommand(null);

        // Then
        assertNotNull(userSearchCriteriaCommand);
        assertNull(userSearchCriteriaCommand.getId());
        assertNull(userSearchCriteriaCommand.getName());
        assertNull(userSearchCriteriaCommand.getEmail());
        assertNull(userSearchCriteriaCommand.getStatusId());
    }
}
