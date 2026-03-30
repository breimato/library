package com.breixo.library.infrastructure.adapter.output.repository.fine;

import java.util.List;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.FineEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.FineMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Fine Retrieval Repository. */
@Component
@RequiredArgsConstructor
public class FineRetrievalRepository implements FineRetrievalPersistencePort {

    /** The Fine My Batis Mapper. */
    private final FineMyBatisMapper fineMyBatisMapper;

    /** The Fine Entity Mapper. */
    private final FineEntityMapper fineEntityMapper;

    /** {@inheritDoc} */
    @Override
    public List<Fine> find(@Valid @NotNull final FineSearchCriteriaCommand fineSearchCriteriaCommand) {

        final var fineEntities = this.fineMyBatisMapper.find(fineSearchCriteriaCommand);

        return this.fineEntityMapper.toFineList(fineEntities);
    }
}
