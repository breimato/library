package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get Users V1 Request Mapper Test. */
class GetUsersV1RequestMapperTest {

    /** The get Users V1 request mapper. */
    final GetUsersV1RequestMapper getUsersV1RequestMapper = new GetUsersV1RequestMapperImpl();

    /** Test mapping when AllFilters then return matching criteria. */
    @Test
    void testToUserSearchCriteriaCommand_whenAllFilters_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var name = Instancio.create(String.class);
        final var email = Instancio.create(String.class);
        final var status = Instancio.create(Integer.class);

        // When
        final var userSearchCriteriaCommand = this.getUsersV1RequestMapper.toUserSearchCriteriaCommand(id, name, email, status);

        // Then
        assertNotNull(userSearchCriteriaCommand);
        assertEquals(id, userSearchCriteriaCommand.getId());
        assertEquals(name, userSearchCriteriaCommand.getName());
        assertEquals(email, userSearchCriteriaCommand.getEmail());
        assertEquals(status, userSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when NoFilters then return matching criteria. */
    @Test
    void testToUserSearchCriteriaCommand_whenNoFilters_thenReturnMatchingCriteria() {

        // Given

        // When
        final var userSearchCriteriaCommand = this.getUsersV1RequestMapper.toUserSearchCriteriaCommand(null, null, null, null);

        // Then
        assertNotNull(userSearchCriteriaCommand);
        assertNull(userSearchCriteriaCommand.getId());
        assertNull(userSearchCriteriaCommand.getName());
        assertNull(userSearchCriteriaCommand.getEmail());
        assertNull(userSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Id then return matching criteria. */
    @Test
    void testToUserSearchCriteriaCommand_whenId_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var userSearchCriteriaCommand = this.getUsersV1RequestMapper.toUserSearchCriteriaCommand(id, null, null, null);

        // Then
        assertNotNull(userSearchCriteriaCommand);
        assertEquals(id, userSearchCriteriaCommand.getId());
        assertNull(userSearchCriteriaCommand.getName());
        assertNull(userSearchCriteriaCommand.getEmail());
        assertNull(userSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Name then return matching criteria. */
    @Test
    void testToUserSearchCriteriaCommand_whenName_thenReturnMatchingCriteria() {

        // Given
        final var name = Instancio.create(String.class);

        // When
        final var userSearchCriteriaCommand = this.getUsersV1RequestMapper.toUserSearchCriteriaCommand(null, name, null, null);

        // Then
        assertNotNull(userSearchCriteriaCommand);
        assertNull(userSearchCriteriaCommand.getId());
        assertEquals(name, userSearchCriteriaCommand.getName());
        assertNull(userSearchCriteriaCommand.getEmail());
        assertNull(userSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Email then return matching criteria. */
    @Test
    void testToUserSearchCriteriaCommand_whenEmail_thenReturnMatchingCriteria() {

        // Given
        final var email = Instancio.create(String.class);

        // When
        final var userSearchCriteriaCommand = this.getUsersV1RequestMapper.toUserSearchCriteriaCommand(null, null, email, null);

        // Then
        assertNotNull(userSearchCriteriaCommand);
        assertNull(userSearchCriteriaCommand.getId());
        assertNull(userSearchCriteriaCommand.getName());
        assertEquals(email, userSearchCriteriaCommand.getEmail());
        assertNull(userSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Status then return matching criteria. */
    @Test
    void testToUserSearchCriteriaCommand_whenStatus_thenReturnMatchingCriteria() {

        // Given
        final var status = Instancio.create(Integer.class);

        // When
        final var userSearchCriteriaCommand = this.getUsersV1RequestMapper.toUserSearchCriteriaCommand(null, null, null, status);

        // Then
        assertNotNull(userSearchCriteriaCommand);
        assertNull(userSearchCriteriaCommand.getId());
        assertNull(userSearchCriteriaCommand.getName());
        assertNull(userSearchCriteriaCommand.getEmail());
        assertEquals(status, userSearchCriteriaCommand.getStatusId());
    }
}
