package com.breixo.library.infrastructure.adapter.output.repository.fine;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.domain.exception.FineException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.port.output.fine.FineUpdatePersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.fine.FineEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.FineMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Fine Update Repository. */
@Component
@RequiredArgsConstructor
public class FineUpdateRepository implements FineUpdatePersistencePort {

    /** The Fine My Batis Mapper. */
    private final FineMyBatisMapper fineMyBatisMapper;

    /** The Fine Entity Mapper. */
    private final FineEntityMapper fineEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Fine execute(@Valid @NotNull final UpdateFineCommand updateFineCommand) {
        this.update(updateFineCommand);
        return this.find(updateFineCommand.id());
    }

    /**
     * Update.
     *
     * @param updateFineCommand the update fine command.
     */
    private void update(final UpdateFineCommand updateFineCommand) {

        final var fineEntity = this.fineEntityMapper.toFineEntity(updateFineCommand);

        try {
            this.fineMyBatisMapper.update(fineEntity);

        } catch (final Exception exception) {
            throw new FineException(
                    ExceptionMessageConstants.FINE_UPDATE_ERROR_CODE_ERROR,
                    ExceptionMessageConstants.FINE_UPDATE_ERROR_MESSAGE_ERROR);
        }
    }

    /**
     * Find.
     *
     * @param id the fine identifier.
     * @return the fine.
     */
    private Fine find(final Integer id) {

        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().id(id).build();

        final var fineEntities = this.fineMyBatisMapper.find(fineSearchCriteriaCommand);

        return this.fineEntityMapper.toFine(fineEntities.getFirst());
    }
}
