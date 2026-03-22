package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.model.UpdateBookCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchBookV1Request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Patch Book Request Mapper. */
@Mapper(componentModel = "spring")
public interface PatchBookRequestMapper {

    /**
     * To update book command.
     *
     * @param id                 The book identifier.
     * @param patchBookV1Request The patch book v1 request.
     * @return The update book command.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "patchBookV1Request.title", target = "title")
    @Mapping(source = "patchBookV1Request.author", target = "author")
    @Mapping(source = "patchBookV1Request.genre", target = "genre")
    @Mapping(source = "patchBookV1Request.totalCopies", target = "totalCopies")
    @Mapping(source = "patchBookV1Request.availableCopies", target = "availableCopies")
    UpdateBookCommand toUpdateBookCommand(Long id, PatchBookV1Request patchBookV1Request);

}
