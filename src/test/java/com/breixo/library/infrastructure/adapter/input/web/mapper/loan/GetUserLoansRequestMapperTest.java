package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get User Loans Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class GetUserLoansRequestMapperTest {

    /** The get user loans request mapper. */
    @InjectMocks
    GetUserLoansRequestMapperImpl getUserLoansRequestMapper;

    /**
     * Test to get user loans command when valid parameters then return mapped command.
     */
    @Test
    void testToGetUserLoansCommand_whenValidParameters_thenReturnMappedCommand() {

        // Given
        final var requesterId = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);

        // When
        final var getUserLoansCommand = this.getUserLoansRequestMapper.toGetUserLoansCommand(requesterId, userId);

        // Then
        assertNotNull(getUserLoansCommand);
        assertEquals(requesterId, getUserLoansCommand.requesterId());
        assertEquals(userId, getUserLoansCommand.userId());
    }

    /**
     * Test to get user loans command when both parameters are null then return null.
     */
    @Test
    void testToGetUserLoansCommand_whenBothParametersAreNull_thenReturnNull() {
        // When / Then
        assertNull(this.getUserLoansRequestMapper.toGetUserLoansCommand(null, null));
    }

    /**
     * Test to get user loans command when requester id is null then return command with null requester id.
     */
    @Test
    void testToGetUserLoansCommand_whenRequesterIdIsNull_thenReturnCommandWithNullRequesterId() {

        // Given
        final var userId = Instancio.create(Integer.class);

        // When
        final var getUserLoansCommand = this.getUserLoansRequestMapper.toGetUserLoansCommand(null, userId);

        // Then
        assertNotNull(getUserLoansCommand);
        assertNull(getUserLoansCommand.requesterId());
        assertEquals(userId, getUserLoansCommand.userId());
    }

    /**
     * Test to get user loans command when user id is null then return command with null user id.
     */
    @Test
    void testToGetUserLoansCommand_whenUserIdIsNull_thenReturnCommandWithNullUserId() {

        // Given
        final var requesterId = Instancio.create(Integer.class);

        // When
        final var getUserLoansCommand = this.getUserLoansRequestMapper.toGetUserLoansCommand(requesterId, null);

        // Then
        assertNotNull(getUserLoansCommand);
        assertEquals(requesterId, getUserLoansCommand.requesterId());
        assertNull(getUserLoansCommand.userId());
    }
}
