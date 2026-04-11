package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchFineV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.common.DateMapper;
import com.breixo.library.infrastructure.mapper.FineStatusMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Patch Fine Request Mapper. */
@Mapper(componentModel = "spring", uses = {FineStatusMapper.class, DateMapper.class})
public interface PatchFineRequestMapper {

    /**
     * To update fine command.
     *
     * @param id                 the fine identifier.
     * @param patchFineV1Request the patch fine V1 request.
     * @return the update fine command.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "patchFineV1Request.amountEuros", target = "amountEuros")
    @Mapping(source = "patchFineV1Request.status", target = "status")
    @Mapping(source = "patchFineV1Request.paidAt", target = "paidAt")
    UpdateFineCommand toUpdateFineCommand(Integer id, PatchFineV1Request patchFineV1Request);
}
