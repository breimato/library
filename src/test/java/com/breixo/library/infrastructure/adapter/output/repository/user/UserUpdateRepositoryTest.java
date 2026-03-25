package com.breixo.library.infrastructure.adapter.output.repository.user;

import java.util.List;

import com.breixo.library.domain.command.user.UpdateUserCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
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

import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class User Update Repository Test. */
@ExtendWith(MockitoExtension.class)
class UserUpdateRepositoryTest {

    /** The user update persistence repository. */
    @InjectMocks
    UserUpdatePersistenceRepository userUpdatePersistenceRepository;

    /** The user my batis mapper. */
    @Mock
    UserMyBatisMapper userMyBatisMapper;

    /** The user entity mapper. */
    @Mock
    UserEntityMapper userEntityMapper;

    /**
     * Test execute when user exists then return updated user.
     */
    @Test
    void testExecute_whenUserExists_thenReturnUpdatedUser() {
        // Given
        final var updateUserCommand = Instancio.create(UpdateUserCommand.class);
        final var userEntity = Instancio.create(UserEntity.class);
        final var updatedUserEntity = Instancio.create(UserEntity.class);
        final var user = Instancio.create(User.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(updateUserCommand.id()).build();

        // When
        when(this.userEntityMapper.toUserEntity(updateUserCommand)).thenReturn(userEntity);
        when(this.userMyBatisMapper.find(userSearchCriteriaCommand)).thenReturn(List.of(updatedUserEntity));
        when(this.userEntityMapper.toUser(updatedUserEntity)).thenReturn(user);
        final var result = this.userUpdatePersistenceRepository.execute(updateUserCommand);

        // Then
        verify(this.userMyBatisMapper, times(1)).update(userEntity);
        verify(this.userMyBatisMapper, times(1)).find(userSearchCriteriaCommand);
        verify(this.userEntityMapper, times(1)).toUser(updatedUserEntity);
        assertEquals(user, result);
    }

    /**
     * Test execute when update throws exception then throw user exception.
     */
    @Test
    void testExecute_whenUpdateThrowsException_thenThrowUserException() {
        // Given
        final var updateUserCommand = Instancio.create(UpdateUserCommand.class);
        final var userEntity = Instancio.create(UserEntity.class);

        // When
        when(this.userEntityMapper.toUserEntity(updateUserCommand)).thenReturn(userEntity);
        doThrow(new RuntimeException()).when(this.userMyBatisMapper).update(userEntity);
        final var userException = assertThrows(UserException.class,
                () -> this.userUpdatePersistenceRepository.execute(updateUserCommand));

        // Then
        verify(this.userEntityMapper, times(1)).toUserEntity(updateUserCommand);
        verify(this.userMyBatisMapper, times(1)).update(userEntity);
        assertEquals(ExceptionMessageConstants.USER_UPDATE_ERROR_CODE_ERROR, userException.getCode());
        assertEquals(ExceptionMessageConstants.USER_UPDATE_ERROR_MESSAGE_ERROR, userException.getMessage());
    }
}
