package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.model.CreateBookCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Request;

import org.mapstruct.Mapper;

/** The Interface Post Book Request Mapper. */
@Mapper(componentModel = "spring")
public interface PostBookRequestMapper {

    /**
     * To create book command.
     *
     * @param postBookV1Request The post book v1 request.
     * @return The create book command.
     */
    CreateBookCommand toCreateBookCommand(PostBookV1Request postBookV1Request);

}
