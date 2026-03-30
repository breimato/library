package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import com.breixo.library.domain.command.fine.CreateFineCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostFineV1Request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Post Fine Request Mapper. */
@Mapper(componentModel = "spring")
public interface PostFineRequestMapper {

    /**
     * To create fine command.
     *
     * @param postFineV1Request the post fine V1 request.
     * @return the create fine command.
     */
    @Mapping(source = "status", target = "statusId")
    CreateFineCommand toCreateFineCommand(PostFineV1Request postFineV1Request);
}
