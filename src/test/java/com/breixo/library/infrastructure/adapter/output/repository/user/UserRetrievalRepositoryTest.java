package com.breixo.library.infrastructure.adapter.output.repository.user;

import java.util.List;
import java.util.Optional;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class User Retrieval Repository Test. */
@ExtendWith(MockitoExtension.class)
class UserRetrievalRepositoryTest {

    /** The user retrieval persistence repository. */
    @InjectMocks
    UserRetrievalRepository userRetrievalRepository;

    /** The user my batis mapper. */
    @Mock
    UserMyBatisMapper userMyBatisMapper;

    /** The user entity mapper. */
    @Mock
    UserEntityMapper userEntityMapper;

    /**
     * Test find when user found then return user.
     */
    @Test
    void testFind_whenUserFound_thenReturnUser() {
        // Given
        final var userSearchCriteriaCommand = Instancio.create(UserSearchCriteriaCommand.class);
        final var userEntity = Instancio.create(UserEntity.class);
        final var user = Instancio.create(User.class);

        // When
        when(this.userMyBatisMapper.find(userSearchCriteriaCommand)).thenReturn(List.of(userEntity));
        when(this.userEntityMapper.toUser(userEntity)).thenReturn(user);
        final var result = this.userRetrievalRepository.find(userSearchCriteriaCommand);

        // Then
        verify(this.userMyBatisMapper, times(1)).find(userSearchCriteriaCommand);
        verify(this.userEntityMapper, times(1)).toUser(userEntity);
        assertEquals(Optional.of(user), result);
    }

    /**
     * Test find when user not found then return empty optional.
     */
    @Test
    void testFind_whenUserNotFound_thenReturnEmptyOptional() {
        // Given
        final var userSearchCriteriaCommand = Instancio.create(UserSearchCriteriaCommand.class);

        // When
        when(this.userMyBatisMapper.find(userSearchCriteriaCommand)).thenReturn(List.of());
        final var result = this.userRetrievalRepository.find(userSearchCriteriaCommand);

        // Then
        verify(this.userMyBatisMapper, times(1)).find(userSearchCriteriaCommand);
        assertTrue(result.isEmpty());
    }

    /**
     * Test find all when users exist then return users.
     */
    @Test
    void testFindAll_whenUsersExist_thenReturnUsers() {
        // Given
        final var userEntities = Instancio.createList(UserEntity.class);
        final var users = Instancio.createList(User.class);

        // When
        when(this.userMyBatisMapper.findAll()).thenReturn(userEntities);
        when(this.userEntityMapper.toUserList(userEntities)).thenReturn(users);
        final var result = this.userRetrievalRepository.findAll();

        // Then
        verify(this.userMyBatisMapper, times(1)).findAll();
        verify(this.userEntityMapper, times(1)).toUserList(userEntities);
        assertEquals(users, result);
    }

    /**
     * Test find all when no users exist then return empty list.
     */
    @Test
    void testFindAll_whenNoUsersExist_thenReturnEmptyList() {
        // Given
        final var users = List.<User>of();

        // When
        when(this.userMyBatisMapper.findAll()).thenReturn(List.of());
        when(this.userEntityMapper.toUserList(List.of())).thenReturn(users);
        final var result = this.userRetrievalRepository.findAll();

        // Then
        verify(this.userMyBatisMapper, times(1)).findAll();
        verify(this.userEntityMapper, times(1)).toUserList(List.of());
        assertTrue(result.isEmpty());
    }
}
