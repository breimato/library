package com.breixo.library.infrastructure.adapter.output.repository.fine;

import com.breixo.library.domain.port.output.fine.FineDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mybatis.FineMyBatisMapper;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Fine Deletion Repository. */
@Component
@RequiredArgsConstructor
public class FineDeletionRepository implements FineDeletionPersistencePort {

    /** The fine my batis mapper. */
    private final FineMyBatisMapper fineMyBatisMapper;

    /** {@inheritDoc} */
    @Override
    public void execute(@NotNull final Integer id) {
        this.fineMyBatisMapper.delete(id);
    }
}
