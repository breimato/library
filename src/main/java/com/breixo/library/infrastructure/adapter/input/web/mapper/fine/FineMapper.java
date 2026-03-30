package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import java.util.List;

import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
import com.breixo.library.infrastructure.mapper.FineStatusMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Fine Mapper. */
@Mapper(componentModel = "spring", uses = {FineStatusMapper.class, DateMapper.class})
public interface FineMapper {

    /**
     * To fine V1.
     *
     * @param fine the fine.
     * @return the fine V1 dto.
     */
    @Mapping(source = "status", target = "status")
    FineV1 toFineV1(Fine fine);

    /**
     * To fine V1 list.
     *
     * @param fines the fines.
     * @return the fine V1 list.
     */
    List<FineV1> toFineV1List(List<Fine> fines);
}
