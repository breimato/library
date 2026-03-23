package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchBookV1Response;

import org.mapstruct.Mapper;

/** The Interface Patch Book Response Mapper. */
@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface PatchBookResponseMapper {

    /**
     * To patch book v1 response.
     *
     * @param book The book.
     * @return The patch book v1 response.
     */
    PatchBookV1Response toPatchBookV1Response(Book book);

}
