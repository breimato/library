package com.breixo.library.infrastructure.adapter.output.mapper;

import java.util.List;

import com.breixo.library.domain.command.fine.CreateFineCommand;
import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.infrastructure.adapter.output.entities.FineEntity;
import com.breixo.library.infrastructure.mapper.FineStatusMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Fine Entity Mapper. */
@Mapper(componentModel = "spring", uses = FineStatusMapper.class)
public interface FineEntityMapper {

    /**
     * To fine.
     *
     * @param fineEntity the fine entity.
     * @return the fine.
     */
    @Mapping(source = "statusId", target = "status")
    Fine toFine(FineEntity fineEntity);

    /**
     * To fine list.
     *
     * @param fineEntities the fine entities.
     * @return the fine list.
     */
    List<Fine> toFineList(List<FineEntity> fineEntities);

    /**
     * To fine entity.
     *
     * @param createFineCommand the create fine command.
     * @return the fine entity.
     */
    FineEntity toFineEntity(CreateFineCommand createFineCommand);

    /**
     * To fine entity.
     *
     * @param updateFineCommand the update fine command.
     * @return the fine entity.
     */
    @Mapping(source = "status", target = "statusId")
    @Mapping(target = "loanId", ignore = true)
    FineEntity toFineEntity(UpdateFineCommand updateFineCommand);
}
