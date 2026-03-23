package com.breixo.library.infrastructure.adapter.output.repository.user;

import java.util.List;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.infrastructure.adapter.output.entities.UserEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.UserEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.UserMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class User Retrieval Repository Test. */
@ExtendWith(MockitoExtension.class)
class UserRetrievalRepositoryTest {

    /** The user retrieval persistence repository. */
    @InjectMocks
    UserRetrievalPersistenceRepository userRetrievalPersistenceRepository;

    /** The user my batis mapper. */
    @Mock
    UserMyBatisMapper userMyBatisMapper;

    /** The user entity mapper. */
    @Mock
    UserEntityMapper userEntityMapper;

    /**
     * Test execute when user found then return user.
     */
    @Test
    void testExecute_whenUserFound_thenReturnUser() {
        // Given
        final var userSearchCriteriaCommand = Instancio.create(UserSearchCriteriaCommand.class);
        final var userEntity = Instancio.create(UserEntity.class);
        final var user = Instancio.create(User.class);

        // When
        when(this.userMyBatisMapper.find(userSearchCriteriaCommand)).thenReturn(List.of(userEntity));
        when(this.userEntityMapper.toUser(userEntity)).thenReturn(user);
        final var result = this.userRetrievalPersistenceRepository.execute(userSearchCriteriaCommand);

        // Then
        verify(this.userMyBatisMapper, times(1)).find(userSearchCriteriaCommand);
        verify(this.userEntityMapper, times(1)).toUser(userEntity);
        assertEquals(user, result);
    }

    /**
     * Test execute when user not found then throw user not found exception.
     */
    @Test
    void testExecute_whenUserNotFound_thenThrowUserNotFoundException() {
        // Given
        final var userSearchCriteriaCommand = Instancio.create(UserSearchCriteriaCommand.class);

        // When
        when(this.userMyBatisMapper.find(userSearchCriteriaCommand)).thenReturn(List.of());
        final var userException = assertThrows(UserException.class,
                () -> this.userRetrievalPersistenceRepository.execute(userSearchCriteriaCommand));

        // Then
        verify(this.userMyBatisMapper, times(1)).find(userSearchCriteriaCommand);
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR, userException.getMessage());
    }
}
