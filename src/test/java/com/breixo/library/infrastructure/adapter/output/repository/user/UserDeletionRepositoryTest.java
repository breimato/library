package com.breixo.library.infrastructure.adapter.output.repository.user;

import com.breixo.library.infrastructure.adapter.output.mybatis.UserMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/** The Class User Deletion Repository Test. */
@ExtendWith(MockitoExtension.class)
class UserDeletionRepositoryTest {

    /** The user deletion persistence repository. */
    @InjectMocks
    UserDeletionPersistenceRepository userDeletionPersistenceRepository;

    /** The user my batis mapper. */
    @Mock
    UserMyBatisMapper userMyBatisMapper;

    /**
     * Test execute when called then delete user.
     */
    @Test
    void testExecute_whenCalled_thenDeleteUser() {
        // Given
        final var id = Instancio.create(Integer.class);

        // When
        this.userDeletionPersistenceRepository.execute(id);

        // Then
        verify(this.userMyBatisMapper, times(1)).delete(id);
    }
}
