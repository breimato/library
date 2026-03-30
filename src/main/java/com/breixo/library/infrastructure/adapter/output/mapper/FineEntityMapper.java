package com.breixo.library.infrastructure.adapter.output.mapper;

import java.util.List;

import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
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
}
