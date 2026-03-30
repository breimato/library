package com.breixo.library.infrastructure.adapter.output.repository.fine;

import com.breixo.library.infrastructure.adapter.output.mybatis.FineMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/** The Class Fine Deletion Repository Test. */
@ExtendWith(MockitoExtension.class)
class FineDeletionRepositoryTest {

    /** The fine deletion repository. */
    @InjectMocks
    FineDeletionRepository fineDeletionRepository;

    /** The fine my batis mapper. */
    @Mock
    FineMyBatisMapper fineMyBatisMapper;

    /**
     * Test execute when id is valid then delete fine.
     */
    @Test
    void testExecute_whenIdIsValid_thenDeleteFine() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        this.fineDeletionRepository.execute(id);

        // Then
        verify(this.fineMyBatisMapper, times(1)).delete(id);
    }
}
