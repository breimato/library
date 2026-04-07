package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.command.book.CreateBookCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Request;
import com.breixo.library.infrastructure.mapper.IsbnMapper;

import org.mapstruct.Mapper;

/** The Interface Post Book Request Mapper. */
@Mapper(componentModel = "spring", uses = IsbnMapper.class)
public interface PostBookRequestMapper {

    /**
     * To create book command.
     *
     * @param requesterId       The requester id.
     * @param postBookV1Request The post book v1 request.
     * @return The create book command.
     */
    @org.mapstruct.Mapping(source = "requesterId", target = "requesterId")
    @org.mapstruct.Mapping(source = "postBookV1Request.isbn", target = "isbn")
    @org.mapstruct.Mapping(source = "postBookV1Request.title", target = "title")
    @org.mapstruct.Mapping(source = "postBookV1Request.author", target = "author")
    @org.mapstruct.Mapping(source = "postBookV1Request.genre", target = "genre")
    @org.mapstruct.Mapping(source = "postBookV1Request.totalCopies", target = "totalCopies")
    @org.mapstruct.Mapping(source = "postBookV1Request.availableCopies", target = "availableCopies")
    CreateBookCommand toCreateBookCommand(Integer requesterId, PostBookV1Request postBookV1Request);

}
