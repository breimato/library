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

    /** The user retrieval repository. */
    @InjectMocks
    UserRetrievalRepository userRetrievalRepository;

    /** The user my batis mapper. */
    @Mock
    UserMyBatisMapper userMyBatisMapper;

    /** The user entity mapper. */
    @Mock
    UserEntityMapper userEntityMapper;

    /**
     * Test find when no criteria then return all users.
     */
    @Test
    void testFind_whenNoCriteria_thenReturnAllUsers() {
        
        // Given
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().build();
        final var userEntities = Instancio.createList(UserEntity.class);
        final var users = Instancio.createList(User.class);

        // When
        when(this.userMyBatisMapper.find(userSearchCriteriaCommand)).thenReturn(userEntities);
        when(this.userEntityMapper.toUserList(userEntities)).thenReturn(users);
        final var result = this.userRetrievalRepository.find(userSearchCriteriaCommand);

        // Then
        verify(this.userMyBatisMapper, times(1)).find(userSearchCriteriaCommand);
        verify(this.userEntityMapper, times(1)).toUserList(userEntities);
        assertEquals(users, result);
    }

    /**
     * Test find when criteria provided then return filtered users.
     */
    @Test
    void testFind_whenCriteriaProvided_thenReturnFilteredUsers() {
        
        // Given
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder()
                .name("John")
                .build();
        final var userEntities = Instancio.createList(UserEntity.class);
        final var users = Instancio.createList(User.class);

        // When
        when(this.userMyBatisMapper.find(userSearchCriteriaCommand)).thenReturn(userEntities);
        when(this.userEntityMapper.toUserList(userEntities)).thenReturn(users);
        final var result = this.userRetrievalRepository.find(userSearchCriteriaCommand);

        // Then
        verify(this.userMyBatisMapper, times(1)).find(userSearchCriteriaCommand);
        verify(this.userEntityMapper, times(1)).toUserList(userEntities);
        assertEquals(users, result);
    }

    /**
     * Test find when no users exist then return empty list.
     */
    @Test
    void testFind_whenNoUsersExist_thenReturnEmptyList() {
        
        // Given
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().build();
        final var users = List.<User>of();

        // When
        when(this.userMyBatisMapper.find(userSearchCriteriaCommand)).thenReturn(List.of());
        when(this.userEntityMapper.toUserList(List.of())).thenReturn(users);
        final var result = this.userRetrievalRepository.find(userSearchCriteriaCommand);

        // Then
        verify(this.userMyBatisMapper, times(1)).find(userSearchCriteriaCommand);
        verify(this.userEntityMapper, times(1)).toUserList(List.of());
        assertTrue(result.isEmpty());
    }

    /**
     * Test find by id when user exists then return optional user.
     */
    @Test
    void testFindById_whenUserExists_thenReturnOptionalUser() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(id).build();
        final var userEntity = Instancio.create(UserEntity.class);
        final var user = Instancio.create(User.class);

        // When
        when(this.userMyBatisMapper.find(userSearchCriteriaCommand)).thenReturn(List.of(userEntity));
        when(this.userEntityMapper.toUserList(List.of(userEntity))).thenReturn(List.of(user));
        final var result = this.userRetrievalRepository.findById(id);

        // Then
        verify(this.userMyBatisMapper, times(1)).find(userSearchCriteriaCommand);
        verify(this.userEntityMapper, times(1)).toUserList(List.of(userEntity));
        assertEquals(Optional.of(user), result);
    }

    /**
     * Test find by id when user not found then return optional empty.
     */
    @Test
    void testFindById_whenUserNotFound_thenReturnOptionalEmpty() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(id).build();

        // When
        when(this.userMyBatisMapper.find(userSearchCriteriaCommand)).thenReturn(List.of());
        when(this.userEntityMapper.toUserList(List.of())).thenReturn(List.of());
        final var result = this.userRetrievalRepository.findById(id);

        // Then
        verify(this.userMyBatisMapper, times(1)).find(userSearchCriteriaCommand);
        verify(this.userEntityMapper, times(1)).toUserList(List.of());
        assertEquals(Optional.empty(), result);
    }
}
