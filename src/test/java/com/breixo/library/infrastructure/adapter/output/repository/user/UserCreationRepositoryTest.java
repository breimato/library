package com.breixo.library.infrastructure.adapter.output.repository.user;

import java.util.List;

import com.breixo.library.domain.command.user.CreateUserCommand;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class User Creation Repository Test. */
@ExtendWith(MockitoExtension.class)
class UserCreationRepositoryTest {

    /** The user creation repository. */
    @InjectMocks
    UserCreationRepository userCreationRepository;

    /** The user my batis mapper. */
    @Mock
    UserMyBatisMapper userMyBatisMapper;

    /** The user entity mapper. */
    @Mock
    UserEntityMapper userEntityMapper;

    /**
     * Test execute when command is valid then create and return user.
     */
    @Test
    void testExecute_whenCommandIsValid_thenCreateAndReturnUser() {
        
        // Given
        final var createUserCommand = Instancio.create(CreateUserCommand.class);
        final var userEntity = Instancio.create(UserEntity.class);
        final var createdUserEntity = Instancio.create(UserEntity.class);
        final var user = Instancio.create(User.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(userEntity.getId()).build();

        // When
        when(this.userEntityMapper.toUserEntity(createUserCommand)).thenReturn(userEntity);
        when(this.userMyBatisMapper.find(userSearchCriteriaCommand)).thenReturn(List.of(createdUserEntity));
        when(this.userEntityMapper.toUser(createdUserEntity)).thenReturn(user);
        final var result = this.userCreationRepository.execute(createUserCommand);

        // Then
        verify(this.userEntityMapper, times(1)).toUserEntity(createUserCommand);
        verify(this.userMyBatisMapper, times(1)).insert(userEntity);
        verify(this.userMyBatisMapper, times(1)).find(userSearchCriteriaCommand);
        verify(this.userEntityMapper, times(1)).toUser(createdUserEntity);
        assertEquals(user, result);
    }
}
