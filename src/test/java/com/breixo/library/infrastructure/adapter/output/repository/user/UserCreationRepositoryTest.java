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
import static org.mockito.Mockito.when;

/** The Class User Creation Repository Test. */
@ExtendWith(MockitoExtension.class)
class UserCreationRepositoryTest {

    /** The user creation persistence repository. */
    @InjectMocks
    UserCreationPersistenceRepository userCreationPersistenceRepository;

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
        final var command = CreateUserCommand.builder().name("John").email("john@example.com").phone("123456789").build();
        final var userEntity = Instancio.create(UserEntity.class);
        final var user = Instancio.create(User.class);
        final var idCriteria = UserSearchCriteriaCommand.builder().id(userEntity.getId()).build();

        // When
        when(this.userEntityMapper.toUserEntity(command)).thenReturn(userEntity);
        when(this.userMyBatisMapper.find(idCriteria)).thenReturn(List.of(userEntity));
        when(this.userEntityMapper.toUser(userEntity)).thenReturn(user);
        final var result = this.userCreationPersistenceRepository.execute(command);

        // Then
        assertEquals(user, result);
    }
}
