package com.breixo.library.infrastructure.adapter.output.repository.fine;

import com.breixo.library.domain.command.fine.CreateFineCommand;
import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.exception.FineException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.port.output.fine.FineCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.output.entities.FineEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.FineEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.FineMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Fine Creation Repository. */
@Component
@RequiredArgsConstructor
public class FineCreationRepository implements FineCreationPersistencePort {

    /** The Fine My Batis Mapper. */
    private final FineMyBatisMapper fineMyBatisMapper;

    /** The Fine Entity Mapper. */
    private final FineEntityMapper fineEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Fine execute(@Valid @NotNull final CreateFineCommand createFineCommand) {

        final var fineEntity = this.insert(createFineCommand);

        return this.find(fineEntity.getId());
    }

    /**
     * Insert.
     *
     * @param createFineCommand the create fine command.
     * @return the fine entity.
     */
    private FineEntity insert(final CreateFineCommand createFineCommand) {

        final var fineEntity = this.fineEntityMapper.toFineEntity(createFineCommand);

        try {
            this.fineMyBatisMapper.insert(fineEntity);

        } catch (final Exception exception) {
            throw new FineException(
                    ExceptionMessageConstants.FINE_CREATION_ERROR_CODE_ERROR,
                    ExceptionMessageConstants.FINE_CREATION_ERROR_MESSAGE_ERROR);
        }
        return fineEntity;
    }

    /**
     * Find.
     *
     * @param id the fine identifier.
     * @return the fine.
     */
    private Fine find(final Integer id) {

        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(id).build();

        final var fineEntity = this.fineMyBatisMapper.find(fineSearchCriteriaCommand).getFirst();

        return this.fineEntityMapper.toFine(fineEntity);
    }
}
